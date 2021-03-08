
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.NokkelMonteringV;
import no.ugland.utransprod.util.YearWeek;

public interface NokkelMonteringVDAO extends DAO<NokkelMonteringV> {
   List<NokkelMonteringV> findBetweenYearWeek(YearWeek var1, YearWeek var2, String var3);

   NokkelMonteringV aggreagateYearWeek(YearWeek var1, String var2);
}
