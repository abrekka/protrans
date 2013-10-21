package no.ugland.utransprod.service;

import java.util.Collection;
import java.util.List;

import no.ugland.utransprod.model.DeviationStatus;

/**
 * Interface for serviceklasse mot tabell DEVIATION_STATUS
 * @author atle.brekka
 */
public interface DeviationStatusManager extends
        OverviewManager<DeviationStatus> {
    String MANAGER_NAME = "deviationStatusManager";
	/**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    List<DeviationStatus> findAll();

    /**
     * Finner alle som ikke gjelder for ansvarlig
     * @return avviksstatuser
     */
    List<DeviationStatus> findAllNotForManager();
    Integer countUsedByDeviation(DeviationStatus deviationStatus);

	List<DeviationStatus> findAllForDeviation();

	Collection<DeviationStatus> findAllForAccident();

	Collection<DeviationStatus> findAllNotForManagerForAccident();

	Collection<DeviationStatus> findAllNotForManagerForDeviation();
}
