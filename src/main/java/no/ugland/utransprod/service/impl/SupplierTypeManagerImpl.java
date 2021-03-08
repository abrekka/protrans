/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.SupplierTypeDAO;
/*    */ import no.ugland.utransprod.model.SupplierType;
/*    */ import no.ugland.utransprod.service.SupplierTypeManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SupplierTypeManagerImpl extends ManagerImpl<SupplierType> implements SupplierTypeManager {
/*    */    public final List<SupplierType> findAll() {
/* 19 */       return this.dao.getObjects("supplierTypeName");
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<SupplierType> findBySupplierType(SupplierType supplierType) {
/* 28 */       return this.dao.findByExampleLike(supplierType);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<SupplierType> findByObject(SupplierType object) {
/* 37 */       return this.findBySupplierType(object);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refreshObject(SupplierType object) {
/* 45 */       ((SupplierTypeDAO)this.dao).refreshObject(object);
/*    */ 
/* 47 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeSupplierType(SupplierType supplierType) {
/* 54 */       this.dao.removeObject(supplierType.getSupplierTypeId());
/*    */ 
/* 56 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeObject(SupplierType object) {
/* 63 */       this.removeSupplierType(object);
/*    */ 
/* 65 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveSupplierType(SupplierType supplierType) {
/* 72 */       this.dao.saveObject(supplierType);
/*    */ 
/* 74 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveObject(SupplierType object) {
/* 81 */       this.saveSupplierType(object);
/*    */ 
/* 83 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public void lazyLoad(SupplierType object, Enum[] enums) {
/* 88 */    }
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(SupplierType object) {
/* 92 */       return object.getSupplierTypeId();
/*    */    }
/*    */ }
