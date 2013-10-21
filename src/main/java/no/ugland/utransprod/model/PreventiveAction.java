package no.ugland.utransprod.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for tabell PREVENTIVE_ACTION
 * 
 * @author atle.brekka
 * 
 */
public class PreventiveAction extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer preventiveActionId;

	/**
	 * 
	 */
	private String manager;

	/**
	 * 
	 */
	private JobFunction jobFunction;

	/**
	 * 
	 */
	private FunctionCategory functionCategory;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private String expectedOutcome;

	/**
	 * 
	 */
	private Date closedDate;

	/**
	 * 
	 */
	private Set<PreventiveActionComment> preventiveActionComments;

	/**
	 * 
	 */
	private String preventiveActionName;

	/**
	 * 
	 */
	public PreventiveAction() {
		super();
	}

	/**
	 * @param preventiveActionId
	 * @param manager
	 * @param jobFunction
	 * @param functionCategory
	 * @param description
	 * @param expectedOutcome
	 * @param closedDate
	 * @param preventiveActionComments
	 * @param preventiveActionName
	 */
	public PreventiveAction(Integer preventiveActionId, String manager,
			JobFunction jobFunction, FunctionCategory functionCategory,
			String description, String expectedOutcome, Date closedDate,
			Set<PreventiveActionComment> preventiveActionComments,
			String preventiveActionName) {
		super();
		this.preventiveActionId = preventiveActionId;
		this.manager = manager;
		this.jobFunction = jobFunction;
		this.functionCategory = functionCategory;
		this.description = description;
		this.expectedOutcome = expectedOutcome;
		this.closedDate = closedDate;
		this.preventiveActionComments = preventiveActionComments;
		this.preventiveActionName = preventiveActionName;
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return forventet resultat
	 */
	public String getExpectedOutcome() {
		return expectedOutcome;
	}

	/**
	 * @param expectedOutcome
	 */
	public void setExpectedOutcome(String expectedOutcome) {
		this.expectedOutcome = expectedOutcome;
	}

	/**
	 * @return kategori
	 */
	public FunctionCategory getFunctionCategory() {
		return functionCategory;
	}

	/**
	 * @param functionCategory
	 */
	public void setFunctionCategory(FunctionCategory functionCategory) {
		this.functionCategory = functionCategory;
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
	 * @return ansvarlig
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @param manager
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * @return id
	 */
	public Integer getPreventiveActionId() {
		return preventiveActionId;
	}

	/**
	 * @param preventiveActionId
	 */
	public void setPreventiveActionId(Integer preventiveActionId) {
		this.preventiveActionId = preventiveActionId;
	}

	/**
	 * @return lukket dato
	 */
	public Date getClosedDate() {
		return closedDate;
	}

	/**
	 * @param closedDate
	 */
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		if (preventiveActionId != null) {
			return preventiveActionId + " - " + preventiveActionName;
		}
		return preventiveActionName;
	}

	/**
	 * @return kommentarer
	 */
	public Set<PreventiveActionComment> getPreventiveActionComments() {
		return preventiveActionComments;
	}

	/**
	 * @param preventiveActionComments
	 */
	public void setPreventiveActionComments(
			Set<PreventiveActionComment> preventiveActionComments) {
		this.preventiveActionComments = preventiveActionComments;
	}

	/**
	 * @param comment
	 */
	public void addPreventiveActionComment(PreventiveActionComment comment) {
		if (comment != null) {
			if (preventiveActionComments == null) {
				preventiveActionComments = new HashSet<PreventiveActionComment>();
			}
			comment.setPreventiveAction(this);
			preventiveActionComments.add(comment);
		}
	}

	/**
	 * @return navn
	 */
	public String getPreventiveActionName() {
		return preventiveActionName;
	}

	/**
	 * @param preventiveActionName
	 */
	public void setPreventiveActionName(String preventiveActionName) {
		this.preventiveActionName = preventiveActionName;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof PreventiveAction))
			return false;
		PreventiveAction castOther = (PreventiveAction) other;
		return new EqualsBuilder().append(preventiveActionId,
				castOther.preventiveActionId).append(jobFunction,
				castOther.jobFunction).append(functionCategory,
				castOther.functionCategory).append(preventiveActionName,
				castOther.preventiveActionName).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(preventiveActionId).append(
				jobFunction).append(functionCategory).append(
				preventiveActionName).toHashCode();
	}

}
