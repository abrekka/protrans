/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.dao.PostShipmentCountVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.model.PostShipmentCountSum;
/*    */ import no.ugland.utransprod.service.PostShipmentCountVManager;
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
/*    */ public class PostShipmentCountVManagerImpl implements PostShipmentCountVManager {
/*    */    private PostShipmentCountVDAO dao;
/*    */ 
/*    */    public final void setPostShipmentCountVDAO(PostShipmentCountVDAO aDao) {
/* 23 */       this.dao = aDao;
/* 24 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PostShipmentCountSum> findByParams(ExcelReportSetting params) {
/* 31 */       return this.dao.findByParams(params);
/*    */    }
/*    */ 
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
