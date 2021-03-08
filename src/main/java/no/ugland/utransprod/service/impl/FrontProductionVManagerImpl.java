/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.FrontProductionVDAO;
/*    */ import no.ugland.utransprod.model.Produceable;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.FrontProductionVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FrontProductionVManagerImpl extends AbstractApplyListManager<Produceable> implements FrontProductionVManager {
/*    */    private FrontProductionVDAO dao;
/*    */ 
/*    */    public final void setFrontProductionVDAO(FrontProductionVDAO aDao) {
/* 19 */       this.dao = aDao;
/* 20 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Produceable> findAllApplyable() {
/* 26 */       return this.dao.findAll();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Produceable> findApplyableByOrderNr(String orderNr) {
/* 33 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refresh(Produceable frontProductionV) {
/* 41 */       this.dao.refresh(frontProductionV);
/*    */ 
/* 43 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Produceable> findApplyableByCustomerNr(Integer customerNr) {
/* 49 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ 
/*    */    public List<Produceable> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 53 */       return this.dao.findByCustomerNrProductAreaGroup(customerNr, productAreaGroup);
/*    */    }
/*    */ 
/*    */    public List<Produceable> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 57 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*    */    }
/*    */ }
