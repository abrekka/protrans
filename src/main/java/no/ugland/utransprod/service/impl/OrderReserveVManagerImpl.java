package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.OrderReserveVDAO;
import no.ugland.utransprod.model.OrderReserveV;
import no.ugland.utransprod.service.OrderReserveVManager;

/**
 * Implementasjon av serviceklasse for view ORDER_RESERVE_V.
 * 
 * @author atle.brekka
 */
public class OrderReserveVManagerImpl implements OrderReserveVManager {
    private OrderReserveVDAO dao;

    /**
     * @param aDao
     */
    public final void setOrderReserveVDAO(final OrderReserveVDAO aDao) {
	this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.OrderReserveVManager#findByProductArea(java.lang.String)
     */
    public final List<OrderReserveV> findByProductArea(final String productArea) {
	return dao.findByProductArea(productArea);
    }

}
