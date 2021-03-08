/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import no.ugland.utransprod.dao.PreventiveActionDAO;
/*     */ import no.ugland.utransprod.dao.SumAvvikVDAO;
/*     */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*     */ import no.ugland.utransprod.model.PreventiveAction;
/*     */ import no.ugland.utransprod.model.SumAvvikV;
/*     */ import no.ugland.utransprod.service.SumAvvikVManager;
/*     */ import no.ugland.utransprod.util.MonthEnum;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;
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
/*     */ public class SumAvvikVManagerImpl implements SumAvvikVManager {
/*     */    private SumAvvikVDAO dao;
/*     */    private PreventiveActionDAO preventiveActionDAO;
/*     */ 
/*     */    public final void setSumAvvikVDAO(SumAvvikVDAO aDao) {
/*  31 */       this.dao = aDao;
/*  32 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void setPreventiveActionDAO(PreventiveActionDAO aDao) {
/*  38 */       this.preventiveActionDAO = aDao;
/*  39 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<SumAvvikV> findByParams(ExcelReportSetting params) {
/*  48 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final String getInfoButtom(ExcelReportSetting params) {
/*  58 */       return null;
/*     */    }
/*     */ 
/*     */    public final String getInfoTop(ExcelReportSetting params) {
/*  62 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/*  70 */       ExcelReportSettingDeviation paramsDeviation = (ExcelReportSettingDeviation)params;
/*  71 */       Map<Object, Object> map = new Hashtable();
/*     */ 
/*     */ 
/*  74 */       this.setDeviationForPeriode(paramsDeviation.getYear(), paramsDeviation.getMonthEnum().getMonth(), paramsDeviation.getProductArea().getProductArea(), map, "Period");
/*     */ 
/*     */ 
/*     */ 
/*  78 */       this.setDeviationTotal(paramsDeviation, map);
/*     */ 
/*     */ 
/*  81 */       this.setDeviationForPeriode(paramsDeviation.getYear() - 1, paramsDeviation.getMonthEnum().getMonth(), paramsDeviation.getProductArea().getProductArea(), map, "LastYear");
/*     */ 
/*     */ 
/*     */ 
/*  85 */       this.setOpenPreventiveActions(map);
/*     */ 
/*     */ 
/*  88 */       this.setClosedPreventiveActionsThisPeriode(paramsDeviation, map);
/*     */ 
/*     */ 
/*  91 */       this.setDeviationOverview(paramsDeviation, map);
/*     */ 
/*  93 */       return map;
/*     */    }
/*     */ 
/*     */    private void setDeviationOverview(ExcelReportSettingDeviation paramsDeviation, Map<Object, Object> map) {
/*  97 */       Map<MonthEnum, Map<String, SumAvvikV>> monthDeviations = new Hashtable();
/*     */ 
/*  99 */       List<MonthEnum> months = MonthEnum.getMonthList();      Iterator var5 = months.iterator();      while(var5.hasNext()) {
/* 100 */          MonthEnum month = (MonthEnum)var5.next();
/* 101 */          this.addMonthDeviations(paramsDeviation.getYear(), paramsDeviation.getProductArea().getProductArea(), month, monthDeviations);
/*     */       }
/*     */ 
/* 104 */       map.put("DeviationList", monthDeviations);
/* 105 */    }
/*     */ 
/*     */ 
/*     */    private void setClosedPreventiveActionsThisPeriode(ExcelReportSettingDeviation paramsDeviation, Map<Object, Object> map) {
/* 109 */       List<PreventiveAction> preventiveActionList = this.preventiveActionDAO.findAllClosedInMonth(paramsDeviation.getMonthEnum().getMonth());
/*     */ 
/* 111 */       if (preventiveActionList != null) {
/* 112 */          map.put("PreventiveActionClosed", preventiveActionList);
/*     */       }
/* 114 */    }
/*     */ 
/*     */    private void setOpenPreventiveActions(Map<Object, Object> map) {
/* 117 */       List<PreventiveAction> preventiveActionList = this.preventiveActionDAO.findAllOpen();
/*     */ 
/* 119 */       if (preventiveActionList != null) {
/* 120 */          map.put("PreventiveActionOpen", preventiveActionList);
/*     */       }
/* 122 */    }
/*     */ 
/*     */ 
/*     */    private void setDeviationForPeriode(Integer year, Integer month, String productArea, Map<Object, Object> map, String keyString) {
/* 126 */       List<SumAvvikV> deviations = this.dao.findByProductAreaYearAndMonth(year, month, productArea);
/*     */ 
/* 128 */       if (deviations != null) {
/* 129 */          map.put(keyString, deviations);
/*     */       }
/* 131 */    }
/*     */ 
/*     */ 
/*     */    private void setDeviationTotal(ExcelReportSettingDeviation paramsDeviation, Map<Object, Object> map) {
/* 135 */       List<SumAvvikV> deviations = this.dao.findSumByProductAreaYearAndMonth(paramsDeviation.getYear(), paramsDeviation.getMonthEnum().getMonth(), paramsDeviation.getProductArea().getProductArea());
/*     */ 
/*     */ 
/* 138 */       if (deviations != null) {
/* 139 */          map.put("Year", deviations);
/*     */       }
/* 141 */    }
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
/*     */    private void addMonthDeviations(Integer year, String productArea, MonthEnum month, Map<MonthEnum, Map<String, SumAvvikV>> monthDeviations) {
/* 153 */       List<SumAvvikV> deviations = this.dao.findByProductAreaYearAndMonthWithClosed(year, month.getMonth(), productArea);
/*     */ 
/* 155 */       Map<String, SumAvvikV> deviationMap = new Hashtable();
/*     */ 
/* 157 */       SumAvvikV currentDeviationOpen = null;
/* 158 */       SumAvvikV currentDeviationClosed = null;
/* 159 */       String jobFunctionName = "";
/* 160 */       int count = 0;      SumAvvikV deviation;
/* 161 */       if (deviations != null) {
/* 162 */          for(Iterator var11 = deviations.iterator(); var11.hasNext(); jobFunctionName = deviation.getJobFunctionName()) {            deviation = (SumAvvikV)var11.next();
/* 163 */             ++count;
/* 164 */             if (!jobFunctionName.equalsIgnoreCase(deviation.getJobFunctionName()) && count != 1) {
/* 165 */                if (currentDeviationOpen != null && currentDeviationClosed != null) {
/* 166 */                   currentDeviationOpen.setClosedCount(currentDeviationClosed.getDeviationCount());
/* 167 */                   deviationMap.put(currentDeviationOpen.getJobFunctionName(), currentDeviationOpen);
/* 168 */                } else if (currentDeviationClosed != null) {
/* 169 */                   currentDeviationClosed.setClosedCount(currentDeviationClosed.getDeviationCount());
/* 170 */                   currentDeviationClosed.setDeviationCount(0);
/* 171 */                   deviationMap.put(currentDeviationClosed.getJobFunctionName(), currentDeviationClosed);
/* 172 */                } else if (currentDeviationOpen != null) {
/* 173 */                   deviationMap.put(currentDeviationOpen.getJobFunctionName(), currentDeviationOpen);
/*     */                }
/* 175 */                currentDeviationOpen = null;
/* 176 */                currentDeviationClosed = null;
/*     */             }
/* 178 */             if (deviation.getClosed() == 0) {
/* 179 */                currentDeviationOpen = deviation;
/*     */             } else {
/* 181 */                currentDeviationClosed = deviation;
/*     */ 
/*     */ 
/*     */             }
/*     */          }
/*     */       }
/*     */ 
/* 188 */       if (currentDeviationOpen != null && currentDeviationClosed != null) {
/* 189 */          currentDeviationOpen.setClosedCount(currentDeviationClosed.getDeviationCount());
/* 190 */          deviationMap.put(currentDeviationOpen.getJobFunctionName(), currentDeviationOpen);
/* 191 */       } else if (currentDeviationClosed != null) {
/* 192 */          currentDeviationClosed.setClosedCount(currentDeviationClosed.getDeviationCount());
/* 193 */          currentDeviationClosed.setDeviationCount(0);
/* 194 */          deviationMap.put(currentDeviationClosed.getJobFunctionName(), currentDeviationClosed);
/* 195 */       } else if (currentDeviationOpen != null) {
/* 196 */          deviationMap.put(currentDeviationOpen.getJobFunctionName(), currentDeviationOpen);
/*     */       }
/* 198 */       monthDeviations.put(month, deviationMap);
/*     */ 
/* 200 */    }
/*     */ 
/*     */ 
/*     */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 204 */       return null;
/*     */    }
/*     */ }
