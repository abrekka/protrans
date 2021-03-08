
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.SutakPackageV;

public interface SutakPackageVManager extends IApplyListManager<PackableListItem> {
   String MANAGER_NAME = "sutakPackageVManager";

   List<PackableListItem> findAllApplyable();

   List<PackableListItem> findApplyableByOrderNr(String var1);

   void refresh(SutakPackageV var1);

   List<PackableListItem> findApplyableByCustomerNr(Integer var1);
}
