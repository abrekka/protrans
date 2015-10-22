package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;

public class TaksteinConverter extends DefaultConverter {

    private static final String ATTRIBUTE_TAKSTEINTYPE = "Taksteintype";
    private static final String SKARPNES = "SKARPNES";
    private static final String JA = "Ja";
    private static final String NEI = "Nei";
    private static final String ATTRIBUTE_SENDES_FRA_GG = "Sendes fra GG";

    public TaksteinConverter(ManagerRepository managerRepository) {
	super(managerRepository);
    }

    public OrderLine convert(ArticleType articleType, Ordln ordln, Order order, Ord ord) {
	return articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN : getOrderLineAndAddAttributes(articleType, ordln, order);
    }

    private OrderLine getOrderLineAndAddAttributes(ArticleType articleType, Ordln ordln, Order order) {
	OrderLine orderLine = super.getOrderLine(articleType, ordln, order);
	setTaksteintype(ordln, orderLine);
	setSendesFraGG(ordln, orderLine);
	return orderLine;
    }

    private void setSendesFraGG(Ordln ordln, OrderLine orderLine) {
	if (ordln.getProd() != null) {
	    String sendesFraGG = getSendesFraGG(ordln.getProd().getInf());
	    orderLine.setAttributeValue(ATTRIBUTE_SENDES_FRA_GG, sendesFraGG);
	}

    }

    private String getSendesFraGG(String inf) {
	return inf != null && inf.startsWith(SKARPNES) ? JA : NEI;
    }

    private void setTaksteintype(Ordln ordln, OrderLine orderLine) {
	if (ordln.getDescription() != null && ordln.getDescription().length() != 0) {
	    orderLine.setAttributeValue(ATTRIBUTE_TAKSTEINTYPE, ordln.getDescription().replaceAll("Takstein:", ""));
	}
    }

}
