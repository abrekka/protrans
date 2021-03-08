
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.NokkelTransportV;
import no.ugland.utransprod.util.YearWeek;

public interface NokkelTransportVDAO extends DAO<NokkelTransportV> {
   List<NokkelTransportV> findBetweenYearWeek(YearWeek var1, YearWeek var2, String var3);

   NokkelTransportV aggreagateYearWeek(YearWeek var1, String var2);
}
