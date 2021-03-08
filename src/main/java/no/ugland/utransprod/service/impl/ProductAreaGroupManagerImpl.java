/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.ProductAreaGroupDAO;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
/*    */ import no.ugland.utransprod.service.ProductAreaGroupManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProductAreaGroupManagerImpl implements ProductAreaGroupManager {
/*    */    private ProductAreaGroupDAO dao;
/*    */ 
/*    */    public final void setProductAreaGroupDAO(ProductAreaGroupDAO aDao) {
/* 17 */       this.dao = aDao;
/* 18 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<ProductAreaGroup> findAll() {
/* 24 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */    public final ProductAreaGroup findByName(String name) {
/* 28 */       return this.dao.findByName(name);
/*    */    }
/*    */ }
