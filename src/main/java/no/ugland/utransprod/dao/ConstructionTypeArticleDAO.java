package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ConstructionTypeArticle;

/**
 * Interface for DAO mot tabell CONSTRUCTION_TYPE_ARTICLE
 * 
 * @author atle.brekka
 * 
 */
public interface ConstructionTypeArticleDAO extends
		DAO<ConstructionTypeArticle> {
	/**
	 * Lazy laster artikkel
	 * 
	 * @param article
	 * @param enums
	 */
	//void lazyLoad(ConstructionTypeArticle article,LazyLoadConstructionTypeArticleEnum[] enums);
}
