
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Date;
import java.util.List;
import no.ugland.utransprod.model.Info;

public interface InfoManager extends OverviewManager<Info> {
   String MANAGER_NAME = "infoManager";

   Info findByDate(Date var1);

   List<String> findListByDate(Date var1);
}
