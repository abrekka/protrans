
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import java.util.Date;
import java.util.List;
import java.util.Set;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;

public interface Packable {
   List<Colli> getColliList();

   List<OrderLine> getOrderLineList();

   void addColli(Colli var1);

   void setColliesDone(Integer var1);

   Date getOrderReady();

   Date getOrderReadyTross();

   Date getOrderReadyPack();

   Date getOrderReadyWall();

   Date getOrderComplete();

   boolean removeColli(Colli var1);

   Transportable getTransportable();

   List<OrderLine> getOwnOrderLines();

   Integer getProbability();

   Order getOrder();

   PostShipment getPostShipment();

   void setDefaultColliesGenerated(Integer var1);

   String getManagerName();

   Integer getDefaultColliesGenerated();

   Set<Colli> getCollies();
}
