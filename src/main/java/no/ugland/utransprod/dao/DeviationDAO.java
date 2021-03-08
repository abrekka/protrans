
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;

public interface DeviationDAO extends DAO<Deviation> {
   void refreshObject(Deviation var1);

   List<Deviation> findByJobFunction(JobFunction var1);

   void lazyLoad(Deviation var1, LazyLoadDeviationEnum[] var2);

   List<Deviation> findByDeviation(Deviation var1);

   List<Deviation> findByManager(ApplicationUser var1);

   List<Deviation> findByOrder(Order var1);

   List<Deviation> findAllAssembly();

   List<Deviation> findByResponsible(ApplicationUser var1);

   Deviation findById(Integer var1);
}
