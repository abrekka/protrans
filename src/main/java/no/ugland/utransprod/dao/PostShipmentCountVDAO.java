
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.PostShipmentCountSum;
import no.ugland.utransprod.model.PostShipmentCountV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

public interface PostShipmentCountVDAO extends DAO<PostShipmentCountV> {
   List<PostShipmentCountSum> findByParams(ExcelReportSetting var1);
}
