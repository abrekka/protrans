package no.ugland.utransprod.service;

import java.util.Date;

import no.ugland.utransprod.model.SumOrderReadyV;

/**
 * Interface for serviceklasse mot view SUM_ORDER_REDY_V
 * 
 * @author atle.brekka
 */
public interface SumOrderReadyVManager {
    String MANAGER_NAME = "sumOrderReadyVManager";

    /**
     * Finner for gitt dato
     * 
     * @param date
     * @return ordre klare
     */
    SumOrderReadyV findByDate(Date date);

    /**
     * Finner sum for gitt uke
     * 
     * @param year
     * @param week
     * @return sum ordre klare
     */
    SumOrderReadyV findSumByWeek(Integer year, Integer week);

    /**
     * Finner for gitt dato og produktområdegruppe
     * 
     * @param date
     * @param productAreaGroupName
     * @return sum
     */
    SumOrderReadyV findByDateAndProductAreaGroupName(Date date);

    /**
     * Finner for gitt uke og produktområdegruppe
     * 
     * @param year
     * @param week
     * @param productAreaGroupName
     * @return sum
     */
    SumOrderReadyV findSumByWeekAndProductAreaGroupName(Integer year, Integer week);
}
