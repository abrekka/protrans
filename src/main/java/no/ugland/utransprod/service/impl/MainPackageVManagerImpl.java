/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.MainPackageVDAO;
/*    */ import no.ugland.utransprod.model.MainPackageV;
/*    */ import no.ugland.utransprod.service.MainPackageVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MainPackageVManagerImpl implements MainPackageVManager {
/*    */    private MainPackageVDAO dao;
/*    */ 
/*    */    public final void setMainPackageVDAO(MainPackageVDAO aDao) {
/* 20 */       this.dao = aDao;
/* 21 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<MainPackageV> findAll() {
/* 27 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refresh(MainPackageV mainPackageV) {
/* 34 */       this.dao.refresh(mainPackageV);
/*    */ 
/* 36 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final MainPackageV findByOrderNr(String orderNr) {
/* 42 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final MainPackageV findByCustomerNr(Integer customerNr) {
/* 49 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ }
