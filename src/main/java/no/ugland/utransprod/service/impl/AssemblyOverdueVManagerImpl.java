/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.AssemblyOverdueVDAO;
/*    */ import no.ugland.utransprod.model.AssemblyOverdueV;
/*    */ import no.ugland.utransprod.service.AssemblyOverdueVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssemblyOverdueVManagerImpl implements AssemblyOverdueVManager {
/*    */    private AssemblyOverdueVDAO dao;
/*    */ 
/*    */    public final void setAssemblyOverdueVDAO(AssemblyOverdueVDAO aDao) {
/* 15 */       this.dao = aDao;
/* 16 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final AssemblyOverdueV getAssemblyOverdueV() {
/* 22 */       return this.dao.getOldest();
/*    */    }
/*    */ }
