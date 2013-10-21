package no.ugland.utransprod.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som representerer tabell CUSTOMER
 * 
 * @author atle.brekka
 * 
 */
public class Customer extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer customerId;

	private Integer customerNr;

	private String firstName;

	private String lastName;

	private Set<Order> orders;
	public static final Customer UNKNOWN = new Customer() {};

	public Customer() {
		super();
	}

	/**
	 * @param customerId
	 * @param customerNr
	 * @param firstName
	 * @param lastName
	 * @param orders
	 */
	public Customer(Integer customerId, Integer customerNr, String firstName,
			String lastName, Set<Order> orders) {
		super();
		this.customerId = customerId;
		this.customerNr = customerNr;
		this.firstName = firstName;
		this.lastName = lastName;
		this.orders = orders;
	}

	/**
	 * @return kundeid
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return kundenr
	 */
	public Integer getCustomerNr() {
		return customerNr;
	}

	/**
	 * @param customerNr
	 */
	public void setCustomerNr(Integer customerNr) {
		this.customerNr = customerNr;
	}

	/**
	 * @return fornavn
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return etternavn
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Customer))
			return false;
		Customer castOther = (Customer) other;
		return new EqualsBuilder().append(customerNr, castOther.customerNr)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(customerNr).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return getCustomerDetails();
	}
    
    public final String getCustomerDetails(){
        return customerNr + " " + firstName + " " + lastName;
    }

	/**
	 * @return ordre
	 */
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * @param order
	 */
	public void removeOrder(Order order) {
		if (this.orders != null) {
			this.orders.remove(order);
		}
		order.setCustomer(null);
	}

	/**
	 * @return fullt navn
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}
}
