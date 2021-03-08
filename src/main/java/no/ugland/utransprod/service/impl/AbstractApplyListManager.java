/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.OrderLineDAO;
/*    */ import no.ugland.utransprod.model.Ordln;
/*    */ import no.ugland.utransprod.service.IApplyListManager;
/*    */ 
/*    */ public abstract class AbstractApplyListManager<T> implements IApplyListManager<T> {
/*    */    protected OrderLineDAO orderLineDAO;
/*    */ 
/*    */    public final void setOrderLineDAO(OrderLineDAO aDao) {
/* 11 */       this.orderLineDAO = aDao;
/* 12 */    }
/*    */ 
/*    */    public Ordln findOrdlnByOrderLine(Integer orderLineId) {
/* 15 */       return this.orderLineDAO.findOrdlnByOrderLine(orderLineId);
/*    */    }
/*    */ }
