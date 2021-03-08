/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.CountyDAO;
/*    */ import no.ugland.utransprod.model.County;
/*    */ import no.ugland.utransprod.service.AreaManager;
/*    */ import no.ugland.utransprod.service.CountyManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CountyManagerImpl implements CountyManager {
/*    */    private CountyDAO dao;
/*    */    private AreaManager areaManager;
/*    */ 
/*    */    public final void setCountyDAO(CountyDAO aDao) {
/* 17 */       this.dao = aDao;
/* 18 */    }
/*    */    public final void setAreaManager(AreaManager aAreaManager) {
/* 20 */       this.areaManager = aAreaManager;
/* 21 */    }
/*    */ 
/*    */    public final County load(String countyNr) {
/* 24 */       return (County)this.dao.getObject(countyNr);
/*    */    }
/*    */ 
/*    */    public final void removeAll() {
/* 28 */       this.areaManager.removeAll();
/* 29 */       this.dao.removeAll();
/*    */ 
/* 31 */    }
/*    */ 
/*    */    public final void saveCounty(County county) {
/* 34 */       this.dao.saveObject(county);
/*    */ 
/* 36 */    }
/*    */ }
