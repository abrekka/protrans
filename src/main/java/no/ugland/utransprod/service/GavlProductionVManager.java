
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.Produceable;

public interface GavlProductionVManager extends IApplyListManager<Produceable> {
   List<Produceable> findAllApplyable();

   List<Produceable> findApplyableByOrderNr(String var1);

   void refresh(Produceable var1);

   List<Produceable> findApplyableByCustomerNr(Integer var1);
}
