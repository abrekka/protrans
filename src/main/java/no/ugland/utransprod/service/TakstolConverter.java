package no.ugland.utransprod.service;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

public class TakstolConverter extends DefaultConverter {

	private static final String ATTRIBUTE_VINKEL = "Vinkel";
	private static final String ATTRIBUTE_BREDDE = "Bredde";
	private static final String ARTICLE_GAVL = "Gavl";

	@Inject
	public TakstolConverter(ManagerRepository managerRepository) {
		super(managerRepository);
	}

//	public OrderLine convert(ArticleType articleType, Ordln ordln, Order order) {
//
//		return articleType == ArticleType.UNKNOWN
//				|| (ordln.getNoinvoab() != null && BigDecimal.valueOf(2).equals(ordln.getNoinvoab()))
//						? OrderLine.UNKNOWN
//						: getOrderLine(articleType, ordln, order);
//
//	}
	
	@Override
	public OrderLine convert(final ArticleType articleType, final Ordln ordln, final Order order, final Ord ord) {
		return articleType == ArticleType.UNKNOWN
				|| (ordln.getNoinvoab() != null && ordln.getNoinvoab().intValue()==2)
						? OrderLine.UNKNOWN
						: getOrderLine(articleType, ordln, order);

	}

	protected OrderLine getOrderLine(ArticleType articleType, Ordln ordln, Order order) {
		OrderLine orderLine = super.getOrderLine(articleType, ordln, order);
		orderLine.setAttributeValue(ATTRIBUTE_VINKEL, Util.convertBigDecimalToString(ordln.getFree4()));
		orderLine.setAttributeValue(ATTRIBUTE_BREDDE, Util.convertBigDecimalToString(ordln.getLgtU()));
		setGavlVinkelOgBredde(orderLine, order);
		return orderLine;
	}

	private void setGavlVinkelOgBredde(OrderLine takstol, Order order) {
		OrderLine gavl = order.getOrderLine(ARTICLE_GAVL);
		String takstolVinkel = takstol.getAttributeValue(ATTRIBUTE_VINKEL);
		String takstolBredde = takstol.getAttributeValue(ATTRIBUTE_BREDDE);
		if (gavl != OrderLine.UNKNOWN && !StringUtils.isEmpty(takstolVinkel)) {
			gavl.setAttributeValue(ATTRIBUTE_VINKEL, takstolVinkel);
		}
		if (gavl != OrderLine.UNKNOWN && !StringUtils.isEmpty(takstolBredde)) {
			gavl.setAttributeValue(ATTRIBUTE_BREDDE, takstolBredde);
		}

	}

}
