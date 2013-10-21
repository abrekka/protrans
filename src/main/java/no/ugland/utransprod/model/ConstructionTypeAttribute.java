package no.ugland.utransprod.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klassesom representerer tabell CONSTRUCTION_TYPE_ATTRIBUTE
 * 
 * @author atle.brekka
 * 
 */
public class ConstructionTypeAttribute extends BaseObject implements
		IArticleAttribute {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer constructionTypeAttributeId;

	/**
	 * 
	 */
	private ConstructionType constructionType;

	/**
	 * 
	 */
	private Attribute attribute;

	/**
	 * 
	 */
	private String attributeValue;

	/**
	 * 
	 */
	private Set<OrderLineAttribute> orderLineAttributes;

	/**
	 * 
	 */
	private Integer dialogOrder;

	/**
	 * 
	 */
	public ConstructionTypeAttribute() {
		super();
	}

	/**
	 * @param constructionTypeAttributeId
	 * @param constructionType
	 * @param attribute
	 * @param attributeValue
	 * @param orderLineAttributes
	 * @param dialogOrder
	 */
	public ConstructionTypeAttribute(Integer constructionTypeAttributeId,
			ConstructionType constructionType, Attribute attribute,
			String attributeValue, Set<OrderLineAttribute> orderLineAttributes,
			Integer dialogOrder) {
		super();
		this.constructionTypeAttributeId = constructionTypeAttributeId;
		this.constructionType = constructionType;
		this.attribute = attribute;
		this.attributeValue = attributeValue;
		this.orderLineAttributes = orderLineAttributes;
		this.dialogOrder = dialogOrder;
	}

	/**
	 * @return attributt
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute
	 */
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return garasjetype
	 */
	public ConstructionType getConstructionType() {
		return constructionType;
	}

	/**
	 * @param constructionType
	 */
	public void setConstructionType(ConstructionType constructionType) {
		this.constructionType = constructionType;
	}

	/**
	 * @return id
	 */
	public Integer getConstructionTypeAttributeId() {
		return constructionTypeAttributeId;
	}

	/**
	 * @param constructionTypeAttributeId
	 */
	public void setConstructionTypeAttributeId(
			Integer constructionTypeAttributeId) {
		this.constructionTypeAttributeId = constructionTypeAttributeId;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ConstructionTypeAttribute))
			return false;
		ConstructionTypeAttribute castOther = (ConstructionTypeAttribute) other;
		return new EqualsBuilder().append(constructionType,
				castOther.constructionType).append(attribute,
				castOther.attribute).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(constructionType).append(attribute)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return attribute.toString() + "-" + attributeValue + "-"
				+ Util.nullIntegerToString(dialogOrder);
	}

	/**
	 * @return verdi
	 */
	public String getAttributeValue() {
		return attributeValue;
	}

	/**
	 * @param attributeValue
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConstructionTypeAttribute clone() {
		return new ConstructionTypeAttribute(constructionTypeAttributeId,
				constructionType, attribute, attributeValue,
				orderLineAttributes, dialogOrder);
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getArticleName()
	 */
	public String getArticleName() {
		return "";
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getAttributeName()
	 */
	public String getAttributeName() {
		return attribute.getName();
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getAttributeValueBool()
	 */
	public Boolean getAttributeValueBool() {
		if (getAttributeValue() != null
				&& getAttributeValue().equalsIgnoreCase("Ja")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getChoices()
	 */
	public List<String> getChoices() {
		ArrayList<String> list = new ArrayList<String>();
		if (attribute != null) {
			Set<AttributeChoice> choices = attribute.getAttributeChoices();
			if (choices != null) {
				for (AttributeChoice choice : choices) {
					list.add(choice.getChoiceValue());
				}
			}
		}
		return list;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getNumberOfItems()
	 */
	public Integer getNumberOfItems() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#isYesNo()
	 */
	public Boolean isYesNo() {
		if (attribute != null) {
			Integer yesNo = attribute.getYesNo();
			if (yesNo != null && yesNo > 0) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setAttributeValueBool(java.lang.Boolean)
	 */
	public Boolean setAttributeValueBool(Boolean bool) {
		if (bool) {
			setAttributeValue("Ja");
		} else {
			setAttributeValue("Nei");
		}
		return bool;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setNumberOfItems(java.lang.Integer)
	 */
	public void setNumberOfItems(Integer numberOfItems) {

	}

	/**
	 * @return attributter
	 */
	public Set<OrderLineAttribute> getOrderLineAttributes() {
		return orderLineAttributes;
	}

	/**
	 * @param orderLineAttributes
	 */
	public void setOrderLineAttributes(
			Set<OrderLineAttribute> orderLineAttributes) {
		this.orderLineAttributes = orderLineAttributes;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getNumberOfItemsLong()
	 */
	public Long getNumberOfItemsLong() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setNumberOfItemsLong(java.lang.Long)
	 */
	public void setNumberOfItemsLong(Long numberOfItems) {
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getDialogOrderArticle()
	 */
	public Integer getDialogOrderArticle() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setDialogOrderArticle(java.lang.Integer)
	 */
	public void setDialogOrderArticle(Integer dialogOrder) {
	}

	/**
	 * @return rekkefølge
	 */
	public Integer getDialogOrder() {
		return dialogOrder;
	}

	/**
	 * @param dialogOrder
	 */
	public void setDialogOrder(Integer dialogOrder) {
		this.dialogOrder = dialogOrder;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getDialogOrderAttribute()
	 */
	public Integer getDialogOrderAttribute() {
		return dialogOrder;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setDialogOrderAttribute(java.lang.Integer)
	 */
	public void setDialogOrderAttribute(Integer dialogOrder) {
		this.dialogOrder = dialogOrder;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getOrderLine()
	 */
	public OrderLine getOrderLine() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getNewInstance()
	 */
	public IArticleAttribute getNewInstance() {
		return new ConstructionTypeAttribute();
	}

    public String getAttributeDataType() {
        return attribute.getAttributeDataType();
    }
}
