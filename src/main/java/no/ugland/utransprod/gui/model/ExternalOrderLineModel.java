package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.Set;

import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.model.ExternalOrderLineAttribute;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for ekstern ordrelinje
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderLineModel extends
		AbstractModel<ExternalOrderLine, ExternalOrderLineModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_ARTICLE_NAME = "articleName";

	/**
	 * 
	 */
	public static final String PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTES = "externalOrderLineAttributes";

	/**
	 * 
	 */
	public static final String PROPERTY_NUMBER_OF_ITEMS = "numberOfItems";

	/**
	 * @param object
	 */
	public ExternalOrderLineModel(ExternalOrderLine object) {
		super(object);
	}

	/**
	 * @return navn
	 */
	public String getArticleName() {
		return object.getArticleName();
	}

	/**
	 * @param articleName
	 */
	public void setArticleName(String articleName) {
		String oldName = getArticleName();
		object.setArticleName(articleName);
		firePropertyChange(PROPERTY_ARTICLE_NAME, oldName, articleName);
	}

	/**
	 * @return antall
	 */
	public Integer getNumberOfItems() {
		return object.getNumberOfItems();
	}

	/**
	 * @param numberOfItems
	 */
	public void setNumberOfItems(Integer numberOfItems) {
		Integer oldNumber = getNumberOfItems();
		object.setNumberOfItems(numberOfItems);
		firePropertyChange(PROPERTY_NUMBER_OF_ITEMS, oldNumber, numberOfItems);
	}

	/**
	 * @return attributter
	 */
	public Set<ExternalOrderLineAttribute> getExternalOrderLineAttributes() {
		return object.getExternalOrderLineAttributes();
	}

	/**
	 * @param externalOrderLineAttributes
	 */
	public void setExternalOrderLineAttributes(
			Set<ExternalOrderLineAttribute> externalOrderLineAttributes) {
		Set<ExternalOrderLineAttribute> oldAtt = getExternalOrderLineAttributes();
		object.setExternalOrderLineAttributes(externalOrderLineAttributes);
		firePropertyChange(PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTES, oldAtt,
				externalOrderLineAttributes);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_ARTICLE_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(
				PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTES)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_NUMBER_OF_ITEMS)
				.addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExternalOrderLineModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		ExternalOrderLineModel model = new ExternalOrderLineModel(
				new ExternalOrderLine());
		model.setArticleName((String) presentationModel
				.getBufferedValue(PROPERTY_ARTICLE_NAME));
		model.setNumberOfItems((Integer) presentationModel
				.getBufferedValue(PROPERTY_NUMBER_OF_ITEMS));
		model
				.setExternalOrderLineAttributes((Set<ExternalOrderLineAttribute>) presentationModel
						.getBufferedValue(PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTES));
		return model;
	}

}
