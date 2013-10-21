package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;

import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.model.AccidentParticipant;
import no.ugland.utransprod.model.EmployeeType;

public class AccidentParticipantModel extends AbstractModel<AccidentParticipant, AccidentParticipantModel> {
    public static final String PROPERTY_FIRST_NAME="firstName";
    public static final String PROPERTY_LAST_NAME="lastName";
    public static final String PROPERTY_EMPLOYEE_TYPE="employeeType";

    public AccidentParticipantModel(AccidentParticipant object) {
        super(object);
    }
    public String getFirstName(){
        return object.getFirstName();
    }
    public void setFirstName(String firstName){
        String oldName=getFirstName();
        object.setFirstName(firstName);
        firePropertyChange(PROPERTY_FIRST_NAME, oldName, firstName);
    }
    public String getLastName(){
        return object.getLastName();
    }
    public void setLastName(String lastName){
        String oldName=getLastName();
        object.setLastName(lastName);
        firePropertyChange(PROPERTY_LAST_NAME, oldName, lastName);
    }
    public EmployeeType getEmployeeType(){
        return object.getEmployeeType();
    }
    public void setEmployeeType(EmployeeType employeeType){
        EmployeeType oldEmployeeType=getEmployeeType();
        object.setEmployeeType(employeeType);
        firePropertyChange(PROPERTY_EMPLOYEE_TYPE, oldEmployeeType, employeeType);
    }

    @Override
    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
        presentationModel.getBufferedModel(PROPERTY_FIRST_NAME).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_LAST_NAME).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_EMPLOYEE_TYPE).addValueChangeListener(listener);
        
    }

    @Override
    public AccidentParticipantModel getBufferedObjectModel(PresentationModel presentationModel) {
        AccidentParticipantModel accidentParticipantModel = new AccidentParticipantModel(
                new AccidentParticipant());
        accidentParticipantModel.setFirstName((String) presentationModel
                .getBufferedValue(PROPERTY_FIRST_NAME));
        accidentParticipantModel.setLastName((String) presentationModel
                .getBufferedValue(PROPERTY_LAST_NAME));
        accidentParticipantModel.setEmployeeType((EmployeeType) presentationModel
                .getBufferedValue(PROPERTY_EMPLOYEE_TYPE));
        return accidentParticipantModel;
    }

}
