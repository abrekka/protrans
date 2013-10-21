package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkelklasse for domeneklasse for view NOKKEL_SALG_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelSalgVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer agreementYear;

	/**
	 * 
	 */
	private Integer agreementWeek;

	/**
	 * 
	 */
	private String productArea;

	/**
	 * 
	 */
	public NokkelSalgVPK() {
		super();
	}

	/**
	 * @param agreementYear
	 * @param agreementWeek
	 * @param productArea
	 */
	public NokkelSalgVPK(Integer agreementYear, Integer agreementWeek,
			String productArea) {
		super();
		this.agreementYear = agreementYear;
		this.agreementWeek = agreementWeek;
		this.productArea = productArea;
	}

	/**
	 * @return uke
	 */
	public Integer getAgreementWeek() {
		return agreementWeek;
	}

	/**
	 * @param agreementWeek
	 */
	public void setAgreementWeek(Integer agreementWeek) {
		this.agreementWeek = agreementWeek;
	}

	/**
	 * @return år
	 */
	public Integer getAgreementYear() {
		return agreementYear;
	}

	/**
	 * @param agreementYear
	 */
	public void setAgreementYear(Integer agreementYear) {
		this.agreementYear = agreementYear;
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
