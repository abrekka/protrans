package no.ugland.utransprod.model;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Klasse for tabell EXTERNAL_ORDER
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrder extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer externalOrderId;

	/**
	 * 
	 */
	private String externalOrderNr;

	/**
	 * 
	 */
	private Date deliveryDate;

	/**
	 * 
	 */
	private String att;

	/**
	 * 
	 */
	private String ourRef;

	/**
	 * 
	 */
	private String markedWith;

	/**
	 * 
	 */
	private String phoneNr;

	/**
	 * 
	 */
	private String faxNr;

	/**
	 * 
	 */
	private Order order;

	/**
	 * 
	 */
	private Supplier supplier;

	/**
	 * 
	 */
	private Set<ExternalOrderLine> externalOrderLines;

	/**
	 * 
	 */
	public ExternalOrder() {
		super();
	}

	/**
	 * @param externalOrderId
	 * @param externalOrderNr
	 * @param order
	 * @param supplier
	 * @param externalOrderLines
	 * @param deliveryDate
	 * @param att
	 * @param ourRef
	 * @param markedWith
	 * @param phoneNr
	 * @param faxNr
	 */
	public ExternalOrder(Integer externalOrderId, String externalOrderNr,
			Order order, Supplier supplier,
			Set<ExternalOrderLine> externalOrderLines, Date deliveryDate,
			String att, String ourRef, String markedWith, String phoneNr,
			String faxNr) {
		super();
		this.externalOrderId = externalOrderId;
		this.externalOrderNr = externalOrderNr;
		this.order = order;
		this.supplier = supplier;
		this.externalOrderLines = externalOrderLines;
		this.deliveryDate = deliveryDate;
		this.att = att;
		this.ourRef = ourRef;
		this.markedWith = markedWith;
		this.phoneNr = phoneNr;
		this.faxNr = faxNr;
	}

	/**
	 * @return id
	 */
	public Integer getExternalOrderId() {
		return externalOrderId;
	}

	/**
	 * @param externalOrderId
	 */
	public void setExternalOrderId(Integer externalOrderId) {
		this.externalOrderId = externalOrderId;
	}

	/**
	 * @return ordrenummer
	 */
	public String getExternalOrderNr() {
		return externalOrderNr;
	}

	/**
	 * @param externalOrderNr
	 */
	public void setExternalOrderNr(String externalOrderNr) {
		this.externalOrderNr = externalOrderNr;
	}

	/**
	 * @return ordre
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 */
	public void setOrder(Order order) {
		this.order = order;
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

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ExternalOrder))
			return false;
		ExternalOrder castOther = (ExternalOrder) other;
		return new EqualsBuilder().append(order, castOther.order).append(
				supplier, castOther.supplier).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(order).append(supplier)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"externalOrderId", externalOrderId).append("externalOrderNr",
				externalOrderNr).append("order", order).append("supplier",
				supplier).toString();
	}

	/**
	 * @return ordrelinjer
	 */
	public Set<ExternalOrderLine> getExternalOrderLines() {
		return externalOrderLines;
	}

	/**
	 * @param externalOrderLines
	 */
	public void setExternalOrderLines(Set<ExternalOrderLine> externalOrderLines) {
		this.externalOrderLines = externalOrderLines;
	}

	/**
	 * @return leveringsdato
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return att.
	 */
	public String getAtt() {
		return att;
	}

	/**
	 * @param att
	 */
	public void setAtt(String att) {
		this.att = att;
	}

	/**
	 * @return merket med
	 */
	public String getMarkedWith() {
		return markedWith;
	}

	/**
	 * @param markedWith
	 */
	public void setMarkedWith(String markedWith) {
		this.markedWith = markedWith;
	}

	/**
	 * @return våres ref.
	 */
	public String getOurRef() {
		return ourRef;
	}

	/**
	 * @param ourRef
	 */
	public void setOurRef(String ourRef) {
		this.ourRef = ourRef;
	}

	/**
	 * @return faxnummer
	 */
	public String getFaxNr() {
		return faxNr;
	}

	/**
	 * @param faxNr
	 */
	public void setFaxNr(String faxNr) {
		this.faxNr = faxNr;
	}

	/**
	 * @return telefonnummer
	 */
	public String getPhoneNr() {
		return phoneNr;
	}

	/**
	 * @param phoneNr
	 */
	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}
}
