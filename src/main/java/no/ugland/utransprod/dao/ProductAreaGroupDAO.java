package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for DAO mot tabell PRODUCT_AREA_GROUP.
 * @author atle.brekka
 */
public interface ProductAreaGroupDAO extends DAO<ProductAreaGroup> {
    ProductAreaGroup findByName(String name);
}
