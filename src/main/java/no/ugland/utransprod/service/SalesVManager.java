
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import com.google.common.collect.Multimap;
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

public interface SalesVManager extends ExcelManager {
   String MANAGER_NAME = "salesVManager";

   List<SaleReportSum> groupSumCountyByProbabilityProductAreaPeriode(ProbabilityEnum var1, ProductArea var2, Periode var3) throws ProTransException;

   List<SaleReportSum> groupSumSalesmanByProbabilityProductAreaPeriode(ProbabilityEnum var1, ProductArea var2, Periode var3) throws ProTransException;

   Integer countByProbabilityProductAreaPeriode(ProbabilityEnum var1, ProductArea var2, Periode var3) throws ProTransException;

   List<SaleReportData> getSaleReportByProbabilityProductAreaPeriode(ProbabilityEnum var1, ProductArea var2, Periode var3, String var4) throws ProTransException;

   Map<ProbabilityEnum, Set<SaleReportData>> getSalesMap(ProductArea var1, Periode var2) throws ProTransException;

   Multimap<ProbabilityEnum, SaleReportSum> getAllReportData(ProductArea var1, Periode var2) throws ProTransException;

   List<?> createReportMapAndGetData(SalesReportType var1, Periode var2, ProductArea var3) throws ProTransException;

   Collection<SalesmanGoal> getSalesGoalList(Periode var1) throws ProTransException;

   SaleReportSum getGroupSumByProbabilityProductAreaPeriod(ProbabilityEnum var1, ProductArea var2, Periode var3) throws ProTransException;
}
