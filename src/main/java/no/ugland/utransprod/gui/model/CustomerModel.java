/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.Customer;
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
/*     */ public class CustomerModel extends AbstractModel<Customer, CustomerModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_CUSTOMER_ID = "customerId";
/*     */    public static final String PROPERTY_CUSTOMER_NR = "customerNr";
/*     */    public static final String PROPERTY_FIRST_NAME = "firstName";
/*     */    public static final String PROPERTY_LAST_NAME = "lastName";
/*     */ 
/*     */    public CustomerModel(Customer customer) {
/*  39 */       super(customer);
/*  40 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getCustomerId() {
/*  46 */       return ((Customer)this.object).getCustomerId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCustomerId(Integer customerId) {
/*  52 */       Integer oldId = this.getCustomerId();
/*  53 */       ((Customer)this.object).setCustomerId(customerId);
/*  54 */       this.firePropertyChange("customerId", oldId, customerId);
/*  55 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getCustomerNr() {
/*  61 */       return ((Customer)this.object).getCustomerNr();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCustomerNr(Integer customerNr) {
/*  68 */       Integer oldNr = ((Customer)this.object).getCustomerNr();
/*  69 */       ((Customer)this.object).setCustomerNr(customerNr);
/*  70 */       this.firePropertyChange("customerNr", oldNr, customerNr);
/*  71 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getFirstName() {
/*  77 */       return ((Customer)this.object).getFirstName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFirstName(String firstName) {
/*  84 */       String oldName = ((Customer)this.object).getFirstName();
/*  85 */       ((Customer)this.object).setFirstName(firstName);
/*  86 */       this.firePropertyChange("firstName", oldName, firstName);
/*  87 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getLastName() {
/*  93 */       return ((Customer)this.object).getLastName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setLastName(String lastName) {
/* 100 */       String oldName = ((Customer)this.object).getLastName();
/* 101 */       ((Customer)this.object).setLastName(lastName);
/* 102 */       this.firePropertyChange("lastName", oldName, lastName);
/* 103 */    }
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
/*     */    public CustomerModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 119 */       CustomerModel customerModel = new CustomerModel(new Customer());
/* 120 */       customerModel.setCustomerId((Integer)presentationModel.getBufferedValue("customerId"));
/* 121 */       customerModel.setCustomerNr((Integer)presentationModel.getBufferedValue("customerNr"));
/* 122 */       customerModel.setFirstName((String)presentationModel.getBufferedValue("firstName"));
/* 123 */       customerModel.setLastName((String)presentationModel.getBufferedValue("lastName"));
/* 124 */       return customerModel;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 134 */       presentationModel.getBufferedModel("customerNr").addValueChangeListener(listener);
/* 135 */       presentationModel.getBufferedModel("firstName").addValueChangeListener(listener);
/* 136 */       presentationModel.getBufferedModel("lastName").addValueChangeListener(listener);
/* 137 */    }
/*     */ }
