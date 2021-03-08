
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;

public interface JobFunctionManager extends OverviewManager<JobFunction> {
   String MANAGER_NAME = "jobFunctionManager";

   List<JobFunction> findAll();

   Boolean isFunctionManager(ApplicationUser var1);
}
