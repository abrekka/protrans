package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.VeggProductionV;

/**
 * Interface for DAO mot view VEGG_PRODUCTION_V
 * 
 * @author atle.brekka
 * 
 */
public interface VeggProductionVDAO extends DAO<VeggProductionV> {
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
	 * 
	 * @param veggProductionV
	 */
	void refresh(Produceable veggProductionV);
	/**
	 * Finner basert på kundenummer
	 * @param customerNr
	 * @return veggproduksjon
	 */
	List<Produceable> findByCustomerNr(Integer customerNr);

	List<Produceable> findByCustomerNrAndProductAreaGroup(Integer customerNr,
			ProductAreaGroup productAreaGroup);

	List<Produceable> findByOrderNrAndProductAreaGroup(String orderNr,
			ProductAreaGroup productAreaGroup);
}
