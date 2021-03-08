
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.FrontProductionV;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;

public interface FrontProductionVDAO extends DAO<FrontProductionV> {
   List<Produceable> findAll();

   List<Produceable> findByOrderNr(String var1);

   void refresh(Produceable var1);

   List<Produceable> findByCustomerNr(Integer var1);

   List<Produceable> findByCustomerNrProductAreaGroup(Integer var1, ProductAreaGroup var2);

   List<Produceable> findByOrderNrAndProductAreaGroup(String var1, ProductAreaGroup var2);
}
