
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.OrderStatusNotSentV;
import no.ugland.utransprod.model.StatusOrdersNotSent;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

public interface OrderStatusNotSentVDAO extends DAO<OrderStatusNotSentV> {
   List<StatusOrdersNotSent> findByParams(ExcelReportSetting var1);
}
