package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Klasse for tabell ADDRESS i SuperOffice
 * @author atle.brekka
 */
public class Address extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer addressId;

    private Integer atypeIdx;

    private String zipcode;

    private String city;

    private String address1;

    private Contact contact;

    public Address() {
    }

    /**
     * @param aAddressId
     * @param aContact
     * @param aAtypeIdx
     * @param aZipcode
     * @param aCity
     * @param aAddress1
     */
    public Address(final Integer aAddressId, final Contact aContact, final Integer aAtypeIdx,
            final String aZipcode, final String aCity, final String aAddress1) {
        super();
        this.addressId = aAddressId;
        this.contact = aContact;
        this.atypeIdx = aAtypeIdx;
        this.zipcode = aZipcode;
        this.city = aCity;
        this.address1 = aAddress1;
    }

    /**
     * @return adresse1
     */
    public final String getAddress1() {
        return address1;
    }

    /**
     * @param aAddress1
     */
    public final void setAddress1(final String aAddress1) {
        this.address1 = aAddress1;
    }

    /**
     * @return id
     */
    public final Integer getAddressId() {
        return addressId;
    }

    /**
     * @param aAddressId
     */
    public final void setAddressId(final Integer aAddressId) {
        this.addressId = aAddressId;
    }

    /**
     * @return adressetype
     */
    public final Integer getAtypeIdx() {
        return atypeIdx;
    }

    /**
     * @param aAtypeIdx
     */
    public final void setAtypeIdx(final Integer aAtypeIdx) {
        this.atypeIdx = aAtypeIdx;
    }

    /**
     * @return poststed
     */
    public final String getCity() {
        return city;
    }

    /**
     * @param aCity
     */
    public final void setCity(final String aCity) {
        this.city = aCity;
    }

    /**
     * @return kontakt
     */
    public final Contact getContact() {
        return contact;
    }

    /**
     * @param aContact
     */
    public final void setContact(final Contact aContact) {
        this.contact = aContact;
    }

    /**
     * @return postnummer
     */
    public final String getZipcode() {
        return zipcode;
    }

    /**
     * @param aZipcode
     */
    public final void setZipcode(final String aZipcode) {
        this.zipcode = aZipcode;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof Address)){
            return false;
        }
        Address castOther = (Address) other;
        return new EqualsBuilder().append(addressId, castOther.addressId)
                .isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(addressId).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "addressId", addressId).append("contact", contact).append(
                "atypeIdx", atypeIdx).append("zipcode", zipcode).append("city",
                city).append("address1", address1).toString();
    }
}
