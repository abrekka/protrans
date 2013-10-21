package no.ugland.utransprod.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klase som representerer tabell CONSTRUCTION_TYPE_ARTICLE_ATTRIBUTE
 * 
 * @author atle.brekka
 * 
 */
public class ConstructionTypeArticleAttribute extends BaseObject implements
		IArticleAttribute {
	private static final long serialVersionUID = 1L;

	private Integer constructionTypeArticleAttributeId;

	private ConstructionTypeArticle constructionTypeArticle;

	private ArticleTypeAttribute articleTypeAttribute;

	private String constructionTypeArticleValue;

	private Integer dialogOrder;

	public ConstructionTypeArticleAttribute() {
		super();
	}

	/**
	 * @param constructionTypeArticleAttributeId
	 * @param constructionTypeArticle
	 * @param articleTypeAttribute
	 * @param constructionTypeArticleValue
	 * @param dialogOrder
	 */
	public ConstructionTypeArticleAttribute(
			Integer constructionTypeArticleAttributeId,
			ConstructionTypeArticle constructionTypeArticle,
			ArticleTypeAttribute articleTypeAttribute,
			String constructionTypeArticleValue, Integer dialogOrder) {
		super();
		this.constructionTypeArticleAttributeId = constructionTypeArticleAttributeId;
		this.constructionTypeArticle = constructionTypeArticle;
		this.articleTypeAttribute = articleTypeAttribute;
		this.constructionTypeArticleValue = constructionTypeArticleValue;
		this.dialogOrder = dialogOrder;
	}

	/**
	 * @return attributt for artikkel
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
	 * @return artikkel for garasjetype
	 */
	public ConstructionTypeArticle getConstructionTypeArticle() {
		return constructionTypeArticle;
	}

	/**
	 * @param constructionTypeArticle
	 */
	public void setConstructionTypeArticle(
			ConstructionTypeArticle constructionTypeArticle) {
		this.constructionTypeArticle = constructionTypeArticle;
	}

	/**
	 * @return id
	 */
	public Integer getConstructionTypeArticleAttributeId() {
		return constructionTypeArticleAttributeId;
	}

	/**
	 * @param constructionTypeArticleAttributeId
	 */
	public void setConstructionTypeArticleAttributeId(
			Integer constructionTypeArticleAttributeId) {
		this.constructionTypeArticleAttributeId = constructionTypeArticleAttributeId;
	}

	/**
	 * @return verdi
	 */
	public String getConstructionTypeArticleValue() {
		return constructionTypeArticleValue;
	}

	/**
	 * @param constructionTypeArticleValue
	 */
	public void setConstructionTypeArticleValue(
			String constructionTypeArticleValue) {
		this.constructionTypeArticleValue = constructionTypeArticleValue;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ConstructionTypeArticleAttribute))
			return false;
		ConstructionTypeArticleAttribute castOther = (ConstructionTypeArticleAttribute) other;
		return new EqualsBuilder().append(constructionTypeArticle,
				castOther.constructionTypeArticle).append(articleTypeAttribute,
				castOther.articleTypeAttribute).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(constructionTypeArticle).append(
				articleTypeAttribute).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return articleTypeAttribute.getAttribute().getName();
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getAttributeName()
	 */
	public String getAttributeName() {
		return articleTypeAttribute.getAttribute().getName();
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getAttributeValue()
	 */
	public String getAttributeValue() {
		return constructionTypeArticleValue;
	}

	/**
	 * Konverterer liste av attributter til interface
	 * 
	 * @param attributes
	 * @return interfacer
	 */
	public static Set<IArticleAttribute> convertToInterface(
			Set<ConstructionTypeArticleAttribute> attributes) {
		Set<IArticleAttribute> convertList = new HashSet<IArticleAttribute>();
		for (ConstructionTypeArticleAttribute attribute : attributes) {
			convertList.add(attribute);
		}
		return convertList;
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setAttributeValue(java.lang.String)
	 */
	public void setAttributeValue(String aValue) {
		// String oldValue = getAttributeValue();
		this.setConstructionTypeArticleValue(aValue);
		// firePropertyChange("attributeValue", oldValue, aValue);
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getArticleName()
	 */
	public String getArticleName() {
		return constructionTypeArticle.getArticleType().getArticleTypeName();
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getNumberOfItems()
	 */
	public Integer getNumberOfItems() {
		return constructionTypeArticle.getNumberOfItems();
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setNumberOfItems(java.lang.Integer)
	 */
	public void setNumberOfItems(Integer numberOfItems) {
		constructionTypeArticle.setNumberOfItems(numberOfItems);

	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getChoices()
	 */
	public List<String> getChoices() {
		ArrayList<String> list = new ArrayList<String>();
		if (articleTypeAttribute != null) {
			Set<AttributeChoice> choices = articleTypeAttribute.getAttribute()
					.getAttributeChoices();
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
		if (articleTypeAttribute != null) {
			Integer yesNo = articleTypeAttribute.getAttribute().getYesNo();
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
	 * @see no.ugland.utransprod.model.IArticleAttribute#getNumberOfItemsLong()
	 */
	public Long getNumberOfItemsLong() {
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setDecimalSeparatorAlwaysShown(false);
		decimalFormat.setParseIntegerOnly(true);
		if (constructionTypeArticle.getNumberOfItems() != null) {
			return Long.valueOf(decimalFormat.format(constructionTypeArticle
					.getNumberOfItems()));
		}
		return null;

	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setNumberOfItemsLong(java.lang.Long)
	 */
	public void setNumberOfItemsLong(Long numberOfItems) {
		if (numberOfItems != null) {
			constructionTypeArticle.setNumberOfItems(Integer
					.valueOf(numberOfItems.intValue()));
		} else {
			constructionTypeArticle.setNumberOfItems(null);
		}

	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#getDialogOrderArticle()
	 */
	public Integer getDialogOrderArticle() {
		return constructionTypeArticle.getDialogOrder();
	}

	/**
	 * @see no.ugland.utransprod.model.IArticleAttribute#setDialogOrderArticle(java.lang.Integer)
	 */
	public void setDialogOrderArticle(Integer dialogOrder) {
		constructionTypeArticle.setDialogOrder(dialogOrder);

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
		return new ConstructionTypeArticleAttribute();
	}

    public String getAttributeDataType() {
        return articleTypeAttribute.getAttributeDataType();
    }

}
