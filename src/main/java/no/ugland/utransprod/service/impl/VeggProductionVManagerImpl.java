/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.VeggProductionVDAO;
/*    */ import no.ugland.utransprod.model.Produceable;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.VeggProductionVManager;
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
/*    */ public class VeggProductionVManagerImpl extends AbstractApplyListManager<Produceable> implements VeggProductionVManager {
/*    */    private VeggProductionVDAO dao;
/*    */ 
/*    */    public final void setVeggProductionVDAO(VeggProductionVDAO aDao) {
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
/*    */    public final void refresh(Produceable veggProductionV) {
/* 44 */       this.dao.refresh(veggProductionV);
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
