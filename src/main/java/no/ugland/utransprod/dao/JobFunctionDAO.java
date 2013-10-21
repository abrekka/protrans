package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;

/**
 * Interface for DAO mot tabell JOB_FUNCTION
 * 
 * @author atle.brekka
 * 
 */
public interface JobFunctionDAO extends DAO<JobFunction> {
	/**
	 * Oppdater objekt
	 * 
	 * @param jobFunction
	 */
	void refreshObject(JobFunction jobFunction);

	/**
	 * Lazy lasting av objekter
	 * 
	 * @param jobFunction
	 * @param enums
	 */
	//void lazyLoad(JobFunction jobFunction, LazyLoadJobFunctionEnum[] enums);
	/**
	 * Sjkker om bruker er manager for en funksjon
	 * @param user
	 * @return true dersom bruker er manager
	 */
	Boolean isFunctionManager(ApplicationUser user);
}
