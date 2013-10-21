package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.CostUnit;

/**
 * Interface for DAO mot tabell COST?UNIT
 * 
 * @author atle.brekka
 * 
 */
public interface CostUnitDAO extends DAO<CostUnit> {
	/**
	 * Finner alle kostnadsenhet
	 * 
	 * @return kostnadsenheter
	 */
	List<CostUnit> findAll();

	/**
	 * Oppdaterer objekt
	 * 
	 * @param costUnit
	 */
	void refreshObject(CostUnit costUnit);

	/**
	 * Finner kostnadsenhet basert på navn
	 * 
	 * @param name
	 * @return kostnadsenhet
	 */
	CostUnit findByName(String name);

	/**
	 * Lasy laster kostnadsenhet
	 * 
	 * @param costUnit
	 * @param enums
	 */
	//void lazyLoad(CostUnit costUnit, LazyLoadCostUnitEnum[] enums);
}
