package no.ugland.utransprod.service.impl;

import java.io.Serializable;

import no.ugland.utransprod.dao.ConstructionTypeArticleDAO;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.service.ConstructionTypeArticleManager;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;

/**
 * Implementasjon av serviceklasse for tabell CONSTRUCTION_TYPE_ARTICLE.
 * @author atle.brekka
 */
public class ConstructionTypeArticleManagerImpl extends ManagerImpl<ConstructionTypeArticle>implements
        ConstructionTypeArticleManager {
    /**
     * @see no.ugland.utransprod.service.ConstructionTypeArticleManager#
     * saveConstructionTypeArticle(no.ugland.utransprod.model.ConstructionTypeArticle)
     */
    public final void saveConstructionTypeArticle(final ConstructionTypeArticle article) {
        dao.saveObject(article);

    }

    @Override
    protected Serializable getObjectId(ConstructionTypeArticle object) {
        return object.getConstructionTypeArticleId();
    }

  

}
