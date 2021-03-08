/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.ExternalOrderDAO;
/*     */ import no.ugland.utransprod.model.ExternalOrder;
/*     */ import no.ugland.utransprod.model.ExternalOrderLine;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.service.ExternalOrderLineManager;
/*     */ import no.ugland.utransprod.service.ExternalOrderManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadEnum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalOrderManagerImpl extends ManagerImpl<ExternalOrder> implements ExternalOrderManager {
/*     */    private ExternalOrderLineManager externalOrderLineManager;
/*     */ 
/*     */    public final void setExternalOrderLineManager(ExternalOrderLineManager aExternalOrderLineManager) {
/*  26 */       this.externalOrderLineManager = aExternalOrderLineManager;
/*  27 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ExternalOrder> findAll() {
/*  33 */       return this.dao.getObjects("deliveryDate");
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ExternalOrder> findByExternalOrder(ExternalOrder externalOrder) {
/*  41 */       return this.dao.findByExampleLike(externalOrder);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ExternalOrder> findByObject(ExternalOrder object) {
/*  50 */       return this.findByExternalOrder(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(ExternalOrder object) {
/*  58 */       ((ExternalOrderDAO)this.dao).refreshObject(object);
/*     */ 
/*  60 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeExternalOrder(ExternalOrder externalOrder) {
/*  66 */       this.dao.removeObject(externalOrder.getExternalOrderId());
/*     */ 
/*  68 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(ExternalOrder object) {
/*  75 */       this.removeExternalOrder(object);
/*     */ 
/*  77 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveExternalOrder(ExternalOrder externalOrder) {
/*  83 */       this.dao.saveObject(externalOrder);
/*     */ 
/*  85 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(ExternalOrder object) {
/*  92 */       this.saveExternalOrder(object);
/*     */ 
/*  94 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ExternalOrder> findByOrder(Order order) {
/* 100 */       return ((ExternalOrderDAO)this.dao).findByOrder(order);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoadExternalOrderLine(ExternalOrderLine externalOrderLine, LazyLoadEnum[][] enums) {
/* 121 */       this.externalOrderLineManager.lazyLoad(externalOrderLine, enums);
/*     */ 
/* 123 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(ExternalOrder object) {
/* 128 */       return object.getExternalOrderId();
/*     */    }
/*     */ }
