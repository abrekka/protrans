
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.util.YearWeek;

public interface NokkelProduksjonVDAO extends DAO<NokkelProduksjonV> {
   NokkelProduksjonV findByWeek(Integer var1, Integer var2);

   List<NokkelProduksjonV> findByYearWeekProductArea(Integer var1, Integer var2, Integer var3, String var4);

   List<NokkelProduksjonV> findBetweenYearWeek(YearWeek var1, YearWeek var2, String var3);

   NokkelProduksjonV aggreagateYearWeek(YearWeek var1, String var2);

   List<NokkelProduksjonV> findByYearWeekProductAreaGroup(Integer var1, Integer var2, Integer var3, String var4);
}
