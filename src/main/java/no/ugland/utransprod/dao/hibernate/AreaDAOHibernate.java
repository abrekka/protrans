package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.AreaDAO;
import no.ugland.utransprod.model.Area;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class AreaDAOHibernate extends BaseDAOHibernate<Area> implements AreaDAO {
	/**
	 * Konstruktør
	 */
	public AreaDAOHibernate() {
		super(Area.class);
	}

	public Area findByAreaCode(final String areaCode) {
		return (Area) getHibernateTemplate().execute(new HibernateCallback() {

			@SuppressWarnings("unchecked")
			public Object doInHibernate(final Session session) {
				List<Area> list = session.createCriteria(Area.class).add(
						Restrictions.eq("areaCode", areaCode)).list();
				return list != null && list.size() == 1 ? list.get(0) : null;
			}
		});
	}

}
