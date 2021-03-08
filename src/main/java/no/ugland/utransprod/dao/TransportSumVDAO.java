
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TransportSumV;

public interface TransportSumVDAO extends DAO<TransportSumV> {
   TransportSumV findYearAndWeek(Integer var1, Integer var2);

   TransportSumV findYearAndWeekByProductAreaGroup(Integer var1, Integer var2, ProductAreaGroup var3);
}
