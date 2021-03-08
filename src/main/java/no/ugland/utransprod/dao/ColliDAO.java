
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;

public interface ColliDAO extends DAO<Colli> {
   Colli findByNameAndOrder(String var1, Order var2);

   void refreshObject(Colli var1);

   Colli findByNameAndPostShipment(String var1, PostShipment var2);

   void lazyLoadAll(Colli var1);

   void oppdaterTransportId(Colli var1, Integer var2);
}
