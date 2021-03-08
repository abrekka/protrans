
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProcentDone;

public interface ProcentDoneManager extends OverviewManager<ProcentDone> {
   String MANAGER_NAME = "procentDoneManager";

   ProcentDone findByYearWeekOrder(Integer var1, Integer var2, Order var3);
}
