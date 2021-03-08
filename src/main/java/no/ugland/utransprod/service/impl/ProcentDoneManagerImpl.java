/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.dao.ProcentDoneDAO;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.ProcentDone;
/*    */ import no.ugland.utransprod.service.ProcentDoneManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProcentDoneManagerImpl extends ManagerImpl<ProcentDone> implements ProcentDoneManager {
/*    */    public ProcentDone findByYearWeekOrder(Integer year, Integer week, Order order) {
/* 15 */       return ((ProcentDoneDAO)this.dao).findByYearWeekOrder(year, week, order);
/*    */    }
/*    */ 
/*    */    public List<ProcentDone> findAll() {
/* 19 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */    public List<ProcentDone> findByObject(ProcentDone object) {
/* 23 */       return this.dao.findByExampleLike(object);
/*    */    }
/*    */ 
/*    */    public void refreshObject(ProcentDone object) {
/* 27 */       this.dao.refreshObject(object, object.getProcentDoneId());
/*    */ 
/* 29 */    }
/*    */ 
/*    */    public void removeObject(ProcentDone object) {
/* 32 */       this.dao.removeObject(object.getProcentDoneId());
/*    */ 
/* 34 */    }
/*    */ 
/*    */    public void saveObject(ProcentDone object) throws ProTransException {
/* 37 */       this.dao.saveObject(object);
/*    */ 
/* 39 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(ProcentDone object) {
/* 44 */       return object.getProcentDoneId();
/*    */    }
/*    */ }
