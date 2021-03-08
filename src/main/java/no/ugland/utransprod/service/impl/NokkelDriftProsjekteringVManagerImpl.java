/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.NokkelDriftProsjekteringVDAO;
/*    */ import no.ugland.utransprod.model.NokkelDriftProsjekteringV;
/*    */ import no.ugland.utransprod.service.NokkelDriftProsjekteringVManager;
/*    */ import no.ugland.utransprod.util.YearWeek;
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
/*    */ public class NokkelDriftProsjekteringVManagerImpl implements NokkelDriftProsjekteringVManager {
/*    */    private NokkelDriftProsjekteringVDAO dao;
/*    */ 
/*    */    public final void setNokkelDriftProsjekteringVDAO(NokkelDriftProsjekteringVDAO aDao) {
/* 22 */       this.dao = aDao;
/* 23 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<NokkelDriftProsjekteringV> findBetweenYearWeek(YearWeek fromYearWeek, YearWeek toYearWeek, String productArea) {
/* 32 */       return this.dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final NokkelDriftProsjekteringV aggreagateYearWeek(YearWeek currentYearWeek, String productArea) {
/* 41 */       return this.dao.aggreagateYearWeek(currentYearWeek, productArea);
/*    */    }
/*    */ }
