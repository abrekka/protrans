
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.OrderReserveV;

public interface OrderReserveVDAO extends DAO<OrderReserveV> {
   List<OrderReserveV> findByProductArea(String var1);
}
