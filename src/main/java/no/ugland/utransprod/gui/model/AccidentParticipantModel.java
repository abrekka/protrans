/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import no.ugland.utransprod.model.AccidentParticipant;
/*    */ import no.ugland.utransprod.model.EmployeeType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccidentParticipantModel extends AbstractModel<AccidentParticipant, AccidentParticipantModel> {
/*    */    public static final String PROPERTY_FIRST_NAME = "firstName";
/*    */    public static final String PROPERTY_LAST_NAME = "lastName";
/*    */    public static final String PROPERTY_EMPLOYEE_TYPE = "employeeType";
/*    */ 
/*    */    public AccidentParticipantModel(AccidentParticipant object) {
/* 17 */       super(object);
/* 18 */    }
/*    */    public String getFirstName() {
/* 20 */       return ((AccidentParticipant)this.object).getFirstName();
/*    */    }
/*    */    public void setFirstName(String firstName) {
/* 23 */       String oldName = this.getFirstName();
/* 24 */       ((AccidentParticipant)this.object).setFirstName(firstName);
/* 25 */       this.firePropertyChange("firstName", oldName, firstName);
/* 26 */    }
/*    */    public String getLastName() {
/* 28 */       return ((AccidentParticipant)this.object).getLastName();
/*    */    }
/*    */    public void setLastName(String lastName) {
/* 31 */       String oldName = this.getLastName();
/* 32 */       ((AccidentParticipant)this.object).setLastName(lastName);
/* 33 */       this.firePropertyChange("lastName", oldName, lastName);
/* 34 */    }
/*    */    public EmployeeType getEmployeeType() {
/* 36 */       return ((AccidentParticipant)this.object).getEmployeeType();
/*    */    }
/*    */    public void setEmployeeType(EmployeeType employeeType) {
/* 39 */       EmployeeType oldEmployeeType = this.getEmployeeType();
/* 40 */       ((AccidentParticipant)this.object).setEmployeeType(employeeType);
/* 41 */       this.firePropertyChange("employeeType", oldEmployeeType, employeeType);
/* 42 */    }
/*    */ 
/*    */ 
/*    */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 46 */       presentationModel.getBufferedModel("firstName").addValueChangeListener(listener);
/* 47 */       presentationModel.getBufferedModel("lastName").addValueChangeListener(listener);
/* 48 */       presentationModel.getBufferedModel("employeeType").addValueChangeListener(listener);
/*    */ 
/* 50 */    }
/*    */ 
/*    */ 
/*    */    public AccidentParticipantModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 54 */       AccidentParticipantModel accidentParticipantModel = new AccidentParticipantModel(new AccidentParticipant());
/*    */ 
/* 56 */       accidentParticipantModel.setFirstName((String)presentationModel.getBufferedValue("firstName"));
/*    */ 
/* 58 */       accidentParticipantModel.setLastName((String)presentationModel.getBufferedValue("lastName"));
/*    */ 
/* 60 */       accidentParticipantModel.setEmployeeType((EmployeeType)presentationModel.getBufferedValue("employeeType"));
/*    */ 
/* 62 */       return accidentParticipantModel;
/*    */    }
/*    */ }
