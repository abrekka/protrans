
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.model.TakstolInfoV;

public interface TakstolInfoVDAO extends DAO<TakstolInfoV> {
   List<TakstolInfoV> findByOrderNr(String var1);

   List<Object[]> summerArbeidsinnsats(String var1, String var2, TransportConstraintEnum var3, String var4);

   List<Object[]> getBeregnetTidForOrdre(String var1, String var2, TransportConstraintEnum var3, String var4);
}
