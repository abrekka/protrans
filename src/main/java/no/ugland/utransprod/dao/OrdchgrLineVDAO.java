
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.OrdchgrLineV;

public interface OrdchgrLineVDAO extends DAO<OrdchgrLineV> {
   List<OrdchgrLineV> getLines(Integer var1, List<Integer> var2);
}
