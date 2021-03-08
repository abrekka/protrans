
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;

public interface TransportLetterObject {
   Order getLetterOrder();

   String getName();

   String getDetails();

   String getTypeName();

   Boolean isNotPostShipment();

   PostShipment getPostShipment();

   Integer getNumberOf();

   Integer getLenght();

   Integer getWidht();

   Integer getHeight();
}
