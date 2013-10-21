package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Modellklasse for tabell FUNCTION_CATGORY
 * 
 * @author atle.brekka
 * 
 */
public class FunctionCategory extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer functionCategoryId;

	/**
	 * 
	 */
	private String functionCategoryName;

	/**
	 * 
	 */
	private String functionCategoryDescription;

	/**
	 * 
	 */
	private JobFunction jobFunction;

	/**
	 * 
	 */
	public FunctionCategory() {
		super();
	}

	/**
	 * @param functionCategoryId
	 * @param functionCategoryDescription
	 * @param jobFunction
	 * @param functionCategoryName
	 */
	public FunctionCategory(Integer functionCategoryId,
			String functionCategoryDescription, JobFunction jobFunction,
			String functionCategoryName) {
		super();
		this.functionCategoryId = functionCategoryId;
		this.functionCategoryDescription = functionCategoryDescription;
		this.jobFunction = jobFunction;
		this.functionCategoryName = functionCategoryName;
	}

	/**
	 * @return beskrivelse
	 */
	public String getFunctionCategoryDescription() {
		return functionCategoryDescription;
	}

	/**
	 * @param functionCategoryDescription
	 */
	public void setFunctionCategoryDescription(
			String functionCategoryDescription) {
		this.functionCategoryDescription = functionCategoryDescription;
	}

	/**
	 * @return id
	 */
	public Integer getFunctionCategoryId() {
		return functionCategoryId;
	}

	/**
	 * @param functionCategoryId
	 */
	public void setFunctionCategoryId(Integer functionCategoryId) {
		this.functionCategoryId = functionCategoryId;
	}

	/**
	 * @return funksjon
	 */
	public JobFunction getJobFunction() {
		return jobFunction;
	}

	/**
	 * @param jobFunction
	 */
	public void setJobFunction(JobFunction jobFunction) {
		this.jobFunction = jobFunction;
	}

	/**
	 * @return navn
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
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof FunctionCategory))
			return false;
		FunctionCategory castOther = (FunctionCategory) other;
		return new EqualsBuilder().append(functionCategoryName,
				castOther.functionCategoryName).append(jobFunction,
				castOther.jobFunction).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(functionCategoryName).append(
				jobFunction).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"functionCategoryName", functionCategoryName).toString();
	}

}
