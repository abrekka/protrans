
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;

public interface ColliManager extends OverviewManager<Colli> {
   String MANAGER_NAME = "colliManager";

   Colli findByNameAndOrder(String var1, Order var2);

   void saveColli(Colli var1);

   Colli findByNameAndPostShipment(String var1, PostShipment var2);

   void lazyLoadAll(Colli var1);
}
