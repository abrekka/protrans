package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListModel;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.OrderPanelTypeEnum;
import no.ugland.utransprod.gui.OrderPanelView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.toedter.calendar.JYearChooser;

public class ConfirmReportViewHandler implements Closeable {
	private PresentationModel presentationModel;

	private JXTable tableResult;

	private SelectionInList reportSelectionList;

	private OrderViewHandler orderViewHandler;

	private SalesmanData currentSalesmanData;

	private JButton buttonExcelResult;

	private JButton buttonExcelBasis;
	private ManagerRepository managerRepository;

	@Inject
	public ConfirmReportViewHandler(final OrderViewHandler aOrderViewHandler,
			ManagerRepository managerRepository) {
		this.managerRepository = managerRepository;
		presentationModel = new PresentationModel(new ConfirmReport());
		reportSelectionList = new SelectionInList((ListModel) presentationModel
				.getValue(ConfirmReport.PROPERTY_REPORT_LIST));
		orderViewHandler = aOrderViewHandler;

	}

	public final JYearChooser getYearChooser() {
		JYearChooser yearChooser = new JYearChooser();
		yearChooser.setName("YearChooser");
		PropertyConnector conn = new PropertyConnector(yearChooser, "year",
				presentationModel.getModel(ConfirmReport.PROPERTY_YEAR),
				"value");
		conn.updateProperty2();
		return yearChooser;
	}

