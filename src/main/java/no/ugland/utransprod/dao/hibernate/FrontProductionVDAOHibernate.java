package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.FrontProductionVDAO;
import no.ugland.utransprod.model.FrontProductionV;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av dao for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class FrontProductionVDAOHibernate extends BaseDAOHibernate<FrontProductionV> implements FrontProductionVDAO {

    /**
     * Konstruktør
     */
    public FrontProductionVDAOHibernate() {
	super(FrontProductionV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.FrontProductionVDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findAll() {
	return getHibernateTemplate().find("from FrontProductionV order by transportYear,transportWeek,loadingDate,transportDetails,loadTime");
    }

    /**
     * @see no.ugland.utransprod.dao.FrontProductionVDAO#findByOrderNr(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findByOrderNr(final String orderNr) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(FrontProductionV.class).add(Restrictions.eq("orderNr", orderNr)).list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.FrontProductionVDAO#refresh(no.ugland.utransprod.model.FrontProductionV)
     */
    public void refresh(Produceable frontProductionV) {
	getHibernateTemplate().flush();
	getHibernateTemplate().load(frontProductionV, frontProductionV.getOrderLineId());

    }

    /**
     * @see no.ugland.utransprod.dao.FrontProductionVDAO#findByCustomerNr(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Produceable> findByCustomerNr(final Integer customerNr) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(FrontProductionV.class).add(Restrictions.eq("customerNr", customerNr)).list();
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public List<Produceable> findByCustomerNrProductAreaGroup(final Integer customerNr, final ProductAreaGroup productAreaGroup) {
	return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(FrontProductionV.class).add(Restrictions.eq("customerNr", customerNr));
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
		Criteria criteria = session.createCriteria(FrontProductionV.class).add(Restrictions.eq("orderNr", orderNr));
		if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
		}
		return criteria.list();
	    }

	});
    }

}
