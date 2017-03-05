package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.util.Periode;

public class AdditionWidhtHeightMax extends AbstractAddition {

	public AdditionWidhtHeightMax(final TransportCostAddition addition, final String articlePath, final String info) {
		super(addition, articlePath, info);
	}

	public final BigDecimal calculateAddition(final BigDecimal basis, final Transportable transportable,
			final Periode period, final boolean ignoreSent) {
		BigDecimal additionValue = BigDecimal.valueOf(0);
		if (transportable.getPostShipment() == null) {
			additionValue = calculateAdditionForOrder(basis, transportable);
		}
		return additionValue;
	}

	private BigDecimal calculateAdditionForOrder(final BigDecimal basis, final Transportable transportable) {
		BigDecimal additionValue = BigDecimal.valueOf(0);
		Order order = transportable.getOrder();
		String orderInfo = order.getInfo();
		if (orderInfo == null) {
			orderInfo = order.orderLinesToString();
		}
		if (orderInfo != null && orderInfo.length() != 0) {
			String[] infoSplit = orderInfo.split("x");
			double totalLenght = (Double.valueOf(infoSplit[0]) * 2) + (Double.valueOf(infoSplit[1]) * 2);
			if (totalLenght >= Double.valueOf(transportCostAdditon.getBasis())) {
				additionValue = basis.multiply(transportCostAdditon.getAddition()).divide(BigDecimal.valueOf(100), 2,
						RoundingMode.HALF_UP);
			}
		}
		return additionValue;
	}
}
