package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.util.Periode;

public class AdditionTiles extends AbstractAddition {

    public AdditionTiles(final TransportCostAddition addition,
            final String articlePath, final String info) {
        super(addition, articlePath, info);
    }

    public final BigDecimal calculateAddition(final BigDecimal basis,
            final Transportable transportable, final Periode period,
            final boolean ignoreSent) {

        BigDecimal addValue = BigDecimal.valueOf(0);
        OrderLine orderLine = transportable.getOrderLine(articlePath);
        if (orderLine != null) {
            addValue = checkAndAddAddition(transportable, period, ignoreSent,
                    orderLine);
        }
        return addValue;
    }

    private BigDecimal checkAndAddAddition(final Transportable transportable,
            final Periode period, final boolean ignoreSent,
            final OrderLine orderLine) {
        BigDecimal addValue = BigDecimal.valueOf(0);
        if (sendingFromGGAndNotShingel(orderLine)) {

            Colli colli = orderLine.getColli();
            if (colli != null) {
                addValue = getAddValue(period, ignoreSent, addValue, colli,
                        transportable);
            }

        }
        return addValue;
    }

    private boolean sendingFromGGAndNotShingel(final OrderLine orderLine) {
        boolean sendingFromGGAndNotShingel = false;
        String attributeValue = orderLine
                .getOrderLineAttributeValue("Sendes fra GG");
        if (attributeValue != null && attributeValue.equalsIgnoreCase("Ja")) {
            attributeValue = orderLine
                    .getOrderLineAttributeValue("Taksteintype");
            if (attributeValue.indexOf("Shingel") < 0) {
                sendingFromGGAndNotShingel = true;
            }
        }
        return sendingFromGGAndNotShingel;
    }

    private BigDecimal getAddValue(final Periode period,
            final boolean ignoreSent, final BigDecimal aAddValue,
            final Colli colli, final Transportable transportable) {
        BigDecimal addValue = aAddValue;
        if (!ignoreSent) {
            if (colli.getSent() != null) {
                // addValue = checkSentInPeriod(period, addValue, colli);
                addValue = addIfColliIsForTransportable(transportable, colli);
            }
        } else {
            addValue = transportCostAdditon.getAddition();
        }
        return addValue;
    }

    private BigDecimal addIfColliIsForTransportable(
            final Transportable transportable, final Colli colli) {
        if (compareColliWithTransportable(transportable, colli) == 0) {
            return transportCostAdditon.getAddition();
        }
        return BigDecimal.valueOf(0);
    }

    private int compareColliWithTransportable(
            final Transportable transportable, final Colli colli) {
        if (transportable.getPostShipment() != null
                && colli.getPostShipment() == null) {
            return -1;
        } else if (transportable.getPostShipment() == null
                && colli.getPostShipment() != null) {
            return 1;
        } else if (transportable.getPostShipment() != null
                && colli.getPostShipment() != null) {
            return transportable.getPostShipment().getPostShipmentId()
                    .compareTo(colli.getPostShipment().getPostShipmentId());
        } else {
            return transportable.getOrder().getOrderId().compareTo(
                    colli.getOrder().getOrderId());
        }
    }

}
