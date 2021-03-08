
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Area;

public interface AreaManager {
   Area load(String var1);

   void removeAll();

   void saveArea(Area var1);

   Area findByAreaCode(String var1);
}
