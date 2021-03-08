
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ProductArea;

public interface ProductAreaManager {
   String MANAGER_NAME = "productAreaManager";

   List<ProductArea> findAll();

   ProductArea findByName(String var1);

   void removeProductArea(ProductArea var1);

   void saveProductArea(ProductArea var1);

   ProductArea getProductAreaForProductAreaNr(Integer var1, boolean var2);

   List<Integer> getProductAreaNrListFromAreaName(String var1);

   List<String> getAllNames();
}
