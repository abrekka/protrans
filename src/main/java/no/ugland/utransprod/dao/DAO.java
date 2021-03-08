
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.io.Serializable;
import java.util.List;
import no.ugland.utransprod.service.enums.LazyLoadEnum;

public interface DAO<E> {
   List<E> getObjects();

   List<E> getObjects(String var1);

   E getObject(Serializable var1);

   void saveObject(E var1);

   void removeObject(Serializable var1);

   List<E> findByExample(E var1);

   List<E> findByExampleLike(E var1);

   void removeAll();

   void refreshObject(Object var1, Serializable var2);

   void lazyLoad(Object var1, Serializable var2, LazyLoadEnum[][] var3);

   E merge(E var1);
}
