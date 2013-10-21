package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.PaidVDAO;
import no.ugland.utransprod.model.PaidV;
import no.ugland.utransprod.model.ProductAreaGroup;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av interface for DAO mot view Paid_V
 * 
 * @author atle.brekka
 * 
 */
public class PaidVDAOHibernate extends BaseDAOHibernate<PaidV> implements
		PaidVDAO {

	/**
	 * Konstruktør
	 */
	public PaidVDAOHibernate() {
		super(PaidV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.PaidVDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<PaidV> findAll() {
		return getHibernateTemplate().find("from PaidV order by sent");
	}

	/**
	 * @see no.ugland.utransprod.dao.PaidVDAO#findByCustomerNr(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<PaidV> findByCustomerNr(final Integer customerNr) {
		return (List<PaidV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(PaidV.class).add(
								Restrictions.eq("customerNr", customerNr))
								.list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.PaidVDAO#findByOrderNr(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<PaidV> findByOrderNr(final String orderNr) {
		return (List<PaidV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(PaidV.class).add(
								Restrictions.eq("orderNr", orderNr)).list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.PaidVDAO#refresh(no.ugland.utransprod.model.PaidV)
	 */
	public void refresh(PaidV paidV) {
		getHibernateTemplate().flush();
		getHibernateTemplate().load(paidV, paidV.getOrderId());

	}

	public List<PaidV> findByCustomerNrAndProductAreaGroup(final Integer customerNr,
			final ProductAreaGroup productAreaGroup) {
		return (List<PaidV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria= session.createCriteria(PaidV.class).add(
								Restrictions.eq("customerNr", customerNr));
						
						if(productAreaGroup!=null&&!productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")){
							criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
						}
						return criteria.list();
					}

				});
	}

	public List<PaidV> findByOrderNrAndProductAreaGroup(final String orderNr,
			final ProductAreaGroup productAreaGroup) {
		return (List<PaidV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria= session.createCriteria(PaidV.class).add(
								Restrictions.eq("orderNr", orderNr));
						
						if(productAreaGroup!=null&&!productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")){
							criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
						}
						return criteria.list();
					}

				});
	}

}
