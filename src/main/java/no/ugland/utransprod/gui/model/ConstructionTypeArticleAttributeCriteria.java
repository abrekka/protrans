/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import no.ugland.utransprod.model.ArticleTypeAttribute;
/*     */ import no.ugland.utransprod.model.ConstructionTypeArticle;
/*     */ import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
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
/*     */ public class ConstructionTypeArticleAttributeCriteria extends ConstructionTypeArticleAttribute {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_ATTRIBUTE_VALUE_BOOLEAN = "attributeValueBoolean";
/*     */    public static final String PROPERTY_ATTRIBUTE_VALUE_FROM = "attributeValueFrom";
/*     */    public static final String PROPERTY_ATTRIBUTE_VALUE_TO = "attributeValueTo";
/*     */    public static final String PROPERTY_ATTRIBUTE_NAME = "attributeName";
/*     */    private String attributeValueFrom;
/*     */    private String attributeValueTo;
/*     */ 
/*     */    public ConstructionTypeArticleAttributeCriteria(ArticleTypeAttribute attribute, ConstructionTypeArticle article) {
/*  56 */       super((Integer)null, article, attribute, (String)null, (Integer)null);
/*  57 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAttributeValueFrom() {
/*  63 */       return this.attributeValueFrom;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAttributeValueFrom(String attributeValueFrom) {
/*  70 */       this.attributeValueFrom = attributeValueFrom;
/*  71 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAttributeValueTo() {
/*  77 */       return this.attributeValueTo;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAttributeValueTo(String attributeValueTo) {
/*  84 */       this.attributeValueTo = attributeValueTo;
/*  85 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getAttributeValueBoolean() {
/*  91 */       return this.attributeValueFrom != null && this.attributeValueFrom.equalsIgnoreCase("Ja") ? Boolean.TRUE : Boolean.FALSE;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAttributeValueBoolean(Boolean bool) {
/* 102 */       if (bool) {
/* 103 */          this.attributeValueFrom = "Ja";
/*     */       } else {
/* 105 */          this.attributeValueFrom = "Nei";
/*     */       }
/* 107 */    }
/*     */ }
