
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.Collection;
import java.util.List;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.DeviationV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

public interface DeviationVDAO extends DAO<DeviationV> {
   List<DeviationV> findByParams(ExcelReportSettingDeviation var1);

   Collection findByOrder(Order var1);

   Collection findByManager(ApplicationUser var1);

   DeviationV findByDeviationId(Integer var1);
}
