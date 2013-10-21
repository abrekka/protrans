package no.ugland.utransprod.gui.checker;

import java.util.Set;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.util.Util;

import org.apache.log4j.Logger;

/**
 * Sjekker status på gulvspon for ordre
 * @author atle.brekka
 */
public class GulvsponStatusChecker implements
        StatusCheckerInterface<Transportable> {
    /**
     * 
     */
    private static final Logger logger = Logger
            .getLogger(GulvsponStatusChecker.class);

    /**
     * 
     */
    private ArticleType articleType;

    /**
     * @param checkArticleType
     */
    public GulvsponStatusChecker(ArticleType checkArticleType) {
        if (checkArticleType == null) {
            logger.error("artikkeltype er null");
        }
        articleType = checkArticleType;
    }

    /**
     * @param transportable
     * @return V dersom ordre har gulvspon og X dersom pakket
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
                            if (orderLine.hasArticle()) {
                                returnBuffer.append("V"
                                        + Util.nullIntegerToString(orderLine
                                                .getNumberOfItems()));
                            }
                            if (orderLine.getColli() != null) {
                                returnBuffer.append("X");
                            }
                            break;
                        }
                    } else {
                        logger.error("Ordrelinje mangler artikkelnavn id "
                                + orderLine.getOrderLineId());
                    }
                }
            }
        }

        return returnBuffer.toString();
    }

    /**
     * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleName()
     */
    public String getArticleName() {
        return articleType.getArticleTypeName();
    }

}
