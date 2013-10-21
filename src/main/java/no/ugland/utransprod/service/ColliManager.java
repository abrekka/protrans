package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;

/**
 * Interface for serviceklasse mot tabell COLLI
 * 
 * @author atle.brekka
 */
public interface ColliManager extends OverviewManager<Colli> {
	String MANAGER_NAME = "colliManager";

	/**
	 * Finner basert på navn og ordre
	 * 
	 * @param colliName
	 * @param order
	 * @return kolli
	 */
	Colli findByNameAndOrder(String colliName, Order order);

	/**
	 * Lagrer kolli
	 * 
	 * @param colli
	 */
	void saveColli(Colli colli);

	/**
	 * Lazy laster kolli
	 * 
	 * @param colli
	 * @param enums
	 */
	// void lazyLoadColli(Colli colli, LazyLoadColliEnum[] enums);

	/**
	 * Finner basert på navn og etterlevering
	 * 
	 * @param colliName
	 * @param postShipment
	 * @return kolli
	 */
	Colli findByNameAndPostShipment(String colliName, PostShipment postShipment);

	void lazyLoadAll(Colli colli);
}
