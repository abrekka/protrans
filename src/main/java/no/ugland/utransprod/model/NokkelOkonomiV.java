package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view NOKKEL_OKONOMI_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelOkonomiV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private NokkelOkonomiVPK nokkelOkonomiVPK;

	/**
	 * 
	 */
	private Integer orderCount;

	/**
	 * 
	 */
	private BigDecimal invoicedAmount;

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
	public NokkelOkonomiV() {
		super();
	}

	/**
	 * @param nokkelOkonomiVPK
	 * @param orderCount
	 * @param invoicedAmount
	 * @param deviationCount
	 * @param internalCost
	 */
	public NokkelOkonomiV(NokkelOkonomiVPK nokkelOkonomiVPK,
			Integer orderCount, BigDecimal invoicedAmount,
			Integer deviationCount, BigDecimal internalCost) {
		super();
		this.nokkelOkonomiVPK = nokkelOkonomiVPK;
		this.orderCount = orderCount;
		this.invoicedAmount = invoicedAmount;
		this.deviationCount = deviationCount;
		this.internalCost = internalCost;
	}

	/**
	 * @return år
	 */
	public Integer getInvoiceYear() {
		return nokkelOkonomiVPK.getInvoiceYear();
	}

	/**
	 * @return uke
	 */
	public Integer getInvoiceWeek() {
		return nokkelOkonomiVPK.getInvoiceWeek();
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
	 * @return fakturert beløp
	 */
	public BigDecimal getInvoicedAmount() {
		return invoicedAmount;
	}

	/**
	 * @param invoicedAmount
	 */
	public void setInvoicedAmount(BigDecimal invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	/**
	 * @return nøkkel
	 */
	public NokkelOkonomiVPK getNokkelOkonomiVPK() {
		return nokkelOkonomiVPK;
	}

	/**
	 * @param nokkelOkonomiVPK
	 */
	public void setNokkelOkonomiVPK(NokkelOkonomiVPK nokkelOkonomiVPK) {
		this.nokkelOkonomiVPK = nokkelOkonomiVPK;
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
		if (!(other instanceof NokkelOkonomiV))
			return false;
		NokkelOkonomiV castOther = (NokkelOkonomiV) other;
		return new EqualsBuilder().append(nokkelOkonomiVPK,
				castOther.nokkelOkonomiVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nokkelOkonomiVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"nokkelOkonomiVPK", nokkelOkonomiVPK).append("orderCount",
				orderCount).append("invoicedAmount", invoicedAmount).append(
				"deviationCount", deviationCount).append("internalCost",
				internalCost).toString();
	}

}
