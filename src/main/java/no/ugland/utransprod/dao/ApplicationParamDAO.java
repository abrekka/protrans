package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ApplicationParam;


/**
 * Interface for DAO mot APPLICATION_PARAM
 * @author atle.brekka
 *
 */
public interface ApplicationParamDAO extends DAO<ApplicationParam>{
	/**
	 * Finner paramter basert på navn
	 * @param paramName
	 * @return parameter
	 */
	ApplicationParam findByName(String paramName);
	/**
	 * Oppdaterer objekt
	 * @param applicationParam
	 */
	void refreshObject(ApplicationParam applicationParam);
	
}
