package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.TextRenderable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view TAKSTEIN_SKARPNES_V
 * 
 * @author atle.brekka
 */
public class TaksteinSkarpnesV extends BaseObject implements TextRenderable, Produceable {
    private static final long serialVersionUID = 1L;

    private Integer orderLineId;

    private Integer customerNr;

    private String customerDetails;

    private String orderNr;

    private String address;

    private String articleName;

    private String attributeInfo;

    private Integer numberOfItems;

    private Date loadingDate;

    private Date produced;

    private String transportDetails;

    private Integer transportYear;

    private Integer transportWeek;

    private String loadTime;

    private String comment;

    private String productAreaGroupName;

    private Date actionStarted;
    /**
     * INfo fra visma
     */
    private Ordln ordln;

    public TaksteinSkarpnesV() {
	super();
    }

    public TaksteinSkarpnesV(final Integer aOrderLineId, final Integer aCustomerNr, final String someCustomerDetails, final String aOrderNr,
	    final String aAddress, final String aArticleName, final String aAttributeInfo, final Integer aNumberOfItems, final Date aLoadingDate,
	    final Date producedDate, final String someTransportDetails, final Integer aTransportYear, final Integer aTransportWeek,
	    final String aLoadTime, final String aComment, final String aProductAreaGroupName, final Date actionStartedDate) {
	super();
	this.orderLineId = aOrderLineId;
	this.customerNr = aCustomerNr;
	this.customerDetails = someCustomerDetails;
	this.orderNr = aOrderNr;
	this.address = aAddress;
	this.articleName = aArticleName;
	this.attributeInfo = aAttributeInfo;
	this.numberOfItems = aNumberOfItems;
	this.loadingDate = aLoadingDate;
	this.produced = producedDate;
	this.transportDetails = someTransportDetails;
	this.transportYear = aTransportYear;
	this.transportWeek = aTransportWeek;
	this.loadTime = aLoadTime;
	this.comment = aComment;
	this.productAreaGroupName = aProductAreaGroupName;
	this.actionStarted = actionStartedDate;
    }

    /**
     * @return adresse
     */
    public String getAddress() {
	return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
	this.address = address;
    }

    /**
     * @return artikkelnavn
     */
    public String getArticleName() {
	return articleName;
    }

    /**
     * @param articleName
     */
    public void setArticleName(String articleName) {
	this.articleName = articleName;
    }

    /**
     * @see no.ugland.utransprod.model.Produceable#getAttributeInfo()
     */
    public String getAttributeInfo() {
	return attributeInfo;
    }

    /**
     * @param attributeInfo
     */
    public void setAttributeInfo(String attributeInfo) {
	this.attributeInfo = attributeInfo;
    }

    /**
     * @see no.ugland.utransprod.model.Produceable#getProduced()
     */
    public Date getProduced() {
	return produced;
    }

    /**
     * @param produced
     */
    public void setProduced(Date produced) {
	this.produced = produced;
    }

    /**
     * @return kundedetaljer
     */
    public String getCustomerDetails() {
	return customerDetails;
    }

    /**
     * @param customerDetails
     */
    public void setCustomerDetails(String customerDetails) {
	this.customerDetails = customerDetails;
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
     * @see no.ugland.utransprod.model.Produceable#getLoadingDate()
     */
    public Date getLoadingDate() {
	return loadingDate;
    }

    /**
     * @param loadingDate
     */
    public void setLoadingDate(Date loadingDate) {
	this.loadingDate = loadingDate;
    }

    /**
     * @return opplastingstid
     */
    public String getLoadTime() {
	return loadTime;
    }

    /**
     * @param loadTime
     */
    public void setLoadTime(String loadTime) {
	this.loadTime = loadTime;
    }

    /**
     * @see no.ugland.utransprod.model.Produceable#getNumberOfItems()
     */
    public Integer getNumberOfItems() {
	return numberOfItems;
    }

    /**
     * @param numberOfItems
     */
    public void setNumberOfItems(Integer numberOfItems) {
	this.numberOfItems = numberOfItems;
    }

    /**
     * @see no.ugland.utransprod.model.Produceable#getOrderLineId()
     */
    public Integer getOrderLineId() {
	return orderLineId;
    }

    /**
     * @param orderLineId
     */
    public void setOrderLineId(Integer orderLineId) {
	this.orderLineId = orderLineId;
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
     * @see no.ugland.utransprod.model.Produceable#getTransportDetails()
     */
    public String getTransportDetails() {
	return transportDetails;
    }

    /**
     * @param transportDetails
     */
    public void setTransportDetails(String transportDetails) {
	this.transportDetails = transportDetails;
    }

    /**
     * @return transportuke
     */
    public Integer getTransportWeek() {
	return transportWeek;
    }

    /**
     * @param transportWeek
     */
    public void setTransportWeek(Integer transportWeek) {
	this.transportWeek = transportWeek;
    }

    /**
     * @return trasportår
     */
    public Integer getTransportYear() {
	return transportYear;
    }

    /**
     * @param transportYear
     */
    public void setTransportYear(Integer transportYear) {
	this.transportYear = transportYear;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof TaksteinSkarpnesV))
	    return false;
	TaksteinSkarpnesV castOther = (TaksteinSkarpnesV) other;
	return new EqualsBuilder().append(orderLineId, castOther.orderLineId).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(orderLineId).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
	return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("orderLineId", orderLineId).toString();
    }

    /**
     * @see no.ugland.utransprod.gui.model.TextRenderable#getOrderString()
     */
    public String getOrderString() {
	StringBuffer buffer = new StringBuffer(customerDetails);

	buffer.append(" - ").append(orderNr).append("\n").append(address).append(" ,");

	return buffer.toString();
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
     * @see no.ugland.utransprod.gui.model.Applyable#isForPostShipment()
     */
    public Boolean isForPostShipment() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.model.Produceable#getProductAreaGroupName()
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

    public Date getActionStarted() {
	return actionStarted;
    }

    public void setActionStarted(Date actionStarted) {
	this.actionStarted = actionStarted;
    }

    public Date getProductionDate() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getProductionUnitName() {
	// TODO Auto-generated method stub
	return null;
    }

    public Ordln getOrdln() {
	return ordln;
    }

    public void setOrdln(Ordln ordln) {
	this.ordln = ordln;
    }

    public Colli getColli() {
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
    }

    public void setRelatedArticles(List<Applyable> relatedArticles) {
    }

    public boolean isRelatedArticlesStarted() {
	return false;
    }

    public Date getRelatedStartedDate() {
	return null;
    }

    public void setProductionUnit(ProductionUnit productionUnit) {
    }

    public void setProductionUnitName(String productionUnitName) {
    }

    public Integer getProbability() {
	return null;
    }

    public String getTrossDrawer() {
	return null;
    }

    public BigDecimal getRealProductionHours() {
	// TODO Auto-generated method stub
	return null;
    }

}
