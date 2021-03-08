
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.OrderReserveV;

public interface OrderReserveVManager {
   String MANAGER_NAME = "orderReserveVManager";

   List<OrderReserveV> findByProductArea(String var1);
}
