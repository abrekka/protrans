
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.TakstolProbability90V;

public interface TakstolProbability90VDAO extends DAO<TakstolProbability90V> {
   List<TakstolProbability90V> findAllTakstoler();
}
