
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.NokkelSalgV;
import no.ugland.utransprod.util.YearWeek;

public interface NokkelSalgVDAO extends DAO<NokkelSalgV> {
   List<NokkelSalgV> findByYearWeek(Integer var1, Integer var2, String var3);

   List<NokkelSalgV> findBetweenYearWeek(YearWeek var1, YearWeek var2, String var3);

   NokkelSalgV aggreagateYearWeek(YearWeek var1, String var2);
}
