package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.FunctionCategoryDAO;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.service.FunctionCategoryManager;

/**
 * Implementasjon av serviceklasse for tabell FUNCTION_CATEGORY.
 * @author atle.brekka
 */
public class FunctionCategoryManagerImpl extends ManagerImpl<FunctionCategory>implements FunctionCategoryManager {
    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<FunctionCategory> findAll() {
        return dao.getObjects();
    }

    /**
     * @param object
     * @return kategorier
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<FunctionCategory> findByObject(final FunctionCategory object) {
        return dao.findByExampleLike(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final FunctionCategory object) {
        ((FunctionCategoryDAO)dao).refreshObject(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final FunctionCategory object) {
        dao.removeObject(object.getFunctionCategoryId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final FunctionCategory object) {
        dao.saveObject(object);

    }


    @Override
    protected Serializable getObjectId(FunctionCategory object) {
        return object.getFunctionCategoryId();
    }
}
