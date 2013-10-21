package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * ID for domeneklasse DeviationSumV
 * 
 * @author atle.brekka
 * 
 */
public class DeviationSumVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String jobFunctionName;

	/**
	 * 
	 */
	private String functionCategoryName;

	/**
	 * 
	 */
	private String deviationStatusName;

	/**
	 * 
	 */
	private Integer registrationWeek;

	/**
	 * 
	 */
	private Integer registrationYear;

	/**
	 * 
	 */
	public DeviationSumVPK() {
		super();
	}

	/**
	 * @param jobFunctionName
	 * @param functionCategoryName
	 * @param deviationStatusName
	 * @param registrationWeek
	 * @param registrationYear
	 */
	public DeviationSumVPK(String jobFunctionName, String functionCategoryName,
			String deviationStatusName, Integer registrationWeek,
			Integer registrationYear) {
		super();
		this.jobFunctionName = jobFunctionName;
		this.functionCategoryName = functionCategoryName;
		this.deviationStatusName = deviationStatusName;
		this.registrationWeek = registrationWeek;
		this.registrationYear = registrationYear;
	}

	/**
	 * @return status
	 */
	public String getDeviationStatusName() {
		return deviationStatusName;
	}

	/**
	 * @param deviationStatusName
	 */
	public void setDeviationStatusName(String deviationStatusName) {
		this.deviationStatusName = deviationStatusName;
	}

	/**
	 * @return kategori
	 */
	public String getFunctionCategoryName() {
		return functionCategoryName;
	}

	/**
	 * @param functionCategoryName
	 */
	public void setFunctionCategoryName(String functionCategoryName) {
		this.functionCategoryName = functionCategoryName;
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
}
