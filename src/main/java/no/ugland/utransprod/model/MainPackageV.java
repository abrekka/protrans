package no.ugland.utransprod.model;

import java.util.Date;

import no.ugland.utransprod.gui.model.TextRenderable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view MAIN_PACKAGE_V
 * 
 * @author atle.brekka
 * 
 */
public class MainPackageV extends BaseObject implements TextRenderable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * 
	 */
    private Integer orderId;

    /**
	 * 
	 */
    private String customerDetails;

    /**
	 * 
	 */
    private String orderNr;

    /**
	 * 
	 */
    private String address;

    /**
	 * 
	 */
    private String constructionTypeName;

    /**
	 * 
	 */
    private String info;

    /**
	 * 
	 */
    private String transportDetails;

    /**
	 * 
	 */
    private Integer packageReady;

    /**
	 * 
	 */
    private Date donePackage;

    /**
	 * 
	 */
    private String comment;

    /**
	 * 
	 */
    private Integer transportYear;

    /**
	 * 
	 */
    private Integer transportWeek;

    /**
	 * 
	 */
    private Integer customerNr;

    /**
	 * 
	 */
    private Date loadingDate;

    /**
	 * 
	 */
    private String productAreaGroupName;

    private Integer probability;

    private Integer productionWeek;

    /**
	 * 
	 */
    public MainPackageV() {
	super();
    }

    /**
     * @param orderId
     * @param customerDetails
     * @param transportDetails
     * @param packageReady
     * @param donePackage
     * @param comment
     * @param transportYear
     * @param transportWeek
     * @param orderNr
     * @param address
     * @param constructionTypeName
     * @param info
     * @param customerNr
     * @param loadingDate
     * @param productAreaGroupName
     */
    public MainPackageV(Integer orderId, String customerDetails, String transportDetails, Integer packageReady, Date donePackage, String comment,
	    Integer transportYear, Integer transportWeek, String orderNr, String address, String constructionTypeName, String info,
	    Integer customerNr, Date loadingDate, String productAreaGroupName) {
	super();
	this.orderId = orderId;
	this.customerDetails = customerDetails;
	this.transportDetails = transportDetails;
	this.packageReady = packageReady;
	this.donePackage = donePackage;
	this.comment = comment;
	this.transportYear = transportYear;
	this.transportWeek = transportWeek;
	this.orderNr = orderNr;

	this.address = address;

	this.constructionTypeName = constructionTypeName;

	this.info = info;
	this.customerNr = customerNr;
	this.loadingDate = loadingDate;
	this.productAreaGroupName = productAreaGroupName;
    }

    /**
     * @return dato for ferdig pakket
     */
    public Date getDonePackage() {
	return donePackage;
    }

    /**
     * @param donePackage
     */
    public void setDonePackage(Date donePackage) {
	this.donePackage = donePackage;
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
     * @return 1 dersom pakking er klar
     */
    public Integer getPackageReady() {
	return packageReady;
    }

    /**
     * @param packageReady
     */
    public void setPackageReady(Integer packageReady) {
	this.packageReady = packageReady;
    }

    /**
     * @return transportdetaljer
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
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof MainPackageV))
	    return false;
	MainPackageV castOther = (MainPackageV) other;
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
	return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("customerDetails", customerDetails).toString();
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
     * @return ordrenummer
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
     * @return opplastingsdato
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

    public Integer getProbability() {
	return probability;
    }

    public void setProbability(Integer probability) {
	this.probability = probability;
    }

    public Integer getProductionWeek() {
	return productionWeek;
    }

    public void setProductionWeek(Integer productionWeek) {
	this.productionWeek = productionWeek;
    }
}
