/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.dao.OrderStatusNotSentVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.model.StatusOrdersNotSent;
/*    */ import no.ugland.utransprod.service.OrderStatusNotSentVManager;
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
/*    */ 
/*    */ public class OrderStatusNotSentVManagerImpl implements OrderStatusNotSentVManager {
/*    */    private OrderStatusNotSentVDAO dao;
/*    */ 
/*    */    public final void setOrderStatusNotSentVDAO(OrderStatusNotSentVDAO aDao) {
/* 24 */       this.dao = aDao;
/* 25 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<StatusOrdersNotSent> findByParams(ExcelReportSetting params) {
/* 32 */       return this.dao.findByParams(params);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final String getInfoButtom(ExcelReportSetting params) {
/* 40 */       return null;
/*    */    }
/*    */    public final String getInfoTop(ExcelReportSetting params) {
/* 43 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 52 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 57 */       return null;
/*    */    }
/*    */ }
