
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Date;
import java.util.List;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Supplier;

public interface AssemblyManager extends OverviewManager<Assembly> {
   String MANAGER_NAME = "assemblyManager";

   List<Assembly> findBySupplierYearWeek(Supplier var1, Integer var2, Integer var3);

   void saveAssembly(Assembly var1);

   List<Assembly> findByYear(Integer var1);

   Assembly get(Integer var1);

   void oppdaterMontering(Integer var1, Date var2, String var3);
}
