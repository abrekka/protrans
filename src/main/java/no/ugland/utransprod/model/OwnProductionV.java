package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.DayEnum;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view OWN_PRODUCTION_V
 * 
 * @author atle.brekka
 * 
 */
public class OwnProductionV extends BaseObject {
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
	private String packedBy;

	/**
	 * 
	 */
	private Integer transportYear;

	/**
	 * 
	 */
	private Integer transportWeek;

	/**
	 * 
	 */
	private Integer customerNr;

	/**
	 * 
	 */
	private String customerName;

	/**
	 * 
	 */
	private BigDecimal garageValue;

	/**
	 * 
	 */
	private Integer orderReadyYear;

	/**
	 * 
	 */
	private Integer orderReadyWeek;

	/**
	 * 
	 */
	private Integer orderReadyDay;

	/**
	 * 
	 */
	private Date packlistReady;

	/**
	 * 
	 */
	private Date invoiceDate;

	/**
	 * 
	 */
	private String productArea;

	/**
	 * 
	 */
	private Date sent;
	/**
	 * 
	 */
	private String orderNr;
	private String productAreaGroupName;

	/**
	 * 
	 */
	public OwnProductionV() {
		super();
	}

	/**
	 * @param orderId
	 * @param packedBy
	 * @param transportYear
	 * @param transportWeek
	 * @param customerNr
	 * @param customerName
	 * @param garageValue
	 * @param orderReadyYear
	 * @param orderReadyWeek
	 * @param orderReadyDay
	 * @param packlistReady
	 * @param invoiceDate
	 * @param productArea
	 * @param sent
	 * @param orderNr 
	 */
	public OwnProductionV(Integer orderId, String packedBy,
			Integer transportYear, Integer transportWeek, Integer customerNr,
			String customerName, BigDecimal garageValue,
			Integer orderReadyYear, Integer orderReadyWeek,
			Integer orderReadyDay, Date packlistReady, Date invoiceDate,
			String productArea, Date sent,String orderNr,String productAreaGroupName) {
		super();
		this.orderId = orderId;
		this.packedBy = packedBy;
		this.transportYear = transportYear;
		this.transportWeek = transportWeek;
		this.customerNr = customerNr;
		this.customerName = customerName;
		this.garageValue = garageValue;
		this.orderReadyYear = orderReadyYear;
		this.orderReadyWeek = orderReadyWeek;
		this.orderReadyDay = orderReadyDay;
		this.packlistReady = packlistReady;
		this.invoiceDate = invoiceDate;
		this.productArea = productArea;
		this.sent = sent;
		this.orderNr=orderNr;
		this.productAreaGroupName=productAreaGroupName;
	}

	/**
	 * @return kundenavn
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return kundenummer
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
	 * @return fakturadato
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @return dfakturadato formatert
	 */
	public String getInvoiceDateFormat() {
		if (invoiceDate != null) {
			return Util.SHORT_DATE_FORMAT.format(invoiceDate);
		}
		return null;
	}

	/**
	 * @param invoiceDate
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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
	 * @return 1 dersom ordre er klar
	 */
	public Integer getOrderReadyDay() {
		return orderReadyDay;
	}

	/**
	 * @return dag når ordre var klar
	 */
	public String getOrderReadyDayString() {
		if (orderReadyDay != null) {
			return DayEnum.getDayString(orderReadyDay).toString();
		}
		return null;
	}

	/**
	 * @param orderReadyDay
	 */
	public void setOrderReadyDay(Integer orderReadyDay) {
		this.orderReadyDay = orderReadyDay;
	}

	/**
	 * @return uke
	 */
	public Integer getOrderReadyWeek() {
		return orderReadyWeek;
	}

	/**
	 * @param orderReadyWeek
	 */
	public void setOrderReadyWeek(Integer orderReadyWeek) {
		this.orderReadyWeek = orderReadyWeek;
	}

	/**
	 * @return år
	 */
	public Integer getOrderReadyYear() {
		return orderReadyYear;
	}

	/**
	 * @param orderReadyYear
	 */
	public void setOrderReadyYear(Integer orderReadyYear) {
		this.orderReadyYear = orderReadyYear;
	}

	/**
	 * @return pakket av
	 */
	public String getPackedBy() {
		return packedBy;
	}

	/**
	 * @param packedBy
	 */
	public void setPackedBy(String packedBy) {
		this.packedBy = packedBy;
	}

	/**
	 * @return dato for når pakkliste var klar
	 */
	public Date getPacklistReady() {
		return packlistReady;
	}

	/**
	 * @return dato for pakkliste formatert
	 */
	public String getPacklistReadyFormat() {
		if (packlistReady != null) {
			return Util.SHORT_DATE_FORMAT.format(packlistReady);
		}
		return null;
	}

	/**
	 * @param packlistReady
	 */
	public void setPacklistReady(Date packlistReady) {
		this.packlistReady = packlistReady;
	}

	/**
	 * @return transportuke
	 */
	public Integer getTransportWeek() {
		return transportWeek;
	}

	/**
	 * @param transportWeek
	 */
	public void setTransportWeek(Integer transportWeek) {
		this.transportWeek = transportWeek;
	}

	/**
	 * @return transportår
	 */
	public Integer getTransportYear() {
		return transportYear;
	}

	/**
	 * @param transportYear
	 */
	public void setTransportYear(Integer transportYear) {
		this.transportYear = transportYear;
	}

	/**
	 * @return transport år og uke
	 */
	public String getTransportYearWeek() {
		if(transportYear!=null){
		return String.valueOf(transportYear) + "-" + transportWeek;
		}
		return "";
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OwnProductionV))
			return false;
		OwnProductionV castOther = (OwnProductionV) other;
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

	/**
	 * @return produktområde
	 */
	public String getProductArea() {
		return productArea;
	}

	/**
	 * @param productArea
	 */
	public void setProductArea(String productArea) {
		this.productArea = productArea;
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
	 * @return ordernummer
	 */
	public String getOrderNr() {
		return orderNr;
	}

	/**
	 * @param orderNr
	 */
	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	public String getProductAreaGroupName() {
		return productAreaGroupName;
	}

	public void setProductAreaGroupName(String productAreaGroupName) {
		this.productAreaGroupName = productAreaGroupName;
	}
}
