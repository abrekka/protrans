
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.EmployeeType;

public interface EmployeeTypeManager extends OverviewManager<EmployeeType> {
   String MANAGER_NAME = "employeeTypeManager";

   List<EmployeeType> findAll();
}
