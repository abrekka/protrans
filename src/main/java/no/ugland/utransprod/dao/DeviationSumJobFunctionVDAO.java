
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.DeviationSumJobFunctionV;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.ProductAreaGroup;

public interface DeviationSumJobFunctionVDAO extends DAO<DeviationSumJobFunctionV> {
   List<DeviationSumJobFunctionV> findByYearAndDeviationFunctionAndProductAreaGroup(Integer var1, JobFunction var2, ProductAreaGroup var3);
}
