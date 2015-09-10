package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.OrderReserveV;

/**
 * Interface for DAO mot view ORDER_RESERVE_V
 * 
 * @author atle.brekka
 * 
 */
public interface OrderReserveVDAO extends DAO<OrderReserveV> {
    /**
     * Finner ordrereserve for gitt produktområde
     * 
     * @param productArea
     * @return ordrereserve
     */
    List<OrderReserveV> findByProductArea(String productArea);
}
