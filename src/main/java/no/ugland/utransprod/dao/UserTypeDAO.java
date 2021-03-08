
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.UserType;

public interface UserTypeDAO extends DAO<UserType> {
   int getNumberOfUsers(UserType var1);

   void refresh(UserType var1);
}
