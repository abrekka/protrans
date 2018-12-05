package no.ugland.utransprod.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PreventiveActionCommentCommentType;
import no.ugland.utransprod.model.Ordln.CostLine;

import org.apache.commons.lang.StringUtils;

public class GarasjeConverter implements ConstructionTypeAttributesConverter {

	private static final String ARTICLE_PATH_GARAGE_TYPE = "Garasjetype";
	private static final String WALL_HEIGHT_ATTRIBUTE = "Vegghøyde";
	private static final String BRICK_WALL_HEIGHT_ATTRIBUTE = "Murhøyde";
	private static final int GARAGE_TYPE_LINE = 1;
	private static final String WIDTH_ATTRIBUTE = "Bredde";
	private static final String LENGTH_ATTRIBUTE = "Lengde";
	private OrdlnManager ordlnManager;
	private CostTypeManager costTypeManager;
	private CostUnitManager costUnitManager;

	public GarasjeConverter(OrdlnManager aOrdlnManager, CostTypeManager costTypeManager,CostUnitManager costUnitManager) {
		ordlnManager = aOrdlnManager;
		this.costTypeManager = costTypeManager;
		this.costUnitManager=costUnitManager;
	}

	public void setConstructionTypeAttributes(Ord ord, Order order) {
		OrderLine garageOrderLine = order.getOrderLine(ARTICLE_PATH_GARAGE_TYPE);

		garageOrderLine = garageOrderLine != OrderLine.UNKNOWN ? garageOrderLine : createGarageOrderLine(order);
		if (ord != null) {
			setGarageAttributes(garageOrderLine, ord);
		}

		if ("Rekke".equalsIgnoreCase(order.getProductArea().getProductArea())) {
			OrderCost monteringskostnad=CostLine.MONTERING_VILLA.addCost(costTypeManager,
					costUnitManager, order, BigDecimal.ZERO);
			order.addOrderCost(monteringskostnad);
		}
		DefaultConverter.setAttributes(garageOrderLine, ordlnManager);
	}

	private void setGarageAttributes(OrderLine garageOrderLine, Ord ord) {
		setWallHeigth(garageOrderLine, ord);
		setBrickWallHeigth(garageOrderLine, ord);
		setWidthAndLength(garageOrderLine, ord);
	}

	private void setWidthAndLength(OrderLine garageOrderLine, Ord ord) {
		Ordln ordln = getVismaOrderLineForGarageType(ord);
		setWidth(ordln, garageOrderLine);
		setLength(ordln, garageOrderLine);
	}

	private void setLength(Ordln ordln, OrderLine garageOrderLine) {
		BigDecimal vismaLength = getVismaLength(ordln);
		if (vismaLengthHasValue(vismaLength)) {
			OrderLineAttribute attribute = garageOrderLine.getAttributeByName(LENGTH_ATTRIBUTE);
			attribute.setAttributeValue(String.valueOf(vismaLength.setScale(0)));
		}

	}

	private boolean vismaLengthHasValue(BigDecimal length) {
		return length != null && length != BigDecimal.ZERO;
	}

	private BigDecimal getVismaLength(Ordln ordln) {
		return ordln != null ? ordln.getLgtU() : null;
	}

	private void setWidth(Ordln ordln, OrderLine garageOrderLine) {
		BigDecimal vismaWidth = getVismaWidth(ordln);
		if (vismaWidthHasValue(vismaWidth)) {
			OrderLineAttribute attribute = garageOrderLine.getAttributeByName(WIDTH_ATTRIBUTE);
			attribute.setAttributeValue(String.valueOf(vismaWidth.setScale(0)));
		}

	}

	private BigDecimal getVismaWidth(Ordln ordln) {
		return ordln != null ? ordln.getWdtu() : null;
	}

	private boolean vismaWidthHasValue(BigDecimal width) {
		return width != null && width != BigDecimal.ZERO;
	}

	private Ordln getVismaOrderLineForGarageType(Ord ord) {
		Ordln ordln = ordlnManager.findByOrdnoAndPrCatNo2(ord.getOrdno(), GARAGE_TYPE_LINE);
		return ordln;
	}

	private void setBrickWallHeigth(OrderLine garageOrderLine, Ord ord) {
		if (hasBrickWallHeigth(ord)) {
			OrderLineAttribute attribute = garageOrderLine.getAttributeByName(BRICK_WALL_HEIGHT_ATTRIBUTE);
			attribute.setAttributeValue(getBrickWallHeigth(ord));
		}

	}

	private String getBrickWallHeigth(Ord ord) {
		return StringUtils.substringBefore(StringUtils.substringBefore(ord.getFree2(), ","), ".");
	}

	private boolean hasBrickWallHeigth(Ord ord) {
		return ord.getFree2() != null ? true : false;
	}

	private void setWallHeigth(OrderLine garageOrderLine, Ord ord) {
		if (hasWallHeigth(ord)) {
			OrderLineAttribute attribute = garageOrderLine.getAttributeByName(WALL_HEIGHT_ATTRIBUTE);
			attribute.setAttributeValue(getWallHeigth(ord));
		}

	}

	private String getWallHeigth(Ord ord) {
		return StringUtils.substringBefore(StringUtils.substringBefore(ord.getFree1(), ","), ".");
	}

	private boolean hasWallHeigth(Ord ord) {
		return ord.getFree1() != null ? true : false;
	}

	private OrderLine createGarageOrderLine(Order order) {
		OrderLine orderLine = new OrderLine();
		orderLine.setArticlePath(ARTICLE_PATH_GARAGE_TYPE);
		order.addOrderLine(orderLine);
		return orderLine;
	}

}
