package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkel for klasse NokkelProduksjonV
 * 
 * @author atle.brekka
 * 
 */
public class NokkelProduksjonVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer orderReadyYear;

	/**
	 * 
	 */
	private Integer orderReadyWeek;

	/**
	 * 
	 */
	private String productArea;
    private String productAreaGroupName;

	/**
	 * 
	 */
	public NokkelProduksjonVPK() {
		super();
	}

	/**
	 * @param orderReadyYear
	 * @param orderReadyWeek
	 * @param productArea
	 */
	public NokkelProduksjonVPK(Integer orderReadyYear, Integer orderReadyWeek,
			String productArea,String productAreaGroupName) {
		super();
		this.orderReadyYear = orderReadyYear;
		this.orderReadyWeek = orderReadyWeek;
		this.productArea = productArea;
        this.productAreaGroupName=productAreaGroupName;
	}

	/**
	 * @return ordre klar uke
	 */
	public Integer getOrderReadyWeek() {
		return orderReadyWeek;
	}

	/**
	 * @param orderReadyWeek
	 */
	public void setOrderReadyWeek(Integer orderReadyWeek) {
		this.orderReadyWeek = orderReadyWeek;
	}

	/**
	 * @return ordre klar år
	 */
	public Integer getOrderReadyYear() {
		return orderReadyYear;
	}

	/**
	 * @param orderReadyYear
	 */
	public void setOrderReadyYear(Integer orderReadyYear) {
		this.orderReadyYear = orderReadyYear;
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

    public String getProductAreaGroupName() {
        return productAreaGroupName;
    }

    public void setProductAreaGroupName(String productAreaGroupName) {
        this.productAreaGroupName = productAreaGroupName;
    }
}
