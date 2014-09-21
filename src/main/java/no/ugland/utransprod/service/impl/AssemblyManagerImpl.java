package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.AssemblyDAO;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.service.AssemblyManager;

/**
 * Implementasjon av manager for montering.
 * 
 * @author atle.brekka
 */
public class AssemblyManagerImpl extends ManagerImpl<Assembly> implements AssemblyManager {
    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<Assembly> findAll() {
	return dao.getObjects();
    }

    /**
     * @param object
     * @return monteringer
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<Assembly> findByObject(final Assembly object) {
	return dao.findByExampleLike(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final Assembly object) {
	((AssemblyDAO) dao).refreshObject(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final Assembly object) {
	dao.removeObject(object.getAssemblyId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final Assembly object) {
	dao.saveObject(object);

    }

    /**
     * @see no.ugland.utransprod.service.AssemblyManager#
     *      findByAssemblyTeamYearWeek(no.ugland.utransprod.model.AssemblyTeam,
     *      java.lang.Integer, java.lang.Integer)
     */
    public final List<Assembly> findBySupplierYearWeek(final Supplier supplier, final Integer year, final Integer week) {
	return ((AssemblyDAO) dao).findBySupplierYearWeek(supplier, year, week);
    }

    /**
     * @see no.ugland.utransprod.service.AssemblyManager#saveAssembly(no.ugland.utransprod.model.Assembly)
     */
    public final void saveAssembly(final Assembly assembly) {
	dao.saveObject(assembly);

    }

    public void lazyLoad(Assembly object, Enum[] enums) {
	// TODO Auto-generated method stub

    }

    @Override
    protected Serializable getObjectId(Assembly object) {
	return object.getAssemblyId();
    }

    public Assembly merge(Assembly object) {
	return dao.merge(object);
    }

    public List<Assembly> findByYear(Integer year) {
	return ((AssemblyDAO) dao).findByYear(year);
    }

    public Assembly get(Integer assemblyId) {
	return ((AssemblyDAO) dao).getObject(assemblyId);
    }

}
