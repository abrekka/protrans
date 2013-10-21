package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view NOKKEL_DRIFT_PROSJEKTERING_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelDriftProsjekteringV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private NokkelDriftProsjekteringVPK nokkelDriftProsjekteringVPK;

	/**
	 * 
	 */
	private Integer orderCount;

	/**
	 * 
	 */
	private Integer deviationCount;

	/**
	 * 
	 */
	private BigDecimal internalCost;

	/**
	 * 
	 */
	private BigDecimal customerCost;

	/**
	 * 
	 */
	public NokkelDriftProsjekteringV() {
		super();
	}

	/**
	 * @param nokkelDriftProsjekteringVPK
	 * @param orderCount
	 * @param deviationCount
	 * @param internalCost
	 * @param customerCost
	 */
	public NokkelDriftProsjekteringV(
			NokkelDriftProsjekteringVPK nokkelDriftProsjekteringVPK,
			Integer orderCount, Integer deviationCount,
			BigDecimal internalCost, BigDecimal customerCost) {
		super();
		this.nokkelDriftProsjekteringVPK = nokkelDriftProsjekteringVPK;
		this.orderCount = orderCount;
		this.deviationCount = deviationCount;
		this.internalCost = internalCost;
		this.customerCost = customerCost;
	}

	/**
	 * @return år
	 */
	public Integer getPacklistYear() {
		return nokkelDriftProsjekteringVPK.getPacklistYear();
	}

	/**
	 * @return uke
	 */
	public Integer getPacklistWeek() {
		return nokkelDriftProsjekteringVPK.getPacklistWeek();
	}

	/**
	 * @return kundekostnad
	 */
	public BigDecimal getCustomerCost() {
		return customerCost;
	}

	/**
	 * @param customerCost
	 */
	public void setCustomerCost(BigDecimal customerCost) {
		this.customerCost = customerCost;
	}

	/**
	 * @return antall avvik
	 */
	public Integer getDeviationCount() {
		return deviationCount;
	}

	/**
	 * @param deviationCount
	 */
	public void setDeviationCount(Integer deviationCount) {
		this.deviationCount = deviationCount;
	}

	/**
	 * @return intern kostnad
	 */
	public BigDecimal getInternalCost() {
		return internalCost;
	}

	/**
	 * @param internalCost
	 */
	public void setInternalCost(BigDecimal internalCost) {
		this.internalCost = internalCost;
	}

	/**
	 * @return nøkkel
	 */
	public NokkelDriftProsjekteringVPK getNokkelDriftProsjekteringVPK() {
		return nokkelDriftProsjekteringVPK;
	}

	/**
	 * @param nokkelDriftProsjekteringVPK
	 */
	public void setNokkelDriftProsjekteringVPK(
			NokkelDriftProsjekteringVPK nokkelDriftProsjekteringVPK) {
		this.nokkelDriftProsjekteringVPK = nokkelDriftProsjekteringVPK;
	}

	/**
	 * @return antall ordre
	 */
	public Integer getOrderCount() {
		return orderCount;
	}

	/**
	 * @param orderCount
	 */
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof NokkelDriftProsjekteringV))
			return false;
		NokkelDriftProsjekteringV castOther = (NokkelDriftProsjekteringV) other;
		return new EqualsBuilder().append(nokkelDriftProsjekteringVPK,
				castOther.nokkelDriftProsjekteringVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nokkelDriftProsjekteringVPK)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"nokkelDriftProsjekteringVPK", nokkelDriftProsjekteringVPK)
				.append("orderCount", orderCount).append("deviationCount",
						deviationCount).append("internalCost", internalCost)
				.append("customerCost", customerCost).toString();
	}

}
