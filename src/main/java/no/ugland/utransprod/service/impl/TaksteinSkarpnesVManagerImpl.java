/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.TaksteinSkarpnesVDAO;
/*    */ import no.ugland.utransprod.model.Produceable;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.TaksteinSkarpnesVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaksteinSkarpnesVManagerImpl extends AbstractApplyListManager<Produceable> implements TaksteinSkarpnesVManager {
/*    */    private TaksteinSkarpnesVDAO dao;
/*    */ 
/*    */    public final void setTaksteinSkarpnesVDAO(TaksteinSkarpnesVDAO aDao) {
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
/*    */    public final List<Produceable> findApplyableByCustomerNr(Integer customerNr) {
/* 33 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Produceable> findApplyableByOrderNr(String orderNr) {
/* 40 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refresh(Produceable object) {
/* 48 */       this.dao.refresh(object);
/*    */ 
/* 50 */    }
/*    */ 
/*    */    public List<Produceable> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 53 */       return this.dao.findByCustomerNrAndProductAreaGroup(customerNr, productAreaGroup);
/*    */    }
/*    */ 
/*    */    public List<Produceable> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 57 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*    */    }
/*    */ }
