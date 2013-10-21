package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.OrderCostDAO;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.service.OrderCostManager;

/**
 * Implementasjon av serviceklasse for tabell ORDER_COST.
 * @author atle.brekka
 */
public class OrderCostManagerImpl implements OrderCostManager {
    private OrderCostDAO dao;

    /**
     * @param aDao
     */
    public final void setOrderCostDAO(final OrderCostDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.OrderCostManager#
     * saveOrderCost(no.ugland.utransprod.model.OrderCost)
     */
    public final void saveOrderCost(final OrderCost orderCost) {
        dao.saveObject(orderCost);

    }

}
