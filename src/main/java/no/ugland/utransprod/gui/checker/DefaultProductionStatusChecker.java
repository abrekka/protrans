package no.ugland.utransprod.gui.checker;

import java.util.Set;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.OrderLine;

import org.apache.log4j.Logger;

/**
 * Sjekker produksjonsstatus på gitt artikkel for en ordre attributtverdi
 * @author atle.brekka
 */
public class DefaultProductionStatusChecker implements
        StatusCheckerInterface<Transportable> {
    /**
     * 
     */
    private static final Logger logger = Logger
            .getLogger(DefaultProductionStatusChecker.class);

    /**
     * 
     */
    private ArticleType articleType;

    /**
     * @param checkArticleType
     */
    public DefaultProductionStatusChecker(ArticleType checkArticleType) {
        if (checkArticleType == null) {
            logger.error("artikkeltype er null");
        }
        articleType = checkArticleType;
    }

    /**
     * @param transportable
     * @return P dersom artikkel er produsert og en X dersom pakket
     * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleStatus(java.lang.Object)
     */
    public String getArticleStatus(Transportable transportable) {
        StringBuffer returnBuffer = new StringBuffer();

        Set<OrderLine> orderLines = transportable.getOrderLines();

        if (orderLines != null && articleType != null) {
            for (OrderLine orderLine : orderLines) {
                checkOrderLine(transportable, returnBuffer, orderLine);
            }
        }
        return returnBuffer.toString();
    }

    private void checkOrderLine(final Transportable transportable,
            final StringBuffer returnBuffer, final OrderLine orderLine) {
        String articleName;
        String checkTypeArticleName;
        if (orderLine.belongTo(transportable)) {
            articleName = orderLine.getArticleName();
            checkTypeArticleName = articleType.getArticleTypeName();
            checkArticle(returnBuffer, orderLine, articleName,
                    checkTypeArticleName);
        }
    }

    private void checkArticle(final StringBuffer returnBuffer,
            final OrderLine orderLine, final String articleName,
            final String checkTypeArticleName) {
        if (articleName != null && checkTypeArticleName != null) {
            if (articleName.equalsIgnoreCase(checkTypeArticleName)) {
                if (orderLine.getProduced() != null) {
                    returnBuffer.append("P");
                }
                if (orderLine.getColli() != null) {
                    returnBuffer.append("X");
                }
            }
        } else {
            logger.error("Ordrelinje mangler artikkelnavn id "
                    + orderLine.getOrderLineId());
        }
    }

    /**
     * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleName()
     */
    public String getArticleName() {
        return articleType.getArticleTypeName();
    }
}
