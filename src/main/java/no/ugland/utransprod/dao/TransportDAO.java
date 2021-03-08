
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
import no.ugland.utransprod.util.Periode;

public interface TransportDAO extends DAO<Transport> {
   void refreshObject(Transport var1);

   void lazyLoadTransport(Transport var1, LazyLoadTransportEnum[] var2);

   List<Transport> findAll();

   List<Transport> findByYearAndWeek(Integer var1, Integer var2);

   List<Transport> findBetweenYearAndWeek(Integer var1, Integer var2, Integer var3, String[] var4);

   List<Transport> findNewTransports();

   List<Transport> findByYearAndWeekAndProductAreaGroup(Integer var1, Integer var2, boolean var3, String var4);

   List<Transport> findSentInPeriode(Periode var1);

   List<Transport> findInPeriode(Periode var1, String var2);
}
