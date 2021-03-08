/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.dao.NotInvoicedVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.model.NotInvoicedV;
/*    */ import no.ugland.utransprod.service.NotInvoicedVManager;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotInvoicedVManagerImpl implements NotInvoicedVManager {
/*    */    private NotInvoicedVDAO dao;
/*    */ 
/*    */    public final void setNotInvoicedVDAO(NotInvoicedVDAO aDao) {
/* 23 */       this.dao = aDao;
/* 24 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<NotInvoicedV> findByParams(ExcelReportSetting params) {
/* 31 */       return this.dao.findByParams(params);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final String getInfoButtom(ExcelReportSetting params) {
/* 39 */       return null;
/*    */    }
/*    */    public final String getInfoTop(ExcelReportSetting params) {
/* 42 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 51 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 56 */       return null;
/*    */    }
/*    */ }
