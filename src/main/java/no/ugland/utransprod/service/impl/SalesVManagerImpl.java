package no.ugland.utransprod.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.SalesDataSnapshotDAO;
import no.ugland.utransprod.dao.SalesVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SalesmanGoal;
import no.ugland.utransprod.model.SalesmanGoal.PeriodeEnum;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SalesVManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.internal.Lists;
import com.google.inject.internal.Maps;

public class SalesVManagerImpl implements SalesVManager {
    private SalesVDAO dao;

    private SalesDataSnapshotDAO salesDataSnapshotDAO;

    private OrderManager orderManager;
    private BudgetManager budgetManager;

    private ProductAreaManager productAreaManager;
    private static Map<ProbabilityEnum, Set<SaleReportData>> reportMap = com.google.common.collect.Maps.newHashMap();

    private static Logger LOGGER = Logger.getLogger(SalesVManagerImpl.class);

    public final void setSalesDataSnapshotDAO(final SalesDataSnapshotDAO aDao) {
	this.salesDataSnapshotDAO = aDao;
    }

    public final void setSalesVDAO(final SalesVDAO aDao) {
	this.dao = aDao;
    }

    public final void setOrderManager(final OrderManager aOrderManager) {
	this.orderManager = aOrderManager;
    }

    public final List<SaleReportSum> groupSumCountyByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum, ProductArea productArea,
	    Periode periode) throws ProTransException {
	int currentWeek = Util.getCurrentWeek();
	int currentYear = Util.getCurrentYear();

	checkPeriode(periode, currentWeek, currentYear);
	// dersom det skal hentes data for gjeldende uke hentes livedata
	if (currentYear == periode.getYear() && currentWeek == periode.getWeek()) {
	    return dao.groupSumCountyByProbabilityProductAreaPeriode(probabilityEnum, productArea, periode);
	}
	return salesDataSnapshotDAO.groupSumCountyByProbabilityProductAreaPeriode(probabilityEnum, productArea, periode);

    }

    public final List<SaleReportSum> groupSumSalesmanByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum, ProductArea productArea,
	    Periode periode) throws ProTransException {
	int currentWeek = Util.getCurrentWeek();
	int currentYear = Util.getCurrentYear();

	checkPeriode(periode, currentWeek, currentYear);
	// dersom det skal hentes data for gjeldende uke hentes livedata
	if (currentYear == periode.getYear() && currentWeek == periode.getWeek()) {
	    return dao.groupSumSalesmanByProbabilityProductAreaPeriode(probabilityEnum, productArea, periode);
	}
	return salesDataSnapshotDAO.groupSumSalesmanByProbabilityProductAreaPeriode(probabilityEnum, productArea, periode);

    }

    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
	SalesReportType reportType = SalesReportType.valueOf(params.getExcelReportType().name());
	return createReportMapAndGetData(reportType, params.getPeriode(), params.getProductArea());
    }

    public List<?> createReportMapAndGetData(SalesReportType reportType, Periode periode, ProductArea productArea) throws ProTransException {

	if (reportType == SalesReportType.SALES_REPORT || reportType == SalesReportType.SALES_REPORT_SALESMAN) {
	    createReportMap(productArea, periode);
	}
	return reportType.getData(reportMap, orderManager, productArea, periode);
    }

    public Multimap<ProbabilityEnum, SaleReportSum> getAllReportData(ProductArea productArea, Periode periode) throws ProTransException {

	Multimap<ProbabilityEnum, SaleReportSum> salesMap = ArrayListMultimap.create();

	for (ProbabilityEnum probability : ProbabilityEnum.getAllSalesProbabilityEnums()) {
	    salesMap.put(probability, getGroupSumByProbabilityProductAreaPeriod(probability, productArea, periode));
	}

	salesMap.put(ProbabilityEnum.PROBABILITY_CONFRIM_ORDER, orderManager.groupSumByProductAreaConfirmPeriode(productArea, periode));

	/*
	 * createReportMap(productArea, periode); Multimap<ProbabilityEnum,
	 * SaleReportSum> salesMap = ArrayListMultimap .create(); for
	 * (SalesReportType reportType : SalesReportType.values()) { if
	 * (reportType.isPartOfSalesStatistic()) {
	 * salesMap.putAll(reportType.getProbability(), (List<SaleReportSum>)
	 * reportType.getData(reportMap, orderManager, productArea, periode)); }
	 * }
	 */

	return salesMap;
    }

    private void createReportMap(ProductArea productArea, Periode periode) throws ProTransException {

	getSalesMap(productArea, periode);

    }

    public Map<ProbabilityEnum, Set<SaleReportData>> getSalesMap(ProductArea productArea, Periode periode) throws ProTransException {
	reportMap.clear();
	LOGGER.debug("getSalesMap");
	// LOGGER.debug("Produktområde: "+productArea.getProductArea());
	LOGGER.debug("Periode: " + periode.getFormattetYearFromWeekToWeek());
	List<SaleReportData> salesDataList = getSaleReportByProductAreaPeriode(productArea, periode);
	LOGGER.debug("SalesDataList Antall: " + salesDataList.size());

	for (SaleReportData sale : salesDataList) {
	    // LOGGER.debug("getSalesMap - "+sale.getProbabilityEnum());
	    sale.getProbabilityEnum().addToSalesReportMap(reportMap, sale, periode);
	}
	return reportMap;
    }

    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) throws ProTransException {
	List<SalesmanGoal> dataList = getSalesGoalList(params.getPeriode());
	Map<Object, Object> dataMap = Maps.newHashMap();
	dataMap.put("Reportdata", dataList);
	return dataMap;
    }

    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
	SalesReportType reportType = SalesReportType.valueOf(params.getExcelReportType().name());
	return reportType.getInfoButtom(params.getProductArea(), params.getPeriode(), this, orderManager);
    }

    public String getInfoTop(ExcelReportSetting params) {
	SalesReportType reportType = SalesReportType.valueOf(params.getExcelReportType().name());
	return reportType.getInfoTop();
    }

    public Integer countByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum, ProductArea productArea, Periode periode)
	    throws ProTransException {
	int currentWeek = Util.getCurrentWeek();
	int currentYear = Util.getCurrentYear();

	checkPeriode(periode, currentWeek, currentYear);

	// dersom det skal hentes data for gjeldende uke hentes livedata
	if (currentYear == periode.getYear() && currentWeek == periode.getWeek()) {
	    return dao.countByProbabilityProductAreaPeriode(probabilityEnum, productArea, periode);
	}
	return salesDataSnapshotDAO.countByProbabilityProductAreaPeriode(probabilityEnum, productArea, periode);
    }

    private void checkPeriode(Periode periode, int currentWeek, int currentYear) throws ProTransException {
	if (periode.getYear() == currentYear
		&& ((periode.getWeek() == currentWeek && periode.getToWeek() != currentWeek) || (periode.getWeek() != currentWeek && periode
			.getToWeek() == currentWeek))) {
	    throw new ProTransException("Salgsdata for gjeldende uke må genereres for seg selv");
	}
    }

    public List<SaleReportData> getSaleReportByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum, ProductArea productArea,
	    Periode periode, String typeName) throws ProTransException {
	int currentWeek = Util.getCurrentWeek();
	int currentYear = Util.getCurrentYear();
	checkPeriode(periode, currentWeek, currentYear);

	// dersom det skal hentes data for gjeldende uke hentes livedata
	if (currentYear == periode.getYear() && currentWeek == periode.getWeek()) {
	    return dao.getSaleReportByProbabilityProductAreaPeriode(probabilityEnum, productArea, periode, typeName);
	}
	return salesDataSnapshotDAO.getSaleReportByProbabilityProductAreaPeriode(probabilityEnum, productArea, periode, typeName);
    }

    public List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea productArea, Periode periode) throws ProTransException {
	int currentWeek = Util.getCurrentWeek();
	int currentYear = Util.getCurrentYear();
	checkPeriode(periode, currentWeek, currentYear);

	// dersom det skal hentes data for gjeldende uke hentes livedata
	if (currentYear == periode.getYear() && currentWeek == periode.getWeek()) {
	    return dao.getSaleReportByProductAreaPeriode(productArea, periode);
	}
	return salesDataSnapshotDAO.getSaleReportByProductAreaPeriode(productArea, periode);
    }

    public CheckObject checkExcel(ExcelReportSetting params) {
	return null;
    }

    public List<SalesmanGoal> getSalesGoalList(Periode periode) throws ProTransException {
	Map<String, SalesmanGoal> salesmanGoals = Maps.newHashMap();

	for (PeriodeEnum periodeEnum : PeriodeEnum.values()) {
	    generateForProductAreaAndReportType(periodeEnum.convertPeriode(periode), salesmanGoals, periodeEnum);

	}

	setBudgetValues(salesmanGoals, periode);

	List<SalesmanGoal> salemanGoalList = Lists.newArrayList(salesmanGoals.values());
	Collections.sort(salemanGoalList);
	return salemanGoalList;
    }

    private void setBudgetValues(Map<String, SalesmanGoal> salesmanGoals, Periode periode) {
	for (SalesmanGoal goal : salesmanGoals.values()) {
	    Budget budget = budgetManager.findByYearAndSalesman(periode.getYear(), goal.getSalesman(), goal.getProductArea(), BudgetType.SALESMAN);
	    goal.setBudgetValue(budget.getBudgetValue());
	    goal.setBudgetValueOffer(budget.getBudgetValueOffer());
	}

    }

    private void generateForProductAreaAndReportType(Periode periode, Map<String, SalesmanGoal> salesmanGoals, PeriodeEnum periodeEnum)
	    throws ProTransException {
	for (ProductArea productArea : productAreaManager.findAll()) {
	    generateForEachReportType(periode, productArea, salesmanGoals, periodeEnum);
	}
    }

    @SuppressWarnings("unchecked")
    private void generateForEachReportType(Periode periode, ProductArea productArea, Map<String, SalesmanGoal> salesmanGoals, PeriodeEnum periodeEnum)
	    throws ProTransException {
	for (SalesReportType reportType : SalesReportType.getSalesGoalReportTypes()) {
	    // første uka i året
	    if (periode.getToWeek() == 0) {
		setAccumulatedLikeWeek(salesmanGoals);
	    } else {
		List<SaleReportSum> saleReportSumList = (List<SaleReportSum>) createReportMapAndGetData(reportType, periode, productArea);
		for (SaleReportSum saleReportSum : saleReportSumList) {
		    SalesmanGoal salesmanGoal = getSalesmanGoal(productArea, salesmanGoals, saleReportSum, periodeEnum);

		    if (salesmanGoal != null) {
			salesmanGoal.setSalesman(saleReportSum.getSalesman());
			salesmanGoal.setProductArea(productArea);

			salesmanGoal.setValue(reportType.getInfoTop(), saleReportSum.getSumOwnProduction(), periodeEnum);
			salesmanGoal.setDbValue(reportType.getInfoTop(), periodeEnum, saleReportSum.getSumDb());
			salesmanGoals.put(saleReportSum.getSalesman() + productArea.getProductArea(), salesmanGoal);
		    }
		}
	    }

	}
    }

    private void setAccumulatedLikeWeek(Map<String, SalesmanGoal> salesmanGoals) {
	for (SalesmanGoal goal : salesmanGoals.values()) {
	    goal.setOfferSumOwnProductionAccumulated(goal.getOfferSumOwnProduction());
	    goal.setOrderSumOwnProductionAccumulated(goal.getOrderSumOwnProduction());
	    goal.setConfirmedOrderSumOwnProductionAccumulated(goal.getConfirmedOrderSumOwnProduction());
	    goal.setDbAccumulated(goal.getDb());
	    goal.setOfferSumOwnProductionAccumulatedLastYear(goal.getOfferSumOwnProductionLastYear());
	    goal.setOrderSumOwnProductionAccumulatedLastYear(goal.getOrderSumOwnProductionLastYear());

	}

    }

    private SalesmanGoal getSalesmanGoal(ProductArea productArea, Map<String, SalesmanGoal> salesmanGoals, SaleReportSum saleReportSum,
	    PeriodeEnum periodeEnum) {
	SalesmanGoal salesmanGoal = salesmanGoals.get(saleReportSum.getSalesman() + productArea.getProductArea());
	salesmanGoal = salesmanGoal != null ? salesmanGoal : periodeEnum.salesmanShouldExist() ? null : new SalesmanGoal();
	return salesmanGoal;
    }

    public void setProductAreaManager(ProductAreaManager productAreaManager) {
	this.productAreaManager = productAreaManager;
    }

    public void setBudgetManager(BudgetManager budgetManager) {
	this.budgetManager = budgetManager;
    }

    public SaleReportSum getGroupSumByProbabilityProductAreaPeriod(ProbabilityEnum probability, ProductArea productArea, Periode periode)
	    throws ProTransException {
	int currentWeek = Util.getCurrentWeek();
	int currentYear = Util.getCurrentYear();
	checkPeriode(periode, currentWeek, currentYear);

	// dersom det skal hentes data for gjeldende uke hentes livedata
	if (currentYear == periode.getYear() && currentWeek == periode.getWeek()) {
	    return dao.getGroupSumByProbabilityProductAreaPeriod(probability, productArea, periode);
	}
	return salesDataSnapshotDAO.getGroupSumByProbabilityProductAreaPeriod(probability, productArea, periode);
    }

}
