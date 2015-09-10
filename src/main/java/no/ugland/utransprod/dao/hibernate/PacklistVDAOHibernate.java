package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.PacklistVDAO;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.ProductAreaGroup;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view PACKLIST_V
 * 
 * @author atle.brekka
 * 
 */
public class PacklistVDAOHibernate extends BaseDAOHibernate<PacklistV> implements PacklistVDAO {

    /**
     * Konstruktør
     */
    public PacklistVDAOHibernate() {
	super(PacklistV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.PacklistVDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<PacklistV> findAll() {
	return getHibernateTemplate().find("from PacklistV order by loadingDate");
    }

    /**
     * @see no.ugland.utransprod.dao.PacklistVDAO#findByOrderNr(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<PacklistV> findByOrderNr(final String orderNr) {
	return (List<PacklistV>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(

		PacklistV.class).add(Restrictions.eq("orderNr", orderNr)).list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.PacklistVDAO#refresh(no.ugland.utransprod.model.PacklistV)
     */
    public void refresh(PacklistV packlistV) {
	getHibernateTemplate().flush();
	getHibernateTemplate().load(packlistV, packlistV.getOrderId());

    }

    /**
     * @see no.ugland.utransprod.dao.PacklistVDAO#findByCustomerNr(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<PacklistV> findByCustomerNr(final Integer customerNr) {
	return (List<PacklistV>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(PacklistV.class).add(Restrictions.eq("customerNr", customerNr)).list();
	    }

	});
    }

    public List<PacklistV> findByCustomerNrAndProductAreaGroup(final Integer customerNr, final ProductAreaGroup productAreaGroup) {
	return (List<PacklistV>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(PacklistV.class).add(Restrictions.eq("customerNr", customerNr));

		if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
		}
		return criteria.list();
	    }

	});
    }

    public List<PacklistV> findByOrderNrAndProductAreaGroup(final String orderNr, final ProductAreaGroup productAreaGroup) {
	return (List<PacklistV>) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(

		PacklistV.class).add(Restrictions.eq("orderNr", orderNr));

		if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
		}
		return criteria.list();
	    }

	});
    }

}
