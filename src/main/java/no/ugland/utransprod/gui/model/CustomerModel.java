package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.Customer;

import com.jgoodies.binding.PresentationModel;

/**
 * Klasse som wrapper en kunde for visning
 * @author atle.brekka
 *
 */
public class CustomerModel extends AbstractModel<Customer,CustomerModel> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static final String PROPERTY_CUSTOMER_ID = "customerId";
	/**
	 * Kundenr
	 */
	public static final String PROPERTY_CUSTOMER_NR = "customerNr";
    /**
     * Fornavn
     */
    public static final String PROPERTY_FIRST_NAME = "firstName";
    /**
     * etternavn
     */
    public static final String PROPERTY_LAST_NAME = "lastName";
    /**
     * @param customer
     */
    public CustomerModel(Customer customer){
        super(customer);
    }
    
    /**
     * @return id
     */
    public Integer getCustomerId(){
    	return object.getCustomerId();
    }
    /**
     * @param customerId
     */
    public void setCustomerId(Integer customerId){
    	Integer oldId = getCustomerId();
    	object.setCustomerId(customerId);
    	firePropertyChange(PROPERTY_CUSTOMER_ID, oldId, customerId);
    }
    
    /**
     * @return kundenummer
     */
    public Integer getCustomerNr(){
    	return object.getCustomerNr();
    }
    
    /**
     * @param customerNr
     */
    public void setCustomerNr(Integer customerNr){
    	Integer oldNr = object.getCustomerNr();
    	object.setCustomerNr(customerNr);
    	firePropertyChange(PROPERTY_CUSTOMER_NR, oldNr, customerNr);
    }
    
    /**
     * @return fornavn
     */
    public String getFirstName(){
    	return object.getFirstName();
    }
    
    /**
     * @param firstName
     */
    public void setFirstName(String firstName){
    	String oldName = object.getFirstName();
    	object.setFirstName(firstName);
    	firePropertyChange(PROPERTY_FIRST_NAME, oldName, firstName);
    }
    
    /**
     * @return etternavn
     */
    public String getLastName(){
    	return object.getLastName();
    }
    
    /**
     * @param lastName
     */
    public void setLastName(String lastName){
    	String oldName = object.getLastName();
    	object.setLastName(lastName);
    	firePropertyChange(PROPERTY_LAST_NAME, oldName, lastName);
    }

	/**
	 * @return kunde
	 */
	/*public Customer getCustomer() {
		return customer;
	}*/
    
	/**
	 * Henter bufferkunde fra modell
	 * @param presentationModel
	 * @return bufferkunde
	 */
    @Override
	public CustomerModel getBufferedObjectModel(PresentationModel presentationModel){
		CustomerModel customerModel = new CustomerModel(new Customer());
		customerModel.setCustomerId((Integer)presentationModel.getBufferedValue(PROPERTY_CUSTOMER_ID));
		customerModel.setCustomerNr((Integer)presentationModel.getBufferedValue(PROPERTY_CUSTOMER_NR));
		customerModel.setFirstName((String)presentationModel.getBufferedValue(PROPERTY_FIRST_NAME));
		customerModel.setLastName((String)presentationModel.getBufferedValue(PROPERTY_LAST_NAME));
		return customerModel;
	}
	
	/**
	 * Legger til lytter for endring i buffer for kunde
	 * @param listener
	 * @param presentationModel
	 */
    @Override
	public void addBufferChangeListener(PropertyChangeListener listener,PresentationModel presentationModel){
		presentationModel.getBufferedModel(PROPERTY_CUSTOMER_NR).addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_FIRST_NAME).addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_LAST_NAME).addValueChangeListener(listener);
	}

	
}
