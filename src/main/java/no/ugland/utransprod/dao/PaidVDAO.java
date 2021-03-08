
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.PaidV;
import no.ugland.utransprod.model.ProductAreaGroup;

public interface PaidVDAO extends DAO<PaidV> {
   List<PaidV> findAll();

   List<PaidV> findByOrderNr(String var1);

   List<PaidV> findByCustomerNr(Integer var1);

   void refresh(PaidV var1);

   List<PaidV> findByCustomerNrAndProductAreaGroup(Integer var1, ProductAreaGroup var2);

   List<PaidV> findByOrderNrAndProductAreaGroup(String var1, ProductAreaGroup var2);
}
