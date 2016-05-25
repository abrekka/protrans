package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.OrderDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.gui.handlers.ReportDataTransport;
import no.ugland.utransprod.gui.model.Ordreinfo;
import no.ugland.utransprod.gui.model.Ordrelinjeinfo;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.TakstolInfoVManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.ReadyCount;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

/**
 * Implementasjon av servidceklasse for tabell CUSTOMER_ORDER.
 * 
 * @author atle.brekka
 */
public class OrderManagerImpl extends ManagerImpl<Order> implements OrderManager {
	/**
	 * @see no.ugland.utransprod.service.OrderManager#removeAll()
	 */
	public final void removeAll() {
		dao.removeAll();

	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#getAllNewOrders()
	 */
	public final List<Order> getAllNewOrders() {
		return ((OrderDAO) dao).getAllNewOrders();
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#saveOrder(no.ugland.utransprod.model.Order)
	 */
	public final void saveOrder(final Order order) throws ProTransException {
		String errorMsg = ((OrderDAO) dao).saveOrder(order);
		if (errorMsg != null) {
			throw new ProTransException(errorMsg);
		}
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findByOrderNr(java.lang.String)
	 */
	public final Order findByOrderNr(final String orderNr) {
		Order order = new Order();
		order.setOrderNr(orderNr);
		List<Order> orders = dao.findByExample(order);
		if (orders == null || orders.size() != 1) {
			return null;
		}
		return orders.get(0);
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findAll()
	 */
	public final List<Order> findAll() {

		return dao.getObjects();
	}

	/**
	 * @param object
	 * @return ordre
	 * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
	 */
	public final List<Order> findByObject(final Order object) {
		return dao.findByExampleLike(object);
		// return findByOrder(object);
	}

	/**
	 * @param object
	 * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
	 */
	public final void removeObject(final Order object) {
		removeOrder(object);

	}

	/**
	 * @param object
	 * @throws ProTransException
	 * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
	 */
	public final void saveObject(final Order object) throws ProTransException {
		saveOrder(object);
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findByOrder(no.ugland.utransprod.model.Order)
	 */
	public final List<Order> findByOrder(final Order order) {
		return ((OrderDAO) dao).findByOrder(order);
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#removeOrder(no.ugland.utransprod.model.Order)
	 */
	public final void removeOrder(final Order order) {
		dao.removeObject(order.getOrderId());

	}

	/**
	 * @param order
	 * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
	 */
	public final void refreshObject(final Order order) {
		((OrderDAO) dao).refreshObject(order);

	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#lazyLoadOrder(no.ugland.utransprod.model.Order,
	 *      no.ugland.utransprod.service.enums.LazyLoadOrderEnum[])
	 */
	public final void lazyLoadOrder(final Order order, final LazyLoadOrderEnum[] enums) {
		((OrderDAO) dao).lazyLoad(order, enums);

	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#getAllAssemblyOrders()
	 */
	public final List<Order> getAllAssemblyOrders() {
		return ((OrderDAO) dao).findAllAssemblyOrders();
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#lazyLoadTree(no.ugland.utransprod.model.Order)
	 */
	public final void lazyLoadTree(final Order order) {
		((OrderDAO) dao).lazyLoadTree(order);

	}

	public final List<ReportDataTransport> getOrdersAndCosts(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {

		List<Object[]> list = ((OrderDAO) dao).getOrdersPostShipmentsAndCosts(fromString, toString,
				transportConstraintEnum, productArea);

		if (list != null && list.size() != 0) {
			TakstolInfoVManager takstolInfoVManager = (TakstolInfoVManager) ModelUtil
					.getBean(TakstolInfoVManager.MANAGER_NAME);
			List<Object[]> beregnetTidForOrdre = takstolInfoVManager.getBeregnetTidForOrdre(fromString, toString,
					transportConstraintEnum, productArea.getProductArea());
			list.addAll(beregnetTidForOrdre);
			Collections.sort(list, sorterId());
			return generateReportData(list);
		}
		return null;
	}

	private Comparator<Object[]> sorterId() {
		return new Comparator<Object[]>() {

			public int compare(Object[] o1, Object[] o2) {
				Integer orderId1 = (Integer) o1[0];
				Integer orderId2 = (Integer) o2[0];
				return orderId1.compareTo(orderId2);
			}
		};
	}

	private List<ReportDataTransport> generateReportData(final List<Object[]> list) {
		Integer orderId;
		Integer postShipmentId;
		Boolean isPostShipment;
		Integer currentOrderId = 0;
		Integer currentPostShipmentId = 0;

		ReportDataTransport reportDataTransport = null;
		ReportDataTransport.clearCostHeading();
		List<ReportDataTransport> reportDataTransportList = new ArrayList<ReportDataTransport>();
		Map<String, BigDecimal> costs = null;

		for (Object[] object : list) {
			orderId = (Integer) object[0];
			if (object.length < 14) {
				System.out.println("test");
			}
			postShipmentId = (Integer) object[13];
			isPostShipment = isPostShipment(postShipmentId);

			if (!orderId.equals(currentOrderId) || (!postShipmentId.equals(currentPostShipmentId))) {
				addReportDataTransportToList(reportDataTransport, reportDataTransportList, costs);
				reportDataTransport = createNewReportDataTransport(isPostShipment, object);
				currentOrderId = orderId;
				currentPostShipmentId = postShipmentId;
				costs = new Hashtable<String, BigDecimal>();

			}
			if (object[10] != null) {
				createCostColumn(costs, object);
			}
		}

		addReportDataTransportToList(reportDataTransport, reportDataTransportList, costs);
		return reportDataTransportList;
	}

	private Boolean isPostShipment(final Integer postShipmentId) {
		Boolean isPostShipment;
		if (postShipmentId == -1) {
			isPostShipment = Boolean.FALSE;
		} else {
			isPostShipment = Boolean.TRUE;
		}
		return isPostShipment;
	}

	private void addReportDataTransportToList(final ReportDataTransport reportDataTransport,
			final List<ReportDataTransport> reportDataTransportList, final Map<String, BigDecimal> costs) {
		if (reportDataTransport != null) {
			reportDataTransport.setCosts(costs);
			reportDataTransportList.add(reportDataTransport);
		}
	}

	private void createCostColumn(final Map<String, BigDecimal> costs, final Object[] object) {
		String heading = (String) object[10] + " - " + (String) object[12];

		if (costs != null) {
			BigDecimal cost = costs.get(heading);
			if (cost != null) {
				cost = cost.add((BigDecimal) object[11]);
			} else {
				cost = (BigDecimal) object[11];
			}
			costs.put(heading, cost);
			ReportDataTransport.addCostHeading(heading);
		}
	}

	private ReportDataTransport createNewReportDataTransport(final Boolean isPostShipment, final Object[] object) {
		ReportDataTransport reportDataTransport;
		reportDataTransport = new ReportDataTransport();

		reportDataTransport.setIsPostShipment(isPostShipment);
		reportDataTransport.setOrderId((Integer) object[0]);
		reportDataTransport.setOrderNr((String) object[1]);
		reportDataTransport.setCustomerNr((Integer) object[2]);
		reportDataTransport.setCustomerName((String) object[3]);
		reportDataTransport.setDeliveryAddress((String) object[4]);
		reportDataTransport.setPostalCode((String) object[5]);
		reportDataTransport.setTransportName((String) object[6]);
		reportDataTransport.setYear((Integer) object[7]);
		reportDataTransport.setWeek((Integer) object[8]);
		reportDataTransport.setProductionWeek((Integer) object[9]);

		return reportDataTransport;
	}

	public final List<ReportDataTransport> countOrderAndCosts(final String fromString, final String toString,
			final TransportConstraintEnum transportConstraintEnum, final ProductArea productArea) {

		Map<String, Integer> numberOfOrders = ((OrderDAO) dao).getCountOrderAndPostShipment(fromString, toString,
				transportConstraintEnum, productArea);
		List<Object[]> list = ((OrderDAO) dao).countOrderPostShipmentAndCosts(fromString, toString,
				transportConstraintEnum, productArea);
		TakstolInfoVManager takstolInfoVManager = (TakstolInfoVManager) ModelUtil
				.getBean(TakstolInfoVManager.MANAGER_NAME);

		Integer currentYear = 0;
		Integer currentWeek = 0;
		Integer year = 0;
		Integer week = 0;
		String yearString;

		ReportDataTransport reportDataTransport = null;
		ReportDataTransport.clearCostHeading();
		List<ReportDataTransport> reportDataTransportList = new ArrayList<ReportDataTransport>();
		Map<String, BigDecimal> costs = null;
		Integer numberOf;

		if (list != null && list.size() != 0) {
			List<Object[]> beregnetTid = takstolInfoVManager.summerArbeidsinnsats(fromString, toString,
					transportConstraintEnum, productArea.getProductArea());
			list.addAll(beregnetTid);
			Collections.sort(list, aarUke());
			for (Object[] object : list) {
				year = (Integer) object[1];
				week = (Integer) object[2];

				yearString = String.format("%1$d%2$02d", year, week);
				numberOf = numberOfOrders.get(yearString);

				if (!year.equals(currentYear) || !week.equals(currentWeek)) {
					addReportDataTransportToList(reportDataTransport, reportDataTransportList, costs);
					reportDataTransport = new ReportDataTransport();
					reportDataTransport.setYear(year);
					reportDataTransport.setWeek(week);
					reportDataTransport.setNumberOf(numberOf);
					currentYear = year;
					currentWeek = week;
					costs = new Hashtable<String, BigDecimal>();

				}
				if (object[3] != null) {
					String heading = (String) object[3] + " - " + (String) object[5];

					if (costs != null) {
						BigDecimal cost = costs.get(heading);
						if (cost != null) {
							cost = cost.add((BigDecimal) object[4]);
						} else {
							cost = (BigDecimal) object[4];
						}
						costs.put(heading, cost);
						ReportDataTransport.addCostHeading(heading);
					}
				}
			}

			addReportDataTransportToList(reportDataTransport, reportDataTransportList, costs);
		}

		return reportDataTransportList;
	}

	private Comparator<Object[]> aarUke() {
		return new Comparator<Object[]>() {

			public int compare(Object[] o1, Object[] o2) {
				String yearString1 = String.format("%1$d%2$02d", o1[1], o1[2]);
				String yearString2 = String.format("%1$d%2$02d", o2[1], o2[2]);
				return yearString1.compareTo(yearString2);
			}
		};
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findNotSent()
	 */
	public final Set<Order> findNotSent() {
		return ((OrderDAO) dao).findNotSent();
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#lazyLoadOrderLineAndCollies(java.lang.Integer)
	 */
	public final Order lazyLoadOrderLineAndCollies(final Integer orderId) {
		return ((OrderDAO) dao).lazyLoadOrderLineAndCollies(orderId);

	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#getAllDeviationOrderLines()
	 */
	public final List<OrderLine> getAllDeviationOrderLines() {
		return ((OrderDAO) dao).getAllDeviationOrderLines();
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findReadyForInvoice()
	 */
	public final List<Order> findReadyForInvoice() {
		return ((OrderDAO) dao).findReadyForInvoice(null);
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findReadyForInvoice(java.lang.String)
	 */
	public final List<Order> findReadyForInvoice(final String orderNr) {
		return ((OrderDAO) dao).findReadyForInvoice(orderNr);
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findReadyForInvoiceCustomerNr(java.lang.Integer)
	 */
	public final List<Order> findReadyForInvoiceCustomerNr(final Integer customerNr) {
		return ((OrderDAO) dao).findReadyForInvoiceCustomerNr(customerNr);
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findAllNotSent()
	 */
	public final List<Order> findAllNotSent() {
		return ((OrderDAO) dao).findAllNotSent(null, null);
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findNotSentByCustomerNr(java.lang.Integer)
	 */
	public final List<Order> findNotSentByCustomerNr(final Integer customerNr) {
		return ((OrderDAO) dao).findNotSentByCustomerNr(customerNr);
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#findNotSentByOrderNr(java.lang.String)
	 */
	public final List<Order> findNotSentByOrderNr(final String orderNr) {
		return ((OrderDAO) dao).findNotSentByOrderNr(orderNr);
	}

	/**
	 * Finner antall klare.
	 * 
	 * @param params
	 * @return antall klare
	 */
	private List<ReadyCount> findReadyCount(final ExcelReportSetting params) {
		return findReadyCountByProductArea(params.getProductArea());
	}

	public List<ReadyCount> findReadyCountByProductArea(final ProductArea productArea) {
		List<Order> orders = ((OrderDAO) dao).findAllNotSentByProductArea("customer", "lastName", productArea);

		return createReadyCountList(orders);
	}

	private List<ReadyCount> createReadyCountList(final List<Order> orders) {
		List<ReadyCount> readyCountList = new ArrayList<ReadyCount>();
		// går gjennom alle ordre som ikke er sendt
		for (Order order : orders) {
			checkAndAddOrderToReadyCountList(readyCountList, order);
		}
		return readyCountList;
	}

	private void checkAndAddOrderToReadyCountList(final List<ReadyCount> readyCountList, final Order order) {
		int rowCounter;
		rowCounter = 0;
		// order er pakket
		// dersom ordre har kolli med innhold
		if (order.hasColliWithContent()) {
			rowCounter = addOrderToReadyCountList(readyCountList, order, rowCounter);
		}
	}

	private int addOrderToReadyCountList(final List<ReadyCount> readyCountList, final Order order, final int rows) {
		int rowCounter = rows;
		List<Colli> collies;
		List<OrderLine> missing;
		lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });

		collies = getColliesWithContent(order);

		missing = getMissingCollies(order);

		if (collies.size() >= missing.size()) {
			rowCounter = addMissingsToReadyCount(readyCountList, order, rowCounter, collies, missing);
		} else {
			rowCounter = addColliesToReadyCount(readyCountList, order, rowCounter, collies, missing);
		}
		return rowCounter;
	}

	private int addColliesToReadyCount(final List<ReadyCount> readyCountList, final Order order, final int rows,
			final List<Colli> collies, final List<OrderLine> missing) {
		int rowCounter = rows;
		for (OrderLine orderLine : missing) {
			rowCounter = addColliToReadyCount(readyCountList, order, rowCounter, collies, orderLine);
		}
		return rowCounter;
	}

	private int addColliToReadyCount(final List<ReadyCount> readyCountList, final Order order, final int rows,
			final List<Colli> collies, final OrderLine orderLine) {
		int rowCounter = rows;
		rowCounter++;
		Colli colli = null;
		if (collies.size() >= rowCounter) {
			colli = collies.get(rowCounter - 1);
		}
		readyCountList.add(new ReadyCount(order, colli, orderLine, rowCounter));
		return rowCounter;
	}

	private int addMissingsToReadyCount(final List<ReadyCount> readyCountList, final Order order, final int rows,
			final List<Colli> collies, final List<OrderLine> missing) {
		int rowCounter = rows;
		for (Colli colli : collies) {
			rowCounter = addMissingToReadyCount(readyCountList, order, rowCounter, missing, colli);
		}
		return rowCounter;
	}

	private int addMissingToReadyCount(final List<ReadyCount> readyCountList, final Order order, final int rowCounter,
			final List<OrderLine> missing, final Colli colli) {
		int rows = rowCounter + 1;

		OrderLine orderLine = null;
		if (missing.size() >= rows) {
			orderLine = missing.get(rows - 1);
		}
		readyCountList.add(new ReadyCount(order, colli, orderLine, rows));
		return rows;
	}

	private List<OrderLine> getMissingCollies(final Order order) {
		List<OrderLine> missing;
		missing = order.getMissingCollies();

		if (missing == null) {
			missing = new ArrayList<OrderLine>();
		}
		return missing;
	}

	private List<Colli> getColliesWithContent(final Order order) {
		List<Colli> collies;
		if (order.getCollies() != null) {
			collies = new ArrayList<Colli>(order.getColliesWithContent());
		} else {
			collies = new ArrayList<Colli>();
		}
		return collies;
	}

	/**
	 * Finner transportliste.
	 * 
	 * @param params
	 * @return transportliste
	 */
	private List<Order> findTransportList(final ExcelReportSetting params) {
		List<Order> orders = ((OrderDAO) dao).findSentInPeriodByProductArea(params.getYear(), params.getWeekFrom(),
				params.getWeekTo(), params.getProductArea());
		for (Order order : orders) {
			lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });
		}
		return orders;
	}

	/**
	 * Brukes av rapporten READY_COUNT.
	 * 
	 * @see no.ugland.utransprod.util.excel.ExcelManager#
	 *      findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
	 */
	public final List<?> findByParams(final ExcelReportSetting params) {
		if (params.getExcelReportType() == ExcelReportEnum.READY_COUNT) {
			return findReadyCount(params);
		}
		return findTransportList(params);

	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#getPacklistCountForWeek(java.util.Date,
	 *      java.util.Date)
	 */
	public final Integer getPacklistCountForWeek(final Date fromDate, final Date toDate) {
		return ((OrderDAO) dao).getPacklistCountForWeek(fromDate, toDate);
	}

	/**
	 * @see no.ugland.utransprod.util.excel.ExcelManager#
	 *      getInfo(no.ugland.utransprod.util.excel.ExcelReportSetting)
	 */
	public final String getInfoButtom(final ExcelReportSetting params) {
		return null;
	}

	public final String getInfoTop(final ExcelReportSetting params) {
		return null;
	}

	/**
	 * Gjør ingenting.
	 * 
	 * @see no.ugland.utransprod.util.excel.ExcelManager#
	 *      getReportDataMap(no.ugland.utransprod.util.excel.ExcelReportSetting)
	 */
	public final Map<Object, Object> getReportDataMap(final ExcelReportSetting params) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.service.OrderManager#
	 *      getPacklistCountForWeekByProductAreaGroupName(java.util.Date,
	 *      java.util.Date, no.ugland.utransprod.model.ProductAreaGroup)
	 */
	public final Integer getPacklistCountForWeekByProductAreaGroupName(final Date fromDate, final Date toDate) {
		return getPacklistCountForWeek(fromDate, toDate);
	}

	public final List<Order> findSentInPeriod(final Periode periode, final String productAreaGroupName) {
		return ((OrderDAO) dao).findSentInPeriod(periode, productAreaGroupName);
	}

	public final List<Order> findByConfirmWeekProductArea(final Integer year, final Integer weekFrom,
			final Integer weekTo, final String productAreaName) {
		return ((OrderDAO) dao).findByConfirmWeekProductArea(year, weekFrom, weekTo, productAreaName);
	}

	public List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
		return ((OrderDAO) dao).groupSumCountyByProductAreaConfirmPeriode(productArea, periode);
	}

	public List<SaleReportSum> sumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
		return ((OrderDAO) dao).sumByProductAreaConfirmPeriode(productArea, periode);
	}

	public List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
		return ((OrderDAO) dao).groupSumSalesmanByProductAreaConfirmPeriode(productArea, periode);
	}

	public Integer countByProductAreaPeriode(ProductArea productArea, Periode periode) {
		return ((OrderDAO) dao).countByProductAreaPeriode(productArea, periode);
	}

	public List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea productArea, Periode periode) {
		return ((OrderDAO) dao).getSaleReportByProductAreaPeriode(productArea, periode);
	}

	public List<Order> findByConfirmWeekProductAreaGroup(Integer year, Integer weekFrom, Integer weekTo,
			ProductAreaGroup productAreaGroup) {
		return ((OrderDAO) dao).findByConfirmWeekProductAreaGroup(year, weekFrom, weekTo, productAreaGroup);
	}

	public CheckObject checkExcel(ExcelReportSetting params) {
		// TODO Auto-generated method stub
		return null;
	}

	public void lazyLoad(Order object, Enum[] enums) {
		lazyLoadOrder(object, (LazyLoadOrderEnum[]) enums);

	}

	@Override
	protected Serializable getObjectId(Order object) {
		return object.getOrderId();
	}

	public void saveOrder(Order order, boolean allowEmptyArticles) throws ProTransException {
		if (allowEmptyArticles) {
			((OrderDAO) dao).saveOrder(order, true);
		} else {
			saveOrder(order);
		}

	}

	public SaleReportSum groupSumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
		return ((OrderDAO) dao).groupSumByProductAreaConfirmPeriode(productArea, periode);
	}

	public Order merge(Order object) {
		return ((OrderDAO) dao).merge(object);

	}

	public List<Ordreinfo> finnOrdreinfo(String orderNr) {
		return ((OrderDAO) dao).finnOrdreinfo(orderNr);
	}

	public List<Ordrelinjeinfo> finnOrdrelinjeinfo(String orderNr) {
		return ((OrderDAO) dao).finnOrdrelinjeinfo(orderNr);
	}

}
