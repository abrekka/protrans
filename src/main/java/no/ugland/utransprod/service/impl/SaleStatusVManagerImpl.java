/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.dao.SaleStatusVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.service.SaleStatusVManager;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSettingSaleStatus;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SaleStatusVManagerImpl implements SaleStatusVManager {
/*    */    private SaleStatusVDAO dao;
/*    */ 
/*    */    public final void setSaleStatusVDAO(SaleStatusVDAO aDao) {
/* 20 */       this.dao = aDao;
/* 21 */    }
/*    */ 
/*    */ 
/*    */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 25 */       return null;
/*    */    }
/*    */ 
/*    */    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
/* 29 */       return this.dao.findByProbabilitesAndProductArea(((ExcelReportSettingSaleStatus)params).getProbabilities(), ((ExcelReportSettingSaleStatus)params).getProductArea());
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
/* 35 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public String getInfoTop(ExcelReportSetting params) {
/* 40 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 45 */       return null;
/*    */    }
/*    */ }
