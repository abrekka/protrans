package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.dao.NokkelOkonomiVDAO;
import no.ugland.utransprod.model.NokkelOkonomiV;
import no.ugland.utransprod.model.NokkelOkonomiVPK;
import no.ugland.utransprod.util.YearWeek;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view NOKKEL_OKONOMI_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelOkonomiVDAOHibernate extends
		BaseDAOHibernate<NokkelOkonomiV> implements NokkelOkonomiVDAO {
	/**
	 * Konstruktør
	 */
	public NokkelOkonomiVDAOHibernate() {
		super(NokkelOkonomiV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.NokkelOkonomiVDAO#findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      no.ugland.utransprod.util.YearWeek, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<NokkelOkonomiV> findBetweenYearWeek(
			final YearWeek fromYearWeek, final YearWeek toYearWeek,
			final String productArea) {
		return (List<NokkelOkonomiV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select nokkelOkonomiV "
								+ "			from NokkelOkonomiV nokkelOkonomiV "
								+ "			where cast(nokkelOkonomiV.nokkelOkonomiVPK.invoiceYear as string)||cast(nokkelOkonomiV.nokkelOkonomiVPK.invoiceWeek+10 as string) "
								+ "					between  :fromString and :toString and "
								+ "					nokkelOkonomiV.nokkelOkonomiVPK.productArea=:productArea";

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
	 * @see no.ugland.utransprod.dao.NokkelOkonomiVDAO#aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
	 *      java.lang.String)
	 */
	public NokkelOkonomiV aggreagateYearWeek(final YearWeek currentYearWeek,
			final String productArea) {
		return (NokkelOkonomiV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						String query = "select sum(nokkelOkonomiV.orderCount),"
								+ "				sum(nokkelOkonomiV.invoicedAmount),"
								+ "				sum(nokkelOkonomiV.deviationCount),"
								+ "				sum(nokkelOkonomiV.internalCost) "
								+ "			from NokkelOkonomiV nokkelOkonomiV "
								+ "			where nokkelOkonomiV.nokkelOkonomiVPK.invoiceYear=:year and "
								+ "		  nokkelOkonomiV.nokkelOkonomiVPK.invoiceWeek <= :week and "
								+ "					nokkelOkonomiV.nokkelOkonomiVPK.productArea=:productArea";

						List<Object[]> list = session
								.createQuery(query)
								.setParameter("year", currentYearWeek.getYear())
								.setParameter("week", currentYearWeek.getWeek())
								.setParameter("productArea", productArea)
								.list();

						if (list != null && list.size() == 1) {
							Object[] obj = list.get(0);
							return new NokkelOkonomiV(new NokkelOkonomiVPK(
									currentYearWeek.getYear(), currentYearWeek
											.getWeek(), productArea),
									(Integer) obj[0], (BigDecimal) obj[1],
									(Integer) obj[2], (BigDecimal) obj[3]);
						}
						return null;
					}

				});
	}

}
