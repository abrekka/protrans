package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.util.Periode;

public class AdditionStandingRoof extends AbstractAddition {

	public AdditionStandingRoof(TransportCostAddition addition,
			String aArticlePath, String someInfo) {
		super(addition, aArticlePath, someInfo);
	}

	public BigDecimal calculateAddition(BigDecimal basis,
			Transportable transportable, Periode period, boolean ignoreSent) {
		BigDecimal additionValue = BigDecimal.valueOf(0);
		if (transportable.getPostShipment() == null) {
			additionValue = calculateAdditionForOrder(basis, transportable);
		}
		return additionValue;
	}

	private BigDecimal calculateAdditionForOrder(final BigDecimal basis,
			final Transportable transportable) {
		BigDecimal additionValue = BigDecimal.valueOf(0);

		Order order = transportable.getOrder();

		OrderLine orderLine = order.getOrderLine("Takstoler");
		if (orderLine != null) {
			OrderLineAttribute attribute = orderLine
					.getAttributeByName("Stående tak");
			if (attribute != null
					&& "Ja".equalsIgnoreCase(attribute.getAttributeValue())) {
				additionValue = basis.multiply(
						transportCostAdditon.getAddition()).divide(
						BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
			}

		}

		return additionValue;
	}

}
