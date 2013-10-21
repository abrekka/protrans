package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;

/**
 * Interface for DAO mot tabell COLLI
 * 
 * @author atle.brekka
 * 
 */
public interface ColliDAO extends DAO<Colli> {
	/**
	 * Finner kolli basert på navn og ordre
	 * 
	 * @param colliName
	 * @param order
	 * @return kolli
	 */
	Colli findByNameAndOrder(String colliName, Order order);

	/**
	 * Oppdaterer objekt
	 * 
	 * @param colli
	 */
	void refreshObject(Colli colli);

	/**
	 * Lazy loader kolli
	 * 
	 * @param colli
	 * @param enums
	 */
	// void lazyLoadColli(Colli colli, LazyLoadColliEnum[] enums);
	/**
	 * Finner basert på etterlevering
	 * 
	 * @param colliName
	 * @param postShipment
	 * @return kolli
	 */
	Colli findByNameAndPostShipment(String colliName, PostShipment postShipment);

	void lazyLoadAll(Colli colli);
}
