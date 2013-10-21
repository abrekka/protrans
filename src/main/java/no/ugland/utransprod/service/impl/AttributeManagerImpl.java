package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.AttributeDAO;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.AttributeManager;

/**
 * Implementasjon av manger for attributter.
 * @author atle.brekka
 */
public class AttributeManagerImpl extends ManagerImpl<Attribute>implements AttributeManager {
    /**
     * @see no.ugland.utransprod.service.AttributeManager#findByName(java.lang.String)
     */
    public final Attribute findByName(final String name) {
        Attribute att = new Attribute();
        att.setName(name);
        List<Attribute> list = dao.findByExample(att);
        if (list == null || list.size() != 1) {
            return null;
        }
        return list.get(0);
    }

    /**
     * @see no.ugland.utransprod.service.AttributeManager#
     * removeAttribute(no.ugland.utransprod.model.Attribute)
     */
    public final void removeAttribute(final Attribute attribute) {
        dao.removeObject(attribute.getAttributeId());

    }

    /**
     * @see no.ugland.utransprod.service.AttributeManager#saveAttribute(no.ugland.utransprod.model.Attribute)
     */
    public final void saveAttribute(final Attribute attribute) {
        dao.saveObject(attribute);

    }

    /**
     * @see no.ugland.utransprod.service.AttributeManager#findAll()
     */
    public final List<Attribute> findAll() {
        return dao.getObjects();
    }

    /**
     * Finner attributt.
     * @param attribute
     * @return attributter
     */
    public final List<Attribute> findByAttribute(final Attribute attribute) {
        return dao.findByExampleLike(attribute);
    }

    /**
     * @param object
     * @return attributter
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<Attribute> findByObject(final Attribute object) {
        return findByAttribute(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final Attribute object) {
        ((AttributeDAO)dao).refreshObject(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final Attribute object) {
        removeAttribute(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final Attribute object) {
        saveAttribute(object);

    }

    /**
     * @see no.ugland.utransprod.service.AttributeManager#lazyLoad(no.ugland.utransprod.model.Attribute,
     *      no.ugland.utransprod.service.enums.LazyLoadAttributeEnum[])
     */
    /*public final void lazyLoad(final Attribute attribute, final LazyLoadAttributeEnum[] enums) {
        dao.lazyLoad(attribute, enums);

    }*/

    /*public void lazyLoad(Attribute object, Enum[] enums) {
        lazyLoad(object,(LazyLoadAttributeEnum[]) enums);
        
    }*/

    @Override
    protected Serializable getObjectId(Attribute object) {
        return object.getAttributeId();
    }
}
