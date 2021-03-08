
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.util.excel.ExcelManager;

public interface FaktureringVManager extends ExcelManager, IApplyListManager<FaktureringV> {
   String MANAGER_NAME = "faktureringVManager";

   List<FaktureringV> findAllApplyable();

   List<FaktureringV> findApplyableByOrderNr(String var1);

   List<FaktureringV> findApplyableByCustomerNr(Integer var1);

   void refresh(FaktureringV var1);

   FaktureringV findByOrderNr(String var1);
}
