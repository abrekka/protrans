package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SupplierProductAreaGroup extends BaseObject {
	private Integer id;
	private Supplier supplier;
	private ProductAreaGroup productAreaGroup;
	public SupplierProductAreaGroup(){
		
	}
	public SupplierProductAreaGroup(Supplier supplier,
			ProductAreaGroup productAreaGroup) {
		super();
		this.supplier = supplier;
		this.productAreaGroup = productAreaGroup;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public ProductAreaGroup getProductAreaGroup() {
		return productAreaGroup;
	}
	public void setProductAreaGroup(ProductAreaGroup productAreaGroup) {
		this.productAreaGroup = productAreaGroup;
	}
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SupplierProductAreaGroup))
			return false;
		SupplierProductAreaGroup castOther = (SupplierProductAreaGroup) other;
		return new EqualsBuilder().append(supplier, castOther.supplier).append(
				productAreaGroup, castOther.productAreaGroup).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(supplier).append(productAreaGroup)
				.toHashCode();
	}
	@Override
	public String toString() {
		return productAreaGroup.getProductAreaGroupName();
	}
}
