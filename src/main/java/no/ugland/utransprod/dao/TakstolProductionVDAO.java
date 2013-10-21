package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TakstolProductionV;

/**
 * Interface for DAO mot view TAKSTOL_PRODUCTION_V
 * @author atle.brekka
 *
 */
public interface TakstolProductionVDAO extends DAO<TakstolProductionV> {
	/**
	 * Finner alle takstoler som skal produseres
	 * 
	 * @return vegger
	 */
	List<Produceable> findAll();

	/**
	 * Finner takstoler som skal produseres basert op ordrenummer
	 * 
	 * @param orderNr
	 * @return vegg
	 */
	List<Produceable> findByOrderNr(String orderNr);

	/**
	 * Oppdater objekt
	 * @param takstolProductionV 
	 */
	void refresh(Produceable takstolProductionV);

	/**
	 * Finn basert på kundenummer
	 * @param customerNr
	 * @return taktol
	 */
	List<Produceable> findByCustomerNr(Integer customerNr);
	List<Produceable> findByOrderNrAndArticleName(String orderNr,String articleName);

	List<Produceable> findByOrderNrAndProductAreaGroup(String orderNr,
			ProductAreaGroup productAreaGroup);

	List<Produceable> findByCustomerNrAndProductAreaGroup(Integer customerNr,
			ProductAreaGroup productAreaGroup);

	
}
