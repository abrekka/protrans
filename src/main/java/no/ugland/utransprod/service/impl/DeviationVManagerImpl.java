/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.dao.DeviationVDAO;
/*     */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*     */ import no.ugland.utransprod.model.ApplicationUser;
/*     */ import no.ugland.utransprod.model.DeviationV;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.service.DeviationVManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadEnum;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviationVManagerImpl implements DeviationVManager {
/*     */    private DeviationVDAO dao;
/*     */ 
/*     */    public final void setDeviationVDAO(DeviationVDAO aDao) {
/*  27 */       this.dao = aDao;
/*  28 */    }
/*     */ 
/*     */ 
/*     */    public CheckObject checkExcel(ExcelReportSetting params) {
/*  32 */       return null;
/*     */    }
/*     */ 
/*     */    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
/*  36 */       return this.dao.findByParams((ExcelReportSettingDeviation)params);
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
/*  41 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getInfoTop(ExcelReportSetting params) {
/*  46 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/*  51 */       return null;
/*     */    }
/*     */ 
/*     */    public List<DeviationV> findAll() {
/*  55 */       return this.dao.getObjects();
/*     */    }
/*     */ 
/*     */ 
/*     */    public List<DeviationV> findByObject(DeviationV object) {
/*  60 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void removeObject(DeviationV object) {
/*  66 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void saveObject(DeviationV object) throws ProTransException {
/*  71 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void refreshObject(DeviationV object) {
/*  76 */    }
/*     */ 
/*     */ 
/*     */    public DeviationV merge(DeviationV object) {
/*  80 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void lazyLoad(DeviationV object, LazyLoadEnum[][] enums) {
/*  86 */    }
/*     */ 
/*     */    public Collection findByOrder(Order order) {
/*  89 */       return this.dao.findByOrder(order);
/*     */    }
/*     */ 
/*     */    public Collection findByManager(ApplicationUser applicationUser) {
/*  93 */       return this.dao.findByManager(applicationUser);
/*     */    }
/*     */ 
/*     */    public DeviationV findByDeviationId(Integer deviationId) {
/*  97 */       return this.dao.findByDeviationId(deviationId);
/*     */    }
/*     */ }
