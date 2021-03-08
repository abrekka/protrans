
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.NotInvoicedV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

public interface NotInvoicedVDAO extends DAO<NotInvoicedV> {
   List<NotInvoicedV> findByParams(ExcelReportSetting var1);
}
