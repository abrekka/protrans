
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.PreventiveAction;

public interface PreventiveActionDAO extends DAO<PreventiveAction> {
   List<PreventiveAction> findAll();

   void refreshPreventiveAction(PreventiveAction var1);

   List<PreventiveAction> findAllOpenByFunctionAndCategory(JobFunction var1, FunctionCategory var2);

   List<PreventiveAction> findAllOpen();

   List<PreventiveAction> findAllClosedInMonth(Integer var1);
}
