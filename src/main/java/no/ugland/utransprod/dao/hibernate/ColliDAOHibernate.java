package no.ugland.utransprod.dao.hibernate;

import java.util.List;
import java.util.Set;

import no.ugland.utransprod.dao.ColliDAO;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av interface for DAO mot COLLI for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class ColliDAOHibernate extends BaseDAOHibernate<Colli> implements ColliDAO {
    /**
	 *
	 */
    public ColliDAOHibernate() {
	super(Colli.class);
    }

    /**
     * @see no.ugland.utransprod.dao.ColliDAO#findByNameAndOrder(java.lang.String,
     *      no.ugland.utransprod.model.Order)
     */
    public Colli findByNameAndOrder(final String colliName, final Order order) {
	return (Colli) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		List<Colli> collies = session.createCriteria(Colli.class).add(Restrictions.eq("colliName", colliName))
			.add(Restrictions.eq("order", order)).list();
		if (collies != null && !collies.isEmpty()) {
		    return collies.get(0);
		}
		return null;
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.ColliDAO#refreshObject(no.ugland.utransprod.model.Colli)
     */
    public void refreshObject(Colli colli) {
	getHibernateTemplate().load(colli, colli.getColliId());

    }

    /**
     * @see no.ugland.utransprod.dao.ColliDAO#lazyLoadColli(no.ugland.utransprod.model.Colli,
     *      no.ugland.utransprod.service.enums.LazyLoadColliEnum[])
     */
    /*
     * public void lazyLoadColli(final Colli colli, final LazyLoadEnum[][]
     * enums) { if (colli != null && colli.getColliId() != null) {
     * getHibernateTemplate().execute(new HibernateCallback() {
     * 
     * public Object doInHibernate(Session session) throws HibernateException {
     * // if (!session.contains(colli)) { session.load(colli,
     * colli.getColliId()); // } Set<OrderLine> set = null;
     * 
     * for (LazyLoadEnum[] lazyEnum : enums) { lazyEnum[0].lazyLoad(colli,
     * lazyEnum[1]); /*switch (lazyEnum) { case ORDER_LINES: set =
     * colli.getOrderLines(); set.iterator();
     * 
     * break; case ORDER_LINE_ATTRIBUTES: if (set != null) {
     * Set<OrderLineAttribute> attributes; for (OrderLine orderLine : set) {
     * attributes = orderLine .getOrderLineAttributes(); attributes.iterator();
     * } } break; } } return null; }
     * 
     * });
     * 
     * }
     * 
     * }
     */

    /**
     * @see no.ugland.utransprod.dao.ColliDAO#findByNameAndPostShipment(java.lang.String,
     *      no.ugland.utransprod.model.PostShipment)
     */
    public Colli findByNameAndPostShipment(final String colliName, final PostShipment postShipment) {
	return (Colli) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		List<Colli> collies = session.createCriteria(Colli.class).add(Restrictions.eq("colliName", colliName))
			.add(Restrictions.eq("postShipment", postShipment)).list();
		if (collies != null && collies.size() == 1) {
		    return collies.get(0);
		}
		return null;
	    }

	});
    }

    public void lazyLoadAll(final Colli colli) {
	getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		if (!session.contains(colli) && colli.getColliId() != null) {
		    session.load(colli, colli.getColliId());
		}
		Set<OrderLine> orderLines = colli.getOrderLines();
		Set<?> set;
		for (OrderLine orderLine : orderLines) {
		    set = orderLine.getOrderLines();
		    set.iterator();

		    set = orderLine.getOrderLineAttributes();
		    set.iterator();
		}

		return null;
	    }

	});
    }

}
