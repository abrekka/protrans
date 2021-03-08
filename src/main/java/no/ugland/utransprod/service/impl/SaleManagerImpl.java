/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.SaleDAO;
/*    */ import no.ugland.utransprod.service.SaleManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SaleManagerImpl implements SaleManager {
/*    */    private SaleDAO dao;
/*    */ 
/*    */    public final void setSaleDAO(SaleDAO aDao) {
/* 13 */       this.dao = aDao;
/* 14 */    }
/*    */ }
