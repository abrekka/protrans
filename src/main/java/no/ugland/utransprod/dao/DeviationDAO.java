package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;

/**
 * Interface for DAO mot tabell DEVIATION
 * 
 * @author atle.brekka
 * 
 */
public interface DeviationDAO extends DAO<Deviation> {
    /**
     * Oppdaterer objekt
     * 
     * @param deviation
     */
    void refreshObject(Deviation deviation);

    /**
     * Finner avvik med gitt jobbfunkjson
     * 
     * @param jobFunction
     * @return avvik
     */
    List<Deviation> findByJobFunction(JobFunction jobFunction);

    /**
     * Lazy laster
     * 
     * @param deviation
     * @param enums
     */
    void lazyLoad(Deviation deviation, LazyLoadDeviationEnum[] enums);

    /**
     * Finner basert på avvik
     * 
     * @param deviation
     * @return avvik
     */
    List<Deviation> findByDeviation(Deviation deviation);

    /**
     * Finnaer alle avvik under en gitt leder
     * 
     * @param applicationUser
     * @return avvik
     */
    List<Deviation> findByManager(final ApplicationUser applicationUser);

    /**
     * Finner avvik til ordre
     * 
     * @param order
     * @return avvik
     */
    List<Deviation> findByOrder(final Order order);

    /**
     * Finner alle med monteringer
     * 
     * @return avvik
     */
    List<Deviation> findAllAssembly();

    List<Deviation> findByResponsible(ApplicationUser applicationUser);

}
