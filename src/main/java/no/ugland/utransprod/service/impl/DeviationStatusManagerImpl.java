/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.DeviationStatusDAO;
/*    */ import no.ugland.utransprod.model.DeviationStatus;
/*    */ import no.ugland.utransprod.service.DeviationStatusManager;
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
/*    */ public class DeviationStatusManagerImpl extends ManagerImpl<DeviationStatus> implements DeviationStatusManager {
/*    */    public final List<DeviationStatus> findAll() {
/* 21 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<DeviationStatus> findByObject(DeviationStatus object) {
/* 30 */       return this.dao.findByExampleLike(object);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refreshObject(DeviationStatus object) {
/* 38 */       ((DeviationStatusDAO)this.dao).refreshObject(object);
/*    */ 
/* 40 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeObject(DeviationStatus object) {
/* 47 */       this.dao.removeObject(object.getDeviationStatusId());
/*    */ 
/* 49 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveObject(DeviationStatus object) {
/* 56 */       this.dao.saveObject(object);
/*    */ 
/* 58 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<DeviationStatus> findAllNotForManager() {
/* 64 */       return ((DeviationStatusDAO)this.dao).findAllNotForManager();
/*    */    }
/*    */ 
/*    */    public Integer countUsedByDeviation(DeviationStatus deviationStatus) {
/* 68 */       return ((DeviationStatusDAO)this.dao).countUsedByDeviation(deviationStatus);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(DeviationStatus object) {
/* 74 */       return object.getDeviationStatusId();
/*    */    }
/*    */ 
/*    */    public List<DeviationStatus> findAllForDeviation() {
/* 78 */       return ((DeviationStatusDAO)this.dao).findAllForDeviation();
/*    */    }
/*    */ 
/*    */    public Collection<DeviationStatus> findAllForAccident() {
/* 82 */       return ((DeviationStatusDAO)this.dao).findAllForAccident();
/*    */    }
/*    */ 
/*    */    public Collection<DeviationStatus> findAllNotForManagerForAccident() {
/* 86 */       return ((DeviationStatusDAO)this.dao).findAllNotForManagerForAccident();
/*    */    }
/*    */ 
/*    */    public Collection<DeviationStatus> findAllNotForManagerForDeviation() {
/* 90 */       return ((DeviationStatusDAO)this.dao).findAllNotForManagerForDeviation();
/*    */    }
/*    */ }
