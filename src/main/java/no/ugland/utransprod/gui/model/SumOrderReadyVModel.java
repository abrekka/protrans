package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import no.ugland.utransprod.model.SumOrderReadyV;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklasse for demeneklasse for view SUM_ORDER_READY_V
 * 
 * @author atle.brekka
 * 
 */
public class SumOrderReadyVModel extends
		AbstractModel<SumOrderReadyV, SumOrderReadyVModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_PACKAGE_SUM_STRING = "packageSumString";

	/**
	 * @param object
	 */
	public SumOrderReadyVModel(SumOrderReadyV object) {
		super(object);
	}

	/**
	 * @return pakkebeløp som streng
	 */
	public String getPackageSumString() {
		if (object.getPackageSum() != null) {
			return String.valueOf(object.getPackageSum());
		}
		return "";
	}

	/**
	 * @param packageSumString
	 */
	public void setPackageSumString(String packageSumString) {
		String oldSum = getPackageSumString();
		if (packageSumString != null) {
			object.setPackageSum(BigDecimal.valueOf(Long
					.valueOf(packageSumString)));
		}
		firePropertyChange(PROPERTY_PACKAGE_SUM_STRING, oldSum,
				packageSumString);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_PACKAGE_SUM_STRING)
				.addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public SumOrderReadyVModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		SumOrderReadyVModel model = new SumOrderReadyVModel(
				new SumOrderReadyV());
		model.setPackageSumString((String) presentationModel
				.getBufferedValue(PROPERTY_PACKAGE_SUM_STRING));
		return model;
	}

}
