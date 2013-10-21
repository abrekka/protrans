package no.ugland.utransprod.model;

import java.io.Serializable;

/**
 * ID for domeneklasse DeviationSumV
 * 
 * @author atle.brekka
 * 
 */
public class DeviationSumJobFunctionVPK implements Serializable {
	private Integer registrationYear;
	private Integer month;
	private Integer registrationWeek;
	private String deviationFunction;
	private String functionCategoryName;
	private String productAreaGroupName;
	
	public DeviationSumJobFunctionVPK(){
		
	}
	
	public DeviationSumJobFunctionVPK(Integer registrationYear, 
			Integer registrationWeek, String deviationFunction,
			String functionCategoryName, String productAreaGroupName) {
		super();
		this.registrationYear = registrationYear;
		this.month = month;
		this.registrationWeek = registrationWeek;
		this.deviationFunction = deviationFunction;
		this.functionCategoryName = functionCategoryName;
		this.productAreaGroupName = productAreaGroupName;
	}
	public Integer getRegistrationYear() {
		return registrationYear;
	}
	public void setRegistrationYear(Integer registrationYear) {
		this.registrationYear = registrationYear;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getRegistrationWeek() {
		return registrationWeek;
	}
	public void setRegistrationWeek(Integer registrationWeek) {
		this.registrationWeek = registrationWeek;
	}
	public String getDeviationFunction() {
		return deviationFunction;
	}
	public void setDeviationFunction(String deviationFunction) {
		this.deviationFunction = deviationFunction;
	}
	public String getFunctionCategoryName() {
		return functionCategoryName;
	}
	public void setFunctionCategoryName(String functionCategoryName) {
		this.functionCategoryName = functionCategoryName;
	}
	public String getProductAreaGroupName() {
		return productAreaGroupName;
	}
	public void setProductAreaGroupName(String productAreaGroupName) {
		this.productAreaGroupName = productAreaGroupName;
	}
}