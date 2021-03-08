
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.WindowAccess;

public interface WindowAccessManager {
   List<WindowAccess> findAll();

   List<WindowAccess> findAllWithTableNames();
}
