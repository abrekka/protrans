
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ApplicationUser;

public interface ApplicationUserManager extends OverviewManager<ApplicationUser> {
   String MANAGER_NAME = "applicationUserManager";

   ApplicationUser login(String var1, String var2);

   List<ApplicationUser> findAll();

   List<ApplicationUser> findAllNotGroup();

   List<String> findAllPackers();

   Boolean isUserFunctionManager(ApplicationUser var1);

   List<String> findAllNamesNotGroup();

   void saveApplicationUser(ApplicationUser var1);
}
