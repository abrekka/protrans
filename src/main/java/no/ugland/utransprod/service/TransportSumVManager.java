package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TransportSumV;

/**
 * Interface for serviceklasse mot view TRANSPORT_SUM_V
 * @author atle.brekka
 */
public interface TransportSumVManager {
    String MANAGER_NAME = "transportSumVManager";

	/**
     * Finner sum for gjeldende uke
     * @param currentYear
     * @param currentWeek
     * @return transportsum
     */
    TransportSumV findYearAndWeek(Integer currentYear, Integer currentWeek);

    /**
     * Finner sum for uke og produktområdegruppe
     * @param currentYear
     * @param currentWeek
     * @param productAreaGroup
     * @return transportsum
     */
    TransportSumV findYearAndWeekByProductAreaGroup(Integer currentYear,
            Integer currentWeek, ProductAreaGroup productAreaGroup);
}
