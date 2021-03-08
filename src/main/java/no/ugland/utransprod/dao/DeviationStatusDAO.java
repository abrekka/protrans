
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.Collection;
import java.util.List;
import no.ugland.utransprod.model.DeviationStatus;

public interface DeviationStatusDAO extends DAO<DeviationStatus> {
   void refreshObject(DeviationStatus var1);

   List<DeviationStatus> findAllNotForManager();

   Integer countUsedByDeviation(DeviationStatus var1);

   List<DeviationStatus> findAllForDeviation();

   Collection<DeviationStatus> findAllNotForManagerForAccident();

   Collection<DeviationStatus> findAllNotForManagerForDeviation();

   Collection<DeviationStatus> findAllForAccident();
}
