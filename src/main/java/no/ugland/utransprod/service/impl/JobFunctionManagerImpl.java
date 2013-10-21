package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.JobFunctionDAO;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.service.JobFunctionManager;

/**
 * Implementasjon av serviceklasse for tabell JOB_FUNCTION.
 * @author atle.brekka
 */
public class JobFunctionManagerImpl extends ManagerImpl<JobFunction>implements JobFunctionManager {
    /**
     * @see no.ugland.utransprod.service.JobFunctionManager#findAll()
     */
    public final List<JobFunction> findAll() {
        return dao.getObjects();
    }

    /**
     * @param object
     * @return funksjoner
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<JobFunction> findByObject(final JobFunction object) {
        return dao.findByExampleLike(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final JobFunction object) {
        ((JobFunctionDAO)dao).refreshObject(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final JobFunction object) {
        dao.removeObject(object.getJobFunctionId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final JobFunction object) {
        dao.saveObject(object);

    }

    /**
     * @see no.ugland.utransprod.service.JobFunctionManager#lazyLoad(no.ugland.utransprod.model.JobFunction,
     *      no.ugland.utransprod.service.enums.LazyLoadJobFunctionEnum[])
     */
   /* public final void lazyLoad(final JobFunction jobFunction,
            final LazyLoadJobFunctionEnum[] enums) {
        dao.lazyLoad(jobFunction, enums);

    }*/

    /**
     * @see no.ugland.utransprod.service.JobFunctionManager#
     * isFunctionManager(no.ugland.utransprod.model.ApplicationUser)
     */
    public final Boolean isFunctionManager(final ApplicationUser user) {
        return ((JobFunctionDAO)dao).isFunctionManager(user);
    }

    /*public void lazyLoad(JobFunction object, Enum[] enums) {
        lazyLoad(object, (LazyLoadJobFunctionEnum[])enums);
        
    }*/

    @Override
    protected Serializable getObjectId(JobFunction object) {
        return object.getJobFunctionId();
    }

}
