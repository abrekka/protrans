package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;

/**
 * Interface for DAO mot tabell CONSTRUCTION_TYPE
 * 
 * @author atle.brekka
 * 
 */
public interface ConstructionTypeDAO extends DAO<ConstructionType> {
	/**
	 * Fjerner alle
	 */
	void removeAll();

	/**
	 * Oppdaterer objek
	 * 
	 * @param constructionType
	 */
	void refreshObject(ConstructionType constructionType);

	/**
	 * Finner alle garasjetyper som ikke er master
	 * 
	 * @return grasjetyper
	 */
	List<ConstructionType> findAllExceptMaster();

	/**
	 * Lazy laster garasjetype
	 * 
	 * @param constructionType
	 * @param lazyEnums
	 */
	void lazyLoad(ConstructionType constructionType,
			LazyLoadConstructionTypeEnum[] lazyEnums);

	/**
	 * Lazy laster grasjeattributter
	 * 
	 * @param attribute
	 * @param lazyEnums
	 */
	void lazyLoadAttribute(ConstructionTypeAttribute attribute,
			LazyLoadConstructionTypeAttributeEnum[] lazyEnums);

	/**
	 * Lazy laster grasjeartikler
	 * 
	 * @param article
	 * @param lazyEnums
	 */
	void lazyLoadArticle(ConstructionTypeArticle article,
			LazyLoadConstructionTypeArticleEnum[] lazyEnums);

	/**
	 * Lazyloader here grasjetypetreet
	 * 
	 * @param constructionType
	 */
	void lazyLoadTree(ConstructionType constructionType);

	/**
	 * Finner konstruksjonstyper med gitt produktområde
	 * 
	 * @param productArea
	 * @return konstruksjonstyper
	 */
	List<ConstructionType> findByProductArea(ProductArea productArea);

	/**
	 * Finner alle master konstruksjonstyper
	 * 
	 * @return konstruksjonstype
	 */
	List<ConstructionType> findAllMasters();

	/**
	 * Finner master konstruksjonstyper for gitt produktomåde
	 * 
	 * @param productArea
	 * @return konstruksjonstyper
	 */
	List<ConstructionType> findMasterByProductArea(ProductArea productArea);
}
