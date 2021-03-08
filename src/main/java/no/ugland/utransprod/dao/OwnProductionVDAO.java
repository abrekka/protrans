
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.OwnProductionV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

public interface OwnProductionVDAO extends DAO<OwnProductionV> {
   List<OwnProductionV> findByParams(ExcelReportSetting var1);

   List<OwnProductionV> findPacklistReady(String var1);

   List<OwnProductionV> findPacklistNotReady(String var1, String var2);
}
