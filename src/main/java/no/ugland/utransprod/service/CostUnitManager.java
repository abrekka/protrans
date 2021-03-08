
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.CostUnit;

public interface CostUnitManager extends OverviewManager<CostUnit> {
   String MANAGER_NAME = "costUnitManager";

   CostUnit findByName(String var1);
}
