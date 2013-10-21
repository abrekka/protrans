package no.ugland.utransprod.util;

import java.util.Comparator;

import no.ugland.utransprod.model.Order;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * Komparatorklasse for ordre.
 * @author atle.brekka
 */
public class OrderTransportComparator implements Comparator<Order> {
    /**
     * @param order1
     * @param order2
     * @return 1 dersom større, -1 dersom mindre og 0 dersom lik
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public final int compare(final Order order1, final Order order2) {
        if (order1.getTransport() != null && order2.getTransport() != null) {
            return new CompareToBuilder().append(
                    order1.getTransport().getLoadingDate(),
                    order2.getTransport().getLoadingDate()).toComparison();

        } else if (order1.getTransport() != null) {
            return -1;
        } else if (order2.getTransport() != null) {
            return 1;
        }
        return 0;
    }
}