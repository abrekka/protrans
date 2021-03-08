
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Supplier;

public interface AssemblyDAO extends DAO<Assembly> {
   void deleteAssemblies(Set<Assembly> var1);

   void refreshObject(Assembly var1);

   List<Assembly> findBySupplierYearWeek(Supplier var1, Integer var2, Integer var3);

   List<Assembly> findByYear(Integer var1);

   void oppdaterMontering(Integer var1, Date var2, String var3);
}
