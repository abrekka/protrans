package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SalesDataSnapshot extends BaseObject {
	private Integer snapshotId;

	private Integer saleId;

	private String orderNr;

	private Integer probability;

	// private Integer groupIdx;
	private Integer productAreaNr;

	private Date saledate;

	private Date registered;

	private String customerNr;

	private String customerName;

	private String deliveryAddress;

	private String postalCode;

	private String postOffice;

	private BigDecimal ownProductionCost;

	private BigDecimal transportCost;

	private BigDecimal assemblyCost;

	private BigDecimal yesLines;

	private BigDecimal contributionMargin;

	private String countyName;

	private String salesman;

	private Integer orderDate;
	private String segmentno;
	private BigDecimal lengde;
	private BigDecimal bredde;
	private BigDecimal areal;

	public SalesDataSnapshot() {
		super();
	}

	public SalesDataSnapshot(final Integer aSnapshotId, final Integer aSaleId, final String aOrderNr,
			final Integer aProbability,
			// final Integer aGroupIdx,
			final Integer aProductAreaNr, final Date aSaledate, final Date isRegistered, final String aCustomerNr,
			final String aCustomerName, final String aDeliveryAddress, final String aPostalCode,
			final String aPostOffice, final BigDecimal aOwnProductionCost, final BigDecimal aTransportCost,
			final BigDecimal aAssemblyCost, final BigDecimal sumYesLines, final BigDecimal aContributionMargin,
			final String aCountyName, final String aSalesman, final Integer aOrderDate, final String segmentno,
			BigDecimal lengde, BigDecimal bredde, BigDecimal areal) {
		super();
		this.snapshotId = aSnapshotId;
		this.saleId = aSaleId;
		this.orderNr = aOrderNr;
		this.probability = aProbability;
		// this.groupIdx = aGroupIdx;
		this.productAreaNr = aProductAreaNr;
		this.saledate = aSaledate;
		this.registered = isRegistered;
		this.customerNr = aCustomerNr;
		this.customerName = aCustomerName;
		this.deliveryAddress = aDeliveryAddress;
		this.postalCode = aPostalCode;
		this.postOffice = aPostOffice;
		this.ownProductionCost = aOwnProductionCost;
		this.transportCost = aTransportCost;
		this.assemblyCost = aAssemblyCost;
		this.yesLines = sumYesLines;
		this.contributionMargin = aContributionMargin;
		this.countyName = aCountyName;
		this.salesman = aSalesman;
		this.orderDate = aOrderDate;
		this.segmentno = segmentno;
		this.lengde = lengde;
		this.bredde = bredde;
		this.areal = areal;
	}

	public final BigDecimal getAssemblyCost() {
		return assemblyCost;
	}

	public final void setAssemblyCost(final BigDecimal aAssemblyCost) {
		this.assemblyCost = aAssemblyCost;
	}

	public final BigDecimal getContributionMargin() {
		return contributionMargin;
	}

	public final void setContributionMargin(final BigDecimal aContributionMargin) {
		this.contributionMargin = aContributionMargin;
	}

	public final String getCountyName() {
		return countyName;
	}

	public final void setCountyName(final String aCountyName) {
		this.countyName = aCountyName;
	}

	public final String getCustomerName() {
		return customerName;
	}

	public final void setCustomerName(final String aCustomerName) {
		this.customerName = aCustomerName;
	}

	public final String getCustomerNr() {
		return customerNr;
	}

	public final void setCustomerNr(final String aCustomerNr) {
		this.customerNr = aCustomerNr;
	}

	public final String getDeliveryAddress() {
		return deliveryAddress;
	}

	public final void setDeliveryAddress(final String aDeliveryAddress) {
		this.deliveryAddress = aDeliveryAddress;
	}

	/*
	 * public final Integer getGroupIdx() { return groupIdx; }
	 * 
	 * public final void setGroupIdx(final Integer aGroupIdx) { this.groupIdx =
	 * aGroupIdx; }
	 */
	public final Integer getProductAreaNr() {
		return productAreaNr;
	}

	public final void setProductAreaNr(final Integer aProductAreaNr) {
		this.productAreaNr = aProductAreaNr;
	}

	public final String getOrderNr() {
		return orderNr;
	}

	public final void setOrderNr(final String aOrderNr) {
		this.orderNr = aOrderNr;
	}

	public final BigDecimal getOwnProductionCost() {
		return ownProductionCost;
	}

	public final void setOwnProductionCost(final BigDecimal aOwnProductionCost) {
		this.ownProductionCost = aOwnProductionCost;
	}

	public final String getPostalCode() {
		return postalCode;
	}

	public final void setPostalCode(final String aPostalCode) {
		this.postalCode = aPostalCode;
	}

	public final String getPostOffice() {
		return postOffice;
	}

	public final void setPostOffice(final String aPostOffice) {
		this.postOffice = aPostOffice;
	}

	public final Integer getProbability() {
		return probability;
	}

	public final void setProbability(final Integer aProbability) {
		this.probability = aProbability;
	}

	public final Date getRegistered() {
		return registered;
	}

	public final void setRegistered(final Date isRegistered) {
		this.registered = isRegistered;
	}

	public final Date getSaledate() {
		return saledate;
	}

	public final void setSaledate(final Date aSaledate) {
		this.saledate = aSaledate;
	}

	public final Integer getSaleId() {
		return saleId;
	}

	public final void setSaleId(final Integer aSaleId) {
		this.saleId = aSaleId;
	}

	public final String getSalesman() {
		return salesman;
	}

	public final void setSalesman(final String aSalesman) {
		this.salesman = aSalesman;
	}

	public final Integer getSnapshotId() {
		return snapshotId;
	}

	public final void setSnapshotId(final Integer aSnapshotId) {
		this.snapshotId = aSnapshotId;
	}

	public final BigDecimal getTransportCost() {
		return transportCost;
	}

	public final void setTransportCost(final BigDecimal aTransportCost) {
		this.transportCost = aTransportCost;
	}

	public final BigDecimal getYesLines() {
		return yesLines;
	}

	public final void setYesLines(final BigDecimal sumYesLines) {
		this.yesLines = sumYesLines;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof SalesDataSnapshot)) {
			return false;
		}
		SalesDataSnapshot castOther = (SalesDataSnapshot) other;
		return new EqualsBuilder().append(snapshotId, castOther.snapshotId).isEquals();
	}

	@Override
	public final int hashCode() {
		return new HashCodeBuilder().append(snapshotId).toHashCode();
	}

	@Override
	public final String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("snapshotId", snapshotId)
				.append("saleId", saleId).append("orderNr", orderNr).append("probability", probability)
				.append("productAreaNr", productAreaNr).append("saledate", saledate).append("registered", registered)
				.append("customerNr", customerNr).append("customerName", customerName)
				.append("deliveryAddress", deliveryAddress).append("postalCode", postalCode)
				.append("postOffice", postOffice).append("ownProductionCost", ownProductionCost)
				.append("transportCost", transportCost).append("assemblyCost", assemblyCost)
				.append("yesLines", yesLines).append("contributionMargin", contributionMargin)
				.append("countyName", countyName).append("salesman", salesman).toString();
	}

	public final Integer getOrderDate() {
		return orderDate;
	}

	public final void setOrderDate(final Integer aOrderDate) {
		this.orderDate = aOrderDate;
	}

	public String getSegmentno() {
		return segmentno;
	}

	public void setSegmentno(String segmentno) {
		this.segmentno = segmentno;
	}

	public BigDecimal getLengde() {
		return lengde;
	}

	public void setLengde(BigDecimal lengde) {
		this.lengde = lengde;
	}

	public BigDecimal getBredde() {
		return bredde;
	}

	public void setBredde(BigDecimal bredde) {
		this.bredde = bredde;
	}

	public BigDecimal getAreal() {
		return areal;
	}

	public void setAreal(BigDecimal areal) {
		this.areal = areal;
	}
}
