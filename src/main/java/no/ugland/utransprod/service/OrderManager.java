package no.ugland.utransprod.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.handlers.ReportDataTransport;
import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.ReadyCount;
import no.ugland.utransprod.util.excel.ExcelManager;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

/**
 * Manager for ordre
 * @author atle.brekka
 */
public interface OrderManager extends OverviewManager<Order>, ExcelManager {
    public final static String MANAGER_NAME="orderManager";
    /**
     * Fjern alle.
     */
    void removeAll();

    /**
     * Hent alle nye ordre.
     * @return ordre
     */
    List<Order> getAllNewOrders();

    /**
     * Lagrer ordre.
     * @param order
     * @throws ProTransException
     */
    void saveOrder(Order order) throws ProTransException;

    /**
     * Finn ordre basert på nummer.
     * @param orderNr
     * @return ordre
     */
    Order findByOrderNr(String orderNr);

    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    List<Order> findAll();

    /**
     * Finn ordre basert på eksempel.
     * @param order
     * @return ordre
     */
    List<Order> findByOrder(Order order);

    /**
     * Fjern ordre.
     * @param order
     */
    void removeOrder(Order order);

    /**
     * Lazy laster ordre.
     * @param order
     * @param enums
     */
    void lazyLoadOrder(Order order, LazyLoadOrderEnum[] enums);

    /**
     * Finner alle ordre som skal monteres.
     * @return ordre
     */
    List<Order> getAllAssemblyOrders();

    /**
     * Lazy laster ordretre.
     * @param order
     */
    void lazyLoadTree(Order order);

    /**
     * Teller antall ordre og summererer kostnad.
     * @param fromString
     * @param toString
     * @param transportConstraintEnum
     * @param productArea
     * @return antall ordre og summert kostnad
     */
    List<ReportDataTransport> countOrderAndCosts(String fromString,
            String toString, TransportConstraintEnum transportConstraintEnum,
            ProductArea productArea);

    /**
     * Finner ordre som ikke er sendt.
     * @return ordre
     */
    Set<Order> findNotSent();

    /**
     * Lazy laster ordrelinjer og kollier.
     * @param orderId
     * @return ordre
     */
    Order lazyLoadOrderLineAndCollies(Integer orderId);

    /**
     * Finner alle ordrelinjer for avvik.
     * @return ordrelinjer
     */
    List<OrderLine> getAllDeviationOrderLines();

    /**
     * Finner alle klare for fakturering.
     * @return ordre
     */
    List<Order> findReadyForInvoice();

    /**
     * Finner alle klare for fakturering basert på ordrenummer.
     * @param orderNr
     * @return ordre
     */
    List<Order> findReadyForInvoice(String orderNr);

    /**
     * Finner alle klare for fakturering basert på kundenummer.
     * @param customerNr
     * @return ordre
     */
    List<Order> findReadyForInvoiceCustomerNr(Integer customerNr);

    /**
     * Finner alle som ikke er sendt.
     * @return ordre
     */
    List<Order> findAllNotSent();

    /**
     * Finner alle som ikke er sendt basert på ordrenummer.
     * @param orderNr
     * @return ordre
     */
    List<Order> findNotSentByOrderNr(String orderNr);

    /**
     * Finner alle som ikke er sendt basert på kundenummer.
     * @param customerNr
     * @return ordre
     */
    List<Order> findNotSentByCustomerNr(Integer customerNr);

    /**
     * Finner alle ordre og kostnader for gitt periode.
     * @param fromString
     * @param toString
     * @param transportConstraintEnum
     * @param productArea
     * @return ordre og kostnader
     */
    List<ReportDataTransport> getOrdersAndCosts(String fromString,
            String toString, TransportConstraintEnum transportConstraintEnum,
            ProductArea productArea);

    /**
     * Teller antall pakklister for periode.
     * @param fromDate
     * @param toDate
     * @return antall pakklister
     */
    Integer getPacklistCountForWeek(Date fromDate, Date toDate);

    /**
     * Henter antall pakklister for periode og produktområdegruppe.
     * @param fromDate
     * @param toDate
     * @param group
     * @return antall
     */
    Integer getPacklistCountForWeekByProductAreaGroupName(Date fromDate,
            Date toDate, ProductAreaGroup group);

    List<Order> findSentInPeriod(Periode periode, String productAreaGroupName);
    List<Order> findByConfirmWeekProductArea(Integer year,Integer weekFrom,Integer weekTo,String productAreaName);
    List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(ProductArea productArea,Periode periode);
    List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(ProductArea productArea,Periode periode);
    Integer countByProductAreaPeriode(ProductArea productArea,Periode periode);
    List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea productArea,Periode periode);
    List<Order> findByConfirmWeekProductAreaGroup(Integer year,Integer weekFrom,Integer weekTo,ProductAreaGroup productAreaName);

	void saveOrder(Order incomingOrder, boolean allowEmptyArticles)throws ProTransException;

	List<SaleReportSum> sumByProductAreaConfirmPeriode(ProductArea productArea,
			Periode periode);
	List<ReadyCount> findReadyCountByProductArea(
			final ProductArea productArea);

	SaleReportSum groupSumByProductAreaConfirmPeriode(ProductArea productArea,
			Periode periode);
	
}
