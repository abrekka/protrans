package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.TextRenderable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Modellklasse for view FAKTURERING_V
 * 
 * @author atle.brekka
 * 
 */
public class FaktureringV extends BaseObject implements TextRenderable, Applyable {
	private static final long serialVersionUID = 1L;

	private Integer orderId;
	private Integer customerNr;

	private String firstName;
	private String lastName;
	private String postalCode;
	private String postOffice;
	private String constructionName;
	private Date levert;
	private BigDecimal customerCost;
	private String orderNr;
	private Date invoiceDate;
	private String comment;
	private Date assembliedDate;
	private String productAreaGroupName;
	private String ordrekoordinator;
	private Date sentMail;

	public FaktureringV() {
		super();
	}

	/**
	 * @param orderId
	 * @param customerNr
	 * @param firstName
	 * @param lastName
	 * @param postalCode
	 * @param postOffice
	 * @param constructionName
	 * @param sent
	 * @param customerCost
	 * @param orderNr
	 * @param invoiceDate
	 * @param comment
	 * @param assembliedDate
	 * @param productAreaGroupName
	 */
	public FaktureringV(Integer orderId, Integer customerNr, String firstName, String lastName, String postalCode,
			String postOffice, String constructionName, Date levert, BigDecimal customerCost, String orderNr,
			Date invoiceDate, String comment, Date assembliedDate, String productAreaGroupName,
			String ordrekoordinator) {
		super();
		this.orderId = orderId;
		this.customerNr = customerNr;
		this.firstName = firstName;
		this.lastName = lastName;
		this.postalCode = postalCode;
		this.postOffice = postOffice;
		this.constructionName = constructionName;
		this.levert = levert;
		this.customerCost = customerCost;
		this.orderNr = orderNr;
		this.invoiceDate = invoiceDate;
		this.comment = comment;
		this.assembliedDate = assembliedDate;
		this.productAreaGroupName = productAreaGroupName;
		this.ordrekoordinator = ordrekoordinator;
	}

	public Date getSentMail() {
		return sentMail;
	}

	public void setSentMail(Date sentMail) {
		this.sentMail = sentMail;
	}

	public String getOrdrekoordinator() {
		return ordrekoordinator;
	}

	public void setOrdrekoordinator(String ordrekoordinator) {
		this.ordrekoordinator = ordrekoordinator;
	}

	/**
	 * @return garasjetype
	 */
	public String getConstructionName() {
		return constructionName;
	}

	/**
	 * @param constructionName
	 */
	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}

	/**
	 * @return kundekostnad
	 */
	public BigDecimal getCustomerCost() {
		return customerCost;
	}

	/**
	 * @param customerCost
	 */
	public void setCustomerCost(BigDecimal customerCost) {
		this.customerCost = customerCost;
	}

	/**
	 * @return kundenummer
	 */
	public Integer getCustomerNr() {
		return customerNr;
	}

	/**
	 * @param customerNr
	 */
	public void setCustomerNr(Integer customerNr) {
		this.customerNr = customerNr;
	}

	/**
	 * @return fornavn
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return etternavn
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return ordreid
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return postnummrt
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return poststed
	 */
	public String getPostOffice() {
		return postOffice;
	}

	/**
	 * @param postOffice
	 */
	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	/**
	 * @return dato sendt
	 */
	public Date getLevert() {
		return levert;
	}

	/**
	 * @param sent
	 */
	public void setLevert(Date levert) {
		this.levert = levert;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof FaktureringV))
			return false;
		FaktureringV castOther = (FaktureringV) other;
		return new EqualsBuilder().append(orderId, castOther.orderId).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderId).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return customerNr + " " + firstName + " " + lastName + " " + postalCode + " " + postOffice + " "
				+ constructionName;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Applyable#getOrderNr()
	 */
	public String getOrderNr() {
		return orderNr;
	}

	/**
	 * @param orderNr
	 */
	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	/**
	 * @return fakturadato
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.TextRenderable#getComment()
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.TextRenderable#getOrderString()
	 */
	public String getOrderString() {
		return customerNr + " " + firstName + " " + lastName + " - " + orderNr + "\n" + postalCode + " " + postOffice
				+ "," + constructionName;

	}

	/**
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return monteringsdato
	 */
	public Date getAssembliedDate() {
		return assembliedDate;
	}

	/**
	 * @param assembliedDate
	 */
	public void setAssembliedDate(Date assembliedDate) {
		this.assembliedDate = assembliedDate;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Applyable#isForPostShipment()
	 */
	public Boolean isForPostShipment() {
		return null;
	}

	/**
	 * @return produktområdegruppenavn
	 */
	public String getProductAreaGroupName() {
		return productAreaGroupName;
	}

	/**
	 * @param productAreaGroupName
	 */
	public void setProductAreaGroupName(String productAreaGroupName) {
		this.productAreaGroupName = productAreaGroupName;
	}

	public Ordln getOrdln() {
		return null;
	}

	public void setOrdln(Ordln ordln) {

	}

	public Integer getOrderLineId() {
		return null;
	}

	public String getArticleName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Colli getColli() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getProduced() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Applyable> getRelatedArticles() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRelatedArticlesComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setColli(Colli colli) {
		// TODO Auto-generated method stub

	}

	public void setProduced(Date produced) {
		// TODO Auto-generated method stub

	}

	public void setRelatedArticles(List<Applyable> relatedArticles) {
		// TODO Auto-generated method stub

	}

	public Integer getNumberOfItems() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProductionUnitName() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getRealProductionHours() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getActionStarted() {
		// TODO Auto-generated method stub
		return null;
	}

}
