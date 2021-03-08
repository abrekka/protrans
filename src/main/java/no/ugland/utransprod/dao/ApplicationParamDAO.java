
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ApplicationParam;

public interface ApplicationParamDAO extends DAO<ApplicationParam> {
   ApplicationParam findByName(String var1);

   void refreshObject(ApplicationParam var1);
}
