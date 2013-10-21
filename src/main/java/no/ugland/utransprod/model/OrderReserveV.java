package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view ORDER_RESERVE_V
 * 
 * @author atle.brekka
 * 
 */
public class OrderReserveV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private OrderReserveVPK orderReserveVPK;

	/**
	 * 
	 */
	private Integer orderCount;

	/**
	 * 
	 */
	private BigDecimal customerCost;

	private BigDecimal ownProduction;

	/**
	 * 
	 */
	public OrderReserveV() {
		super();
	}

	/**
	 * @param orderReserveVPK
	 * @param orderCount
	 * @param customerCost
	 */
	public OrderReserveV(OrderReserveVPK orderReserveVPK, Integer orderCount,
			BigDecimal customerCost,BigDecimal ownProduction) {
		super();
		this.orderReserveVPK = orderReserveVPK;
		this.orderCount = orderCount;
		this.customerCost = customerCost;
		this.ownProduction=ownProduction;
	}

	/**
	 * @return produktområde
	 */
	public String getProductArea() {
		return orderReserveVPK.getProductArea();
	}

	/**
	 * @return Ja drsom pakkliste er klar
	 */
	public String getIsPacklistReady() {
		return orderReserveVPK.getIsPacklistReady();
	}

	/**
	 * @return kundekostnad
	 */
	public BigDecimal getCustomerCost() {
		return customerCost;
	}

	/**
	 * @param customerCost
	 */
	public void setCustomerCost(BigDecimal customerCost) {
		this.customerCost = customerCost;
	}

	/**
	 * @return antall ordre
	 */
	public Integer getOrderCount() {
		return orderCount;
	}

	/**
	 * @param orderCount
	 */
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	/**
	 * @return nøkkel
	 */
	public OrderReserveVPK getOrderReserveVPK() {
		return orderReserveVPK;
	}

	/**
	 * @param orderReserveVPK
	 */
	public void setOrderReserveVPK(OrderReserveVPK orderReserveVPK) {
		this.orderReserveVPK = orderReserveVPK;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OrderReserveV))
			return false;
		OrderReserveV castOther = (OrderReserveV) other;
		return new EqualsBuilder().append(orderReserveVPK,
				castOther.orderReserveVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderReserveVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"orderReserveVPK", orderReserveVPK).append("orderCount",
				orderCount).append("customerCost", customerCost).toString();
	}

	public BigDecimal getOwnProduction() {
		return ownProduction;
	}

	public void setOwnProduction(BigDecimal ownProduction) {
		this.ownProduction = ownProduction;
	}
}
