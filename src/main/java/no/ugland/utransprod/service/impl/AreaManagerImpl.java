/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.AreaDAO;
/*    */ import no.ugland.utransprod.dao.TransportCostDAO;
/*    */ import no.ugland.utransprod.model.Area;
/*    */ import no.ugland.utransprod.service.AreaManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AreaManagerImpl implements AreaManager {
/*    */    private AreaDAO dao;
/*    */    private TransportCostDAO transportCostDAO;
/*    */ 
/*    */    public final void setAreaDAO(AreaDAO aDao) {
/* 18 */       this.dao = aDao;
/* 19 */    }
/*    */ 
/*    */    public final void setTransportCostDAO(TransportCostDAO aDao) {
/* 22 */       this.transportCostDAO = aDao;
/* 23 */    }
/*    */ 
/*    */    public final Area load(String areaCode) {
/* 26 */       return (Area)this.dao.getObject(areaCode);
/*    */    }
/*    */ 
/*    */    public final void removeAll() {
/* 30 */       this.transportCostDAO.removeAll();
/* 31 */       this.dao.removeAll();
/*    */ 
/* 33 */    }
/*    */ 
/*    */    public final void saveArea(Area area) {
/* 36 */       this.dao.saveObject(area);
/*    */ 
/* 38 */    }
/*    */ 
/*    */    public Area findByAreaCode(String areaCode) {
/* 41 */       return this.dao.findByAreaCode(areaCode);
/*    */    }
/*    */ }
