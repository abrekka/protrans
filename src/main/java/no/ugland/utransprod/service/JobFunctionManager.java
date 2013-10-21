package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;

/**
 * Interface for serviceklasse mot tabell JOB_FUNCTION
 * @author atle.brekka
 */
public interface JobFunctionManager extends OverviewManager<JobFunction> {
    String MANAGER_NAME = "jobFunctionManager";

	/**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    List<JobFunction> findAll();

    /**
     * Lazy laster funksjon
     * @param jobFunction
     * @param enums
     */
    //void lazyLoad(JobFunction jobFunction, LazyLoadJobFunctionEnum[] enums);

    /**
     * Sjekker om bruker er funksjonsleder
     * @param user
     * @return true dersom funksjonsleder
     */
    Boolean isFunctionManager(ApplicationUser user);
}
