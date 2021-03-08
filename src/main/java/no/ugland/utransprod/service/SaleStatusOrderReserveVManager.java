
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusOrderReserveV;

public interface SaleStatusOrderReserveVManager {
   String MANAGER_NAME = "saleStatusOrderReserveVManager";

   SaleStatusOrderReserveV findByProductArea(ProductArea var1);
}
