package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.Supplier;

/**
 * Interface for DAO mot tabell ORDER_COST
 * @author atle.brekka
 *
 */
public interface OrderCostDAO extends DAO<OrderCost>{
    List<OrderCost> findBySupplier(Supplier supplier);
}
