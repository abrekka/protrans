package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view SUM_ORDER_READY_V
 * 
 * @author atle.brekka
 * 
 */
public class SumOrderReadyV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private SumOrderReadyVPK sumOrderReadyVPK;

	/**
	 * 
	 */
	private BigDecimal packageSum;

	/**
	 * 
	 */
	private Integer orderReadyWeek;

	/**
	 * 
	 */
	private Integer orderReadyYear;

	/**
	 * 
	 */
	private String productAreaGroupName;

	/**
	 * 
	 */
	public SumOrderReadyV() {
		super();
	}

	/**
	 * @param sumOrderReadyVPK
	 * @param packageSum
	 * @param orderReadyWeek
	 * @param orderReadyYear
	 * @param productAreaGroupName
	 */
	public SumOrderReadyV(SumOrderReadyVPK sumOrderReadyVPK,
			BigDecimal packageSum, Integer orderReadyWeek,
			Integer orderReadyYear, String productAreaGroupName) {
		super();
		this.sumOrderReadyVPK = sumOrderReadyVPK;
		this.packageSum = packageSum;
		this.orderReadyWeek = orderReadyWeek;
		this.orderReadyYear = orderReadyYear;
		this.productAreaGroupName = productAreaGroupName;
	}

	/**
	 * @return nøkkel
	 */
	public SumOrderReadyVPK getSumOrderReadyVPK() {
		return sumOrderReadyVPK;
	}

	/**
	 * @param sumOrderReadyVPK
	 */
	public void setSumOrderReadyVPK(SumOrderReadyVPK sumOrderReadyVPK) {
		this.sumOrderReadyVPK = sumOrderReadyVPK;
	}

	/**
	 * @return dato for ordre klar
	 */
	public String getOrderReady() {
		return sumOrderReadyVPK.getOrderReady();
	}

	/**
	 * @return pakkebeløp
	 */
	public BigDecimal getPackageSum() {
		return packageSum;
	}

	/**
	 * @param packageSum
	 */
	public void setPackageSum(BigDecimal packageSum) {
		this.packageSum = packageSum;
	}

	/**
	 * @return ordre klar uke
	 */
	public Integer getOrderReadyWeek() {
		return orderReadyWeek;
	}

	/**
	 * @param orderReadyWeek
	 */
	public void setOrderReadyWeek(Integer orderReadyWeek) {
		this.orderReadyWeek = orderReadyWeek;
	}

	/**
	 * @return ordre klar år
	 */
	public Integer getOrderReadyYear() {
		return orderReadyYear;
	}

	/**
	 * @param orderReadyYear
	 */
	public void setOrderReadyYear(Integer orderReadyYear) {
		this.orderReadyYear = orderReadyYear;
	}

	/**
	 * @return produktområdenavn
	 */
	public String getProductArea() {
		return sumOrderReadyVPK.getProductArea();
	}

	/**
	 * @return produktområdegruppenavn
	 */
	public String getProductAreaGroupName() {
		return productAreaGroupName;
	}

	/**
	 * @param productAreaGroupName
	 */
	public void setProductAreaGroupName(String productAreaGroupName) {
		this.productAreaGroupName = productAreaGroupName;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SumOrderReadyV))
			return false;
		SumOrderReadyV castOther = (SumOrderReadyV) other;
		return new EqualsBuilder().append(sumOrderReadyVPK,
				castOther.sumOrderReadyVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sumOrderReadyVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"sumOrderReadyVPK", sumOrderReadyVPK).append("packageSum",
				packageSum).append("orderReadyWeek", orderReadyWeek).append(
				"orderReadyYear", orderReadyYear).append(
				"productAreaGroupName", productAreaGroupName).toString();
	}

}
