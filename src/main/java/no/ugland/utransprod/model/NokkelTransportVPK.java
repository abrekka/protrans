package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkelklasse for domeneklasse for view NOKKEL_TRANSPORT_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelTransportVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer orderSentYear;

	/**
	 * 
	 */
	private Integer orderSentWeek;

	/**
	 * 
	 */
	private String productArea;

	/**
	 * 
	 */
	public NokkelTransportVPK() {
		super();
	}

	/**
	 * @param orderSentYear
	 * @param orderSentWeek
	 * @param productArea
	 */
	public NokkelTransportVPK(Integer orderSentYear, Integer orderSentWeek,
			String productArea) {
		super();
		this.orderSentYear = orderSentYear;
		this.orderSentWeek = orderSentWeek;
		this.productArea = productArea;
	}

	/**
	 * @return uke
	 */
	public Integer getOrderSentWeek() {
		return orderSentWeek;
	}

	/**
	 * @param orderSentWeek
	 */
	public void setOrderSentWeek(Integer orderSentWeek) {
		this.orderSentWeek = orderSentWeek;
	}

	/**
	 * @return år
	 */
	public Integer getOrderSentYear() {
		return orderSentYear;
	}

	/**
	 * @param orderSentYear
	 */
	public void setOrderSentYear(Integer orderSentYear) {
		this.orderSentYear = orderSentYear;
	}

	/**
	 * @return produktområde
	 */
	public String getProductArea() {
		return productArea;
	}

	/**
	 * @param productArea
	 */
	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}

}
