package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SalesV extends BaseObject {
    private Integer saleId;

    private String orderNr;

    private Integer probability;

    private Integer productAreaNr;

    private Integer saledate;

    private Integer registered;

    private Integer customerNr;

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

    public SalesV() {
        super();
    }

    public SalesV(final Integer aSaleId, final String aOrderNr, final Integer aProbability,
            final Integer aProductAreaNr,
            final Integer aSaledate, final Integer isRegistered,
            final Integer aCustomerNr, final String aCustomerName, final String aDeliveryAddress,
            final String aPostalCode, final String aPostOffice, final BigDecimal aOwnProductionCost,
            final BigDecimal aTransportCost, final BigDecimal aAssemblyCost,
            final BigDecimal sumYesLines, final BigDecimal aContributionMargin,
            final String aCountyName, final String aSalesman,final Integer aOrderDate) {
        super();
        this.saleId = aSaleId;
        this.orderNr = aOrderNr;
        this.probability = aProbability;
        this.productAreaNr=aProductAreaNr;
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
        this.orderDate=aOrderDate;
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

    public final Integer getCustomerNr() {
        return customerNr;
    }

    public final void setCustomerNr(final Integer aCustomerNr) {
        this.customerNr = aCustomerNr;
    }

    public final String getDeliveryAddress() {
        return deliveryAddress;
    }

    public final void setDeliveryAddress(final String aDeliveryAddress) {
        this.deliveryAddress = aDeliveryAddress;
    }

    /*public final Integer getGroupIdx() {
        return groupIdx;
    }

    public final void setGroupIdx(final Integer aGroupIdx) {
        this.groupIdx = aGroupIdx;
    }*/
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

    public final Integer getRegistered() {
        return registered;
    }

    public final void setRegistered(final Integer isRegistered) {
        this.registered = isRegistered;
    }

    public final Integer getSaledate() {
        return saledate;
    }

    public final void setSaledate(final Integer aSaledate) {
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
        if (!(other instanceof SalesV)){
            return false;
        }
        SalesV castOther = (SalesV) other;
        return new EqualsBuilder().append(saleId, castOther.saleId).isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(saleId).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "saleId", saleId).append("orderNr", orderNr).append(
                "probability", probability).append("productAreaNr", productAreaNr)
                .append("saledate", saledate).append("registered", registered)
                .append("customerNr", customerNr).append("customerName",
                        customerName)
                .append("deliveryAddress", deliveryAddress).append(
                        "postalCode", postalCode).append("postOffice",
                        postOffice).append("ownProductionCost",
                        ownProductionCost).append("transportCost",
                        transportCost).append("assemblyCost", assemblyCost)
                .append("yesLines", yesLines).append("contributionMargin",
                        contributionMargin).append("countyName", countyName)
                .append("salesman", salesman).toString();
    }

    public final Integer getOrderDate() {
        return orderDate;
    }

    public final void setOrderDate(final Integer aOrderDate) {
        this.orderDate = aOrderDate;
    }
}
