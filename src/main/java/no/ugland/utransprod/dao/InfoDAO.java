
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.Date;
import java.util.List;
import no.ugland.utransprod.model.Info;

public interface InfoDAO extends DAO<Info> {
   List<Info> findAll();

   void refreshObject(Info var1);

   List<Info> findByDate(Date var1);
}
