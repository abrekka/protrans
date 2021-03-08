
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

public interface OrderSegmentNoVManager {
   List<SaleReportSum> sumByProductAreaConfirmPeriode(ProductArea var1, Periode var2);

   List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(ProductArea var1, Periode var2);

   List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(ProductArea var1, Periode var2);

   List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea var1, Periode var2);

   SaleReportSum groupSumByProductAreaConfirmPeriode(ProductArea var1, Periode var2);

   Integer countByProductAreaPeriode(ProductArea var1, Periode var2);
}
