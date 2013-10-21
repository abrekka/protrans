package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TakstoltegnerV extends BaseObject{
	private String orderNr;
	private Date trossReady;
	private String customerName;
	private String productAreaGroupName;
	private String trossDrawer;
	private BigDecimal costAmount;
	private Integer customerNr;
	private String postalCode;
	private BigDecimal takProsjektering;
	private BigDecimal maxTrossHeight;
	private Integer probability;
	private Date packlistReady;
	private String salesman;

	public String getOrderNr() {
		return orderNr;
	}

	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof TakstoltegnerV))
			return false;
		TakstoltegnerV castOther = (TakstoltegnerV) other;
		return new EqualsBuilder().append(orderNr, castOther.orderNr)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderNr).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("orderNr", orderNr).toString();
	}

	public Date getTrossReady() {
		return trossReady;
	}

	public void setTrossReady(Date trossReady) {
		this.trossReady = trossReady;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getProductAreaGroupName() {
		return productAreaGroupName;
	}

	public void setCustomerName(String name) {
		this.customerName = name;
	}

	public void setProductAreaGroupName(String productAreaGroupName) {
		this.productAreaGroupName = productAreaGroupName;
	}

	public String getTrossDrawer() {
		return trossDrawer;
	}

	public void setTrossDrawer(String trossDrawer) {
		this.trossDrawer = trossDrawer;
	}

	public BigDecimal getCostAmount() {
		return costAmount!=null?costAmount:BigDecimal.ZERO;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public String orderNr() {
		return orderNr;
	}

	public Integer getCustomerNr() {
		return customerNr;
	}

	public void setCustomerNr(Integer customerNr) {
		this.customerNr = customerNr;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public BigDecimal getTakProsjektering() {
		return takProsjektering;
	}

	public void setTakProsjektering(BigDecimal takProsjektering) {
		this.takProsjektering = takProsjektering;
	}

	public BigDecimal getMaxTrossHeight() {
		return maxTrossHeight;
	}

	public void setMaxTrossHeight(BigDecimal maxTrossHeight) {
		this.maxTrossHeight = maxTrossHeight;
	}

	public Integer getProbability() {
		return probability;
	}

	public void setProbability(Integer probability) {
		this.probability = probability;
	}

	public Date getPacklistReady() {
		return packlistReady;
	}

	public void setPacklistReady(Date packlistReady) {
		this.packlistReady = packlistReady;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	
}
