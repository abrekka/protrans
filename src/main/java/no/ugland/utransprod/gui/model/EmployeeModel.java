/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.Employee;
/*     */ import no.ugland.utransprod.model.EmployeeType;
/*     */ import no.ugland.utransprod.util.Util;
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
/*     */ public class EmployeeModel extends AbstractModel<Employee, EmployeeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_EMPLOYEE_ID = "employeeId";
/*     */    public static final String PROPERTY_FIRST_NAME = "firstName";
/*     */    public static final String PROPERTY_LAST_NAME = "lastName";
/*     */    public static final String PROPERTY_PHONE = "phone";
/*     */    public static final String PROPERTY_EMPLOYEE_TYPE = "employeeType";
/*     */    public static final String PROPERTY_INACTIVE_BOOL = "inactiveBool";
/*     */ 
/*     */    public EmployeeModel(Employee object) {
/*  36 */       super(object);
/*  37 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getEmployeeId() {
/*  43 */       return ((Employee)this.object).getEmployeeId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setEmployeeId(Integer employeeId) {
/*  50 */       Integer oldId = this.getEmployeeId();
/*  51 */       ((Employee)this.object).setEmployeeId(employeeId);
/*  52 */       this.firePropertyChange("employeeId", oldId, employeeId);
/*  53 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getFirstName() {
/*  59 */       return ((Employee)this.object).getFirstName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFirstName(String firstName) {
/*  66 */       String oldName = this.getFirstName();
/*  67 */       ((Employee)this.object).setFirstName(firstName);
/*  68 */       this.firePropertyChange("firstName", oldName, firstName);
/*  69 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getLastName() {
/*  75 */       return ((Employee)this.object).getLastName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setLastName(String lastName) {
/*  82 */       String oldName = this.getLastName();
/*  83 */       ((Employee)this.object).setLastName(lastName);
/*  84 */       this.firePropertyChange("lastName", oldName, lastName);
/*  85 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPhone() {
/*  91 */       return ((Employee)this.object).getPhone();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPhone(String phone) {
/*  98 */       String oldPhone = this.getPhone();
/*  99 */       ((Employee)this.object).setPhone(phone);
/* 100 */       this.firePropertyChange("phone", oldPhone, phone);
/* 101 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public EmployeeType getEmployeeType() {
/* 107 */       return ((Employee)this.object).getEmployeeType();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setEmployeeType(EmployeeType employeeType) {
/* 114 */       EmployeeType oldType = this.getEmployeeType();
/* 115 */       ((Employee)this.object).setEmployeeType(employeeType);
/* 116 */       this.firePropertyChange("employeeType", oldType, employeeType);
/* 117 */    }
/*     */    public Boolean getInactiveBool() {
/* 119 */       return Util.convertNumberToBoolean(((Employee)this.object).getInactive());
/*     */    }
/*     */    public void setInactiveBool(Boolean isInactive) {
/* 122 */       Boolean oldInactive = this.getInactiveBool();
/* 123 */       ((Employee)this.object).setInactive(Util.convertBooleanToNumber(isInactive));
/* 124 */       this.firePropertyChange("inactiveBool", oldInactive, isInactive);
/* 125 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 134 */       presentationModel.getBufferedModel("firstName").addValueChangeListener(listener);
/*     */ 
/* 136 */       presentationModel.getBufferedModel("lastName").addValueChangeListener(listener);
/*     */ 
/* 138 */       presentationModel.getBufferedModel("phone").addValueChangeListener(listener);
/*     */ 
/* 140 */       presentationModel.getBufferedModel("employeeType").addValueChangeListener(listener);
/*     */ 
/* 142 */       presentationModel.getBufferedModel("inactiveBool").addValueChangeListener(listener);
/*     */ 
/* 144 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public EmployeeModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 152 */       EmployeeModel employeeModel = new EmployeeModel(new Employee());
/* 153 */       employeeModel.setEmployeeId((Integer)presentationModel.getBufferedValue("employeeId"));
/*     */ 
/* 155 */       employeeModel.setFirstName((String)presentationModel.getBufferedValue("firstName"));
/*     */ 
/* 157 */       employeeModel.setLastName((String)presentationModel.getBufferedValue("lastName"));
/*     */ 
/* 159 */       employeeModel.setPhone((String)presentationModel.getBufferedValue("phone"));
/*     */ 
/* 161 */       employeeModel.setEmployeeType((EmployeeType)presentationModel.getBufferedValue("employeeType"));
/*     */ 
/* 163 */       employeeModel.setInactiveBool((Boolean)presentationModel.getBufferedValue("inactiveBool"));
/*     */ 
/*     */ 
/* 166 */       return employeeModel;
/*     */    }
/*     */ }
