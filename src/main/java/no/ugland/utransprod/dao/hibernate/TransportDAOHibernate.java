package no.ugland.utransprod.dao.hibernate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.dao.TransportDAO;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.google.inject.internal.Lists;

/**
 * Implemntasjon av DAO for view TRANSPORT for hibernate.
 * 
 * @author atle.brekka
 */
public class TransportDAOHibernate extends BaseDAOHibernate<Transport> implements TransportDAO {

    /**
     * Konstruktør.
     */
    public TransportDAOHibernate() {
	super(Transport.class);
    }

    /**
     * @see no.ugland.utransprod.dao.TransportDAO#refreshObject(no.ugland.utransprod.model.Transport)
     */
    public final void refreshObject(final Transport transport) {
	getHibernateTemplate().load(transport, transport.getTransportId());
	getHibernateTemplate().flush();

    }

    /**
     * @see no.ugland.utransprod.dao.TransportDAO#lazyLoadTransport(no.ugland.utransprod.model.Transport,
     *      no.ugland.utransprod.service.enums.LazyLoadTransportEnum[])
     */
    public final void lazyLoadTransport(final Transport transport, final LazyLoadTransportEnum[] enums) {
	if (transport != null && transport.getTransportId() != null) {
	    getHibernateTemplate().execute(new HibernateCallback() {

		@SuppressWarnings("incomplete-switch")
		public Object doInHibernate(final Session session) {
		    if (!session.contains(transport)) {
			session.load(transport, transport.getTransportId());
		    }

		    Set<?> set;

		    List<LazyLoadTransportEnum> enumList = Arrays.asList(enums);

		    for (LazyLoadTransportEnum lazyEnum : enumList) {
			switch (lazyEnum) {
			case ORDER:
			    Set<Order> orders = transport.getOrders();
			    if (!Hibernate.isInitialized(orders)) {
				orders.size();
			    }
			    if (enumList.contains(LazyLoadTransportEnum.ORDER_LINES)) {
				for (Order order : orders) {
				    Set<OrderLine> orderLines = order.getOrderLines();
				    if (!Hibernate.isInitialized(orderLines)) {
					orderLines.size();
				    }

				    if (enumList.contains(LazyLoadTransportEnum.ORDER_LINE_ATTRIBUTES)) {
					for (OrderLine orderLine : orderLines) {
					    set = orderLine.getOrderLineAttributes();

					    if (!Hibernate.isInitialized(set)) {
						set.size();
					    }

					}
				    }
				}
			    }

			    if (enumList.contains(LazyLoadTransportEnum.ORDER_COMMENTS)) {
				for (Order order : orders) {
				    set = order.getOrderComments();
				    if (!Hibernate.isInitialized(set)) {
					set.size();
				    }
				}
			    }

			    if (enumList.contains(LazyLoadTransportEnum.ORDER_COLLIES)) {
				for (Order order : orders) {
				    set = order.getCollies();
				    if (!Hibernate.isInitialized(set)) {
					set.size();
				    }
				}
			    }

			    // orders.iterator();

			    break;
			case POST_SHIPMENTS:
			    Set<PostShipment> postShipments = transport.getPostShipments();
			    if (!Hibernate.isInitialized(postShipments)) {
				postShipments.size();
			    }
			    if (enumList.contains(LazyLoadTransportEnum.ORDER_LINES)) {
				for (PostShipment postShipment : postShipments) {
				    Set<OrderLine> orderLines = postShipment.getOrderLines();
				    if (!Hibernate.isInitialized(orderLines)) {
					orderLines.size();
				    }

				    if (enumList.contains(LazyLoadTransportEnum.ORDER_LINE_ATTRIBUTES)) {
					for (OrderLine orderLine : orderLines) {
					    set = orderLine.getOrderLineAttributes();

					    if (!Hibernate.isInitialized(set)) {
						set.size();
					    }

					}
				    }
				}
			    }
			    if (enumList.contains(LazyLoadTransportEnum.POST_SHIPMENT_COLLIES)) {
				for (PostShipment postShipment : postShipments) {
				    set = postShipment.getCollies();
				    if (!Hibernate.isInitialized(set)) {
					set.size();
				    }
				}
			    }

			    if (enumList.contains(LazyLoadTransportEnum.ORDER_COMMENTS)) {
				for (PostShipment postShipment : postShipments) {
				    set = postShipment.getOrder().getOrderComments();
				    if (!Hibernate.isInitialized(set)) {
					set.size();
				    }
				}
			    }

			    // postShipments.iterator();
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
     * @see no.ugland.utransprod.dao.TransportDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public final List<Transport> findAll() {
	return (List<Transport>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		return session.createCriteria(Transport.class).addOrder(org.hibernate.criterion.Order.asc("transportYear"))
			.addOrder(org.hibernate.criterion.Order.asc("transportWeek")).list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.TransportDAO#findByYearAndWeek(java.lang.Integer,
     *      java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public final List<Transport> findByYearAndWeek(final Integer year, final Integer week) {
	return (List<Transport>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {

		return session.createCriteria(Transport.class).add(Restrictions.eq("transportYear", year))
			.add(Restrictions.eq("transportWeek", week)).setFetchMode("order.orderLines", FetchMode.JOIN)
			.setFetchMode("postShipment.orderLines", FetchMode.JOIN).addOrder(org.hibernate.criterion.Order.asc("loadingDate"))
			.addOrder(org.hibernate.criterion.Order.asc("loadTime")).list();
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public final List<Transport> findBetweenYearAndWeek(final Integer year, final Integer fromWeek, final Integer toWeek, final String[] orderBy) {
	return (List<Transport>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {

		Criteria crit = session.createCriteria(Transport.class).add(Restrictions.eq("transportYear", year))
			.add(Restrictions.between("transportWeek", fromWeek, toWeek));

		if (orderBy != null) {
		    for (int i = 0; i < orderBy.length; i++) {
			crit.addOrder(org.hibernate.criterion.Order.asc(orderBy[i]));
		    }
		}
		return crit.list();

	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.TransportDAO#findNewTransports()
     */
    @SuppressWarnings("unchecked")
    public final List<Transport> findNewTransports() {
	return (List<Transport>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {

		return session.createCriteria(Transport.class).add(Restrictions.ge("transportYear", Util.getCurrentYear()))
			.add(Restrictions.ge("transportWeek", Util.getCurrentWeek())).addOrder(org.hibernate.criterion.Order.asc("transportYear"))
			.addOrder(org.hibernate.criterion.Order.asc("transportWeek")).list();
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public final List<Transport> findByYearAndWeekAndProductAreaGroup(final Integer year, final Integer week, final ProductAreaGroup productAreaGroup) {
	return (List<Transport>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {

		List<Transport> transportList = getTransportList(year, week, productAreaGroup, session);
		List<Transport> emptyTransportList = findEmptyTransportsByYearAndWeek(year, week);
		transportList.addAll(emptyTransportList);
		return transportList;
	    }

	});
    }

    @SuppressWarnings("unchecked")
    final List<Transport> getTransportList(final Integer year, final Integer week, final ProductAreaGroup productAreaGroup, final Session session) {
	String sql = "select transport from Transport transport " + " left outer join fetch transport.orders transportOrder "
		+ "left outer join fetch transportOrder.orderLines " + " left outer join fetch transport.postShipments transportPostShipment "
		+ "left outer join fetch transportPostShipment.orderLines " + "          where transport.transportYear=:year and "
		+ "                  transport.transportWeek=:week and "
		+ "       (exists(select 1 from Order customerOrder,ProductArea productArea "
		+ "                             where customerOrder.transport=transport and "
		+ "                               customerOrder.productArea=productArea and "
		+ "                      productArea.productAreaGroup=:productAreaGroup) or " + " exists(select 1 from PostShipment postShipment,"
		+ "               Order customerOrder,ProductArea productArea "
		+ "                              where postShipment.transport=transport and "
		+ "                                    postShipment.order=customerOrder and "
		+ "                               customerOrder.productArea=productArea and "
		+ "                         productArea.productAreaGroup=:productAreaGroup))";

	Query query = session.createQuery(sql).setParameter("productAreaGroup", productAreaGroup).setParameter("year", year)
		.setParameter("week", week);
	Set<Transport> transportList = com.google.common.collect.Sets.newHashSet(query.list());
	return Lists.newArrayList(transportList);
    }

    @SuppressWarnings("unchecked")
    final List<Transport> findEmptyTransportsByYearAndWeek(final Integer year, final Integer week) {
	return getHibernateTemplate().executeFind(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		String sql = "select transport from Transport transport " + " left outer join fetch transport.orders transportOrder "
			+ "left outer join fetch transportOrder.orderLines" + " left outer join fetch transport.postShipments transportPostShipment "
			+ "left outer join fetch transportPostShipment.orderLines " + "       where transport.transportYear=:year and "
			+ "               transport.transportWeek=:week and " + "               not exists(select 1 from Order customerOrder"
			+ "                           where customerOrder.transport=transport) and "
			+ "               not exists(select 1 from PostShipment postShipment "
			+ "                           where postShipment.transport=transport)";
		return session.createQuery(sql).setParameter("year", year).setParameter("week", week).list();
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public final List<Transport> findSentInPeriode(final Periode periode) {
	return (List<Transport>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		Date fromDate = Util.getFirstDateInWeek(periode.getYear(), periode.getWeek());
		Date toDate = Util.getLastDateInWeek(periode.getYear(), periode.getToWeek());
		return session.createCriteria(Transport.class).add(Restrictions.between("sent", fromDate, toDate)).list();
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public final List<Transport> findInPeriode(final Periode periode, final String productAreaGroupName) {
	return (List<Transport>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		String sql = "select transport from Transport transport " + "          where transport.transportYear=:year and "
			+ "                  transport.transportWeek between :weekFrom and " + "                  :weekTo and "
			+ "       (exists(select 1 from Order customerOrder,ProductArea productArea,"
			+ "                             ProductAreaGroup productAreaGroup "
			+ "                             where customerOrder.transport=transport and "
			+ "                               customerOrder.productArea=productArea and "
			+ "                      productArea.productAreaGroup=productAreaGroup and "
			+ "                productAreaGroup.productAreaGroupName=" + "                :productAreaGroupName) or "
			+ " exists(select 1 from PostShipment postShipment," + "               Order customerOrder,ProductArea productArea,"
			+ "               ProductAreaGroup productAreaGroup "
			+ "                              where postShipment.transport=transport and "
			+ "                                    postShipment.order=customerOrder and "
			+ "                               customerOrder.productArea=productArea and "
			+ "                         productArea.productAreaGroup=productAreaGroup and"
			+ "                   productAreaGroup.productAreaGroupName=" + "                    :productAreaGroupName))";

		List<Transport> transportList = session.createQuery(sql).setParameter("productAreaGroupName", productAreaGroupName)
			.setParameter("year", periode.getYear()).setParameter("weekFrom", periode.getWeek())
			.setParameter("weekTo", periode.getToWeek()).list();
		return transportList;
	    }

	});

    }
}
