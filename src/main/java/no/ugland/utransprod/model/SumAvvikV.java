package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view SUM_AVVIK_V
 * 
 * @author atle.brekka
 * 
 */
public class SumAvvikV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private SumAvvikVPK sumAvvikVPK;

	/**
	 * 
	 */
	private Integer deviationCount = 0;

	/**
	 * 
	 */
	private BigDecimal internalCost;

	/**
	 * 
	 */
	private Integer registrationMonth;

	/**
	 * 
	 */
	private Integer closedCount = 0;

	/**
	 * 
	 */
	public SumAvvikV() {
		super();
	}

	/**
	 * @param sumAvvikVPK
	 * @param deviationCount
	 * @param internalCost
	 * @param registrationMonth
	 */
	public SumAvvikV(SumAvvikVPK sumAvvikVPK, Integer deviationCount,
			BigDecimal internalCost, Integer registrationMonth) {
		super();
		this.sumAvvikVPK = sumAvvikVPK;
		this.deviationCount = deviationCount;
		this.internalCost = internalCost;
		this.registrationMonth = registrationMonth;

	}

	/**
	 * @param year
	 * @param month
	 * @param deviationCount
	 * @param internalCost
	 * @param productArea
	 * @param jobFunctionName
	 */
	public SumAvvikV(Integer year, Integer month, Integer deviationCount,
			BigDecimal internalCost, String productArea, String jobFunctionName) {
		this(new SumAvvikVPK(productArea, jobFunctionName, year, null, null),
				deviationCount, internalCost, month);
	}

	/**
	 * @param year
	 * @param deviationCount
	 * @param internalCost
	 * @param productArea
	 * @param jobFunctionName
	 */
	public SumAvvikV(Integer year, Integer deviationCount,
			BigDecimal internalCost, String productArea, String jobFunctionName) {
		this(new SumAvvikVPK(productArea, jobFunctionName, year, null, null),
				deviationCount, internalCost, null);
	}

	/**
	 * @param year
	 * @param month
	 * @param deviationCount
	 * @param internalCost
	 * @param productArea
	 * @param jobFunctionName
	 * @param closed
	 */
	public SumAvvikV(Integer year, Integer month, Integer deviationCount,
			BigDecimal internalCost, String productArea,
			String jobFunctionName, Integer closed) {
		this(new SumAvvikVPK(productArea, jobFunctionName, year, null, closed),
				deviationCount, internalCost, month);
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
	 * @return kostnad
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
	 * @return funksjon
	 */
	public String getJobFunctionName() {
		return sumAvvikVPK.getJobFunctionName();
	}

	/**
	 * @return produktområde
	 */
	public String getProductArea() {
		return sumAvvikVPK.getProductArea();
	}

	/**
	 * @return registreringsmåned
	 */
	public Integer getRegistrationMonth() {
		return registrationMonth;
	}

	/**
	 * @param registrationMonth
	 */
	public void setRegistrationMonth(Integer registrationMonth) {
		this.registrationMonth = registrationMonth;
	}

	/**
	 * @return registreringsuke
	 */
	public Integer getRegistrationWeek() {
		return sumAvvikVPK.getRegistrationWeek();
	}

	/**
	 * @return registrereringsår
	 */
	public Integer getRegistrationYear() {
		return sumAvvikVPK.getRegistrationYear();
	}

	/**
	 * @return id
	 */
	public SumAvvikVPK getSumAvvikVPK() {
		return sumAvvikVPK;
	}

	/**
	 * @param sumAvvikVPK
	 */
	public void setSumAvvikVPK(SumAvvikVPK sumAvvikVPK) {
		this.sumAvvikVPK = sumAvvikVPK;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SumAvvikV))
			return false;
		SumAvvikV castOther = (SumAvvikV) other;
		return new EqualsBuilder().append(sumAvvikVPK, castOther.sumAvvikVPK)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sumAvvikVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"sumAvvikVPK", sumAvvikVPK).append("deviationCount",
				deviationCount).append("internalCost", internalCost).append(
				"registrationMonth", registrationMonth).toString();
	}

	/**
	 * @return 1 dersom lukket
	 */
	public Integer getClosed() {
		return sumAvvikVPK.getClosed();
	}

	/**
	 * @return antall lukket
	 */
	public Integer getClosedCount() {
		return closedCount;
	}

	/**
	 * @param closedCount
	 */
	public void setClosedCount(Integer closedCount) {
		this.closedCount = closedCount;
	}

	/**
	 * Legger til antall
	 * 
	 * @param added
	 */
	public void addDeviationCount(Integer added) {
		deviationCount = deviationCount + added;
	}

	/**
	 * Legger til antall lukkede
	 * 
	 * @param added
	 */
	public void addClosedCount(Integer added) {
		closedCount = closedCount + added;
	}
}
