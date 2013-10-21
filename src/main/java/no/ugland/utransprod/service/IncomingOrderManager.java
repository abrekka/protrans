package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Udsalesmall;

/**
 * Interface for serviceklasse som håndterer innkomne ordre fra SuperOffice
 * @author atle.brekka
 */
public interface IncomingOrderManager extends OverviewManager<Order> {
    String MANAGER_NAME = "incomingOrderManager";
	/**
     * Finn basert på ordrenummer
     * @param orderNr
     * @return ordre
     */
    List<Order> findByOrderNr(String orderNr);
    void setCosts(Order incomingOrder)throws ProTransException;
    void setOrderLines(Order incomingOrder,ManagerRepository managerRepository);
    void setCustomerCost(Order incomingOrder,Udsalesmall udsalesmall) ;
}
