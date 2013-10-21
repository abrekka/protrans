package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import no.ugland.utransprod.model.TransportSumV;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklasse for domeneklasse for view TRANSPORT_SUM_V
 * 
 * @author atle.brekka
 * 
 */
public class TransportSumVModel extends
		AbstractModel<TransportSumV, TransportSumVModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_ORDER_COUNT_STRING = "orderCountString";

	/**
	 * 
	 */
	public static final String PROPERTY_GARAGE_COST_STRING = "garageCostString";

	/**
	 * @param object
	 */
	public TransportSumVModel(TransportSumV object) {
		super(object);
	}

	/**
	 * @return antall ordre som streng
	 */
	public String getOrderCountString() {
		if (object.getOrderCount() != null) {
			return String.valueOf(object.getOrderCount());
		}
		return null;
	}

	/**
	 * @param orderCount
	 */
	public void setOrderCountString(String orderCount) {
		String oldCount = getOrderCountString();
		if (orderCount != null) {
			object.setOrderCount(Integer.valueOf(orderCount));
		} else {
			object.setOrderCount(null);
		}
		firePropertyChange(PROPERTY_ORDER_COUNT_STRING, oldCount, orderCount);
	}

	/**
	 * @return garasjekostnad som streng
	 */
	public String getGarageCostString() {
		if (object.getGarageCost() != null) {
			return String.valueOf(object.getGarageCost());
		}
		return null;
	}

	/**
	 * @param garageCost
	 */
	public void setGarageCostString(String garageCost) {
		String oldCost = getGarageCostString();
		if (garageCost != null) {
			object
					.setGarageCost(BigDecimal.valueOf(Double
							.valueOf(garageCost)));
		} else {
			object.setGarageCost(null);
		}
		firePropertyChange(PROPERTY_GARAGE_COST_STRING, oldCost, garageCost);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_ORDER_COUNT_STRING)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_GARAGE_COST_STRING)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public TransportSumVModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		TransportSumVModel model = new TransportSumVModel(new TransportSumV());
		model.setOrderCountString((String) presentationModel
				.getBufferedValue(PROPERTY_ORDER_COUNT_STRING));
		model.setGarageCostString((String) presentationModel
				.getBufferedValue(PROPERTY_GARAGE_COST_STRING));
		return model;
	}

}
