package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.ExternalOrderLineAttribute;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for ekstern ordrelinjeattributt
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderLineAttributeModel
		extends
		AbstractModel<ExternalOrderLineAttribute, ExternalOrderLineAttributeModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_NAME = "externalOrderLineAttributeName";

	/**
	 * 
	 */
	public static final String PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_VALUE = "externalOrderLineAttributeValue";

	/**
	 * @param object
	 */
	public ExternalOrderLineAttributeModel(ExternalOrderLineAttribute object) {
		super(object);
	}

	/**
	 * @return navn
	 */
	public String getExternalOrderLineAttributeName() {
		return object.getExternalOrderLineAttributeName();
	}

	/**
	 * @param externalOrderLineAttributeName
	 */
	public void setExternalOrderLineAttributeName(
			String externalOrderLineAttributeName) {
		String oldName = getExternalOrderLineAttributeName();
		object
				.setExternalOrderLineAttributeName(externalOrderLineAttributeName);
		firePropertyChange(PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_NAME,
				oldName, externalOrderLineAttributeName);
	}

	/**
	 * @return verdi
	 */
	public String getExternalOrderLineAttributeValue() {
		return object.getExternalOrderLineAttributeValue();
	}

	/**
	 * @param externalOrderLineAttributeValue
	 */
	public void setExternalOrderLineAttributeValue(
			String externalOrderLineAttributeValue) {
		String oldValue = getExternalOrderLineAttributeValue();
		object
				.setExternalOrderLineAttributeValue(externalOrderLineAttributeValue);
		firePropertyChange(PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_VALUE,
				oldValue, externalOrderLineAttributeValue);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(
				PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(
				PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_VALUE)
				.addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public ExternalOrderLineAttributeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		ExternalOrderLineAttributeModel model = new ExternalOrderLineAttributeModel(
				new ExternalOrderLineAttribute());
		model.setExternalOrderLineAttributeName((String) presentationModel
				.getBufferedValue(PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_NAME));
		model
				.setExternalOrderLineAttributeValue((String) presentationModel
						.getBufferedValue(PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_VALUE));
		return model;
	}

}
