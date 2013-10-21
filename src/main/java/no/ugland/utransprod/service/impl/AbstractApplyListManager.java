package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.OrderLineDAO;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.service.IApplyListManager;

public abstract class AbstractApplyListManager<T> implements IApplyListManager<T>{
    protected OrderLineDAO orderLineDAO;
    
    public final void setOrderLineDAO(final OrderLineDAO aDao) {
        this.orderLineDAO = aDao;
    }
    
    public Ordln findOrdlnByOrderLine(Integer orderLineId) {
        return orderLineDAO.findOrdlnByOrderLine(orderLineId);
    }
}
