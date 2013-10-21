package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ApplicationParamDAO;
import no.ugland.utransprod.model.ApplicationParam;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for APPLICATION_PARAM for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class ApplicationParamDAOHibernate extends
		BaseDAOHibernate<ApplicationParam> implements ApplicationParamDAO {
	/**
	 * Konstruktør
	 */
	public ApplicationParamDAOHibernate() {
		super(ApplicationParam.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.ApplicationParamDAO#findByName(java.lang.String)
	 */
	public ApplicationParam findByName(final String paramName) {

		return (ApplicationParam) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						List<ApplicationParam> list = session.createCriteria(
								ApplicationParam.class).add(
								Restrictions.ilike("paramName", paramName))
								.list();

						if (list != null && list.size() == 1) {
							return list.get(0);
						}
						return null;
					}

				});

	}

	/**
	 * @see no.ugland.utransprod.dao.ApplicationParamDAO#refreshObject(no.ugland.utransprod.model.ApplicationParam)
	 */
	public void refreshObject(ApplicationParam applicationParam) {
		getHibernateTemplate().load(applicationParam,
				applicationParam.getParamId());

	}

}
