
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.TakstolProductionV;

public interface TakstolProductionVManager extends IApplyListManager<Produceable>, TakstolVManager {
   String MANAGER_NAME = "takstolProductionVManager";

   List<Produceable> findApplyableByOrderNrAndArticleName(String var1, String var2);

   List<TakstolProductionV> findByOrderNr(String var1);
}
