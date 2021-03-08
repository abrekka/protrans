
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.NokkelOkonomiV;
import no.ugland.utransprod.util.YearWeek;

public interface NokkelOkonomiVDAO extends DAO<NokkelOkonomiV> {
   List<NokkelOkonomiV> findBetweenYearWeek(YearWeek var1, YearWeek var2, String var3);

   NokkelOkonomiV aggreagateYearWeek(YearWeek var1, String var2);
}
