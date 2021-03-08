
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

public interface FaktureringVDAO extends DAO<FaktureringV> {
   List<FaktureringV> findAll();

   List<FaktureringV> findByOrderNr(String var1);

   List<FaktureringV> findByCustomerNr(Integer var1);

   void refresh(FaktureringV var1);

   List<FaktureringV> findByParams(ExcelReportSetting var1);

   List<FaktureringV> findByCustomerNrAndProductAreaGroup(Integer var1, ProductAreaGroup var2);

   List<FaktureringV> findByOrderNrAndProductAreaGroup(String var1, ProductAreaGroup var2);
}
