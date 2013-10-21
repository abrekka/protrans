package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IntelleV extends BaseObject{
	private String orderNr;
	private Integer maxHoyde;

	public Integer getMaxHoyde() {
		return maxHoyde;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof IntelleV))
			return false;
		IntelleV castOther = (IntelleV) other;
		return new EqualsBuilder().append(orderNr, castOther.orderNr).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderNr).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("orderNr", orderNr).append(
				"maxHoyde", maxHoyde).toString();
	}

	public String getOrderNr() {
		return orderNr;
	}

	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	public void setMaxHoyde(Integer maxHoyde) {
		this.maxHoyde = maxHoyde;
	}

}
