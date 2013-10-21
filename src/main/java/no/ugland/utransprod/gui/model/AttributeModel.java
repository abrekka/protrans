package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;

import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.AttributeChoice;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * GUI-modell for attributt
 * 
 * @author atle.brekka
 * 
 */
public class AttributeModel extends AbstractModel<Attribute, AttributeModel> {
	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_ATTRIBUTE_ID = "attributeId";

	public static final String PROPERTY_NAME = "name";

	public static final String PROPERTY_DESCRIPTION = "description";

	public static final String PROPERTY_YES_NO = "yesNo";

	public static final String PROPERTY_CHOICE_LIST = "choiceList";

	public static final String PROPERTY_SPECIAL_CONCERN = "specialConcern";

	public static final String PROPERTY_CHOICE_SELECTION_LIST = "choiceSelectionList";
	public static final String PROPERTY_PROD_CAT_NO = "prodCatNo";
	public static final String PROPERTY_PROD_CAT_NO_2 = "prodCatNo2";

    public static final String PROPERTY_ATTRIBUTE_DATA_TYPE = "attributeDataType";

	private final ArrayListModel choiceList;

	/**
	 * 
	 */
	private final SelectionInList choiceSelectionList;

	/**
	 * @param attribute
	 */
	public AttributeModel(Attribute attribute) {
		super(attribute);
		if ((attribute.getYesNo() == null || attribute.getYesNo() < 1)
				&& attribute.getAttributeChoices() != null) {
			choiceList = new ArrayListModel(attribute.getAttributeChoices());
		} else {
			choiceList = new ArrayListModel();
		}
		choiceSelectionList = new SelectionInList((ListModel) choiceList);

	}

	/**
	 * @return valg
	 */
	public ArrayListModel getChoiceList() {
		return new ArrayListModel(choiceList);
	}

	/**
	 * @param choiceList
	 */
	public void setChoiceList(ArrayListModel choiceList) {
		ArrayListModel oldList = getChoiceList();
		this.choiceList.clear();
		this.choiceList.addAll(choiceList);
		firePropertyChange(PROPERTY_CHOICE_LIST, oldList, choiceList);
	}

	/**
	 * @return selekteringsliste for valg
	 */
	public SelectionInList getChoiceSelectionList() {
		return choiceSelectionList;
	}

	/**
	 * @return id
	 */
	public Integer getAttributeId() {
		return object.getAttributeId();
	}

	/**
	 * @param attributeId
	 */
	public void setAttributeId(Integer attributeId) {
		Integer oldId = getAttributeId();
		object.setAttributeId(attributeId);
		firePropertyChange(PROPERTY_ATTRIBUTE_ID, oldId, attributeId);
	}

	/**
	 * @return navn
	 */
	public String getName() {
		return object.getName();
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		String oldName = getName();
		object.setName(name);
		firePropertyChange(PROPERTY_NAME, oldName, name);
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return object.getDescription();
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		String oldDesc = getDescription();
		object.setDescription(description);
		firePropertyChange(PROPERTY_DESCRIPTION, oldDesc, description);
	}

	/**
	 * @param yesNo
	 */
	public void setYesNo(Boolean yesNo) {
		Boolean oldYesNo = getYesNo();
		if (yesNo) {
			object.setYesNo(1);
		} else {
			object.setYesNo(0);
		}

		firePropertyChange(PROPERTY_YES_NO, oldYesNo, yesNo);
	}

