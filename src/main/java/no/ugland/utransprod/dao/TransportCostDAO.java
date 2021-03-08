
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.math.BigDecimal;
import java.util.List;
import no.ugland.utransprod.model.TransportCost;

public interface TransportCostDAO extends DAO<TransportCost> {
   List<TransportCost> findAll();

   TransportCost findByPostalCode(String var1);

   void setAllPostalCodesInvalid();

   void updatePriceForPostalCodes(String var1, String var2, BigDecimal var3, Integer var4, BigDecimal var5);

   String findCountyNameByPostalCode(String var1);
}
