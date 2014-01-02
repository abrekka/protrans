package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Ordln;

/**
 * Interface for klasser som skal kunne brukes i produksjon og pakking
 * 
 * @author atle.brekka
 * 
 */
public interface Applyable {
    String getOrderNr();

    Boolean isForPostShipment();

    Integer getOrderLineId();

    void setOrdln(Ordln ordln);

    Ordln getOrdln();

    String getArticleName();

    Colli getColli();

    void setProduced(Date produced);

    Date getProduced();

    void setColli(Colli colli);

    List<Applyable> getRelatedArticles();

    void setRelatedArticles(List<Applyable> relatedArticles);

    boolean isRelatedArticlesComplete();

    Integer getNumberOfItems();

    String getProductionUnitName();

    BigDecimal getRealProductionHours();

}
