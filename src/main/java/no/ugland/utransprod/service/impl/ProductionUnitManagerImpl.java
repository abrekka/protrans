/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.ProductionUnitDAO;
/*    */ import no.ugland.utransprod.model.ArticleType;
/*    */ import no.ugland.utransprod.model.ProductionUnit;
/*    */ import no.ugland.utransprod.service.ProductionUnitManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProductionUnitManagerImpl extends ManagerImpl<ProductionUnit> implements ProductionUnitManager {
/*    */    protected Serializable getObjectId(ProductionUnit object) {
/* 15 */       return object.getProductionUnitId();
/*    */    }
/*    */ 
/*    */    public List<ProductionUnit> findByArticleTypeProductAreaGroup(ArticleType articleType, String productAreaGroupName) {
/* 19 */       return ((ProductionUnitDAO)this.dao).findByArticleTypeProductAreaGroup(articleType, productAreaGroupName);
/*    */    }
/*    */ 
/*    */    public ProductionUnit findByName(String name) {
/* 23 */       return ((ProductionUnitDAO)this.dao).findByName(name);
/*    */    }
/*    */ }
