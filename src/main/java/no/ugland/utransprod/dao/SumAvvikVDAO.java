
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.SumAvvikV;

public interface SumAvvikVDAO extends DAO<SumAvvikV> {
   List<SumAvvikV> findByProductAreaYearAndMonth(Integer var1, Integer var2, String var3);

   List<SumAvvikV> findSumByProductAreaYearAndMonth(Integer var1, Integer var2, String var3);

   List<SumAvvikV> findByProductAreaYearAndMonthWithClosed(Integer var1, Integer var2, String var3);
}
