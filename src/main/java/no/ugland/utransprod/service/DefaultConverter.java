package no.ugland.utransprod.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.AttributeChoice;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

public class DefaultConverter implements ArticleTypeToOrderLineConverter {
    protected ManagerRepository managerRepository;

    @Inject
    public DefaultConverter(final ManagerRepository aManagerRepository) {
	managerRepository = aManagerRepository;
    }

    public OrderLine convert(final ArticleType articleType, final Ordln ordln, final Order order, final Ord ord) {
	return articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN : getOrderLine(articleType, ordln, order);

    }

    protected OrderLine getOrderLine(final ArticleType articleType, final Ordln ordln, final Order order) {
	OrderLine orderLine = new OrderLine();
	orderLine.setArticleType(articleType);
	orderLine.setOrdln(ordln);
	orderLine.setHasArticle(1);
	setNumberOfItems(ordln, orderLine);
	orderLine.setOrder(order);
	setOrderLineAttributes(articleType, orderLine);
	setAttributeHasValue(articleType, orderLine);
	setOrdnoAndLnno(ordln, orderLine);
	orderLine.setArticlePath(orderLine.getGeneratedArticlePath());
	setAttributes(orderLine, managerRepository.getOrdlnManager());
	return orderLine;
    }

    private void setOrdnoAndLnno(final Ordln ordln, OrderLine orderLine) {
	if (ordln != null) {
	    orderLine.setOrdNo(ordln.getOrdno());
	    orderLine.setLnNo(ordln.getLnno());
	}
    }

    private void setAttributeHasValue(final ArticleType articleType, OrderLine orderLine) {
	if (articleType != null) {
	    orderLine.setOrderLineAttributeValue("Har " + articleType.getArticleTypeName(), "Ja");
	}
    }

    private void setOrderLineAttributes(final ArticleType articleType, OrderLine orderLine) {
	if (articleType != null) {
	    orderLine.setOrderLineAttributes(OrderModel.getOrderLinesAttributes(articleType.getArticleTypeAttributes(), orderLine));
	}
    }

    private void setNumberOfItems(final Ordln ordln, OrderLine orderLine) {
	orderLine.setNumberOfItems(Util.convertBigDecimalToInteger(ordln != null ? ordln.getNoInvo() : BigDecimal.ZERO));
    }

    public static void setAttributes(final OrderLine orderLine, OrdlnManager ordlnManager) {
	Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes() != null ? orderLine.getOrderLineAttributes()
		: new HashSet<OrderLineAttribute>();
	for (OrderLineAttribute orderLineAttribute : attributes) {
	    Attribute attribute = orderLineAttribute.getAttribute();
	    if (!setChoices(orderLine, attribute, orderLineAttribute)) {
		setAttributeBoolValue(orderLine, ordlnManager, orderLineAttribute, attribute);
	    }
	}
    }

    private static void setAttributeBoolValue(final OrderLine orderLine, OrdlnManager ordlnManager, OrderLineAttribute orderLineAttribute,
	    Attribute attribute) {
	Ordln ordln = attribute.hasProdCatNo() ? ordlnManager.findByOrderNrProdCatNo(orderLine.getOrderNr(), attribute.getProdCatNo(),
		attribute.getProdCatNo2()) : Ordln.UNKNOWN;
	if (ordln != Ordln.UNKNOWN) {
	    orderLineAttribute.setAttributeValueBool(true);
	}
    }

    private static boolean setChoices(final OrderLine orderLine, final Attribute attribute, final OrderLineAttribute orderLineAttribute) {
	OrdlnManager ordlnManager = (OrdlnManager) ModelUtil.getBean(OrdlnManager.MANAGER_NAME);
	Set<AttributeChoice> choices = attribute.getAttributeChoices() != null ? attribute.getAttributeChoices() : new HashSet<AttributeChoice>();
	boolean hasChoices = false;
	boolean notFound = true;
	Iterator<AttributeChoice> choiceIt = choices.iterator();
	while (choiceIt.hasNext() && notFound) {
	    AttributeChoice choice = choiceIt.next();
	    hasChoices = true;
	    Ordln ordln = choice.hasProdCatNo() ? ordlnManager.findByOrderNrProdCatNo(orderLine.getOrderNr(), choice.getProdCatNo(),
		    choice.getProdCatNo2()) : Ordln.UNKNOWN;
	    String value = ordln != Ordln.UNKNOWN ? choice.getChoiceValue() : "";
	    orderLineAttribute.setAttributeValue(value);
	    notFound = value.length() != 0 ? false : true;
	}
	return hasChoices;
    }

}
