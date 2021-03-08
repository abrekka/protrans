/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.FunctionCategoryDAO;
/*    */ import no.ugland.utransprod.model.FunctionCategory;
/*    */ import no.ugland.utransprod.service.FunctionCategoryManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FunctionCategoryManagerImpl extends ManagerImpl<FunctionCategory> implements FunctionCategoryManager {
/*    */    public final List<FunctionCategory> findAll() {
/* 19 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<FunctionCategory> findByObject(FunctionCategory object) {
/* 28 */       return this.dao.findByExampleLike(object);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refreshObject(FunctionCategory object) {
/* 36 */       ((FunctionCategoryDAO)this.dao).refreshObject(object);
/*    */ 
/* 38 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeObject(FunctionCategory object) {
/* 45 */       this.dao.removeObject(object.getFunctionCategoryId());
/*    */ 
/* 47 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveObject(FunctionCategory object) {
/* 54 */       this.dao.saveObject(object);
/*    */ 
/* 56 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(FunctionCategory object) {
/* 61 */       return object.getFunctionCategoryId();
/*    */    }
/*    */ }
