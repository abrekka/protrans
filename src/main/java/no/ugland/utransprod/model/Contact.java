package no.ugland.utransprod.model;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Klasse for tabell CONTACT i SuperOffice
 * 
 * @author atle.brekka
 * 
 */
public class Contact extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer contactId;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String number2;

	/**
	 * 
	 */
	private Integer categoryIdx;

	/**
	 * 
	 */
	private Date updated;

	/**
	 * 
	 */
	private Set<Sale> sales;

	/**
	 * 
	 */
	private Set<OfferAddress> offerAddresses;

	/**
	 * 
	 */
	private Set<Phone> phones;

	/**
	 * 
	 */
	private Set<Address> addresses;

	/**
	 * 
	 */
	private Associate associate;

	/**
	 * 
	 */
	public Contact() {
	}

	/**
	 * @param contactId
	 * @param name
	 * @param number2
	 * @param categoryIdx
	 * @param updated
	 * @param sales
	 * @param offerAddresses
	 * @param phones
	 * @param addresses
	 * @param associate
	 */
	public Contact(Integer contactId, String name, String number2,
			Integer categoryIdx, Date updated, Set<Sale> sales,
			Set<OfferAddress> offerAddresses, Set<Phone> phones,
			Set<Address> addresses, Associate associate) {
		super();
		this.contactId = contactId;
		this.name = name;
		this.number2 = number2;
		this.categoryIdx = categoryIdx;
		this.updated = updated;
		this.sales = sales;
		this.offerAddresses = offerAddresses;
		this.phones = phones;
		this.addresses = addresses;
		this.associate = associate;
	}

	/**
	 * @return kategori
	 */
	public Integer getCategoryIdx() {
		return categoryIdx;
	}

	/**
	 * @param categoryIdx
	 */
	public void setCategoryIdx(Integer categoryIdx) {
		this.categoryIdx = categoryIdx;
	}

	/**
	 * @return id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * @param contactId
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return navn
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return number2
	 */
	public String getNumber2() {
		return number2;
	}

	/**
	 * @param number2
	 */
	public void setNumber2(String number2) {
		this.number2 = number2;
	}

	/**
	 * @return oppdatertdato
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Contact))
			return false;
		Contact castOther = (Contact) other;
		return new EqualsBuilder().append(contactId, castOther.contactId)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(contactId).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"name", name).toString();
	}

	/**
	 * @return salg
	 */
	public Set<Sale> getSales() {
		return sales;
	}

	/**
	 * @param sales
	 */
	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}

	/**
	 * @return adresser
	 */
	public Set<OfferAddress> getOfferAddresses() {
		return offerAddresses;
	}

	/**
	 * @param offerAddresses
	 */
	public void setOfferAddresses(Set<OfferAddress> offerAddresses) {
		this.offerAddresses = offerAddresses;
	}

	/**
	 * @return telfonnumre
	 */
	public Set<Phone> getPhones() {
		return phones;
	}

	/**
	 * @param phones
	 */
	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	/**
	 * @return adresser
	 */
	public Set<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 */
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return leveringsadresse
	 */
	public Address getDeliveryAddress() {
		if (addresses != null) {
			for (Address address : addresses) {
				if (address.getAtypeIdx() == 2) {
					return address;
				}
			}
		}
		return null;
	}

	/**
	 * @return assosiasjon
	 */
	public Associate getAssociate() {
		return associate;
	}

	/**
	 * @param associate
	 */
	public void setAssociate(Associate associate) {
		this.associate = associate;
	}
}
