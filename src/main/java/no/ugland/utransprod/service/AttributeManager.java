package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.Attribute;

/**
 * Interface for manager som håndterer attributter
 * @author atle.brekka
 */
public interface AttributeManager extends OverviewManager<Attribute> {
    public static final String MANAGER_NAME="attributeManager";
    /**
     * Lagrer attributt
     * @param attribute
     */
    void saveAttribute(Attribute attribute);

    /**
     * fjerner attributt
     * @param attribute
     */
    void removeAttribute(Attribute attribute);

    /**
     * Finner basert på navn
     * @param name
     * @return attributt
     */
    Attribute findByName(String name);

    /**
     * Finner alle
     * @return alle attributter
     */
    List<Attribute> findAll();

    /**
     * Lazy laster attributt
     * @param battribute
     * @param enums
     */
    //void lazyLoad(Attribute battribute, LazyLoadAttributeEnum[] enums);
}
