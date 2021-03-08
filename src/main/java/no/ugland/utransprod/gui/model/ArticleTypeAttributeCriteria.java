/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.ArticleTypeAttribute;
/*     */ import no.ugland.utransprod.model.AttributeChoice;
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
/*     */ public class ArticleTypeAttributeCriteria extends ArticleTypeAttribute {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_ATTRIBUTE_VALUE_BOOLEAN = "attributeValueBoolean";
/*     */    public static final String PROPERTY_ATTRIBUTE_VALUE_FROM = "attributeValueFrom";
/*     */    public static final String PROPERTY_ATTRIBUTE_VALUE_TO = "attributeValueTo";
/*     */    public static final String PROPERTY_ATTRIBUTE_NAME = "attributeName";
/*     */    private String attributeValueFrom;
/*     */    private String attributeValueTo;
/*     */ 
/*     */    public ArticleTypeAttributeCriteria(ArticleTypeAttribute attribute) {
/*  56 */       super((Integer)null, attribute.getArticleType(), attribute.getAttribute(), attribute.getConstructionTypeArticleAttributes(), attribute.getAttributeFormula());
/*     */ 
/*     */ 
/*  59 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAttributeValueFrom() {
/*  65 */       return this.attributeValueFrom;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAttributeValueFrom(String attributeValueFrom) {
/*  72 */       this.attributeValueFrom = attributeValueFrom;
/*  73 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAttributeValueTo() {
/*  79 */       return this.attributeValueTo;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAttributeValueTo(String attributeValueTo) {
/*  86 */       this.attributeValueTo = attributeValueTo;
/*  87 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getAttributeValueBoolean() {
/*  93 */       return this.attributeValueFrom != null && this.attributeValueFrom.equalsIgnoreCase("Ja") ? Boolean.TRUE : Boolean.FALSE;
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
/* 104 */       if (bool) {
/* 105 */          this.attributeValueFrom = "Ja";
/*     */       } else {
/* 107 */          this.attributeValueFrom = "Nei";
/*     */       }
/* 109 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean isYesNo() {
/* 115 */       Integer yesNo = null;
/*     */ 
/* 117 */       yesNo = this.getAttribute().getYesNo();
/* 118 */       return yesNo != null && yesNo > 0 ? Boolean.TRUE : Boolean.FALSE;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<String> getChoices() {
/* 128 */       List<String> list = new ArrayList();
/* 129 */       Set<AttributeChoice> choices = this.getAttribute().getAttributeChoices();
/* 130 */       if (choices != null) {         Iterator var3 = choices.iterator();         while(var3.hasNext()) {
/* 131 */             AttributeChoice choice = (AttributeChoice)var3.next();
/* 132 */             list.add(choice.getChoiceValue());
/*     */          }      }
/*     */ 
/* 135 */       return list;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAttributeName() {
/* 143 */       return this.getAttribute().getName();
/*     */    }
/*     */ }
