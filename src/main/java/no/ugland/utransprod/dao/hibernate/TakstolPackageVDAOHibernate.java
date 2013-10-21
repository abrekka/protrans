package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.TakstolPackageVDAO;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TakstolPackageV;

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
public class TakstolPackageVDAOHibernate extends
		BaseDAOHibernate<TakstolPackageV> implements TakstolPackageVDAO {

	/**
	 * Konstruktør
	 */
	public TakstolPackageVDAOHibernate() {
		super(TakstolPackageV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.TakstolPackageVDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<PackableListItem> findAll() {
		return getHibernateTemplate()
				.find(
						"from TakstolPackageV order by orderNr,defaultArticle,numberOfItems desc");
	}

	/**
	 * @see no.ugland.utransprod.dao.TakstolPackageVDAO#findByOrderNr(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<PackableListItem> findByOrderNr(final String orderNr) {
		return (List<PackableListItem>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(

						TakstolPackageV.class).add(
								Restrictions.eq("orderNr", orderNr)).addOrder(
								Order.asc("orderNr")).addOrder(
								Order.asc("defaultArticle")).addOrder(
								Order.desc("numberOfItems")).list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.TakstolPackageVDAO#refresh(no.ugland.utransprod.model.TakstolPackageV)
	 */
	public void refresh(TakstolPackageV takstolPackageV) {
		getHibernateTemplate().flush();
		getHibernateTemplate().load(takstolPackageV,
				takstolPackageV.getOrderLineId());

	}

	/**
	 * @see no.ugland.utransprod.dao.TakstolPackageVDAO#findByCustomerNr(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<PackableListItem> findByCustomerNr(final Integer customerNr) {
		return (List<PackableListItem>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(TakstolPackageV.class)
								.add(Restrictions.eq("customerNr", customerNr))
								.addOrder(Order.asc("orderNr")).addOrder(
										Order.asc("defaultArticle")).addOrder(
										Order.desc("numberOfItems")).list();
					}

				});
	}

	public List<PackableListItem> findApplyableByOrderNrAndArticleName(
			final String orderNr, final String mainArticleName) {
		return (List<PackableListItem>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(

						TakstolPackageV.class).add(
								Restrictions.eq("orderNr", orderNr))
								.add(
										Restrictions.eq("articleName",
												mainArticleName)).addOrder(
										Order.asc("orderNr")).addOrder(
										Order.asc("defaultArticle")).addOrder(
										Order.desc("numberOfItems")).list();
					}

				});
	}

	public List<PackableListItem> findByCustomerNrAndProductAreaGroup(
			final Integer customerNr, final ProductAreaGroup productAreaGroup) {
		return (List<PackableListItem>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria= session.createCriteria(TakstolPackageV.class)
								.add(Restrictions.eq("customerNr", customerNr));
						if(productAreaGroup!=null&&!productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")){
							criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
						}
						
						criteria.addOrder(Order.asc("orderNr")).addOrder(
										Order.asc("defaultArticle")).addOrder(
										Order.desc("numberOfItems"));
						return criteria.list();
					}

				});
	}

	public List<PackableListItem> findByOrderNrAndProductAreaGroup(
			final String orderNr, final ProductAreaGroup productAreaGroup) {
		return (List<PackableListItem>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria= session.createCriteria(
						TakstolPackageV.class).add(
								Restrictions.eq("orderNr", orderNr));
						if(productAreaGroup!=null&&!productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")){
							criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
						}
						criteria.addOrder(
								Order.asc("orderNr")).addOrder(
								Order.asc("defaultArticle")).addOrder(
								Order.desc("numberOfItems"));
						return criteria.list();
					}

				});
	}

}
