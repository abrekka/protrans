
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Collection;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.DeviationV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.util.excel.ExcelManager;

public interface DeviationVManager extends ExcelManager, OverviewManager<DeviationV> {
   String MANAGER_NAME = "deviationVManager";

   Collection findByOrder(Order var1);

   Collection findByManager(ApplicationUser var1);

   DeviationV findByDeviationId(Integer var1);
}
