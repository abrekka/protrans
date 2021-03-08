
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.ProTransException;

public interface OverviewManager<E> extends Manager<E> {
   List<E> findAll();

   List<E> findByObject(E var1);

   void removeObject(E var1);

   void saveObject(E var1) throws ProTransException;

   void refreshObject(E var1);

   E merge(E var1);
}
