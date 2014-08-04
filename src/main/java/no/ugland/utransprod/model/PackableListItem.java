package no.ugland.utransprod.model;

import java.util.Date;

import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.TextRenderable;

/**
 * Interface for objekter som kan pakkes
 * 
 * @author atle.brekka
 * 
 */
public interface PackableListItem extends TextRenderable, Applyable {
    /**
     * @return kolli
     */
    Colli getColli();

    /**
     * @return transportdetaljer
     */
    String getTransportDetails();

    /**
     * @return antall
     */
    Integer getNumberOfItems();

    /**
     * @return opplastingsdato
     */
    Date getLoadingDate();

    /**
     * @return attributtinfo
     */
    String getAttributeInfo();

    /**
     * @return ordrelinjeid
     */
    Integer getOrderLineId();

    /**
     * @return produktområdegruppenavn
     */
    String getProductAreaGroupName();

    Date getActionStarted();

    String getArticleName();

    void setColli(Colli colli);

    Integer getTransportYear();

    Integer getTransportWeek();

    String getLoadTime();

    boolean isRelatedArticlesStarted();

    Integer getProbability();

    Integer getRest();

    Integer getProductionWeek();

    Date getPacklistReady();

}
