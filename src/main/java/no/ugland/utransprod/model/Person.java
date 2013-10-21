package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for tabell PERSON i SuperOffice
 * 
 * @author atle.brekka
 * 
 */
public class Person extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer personId;

	/**
	 * 
	 */
	private String firstname;

	/**
	 * 
	 */
	private String lastname;

	/**
	 * 
	 */
	public Person() {
		super();
	}

	/**
	 * @param personId
	 * @param firstname
	 * @param lastname
	 */
	public Person(Integer personId, String firstname, String lastname) {
		super();
		this.personId = personId;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	/**
	 * @return fornavn
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return etternavn
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return id
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Person))
			return false;
		Person castOther = (Person) other;
		return new EqualsBuilder().append(personId, castOther.personId)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(personId).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return firstname + " " + lastname;
	}

}
