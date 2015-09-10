package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.GavlProductionVDAO;
import no.ugland.utransprod.model.GavlProductionV;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view GAVL_PRODUCTION_V
 * 
 * @author atle.brekka
 * 
 */
public class GavlProductionVDAOHibernate extends BaseDAOHibernate<GavlProductionV> implements GavlProductionVDAO {

    /**
     * Konstruktør
     */
    public GavlProductionVDAOHibernate() {
	super(GavlProductionV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.GavlProductionVDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findAll() {
	return getHibernateTemplate().find("from GavlProductionV order by transportYear,transportWeek,loadingDate,transportDetails");
    }

    /**
     * @see no.ugland.utransprod.dao.GavlProductionVDAO#findByOrderNr(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findByOrderNr(final String orderNr) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(

		GavlProductionV.class).add(Restrictions.eq("orderNr", orderNr)).list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.GavlProductionVDAO#refresh(no.ugland.utransprod.model.GavlProductionV)
     */
    public void refresh(Produceable gavlProductionV) {
	getHibernateTemplate().flush();
	getHibernateTemplate().load(gavlProductionV, gavlProductionV.getOrderLineId());

    }

    /**
     * @see no.ugland.utransprod.dao.GavlProductionVDAO#findByCustomerNr(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findByCustomerNr(final Integer customerNr) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(GavlProductionV.class).add(Restrictions.eq("customerNr", customerNr)).list();
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public List<Produceable> findByCustomerNrAndProductAreaGroup(final Integer customerNr, final ProductAreaGroup productAreaGroup) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(GavlProductionV.class).add(Restrictions.eq("customerNr", customerNr));

		if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
		}
		return criteria.list();
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public List<Produceable> findByOrderNrAndProductAreaGroup(final String orderNr, final ProductAreaGroup productAreaGroup) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(

		GavlProductionV.class).add(Restrictions.eq("orderNr", orderNr));

		if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
		}
		return criteria.list();
	    }

	});
    }

}
