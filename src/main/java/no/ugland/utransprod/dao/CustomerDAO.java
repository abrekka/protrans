
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.Customer;

public interface CustomerDAO extends DAO<Customer> {
   void removeAll();

   List<Customer> findAll();

   void refreshObject(Customer var1);

   Customer findByCustomerNr(Integer var1);
}
