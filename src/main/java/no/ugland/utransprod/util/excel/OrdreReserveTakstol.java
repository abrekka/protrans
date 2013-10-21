package no.ugland.utransprod.util.excel;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.CompareToBuilder;

public class OrdreReserveTakstol implements Comparable<OrdreReserveTakstol> {

	private String type;
	private String productAreaGroup;
	private String customerNr;
	private String customerName;
	private String orderNr;
	private BigDecimal ownProduction;
	private BigDecimal deliveryCost;
	private Date productionDate;
	private Integer transportYear;
	private Integer transportWeek;

	public void setType(String aType) {
		type=aType;
		
	}

	public String getType() {
		return type;
	}

	public void setProductAreaGroup(String productAreaGroupName) {
		this.productAreaGroup=productAreaGroupName;
		
	}

	public String getProductAreaGroup() {
		return productAreaGroup;
	}

	public void setCustomerNr(String customerNr) {
		this.customerNr=customerNr;
		
	}

	public String getCustomerNr() {
		return customerNr;
	}

	public void setCustomerName(String customerName) {
		this.customerName=customerName;
		
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setOrderNr(String orderNumber) {
		this.orderNr=orderNumber;
		
	}

	public String getOrderNr() {
		return orderNr;
	}

	public void setOwnProduction(BigDecimal ownProduction) {
		this.ownProduction=ownProduction;
		
	}

	public BigDecimal getOwnProduction() {
		return ownProduction;
	}

	public void setDeliveryCost(BigDecimal deliveryCost) {
		this.deliveryCost=deliveryCost;
		
	}

	public BigDecimal getDeliveryCost() {
		return deliveryCost;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OrdreReserveTakstol))
			return false;
		OrdreReserveTakstol castOther = (OrdreReserveTakstol) other;
		return new EqualsBuilder().append(orderNr, castOther.orderNr)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderNr).toHashCode();
	}

	public int compareTo(final OrdreReserveTakstol other) {
		return new CompareToBuilder().append(other.type,type ).append(other.productAreaGroup,productAreaGroup).toComparison();
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate=productionDate;
		
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setTransportYear(Integer transportYear) {
		this.transportYear=transportYear;
		
	}

	public Integer getTransportYear() {
		return transportYear;
	}

	public void setTransportWeek(Integer transportWeek) {
		this.transportWeek=transportWeek;
		
	}

	public Integer getTransportWeek() {
		return transportWeek;
	}

}
