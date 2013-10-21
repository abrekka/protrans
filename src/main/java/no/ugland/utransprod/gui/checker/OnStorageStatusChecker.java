package no.ugland.utransprod.gui.checker;

import java.util.Set;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;

/**
 * Sjekker om gitt artikkel er på lager
 * @author atle.brekka
 */
public class OnStorageStatusChecker implements
        StatusCheckerInterface<Transportable> {

    /**
     * 
     */
    private ArticleType articleType;

    /**
     * @param checkArticleType
     */
    public OnStorageStatusChecker(ArticleType checkArticleType) {
        articleType = checkArticleType;
    }

    /**
     * @param transportable
     * @return e dersomm ikke på lager og X dersom produsert, dersom på lager
     *         vises ingenting og X dersom pakket
     * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleStatus(java.lang.Object)
     */
    public String getArticleStatus(Transportable transportable) {
        StringBuffer returnBuffer = new StringBuffer();

        Set<OrderLine> orderLines = transportable.getOrderLines();
        String articleName = null;
        String checkTypeArticleName = null;

        if (orderLines != null && articleType != null) {
            for (OrderLine orderLine : orderLines) {
                if (orderLine.belongTo(transportable)) {
                    articleName = orderLine.getArticleName();
                    checkTypeArticleName = articleType.getArticleTypeName();
                    if (articleName != null && checkTypeArticleName != null) {
                        if (articleName.equalsIgnoreCase(checkTypeArticleName)) {
                            if (!isLager(orderLine)) {
                                returnBuffer.append("e");

                                if (orderLine.getProduced() != null) {
                                    returnBuffer.append("X");
                                }
                            } else {
                                if (orderLine.getColli() != null) {
                                    returnBuffer.append("X");
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        return returnBuffer.toString();
    }

    /**
     * Sjekker om artikkel er lagervare
     * @param orderLine
     * @return true dersom lagervare
     */
    private boolean isLager(OrderLine orderLine) {
        boolean returnValue = false;
        Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes();

        if (attributes != null) {

            for (OrderLineAttribute attribute : attributes) {
                if (attribute.getAttributeName().equalsIgnoreCase("Lager")) {
                    if (attribute.getAttributeValueBool()) {
                        returnValue = true;
                    }
                    break;
                }
            }
        }

        return returnValue;
    }

    /**
     * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleName()
     */
    public String getArticleName() {
        return articleType.getArticleTypeName();
    }

}
