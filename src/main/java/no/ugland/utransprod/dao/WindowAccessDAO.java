
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.WindowAccess;

public interface WindowAccessDAO extends DAO<WindowAccess> {
   List<WindowAccess> findAllWithTableNames();
}
