
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.Cutting;

public interface CuttingManager extends Manager<Cutting> {
   String MANAGER_NAME = "cuttingManager";

   void saveCutting(Cutting var1, Boolean var2) throws ProTransException;

   Cutting findByProId(String var1);

   void removeCutting(Cutting var1);
}
