package no.ugland.utransprod.model;

import java.util.Set;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klase som represnterer tabell ARTICLE_TYPE_ATTRIBUTE
 * @author atle.brekka
 */
public class ArticleTypeAttribute extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer articleTypeAttributeId;

    private ArticleType articleType;

    private Attribute attribute;

    private Set<ConstructionTypeArticleAttribute> constructionTypeArticleAttributes;

    private String attributeFormula;

	private Integer inactive;
    

    public ArticleTypeAttribute() {
        super();
    }

    /**
     * @param aArticleTypeAttributeId
     * @param aArticleType
     * @param aAttribute
     * @param aConstructionTypeArticleAttributes
     * @param aAttributeFormula
     */
    public ArticleTypeAttribute(
            final Integer aArticleTypeAttributeId,
            final ArticleType aArticleType,
            final Attribute aAttribute,
            final Set<ConstructionTypeArticleAttribute> aConstructionTypeArticleAttributes,
            final String aAttributeFormula) {
        super();
        this.articleTypeAttributeId = aArticleTypeAttributeId;
        this.articleType = aArticleType;
        this.attribute = aAttribute;
        this.constructionTypeArticleAttributes = aConstructionTypeArticleAttributes;
        this.attributeFormula = aAttributeFormula;
        
    }

    /**
     * @return artikkeltype
     */
    public final ArticleType getArticleType() {
        return articleType;
    }

    /**
     * @param aArticleType
     */
    public final void setArticleType(final ArticleType aArticleType) {
        this.articleType = aArticleType;
    }

    /**
     * @return id
     */
    public final Integer getArticleTypeAttributeId() {
        return articleTypeAttributeId;
    }

    /**
     * @param aArticleTypeAttributeId
     */
    public final void setArticleTypeAttributeId(final Integer aArticleTypeAttributeId) {
        this.articleTypeAttributeId = aArticleTypeAttributeId;
    }

    /**
     * @return attributt
     */
    public final Attribute getAttribute() {
        return attribute;
    }

    /**
     * @param aAttribute
     */
    public final void setAttribute(final Attribute aAttribute) {
        this.attribute = aAttribute;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof ArticleTypeAttribute)){
            return false;
        }
        ArticleTypeAttribute castOther = (ArticleTypeAttribute) other;
        return new EqualsBuilder().append(articleType, castOther.articleType)
                .append(attribute, castOther.attribute).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(articleType).append(attribute)
                .toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public final String toString() {
        return attribute.getName();
    }

    /**
     * @return attributter
     */
    public final Set<ConstructionTypeArticleAttribute> getConstructionTypeArticleAttributes() {
        return constructionTypeArticleAttributes;
    }

    /**
     * @param aConstructionTypeArticleAttributes
     */
    public final void setConstructionTypeArticleAttributes(
            final Set<ConstructionTypeArticleAttribute> aConstructionTypeArticleAttributes) {
        this.constructionTypeArticleAttributes = aConstructionTypeArticleAttributes;
    }

    /**
     * @return formel
     */
    public final String getAttributeFormula() {
        return attributeFormula;
    }

    /**
     * @param aAttributeFormula
     */
    public final void setAttributeFormula(final String aAttributeFormula) {
        this.attributeFormula = aAttributeFormula;
    }

    public String getAttributeDataType() {
        return attribute.getAttributeDataType();
    }


	public Integer getInactive() {
		return inactive;
	}

	public void setInactive(Integer inactive) {
		this.inactive = inactive;
	}

	public Boolean getIsInactive() {
		return Util.convertNumberToBoolean(inactive);
	}

	


}
