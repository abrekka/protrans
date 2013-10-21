package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for SuperOffice tabell PHONE
 * 
 * @author atle.brekka
 * 
 */
public class Phone extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer phoneId;

	/**
	 * 
	 */
	private Integer ptypeIdx;

	/**
	 * 
	 */
	private String phone;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Contact contact;

	/**
	 * 
	 */
	public Phone() {
		super();
	}

	/**
	 * @param phoneId
	 * @param ptypeIdx
	 * @param phone
	 * @param description
	 * @param contact
	 */
	public Phone(Integer phoneId, Integer ptypeIdx, String phone,
			String description, Contact contact) {
		super();
		this.phoneId = phoneId;
		this.ptypeIdx = ptypeIdx;
		this.phone = phone;
		this.description = description;
		this.contact = contact;
	}

	/**
	 * @return kontakt
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * @param contact
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
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
	 * @return type
	 */
	public Integer getPtypeIdx() {
		return ptypeIdx;
	}

	/**
	 * @param ptypeIdx
	 */
	public void setPtypeIdx(Integer ptypeIdx) {
		this.ptypeIdx = ptypeIdx;
	}

	/**
	 * @return telefonnummer
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return id
	 */
	public Integer getPhoneId() {
		return phoneId;
	}

	/**
	 * @param phoneId
	 */
	public void setPhoneId(Integer phoneId) {
		this.phoneId = phoneId;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Phone))
			return false;
		Phone castOther = (Phone) other;
		return new EqualsBuilder().append(phoneId, castOther.phoneId)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(phoneId).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"phoneId", phoneId).append("ptypeIdx", ptypeIdx).append(
				"phone", phone).append("description", description).append(
				"contact", contact).toString();
	}

}
