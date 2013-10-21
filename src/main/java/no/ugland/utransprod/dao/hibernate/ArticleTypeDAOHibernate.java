package no.ugland.utransprod.dao.hibernate;

import java.util.List;
import java.util.Set;

import no.ugland.utransprod.dao.ArticleTypeDAO;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ArticleTypeArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot ARTICLE_TYPE for hibernate
 * @author atle.brekka
 */
public class ArticleTypeDAOHibernate extends BaseDAOHibernate<ArticleType> implements ArticleTypeDAO {
    /**
     * Konstruktør
     */
    public ArticleTypeDAOHibernate() {
        super(ArticleType.class);
    }

    /**
     * @see no.ugland.utransprod.dao.ArticleTypeDAO#refreshObject(no.ugland.utransprod.model.ArticleType)
     */
    public void refreshObject(ArticleType articleType) {
        getHibernateTemplate().load(articleType, articleType.getArticleTypeId());
    }

    /**
     * @see no.ugland.utransprod.dao.ArticleTypeDAO#findByName(java.lang.String)
     */
    public ArticleType findByName(final String name) {
        return (ArticleType) getHibernateTemplate().execute(new HibernateCallback() {

            @SuppressWarnings("unchecked")
            public Object doInHibernate(Session session) throws HibernateException {
                List<ArticleType> list = session.createCriteria(ArticleType.class).add(
                        Restrictions.ilike("articleTypeName", name)).list();

                if (list != null && list.size() == 1) {
                    return list.get(0);
                }
                return null;
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.ArticleTypeDAO#lazyLoad(no.ugland.utransprod.model.ArticleType,
     *      no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum[])
     */
    public void lazyLoad(final ArticleType articleType, final LazyLoadArticleTypeEnum[] enums) {
        if (articleType != null && articleType.getArticleTypeId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException {
                    if (!session.contains(articleType)) {
                        session.load(articleType, articleType.getArticleTypeId());
                    }

                    Set<?> set;

                    for (LazyLoadArticleTypeEnum lazyEnum : enums) {
                        switch (lazyEnum) {
                        case ATTRIBUTE:
                            set = articleType.getArticleTypeAttributes();
                            if (set != null) {
                                set.iterator();
                            }
                            break;
                        case ARTICLE_TYPE_ARTICLE_TYPE:
                            Set<ArticleTypeArticleType> articleTypeArticleTypes = articleType
                                    .getArticleTypeArticleTypes();
                            for (ArticleTypeArticleType articleTypeArticleType : articleTypeArticleTypes) {
                                lazyLoad(articleTypeArticleType.getArticleTypeRef(), enums);
                            }

                            break;
                        case ARTICLE_TYPE_ARTICLE_TYPE_REF:
                            set = articleType.getArticleTypeArticleTypeRefs();
                            set.iterator();
                            break;
                        case CONSTRUCTION_TYPE_ARTICLE:
                            set = articleType.getConstructionTypeArticles();
                            set.iterator();
                            break;
                        }
                    }
                    return null;
                }

            });

        }

    }

    /**
     * @see no.ugland.utransprod.dao.ArticleTypeDAO#findAllConstructionTypeAttributes()
     */
    @SuppressWarnings("unchecked")
    public List<Attribute> findAllConstructionTypeAttributes() {
        return (List<Attribute>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                return session
                        .createQuery(
                                "select distinct constructionTypeAttribute.attribute from ConstructionTypeAttribute constructionTypeAttribute")
                        .list();

            }

        });
    }


    public ArticleType findByProdCatNoAndProdCatNo2(final Integer prCatNo, final Integer prCatNo2) {
        return (ArticleType) getHibernateTemplate().execute(new HibernateCallback() {

            @SuppressWarnings("unchecked")
            public Object doInHibernate(final Session session) {
                Criteria criteria = session.createCriteria(ArticleType.class);
                
                criteria = prCatNo!=null?criteria.add(Restrictions.eq("prodCatNo", prCatNo)):criteria.add(Restrictions.isNull("prodCatNo"));
                criteria = prCatNo2!=null?criteria.add(Restrictions.eq("prodCatNo2", prCatNo2)):criteria.add(Restrictions.isNull("prodCatNo2"));
                
                List<ArticleType> list =criteria.list();
                if (list != null && list.size() == 1) {
                    return list.get(0);
                }
                return ArticleType.UNKNOWN;
            }
        });
    }
}
