/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.CustTrDAO;
/*    */ import no.ugland.utransprod.model.CustTr;
/*    */ import no.ugland.utransprod.service.CustTrManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustTrManagerImpl implements CustTrManager {
/*    */    private CustTrDAO dao;
/*    */ 
/*    */    public final void setCustTrDAO(CustTrDAO aDao) {
/* 17 */       this.dao = aDao;
/* 18 */    }
/*    */ 
/*    */    public List<CustTr> findByOrderNr(String orderNr) {
/* 21 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ }
