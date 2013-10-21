package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.CostTypeDAO;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.service.CostTypeManager;

/**
 * Implementasjon av manager for kostnadstype for hibernate.
 * @author atle.brekka
 */
public class CostTypeManagerImpl extends ManagerImpl<CostType>implements CostTypeManager {
    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<CostType> findAll() {
        return ((CostTypeDAO)dao).findAll();
    }

    /**
     * Finner basert på eksempel.
     * @param costType
     * @return liste med kostnadstyper
     */
    public final List<CostType> findByCostType(final CostType costType) {
        return dao.findByExampleLike(costType);
    }

    /**
     * @param object
     * @return liste med kostnadstype
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<CostType> findByObject(final CostType object) {
        return findByCostType(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final CostType object) {
        ((CostTypeDAO)dao).refreshObject(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final CostType object) {
        dao.removeObject(object.getCostTypeId());

    }

    /**
     * Lagrer kostnadstype.
     * @param costType
     */
    public final void saveCostType(final CostType costType) {
        dao.saveObject(costType);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final CostType object) {
        saveCostType(object);

    }

    /**
     * @see no.ugland.utransprod.service.CostTypeManager#findByName(java.lang.String)
     */
    public final CostType findByName(final String name) {
        return ((CostTypeDAO)dao).findByName(name);
    }

   

    @Override
    protected Serializable getObjectId(CostType object) {
        return object.getCostTypeId();
    }

}
