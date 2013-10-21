package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SalesDataDao<T> extends BaseDAOHibernate<T> {
	private String tableName;
	private String tableVarName;
	private String idColumnName;
	private static Logger LOGGER = Logger.getLogger(SalesDataDao.class);

	public SalesDataDao(Class clazz, String aTableName, String aIdColumnName) {
		super(clazz);
		idColumnName = aIdColumnName;
		tableName = aTableName;
		tableVarName = StringUtils.lowerCase(tableName);
	}

	@SuppressWarnings("unchecked")
	public final List<SaleReportSum> groupSumCountyByProbabilityProductAreaPeriode(
			final ProbabilityEnum probabilityEnum,
			final ProductArea productArea, final Periode periode) {
		return (List<SaleReportSum>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						Integer startDate = Util.convertDateToInt(periode
								.getStartDate());
						Integer endDate = Util.convertDateToInt(periode
								.getEndDate());
						String sql = "select new no.ugland.utransprod.util.report.SaleReportSum("
								+ "               count($TABLE_VAR_NAME$.saleId),"
								+ "               sum($TABLE_VAR_NAME$.ownProductionCost),"
								+ "               sum($TABLE_VAR_NAME$.transportCost),"
								+ "               sum($TABLE_VAR_NAME$.assemblyCost),"
								+ "               sum($TABLE_VAR_NAME$.yesLines),"
								+ "               sum($TABLE_VAR_NAME$.contributionMargin),"
								+ "               $TABLE_VAR_NAME$.countyName)"
								+ "   from $TABLE_NAME$ $TABLE_VAR_NAME$ "
								+ "   where   $TABLE_VAR_NAME$.productAreaNr in (:productAreaNr) and "
								+ "     $TABLE_VAR_NAME$.probability in(:probabilityList) and "
								+ "           $TABLE_VAR_NAME$."
								+ probabilityEnum.getDateString()
								+ " between :startDate and :endDate and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost is not null and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost > :ownProductionCostLimit"
								+ "   group by $TABLE_VAR_NAME$.countyName";
						sql = StringUtils.replace(sql, "$TABLE_NAME$",
								tableName);
						sql = StringUtils.replace(sql, "$TABLE_VAR_NAME$",
								tableName);
						return session
								.createQuery(sql)
								.setParameterList("probabilityList",
										probabilityEnum.getProbabilityList())
								.setParameterList("productAreaNr",
										productArea.getProductAreaNrList())
								.setParameter("startDate", startDate)
								.setParameter("endDate", endDate)
								.setParameter("ownProductionCostLimit",
										productArea.getOwnProductionCostLimit())
								.list();
					}

				});
	}

	@SuppressWarnings("unchecked")
	public final List<SaleReportSum> groupSumSalesmanByProbabilityProductAreaPeriode(
			final ProbabilityEnum probabilityEnum,
			final ProductArea productArea, final Periode periode) {
		return (List<SaleReportSum>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						Integer startDate = Util.convertDateToInt(periode
								.getStartDate());
						Integer endDate = Util.convertDateToInt(periode
								.getEndDate());
						String sql = "select new no.ugland.utransprod.util.report.SaleReportSum("
								+ "               count($TABLE_VAR_NAME$.saleId),"
								+ "               sum($TABLE_VAR_NAME$.ownProductionCost),"
								+ "               sum($TABLE_VAR_NAME$.transportCost),"
								+ "               sum($TABLE_VAR_NAME$.assemblyCost),"
								+ "               sum($TABLE_VAR_NAME$.yesLines),"
								+ "               sum($TABLE_VAR_NAME$.contributionMargin),"
								+ "               '',$TABLE_VAR_NAME$.salesman)"
								+ "   from $TABLE_NAME$ $TABLE_VAR_NAME$ "
								+ "   where   $TABLE_VAR_NAME$.productAreaNr in (:productAreaNr) and "
								+ "     $TABLE_VAR_NAME$.probability in(:probabilityList) and "
								+ "           $TABLE_VAR_NAME$."
								+ probabilityEnum.getDateString()
								+ " between :startDate and :endDate and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost is not null and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost > :ownProductionCostLimit"
								+ "   group by $TABLE_VAR_NAME$.salesman";
						sql = StringUtils.replace(sql, "$TABLE_NAME$",
								tableName);
						sql = StringUtils.replace(sql, "$TABLE_VAR_NAME$",
								tableName);
						return session
								.createQuery(sql)
								.setParameterList("probabilityList",
										probabilityEnum.getProbabilityList())
								.setParameterList("productAreaNr",
										productArea.getProductAreaNrList())
								.setParameter("startDate", startDate)
								.setParameter("endDate", endDate)
								.setParameter("ownProductionCostLimit",
										productArea.getOwnProductionCostLimit())
								.list();
					}

				});
	}

	public final Integer countByProbabilityProductAreaPeriode(
			final ProbabilityEnum probabilityEnum,
			final ProductArea productArea, final Periode periode) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(final Session session) {
						Integer startDate = Util.convertDateToInt(periode
								.getStartDate());
						Integer endDate = Util.convertDateToInt(periode
								.getEndDate());
						String sql = "select count($TABLE_VAR_NAME$.$ID_COLUMN_NAME$)"
								+ "   from $TABLE_NAME$ $TABLE_VAR_NAME$ "
								+ "   where   $TABLE_VAR_NAME$.productAreaNr in (:productAreaNr) and "
								+ "     $TABLE_VAR_NAME$.probability in(:probabilityList) and "
								+ "           $TABLE_VAR_NAME$."
								+ probabilityEnum.getDateString()
								+ " between :startDate and :endDate and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost is not null and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost > :ownProductionCostLimit";
						sql = StringUtils.replace(sql, "$TABLE_NAME$",
								tableName);
						sql = StringUtils.replace(sql, "$TABLE_VAR_NAME$",
								tableName);
						sql = StringUtils.replace(sql, "$ID_COLUMN_NAME$",
								idColumnName);
						List<Integer> countList = session
								.createQuery(sql)
								.setParameterList("probabilityList",
										probabilityEnum.getProbabilityList())
								.setParameterList("productAreaNr",
										productArea.getProductAreaNrList())
								.setParameter("startDate", startDate)
								.setParameter("endDate", endDate)
								.setParameter("ownProductionCostLimit",
										productArea.getOwnProductionCostLimit())
								.list();

						if (countList != null && countList.size() == 1) {
							return countList.get(0);
						}
						return 0;
					}

				});
	}

	@SuppressWarnings("unchecked")
	public final List<SaleReportData> getSaleReportByProbabilityProductAreaPeriode(
			final ProbabilityEnum probabilityEnum,
			final ProductArea productArea, final Periode periode,
			final String typeName) {
		return (List<SaleReportData>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						Integer startDate = Util.convertDateToInt(periode
								.getStartDate());
						Integer endDate = Util.convertDateToInt(periode
								.getEndDate());
						String sql = "select new no.ugland.utransprod.util.report.SaleReportData($TABLE_VAR_NAME$.probability,"
								+ "               $TABLE_VAR_NAME$.countyName,"
								+ "               $TABLE_VAR_NAME$.salesman,"
								+ "               $TABLE_VAR_NAME$.customerNr,"
								+ "               $TABLE_VAR_NAME$.customerName,"
								+ "               $TABLE_VAR_NAME$.orderNr,"
								+ "               $TABLE_VAR_NAME$.ownProductionCost,"
								+ "         $TABLE_VAR_NAME$.transportCost,"
								+ "       $TABLE_VAR_NAME$.assemblyCost,"
								+ "       $TABLE_VAR_NAME$.yesLines,"
								+ "       $TABLE_VAR_NAME$.contributionMargin,"
								+ "       $TABLE_VAR_NAME$.saledate,$TABLE_VAR_NAME$.registered,$TABLE_VAR_NAME$.orderDate)"
								+ "   from $TABLE_NAME$ $TABLE_VAR_NAME$ "
								+ "   where   $TABLE_VAR_NAME$.productAreaNr in (:productAreaNr) and "
								+ "           $TABLE_VAR_NAME$.saledate "
								+ " between :startDate and :endDate and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost is not null and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost > :ownProductionCostLimit";
						sql = StringUtils.replace(sql, "$TABLE_NAME$",
								tableName);
						sql = StringUtils.replace(sql, "$TABLE_VAR_NAME$",
								tableName);
						return session
								.createQuery(sql)
								.setParameterList("productAreaNr",
										productArea.getProductAreaNrList())
								.setParameter("startDate", startDate)
								.setParameter("endDate", endDate)
								.setParameter("ownProductionCostLimit",
										productArea.getOwnProductionCostLimit())
								.list();
					}

				});
	}

	public List<SaleReportData> getSaleReportByProductAreaPeriode(
			final ProductArea productArea, final Periode periode) {
		return (List<SaleReportData>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						Integer startDate = Util.convertDateToInt(periode
								.getStartDate());
						Integer endDate = Util.convertDateToInt(periode
								.getEndDate());
						String sql = "select new no.ugland.utransprod.util.report.SaleReportData($TABLE_VAR_NAME$.probability,"
								+ "               $TABLE_VAR_NAME$.countyName,"
								+ "               $TABLE_VAR_NAME$.salesman,"
								+ "               $TABLE_VAR_NAME$.customerNr,"
								+ "               $TABLE_VAR_NAME$.customerName,"
								+ "               $TABLE_VAR_NAME$.orderNr,"
								+ "               $TABLE_VAR_NAME$.ownProductionCost,"
								+ "         $TABLE_VAR_NAME$.transportCost,"
								+ "       $TABLE_VAR_NAME$.assemblyCost,"
								+ "       $TABLE_VAR_NAME$.yesLines,"
								+ "       $TABLE_VAR_NAME$.contributionMargin,"
								+ "       $TABLE_VAR_NAME$.saledate,$TABLE_VAR_NAME$.registered,$TABLE_VAR_NAME$.orderDate,$TABLE_VAR_NAME$.contributionMargin)"
								+ "   from $TABLE_NAME$ $TABLE_VAR_NAME$ "
								+ "   where   $TABLE_VAR_NAME$.productAreaNr in (:productAreaNr) and "
								+ "           $TABLE_VAR_NAME$.saledate "
								+ " between :startDate and :endDate and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost is not null and "
								+ "     $TABLE_VAR_NAME$.ownProductionCost > :ownProductionCostLimit";
						sql = StringUtils.replace(sql, "$TABLE_NAME$",
								tableName);
						sql = StringUtils.replace(sql, "$TABLE_VAR_NAME$",
								tableName);
						return session
								.createQuery(sql)
								.setParameterList("productAreaNr",
										productArea.getProductAreaNrList())
								.setParameter("startDate", startDate)
								.setParameter("endDate", endDate)
								.setParameter("ownProductionCostLimit",
										productArea.getOwnProductionCostLimit())
								.list();
					}

				});
	}

	public SaleReportSum getGroupSumByProbabilityProductAreaPeriod(
			final ProbabilityEnum probability, final ProductArea productArea,
			final Periode periode) {
		return (SaleReportSum) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						Integer startDate = Util.convertDateToInt(periode
								.getStartDate());
						Integer endDate = Util.convertDateToInt(periode
								.getEndDate());
						LOGGER.debug("startDate: " + startDate);
						LOGGER.debug("endDate: " + endDate);
						String sql = "select new no.ugland.utransprod.util.report.SaleReportSum("
								+ "               count(distinct sale.saleId),"
								+ "               sum(sale.ownProductionCost),"
								+ "               sum(sale.transportCost),"
								+ "               sum(sale.assemblyCost),"
								+ "               sum(sale.yesLines),"
								+ "               sum(sale.contributionMargin))"
								+ "   from $TABLE_NAME$ sale "
								+ "   where   sale.productAreaNr in (:productAreaNr) and "
								+ "     sale.probability not in(:probabilityList) and "
								+ "           sale."
								+ probability.getDateString()
								+ " between :startDate and :endDate and "
								+ "     sale.ownProductionCost is not null and "
								+ "     sale.ownProductionCost > :ownProductionCostLimit and "
								+ "sale.saledate =(select max(sale2.saledate) from $TABLE_NAME$ sale2 where sale2.saleId=sale.saleId)"
								+ "   group by sale.productAreaNr";
						sql = StringUtils.replace(sql, "$TABLE_NAME$",
								tableName);
						// sql = StringUtils.replace(sql, "$TABLE_VAR_NAME$",
						// tableName);
						List<SaleReportSum> list = session
								.createQuery(sql)
								.setParameterList("probabilityList",
										probability.getNotProbabilityList())
								.setParameterList("productAreaNr",
										productArea.getProductAreaNrList())
								.setParameter("startDate", startDate)
								.setParameter("endDate", endDate)
								.setParameter("ownProductionCostLimit",
										productArea.getOwnProductionCostLimit())
								.list();

						return list != null && list.size() == 1 ? list.get(0)
								: SaleReportSum.UNKNOWN;
					}

				});

	}
}
