/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.DeviationDAO;
/*     */ import no.ugland.utransprod.model.ApplicationUser;
/*     */ import no.ugland.utransprod.model.Deviation;
/*     */ import no.ugland.utransprod.model.JobFunction;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.service.DeviationManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;
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
/*     */ public class DeviationManagerImpl extends ManagerImpl<Deviation> implements DeviationManager {
/*     */    public final List<Deviation> findAll() {
/*  25 */       return this.dao.getObjects();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Deviation> findByObject(Deviation object) {
/*  34 */       return ((DeviationDAO)this.dao).findByDeviation(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(Deviation object) {
/*  42 */       ((DeviationDAO)this.dao).refreshObject(object);
/*     */ 
/*  44 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeDeviation(Deviation deviation) {
/*  52 */       if (deviation.getDeviationId() != null) {
/*  53 */          this.dao.removeObject(deviation.getDeviationId());
/*     */       }
/*     */ 
/*  56 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(Deviation object) {
/*  63 */       this.removeDeviation(object);
/*     */ 
/*  65 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveDeviation(Deviation deviation) {
/*  73 */       this.dao.saveObject(deviation);
/*     */ 
/*  75 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(Deviation object) {
/*  82 */       this.saveDeviation(object);
/*     */ 
/*  84 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Deviation> findByJobFunction(JobFunction jobFunction) {
/*  91 */       return ((DeviationDAO)this.dao).findByJobFunction(jobFunction);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoad(Deviation deviation, LazyLoadDeviationEnum[] enums) {
/*  99 */       ((DeviationDAO)this.dao).lazyLoad(deviation, enums);
/*     */ 
/* 101 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Deviation> findByManager(ApplicationUser applicationUser) {
/* 108 */       return ((DeviationDAO)this.dao).findByManager(applicationUser);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Deviation> findByOrder(Order order) {
/* 115 */       return ((DeviationDAO)this.dao).findByOrder(order);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Deviation> findAllAssembly() {
/* 122 */       return ((DeviationDAO)this.dao).findAllAssembly();
/*     */    }
/*     */ 
/*     */    public void lazyLoad(Deviation object, Enum[] enums) {
/* 126 */       this.lazyLoad(object, (LazyLoadDeviationEnum[])((LazyLoadDeviationEnum[])enums));
/*     */ 
/* 128 */    }
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(Deviation object) {
/* 132 */       return object.getDeviationId();
/*     */    }
/*     */ 
/*     */    public List<Deviation> findByResponsible(ApplicationUser applicationUser) {
/* 136 */       return ((DeviationDAO)this.dao).findByResponsible(applicationUser);
/*     */    }
/*     */ 
/*     */    public Deviation findById(Integer deviationId) {
/* 140 */       return ((DeviationDAO)this.dao).findById(deviationId);
/*     */    }
/*     */ }
