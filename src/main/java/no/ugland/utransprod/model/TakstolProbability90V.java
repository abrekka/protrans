package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TakstolProbability90V extends BaseObject {
	private Integer saleId;
	private String number1;
	private BigDecimal amount;
	private Date saledate;
	private Integer userdefId;
	private Date registered;
	private Integer probability;
	private Integer contactId;
	private Integer productAreaNr;
	private String deliveryAddress;
	private String postalCode;
	private String postoffice;
	private String salesMan;
	private String telephoneNrSite;
	private String customerNr;
	private String customerName;
	private BigDecimal ownProduction;
	private BigDecimal deliveryCost;

	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public String getNumber1() {
		return number1;
	}

	public void setNumber1(String number1) {
		this.number1 = number1;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getSaledate() {
		return saledate;
	}

	public void setSaledate(Date saledate) {
		this.saledate = saledate;
	}

	public Integer getUserdefId() {
		return userdefId;
	}

	public void setUserdefId(Integer userdefId) {
		this.userdefId = userdefId;
	}

	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(Date registered) {
		this.registered = registered;
	}

	public Integer getProbability() {
		return probability;
	}

	public void setProbability(Integer probability) {
		this.probability = probability;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public Integer getProductAreaNr() {
		return productAreaNr;
	}

	public void setProductAreaNr(Integer productAreaNr) {
		this.productAreaNr = productAreaNr;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostoffice() {
		return postoffice;
	}

	public void setPostoffice(String postoffice) {
		this.postoffice = postoffice;
	}

	public String getSalesMan() {
		return salesMan;
	}

	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}

	public String getTelephoneNrSite() {
		return telephoneNrSite;
	}

	public void setTelephoneNrSite(String telephoneNrSite) {
		this.telephoneNrSite = telephoneNrSite;
	}

	public String getCustomerNr() {
		return customerNr;
	}

	public void setCustomerNr(String customerNr) {
		this.customerNr = customerNr;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getOwnProduction() {
		return ownProduction;
	}

	public void setOwnProduction(BigDecimal ownProduction) {
		this.ownProduction = ownProduction;
	}

	public BigDecimal getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(BigDecimal deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof TakstolProbability90V))
			return false;
		TakstolProbability90V castOther = (TakstolProbability90V) other;
		return new EqualsBuilder().append(saleId, castOther.saleId).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(saleId).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("saleId", saleId).append(
				"number1", number1).append("amount", amount).append("saledate",
				saledate).append("userdefId", userdefId).append("registered",
				registered).append("probability", probability).append(
				"contactId", contactId).append("productAreaNr", productAreaNr)
				.append("deliveryAddress", deliveryAddress).append(
						"postalCode", postalCode).append("postoffice",
						postoffice).append("salesMan", salesMan).append(
						"telephoneNrSite", telephoneNrSite).append(
						"customerNr", customerNr).append("customerName",
						customerName).append("ownProduction", ownProduction)
				.append("deliveryCost", deliveryCost).toString();
	}

}
