package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.EmployeeType;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for ansattype
 * 
 * @author atle.brekka
 * 
 */
public class EmployeeTypeModel extends
		AbstractModel<EmployeeType, EmployeeTypeModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_EMPLOYEE_TYPE_ID = "employeeTypeId";

	/**
	 * 
	 */
	public static final String PROPERTY_EMPLOYEE_TYPE_NAME = "employeeTypeName";

	/**
	 * 
	 */
	public static final String PROPERTY_EMPLOYEE_TYPE_DESCRIPTION = "employeeTypeDescription";

	/**
	 * @param object
	 */
	public EmployeeTypeModel(EmployeeType object) {
		super(object);
	}

	/**
	 * @return id
	 */
	public Integer getEmployeeTypeId() {
		return object.getEmployeeTypeId();
	}

	/**
	 * @param employeeTypeId
	 */
	public void setEmployeeTypeId(Integer employeeTypeId) {
		Integer oldId = getEmployeeTypeId();
		object.setEmployeeTypeId(employeeTypeId);
		firePropertyChange(PROPERTY_EMPLOYEE_TYPE_ID, oldId, employeeTypeId);
	}

	/**
	 * @return navn
	 */
	public String getEmployeeTypeName() {
		return object.getEmployeeTypeName();
	}

	/**
	 * @param employeeTypeName
	 */
	public void setEmployeeTypeName(String employeeTypeName) {
		String oldName = getEmployeeTypeName();
		object.setEmployeeTypeName(employeeTypeName);
		firePropertyChange(PROPERTY_EMPLOYEE_TYPE_NAME, oldName,
				employeeTypeName);
	}

	/**
	 * @return beskrivelse
	 */
	public String getEmployeeTypeDescription() {
		return object.getEmployeeTypeDescription();
	}

	/**
	 * @param employeeTypeDescription
	 */
	public void setEmployeeTypeDescription(String employeeTypeDescription) {
		String oldDesc = getEmployeeTypeDescription();
		object.setEmployeeTypeDescription(employeeTypeDescription);
		firePropertyChange(PROPERTY_EMPLOYEE_TYPE_DESCRIPTION, oldDesc,
				employeeTypeDescription);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_EMPLOYEE_TYPE_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_EMPLOYEE_TYPE_DESCRIPTION)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public EmployeeTypeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		EmployeeTypeModel employeeTypeModel = new EmployeeTypeModel(
				new EmployeeType());
		employeeTypeModel.setEmployeeTypeId((Integer) presentationModel
				.getBufferedValue(PROPERTY_EMPLOYEE_TYPE_ID));
		employeeTypeModel.setEmployeeTypeName((String) presentationModel
				.getBufferedValue(PROPERTY_EMPLOYEE_TYPE_NAME));
		employeeTypeModel.setEmployeeTypeDescription((String) presentationModel
				.getBufferedValue(PROPERTY_EMPLOYEE_TYPE_DESCRIPTION));

		return employeeTypeModel;
	}

}
