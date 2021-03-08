
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.service.enums.LazyLoadEnum;

public interface Manager<E> {
   void lazyLoad(E var1, LazyLoadEnum[][] var2);
}
