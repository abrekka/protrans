package no.ugland.utransprod.dao.hibernate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.dao.ConstructionTypeDAO;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implemntasjon av DAO for tabell CONSTRUCTION?TYPE for hibernate.
 * @author atle.brekka
 */
public class ConstructionTypeDAOHibernate extends
        BaseDAOHibernate<ConstructionType> implements ConstructionTypeDAO {
    public ConstructionTypeDAOHibernate() {
        super(ConstructionType.class);
    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#removeAll()
     */
    @Override
    public final void removeAll() {
        getHibernateTemplate().bulkUpdate("delete from ConstructionType");

    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#refreshObject(
     * no.ugland.utransprod.model.ConstructionType)
     */
    public final void refreshObject(final ConstructionType constructionType) {
        getHibernateTemplate().load(constructionType,
                constructionType.getConstructionTypeId());

    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#findAllExceptMaster()
     */
    @SuppressWarnings("unchecked")
    public final List<ConstructionType> findAllExceptMaster() {
        return (List<ConstructionType>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session){

                        return session
                                .createCriteria(ConstructionType.class)
                                .add(
                                        Restrictions.or(Restrictions
                                                .isNull("isMaster"),
                                                Restrictions.ne("isMaster", 1)))
                                .list();
                    }

                });

    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#lazyLoad(no.ugland.utransprod.model.ConstructionType,
     *      no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum[])
     */
    public final void lazyLoad(final ConstructionType constructionType,
            final LazyLoadConstructionTypeEnum[] lazyEnums) {

        if (constructionType != null
                && constructionType.getConstructionTypeId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                @SuppressWarnings("incomplete-switch")
                public Object doInHibernate(final Session session){
                    session.load(constructionType, constructionType
                            .getConstructionTypeId());
                    Set<?> set;

                    for (LazyLoadConstructionTypeEnum lazyEnum : lazyEnums) {
                        switch (lazyEnum) {
                        case CONSTRUCTION_TYPE_ATTRIBUTE:
                            set = constructionType
                                    .getConstructionTypeAttributes();
                            set.iterator();
                            break;
                        case CONSTRUCTION_TYPE_ARTICLE:

                            if (Arrays
                                    .asList(lazyEnums)
                                    .contains(
                                            LazyLoadConstructionTypeEnum.
                                            CONSTRUCTION_TYPE_ARTICLE_ATTRIBUTES)) {
                                Set<ConstructionTypeArticle> articles = constructionType
                                        .getConstructionTypeArticles();
                                for (ConstructionTypeArticle article : articles) {
                                    lazyLoadArticle(
                                            article,
                                            new LazyLoadConstructionTypeArticleEnum[] {
                                                    LazyLoadConstructionTypeArticleEnum.ATTRIBUTES,
                                                    LazyLoadConstructionTypeArticleEnum.
                                                    CONSTRUCTION_TYPE_ARTICLES});
                                }
                            } else {
                                set = constructionType
                                        .getConstructionTypeArticles();
                                set.iterator();
                            }
                            break;
                        case ORDER:
                            set = constructionType.getOrders();
                            set.iterator();
                            break;
                            default:
                        }
                    }
                    return null;
                }

            });

        }

    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#lazyLoadAttribute(no.ugland.utransprod.model.
     * ConstructionTypeAttribute,
     *      no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum[])
     */
    public final void lazyLoadAttribute(final ConstructionTypeAttribute attribute,
            final LazyLoadConstructionTypeAttributeEnum[] lazyEnums) {
        if (attribute != null
                && attribute.getConstructionTypeAttributeId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) {
                    session.load(attribute, attribute
                            .getConstructionTypeAttributeId());
                    Set<?> set;

                    for (LazyLoadConstructionTypeAttributeEnum lazyEnum : lazyEnums) {
                        switch (lazyEnum) {
                        case ORDER_LINE_ATTRIBUTE:
                            set = attribute.getOrderLineAttributes();
                            set.iterator();
                            break;
                            default:
                        }
                    }
                    return null;
                }

            });

        }

    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#lazyLoadArticle(no.ugland.utransprod.model.
     * ConstructionTypeArticle,
     *      no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum[])
     */
    public final void lazyLoadArticle(final ConstructionTypeArticle article,
            final LazyLoadConstructionTypeArticleEnum[] lazyEnums) {
        if (article != null && article.getConstructionTypeArticleId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session){
                    if (!session.contains(article)) {
                        session.load(article, article
                                .getConstructionTypeArticleId());
                    }
                    Set<?> set;

                    for (LazyLoadConstructionTypeArticleEnum lazyEnum : lazyEnums) {
                        switch (lazyEnum) {
                        case ORDER_LINE:
                            set = article.getOrderLines();
                            set.iterator();
                            break;
                        case ATTRIBUTES:
                            set = article.getAttributes();
                            set.iterator();
                            break;
                        case CONSTRUCTION_TYPE_ARTICLES:
                            set = article.getConstructionTypeArticles();
                            set.iterator();
                            break;
                            default:

                        }
                    }
                    return null;
                }

            });

        }

    }

    /**
     * Lazyloader trestruktur.
     * @param articles
     */
    final void lazyLoadTreeConstructionTypes(final Set<ConstructionTypeArticle> articles) {
        if (articles == null) {
            return;
        }
        Set<?> set;
        for (ConstructionTypeArticle article : articles) {
            set = article.getAttributes();
            set.iterator();
            lazyLoadTreeConstructionTypes(article.getConstructionTypeArticles());
        }
    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#lazyLoadTree(no.ugland.utransprod.model.
     * ConstructionType)
     */
    public final void lazyLoadTree(final ConstructionType constructionType) {
        if (constructionType != null
                && constructionType.getConstructionTypeId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) {

                    session.load(constructionType, constructionType
                            .getConstructionTypeId());

                    Set<?> set;

                    Set<ConstructionTypeArticle> articles = constructionType
                            .getConstructionTypeArticles();
                    lazyLoadTreeConstructionTypes(articles);

                    set = constructionType.getConstructionTypeAttributes();
                    set.iterator();
                    return null;
                }
            });
        }

    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#findByProductArea(no.ugland.utransprod.model.
     * ProductArea)
     */
    @SuppressWarnings("unchecked")
    public final List<ConstructionType> findByProductArea(
            final ProductArea productArea) {
        return (List<ConstructionType>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session){
                        return session
                                .createCriteria(ConstructionType.class)
                                .add(
                                        Restrictions.eq("productArea",
                                                productArea))
                                .add(
                                        Restrictions.or(Restrictions
                                                .isNull("isMaster"),
                                                Restrictions.ne("isMaster", 1)))
                                .list();
                    }

                });
    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#findAllMasters()
     */
    @SuppressWarnings("unchecked")
    public final List<ConstructionType> findAllMasters() {
        return (List<ConstructionType>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {

                        return session.createCriteria(ConstructionType.class)
                                .add(Restrictions.eq("isMaster", 1)).list();
                    }

                });
    }

    /**
     * @see no.ugland.utransprod.dao.ConstructionTypeDAO#findMasterByProductArea(no.ugland.utransprod.model.
     * ProductArea)
     */
    @SuppressWarnings("unchecked")
    public final List<ConstructionType> findMasterByProductArea(
            final ProductArea productArea) {
        return (List<ConstructionType>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session){

                        return session.createCriteria(ConstructionType.class)
                                .add(Restrictions.eq("isMaster", 1)).add(
                                        Restrictions.eq("productArea",
                                                productArea)).list();
                    }

                });
    }

}
