
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TakstolProductionV;

public interface TakstolProductionVDAO extends DAO<TakstolProductionV> {
   List<Produceable> findAll();

   List<Produceable> findByOrderNr(String var1);

   void refresh(Produceable var1);

   List<Produceable> findByCustomerNr(Integer var1);

   List<Produceable> findByOrderNrAndArticleName(String var1, String var2);

   List<Produceable> findByOrderNrAndProductAreaGroup(String var1, ProductAreaGroup var2);

   List<Produceable> findByCustomerNrAndProductAreaGroup(Integer var1, ProductAreaGroup var2);
}
