
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.MainPackageV;

public interface MainPackageVManager {
   String MANAGER_NAME = "mainPackageVManager";

   List<MainPackageV> findAll();

   void refresh(MainPackageV var1);

   MainPackageV findByOrderNr(String var1);

   MainPackageV findByCustomerNr(Integer var1);
}
