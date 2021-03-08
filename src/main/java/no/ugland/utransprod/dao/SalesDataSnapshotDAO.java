
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SalesDataSnapshot;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

public interface SalesDataSnapshotDAO extends DAO<SalesDataSnapshot> {
   List<SaleReportSum> groupSumCountyByProbabilityProductAreaPeriode(ProbabilityEnum var1, ProductArea var2, Periode var3);

   List<SaleReportSum> groupSumSalesmanByProbabilityProductAreaPeriode(ProbabilityEnum var1, ProductArea var2, Periode var3);

   Integer countByProbabilityProductAreaPeriode(ProbabilityEnum var1, ProductArea var2, Periode var3);

   List<SaleReportData> getSaleReportByProbabilityProductAreaPeriode(ProbabilityEnum var1, ProductArea var2, Periode var3, String var4);

   List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea var1, Periode var2);

   SaleReportSum getGroupSumByProbabilityProductAreaPeriod(ProbabilityEnum var1, ProductArea var2, Periode var3);
}
