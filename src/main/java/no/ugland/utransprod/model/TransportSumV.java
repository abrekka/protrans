package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view TRANSPORT_SUM_V
 * 
 * @author atle.brekka
 * 
 */
public class TransportSumV extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer orderCount;

	private BigDecimal garageCost;

	private TransportSumVPK transportSumVPK;
	public static final TransportSumV UNKNOWN = new TransportSumV() {

        @Override
        public BigDecimal getGarageCost() {
            return BigDecimal.ZERO;
        }

        @Override
        public Integer getOrderCount() {
            return Integer.valueOf(0);
        }
	    
	};

	public TransportSumV() {
		super();
	}

	/**
	 * @param orderCount
	 * @param garageCost
	 * @param transportYear
	 * @param transportWeek
	 */
	public TransportSumV(Integer orderCount, BigDecimal garageCost,
			Integer transportYear, Integer transportWeek) {
		this(orderCount, garageCost, new TransportSumVPK(transportYear,
				transportWeek, null));
	}
	public TransportSumV(Long orderCount, BigDecimal garageCost,
			Integer transportYear, Integer transportWeek) {
		this(orderCount.intValue(), garageCost, new TransportSumVPK(transportYear,
				transportWeek, null));
	}

	/**
	 * @param orderCount
	 * @param garageCost
	 * @param transportSumVPK
	 */
	public TransportSumV(Integer orderCount, BigDecimal garageCost,
			TransportSumVPK transportSumVPK) {
		super();
		this.orderCount = orderCount;
		this.garageCost = garageCost;
		this.transportSumVPK = transportSumVPK;
	}
	public TransportSumV(Long orderCount, BigDecimal garageCost,
			TransportSumVPK transportSumVPK) {
		this(orderCount.intValue(), garageCost, transportSumVPK);
	}

	/**
	 * @return nøkkel
	 */
	public TransportSumVPK getTransportSumVPK() {
		return transportSumVPK;
	}

	/**
	 * @param transportSumVPK
	 */
	public void setTransportSumVPK(TransportSumVPK transportSumVPK) {
		this.transportSumVPK = transportSumVPK;
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
	 * @return transportuke
	 */
	public Integer getTransportWeek() {
		return transportSumVPK.getTransportWeek();
	}

	/**
	 * @return transportår
	 */
	public Integer getTransportYear() {
		return transportSumVPK.getTransportYear();
	}

	/**
	 * @return produktområdegruppenavn
	 */
	public String getProductAreaGroupName() {
		return transportSumVPK.getProductAreaGroupName();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof TransportSumV))
			return false;
		TransportSumV castOther = (TransportSumV) other;
		return new EqualsBuilder().append(transportSumVPK,
				castOther.transportSumVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(transportSumVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"orderCount", orderCount).append("garageCost", garageCost)
				.append("transportSumVPK", transportSumVPK).toString();
	}

}
