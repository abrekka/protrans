
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Collection;
import java.util.List;
import no.ugland.utransprod.model.DeviationStatus;

public interface DeviationStatusManager extends OverviewManager<DeviationStatus> {
   String MANAGER_NAME = "deviationStatusManager";

   List<DeviationStatus> findAll();

   List<DeviationStatus> findAllNotForManager();

   Integer countUsedByDeviation(DeviationStatus var1);

   List<DeviationStatus> findAllForDeviation();

   Collection<DeviationStatus> findAllForAccident();

   Collection<DeviationStatus> findAllNotForManagerForAccident();

   Collection<DeviationStatus> findAllNotForManagerForDeviation();
}
