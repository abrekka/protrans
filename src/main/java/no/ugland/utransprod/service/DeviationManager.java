
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;

public interface DeviationManager extends OverviewManager<Deviation> {
   String MANAGER_NAME = "deviationManager";

   List<Deviation> findByJobFunction(JobFunction var1);

   void lazyLoad(Deviation var1, LazyLoadDeviationEnum[] var2);

   List<Deviation> findByManager(ApplicationUser var1);

   List<Deviation> findByOrder(Order var1);

   List<Deviation> findAllAssembly();

   void saveDeviation(Deviation var1);

   List<Deviation> findByResponsible(ApplicationUser var1);

   Deviation findById(Integer var1);
}
