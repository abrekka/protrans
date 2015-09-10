package no.ugland.utransprod.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SalesmanGoal;
import no.ugland.utransprod.service.impl.SalesReportType;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelManager;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

import com.google.common.collect.Multimap;

public interface SalesVManager extends ExcelManager {
    String MANAGER_NAME = "salesVManager";

    List<SaleReportSum> groupSumCountyByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum, ProductArea productArea, Periode periode)
	    throws ProTransException;

    List<SaleReportSum> groupSumSalesmanByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum, ProductArea productArea, Periode periode)
	    throws ProTransException;

    Integer countByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum, ProductArea productArea, Periode periode) throws ProTransException;

    List<SaleReportData> getSaleReportByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum, ProductArea productArea, Periode periode,
	    String typeName) throws ProTransException;

    Map<ProbabilityEnum, Set<SaleReportData>> getSalesMap(ProductArea poductArea, Periode periode) throws ProTransException;

    Multimap<ProbabilityEnum, SaleReportSum> getAllReportData(ProductArea productArea, Periode periode) throws ProTransException;

    List<?> createReportMapAndGetData(SalesReportType reportType, Periode periode, ProductArea productArea) throws ProTransException;

    Collection<SalesmanGoal> getSalesGoalList(Periode periode) throws ProTransException;

    SaleReportSum getGroupSumByProbabilityProductAreaPeriod(ProbabilityEnum probability, ProductArea productArea, Periode periode)
	    throws ProTransException;
}
