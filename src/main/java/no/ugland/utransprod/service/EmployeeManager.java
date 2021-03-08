
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.Supplier;

public interface EmployeeManager extends OverviewManager<Employee> {
   String MANAGER_NAME = "employeeManager";

   List<Employee> findAll();

   List<Employee> findBySupplier(Supplier var1);
}
