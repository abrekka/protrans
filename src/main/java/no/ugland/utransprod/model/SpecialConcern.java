package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Donemeklasse for tabell SPECIAL_CONCERN
 * 
 * @author atle.brekka
 * 
 */
public class SpecialConcern extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer specialConcernId;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Integer specialTransport;

	/**
	 * 
	 */
	public SpecialConcern() {
		super();
	}

	/**
	 * @param specialConcernId
	 * @param description
	 * @param specialTransport
	 */
	public SpecialConcern(Integer specialConcernId, String description,
			Integer specialTransport) {
		super();
		this.specialConcernId = specialConcernId;
		this.description = description;
		this.specialTransport = specialTransport;
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
	 * @return id
	 */
	public Integer getSpecialConcernId() {
		return specialConcernId;
	}

	/**
	 * @param specialConcernId
	 */
	public void setSpecialConcernId(Integer specialConcernId) {
		this.specialConcernId = specialConcernId;
	}

	/**
	 * @return 1 dersom spesialtilfelle for transport
	 */
	public Integer getSpecialTransport() {
		return specialTransport;
	}

	/**
	 * @param specialTransport
	 */
	public void setSpecialTransport(Integer specialTransport) {
		this.specialTransport = specialTransport;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SpecialConcern))
			return false;
		SpecialConcern castOther = (SpecialConcern) other;
		return new EqualsBuilder().append(specialConcernId,
				castOther.specialConcernId).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(specialConcernId).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("description", description)
				.toString();
	}
}
