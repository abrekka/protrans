package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ShowReportAction;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.TransportCostBasisModel;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.OrderCostTransportComparator;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.TransportCostBasis;
import no.ugland.utransprod.service.TransportCostBasisManager;
import no.ugland.utransprod.service.TransportCostManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.report.ReportViewer;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.SortOrder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

public class TransportCostViewHandler extends
		DefaultAbstractViewHandler<TransportCostBasis, TransportCostBasisModel> {

	private static final long serialVersionUID = 1L;

	private PresentationModel presentationModelPeriod;

	private Periode period;

	private JXTable tableTransportCostOrder;

	private ArrayListModel orderCostList;

	private SelectionInList orderCostSelectionList;

	private TransportCostBasis currentTransportCostBasis;
	private PresentationModel presentationModelTotalInfo;
	private JLabel labelTotalNumber;
	private JLabel labelTotalCost;

	public TransportCostViewHandler(final Login aLogin,
			TransportCostBasisManager transportCostBasisManager) {
		super("Transportkostnad", transportCostBasisManager, aLogin
				.getUserType(), true);
		period = new Periode();
		presentationModelPeriod = new PresentationModel(period);
		presentationModelTotalInfo = new PresentationModel(new TotalInfo());
		orderCostList = new ArrayListModel();
		orderCostSelectionList = new SelectionInList((ListModel) orderCostList);
	}

	public final JYearChooser getYearChooser() {
		JYearChooser yearChooser = new JYearChooser();
		yearChooser.setName("YearChooserTransportCost");
		PropertyConnector conn = new PropertyConnector(yearChooser, "year",
				presentationModelPeriod.getModel(YearWeek.PROPERTY_YEAR),
				"value");
		conn.updateProperty2();
		return yearChooser;
	}

	public final JComboBox getComboBoxWeekFrom() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
				presentationModelPeriod.getModel(YearWeek.PROPERTY_WEEK)));
		comboBox.setName("ComboBoxWeekFrom");
		return comboBox;
	}

	public final JComboBox getComboBoxWeekTo() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
				presentationModelPeriod.getModel(Periode.PROPERTY_TO_WEEK)));
		comboBox.setName("ComboBoxWeekTo");
		return comboBox;
	}

	public final JButton getButtonGenerate() {
		JButton button = new JButton(new GenerateAction());
		button.setName("ButtonGenerate");
		return button;
	}

	public JButton getButtonReport(WindowInterface window) {
		JButton button = new JButton(new ShowReportAction(window,
				new TransportCostBasisPrinter(window), null));
		button.setIcon(IconEnum.ICON_PRINT.getIcon());
		button.setName("ButtonShowReport");
		button.setEnabled(false);
		return button;
	}

	public JButton getButtonSetInvoiceNr(WindowInterface window) {
		JButton button = new JButton(new SetInvoiceNrAction());
		button.setName("ButtonSetInvoiceNr");
		button.setEnabled(false);
		return button;
	}

	public JXTable getTableTransportCostOrder() {
		tableTransportCostOrder = new JXTable();
		tableTransportCostOrder.setName("TableTransportCostTable");

		tableTransportCostOrder.setModel(new TransportCostOrderTableModel(
				orderCostSelectionList));

		tableTransportCostOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableTransportCostOrder
				.setSelectionModel(new SingleListSelectionAdapter(
						orderCostSelectionList.getSelectionIndexHolder()));
		tableTransportCostOrder.setColumnControlVisible(true);
		tableTransportCostOrder.setSearchable(null);
		tableTransportCostOrder.setRowMargin(0);
		tableTransportCostOrder.addHighlighter(HighlighterFactory
				.createAlternateStriping());

		// ordrenr
		tableTransportCostOrder.getColumnExt(0).setPreferredWidth(50);
		// kunde
		tableTransportCostOrder.getColumnExt(1).setPreferredWidth(150);
		// postnr
		tableTransportCostOrder.getColumnExt(2).setPreferredWidth(50);
		// poststed
		tableTransportCostOrder.getColumnExt(3).setPreferredWidth(100);
		// type
		tableTransportCostOrder.getColumnExt(4).setPreferredWidth(50);
		// kostnad
		tableTransportCostOrder.getColumnExt(5).setPreferredWidth(60);
		// kostnadsdetaljer
		tableTransportCostOrder.getColumnExt(6).setPreferredWidth(150);
		// transport
		tableTransportCostOrder.getColumnExt(7).setPreferredWidth(100);
		// sjåfør
		tableTransportCostOrder.getColumnExt(8).setPreferredWidth(100);

		return tableTransportCostOrder;
	}

	public final JPanel buildTabbedPane(final JButton buttonRemove,
			final JButton buttonReport, final JButton buttonSetInvoiceNr) {
		FormLayout layout = new FormLayout("150:grow", "fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Refnr", buildTransportCostBasisPanel(buttonRemove,
				buttonReport, buttonSetInvoiceNr));
		tabbedPane.add("Grunnlag", buildTransportCostOrderPanel());

		builder.add(tabbedPane, cc.xy(1, 1));

		return builder.getPanel();
	}

	private JLabel getLabelTotalNumber() {
		labelTotalNumber = BasicComponentFactory
				.createLabel(presentationModelTotalInfo
						.getModel(TotalInfo.PROPERTY_TOTAL_NUMBER_STRING));
		return labelTotalNumber;
	}

	private JLabel getLabelTotalCost() {
		labelTotalCost = BasicComponentFactory
				.createLabel(presentationModelTotalInfo
						.getModel(TotalInfo.PROPERTY_TOTAL_COST_STRING));
		return labelTotalCost;
	}

	private JPanel buildTransportCostOrderPanel() {
		FormLayout layout = new FormLayout("p,3dlu,p,3dlu,p,3dlu,40dlu:grow",
				"p,3dlu,fill:120dlu:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		getTableTransportCostOrder();
		getLabelTotalNumber();
		getLabelTotalCost();

		builder.addLabel("Totalt antall:", cc.xy(1, 1));
		builder.add(labelTotalNumber, cc.xy(3, 1));
		builder.addLabel("Totale kostnader:", cc.xy(5, 1));
		builder.add(labelTotalCost, cc.xy(7, 1));
		builder.add(new JScrollPane(tableTransportCostOrder), cc.xyw(1, 3, 7));

		JPanel panelBasis = builder.getPanel();
		panelBasis.addComponentListener(new PanelBasisListener(window));

		return panelBasis;
	}

	private JPanel buildTransportCostBasisPanel(final JButton buttonRemove,
			final JButton buttonReport, final JButton buttonSetInvoiceNr) {
		FormLayout layout = new FormLayout("50dlu:grow",
				"fill:110dlu:grow,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.add(new JScrollPane(table), cc.xy(1, 1));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonReport,
				buttonSetInvoiceNr, buttonRemove), cc.xy(1, 3));

		return builder.getPanel();
	}

	@Override
	public final CheckObject checkDeleteObject(
			final TransportCostBasis transportCostBasis) {
		overviewManager.lazyLoad(transportCostBasis, new LazyLoadEnum[][] { {
				LazyLoadEnum.ORDER_COSTS, LazyLoadEnum.NONE } });
		if (transportCostBasis.getOrderCosts() != null
				&& transportCostBasis.getOrderCosts().size() > 0) {
			OrderCost orderCost = transportCostBasis.getOrderCosts().iterator()
					.next();
			if (orderCost.getInvoiceNr() != null
					&& orderCost.getInvoiceNr().length() != 0) {
				return new CheckObject(
						"Kan ikke slette grunnlag som har satt fakturanummer",
						false);
			}
		}
		return null;
	}

	@Override
	public final CheckObject checkSaveObject(
			final TransportCostBasisModel object,
			final PresentationModel presentationModel,
			final WindowInterface aWindow) {
		return null;
	}

	@Override
	public final String getAddRemoveString() {
		return "grunnlag";
	}

	@Override
	public final String getClassName() {
		return "TransportCostBasis";
	}

	@Override
	protected final AbstractEditView<TransportCostBasisModel, TransportCostBasis> getEditView(
			final AbstractViewHandler<TransportCostBasis, TransportCostBasisModel> handler,
			final TransportCostBasis object, final boolean searching) {
		return null;
	}

	@Override
	public final TransportCostBasis getNewObject() {
		return null;
	}

	@Override
	public final TableModel getTableModel(final WindowInterface aWindow) {
		return new TransportCostBasisTableModel(objectSelectionList);
	}

	@Override
	public final String getTableWidth() {
		return null;
	}

	@Override
	public final String getTitle() {
		return "Transportkostnad";
	}

	@Override
	public final Dimension getWindowSize() {
		return new Dimension(600, 375);
	}

	@Override
	public final Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Transportkostnad");
	}

	@Override
	public final void setColumnWidth(final JXTable table) {
		table.setSortOrder(0, SortOrder.DESCENDING);
		// Refnr
		table.getColumnExt(0).setPreferredWidth(50);
		// periode
		table.getColumnExt(1).setPreferredWidth(70);
		// Transportfirma
		table.getColumnExt(2).setPreferredWidth(300);

	}

	private static final class TransportCostBasisTableModel extends
			AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Refnr", "Periode",
				"Transportfirma", "Fakturanr" };

		TransportCostBasisTableModel(ListModel listModel) {
			super(listModel, COLUMNS);
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			TransportCostBasis transportCostBasis = (TransportCostBasis) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return transportCostBasis.getTransportCostBasisId();
			case 1:
				return transportCostBasis.getPeriode();
			case 2:
				return transportCostBasis.getSupplier();
			case 3:
				return transportCostBasis.getInvoiceNr();
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
			case 3:
				return Integer.class;
			case 1:
				return String.class;
			case 2:
				return Supplier.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}
	}

	private static final class TransportCostOrderTableModel extends
			AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Ordrenr", "Kunde", "Postnr",
				"Poststed", "Type", "Kostnad", "Kostdetaljer", "Transport",
				"Sjåfør" };

		TransportCostOrderTableModel(ListModel listModel) {
			super(listModel, COLUMNS);
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			OrderCost orderCost = (OrderCost) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return orderCost.getOrder().getOrderNr();
			case 1:
				return orderCost.getOrder().getCustomer();
			case 2:
				return orderCost.getOrder().getPostalCode();
			case 3:
				return orderCost.getOrder().getPostOffice();
			case 4:
				return orderCost.getOrder().getConstructionTypeString();
			case 5:
				return orderCost.getCostAmount();
			case 6:
				return orderCost.getComment();
			case 7:
				return orderCost.getTransport();
			case 8:
				return orderCost.getOrder().getTransport().getEmployee();
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
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
			case 2:
			case 3:
			case 4:
			case 6:
				return String.class;
			case 5:
				return BigDecimal.class;
			case 1:
				return Customer.class;
			case 7:
				return Transport.class;
			case 8:
				return Employee.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	private final class GenerateAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public GenerateAction() {
			super("Generer grunnlag");
		}

		public void actionPerformed(ActionEvent e) {
			Util.runInThreadWheel(window.getRootPane(),
					new TransportCostGenerator(), null);
		}
	}

	private final class TransportCostGenerator implements Threadable {

		public void doWhenFinished(Object object) {
			if (object != null) {
				Util.showErrorDialog(window, "Feil", object.toString());
			}

		}

		public Object doWork(Object[] params, JLabel labelInfo) {
			String errorMsg = null;
			try {
				labelInfo.setText("Genererer grunnlag for transportkostnad...");
				TransportCostManager transportCostManager = (TransportCostManager) ModelUtil
						.getBean("transportCostManager");
				List<TransportCostBasis> list = transportCostManager
						.generateTransportCostList(period);
				if (list != null && list.size() != 0) {
					objectList.addAll(0, list);
				}
			} catch (ProTransException e) {
				e.printStackTrace();
				errorMsg = e.getMessage();
			}
			return errorMsg;
		}

		public void enableComponents(boolean enable) {
		}

	}

	private class PanelBasisListener extends ComponentAdapter {
		private WindowInterface window;

		public PanelBasisListener(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ComponentAdapter#componentShown(java.awt.event.ComponentEvent)
		 */
		@Override
		public void componentShown(ComponentEvent event) {
			Util.runInThreadWheel(window.getRootPane(), new Threadable() {

				public void enableComponents(boolean enable) {
				}

				public Object doWork(Object[] params, JLabel labelInfo) {
					labelInfo.setText("Henter grunnlag...");
					showSelectedTransportCost();
					return null;
				}

				public void doWhenFinished(Object object) {
				}

			}, null);

		}

	}

	void showSelectedTransportCost() {
		if (objectSelectionList.hasSelection()) {
			TransportCostBasis transportCostBasis = getSelectedTransportCostBasis();
			changeOrderCostList(transportCostBasis);
		}

	}

	@SuppressWarnings("unchecked")
	private void changeOrderCostList(TransportCostBasis transportCostBasis) {
		if (currentTransportCostBasis == null
				|| !currentTransportCostBasis.equals(transportCostBasis)) {
			currentTransportCostBasis = transportCostBasis;
			overviewManager.lazyLoad(transportCostBasis,
					new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
							LazyLoadEnum.NONE } });

			objectSelectionList.clearSelection();
			orderCostSelectionList.clearSelection();
			orderCostList.clear();
			orderCostList.addAll(transportCostBasis.getOrderCosts());
			Collections.sort(orderCostList, new OrderCostTransportComparator());
			BigDecimal totalCost = getTotalCost(orderCostList);
			presentationModelTotalInfo.setValue(
					TotalInfo.PROPERTY_TOTAL_NUMBER_STRING, String
							.valueOf(orderCostList.size()));
			presentationModelTotalInfo.setValue(
					TotalInfo.PROPERTY_TOTAL_COST_STRING, String
							.valueOf(totalCost));
		}
	}

	@SuppressWarnings("unchecked")
	private BigDecimal getTotalCost(ArrayListModel orderCosts) {
		BigDecimal totalCost = BigDecimal.valueOf(0);
		Iterator<OrderCost> it = orderCosts.iterator();
		while (it.hasNext()) {
			OrderCost orderCost = it.next();
			totalCost = totalCost.add(orderCost.getCostAmount());
		}
		return totalCost;
	}

	private TransportCostBasis getSelectedTransportCostBasis() {
		int index = objectSelectionList.getSelectionIndex();
		TransportCostBasis transportCostBasis = (TransportCostBasis) objectSelectionList
				.getElementAt(table.convertRowIndexToModel(index));
		return transportCostBasis;
	}

	private class TransportCostBasisPrinter implements Threadable {
		private WindowInterface owner;

		/**
		 * @param aOwner
		 */
		public TransportCostBasisPrinter(final WindowInterface aOwner) {
			owner = aOwner;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
		 */
		public void doWhenFinished(Object object) {
			if (object != null) {
				Util.showErrorDialog(owner, "Feil", object.toString());
			}
		}

		public Object doWork(Object[] params, JLabel labelInfo) {
			String errorMsg = null;
			try {
				labelInfo.setText("Genererer rapport...");
				ReportViewer reportViewer = new ReportViewer("Transpotkostnad");
				TransportCostBasis transportCostBasis = getSelectedTransportCostBasis();
				overviewManager.lazyLoad(transportCostBasis,
						new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
								LazyLoadEnum.NONE } });
				List<TransportCostBasis> transportCostBasisList = new ArrayList<TransportCostBasis>();
				transportCostBasisList.add(transportCostBasis);
				reportViewer.generateProtransReportFromBeanAndShow(
						transportCostBasisList, "Transportkostnad",
						ReportEnum.TRANSPORT_COST, null, "transportkostnad"
								+ transportCostBasis.getTransportCostBasisId()
								+ ".pdf", owner, true);

			} catch (ProTransException e) {
				e.printStackTrace();
				errorMsg = e.getMessage();
			}
			return errorMsg;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(boolean enable) {
		}

	}

	private class SetInvoiceNrAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SetInvoiceNrAction() {
			super("Sett fakturanr");
		}

		public final void actionPerformed(final ActionEvent arg0) {
			String invoiceNr = Util
					.showInputDialog(window, "", "Fakturanummer");
			if (invoiceNr != null) {
				Util.runInThreadWheel(window.getRootPane(),
						new InvoiceNrSetter(invoiceNr), null);
			}

		}
	}

	private class InvoiceNrSetter implements Threadable {
		private String invoiceNr;

		public InvoiceNrSetter(String aInvoiceNr) {
			invoiceNr = aInvoiceNr;
		}

		public void doWhenFinished(Object object) {
		}

		public Object doWork(Object[] params, JLabel labelInfo) {
			TransportCostBasis transportCostBasis = getSelectedTransportCostBasis();
			labelInfo.setText("Setter fakturanummer...");
			((TransportCostBasisManager) overviewManager).setInvoiceNr(
					transportCostBasis, invoiceNr);
			return null;
		}

		public void enableComponents(boolean enable) {
		}

	}

	public class TotalInfo extends Model {
		private static final long serialVersionUID = 1L;
		public static final String PROPERTY_TOTAL_NUMBER_STRING = "totalNumberString";
		public static final String PROPERTY_TOTAL_COST_STRING = "totalCostString";
		public static final String PROPERTY_TOTAL_NUMBER = "totalNumber";
		private Integer totalNumber;
		private BigDecimal totalCost;

		public TotalInfo() {
			totalNumber = 0;
			totalCost = BigDecimal.valueOf(0);
		}

		public String getTotalNumberString() {
			if (totalNumber != null) {
				return String.valueOf(totalNumber);
			}
			return null;
		}

		public void setTotalNumberString(String numberString) {
			String oldNumberString = getTotalNumberString();
			if (numberString != null) {
				totalNumber = Integer.valueOf(numberString);
			}
			firePropertyChange(PROPERTY_TOTAL_NUMBER_STRING, oldNumberString,
					numberString);
		}

		public Integer getTotalNumber() {
			return totalNumber;
		}

		public void setTotalNumber(Integer number) {
			Integer oldNumber = getTotalNumber();

			totalNumber = number;

			firePropertyChange(PROPERTY_TOTAL_NUMBER, oldNumber, number);
		}

		public String getTotalCostString() {
			if (totalCost != null) {
				return String.valueOf(totalCost);
			}
			return null;
		}

		public void setTotalCostString(String costString) {
			String oldCostString = getTotalNumberString();
			if (costString != null) {
				totalCost = BigDecimal.valueOf(Double.valueOf(costString));
			}
			firePropertyChange(PROPERTY_TOTAL_COST_STRING, oldCostString,
					costString);
		}
	}
}
