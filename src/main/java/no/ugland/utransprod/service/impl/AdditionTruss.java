package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.util.Periode;

public class AdditionTruss extends AbstractAddition {

    public AdditionTruss(final TransportCostAddition addition, final String articlePath,
            final String info) {
        super(addition, articlePath, info);
    }

    public final BigDecimal calculateAddition(final BigDecimal basis,
            final Transportable transportable, final Periode period,
            final boolean ignoreSent) {
        OrderLine orderLine = transportable.getOrderLine(articlePath);
        BigDecimal addValue = BigDecimal.valueOf(0);
        if (orderLine != null) {
            Colli colli = orderLine.getColli();
            if (colli != null) {
                addValue = getAddValue(period, ignoreSent, orderLine, addValue,
                        colli, transportable);
            }
        }
        return addValue;
    }

    private BigDecimal getAddValue(final Periode period,
            final boolean ignoreSent, final OrderLine orderLine, final BigDecimal aAddValue,
            final Colli colli, final Transportable transportable) {
        BigDecimal addValue=aAddValue;
        if (!ignoreSent) {
            if (colli.getSent() != null) {
                addValue = addIfColliIsForTransportable(transportable, colli,
                        orderLine);
            }
        } else {
            addValue = checkAndGetAddValue(orderLine);
        }
        return addValue;
    }

    private BigDecimal addIfColliIsForTransportable(
            final Transportable transportable, final Colli colli, final OrderLine orderLine) {

        if (compareColliWithTransportable(transportable, colli) == 0) {
            return checkAndGetAddValue(orderLine);
        }
        return BigDecimal.valueOf(0);
    }

    private int compareColliWithTransportable(final Transportable transportable,
            final Colli colli) {
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

    private BigDecimal checkAndGetAddValue(final OrderLine orderLine) {
        OrderLineAttribute attribute = orderLine.getAttributeByName("Vinkel");
        BigDecimal addValue = BigDecimal.valueOf(0);
        String value = attribute.getAttributeNumberValue();
        if (Double.valueOf(value) >= Double.valueOf(transportCostAdditon
                .getBasis())) {
            addValue = transportCostAdditon.getAddition();
        }
        return addValue;
    }

}
