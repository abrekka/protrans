package no.ugland.utransprod.util;

import no.ugland.utransprod.model.OrderLine;

/**
 * Wrapper klasse for ordrelinjer som skal vises i dialog.
 * @author atle.brekka
 */
public class OrderLineWrapper {

    private OrderLine orderLine;

    /**
     * @param aOrderLine
     */
    public OrderLineWrapper(final OrderLine aOrderLine) {
        orderLine = aOrderLine;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return orderLine.toString() + ":"
                + Util.removeNoAttributes(orderLine.getAttributeInfo());
    }
}
