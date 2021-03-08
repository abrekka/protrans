/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.NokkelOkonomiVDAO;
/*    */ import no.ugland.utransprod.model.NokkelOkonomiV;
/*    */ import no.ugland.utransprod.service.NokkelOkonomiVManager;
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
/*    */ public class NokkelOkonomiVManagerImpl implements NokkelOkonomiVManager {
/*    */    private NokkelOkonomiVDAO dao;
/*    */ 
/*    */    public final void setNokkelOkonomiVDAO(NokkelOkonomiVDAO aDao) {
/* 21 */       this.dao = aDao;
/* 22 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<NokkelOkonomiV> findBetweenYearWeek(YearWeek fromYearWeek, YearWeek toYearWeek, String productArea) {
/* 31 */       return this.dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final NokkelOkonomiV aggreagateYearWeek(YearWeek currentYearWeek, String productArea) {
/* 41 */       return this.dao.aggreagateYearWeek(currentYearWeek, productArea);
/*    */    }
/*    */ }
