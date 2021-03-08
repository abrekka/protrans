/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import no.ugland.utransprod.dao.SumOrderReadyVDAO;
/*    */ import no.ugland.utransprod.model.SumOrderReadyV;
/*    */ import no.ugland.utransprod.service.SumOrderReadyVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SumOrderReadyVManagerImpl implements SumOrderReadyVManager {
/*    */    private SumOrderReadyVDAO dao;
/*    */    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");
/*    */ 
/*    */    public final void setSumOrderReadyVDAO(SumOrderReadyVDAO aDao) {
/* 21 */       this.dao = aDao;
/* 22 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final SumOrderReadyV findByDate(Date date) {
/* 28 */       return this.dao.findByDate(DATE_FORMAT.format(date));
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final SumOrderReadyV findSumByWeek(Integer year, Integer week) {
/* 36 */       return this.dao.findSumByWeek(year, week);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final SumOrderReadyV findByDateAndProductAreaGroupName(Date date) {
/* 44 */       return this.findByDate(date);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final SumOrderReadyV findSumByWeekAndProductAreaGroupName(Integer year, Integer week) {
/* 53 */       return this.findSumByWeek(year, week);
/*    */    }
/*    */ }
