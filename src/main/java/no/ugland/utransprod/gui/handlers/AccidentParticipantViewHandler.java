/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import com.jgoodies.binding.adapter.BasicComponentFactory;
/*    */ import java.util.List;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JTextField;
/*    */ import no.ugland.utransprod.gui.edit.AbstractEditView;
/*    */ import no.ugland.utransprod.gui.model.AccidentParticipantModel;
/*    */ import no.ugland.utransprod.model.AccidentParticipant;
/*    */ import no.ugland.utransprod.model.UserType;
/*    */ import no.ugland.utransprod.service.OverviewManager;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccidentParticipantViewHandler extends AbstractViewHandlerShort<AccidentParticipant, AccidentParticipantModel> {
/*    */    public AccidentParticipantViewHandler(UserType aUserType) {
/* 27 */       super("Involvert", (OverviewManager)null, false, aUserType, true);
/* 28 */    }
/*    */    public JTextField getTextFieldFirstName(PresentationModel presentationModel) {
/* 30 */       JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel("firstName"));
/* 31 */       textField.setName("TextFieldFirstName");
/* 32 */       List<String> firstNameList = Util.getFirstNameList();
/* 33 */       AutoCompleteDecorator.decorate(textField, firstNameList, false);
/* 34 */       return textField;
/*    */    }
/*    */    public JTextField getTextFieldLastName(PresentationModel presentationModel) {
/* 37 */       JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel("lastName"));
/* 38 */       textField.setName("TextFieldLastName");
/* 39 */       List<String> lastNameList = Util.getLastNameList();
/* 40 */       AutoCompleteDecorator.decorate(textField, lastNameList, false);
/* 41 */       return textField;
/*    */    }
/*    */ 
/*    */    public JComboBox getComboBoxEmployeeType(PresentationModel presentationModel) {
/* 45 */       JComboBox comboBox = Util.getComboBoxEmployeeType(true, presentationModel.getBufferedModel("employeeType"));
/* 46 */       comboBox.setName("ComboBoxEmployeeType");
/* 47 */       return comboBox;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getAddRemoveString() {
/* 53 */       return "involvert";
/*    */    }
/*    */ 
/*    */ 
/*    */    public String getClassName() {
/* 58 */       return "AccidentParticipant";
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    protected AbstractEditView<AccidentParticipantModel, AccidentParticipant> getEditView(AbstractViewHandler<AccidentParticipant, AccidentParticipantModel> handler, AccidentParticipant object, boolean searching) {
/* 64 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public Boolean hasWriteAccess() {
/* 73 */       return null;
/*    */    }
/*    */ 
/*    */    public CheckObject checkDeleteObject(AccidentParticipant object) {
/* 77 */       return null;
/*    */    }
/*    */ }
