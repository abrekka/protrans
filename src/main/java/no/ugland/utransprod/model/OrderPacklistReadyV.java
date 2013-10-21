package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view ORDER_PACKLIST_READY_V
 * 
 * @author atle.brekka
 * 
 */
public class OrderPacklistReadyV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private OrderPacklistReadyVPK orderPacklistReadyVPK;

	/**
	 * 
	 */
	private Integer orderCount;

	/**
	 * 
	 */
	private BigDecimal customerCost;

	/**
	 * 
	 */
	public OrderPacklistReadyV() {
		super();
	}

	/**
	 * @param orderPacklistReadyVPK
	 * @param orderCount
	 * @param customerCost
	 */
	public OrderPacklistReadyV(OrderPacklistReadyVPK orderPacklistReadyVPK,
			Integer orderCount, BigDecimal customerCost) {
		super();
		this.orderPacklistReadyVPK = orderPacklistReadyVPK;
		this.orderCount = orderCount;
		this.customerCost = customerCost;
	}

	/**
	 * @param year
	 * @param week
	 * @param count
	 * @param cost
	 */
	public OrderPacklistReadyV(Integer year, Integer week, Integer count,
			BigDecimal cost) {
		super();
		this.orderPacklistReadyVPK = new OrderPacklistReadyVPK(year, week, null);
		this.orderCount = count;
		this.customerCost = cost;
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
	 * @return id
	 */
	public OrderPacklistReadyVPK getOrderPacklistReadyVPK() {
		return orderPacklistReadyVPK;
	}

	/**
	 * @param orderPacklistReadyVPK
	 */
	public void setOrderPacklistReadyVPK(
			OrderPacklistReadyVPK orderPacklistReadyVPK) {
		this.orderPacklistReadyVPK = orderPacklistReadyVPK;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OrderPacklistReadyV))
			return false;
		OrderPacklistReadyV castOther = (OrderPacklistReadyV) other;
		return new EqualsBuilder().append(orderPacklistReadyVPK,
				castOther.orderPacklistReadyVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderPacklistReadyVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"orderPacklistReadyVPK", orderPacklistReadyVPK).append(
				"orderCount", orderCount).append("customerCost", customerCost)
				.toString();
	}

	/**
	 * @return budsjett
	 */
	public BigDecimal getBudget() {
		return null;
	}

}
