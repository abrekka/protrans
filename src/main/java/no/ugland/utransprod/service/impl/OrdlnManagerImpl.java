/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.OrdlnDAO;
/*    */ import no.ugland.utransprod.model.Ord;
/*    */ import no.ugland.utransprod.model.Ordln;
/*    */ import no.ugland.utransprod.service.OrdlnManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OrdlnManagerImpl implements OrdlnManager {
/*    */    private OrdlnDAO dao;
/*    */ 
/*    */    public final void setOrdlnDAO(OrdlnDAO aDao) {
/* 18 */       this.dao = aDao;
/* 19 */    }
/*    */ 
/*    */    public final List<Ordln> findByOrderNr(String orderNr) {
/* 22 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */    public final List<Ordln> findForFakturagrunnlag(String orderNr) {
/* 26 */       return this.dao.findForFakturagrunnlag(orderNr);
/*    */    }
/*    */ 
/*    */    public BigDecimal getSumCstpr(String orderNr, Integer prCatNo) {
/* 30 */       return this.dao.getSumCstpr(orderNr, prCatNo);
/*    */    }
/*    */ 
/*    */    public Ordln findByOrderNrProdCatNo(String orderNr, Integer prodCatNo, Integer prodCatNo2) {
/* 34 */       return this.dao.findByOrderNrProdCatNo(orderNr, prodCatNo, prodCatNo2);
/*    */    }
/*    */ 
/*    */    public BigDecimal getSumCcstpr(String orderNr, Integer prodCatNo) {
/* 38 */       return this.dao.getSumCcstpr(orderNr, prodCatNo);
/*    */    }
/*    */ 
/*    */    public Integer isOrderLineInStorage(Integer ordNo, Integer lnNo) {
/* 42 */       return ordNo == null ? null : this.dao.isOrderLineInStorage(ordNo, lnNo);
/*    */    }
/*    */ 
/*    */    public Ord findOrdByOrderNr(String orderNr) {
/* 46 */       return this.dao.findOrdByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */    public Ordln findByOrdnoAndPrCatNo2(Integer ordno, Integer prodCatNo2) {
/* 50 */       return this.dao.findByOrdnoAndPrCatNo2(ordno, prodCatNo2);
/*    */    }
/*    */ 
/*    */    public List<Ordln> findCostLines(String orderNr) {
/* 54 */       return this.dao.findCostLines(orderNr);
/*    */    }
/*    */ 
/*    */    public Ordln findByOrdNoAndLnNo(Integer ordNo, Integer lnNo) {
/* 58 */       return this.dao.findByOrdNoAndLnNo(ordNo, lnNo);
/*    */    }
/*    */ 
/*    */    public Ord findByOrdNo(Integer ordNo) {
/* 62 */       return this.dao.findByOrdNo(ordNo);
/*    */    }
/*    */ 
/*    */    public List<Ordln> findTaksteinInfo(String orderNr) {
/* 66 */       return this.dao.findTaksteinInfo(orderNr);
/*    */    }
/*    */ 
/*    */    public List<Ordln> findOrdLnByOrdNo(Integer ordno) {
/* 70 */       return this.dao.findOrdLnByOrdNo(ordno);
/*    */    }
/*    */ }
