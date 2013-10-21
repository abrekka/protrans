package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * Nøkkel for view POST_SHIPMENT_COUNT_V
 * 
 * @author atle.brekka
 * 
 */
public class PostShipmentCountVPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer sentYear;

	/**
	 * 
	 */
	private Integer sentWeek;

	/**
	 * 
	 */
	private String jobFunctionName;
	private String productArea;

	/**
	 * 
	 */
	public PostShipmentCountVPK() {
		super();
	}

	/**
	 * @param sentYear
	 * @param sentWeek
	 * @param jobFunctionName
	 */
	public PostShipmentCountVPK(Integer sentYear, Integer sentWeek,
			String jobFunctionName,String productArea) {
		super();
		this.sentYear = sentYear;
		this.sentWeek = sentWeek;
		this.jobFunctionName = jobFunctionName;
		this.productArea=productArea;
	}

	/**
	 * @return funksjonnavn
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
	 * @return sendt uke
	 */
	public Integer getSentWeek() {
		return sentWeek;
	}

	/**
	 * @param sentWeek
	 */
	public void setSentWeek(Integer sentWeek) {
		this.sentWeek = sentWeek;
	}

	/**
	 * @return sendt år
	 */
	public Integer getSentYear() {
		return sentYear;
	}

	/**
	 * @param sentYear
	 */
	public void setSentYear(Integer sentYear) {
		this.sentYear = sentYear;
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}
}
