
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ApplicationUser;

public interface ApplicationUserDAO extends DAO<ApplicationUser> {
   List<ApplicationUser> findAllNotGroup();

   void refreshObject(ApplicationUser var1);

   List<ApplicationUser> findByUserNameAndPassword(String var1, String var2);

   List<String> findAllPackers();

   ApplicationUser findByFullName(String var1);
}
