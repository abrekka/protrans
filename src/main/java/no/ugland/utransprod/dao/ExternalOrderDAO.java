package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.Order;

/**
 * Interface for DAO mot tabell EXTERNAL_ORDER
 * @author atle.brekka
 *
 */
public interface ExternalOrderDAO extends DAO<ExternalOrder>{
	/**
	 * Oppdaterer ekstern ordre
	 * @param externalOrder
	 */
	void refreshObject(ExternalOrder externalOrder);
	/**
	 * Finner eksterne ordre for en order
	 * @param order
	 * @return eksterne ordre
	 */
	List<ExternalOrder> findByOrder(Order order);
	/**
	 * Lazy laster
	 * @param externalOrder
	 * @param enums
	 */
	//void lazyLoad(ExternalOrder externalOrder,LazyLoadExternalOrderEnum[] enums);
}
