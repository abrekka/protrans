package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.dao.NokkelDriftProsjekteringVDAO;
import no.ugland.utransprod.model.NokkelDriftProsjekteringV;
import no.ugland.utransprod.model.NokkelDriftProsjekteringVPK;
import no.ugland.utransprod.util.YearWeek;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view NOKKEL_DRIFT_PROSJEKTERING_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelDriftProsjekteringVDAOHibernate extends
		BaseDAOHibernate<NokkelDriftProsjekteringV> implements
		NokkelDriftProsjekteringVDAO {
	/**
	 * Konstruktør
	 */
	public NokkelDriftProsjekteringVDAOHibernate() {
		super(NokkelDriftProsjekteringV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.NokkelDriftProsjekteringVDAO#findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      no.ugland.utransprod.util.YearWeek, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<NokkelDriftProsjekteringV> findBetweenYearWeek(
			final YearWeek fromYearWeek, final YearWeek toYearWeek,
			final String productArea) {
		return (List<NokkelDriftProsjekteringV>) getHibernateTemplate()
				.execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select nokkelDriftProsjekteringV "
								+ "			from NokkelDriftProsjekteringV nokkelDriftProsjekteringV "
								+ "			where cast(nokkelDriftProsjekteringV.nokkelDriftProsjekteringVPK.packlistYear as string)||cast(nokkelDriftProsjekteringV.nokkelDriftProsjekteringVPK.packlistWeek+10 as string) "
								+ "					between  :fromString and :toString and "
								+ "					nokkelDriftProsjekteringV.nokkelDriftProsjekteringVPK.productArea=:productArea";

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
	 * @see no.ugland.utransprod.dao.NokkelDriftProsjekteringVDAO#aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      java.lang.String)
	 */
	public NokkelDriftProsjekteringV aggreagateYearWeek(
			final YearWeek currentYearWeek, final String productArea) {
		return (NokkelDriftProsjekteringV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select sum(nokkelDriftProsjekteringV.orderCount),"
								+ "				sum(nokkelDriftProsjekteringV.deviationCount),"
								+ "				sum(nokkelDriftProsjekteringV.internalCost),"
								+ "				sum(nokkelDriftProsjekteringV.customerCost) "
								+ "			from NokkelDriftProsjekteringV nokkelDriftProsjekteringV "
								+ "			where nokkelDriftProsjekteringV.nokkelDriftProsjekteringVPK.packlistYear=:year and "
								+ "		  nokkelDriftProsjekteringV.nokkelDriftProsjekteringVPK.packlistWeek <= :week and "
								+ "					nokkelDriftProsjekteringV.nokkelDriftProsjekteringVPK.productArea=:productArea";

						List<Object[]> list = session
								.createQuery(query)
								.setParameter("year", currentYearWeek.getYear())
								.setParameter("week", currentYearWeek.getWeek())
								.setParameter("productArea", productArea)
								.list();

						if (list != null && list.size() == 1) {
							Object[] obj = list.get(0);
							return new NokkelDriftProsjekteringV(
									new NokkelDriftProsjekteringVPK(
											currentYearWeek.getYear(),
											currentYearWeek.getWeek(),
											productArea), (Integer) obj[0],
									(Integer) obj[1], (BigDecimal) obj[2],
									(BigDecimal) obj[3]);
						}
						return null;
					}

				});
	}

}
