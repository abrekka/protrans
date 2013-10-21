package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for serviceklasse mot tabell PRODUCT_AREA_GROUP.
 * @author atle.brekka
 */
public interface ProductAreaGroupManager {
    String MANAGER_NAME = "productAreaGroupManager";

	/**
     * Finner alle.
     * @return produktområdegrupper
     */
    List<ProductAreaGroup> findAll();

    ProductAreaGroup findByName(String name);
}
