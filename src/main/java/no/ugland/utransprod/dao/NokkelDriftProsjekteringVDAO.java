
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.NokkelDriftProsjekteringV;
import no.ugland.utransprod.util.YearWeek;

public interface NokkelDriftProsjekteringVDAO extends DAO<NokkelDriftProsjekteringV> {
   List<NokkelDriftProsjekteringV> findBetweenYearWeek(YearWeek var1, YearWeek var2, String var3);

   NokkelDriftProsjekteringV aggreagateYearWeek(YearWeek var1, String var2);
}
