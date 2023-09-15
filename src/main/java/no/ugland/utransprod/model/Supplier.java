package no.ugland.utransprod.model;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;

/**
 * Domeneklasse for tabell SUPPLIER
 * 
 * @author atle.brekka
 */
public class Supplier extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer supplierId;

	private String supplierName;

	private String supplierDescription;

	private String phone;

	private SupplierType supplierType;

	private String address;

	private String postalCode;

	private String postOffice;

	private String fax;

	private Set<Employee> employees;

	private Set<Assembly> assemblies;
	private Integer inactive;
	private Set<SupplierProductAreaGroup> supplierProductAreaGroups;

	private String supplierNr;
	private Integer holidayFrom;
	private Integer holidayTo;

	/**
	 * 
	 */
	public Supplier() {
		super();
	}

	/**
	 * @param supplierId
	 * @param supplierName
	 * @param supplierDescription
	 * @param phone
	 * @param supplierType
	 * @param address
	 * @param postalCode
	 * @param postOffice
	 * @param fax
	 * @param employees
	 */
	public Supplier(Integer supplierId, String supplierName, String supplierDescription, String phone,
			SupplierType supplierType, String address, String postalCode, String postOffice, String fax,
			Set<Employee> employees, final Set<Assembly> someAssemblies, final Integer isInactive) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.supplierDescription = supplierDescription;
		this.phone = phone;
		this.supplierType = supplierType;
		this.address = address;
		this.postalCode = postalCode;
		this.postOffice = postOffice;
		this.fax = fax;
		this.employees = employees;
		this.assemblies = someAssemblies;
		this.inactive = isInactive;
	}

	public Integer getHolidayFrom() {
		return holidayFrom;
	}

	public Integer getHolidayTo() {
		return holidayTo;
	}

	public void setHolidayFrom(Integer holidayFrom) {
		this.holidayFrom = holidayFrom;
	}

	public void setHolidayTo(Integer holidayTo) {
		this.holidayTo = holidayTo;
	}

	/**
	 * @return beskrivelse
	 */
	public String getSupplierDescription() {
		return supplierDescription;
	}

	/**
	 * @param supplierDescription
	 */
	public void setSupplierDescription(String supplierDescription) {
		this.supplierDescription = supplierDescription;
	}

	/**
	 * @return id
	 */
	public Integer getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 */
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * @return navn
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * @param supplierName
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Supplier))
			return false;
		Supplier castOther = (Supplier) other;
		return new EqualsBuilder().append(supplierName, castOther.supplierName).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(supplierName).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("supplierName", supplierName).toString();
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
	 * @return type
	 */
	public SupplierType getSupplierType() {
		return supplierType;
	}

	/**
	 * @param supplierType
	 */
	public void setSupplierType(SupplierType supplierType) {
		this.supplierType = supplierType;
	}

	/**
	 * @return adresse
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return postnummer
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return poststed
	 */
	public String getPostOffice() {
		return postOffice;
	}

	/**
	 * @param postOffice
	 */
	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	/**
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return ansatte
	 */
	public Set<Employee> getEmployees() {
		return employees;
	}

	/**
	 * @param employees
	 */
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Set<Assembly> getAssemblies() {
		return assemblies;
	}

	public void setAssemblies(Set<Assembly> assemblies) {
		this.assemblies = assemblies;
	}

	public final Integer getInactive() {
		return inactive;
	}

	public final void setInactive(final Integer isInactive) {
		this.inactive = isInactive;
	}

	public Set<SupplierProductAreaGroup> getSupplierProductAreaGroups() {
		return supplierProductAreaGroups;
	}

	public void setSupplierProductAreaGroups(Set<SupplierProductAreaGroup> productAreaGroups) {
		this.supplierProductAreaGroups = productAreaGroups;
	}

	public Collection<Employee> getActiveEmployees() {
		return Lists.newArrayList(Iterables.filter(employees, aktive()));
	}

	private Predicate<Employee> aktive() {
		return new Predicate<Employee>() {

			public boolean apply(Employee employee) {
				return !employee.erInaktiv();
			}
		};
	}

	public String getSupplierNr() {
		return supplierNr;
	}

	public void setSupplierNr(String supplierNr) {
		this.supplierNr = supplierNr;
	}

}
