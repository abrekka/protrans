/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.OrderCostDAO;
/*    */ import no.ugland.utransprod.model.OrderCost;
/*    */ import no.ugland.utransprod.service.OrderCostManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OrderCostManagerImpl implements OrderCostManager {
/*    */    private OrderCostDAO dao;
/*    */ 
/*    */    public final void setOrderCostDAO(OrderCostDAO aDao) {
/* 18 */       this.dao = aDao;
/* 19 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveOrderCost(OrderCost orderCost) {
/* 26 */       this.dao.saveObject(orderCost);
/*    */ 
/* 28 */    }
/*    */ }
