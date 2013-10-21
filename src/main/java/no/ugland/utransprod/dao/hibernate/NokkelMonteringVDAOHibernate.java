package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.dao.NokkelMonteringVDAO;
import no.ugland.utransprod.model.NokkelMonteringV;
import no.ugland.utransprod.model.NokkelMonteringVPK;
import no.ugland.utransprod.util.YearWeek;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view NOKKEL_MONTERING_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelMonteringVDAOHibernate extends
		BaseDAOHibernate<NokkelMonteringV> implements NokkelMonteringVDAO {
	/**
	 * Konstruktør
	 */
	public NokkelMonteringVDAOHibernate() {
		super(NokkelMonteringV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.NokkelMonteringVDAO#findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      no.ugland.utransprod.util.YearWeek, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<NokkelMonteringV> findBetweenYearWeek(
			final YearWeek fromYearWeek, final YearWeek toYearWeek,
			final String productArea) {
		return (List<NokkelMonteringV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select nokkelMonteringV "
								+ "			from NokkelMonteringV nokkelMonteringV "
								+ "			where cast(nokkelMonteringV.nokkelMonteringVPK.assembliedYear as string)||cast(nokkelMonteringV.nokkelMonteringVPK.assembliedWeek+10 as string) "
								+ "					between  :fromString and :toString and "
								+ "					nokkelMonteringV.nokkelMonteringVPK.productArea=:productArea";

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
	 * @see no.ugland.utransprod.dao.NokkelMonteringVDAO#aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      java.lang.String)
	 */
	public NokkelMonteringV aggreagateYearWeek(final YearWeek currentYearWeek,
			final String productArea) {
		return (NokkelMonteringV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select sum(nokkelMonteringV.orderCount),"
								+ "				sum(nokkelMonteringV.deliveryCost),"
								+ "				sum(nokkelMonteringV.assemblyCost),"
								+ "				sum(nokkelMonteringV.garageCost),"
								+ "				sum(nokkelMonteringV.deviationCount),"
								+ "				sum(nokkelMonteringV.internalCost) "
								+ "			from NokkelMonteringV nokkelMonteringV "
								+ "			where nokkelMonteringV.nokkelMonteringVPK.assembliedYear=:year and "
								+ "		  nokkelMonteringV.nokkelMonteringVPK.assembliedWeek <= :week and "
								+ "					nokkelMonteringV.nokkelMonteringVPK.productArea=:productArea";

						List<Object[]> list = session
								.createQuery(query)
								.setParameter("year", currentYearWeek.getYear())
								.setParameter("week", currentYearWeek.getWeek())
								.setParameter("productArea", productArea)
								.list();

						if (list != null && list.size() == 1) {
							Object[] obj = list.get(0);
							return new NokkelMonteringV(new NokkelMonteringVPK(
									currentYearWeek.getYear(), currentYearWeek
											.getWeek(), productArea),
									(Integer) obj[0], (BigDecimal) obj[1],
									(BigDecimal) obj[2], (BigDecimal) obj[3],
									(Integer) obj[4], (BigDecimal) obj[5]);
						}
						return null;
					}

				});
	}

}
