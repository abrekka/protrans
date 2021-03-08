/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.IgarasjenPackageVDAO;
/*    */ import no.ugland.utransprod.model.IgarasjenPackageV;
/*    */ import no.ugland.utransprod.model.PackableListItem;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.IgarasjenPackageVManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IgarasjenPackageVManagerImpl extends AbstractApplyListManager<PackableListItem> implements IgarasjenPackageVManager {
/*    */    private IgarasjenPackageVDAO dao;
/*    */ 
/*    */    public final void setIgarasjenPackageVDAO(IgarasjenPackageVDAO aDao) {
/* 19 */       this.dao = aDao;
/* 20 */    }
/*    */ 
/*    */    public final List<PackableListItem> findAllApplyable() {
/* 23 */       return this.dao.findAll();
/*    */    }
/*    */ 
/*    */    public final List<PackableListItem> findApplyableByOrderNr(String orderNr) {
/* 27 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */    public final void refresh(IgarasjenPackageV igarasjenPackageV) {
/* 31 */       this.dao.refresh(igarasjenPackageV);
/*    */ 
/* 33 */    }
/*    */ 
/*    */    public final List<PackableListItem> findApplyableByCustomerNr(Integer customerNr) {
/* 36 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ 
/*    */    public final void refresh(PackableListItem object) {
/* 40 */       this.refresh((IgarasjenPackageV)object);
/*    */ 
/* 42 */    }
/*    */ 
/*    */ 
/*    */    public List<PackableListItem> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 46 */       return this.dao.findByCustomerNrProductAreaGroup(customerNr, productAreaGroup);
/*    */    }
/*    */ 
/*    */ 
/*    */    public List<PackableListItem> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 51 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*    */    }
/*    */ }
