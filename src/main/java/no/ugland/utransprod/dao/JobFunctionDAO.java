
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;

public interface JobFunctionDAO extends DAO<JobFunction> {
   void refreshObject(JobFunction var1);

   Boolean isFunctionManager(ApplicationUser var1);
}
