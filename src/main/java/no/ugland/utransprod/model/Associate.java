package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Klasse for tabell ASSOCIATE i SuperOffice
 * 
 * @author atle.brekka
 * 
 */
public class Associate extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer associateId;

	/**
	 * 
	 */
	private Person person;

	/**
	 * 
	 */
	public Associate() {
		super();
	}

	/**
	 * @param associateId
	 * @param person
	 */
	public Associate(Integer associateId, Person person) {
		super();
		this.associateId = associateId;
		this.person = person;
	}

	/**
	 * @return id
	 */
	public Integer getAssociateId() {
		return associateId;
	}

	/**
	 * @param associateId
	 */
	public void setAssociateId(Integer associateId) {
		this.associateId = associateId;
	}

	/**
	 * @return person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Associate))
			return false;
		Associate castOther = (Associate) other;
		return new EqualsBuilder().append(associateId, castOther.associateId)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(associateId).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"associateId", associateId).append("person", person).toString();
	}

}
