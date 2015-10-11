package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

public interface OrderSegmentNoVDAO {

    List<SaleReportSum> sumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode);

    List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(ProductArea productArea, Periode periode);

    List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(ProductArea productArea, Periode periode);

    List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea productArea, Periode periode);

    SaleReportSum groupSumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode);

    Integer countByProductAreaPeriode(ProductArea productArea, Periode periode);

}
