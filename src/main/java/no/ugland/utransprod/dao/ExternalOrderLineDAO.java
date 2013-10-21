package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ExternalOrderLine;

/**
 * Interface for DAO mot tabell EXTERNAL_ORDER_LINE
 * @author atle.brekka
 * 
 */
public interface ExternalOrderLineDAO extends DAO<ExternalOrderLine> {
	/**
	 * Lazy laster
	 * @param externalOrderLine
	 * @param enums
	 */
	//void lazyLoad(ExternalOrderLine externalOrderLine,LazyLoadExternalOrderLineEnum[] enums);
}
