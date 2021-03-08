/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.NokkelSalgVDAO;
/*    */ import no.ugland.utransprod.model.NokkelSalgV;
/*    */ import no.ugland.utransprod.service.NokkelSalgVManager;
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
/*    */ public class NokkelSalgVManagerImpl implements NokkelSalgVManager {
/*    */    private NokkelSalgVDAO dao;
/*    */ 
/*    */    public final void setNokkelSalgVDAO(NokkelSalgVDAO aDao) {
/* 21 */       this.dao = aDao;
/* 22 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<NokkelSalgV> findByYearWeek(Integer year, Integer week, String productArea) {
/* 30 */       return this.dao.findByYearWeek(year, week, productArea);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<NokkelSalgV> findBetweenYearWeek(YearWeek fromYearWeek, YearWeek toYearWeek, String productArea) {
/* 40 */       return this.dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final NokkelSalgV aggreagateYearWeek(YearWeek currentYearWeek, String productArea) {
/* 50 */       return this.dao.aggreagateYearWeek(currentYearWeek, productArea);
/*    */    }
/*    */ }
