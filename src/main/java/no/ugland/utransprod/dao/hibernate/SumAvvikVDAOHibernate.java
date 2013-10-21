package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.SumAvvikVDAO;
import no.ugland.utransprod.model.SumAvvikV;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view SUM_AVVIK_V
 * 
 * @author atle.brekka
 * 
 */
public class SumAvvikVDAOHibernate extends BaseDAOHibernate<SumAvvikV>
		implements SumAvvikVDAO {

	/**
	 * Konstruktør
	 */
	public SumAvvikVDAOHibernate() {
		super(SumAvvikV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.SumAvvikVDAO#findByProductAreaYearAndMonth(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<SumAvvikV> findByProductAreaYearAndMonth(final Integer year,
			final Integer month, final String productArea) {
		return (List<SumAvvikV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {

						String sql = "select new SumAvvikV(sumAvvikV.sumAvvikVPK.registrationYear,"
								+ "					sumAvvikV.registrationMonth,"
								+ "					sum(sumAvvikV.deviationCount),"
								+ "					sum(sumAvvikV.internalCost),"
								+ "					sumAvvikV.sumAvvikVPK.productArea,"
								+ "					sumAvvikV.sumAvvikVPK.jobFunctionName) "
								+ "	from SumAvvikV sumAvvikV "
								+ "	where sumAvvikV.sumAvvikVPK.registrationYear=:year and "
								+ "		sumAvvikV.registrationMonth=:month and "
								+ "		sumAvvikV.sumAvvikVPK.productArea=:productArea "
								+ "	group by sumAvvikV.sumAvvikVPK.registrationYear,"
								+ "		sumAvvikV.registrationMonth,"
								+ "		sumAvvikV.sumAvvikVPK.productArea,"
								+ "		sumAvvikV.sumAvvikVPK.jobFunctionName "
								+ "	order by sumAvvikV.sumAvvikVPK.jobFunctionName";

						return session.createQuery(sql).setParameter("year",
								year).setParameter("month", month)
								.setParameter("productArea", productArea)
								.list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.SumAvvikVDAO#findSumByProductAreaYearAndMonth(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<SumAvvikV> findSumByProductAreaYearAndMonth(final Integer year,
			final Integer month, final String productArea) {
		return (List<SumAvvikV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {

						String sql = "select new SumAvvikV(sumAvvikV.sumAvvikVPK.registrationYear,"
								+ "					sum(sumAvvikV.deviationCount),"
								+ "					sum(sumAvvikV.internalCost),"
								+ "					sumAvvikV.sumAvvikVPK.productArea,"
								+ "					sumAvvikV.sumAvvikVPK.jobFunctionName) "
								+ "	from SumAvvikV sumAvvikV "
								+ "	where sumAvvikV.sumAvvikVPK.registrationYear=:year and "
								+ "		sumAvvikV.registrationMonth<=:month and "
								+ "		sumAvvikV.sumAvvikVPK.productArea=:productArea "
								+ "	group by sumAvvikV.sumAvvikVPK.registrationYear,"
								+ "		sumAvvikV.sumAvvikVPK.productArea,"
								+ "		sumAvvikV.sumAvvikVPK.jobFunctionName "
								+ "	order by sumAvvikV.sumAvvikVPK.jobFunctionName";

						return session.createQuery(sql).setParameter("year",
								year).setParameter("month", month)
								.setParameter("productArea", productArea)
								.list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.SumAvvikVDAO#findByProductAreaYearAndMonthWithClosed(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<SumAvvikV> findByProductAreaYearAndMonthWithClosed(
			final Integer year, final Integer month, final String productArea) {
		return (List<SumAvvikV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {

						String sql = "select new SumAvvikV(sumAvvikV.sumAvvikVPK.registrationYear,"
								+ "					sumAvvikV.registrationMonth,"
								+ "					sum(sumAvvikV.deviationCount),"
								+ "					sum(sumAvvikV.internalCost),"
								+ "					sumAvvikV.sumAvvikVPK.productArea,"
								+ "					sumAvvikV.sumAvvikVPK.jobFunctionName,"
								+ "					sumAvvikV.sumAvvikVPK.closed) "
								+ "	from SumAvvikV sumAvvikV "
								+ "	where sumAvvikV.sumAvvikVPK.registrationYear=:year and "
								+ "		sumAvvikV.registrationMonth=:month and "
								+ "		sumAvvikV.sumAvvikVPK.productArea=:productArea "
								+ "	group by sumAvvikV.sumAvvikVPK.registrationYear,"
								+ "		sumAvvikV.registrationMonth,"
								+ "		sumAvvikV.sumAvvikVPK.productArea,"
								+ "		sumAvvikV.sumAvvikVPK.jobFunctionName, "
								+ "		sumAvvikV.sumAvvikVPK.closed "
								+ "	order by sumAvvikV.sumAvvikVPK.jobFunctionName,sumAvvikV.sumAvvikVPK.closed";

						return session.createQuery(sql).setParameter("year",
								year).setParameter("month", month)
								.setParameter("productArea", productArea)
								.list();
					}

				});
	}

}