	public final JComboBox getComboBoxWeekFrom() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
				presentationModel.getModel(ConfirmReport.PROPERTY_WEEK_FROM)));
		comboBox.setName("ComboBoxWeekFrom");
		return comboBox;
	}

	public final JComboBox getComboBoxWeekTo() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
				presentationModel.getModel(ConfirmReport.PROPERTY_WEEK_TO)));
		comboBox.setName("ComboBoxWeekTo");
		return comboBox;
	}

	public final JComboBox getComboBoxProductAreaGroup() {
		JComboBox comboBox = Util
				.createComboBoxProductAreaGroup(presentationModel
						.getModel(ConfirmReport.PROPERTY_PRODUCT_AREA_GROUP));
		comboBox.setName("ComboBoxProductAreaGroup");
		return comboBox;
	}

	public final JButton getButtonGenerateReport(final WindowInterface window) {
		JButton button = new JButton(new GenerateReportAction(window));
		button.setName("ButtonGenerateReport");
		return button;
	}

	public final JButton getButtonCancel(final WindowInterface window) {
		JButton button = new CancelButton(window, this, true);
		button.setName("ButtonCancel");
		return button;
	}

	public final JButton getButtonExcelResult(final WindowInterface window) {
		buttonExcelResult = new JButton(new ExcelResultAction(window));
		buttonExcelResult.setName("ButtonExcelResult");
		buttonExcelResult.setIcon(IconEnum.ICON_EXCEL.getIcon());
		return buttonExcelResult;
	}

	public final JButton getButtonExcelBasis(final WindowInterface window) {
		buttonExcelBasis = new JButton(new ExcelBasisAction(window));
		buttonExcelBasis.setName("ButtonExcelBasis");
		buttonExcelBasis.setIcon(IconEnum.ICON_EXCEL.getIcon());
		return buttonExcelBasis;
	}

	public final JXTable getTableResult() {
		tableResult = new JXTable(new ConfirmReportTableModel(
				reportSelectionList));
		tableResult.setName("TableResult");
		tableResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableResult.setColumnControlVisible(true);

		tableResult.setSelectionModel(new SingleListSelectionAdapter(
				reportSelectionList.getSelectionIndexHolder()));

		tableResult.getColumnExt(0).setPreferredWidth(150);
		tableResult.getColumnExt(1).setPreferredWidth(50);
		tableResult.getColumnExt(2).setPreferredWidth(110);
		tableResult.getColumnExt(3).setPreferredWidth(50);
		tableResult.getColumnExt(4).setPreferredWidth(80);
		tableResult.getColumnExt(5).setPreferredWidth(50);
		tableResult.getColumnExt(6).setPreferredWidth(100);

		return tableResult;
	}

	public final OrderPanelView getOrderPanelView() {

		return new OrderPanelView(orderViewHandler,
				OrderPanelTypeEnum.CONFIRM_REPORT, null);
	}

	public class ConfirmReport extends Model {
		private static final long serialVersionUID = 1L;

		public static final String PROPERTY_YEAR = "year";

		public static final String PROPERTY_WEEK_FROM = "weekFrom";
		public static final String PROPERTY_WEEK_TO = "weekTo";

		public static final String PROPERTY_REPORT_LIST = "reportList";
		public static final String PROPERTY_PRODUCT_AREA_GROUP = "productAreaGroup";

		private Integer year;

		private Integer weekFrom;
		private Integer weekTo;

		private final ArrayListModel reportList;

		private ArrayListModel reportBasisList;
		private ProductAreaGroup productAreaGroup;

		public ConfirmReport() {
			reportList = new ArrayListModel();
			year = Util.getCurrentYear();
			weekFrom = Util.getCurrentWeek();
			weekTo = Util.getCurrentWeek();
		}

		public final ArrayListModel getReportList() {
			return reportList;
		}

		public final void setReportList(final ArrayListModel aReportList) {
			ArrayListModel oldList = new ArrayListModel(getReportList());
			reportList.clear();
			reportList.addAll(aReportList);
			firePropertyChange(PROPERTY_REPORT_LIST, oldList, aReportList);
		}

		public final ArrayListModel getReportBasisList() {
			if (reportBasisList != null) {
				return new ArrayListModel(reportBasisList);
			}
			return null;
		}

		public final Integer getYear() {
			return year;
		}

		public final void setYear(final Integer aYear) {
			Integer oldYear = getYear();
			year = aYear;
			firePropertyChange(PROPERTY_YEAR, oldYear, aYear);
		}

		public final Integer getWeekFrom() {
			return weekFrom;
		}

		public final void setWeekFrom(final Integer aWeekFrom) {
			Integer oldWeekFrom = getWeekFrom();
			weekFrom = aWeekFrom;
			firePropertyChange(PROPERTY_WEEK_FROM, oldWeekFrom, aWeekFrom);
		}

		public final Integer getWeekTo() {
			return weekTo;
		}

		public final void setWeekTo(final Integer aWeekTo) {
			Integer oldWeekTo = getWeekTo();
			weekTo = aWeekTo;
			firePropertyChange(PROPERTY_WEEK_TO, oldWeekTo, aWeekTo);
		}

		public final ProductAreaGroup getProductAreaGroup() {
			return productAreaGroup;
		}

		public final void setProductAreaGroup(
				final ProductAreaGroup aProductAreaGroup) {
			ProductAreaGroup newGroup = aProductAreaGroup;
			if (newGroup != null
					&& newGroup.getProductAreaGroupName().equalsIgnoreCase(
							"Alle")) {
				newGroup = null;
			}
			ProductAreaGroup oldGroup = getProductAreaGroup();
			productAreaGroup = newGroup;
			firePropertyChange(PROPERTY_PRODUCT_AREA_GROUP, oldGroup, newGroup);
		}
	}

	private class GenerateReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public GenerateReportAction(final WindowInterface aWindow) {
			super("Rapport");
			window = aWindow;
		}

		public void actionPerformed(final ActionEvent e) {
			Util.runInThreadWheel(window.getRootPane(),
					new ConfrmReportGenerator(), null);

		}

	}

	private static final class ConfirmReportTableModel extends
			AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Selger", "Antall",
				"Egenproduksjon", "Frakt", "Montering", "Intern",
				"Dekningsgrad" };

		/**
		 * @param listModel
		 */
		ConfirmReportTableModel(final ListModel listModel) {
			super(listModel, COLUMNS);
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(final int rowIndex, final int columnIndex) {

			SalesmanData salesmanData = (SalesmanData) getRow(rowIndex);

			switch (columnIndex) {
			case 0:
				return salesmanData.getSalesmanName();
			case 1:
				return salesmanData.getNumberOfOrders();
			case 2:
				return salesmanData.getOwnProductionCostCustomer();
			case 3:
				return salesmanData.getDeliveryCostCustomer();
			case 4:
				return salesmanData.getAssemblyCostCustomer();
			case 5:
				return salesmanData.getOwnProductionCostInternal();
			case 6:
				return salesmanData.getContributionRate();
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * Henter kolonneklasse
		 * 
		 * @param columnIndex
		 * @return kolonneklasse
		 */
		@Override
		public Class<?> getColumnClass(final int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return Integer.class;
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				return BigDecimal.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	public final boolean canClose(final String actionString,
			final WindowInterface window) {
		return true;
	}

	public final Dimension getWindowSize() {
		return new Dimension(800, 600);
	}

	class ConfrmReportGenerator implements Threadable {

		public void doWhenFinished(final Object object) {
		}

		public Object doWork(final Object[] params, final JLabel labelInfo) {
			labelInfo.setText("Genererer avropsrapport...");
			generateConfirmReport();
			return null;
		}

		public void enableComponents(final boolean enable) {
		}

	}

	final void generateConfirmReport() {
		List<Order> orders = managerRepository
				.getOrderManager()
				.findByConfirmWeekProductAreaGroup(
						(Integer) presentationModel
								.getValue(ConfirmReport.PROPERTY_YEAR),
						(Integer) presentationModel
								.getValue(ConfirmReport.PROPERTY_WEEK_FROM),
						(Integer) presentationModel
								.getValue(ConfirmReport.PROPERTY_WEEK_TO),
						(ProductAreaGroup) presentationModel
								.getValue(ConfirmReport.PROPERTY_PRODUCT_AREA_GROUP));
		if (orders != null) {
			aggregateConfirmReportData(orders, managerRepository
					.getOrderManager());
		}
	}

	final void aggregateConfirmReportData(final List<Order> orders,
			final OrderManager orderManager) {
		List<SalesmanData> salesmanDataList = new ArrayList<SalesmanData>();
		SalesmanData currentData = new SalesmanData("");

		String salesman;

		for (Order order : orders) {
			orderManager.lazyLoadOrder(order,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });
			salesman = order.getSalesman();
			if (salesman == null) {
				salesman = "ikke satt";
			}
			if (!salesman.equalsIgnoreCase(currentData.getSalesmanName())) {
				addSalesmanData(currentData, salesmanDataList);
				currentData = new SalesmanData(salesman);
			}
			addOrderAndCosts(currentData, order);

		}
		addSalesmanData(currentData, salesmanDataList);
		presentationModel.setValue(ConfirmReport.PROPERTY_REPORT_LIST,
				new ArrayListModel(salesmanDataList));
	}

	private void addOrderAndCosts(final SalesmanData salesmanData,
			final Order order) {
		salesmanData.addOrder(order);
		salesmanData.addOwnProductionCostCustomer(order.getCost(
				"Egenproduksjon", "Kunde"));
		salesmanData.addDeliveryCostCustomer(order.getCost("Frakt", "Kunde"));
		salesmanData.addAssemblyCostCustomer(order
				.getCost("Montering", "Kunde"));
		salesmanData.addOwnProductionCostInternal(order.getCost(
				"Egenproduksjon", "Intern"));
	}

	private void addSalesmanData(final SalesmanData salesmanData,
			final List<SalesmanData> salesmanDataList) {
		if (salesmanData.getSalesmanName() != null
				&& salesmanData.getSalesmanName().length() != 0) {
			salesmanDataList.add(salesmanData);
		}
	}

	private class SalesmanData {
		private String salesmanName;

		private BigDecimal ownProductionCostCustomer;

		private BigDecimal deliveryCostCustomer;

		private BigDecimal assemblyCostCustomer;

		private BigDecimal ownProductionCostInternal;

		private List<Order> orders;

		public SalesmanData(final String aSalesmanName) {
			salesmanName = aSalesmanName;
			ownProductionCostCustomer = BigDecimal.valueOf(0);
			deliveryCostCustomer = BigDecimal.valueOf(0);
			assemblyCostCustomer = BigDecimal.valueOf(0);
			ownProductionCostInternal = BigDecimal.valueOf(0);
			orders = new ArrayList<Order>();
		}

		public final String getSalesmanName() {
			return salesmanName;
		}

		public final void addOwnProductionCostCustomer(final BigDecimal cost) {
			if (cost != null) {
				ownProductionCostCustomer = ownProductionCostCustomer.add(cost);
			}
		}

		public final void addDeliveryCostCustomer(final BigDecimal cost) {
			if (cost != null) {
				deliveryCostCustomer = deliveryCostCustomer.add(cost);
			}
		}

		public final void addAssemblyCostCustomer(final BigDecimal cost) {
			if (cost != null) {
				assemblyCostCustomer = assemblyCostCustomer.add(cost);
			}
		}

		public final void addOwnProductionCostInternal(final BigDecimal cost) {
			if (cost != null) {
				ownProductionCostInternal = ownProductionCostInternal.add(cost);
			}
		}

		public final void addOrder(final Order order) {
			orders.add(order);
		}

		public final Integer getNumberOfOrders() {
			return orders.size();
		}

		public BigDecimal getAssemblyCostCustomer() {
			return assemblyCostCustomer;
		}

		public BigDecimal getDeliveryCostCustomer() {
			return deliveryCostCustomer;
		}

		public BigDecimal getOwnProductionCostCustomer() {
			return ownProductionCostCustomer;
		}

		public BigDecimal getOwnProductionCostInternal() {
			return ownProductionCostInternal;
		}

		public final BigDecimal getContributionRate() {
			if (ownProductionCostCustomer.intValue() != 0) {
				BigDecimal customerMinusInternal = ownProductionCostCustomer
						.subtract(ownProductionCostInternal);
				BigDecimal customerMinusInternalDivideCustomer = customerMinusInternal
						.divide(ownProductionCostCustomer, 2,
								RoundingMode.HALF_EVEN);
				return BigDecimal.valueOf(1).subtract(
						customerMinusInternalDivideCustomer);
			}
			return BigDecimal.valueOf(0);
		}

		public final List<Order> getOrders() {
			return orders;
		}
	}

	public final ComponentListener getPanelBasisListener(
			final WindowInterface window) {
		return new PanelBasisListener(window);
	}

	private class PanelBasisListener extends ComponentAdapter {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public PanelBasisListener(final WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ComponentAdapter#componentShown(java.awt.event.ComponentEvent)
		 */
		@Override
		public void componentShown(final ComponentEvent event) {
			JTabbedPane parent = null;
			if (event.getSource() instanceof JPanel) {
				if (((JPanel) event.getSource()).getParent() instanceof JTabbedPane) {
					parent = (JTabbedPane) ((JPanel) event.getSource())
							.getParent();
				}
			}
			Util.runInThreadWheel(window.getRootPane(), new Threadable() {

				public void enableComponents(final boolean enable) {
				}

				public Object doWork(final Object[] params,
						final JLabel labelInfo) {
					labelInfo.setText("Henter grunnlag...");
					JTabbedPane tabbedPane = null;
					if (params != null) {
						tabbedPane = (JTabbedPane) params[0];
					}
					showSelectedSalesman(tabbedPane);
					return null;
				}

				public void doWhenFinished(final Object object) {
				}

			}, new Object[] { parent });

		}

	}

	final void showSelectedSalesman(final JTabbedPane tabbedPane) {
		if (reportSelectionList.hasSelection()) {
			SalesmanData salesmanData = getSelectedSalesman();
			if (tabbedPane != null) {
				tabbedPane.setTitleAt(1, "Grunnlag "
						+ salesmanData.getSalesmanName());
			}
			changeBasisList(salesmanData);
		}

	}

	private void changeBasisList(final SalesmanData salesmanData) {
		if (currentSalesmanData == null
				|| !currentSalesmanData.equals(salesmanData)) {
			currentSalesmanData = salesmanData;
			orderViewHandler.setOrderPanelList(salesmanData.getOrders());
		}
	}

	private SalesmanData getSelectedSalesman() {
		int index = reportSelectionList.getSelectionIndex();
		SalesmanData salesmanData = (SalesmanData) reportSelectionList
				.getElementAt(tableResult.convertRowIndexToModel(index));
		return salesmanData;
	}

	private class ExcelResultAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public ExcelResultAction(final WindowInterface aWindow) {
			super("Excel");
			window = aWindow;
		}

		public void actionPerformed(final ActionEvent e) {
			generateExcelForResult(window);

		}
	}

	private class ExcelBasisAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public ExcelBasisAction(final WindowInterface aWindow) {
			super("Excel");
			window = aWindow;
		}

		public void actionPerformed(final ActionEvent e) {
			generateExcelForBasis(window);
		}
	}

	final void generateExcelForResult(final WindowInterface window) {
		Map<Integer, Integer> colSizes = new Hashtable<Integer, Integer>();
		colSizes.put(0, 5000);
		colSizes.put(2, 5000);
		colSizes.put(4, 4000);
		ExcelUtil.showDataInExcelInThread(window, "Avropsrapport.xls",
				"Avropsrapport", tableResult, null, colSizes, 12, false);
	}

	final void generateExcelForBasis(final WindowInterface window) {
		Map<Integer, Integer> colSizes = new Hashtable<Integer, Integer>();

		colSizes.put(0, 7000);
		colSizes.put(2, 5000);
		colSizes.put(3, 4000);
		colSizes.put(4, 4000);
		colSizes.put(6, 5000);
		colSizes.put(7, 5000);
		colSizes.put(9, 4000);
		SalesmanData salesman = getSelectedSalesman();
		List<Integer> numCols = new ArrayList<Integer>();
		numCols.add(7);
		numCols.add(8);
		numCols.add(9);
		numCols.add(10);
		numCols.add(11);
		ExcelUtil.showDataInExcelInThread(window, "Avropsrapport_grunnlag.xls",
				"Grunnlag " + salesman.getSalesmanName(), orderViewHandler
						.getPanelTableOrders(OrderPanelTypeEnum.CONFIRM_REPORT,
								window), numCols, colSizes, 12, false);
	}

}
