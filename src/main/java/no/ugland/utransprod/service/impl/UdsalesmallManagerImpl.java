/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.UdsalesmallDAO;
/*    */ import no.ugland.utransprod.model.Udsalesmall;
/*    */ import no.ugland.utransprod.service.UdsalesmallManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UdsalesmallManagerImpl implements UdsalesmallManager {
/*    */    private UdsalesmallDAO dao;
/*    */ 
/*    */    public final void setUdsalesmallDAO(UdsalesmallDAO aDao) {
/* 14 */       this.dao = aDao;
/* 15 */    }
/*    */ 
/*    */    public Udsalesmall findByOrderNr(String orderNr) {
/* 18 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ }
