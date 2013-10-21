package no.ugland.utransprod.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som representerer tabell ORDER_LINE_ATTRIBUTE
 * 
 * @author atle.brekka
 */
public class OrderLineAttribute extends BaseObject implements IArticleAttribute {
	private static final long serialVersionUID = 1L;

	private Integer orderLineAttributeId;

	private OrderLine orderLine;

	private ConstructionTypeArticleAttribute constructionTypeArticleAttribute;

	private ConstructionTypeAttribute constructionTypeAttribute;

	private ArticleTypeAttribute articleTypeAttribute;

	private String orderLineAttributeValue = "";

	private Integer dialogOrder;

	private String orderLineAttributeName;
	public static final OrderLineAttribute UNKNOWN = new OrderLineAttribute() {

		private static final long serialVersionUID = 1L;

		@Override
		public String getAttributeValue() {
			return "";
		}

		@Override
		public String getAttributeName() {
			return "UNKNOWN";
		}

	};

	public OrderLineAttribute() {
		super();
	}

	/**
	 * @param orderLineAttributeId
	 * @param orderLine
	 * @param constructionTypeArticleAttribute
	 * @param constructionTypeAttribute
	 * @param articleTypeAttribute
	 * @param orderLineAttributeValue
	 * @param dialogOrder
	 * @param orderLineAttributeName
	 */
	public OrderLineAttribute(Integer orderLineAttributeId,
			OrderLine orderLine,
			ConstructionTypeArticleAttribute constructionTypeArticleAttribute,
			ConstructionTypeAttribute constructionTypeAttribute,
			ArticleTypeAttribute articleTypeAttribute,
			String orderLineAttributeValue, Integer dialogOrder,
			String orderLineAttributeName) {
		super();
		this.orderLineAttributeId = orderLineAttributeId;
		this.orderLine = orderLine;
		this.constructionTypeArticleAttribute = constructionTypeArticleAttribute;
		this.constructionTypeAttribute = constructionTypeAttribute;
		this.articleTypeAttribute = articleTypeAttribute;
		this.orderLineAttributeValue = orderLineAttributeValue;
		this.dialogOrder = dialogOrder;
		this.orderLineAttributeName = orderLineAttributeName;
	}

	/**
	 * @return artikkeltattributt
	 */
	public ArticleTypeAttribute getArticleTypeAttribute() {
		return articleTypeAttribute;
	}

	/**
	 * @param articleTypeAttribute
	 */
	public void setArticleTypeAttribute(
			ArticleTypeAttribute articleTypeAttribute) {
		this.articleTypeAttribute = articleTypeAttribute;
	}

	/**
	 * @return artikkelattributt for garasjetype
	 */
	public ConstructionTypeArticleAttribute getConstructionTypeArticleAttribute() {
		return constructionTypeArticleAttribute;
	}

	/**
	 * @param constructionTypeArticleAttribute
	 */
	public void setConstructionTypeArticleAttribute(
			ConstructionTypeArticleAttribute constructionTypeArticleAttribute) {
		this.constructionTypeArticleAttribute = constructionTypeArticleAttribute;
	}

	/**
	 * @return attributt for garasjetype
	 */
	public ConstructionTypeAttribute getConstructionTypeAttribute() {
		return constructionTypeAttribute;
	}

	/**
	 * @param constructionTypeAttribute
	 */
	public void setConstructionTypeAttribute(
			ConstructionTypeAttribute constructionTypeAttribute) {
		this.constructionTypeAttribute = constructionTypeAttribute;
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
	 * @return id
	 */
	public Integer getOrderLineAttributeId() {
		return orderLineAttributeId;
	}

	/**
	 * @param orderLineAttributeId
	 */
	public void setOrderLineAttributeId(Integer orderLineAttributeId) {
		this.orderLineAttributeId = orderLineAttributeId;
	}

	/**
	 * @return verdi
	 */
	public String getOrderLineAttributeValue() {
		return orderLineAttributeValue;
	}

	/**
	 * @param orderLineAttributeValue
	 */
	public void setOrderLineAttributeValue(String orderLineAttributeValue) {
		this.orderLineAttributeValue = orderLineAttributeValue;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		if (orderLineAttributeName != null) {
			return orderLineAttributeName;
		} else if (articleTypeAttribute != null) {
			return articleTypeAttribute.getAttribute().getName();
		} else if (constructionTypeArticleAttribute != null) {
			return constructionTypeArticleAttribute.getArticleTypeAttribute()
					.getAttribute().getName();
		} else if (constructionTypeAttribute != null) {
			return constructionTypeAttribute.getAttribute().getName();
		}
		return "";
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getAttributeName()
	 */
	public String getAttributeName() {
		return toString();
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getAttributeValue()
	 */
	public String getAttributeValue() {
		return this.orderLineAttributeValue;
	}

	/**
	 * Konverterer liste av attributter til interfacer
	 * 
	 * @param attributes
	 * @return interfacer
	 */
	public static Set<IArticleAttribute> convertToInterface(
			Set<OrderLineAttribute> attributes) {
		HashSet<IArticleAttribute> convertList = new HashSet<IArticleAttribute>();
		for (OrderLineAttribute attribute : attributes) {
			convertList.add(attribute);
		}
		return convertList;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setAttributeValue(java.lang.String)
	 */
	public void setAttributeValue(String aValue) {
		// String oldstring = getOrderLineAttributeValue();
		setOrderLineAttributeValue(aValue);
		// firePropertyChange("attributeValue", oldstring, aValue);

	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getArticleName()
	 */
	public String getArticleName() {
		if (articleTypeAttribute != null) {
			return articleTypeAttribute.getArticleType().getArticleTypeName();
		} else if (constructionTypeArticleAttribute != null) {
			return constructionTypeArticleAttribute.getArticleTypeAttribute()
					.getArticleType().getArticleTypeName();
		} else if (constructionTypeAttribute != null) {
			return constructionTypeAttribute.getConstructionType().getName();
		}
		return "";
	}

	public Attribute getAttribute() {
		if (articleTypeAttribute != null) {
			return articleTypeAttribute.getAttribute();
		} else if (constructionTypeArticleAttribute != null) {
			return constructionTypeArticleAttribute.getArticleTypeAttribute()
					.getAttribute();
		} else if (constructionTypeAttribute != null) {
			return constructionTypeAttribute.getAttribute();
		}
		return Attribute.UNKNOWN;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getNumberOfItems()
	 */
	public Integer getNumberOfItems() {
		return orderLine.getNumberOfItems();
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getNumberOfItemsLong()
	 */
	public Long getNumberOfItemsLong() {
		if (orderLine.getNumberOfItems() != null) {
			return Long.valueOf(getNumberOfItems());
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setNumberOfItems(java.lang.Integer)
	 */
	public void setNumberOfItems(Integer numberOfItems) {
		orderLine.setNumberOfItems(numberOfItems);

	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setNumberOfItemsLong(java.lang.Long)
	 */
	public void setNumberOfItemsLong(Long numberOfItems) {
		if (numberOfItems != null) {
			setNumberOfItems(Integer.valueOf(numberOfItems.intValue()));
		} else {
			setNumberOfItems(null);
		}

	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getChoices()
	 */
	public List<String> getChoices() {
		List<String> list = new ArrayList<String>();
		if (articleTypeAttribute != null) {
			Set<AttributeChoice> choices = articleTypeAttribute.getAttribute()
					.getAttributeChoices();
			if (choices != null) {
				for (AttributeChoice choice : choices) {
					list.add(choice.getChoiceValue());
				}
			}
		} else if (constructionTypeArticleAttribute != null) {
			list = constructionTypeArticleAttribute.getChoices();
		} else if (constructionTypeAttribute != null) {
			Set<AttributeChoice> choices = constructionTypeAttribute
					.getAttribute().getAttributeChoices();
			if (choices != null) {
				for (AttributeChoice choice : choices) {
					list.add(choice.getChoiceValue());
				}
			}
		}
		return list;

	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#isYesNo()
	 */
	public Boolean isYesNo() {
		Integer yesNo = null;

		if (articleTypeAttribute != null) {
			yesNo = articleTypeAttribute.getAttribute().getYesNo();
			if (yesNo != null && yesNo > 0) {
				return Boolean.TRUE;
			}
		} else if (constructionTypeArticleAttribute != null) {
			return constructionTypeArticleAttribute.isYesNo();
		} else if (constructionTypeAttribute != null) {
			yesNo = constructionTypeAttribute.getAttribute().getYesNo();
			if (yesNo != null && yesNo > 0) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * @return true dersom attributt har valg
	 */
	public Boolean isSelection() {

		if (articleTypeAttribute != null) {
			if (articleTypeAttribute.getAttribute().getChoices() != null
					&& articleTypeAttribute.getAttribute().getChoices().size() > 1) {
				return Boolean.TRUE;
			}
		} else if (constructionTypeArticleAttribute != null) {
			if (constructionTypeArticleAttribute.getArticleTypeAttribute()
					.getAttribute().getChoices() != null
					&& constructionTypeArticleAttribute
							.getArticleTypeAttribute().getAttribute()
							.getChoices().size() > 1) {
				return Boolean.TRUE;
			}
		} else if (constructionTypeAttribute != null) {
			if (constructionTypeAttribute.getAttribute().getChoices() != null
					&& constructionTypeAttribute.getAttribute().getChoices()
							.size() > 1) {
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
	 * @return dialogrekkefølge
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
	 * @return attributtnavn
	 */
	public String getOrderLineAttributeName() {
		if (orderLineAttributeName != null
				&& orderLineAttributeName.length() != 0) {
			return orderLineAttributeName;
		} else if (articleTypeAttribute != null) {
			return articleTypeAttribute.getAttribute().getName();
		} else if (constructionTypeArticleAttribute != null) {
			return constructionTypeArticleAttribute.getArticleTypeAttribute()
					.getAttribute().getName();
		} else if (constructionTypeAttribute != null) {
			return constructionTypeAttribute.getAttribute().getName();
		}
		return "";

	}

	/**
	 * @param orderLineAttributeName
	 */
	public void setOrderLineAttributeName(String orderLineAttributeName) {
		this.orderLineAttributeName = orderLineAttributeName;
	}

	/**
	 * @return true dersom spesielle hensyn
	 */
	public Boolean isSpecialConcern() {
		if (constructionTypeArticleAttribute != null
				&& constructionTypeArticleAttribute.getArticleTypeAttribute()
						.getAttribute().getSpecialConcern() != null
				&& constructionTypeArticleAttribute.getArticleTypeAttribute()
						.getAttribute().getSpecialConcern() == 1) {
			return Boolean.TRUE;
		} else if (constructionTypeAttribute != null
				&& constructionTypeAttribute.getAttribute().getSpecialConcern() != null
				&& constructionTypeAttribute.getAttribute().getSpecialConcern() == 1) {
			return Boolean.TRUE;
		} else if (articleTypeAttribute != null
				&& articleTypeAttribute.getAttribute().getSpecialConcern() != null
				&& articleTypeAttribute.getAttribute().getSpecialConcern() == 1) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @return attributtformel
	 */
	public String getAttributeFormula() {

		ArticleTypeAttribute attribute = null;
		if (constructionTypeArticleAttribute != null) {
			attribute = constructionTypeArticleAttribute
					.getArticleTypeAttribute();
		} else if (articleTypeAttribute != null) {
			attribute = articleTypeAttribute;
		}
		if (attribute != null && attribute.getAttributeFormula() != null
				&& attribute.getAttributeFormula().length() != 0) {
			return attribute.getAttributeFormula();
		}
		return null;
	}

	/**
	 * @return attributtverdi som tall
	 */
	public String getAttributeNumberValue() {
		String string = "0";
		if (orderLineAttributeValue != null) {
			Pattern pattern = Pattern.compile("\\d*\\.?\\d*");
			Matcher matcher = pattern.matcher(orderLineAttributeValue);
			if (matcher.lookingAt()) {
				string = matcher.group();
			}

		}
		return string.length() != 0 ? string : "0";
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getNewInstance()
	 */
	public IArticleAttribute getNewInstance() {
		return new OrderLineAttribute();
	}

	public static OrderLineAttribute cloneAttribute(
			ConstructionTypeAttribute attribute) {
		OrderLineAttribute orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setAttributeValue(attribute.getAttributeValue());
		orderLineAttribute.setConstructionTypeAttribute(attribute);
		orderLineAttribute
				.setOrderLineAttributeName(attribute.getArticleName());
		return orderLineAttribute;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OrderLineAttribute))
			return false;
		OrderLineAttribute castOther = (OrderLineAttribute) other;
		return new EqualsBuilder().append(orderLine, castOther.orderLine)
				.append(constructionTypeArticleAttribute,
						castOther.constructionTypeArticleAttribute).append(
						constructionTypeAttribute,
						castOther.constructionTypeAttribute).append(
						articleTypeAttribute, castOther.articleTypeAttribute)
				.append(orderLineAttributeValue,
						castOther.orderLineAttributeValue).append(
						orderLineAttributeName,
						castOther.orderLineAttributeName).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderLine).append(
				constructionTypeArticleAttribute).append(
				constructionTypeAttribute).append(articleTypeAttribute).append(
				orderLineAttributeValue).append(orderLineAttributeName)
				.toHashCode();
	}

	public String getAttributeDataType() {
		return articleTypeAttribute != null ? articleTypeAttribute
				.getAttributeDataType()
				: constructionTypeArticleAttribute != null ? constructionTypeArticleAttribute
						.getAttributeDataType()
						: constructionTypeAttribute.getAttributeDataType();
	}
}
