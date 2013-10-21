package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.TextRenderable;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for view PAID_V
 * @author atle.brekka
 */
public class PaidV extends BaseObject implements TextRenderable, Applyable {
    private static final long serialVersionUID = 1L;

    private Integer orderId;

    private Integer customerNr;

    private String firstName;

    private String lastName;

    private String postalCode;

    private String postOffice;

    private String constructionName;

    private Date sent;

    private BigDecimal customerCost;

    private String orderNr;

    private Date paidDate;

    private String comment;

    private Integer doAssembly;

    private String productAreaGroupName;
    /**
     * Brukes for kundetransaksjoner i Visma
     */
    private List<CustTr> custTrs;

    public PaidV() {
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
     * @param paidDate
     * @param comment
     * @param doAssembly
     * @param productAreaGroupName
     */
    public PaidV(Integer orderId, Integer customerNr, String firstName, String lastName, String postalCode,
            String postOffice, String constructionName, Date sent, BigDecimal customerCost, String orderNr,
            Date paidDate, String comment, Integer doAssembly, String productAreaGroupName) {
        super();
        this.orderId = orderId;
        this.customerNr = customerNr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.postOffice = postOffice;
        this.constructionName = constructionName;
        this.sent = sent;
        this.customerCost = customerCost;
        this.orderNr = orderNr;
        this.paidDate = paidDate;
        this.comment = comment;
        this.doAssembly = doAssembly;
        this.productAreaGroupName = productAreaGroupName;
    }

    /**
     * @see no.ugland.utransprod.gui.model.TextRenderable#getComment()
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return konstruksjonstype
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
     * @return 1 dersom montering
     */
    public Integer getDoAssembly() {
        return doAssembly;
    }

    /**
     * @param doAssembly
     */
    public void setDoAssembly(Integer doAssembly) {
        this.doAssembly = doAssembly;
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
     * @return betalt dato
     */
    public Date getPaidDate() {
        return paidDate;
    }

    /**
     * @param paidDate
     */
    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    /**
     * @return postnummer
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
     * @return sendtdato
     */
    public Date getSent() {
        return sent;
    }

    /**
     * @param sent
     */
    public void setSent(Date sent) {
        this.sent = sent;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof PaidV))
            return false;
        PaidV castOther = (PaidV) other;
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
     * @see no.ugland.utransprod.gui.model.TextRenderable#getOrderString()
     */
    public String getOrderString() {
        return customerNr + " " + firstName + " " + lastName + " - " + orderNr + "\n" + postalCode + " "
                + postOffice + "," + constructionName;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Applyable#isForPostShipment()
     */
    public Boolean isForPostShipment() {
        return Boolean.FALSE;
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

    public List<CustTr> getCustTrs() {
        return custTrs;
    }

    public void setCustTrs(List<CustTr> custTrs) {
        this.custTrs = custTrs;
    }

    public Integer getOrderLineId() {
        return null;
    }

    public Ordln getOrdln() {
        return null;
    }

    public void setOrdln(Ordln ordln) {
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

    public String getInvoiceAmount() {
        BigDecimal invoiceAmount = BigDecimal.ZERO;
        List<CustTr> invoices = custTrs != null ? custTrs : new ArrayList<CustTr>();
        for (CustTr custTr : invoices) {
            invoiceAmount = invoiceAmount.add(custTr.getInvoiceAmount() != null ? custTr.getInvoiceAmount()
                    : BigDecimal.ZERO);
        }
        return String.format("%1$.0f", invoiceAmount.setScale(0, RoundingMode.HALF_EVEN));
    }

    public String getRestAmount() {
        BigDecimal restAmount = BigDecimal.ZERO;
        List<CustTr> invoices = custTrs != null ? custTrs : new ArrayList<CustTr>();
        for (CustTr custTr : invoices) {
            restAmount = restAmount.add(custTr.getRestAmount() != null ? custTr.getRestAmount()
                    : BigDecimal.ZERO);
        }
        return String.format("%1$.0f", restAmount.setScale(0, RoundingMode.HALF_EVEN));
    }

    public String getDueDate() {
        Date dueDate = null;
        List<CustTr> invoices = custTrs != null ? custTrs : new ArrayList<CustTr>();
        for (CustTr custTr : invoices) {
            dueDate = dueDate == null ? custTr.getDueDate() : dueDate
                    .before(custTr.getDueDate() != null ? custTr.getDueDate() : dueDate) ? custTr
                    .getDueDate() : dueDate;
        }
        return dueDate!=null?Util.SHORT_DATE_FORMAT.format(dueDate):null;
    }

	public Integer getNumberOfItems() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProductionUnitName() {
		// TODO Auto-generated method stub
		return null;
	}

}
