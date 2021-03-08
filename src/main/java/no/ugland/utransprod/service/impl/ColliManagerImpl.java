/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.ColliDAO;
/*     */ import no.ugland.utransprod.model.Colli;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.PostShipment;
/*     */ import no.ugland.utransprod.service.ColliManager;
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
/*     */ public class ColliManagerImpl extends ManagerImpl<Colli> implements ColliManager {
/*     */    public final Colli findByNameAndOrder(String colliName, Order order) {
/*  25 */       return ((ColliDAO)this.dao).findByNameAndOrder(colliName, order);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveColli(Colli colli) {
/*  32 */       this.dao.saveObject(colli);
/*     */ 
/*  34 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Colli> findAll() {
/*  40 */       return this.dao.getObjects();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Colli> findByObject(Colli object) {
/*  49 */       return this.dao.findByExampleLike(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(Colli object) {
/*  57 */       ((ColliDAO)this.dao).refreshObject(object);
/*     */ 
/*  59 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(Colli object) {
/*  66 */       if (object.getColliId() != null) {
/*  67 */          this.dao.removeObject(object.getColliId());
/*     */       }
/*     */ 
/*  70 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(Colli object) {
/*  77 */       this.saveColli(object);
/*     */ 
/*  79 */    }
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
/*     */    public final Colli findByNameAndPostShipment(String colliName, PostShipment postShipment) {
/*  98 */       return ((ColliDAO)this.dao).findByNameAndPostShipment(colliName, postShipment);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(Colli object) {
/* 104 */       return object.getColliId();
/*     */    }
/*     */ 
/*     */    public void lazyLoadAll(Colli colli) {
/* 108 */       ((ColliDAO)this.dao).lazyLoadAll(colli);
/* 109 */    }
/*     */ 
/*     */    public void oppdaterTransportId(Colli colli, Integer transportId) {
/* 112 */       ((ColliDAO)this.dao).oppdaterTransportId(colli, transportId);
/*     */ 
/* 114 */    }
/*     */ }
