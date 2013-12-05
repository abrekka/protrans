package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SaleStatusV extends BaseObject {
    private Integer customerNr;
    private String customerName;
    private String orderNr;
    private BigDecimal amount;
    private Integer probability;
    private Date saledate;
    private Integer buildDate;
    private String saleStatus;
    private String salesman;
    private Integer deptno;

    public SaleStatusV() {
        super();
    }

    public SaleStatusV(Integer customerNr, String customerName, String orderNr, BigDecimal amount,
            Integer probability, Date saledate, Integer buildDate, String saleStatus, String salesman,
            Integer deptno) {
        super();
        this.customerNr = customerNr;
        this.customerName = customerName;
        this.orderNr = orderNr;
        this.amount = amount;
        this.probability = probability;
        this.saledate = saledate;
        this.buildDate = buildDate;
        this.saleStatus = saleStatus;
        this.salesman = salesman;
        this.deptno = deptno;
    }

    public Integer getCustomerNr() {
        return customerNr;
    }

    public void setCustomerNr(Integer customerNr) {
        this.customerNr = customerNr;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(String orderNr) {
        this.orderNr = orderNr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Date getSaledate() {
        return saledate;
    }

    public void setSaledate(Date saledate) {
        this.saledate = saledate;
    }

    public Integer getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Integer buildDate) {
        this.buildDate = buildDate;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof SaleStatusV))
            return false;
        SaleStatusV castOther = (SaleStatusV) other;
        return new EqualsBuilder().append(orderNr, castOther.orderNr).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(orderNr).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerNr", customerNr)
                .append("customerName", customerName).append("orderNr", orderNr).append("amount", amount)
                .append("probability", probability).append("saledate", saledate).append("buildDate",
                        buildDate).append("saleStatus", saleStatus).append("salesman", salesman).append(
                        "deptno", deptno).toString();
    }

    public String getSaleDateAsDate() {
        return Util.formatDate(saledate, Util.SHORT_DATE_FORMAT);
    }

    public Integer getSaleWeek() {
        return Util.getWeekPart(saledate);
    }

    public Integer getSaleYear() {
        return Util.getYearPart(saledate);
    }

    public String getProductArea() {
        return Util.getProductAreaFromProductAreaNr(deptno) != null ? Util.getProductAreaFromProductAreaNr(
                deptno).getProductArea() : "";
    }
    
    public String getBuildDateAsDate() {
        return Util.formatDate(Util.convertIntToDate(buildDate), Util.SHORT_DATE_FORMAT);
    }
}
