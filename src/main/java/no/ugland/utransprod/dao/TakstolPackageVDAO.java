
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TakstolPackageV;

public interface TakstolPackageVDAO extends DAO<TakstolPackageV> {
   List<PackableListItem> findAll();

   List<PackableListItem> findByOrderNr(String var1);

   void refresh(TakstolPackageV var1);

   List<PackableListItem> findByCustomerNr(Integer var1);

   List<PackableListItem> findApplyableByOrderNrAndArticleName(String var1, String var2);

   List<PackableListItem> findByOrderNrAndProductAreaGroup(String var1, ProductAreaGroup var2);

   List<PackableListItem> findByCustomerNrAndProductAreaGroup(Integer var1, ProductAreaGroup var2);
}
