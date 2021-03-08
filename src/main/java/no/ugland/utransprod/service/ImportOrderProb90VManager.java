
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.TakstolProbability90V;
import no.ugland.utransprod.util.excel.ExcelManager;

public interface ImportOrderProb90VManager extends ExcelManager {
   String MANAGER_NAME = "takstolProbability90VManager";

   List<TakstolProbability90V> findAllTakstoler();
}
