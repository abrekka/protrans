/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.PacklistVDAO;
/*    */ import no.ugland.utransprod.model.PacklistV;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.PacklistVManager;
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
/*    */ public class PacklistVManagerImpl extends AbstractApplyListManager<PacklistV> implements PacklistVManager {
/*    */    private PacklistVDAO dao;
/*    */ 
/*    */    public final void setPacklistVDAO(PacklistVDAO aDao) {
/* 22 */       this.dao = aDao;
/* 23 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PacklistV> findAllApplyable() {
/* 29 */       return this.dao.findAll();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PacklistV> findApplyableByOrderNr(String orderNr) {
/* 36 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refresh(PacklistV productionV) {
/* 44 */       this.dao.refresh(productionV);
/*    */ 
/* 46 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PacklistV> findApplyableByCustomerNr(Integer customerNr) {
/* 52 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ 
/*    */    public List<PacklistV> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 56 */       return this.dao.findByCustomerNrAndProductAreaGroup(customerNr, productAreaGroup);
/*    */    }
/*    */ 
/*    */    public List<PacklistV> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 60 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*    */    }
/*    */ }
