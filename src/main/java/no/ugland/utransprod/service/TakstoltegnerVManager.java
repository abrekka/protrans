
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Collection;
import java.util.Map;
import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.model.TakstoltegnerVSum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelManager;

public interface TakstoltegnerVManager extends ExcelManager {
   String MANAGER_NAME = "takstoltegnerVManager";

   Map<TakstoltegnerVSum, Collection<TakstoltegnerV>> findByPeriode(Periode var1);
}
