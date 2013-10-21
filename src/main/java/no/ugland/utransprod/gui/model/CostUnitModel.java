package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.CostUnit;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklase for kostnadsenhet
 * 
 * @author atle.brekka
 * 
 */
public class CostUnitModel extends AbstractModel<CostUnit, CostUnitModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_COST_UNIT_ID = "costUnitId";

	/**
	 * 
	 */
	public static final String PROPERTY_COST_UNIT_NAME = "costUnitName";

	/**
	 * 
	 */
	public static final String PROPERTY_COST_UNIT_DESCRIPTION = "description";

	/**
	 * @param costUnit
	 */
	public CostUnitModel(CostUnit costUnit) {
		super(costUnit);
	}

	/**
	 * @return id
	 */
	public Integer getCostUnitId() {
		return object.getCostUnitId();
	}

	/**
	 * @param costUnitId
	 */
	public void setCostUnitId(Integer costUnitId) {
		Integer oldId = getCostUnitId();
		object.setCostUnitId(costUnitId);
		firePropertyChange(PROPERTY_COST_UNIT_ID, oldId, costUnitId);
	}

	/**
	 * @return navn
	 */
	public String getCostUnitName() {
		return object.getCostUnitName();
	}

	/**
	 * @param costUnitName
	 */
	public void setCostUnitName(String costUnitName) {
		String oldName = getCostUnitName();
		object.setCostUnitName(costUnitName);
		firePropertyChange(PROPERTY_COST_UNIT_NAME, oldName, costUnitName);
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
		firePropertyChange(PROPERTY_COST_UNIT_DESCRIPTION, oldDesc, description);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_COST_UNIT_ID)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COST_UNIT_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COST_UNIT_DESCRIPTION)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public CostUnitModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		CostUnitModel costUnitModel = new CostUnitModel(new CostUnit());
		costUnitModel.setCostUnitId((Integer) presentationModel
				.getBufferedValue(PROPERTY_COST_UNIT_ID));
		costUnitModel.setCostUnitName((String) presentationModel
				.getBufferedValue(PROPERTY_COST_UNIT_NAME));
		costUnitModel.setDescription((String) presentationModel
				.getBufferedValue(PROPERTY_COST_UNIT_DESCRIPTION));
		return costUnitModel;
	}
}
