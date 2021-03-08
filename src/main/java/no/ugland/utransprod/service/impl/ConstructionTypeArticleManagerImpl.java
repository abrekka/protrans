/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import no.ugland.utransprod.model.ConstructionTypeArticle;
/*    */ import no.ugland.utransprod.service.ConstructionTypeArticleManager;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstructionTypeArticleManagerImpl extends ManagerImpl<ConstructionTypeArticle> implements ConstructionTypeArticleManager {
/*    */    public final void saveConstructionTypeArticle(ConstructionTypeArticle article) {
/* 21 */       this.dao.saveObject(article);
/*    */ 
/* 23 */    }
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(ConstructionTypeArticle object) {
/* 27 */       return object.getConstructionTypeArticleId();
/*    */    }
/*    */ }
