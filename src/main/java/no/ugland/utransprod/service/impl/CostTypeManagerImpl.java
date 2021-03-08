/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.CostTypeDAO;
/*    */ import no.ugland.utransprod.model.CostType;
/*    */ import no.ugland.utransprod.service.CostTypeManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CostTypeManagerImpl extends ManagerImpl<CostType> implements CostTypeManager {
/*    */    public final List<CostType> findAll() {
/* 19 */       return ((CostTypeDAO)this.dao).findAll();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<CostType> findByCostType(CostType costType) {
/* 28 */       return this.dao.findByExampleLike(costType);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<CostType> findByObject(CostType object) {
/* 37 */       return this.findByCostType(object);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refreshObject(CostType object) {
/* 45 */       ((CostTypeDAO)this.dao).refreshObject(object);
/*    */ 
/* 47 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeObject(CostType object) {
/* 54 */       this.dao.removeObject(object.getCostTypeId());
/*    */ 
/* 56 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveCostType(CostType costType) {
/* 63 */       this.dao.saveObject(costType);
/*    */ 
/* 65 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveObject(CostType object) {
/* 72 */       this.saveCostType(object);
/*    */ 
/* 74 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final CostType findByName(String name) {
/* 80 */       return ((CostTypeDAO)this.dao).findByName(name);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(CostType object) {
/* 87 */       return object.getCostTypeId();
/*    */    }
/*    */ }
