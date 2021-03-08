/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.list.ArrayListModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.ArticleType;
/*     */ import no.ugland.utransprod.model.ArticleTypeArticleType;
/*     */ import no.ugland.utransprod.model.ArticleTypeAttribute;
/*     */ import no.ugland.utransprod.model.Attribute;
/*     */ import no.ugland.utransprod.util.Util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArticleTypeModel extends AbstractModel<ArticleType, ArticleTypeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_ARTICLE_TYPE_ID = "articleTypeId";
/*     */    public static final String PROPERTY_ARTICLE_TYPE_NAME = "articleTypeName";
/*     */    public static final String PROPERTY_DESCRIPTION = "description";
/*     */    public static final String PROPERTY_ARTICLE_TYPE_ATTRIBUTES = "articleTypeAttributes";
/*     */    public static final String PROPERTY_ATTRIBUTES = "attributes";
/*     */    public static final String PROPERTY_ARTICLES = "articles";
/*     */    public static final String PROPERTY_ARTICLE_TYPE_ARTICLE_TYPES = "articleTypeArticleTypes";
/*     */    public static final String PROPERTY_TOP_LEVEL_BOOL = "topLevelBoolean";
/*     */    public static final String PROPERTY_METRIC = "metric";
/*     */    public static final String PROPERTY_PROD_CAT_NO = "prodCatNo";
/*     */    public static final String PROPERTY_PROD_CAT_NO_2 = "prodCatNo2";
/*     */    public static final String PROPERTY_FORCE_IMPORT_BOOL = "forceImportBoolean";
/*     */    private ArrayListModel articleTypeAttributes;
/*     */    private ArrayListModel articleTypeArticleTypes;
/*     */ 
/*     */    public ArticleTypeModel(ArticleType object) {
/*  84 */       super(object);
/*  85 */       if (object.getArticleTypeAttributes() != null) {
/*     */ 
/*  87 */          this.articleTypeAttributes = new ArrayListModel(object.getArticleTypeAttributes());
/*     */       } else {
/*  89 */          this.articleTypeAttributes = new ArrayListModel();
/*     */       }
/*     */ 
/*  92 */       if (object.getArticleTypeArticleTypes() != null) {
/*     */ 
/*  94 */          this.articleTypeArticleTypes = new ArrayListModel(object.getArticleTypeArticleTypes());
/*     */       } else {
/*  96 */          this.articleTypeArticleTypes = new ArrayListModel();
/*     */       }
/*  98 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<ArticleType> getArticles() {
/* 105 */       return ((ArticleType)this.object).getArticles();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getArticleTypeId() {
/* 112 */       return ((ArticleType)this.object).getArticleTypeId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setArticleTypeId(Integer articleTypeId) {
/* 119 */       Integer oldId = this.getArticleTypeId();
/* 120 */       ((ArticleType)this.object).setArticleTypeId(articleTypeId);
/* 121 */       this.firePropertyChange("articleTypeId", oldId, articleTypeId);
/* 122 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getArticleTypeName() {
/* 128 */       return ((ArticleType)this.object).getArticleTypeName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setArticleTypeName(String articleTypeName) {
/* 135 */       String oldName = this.getArticleTypeName();
/* 136 */       ((ArticleType)this.object).setArticleTypeName(articleTypeName);
/* 137 */       this.firePropertyChange("articleTypeName", oldName, articleTypeName);
/* 138 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDescription() {
/* 144 */       return ((ArticleType)this.object).getDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDescription(String description) {
/* 151 */       String oldDesc = this.getDescription();
/* 152 */       ((ArticleType)this.object).setDescription(description);
/* 153 */       this.firePropertyChange("description", oldDesc, description);
/* 154 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getArticleTypeAttributes() {
/* 160 */       return this.articleTypeAttributes;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setArticleTypeAttributes(ArrayListModel articleTypeAttributes) {
/* 167 */       ArrayListModel oldArticleTypeAttributes = this.getArticleTypeAttributes();
/* 168 */       this.articleTypeAttributes = articleTypeAttributes;
/* 169 */       this.firePropertyChange("articleTypeAttributes", oldArticleTypeAttributes, articleTypeAttributes);
/*     */ 
/* 171 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getArticleTypeArticleTypes() {
/* 178 */       return this.articleTypeArticleTypes;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setArticleTypeArticleTypes(ArrayListModel articleTypeArticleTypes) {
/* 186 */       ArrayListModel oldArticleTypeArticles = this.getArticleTypeArticleTypes();
/* 187 */       this.articleTypeArticleTypes = articleTypeArticleTypes;
/* 188 */       this.firePropertyChange("articleTypeArticleTypes", oldArticleTypeArticles, articleTypeArticleTypes);
/*     */ 
/* 190 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<Attribute> getAttributes() {
/* 196 */       return ((ArticleType)this.object).getAttributes();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAttributes(List<Attribute> attributes) {
/* 203 */       if (attributes != null) {
/* 204 */          ArrayListModel oldAttributes = this.getArticleTypeAttributes();
/* 205 */          if (oldAttributes == null) {
/* 206 */             oldAttributes = new ArrayListModel();
/*     */          }         Iterator var4 = attributes.iterator();
/*     */          while(var4.hasNext()) {
/* 209 */             Attribute attribute = (Attribute)var4.next();
/* 210 */             ArticleTypeAttribute articleTypeAttribute = new ArticleTypeAttribute((Integer)null, (ArticleType)this.object, attribute, (Set)null, (String)null);
/*     */ 
/* 212 */             oldAttributes.add(articleTypeAttribute);
/*     */          }
/* 214 */          this.setArticleTypeAttributes(oldAttributes);
/*     */       }
/* 216 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void removeAttribute(ArticleTypeAttribute attribute) {
/* 223 */       ArrayListModel oldAttributes = new ArrayListModel(this.getArticleTypeAttributes());
/* 224 */       oldAttributes.remove(attribute);
/* 225 */       this.setArticleTypeAttributes(oldAttributes);
/* 226 */    }
/*     */ 
/*     */    public String getProdCatNo() {
/* 229 */       return Util.convertIntegerToString(((ArticleType)this.object).getProdCatNo());
/*     */    }
/*     */    public void setProdCatNo(String aNo) {
/* 232 */       String oldNo = this.getProdCatNo();
/* 233 */       ((ArticleType)this.object).setProdCatNo(Util.convertStringToInteger(aNo));
/* 234 */       this.firePropertyChange("prodCatNo", oldNo, aNo);
/* 235 */    }
/*     */    public String getProdCatNo2() {
/* 237 */       return Util.convertIntegerToString(((ArticleType)this.object).getProdCatNo2());
/*     */    }
/*     */    public void setProdCatNo2(String aNo) {
/* 240 */       String oldNo = this.getProdCatNo2();
/* 241 */       ((ArticleType)this.object).setProdCatNo2(Util.convertStringToInteger(aNo));
/* 242 */       this.firePropertyChange("prodCatNo2", oldNo, aNo);
/* 243 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArticleTypeModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 251 */       ArticleTypeModel articleTypeModel = new ArticleTypeModel(new ArticleType());
/*     */ 
/* 253 */       articleTypeModel.setArticleTypeId((Integer)presentationModel.getBufferedValue("articleTypeId"));
/*     */ 
/* 255 */       articleTypeModel.setArticleTypeName((String)presentationModel.getBufferedValue("articleTypeName"));
/*     */ 
/* 257 */       articleTypeModel.setDescription((String)presentationModel.getBufferedValue("description"));
/*     */ 
/* 259 */       articleTypeModel.setArticleTypeAttributes((ArrayListModel)presentationModel.getBufferedValue("articleTypeAttributes"));
/*     */ 
/*     */ 
/* 262 */       articleTypeModel.setProdCatNo((String)presentationModel.getBufferedValue("prodCatNo"));
/*     */ 
/*     */ 
/* 265 */       articleTypeModel.setProdCatNo2((String)presentationModel.getBufferedValue("prodCatNo2"));
/*     */ 
/*     */ 
/* 268 */       articleTypeModel.setForceImportBoolean((Boolean)presentationModel.getBufferedValue("forceImportBoolean"));
/*     */ 
/* 270 */       return articleTypeModel;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 280 */       presentationModel.getBufferedModel("articleTypeId").addValueChangeListener(listener);
/*     */ 
/* 282 */       presentationModel.getBufferedModel("articleTypeName").addValueChangeListener(listener);
/*     */ 
/* 284 */       presentationModel.getBufferedModel("description").addValueChangeListener(listener);
/*     */ 
/* 286 */       presentationModel.getBufferedModel("articleTypeAttributes").addValueChangeListener(listener);
/*     */ 
/* 288 */       presentationModel.getBufferedModel("prodCatNo").addValueChangeListener(listener);
/*     */ 
/* 290 */       presentationModel.getBufferedModel("prodCatNo2").addValueChangeListener(listener);
/*     */ 
/* 292 */       presentationModel.getBufferedModel("forceImportBoolean").addValueChangeListener(listener);
/*     */ 
/* 294 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void viewToModel() {
/*     */       Object refs;
/* 302 */       if (this.articleTypeAttributes != null) {
/* 303 */          refs = ((ArticleType)this.object).getArticleTypeAttributes();
/*     */ 
/* 305 */          if (refs == null) {
/* 306 */             refs = new HashSet();
/*     */          }
/* 308 */          ((Set)refs).clear();
/* 309 */          ((Set)refs).addAll(this.articleTypeAttributes);
/* 310 */          ((ArticleType)this.object).setArticleTypeAttributes((Set)refs);
/*     */       }
/*     */ 
/* 313 */       if (this.articleTypeArticleTypes != null) {
/* 314 */          refs = ((ArticleType)this.object).getArticleTypeArticleTypes();
/*     */ 
/* 316 */          if (refs == null) {
/* 317 */             refs = new HashSet();
/*     */          }
/* 319 */          ((Set)refs).clear();
/* 320 */          ((Set)refs).addAll(this.articleTypeArticleTypes);
/* 321 */          ((ArticleType)this.object).setArticleTypeArticleTypes((Set)refs);
/*     */       }
/*     */ 
/* 324 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void modelToView() {
/* 331 */       if (((ArticleType)this.object).getArticleTypeAttributes() != null) {
/* 332 */          this.articleTypeAttributes.clear();
/* 333 */          this.articleTypeAttributes.addAll(((ArticleType)this.object).getArticleTypeAttributes());
/*     */       }
/*     */ 
/* 336 */       if (((ArticleType)this.object).getArticleTypeArticleTypes() != null) {
/* 337 */          this.articleTypeArticleTypes.clear();
/* 338 */          this.articleTypeArticleTypes.addAll(((ArticleType)this.object).getArticleTypeArticleTypes());
/*     */       }
/* 340 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<ArticleTypeAttribute> getArticleAttributes(List<Attribute> attributes) {
/* 350 */       ArrayListModel tmpArticleTypeAttributes = new ArrayListModel();
/* 351 */       if (attributes != null) {
/*     */          Iterator var3 = attributes.iterator();         while(var3.hasNext()) {
/* 353 */             Attribute attribute = (Attribute)var3.next();
/* 354 */             tmpArticleTypeAttributes.add(new ArticleTypeAttribute((Integer)null, (ArticleType)this.object, attribute, (Set)null, (String)null));
/*     */          }
/*     */       }
/*     */ 
/* 358 */       return tmpArticleTypeAttributes;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void firePropertyChange() {
/* 365 */       this.fireMultiplePropertiesChanged();
/* 366 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static ArticleType cloneArticleType(ArticleType orgArticle) {
/* 374 */       ArticleType clonedArticle = new ArticleType();
/* 375 */       clonedArticle.setArticleTypeId(orgArticle.getArticleTypeId());
/*     */ 
/* 377 */       clonedArticle.setArticleTypeArticleTypes(cloneArticleTypeArticleTypes(orgArticle.getArticleTypeArticleTypes()));
/*     */ 
/* 379 */       clonedArticle.setArticleTypeName(orgArticle.getArticleTypeName());
/* 380 */       return clonedArticle;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static Set<ArticleTypeArticleType> cloneArticleTypeArticleTypes(Set<ArticleTypeArticleType> refs) {
/* 390 */       HashSet<ArticleTypeArticleType> clonedRefs = new HashSet();
/* 391 */       if (refs != null) {         Iterator var2 = refs.iterator();         while(var2.hasNext()) {
/* 392 */             ArticleTypeArticleType articleType = (ArticleTypeArticleType)var2.next();
/* 393 */             clonedRefs.add(new ArticleTypeArticleType(articleType.getArticleTypeArticleTypeId(), articleType.getArticleType(), articleType.getArticleTypeRef()));
/*     */ 
/*     */          }
/*     */       }
/*     */ 
/* 398 */       return clonedRefs;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getTopLevelBoolean() {
/* 405 */       return ((ArticleType)this.object).getTopLevelBoolean();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setTopLevelBoolean(Boolean topLevel) {
/* 412 */       Boolean oldBool = this.getTopLevelBoolean();
/* 413 */       ((ArticleType)this.object).setTopeLevelBoolean(topLevel);
/* 414 */       this.firePropertyChange("topLevelBoolean", oldBool, topLevel);
/* 415 */    }
/*     */ 
/*     */    public Boolean getForceImportBoolean() {
/* 418 */       return ((ArticleType)this.object).forceImport();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setForceImportBoolean(Boolean force) {
/* 425 */       Boolean oldBool = this.getForceImportBoolean();
/* 426 */       ((ArticleType)this.object).setForceImportBoolean(force);
/* 427 */       this.firePropertyChange("forceImportBoolean", oldBool, force);
/* 428 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMetric() {
/* 434 */       return ((ArticleType)this.object).getMetric();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMetric(String metric) {
/* 441 */       String oldMetric = this.getMetric();
/* 442 */       ((ArticleType)this.object).setMetric(metric);
/* 443 */       this.firePropertyChange("metric", oldMetric, metric);
/* 444 */    }
/*     */ }
