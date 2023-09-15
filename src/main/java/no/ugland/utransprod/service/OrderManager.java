
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.handlers.ReportDataTransport;
import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.gui.model.Delelisteinfo;
import no.ugland.utransprod.gui.model.Ordreinfo;
import no.ugland.utransprod.gui.model.Ordrelinjeinfo;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.ReadyCount;
import no.ugland.utransprod.util.excel.ExcelManager;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

public interface OrderManager extends OverviewManager<Order>, ExcelManager {
	String MANAGER_NAME = "orderManager";

	void removeAll();

	List<Order> getAllNewOrders();

	void saveOrder(Order var1) throws ProTransException;

	Order findByOrderNr(String var1);

	List<Order> findAll();

	List<Order> findByOrder(Order var1);

	void removeOrder(Order var1);

	void lazyLoadOrder(Order var1, LazyLoadOrderEnum[] var2);

	List<Order> getAllAssemblyOrders();

	void lazyLoadTree(Order var1);

	List<ReportDataTransport> countOrderAndCosts(String var1, String var2, TransportConstraintEnum var3,
			ProductArea var4);

	Set<Order> findNotSent();

	Order lazyLoadOrderLineAndCollies(Integer var1);

	List<OrderLine> getAllDeviationOrderLines();

	List<Order> findReadyForInvoice();

	List<Order> findReadyForInvoice(String var1);

	List<Order> findReadyForInvoiceCustomerNr(Integer var1);

	List<Order> findAllNotSent();

	List<Order> findNotSentByOrderNr(String var1);

	List<Order> findNotSentByCustomerNr(Integer var1);

	List<ReportDataTransport> getOrdersAndCosts(String var1, String var2, TransportConstraintEnum var3,
			ProductArea var4);

	Integer getPacklistCountForWeek(Date var1, Date var2);

	Integer getPacklistCountForWeekByProductAreaGroupName(Date var1, Date var2);

	List<Order> findSentInPeriod(Periode var1, String var2);

	List<Order> findByConfirmWeekProductArea(Integer var1, Integer var2, Integer var3, String var4);

	List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(ProductArea var1, Periode var2);

	List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(ProductArea var1, Periode var2);

	Integer countByProductAreaPeriode(ProductArea var1, Periode var2);

	List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea var1, Periode var2);

	List<Order> findByConfirmWeekProductAreaGroup(Integer var1, Integer var2, Integer var3, ProductAreaGroup var4);

	void saveOrder(Order var1, boolean var2) throws ProTransException;

	List<SaleReportSum> sumByProductAreaConfirmPeriode(ProductArea var1, Periode var2);

	List<ReadyCount> findReadyCountByProductArea(ProductArea var1);

	SaleReportSum groupSumByProductAreaConfirmPeriode(ProductArea var1, Periode var2);

	List<Ordreinfo> finnOrdreinfo(String var1);

	List<Ordrelinjeinfo> finnOrdrelinjeinfo(Integer var1);

	void fjernOrdreKomplett(String var1);

	void settOrdreKomplett(String var1, Date var2);

	void settStatus(Integer var1, String var2);

	List<Delelisteinfo> finnDeleliste(String var1, String var2, String var3, String var4);

	void settMontering(Integer var1, Assembly var2);

	void settMontering(Integer var1, boolean var2);

	Order getOrderWithOrderLinesAndCollies(String var1);

	List<String> finnMonteringsanvisninger(String var1);

	void taBortSentOgManglerKolli(Order var1);

	void settSentDato(Order var1, Date var2);

	void settLevert(Order var1, Date var2);

	void oppdaterTransportId(Order var1, Transport var2);

	void oppdaterStatus(Order var1, String var2);

	void settFakturert(String inf6, Date invoicedDate);
}
