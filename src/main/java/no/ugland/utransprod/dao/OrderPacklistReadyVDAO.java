
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.OrderPacklistReadyV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

public interface OrderPacklistReadyVDAO extends DAO<OrderPacklistReadyV> {
   List<OrderPacklistReadyV> findByParams(ExcelReportSetting var1);
}
