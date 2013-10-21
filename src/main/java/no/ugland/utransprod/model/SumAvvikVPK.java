package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkelklasse for domeneklasse for view SUM_AVVIK_V
 * 
 * @author atle.brekka
 * 
 */
public class SumAvvikVPK implements Serializable {
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
	private String jobFunctionName;

	/**
	 * 
	 */
	private Integer registrationYear;

	/**
	 * 
	 */
	private Integer registrationWeek;

	/**
	 * 
	 */
	private Integer closed;

	/**
	 * 
	 */
	public SumAvvikVPK() {
		super();
	}

	/**
	 * @param productArea
	 * @param jobFunctionName
	 * @param registrationYear
	 * @param registrationWeek
	 * @param closed
	 */
	public SumAvvikVPK(String productArea, String jobFunctionName,
			Integer registrationYear, Integer registrationWeek, Integer closed) {
		super();
		this.productArea = productArea;
		this.jobFunctionName = jobFunctionName;
		this.registrationYear = registrationYear;
		this.registrationWeek = registrationWeek;
		this.closed = closed;
	}

	/**
	 * @return funksjon
	 */
	public String getJobFunctionName() {
		return jobFunctionName;
	}

	/**
	 * @param jobFunctionName
	 */
	public void setJobFunctionName(String jobFunctionName) {
		this.jobFunctionName = jobFunctionName;
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

	/**
	 * @return registreringsuke
	 */
	public Integer getRegistrationWeek() {
		return registrationWeek;
	}

	/**
	 * @param registrationWeek
	 */
	public void setRegistrationWeek(Integer registrationWeek) {
		this.registrationWeek = registrationWeek;
	}

	/**
	 * @return registreringsår
	 */
	public Integer getRegistrationYear() {
		return registrationYear;
	}

	/**
	 * @param registrationYear
	 */
	public void setRegistrationYear(Integer registrationYear) {
		this.registrationYear = registrationYear;
	}

	/**
	 * @return 1 dersom lukket
	 */
	public Integer getClosed() {
		return closed;
	}

	/**
	 * @param closed
	 */
	public void setClosed(Integer closed) {
		this.closed = closed;
	}
}
