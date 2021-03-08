
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.Supplier;

public interface EmployeeDAO extends DAO<Employee> {
   void refreshObject(Employee var1);

   List<Employee> findBySupplier(Supplier var1);
}
