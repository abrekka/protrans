
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;

public interface IgarasjenPackageVDAO extends DAO<IgarasjenPackageV> {
   List<PackableListItem> findAll();

   List<PackableListItem> findByOrderNr(String var1);

   void refresh(IgarasjenPackageV var1);

   List<PackableListItem> findByCustomerNr(Integer var1);

   List<PackableListItem> findByCustomerNrProductAreaGroup(Integer var1, ProductAreaGroup var2);

   List<PackableListItem> findByOrderNrAndProductAreaGroup(String var1, ProductAreaGroup var2);
}
