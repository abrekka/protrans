package no.ugland.utransprod.service;

import no.ugland.utransprod.model.OrderCost;

/**
 * Interface for serviceklasse mot tabell ORDER_COST
 * @author atle.brekka
 */
public interface OrderCostManager {
    /**
     * Lagrer kostnad
     * @param orderCost
     */
    void saveOrderCost(OrderCost orderCost);
}
