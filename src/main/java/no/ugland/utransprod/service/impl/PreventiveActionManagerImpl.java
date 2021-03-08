/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.PreventiveActionDAO;
/*     */ import no.ugland.utransprod.model.FunctionCategory;
/*     */ import no.ugland.utransprod.model.JobFunction;
/*     */ import no.ugland.utransprod.model.PreventiveAction;
/*     */ import no.ugland.utransprod.service.PreventiveActionManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PreventiveActionManagerImpl extends ManagerImpl<PreventiveAction> implements PreventiveActionManager {
/*     */    public final List<PreventiveAction> findAll() {
/*  21 */       return ((PreventiveActionDAO)this.dao).findAll();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PreventiveAction> findByObject(PreventiveAction object) {
/*  30 */       return this.dao.findByExampleLike(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(PreventiveAction object) {
/*  38 */       ((PreventiveActionDAO)this.dao).refreshPreventiveAction(object);
/*     */ 
/*  40 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removePreventiveAction(PreventiveAction preventiveAction) {
/*  47 */       this.dao.removeObject(preventiveAction.getPreventiveActionId());
/*     */ 
/*  49 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(PreventiveAction object) {
/*  56 */       this.removePreventiveAction(object);
/*     */ 
/*  58 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(PreventiveAction object) {
/*  65 */       this.savePreventiveAction(object);
/*     */ 
/*  67 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void savePreventiveAction(PreventiveAction preventiveAction) {
/*  74 */       this.dao.saveObject(preventiveAction);
/*     */ 
/*  76 */    }
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
/*     */    public final List<PreventiveAction> findAllOpenByFunctionAndCategory(JobFunction jobFunction, FunctionCategory functionCategory) {
/*  96 */       return ((PreventiveActionDAO)this.dao).findAllOpenByFunctionAndCategory(jobFunction, functionCategory);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(PreventiveAction object) {
/* 107 */       return object.getPreventiveActionId();
/*     */    }
/*     */ }
