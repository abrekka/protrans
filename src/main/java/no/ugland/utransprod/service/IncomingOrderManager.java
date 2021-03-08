
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Udsalesmall;

public interface IncomingOrderManager extends OverviewManager<Order> {
   String MANAGER_NAME = "incomingOrderManager";

   List<Order> findByOrderNr(String var1);

   void setCosts(Order var1) throws ProTransException;

   void setOrderLines(Order var1, ManagerRepository var2);

   void setCustomerCost(Order var1, Udsalesmall var2);
}
