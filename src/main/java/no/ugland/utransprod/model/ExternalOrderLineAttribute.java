package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Modellklasse for tabell EXTERNAL_ORDER_LINE_ATTRIBUTE
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderLineAttribute extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer externalOrderLineAttributeId;

	/**
	 * 
	 */
	private ExternalOrderLine externalOrderLine;

	/**
	 * 
	 */
	private String externalOrderLineAttributeValue;

	/**
	 * 
	 */
	private String externalOrderLineAttributeName;

	/**
	 * 
	 */
	public ExternalOrderLineAttribute() {
		super();
	}

	/**
	 * @param externalOrderLineAttributeId
	 * @param externalOrderLine
	 * @param externalOrderLineAttributeValue
	 * @param externalOrderLineAttributeName
	 */
	public ExternalOrderLineAttribute(Integer externalOrderLineAttributeId,
			ExternalOrderLine externalOrderLine,
			String externalOrderLineAttributeValue,
			String externalOrderLineAttributeName) {
		super();
		this.externalOrderLineAttributeId = externalOrderLineAttributeId;
		this.externalOrderLine = externalOrderLine;
		this.externalOrderLineAttributeValue = externalOrderLineAttributeValue;
		this.externalOrderLineAttributeName = externalOrderLineAttributeName;
	}

	/**
	 * @return orderlinje
	 */
	public ExternalOrderLine getExternalOrderLine() {
		return externalOrderLine;
	}

	/**
	 * @param externalOrderLine
	 */
	public void setExternalOrderLine(ExternalOrderLine externalOrderLine) {
		this.externalOrderLine = externalOrderLine;
	}

	/**
	 * @return id
	 */
	public Integer getExternalOrderLineAttributeId() {
		return externalOrderLineAttributeId;
	}

	/**
	 * @param externalOrderLineAttributeId
	 */
	public void setExternalOrderLineAttributeId(
			Integer externalOrderLineAttributeId) {
		this.externalOrderLineAttributeId = externalOrderLineAttributeId;
	}

	/**
	 * @return verdi
	 */
	public String getExternalOrderLineAttributeValue() {
		return externalOrderLineAttributeValue;
	}

	/**
	 * @param externalOrderLineAttributeValue
	 */
	public void setExternalOrderLineAttributeValue(
			String externalOrderLineAttributeValue) {
		this.externalOrderLineAttributeValue = externalOrderLineAttributeValue;
	}

	/**
	 * @return attributtnavn
	 */
	public String getExternalOrderLineAttributeName() {
		return externalOrderLineAttributeName;
	}

	/**
	 * @param externalOrderLineAttributeName
	 */
	public void setExternalOrderLineAttributeName(
			String externalOrderLineAttributeName) {
		this.externalOrderLineAttributeName = externalOrderLineAttributeName;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ExternalOrderLineAttribute))
			return false;
		ExternalOrderLineAttribute castOther = (ExternalOrderLineAttribute) other;
		return new EqualsBuilder().append(externalOrderLine,
				castOther.externalOrderLine).append(
				externalOrderLineAttributeName,
				castOther.externalOrderLineAttributeName).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(externalOrderLine).append(
				externalOrderLineAttributeName).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"externalOrderLineAttributeValue",
				externalOrderLineAttributeValue).append(
				"externalOrderLineAttributeName",
				externalOrderLineAttributeName).toString();
	}
}
