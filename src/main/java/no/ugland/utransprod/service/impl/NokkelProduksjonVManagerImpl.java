/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import no.ugland.utransprod.dao.NokkelProduksjonVDAO;
/*     */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*     */ import no.ugland.utransprod.model.NokkelProduksjonV;
/*     */ import no.ugland.utransprod.service.NokkelProduksjonVManager;
/*     */ import no.ugland.utransprod.util.YearWeek;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportEnum;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NokkelProduksjonVManagerImpl implements NokkelProduksjonVManager {
/*     */    private NokkelProduksjonVDAO dao;
/*     */ 
/*     */    public final void setNokkelProduksjonVDAO(NokkelProduksjonVDAO aDao) {
/*  28 */       this.dao = aDao;
/*  29 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final NokkelProduksjonV findByWeek(Integer year, Integer week) {
/*  36 */       return this.dao.findByWeek(year, week);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<NokkelProduksjonV> findByParams(ExcelReportSetting params) {
/*  44 */       return params.getExcelReportType() == ExcelReportEnum.PRODUCTIVITY_PACK ? this.dao.findByYearWeekProductArea(params.getYear(), params.getWeekFrom(), params.getWeekTo(), params.getProductAreaName()) : this.dao.findByYearWeekProductAreaGroup(params.getYear(), params.getWeekFrom(), params.getWeekTo(), ((ExcelReportSettingOwnProduction)params).getProductAreaGroupName());
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
/*     */    public final NokkelProduksjonV aggreagateYearWeek(YearWeek currentYearWeek, String productArea) {
/*  57 */       return this.dao.aggreagateYearWeek(currentYearWeek, productArea);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<NokkelProduksjonV> findBetweenYearWeek(YearWeek fromYearWeek, YearWeek toYearWeek, String productArea) {
/*  66 */       return this.dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final String getInfoButtom(ExcelReportSetting params) {
/*  74 */       List<NokkelProduksjonV> prodList = this.findByParams(params);
/*  75 */       BigDecimal budget = BigDecimal.valueOf(0L);
/*  76 */       if (prodList != null) {         Iterator var4 = prodList.iterator();         while(var4.hasNext()) {
/*  77 */             NokkelProduksjonV prod = (NokkelProduksjonV)var4.next();
/*  78 */             BigDecimal currentBudget = prod.getBudgetValue();
/*  79 */             if (currentBudget != null) {
/*  80 */                budget = budget.add(currentBudget);
/*     */             }         }
/*     */       }
/*     */ 
/*  84 */       budget = budget.divide(BigDecimal.valueOf(5L));
/*  85 */       return String.format("For å klare budsjett må det pakkes for %1$.0f hver dag", budget);
/*     */    }
/*     */ 
/*     */    public final String getInfoTop(ExcelReportSetting params) {
/*  89 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/*  99 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 104 */       return null;
/*     */    }
/*     */ }
