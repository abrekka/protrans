/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.GavlProductionVDAO;
/*    */ import no.ugland.utransprod.model.Produceable;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.GavlProductionVManager;
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
/*    */ public class GavlProductionVManagerImpl extends AbstractApplyListManager<Produceable> implements GavlProductionVManager {
/*    */    private GavlProductionVDAO dao;
/*    */ 
/*    */    public final void setGavlProductionVDAO(GavlProductionVDAO aDao) {
/* 22 */       this.dao = aDao;
/* 23 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Produceable> findAllApplyable() {
/* 29 */       return this.dao.findAll();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Produceable> findApplyableByOrderNr(String orderNr) {
/* 36 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refresh(Produceable productionV) {
/* 44 */       this.dao.refresh(productionV);
/*    */ 
/* 46 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Produceable> findApplyableByCustomerNr(Integer customerNr) {
/* 52 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ 
/*    */    public List<Produceable> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 56 */       return this.dao.findByCustomerNrAndProductAreaGroup(customerNr, productAreaGroup);
/*    */    }
/*    */ 
/*    */    public List<Produceable> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 60 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*    */    }
/*    */ }
