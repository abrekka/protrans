package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.AttributeChoice;

/**
 * Brukes ved artikkelstatistikk
 * 
 * @author atle.brekka
 * 
 */
public class ArticleTypeAttributeCriteria extends ArticleTypeAttribute {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_VALUE_BOOLEAN = "attributeValueBoolean";

	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_VALUE_FROM = "attributeValueFrom";

	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_VALUE_TO = "attributeValueTo";

	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_NAME = "attributeName";

	/**
	 * 
	 */
	private String attributeValueFrom;

	/**
	 * 
	 */
	private String attributeValueTo;

	/**
	 * @param attribute
	 */
	public ArticleTypeAttributeCriteria(ArticleTypeAttribute attribute) {
		super(null, attribute.getArticleType(), attribute.getAttribute(),
				attribute.getConstructionTypeArticleAttributes(), attribute
						.getAttributeFormula());
	}

	/**
	 * @return attributtverdi fra
	 */
	public String getAttributeValueFrom() {
		return attributeValueFrom;
	}

	/**
	 * @param attributeValueFrom
	 */
	public void setAttributeValueFrom(String attributeValueFrom) {
		this.attributeValueFrom = attributeValueFrom;
	}

	/**
	 * @return attributtverdi fra
	 */
	public String getAttributeValueTo() {
		return attributeValueTo;
	}

	/**
	 * @param attributeValueTo
	 */
	public void setAttributeValueTo(String attributeValueTo) {
		this.attributeValueTo = attributeValueTo;
	}

	/**
	 * @return attributtverdi som boolsk verdi
	 */
	public Boolean getAttributeValueBoolean() {
		if (attributeValueFrom != null
				&& attributeValueFrom.equalsIgnoreCase("Ja")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @param bool
	 */
	public void setAttributeValueBoolean(Boolean bool) {
		if (bool) {
			this.attributeValueFrom = "Ja";
		} else {
			this.attributeValueFrom = "Nei";
		}
	}

	/**
	 * @return true dersom attributtverdi er boolsk
	 */
	public Boolean isYesNo() {
		Integer yesNo = null;

		yesNo = getAttribute().getYesNo();
		if (yesNo != null && yesNo > 0) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @return attributtvalg
	 */
	public List<String> getChoices() {
		List<String> list = new ArrayList<String>();
		Set<AttributeChoice> choices = getAttribute().getAttributeChoices();
		if (choices != null) {
			for (AttributeChoice choice : choices) {
				list.add(choice.getChoiceValue());
			}
		}
		return list;

	}

	/**
	 * @return attributtnavn
	 */
	public String getAttributeName() {
		return getAttribute().getName();
	}

}
