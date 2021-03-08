
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.SalesStatistic;

public interface SalesWebManager {
   SalesStatistic generateSalesStatistics(Integer var1, Integer var2, String var3) throws ProTransException;

   List<String> getProductAreaNames();
}
