package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.SumOrderReadyV;

/**
 * Interface for DAO mot view SUM_ORDER_V
 * 
 * @author atle.brekka
 * 
 */
public interface SumOrderReadyVDAO extends DAO<SumOrderReadyV> {
	/**
	 * Finner pakket verdi for gitt dato
	 * 
	 * @param dateString
	 * @return pakket verdi
	 */
	SumOrderReadyV findByDate(String dateString);

	/**
	 * Finner pakket verdi for gitt uke
	 * 
	 * @param year
	 * 
	 * @param week
	 * @return pakket verdi
	 */
	SumOrderReadyV findSumByWeek(Integer year, Integer week);

	/**
	 * Finner pakket verdi for gitt dato og produktområdegruppe
	 * 
	 * @param dateString
	 * @param productAreaGroupName
	 * @return pakket verdi
	 */
	SumOrderReadyV findByDateAndProductAreaGroupName(String dateString,
			String productAreaGroupName);

	/**
	 * Finner pakket verdi for gitt uke og produktområdegruppe
	 * 
	 * @param year
	 * @param week
	 * @param productAreaGroupName
	 * @return pakket verdi
	 */
	SumOrderReadyV findSumByWeekAndProductAreaGroupName(Integer year,
			Integer week, String productAreaGroupName);
}
