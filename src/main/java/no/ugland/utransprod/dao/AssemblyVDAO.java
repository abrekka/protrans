
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.AssemblyV;

public interface AssemblyVDAO {
   List<AssemblyV> findByYear(int var1);
}
