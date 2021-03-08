/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.OrderSegmentNoVDAO;
/*    */ import no.ugland.utransprod.model.ProductArea;
/*    */ import no.ugland.utransprod.service.OrderSegmentNoVManager;
/*    */ import no.ugland.utransprod.util.Periode;
/*    */ import no.ugland.utransprod.util.report.SaleReportData;
/*    */ import no.ugland.utransprod.util.report.SaleReportSum;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OrderSegmentNoVManagerImpl implements OrderSegmentNoVManager {
/*    */    protected OrderSegmentNoVDAO dao;
/*    */ 
/*    */    public void setOrderSegmentNoVDao(OrderSegmentNoVDAO aDao) {
/* 17 */       this.dao = aDao;
/* 18 */    }
/*    */ 
/*    */    public List<SaleReportSum> sumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
/* 21 */       return this.dao.sumByProductAreaConfirmPeriode(productArea, periode);
/*    */    }
/*    */ 
/*    */    public List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
/* 25 */       return this.dao.groupSumCountyByProductAreaConfirmPeriode(productArea, periode);
/*    */    }
/*    */ 
/*    */    public List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
/* 29 */       return this.dao.groupSumSalesmanByProductAreaConfirmPeriode(productArea, periode);
/*    */    }
/*    */ 
/*    */    public List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea productArea, Periode periode) {
/* 33 */       return this.dao.getSaleReportByProductAreaPeriode(productArea, periode);
/*    */    }
/*    */ 
/*    */    public SaleReportSum groupSumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
/* 37 */       return this.dao.groupSumByProductAreaConfirmPeriode(productArea, periode);
/*    */    }
/*    */ 
/*    */    public Integer countByProductAreaPeriode(ProductArea productArea, Periode periode) {
/* 41 */       return this.dao.countByProductAreaPeriode(productArea, periode);
/*    */    }
/*    */ }
