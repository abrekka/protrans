package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ProductArea;

/**
 * Interface for DAO mot tabell PRODUCT_AREA
 * @author atle.brekka
 *
 */
public interface ProductAreaDAO extends DAO<ProductArea> {

	ProductArea findByProductAreaNr(Integer productAreaNr);

	List<String> findAllNames();
	
}
