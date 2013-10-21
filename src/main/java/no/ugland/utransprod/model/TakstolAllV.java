package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.util.TakstolInterface;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TakstolAllV extends BaseObject implements TakstolInterface {
	    protected Integer orderLineId;
	    private Integer orderId;
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
	    protected Date produced;
	    private Date productionDate;
	    private String productionUnitName;
	    private Integer defaultArticle;

		private List<TakstolInterface> relatedArticles;
		private BigDecimal ownProduction;
		private BigDecimal ownInternalProduction;
		private BigDecimal price;
		private BigDecimal dc1p;
		private String customerName;
		private BigDecimal deliveryCost;
		private Date packlistReady;
		private Integer isDefault;
		private Integer probability;
		private Integer sent;
		

		public Integer getOrderLineId() {
			return orderLineId;
		}

		public void setOrderLineId(Integer orderLineId) {
			this.orderLineId = orderLineId;
		}

		public Integer getCustomerNr() {
			return customerNr;
		}

		public void setCustomerNr(Integer customerNr) {
			this.customerNr = customerNr;
		}

		public String getCustomerDetails() {
			return customerDetails;
		}

		public void setCustomerDetails(String customerDetails) {
			this.customerDetails = customerDetails;
		}

		public String getOrderNr() {
			return orderNr;
		}

		public void setOrderNr(String orderNr) {
			this.orderNr = orderNr;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

		public String getConstructionTypeName() {
			return constructionTypeName;
		}

		public void setConstructionTypeName(String constructionTypeName) {
			this.constructionTypeName = constructionTypeName;
		}

		public String getArticleName() {
			return articleName;
		}

		public void setArticleName(String articleName) {
			this.articleName = articleName;
		}

		public String getAttributeInfo() {
			return attributeInfo;
		}

		public void setAttributeInfo(String attributeInfo) {
			this.attributeInfo = attributeInfo;
		}

		public Integer getNumberOfItems() {
			return numberOfItems;
		}

		public void setNumberOfItems(Integer numberOfItems) {
			this.numberOfItems = numberOfItems;
		}

		public Date getLoadingDate() {
			return loadingDate;
		}

		public void setLoadingDate(Date loadingDate) {
			this.loadingDate = loadingDate;
		}

		public String getTransportDetails() {
			return transportDetails;
		}

		public void setTransportDetails(String transportDetails) {
			this.transportDetails = transportDetails;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public Integer getTransportYear() {
			return transportYear;
		}

		public void setTransportYear(Integer transportYear) {
			this.transportYear = transportYear;
		}

		public Integer getTransportWeek() {
			return transportWeek;
		}

		public void setTransportWeek(Integer transportWeek) {
			this.transportWeek = transportWeek;
		}

		public String getLoadTime() {
			return loadTime;
		}

		public void setLoadTime(String loadTime) {
			this.loadTime = loadTime;
		}

		public Integer getPostShipmentId() {
			return postShipmentId;
		}

		public void setPostShipmentId(Integer postShipmentId) {
			this.postShipmentId = postShipmentId;
		}

		public String getProductAreaGroupName() {
			return productAreaGroupName;
		}

		public void setProductAreaGroupName(String productAreaGroupName) {
			this.productAreaGroupName = productAreaGroupName;
		}

		public Date getActionStarted() {
			return actionStarted;
		}

		public void setActionStarted(Date actionStarted) {
			this.actionStarted = actionStarted;
		}

		public Date getProduced() {
			return produced;
		}

		public void setProduced(Date produced) {
			this.produced = produced;
		}

		public Date getProductionDate() {
			return productionDate;
		}

		public void setProductionDate(Date productionDate) {
			this.productionDate = productionDate;
		}

		public String getProductionUnitName() {
			return productionUnitName;
		}

		public void setProductionUnitName(String productionUnitName) {
			this.productionUnitName = productionUnitName;
		}

		public Integer getDefaultArticle() {
			return defaultArticle;
		}

		public void setDefaultArticle(Integer defaultArticle) {
			this.defaultArticle = defaultArticle;
		}

		public List<TakstolInterface> getRelatedArticles() {
			return relatedArticles;
		}

		public void setRelatedArticles(List<TakstolInterface> relatedArticles) {
			this.relatedArticles = relatedArticles;
		}

		@Override
		public boolean equals(final Object other) {
			if (!(other instanceof TakstolAllV))
				return false;
			TakstolAllV castOther = (TakstolAllV) other;
			return new EqualsBuilder().append(orderLineId, castOther.orderLineId)
					.isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(orderLineId).toHashCode();
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this).append("orderLineId", orderLineId)
					.toString();
		}

		public Integer getOrderId() {
			return orderId;
		}

		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}
		
		public TakstolAllV clone() {
			TakstolAllV tmpObject = new TakstolAllV();
			tmpObject.setActionStarted(this.getActionStarted());
			tmpObject.setAddress(this.getAddress());
			tmpObject.setArticleName(this.getArticleName());

			tmpObject.setComment(this.getComment());
			tmpObject.setConstructionTypeName(this.getConstructionTypeName());
			tmpObject.setCustomerDetails(this.getCustomerDetails());
			tmpObject.setCustomerNr(this.getCustomerNr());
			tmpObject.setInfo(this.getInfo());
			tmpObject.setLoadingDate(this.getLoadingDate());
			tmpObject.setLoadTime(this.getLoadTime());
			tmpObject.setOrderNr(this.getOrderNr());
			tmpObject.setTransportDetails(this.getTransportDetails());
			tmpObject.setTransportWeek(this.getTransportWeek());
			tmpObject.setTransportYear(this.getTransportYear());
			tmpObject.setProductAreaGroupName(this.getProductAreaGroupName());
			tmpObject.setProduced(this.getProduced());
			
			return tmpObject;

		}
		public void addRelatedArticle(TakstolInterface item) {
			relatedArticles = relatedArticles == null ? new ArrayList<TakstolInterface>()
					: relatedArticles;
			relatedArticles.add(item);

		}

		public BigDecimal getOwnProduction() {
			return ownProduction;
		}

		public void setOwnProduction(BigDecimal ownProduction) {
			this.ownProduction = ownProduction;
		}

		public BigDecimal getOwnInternalProduction() {
			return ownInternalProduction;
		}

		public void setOwnInternalProduction(BigDecimal ownInternalProduction) {
			this.ownInternalProduction = ownInternalProduction;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public BigDecimal getCalculatedPrice() {
			BigDecimal pris=price!=null?price:BigDecimal.ZERO;
			BigDecimal antall=numberOfItems!=null?BigDecimal.valueOf(numberOfItems):BigDecimal.ZERO;
			BigDecimal rabatt=dc1p!=null?dc1p:BigDecimal.ZERO;
			BigDecimal rabattProsent=BigDecimal.valueOf(1).subtract(rabatt.divide(BigDecimal.valueOf(100)));
			return antall.multiply(pris).multiply(rabattProsent) ;
		}

		public BigDecimal getDc1p() {
			return dc1p;
		}

		public void setDc1p(BigDecimal dc1p) {
			this.dc1p = dc1p;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public BigDecimal getDeliveryCost() {
			return deliveryCost;
		}

		public void setDeliveryCost(BigDecimal deliveryCost) {
			this.deliveryCost = deliveryCost;
		}

		public Date getPacklistReady() {
			return packlistReady;
		}

		public void setPacklistReady(Date packlistReady) {
			this.packlistReady = packlistReady;
		}

		public Integer getIsDefault() {
			return isDefault;
		}

		public void setIsDefault(Integer isDefault) {
			this.isDefault = isDefault;
		}

		public Integer getProbability() {
			return probability;
		}

		public void setProbability(Integer probability) {
			this.probability = probability;
		}

		public Integer getSent() {
			return sent;
		}

		public void setSent(Integer sent) {
			this.sent = sent;
		}
}
