/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.TransportCostBasisDAO;
/*    */ import no.ugland.utransprod.model.TransportCostBasis;
/*    */ import no.ugland.utransprod.service.TransportCostBasisManager;
/*    */ 
/*    */ 
/*    */ public class TransportCostBasisManagerImpl extends ManagerImpl<TransportCostBasis> implements TransportCostBasisManager {
/*    */    public final void saveTransportCostBasisList(List<TransportCostBasis> transportCostBasisList) {
/* 13 */       Iterator var2 = transportCostBasisList.iterator();      while(var2.hasNext()) {         TransportCostBasis basis = (TransportCostBasis)var2.next();
/* 14 */          this.dao.saveObject(basis);
/*    */       }
/*    */ 
/* 17 */    }
/*    */ 
/*    */ 
/*    */    public final void removeTransportCostBasis(TransportCostBasis transportCostBasis) {
/* 21 */       this.dao.removeObject(transportCostBasis.getTransportCostBasisId());
/*    */ 
/* 23 */    }
/*    */ 
/*    */ 
/*    */    public final List<TransportCostBasis> findById(Integer transportCostBasisId) {
/* 27 */       return ((TransportCostBasisDAO)this.dao).findById(transportCostBasisId);
/*    */    }
/*    */ 
/*    */ 
/*    */    public final void saveTransportCostBasis(TransportCostBasis transportCostBasis) {
/* 32 */       this.dao.saveObject(transportCostBasis);
/*    */ 
/* 34 */    }
/*    */ 
/*    */    public final List<TransportCostBasis> findAll() {
/* 37 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */    public final List<TransportCostBasis> findByObject(TransportCostBasis object) {
/* 41 */       return this.dao.findByExample(object);
/*    */    }
/*    */ 
/*    */    public final void refreshObject(TransportCostBasis object) {
/* 45 */       this.dao.refreshObject(object, object.getTransportCostBasisId());
/* 46 */    }
/*    */ 
/*    */    public final void removeObject(TransportCostBasis object) {
/* 49 */       this.removeTransportCostBasis(object);
/* 50 */    }
/*    */ 
/*    */    public final void saveObject(TransportCostBasis object) {
/* 53 */       this.saveTransportCostBasis(object);
/* 54 */    }
/*    */ 
/*    */    public final void setInvoiceNr(TransportCostBasis transportCostBasis, String invoiceNr) {
/* 57 */       transportCostBasis.setInvoiceNr(invoiceNr);
/* 58 */       this.saveTransportCostBasis(transportCostBasis);
/* 59 */       ((TransportCostBasisDAO)this.dao).setInvoiceNr(transportCostBasis, invoiceNr);
/* 60 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(TransportCostBasis object) {
/* 69 */       return object.getTransportCostBasisId();
/*    */    }
/*    */ }
