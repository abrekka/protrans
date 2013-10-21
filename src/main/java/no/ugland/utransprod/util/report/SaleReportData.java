package no.ugland.utransprod.util.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SaleReportData implements Comparable<SaleReportData> {
	private String type;

	private String countyName;

	private String salesman;

	private Integer customerNr;

	private String customerName;

	private String orderNr;

	private BigDecimal ownProductionCost;

	private BigDecimal transportCost;

	private BigDecimal assemblyCost;

	private BigDecimal yesLines;

	private BigDecimal db;

	private BigDecimal dg;

	private Date salesDate;
	private Date registered;
	private Date orderDate;
	private ProbabilityEnum probabilityEnum;

	private BigDecimal contributionMargin;

	public SaleReportData() {
		super();
	}

	public SaleReportData(int probability, String countyName, String salesman,
			Integer customerNr, String customerName, String orderNr,
			BigDecimal ownProductionCost, BigDecimal transportCost,
			BigDecimal assemblyCost, BigDecimal yesLines, BigDecimal db,
			Integer aSalesDate, Integer aRegsiteredDate, Integer aOrderDate,
			BigDecimal aContributionMargin) {
		super();
		this.contributionMargin = aContributionMargin;
		// this.type = type;
		this.type = ProbabilityEnum.getTypeNameByProbability(probability);
		this.countyName = countyName;
		this.salesman = salesman;
		this.customerNr = customerNr;
		this.customerName = customerName;
		this.orderNr = orderNr;
		this.ownProductionCost = ownProductionCost;
		this.transportCost = transportCost;
		this.assemblyCost = assemblyCost;
		this.yesLines = yesLines;
		this.db = db;
		if (ownProductionCost != null && ownProductionCost.intValue() != 0) {
			this.dg = db.divide(ownProductionCost, 2, RoundingMode.HALF_EVEN);
		}

		if (aSalesDate != null) {
			this.salesDate = Util.convertIntToDate(aSalesDate);
		}
		if (aRegsiteredDate != null) {
			this.registered = Util.convertIntToDate(aRegsiteredDate);
		}
		if (aOrderDate != null) {
			this.orderDate = Util.convertIntToDate(aOrderDate);
		}
		this.probabilityEnum = ProbabilityEnum.getProbabilityEnum(probability);
	}

	public SaleReportData(String type, String countyName, String salesman,
			Integer customerNr, String customerName, String orderNr,
			BigDecimal ownProductionCost, BigDecimal transportCost,
			BigDecimal assemblyCost, BigDecimal yesLines, BigDecimal db,
			BigDecimal dg, Date aSalesDate) {
		super();
		this.type = type;
		this.countyName = countyName;
		this.salesman = salesman;
		this.customerNr = customerNr;
		this.customerName = customerName;
		this.orderNr = orderNr;
		this.ownProductionCost = ownProductionCost;
		this.transportCost = transportCost;
		this.assemblyCost = assemblyCost;
		this.yesLines = yesLines;
		this.db = db;
		this.dg = dg;
		this.salesDate = aSalesDate;
	}

	public BigDecimal getAssemblyCost() {
		return assemblyCost != null ? assemblyCost : BigDecimal.ZERO;
	}

	public void setAssemblyCost(BigDecimal assemblyCost) {
		this.assemblyCost = assemblyCost;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getCustomerNr() {
		return customerNr;
	}

	public void setCustomerNr(Integer customerNr) {
		this.customerNr = customerNr;
	}

	public BigDecimal getDb() {
		return db;
	}

	public void setDb(BigDecimal db) {
		this.db = db;
	}

	public BigDecimal getDg() {
		// return dg != null ? BigDecimal.valueOf(1).subtract(dg) : null;
		return dg;
	}

	public void setDg(BigDecimal dg) {
		this.dg = dg;
	}

	public String getOrderNr() {
		return orderNr;
	}

	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	public BigDecimal getOwnProductionCost() {
		return ownProductionCost != null ? ownProductionCost : BigDecimal.ZERO;
	}

	public void setOwnProductionCost(BigDecimal ownProductionCost) {
		this.ownProductionCost = ownProductionCost;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public BigDecimal getTransportCost() {
		return transportCost != null ? transportCost : BigDecimal.ZERO;
	}

	public void setTransportCost(BigDecimal transportCost) {
		this.transportCost = transportCost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getYesLines() {
		return yesLines != null ? yesLines : BigDecimal.ZERO;
	}

	public void setYesLines(BigDecimal yesLines) {
		this.yesLines = yesLines;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public String getSalesDateString() {
		if (salesDate != null) {
			return Util.SHORT_DATE_FORMAT.format(salesDate);
		}
		return "";
	}

	public String getSalesWeek() {
		Integer week = Util.getWeekPart(salesDate);
		return week != null ? String.valueOf(week) : null;
	}

	public int compareTo(final SaleReportData other) {
		return new CompareToBuilder().append(type, other.type).toComparison();
	}

	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(Date registered) {
		this.registered = registered;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public ProbabilityEnum getProbabilityEnum() {
		return probabilityEnum;
	}

	public void setProbabilityEnum(ProbabilityEnum probabilityEnum) {
		this.probabilityEnum = probabilityEnum;
		this.type = probabilityEnum.getTypeName();
	}

	public SaleReportData cloneSaleData() {
		SaleReportData tmp = new SaleReportData();
		tmp.setAssemblyCost(this.getAssemblyCost());
		tmp.setCountyName(this.getCountyName());
		tmp.setCustomerName(this.getCustomerName());
		tmp.setCustomerNr(this.getCustomerNr());
		tmp.setDb(this.getDb());
		tmp.setDg(this.getDg());
		tmp.setOrderDate(this.getOrderDate());
		tmp.setOrderNr(this.getOrderNr());
		tmp.setOwnProductionCost(this.getOwnProductionCost());
		tmp.setProbabilityEnum(this.getProbabilityEnum());
		tmp.setRegistered(this.getRegistered());
		tmp.setSalesDate(this.getSalesDate());
		tmp.setSalesman(this.getSalesman());
		tmp.setTransportCost(this.getTransportCost());
		tmp.setType(this.getType());
		tmp.setYesLines(this.getYesLines());

		return tmp;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SaleReportData))
			return false;
		SaleReportData castOther = (SaleReportData) other;
		return new EqualsBuilder().append(orderNr, castOther.orderNr).append(
				probabilityEnum, castOther.probabilityEnum).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderNr).append(probabilityEnum)
				.toHashCode();
	}

	public BigDecimal getContributionMargin() {
		return contributionMargin != null ? contributionMargin
				: BigDecimal.ZERO;
	}
}
