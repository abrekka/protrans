/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.OrdchgrHeadVDAO;
/*    */ import no.ugland.utransprod.dao.OrdchgrLineVDAO;
/*    */ import no.ugland.utransprod.model.OrdchgrHeadV;
/*    */ import no.ugland.utransprod.model.OrdchgrLineV;
/*    */ import no.ugland.utransprod.model.OrdchgrLineVPK;
/*    */ import no.ugland.utransprod.service.OrdchgrHeadVManager;
/*    */ 
/*    */ 
/*    */ public class OrdchgrHeadVManagerImpl implements OrdchgrHeadVManager {
/*    */    private OrdchgrHeadVDAO ordchgrHeadVDAO;
/*    */    private OrdchgrLineVDAO ordchgrLineVDAO;
/*    */ 
/*    */    public void setOrdchgrHeadVDAO(OrdchgrHeadVDAO aOrdchgrHeadVDAO) {
/* 17 */       this.ordchgrHeadVDAO = aOrdchgrHeadVDAO;
/* 18 */    }
/*    */ 
/*    */    public void setOrdchgrLineVDAO(OrdchgrLineVDAO aOrdchgrLineVDAO) {
/* 21 */       this.ordchgrLineVDAO = aOrdchgrLineVDAO;
/* 22 */    }
/*    */ 
/*    */    public OrdchgrHeadV getHead(Integer ordNo) {
/* 25 */       return ordNo != null ? (OrdchgrHeadV)this.ordchgrHeadVDAO.getObject(ordNo) : null;
/*    */    }
/*    */ 
/*    */    public OrdchgrLineV getLine(Integer ordNo, Integer lnNo) {
/* 29 */       return (OrdchgrLineV)this.ordchgrLineVDAO.getObject(new OrdchgrLineVPK(ordNo, lnNo));
/*    */    }
/*    */ 
/*    */    public List<OrdchgrLineV> getLines(Integer ordNo, List<Integer> lnNos) {
/* 33 */       return this.ordchgrLineVDAO.getLines(ordNo, lnNos);
/*    */    }
/*    */ }
