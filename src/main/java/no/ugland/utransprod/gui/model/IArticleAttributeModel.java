package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.IArticleAttribute;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklasse for interface IArticleAttributeModel
 * 
 * @author atle.brekka
 * 
 */
public class IArticleAttributeModel extends
		AbstractModel<IArticleAttribute, IArticleAttributeModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_VALUE = "attributeValue";

	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_VALUE_BOOL = "attributeValueBool";

	/**
	 * 
	 */
	public static final String PROPERTY_DIALOG_ORDER_ATTRIBUTE = "dialogOrderAttribute";

	/**
	 * 
	 */
	public static final String PROPERTY_NUMBER_OF_ITEMS_LONG = "numberOfItemsLong";

	/**
	 * @param object
	 */
	public IArticleAttributeModel(IArticleAttribute object) {
		super(object);
	}

	/**
	 * @return attributtverdi
	 */
	public String getAttributeValue() {
		return object.getAttributeValue();
	}

	/**
	 * @param attributeValue
	 */
	public void setAttributeValue(String attributeValue) {
		String oldValue = getAttributeValue();
		object.setAttributeValue(attributeValue);
		firePropertyChange(PROPERTY_ATTRIBUTE_VALUE, oldValue, attributeValue);
	}

	/**
	 * @return attributtverdi som boolsk
	 */
	public Boolean getAttributeValueBool() {
		return object.getAttributeValueBool();
	}

	/**
	 * @param value
	 */
	public void setAttributeValueBool(Boolean value) {
		Boolean oldValue = getAttributeValueBool();
		object.setAttributeValueBool(value);
		firePropertyChange(PROPERTY_ATTRIBUTE_VALUE_BOOL, oldValue, value);
	}

	/**
	 * @return rekkefølge på attributt
	 */
	public Integer getDialogOrderAttribute() {
		return object.getDialogOrderAttribute();
	}

	/**
	 * @return antall artikler som Long
	 */
	public Long getNumberOfItemsLong() {
		return object.getNumberOfItemsLong();

	}

	/**
	 * @param numberOfItems
	 */
	public void setNumberOfItemsLong(Long numberOfItems) {
		Long oldNumber = getNumberOfItemsLong();
		object.setNumberOfItemsLong(numberOfItems);
		firePropertyChange(PROPERTY_NUMBER_OF_ITEMS_LONG, oldNumber,
				numberOfItems);

	}

	/**
	 * @param dialogOrder
	 */
	public void setDialogOrderAttribute(Integer dialogOrder) {
		Integer oldOrder = getDialogOrderAttribute();
		object.setDialogOrderAttribute(dialogOrder);
		firePropertyChange(PROPERTY_DIALOG_ORDER_ATTRIBUTE, oldOrder,
				dialogOrder);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_ATTRIBUTE_VALUE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_ATTRIBUTE_VALUE_BOOL)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DIALOG_ORDER_ATTRIBUTE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_NUMBER_OF_ITEMS_LONG)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public IArticleAttributeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		IArticleAttributeModel model = new IArticleAttributeModel(object
				.getNewInstance());
		model.setAttributeValue((String) presentationModel
				.getBufferedValue(PROPERTY_ATTRIBUTE_VALUE));
		model.setAttributeValueBool((Boolean) presentationModel
				.getBufferedValue(PROPERTY_ATTRIBUTE_VALUE_BOOL));
		model.setDialogOrderAttribute((Integer) presentationModel
				.getBufferedValue(PROPERTY_DIALOG_ORDER_ATTRIBUTE));
		model.setNumberOfItemsLong((Long) presentationModel
				.getBufferedValue(PROPERTY_NUMBER_OF_ITEMS_LONG));
		return model;
	}

}
