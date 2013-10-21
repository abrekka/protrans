package no.ugland.utransprod.gui.model;

import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;

/**
 * GUI-modell for garasjeartikkelattributt kriteria
 * 
 * @author atle.brekka
 * 
 */
public class ConstructionTypeArticleAttributeCriteria extends
		ConstructionTypeArticleAttribute {
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
	 * @param article
	 */
	public ConstructionTypeArticleAttributeCriteria(
			ArticleTypeAttribute attribute, ConstructionTypeArticle article) {
		super(null, article, attribute, null, null);
	}

	/**
	 * @return fra attributtverdi
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
	 * @return til attributtverdi
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
}
