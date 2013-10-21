package no.ugland.utransprod.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;

/**
 * Manager for garasjetype
 * @author atle.brekka
 */
public interface ConstructionTypeManager extends
        OverviewManager<ConstructionType> {
    String MANAGER_NAME = "constructionTypeManager";

    /**
     * Lagrer garasjetype
     * @param constructionType
     */
    void saveConstructionType(ConstructionType constructionType);

    /**
     * Fjern alle
     */
    void removeAll();

    /**
     * Hent alle
     * @return garasjetyper
     */
    List<ConstructionType> findAll();

    /**
     * Finner basert på navn
     * @param aName
     * @return garasjetype
     */
    ConstructionType findByName(String aName);

    /**
     * Fjerner garasjetype
     * @param constructionType
     */
    void removeConstructionType(ConstructionType constructionType);

    /**
     * Finner master for gitt produktområde
     * @param productArea
     * @return konstruksjonstype
     */
    ConstructionType findMaster(ProductArea productArea);

    /**
     * Lazy laster konstruksjonstype
     * @param constructionType
     * @param enums
     */
    void lazyLoad(ConstructionType constructionType,
            LazyLoadConstructionTypeEnum[] enums);

    /**
     * Lazy laster attributt
     * @param constructionTypeAttribute
     * @param enums
     */
    void lazyLoadAttribute(ConstructionTypeAttribute constructionTypeAttribute,
            LazyLoadConstructionTypeAttributeEnum[] enums);

    /**
     * Lazy laster artikkel
     * @param article
     * @param enums
     */
    void lazyLoadArticle(ConstructionTypeArticle article,
            LazyLoadConstructionTypeArticleEnum[] enums);

    /**
     * Lazy laster alt for konstruksjonstype
     * @param constructionType
     */
    void lazyLoadTree(ConstructionType constructionType);

    /**
     * Finner alle
     * @return konstruksjonstyper
     */
    List<ConstructionType> findAllIncludeMaster();

    /**
     * Finner alle for gitt produktområde
     * @param productArea
     * @return konstruksjonstyper
     */
    List<ConstructionType> findByProductArea(ProductArea productArea);

    /**
     * Finner alle mastere
     * @return kontruksjonstyper
     */
    List<ConstructionType> findAllMasters();
    Set<OrderLine> getOrderLinesForNewConstructionType(Collection<OrderLine> originalOrderLines,ConstructionType newConstructionType,Order order,Deviation deviation);
    Set<OrderLine> updateOrderLinesFromVisma(
			Set<OrderLine> orderLines, Collection<OrderLine> vismaOrderLines);
}
