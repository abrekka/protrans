
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.UserType;

public interface UserTypeManager extends OverviewManager<UserType> {
   String MANAGER_NAME = "userTypeManager";

   int getNumberOfUsers(UserType var1);
}
