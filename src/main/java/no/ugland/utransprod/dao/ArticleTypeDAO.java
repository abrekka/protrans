package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;

/**
 * Interface for DAO mot tabell ARTICLE_TYPE
 * @author atle.brekka
 */
public interface ArticleTypeDAO extends DAO<ArticleType> {
    String DAO_NAME = "articleTypeDAO";

    /**
     * Henter artikkel på nytt fra database
     * @param articleType
     */
    void refreshObject(ArticleType articleType);

    /**
     * Finner artikkel basert på navn
     * @param name
     * @return artikkel
     */
    ArticleType findByName(String name);

    /**
     * Lazy laster artikkeltype
     * @param articletype
     * @param enums
     */
    void lazyLoad(ArticleType articletype, LazyLoadArticleTypeEnum[] enums);

    /**
     * Finner alle attributter for garasjetype
     * @return attributter
     */
    List<Attribute> findAllConstructionTypeAttributes();

    /**
     * Lazy laster artikkelattributt
     * @param attribute
     * @param enums
     */
    //void lazyLoadArticleAttribute(ArticleTypeAttribute attribute, LazyLoadArticleTypeAttributeEnum[] enums);

    ArticleType findByProdCatNoAndProdCatNo2(Integer prCatNo, Integer prCatNo2);
}
