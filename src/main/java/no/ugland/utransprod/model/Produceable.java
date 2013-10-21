package no.ugland.utransprod.model;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.TextRenderable;

/**
 * Interface for objekter som er produserbare
 * @author atle.brekka
 */
public interface Produceable extends TextRenderable, Applyable {
    /**
     * Produsert dato
     * @return Produsert dato
     */
    Date getProduced();

    /**
     * @return transportdetaljer
     */
    String getTransportDetails();

    /**
     * @return antall
     */
    Integer getNumberOfItems();

    /**
     * @return attributtinfo
     */
    String getAttributeInfo();

    /**
     * @return opplastingsdato
     */
    Date getLoadingDate();

    /**
     * @return ordrelinje id
     */
    Integer getOrderLineId();

    /**
     * @return produktområdegruppenavnOF
     */
    String getProductAreaGroupName();

    Date getActionStarted();
    Date getProductionDate();
    String getProductionUnitName();

    String getArticleName();

	Integer getTransportYear();

	Integer getTransportWeek();

	String getLoadTime();

	boolean isRelatedArticlesStarted();

	Date getRelatedStartedDate();

	void setProductionUnitName(String productionUnitName);

	Integer getProbability();

	String getTrossDrawer();


	


}
