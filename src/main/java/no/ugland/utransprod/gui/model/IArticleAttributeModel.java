/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.IArticleAttribute;
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
/*     */ public class IArticleAttributeModel extends AbstractModel<IArticleAttribute, IArticleAttributeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_ATTRIBUTE_VALUE = "attributeValue";
/*     */    public static final String PROPERTY_ATTRIBUTE_VALUE_BOOL = "attributeValueBool";
/*     */    public static final String PROPERTY_DIALOG_ORDER_ATTRIBUTE = "dialogOrderAttribute";
/*     */    public static final String PROPERTY_NUMBER_OF_ITEMS_LONG = "numberOfItemsLong";
/*     */ 
/*     */    public IArticleAttributeModel(IArticleAttribute object) {
/*  46 */       super(object);
/*  47 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAttributeValue() {
/*  53 */       return ((IArticleAttribute)this.object).getAttributeValue();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAttributeValue(String attributeValue) {
/*  60 */       String oldValue = this.getAttributeValue();
/*  61 */       ((IArticleAttribute)this.object).setAttributeValue(attributeValue);
/*  62 */       this.firePropertyChange("attributeValue", oldValue, attributeValue);
/*  63 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getAttributeValueBool() {
/*  69 */       return ((IArticleAttribute)this.object).getAttributeValueBool();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAttributeValueBool(Boolean value) {
/*  76 */       Boolean oldValue = this.getAttributeValueBool();
/*  77 */       ((IArticleAttribute)this.object).setAttributeValueBool(value);
/*  78 */       this.firePropertyChange("attributeValueBool", oldValue, value);
/*  79 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getDialogOrderAttribute() {
/*  85 */       return ((IArticleAttribute)this.object).getDialogOrderAttribute();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Long getNumberOfItemsLong() {
/*  92 */       return ((IArticleAttribute)this.object).getNumberOfItemsLong();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setNumberOfItemsLong(Long numberOfItems) {
/* 100 */       Long oldNumber = this.getNumberOfItemsLong();
/* 101 */       ((IArticleAttribute)this.object).setNumberOfItemsLong(numberOfItems);
/* 102 */       this.firePropertyChange("numberOfItemsLong", oldNumber, numberOfItems);
/*     */ 
/*     */ 
/* 105 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDialogOrderAttribute(Integer dialogOrder) {
/* 111 */       Integer oldOrder = this.getDialogOrderAttribute();
/* 112 */       ((IArticleAttribute)this.object).setDialogOrderAttribute(dialogOrder);
/* 113 */       this.firePropertyChange("dialogOrderAttribute", oldOrder, dialogOrder);
/*     */ 
/* 115 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 124 */       presentationModel.getBufferedModel("attributeValue").addValueChangeListener(listener);
/*     */ 
/* 126 */       presentationModel.getBufferedModel("attributeValueBool").addValueChangeListener(listener);
/*     */ 
/* 128 */       presentationModel.getBufferedModel("dialogOrderAttribute").addValueChangeListener(listener);
/*     */ 
/* 130 */       presentationModel.getBufferedModel("numberOfItemsLong").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 133 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public IArticleAttributeModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 141 */       IArticleAttributeModel model = new IArticleAttributeModel(((IArticleAttribute)this.object).getNewInstance());
/*     */ 
/* 143 */       model.setAttributeValue((String)presentationModel.getBufferedValue("attributeValue"));
/*     */ 
/* 145 */       model.setAttributeValueBool((Boolean)presentationModel.getBufferedValue("attributeValueBool"));
/*     */ 
/* 147 */       model.setDialogOrderAttribute((Integer)presentationModel.getBufferedValue("dialogOrderAttribute"));
/*     */ 
/* 149 */       model.setNumberOfItemsLong((Long)presentationModel.getBufferedValue("numberOfItemsLong"));
/*     */ 
/* 151 */       return model;
/*     */    }
/*     */ }
