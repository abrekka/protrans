package no.ugland.utransprod.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.CompareToBuilder;

public abstract class PackageProduction extends BaseObject  {
    protected Integer orderLineId;

    protected Integer customerNr;
    protected String customerDetails;

    protected String orderNr;

    protected String address;

    protected String info;

    protected String constructionTypeName;

    protected String articleName;

    protected String attributeInfo;

    protected Integer numberOfItems;

    protected Date loadingDate;
    protected String transportDetails;

    protected String comment;

    protected Integer transportYear;

    protected Integer transportWeek;

    protected String loadTime;

    protected Integer postShipmentId;
    protected String productAreaGroupName;
    protected Date actionStarted;
    protected Colli colli;
    protected Integer probability;

    /**
     * Visma
     */
    protected Ordln ordln;


	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof PackageProduction))
			return false;
		PackageProduction castOther = (PackageProduction) other;
		return new EqualsBuilder().append(orderLineId, castOther.orderLineId)
				.append(customerNr, castOther.customerNr)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderLineId)
		.append(customerNr)
				.toHashCode();
	}

	public PackageProduction() {
        super();
    }

    public PackageProduction(final Integer aOrderLineId, final Integer aCustomerNr,
            final String someCustomerDetails, final String aOrderNr, final String aAddress,
            final String someInfo, final String aConstructionTypeName, final String aArticleName,
            final String someAttributeInfo, final Integer aNumberOfItems, final Date aLoadingDate,
            final String someTransportDetails, final String aComment, final Integer aTransportYear,
            final Integer aTransportWeek, final String aLoadTime, final Integer aPostShipmentId,
            final String aProductAreaGroupName, final Date dateActionStarted,final Colli aColli) {
        super();
        this.orderLineId = aOrderLineId;
        this.customerNr = aCustomerNr;
        this.customerDetails = someCustomerDetails;
        this.orderNr = aOrderNr;
        this.address = aAddress;
        this.info = someInfo;
        this.constructionTypeName = aConstructionTypeName;
        this.articleName = aArticleName;
        this.attributeInfo = someAttributeInfo;
        this.numberOfItems = aNumberOfItems;
        this.loadingDate = aLoadingDate;
        this.transportDetails = someTransportDetails;
        this.comment = aComment;
        this.transportYear = aTransportYear;
        this.transportWeek = aTransportWeek;
        this.loadTime = aLoadTime;
        this.postShipmentId = aPostShipmentId;
        this.productAreaGroupName = aProductAreaGroupName;
        this.actionStarted = dateActionStarted;
        this.colli=aColli;
    }

    /**
     * @return kundenummer
     */
    public final Integer getCustomerNr() {
        return customerNr;
    }

    /**
     * @param aCustomerNr
     */
    public final void setCustomerNr(final Integer aCustomerNr) {
        this.customerNr = aCustomerNr;
    }

    /**
     * @return id
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
     * @return konstruksjonstypenavn
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
     * @return info
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
     * @return opplastingstidspunkt
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
     * @see no.ugland.utransprod.gui.model.TextRenderable#getOrderString()
     */
    public String getOrderString() {
        StringBuffer buffer = new StringBuffer(customerDetails);

        buffer.append(" - ").append(orderNr).append("\n").append(address).append(" ,").append(
                constructionTypeName).append(",").append(info);

        return buffer.toString();
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
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("orderLineId", orderLineId)
                .toString();
    }

	public Integer getProbability() {
		return probability;
	}

	public void setProbability(Integer probability) {
		this.probability = probability;
	}
}
