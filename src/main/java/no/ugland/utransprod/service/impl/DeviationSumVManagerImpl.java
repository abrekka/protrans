/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.dao.DeviationSumVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.model.DeviationSumV;
/*    */ import no.ugland.utransprod.service.DeviationSumVManager;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeviationSumVManagerImpl implements DeviationSumVManager {
/*    */    private DeviationSumVDAO dao;
/*    */ 
/*    */    public final void setDeviationSumVDAO(DeviationSumVDAO aDao) {
/* 24 */       this.dao = aDao;
/* 25 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<DeviationSumV> findByParams(ExcelReportSetting params) {
/* 32 */       return this.dao.findByParams((ExcelReportSettingDeviation)params);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final String getInfoButtom(ExcelReportSetting params) {
/* 40 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 49 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public String getInfoTop(ExcelReportSetting params) {
/* 54 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 59 */       return null;
/*    */    }
/*    */ }
