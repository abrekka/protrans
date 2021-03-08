/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.DelAltDAO;
/*    */ import no.ugland.utransprod.model.DelAlt;
/*    */ import no.ugland.utransprod.service.DelAltManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DelAltManagerImpl implements DelAltManager {
/*    */    private DelAltDAO dao;
/*    */ 
/*    */    public final void setDelAltDAO(DelAltDAO aDao) {
/* 16 */       this.dao = aDao;
/* 17 */    }
/*    */ 
/*    */    public List<DelAlt> finnForProdno(String prodno) {
/* 20 */       return this.dao.finnForProdno(prodno);
/*    */    }
/*    */ }
