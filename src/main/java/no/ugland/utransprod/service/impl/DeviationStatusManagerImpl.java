package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import no.ugland.utransprod.dao.DeviationStatusDAO;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.service.DeviationStatusManager;

/**
 * Implementasjon av serviceklasse for tabell DEVIATION_STATUS.
 * @author atle.brekka
 */
public class DeviationStatusManagerImpl extends ManagerImpl<DeviationStatus>implements DeviationStatusManager {

    /**
     * @see no.ugland.utransprod.service.DeviationStatusManager#findAll()
     */
    public final List<DeviationStatus> findAll() {
        return dao.getObjects();
    }

    /**
     * @param object
     * @return statuser
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<DeviationStatus> findByObject(final DeviationStatus object) {
        return dao.findByExampleLike(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final DeviationStatus object) {
        ((DeviationStatusDAO)dao).refreshObject(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final DeviationStatus object) {
        dao.removeObject(object.getDeviationStatusId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final DeviationStatus object) {
        dao.saveObject(object);

    }

    /**
     * @see no.ugland.utransprod.service.DeviationStatusManager#findAllNotForManager()
     */
    public final List<DeviationStatus> findAllNotForManager() {
        return ((DeviationStatusDAO)dao).findAllNotForManager();
    }

    public Integer countUsedByDeviation(final DeviationStatus deviationStatus) {
        return ((DeviationStatusDAO)dao).countUsedByDeviation(deviationStatus);
    }


    @Override
    protected Serializable getObjectId(DeviationStatus object) {
        return object.getDeviationStatusId();
    }

	public List<DeviationStatus> findAllForDeviation() {
		return ((DeviationStatusDAO)dao).findAllForDeviation();
	}

	public Collection<DeviationStatus> findAllForAccident() {
		return ((DeviationStatusDAO)dao).findAllForAccident();
	}

	public Collection<DeviationStatus> findAllNotForManagerForAccident() {
		return ((DeviationStatusDAO)dao).findAllNotForManagerForAccident();
	}

	public Collection<DeviationStatus> findAllNotForManagerForDeviation() {
		return ((DeviationStatusDAO)dao).findAllNotForManagerForDeviation();
	}

}
