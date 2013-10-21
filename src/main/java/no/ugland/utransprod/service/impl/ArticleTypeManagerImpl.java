package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.ArticleTypeDAO;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;

/**
 * Implementasjon av manager for artikkeltype.
 * @author atle.brekka
 */
public class ArticleTypeManagerImpl extends ManagerImpl<ArticleType> implements ArticleTypeManager {
    /**
     * @see no.ugland.utransprod.service.ArticleTypeManager#findByName(java.lang.String)
     */
    public final ArticleType findByName(final String name) {
        ArticleType articleType = ((ArticleTypeDAO)dao).findByName(name);

        return articleType;
    }

    /**
     * @see no.ugland.utransprod.service.ArticleTypeManager#
     * saveArticleType(no.ugland.utransprod.model.ArticleType)
     */
    public final void saveArticleType(final ArticleType articleType) {
        dao.saveObject(articleType);

    }

    /**
     * @see no.ugland.utransprod.service.ArticleTypeManager#
     * removeArticleType(no.ugland.utransprod.model.ArticleType)
     */
    public final void removeArticleType(final ArticleType articleType) {
        dao.removeObject(articleType.getArticleTypeId());

    }

    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<ArticleType> findAll() {
        return dao.getObjects("articleTypeName");
    }

    /**
     * @param object
     * @return artikkeltyper
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<ArticleType> findByObject(final ArticleType object) {
        return dao.findByExampleLike(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final ArticleType object) {
        removeArticleType(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final ArticleType object) {
        saveArticleType(object);

    }

    /**
     * @param articleType
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final ArticleType articleType) {
        ((ArticleTypeDAO)dao).refreshObject(articleType);

    }

    /**
     * @see no.ugland.utransprod.service.ArticleTypeManager#lazyLoad(no.ugland.utransprod.model.ArticleType,
     *      no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum[])
     */
    public final void lazyLoad(final ArticleType articletype,
            final LazyLoadArticleTypeEnum[] enums) {
        ((ArticleTypeDAO)dao).lazyLoad(articletype, enums);

    }

    /**
     * @see no.ugland.utransprod.service.ArticleTypeManager#findAllConstructionTypeAttributes()
     */
    public final List<Attribute> findAllConstructionTypeAttributes() {
        return ((ArticleTypeDAO)dao).findAllConstructionTypeAttributes();
    }

    /**
     * @see no.ugland.utransprod.service.ArticleTypeManager#findAllTopLevel()
     */
    public final List<ArticleType> findAllTopLevel() {
        ArticleType example = new ArticleType();
        example.setTopLevel(1);

        return dao.findByExample(example);
    }

    /**
     * @see no.ugland.utransprod.service.ArticleTypeManager#
     * lazyLoadArticleAttribute(no.ugland.utransprod.model.ArticleTypeAttribute,
     *      no.ugland.utransprod.service.enums.LazyLoadArticleTypeAttributeEnum[])
     */
    /*public final void lazyLoadArticleAttribute(final ArticleTypeAttribute attribute,
            final LazyLoadArticleTypeAttributeEnum[] enums) {
        ((ArticleTypeDAO)dao).lazyLoadArticleAttribute(attribute, enums);

    }*/

    public void lazyLoad(ArticleType object, Enum[] enums) {
        lazyLoad(object,(LazyLoadArticleTypeEnum[]) enums);
        
    }

    @Override
    protected Serializable getObjectId(ArticleType object) {
        return object.getArticleTypeId();
    }

}
