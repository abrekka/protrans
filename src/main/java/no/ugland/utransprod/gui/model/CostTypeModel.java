package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.CostType;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklasse for kostnadstype
 * 
 * @author atle.brekka
 * 
 */
public class CostTypeModel extends AbstractModel<CostType, CostTypeModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_COST_TYPE_ID = "costTypeId";

	/**
	 * 
	 */
	public static final String PROPERTY_COST_TYPE_NAME = "costTypeName";

	/**
	 * 
	 */
	public static final String PROPERTY_COST_TYPE_DESCRIPTION = "description";

	/**
	 * @param costType
	 */
	public CostTypeModel(CostType costType) {
		super(costType);
	}

	/**
	 * @return id
	 */
	public Integer getCostTypeId() {
		return object.getCostTypeId();
	}

	/**
	 * @param costTypeId
	 */
	public void setCostTypeId(Integer costTypeId) {
		Integer oldId = getCostTypeId();
		object.setCostTypeId(costTypeId);
		firePropertyChange(PROPERTY_COST_TYPE_ID, oldId, costTypeId);
	}

	/**
	 * @return navn
	 */
	public String getCostTypeName() {
		return object.getCostTypeName();
	}

	/**
	 * @param costTypeName
	 */
	public void setCostTypeName(String costTypeName) {
		String oldName = getCostTypeName();
		object.setCostTypeName(costTypeName);
		firePropertyChange(PROPERTY_COST_TYPE_NAME, oldName, costTypeName);
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
		firePropertyChange(PROPERTY_COST_TYPE_DESCRIPTION, oldDesc, description);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_COST_TYPE_ID)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COST_TYPE_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COST_TYPE_DESCRIPTION)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public CostTypeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		CostTypeModel costTypeModel = new CostTypeModel(new CostType());
		costTypeModel.setCostTypeId((Integer) presentationModel
				.getBufferedValue(PROPERTY_COST_TYPE_ID));
		costTypeModel.setCostTypeName((String) presentationModel
				.getBufferedValue(PROPERTY_COST_TYPE_NAME));
		costTypeModel.setDescription((String) presentationModel
				.getBufferedValue(PROPERTY_COST_TYPE_DESCRIPTION));
		return costTypeModel;
	}
}
