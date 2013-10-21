package no.ugland.utransprod.gui.checker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;

/**
 * Sjekker status på takstein
 * @author atle.brekka
 */
public class TaksteinAttributeStatusChecker implements StatusCheckerInterface<Transportable> {
    private String attributeName;

    private String articleName;

    private String attributeValue;

    private String typeAttribute;

    /**
     * @param aAttributeName
     * @param aArticleName
     * @param aAttributeValue
     * @param aTypeAttribute
     */
    public TaksteinAttributeStatusChecker(final String aAttributeName, final String aArticleName,
            final String aAttributeValue, final String aTypeAttribute) {
        this.attributeName = aAttributeName;
        this.articleName = aArticleName;
        this.attributeValue = aAttributeValue;
        this.typeAttribute = aTypeAttribute;
    }

    /**
     * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleName()
     */
    public final String getArticleName() {
        return articleName;
    }

    /**
     * @param transportable
     * @return status V dersom det skal være med samt tre første bokstavene av
     *         type, X dersom pakket
     * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleStatus(java.lang.Object)
     */
    public final String getArticleStatus(final Transportable transportable) {
        StringBuffer returnBuffer = new StringBuffer();

        Set<OrderLine> orderLines = transportable.getOrderLines() != null ? transportable.getOrderLines()
                : new HashSet<OrderLine>();

        String typeName = "";
        Iterator<OrderLine> orderLineIt = orderLines.iterator();
        while (orderLineIt.hasNext() && typeName.length() == 0) {
            OrderLine orderLine = orderLineIt.next();
            typeName = orderLine.belongTo(transportable)
                    && orderLine.getArticleName().equalsIgnoreCase(articleName) ? checkOrderLine(
                    transportable, returnBuffer, orderLine) : "";
        }

        returnBuffer.insert(0, typeName);
        return returnBuffer.toString();
    }

    private String checkOrderLine(final Transportable transportable, StringBuffer returnBuffer,
            final OrderLine orderLine) {

        returnBuffer = orderLine.getColli() != null ? returnBuffer.append("X") : returnBuffer;
        Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes() != null ? orderLine
                .getOrderLineAttributes() : new HashSet<OrderLineAttribute>();

        return checkAttributes(returnBuffer, attributes);
    }

    private String checkAttributes(StringBuffer returnBuffer, final Set<OrderLineAttribute> attributes) {
        Iterator<OrderLineAttribute> attributeIt = attributes.iterator();
        boolean nameNotFound = true;
        String typeName = "";

        while (attributeIt.hasNext() && (nameNotFound || typeName.length() == 0)) {
            OrderLineAttribute attribute = attributeIt.next();
            typeName = attribute.getAttributeName().equalsIgnoreCase(typeAttribute) && typeName.length() == 0 ? attribute
                    .getAttributeValue().substring(0, 3)
                    + ","
                    : typeName;
            nameNotFound = attribute.getAttributeName().equalsIgnoreCase(attributeName)
                    && attribute.getAttributeValue().equalsIgnoreCase(attributeValue) && nameNotFound ? false
                    : nameNotFound;
        }
        returnBuffer = nameNotFound ? returnBuffer : returnBuffer.append("V");

        return typeName;
    }

}
