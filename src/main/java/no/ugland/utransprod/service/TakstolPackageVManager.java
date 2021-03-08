
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.TakstolPackageV;

public interface TakstolPackageVManager extends IApplyListManager<PackableListItem>, TakstolVManager {
   String MANAGER_NAME = "takstolPackageVManager";

   void refresh(TakstolPackageV var1);

   List<PackableListItem> findApplyableByOrderNrAndArticleName(String var1, String var2);
}
