
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.enums.LazyLoadEnum;

public interface ExternalOrderManager extends OverviewManager<ExternalOrder> {
   String MANAGER_NAME = "externalOrderManager";

   List<ExternalOrder> findByOrder(Order var1);

   void lazyLoadExternalOrderLine(ExternalOrderLine var1, LazyLoadEnum[][] var2);
}
