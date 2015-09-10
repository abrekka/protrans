package no.ugland.utransprod.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

/**
 * Interface for DAO mot tabell ORDER
 * 
 * @author atle.brekka
 */
public interface OrderDAO extends DAO<Order> {
    /**
     * Fjerner alle
     */
    void removeAll();

    /**
     * Henter alle nye order, altså de som ikke har satt transport
     * 
     * @return ordre
     */
    List<Order> getAllNewOrders();

    /**
     * Finner ordre basert på eksempel
     * 
     * @param order
     * @return ordre
     */
    List<Order> findByOrder(Order order);

    /**
     * Oppdaterer objekt
     * 
     * @param order
     */
    void refreshObject(Order order);

    /**
     * Lazy laster ordre
     * 
     * @param order
     * @param lazyEnums
     */
    void lazyLoad(Order order, LazyLoadOrderEnum[] lazyEnums);

    /**
     * Finner alle ordre som har montering
     * 
     * @return ordre
     */
    List<Order> findAllAssemblyOrders();

    /**
     * Lazy laster objekttre under ordre
     * 
     * @param order
     */
    void lazyLoadTree(Order order);

    /**
     * Aggregerer antall og kostnader for ordre
     * 
     * @param fromString
     * @param toString
     * @param transportConstraintEnum
     * @param productArea
     * @return aggregert antall og kostnad
     */
    List<Object[]> countOrderPostShipmentAndCosts(String fromString, String toString, TransportConstraintEnum transportConstraintEnum,
	    ProductArea productArea);

    /**
     * Teller antall order
     * 
     * @param fromString
     * @param toString
     * @param transportConstraintEnum
     * @param productArea
     * @return antall ordre
     */
    Map<String, Integer> getCountOrderAndPostShipment(String fromString, String toString, TransportConstraintEnum transportConstraintEnum,
	    ProductArea productArea);

    /**
     * Finner ordre som ikke er sendt eller er historiske
     * 
     * @return ordre
     */
    Set<Order> findNotSent();

    /**
     * Lazy laster ordrelinjer og kollier
     * 
     * @param orderId
     * @return ordre som er lastet med ordrelinjer og kollier
     */
    Order lazyLoadOrderLineAndCollies(Integer orderId);

    /**
     * Hent alle avviksordrelinjer
     * 
     * @return avviksordrelinjer
     */
    List<OrderLine> getAllDeviationOrderLines();

    /**
     * Finner alle ordre som er klare til fakturering
     * 
     * @param orderNr
     * @return ordre
     */
    List<Order> findReadyForInvoice(String orderNr);

    /**
     * Finn ordre klar til fakturering
     * 
     * @param customerNr
     * @return ordre
     */
    List<Order> findReadyForInvoiceCustomerNr(Integer customerNr);

    /**
     * Finner basert på ordrenummer
     * 
     * @param orderNr
     * @return ordre
     */
    Order findByOrderNr(String orderNr);

    /**
     * Finner alle ordre som ikke er sent
     * 
     * @param criteria
     * @param orderBy
     * @return ordre
     */
    List<Order> findAllNotSent(String criteria, String orderBy);

    List<Order> findAllNotSentByProductArea(String criteria, String orderBy, ProductArea productArea);

    /**
     * Finner alle som ikke er sendt basert på kundenummer
     * 
     * @param customerNr
     * @return ordre
     */
    List<Order> findNotSentByCustomerNr(Integer customerNr);

    /**
     * Finner alle som ikke er sendt basert på ordrenummer
     * 
     * @param orderNr
     * @return ordre
     */
    List<Order> findNotSentByOrderNr(String orderNr);

    List<Order> findSentInPeriod(Periode periode, String productAreaGroupName);

    List<Order> findSentInPeriodByProductArea(Integer year, Integer weekFrom, Integer weekTo, ProductArea productArea);

    /**
     * Henter ut ordre og etterleveringer for en gitt periode
     * 
     * @param fromString
     * @param toString
     * @param transportConstraintEnum
     * @param productArea
     * @return ordre og etterleveringer
     */
    List<Object[]> getOrdersPostShipmentsAndCosts(final String fromString, final String toString,
	    final TransportConstraintEnum transportConstraintEnum, ProductArea productArea);

    /**
     * Henter antall pakklister klare for en periode
     * 
     * @param fromDate
     * @param toDate
     * @return antall
     */
    Integer getPacklistCountForWeek(Date fromDate, Date toDate);

    /**
     * Lagrer ordre
     * 
     * @param order
     * @return feilmeling
     */
    String saveOrder(Order order);

    /**
     * Henter antall pakklister klare for en periode for gitt
     * produktområdegruppe
     * 
     * @param fromDate
     * @param toDate
     * @param group
     * @return antall
     */
    Integer getPacklistCountForWeekByProductAreaGroupName(Date fromDate, Date toDate, ProductAreaGroup group);

    List<Order> findByConfirmWeekProductArea(Integer year, Integer weekFrom, Integer weekTo, String productAreaName);

    List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(ProductArea productArea, Periode periode);

    List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(ProductArea productArea, Periode periode);

    Integer countByProductAreaPeriode(ProductArea productArea, Periode periode);

    List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea productArea, Periode periode);

    List<Order> findByConfirmWeekProductAreaGroup(Integer year, Integer weekFrom, Integer weekTo, ProductAreaGroup productAreaGroup);

    String saveOrder(Order order, boolean allowEmptyArticles);

    List<SaleReportSum> sumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode);

    SaleReportSum groupSumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode);

    Order merge(Order object);
}
