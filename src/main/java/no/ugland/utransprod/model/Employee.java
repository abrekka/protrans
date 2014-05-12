package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.base.Joiner;

/**
 * Klasse for tabell EMPLOYEE
 * 
 * @author atle.brekka
 * 
 */
public class Employee extends BaseObject implements Comparable<Employee> {
    private static final long serialVersionUID = 1L;

    private Integer employeeId;

    private String firstName;

    private String lastName;

    private String phone;

    private EmployeeType employeeType;

    private Supplier supplier;

    private Integer inactive;

    public Employee() {
	super();
    }

    /**
     * @param employeeId
     * @param firstName
     * @param lastName
     * @param phone
     * @param employeeType
     * @param supplier
     */
    public Employee(Integer employeeId, String firstName, String lastName, String phone, EmployeeType employeeType, Supplier supplier) {
	super();
	this.employeeId = employeeId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.phone = phone;
	this.employeeType = employeeType;
	this.supplier = supplier;
    }

    /**
     * @return id
     */
    public Integer getEmployeeId() {
	return employeeId;
    }

    /**
     * @param employeeId
     */
    public void setEmployeeId(Integer employeeId) {
	this.employeeId = employeeId;
    }

    /**
     * @return ansattype
     */
    public EmployeeType getEmployeeType() {
	return employeeType;
    }

    /**
     * @param employeeType
     */
    public void setEmployeeType(EmployeeType employeeType) {
	this.employeeType = employeeType;
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
     * @return telefonnummer
     */
    public String getPhone() {
	return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
	this.phone = phone;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof Employee))
	    return false;
	Employee castOther = (Employee) other;
	return new EqualsBuilder().append(firstName, castOther.firstName).append(lastName, castOther.lastName)
		.append(employeeType, castOther.employeeType).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(firstName).append(lastName).append(employeeType).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
	return getFullName();
    }

    public final String getFullName() {
	return Joiner.on(" ").skipNulls().join(firstName, lastName);
    }

    /**
     * @return leverandør
     */
    public Supplier getSupplier() {
	return supplier;
    }

    /**
     * @param supplier
     */
    public void setSupplier(Supplier supplier) {
	this.supplier = supplier;
    }

    public int compareTo(final Employee other) {
	return new CompareToBuilder().append(firstName, other.firstName).append(lastName, other.lastName).toComparison();
    }

    public Integer getInactive() {
	return inactive;
    }

    public void setInactive(Integer inactive) {
	this.inactive = inactive;
    }

    public boolean erInaktiv() {
	return inactive == null || inactive == 0 ? false : true;
    }
}
