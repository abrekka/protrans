package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.util.Periode;

public class AdditionInnredning extends AbstractAddition {

	public AdditionInnredning(TransportCostAddition addition, String aArticlePath, String someInfo) {
		super(addition, aArticlePath, someInfo);
	}

	public BigDecimal calculateAddition(BigDecimal basis, Transportable transportable, Periode period,
			boolean ignoreSent) {
		BigDecimal addValue = BigDecimal.valueOf(0);
		OrderLine orderLine = transportable.getOrderLine(articlePath);
		if (orderLine != null) {

			Colli colli = orderLine.getColli();
			if (colli != null) {
				addValue = getAddValue(period, ignoreSent, addValue, colli, transportable);
			}
		}
		return addValue;
	}

	private BigDecimal getAddValue(final Periode period, final boolean ignoreSent, final BigDecimal aAddValue,
			final Colli colli, final Transportable transportable) {
		BigDecimal addValue = aAddValue;
		if (!ignoreSent) {
			if (colli.getSent() != null) {
				addValue = addIfColliIsForTransportable(transportable, colli);
			}
		} else {
			addValue = transportCostAdditon.getAddition();
		}
		return addValue;
	}

	private BigDecimal addIfColliIsForTransportable(final Transportable transportable, final Colli colli) {
		if (compareColliWithTransportable(transportable, colli) == 0) {
			return transportCostAdditon.getAddition();
		}
		return BigDecimal.valueOf(0);
	}

	private int compareColliWithTransportable(final Transportable transportable, final Colli colli) {
		if (transportable.getPostShipment() != null && colli.getPostShipment() == null) {
			return -1;
		} else if (transportable.getPostShipment() == null && colli.getPostShipment() != null) {
			return 1;
		} else if (transportable.getPostShipment() != null && colli.getPostShipment() != null) {
			return transportable.getPostShipment().getPostShipmentId()
					.compareTo(colli.getPostShipment().getPostShipmentId());
		} else {
			return transportable.getOrder().getOrderId().compareTo(colli.getOrder().getOrderId());
		}
	}

}
