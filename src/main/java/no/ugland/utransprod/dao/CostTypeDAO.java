package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.CostType;

/**
 * Interface for DAO mot tabell COST_TYPE
 * @author atle.brekka
 *
 */
public interface CostTypeDAO extends DAO<CostType>{
	/**
	 * Finner alle kosttyper
	 * @return kosttyper
	 */
	List<CostType> findAll();
	/**
	 * Oppdaterer objekt
	 * @param costType
	 */
	void refreshObject(CostType costType);
	/**
	 * Finner kostnadstype basert på navn
	 * @param name
	 * @return kostnadstype
	 */
	CostType findByName(String name);
}
