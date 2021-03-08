/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.model.Accident;
/*    */ import no.ugland.utransprod.service.AccidentManager;
/*    */ 
/*    */ 
/*    */ public class AccidentManagerImpl extends ManagerImpl<Accident> implements AccidentManager {
/*    */    public final void saveAccident(Accident accident) {
/* 11 */       this.dao.saveObject(accident);
/*    */ 
/* 13 */    }
/*    */ 
/*    */    public final void removeAccident(Accident accident) {
/* 16 */       this.dao.removeObject(accident.getAccidentId());
/*    */ 
/* 18 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Accident> findAll() {
/* 24 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */    public final List<Accident> findByObject(Accident accident) {
/* 28 */       return this.dao.findByExampleLike(accident);
/*    */    }
/*    */ 
/*    */    public final void removeObject(Accident accident) {
/* 32 */       this.removeAccident(accident);
/*    */ 
/* 34 */    }
/*    */ 
/*    */    public final void saveObject(Accident accident) {
/* 37 */       this.saveAccident(accident);
/*    */ 
/* 39 */    }
/*    */ 
/*    */    public final void refreshObject(Accident accident) {
/* 42 */       this.dao.refreshObject(accident, accident.getAccidentId());
/*    */ 
/* 44 */    }
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
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(Accident object) {
/* 58 */       return object.getAccidentId();
/*    */    }
/*    */ }
