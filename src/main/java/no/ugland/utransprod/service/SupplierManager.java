
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.Supplier;

public interface SupplierManager extends OverviewManager<Supplier> {
   String MANAGER_NAME = "supplierManager";

   List<Supplier> findAll();

   List<Supplier> findByTypeName(String var1, String var2);

   Supplier findByName(String var1);

   List<Supplier> findActiveByTypeName(String var1, String var2);

   List<Supplier> findHavingAssembly(Integer var1, Integer var2, Integer var3);

   Boolean hasOrderCosts(Supplier var1);
}
