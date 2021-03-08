/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.NokkelTransportVDAO;
/*    */ import no.ugland.utransprod.model.NokkelTransportV;
/*    */ import no.ugland.utransprod.service.NokkelTransportVManager;
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
/*    */ public class NokkelTransportVManagerImpl implements NokkelTransportVManager {
/*    */    private NokkelTransportVDAO dao;
/*    */ 
/*    */    public final void setNokkelTransportVDAO(NokkelTransportVDAO aDao) {
/* 21 */       this.dao = aDao;
/* 22 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<NokkelTransportV> findBetweenYearWeek(YearWeek fromYearWeek, YearWeek toYearWeek, String productArea) {
/* 31 */       return this.dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final NokkelTransportV aggreagateYearWeek(YearWeek currentYearWeek, String productArea) {
/* 41 */       return this.dao.aggreagateYearWeek(currentYearWeek, productArea);
/*    */    }
/*    */ }
