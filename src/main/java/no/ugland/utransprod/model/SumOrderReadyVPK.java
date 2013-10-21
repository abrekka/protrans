package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkelklasse for domeneklasse for view SUM_ORDER_READY_V
 * 
 * @author atle.brekka
 * 
 */
public class SumOrderReadyVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String orderReady;

	/**
	 * 
	 */
	private String productArea;

	/**
	 * 
	 */
	public SumOrderReadyVPK() {
		super();
	}

	/**
	 * @param orderReady
	 * @param productArea
	 */
	public SumOrderReadyVPK(String orderReady, String productArea) {
		super();
		this.orderReady = orderReady;
		this.productArea = productArea;
	}

	/**
	 * @return ordre klar
	 */
	public String getOrderReady() {
		return orderReady;
	}

	/**
	 * @param orderReady
	 */
	public void setOrderReady(String orderReady) {
		this.orderReady = orderReady;
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
