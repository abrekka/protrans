package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view NOT_INVOICED_V
 * 
 * @author atle.brekka
 * 
 */
public class NotInvoicedV extends BaseObject {
	private static final long serialVersionUID = 1L;
	private Integer orderId;
	private String orderDetails;
	private Date sent;
	private BigDecimal garageValue;
	private String productArea;
	private String orderNr;
	
	public NotInvoicedV() {
		super();
	}
	
	public String getOrderNr() {
		return orderNr;
	}
	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	/**
	 * @param orderId
	 * @param orderDetails
	 * @param sent
	 * @param garageValue
	 */
	public NotInvoicedV(Integer orderId, String orderDetails, Date sent,
			BigDecimal garageValue,String productArea) {
		super();
		this.orderId = orderId;
		this.orderDetails = orderDetails;
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
	 * @return ordredetaljer
	 */
	public String getOrderDetails() {
		return orderDetails;
	}

	/**
	 * @param orderDetails
	 */
	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
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
	 * @return sendt dato
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
	 * @return sendt dato som tekst
	 */
	public String getSentString() {
		if (sent != null) {
			return Util.SHORT_DATE_FORMAT.format(sent);
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof NotInvoicedV))
			return false;
		NotInvoicedV castOther = (NotInvoicedV) other;
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
