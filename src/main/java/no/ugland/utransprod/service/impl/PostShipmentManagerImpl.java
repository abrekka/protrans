/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.PostShipmentDAO;
/*     */ import no.ugland.utransprod.model.PostShipment;
/*     */ import no.ugland.utransprod.service.PostShipmentManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
/*     */ import no.ugland.utransprod.util.Periode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PostShipmentManagerImpl extends ManagerImpl<PostShipment> implements PostShipmentManager {
/*     */    public final List<PostShipment> findAllWithoutTransport() {
/*  22 */       return ((PostShipmentDAO)this.dao).findAllWithoutTransport();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void savePostShipment(PostShipment postShipment) {
/*  30 */       this.dao.saveObject(postShipment);
/*     */ 
/*  32 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removePostShipment(PostShipment postShipment) {
/*  39 */       this.dao.removeObject(postShipment.getPostShipmentId());
/*     */ 
/*  41 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PostShipment> findAllNotSent() {
/*  47 */       return ((PostShipmentDAO)this.dao).findAllNotSent();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoad(PostShipment postShipment, LazyLoadPostShipmentEnum[] enums) {
/*  57 */       ((PostShipmentDAO)this.dao).lazyLoad(postShipment, enums);
/*     */ 
/*  59 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PostShipment> findAll() {
/*  65 */       return ((PostShipmentDAO)this.dao).findAll();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PostShipment> findByObject(PostShipment object) {
/*  74 */       return this.dao.findByExampleLike(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(PostShipment object) {
/*  82 */       ((PostShipmentDAO)this.dao).refreshPostShipment(object);
/*     */ 
/*  84 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(PostShipment object) {
/*  91 */       this.removePostShipment(object);
/*     */ 
/*  93 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(PostShipment object) {
/* 100 */       this.savePostShipment(object);
/*     */ 
/* 102 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoadTree(PostShipment postShipment) {
/* 109 */       ((PostShipmentDAO)this.dao).lazyLoadTree(postShipment);
/*     */ 
/* 111 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PostShipment> findByOrderNr(String orderNr) {
/* 117 */       return ((PostShipmentDAO)this.dao).findByOrderNr(orderNr);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PostShipment> findByCustomerNr(Integer customerNr) {
/* 124 */       return ((PostShipmentDAO)this.dao).findByCustomerNr(customerNr);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void load(PostShipment postShipment) {
/* 131 */       ((PostShipmentDAO)this.dao).load(postShipment);
/*     */ 
/* 133 */    }
/*     */ 
/*     */ 
/*     */    public final List<PostShipment> findSentInPeriod(Periode periode, String productAreaGroupName) {
/* 137 */       return ((PostShipmentDAO)this.dao).findSentInPeriod(periode, productAreaGroupName);
/*     */    }
/*     */ 
/*     */    public final PostShipment loadById(Integer postShipmentId) {
/* 141 */       return (PostShipment)this.dao.getObject(postShipmentId);
/*     */    }
/*     */ 
/*     */    public void lazyLoad(PostShipment object, Enum[] enums) {
/* 145 */       this.lazyLoad(object, (LazyLoadPostShipmentEnum[])((LazyLoadPostShipmentEnum[])enums));
/*     */ 
/* 147 */    }
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(PostShipment object) {
/* 151 */       return object.getPostShipmentId();
/*     */    }
/*     */ 
/*     */    public void settSentDato(PostShipment postShipment, Date sentDate) {
/* 155 */       ((PostShipmentDAO)this.dao).settSentDato(postShipment, sentDate);
/*     */ 
/* 157 */    }
/*     */ 
/*     */    public void settLevert(PostShipment postShipment, Date levertDate) {
/* 160 */       ((PostShipmentDAO)this.dao).settLevert(postShipment, levertDate);
/*     */ 
/* 162 */    }
/*     */ }
