
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.DeviationSumV;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

public interface DeviationSumVDAO extends DAO<DeviationSumV> {
   List<DeviationSumV> findByParams(ExcelReportSettingDeviation var1);
}
