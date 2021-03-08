
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.Collection;
import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.util.Periode;

public interface TakstoltegnerVDAO {
   Collection<TakstoltegnerV> findByPeriode(Periode var1);
}
