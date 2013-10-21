package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Attribute;

/**
 * Interface for DAO mot tabell ATTRIBUTE
 * @author atle.brekka
 */
public interface AttributeDAO extends DAO<Attribute> {
    /**
     * Oppdaterer object
     * @param attribute
     */
    void refreshObject(Attribute attribute);

    /**
     * Lazy laster attributt
     * @param attribute
     * @param enums
     */
    //void lazyLoad(Attribute attribute, LazyLoadAttributeEnum[] enums);
}
