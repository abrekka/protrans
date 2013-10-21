package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.TransportSumVDAO;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TransportSumV;

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
public class TransportSumVDAOHibernate extends BaseDAOHibernate<TransportSumV>
		implements TransportSumVDAO {

	/**
	 * Konstruktør
	 */
	public TransportSumVDAOHibernate() {
		super(TransportSumV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.TransportSumVDAO#findYearAndWeek(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	public TransportSumV findYearAndWeek(final Integer currentYear,
			final Integer currentWeek) {
		return (TransportSumV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						String sql = "select new TransportSumV(sum(transportSumV.orderCount), "
								+ "							sum(transportSumV.garageCost), "
								+ "							transportSumV.transportSumVPK.transportYear, "
								+ "							transportSumV.transportSumVPK.transportWeek) "
								+ "  from TransportSumV transportSumV "
								+ "  where transportSumV.transportSumVPK.transportYear=:year and "
								+ "		transportSumV.transportSumVPK.transportWeek=:week "
								+ "  group by transportSumV.transportSumVPK.transportYear, "
								+ "		transportSumV.transportSumVPK.transportWeek";

						List<TransportSumV> list = session.createQuery(sql)
								.setParameter("year", currentYear)
								.setParameter("week", currentWeek).list();
						if (list != null && list.size() == 1) {
							return list.get(0);
						}
						return TransportSumV.UNKNOWN;
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.TransportSumVDAO#findYearAndWeekByProductAreaGroup(java.lang.Integer,
	 *      java.lang.Integer, no.ugland.utransprod.model.ProductAreaGroup)
	 */
	public TransportSumV findYearAndWeekByProductAreaGroup(
			final Integer currentYear, final Integer currentWeek,
			final ProductAreaGroup productAreaGroup) {
		return (TransportSumV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						List<TransportSumV> list = session.createCriteria(
								TransportSumV.class).add(
								Restrictions.eq(
										"transportSumVPK.transportYear",
										currentYear)).add(
								Restrictions.eq(
										"transportSumVPK.transportWeek",
										currentWeek)).add(
								Restrictions.eq(
										"transportSumVPK.productAreaGroupName",
										productAreaGroup
												.getProductAreaGroupName()))
								.list();
						if (list != null && list.size() == 1) {
							return list.get(0);
						}
						return TransportSumV.UNKNOWN;
					}

				});
	}

}
