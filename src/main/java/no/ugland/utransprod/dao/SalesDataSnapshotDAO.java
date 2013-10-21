package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SalesDataSnapshot;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

public interface SalesDataSnapshotDAO extends DAO<SalesDataSnapshot> {
    List<SaleReportSum> groupSumCountyByProbabilityProductAreaPeriode(
            ProbabilityEnum probabilityEnum, ProductArea productArea, Periode periode);
    List<SaleReportSum> groupSumSalesmanByProbabilityProductAreaPeriode(
            ProbabilityEnum probabilityEnum, ProductArea productArea, Periode periode);

    Integer countByProbabilityProductAreaPeriode(ProbabilityEnum probabilityEnum,
            ProductArea productArea, Periode periode);

    List<SaleReportData> getSaleReportByProbabilityProductAreaPeriode(
            ProbabilityEnum probabilityEnum, ProductArea productArea, Periode periode,
            String typeName);
	List<SaleReportData> getSaleReportByProductAreaPeriode(
			ProductArea productArea, Periode periode);
	SaleReportSum getGroupSumByProbabilityProductAreaPeriod(
			ProbabilityEnum probability, ProductArea productArea,
			Periode periode);
}
