package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;

import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.AttributeChoice;
import no.ugland.utransprod.util.Util;

public class AttributeChoiceModel extends AbstractModel<AttributeChoice, AttributeChoiceModel> {
    public static final String PROPERTY_CHOICE_VALUE="choiceValue";
    public static final String PROPERTY_PROD_CAT_NO="prodCatNo";
    public static final String PROPERTY_PROD_CAT_NO_2="prodCatNo2";
    public static final String PROPERTY_COMMENT="comment";

    public AttributeChoiceModel(AttributeChoice object) {
        super(object);
    }
    
    public String getChoiceValue(){
        return object.getChoiceValue();
    }
    public void setChoiceValue(final String aValue){
        String oldValue=getChoiceValue();
        object.setChoiceValue(aValue);
        firePropertyChange(PROPERTY_CHOICE_VALUE, oldValue, aValue);
    }
    public String getProdCatNo(){
        return Util.convertIntegerToString(object.getProdCatNo());
    }
    public void setProdCatNo(final String aProdCatNo){
        String oldValue=getProdCatNo();
        object.setProdCatNo(Util.convertStringToInteger(aProdCatNo));
        firePropertyChange(PROPERTY_PROD_CAT_NO, oldValue, aProdCatNo);
    }
    public String getProdCatNo2(){
        return Util.convertIntegerToString(object.getProdCatNo2());
    }
    public void setProdCatNo2(final String aProdCatNo2){
        String oldValue=getProdCatNo2();
        object.setProdCatNo2(Util.convertStringToInteger(aProdCatNo2));
        firePropertyChange(PROPERTY_PROD_CAT_NO_2, oldValue, aProdCatNo2);
    }
    public String getComment(){
        return object.getComment();
    }
    public void setComment(final String aComment){
        String oldValue=getComment();
        object.setComment(aComment);
        firePropertyChange(PROPERTY_COMMENT, oldValue, aComment);
    }

    @Override
    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
        presentationModel.getBufferedModel(PROPERTY_CHOICE_VALUE)
        .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PROD_CAT_NO)
        .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PROD_CAT_NO_2)
        .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_COMMENT)
        .addValueChangeListener(listener);
    }

    @Override
    public AttributeChoiceModel getBufferedObjectModel(PresentationModel presentationModel) {
        AttributeChoiceModel attributeChoiceModel = new AttributeChoiceModel(new AttributeChoice());
        attributeChoiceModel.setChoiceValue((String) presentationModel
                .getBufferedValue(PROPERTY_CHOICE_VALUE));
        attributeChoiceModel.setProdCatNo((String) presentationModel
                .getBufferedValue(PROPERTY_PROD_CAT_NO));
        attributeChoiceModel.setProdCatNo2((String) presentationModel
                .getBufferedValue(PROPERTY_PROD_CAT_NO_2));
        attributeChoiceModel.setComment((String) presentationModel
                .getBufferedValue(PROPERTY_COMMENT));
        return attributeChoiceModel;
    }

}
