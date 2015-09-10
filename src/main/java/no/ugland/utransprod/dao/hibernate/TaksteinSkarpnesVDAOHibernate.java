package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.TaksteinSkarpnesVDAO;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TaksteinSkarpnesV;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for view TAKSTEIN_SKARPNES_V for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class TaksteinSkarpnesVDAOHibernate extends BaseDAOHibernate<TaksteinSkarpnesV> implements TaksteinSkarpnesVDAO {

    /**
     * Konstruktør
     */
    public TaksteinSkarpnesVDAOHibernate() {
	super(TaksteinSkarpnesV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.TaksteinSkarpnesVDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findAll() {
	return getHibernateTemplate().find("from TaksteinSkarpnesV order by transportYear,transportWeek,loadingDate,transportDetails,loadTime");
    }

    /**
     * @see no.ugland.utransprod.dao.TaksteinSkarpnesVDAO#findByOrderNr(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findByOrderNr(final String orderNr) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(

		TaksteinSkarpnesV.class).add(Restrictions.eq("orderNr", orderNr)).list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.TaksteinSkarpnesVDAO#refresh(no.ugland.utransprod.model.Produceable)
     */
    public void refresh(Produceable taksteinSkarpnesV) {
	getHibernateTemplate().flush();
	getHibernateTemplate().load(taksteinSkarpnesV, taksteinSkarpnesV.getOrderLineId());

    }

    /**
     * @see no.ugland.utransprod.dao.TaksteinSkarpnesVDAO#findByCustomerNr(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findByCustomerNr(final Integer customerNr) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(TaksteinSkarpnesV.class).add(Restrictions.eq("customerNr", customerNr)).list();
	    }

	});
    }

    public List<Produceable> findByCustomerNrAndProductAreaGroup(final Integer customerNr, final ProductAreaGroup productAreaGroup) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(TaksteinSkarpnesV.class).add(Restrictions.eq("customerNr", customerNr));

		if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
		}
		return criteria.list();
	    }

	});
    }

    public List<Produceable> findByOrderNrAndProductAreaGroup(final String orderNr, final ProductAreaGroup productAreaGroup) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(

		TaksteinSkarpnesV.class).add(Restrictions.eq("orderNr", orderNr));

		if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
		}
		return criteria.list();
	    }

	});
    }

}
