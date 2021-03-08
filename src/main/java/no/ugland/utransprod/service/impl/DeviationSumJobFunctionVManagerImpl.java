/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.dao.DeviationSumJobFunctionVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.model.DeviationSumJobFunctionV;
/*    */ import no.ugland.utransprod.service.DeviationSumJobFunctionVManager;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeviationSumJobFunctionVManagerImpl implements DeviationSumJobFunctionVManager {
/*    */    private DeviationSumJobFunctionVDAO dao;
/*    */ 
/*    */    public final void setDeviationSumJobFunctionVDAO(DeviationSumJobFunctionVDAO aDao) {
/* 24 */       this.dao = aDao;
/* 25 */    }
/*    */ 
/*    */ 
/*    */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 29 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
/* 35 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
/* 41 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public String getInfoTop(ExcelReportSetting params) {
/* 46 */       return null;
/*    */    }
/*    */ 
/*    */    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 50 */       List<DeviationSumJobFunctionV> deviations = this.dao.findByYearAndDeviationFunctionAndProductAreaGroup(params.getYear(), ((ExcelReportSettingDeviation)params).getDeviationFunction(), ((ExcelReportSettingDeviation)params).getProductAreaGroup());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 55 */       Set<String> functionCategories = new HashSet();
/* 56 */       Map<String, DeviationSumJobFunctionV> reportData = new Hashtable();      Iterator var5 = deviations.iterator();      while(var5.hasNext()) {
/* 57 */          DeviationSumJobFunctionV deviation = (DeviationSumJobFunctionV)var5.next();
/* 58 */          functionCategories.add(deviation.getFunctionCategoryName());
/* 59 */          reportData.put("" + deviation.getRegistrationYear() + "_" + deviation.getRegistrationWeek() + "_" + deviation.getFunctionCategoryName(), deviation);
/*    */ 
/*    */       }
/*    */ 
/* 63 */       Map<Object, Object> reportMap = new HashMap();
/* 64 */       reportMap.put("FunctionCategory", functionCategories);
/* 65 */       reportMap.put("ReportData", reportData);
/* 66 */       return reportMap;
/*    */    }
/*    */ }
