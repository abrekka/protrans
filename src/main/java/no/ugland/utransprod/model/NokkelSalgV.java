package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view NOKKEL_SALG_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelSalgV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private NokkelSalgVPK nokkelSalgVPK;

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
	public NokkelSalgV() {
		super();
	}

	/**
	 * @param nokkelSalgVPK
	 * @param orderCount
	 * @param deviationCount
	 * @param internalCost
	 */
	public NokkelSalgV(NokkelSalgVPK nokkelSalgVPK, Integer orderCount,
			Integer deviationCount, BigDecimal internalCost) {
		super();
		this.nokkelSalgVPK = nokkelSalgVPK;
		this.orderCount = orderCount;
		this.deviationCount = deviationCount;
		this.internalCost = internalCost;
	}

	/**
	 * @return år
	 */
	public Integer getAgreementYear() {
		return nokkelSalgVPK.getAgreementYear();
	}

	/**
	 * @return uke
	 */
	public Integer getAgreementWeek() {
		return nokkelSalgVPK.getAgreementWeek();
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
	public NokkelSalgVPK getNokkelSalgVPK() {
		return nokkelSalgVPK;
	}

	/**
	 * @param nokkelSalgVPK
	 */
	public void setNokkelSalgVPK(NokkelSalgVPK nokkelSalgVPK) {
		this.nokkelSalgVPK = nokkelSalgVPK;
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
		if (!(other instanceof NokkelSalgV))
			return false;
		NokkelSalgV castOther = (NokkelSalgV) other;
		return new EqualsBuilder().append(nokkelSalgVPK,
				castOther.nokkelSalgVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nokkelSalgVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"nokkelSalgVPK", nokkelSalgVPK)
				.append("orderCount", orderCount).append("deviationCount",
						deviationCount).append("internalCost", internalCost)
				.toString();
	}
}
