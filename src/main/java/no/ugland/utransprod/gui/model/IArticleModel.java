package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.IArticle;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklasse for interface IArticle
 * 
 * @author atle.brekka
 * 
 */
public class IArticleModel extends AbstractModel<IArticle, IArticleModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_NUMBER_OF_ITEMS_LONG = "numberOfItemsLong";

	/**
	 * 
	 */
	public static final String PROPERTY_DIALOG_ORDER = "dialogOrder";

	/**
	 * @param object
	 */
	public IArticleModel(IArticle object) {
		super(object);
	}

	/**
	 * @return antall som Long
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
	 * @return rekkefølge
	 */
	public Integer getDialogOrder() {
		return object.getDialogOrder();

	}

	/**
	 * @param dialogOrder
	 */
	public void setDialogOrder(Integer dialogOrder) {
		Integer oldNumber = getDialogOrder();
		object.setDialogOrder(dialogOrder);
		firePropertyChange(PROPERTY_DIALOG_ORDER, oldNumber, dialogOrder);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_NUMBER_OF_ITEMS_LONG)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DIALOG_ORDER)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public IArticleModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		IArticleModel model = new IArticleModel(object.getNewInstance());
		model.setNumberOfItemsLong((Long) presentationModel
				.getBufferedValue(PROPERTY_NUMBER_OF_ITEMS_LONG));
		model.setDialogOrder((Integer) presentationModel
				.getBufferedValue(PROPERTY_DIALOG_ORDER));
		return model;
	}

}
