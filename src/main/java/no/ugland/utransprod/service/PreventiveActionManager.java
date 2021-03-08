
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.PreventiveAction;

public interface PreventiveActionManager extends OverviewManager<PreventiveAction> {
   String MANAGER_NAME = "preventiveActionManager";

   List<PreventiveAction> findAllOpenByFunctionAndCategory(JobFunction var1, FunctionCategory var2);
}
