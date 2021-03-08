
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ProductAreaGroup;

public interface ProductAreaGroupManager {
   String MANAGER_NAME = "productAreaGroupManager";

   List<ProductAreaGroup> findAll();

   ProductAreaGroup findByName(String var1);
}
