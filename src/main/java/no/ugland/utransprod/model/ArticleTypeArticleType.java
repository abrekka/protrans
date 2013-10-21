package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som representerer tabell ARTICLE_TYPE_ARTICLE_TYPE
 * @author atle.brekka
 */
public class ArticleTypeArticleType extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer articleTypeArticleTypeId;

    private ArticleType articleType;

    private ArticleType articleTypeRef;

    public ArticleTypeArticleType() {
        super();
    }

    /**
     * @param aArticleTypeArticleTypeId
     * @param aArticleType
     * @param aArticleTypeRef
     */
    public ArticleTypeArticleType(final Integer aArticleTypeArticleTypeId,
            final ArticleType aArticleType, final ArticleType aArticleTypeRef) {
        super();
        this.articleTypeArticleTypeId = aArticleTypeArticleTypeId;
        this.articleType = aArticleType;
        this.articleTypeRef = aArticleTypeRef;
    }

    /**
     * @return artikkel
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
    public final Integer getArticleTypeArticleTypeId() {
        return articleTypeArticleTypeId;
    }

    /**
     * @param aArticleTypeArticleTypeId
     */
    public final void setArticleTypeArticleTypeId(final Integer aArticleTypeArticleTypeId) {
        this.articleTypeArticleTypeId = aArticleTypeArticleTypeId;
    }

    /**
     * @return artikkel det refereres til
     */
    public final ArticleType getArticleTypeRef() {
        return articleTypeRef;
    }

    /**
     * @param aArticleTypeRef
     */
    public final void setArticleTypeRef(final ArticleType aArticleTypeRef) {
        this.articleTypeRef = aArticleTypeRef;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof ArticleTypeArticleType)){
            return false;
        }
        ArticleTypeArticleType castOther = (ArticleTypeArticleType) other;
        return new EqualsBuilder().append(articleType, castOther.articleType)
                .append(articleTypeRef, castOther.articleTypeRef).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(articleType).append(articleTypeRef)
                .toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public final String toString() {
        return articleTypeRef.toString();
    }
}
