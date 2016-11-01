package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.hibernate.QuerySettings;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.excel.ExcelManager;

/**
 * Interface for serviceklasse mot tabell ORDER_LINE
 * @author atle.brekka
 */
public interface OrderLineManager extends IApplyListManager<PackableListItem>,ExcelManager {
    public final static String MANAGER_NAME="orderLineManager";
    /**
     * Finner ikke produserte for gitt artikkelnavn
     * @param articleName
     * @return ikke produserte ordrelinjer
     */
    List<OrderLine> findUnproducedByArticle(String articleName);

    /**
     * Lagrer ordrelinje
     * @param orderLine
     */
    void saveOrderLine(OrderLine orderLine);

    /**
     * Lazy laster ordre
     * @param order
     * @param enums
     */
    void lazyLoadOrder(Order order, LazyLoadOrderEnum[] enums);

    /**
     * Lazy laster ordrelinje
     * @param orderLine
     * @param enums
     */
    void lazyLoad(OrderLine orderLine, LazyLoadOrderLineEnum[] enums);

    /**
     * Teller antall ordrelinjer
     * @param querySettings
     * @return antall ordrelinjer
     */
    Integer countByDate(QuerySettings querySettings);

    /**
     * Finner order med kriterier satt for ordrelinjer
     * @param criterias
     * @param querySettings
     * @return ordre
     */
    List<Order> findByConstructionTypeArticleAttributes(
            List<OrderLine> criterias, QuerySettings querySettings);

    /**
     * Finner ordrelinjer som ikke er pakket for gitt artikkelnavn
     * @param articleName
     * @return ordrelinjer
     */
    List<OrderLine> findUnpackageByArticle(String articleName);

    /**
     * Lagrer ordre
     * @param order
     * @throws ProTransException
     */
    void saveOrder(Order order) throws ProTransException;

    /**
     * Oppdaterer ordre
     * @param order
     */
    void refreshOrder(Order order);

    /**
     * Lazy laster ordrelinjetre
     * @param orderLine
     */
    void lazyLoadTree(OrderLine orderLine);

    /**
     * Finner ikke produserte basert på ordrenummer og artikkelnavn
     * @param orderNr
     * @param articleName
     * @return ordrelinjer
     */
    List<OrderLine> findUnproducedByOrderNrAndArticleName(String orderNr,
            String articleName);

    /**
     * Finner ikke pakket basert på ordrenummer og artikkelnavn
     * @param orderNr
     * @param articleName
     * @return ordrelinjer
     */
    List<OrderLine> findUnpackedByOrderNrAndArticleName(String orderNr,
            String articleName);

    /**
     * Finner basert på id
     * @param orderLineId
     * @return ordrelinje
     */
    OrderLine findByOrderLineId(Integer orderLineId);

    /**
     * Finner ikke produsert basert på kundenummer og artikkelnavn
     * @param customerNr
     * @param articleName
     * @return ordrelinjer
     */
    List<OrderLine> findUnproducedByCustomerNrAndArticleName(
            Integer customerNr, String articleName);

    /**
     * Finner ikk pakker basert på kundenummer og artikkelnavn
     * @param customerNr
     * @param articleName
     * @return ordrelinjer
     */
    List<OrderLine> findUnpackedByCustomerNrAndArticleName(Integer customerNr,
            String articleName);

    /**
     * Finner basert på artikkelnavn, attributtnavn og attributtverdi
     * @param articleName
     * @param attributeName
     * @param attributeValue
     * @return ordrelinjer
     */
    List<OrderLine> findByArticleAndAttribute(String articleName,
            String attributeName, String attributeValue);

    /**
     * Finner basert på ordrenummer, artikkelnavn, attributtnavn og
     * attributtverdi
     * @param orderNr
     * @param articleName
     * @param attributeName
     * @param attributeValue
     * @return ordrelinjer
     */
    List<OrderLine> findByOrderNrArticleNameAndAttribute(String orderNr,
            String articleName, String attributeName, String attributeValue);

    /**
     * Finner basert på kundenummer, artikkelnavn, attributtnavn og
     * attributtverdi
     * @param customerNr
     * @param articleName
     * @param attributeName
     * @param attributeValue
     * @return ordrelinjer
     */
    List<OrderLine> findByCustomerNrArticleNameAndAttribute(Integer customerNr,
            String articleName, String attributeName, String attributeValue);
    List<OrderLine> findAllConstructionTypeNotSent(ProductArea productArea);

	void fjernColli(Integer orderLineId);
}
