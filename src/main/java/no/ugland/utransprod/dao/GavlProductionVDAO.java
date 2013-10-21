package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.GavlProductionV;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for DAO mot view GAVL_PRODUCTION_V
 * @author atle.brekka
 *
 */
public interface GavlProductionVDAO extends DAO<GavlProductionV> {
	/**
	 * Finner alle vegger som skal produseres
	 * 
	 * @return vegger
	 */
	List<Produceable> findAll();

	/**
	 * Finner vegg som skal produseres basert op ordrenummer
	 * 
	 * @param orderNr
	 * @return vegg
	 */
	List<Produceable> findByOrderNr(String orderNr);

	/**
	 * Oppdater objekt
	 * @param productionV 
	 */
	void refresh(Produceable productionV);

	/**
	 * Finn basert på kundenummer
	 * @param customerNr
	 * @return gavlproduksjon
	 */
	List<Produceable> findByCustomerNr(Integer customerNr);

	List<Produceable> findByCustomerNrAndProductAreaGroup(Integer customerNr,
			ProductAreaGroup productAreaGroup);

	List<Produceable> findByOrderNrAndProductAreaGroup(String orderNr,
			ProductAreaGroup productAreaGroup);
}
