package no.ugland.utransprod.service.enums;

/**
 * Lazy lasting av artikkel.
 * @author atle.brekka
 */
public enum LazyLoadArticleTypeEnum {
    /**
     * Attributter.
     */
    ATTRIBUTE,
    /**
     * Artikkel.
     */
    ARTICLE_TYPE_ARTICLE_TYPE,
    /**
     * Referanser til artikler.
     */
    ARTICLE_TYPE_ARTICLE_TYPE_REF,
    /**
     * Konstruksjonstypeartikler.
     */
    CONSTRUCTION_TYPE_ARTICLE;
}
