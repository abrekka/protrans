package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.dao.NokkelTransportVDAO;
import no.ugland.utransprod.model.NokkelTransportV;
import no.ugland.utransprod.model.NokkelTransportVPK;
import no.ugland.utransprod.util.YearWeek;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view NOKKEL_TRANSPORT_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelTransportVDAOHibernate extends
		BaseDAOHibernate<NokkelTransportV> implements NokkelTransportVDAO {
	/**
	 * Konstruktør
	 */
	public NokkelTransportVDAOHibernate() {
		super(NokkelTransportV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.NokkelTransportVDAO#findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      no.ugland.utransprod.util.YearWeek, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<NokkelTransportV> findBetweenYearWeek(
			final YearWeek fromYearWeek, final YearWeek toYearWeek,
			final String productArea) {
		return (List<NokkelTransportV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select nokkelTransportV "
								+ "			from NokkelTransportV nokkelTransportV "
								+ "			where cast(nokkelTransportV.nokkelTransportVPK.orderSentYear as string)||cast(nokkelTransportV.nokkelTransportVPK.orderSentWeek+10 as string) "
								+ "					between  :fromString and :toString and "
								+ "					nokkelTransportV.nokkelTransportVPK.productArea=:productArea";

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
	 * @see no.ugland.utransprod.dao.NokkelTransportVDAO#aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      java.lang.String)
	 */
	public NokkelTransportV aggreagateYearWeek(final YearWeek currentYearWeek,
			final String productArea) {
		return (NokkelTransportV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select sum(nokkelTransportV.assemblied),"
								+ "				sum(nokkelTransportV.notAssemblied),"
								+ "				sum(nokkelTransportV.garageCost),"
								+ "				sum(nokkelTransportV.transportCost),"
								+ "				sum(nokkelTransportV.assemblyCost),"
								+ "				sum(nokkelTransportV.deviationCount),"
								+ "				sum(nokkelTransportV.internalCost) "
								+ "			from NokkelTransportV nokkelTransportV "
								+ "			where nokkelTransportV.nokkelTransportVPK.orderSentYear=:year and "
								+ "		  nokkelTransportV.nokkelTransportVPK.orderSentWeek <= :week and "
								+ "					nokkelTransportV.nokkelTransportVPK.productArea=:productArea";

						List<Object[]> list = session
								.createQuery(query)
								.setParameter("year", currentYearWeek.getYear())
								.setParameter("week", currentYearWeek.getWeek())
								.setParameter("productArea", productArea)
								.list();

						if (list != null && list.size() == 1) {
							Object[] obj = list.get(0);
							return new NokkelTransportV(new NokkelTransportVPK(
									currentYearWeek.getYear(), currentYearWeek
											.getWeek(), productArea),
									(Integer) obj[0], (Integer) obj[1],
									(BigDecimal) obj[2], (BigDecimal) obj[3],
									(BigDecimal) obj[4], (Integer) obj[5],
									(BigDecimal) obj[6]);
						}
						return null;
					}

				});
	}

}
