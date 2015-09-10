package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.VeggProductionVDAO;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.VeggProductionV;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view VEGG_PRODUCTION_V for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class VeggProductionVDAOHibernate extends BaseDAOHibernate<VeggProductionV> implements VeggProductionVDAO {

    /**
     * Konstruktør
     */
    public VeggProductionVDAOHibernate() {
	super(VeggProductionV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.VeggProductionVDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findAll() {
	return getHibernateTemplate().find("from VeggProductionV order by transportYear,transportWeek,loadingDate,transportDetails,loadTime");
    }

    /**
     * @see no.ugland.utransprod.dao.VeggProductionVDAO#findByOrderNr(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findByOrderNr(final String orderNr) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(VeggProductionV.class).add(Restrictions.eq("orderNr", orderNr)).list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.VeggProductionVDAO#refresh(no.ugland.utransprod.model.VeggProductionV)
     */
    public void refresh(Produceable veggProductionV) {
	getHibernateTemplate().flush();
	getHibernateTemplate().load(veggProductionV, veggProductionV.getOrderNr());

    }

    /**
     * @see no.ugland.utransprod.dao.VeggProductionVDAO#findByCustomerNr(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findByCustomerNr(final Integer customerNr) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(VeggProductionV.class).add(Restrictions.eq("customerNr", customerNr)).list();
	    }

	});
    }

    public List<Produceable> findByCustomerNrAndProductAreaGroup(final Integer customerNr, final ProductAreaGroup productAreaGroup) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(VeggProductionV.class).add(Restrictions.eq("customerNr", customerNr));

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
		Criteria criteria = session.createCriteria(VeggProductionV.class).add(Restrictions.eq("orderNr", orderNr));

		if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
		}
		return criteria.list();
	    }

	});
    }

}
