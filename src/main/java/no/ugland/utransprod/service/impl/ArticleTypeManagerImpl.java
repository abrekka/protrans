/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.ArticleTypeDAO;
/*     */ import no.ugland.utransprod.model.ArticleType;
/*     */ import no.ugland.utransprod.model.Attribute;
/*     */ import no.ugland.utransprod.service.ArticleTypeManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArticleTypeManagerImpl extends ManagerImpl<ArticleType> implements ArticleTypeManager {
/*     */    public final ArticleType findByName(String name) {
/*  22 */       ArticleType articleType = ((ArticleTypeDAO)this.dao).findByName(name);
/*     */ 
/*  24 */       return articleType;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveArticleType(ArticleType articleType) {
/*  32 */       this.dao.saveObject(articleType);
/*     */ 
/*  34 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeArticleType(ArticleType articleType) {
/*  41 */       this.dao.removeObject(articleType.getArticleTypeId());
/*     */ 
/*  43 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ArticleType> findAll() {
/*  49 */       return this.dao.getObjects("articleTypeName");
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ArticleType> findByObject(ArticleType object) {
/*  58 */       return this.dao.findByExampleLike(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(ArticleType object) {
/*  66 */       this.removeArticleType(object);
/*     */ 
/*  68 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(ArticleType object) {
/*  75 */       this.saveArticleType(object);
/*     */ 
/*  77 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(ArticleType articleType) {
/*  84 */       ((ArticleTypeDAO)this.dao).refreshObject(articleType);
/*     */ 
/*  86 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoad(ArticleType articletype, LazyLoadArticleTypeEnum[] enums) {
/*  93 */       ((ArticleTypeDAO)this.dao).lazyLoad(articletype, enums);
/*     */ 
/*  95 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Attribute> findAllConstructionTypeAttributes() {
/* 101 */       return ((ArticleTypeDAO)this.dao).findAllConstructionTypeAttributes();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ArticleType> findAllTopLevel() {
/* 108 */       ArticleType example = new ArticleType();
/* 109 */       example.setTopLevel(1);
/*     */ 
/* 111 */       return this.dao.findByExample(example);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void lazyLoad(ArticleType object, Enum[] enums) {
/* 128 */       this.lazyLoad(object, (LazyLoadArticleTypeEnum[])((LazyLoadArticleTypeEnum[])enums));
/*     */ 
/* 130 */    }
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(ArticleType object) {
/* 134 */       return object.getArticleTypeId();
/*     */    }
/*     */ 
/*     */    public ArticleType merge(ArticleType object) {
/* 138 */       return (ArticleType)this.dao.merge(object);
/*     */    }
/*     */ }
