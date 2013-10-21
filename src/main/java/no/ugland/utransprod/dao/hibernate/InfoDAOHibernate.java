package no.ugland.utransprod.dao.hibernate;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.dao.InfoDAO;
import no.ugland.utransprod.model.Info;
import no.ugland.utransprod.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for tabell INFO
 * 
 * @author atle.brekka
 * 
 */
public class InfoDAOHibernate extends BaseDAOHibernate<Info> implements InfoDAO {

	/**
	 * Konstruktør
	 */
	public InfoDAOHibernate() {
		super(Info.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.InfoDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<Info> findAll() {
		return getHibernateTemplate().find("from Info order by dateFrom desc");
	}

	/**
	 * @see no.ugland.utransprod.dao.InfoDAO#refreshObject(no.ugland.utransprod.model.Info)
	 */
	public void refreshObject(Info info) {
		getHibernateTemplate().load(info, info.getInfoId());

	}

	/**
	 * @see no.ugland.utransprod.dao.InfoDAO#findByDate(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<Info> findByDate(final Date date) {
		return (List<Info>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {

						return session
								.createSQLQuery(
										"select * from Info where :date between convert(nvarchar,date_From,12) and convert(nvarchar,date_to,12)")
								.addEntity(Info.class).setParameter("date",
										Util.DATE_FORMAT_YYMMDD.format(date))
								.list();
					}

				});
	}

}
