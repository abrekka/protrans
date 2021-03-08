
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.GulvsponPackageV;
import no.ugland.utransprod.model.PackableListItem;

public interface GulvsponPackageVManager extends IApplyListManager<PackableListItem> {
   String MANAGER_NAME = "gulvsponPackageVManager";

   List<PackableListItem> findAllApplyable();

   List<PackableListItem> findApplyableByOrderNr(String var1);

   void refresh(GulvsponPackageV var1);

   List<PackableListItem> findApplyableByCustomerNr(Integer var1);
}
