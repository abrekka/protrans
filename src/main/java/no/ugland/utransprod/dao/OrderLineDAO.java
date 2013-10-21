package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.dao.hibernate.QuerySettings;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.Periode;

/**
 * Interface for DAO mot ORDER_LINE
 * @author atle.brekka
 */
public interface OrderLineDAO extends DAO<OrderLine> {
    /**
     * Finner ordre som har uproduserte artikler basert på artikkeltype
     * @param article
     * @return ordrelinjer
     */
    List<OrderLine> findUnproducedByArticle(ArticleType article);

    /**
     * Lazy laster ordrelinje
     * @param orderLine
     * @param enums
     */
    void lazyLoad(OrderLine orderLine, LazyLoadOrderLineEnum[] enums);

    /**
     * Teller antall ordre for gitt utvalg
     * @param querySettings
     * @return antall ordre
     */
    Integer countByDate(QuerySettings querySettings);

    /**
     * Finner alle ordre som tilfredsstiller utvalg
     * @param criterias
     * @param querySettings
     * @return ordre
     */
    List<Order> findByConstructionTypeArticleAttributes(List<OrderLine> criterias,
            QuerySettings querySettings);

    /**
     * Finner ordrelinjer som ikke er pakket basert på artikkel
     * @param articleType
     * @return ordrelinjer
     */
    List<OrderLine> findUnpackageByArticle(ArticleType articleType);

    /**
     * Lazy laster ordretre
     * @param orderLine
     */
    void lazyLoadTree(OrderLine orderLine);

    /**
     * Finner uproduserte basert på ordrenr og artikkelnavn
     * @param orderNr
     * @param articleType
     * @return ordrelinjer
     */
    List<OrderLine> findUnproducedByOrderNrAndArticleName(final String orderNr,
            final ArticleType articleType);

    /**
     * Finner upakkede ordrelinjer basert på ordrenummer og artikkeltype
     * @param orderNr
     * @param articleType
     * @return ordrelinjer
     */
    List<OrderLine> findUnpackedByOrderNrAndArticleName(final String orderNr, final ArticleType articleType);

    /**
     * Finner uproduserte basert på kundenummer og artikkeltype
     * @param customerNr
     * @param articleType
     * @return ordrelinjer
     */
    List<OrderLine> findUnproducedByCustomerNrAndArticleName(
            final Integer customerNr, final ArticleType articleType);

    /**
     * Finner upakkede basert på kundenummer og artikkeltype
     * @param customerNr
     * @param articleType
     * @return ordrelinjer
     */
    List<OrderLine> findUnpackedByCustomerNrAndArticleName(
            final Integer customerNr, final ArticleType articleType);

    /**
     * Finner basert på artikkeltype og attributt med gitt verdi
     * @param articleType
     * @param attributeName
     * @param attributeValue
     * @return ordrelinjer
     */
    List<OrderLine> findByArticleAndAttribute(
            ArticleType articleType, String attributeName, String attributeValue);

    /**
     * Finner basert på ordrenummer, artikkeltype og attributt med gitt verdi
     * @param orderNr
     * @param articleType
     * @param attributeName
     * @param attributeValue
     * @return ordrelinjer
     */
    List<OrderLine> findByOrderNrArticleNameAndAttribute(
            String orderNr, ArticleType articleType, String attributeName, String attributeValue);

    /**
     * Finner basert på kundenummer, artikkeltype og attributt med gitt verdi
     * @param customerNr
     * @param articleType
     * @param attributeName
     * @param attributeValue
     * @return ordrelinjer
     */
    List<OrderLine> findByCustomerNrArticleNameAndAttribute(
            Integer customerNr, ArticleType articleType, String attributeName, String attributeValue);

    /**
     * Brukes til pakking av takstein
     * @return takstein
     */
    List<PackableListItem> findAllApplyable();

    /**
     * Brukes til pakking av takstein, finner takstein for gitt kunde
     * @param customerNr
     * @return takstein
     */
    List<PackableListItem> findApplyableByCustomerNr(Integer customerNr);

    /**
     * Brukes ved pakking av takstein, finner takstein for gitt ordre
     * @param orderNr
     * @return takstein
     */
    List<PackableListItem> findApplyableByOrderNr(String orderNr);

    /**
     * Oppdaterer
     * @param object
     */
    void refresh(PackableListItem object);

    List<OrderLine> findAllConstructionTypeNotSent(ProductArea productArea);

    Ordln findOrdlnByOrderLine(Integer orderLineId);

    List<Order> findTakstolOwnOrderByPeriode(Periode periode);
}
