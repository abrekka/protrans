package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view NOKKEL_TRANSPORT_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelTransportV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private NokkelTransportVPK nokkelTransportVPK;

	/**
	 * 
	 */
	private Integer assemblied;

	/**
	 * 
	 */
	private Integer notAssemblied;

	/**
	 * 
	 */
	private BigDecimal garageCost;

	/**
	 * 
	 */
	private BigDecimal transportCost;

	/**
	 * 
	 */
	private BigDecimal assemblyCost;

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
	public NokkelTransportV() {
		super();
	}

	/**
	 * @param nokkelTransportVPK
	 * @param assemblied
	 * @param notAssemblied
	 * @param garageCost
	 * @param transportCost
	 * @param assemblyCost
	 * @param deviationCount
	 * @param internalCost
	 */
	public NokkelTransportV(NokkelTransportVPK nokkelTransportVPK,
			Integer assemblied, Integer notAssemblied, BigDecimal garageCost,
			BigDecimal transportCost, BigDecimal assemblyCost,
			Integer deviationCount, BigDecimal internalCost) {
		super();
		this.nokkelTransportVPK = nokkelTransportVPK;
		this.assemblied = assemblied;
		this.notAssemblied = notAssemblied;
		this.garageCost = garageCost;
		this.transportCost = transportCost;
		this.assemblyCost = assemblyCost;
		this.deviationCount = deviationCount;
		this.internalCost = internalCost;
	}

	/**
	 * @return år
	 */
	public Integer getOrderSentYear() {
		return nokkelTransportVPK.getOrderSentYear();
	}

	/**
	 * @return uke
	 */
	public Integer getOrderSentWeek() {
		return nokkelTransportVPK.getOrderSentWeek();
	}

	/**
	 * @return antall montert
	 */
	public Integer getAssemblied() {
		return assemblied;
	}

	/**
	 * @param assemblied
	 */
	public void setAssemblied(Integer assemblied) {
		this.assemblied = assemblied;
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
	public NokkelTransportVPK getNokkelTransportVPK() {
		return nokkelTransportVPK;
	}

	/**
	 * @param nokkelTransportVPK
	 */
	public void setNokkelTransportVPK(NokkelTransportVPK nokkelTransportVPK) {
		this.nokkelTransportVPK = nokkelTransportVPK;
	}

	/**
	 * @return antall ikke montert
	 */
	public Integer getNotAssemblied() {
		return notAssemblied;
	}

	/**
	 * @param notAssemblied
	 */
	public void setNotAssemblied(Integer notAssemblied) {
		this.notAssemblied = notAssemblied;
	}

	/**
	 * @return transportkostnad
	 */
	public BigDecimal getTransportCost() {
		return transportCost;
	}

	/**
	 * @param transportCost
	 */
	public void setTransportCost(BigDecimal transportCost) {
		this.transportCost = transportCost;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof NokkelTransportV))
			return false;
		NokkelTransportV castOther = (NokkelTransportV) other;
		return new EqualsBuilder().append(nokkelTransportVPK,
				castOther.nokkelTransportVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nokkelTransportVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"nokkelTransportVPK", nokkelTransportVPK).append("assemblied",
				assemblied).append("notAssemblied", notAssemblied).append(
				"garageCost", garageCost)
				.append("transportCost", transportCost).append("assemblyCost",
						assemblyCost).append("deviationCount", deviationCount)
				.append("internalCost", internalCost).toString();
	}

}
