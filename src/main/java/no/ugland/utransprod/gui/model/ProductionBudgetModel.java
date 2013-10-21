package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for budsjett
 * 
 * @author atle.brekka
 * 
 */
public class ProductionBudgetModel extends
		AbstractModel<Budget, ProductionBudgetModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_BUDGET_ID = "budgetId";

	/**
	 * 
	 */
	public static final String PROPERTY_BUDGET_YEAR = "budgetYear";

	/**
	 * 
	 */
	public static final String PROPERTY_BUDGET_WEEK = "budgetWeek";

	/**
	 * 
	 */
	public static final String PROPERTY_BUDGET_VALUE = "budgetValue";
	/**
	 * 
	 */
	public static final String PROPERTY_PRODUCT_AREA = "productArea";

	/**
	 * @param object
	 */
	public ProductionBudgetModel(Budget object) {
		super(object);
	}

	/**
	 * @return budsjettår
	 */
	public Integer getBudgetYear() {
		if (object.getBudgetYear() == null) {
			object.setBudgetYear(Util.getCurrentYear());

		}

		return object.getBudgetYear();
	}

	/**
	 * @param budgetYear
	 */
	public void setBudgetYear(Integer budgetYear) {
		Integer oldYear = getBudgetYear();
		object.setBudgetYear(budgetYear);
		firePropertyChange(PROPERTY_BUDGET_YEAR, oldYear, budgetYear);
	}

	/**
	 * @return id
	 */
	public Integer getBudgetId() {
		return object.getBudgetId();
	}

	/**
	 * @param budgetId
	 */
	public void setBudgetId(Integer budgetId) {
		Integer oldId = getBudgetId();
		object.setBudgetId(budgetId);
		firePropertyChange(PROPERTY_BUDGET_ID, oldId, budgetId);
	}

	/**
	 * @return budsjettuke
	 */
	public Integer getBudgetWeek() {
		return object.getBudgetWeek();
	}

	/**
	 * @param budgetWeek
	 */
	public void setBudgetWeek(Integer budgetWeek) {
		Integer oldWeek = getBudgetWeek();
		object.setBudgetWeek(budgetWeek);
		firePropertyChange(PROPERTY_BUDGET_WEEK, oldWeek, budgetWeek);
	}
	
	/**
	 * @return budsjettuke
	 */
	public ProductArea getProductArea() {
		return object.getProductArea();
	}

	/**
	 * @param productArea
	 */
	public void setProductArea(ProductArea productArea) {
		ProductArea oldArea = getProductArea();
		object.setProductArea(productArea);
		firePropertyChange(PROPERTY_PRODUCT_AREA, oldArea, productArea);
	}

	/**
	 * @return budsjettverdi
	 */
	public String getBudgetValue() {
		if (object.getBudgetValue() != null) {
			return String.valueOf(object.getBudgetValue());
		}
		return null;
	}

	/**
	 * @param budgetValue
	 */
	public void setBudgetValue(String budgetValue) {
		String oldValue = getBudgetValue();
		if (budgetValue != null && Util.isNumber(budgetValue)) {
			object.setBudgetValue(BigDecimal.valueOf(Double
					.valueOf(budgetValue)));
		} else {
			object.setBudgetValue(null);
		}

		firePropertyChange(PROPERTY_BUDGET_VALUE, oldValue, budgetValue);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_BUDGET_YEAR)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_BUDGET_WEEK)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_BUDGET_VALUE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PRODUCT_AREA)
		.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public ProductionBudgetModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		ProductionBudgetModel productionBudgetModel = new ProductionBudgetModel(
				new Budget());
		productionBudgetModel.setBudgetId((Integer) presentationModel
				.getBufferedValue(PROPERTY_BUDGET_ID));
		productionBudgetModel.setBudgetYear((Integer) presentationModel
				.getBufferedValue(PROPERTY_BUDGET_YEAR));
		productionBudgetModel.setBudgetWeek((Integer) presentationModel
				.getBufferedValue(PROPERTY_BUDGET_WEEK));
		productionBudgetModel.setBudgetValue((String) presentationModel
				.getBufferedValue(PROPERTY_BUDGET_VALUE));
		productionBudgetModel.setProductArea((ProductArea) presentationModel
				.getBufferedValue(PROPERTY_PRODUCT_AREA));
		return productionBudgetModel;
	}

}
