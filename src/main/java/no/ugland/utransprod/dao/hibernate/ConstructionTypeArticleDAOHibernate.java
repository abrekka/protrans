package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.ConstructionTypeArticleDAO;
import no.ugland.utransprod.model.ConstructionTypeArticle;

/**
 * Implementasjon av DAO mot tabell CONSTRUCTION_TYPE_ARTICLE
 * 
 * @author atle.brekka
 * 
 */
public class ConstructionTypeArticleDAOHibernate extends
		BaseDAOHibernate<ConstructionTypeArticle> implements
		ConstructionTypeArticleDAO {
	/**
	 * Konstruktør
	 */
	public ConstructionTypeArticleDAOHibernate() {
		super(ConstructionTypeArticle.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.ConstructionTypeArticleDAO#lazyLoad(no.ugland.utransprod.model.ConstructionTypeArticle,
	 *      no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum[])
	 */
	/*public void lazyLoad(final ConstructionTypeArticle article,
			final LazyLoadConstructionTypeArticleEnum[] enums) {
		if (article != null && article.getConstructionTypeArticleId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					if (!session.contains(article)) {
						session.load(article, article
								.getConstructionTypeArticleId());
					}
					Set<?> set;

					for (LazyLoadConstructionTypeArticleEnum lazyEnum : enums) {
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

						}
					}
					return null;
				}

			});

		}

	}*/

}