	/**
	 * @return true dersom attributt har boolsk verdi
	 */
	public Boolean getYesNo() {
		if (object.getYesNo() == null || object.getYesNo() < 1) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	/**
	 * @param specialConcern
	 */
	public void setSpecialConcern(Boolean specialConcern) {
		Boolean oldConcern = getSpecialConcern();
		if (specialConcern) {
			object.setSpecialConcern(1);
		} else {
			object.setSpecialConcern(0);
		}

		firePropertyChange(PROPERTY_SPECIAL_CONCERN, oldConcern, specialConcern);
	}

	/**
	 * @return true dersom attributt brukes til spesielle hensyn
	 */
	public Boolean getSpecialConcern() {
		if (object.getSpecialConcern() == null
				|| object.getSpecialConcern() < 1) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}
	public String getProdCatNo(){
        return Util.convertIntegerToString(object.getProdCatNo());
    }
    public void setProdCatNo(String aNo){
        String oldNo=getProdCatNo();
        object.setProdCatNo(Util.convertStringToInteger(aNo));
        firePropertyChange(PROPERTY_PROD_CAT_NO, oldNo, aNo);
    }
    public String getProdCatNo2(){
        return Util.convertIntegerToString(object.getProdCatNo2());
    }
    public void setProdCatNo2(String aNo){
        String oldNo=getProdCatNo2();
        object.setProdCatNo2(Util.convertStringToInteger(aNo));
        firePropertyChange(PROPERTY_PROD_CAT_NO_2, oldNo, aNo);
    }
    public String getAttributeDataType(){
        return object.getAttributeDataType();
    }
    public void setAttributeDataType(String aDataType){
        String oldType=getAttributeDataType();
        object.setAttributeDataType(aDataType);
        firePropertyChange(PROPERTY_ATTRIBUTE_DATA_TYPE, oldType, aDataType);
    }

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_ATTRIBUTE_ID)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DESCRIPTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_YES_NO)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_CHOICE_LIST)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PROD_CAT_NO)
        .addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PROD_CAT_NO_2)
        .addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_ATTRIBUTE_DATA_TYPE)
        .addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public AttributeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		AttributeModel attributeModel = new AttributeModel(new Attribute());
		attributeModel.setAttributeId((Integer) presentationModel
				.getBufferedValue(PROPERTY_ATTRIBUTE_ID));
		attributeModel.setName((String) presentationModel
				.getBufferedValue(PROPERTY_NAME));
		attributeModel.setDescription((String) presentationModel
				.getBufferedValue(PROPERTY_DESCRIPTION));
		attributeModel.setYesNo((Boolean) presentationModel
				.getBufferedValue(PROPERTY_YES_NO));
		attributeModel.setChoiceList((ArrayListModel) presentationModel
				.getBufferedValue(PROPERTY_CHOICE_LIST));
		attributeModel.setProdCatNo((String) presentationModel
                .getBufferedValue(PROPERTY_PROD_CAT_NO));
		attributeModel.setProdCatNo2((String) presentationModel
                .getBufferedValue(PROPERTY_PROD_CAT_NO_2));
		attributeModel.setAttributeDataType((String) presentationModel
                .getBufferedValue(PROPERTY_ATTRIBUTE_DATA_TYPE));
		return attributeModel;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#modelToView()
	 */
	@Override
	public void modelToView() {
		if (object.getAttributeChoices() != null) {
			choiceList.clear();
			choiceList.addAll(object.getAttributeChoices());
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#viewToModel()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void viewToModel() {
		if (choiceList != null) {
			Set<AttributeChoice> choices = object.getAttributeChoices();

			if (choices == null) {
				choices = new HashSet<AttributeChoice>();
			}
			choices.clear();
			choices.addAll(choiceList);
			object.setAttributeChoices(choices);
		}
	}

	/**
	 * Kloner attributtvalg
	 * 
	 * @param orgCoices
	 * @return valg
	 */
	public static ArrayListModel cloneChoices(List<AttributeChoice> orgCoices) {
		ArrayListModel newList = new ArrayListModel();
		if (orgCoices != null) {
			for (AttributeChoice choice : orgCoices) {
				newList.add(new AttributeChoice(choice.getAttributeChoiceId(),
						choice.getChoiceValue(), choice.getAttribute(), choice
								.getComment(),choice.getProdCatNo(),choice.getProdCatNo2()));
			}
		}
		return newList;
	}

}
