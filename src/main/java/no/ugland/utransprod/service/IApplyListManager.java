package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for serviceklasser som brukes ved produksjon og pakking
 * @author atle.brekka
 * @param <T>
 */
public interface IApplyListManager<T> {
    /**
     * Finn alle
     * @return artikler til pakking eller produksjon
     */
    List<T> findAllApplyable();

    /**
     * Finn basert på ordrenummer
     * @param orderNr
     * @return artikler
     */
    List<T> findApplyableByOrderNr(String orderNr);

    /**
     * Finn basert på kundenummer
     * @param customerNr
     * @return artikler
     */
    List<T> findApplyableByCustomerNr(Integer customerNr);

    /**
     * Oppdater
     * @param object
     */
    void refresh(T object);
    Ordln findOrdlnByOrderLine(Integer orderLineId);

	List<T> findApplyableByOrderNrAndProductAreaGroup(String orderNr,
			ProductAreaGroup productAreaGroup);

	List<T> findApplyableByCustomerNrAndProductAreaGroup(Integer valueOf,
			ProductAreaGroup productAreaGroup);

	
}
