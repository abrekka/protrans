package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderSegmentNoV {

	private BigDecimal assemblyCost;
	private BigDecimal ownProduction;
	private BigDecimal deliveryCost;
	private BigDecimal contributionMargin;
	private BigDecimal jaLinjer;
	private String postalCode;
	private String salesman;
	private Integer customerNr;
	private String customerFullName;
	private String orderNr;
	private BigDecimal contributionRate;
	private Date orderDate;
	private String productAreaNr;
	private Integer orderId;
	private Date agreementDate;
	private String constructionTypeName;
	private String productAreaName;
	private String segmentNo;
	private String countyName;
	private BigDecimal areal;

	public String getSegmentNo() {
		return segmentNo;
	}

	public void setSegmentNo(String segmentNo) {
		this.segmentNo = segmentNo;
	}

	public String getProductAreaName() {
		return productAreaName;
	}

	public void setProductAreaName(String productAreaName) {
		this.productAreaName = productAreaName;
	}

	public String getConstructionTypeName() {
		return constructionTypeName;
	}

	public void setConstructionTypeName(String constructionTypeName) {
		this.constructionTypeName = constructionTypeName;
	}

	public Date getAgreementDate() {
		return agreementDate;
	}

	public void setAgreementDate(Date agreementDate) {
		this.agreementDate = agreementDate;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getAssemblyCost() {
		return assemblyCost;
	}

	public void setAssemblyCost(BigDecimal assemblyCost) {
		this.assemblyCost = assemblyCost;
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

	public BigDecimal getContributionMargin() {
		return contributionMargin;
	}

	public void setContributionMargin(BigDecimal contributionMargin) {
		this.contributionMargin = contributionMargin;
	}

	public BigDecimal getJaLinjer() {
		return jaLinjer;
	}

	public void setJaLinjer(BigDecimal jaLinjer) {
		this.jaLinjer = jaLinjer;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public Integer getCustomerNr() {
		return customerNr;
	}

	public void setCustomerNr(Integer customerNr) {
		this.customerNr = customerNr;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getOrderNr() {
		return orderNr;
	}

	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	public BigDecimal getContributionRate() {
		return contributionRate;
	}

	public void setContributionRate(BigDecimal contributionRate) {
		this.contributionRate = contributionRate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getProductAreaNr() {
		return productAreaNr;
	}

	public void setProductAreaNr(String productAreaNr) {
		this.productAreaNr = productAreaNr;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public BigDecimal getAreal() {
		return areal;
	}
	public void setAreal(BigDecimal areal) {
		this.areal = areal;
	}
}

