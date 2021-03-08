
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineV;

public interface OrdchgrHeadVManager {
   String MANAGER_NAME = "ordchgrHeadVManager";

   OrdchgrHeadV getHead(Integer var1);

   OrdchgrLineV getLine(Integer var1, Integer var2);

   List<OrdchgrLineV> getLines(Integer var1, List<Integer> var2);
}
