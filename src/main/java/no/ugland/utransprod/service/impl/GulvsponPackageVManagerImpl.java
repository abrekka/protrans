/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.GulvsponPackageVDAO;
/*    */ import no.ugland.utransprod.model.GulvsponPackageV;
/*    */ import no.ugland.utransprod.model.PackableListItem;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.GulvsponPackageVManager;
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
/*    */ public class GulvsponPackageVManagerImpl extends AbstractApplyListManager<PackableListItem> implements GulvsponPackageVManager {
/*    */    private GulvsponPackageVDAO dao;
/*    */ 
/*    */    public final void setGulvsponPackageVDAO(GulvsponPackageVDAO aDao) {
/* 23 */       this.dao = aDao;
/* 24 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PackableListItem> findAllApplyable() {
/* 30 */       return this.dao.findAll();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PackableListItem> findApplyableByOrderNr(String orderNr) {
/* 37 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refresh(GulvsponPackageV gulvsponPackageV) {
/* 45 */       this.dao.refresh(gulvsponPackageV);
/*    */ 
/* 47 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<PackableListItem> findApplyableByCustomerNr(Integer customerNr) {
/* 53 */       return this.dao.findByCustomerNr(customerNr);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refresh(PackableListItem object) {
/* 61 */       this.refresh((GulvsponPackageV)object);
/*    */ 
/* 63 */    }
/*    */ 
/*    */    public List<PackableListItem> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 66 */       return this.dao.findByCustomerNrProductAreaGroup(customerNr, productAreaGroup);
/*    */    }
/*    */ 
/*    */    public List<PackableListItem> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 70 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*    */    }
/*    */ }
