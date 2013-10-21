package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ExternalOrderLine;


/**
 * Interface for serviceklasse mot tabell EXTERNAL_ORDER_LINE
 * @author atle.brekka
 */
public interface ExternalOrderLineManager extends OverviewManager<ExternalOrderLine>{

	String MANAGER_NAME = "externalOrderLineManager";
    /**
     * Lazy laster ekstern ordrelinje
     * @param externalOrderLine
     * @param enums
     */
    //void lazyLoad(ExternalOrderLine externalOrderLine,LazyLoadExternalOrderLineEnum[] enums);

}
