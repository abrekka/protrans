
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;

public interface IgarasjenPackageVManager extends IApplyListManager<PackableListItem> {
   String MANAGER_NAME = "igarasjenPackageVManager";

   List<PackableListItem> findAllApplyable();

   List<PackableListItem> findApplyableByOrderNr(String var1);

   void refresh(IgarasjenPackageV var1);

   List<PackableListItem> findApplyableByCustomerNr(Integer var1);
}
