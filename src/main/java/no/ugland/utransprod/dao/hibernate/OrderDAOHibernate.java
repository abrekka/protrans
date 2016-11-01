package no.ugland.utransprod.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.google.common.collect.Lists;

import no.ugland.utransprod.dao.OrderDAO;
import no.ugland.utransprod.dao.OrderLineDAO;
import no.ugland.utransprod.dao.TransportCostDAO;
import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.gui.model.Delelisteinfo;
import no.ugland.utransprod.gui.model.Ordreinfo;
import no.ugland.utransprod.gui.model.Ordrelinjeinfo;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

/**
 * Implementasjon av DAO for tabell ORDER for hibernate
 * 
 * @author atle.brekka
 */
public class OrderDAOHibernate extends BaseDAOHibernate<Order> implements OrderDAO {
	OrderLineDAO orderLineDAO;

	TransportCostDAO transportCostDAO;

	public final void setOrderLineDAO(final OrderLineDAO aOrderLineDAO) {
		this.orderLineDAO = aOrderLineDAO;
	}

	public final void setTransportCostDAO(final TransportCostDAO aTransportCostDAO) {
		this.transportCostDAO = aTransportCostDAO;
	}

	public OrderDAOHibernate() {
		super(Order.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#removeAll()
	 */
	@Override
	public final void removeAll() {
		getHibernateTemplate().bulkUpdate("delete from Order");

	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#getAllNewOrders()
	 */
	@SuppressWarnings("unchecked")
	public final List<Order> getAllNewOrders() {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Criteria crit = session.createCriteria(Order.class).add(Restrictions.isNull("transport"));
				crit.setFetchMode("assembly", FetchMode.JOIN);
				crit.setFetchMode("assembly.assemblyTeam", FetchMode.JOIN);
				return crit.list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findByOrder(no.ugland.utransprod.model.Order)
	 */
	@SuppressWarnings("unchecked")
	public final List<Order> findByOrder(final Order order) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Example example = Example.create(order);
				example.enableLike(MatchMode.ANYWHERE);
				example.ignoreCase();
				example.excludeZeroes();

				Criteria crit = session.createCriteria(Order.class).add(example);

				if (order.getAssembly() != null && order.doAssembly()) {
					Assembly assembly = order.getAssembly();
					crit = crit.createCriteria("assembly")
							.add(Restrictions.eq("assemblyYear", assembly.getAssemblyYear()));

					if (assembly.getAssemblyWeek() != null) {
						crit = crit.add(Restrictions.eq("assemblyWeek", assembly.getAssemblyWeek()));
					}
					if (assembly.getSupplier() != null) {
						crit = crit.add(Restrictions.eq("supplier", assembly.getSupplier()));
					}
				}

				if (order.getCustomer() != null) {
					Customer customer = order.getCustomer();
					crit = crit.createCriteria("customer");
					if (customer.getCustomerNr() != null) {
						crit.add(Restrictions.eq("customerNr", customer.getCustomerNr()));
					}
					if (customer.getFirstName() != null) {
						crit.add(Restrictions.ilike("firstName", customer.getFirstName()));
					}
					if (customer.getLastName() != null) {
						crit.add(Restrictions.ilike("lastName", customer.getLastName()));
					}
				}
				if (order.getConstructionType() != null) {
					crit.add(Restrictions.eq("constructionType", order.getConstructionType()));
				}
				if (order.getTransport() != null) {
					crit.add(Restrictions.eq("transport", order.getTransport()));
				}
				return crit.list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#refreshObject(no.ugland.utransprod.model.Order)
	 */
	public final void refreshObject(final Order order) {
		if (order.getOrderId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(final Session session) {
					session.flush();
					session.load(order, order.getOrderId());
					Set<?> set = order.getOrderLines();
					set.iterator();
					set = order.getCollies();
					set.iterator();
					set = order.getOrderComments();
					set.iterator();
					return null;
				}

			});
		}
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#lazyLoad(no.ugland.utransprod.model.Order,
	 *      no.ugland.utransprod.service.enums.LazyLoadOrderEnum[])
	 */
	public final void lazyLoad(final Order order, final LazyLoadOrderEnum[] lazyEnums) {
		if (order != null && order.getOrderId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				@SuppressWarnings("incomplete-switch")
				public Object doInHibernate(final Session session) {
					if (!session.contains(order)) {
						session.load(order, order.getOrderId());
					}
					Set<?> set;
					List<LazyLoadOrderEnum> enumList = Arrays.asList(lazyEnums);

					for (LazyLoadOrderEnum lazyEnum : lazyEnums) {
						switch (lazyEnum) {
						case ORDER_LINES:

							Set<OrderLine> orderLines = order.getOrderLines();
							if (!Hibernate.isInitialized(orderLines)) {
								orderLines.size();
							}
							if (enumList.contains(LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES)) {
								for (OrderLine orderLine : orderLines) {

									orderLineDAO.lazyLoad(orderLine,
											new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINES,
													LazyLoadOrderLineEnum.ORDER_LINES_ORDER_LINES,
													LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });

								}
							} else if (enumList.contains(LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES)) {
								for (OrderLine orderLine : orderLines) {

									orderLineDAO.lazyLoad(orderLine,
											new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });

								}

							}
							break;
						case ORDER_COSTS:
							set = order.getOrderCosts();
							set.iterator();
							break;
						case POST_SHIPMENTS:
							set = order.getPostShipments();
							set.iterator();
							break;
						case COLLIES:
							Set<Colli> collies = order.getCollies();
							if (!Hibernate.isInitialized(collies)) {
								collies.size();
							}
							if (collies != null) {
								for (Colli colli : collies) {
									set = colli.getOrderLines();
									if (!Hibernate.isInitialized(set)) {
										set.size();
									}
								}
							}

							break;
						case EXTERNAL_ORDER:
							set = order.getExternalOrders();
							set.iterator();
							break;
						case COMMENTS:
							Set<OrderComment> comments = order.getOrderComments();
							if (comments != null) {
								for (OrderComment comment : comments) {
									set = comment.getOrderCommentCommentTypes();
									set.iterator();
								}
							}
							break;
						case DEVIATIONS:
							set = order.getDeviations();
							set.iterator();
							break;
						case PROCENT_DONE:
							set = order.getProcentDones();
							set.iterator();
							break;
						default:
							break;
						}
					}
					return null;
				}

			});
		}

	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findAllAssemblyOrders()
	 */
	@SuppressWarnings("unchecked")
	public final List<Order> findAllAssemblyOrders() {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer sqlBuffer = new StringBuffer("select customerOrder from Order customerOrder where ")
						.append("customerOrder.doAssembly = 1 and ")
						.append("not exists(select 1 from Assembly assembly where ")
						.append("assembly.order = customerOrder and ((inactive is null or "
								+ "inactive = 0)) and assembly.supplier is not null)");
				return session.createQuery(sqlBuffer.toString()).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#lazyLoadTree(no.ugland.utransprod.model.Order)
	 */
	public final void lazyLoadTree(final Order order) {
		if (order != null && order.getOrderId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(final Session session) {

					session.load(order, order.getOrderId());

					Set<?> set;
					set = order.getCollies();
					set.iterator();

					set = order.getOrderCosts();
					set.iterator();

					Set<OrderComment> comments = order.getOrderComments();
					if (comments != null) {
						for (OrderComment comment : comments) {
							set = comment.getOrderCommentCommentTypes();
							set.iterator();
						}
					}

					Set<OrderLine> orderLines = order.getOrderLines();
					lazyLoadTreeOrderLines(orderLines);

					return null;
				}
			});
		}

	}

	/**
	 * Lazy laster ordrelinjer
	 * 
	 * @param orderLines
	 */
	final void lazyLoadTreeOrderLines(final Set<OrderLine> orderLines) {
		if (orderLines == null) {
			return;
		}
		Set<?> set;
		for (OrderLine orderLine : orderLines) {
			set = orderLine.getOrderLineAttributes();
			set.iterator();
			lazyLoadTreeOrderLines(orderLine.getOrderLines());
		}
	}

	public final List<Object[]> getOrdersPostShipmentsAndCosts(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {
		List<Object[]> list = getOrdersAndCosts(fromString, toString, transportConstraintEnum, productArea);
		List<Object[]> listPostShipment = getPostShipmentsAndCosts(fromString, toString, transportConstraintEnum,
				productArea);
		list.addAll(listPostShipment);
		return list;
	}

	/**
	 * Henter ut etterleveringer og kostnader
	 * 
	 * @param fromString
	 * @param toString
	 * @param transportConstraintEnum
	 * @param productArea
	 * @return etterleveringer og kostnader
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getPostShipmentsAndCosts(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer queryString = new StringBuffer("select customerOrder.order_Id," + "customerOrder.order_Nr,"
						+ "customer.customer_Nr," + "customer.first_Name+' '+customer.last_Name,"
						+ "customerOrder.delivery_Address," + "customerOrder.postal_Code," + "transport.transport_Name,"
						+ "transport.transport_Year," + "transport.transport_Week," + "costType.cost_Type_Name,"
						+ "       orderCost.cost_Amount," + "costUnit.cost_Unit_Name,"
						+ "       postShipment.post_Shipment_Id " + "FROM   Post_shipment AS postShipment INNER JOIN "
						+ " Customer_order AS customerOrder ON postShipment.order_id = "
						+ "customerOrder.Order_id INNER JOIN " + " Customer AS customer ON customerOrder.Customer_id = "
						+ "customer.Customer_id INNER JOIN " + " Transport AS transport ON postShipment.transport_id = "
						+ "transport.Transport_id LEFT OUTER JOIN " + " Cost_unit AS costUnit INNER JOIN "
						+ " Order_cost AS orderCost INNER JOIN "
						+ " Deviation AS deviation ON orderCost.deviation_id = " + "deviation.deviation_id INNER JOIN "
						+ " Cost_type AS costType ON orderCost.Cost_type_id = " + "costType.Cost_type_id ON "
						+ " costUnit.Cost_unit_id = orderCost.Cost_unit_id ON "
						+ " postShipment.deviation_id = deviation.deviation_id "
						+ "WHERE customerOrder.product_area_id=:productAreaId and "
						+ " (CAST(transport.Transport_Year AS varchar(255)) + "
						+ "CAST(transport.Transport_Week + 10 AS varchar(255)) "
						+ " BETWEEN :fromString AND :toString)");

				switch (transportConstraintEnum) {
				case TRANSPORTED:
					queryString.append(" and postShipment.sent is not null");
					break;
				case TRANSPORT_PLANNED:
					queryString.append(" and postShipment.sent is null");
					break;
				case ALL:
					break;
				default:
					break;
				}

				queryString.append(" order by customerOrder.order_Id,postShipment.post_Shipment_Id");

				return session.createSQLQuery(queryString.toString()).setParameter("fromString", fromString)
						.setParameter("toString", toString)
						.setParameter("productAreaId", productArea.getProductAreaId()).list();

			}
		});

	}

	/**
	 * Henter ut ordre og kostnader for en gitt periode
	 * 
	 * @param fromString
	 * @param toString
	 * @param transportConstraintEnum
	 * @param productArea
	 * @return order og kostnader
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getOrdersAndCosts(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer queryString = new StringBuffer("select customerOrder.orderId," + "customerOrder.orderNr,"
						+ "customer.customerNr," + "customer.firstName+' '+customer.lastName,"
						+ "customerOrder.deliveryAddress," + "customerOrder.postalCode," + "transport.transportName,"
						+ "transport.transportYear," + "transport.transportWeek,customerOrder.productionWeek,"
						+ "costType.costTypeName," + "orderCost.costAmount," + "costUnit.costUnitName,"
						+ "       -1 as postShipmentId " + "from Customer customer," + " OrderCost orderCost,"
						+ " Order customerOrder," + " Transport transport," + " CostType costType, "
						+ " CostUnit costUnit " + "where customer = customerOrder.customer and "
						+ " orderCost.order = customerOrder and " + " orderCost.costType = costType and "
						+ " orderCost.costUnit = costUnit and " + " customerOrder.productArea=:productArea and "
						+ " not exists(select 1 from Deviation deviation where deviation="
						+ "orderCost.deviation and deviation.postShipment is not null) and"
						+ " customerOrder.transport = transport and " + " cast(transport.transportYear as string)||"
						+ "cast(transport.transportWeek+10 as string) " + " between  :fromString and :toString");

				switch (transportConstraintEnum) {
				case TRANSPORTED:
					queryString.append(" and customerOrder.sent is not null");
					break;
				case TRANSPORT_PLANNED:
					queryString.append(" and customerOrder.sent is null");
					break;
				case ALL:
					break;
				default:
					break;
				}

				queryString.append(" order by customerOrder.orderId");

				return session.createQuery(queryString.toString()).setParameter("fromString", fromString)
						.setParameter("toString", toString).setParameter("productArea", productArea).list();

			}
		});

	}

	/**
	 * Teller etterleveringer og summerer kostnader for etterleveringer i en
	 * gitt periode
	 * 
	 * @param fromString
	 * @param toString
	 * @param transportConstraintEnum
	 * @param productArea
	 * @return etterleveringer og summerte kostnader
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countPostShipmentAndCosts(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer queryString = new StringBuffer("select count(postShipment.postShipmentId),"
						+ "       transport.transportYear," + "       transport.transportWeek,"
						+ "       costType.costTypeName," + "       sum(orderCost.costAmount),"
						+ "       costUnit.costUnitName " + "from OrderCost orderCost,"
						+ "     PostShipment postShipment," + "     Transport transport," + "     CostType costType, "
						+ "     CostUnit costUnit, " + "     Deviation deviation," + "     Order customerOrder "
						+ "where postShipment.order=customerOrder and "
						+ "      customerOrder.productArea=:productArea and "
						+ "      postShipment.deviation=deviation and " + "      orderCost.deviation = deviation and "
						+ "      orderCost.costType = costType and " + "      orderCost.costUnit = costUnit and "
						+ "      postShipment.transport = transport and "
						+ "     cast(transport.transportYear as string)"
						+ "     ||cast(transport.transportWeek+10 as string) between  "
						+ "     :fromString and :toString");

				switch (transportConstraintEnum) {
				case TRANSPORTED:
					queryString.append(" and postShipment.sent is not null");
					break;
				case TRANSPORT_PLANNED:
					queryString.append(" and postShipment.sent is null");
					break;
				case ALL:
					break;
				default:
					break;
				}

				queryString.append(" group by transport.transportYear,").append("transport.transportWeek,")
						.append("costType.costTypeName,costUnit.costUnitName");
				queryString.append(" order by transport.transportYear,transport.transportWeek");

				return session.createQuery(queryString.toString()).setParameter("fromString", fromString)
						.setParameter("toString", toString).setParameter("productArea", productArea).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#countOrderPostShipmentAndCosts(java.lang.String,
	 *      java.lang.String,
	 *      no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum,
	 *      no.ugland.utransprod.model.ProductArea)
	 */
	public final List<Object[]> countOrderPostShipmentAndCosts(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {
		List<Object[]> list = countOrderAndCosts(fromString, toString, transportConstraintEnum, productArea);
		list.addAll(countPostShipmentAndCosts(fromString, toString, transportConstraintEnum, productArea));
		return list;

	}

	/**
	 * Teller ordre og summerer kostander for en gitt periode
	 * 
	 * @param fromString
	 * @param toString
	 * @param transportConstraintEnum
	 * @param productArea
	 * @return antall ordre og summerte kostnader
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countOrderAndCosts(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer queryString = new StringBuffer("select count(customerOrder.orderId),"
						+ "       transport.transportYear," + "       transport.transportWeek,"
						+ "       costType.costTypeName," + "       sum(orderCost.costAmount),"
						+ "       costUnit.costUnitName " + "from OrderCost orderCost," + "     Order customerOrder,"
						+ "     Transport transport," + "     CostType costType, " + "     CostUnit costUnit "
						+ "where customerOrder.productArea=:productArea and "
						+ "      orderCost.order = customerOrder and " + "      orderCost.costType = costType and "
						+ "      orderCost.costUnit = costUnit and "
						+ "      not exists(select 1 from Deviation deviation where deviation"
						+ "      =orderCost.deviation and deviation.postShipment is not null)"
						+ " and customerOrder.transport = transport and "
						+ "    cast(transport.transportYear as string)"
						+ "    ||cast(transport.transportWeek+10 as string) between  "
						+ "    :fromString and :toString");

				switch (transportConstraintEnum) {
				case TRANSPORTED:
					queryString.append(" and customerOrder.sent is not null");
					break;
				case TRANSPORT_PLANNED:
					queryString.append(" and customerOrder.sent is null");
					break;
				case ALL:
					break;
				default:
					break;
				}

				queryString.append(" group by transport.transportYear,transport.transportWeek,")
						.append("costType.costTypeName,costUnit.costUnitName");
				queryString.append(" order by transport.transportYear,transport.transportWeek");

				return session.createQuery(queryString.toString()).setParameter("fromString", fromString)
						.setParameter("toString", toString).setParameter("productArea", productArea).list();
			}

		});
	}

	/**
	 * Teller antall etterleveringer i en gitt periode
	 * 
	 * @param fromString
	 * @param toString
	 * @param transportConstraintEnum
	 * @param productArea
	 * @return antall
	 */
	@SuppressWarnings("unchecked")
	public final Map<String, Integer> getCountPostShipment(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {
		return (Map<String, Integer>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer queryString = new StringBuffer(
						"select count(*),transport.transportYear,transport.transportWeek" + " from "
								+ "PostShipment postShipment,Transport transport,Order customerOrder" + " where ")
										.append(" postShipment.transport = transport")
										.append(" and postShipment.order=customerOrder")
										.append(" and customerOrder.productArea=:productArea")
										.append(" and cast(transport.transportYear as string)")
										.append("||cast(transport.transportWeek+10 as string) between ")
										.append(" :fromString and :toString");

				switch (transportConstraintEnum) {
				case TRANSPORTED:
					queryString.append(" and postShipment.sent is not null");
					break;
				case TRANSPORT_PLANNED:
					queryString.append(" and postShipment.sent is null");
					break;
				case ALL:
					break;
				default:
					break;
				}

				queryString.append(" group by transport.transportYear,transport.transportWeek");
				queryString.append(" order by transport.transportYear,transport.transportWeek");

				List<Object[]> counts = session.createQuery(queryString.toString())
						.setParameter("fromString", fromString).setParameter("toString", toString)
						.setParameter("productArea", productArea).list();

				Map<String, Integer> yearMap = new Hashtable<String, Integer>();
				if (counts != null) {
					for (Object[] year : counts) {
						yearMap.put(String.format("%1$d%2$02d", year[1], year[2]), (Integer) year[0]);
					}
				}
				return yearMap;
			}

		});

	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#getCountOrderAndPostShipment(java.lang.String,
	 *      java.lang.String,
	 *      no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum,
	 *      no.ugland.utransprod.model.ProductArea)
	 */
	public final Map<String, Integer> getCountOrderAndPostShipment(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {

		Map<String, Integer> countMapOrder = getCountOrder(fromString, toString, transportConstraintEnum, productArea);
		Map<String, Integer> countMapPostShipment = getCountPostShipment(fromString, toString, transportConstraintEnum,
				productArea);

		if (countMapPostShipment != null) {
			Set<String> keys = countMapPostShipment.keySet();
			for (String key : keys) {
				Integer count = countMapOrder.get(key);
				if (count != null) {
					count = count + countMapPostShipment.get(key);
				} else {
					count = countMapPostShipment.get(key);
				}
				countMapOrder.put(key, count);
			}
		}
		return countMapOrder;
	}

	/**
	 * Teller antall ordre for en gitt periode
	 * 
	 * @param fromString
	 * @param toString
	 * @param transportConstraintEnum
	 * @param productArea
	 * @return antall
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Integer> getCountOrder(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {
		return (Map<String, Integer>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer queryString = new StringBuffer(
						"select count(*),transport.transportYear,transport.transportWeek" + " from "
								+ "Order customerOrder,Transport transport" + " where ")
										.append(" customerOrder.transport = transport")
										.append(" and customerOrder.productArea=:productArea")
										.append(" and cast(transport.transportYear as string)||")
										.append("cast(transport.transportWeek+10 as string) between ")
										.append(" :fromString and :toString");

				switch (transportConstraintEnum) {
				case TRANSPORTED:
					queryString.append(" and customerOrder.sent is not null");
					break;
				case TRANSPORT_PLANNED:
					queryString.append(" and customerOrder.sent is null");
					break;
				case ALL:
					break;
				default:
					break;
				}

				queryString.append(" group by transport.transportYear,transport.transportWeek");
				queryString.append(" order by transport.transportYear,transport.transportWeek");

				List<Object[]> counts = session.createQuery(queryString.toString())
						.setParameter("fromString", fromString).setParameter("toString", toString)
						.setParameter("productArea", productArea).list();

				Map<String, Integer> yearMap = new Hashtable<String, Integer>();
				if (counts != null) {
					for (Object[] year : counts) {
						yearMap.put(String.format("%1$d%2$02d", year[1], year[2]), (Integer) year[0]);
					}
				}
				return yearMap;
			}

		});

	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findNotSent()
	 */
	@SuppressWarnings("unchecked")
	public final Set<Order> findNotSent() {
		return (Set<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				StringBuffer sqlBuffer = new StringBuffer("select distinct customerOrder from ")
						.append("Order customerOrder left outer join customerOrder.transport ").append(" where ")
						.append("customerOrder.sent is null").append(" and(customerOrder.transport is null or")
						.append(" customerOrder.transport.transportName <> 'Historisk')");

				return new HashSet<Order>(session.createQuery(sqlBuffer.toString()).list());
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#lazyLoadOrderLineAndCollies(java.lang.Integer)
	 */
	public final Order lazyLoadOrderLineAndCollies(final Integer orderId) {
		return (Order) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer sqlBuffer = new StringBuffer("select customerOrder from Order customerOrder")
						.append(" left outer join fetch customerOrder.orderLines")
						.append(" left outer join fetch customerOrder.collies")
						.append(" left outer join fetch customerOrder.orderComments")
						.append(" where customerOrder.orderId = :orderId");
				return session.createQuery(sqlBuffer.toString()).setParameter("orderId", orderId).uniqueResult();

			}

		});

	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#getAllDeviationOrderLines()
	 */
	@SuppressWarnings("unchecked")
	public final List<OrderLine> getAllDeviationOrderLines() {
		return (List<OrderLine>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer sqlBuffer = new StringBuffer(
						"select orderLine from Order customerOrder,OrderLine orderLine,"
								+ "ConstructionTypeArticle constructionTypeArticle," + "ArticleType articleType ");

				sqlBuffer.append("where orderLine.constructionTypeArticle = constructionTypeArticle ")
						.append("and constructionTypeArticle.articleType = articleType ")
						.append("and articleType.topLevel=1 ").append("and orderLine.colli is null ")

						.append("and customerOrder = orderLine.order ")
						.append("and customerOrder.transport is not null ")
						.append("and customerOrder.sent is not null ").append("order by customerOrder.customer");

				return session.createQuery(sqlBuffer.toString()).list();

			}

		});

	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findReadyForInvoice(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public final List<Order> findReadyForInvoice(final String orderNr) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer sqlBuffer = new StringBuffer("select customerOrder from Order customerOrder");

				sqlBuffer.append(" where (customerOrder.sent is not null");
				if (orderNr != null) {
					sqlBuffer.append(" and customerOrder.orderNr='").append(orderNr).append("' ");
				}
				sqlBuffer.append(" and exists(select 1 from Assembly assembly where assembly.order=")
						.append("customerOrder and assembly.assemblied='Ja'))")
						.append(" or(customerOrder.sent is not null ");
				if (orderNr != null) {
					sqlBuffer.append(" and customerOrder.orderNr='").append(orderNr).append("' ");
				}
				sqlBuffer.append(" and (customerOrder.doAssembly is null or ").append("customerOrder.doAssembly=0))");

				sqlBuffer.append(" order by customerOrder.sent");

				return session.createQuery(sqlBuffer.toString()).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findReadyForInvoiceCustomerNr(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public final List<Order> findReadyForInvoiceCustomerNr(final Integer customerNr) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				StringBuffer sqlBuffer = new StringBuffer(
						"select customerOrder from Order customerOrder,Customer customer");

				sqlBuffer.append(" where (customerOrder.sent is not null")
						.append(" and customerOrder.customer=customer and ").append("customer.customerNr=")
						.append(customerNr).append(" ")
						.append(" and exists(select 1 from Assembly assembly where assembly.order=")
						.append("customerOrder and assembly.assemblied='Ja'))")
						.append(" or(customerOrder.sent is not null ")
						.append(" and customerOrder.customer=customer and ").append("customer.customerNr=")
						.append(customerNr).append(" ").append(" and (customerOrder.doAssembly is null or ")
						.append("customerOrder.doAssembly=0))");

				sqlBuffer.append(" order by customerOrder.sent");

				return session.createQuery(sqlBuffer.toString()).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findByOrderNr(java.lang.String)
	 */
	public final Order findByOrderNr(final String orderNr) {
		return (Order) getHibernateTemplate().execute(new HibernateCallback() {

			@SuppressWarnings("unchecked")
			public Object doInHibernate(final Session session) {
				List<Order> list = session.createCriteria(Order.class).add(Restrictions.eq("orderNr", orderNr)).list();
				if (list != null && list.size() == 1) {
					return list.get(0);
				}
				return null;
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findAllNotSent(java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public final List<Order> findAllNotSent(final String criteria, final String orderBy) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Criteria crit = session.createCriteria(Order.class).add(Restrictions.isNull("sent"));

				if (criteria != null) {
					crit = crit.createCriteria(criteria);
				}
				if (orderBy != null) {
					crit.addOrder(org.hibernate.criterion.Order.asc(orderBy));
				}
				return crit.list();

			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findNotSentByCustomerNr(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public final List<Order> findNotSentByCustomerNr(final Integer customerNr) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				return session.createCriteria(Order.class).add(Restrictions.isNull("sent")).createCriteria("customer")
						.add(Restrictions.eq("customerNr", customerNr)).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#findNotSentByOrderNr(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public final List<Order> findNotSentByOrderNr(final String orderNr) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				return session.createCriteria(Order.class).add(Restrictions.isNull("sent"))
						.add(Restrictions.eq("orderNr", orderNr)).list();
			}

		});
	}

	@SuppressWarnings("unchecked")
	public final List<Order> findSentInPeriod(final Periode periode, final String productAreaGroupName) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Date fromDate = Util.getFirstDateInWeek(periode.getYear(), periode.getWeek());
				Date toDate = Util.getLastDateInWeek(periode.getYear(), periode.getToWeek());
				return session.createCriteria(Order.class).add(Restrictions.isNotNull("transport"))
						.add(Restrictions.between("sent", fromDate, toDate)).createCriteria("productArea")
						.createCriteria("productAreaGroup")
						.add(Restrictions.ilike("productAreaGroupName", productAreaGroupName)).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#getPacklistCountForWeek(java.util.Date,
	 *      java.util.Date)
	 */
	public final Integer getPacklistCountForWeek(final Date fromDate, final Date toDate) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			@SuppressWarnings("unchecked")
			public Object doInHibernate(final Session session) {
				String query = "select count(customerOrder.orderId) from Order customerOrder where "
						+ "customerOrder.packlistReady between :fromDate and :toDate";
				List<Integer> list = session.createQuery(query).setParameter("fromDate", fromDate)
						.setParameter("toDate", toDate).list();
				if (list != null && list.size() == 1) {
					return list.get(0);
				}
				return null;
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#saveOrder(no.ugland.utransprod.model.Order)
	 */
	public final String saveOrder(final Order order) {
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				if (Hibernate.isInitialized(order.getOrderLines())) {
					if (order.getOrderLines() == null || order.getOrderLines().size() == 0) {

						return "Order " + order + " mangler artikler!";
					}
				}

				session.saveOrUpdate(order);
				return null;
			}

		});

	}

	/**
	 * @see no.ugland.utransprod.dao.OrderDAO#getPacklistCountForWeekByProductAreaGroupName(java.util.Date,
	 *      java.util.Date, no.ugland.utransprod.model.ProductAreaGroup)
	 */
	public final Integer getPacklistCountForWeekByProductAreaGroupName(final Date fromDate, final Date toDate) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			@SuppressWarnings("unchecked")
			public Object doInHibernate(final Session session) {
				String query = "select count(customerOrder.orderId) " + " from Order customerOrder "
						+ " where customerOrder.packlistReady between :fromDate and :toDate "
						// +
						// "and
						// customerOrder.productArea.productAreaGroup.productAreaGroupName="
						// + ":groupName"
				;
				List<Integer> list = session.createQuery(query).setParameter("fromDate", fromDate)
						.setParameter("toDate", toDate)
						// .setParameter("groupName",
						// group.getProductAreaGroupName())
						.list();
				if (list != null && list.size() == 1) {
					Object object = list.get(0);
					if (object instanceof Long) {
						return ((Long) object).intValue();
					} else {
						return object;
					}
				}
				return null;
			}

		});
	}

	@SuppressWarnings("unchecked")
	public final List<Order> findAllNotSentByProductArea(final String criteria, final String orderBy,
			final ProductArea productArea) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Criteria crit = session.createCriteria(Order.class).add(Restrictions.isNull("sent"))
						.add(Restrictions.eq("productArea", productArea));

				if (criteria != null) {
					crit = crit.createCriteria(criteria);
				}
				if (orderBy != null) {
					crit.addOrder(org.hibernate.criterion.Order.asc(orderBy));
				}
				return crit.list();

			}

		});
	}

	@SuppressWarnings("unchecked")
	public final List<Order> findSentInPeriodByProductArea(final Integer year, final Integer weekFrom,
			final Integer weekTo, final ProductArea productArea) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Date fromDate = Util.getFirstDateInWeek(year, weekFrom);
				Date toDate = Util.getLastDateInWeek(year, weekTo);
				return session.createCriteria(Order.class).add(Restrictions.between("sent", fromDate, toDate))
						.add(Restrictions.eq("productArea", productArea))
						.addOrder(org.hibernate.criterion.Order.asc("sent")).list();
			}

		});
	}

	@SuppressWarnings("unchecked")
	public final List<Order> findByConfirmWeekProductArea(final Integer year, final Integer weekFrom,
			final Integer weekTo, final String productAreaName) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Criteria criteria = session.createCriteria(Order.class)
						.add(Expression.sql("datepart(year,agreement_date)=?", year, Hibernate.INTEGER))
						.add(Expression.sql("dbo.GetISOWeekNumberFromDate(agreement_date) between ? and ?",
								new Object[] { weekFrom, weekTo }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER }))
						.addOrder(org.hibernate.criterion.Order.asc("salesman")).createCriteria("constructionType")
						.add(Restrictions.not(Restrictions.ilike("name", "%tilleggsordre%")));
				if (productAreaName != null) {
					criteria.createCriteria("productArea").add(Restrictions.eq("productArea", productAreaName));
				}
				return criteria.list();
			}

		});
	}

	public final List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(final ProductArea productArea,
			final Periode periode) {

		List<Order> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(),
				periode.getToWeek(), productArea == null ? null : productArea.getProductArea());

		Map<String, List<Order>> orderByCountyMap = arrangeOrdersByCounty(confirmedOrders);

		List<SaleReportSum> saleReportSums = aggregateCountySaleReportSums(orderByCountyMap);

		return saleReportSums;
	}

	public final List<SaleReportSum> sumByProductAreaConfirmPeriode(final ProductArea productArea,
			final Periode periode) {

		List<Order> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(),
				periode.getToWeek(), productArea.getProductArea());

		SaleReportSum saleReportSum = aggregateSaleReportSumForCountyOrders(confirmedOrders, "Alle");

		return Lists.newArrayList(saleReportSum);
	}

	public final List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(final ProductArea productArea,
			final Periode periode) {

		List<Order> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(),
				periode.getToWeek(), productArea == null ? null : productArea.getProductArea());

		Map<String, List<Order>> orderByCountyMap = arrangeOrdersBySalesman(confirmedOrders);

		List<SaleReportSum> saleReportSums = aggregateSalesmanSaleReportSums(orderByCountyMap);

		return saleReportSums;
	}

	private List<SaleReportSum> aggregateCountySaleReportSums(final Map<String, List<Order>> countyOrders) {
		List<SaleReportSum> saleReportSums = new ArrayList<SaleReportSum>();
		if (countyOrders != null) {
			Set<String> countyNames = countyOrders.keySet();
			for (String countyName : countyNames) {
				List<Order> orders = countyOrders.get(countyName);
				saleReportSums.add(aggregateSaleReportSumForCountyOrders(orders, countyName));
			}
		}
		return saleReportSums;
	}

	private List<SaleReportSum> aggregateSalesmanSaleReportSums(final Map<String, List<Order>> salesmanOrders) {
		List<SaleReportSum> saleReportSums = new ArrayList<SaleReportSum>();
		if (salesmanOrders != null) {
			Set<String> salesmanNames = salesmanOrders.keySet();
			for (String name : salesmanNames) {
				List<Order> orders = salesmanOrders.get(name);
				saleReportSums.add(aggregateSaleReportSumForSalesmanOrders(orders, name));
			}
		}
		return saleReportSums;
	}

	private SaleReportSum aggregateSaleReportSumForCountyOrders(final List<Order> orders, final String countyName) {
		SaleReportSum saleReportSum = new SaleReportSum();
		saleReportSum.setCountyName(countyName);

		for (Order order : orders) {
			lazyLoad(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });
			saleReportSum.increaseCount();

			saleReportSum.addSumAssembly(order.getAssemblyCost());
			saleReportSum.addSumOwnProduction(order.getGarageValue());
			saleReportSum.addSumTransport(order.getTransportValue());
			saleReportSum.addSumDb(order.getContributionMargin());
			saleReportSum.addSumYesLines(order.getJaLinjer());

		}

		return saleReportSum;
	}

	private SaleReportSum aggregateSaleReportSumForSalesmanOrders(final List<Order> orders, final String salesmanName) {
		SaleReportSum saleReportSum = new SaleReportSum();
		saleReportSum.setSalesman(salesmanName);

		for (Order order : orders) {
			lazyLoad(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });
			saleReportSum.increaseCount();

			saleReportSum.addSumAssembly(order.getAssemblyCost());
			saleReportSum.addSumOwnProduction(order.getGarageValue());
			saleReportSum.addSumTransport(order.getTransportValue());
			saleReportSum.addSumDb(order.getContributionMargin());
			saleReportSum.addSumYesLines(order.getJaLinjer());

		}

		return saleReportSum;
	}

	private Map<String, List<Order>> arrangeOrdersByCounty(final List<Order> orders) {
		Map<String, List<Order>> countyOrders = new Hashtable<String, List<Order>>();

		if (orders != null) {
			for (Order order : orders) {
				addOrderToCountyMap(countyOrders, order);
			}
		}
		return countyOrders;
	}

	private Map<String, List<Order>> arrangeOrdersBySalesman(final List<Order> orders) {
		Map<String, List<Order>> salesmanOrders = new Hashtable<String, List<Order>>();

		if (orders != null) {
			for (Order order : orders) {
				addOrderToSalesmanMap(salesmanOrders, order);
			}
		}
		return salesmanOrders;
	}

	private void addOrderToCountyMap(final Map<String, List<Order>> countyOrders, final Order order) {
		String countyName = getCountyName(order);
		List<Order> countyOrderList = countyOrders.get(countyName);
		if (countyOrderList == null) {
			countyOrderList = new ArrayList<Order>();
		}
		countyOrderList.add(order);
		countyOrders.put(countyName, countyOrderList);
	}

	private void addOrderToSalesmanMap(final Map<String, List<Order>> salesmanOrders, final Order order) {
		String salesmanName = order.getSalesman() != null ? order.getSalesman() : "";

		List<Order> salesmanOrderList = salesmanOrders.get(salesmanName);
		if (salesmanOrderList == null) {
			salesmanOrderList = new ArrayList<Order>();
		}
		salesmanOrderList.add(order);
		salesmanOrders.put(salesmanName, salesmanOrderList);
	}

	private String getCountyName(final Order order) {

		String countyName = transportCostDAO.findCountyNameByPostalCode(order.getPostalCode());
		if (countyName == null) {
			countyName = "";
		}
		return countyName;
	}

	public final Integer countByProductAreaPeriode(final ProductArea productArea, final Periode periode) {
		List<Order> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(),
				periode.getToWeek(), productArea == null ? null : productArea.getProductArea());

		if (confirmedOrders != null) {
			return confirmedOrders.size();
		}
		return 0;
	}

	public final List<SaleReportData> getSaleReportByProductAreaPeriode(final ProductArea productArea,
			final Periode periode) {
		List<Order> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(),
				periode.getToWeek(), productArea == null ? null : productArea.getProductArea());

		return createSaleReportList(confirmedOrders);
	}

	private List<SaleReportData> createSaleReportList(final List<Order> orders) {
		List<SaleReportData> saleReportDataList = new ArrayList<SaleReportData>();
		if (orders != null) {
			for (Order order : orders) {
				lazyLoad(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });
				String countyName = transportCostDAO.findCountyNameByPostalCode(order.getPostalCode());
				saleReportDataList.add(new SaleReportData("Avrop", countyName, order.getSalesman(),
						String.valueOf(order.getCustomer().getCustomerNr()), order.getCustomer().getFullName(),
						order.getOrderNr(), order.getGarageValue(), order.getTransportValue(), order.getAssemblyCost(),
						order.getJaLinjer(), order.getContributionMargin(), order.getContributionRate(),
						order.getOrderDate(), Integer.valueOf(order.getProductArea().getProductAreaNr()), null, null));
			}
		}
		return saleReportDataList;
	}

	@SuppressWarnings("unchecked")
	public final List<Order> findByConfirmWeekProductAreaGroup(final Integer year, final Integer weekFrom,
			final Integer weekTo, final ProductAreaGroup productAreaGroup) {
		return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Criteria criteria = session.createCriteria(Order.class)
						.add(Expression.sql("datepart(year,agreement_date)=?", year, Hibernate.INTEGER))
						.add(Expression.sql("dbo.GetISOWeekNumberFromDate(agreement_date) between ? and ?",
								new Object[] { weekFrom, weekTo }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER }))
						.addOrder(org.hibernate.criterion.Order.asc("salesman"));
				if (productAreaGroup != null) {
					criteria.createCriteria("productArea").add(Restrictions.eq("productAreaGroup", productAreaGroup));
				}
				return criteria.list();
			}

		});
	}

	public String saveOrder(Order order, boolean allowEmptyArticles) {
		String errorMsg = null;
		if (allowEmptyArticles) {
			getHibernateTemplate().saveOrUpdate(order);
		} else {
			errorMsg = saveOrder(order);
		}
		return errorMsg;

	}

	public SaleReportSum groupSumByProductAreaConfirmPeriode(final ProductArea productArea, final Periode periode) {
		List<Order> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(),
				periode.getToWeek(), productArea.getProductArea());

		SaleReportSum saleReportSum = aggregateSaleReportSumForCountyOrders(confirmedOrders, "");

		return saleReportSum;

	}

	public Order merge(Order order) {
		return (Order) getHibernateTemplate().merge(order);

	}

	public List<Ordreinfo> finnOrdreinfo(final String orderNr) {
		return (List<Ordreinfo>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				String sql = "SELECT Protrans2.dbo.customer_order.order_nr,F0100.dbo.ordln.LnNo,"
						+ "F0100.dbo.ordln.descr,cast(F0100.dbo.ord.free1 as int) as vegg,cast(F0100.dbo.ord.free2 as int) as mur,"
						+ "F0100.dbo.ordln.prodno,F0100.dbo.prod.inf8,F0100.dbo.ordln.PurcNo,F0100.dbo.prod.PrCatNo,"
						+ " cast(F0100.dbo.ordln.free4 as int) as free4,F0100.dbo.prod.PrCatNo2,F0100.dbo.prod.inf"
						+ " FROM F0100.dbo.ord inner join"
						+ " F0100.dbo.ordln on F0100.dbo.ord.ordno = F0100.dbo.ordln.ordno inner join"
						+ " F0100.dbo.prod on F0100.dbo.ordln.prodno =  F0100.dbo.prod.prodno inner join"
						+ " Protrans2.dbo.customer_order on Protrans2.dbo.customer_order.order_nr=F0100.dbo.ord.inf6"
						+ " where Protrans2.dbo.customer_order.order_nr='" + orderNr
						+ "' and(F0100.dbo.ordln.prodno like 'tak %' or" + " F0100.dbo.ordln.prodno like 'ff%' or"
						+ " F0100.dbo.prod.PrCatNo2 = 32 or" + " F0100.dbo.prod.PrCatNo2 = 4 or"
						+ " F0100.dbo.ordln.prodno='OVERSKRIFT' or" + " F0100.dbo.ordln.descr like 'Vegg:%' or"
						+ " F0100.dbo.ordln.descr like 'Gavl:%' or" + " F0100.dbo.prod.PrCatNo2 in(29,30) or"
						+ " (F0100.dbo.prod.PrCatNo = 509600 and F0100.dbo.ordln.purcno>0))";

				List<Ordreinfo> ordreinfo = Lists.newArrayList();
				List<Object[]> resultater = session.createSQLQuery(sql).list();
				for (Object[] linje : resultater) {
					ordreinfo.add(
							new Ordreinfo((String) linje[0], (Integer) linje[1], (String) linje[2], (Integer) linje[3],
									(Integer) linje[4], (String) linje[5], (String) linje[6], (Integer) linje[7],
									(Integer) linje[8], (Integer) linje[9], (Integer) linje[10], (String) linje[11]));
				}
				return ordreinfo;
			}

		});
	}

	public List<Ordrelinjeinfo> finnOrdrelinjeinfo(final Integer orderId) {
		return (List<Ordrelinjeinfo>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				String sql = "SELECT TrInf3," + "prodTp2," + "article_name," + "descr," + "attribute_info"
						+ " FROM main_package_order_line_v" + " where order_id=" + orderId;

				List<Ordrelinjeinfo> ordrelinjeinfo = Lists.newArrayList();
				List<Object[]> resultater = session.createSQLQuery(sql).list();
				for (Object[] linje : resultater) {
					ordrelinjeinfo.add(new Ordrelinjeinfo((String) linje[0], (Integer) linje[1], (String) linje[2],
							(Integer) linje[3], (String) linje[4], (String) linje[5]));
				}
				return ordrelinjeinfo;
			}

		});
	}

	public void fjernOrdreKomplett(final String orderNr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				String sql = "update Order o set o.orderComplete=:komplett, o.colliesDone=:done where o.orderNr=:orderNr";

				session.createQuery(sql).setString("komplett", null).setInteger("done", 0).setString("orderNr", orderNr)
						.executeUpdate();
				return null;
			}

		});

	}

	public void setOrdreKomplett(final String orderNr, final Date dato) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				String sql = "update Order o set o.orderComplete=:komplett where o.orderNr=:orderNr";

				session.createQuery(sql).setTimestamp("komplett", dato).setString("orderNr", orderNr).executeUpdate();
				return null;
			}

		});

	}

	public void setStatus(final Integer orderId, final String status) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				String sql = "update Order o set o.status=:status where o.orderId=:orderId";

				session.createQuery(sql).setString("status", status).setInteger("orderId", orderId).executeUpdate();
				return null;
			}

		});

	}

	public List<Delelisteinfo> finnDeleliste(final String ordrenr, final String kundenavn, final String sted,
			final String garasjetype) {
		return (List<Delelisteinfo>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				String sql = "SELECT cast(NoInvoAb as int) as antall,F0100.dbo.ordln.prodtp," + "F0100.dbo.txt.txt, "
						+ "F0100.dbo.ordln.prodtp2, " + "F0100.dbo.Unit.descr as enhet"
						+ ", F0100.dbo.ordln.descr, F0100.dbo.ordln.trinf4,F0100.dbo.prod.inf8,F0100.dbo.prod.prodno,F0100.dbo.prod.PrCatNo2 "
						+ "FROM F0100.dbo.OrdLn inner join "
						+ "F0100.dbo.txt on F0100.dbo.txt.txtno=F0100.dbo.ordln.prodtp2 inner join "
						+ "F0100.dbo.ord on F0100.dbo.ordln.ordno=F0100.dbo.ord.ordno inner join "
						+ "F0100.dbo.Unit on F0100.dbo.Unit.un=F0100.dbo.ordln.un inner join "
						+ "F0100.dbo.prod on F0100.dbo.prod.prodno=F0100.dbo.ordln.ProdNo "
						+ "where F0100.dbo.txt.Lang = 47 and F0100.dbo.txt.txttp = 58 and F0100.dbo.ordln.prodtp in(10,20,30,35,40,50) "
						+ " and F0100.dbo.ord.inf6='" + ordrenr + "' "
						+ "order by F0100.dbo.ordln.prodtp2,F0100.dbo.ordln.trinf3,F0100.dbo.ordln.trinf4";

				List<Delelisteinfo> deleliste = Lists.newArrayList();
				List<Object[]> resultater = session.createSQLQuery(sql).list();
				for (Object[] linje : resultater) {
					deleliste.add(new Delelisteinfo(ordrenr, kundenavn, sted, garasjetype, (Integer) linje[0],
							(Integer) linje[1], (String) linje[2], (Integer) linje[3], (String) linje[4],
							(String) linje[5], (String) linje[6], (String) linje[7], (String) linje[8],(Integer) linje[9]));
				}
				return deleliste;
			}

		});
	}

	public void settMontering(final Integer orderId, final Assembly assembly) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				String sql = "update Order o set o.assembly=:assembly where o.orderId=:orderId";

				session.createQuery(sql).setEntity("assembly", assembly).setInteger("orderId", orderId).executeUpdate();
				return null;
			}

		});

		
	}

	public void settMontering(final Integer orderId, final boolean montering) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				String sql = "update Order o set o.doAssembly=:montering where o.orderId=:orderId";

				session.createQuery(sql).setBoolean("montering", montering).setInteger("orderId", orderId).executeUpdate();
				return null;
			}

		});

		
	}

}
