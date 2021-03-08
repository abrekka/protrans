/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.PaidVDAO;
/*    */ import no.ugland.utransprod.model.PaidV;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.PaidVManager;
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
/*    */ public class PaidVManagerImpl extends AbstractApplyListManager<PaidV> implements PaidVManager {
/*    */    private PaidVDAO dao;
/*    */ 
/*    */    public final void setPaidVDAO(PaidVDAO aDao) {
/* 22 */       this.dao = aDao;
/* 23 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PaidV> findAllApplyable() {
/* 29 */       return this.dao.findAll();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PaidV> findApplyableByCustomerNr(Integer customerNr) {
/* 36 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PaidV> findApplyableByOrderNr(String orderNr) {
/* 43 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refresh(PaidV paidV) {
/* 51 */       this.dao.refresh(paidV);
/*    */ 
/* 53 */    }
/*    */ 
/*    */    public List<PaidV> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 56 */       return this.dao.findByCustomerNrAndProductAreaGroup(customerNr, productAreaGroup);
/*    */    }
/*    */ 
/*    */    public List<PaidV> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 60 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*    */    }
/*    */ }
