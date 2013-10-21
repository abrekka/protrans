package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view NOKKEL_MONTERING_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelMonteringV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private NokkelMonteringVPK nokkelMonteringVPK;

	/**
	 * 
	 */
	private Integer orderCount;

	/**
	 * 
	 */
	private BigDecimal deliveryCost;

	/**
	 * 
	 */
	private BigDecimal assemblyCost;

	/**
	 * 
	 */
	private BigDecimal garageCost;

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
	public NokkelMonteringV() {
		super();
	}

	/**
	 * @param nokkelMonteringVPK
	 * @param orderCount
	 * @param devliveryCost
	 * @param assemblyCost
	 * @param garageCost
	 * @param deviationCount
	 * @param internalCost
	 */
	public NokkelMonteringV(NokkelMonteringVPK nokkelMonteringVPK,
			Integer orderCount, BigDecimal devliveryCost,
			BigDecimal assemblyCost, BigDecimal garageCost,
			Integer deviationCount, BigDecimal internalCost) {
		super();
		this.nokkelMonteringVPK = nokkelMonteringVPK;
		this.orderCount = orderCount;
		this.deliveryCost = devliveryCost;
		this.assemblyCost = assemblyCost;
		this.garageCost = garageCost;
		this.deviationCount = deviationCount;
		this.internalCost = internalCost;
	}

	/**
	 * @return år
	 */
	public Integer getAssembliedYear() {
		return nokkelMonteringVPK.getAssembliedYear();
	}

	/**
	 * @return uke
	 */
	public Integer getAssembliedWeek() {
		return nokkelMonteringVPK.getAssembliedWeek();
	}

	/**
	 * @return monteringskostnad
	 */
	public BigDecimal getAssemblyCost() {
		return assemblyCost;
	}

	/**
	 * @param assemblyCost
	 */
	public void setAssemblyCost(BigDecimal assemblyCost) {
		this.assemblyCost = assemblyCost;
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
	 * @return transportkostnad
	 */
	public BigDecimal getDeliveryCost() {
		return deliveryCost;
	}

	/**
	 * @param devliveryCost
	 */
	public void setDeliveryCost(BigDecimal devliveryCost) {
		this.deliveryCost = devliveryCost;
	}

	/**
	 * @return garasjekostnad
	 */
	public BigDecimal getGarageCost() {
		return garageCost;
	}

	/**
	 * @param garageCost
	 */
	public void setGarageCost(BigDecimal garageCost) {
		this.garageCost = garageCost;
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
	public NokkelMonteringVPK getNokkelMonteringVPK() {
		return nokkelMonteringVPK;
	}

	/**
	 * @param nokkelMonteringVPK
	 */
	public void setNokkelMonteringVPK(NokkelMonteringVPK nokkelMonteringVPK) {
		this.nokkelMonteringVPK = nokkelMonteringVPK;
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
		if (!(other instanceof NokkelMonteringV))
			return false;
		NokkelMonteringV castOther = (NokkelMonteringV) other;
		return new EqualsBuilder().append(nokkelMonteringVPK,
				castOther.nokkelMonteringVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nokkelMonteringVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"nokkelMonteringVPK", nokkelMonteringVPK).append("orderCount",
				orderCount).append("devliveryCost", deliveryCost).append(
				"assemblyCost", assemblyCost).append("garageCost", garageCost)
				.append("deviationCount", deviationCount).append(
						"internalCost", internalCost).toString();
	}

}
