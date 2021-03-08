
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.MainPackageV;

public interface MainPackageVDAO extends DAO<MainPackageV> {
   void refresh(MainPackageV var1);

   MainPackageV findByOrderNr(String var1);

   MainPackageV findByCustomerNr(Integer var1);
}
