/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.EmployeeType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmployeeTypeModel extends AbstractModel<EmployeeType, EmployeeTypeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_EMPLOYEE_TYPE_ID = "employeeTypeId";
/*     */    public static final String PROPERTY_EMPLOYEE_TYPE_NAME = "employeeTypeName";
/*     */    public static final String PROPERTY_EMPLOYEE_TYPE_DESCRIPTION = "employeeTypeDescription";
/*     */ 
/*     */    public EmployeeTypeModel(EmployeeType object) {
/*  41 */       super(object);
/*  42 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getEmployeeTypeId() {
/*  48 */       return ((EmployeeType)this.object).getEmployeeTypeId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setEmployeeTypeId(Integer employeeTypeId) {
/*  55 */       Integer oldId = this.getEmployeeTypeId();
/*  56 */       ((EmployeeType)this.object).setEmployeeTypeId(employeeTypeId);
/*  57 */       this.firePropertyChange("employeeTypeId", oldId, employeeTypeId);
/*  58 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getEmployeeTypeName() {
/*  64 */       return ((EmployeeType)this.object).getEmployeeTypeName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setEmployeeTypeName(String employeeTypeName) {
/*  71 */       String oldName = this.getEmployeeTypeName();
/*  72 */       ((EmployeeType)this.object).setEmployeeTypeName(employeeTypeName);
/*  73 */       this.firePropertyChange("employeeTypeName", oldName, employeeTypeName);
/*     */ 
/*  75 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getEmployeeTypeDescription() {
/*  81 */       return ((EmployeeType)this.object).getEmployeeTypeDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setEmployeeTypeDescription(String employeeTypeDescription) {
/*  88 */       String oldDesc = this.getEmployeeTypeDescription();
/*  89 */       ((EmployeeType)this.object).setEmployeeTypeDescription(employeeTypeDescription);
/*  90 */       this.firePropertyChange("employeeTypeDescription", oldDesc, employeeTypeDescription);
/*     */ 
/*  92 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 101 */       presentationModel.getBufferedModel("employeeTypeName").addValueChangeListener(listener);
/*     */ 
/* 103 */       presentationModel.getBufferedModel("employeeTypeDescription").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 106 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public EmployeeTypeModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 114 */       EmployeeTypeModel employeeTypeModel = new EmployeeTypeModel(new EmployeeType());
/*     */ 
/* 116 */       employeeTypeModel.setEmployeeTypeId((Integer)presentationModel.getBufferedValue("employeeTypeId"));
/*     */ 
/* 118 */       employeeTypeModel.setEmployeeTypeName((String)presentationModel.getBufferedValue("employeeTypeName"));
/*     */ 
/* 120 */       employeeTypeModel.setEmployeeTypeDescription((String)presentationModel.getBufferedValue("employeeTypeDescription"));
/*     */ 
/*     */ 
/* 123 */       return employeeTypeModel;
/*     */    }
/*     */ }
