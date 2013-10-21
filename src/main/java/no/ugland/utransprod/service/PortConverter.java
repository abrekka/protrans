package no.ugland.utransprod.service;

import java.math.BigDecimal;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

public class PortConverter extends DefaultConverter {

	private static final String ATTRIBUTE_PORTMAAL = "Portmål";

	@Inject
	public PortConverter(ManagerRepository managerRepository) {
		super(managerRepository);
	}

	public OrderLine convert(ArticleType articleType, Ordln ordln, Order order) {
		return articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN
				: getOrderLine(articleType, ordln, order);

	}

	protected OrderLine getOrderLine(ArticleType articleType, Ordln ordln,
			Order order) {
		OrderLine orderLine = super.getOrderLine(articleType, ordln, order);
		setSizeAttribute(orderLine, ordln);
		return orderLine;
	}

	private void setSizeAttribute(OrderLine orderLine, Ordln ordln) {
		if (hasCompleteSizeInfo(ordln)) {
			orderLine.setAttributeValue(ATTRIBUTE_PORTMAAL, getSize(ordln));
		}
	}

	private boolean hasCompleteSizeInfo(Ordln ordln) {
		return ordln.getLgtU() != null && ordln.getLgtU() != BigDecimal.ZERO
				&& ordln.getHgtU() != null
				&& ordln.getHgtU() != BigDecimal.ZERO;
	}

	private String getSize(Ordln ordln) {
		return Util.convertBigDecimalToString(ordln.getLgtU()) + "x"
				+ Util.convertBigDecimalToString(ordln.getHgtU());
	}

}
