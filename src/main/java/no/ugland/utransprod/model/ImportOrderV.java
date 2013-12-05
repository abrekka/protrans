package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for SuperOffice tabell SALE
 * @author atle.brekka
 */
public class ImportOrderV extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer saleId;

    private String number1;

    private BigDecimal amount;

    private Date saledate;

    private Integer contactId;

    private Integer userdefId;


    private Integer productAreaNr;//100-rekke,200-villa,300-takstol,400-byggelement
    private Date registered;

    private Integer probability;
    private String deliveryAddress;
    private String postalCode;
    private String postoffice;
    private String salesMan;

	private String telephoneNrSite;
	private Integer maksHoyde;

    public ImportOrderV() {
        super();
    }


    /**
     * @return beløp
     */
    public final BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param aAmount
     */
    public final void setAmount(final BigDecimal aAmount) {
        this.amount = aAmount;
    }

    /**
     * @return kontakt
     */
    public final Integer getContactId() {
        return contactId;
    }

    public final void setContactId(final Integer aContactId) {
        this.contactId = aContactId;
    }

    /**
     * @return nummer1
     */
    public final String getNumber1() {
        return number1;
    }

    /**
     * @param aNumber1
     */
    public final void setNumber1(final String aNumber1) {
        this.number1 = aNumber1;
    }

    /**
     * @return salgsdato
     */
    public final Date getSaledate() {
        return saledate;
    }

    /**
     * @param asSaledate
     */
    public final void setSaledate(final Date asSaledate) {
        this.saledate = asSaledate;
    }

    /**
     * @return id
     */
    public final Integer getSaleId() {
        return saleId;
    }

    /**
     * @param aSaleId
     */
    public final void setSaleId(final Integer aSaleId) {
        this.saleId = aSaleId;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof ImportOrderV)) {
            return false;
        }
        ImportOrderV castOther = (ImportOrderV) other;
        return new EqualsBuilder().append(saleId, castOther.saleId).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(saleId).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "saleId", saleId).toString();
    }

    

    public final Integer getUserdefId() {
        return userdefId;
    }

    public final void setUserdefId(final Integer aUserdefId) {
        this.userdefId = aUserdefId;
    }

    public final Integer getProductAreaNr() {
        return productAreaNr;
    }

    public final void setProductAreaNr(final Integer aProductAreaNr) {
        this.productAreaNr = aProductAreaNr;
    }

    public final Date getRegistered() {
        return registered;
    }

    public final void setRegistered(final Date isRegistered) {
        this.registered = isRegistered;
    }

    public final Integer getProbability() {
        return probability;
    }

    public final void setProbability(final Integer aProbability) {
        this.probability = aProbability;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostoffice() {
        return postoffice;
    }

    public void setPostoffice(String postoffice) {
        this.postoffice = postoffice;
    }

	public String getSalesMan() {
		return salesMan;
	}

	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}

	public String getTelephoneNrSite() {
		return telephoneNrSite;
	}

	public void setTelephoneNrSite(String telephoneNrSite) {
		this.telephoneNrSite = telephoneNrSite;
	}


	public Integer getMaksHoyde() {
		return maksHoyde;
	}


	public void setMaksHoyde(Integer maksHoyde) {
		this.maksHoyde = maksHoyde;
	}
}
