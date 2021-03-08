
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.CostType;

public interface CostTypeManager extends OverviewManager<CostType> {
   String MANAGER_NAME = "costTypeManager";

   CostType findByName(String var1);
}
