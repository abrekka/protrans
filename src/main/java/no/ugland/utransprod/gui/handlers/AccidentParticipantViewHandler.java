package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.model.AccidentParticipantModel;
import no.ugland.utransprod.model.AccidentParticipant;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;

public class AccidentParticipantViewHandler extends
AbstractViewHandlerShort<AccidentParticipant, AccidentParticipantModel> {

    public AccidentParticipantViewHandler(UserType aUserType) {
        super("Involvert", null,false, aUserType, true);
    }
    public JTextField getTextFieldFirstName(PresentationModel presentationModel){
        JTextField textField=BasicComponentFactory.createTextField(presentationModel.getBufferedModel(AccidentParticipantModel.PROPERTY_FIRST_NAME));
        textField.setName("TextFieldFirstName");
        List<String> firstNameList = Util.getFirstNameList();
        AutoCompleteDecorator.decorate(textField,firstNameList,false);
        return textField;
    }
    public JTextField getTextFieldLastName(PresentationModel presentationModel){
        JTextField textField=BasicComponentFactory.createTextField(presentationModel.getBufferedModel(AccidentParticipantModel.PROPERTY_LAST_NAME));
        textField.setName("TextFieldLastName");
        List<String> lastNameList = Util.getLastNameList();
        AutoCompleteDecorator.decorate(textField,lastNameList,false);
        return textField;
    }
    
    public JComboBox getComboBoxEmployeeType(PresentationModel presentationModel){
        JComboBox comboBox = Util.getComboBoxEmployeeType(true, presentationModel.getBufferedModel(AccidentParticipantModel.PROPERTY_EMPLOYEE_TYPE));
        comboBox.setName("ComboBoxEmployeeType");
        return comboBox;
    }


    @Override
    public String getAddRemoveString() {
        return "involvert";
    }

    @Override
    public String getClassName() {
        return "AccidentParticipant";
    }

    @Override
    protected AbstractEditView<AccidentParticipantModel, AccidentParticipant> getEditView(AbstractViewHandler<AccidentParticipant, AccidentParticipantModel> handler, AccidentParticipant object, boolean searching) {
        // TODO Auto-generated method stub
        return null;
    }

  


    @Override
    public Boolean hasWriteAccess() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public CheckObject checkDeleteObject(AccidentParticipant object) {
        return null;
    }
    
    


}
