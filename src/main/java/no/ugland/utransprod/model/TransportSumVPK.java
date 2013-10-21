package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkelklasse for domeneklasse for view TRANSPORT_SUM_V
 * 
 * @author atle.brekka
 * 
 */
public class TransportSumVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer transportYear;

	/**
	 * 
	 */
	/**
	 * 
	 */
	private Integer transportWeek;

	/**
	 * 
	 */
	private String productAreaGroupName;

	/**
	 * 
	 */
	public TransportSumVPK() {
		super();
	}

	/**
	 * @param transportYear
	 * @param transportWeek
	 * @param productAreaGroupName
	 */
	public TransportSumVPK(Integer transportYear, Integer transportWeek,
			String productAreaGroupName) {
		super();
		this.transportYear = transportYear;
		this.transportWeek = transportWeek;
		this.productAreaGroupName = productAreaGroupName;
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
	 * @return transportuke
	 */
	public Integer getTransportWeek() {
		return transportWeek;
	}

	/**
	 * @param transportWeek
	 */
	public void setTransportWeek(Integer transportWeek) {
		this.transportWeek = transportWeek;
	}

	/**
	 * @return transportår
	 */
	public Integer getTransportYear() {
		return transportYear;
	}

	/**
	 * @param transportYear
	 */
	public void setTransportYear(Integer transportYear) {
		this.transportYear = transportYear;
	}
}
