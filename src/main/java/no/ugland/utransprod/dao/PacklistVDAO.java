
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.ProductAreaGroup;

public interface PacklistVDAO extends DAO<PacklistV> {
   String DAO_NAME = "packlistVDAO";

   List<PacklistV> findAll();

   List<PacklistV> findByOrderNr(String var1);

   void refresh(PacklistV var1);

   List<PacklistV> findByCustomerNr(Integer var1);

   List<PacklistV> findByCustomerNrAndProductAreaGroup(Integer var1, ProductAreaGroup var2);

   List<PacklistV> findByOrderNrAndProductAreaGroup(String var1, ProductAreaGroup var2);
}
