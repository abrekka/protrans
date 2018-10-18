package no.ugland.utransprod.dao.hibernate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.dao.OrderLineDAO;
import no.ugland.utransprod.dao.PostShipmentDAO;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell POST_SHIPMENT
 * 
 * @author atle.brekka
 */
public class PostShipmentDAOHibernate extends BaseDAOHibernate<PostShipment> implements PostShipmentDAO {

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
	 * 
	 */
	public PostShipmentDAOHibernate() {
		super(PostShipment.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#findAllWithoutTransport()
	 */
	@SuppressWarnings("unchecked")
	public List<PostShipment> findAllWithoutTransport() {
		return (List<PostShipment>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				// Query query = session.createQuery("select new
				// PostShipment(postShipment.postShipmentId,postShipment.order,postShipment.postShipmentReady)
				// from PostShipment postShipment where
				// postShipment.transport is null");
				// return query.list();
				return session.createCriteria(PostShipment.class).add(Restrictions.isNull("transport")).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#findAllNotSent()
	 */
	@SuppressWarnings("unchecked")
	public List<PostShipment> findAllNotSent() {
		return (List<PostShipment>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {

				String sql = "select new PostShipment(postShipment.postShipmentId,postShipment.postShipmentComplete,postShipment.order,postShipment.transport,postShipment.deviation,postShipment.sent,postShipment.postShipmentReady,postShipment.status) "
						+ "from PostShipment postShipment " + "left outer join postShipment.transport " +
						// "left join postShipment.order " +
						"left outer join postShipment.deviation " + "where postShipment.sent is null";

				Query query = session.createQuery(sql);

				return query.list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#lazyLoad(no.ugland.utransprod.model.PostShipment,
	 *      no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum[])
	 */
	public void lazyLoad(final PostShipment postShipment, final LazyLoadPostShipmentEnum[] enums) {
		if (postShipment != null && postShipment.getPostShipmentId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				@SuppressWarnings("incomplete-switch")
				public Object doInHibernate(Session session) throws HibernateException {
					session.load(postShipment, postShipment.getPostShipmentId());
					Set<?> set;
					List<LazyLoadPostShipmentEnum> enumList = Arrays.asList(enums);

					for (LazyLoadPostShipmentEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case COLLIES:
							Set<Colli> collies = postShipment.getCollies();
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
						case ORDER_LINES:
							Set<OrderLine> orderLines = postShipment.getOrderLines();
							if (enumList.contains(LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES)) {
								for (OrderLine orderLine : orderLines) {
									orderLineDAO.lazyLoad(orderLine,
											new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINES,
													LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
								}
							} else if (enumList.contains(LazyLoadPostShipmentEnum.ORDER_LINE_ATTRIBUTES)) {
								for (OrderLine orderLine : orderLines) {

									orderLineDAO.lazyLoad(orderLine,
											new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });

								}

							}

							else {
								orderLines.iterator();
							}
							break;
						case POST_SHIPMENT_REFS:
							set = postShipment.getPostShipmentRefs();
							set.iterator();
							break;
						case ORDER_COMMENTS:
							set = postShipment.getOrder().getOrderComments();
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
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#refreshPostShipment(no.ugland.utransprod.model.PostShipment)
	 */
	public void refreshPostShipment(PostShipment postShipment) {
		getHibernateTemplate().load(postShipment, postShipment.getPostShipmentId());
		getHibernateTemplate().flush();

	}

	/**
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<PostShipment> findAll() {
		return getHibernateTemplate().find("from PostShipment");
	}

	/**
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#lazyLoadTree(no.ugland.utransprod.model.PostShipment)
	 */
	public void lazyLoadTree(final PostShipment postShipment) {
		if (postShipment != null && postShipment.getPostShipmentId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {

					if (!session.contains(postShipment)) {
						session.load(postShipment, postShipment.getPostShipmentId());
					}

					Set<?> set;
					set = postShipment.getCollies();
					set.iterator();

					if (postShipment.getDeviation() != null) {
						set = postShipment.getDeviation().getOrderComments();
						set.iterator();
					}

					set = postShipment.getOrder().getOrderComments();
					set.iterator();

					Set<OrderLine> orderLines = postShipment.getOrderLines();
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
	void lazyLoadTreeOrderLines(Set<OrderLine> orderLines) {
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

	/**
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#findByOrderNr(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<PostShipment> findByOrderNr(final String orderNr) {
		return (List<PostShipment>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createCriteria(PostShipment.class).createCriteria("order")
						.add(Restrictions.eq("orderNr", orderNr)).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#findByCustomerNr(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<PostShipment> findByCustomerNr(final Integer customerNr) {
		return (List<PostShipment>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createCriteria(PostShipment.class).createCriteria("order").createCriteria("customer")
						.add(Restrictions.eq("customerNr", customerNr)).list();
			}

		});
	}

	/**
	 * @see no.ugland.utransprod.dao.PostShipmentDAO#load(no.ugland.utransprod.model.PostShipment)
	 */
	public void load(final PostShipment postShipment) {
		if (postShipment != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					session.load(postShipment, postShipment.getPostShipmentId());
					return null;
				}

			});
		}

	}

	@SuppressWarnings("unchecked")
	public final List<PostShipment> findSentInPeriod(final Periode periode, final String productAreaGroupName) {
		return (List<PostShipment>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				Date fromDate = Util.getFirstDateInWeek(periode.getYear(), periode.getWeek());
				Date toDate = Util.getLastDateInWeek(periode.getYear(), periode.getToWeek());
				return session.createCriteria(PostShipment.class).add(Restrictions.isNotNull("transport"))
						.add(Restrictions.between("sent", fromDate, toDate)).createCriteria("order")
						.createCriteria("productArea").createCriteria("productAreaGroup")
						.add(Restrictions.ilike("productAreaGroupName", productAreaGroupName)).list();
			}

		});
	}

	public void settSentDato(final PostShipment postShipment, final Date sentDate) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				String sql = "update PostShipment o set o.sent=:sentDate where o.postShipmentId=:postShipmentId";

				session.createQuery(sql).setDate("sentDate", sentDate)
						.setInteger("postShipmentId", postShipment.getPostShipmentId()).executeUpdate();
				return null;
			}

		});

	}

	public void settLevert(final PostShipment postShipment, final Date levertDate) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				String sql = "update PostShipment o set o.levert=:levertDate where o.postShipmentId=:postShipmentId";

				session.createQuery(sql).setDate("levertDate", levertDate)
						.setInteger("postShipmentId", postShipment.getPostShipmentId()).executeUpdate();
				return null;
			}

		});

		
	}

}
