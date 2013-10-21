package no.ugland.utransprod.util.report;

import java.math.BigDecimal;
import java.util.Date;

import no.ugland.utransprod.util.Util;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class JiggReportData {

	private String orderInfo;
	private Date startDate;
	private Date produced;
	private BigDecimal ownProduction;
	private BigDecimal ownInternalProduction;
	private BigDecimal price;
	private BigDecimal ownProductionInfo;
	private String articleName;
	private BigDecimal ownInternalProductionInfo;

	public void setOrderInfo(String string) {
		orderInfo=string;
		
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setStartDate(Date actionStarted) {
		startDate=actionStarted;
		
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setProduced(Date produced) {
		this.produced=produced;
		
	}

	public Date getProduced() {
		return produced;
	}

	public void setOwnProduction(BigDecimal ownProduction) {
		this.ownProduction=ownProduction;
		
	}

	public BigDecimal getOwnProduction() {
		return ownProduction;
	}

	public void setOwnInternalProduction(BigDecimal ownInternalProduction) {
		this.ownInternalProduction=ownInternalProduction;
		
	}

	public BigDecimal getOwnInternalProduction() {
		return ownInternalProduction;
	}

	public void setPrice(BigDecimal price) {
		this.price=price;
		
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setOwnProductionInfo(BigDecimal ownProductionInfo) {
		this.ownProductionInfo=ownProductionInfo;
		
	}

	public BigDecimal getOwnInternalProductionInfo() {
		return ownProductionInfo;
	}

	public void setOwnInternalProductionInfo(BigDecimal ownInternalProductionInfo) {
		this.ownInternalProductionInfo = ownInternalProductionInfo;
	}

	public void setArticleName(String articleName) {
		this.articleName=articleName;
		
	}

	public String getArticleName() {
		return articleName;
	}

	public String getOwnProductionString() {
		
		return ownProductionInfo!=null?"("+Util.convertBigDecimalToString(ownProductionInfo)+")":Util.convertBigDecimalToString(ownProduction);
	}

	public BigDecimal getOwnOwnInternalProductionInfo() {
		return ownInternalProductionInfo;
	}

	public void setOwnOwnInternalProductionInfo(
			BigDecimal ownOwnInternalProductionInfo) {
		this.ownInternalProductionInfo = ownOwnInternalProductionInfo;
	}

	public String getOwnInternalProductionString() {
		return ownInternalProductionInfo!=null?"("+Util.convertBigDecimalToString(ownInternalProductionInfo)+")":Util.convertBigDecimalToString(ownInternalProduction);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof JiggReportData))
			return false;
		JiggReportData castOther = (JiggReportData) other;
		return new EqualsBuilder().append(orderInfo, castOther.orderInfo)
				.append(produced, castOther.produced).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderInfo).append(produced)
				.toHashCode();
	}


}
