
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.ProductAreaGroup;

public interface IApplyListManager<T> {
   List<T> findAllApplyable();

   List<T> findApplyableByOrderNr(String var1);

   List<T> findApplyableByCustomerNr(Integer var1);

   void refresh(T var1);

   Ordln findOrdlnByOrderLine(Integer var1);

   List<T> findApplyableByOrderNrAndProductAreaGroup(String var1, ProductAreaGroup var2);

   List<T> findApplyableByCustomerNrAndProductAreaGroup(Integer var1, ProductAreaGroup var2);
}
