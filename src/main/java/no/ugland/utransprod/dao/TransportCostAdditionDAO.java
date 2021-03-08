
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.TransportCostAddition;

public interface TransportCostAdditionDAO extends DAO<TransportCostAddition> {
   TransportCostAddition findByDescription(String var1);

   List<TransportCostAddition> findTransportBasisAdditions(Integer var1);
}
