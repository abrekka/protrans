/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.dao.OrderPacklistReadyVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.model.OrderPacklistReadyV;
/*    */ import no.ugland.utransprod.service.OrderPacklistReadyVManager;
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
/*    */ public class OrderPacklistReadyVManagerImpl implements OrderPacklistReadyVManager {
/*    */    private OrderPacklistReadyVDAO dao;
/*    */ 
/*    */    public final void setOrderPacklistReadyVDAO(OrderPacklistReadyVDAO aDao) {
/* 24 */       this.dao = aDao;
/* 25 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<OrderPacklistReadyV> findByParams(ExcelReportSetting params) {
/* 32 */       return this.dao.findByParams(params);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final String getInfoButtom(ExcelReportSetting params) {
/* 41 */       return null;
/*    */    }
/*    */    public final String getInfoTop(ExcelReportSetting params) {
/* 44 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 53 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 58 */       return null;
/*    */    }
/*    */ }
