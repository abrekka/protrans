
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.IntelleV;

public interface IntelleVManager {
   String MANAGER_NAME = "intelleVManager";

   IntelleV findByOrdreNr(String var1);
}
