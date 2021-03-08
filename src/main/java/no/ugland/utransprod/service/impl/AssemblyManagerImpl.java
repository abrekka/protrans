/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.AssemblyDAO;
/*     */ import no.ugland.utransprod.model.Assembly;
/*     */ import no.ugland.utransprod.model.Supplier;
/*     */ import no.ugland.utransprod.service.AssemblyManager;
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
/*     */ public class AssemblyManagerImpl extends ManagerImpl<Assembly> implements AssemblyManager {
/*     */    public final List<Assembly> findAll() {
/*  22 */       return this.dao.getObjects();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Assembly> findByObject(Assembly object) {
/*  31 */       return this.dao.findByExampleLike(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(Assembly object) {
/*  39 */       ((AssemblyDAO)this.dao).refreshObject(object);
/*     */ 
/*  41 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(Assembly object) {
/*  48 */       this.dao.removeObject(object.getAssemblyId());
/*     */ 
/*  50 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(Assembly object) {
/*  57 */       this.dao.saveObject(object);
/*     */ 
/*  59 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Assembly> findBySupplierYearWeek(Supplier supplier, Integer year, Integer week) {
/*  67 */       return ((AssemblyDAO)this.dao).findBySupplierYearWeek(supplier, year, week);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveAssembly(Assembly assembly) {
/*  74 */       this.dao.saveObject(assembly);
/*     */ 
/*  76 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void lazyLoad(Assembly object, Enum[] enums) {
/*  81 */    }
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(Assembly object) {
/*  85 */       return object.getAssemblyId();
/*     */    }
/*     */ 
/*     */    public Assembly merge(Assembly object) {
/*  89 */       return (Assembly)this.dao.merge(object);
/*     */    }
/*     */ 
/*     */    public List<Assembly> findByYear(Integer year) {
/*  93 */       return ((AssemblyDAO)this.dao).findByYear(year);
/*     */    }
/*     */ 
/*     */    public Assembly get(Integer assemblyId) {
/*  97 */       return (Assembly)((AssemblyDAO)this.dao).getObject(assemblyId);
/*     */    }
/*     */ 
/*     */    public void oppdaterMontering(Integer assemblyId, Date assembliedDate, String planned) {
/* 101 */       ((AssemblyDAO)this.dao).oppdaterMontering(assemblyId, assembliedDate, planned);
/*     */ 
/* 103 */    }
/*     */ }
