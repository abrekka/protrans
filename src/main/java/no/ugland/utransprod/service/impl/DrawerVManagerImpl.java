/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.dao.DocumentDAO;
/*    */ import no.ugland.utransprod.dao.DrawerVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.model.DrawerV;
/*    */ import no.ugland.utransprod.model.ProductArea;
/*    */ import no.ugland.utransprod.service.DrawerVManager;
/*    */ import no.ugland.utransprod.util.Periode;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ import no.ugland.utransprod.util.excel.ExcelManager;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportEnum;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*    */ import no.ugland.utransprod.util.report.DrawerGroup;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DrawerVManagerImpl implements DrawerVManager, ExcelManager {
/*    */    private DrawerVDAO dao;
/*    */    private DocumentDAO documentDao;
/*    */ 
/*    */    public final void setDrawerVDAO(DrawerVDAO aDao) {
/* 28 */       this.dao = aDao;
/* 29 */    }
/*    */ 
/*    */    public final void setDocumentDAO(DocumentDAO aDao) {
/* 32 */       this.documentDao = aDao;
/* 33 */    }
/*    */ 
/*    */    public List<DrawerGroup> groupByProductAreaPeriode(ProductArea productArea, Periode periode) {
/* 36 */       List<DrawerGroup> drawerGroups = this.dao.groupByProductAreaPeriode(productArea.getProductAreaNrList(), periode);
/* 37 */       if (drawerGroups != null) {         Iterator var4 = drawerGroups.iterator();         while(var4.hasNext()) {
/* 38 */             DrawerGroup drawerGroup = (DrawerGroup)var4.next();
/* 39 */             drawerGroup.setProductArea(productArea);
/*    */          }      }
/*    */ 
/* 42 */       return drawerGroups;
/*    */    }
/*    */ 
/*    */    public List<DrawerV> findByProductAreaPeriode(List<Integer> groupIdxList, Periode periode) {
/* 46 */       return this.dao.findByProductAreaPeriode(groupIdxList, periode);
/*    */    }
/*    */ 
/*    */    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
/* 50 */       return params.getExcelReportType() == ExcelReportEnum.DRAWER_REPORT ? this.groupByProductAreaPeriode(params.getProductArea(), params.getPeriode()) : this.findByProductAreaPeriode(params.getProductArea().getProductAreaNrList(), params.getPeriode());
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
/* 59 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public String getInfoTop(ExcelReportSetting params) {
/* 64 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 69 */       return null;
/*    */    }
/*    */ 
/*    */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 73 */       List<Integer> drawerDocumentIdList = this.dao.getDocumentIdByPeriode(params.getPeriode());
/* 74 */       List<Integer> documentIdList = this.documentDao.getDocumentIdByPeriode(params.getPeriode());
/* 75 */       if (drawerDocumentIdList.size() != documentIdList.size()) {
/* 76 */          List<Integer> diffList = Util.getDiff(documentIdList, drawerDocumentIdList);
/* 77 */          return new CheckObject("Dokumenter med id " + diffList.toString() + " mangler i rapport", true);
/*    */       } else {
/* 79 */          return null;
/*    */       }
/*    */    }
/*    */ }
