package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.CostUnitDAO;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.service.CostUnitManager;

/**
 * Implementasjon av manager for kostnadsenhet for hiberbate.
 * @author atle.brekka
 */
public class CostUnitManagerImpl extends ManagerImpl<CostUnit>implements CostUnitManager {
    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<CostUnit> findAll() {
        return ((CostUnitDAO)dao).findAll();
    }

    /**
     * Finner basert på eksempel.
     * @param costUnit
     * @return liste med kostnadsenheter
     */
    public final List<CostUnit> findByCostUnit(final CostUnit costUnit) {
        return dao.findByExampleLike(costUnit);
    }

    /**
     * @param object
     * @return liste
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<CostUnit> findByObject(final CostUnit object) {
        return findByCostUnit(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final CostUnit object) {
        ((CostUnitDAO)dao).refreshObject(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final CostUnit object) {
        dao.removeObject(object.getCostUnitId());

    }

    /**
     * Lagrer kostnadsenhet.
     * @param costUnit
     */
    public final void saveCostUnit(final CostUnit costUnit) {
        dao.saveObject(costUnit);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final CostUnit object) {
        saveCostUnit(object);

    }

    /**
     * @see no.ugland.utransprod.service.CostUnitManager#findByName(java.lang.String)
     */
    public final CostUnit findByName(final String name) {
        return ((CostUnitDAO)dao).findByName(name);
    }

    /**
     * @see no.ugland.utransprod.service.CostUnitManager#lazyLoad(no.ugland.utransprod.model.CostUnit,
     *      no.ugland.utransprod.service.enums.LazyLoadCostUnitEnum[])
     */
   /* public final void lazyLoad(final CostUnit costUnit, final LazyLoadCostUnitEnum[] enums) {
        dao.lazyLoad(costUnit, enums);

    }*/

    /*public void lazyLoad(CostUnit object, Enum[] enums) {
        lazyLoad(object, (LazyLoadCostUnitEnum[])enums);
        
    }*/

    @Override
    protected Serializable getObjectId(CostUnit object) {
        return object.getCostUnitId();
    }

}
