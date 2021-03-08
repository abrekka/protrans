
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.util.YearWeek;

public interface NokkelVManager<T> {
   List<T> findBetweenYearWeek(YearWeek var1, YearWeek var2, String var3);

   T aggreagateYearWeek(YearWeek var1, String var2);
}
