
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ProductArea;

public interface ProductAreaDAO extends DAO<ProductArea> {
   ProductArea findByProductAreaNr(Integer var1);

   List<String> findAllNames();
}
