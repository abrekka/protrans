/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.SutakPackageVDAO;
/*    */ import no.ugland.utransprod.model.PackableListItem;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.model.SutakPackageV;
/*    */ import no.ugland.utransprod.service.SutakPackageVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SutakPackageVManagerImpl extends AbstractApplyListManager<PackableListItem> implements SutakPackageVManager {
/*    */    private SutakPackageVDAO dao;
/*    */ 
/*    */    public final void setSutakPackageVDAO(SutakPackageVDAO aDao) {
/* 21 */       this.dao = aDao;
/* 22 */    }
/*    */ 
/*    */    public final List<PackableListItem> findAllApplyable() {
/* 25 */       return this.dao.findAll();
/*    */    }
/*    */ 
/*    */    public final List<PackableListItem> findApplyableByOrderNr(String orderNr) {
/* 29 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */    public final void refresh(SutakPackageV sutakPackageV) {
/* 33 */       this.dao.refresh(sutakPackageV);
/*    */ 
/* 35 */    }
/*    */ 
/*    */    public final List<PackableListItem> findApplyableByCustomerNr(Integer customerNr) {
/* 38 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ 
/*    */    public final void refresh(PackableListItem object) {
/* 42 */       this.refresh((SutakPackageV)object);
/*    */ 
/* 44 */    }
/*    */ 
/*    */ 
/*    */    public List<PackableListItem> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 48 */       return this.dao.findByCustomerNrProductAreaGroup(customerNr, productAreaGroup);
/*    */    }
/*    */ 
/*    */ 
/*    */    public List<PackableListItem> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 53 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*    */    }
/*    */ }
