
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.Supplier;

public interface OrderCostDAO extends DAO<OrderCost> {
   List<OrderCost> findBySupplier(Supplier var1);
}
