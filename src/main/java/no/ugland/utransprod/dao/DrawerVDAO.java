
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.DrawerV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.DrawerGroup;

public interface DrawerVDAO extends DAO<DrawerV> {
   List<DrawerGroup> groupByProductAreaPeriode(List<Integer> var1, Periode var2);

   List<DrawerV> findByProductAreaPeriode(List<Integer> var1, Periode var2);

   List<Integer> getDocumentIdByPeriode(Periode var1);
}
