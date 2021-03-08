/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.AssemblyVDAO;
/*    */ import no.ugland.utransprod.model.AssemblyV;
/*    */ import no.ugland.utransprod.service.AssemblyVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssemblyVManagerImpl implements AssemblyVManager {
/*    */    private AssemblyVDAO dao;
/*    */ 
/*    */    public final void setAssemblyVDAO(AssemblyVDAO aDao) {
/* 14 */       this.dao = aDao;
/* 15 */    }
/*    */ 
/*    */    public List<AssemblyV> findByYear(int aar) {
/* 18 */       return this.dao.findByYear(aar);
/*    */    }
/*    */ }
