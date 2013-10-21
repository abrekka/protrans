package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import no.ugland.utransprod.model.NokkelProduksjonV;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for nøkkelproduksjon
 * 
 * @author atle.brekka
 * 
 */
public class NokkelProduksjonVModel extends
		AbstractModel<NokkelProduksjonV, NokkelProduksjonVModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_BUDGET_VALUE_STRING = "budgetValueString";

	/**
	 * 
	 */
	public static final String PROPERTY_BUDGET_DEVIATION_STRING = "budgetDeviationString";

	/**
	 * 
	 */
	public static final String PROPERTY_BUDGET_DEVIATION_PROC_STRING = "budgetDeviationProcString";

	/**
	 * 
	 */
	public static final String PROPERTY_PACKAGE_SUM_WEEK_STRING = "packageSumWeekString";

	/**
	 * @param object
	 */
	public NokkelProduksjonVModel(NokkelProduksjonV object) {
		super(object);
	}

	/**
	 * @return budsjettverdi som streng
	 */
	public String getBudgetValueString() {
		return String.format("%1$.0f", object.getBudgetValue());
	}

	/**
	 * @param budgetValue
	 */
	public void setBudgetValueString(String budgetValue) {
		String oldValue = getBudgetValueString();
		if (budgetValue != null) {
			object.setBudgetValue(BigDecimal.valueOf(Double
					.valueOf(budgetValue)));
		} else {
			object.setBudgetValue(null);
		}
		firePropertyChange(PROPERTY_BUDGET_VALUE_STRING, oldValue, budgetValue);
	}

	/**
	 * @return pakkesum som streng
	 */
	public String getPackageSumWeekString() {
		return String.format("%1$.0f", object.getPackageSumWeek());
	}

	/**
	 * @param packageSumWeekString
	 */
	public void setPackageSumWeekString(String packageSumWeekString) {
		String oldValue = getPackageSumWeekString();
		if (packageSumWeekString != null) {
			object.setPackageSumWeek(BigDecimal.valueOf(Double
					.valueOf(packageSumWeekString)));
		} else {
			object.setPackageSumWeek(null);
		}
		firePropertyChange(PROPERTY_PACKAGE_SUM_WEEK_STRING, oldValue,
				packageSumWeekString);
	}

	/**
	 * @return budsjettavvik som tekst
	 */
	public String getBudgetDeviationString() {
		return String.format("%1$.0f", object.getBudgetDeviation());
	}

	/**
	 * @param budgetDeviation
	 */
	public void setBudgetDeviationString(String budgetDeviation) {
		String oldValue = getBudgetDeviationString();
		if (budgetDeviation != null) {
			object.setBudgetDeviation(BigDecimal.valueOf(Double
					.valueOf(budgetDeviation)));
		} else {
			object.setBudgetDeviation(null);
		}
		firePropertyChange(PROPERTY_BUDGET_DEVIATION_STRING, oldValue,
				budgetDeviation);
	}

	/**
	 * @return budsjettavviksprosent som tekst
	 */
	public String getBudgetDeviationProcString() {
		return String.format("%1$.1f", object.getBudgetDeviationProc());
	}

	/**
	 * @param budgetDeviationProc
	 */
	public void setBudgetDeviationProcString(String budgetDeviationProc) {
		String oldValue = getBudgetDeviationProcString();
		if (budgetDeviationProc != null) {
			object.setBudgetDeviationProc(BigDecimal.valueOf(Double
					.valueOf(budgetDeviationProc)));
		} else {
			object.setBudgetDeviationProc(null);
		}
		firePropertyChange(PROPERTY_BUDGET_DEVIATION_PROC_STRING, oldValue,
				budgetDeviationProc);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(
				PROPERTY_BUDGET_DEVIATION_PROC_STRING).addValueChangeListener(
				listener);
		presentationModel.getBufferedModel(PROPERTY_BUDGET_DEVIATION_STRING)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_BUDGET_VALUE_STRING)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PACKAGE_SUM_WEEK_STRING)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public NokkelProduksjonVModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		NokkelProduksjonVModel model = new NokkelProduksjonVModel(
				new NokkelProduksjonV());
		model.setBudgetDeviationProcString((String) presentationModel
				.getBufferedValue(PROPERTY_BUDGET_DEVIATION_PROC_STRING));
		model.setBudgetDeviationString((String) presentationModel
				.getBufferedValue(PROPERTY_BUDGET_DEVIATION_STRING));
		model.setBudgetValueString((String) presentationModel
				.getBufferedValue(PROPERTY_BUDGET_VALUE_STRING));
		model.setPackageSumWeekString((String) presentationModel
				.getBufferedValue(PROPERTY_PACKAGE_SUM_WEEK_STRING));
		return model;
	}

}
