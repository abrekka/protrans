/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.CostUnitDAO;
/*     */ import no.ugland.utransprod.model.CostUnit;
/*     */ import no.ugland.utransprod.service.CostUnitManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CostUnitManagerImpl extends ManagerImpl<CostUnit> implements CostUnitManager {
/*     */    public final List<CostUnit> findAll() {
/*  19 */       return ((CostUnitDAO)this.dao).findAll();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<CostUnit> findByCostUnit(CostUnit costUnit) {
/*  28 */       return this.dao.findByExampleLike(costUnit);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<CostUnit> findByObject(CostUnit object) {
/*  37 */       return this.findByCostUnit(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(CostUnit object) {
/*  45 */       ((CostUnitDAO)this.dao).refreshObject(object);
/*     */ 
/*  47 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(CostUnit object) {
/*  54 */       this.dao.removeObject(object.getCostUnitId());
/*     */ 
/*  56 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveCostUnit(CostUnit costUnit) {
/*  63 */       this.dao.saveObject(costUnit);
/*     */ 
/*  65 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(CostUnit object) {
/*  72 */       this.saveCostUnit(object);
/*     */ 
/*  74 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final CostUnit findByName(String name) {
/*  80 */       return ((CostUnitDAO)this.dao).findByName(name);
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
/*     */    protected Serializable getObjectId(CostUnit object) {
/*  99 */       return object.getCostUnitId();
/*     */    }
/*     */ }
