package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.PreventiveActionDAO;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.service.PreventiveActionManager;

/**
 * Implementasjon av serviceklasse for tabell PREVENTIVE_ACTION.
 * @author atle.brekka
 */
public class PreventiveActionManagerImpl extends ManagerImpl<PreventiveAction>implements PreventiveActionManager {
    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<PreventiveAction> findAll() {
        return ((PreventiveActionDAO)dao).findAll();
    }

    /**
     * @param object
     * @return korrigerende tiltak
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<PreventiveAction> findByObject(final PreventiveAction object) {
        return dao.findByExampleLike(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final PreventiveAction object) {
        ((PreventiveActionDAO)dao).refreshPreventiveAction(object);

    }

    /**
     * Fjerner korrigerende tiltak.
     * @param preventiveAction
     */
    public final void removePreventiveAction(final PreventiveAction preventiveAction) {
        dao.removeObject(preventiveAction.getPreventiveActionId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final PreventiveAction object) {
        removePreventiveAction(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final PreventiveAction object) {
        savePreventiveAction(object);

    }

    /**
     * Lagrer korrigerende tiltak.
     * @param preventiveAction
     */
    public final void savePreventiveAction(final PreventiveAction preventiveAction) {
        dao.saveObject(preventiveAction);

    }

    /**
     * @see no.ugland.utransprod.service.PreventiveActionManager#
     * lazyLoad(no.ugland.utransprod.model.PreventiveAction,
     *      no.ugland.utransprod.service.enums.LazyLoadPreventiveActionEnum[])
     */
   /* public final void lazyLoad(final PreventiveAction preventiveAction,
            final LazyLoadPreventiveActionEnum[] enums) {
        dao.lazyLoad(preventiveAction, enums);

    }*/

    /**
     * @see no.ugland.utransprod.service.PreventiveActionManager#
     * findAllOpenByFunctionAndCategory(no.ugland.utransprod.model.JobFunction,
     *      no.ugland.utransprod.model.FunctionCategory)
     */
    public final List<PreventiveAction> findAllOpenByFunctionAndCategory(
            final JobFunction jobFunction, final FunctionCategory functionCategory) {
        return ((PreventiveActionDAO)dao).findAllOpenByFunctionAndCategory(jobFunction,
                functionCategory);
    }

   /*public void lazyLoad(PreventiveAction object, Enum[] enums) {
        lazyLoad(object,(LazyLoadPreventiveActionEnum[]) enums);
        
    }*/

    @Override
    protected Serializable getObjectId(PreventiveAction object) {
        return object.getPreventiveActionId();
    }

}
