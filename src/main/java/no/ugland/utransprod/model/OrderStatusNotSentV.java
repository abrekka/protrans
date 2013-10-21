package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view PRDER_STATUS_NOT_SENT_V
 * 
 * @author atle.brekka
 * 
 */
public class OrderStatusNotSentV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer orderId;

	/**
	 * 
	 */
	private Date packlistReady;

	/**
	 * 
	 */
	private Date orderReady;

	/**
	 * 
	 */
	private Date sent;

	/**
	 * 
	 */
	private BigDecimal garageValue;
	private String productArea;

	/**
	 * 
	 */
	public OrderStatusNotSentV() {
		super();
	}

	/**
	 * @param orderId
	 * @param packlistReady
	 * @param orderReady
	 * @param sent
	 * @param garageValue
	 */
	public OrderStatusNotSentV(Integer orderId, Date packlistReady,
			Date orderReady, Date sent, BigDecimal garageValue,String productArea) {
		super();
		this.orderId = orderId;
		this.packlistReady = packlistReady;
		this.orderReady = orderReady;
		this.sent = sent;
		this.garageValue = garageValue;
		this.productArea=productArea;
	}

	/**
	 * @return garasjeverdi
	 */
	public BigDecimal getGarageValue() {
		return garageValue;
	}

	/**
	 * @param garageValue
	 */
	public void setGarageValue(BigDecimal garageValue) {
		this.garageValue = garageValue;
	}

	/**
	 * @return ordreid
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return dato for ordre klar
	 */
	public Date getOrderReady() {
		return orderReady;
	}

	/**
	 * @param orderReady
	 */
	public void setOrderReady(Date orderReady) {
		this.orderReady = orderReady;
	}

	/**
	 * @return dato for pakkliste klar
	 */
	public Date getPacklistReady() {
		return packlistReady;
	}

	/**
	 * @param packlistReady
	 */
	public void setPacklistReady(Date packlistReady) {
		this.packlistReady = packlistReady;
	}

	/**
	 * @return sendtdato
	 */
	public Date getSent() {
		return sent;
	}

	/**
	 * @param sent
	 */
	public void setSent(Date sent) {
		this.sent = sent;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OrderStatusNotSentV))
			return false;
		OrderStatusNotSentV castOther = (OrderStatusNotSentV) other;
		return new EqualsBuilder().append(orderId, castOther.orderId)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderId).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"orderId", orderId).toString();
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}
}
