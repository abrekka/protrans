package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkelklasse for domeneklasse for view NOKKEL_OKONOMI_V 
 * @author atle.brekka
 *
 */
public class NokkelOkonomiVPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer invoiceYear;

	/**
	 * 
	 */
	private Integer invoiceWeek;

	/**
	 * 
	 */
	private String productArea;

	/**
	 * 
	 */
	public NokkelOkonomiVPK() {
		super();
	}

	/**
	 * @param invoiceYear
	 * @param invoiceWeek
	 * @param productArea
	 */
	public NokkelOkonomiVPK(Integer invoiceYear, Integer invoiceWeek, String productArea) {
		super();
		this.invoiceYear = invoiceYear;
		this.invoiceWeek = invoiceWeek;
		this.productArea = productArea;
	}

	/**
	 * @return uke
	 */
	public Integer getInvoiceWeek() {
		return invoiceWeek;
	}

	/**
	 * @param invoiceWeek
	 */
	public void setInvoiceWeek(Integer invoiceWeek) {
		this.invoiceWeek = invoiceWeek;
	}

	/**
	 * @return år
	 */
	public Integer getInvoiceYear() {
		return invoiceYear;
	}

	/**
	 * @param invoiceYear
	 */
	public void setInvoiceYear(Integer invoiceYear) {
		this.invoiceYear = invoiceYear;
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
