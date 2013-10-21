package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklase for tabell OFFER_ADDRESS som ligger i SuperOffice
 * 
 * @author atle.brekka
 * 
 */
public class OfferAddress extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer addressId;

	/**
	 * 
	 */
	private String address1;

	/**
	 * 
	 */
	private String zipcode;

	/**
	 * 
	 */
	private String city;

	/**
	 * 
	 */
	private Contact contact;

	/**
	 * 
	 */
	public OfferAddress() {
		super();
	}

	/**
	 * @param addressId
	 * @param address1
	 * @param zipcode
	 * @param city
	 * @param contact
	 */
	public OfferAddress(Integer addressId, String address1, String zipcode,
			String city, Contact contact) {
		super();
		this.addressId = addressId;
		this.address1 = address1;
		this.zipcode = zipcode;
		this.city = city;
		this.contact = contact;
	}

	/**
	 * @return adresse
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return id
	 */
	public Integer getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return poststed
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * @return postnummer
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OfferAddress))
			return false;
		OfferAddress castOther = (OfferAddress) other;
		return new EqualsBuilder().append(addressId, castOther.addressId)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(addressId).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"addressId", addressId).append("address1", address1).append(
				"zipcode", zipcode).append("city", city).append("contact",
				contact).toString();
	}
}
