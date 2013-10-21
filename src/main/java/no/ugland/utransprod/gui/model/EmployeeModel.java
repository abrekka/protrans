package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.EmployeeType;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for ansatt
 * 
 * @author atle.brekka
 * 
 */
public class EmployeeModel extends AbstractModel<Employee, EmployeeModel> {
	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_EMPLOYEE_ID = "employeeId";

	public static final String PROPERTY_FIRST_NAME = "firstName";

	public static final String PROPERTY_LAST_NAME = "lastName";

	public static final String PROPERTY_PHONE = "phone";

	public static final String PROPERTY_EMPLOYEE_TYPE = "employeeType";

	public static final String PROPERTY_INACTIVE_BOOL = "inactiveBool";

	/**
	 * @param object
	 */
	public EmployeeModel(Employee object) {
		super(object);
	}

	/**
	 * @return id
	 */
	public Integer getEmployeeId() {
		return object.getEmployeeId();
	}

	/**
	 * @param employeeId
	 */
	public void setEmployeeId(Integer employeeId) {
		Integer oldId = getEmployeeId();
		object.setEmployeeId(employeeId);
		firePropertyChange(PROPERTY_EMPLOYEE_ID, oldId, employeeId);
	}

	/**
	 * @return fornavn
	 */
	public String getFirstName() {
		return object.getFirstName();
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		String oldName = getFirstName();
		object.setFirstName(firstName);
		firePropertyChange(PROPERTY_FIRST_NAME, oldName, firstName);
	}

	/**
	 * @return etternavn
	 */
	public String getLastName() {
		return object.getLastName();
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		String oldName = getLastName();
		object.setLastName(lastName);
		firePropertyChange(PROPERTY_LAST_NAME, oldName, lastName);
	}

	/**
	 * @return telefonnummer
	 */
	public String getPhone() {
		return object.getPhone();
	}

	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		String oldPhone = getPhone();
		object.setPhone(phone);
		firePropertyChange(PROPERTY_PHONE, oldPhone, phone);
	}

	/**
	 * @return ansattype
	 */
	public EmployeeType getEmployeeType() {
		return object.getEmployeeType();
	}

	/**
	 * @param employeeType
	 */
	public void setEmployeeType(EmployeeType employeeType) {
		EmployeeType oldType = getEmployeeType();
		object.setEmployeeType(employeeType);
		firePropertyChange(PROPERTY_EMPLOYEE_TYPE, oldType, employeeType);
	}
	public Boolean getInactiveBool() {
        return Util.convertNumberToBoolean(object.getInactive());
    }
	public void setInactiveBool(Boolean isInactive) {
        Boolean oldInactive = getInactiveBool();
        object.setInactive(Util.convertBooleanToNumber(isInactive));
        firePropertyChange(PROPERTY_INACTIVE_BOOL, oldInactive, isInactive);
    }

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_FIRST_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_LAST_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PHONE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_EMPLOYEE_TYPE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_INACTIVE_BOOL)
        .addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public EmployeeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		EmployeeModel employeeModel = new EmployeeModel(new Employee());
		employeeModel.setEmployeeId((Integer) presentationModel
				.getBufferedValue(PROPERTY_EMPLOYEE_ID));
		employeeModel.setFirstName((String) presentationModel
				.getBufferedValue(PROPERTY_FIRST_NAME));
		employeeModel.setLastName((String) presentationModel
				.getBufferedValue(PROPERTY_LAST_NAME));
		employeeModel.setPhone((String) presentationModel
				.getBufferedValue(PROPERTY_PHONE));
		employeeModel.setEmployeeType((EmployeeType) presentationModel
				.getBufferedValue(PROPERTY_EMPLOYEE_TYPE));
		employeeModel.setInactiveBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_INACTIVE_BOOL));

		return employeeModel;
	}

}
