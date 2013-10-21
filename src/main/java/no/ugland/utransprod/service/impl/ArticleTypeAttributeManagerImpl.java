package no.ugland.utransprod.service.impl;

import java.io.Serializable;

import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.service.ArticleTypeAttributeManager;

public class ArticleTypeAttributeManagerImpl extends ManagerImpl<ArticleTypeAttribute> implements ArticleTypeAttributeManager{

    @Override
    protected Serializable getObjectId(ArticleTypeAttribute object) {
        return object.getArticleTypeAttributeId();
    }

}
