package no.ugland.utransprod.gui.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;

/**
 * Interface for objekter som kan pakkes
 * 
 * @author atle.brekka
 * 
 */
public interface Packable {
	/**
	 * @return kollier
	 */
	List<Colli> getColliList();

	/**
	 * @return ordrelinjer
	 */
	List<OrderLine> getOrderLineList();

	/**
	 * Legger til kolli
	 * 
	 * @param colli
	 */
	void addColli(Colli colli);

	/**
	 * Setter kollier klare
	 * 
	 * @param colliesDone
	 */
	void setColliesDone(Integer colliesDone);

	/**
	 * @return dato for klargjort
	 */
	Date getOrderReady();

	/**
	 * @return dato for komplett
	 */
	Date getOrderComplete();

	/**
	 * Fjerner kolli
	 * 
	 * @param colli
	 */
	boolean removeColli(Colli colli);
    Transportable getTransportable();
    List<OrderLine> getOwnOrderLines();

	Integer getProbability();

	Order getOrder();

	PostShipment getPostShipment();

	void setDefaultColliesGenerated(Integer i);

	String getManagerName();

	Integer getDefaultColliesGenerated();

	Set<Colli> getCollies();
}
