
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.SumOrderReadyV;

public interface SumOrderReadyVDAO extends DAO<SumOrderReadyV> {
   SumOrderReadyV findByDate(String var1);

   SumOrderReadyV findSumByWeek(Integer var1, Integer var2);

   SumOrderReadyV findByDateAndProductAreaGroupName(String var1, String var2);

   SumOrderReadyV findSumByWeekAndProductAreaGroupName(Integer var1, Integer var2, String var3);
}
