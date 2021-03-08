
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.CostUnit;

public interface CostUnitDAO extends DAO<CostUnit> {
   List<CostUnit> findAll();

   void refreshObject(CostUnit var1);

   CostUnit findByName(String var1);
}
