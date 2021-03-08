
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TransportSumV;

public interface TransportSumVManager {
   String MANAGER_NAME = "transportSumVManager";

   TransportSumV findYearAndWeek(Integer var1, Integer var2);

   TransportSumV findYearAndWeekByProductAreaGroup(Integer var1, Integer var2, ProductAreaGroup var3);
}
