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
/*     */ import no.ugland.utransprod.model.Attribute;
/*     */ import no.ugland.utransprod.model.ConstructionType;
/*     */ import no.ugland.utransprod.model.ConstructionTypeArticle;
/*     */ import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
/*     */ import no.ugland.utransprod.model.ConstructionTypeAttribute;
/*     */ import no.ugland.utransprod.model.ProductArea;
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
/*     */ public class ConstructionTypeModel extends AbstractModel<ConstructionType, ConstructionTypeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_CONSTRUCTION_TYPE_ID = "constructionTypeId";
/*     */    public static final String PROPERTY_NAME = "name";
/*     */    public static final String PROPERTY_DESCRIPTION = "description";
/*     */    public static final String PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES = "constructionTypeAttributes";
/*     */    public static final String PROPERTY_ATTRIBUTES = "attributes";
/*     */    public static final String PROPERTY_ARTICLES = "articles";
/*     */    public static final String PROPERTY_CONSTRUCTION_TYPE_ARTICLES = "constructionTypeArticles";
/*     */    public static final String PROPERTY_SKETCH_NAME = "sketchName";
/*     */    public static final String PROPERTY_PRODUCT_AREA = "productArea";
/*     */    public static final String PROPERTY_IS_MASTER = "isMaster";
/*     */    private ArrayListModel constructionTypeAttributes;
/*     */    private ArrayListModel constructionTypeArticles;
/*     */ 
/*     */    public ConstructionTypeModel(ConstructionType aConstructionType) {
/*  96 */       super(aConstructionType);
/*  97 */       if (((ConstructionType)this.object).getConstructionTypeAttributes() != null) {
/*  98 */          this.constructionTypeAttributes = new ArrayListModel(((ConstructionType)this.object).getConstructionTypeAttributes());
/*     */ 
/*     */       } else {
/* 101 */          this.constructionTypeAttributes = new ArrayListModel();
/*     */       }
/*     */ 
/* 104 */       if (((ConstructionType)this.object).getConstructionTypeArticles() != null) {
/* 105 */          this.constructionTypeArticles = new ArrayListModel(((ConstructionType)this.object).getConstructionTypeArticles());
/*     */ 
/*     */       } else {
/* 108 */          this.constructionTypeArticles = new ArrayListModel();
/*     */       }
/* 110 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<Attribute> getAttributes() {
/* 116 */       return ((ConstructionType)this.object).getAttributes();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<ArticleType> getArticles() {
/* 123 */       return ((ConstructionType)this.object).getArticles();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getConstructionTypeAttributes() {
/* 130 */       return this.constructionTypeAttributes;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getConstructionTypeArticles() {
/* 137 */       return this.constructionTypeArticles;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setConstructionTypeAttributes(ArrayListModel constructionTypeAttributes) {
/* 145 */       ArrayListModel oldConstructionTypeAttributes = this.getConstructionTypeAttributes();
/* 146 */       this.constructionTypeAttributes = constructionTypeAttributes;
/* 147 */       this.firePropertyChange("constructionTypeAttributes", oldConstructionTypeAttributes, constructionTypeAttributes);
/*     */ 
/* 149 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setConstructionTypeArticles(ArrayListModel constructionTypeArticles) {
/* 156 */       ArrayListModel oldConstructionTypeArticles = this.getConstructionTypeArticles();
/* 157 */       this.constructionTypeArticles = constructionTypeArticles;
/* 158 */       this.firePropertyChange("constructionTypeArticles", oldConstructionTypeArticles, constructionTypeArticles);
/*     */ 
/* 160 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getConstructionTypeId() {
/* 166 */       return ((ConstructionType)this.object).getConstructionTypeId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setConstructionTypeId(Integer constructionTypeId) {
/* 173 */       Integer oldId = this.getConstructionTypeId();
/* 174 */       ((ConstructionType)this.object).setConstructionTypeId(constructionTypeId);
/* 175 */       this.firePropertyChange("constructionTypeId", oldId, constructionTypeId);
/*     */ 
/* 177 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getName() {
/* 183 */       return ((ConstructionType)this.object).getName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setName(String name) {
/* 190 */       String oldName = this.getName();
/* 191 */       ((ConstructionType)this.object).setName(name);
/* 192 */       this.firePropertyChange("name", oldName, name);
/* 193 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDescription() {
/* 199 */       return ((ConstructionType)this.object).getDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDescription(String description) {
/* 206 */       String oldDesc = this.getDescription();
/* 207 */       ((ConstructionType)this.object).setDescription(description);
/* 208 */       this.firePropertyChange("description", oldDesc, description);
/* 209 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public SketchEnum getSketchName() {
/* 215 */       return SketchEnum.getSketchEnum(((ConstructionType)this.object).getSketchName());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSketchName(SketchEnum sketchEnum) {
/* 224 */       SketchEnum oldName = this.getSketchName();
/* 225 */       if (sketchEnum != null) {
/* 226 */          ((ConstructionType)this.object).setSketchName(sketchEnum.getFileName());
/*     */       }
/* 228 */       this.firePropertyChange("sketchName", oldName, sketchEnum);
/* 229 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ProductArea getProductArea() {
/* 235 */       return ((ConstructionType)this.object).getProductArea();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setProductArea(ProductArea productArea) {
/* 244 */       ProductArea oldArea = this.getProductArea();
/* 245 */       ((ConstructionType)this.object).setProductArea(productArea);
/*     */ 
/* 247 */       this.firePropertyChange("productArea", oldArea, productArea);
/* 248 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getIsMaster() {
/* 254 */       return ((ConstructionType)this.object).getIsMaster();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setIsMaster(Integer isMaster) {
/* 263 */       Integer oldMaster = this.getIsMaster();
/* 264 */       ((ConstructionType)this.object).setIsMaster(isMaster);
/*     */ 
/* 266 */       this.firePropertyChange("isMaster", oldMaster, isMaster);
/* 267 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 276 */       presentationModel.getBufferedModel("name").addValueChangeListener(listener);
/*     */ 
/* 278 */       presentationModel.getBufferedModel("description").addValueChangeListener(listener);
/*     */ 
/* 280 */       presentationModel.getBufferedModel("constructionTypeAttributes").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 283 */       presentationModel.getBufferedModel("constructionTypeArticles").addValueChangeListener(listener);
/*     */ 
/* 285 */       presentationModel.getBufferedModel("sketchName").addValueChangeListener(listener);
/*     */ 
/* 287 */       presentationModel.getBufferedModel("productArea").addValueChangeListener(listener);
/*     */ 
/* 289 */       presentationModel.getBufferedModel("isMaster").addValueChangeListener(listener);
/*     */ 
/* 291 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ConstructionTypeModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 299 */       ConstructionTypeModel constructionTypeModel = new ConstructionTypeModel(new ConstructionType());
/*     */ 
/* 301 */       constructionTypeModel.setConstructionTypeId((Integer)presentationModel.getBufferedValue("constructionTypeId"));
/*     */ 
/* 303 */       constructionTypeModel.setName((String)presentationModel.getBufferedValue("name"));
/*     */ 
/* 305 */       constructionTypeModel.setDescription((String)presentationModel.getBufferedValue("description"));
/*     */ 
/* 307 */       constructionTypeModel.setConstructionTypeAttributes((ArrayListModel)presentationModel.getBufferedValue("constructionTypeAttributes"));
/*     */ 
/*     */ 
/* 310 */       constructionTypeModel.setConstructionTypeArticles((ArrayListModel)presentationModel.getBufferedValue("constructionTypeArticles"));
/*     */ 
/*     */ 
/* 313 */       constructionTypeModel.setSketchName((SketchEnum)presentationModel.getBufferedValue("sketchName"));
/*     */ 
/* 315 */       constructionTypeModel.setProductArea((ProductArea)presentationModel.getBufferedValue("productArea"));
/*     */ 
/* 317 */       constructionTypeModel.setIsMaster((Integer)presentationModel.getBufferedValue("isMaster"));
/*     */ 
/* 319 */       return constructionTypeModel;
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
/*     */    public ConstructionTypeAttribute getArticleAttribute(Attribute attribute, String attributeValue, Integer dialogOrder) {
/* 332 */       return attribute != null ? new ConstructionTypeAttribute((Integer)null, (ConstructionType)this.object, attribute, attributeValue, (Set)null, dialogOrder) : null;
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
/*     */    public void viewToModel() {
/*     */       Object articles;
/* 347 */       if (this.constructionTypeAttributes != null) {
/* 348 */          articles = ((ConstructionType)this.object).getConstructionTypeAttributes();
/*     */ 
/* 350 */          if (articles == null) {
/* 351 */             articles = new HashSet();
/*     */          }
/* 353 */          ((Set)articles).clear();
/* 354 */          ((Set)articles).addAll(this.constructionTypeAttributes);
/* 355 */          ((ConstructionType)this.object).setConstructionTypeAttributes((Set)articles);
/*     */       }
/*     */ 
/* 358 */       if (this.constructionTypeArticles != null) {
/* 359 */          articles = ((ConstructionType)this.object).getConstructionTypeArticles();
/*     */ 
/* 361 */          if (articles == null) {
/* 362 */             articles = new HashSet();
/*     */          }
/* 364 */          ((Set)articles).clear();
/* 365 */          ((Set)articles).addAll(this.constructionTypeArticles);
/* 366 */          ((ConstructionType)this.object).setConstructionTypeArticles((Set)articles);
/*     */       }
/*     */ 
/* 369 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void modelToView() {
/* 376 */       if (((ConstructionType)this.object).getConstructionTypeAttributes() != null) {
/* 377 */          this.constructionTypeAttributes.clear();
/* 378 */          this.constructionTypeAttributes.addAll(((ConstructionType)this.object).getConstructionTypeAttributes());
/*     */ 
/*     */       }
/*     */ 
/* 382 */       if (((ConstructionType)this.object).getConstructionTypeArticles() != null) {
/* 383 */          this.constructionTypeArticles.clear();
/* 384 */          this.constructionTypeArticles.addAll(((ConstructionType)this.object).getConstructionTypeArticles());
/*     */       }
/*     */ 
/* 387 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static ConstructionType cloneConstructionType(ConstructionType original) {
/* 397 */       ConstructionType cloned = new ConstructionType();
/* 398 */       cloned.setConstructionTypeId(original.getConstructionTypeId());
/*     */ 
/* 400 */       cloned.setConstructionTypeArticles(clonedConstructionTypeArticles(original.getConstructionTypeArticles()));
/*     */ 
/* 402 */       return cloned;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static Set<ConstructionTypeArticle> clonedConstructionTypeArticles(Set<ConstructionTypeArticle> articles) {
/* 413 */       HashSet<ConstructionTypeArticle> clonedSet = new HashSet();
/* 414 */       if (articles != null) {         Iterator var2 = articles.iterator();         while(var2.hasNext()) {
/* 415 */             ConstructionTypeArticle article = (ConstructionTypeArticle)var2.next();
/* 416 */             clonedSet.add(new ConstructionTypeArticle(article.getConstructionTypeArticleId(), article.getConstructionType(), article.getArticleType(), clonedAttributes(article.getAttributes()), article.getConstructionTypeArticleRef(), clonedConstructionTypeArticles(article.getConstructionTypeArticles()), article.getNumberOfItems(), article.getOrderLines(), article.getDialogOrder()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */          }
/*     */       }
/*     */ 
/* 427 */       return clonedSet;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private static Set<ConstructionTypeArticleAttribute> clonedAttributes(Set<ConstructionTypeArticleAttribute> orgSet) {
/* 438 */       HashSet<ConstructionTypeArticleAttribute> clonedSet = new HashSet();
/*     */ 
/* 440 */       if (orgSet != null) {         Iterator var2 = orgSet.iterator();         while(var2.hasNext()) {
/* 441 */             ConstructionTypeArticleAttribute attribute = (ConstructionTypeArticleAttribute)var2.next();
/* 442 */             clonedSet.add(new ConstructionTypeArticleAttribute(attribute.getConstructionTypeArticleAttributeId(), attribute.getConstructionTypeArticle(), attribute.getArticleTypeAttribute(), attribute.getConstructionTypeArticleValue(), attribute.getDialogOrder()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */          }
/*     */       }
/*     */ 
/* 450 */       return clonedSet;
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
/*     */    public static Set<ConstructionTypeAttribute> copyConstructionTypeAttributes(ConstructionType constructionType, Set<ConstructionTypeAttribute> attributes) {
/* 463 */       HashSet<ConstructionTypeAttribute> newAttributes = null;
/* 464 */       if (attributes != null) {
/* 465 */          newAttributes = new HashSet();         Iterator var3 = attributes.iterator();         while(var3.hasNext()) {
/* 466 */             ConstructionTypeAttribute attribute = (ConstructionTypeAttribute)var3.next();
/* 467 */             newAttributes.add(new ConstructionTypeAttribute((Integer)null, constructionType, attribute.getAttribute(), attribute.getAttributeValue(), attribute.getOrderLineAttributes(), attribute.getDialogOrder()));
/*     */ 
/*     */ 
/*     */ 
/*     */          }
/*     */       }
/*     */ 
/* 474 */       return newAttributes;
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
/*     */    public static Set<ConstructionTypeArticle> copyConstructionTypeArticles(ConstructionType newConstructionType, Set<ConstructionTypeArticle> orgArticles, ConstructionTypeArticle constructionTypeArticleRef) {
/* 489 */       HashSet<ConstructionTypeArticle> newArticles = null;
/*     */ 
/* 491 */       if (orgArticles != null && orgArticles.size() != 0) {
/* 492 */          newArticles = new HashSet();
/*     */ 
/*     */ 
/* 495 */          Iterator var5 = orgArticles.iterator();         while(var5.hasNext()) {            ConstructionTypeArticle article = (ConstructionTypeArticle)var5.next();
/* 496 */             ConstructionTypeArticle newConstructionTypeArticle = new ConstructionTypeArticle((Integer)null, newConstructionType, article.getArticleType(), (Set)null, constructionTypeArticleRef, (Set)null, article.getNumberOfItems(), (Set)null, article.getDialogOrder());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 502 */             newConstructionTypeArticle.setConstructionTypeArticles(copyConstructionTypeArticles((ConstructionType)null, article.getConstructionTypeArticles(), newConstructionTypeArticle));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 507 */             newConstructionTypeArticle.setAttributes(copyConstructionTypeArticleAttributes(newConstructionTypeArticle, article.getAttributes()));
/*     */ 
/*     */ 
/*     */ 
/* 511 */             newArticles.add(newConstructionTypeArticle);
/*     */          }      }
/*     */ 
/* 514 */       return newArticles;
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
/*     */    private static Set<ConstructionTypeArticleAttribute> copyConstructionTypeArticleAttributes(ConstructionTypeArticle constructionTypeArticle, Set<ConstructionTypeArticleAttribute> attributes) {
/* 527 */       HashSet<ConstructionTypeArticleAttribute> newAttributes = null;
/* 528 */       if (attributes != null && attributes.size() != 0) {
/* 529 */          newAttributes = new HashSet();
/*     */          Iterator var3 = attributes.iterator();         while(var3.hasNext()) {
/* 531 */             ConstructionTypeArticleAttribute attribute = (ConstructionTypeArticleAttribute)var3.next();
/* 532 */             newAttributes.add(new ConstructionTypeArticleAttribute((Integer)null, constructionTypeArticle, attribute.getArticleTypeAttribute(), attribute.getConstructionTypeArticleValue(), attribute.getDialogOrder()));
/*     */ 
/*     */ 
/*     */ 
/*     */          }
/*     */       }
/*     */ 
/* 539 */       return newAttributes;
/*     */    }
/*     */ }
