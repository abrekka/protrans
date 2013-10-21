package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TaksteinSkarpnesV;

/**
 * Interface for DAo mot view TAKSTEIN_SKARPNES_V
 * 
 * @author atle.brekka
 * 
 */
public interface TaksteinSkarpnesVDAO extends DAO<TaksteinSkarpnesV> {
	/**
	 * Finner alle
	 * 
	 * @return takstein
	 */
	List<Produceable> findAll();

	/**
	 * Finner takstein som skal bestilles basert op ordrenummer
	 * 
	 * @param orderNr
	 * @return vegg
	 */
	List<Produceable> findByOrderNr(String orderNr);

	/**
	 * Oppdater objekt
	 * 
	 * @param taksteinSkarpnesV
	 */
	void refresh(Produceable taksteinSkarpnesV);

	/**
	 * Finn basert på kundenummer
	 * 
	 * @param customerNr
	 * @return taktol
	 */
	List<Produceable> findByCustomerNr(Integer customerNr);

	List<Produceable> findByCustomerNrAndProductAreaGroup(Integer customerNr,
			ProductAreaGroup productAreaGroup);

	List<Produceable> findByOrderNrAndProductAreaGroup(String orderNr,
			ProductAreaGroup productAreaGroup);
}
