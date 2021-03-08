
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.CostType;

public interface CostTypeDAO extends DAO<CostType> {
   List<CostType> findAll();

   void refreshObject(CostType var1);

   CostType findByName(String var1);
}
