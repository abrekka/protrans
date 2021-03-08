
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Date;
import no.ugland.utransprod.model.SumOrderReadyV;

public interface SumOrderReadyVManager {
   String MANAGER_NAME = "sumOrderReadyVManager";

   SumOrderReadyV findByDate(Date var1);

   SumOrderReadyV findSumByWeek(Integer var1, Integer var2);

   SumOrderReadyV findByDateAndProductAreaGroupName(Date var1);

   SumOrderReadyV findSumByWeekAndProductAreaGroupName(Integer var1, Integer var2);
}
