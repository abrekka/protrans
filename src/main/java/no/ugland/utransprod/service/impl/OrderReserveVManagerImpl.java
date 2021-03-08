/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.OrderReserveVDAO;
/*    */ import no.ugland.utransprod.model.OrderReserveV;
/*    */ import no.ugland.utransprod.service.OrderReserveVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OrderReserveVManagerImpl implements OrderReserveVManager {
/*    */    private OrderReserveVDAO dao;
/*    */ 
/*    */    public final void setOrderReserveVDAO(OrderReserveVDAO aDao) {
/* 21 */       this.dao = aDao;
/* 22 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<OrderReserveV> findByProductArea(String productArea) {
/* 28 */       return this.dao.findByProductArea(productArea);
/*    */    }
/*    */ }
