/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import no.ugland.utransprod.model.AttributeChoice;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeChoiceModel extends AbstractModel<AttributeChoice, AttributeChoiceModel> {
/*    */    public static final String PROPERTY_CHOICE_VALUE = "choiceValue";
/*    */    public static final String PROPERTY_PROD_CAT_NO = "prodCatNo";
/*    */    public static final String PROPERTY_PROD_CAT_NO_2 = "prodCatNo2";
/*    */    public static final String PROPERTY_COMMENT = "comment";
/*    */ 
/*    */    public AttributeChoiceModel(AttributeChoice object) {
/* 19 */       super(object);
/* 20 */    }
/*    */ 
/*    */    public String getChoiceValue() {
/* 23 */       return ((AttributeChoice)this.object).getChoiceValue();
/*    */    }
/*    */    public void setChoiceValue(String aValue) {
/* 26 */       String oldValue = this.getChoiceValue();
/* 27 */       ((AttributeChoice)this.object).setChoiceValue(aValue);
/* 28 */       this.firePropertyChange("choiceValue", oldValue, aValue);
/* 29 */    }
/*    */    public String getProdCatNo() {
/* 31 */       return Util.convertIntegerToString(((AttributeChoice)this.object).getProdCatNo());
/*    */    }
/*    */    public void setProdCatNo(String aProdCatNo) {
/* 34 */       String oldValue = this.getProdCatNo();
/* 35 */       ((AttributeChoice)this.object).setProdCatNo(Util.convertStringToInteger(aProdCatNo));
/* 36 */       this.firePropertyChange("prodCatNo", oldValue, aProdCatNo);
/* 37 */    }
/*    */    public String getProdCatNo2() {
/* 39 */       return Util.convertIntegerToString(((AttributeChoice)this.object).getProdCatNo2());
/*    */    }
/*    */    public void setProdCatNo2(String aProdCatNo2) {
/* 42 */       String oldValue = this.getProdCatNo2();
/* 43 */       ((AttributeChoice)this.object).setProdCatNo2(Util.convertStringToInteger(aProdCatNo2));
/* 44 */       this.firePropertyChange("prodCatNo2", oldValue, aProdCatNo2);
/* 45 */    }
/*    */    public String getComment() {
/* 47 */       return ((AttributeChoice)this.object).getComment();
/*    */    }
/*    */    public void setComment(String aComment) {
/* 50 */       String oldValue = this.getComment();
/* 51 */       ((AttributeChoice)this.object).setComment(aComment);
/* 52 */       this.firePropertyChange("comment", oldValue, aComment);
/* 53 */    }
/*    */ 
/*    */ 
/*    */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 57 */       presentationModel.getBufferedModel("choiceValue").addValueChangeListener(listener);
/*    */ 
/* 59 */       presentationModel.getBufferedModel("prodCatNo").addValueChangeListener(listener);
/*    */ 
/* 61 */       presentationModel.getBufferedModel("prodCatNo2").addValueChangeListener(listener);
/*    */ 
/* 63 */       presentationModel.getBufferedModel("comment").addValueChangeListener(listener);
/*    */ 
/* 65 */    }
/*    */ 
/*    */ 
/*    */    public AttributeChoiceModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 69 */       AttributeChoiceModel attributeChoiceModel = new AttributeChoiceModel(new AttributeChoice());
/* 70 */       attributeChoiceModel.setChoiceValue((String)presentationModel.getBufferedValue("choiceValue"));
/*    */ 
/* 72 */       attributeChoiceModel.setProdCatNo((String)presentationModel.getBufferedValue("prodCatNo"));
/*    */ 
/* 74 */       attributeChoiceModel.setProdCatNo2((String)presentationModel.getBufferedValue("prodCatNo2"));
/*    */ 
/* 76 */       attributeChoiceModel.setComment((String)presentationModel.getBufferedValue("comment"));
/*    */ 
/* 78 */       return attributeChoiceModel;
/*    */    }
/*    */ }
