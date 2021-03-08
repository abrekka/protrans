
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.County;

public interface CountyManager {
   County load(String var1);

   void removeAll();

   void saveCounty(County var1);
}
