package no.ugland.utransprod.dao.hibernate;

import java.util.Collection;
import java.util.Date;

import no.ugland.utransprod.dao.TakstoltegnerVDAO;
import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class TakstoltegnerVDAOHibernate extends
		BaseDAOHibernate<TakstoltegnerV> implements TakstoltegnerVDAO {

	public TakstoltegnerVDAOHibernate() {
		super(TakstoltegnerV.class);
	}

	public Collection<TakstoltegnerV> findByPeriode(final Periode periode) {
		return (Collection<TakstoltegnerV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session) {
						Date startDate = Util.getFirstDateInWeek(
								periode.getYear(), periode.getWeek());
						Date enddate = Util.getLastDateInWeek(
								periode.getYear(), periode.getToWeek());
						return session
								.createCriteria(TakstoltegnerV.class)
								.add(Restrictions.between("trossReady",
										startDate, enddate))
								.addOrder(Order.asc("trossDrawer")).list();
					}
				});
	}

}
