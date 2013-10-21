package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.MainPackageVDAO;
import no.ugland.utransprod.model.MainPackageV;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view MAIN_PACKAGE_V for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class MainPackageVDAOHibernate extends BaseDAOHibernate<MainPackageV>
		implements MainPackageVDAO {
	/**
	 * Konstruktør
	 */
	public MainPackageVDAOHibernate() {
		super(MainPackageV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.MainPackageVDAO#refresh(no.ugland.utransprod.model.MainPackageV)
	 */
	public void refresh(MainPackageV mainPackageV) {
		getHibernateTemplate().flush();
		getHibernateTemplate().load(mainPackageV, mainPackageV.getOrderId());

	}

	/**
	 * @see no.ugland.utransprod.dao.MainPackageVDAO#findByOrderNr(java.lang.String)
	 */
	public MainPackageV findByOrderNr(final String orderNr) {
		return (MainPackageV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						List<MainPackageV> list = session.createCriteria(
								MainPackageV.class).add(
								Restrictions.eq("orderNr", orderNr)).list();
						if (list != null && list.size() == 1) {
							return list.get(0);
						}
						return null;
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.MainPackageVDAO#findByCustomerNr(java.lang.Integer)
	 */
	public MainPackageV findByCustomerNr(final Integer customerNr) {
		return (MainPackageV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						List<MainPackageV> list = session.createCriteria(
								MainPackageV.class).add(
								Restrictions.eq("customerNr", customerNr))
								.list();
						if (list != null && list.size() == 1) {
							return list.get(0);
						}
						return null;
					}

				});
	}

}
