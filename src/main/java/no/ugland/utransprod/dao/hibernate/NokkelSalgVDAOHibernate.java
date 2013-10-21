package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.dao.NokkelSalgVDAO;
import no.ugland.utransprod.model.NokkelSalgV;
import no.ugland.utransprod.model.NokkelSalgVPK;
import no.ugland.utransprod.util.YearWeek;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view NOKKEL_SALG_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelSalgVDAOHibernate extends BaseDAOHibernate<NokkelSalgV>
		implements NokkelSalgVDAO {
	/**
	 * Konstruktør
	 */
	public NokkelSalgVDAOHibernate() {
		super(NokkelSalgV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.NokkelSalgVDAO#findByYearWeek(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<NokkelSalgV> findByYearWeek(final Integer year,
			final Integer week, final String productArea) {
		return (List<NokkelSalgV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(NokkelSalgV.class).add(
								Restrictions.eq("nokkelSalgVPK.agreementYear",
										year)).add(
								Restrictions.eq("nokkelSalgVPK.agreementWeek",
										week)).add(
								Restrictions.eq("nokkelSalgVPK.productArea",
										productArea)).list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.NokkelSalgVDAO#findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      no.ugland.utransprod.util.YearWeek, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<NokkelSalgV> findBetweenYearWeek(final YearWeek fromYearWeek,
			final YearWeek toYearWeek, final String productArea) {
		return (List<NokkelSalgV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select nokkelSalgV "
								+ "			from NokkelSalgV nokkelSalgV "
								+ "			where cast(nokkelSalgV.nokkelSalgVPK.agreementYear as string)||cast(nokkelSalgV.nokkelSalgVPK.agreementWeek+10 as string) "
								+ "					between  :fromString and :toString and "
								+ "					nokkelSalgV.nokkelSalgVPK.productArea=:productArea";

						return session.createQuery(query).setParameter(
								"fromString",
								fromYearWeek.getFormattetYearWeekAdd10())
								.setParameter("toString",
										toYearWeek.getFormattetYearWeekAdd10())
								.setParameter("productArea", productArea)
								.list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.NokkelSalgVDAO#aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      java.lang.String)
	 */
	public NokkelSalgV aggreagateYearWeek(final YearWeek currentYearWeek,
			final String productArea) {
		return (NokkelSalgV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select sum(nokkelSalgV.orderCount),"
								+ "				sum(nokkelSalgV.deviationCount),"
								+ "				sum(nokkelSalgV.internalCost) "
								+ "			from NokkelSalgV nokkelSalgV "
								+ "			where nokkelSalgV.nokkelSalgVPK.agreementYear=:year and "
								+ "		  nokkelSalgV.nokkelSalgVPK.agreementWeek <= :week and "
								+ "					nokkelSalgV.nokkelSalgVPK.productArea=:productArea";

						List<Object[]> list = session
								.createQuery(query)
								.setParameter("year", currentYearWeek.getYear())
								.setParameter("week", currentYearWeek.getWeek())
								.setParameter("productArea", productArea)
								.list();

						if (list != null && list.size() == 1) {
							Object[] obj = list.get(0);
							return new NokkelSalgV(new NokkelSalgVPK(
									currentYearWeek.getYear(), currentYearWeek
											.getWeek(), productArea),
									(Integer) obj[0], (Integer) obj[1],
									(BigDecimal) obj[2]);
						}
						return null;
					}

				});
	}

}
