/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.TransportSumVDAO;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.model.TransportSumV;
/*    */ import no.ugland.utransprod.service.TransportSumVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransportSumVManagerImpl implements TransportSumVManager {
/*    */    private TransportSumVDAO dao;
/*    */ 
/*    */    public final void setTransportSumVDAO(TransportSumVDAO aDao) {
/* 19 */       this.dao = aDao;
/* 20 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final TransportSumV findYearAndWeek(Integer currentYear, Integer currentWeek) {
/* 27 */       return this.dao.findYearAndWeek(currentYear, currentWeek);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final TransportSumV findYearAndWeekByProductAreaGroup(Integer currentYear, Integer currentWeek, ProductAreaGroup productAreaGroup) {
/* 37 */       return productAreaGroup.getProductAreaGroupName().length() == 0 ? this.findYearAndWeek(currentYear, currentWeek) : this.dao.findYearAndWeekByProductAreaGroup(currentYear, currentWeek, productAreaGroup);
/*    */    }
/*    */ }
