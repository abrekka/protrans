
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import com.google.common.collect.Multimap;
import java.util.List;
import no.ugland.utransprod.model.ApplicationParam;

public interface ApplicationParamManager extends OverviewManager<ApplicationParam> {
   String MANAGER_NAME = "applicationParamManager";

   String findByName(String var1);

   String findByNameSilent(String var1);

   Multimap<String, String> getColliSetup();

   List<ApplicationParam> findByExampleLike(ApplicationParam var1);

   void saveApplicationParam(ApplicationParam var1);

   ApplicationParam findParam(String var1);
}
