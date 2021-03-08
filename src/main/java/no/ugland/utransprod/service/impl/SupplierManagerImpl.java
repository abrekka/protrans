/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.OrderCostDAO;
/*     */ import no.ugland.utransprod.dao.SupplierDAO;
/*     */ import no.ugland.utransprod.model.OrderCost;
/*     */ import no.ugland.utransprod.model.Supplier;
/*     */ import no.ugland.utransprod.service.SupplierManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SupplierManagerImpl extends ManagerImpl<Supplier> implements SupplierManager {
/*     */    private OrderCostDAO orderCostDAO;
/*     */ 
/*     */    public final void setOrderCostDAO(OrderCostDAO aDao) {
/*  21 */       this.orderCostDAO = aDao;
/*  22 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Supplier> findAll() {
/*  28 */       return this.dao.getObjects("supplierName");
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Supplier> findBySupplier(Supplier supplier) {
/*  38 */       return this.dao.findByExampleLike(supplier);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Supplier> findByObject(Supplier object) {
/*  47 */       return this.findBySupplier(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(Supplier object) {
/*  55 */       ((SupplierDAO)this.dao).refreshObject(object);
/*     */ 
/*  57 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeSupplier(Supplier supplier) {
/*  65 */       this.dao.removeObject(supplier.getSupplierId());
/*     */ 
/*  67 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(Supplier object) {
/*  74 */       this.removeSupplier(object);
/*     */ 
/*  76 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveSupplier(Supplier supplier) {
/*  84 */       this.dao.saveObject(supplier);
/*     */ 
/*  86 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(Supplier object) {
/*  93 */       this.saveSupplier(object);
/*     */ 
/*  95 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Supplier> findByTypeName(String typeString, String orderBy) {
/* 102 */       return ((SupplierDAO)this.dao).findByTypeName(typeString, orderBy);
/*     */    }
/*     */ 
/*     */    public final Supplier findByName(String name) {
/* 106 */       return ((SupplierDAO)this.dao).findByName(name);
/*     */    }
/*     */ 
/*     */    public List<Supplier> findActiveByTypeName(String typeString, String orderBy) {
/* 110 */       return ((SupplierDAO)this.dao).findActiveByTypeName(typeString, orderBy);
/*     */    }
/*     */ 
/*     */    public List<Supplier> findHavingAssembly(Integer year, Integer fromWeek, Integer toWeek) {
/* 114 */       return ((SupplierDAO)this.dao).findHavingAssembly(year, fromWeek, toWeek);
/*     */    }
/*     */ 
/*     */    public Boolean hasOrderCosts(Supplier supplier) {
/* 118 */       List<OrderCost> orderCosts = this.orderCostDAO.findBySupplier(supplier);
/* 119 */       return orderCosts != null && orderCosts.size() != 0 ? true : false;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(Supplier object) {
/* 127 */       return object.getSupplierId();
/*     */    }
/*     */ }
