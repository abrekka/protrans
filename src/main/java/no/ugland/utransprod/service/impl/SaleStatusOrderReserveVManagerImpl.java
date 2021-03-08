/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.SaleStatusOrderReserveVDAO;
/*    */ import no.ugland.utransprod.model.ProductArea;
/*    */ import no.ugland.utransprod.model.SaleStatusOrderReserveV;
/*    */ import no.ugland.utransprod.service.SaleStatusOrderReserveVManager;
/*    */ 
/*    */ public class SaleStatusOrderReserveVManagerImpl implements SaleStatusOrderReserveVManager {
/*    */    private SaleStatusOrderReserveVDAO dao;
/*    */ 
/*    */    public SaleStatusOrderReserveV findByProductArea(ProductArea productArea) {
/* 12 */       return this.dao.findByProductArea(productArea);
/*    */    }
/*    */ 
/*    */    public void setSaleStatusOrderReserveVDAO(SaleStatusOrderReserveVDAO saleStatusOrderReserveVDAO) {
/* 16 */       this.dao = saleStatusOrderReserveVDAO;
/* 17 */    }
/*    */ }
