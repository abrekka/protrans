
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.Customer;

public interface CustomerManager extends OverviewManager<Customer> {
   String MANAGER_NAME = "customerManager";

   void saveCustomer(Customer var1);

   Customer findByCustomerNr(Integer var1);

   void removeAll();

   void removeCustomer(Customer var1);

   List<Customer> findAll();

   List<Customer> findByCustomer(Customer var1);
}
