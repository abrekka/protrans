package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkel for domeneklasse OrderPacklistReadyV
 * 
 * @author atle.brekka
 * 
 */
public class OrderPacklistReadyVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer packlistYear;

	/**
	 * 
	 */
	private Integer packlistWeek;

	/**
	 * 
	 */
	private String productArea;

	/**
	 * 
	 */
	public OrderPacklistReadyVPK() {
		super();
	}

	/**
	 * @param packlistYear
	 * @param packlistWeek
	 * @param productArea
	 */
	public OrderPacklistReadyVPK(Integer packlistYear, Integer packlistWeek,
			String productArea) {
		super();
		this.packlistYear = packlistYear;
		this.packlistWeek = packlistWeek;
		this.productArea = productArea;
	}

	/**
	 * @return uke
	 */
	public Integer getPacklistWeek() {
		return packlistWeek;
	}

	/**
	 * @param packlistWeek
	 */
	public void setPacklistWeek(Integer packlistWeek) {
		this.packlistWeek = packlistWeek;
	}

	/**
	 * @return år
	 */
	public Integer getPacklistYear() {
		return packlistYear;
	}

	/**
	 * @param packlistYear
	 */
	public void setPacklistYear(Integer packlistYear) {
		this.packlistYear = packlistYear;
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
