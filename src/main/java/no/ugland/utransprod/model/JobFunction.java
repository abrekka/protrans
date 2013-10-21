package no.ugland.utransprod.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for tabell JOB_FUNCTION
 * 
 * @author atle.brekka
 * 
 */
public class JobFunction extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer jobFunctionId;

	/**
	 * 
	 */
	private String jobFunctionName;

	/**
	 * 
	 */
	private String jobFunctionDescription;

	/**
	 * 
	 */
	private ApplicationUser manager;

	/**
	 * 
	 */
	private Set<Deviation> deviations;

	/**
	 * 
	 */
	private Set<Deviation> ownDeviations;

	/**
	 * 
	 */
	private Set<FunctionCategory> functionCategories;

	/**
	 * 
	 */
	public JobFunction() {
		super();
	}

	/**
	 * @param jobFunctionId
	 * @param jobFunctionName
	 * @param manager
	 * @param jobFunctionDescription
	 * @param deviations
	 * @param ownDeviations
	 * @param functionCategories
	 */
	public JobFunction(Integer jobFunctionId, String jobFunctionName,
			ApplicationUser manager, String jobFunctionDescription,
			Set<Deviation> deviations, Set<Deviation> ownDeviations,
			Set<FunctionCategory> functionCategories) {
		super();
		this.jobFunctionId = jobFunctionId;
		this.jobFunctionName = jobFunctionName;
		this.manager = manager;
		this.jobFunctionDescription = jobFunctionDescription;
		this.deviations = deviations;
		this.ownDeviations = ownDeviations;
		this.functionCategories = functionCategories;
	}

	public JobFunction(String jobFunctionName) {
		this(null,jobFunctionName,null,null,null,null,null);
	}

	/**
	 * @return id
	 */
	public Integer getJobFunctionId() {
		return jobFunctionId;
	}

	/**
	 * @param jobFunctionId
	 */
	public void setJobFunctionId(Integer jobFunctionId) {
		this.jobFunctionId = jobFunctionId;
	}

	/**
	 * @return funksjonsnavn
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
	 * @return funksjonsleder
	 */
	public ApplicationUser getManager() {
		return manager;
	}

	/**
	 * @param manager
	 */
	public void setManager(ApplicationUser manager) {
		this.manager = manager;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof JobFunction))
			return false;
		JobFunction castOther = (JobFunction) other;
		return new EqualsBuilder().append(jobFunctionName,
				castOther.jobFunctionName).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(jobFunctionName).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return jobFunctionName;
	}

	/**
	 * @return funksjonsbeskrivelse
	 */
	public String getJobFunctionDescription() {
		return jobFunctionDescription;
	}

	/**
	 * @param jobFunctionDescription
	 */
	public void setJobFunctionDescription(String jobFunctionDescription) {
		this.jobFunctionDescription = jobFunctionDescription;
	}

	/**
	 * @return avviksfunksjoner
	 */
	public Set<Deviation> getDeviations() {
		return deviations;
	}

	/**
	 * @param deviations
	 */
	public void setDeviations(Set<Deviation> deviations) {
		this.deviations = deviations;
	}

	/**
	 * @return avvik regisret fra funksjon
	 */
	public Set<Deviation> getOwnDeviations() {
		return ownDeviations;
	}

	/**
	 * @param ownDeviations
	 */
	public void setOwnDeviations(Set<Deviation> ownDeviations) {
		this.ownDeviations = ownDeviations;
	}

	/**
	 * @return kategorier
	 */
	public Set<FunctionCategory> getFunctionCategories() {
		return functionCategories;
	}

	/**
	 * @param functionCategories
	 */
	public void setFunctionCategories(Set<FunctionCategory> functionCategories) {
		this.functionCategories = functionCategories;
	}
}
