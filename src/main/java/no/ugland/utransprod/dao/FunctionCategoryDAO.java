package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.FunctionCategory;

/**
 * Interface for DAO mot tabell FUNCTION_CATEGORY
 * 
 * @author atle.brekka
 * 
 */
public interface FunctionCategoryDAO extends DAO<FunctionCategory> {
	/**
	 * Oppdater objekt
	 * 
	 * @param functionCategory
	 */
	void refreshObject(FunctionCategory functionCategory);

}
