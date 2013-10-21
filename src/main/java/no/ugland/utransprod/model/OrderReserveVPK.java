package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkelklasse for demenklasse for view ORDER_RESERVE_V
 * 
 * @author atle.brekka
 * 
 */
public class OrderReserveVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String productArea;

	/**
	 * 
	 */
	private String isPacklistReady;

	/**
	 * 
	 */
	public OrderReserveVPK() {
		super();
	}

	/**
	 * @param productArea
	 * @param isPacklistReady
	 */
	public OrderReserveVPK(String productArea, String isPacklistReady) {
		super();
		this.productArea = productArea;
		this.isPacklistReady = isPacklistReady;
	}

	/**
	 * @return Ja dersom pakkliste er klar
	 */
	public String getIsPacklistReady() {
		return isPacklistReady;
	}

	/**
	 * @param isPacklistReady
	 */
	public void setIsPacklistReady(String isPacklistReady) {
		this.isPacklistReady = isPacklistReady;
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
