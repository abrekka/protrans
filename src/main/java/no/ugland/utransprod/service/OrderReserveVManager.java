package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.OrderReserveV;

/**
 * Interface for serviceklasse mot view ORDER_RESERVE_V
 * @author atle.brekka
 */
public interface OrderReserveVManager {
    String MANAGER_NAME = "orderReserveVManager";

	/**
     * Finner basert på produktområde
     * @param productArea
     * @return ordrereserve
     */
    List<OrderReserveV> findByProductArea(String productArea);
}
