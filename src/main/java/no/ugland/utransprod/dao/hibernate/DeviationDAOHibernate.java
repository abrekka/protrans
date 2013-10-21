package no.ugland.utransprod.dao.hibernate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.dao.DeviationDAO;
import no.ugland.utransprod.dao.OrderLineDAO;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.Util;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class DeviationDAOHibernate extends BaseDAOHibernate<Deviation>
		implements DeviationDAO {
	/**
	 * 
	 */
	OrderLineDAO orderLineDAO;

	/**
	 * @param orderLineDAO
	 */
	public void setOrderLineDAO(OrderLineDAO orderLineDAO) {
		this.orderLineDAO = orderLineDAO;
	}

	/**
	 * Konstruktør
	 */
	public DeviationDAOHibernate() {
		super(Deviation.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.DeviationDAO#refreshObject(no.ugland.utransprod.model.Deviation)
	 */
	public void refreshObject(Deviation deviation) {
		getHibernateTemplate().load(deviation, deviation.getDeviationId());

	}

	/**
	 * @see no.ugland.utransprod.dao.DeviationDAO#findByJobFunction(no.ugland.utransprod.model.JobFunction)
	 */
	@SuppressWarnings("unchecked")
	public List<Deviation> findByJobFunction(final JobFunction jobFunction) {
		return (List<Deviation>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(Deviation.class).add(
								Restrictions.eq("deviationFunction",
										jobFunction)).list();
					}

				});
	}

	/**
	 * Lazy laster ordrelinjetre
	 * 
	 * @param orderLine
	 */
	void lazyLoadOrderLine(OrderLine orderLine) {
		orderLineDAO.lazyLoad(orderLine, new LazyLoadOrderLineEnum[] {
				LazyLoadOrderLineEnum.ORDER_LINES,
				LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });

		Set<OrderLine> orderLines = orderLine.getOrderLines();
		if (orderLines != null) {
			for (OrderLine line : orderLines) {
				lazyLoadOrderLine(line);
			}
		}
	}

	/**
	 * @see no.ugland.utransprod.dao.DeviationDAO#lazyLoad(no.ugland.utransprod.model.Deviation,
	 *      no.ugland.utransprod.service.enums.LazyLoadDeviationEnum[])
	 */
	public void lazyLoad(final Deviation deviation,
			final LazyLoadDeviationEnum[] enums) {
		if (deviation != null && deviation.getDeviationId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				@SuppressWarnings("incomplete-switch")
				public Object doInHibernate(Session session)
						throws HibernateException {

					session.load(deviation, deviation.getDeviationId());

					Set<?> set;
					List<LazyLoadDeviationEnum> enumList = Arrays.asList(enums);

					for (LazyLoadDeviationEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case COMMENTS:
							set = deviation.getOrderComments();
							set.iterator();
							break;
						case ORDER_LINES:
							Set<OrderLine> orderLines;

							orderLines = deviation.getOrderLines();

							if (orderLines != null) {
								if (enumList
										.contains(LazyLoadDeviationEnum.ORDER_LINE_ORDER_LINES)) {
									for (OrderLine orderLine : orderLines) {
										lazyLoadOrderLine(orderLine);

									}
								} else {
									orderLines.iterator();
								}
							}

							if (deviation.getPostShipment() != null) {
								orderLines = deviation.getPostShipment()
										.getOrderLines();

								if (orderLines != null) {
									if (enumList
											.contains(LazyLoadDeviationEnum.ORDER_LINE_ORDER_LINES)) {
										for (OrderLine orderLine : orderLines) {
											lazyLoadOrderLine(orderLine);
										}
									} else {
										orderLines.iterator();
									}
								}
							}

							break;
						case ORDER_COSTS:
							set = deviation.getOrderCosts();
							set.iterator();
							break;
						}

					}
					return null;
				}

			});

		}

	}

	/**
	 * @see no.ugland.utransprod.dao.DeviationDAO#findByDeviation(no.ugland.utransprod.model.Deviation)
	 */
	@SuppressWarnings("unchecked")
	public List<Deviation> findByDeviation(final Deviation deviation) {
		if (deviation != null) {
			return (List<Deviation>) getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException {
							Criteria crit = session
									.createCriteria(Deviation.class);
                            if(deviation.getInitiatedBy()!=null){
                                crit.add(Restrictions.eq("initiatedBy",
                                        deviation.getInitiatedBy()));
                            }
							if (deviation.getDeviationId() != null) {
								crit.add(Restrictions.eq("deviationId",
										deviation.getDeviationId()));
							}
							if (deviation.getUserName() != null) {
								crit.add(Restrictions.ilike("userName",
										deviation.getUserName()));
							}
							if (deviation.getResponsible() != null) {
								crit.add(Restrictions.ilike("responsible",
										deviation.getResponsible()));
							}
							if (deviation.getOrderNr() != null) {
								crit.add(Restrictions.eq("orderNr", deviation
										.getOrderNr()));
							}
                            if (deviation.getProjectNr() != null) {
                                crit.add(Restrictions.eq("projectNr",
                                        deviation.getProjectNr()));
                            }
							if (deviation.getCustomerNr() != null) {
								crit.add(Restrictions.eq("customerNr",
										deviation.getCustomerNr()));
							}
                            
							if (deviation.getCustomerName() != null) {
								crit.add(Restrictions.ilike("customerName",
										deviation.getCustomerName()));
							}
                            if (deviation.getPreventiveAction() != null) {
                                crit.add(Restrictions.ilike("preventiveAction",
                                        deviation.getPreventiveAction()));
                            }
							if (deviation.getProductArea() != null) {
								crit.add(Restrictions.eq("productArea",
										deviation.getProductArea()));
							}
							if (deviation.getProductName() != null) {
								crit.add(Restrictions.ilike("productName",
										deviation.getProductName()));
							}
							
							if (deviation.getIsPostShipment().equalsIgnoreCase(
									"Ja")) {
								crit
										.add(Restrictions
												.isNotNull("postShipment"));
							}
							if (deviation.getDoAssembly() != null) {
								crit.add(Restrictions.eq("doAssembly",
										deviation.getDoAssembly()));
							}
							if (deviation.getOwnFunction() != null) {
								crit.add(Restrictions.eq("ownFunction",
										deviation.getOwnFunction()));
							}
							if (deviation.getDeviationFunction() != null) {
								crit.add(Restrictions.eq("deviationFunction",
										deviation.getDeviationFunction()));
							}
							if (deviation.getFunctionCategory() != null) {
								crit.add(Restrictions.eq("functionCategory",
										deviation.getFunctionCategory()));
							}
							if (deviation.getDeviationStatus() != null) {
								crit.add(Restrictions.eq("deviationStatus",
										deviation.getDeviationStatus()));
							}
							if (deviation.getDateClosed() != null) {
								crit.add(Restrictions.eq("dateClosed",
										deviation.getDateClosed()));
							}
							if (deviation.getChecked() != null) {
								crit.add(Restrictions.eq("checked", deviation
										.getChecked()));
							}

							if (deviation.getDateFrom() != null
									&& deviation.getDateTo() == null) {
								crit.add(Restrictions.gt("registrationDate",
										Util.getShortDate(deviation
												.getDateFrom())));
							}
							if (deviation.getDateFrom() == null
									&& deviation.getDateTo() != null) {
								crit.add(Restrictions.lt("registrationDate",
										Util.getShortDateLast(deviation
												.getDateTo())));
							}
							if (deviation.getDateFrom() != null
									&& deviation.getDateTo() != null) {
								crit.add(Restrictions.between(
										"registrationDate", Util
												.getShortDate(deviation
														.getDateFrom()), Util
												.getShortDateLast(deviation
														.getDateTo())));
							}

							return crit.list();
						}

					});
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.dao.DeviationDAO#findByManager(no.ugland.utransprod.model.ApplicationUser)
	 */
	@SuppressWarnings("unchecked")
	public List<Deviation> findByManager(final ApplicationUser applicationUser) {
		return (List<Deviation>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(Deviation.class)
								.createCriteria("deviationFunction").add(
										Restrictions.eq("manager",
												applicationUser)).list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.DeviationDAO#findByOrder(no.ugland.utransprod.model.Order)
	 */
	@SuppressWarnings("unchecked")
	public List<Deviation> findByOrder(final Order order) {
		return (List<Deviation>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(Deviation.class).add(
								Restrictions.eq("orderNr", order.getOrderNr()))
								.list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.DeviationDAO#findAllAssembly()
	 */
	@SuppressWarnings("unchecked")
	public List<Deviation> findAllAssembly() {
		return (List<Deviation>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						StringBuffer sqlBuffer = new StringBuffer(
								"select deviation from Deviation deviation where ")
								.append("deviation.doAssembly = 1 and ")
								.append(
										"not exists(select 1 from Assembly assembly where ")
								.append("assembly.deviation = deviation)");
						return session.createQuery(sqlBuffer.toString()).list();
					}

				});
	}

	}
