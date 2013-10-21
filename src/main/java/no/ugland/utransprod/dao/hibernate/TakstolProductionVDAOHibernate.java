package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.TakstolProductionVDAO;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TakstolProductionV;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av dao for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class TakstolProductionVDAOHibernate extends
		BaseDAOHibernate<TakstolProductionV> implements TakstolProductionVDAO {

	/**
	 * Konstruktør
	 */
	public TakstolProductionVDAOHibernate() {
		super(TakstolProductionV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.TakstolProductionVDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<Produceable> findAll() {
		return getHibernateTemplate()
				.find(
						"from TakstolProductionV order by orderNr,defaultArticle,numberOfItems desc,orderLineId");
	}

	/**
	 * @see no.ugland.utransprod.dao.TakstolProductionVDAO#findByOrderNr(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Produceable> findByOrderNr(final String orderNr) {
		return (List<Produceable>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(

						TakstolProductionV.class).add(
								Restrictions.eq("orderNr", orderNr)).addOrder(
								Order.asc("orderNr")).addOrder(
								Order.asc("defaultArticle")).addOrder(
								Order.desc("numberOfItems")).addOrder(
								Order.asc("orderLineId")).list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.TakstolProductionVDAO#refresh(no.ugland.utransprod.model.TakstolProductionV)
	 */
	public void refresh(Produceable takstolProductionV) {
		getHibernateTemplate().flush();
		getHibernateTemplate().load(takstolProductionV,
				takstolProductionV.getOrderLineId());

	}

	/**
	 * @see no.ugland.utransprod.dao.TakstolProductionVDAO#findByCustomerNr(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<Produceable> findByCustomerNr(final Integer customerNr) {
		return (List<Produceable>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(TakstolProductionV.class)
								.add(Restrictions.eq("customerNr", customerNr))
								.addOrder(Order.asc("orderNr")).addOrder(
										Order.asc("defaultArticle")).addOrder(
										Order.desc("numberOfItems")).addOrder(
										Order.asc("orderLineId")).list();
					}

				});
	}

	public List<Produceable> findByOrderNrAndArticleName(final String orderNr,
			final String articleName) {
		return (List<Produceable>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(

						TakstolProductionV.class).add(
								Restrictions.eq("orderNr", orderNr)).add(
								Restrictions.eq("articleName", articleName))
								.addOrder(Order.asc("orderNr")).addOrder(
										Order.asc("defaultArticle")).addOrder(
										Order.desc("numberOfItems")).addOrder(
										Order.asc("orderLineId")).list();
					}

				});
	}

	public List<Produceable> findByCustomerNrAndProductAreaGroup(
			final Integer customerNr, final ProductAreaGroup productAreaGroup) {
		return (List<Produceable>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = session.createCriteria(
								TakstolProductionV.class).add(
								Restrictions.eq("customerNr", customerNr));
						if (productAreaGroup != null
								&& !productAreaGroup.getProductAreaGroupName()
										.equalsIgnoreCase("Alle")) {
							criteria.add(Restrictions.eq(
									"productAreaGroupName", productAreaGroup
											.getProductAreaGroupName()));
						}

						criteria.addOrder(Order.asc("orderNr")).addOrder(
								Order.asc("defaultArticle")).addOrder(
								Order.desc("numberOfItems")).addOrder(
								Order.asc("orderLineId"));
						return criteria.list();
					}

				});
	}

	public List<Produceable> findByOrderNrAndProductAreaGroup(
			final String orderNr, final ProductAreaGroup productAreaGroup) {
		return (List<Produceable>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = session.createCriteria(

						TakstolProductionV.class).add(
								Restrictions.eq("orderNr", orderNr));
						if (productAreaGroup != null
								&& !productAreaGroup.getProductAreaGroupName()
										.equalsIgnoreCase("Alle")) {
							criteria.add(Restrictions.eq(
									"productAreaGroupName", productAreaGroup
											.getProductAreaGroupName()));
						}
						criteria.addOrder(Order.asc("orderNr")).addOrder(
								Order.asc("defaultArticle")).addOrder(
								Order.desc("numberOfItems")).addOrder(
								Order.asc("orderLineId"));

						return criteria.list();
					}

				});
	}

}
