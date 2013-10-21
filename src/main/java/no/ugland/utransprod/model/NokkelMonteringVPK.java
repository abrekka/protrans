package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkelklasse for domeneklasse for view NOKKEL_MONTERING_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelMonteringVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer assembliedYear;

	/**
	 * 
	 */
	private Integer assembliedWeek;

	/**
	 * 
	 */
	private String productArea;

	/**
	 * 
	 */
	public NokkelMonteringVPK() {
		super();
	}

	/**
	 * @param assembliedYear
	 * @param assembliedWeek
	 * @param productArea
	 */
	public NokkelMonteringVPK(Integer assembliedYear, Integer assembliedWeek,
			String productArea) {
		super();
		this.assembliedYear = assembliedYear;
		this.assembliedWeek = assembliedWeek;
		this.productArea = productArea;
	}

	/**
	 * @return uke
	 */
	public Integer getAssembliedWeek() {
		return assembliedWeek;
	}

	/**
	 * @param assembliedWeek
	 */
	public void setAssembliedWeek(Integer assembliedWeek) {
		this.assembliedWeek = assembliedWeek;
	}

	/**
	 * @return år
	 */
	public Integer getAssembliedYear() {
		return assembliedYear;
	}

	/**
	 * @param assembliedYear
	 */
	public void setAssembliedYear(Integer assembliedYear) {
		this.assembliedYear = assembliedYear;
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
