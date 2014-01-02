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
 * Domeneklasse for view GULVSPON_PACKAGE_V
 * 
 * @author atle.brekka
 */
public class GulvsponPackageV extends BaseObject implements TextRenderable, PackableListItem {
    private static final long serialVersionUID = 1L;

    private Integer orderLineId;

    private String customerDetails;

    private String orderNr;

    private String address;

    private String info;

    private String constructionTypeName;

    private String articleName;

    private String attributeInfo;

    private Integer numberOfItems;

    private Date loadingDate;

    private String transportDetails;

    private String comment;

    private Integer transportYear;

    private Integer transportWeek;

    private Colli colli;

    private Integer customerNr;

    private String loadTime;

    private Integer postShipmentId;

    private String productAreaGroupName;

    private Date actionStarted;
    /**
     * Brukes til å lagre kobling til visma
     */
    private Ordln ordln;

    public GulvsponPackageV() {
	super();

    }

    public GulvsponPackageV(final Integer aOrderLineId, final String someCustomerDetails, final String aOrderNr, final String aAddress,
	    final String someInfo, final String aConstructionTypeName, final String aArticleName, final String aAttributeInfo,
	    final Integer aNumberOfItems, final Date aLoadingDate, final Colli aColli, final String someTransportDetails, final String aComment,
	    final Integer aTransportYear, final Integer aTransportWeek, final Integer aCustomerNr, final String aLoadTime,
	    final Integer aPostShipmentId, final String aProductAreaGroupName, final Date actionStartedDate) {
	super();
	this.orderLineId = aOrderLineId;
	this.customerDetails = someCustomerDetails;
	this.orderNr = aOrderNr;
	this.address = aAddress;
	this.info = someInfo;
	this.constructionTypeName = aConstructionTypeName;
	this.articleName = aArticleName;
	this.attributeInfo = aAttributeInfo;
	this.numberOfItems = aNumberOfItems;
	this.loadingDate = aLoadingDate;
	this.colli = aColli;
	this.transportDetails = someTransportDetails;
	this.comment = aComment;
	this.transportYear = aTransportYear;
	this.transportWeek = aTransportWeek;
	this.customerNr = aCustomerNr;
	this.loadTime = aLoadTime;
	this.postShipmentId = aPostShipmentId;
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
     * @return artikkelnavn (gulvspon)
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
     * @see no.ugland.utransprod.model.PackableListItem#getAttributeInfo()
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
     * @return garasjetype
     */
    public String getConstructionTypeName() {
	return constructionTypeName;
    }

    /**
     * @param constructionTypeName
     */
    public void setConstructionTypeName(String constructionTypeName) {
	this.constructionTypeName = constructionTypeName;
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
     * @return info om bredde og høyde
     */
    public String getInfo() {
	return info;
    }

    /**
     * @param info
     */
    public void setInfo(String info) {
	this.info = info;
    }

    /**
     * @see no.ugland.utransprod.model.PackableListItem#getLoadingDate()
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
     * @see no.ugland.utransprod.model.PackableListItem#getNumberOfItems()
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
     * @return ordrelinjeid
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
     * @see no.ugland.utransprod.model.PackableListItem#getColli()
     */
    public Colli getColli() {
	return colli;
    }

    /**
     * @param colli
     */
    public void setColli(Colli colli) {
	this.colli = colli;
    }

    /**
     * @see no.ugland.utransprod.model.PackableListItem#getTransportDetails()
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
     * @return transportår
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
	if (!(other instanceof GulvsponPackageV))
	    return false;
	GulvsponPackageV castOther = (GulvsponPackageV) other;
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
	return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("orderLineId", orderLineId).append("customerDetails", customerDetails)
		.append("orderNr", orderNr).append("address", address).append("info", info).append("constructionTypeName", constructionTypeName)
		.append("articleName", articleName).append("attributeInfo", attributeInfo).append("numberOfItems", numberOfItems)
		.append("loadingDate", loadingDate).append("colli", colli).append("transportDetails", transportDetails).append("comment", comment)
		.append("transportYear", transportYear).append("transportWeek", transportWeek).toString();
    }

    /**
     * @see no.ugland.utransprod.gui.model.TextRenderable#getOrderString()
     */
    public String getOrderString() {
	StringBuffer buffer = new StringBuffer(customerDetails);

	buffer.append(" - ").append(orderNr).append("\n").append(address).append(" ,").append(constructionTypeName).append(",").append(info);

	return buffer.toString();
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
     * @return opplatingstid
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
     * @return etterleveringid
     */
    public Integer getPostShipmentId() {
	return postShipmentId;
    }

    /**
     * @param postShipmentId
     */
    public void setPostShipmentId(Integer postShipmentId) {
	this.postShipmentId = postShipmentId;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Applyable#isForPostShipment()
     */
    public Boolean isForPostShipment() {
	if (postShipmentId != null) {
	    return Boolean.TRUE;
	}
	return Boolean.FALSE;
    }

    /**
     * @see no.ugland.utransprod.model.PackableListItem#getProductAreaGroupName()
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

    public Ordln getOrdln() {
	return ordln;
    }

    public void setOrdln(Ordln ordln) {
	this.ordln = ordln;
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

    public void setProduced(Date produced) {
	// TODO Auto-generated method stub

    }

    public void setRelatedArticles(List<Applyable> relatedArticles) {
	// TODO Auto-generated method stub

    }

    public boolean isRelatedArticlesStarted() {
	// TODO Auto-generated method stub
	return false;
    }

    public String getProductionUnitName() {
	// TODO Auto-generated method stub
	return null;
    }

    public Integer getProbability() {
	// TODO Auto-generated method stub
	return null;
    }

    public BigDecimal getRealProductionHours() {
	// TODO Auto-generated method stub
	return null;
    }

}
