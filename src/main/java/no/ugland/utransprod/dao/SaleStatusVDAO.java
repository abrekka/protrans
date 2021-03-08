
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusV;

public interface SaleStatusVDAO extends DAO<SaleStatusV> {
   List<SaleStatusV> findByProbabilitesAndProductArea(List<Integer> var1, ProductArea var2);
}
