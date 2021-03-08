
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.DelAlt;

public interface DelAltManager {
   String MANAGER_NAME = "delAltManager";

   List<DelAlt> finnForProdno(String var1);
}
