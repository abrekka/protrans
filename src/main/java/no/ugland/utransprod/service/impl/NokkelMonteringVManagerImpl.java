/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.NokkelMonteringVDAO;
/*    */ import no.ugland.utransprod.model.NokkelMonteringV;
/*    */ import no.ugland.utransprod.service.NokkelMonteringVManager;
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
/*    */ public class NokkelMonteringVManagerImpl implements NokkelMonteringVManager {
/*    */    private NokkelMonteringVDAO dao;
/*    */ 
/*    */    public final void setNokkelMonteringVDAO(NokkelMonteringVDAO aDao) {
/* 21 */       this.dao = aDao;
/* 22 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<NokkelMonteringV> findBetweenYearWeek(YearWeek fromYearWeek, YearWeek toYearWeek, String productArea) {
/* 31 */       return this.dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final NokkelMonteringV aggreagateYearWeek(YearWeek currentYearWeek, String productArea) {
/* 41 */       return this.dao.aggreagateYearWeek(currentYearWeek, productArea);
/*    */    }
/*    */ }
