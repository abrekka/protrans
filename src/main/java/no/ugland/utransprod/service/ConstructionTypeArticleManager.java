package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;

/**
 * Interface for serviceklasse mot tabell CONSTRUCTION_TYPE_ARTICLE
 * @author atle.brekka
 */
public interface ConstructionTypeArticleManager extends Manager<ConstructionTypeArticle>{
    String MANAGER_NAME = "constructionTypeArticleManager";

    /**
     * Lagrer konstruksjonsartikkel
     * @param article
     */
    void saveConstructionTypeArticle(ConstructionTypeArticle article);

    /**
     * Lazy laster konstruksjonsartikkel
     * @param article
     * @param enums
     */
    //void lazyLoad(ConstructionTypeArticle article,LazyLoadConstructionTypeArticleEnum[] enums);
}
