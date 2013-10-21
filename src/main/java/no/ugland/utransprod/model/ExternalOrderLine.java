package no.ugland.utransprod.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Modellklasse for tabell EXTERNAL_ORDER_LINE
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderLine extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer externalOrderLineId;

	/**
	 * 
	 */
	private String articleName;

	/**
	 * 
	 */
	private OrderLine orderLine;

	/**
	 * 
	 */
	private ExternalOrder externalOrder;

	/**
	 * 
	 */
	private Integer numberOfItems;

	/**
	 * 
	 */
	private String attributeInfo;

	/**
	 * 
	 */
	private Set<ExternalOrderLineAttribute> externalOrderLineAttributes;

	/**
	 * 
	 */
	public ExternalOrderLine() {
		super();
	}

	/**
	 * @param externalOrderLineId
	 * @param articleName
	 * @param orderLine
	 * @param externalOrder
	 * @param attributeInfo
	 * @param externalOrderLineAttributes
	 * @param numberOfItems
	 */
	public ExternalOrderLine(Integer externalOrderLineId, String articleName,
			OrderLine orderLine, ExternalOrder externalOrder,
			String attributeInfo,
			Set<ExternalOrderLineAttribute> externalOrderLineAttributes,
			Integer numberOfItems) {
		super();
		this.externalOrderLineId = externalOrderLineId;
		this.orderLine = orderLine;
		this.externalOrder = externalOrder;
		this.attributeInfo = attributeInfo;
		this.articleName = articleName;
		this.externalOrderLineAttributes = externalOrderLineAttributes;
		this.numberOfItems = numberOfItems;
	}

	/**
	 * @return ekstern ordre
	 */
	public ExternalOrder getExternalOrder() {
		return externalOrder;
	}

	/**
	 * @param externalOrder
	 */
	public void setExternalOrder(ExternalOrder externalOrder) {
		this.externalOrder = externalOrder;
	}

	/**
	 * @return id
	 */
	public Integer getExternalOrderLineId() {
		return externalOrderLineId;
	}

	/**
	 * @param externalOrderLineId
	 */
	public void setExternalOrderLineId(Integer externalOrderLineId) {
		this.externalOrderLineId = externalOrderLineId;
	}

	/**
	 * @return ordrelinje
	 */
	public OrderLine getOrderLine() {
		return orderLine;
	}

	/**
	 * @param orderLine
	 */
	public void setOrderLine(OrderLine orderLine) {
		this.orderLine = orderLine;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ExternalOrderLine))
			return false;
		ExternalOrderLine castOther = (ExternalOrderLine) other;
		return new EqualsBuilder().append(orderLine, castOther.orderLine)
				.append(externalOrder, castOther.externalOrder).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderLine).append(externalOrder)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"externalOrderLineId", externalOrderLineId).append("orderLine",
				orderLine).append("externalOrder", externalOrder).toString();
	}

	/**
	 * @return info
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
	 * @return attributter
	 */
	public Set<ExternalOrderLineAttribute> getExternalOrderLineAttributes() {
		return externalOrderLineAttributes;
	}

	/**
	 * @param externalOrderLineAttributes
	 */
	public void setExternalOrderLineAttributes(
			Set<ExternalOrderLineAttribute> externalOrderLineAttributes) {
		this.externalOrderLineAttributes = externalOrderLineAttributes;
	}

	/**
	 * @return attributter som en lang streng
	 */
	public String getExternalOrderLineAttributesAsString() {
		StringBuffer tmpBuffer = new StringBuffer("");

		if (externalOrderLineAttributes != null) {
			for (ExternalOrderLineAttribute attribute : externalOrderLineAttributes) {
				tmpBuffer.append(attribute.getExternalOrderLineAttributeName())
						.append(":").append(
								attribute.getExternalOrderLineAttributeValue())
						.append(" ");
			}
		}
		return tmpBuffer.toString();
	}

	/**
	 * @return antall
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
}
