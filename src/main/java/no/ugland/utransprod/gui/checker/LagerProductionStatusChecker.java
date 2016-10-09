package no.ugland.utransprod.gui.checker;

import org.hibernate.Hibernate;

import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.ModelUtil;

/**
 * Klasse som brukes til å sjekke om ordrelinje for Vegg og front er produsert
 * dersom den ikke er på lager. Dersom den er på lager trenger den ikke
 * produseres
 * 
 * @author atle.brekka
 */
public class LagerProductionStatusChecker implements StatusCheckerInterface<OrderLine> {
	private String articleName;

	private String attributeName;

	private String attributeValue;

	private OrderLineManager orderLineManager;

	/**
	 * @param aArticleName
	 * @param aAttributeName
	 * @param aAttributeValue
	 */
	public LagerProductionStatusChecker(final String aArticleName, final String aAttributeName,
			final String aAttributeValue) {
		orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
		articleName = aArticleName;
		attributeName = aAttributeName;
		attributeValue = aAttributeValue;
	}

	/**
	 * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleName()
	 */
	public final String getArticleName() {
		return articleName;
	}

	/**
	 * @param orderLine
	 * @return status
	 * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleStatus(java.lang.Object)
	 */
	public final String getArticleStatus(final OrderLine orderLine) {
		String status = "Nei";

		if (orderLine.getProduced() != null) {
			status = "Ja";
		} else {
			if (!Hibernate.isInitialized(orderLine.getOrderLineAttributes())) {
				orderLineManager.lazyLoad(orderLine,
						new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
			}
			OrderLineAttribute attribute = orderLine.getAttributeByName(attributeName);

			if (attribute != null && attribute.getOrderLineAttributeValue().equalsIgnoreCase(attributeValue)) {
				status = "Nei";
			} else {
				status = "Ja";
			}
		}

		return status;
	}

}
