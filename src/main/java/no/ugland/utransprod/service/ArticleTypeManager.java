package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;

/**
 * Interface for mnger som håndterer artikkeltyper.
 * @author atle.brekka
 */
public interface ArticleTypeManager extends OverviewManager<ArticleType> {
    public static final String MANAGER_NAME="articleTypeManager";
    /**
     * Lagrer artikkeltype.
     * @param articleType
     */
    void saveArticleType(ArticleType articleType);

    /**
     * Finner basert på navn.
     * @param name
     * @return artikkeltype
     */
    ArticleType findByName(String name);

    /**
     * Fjerner artikkeltype.
     * @param articleType
     */
    void removeArticleType(ArticleType articleType);

    /**
     * Lazy laster artikkeltype.
     * @param articletype
     * @param enums
     */
    void lazyLoad(ArticleType articletype, LazyLoadArticleTypeEnum[] enums);

    /**
     * Finner alle attributter som er definert som konstruksjonsattributter.
     * @return attributter
     */
    List<Attribute> findAllConstructionTypeAttributes();

    /**
     * Finner alle artikler som er toppartikler.
     * @return artikler
     */
    List<ArticleType> findAllTopLevel();

    /**
     * Lazy laster attributt.
     * @param attribute
     * @param enums
     */
    //void lazyLoadArticleAttribute(ArticleTypeAttribute attribute,LazyLoadArticleTypeAttributeEnum[] enums);
}
