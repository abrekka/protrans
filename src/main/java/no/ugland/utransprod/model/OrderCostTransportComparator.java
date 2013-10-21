/**
 * 
 */
package no.ugland.utransprod.model;

import java.util.Comparator;


import org.apache.commons.lang.builder.CompareToBuilder;

public class OrderCostTransportComparator implements Comparator<OrderCost> {

    public int compare(final OrderCost orderCost1, final OrderCost orderCost2) {
        return new CompareToBuilder().append(
                orderCost1.getTransport().getEmployee(),
                orderCost2.getTransport().getEmployee()).append(
                orderCost1.getTransport(), orderCost2.getTransport())
                .toComparison();

    }

}