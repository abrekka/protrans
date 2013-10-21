package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.MainPackageV;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for garasjepakking
 * 
 * @author atle.brekka
 * 
 */
public class MainPackageVModel extends
		AbstractModel<MainPackageV, MainPackageVModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_ORDER_ID = "orderId";

	/**
	 * @param object
	 */
	public MainPackageVModel(MainPackageV object) {
		super(object);
	}

	/**
	 * @return ordreid
	 */
	public Integer getOrderId() {
		return object.getOrderId();
	}

	/**
	 * @param orderId
	 */
	public void setOrderId(Integer orderId) {
		Integer oldId = getOrderId();
		object.setOrderId(orderId);
		firePropertyChange(PROPERTY_ORDER_ID, oldId, orderId);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_ORDER_ID)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public MainPackageVModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		MainPackageVModel mainPackageVModel = new MainPackageVModel(
				new MainPackageV());
		mainPackageVModel.setOrderId((Integer) presentationModel
				.getBufferedValue(PROPERTY_ORDER_ID));
		return mainPackageVModel;
	}

}
