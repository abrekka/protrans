/**
 *
 */
package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.SalesVManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

public enum SalesReportType {

    SALES_REPORT_OFFER_STATISTIC("Tilbud", ProbabilityEnum.PROBABILITY_OFFER, true, false) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    return getSumData(reportMap, getProbability());
	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager)
		throws ProTransException {
	    // TODO Auto-generated method stub
	    return null;
	}
    },
    SALES_REPORT_ORDER_STATISTIC("Ordre", ProbabilityEnum.PROBABILITY_ORDER, true, false) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    return getSumData(reportMap, getProbability());
	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager)
		throws ProTransException {
	    // TODO Auto-generated method stub
	    return null;
	}
    },
    SALES_REPORT_CONFIRMED_ORDER_STATISTIC("Avrop", ProbabilityEnum.PROBABILITY_CONFRIM_ORDER, true, false) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    return orderManager.sumByProductAreaConfirmPeriode(productArea, periode);
	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager)
		throws ProTransException {
	    // TODO Auto-generated method stub
	    return null;
	}
    },
    SALES_REPORT("Tilbud", ProbabilityEnum.PROBABILITY_OFFER, false, false) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    List<SaleReportSum> list = getSumDataByCounty(reportMap, getProbability());
	    Collections.sort(list, new SaleReportSumDataCountyComparator());
	    return list;

	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager) {
	    return null;
	}
    },
    SALES_REPORT_SALESMAN("Tilbud", ProbabilityEnum.PROBABILITY_OFFER, false, true) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    List<SaleReportSum> list = getSumDataBySalesman(reportMap, getProbability());
	    Collections.sort(list, new SaleReportSumDataSalesmanComparator());
	    return list;
	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager) {
	    return null;
	}
    },
    SALES_REPORT_ORDER("Ordre", ProbabilityEnum.PROBABILITY_ORDER, false, false) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    List<SaleReportSum> list = getSumDataByCounty(reportMap, getProbability());
	    Collections.sort(list, new SaleReportSumDataCountyComparator());
	    return list;

	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager) {
	    return null;
	}
    },
    SALES_REPORT_ORDER_SALESMAN("Ordre", ProbabilityEnum.PROBABILITY_ORDER, false, true) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    List<SaleReportSum> list = getSumDataBySalesman(reportMap, getProbability());
	    Collections.sort(list, new SaleReportSumDataSalesmanComparator());
	    return list;
	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager) {
	    return null;
	}
    },
    SALES_REPORT_CONFIRMED_ORDER("Avrop", ProbabilityEnum.PROBABILITY_CONFRIM_ORDER, false, false) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    List<SaleReportSum> list = orderManager.groupSumCountyByProductAreaConfirmPeriode(productArea, periode);
	    Collections.sort(list, new SaleReportSumDataCountyComparator());
	    return list;
	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager)
		throws ProTransException {
	    return getInfoButtomConfirmed(productArea, periode, salesVManager, orderManager);
	}
    },
    SALES_REPORT_CONFIRMED_ORDER_SALESMAN("Avrop", ProbabilityEnum.PROBABILITY_CONFRIM_ORDER, false, true) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {
	    List<SaleReportSum> list = orderManager.groupSumSalesmanByProductAreaConfirmPeriode(productArea, periode);
	    Collections.sort(list, new SaleReportSumDataSalesmanComparator());
	    return list;

	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager)
		throws ProTransException {
	    return getInfoButtomConfirmed(productArea, periode, salesVManager, orderManager);
	}
    },
    SALES_REPORT_BASIS("Grunnlag", ProbabilityEnum.PROBABILITY_UNKNOWN, false, false) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {

	    Set<SaleReportData> salesDataSetOffer = reportMap.get(ProbabilityEnum.PROBABILITY_OFFER);
	    Set<SaleReportData> salesDataSetOrder = reportMap.get(ProbabilityEnum.PROBABILITY_ORDER);
	    List<SaleReportData> salesDataListConfirmOrder = orderManager.getSaleReportByProductAreaPeriode(productArea, periode);
	    List<SaleReportData> salesDataList = new ArrayList<SaleReportData>();
	    boolean success = salesDataSetOffer != null ? salesDataList.addAll(salesDataSetOffer) : false;
	    success = salesDataSetOrder != null ? salesDataList.addAll(salesDataSetOrder) : false;
	    success = salesDataListConfirmOrder != null ? salesDataList.addAll(salesDataListConfirmOrder) : false;
	    Collections.sort(salesDataList, new SaleReportDataCountyComparator());
	    return salesDataList;
	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager) {
	    return null;
	}
    },
    SALES_REPORT_BASIS_SALESMAN("Grunnlag", ProbabilityEnum.PROBABILITY_UNKNOWN, false, false) {
	@Override
	public List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
		Periode periode) {

	    Set<SaleReportData> salesDataSetOffer = reportMap.get(ProbabilityEnum.PROBABILITY_OFFER);
	    Set<SaleReportData> salesDataSetOrder = reportMap.get(ProbabilityEnum.PROBABILITY_ORDER);
	    List<SaleReportData> salesDataListConfirmOrder = orderManager.getSaleReportByProductAreaPeriode(productArea, periode);
	    List<SaleReportData> salesDataList = new ArrayList<SaleReportData>();
	    boolean success = salesDataSetOffer != null ? salesDataList.addAll(salesDataSetOffer) : false;
	    success = salesDataSetOrder != null ? salesDataList.addAll(salesDataSetOrder) : false;
	    success = salesDataListConfirmOrder != null ? salesDataList.addAll(salesDataListConfirmOrder) : false;
	    Collections.sort(salesDataList, new SaleReportDataSalesmanComparator());
	    return salesDataList;
	}

	@Override
	public String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager) {
	    return null;
	}
    };
    private String infoTop;
    private ProbabilityEnum probability;
    private boolean partOfSalesStatistic;
    private boolean partOfSalesGoal;
    private static final List<SalesReportType> SALES_GOAL_REPORT_TYPES = Lists.newArrayList();
    private static Logger LOGGER = Logger.getLogger(SalesReportType.class);

    static {
	for (SalesReportType reportType : SalesReportType.values()) {
	    if (reportType.isPartOfSalesGoal()) {
		SALES_GOAL_REPORT_TYPES.add(reportType);
	    }
	}
    }

    private SalesReportType(String aInfoTop, ProbabilityEnum aProbablility, boolean isPartOfSalesStatistic, boolean isPartOfSalesGoal) {
	infoTop = aInfoTop;
	probability = aProbablility;
	partOfSalesStatistic = isPartOfSalesStatistic;
	partOfSalesGoal = isPartOfSalesGoal;
    }

    public boolean isPartOfSalesGoal() {
	return partOfSalesGoal;
    }

    public boolean isPartOfSalesStatistic() {
	return partOfSalesStatistic;
    }

    public ProbabilityEnum getProbability() {
	return probability;
    }

    private static List<SaleReportSum> getSumData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, ProbabilityEnum probabilityEnum) {
	Set<SaleReportData> salesReportData = reportMap.get(probabilityEnum);
	if (salesReportData != null) {
	    LOGGER.debug("Antall salesReportData: " + salesReportData.size());
	} else {
	    LOGGER.debug("Antall salesReportData: NULL");
	}
	LOGGER.debug("sannsynlighet : " + probabilityEnum);
	LOGGER.debug("reportMap antall : " + reportMap.size());

	List<SaleReportSum> list = Lists.newArrayList();
	if (salesReportData != null) {
	    SaleReportSum saleReportSum = new SaleReportSum();
	    for (SaleReportData sale : salesReportData) {
		saleReportSum.addOrderCount();
		saleReportSum.addSumOwnProduction(sale.getOwnProductionCost());
		saleReportSum.addSumTransport(sale.getTransportCost());
		saleReportSum.addSumAssembly(sale.getAssemblyCost());
		saleReportSum.addSumYesLines(sale.getYesLines());
		saleReportSum.addSumDb(sale.getDb());
		saleReportSum.setCountyName("Alle");
	    }
	    LOGGER.debug("Count : " + saleReportSum.getOrderCount());
	    LOGGER.debug("OwnProduction : " + saleReportSum.getSumOwnProduction());
	    list.add(saleReportSum);
	}
	return list;
    }

    private static List<SaleReportSum> getSumDataByCounty(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, ProbabilityEnum probabilityEnum) {
	Set<SaleReportData> salesReportData = reportMap.get(probabilityEnum);
	Map<String, SaleReportSum> salesMap = new Hashtable<String, SaleReportSum>();
	if (salesReportData != null) {

	    for (SaleReportData sale : salesReportData) {
		if (sale.getOrderNr().equalsIgnoreCase("99695")) {
		    System.out.println("test");
		}
		String countyName = sale.getCountyName() != null ? sale.getCountyName() : "";
		SaleReportSum saleReportSum = salesMap.get(countyName);
		saleReportSum = saleReportSum == null ? new SaleReportSum() : saleReportSum;
		saleReportSum.addOrderCount();
		saleReportSum.addSumOwnProduction(sale.getOwnProductionCost());
		saleReportSum.addSumTransport(sale.getTransportCost());
		saleReportSum.addSumAssembly(sale.getAssemblyCost());
		saleReportSum.addSumYesLines(sale.getYesLines());
		saleReportSum.addSumDb(sale.getDb());
		saleReportSum.setCountyName(countyName);
		salesMap.put(countyName, saleReportSum);
	    }
	}
	List<SaleReportSum> list = new ArrayList(salesMap.values());
	return list;
    }

    private static List<SaleReportSum> getSumDataBySalesman(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, ProbabilityEnum probabilityEnum) {
	Set<SaleReportData> salesReportData = reportMap.get(probabilityEnum);
	Map<String, SaleReportSum> salesMap = new Hashtable<String, SaleReportSum>();
	if (salesReportData != null) {

	    for (SaleReportData sale : salesReportData) {
		String salesman = sale.getSalesman() != null ? sale.getSalesman() : "";
		SaleReportSum saleReportSum = salesMap.get(salesman);
		saleReportSum = saleReportSum == null ? new SaleReportSum() : saleReportSum;
		saleReportSum.addOrderCount();
		saleReportSum.addSumOwnProduction(sale.getOwnProductionCost());
		saleReportSum.addSumTransport(sale.getTransportCost());
		saleReportSum.addSumAssembly(sale.getAssemblyCost());
		saleReportSum.addSumYesLines(sale.getYesLines());
		saleReportSum.addSumDb(sale.getDb());
		saleReportSum.setSalesman(salesman);
		salesMap.put(salesman, saleReportSum);
	    }
	}
	List<SaleReportSum> list = new ArrayList(salesMap.values());
	return list;
    }

    public abstract List<?> getData(Map<ProbabilityEnum, Set<SaleReportData>> reportMap, OrderManager orderManager, ProductArea productArea,
	    Periode periode);

    public abstract String getInfoButtom(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager)
	    throws ProTransException;

    public String getInfoTop() {
	return infoTop;
    }

    private static List<SaleReportData> getDataBasis(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager)
	    throws ProTransException {
	List<SaleReportData> salesDataList = salesVManager.getSaleReportByProbabilityProductAreaPeriode(ProbabilityEnum.PROBABILITY_OFFER,
		productArea, periode, "Tilbud");
	salesDataList.addAll(salesVManager.getSaleReportByProbabilityProductAreaPeriode(ProbabilityEnum.PROBABILITY_ORDER, productArea, periode,
		"Ordre"));
	salesDataList.addAll(orderManager.getSaleReportByProductAreaPeriode(productArea, periode));
	return salesDataList;
    }

    private static String getInfoButtomConfirmed(ProductArea productArea, Periode periode, SalesVManager salesVManager, OrderManager orderManager)
	    throws ProTransException {
	BigDecimal numberOfOffer = BigDecimal.valueOf(salesVManager.countByProbabilityProductAreaPeriode(ProbabilityEnum.PROBABILITY_OFFER,
		productArea, periode));
	BigDecimal numberOfOrder = BigDecimal.valueOf(salesVManager.countByProbabilityProductAreaPeriode(ProbabilityEnum.PROBABILITY_ORDER,
		productArea, periode));
	BigDecimal numberOfSales = BigDecimal.valueOf(orderManager.countByProductAreaPeriode(productArea, periode));

	BigDecimal percentageOrder = BigDecimal.valueOf(0);
	BigDecimal percentageSales = BigDecimal.valueOf(0);

	if (numberOfOffer.intValue() != 0) {
	    percentageOrder = numberOfOrder.divide(numberOfOffer, 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100));
	    percentageSales = numberOfSales.divide(numberOfOffer, 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100));
	}

	return String.format("prosent ordre iht tilbud %1$.2f   prosent avrop iht tilbud %2$.2f", percentageOrder, percentageSales);
    }

    public class SaleReportDataCountyComparator implements Comparator<SaleReportData> {

	public int compare(SaleReportData o1, SaleReportData o2) {
	    return new CompareToBuilder().append(o1.getType(), o2.getType()).toComparison();
	}

    }

    public class SaleReportSumDataCountyComparator implements Comparator<SaleReportSum> {

	public int compare(SaleReportSum o1, SaleReportSum o2) {
	    return new CompareToBuilder().append(o1.getCountyName(), o2.getCountyName()).toComparison();
	}

    }

    public class SaleReportSumDataSalesmanComparator implements Comparator<SaleReportSum> {

	public int compare(SaleReportSum o1, SaleReportSum o2) {
	    return new CompareToBuilder().append(o1.getSalesman(), o2.getSalesman()).toComparison();
	}

    }

    public class SaleReportDataSalesmanComparator implements Comparator<SaleReportData> {

	public int compare(SaleReportData o1, SaleReportData o2) {
	    return new CompareToBuilder().append(o1.getSalesman(), o2.getSalesman()).toComparison();
	}

    }

    public static List<SalesReportType> getSalesGoalReportTypes() {
	return SALES_GOAL_REPORT_TYPES;
    }
}