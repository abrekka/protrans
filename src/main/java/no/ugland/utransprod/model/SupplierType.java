package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for tabell SUPPLIER_TYPE
 * 
 * @author atle.brekka
 * 
 */
public class SupplierType extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer supplierTypeId;

	/**
	 * 
	 */
	private String supplierTypeName;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	public SupplierType() {
		super();
	}

	/**
	 * @param supplierTypeId
	 * @param supplierTypeName
	 * @param description
	 */
	public SupplierType(Integer supplierTypeId, String supplierTypeName,
			String description) {
		super();
		this.supplierTypeId = supplierTypeId;
		this.supplierTypeName = supplierTypeName;
		this.description = description;
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return id
	 */
	public Integer getSupplierTypeId() {
		return supplierTypeId;
	}

	/**
	 * @param supplierTypeId
	 */
	public void setSupplierTypeId(Integer supplierTypeId) {
		this.supplierTypeId = supplierTypeId;
	}

	/**
	 * @return navn
	 */
	public String getSupplierTypeName() {
		return supplierTypeName;
	}

	/**
	 * @param supplierTypeName
	 */
	public void setSupplierTypeName(String supplierTypeName) {
		this.supplierTypeName = supplierTypeName;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SupplierType))
			return false;
		SupplierType castOther = (SupplierType) other;
		return new EqualsBuilder().append(supplierTypeName,
				castOther.supplierTypeName).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(supplierTypeName).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return supplierTypeName;
	}
}
