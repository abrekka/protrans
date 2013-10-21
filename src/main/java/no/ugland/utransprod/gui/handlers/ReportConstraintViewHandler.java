package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.jdesktop.swingx.JXTable;

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
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

/**
 * Håndterer rapportutvalg
 * 
 * @author atle.brekka
 * 
 */
public class ReportConstraintViewHandler implements Closeable {
	/**
	 * 
	 */
	private PresentationModel presentationModel;

	/**
	 * 
	 */
	private final ArrayListModel reportList = new ArrayListModel();

	/**
	 * 
	 */
	private final SelectionInList reportSelectionList = new SelectionInList(
			(ListModel) reportList);

	/**
	 * 
	 */
	final ArrayListModel reportBasisList = new ArrayListModel();

	/**
	 * 
	 */
	final SelectionInList reportBasisSelectionList = new SelectionInList(
			(ListModel) reportBasisList);

	/**
	 * 
	 */
	TransportReportSetting transportReportSetting;

	/**
	 * 
	 */
	private JXTable tableReport;

	/**
	 * 
	 */
	private JXTable tableBasis;

	/**
	 * 
	 */
	private boolean disposeOnClose = true;

	/**
	 * 
	 */
	boolean generateBasis = true;

	/**
	 * 
	 */
	private JPanel panelBasis;

	/**
	 * 
	 */
	private JPanel panelStatistic;

	/**
	 * 
	 */
	JTabbedPane tabbedPane;

	/**
	 * 
	 */
	private ReportTableModel reportTableModelBasis;

	

	/**
	 * 
	 */
	public ReportConstraintViewHandler() {
		transportReportSetting = new TransportReportSetting();

		presentationModel = new PresentationModel(transportReportSetting);
		
	}

	

	/**
	 * Lager årvelger for fra år
	 * 
	 * @return årvelger
	 */
	public JYearChooser getYearChooserFrom() {
		JYearChooser yearChooser = new JYearChooser();
		PropertyConnector.connect(yearChooser, "year", presentationModel
				.getModel(TransportReportSetting.PROPERTY_YEAR_FROM), "value");
		return yearChooser;
	}

	/**
	 * Lager årvelger for til år
	 * 
	 * @return årvelger
	 */
	public JYearChooser getYearChooserTo() {
		JYearChooser yearChooser = new JYearChooser();
		PropertyConnector.connect(yearChooser, "year", presentationModel
				.getModel(TransportReportSetting.PROPERTY_YEAR_TO), "value");
		return yearChooser;
	}

	/**
	 * Lager komboboks for fra uke
	 * 
	 * @return kombboks
	 */
	public JComboBox getComboBoxWeekFrom() {
		return new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
				presentationModel
						.getModel(TransportReportSetting.PROPERTY_WEEK_FROM)));
	}

	public JComboBox getComboBoxProductArea() {
		return Util.createComboBoxProductArea(presentationModel
								.getModel(TransportReportSetting.PROPERTY_PRODUCT_AREA),false);
	}

	/**
	 * Lager komboboks for til uke
	 * 
	 * @return komboboks
	 */
	public JComboBox getComboBoxWeekTo() {
		return new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
				presentationModel
						.getModel(TransportReportSetting.PROPERTY_WEEK_TO)));
	}

	/**
	 * Lager radioknapp for transportert
	 * 
	 * @return radioknapp
	 */
	public JRadioButton getRadioButtonTransported() {
		return BasicComponentFactory
				.createRadioButton(
						presentationModel
								.getModel(TransportReportSetting.PROPERTY_TRANSPORT_CONSTRAINT_ENUM_VALUE),
						"TRANSPORTED", "Transportert");
	}

	/**
	 * Lager radioknapp for planlagt
	 * 
	 * @return radioknapp
	 */
	public JRadioButton getRadioButtonTransportPlanned() {
		return BasicComponentFactory
				.createRadioButton(
						presentationModel
								.getModel(TransportReportSetting.PROPERTY_TRANSPORT_CONSTRAINT_ENUM_VALUE),
						"TRANSPORT_PLANNED", "Transport planlagt");
	}

	/**
	 * Lager radioknapp for alle
	 * 
	 * @return radioknapp
	 */
	public JRadioButton getRadioButtonAll() {
		return BasicComponentFactory
				.createRadioButton(
						presentationModel
								.getModel(TransportReportSetting.PROPERTY_TRANSPORT_CONSTRAINT_ENUM_VALUE),
						"ALL", "Alle");
	}

	/**
	 * Lager avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonCancel(WindowInterface window) {
		return new CancelButton(window, this, disposeOnClose);
	}

	/**
	 * Lager knapp for excel
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonExcel(WindowInterface window) {
		JButton button = new JButton(new ExcelAction(window));
		button.setIcon(IconEnum.ICON_EXCEL.getIcon());
		return button;
	}

	/**
	 * Lager knapp for rapport
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonShowReport(WindowInterface window) {
		JButton button = new JButton(new ShowReportAction(window));
		button.setName("ButtonShowReport");
		return button;
	}

	/**
	 * Lager tabell for rapport
	 * 
	 * @return tabell
	 */
	public JXTable getTableReport() {
		tableReport = new JXTable();

		tableReport.setModel(new ReportTableModel(reportSelectionList,
				new String[] { "Antall" }, false, false));
		tableReport.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableReport.setSelectionModel(new SingleListSelectionAdapter(
				reportSelectionList.getSelectionIndexHolder()));
		tableReport.setColumnControlVisible(true);
		tableReport.setSearchable(null);

		tableReport.setShowGrid(true);
		tableReport.packAll();
		return tableReport;
	}

	/**
	 * Lager tabell for grunnlag
	 * 
	 * @return tabell
	 */
	public JXTable getTableBasis() {
		tableBasis = new JXTable();

		tableBasis.setModel(new ReportTableModel(reportBasisSelectionList,
				new String[] { "Antall" }, true, false));
		tableBasis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableBasis.setSelectionModel(new SingleListSelectionAdapter(
				reportBasisSelectionList.getSelectionIndexHolder()));
		tableBasis.setColumnControlVisible(true);
		tableBasis.setSearchable(null);

		tableBasis.setShowGrid(true);
		tableBasis.packAll();
		return tableBasis;
	}

	/**
	 * Initierer tabeller
	 * 
	 */
	private void initComponents() {
		tableReport = getTableReport();
		tableBasis = getTableBasis();
	}

	/**
	 * Bygger tabpane med statistikk og grunnlag
	 * 
	 * @param window
	 * @return panel
	 */
	public JPanel buildTabbedPane(WindowInterface window) {
		initComponents();
		FormLayout layout = new FormLayout("p:grow", "p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		tabbedPane = new JTabbedPane();
		tabbedPane.add("Statistikk", buildStatisticPanel());
		tabbedPane.add("Grunnlag", buildBasisPanel(window));

		builder.add(tabbedPane, cc.xy(1, 1));

		return builder.getPanel();
	}

	/**
	 * Bygger statistikkpanel
	 * 
	 * @return panel
	 */
	private JPanel buildStatisticPanel() {
		FormLayout layout = new FormLayout("300dlu:grow", "100dlu:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.add(new JScrollPane(tableReport), cc.xy(1, 1));

		panelStatistic = builder.getPanel();
		return panelStatistic;
	}

	/**
	 * Bygger grunnlagpanel
	 * 
	 * @param window
	 * @return panel
	 */
	private JPanel buildBasisPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("300dlu:grow", "100dlu:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.add(new JScrollPane(tableBasis), cc.xy(1, 1));

		panelBasis = builder.getPanel();
		panelBasis.addComponentListener(new PanelBasisListener(window));
		return panelBasis;
	}

	/**
	 * Holde på rapportsettinger
	 * 
	 * @author atle.brekka
	 * 
	 */
	public class TransportReportSetting extends Model {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public final static String PROPERTY_YEAR_FROM = "yearFrom";

		/**
		 * 
		 */
		public final static String PROPERTY_YEAR_TO = "yearTo";

		/**
		 * 
		 */
		public final static String PROPERTY_WEEK_FROM = "weekFrom";

		/**
		 * 
		 */
		public final static String PROPERTY_WEEK_TO = "weekTo";

		/**
		 * 
		 */
		public final static String PROPERTY_TRANSPORT_CONSTRAINT_ENUM = "transportConstraintEnum";

		/**
		 * 
		 */
		public final static String PROPERTY_TRANSPORT_CONSTRAINT_ENUM_VALUE = "transportConstraintEnumValue";

		public final static String PROPERTY_PRODUCT_AREA = "productArea";

		/**
		 * 
		 */
		private Integer yearFrom;

		/**
		 * 
		 */
		private Integer yearTo;

		/**
		 * 
		 */
		private Integer weekFrom;

		/**
		 * 
		 */
		private Integer weekTo;

		/**
		 * 
		 */
		private TransportConstraintEnum transportConstraintEnum;

		private ProductArea productArea;

		/**
		 * 
		 */
		public TransportReportSetting() {
			transportConstraintEnum = TransportConstraintEnum.TRANSPORTED;
			weekFrom = Util.getCurrentWeek();
			weekTo = Util.getCurrentWeek();
			yearFrom = Util.getCurrentYear();
			yearTo = Util.getCurrentYear();
		}

		/**
		 * @return enumverdi
		 */
		public String getTransportConstraintEnumValue() {
			if (transportConstraintEnum != null) {
				return transportConstraintEnum.getValue();
			}
			return "";
		}

		/**
		 * @param value
		 */
		public void setTransportConstraintEnumValue(String value) {
			String oldValue = getTransportConstraintEnumValue();
			transportConstraintEnum = TransportConstraintEnum.getEnum(value);
			firePropertyChange(PROPERTY_TRANSPORT_CONSTRAINT_ENUM_VALUE,
					oldValue, value);
		}

		/**
		 * @return rapportutvalg
		 */
		public TransportConstraintEnum getTransportConstraintEnum() {
			return transportConstraintEnum;
		}

		/**
		 * @param transportConstraintEnum
		 */
		public void setTransportConstraintEnum(
				TransportConstraintEnum transportConstraintEnum) {
			TransportConstraintEnum oldEnum = getTransportConstraintEnum();
			this.transportConstraintEnum = transportConstraintEnum;
			firePropertyChange(PROPERTY_TRANSPORT_CONSTRAINT_ENUM, oldEnum,
					transportConstraintEnum);
		}

		/**
		 * @return fra uke
		 */
		public Integer getWeekFrom() {
			return weekFrom;
		}

		/**
		 * @param weekFrom
		 */
		public void setWeekFrom(Integer weekFrom) {
			Integer oldWeek = getWeekFrom();
			this.weekFrom = weekFrom;
			firePropertyChange(PROPERTY_WEEK_FROM, oldWeek, weekFrom);
		}

		/**
		 * @return til uke
		 */
		public Integer getWeekTo() {
			return weekTo;
		}

		/**
		 * @param weekTo
		 */
		public void setWeekTo(Integer weekTo) {
			Integer oldWeek = getWeekTo();
			this.weekTo = weekTo;
			firePropertyChange(PROPERTY_WEEK_TO, oldWeek, weekTo);
		}

		/**
		 * @return fra år
		 */
		public Integer getYearFrom() {
			return yearFrom;
		}

		/**
		 * @param yearFrom
		 */
		public void setYearFrom(Integer yearFrom) {
			Integer oldYear = getYearFrom();
			this.yearFrom = yearFrom;
			firePropertyChange(PROPERTY_YEAR_FROM, oldYear, yearFrom);
		}

		/**
		 * @return til år
		 */
		public Integer getYearTo() {
			return yearTo;
		}

		/**
		 * @param yearTo
		 */
		public void setYearTo(Integer yearTo) {
			Integer oldYear = getYearTo();
			this.yearTo = yearTo;
			firePropertyChange(PROPERTY_YEAR_TO, oldYear, yearTo);
		}

		/**
		 * @return fra år og uke som streng
		 */
		public String getFromString() {
			return String.format("%1$d%2$02d", yearFrom, weekFrom + 10);
		}

		/**
		 * @return til år og uke som streng
		 */
		public String getToString() {
			return String.format("%1$d%2$02d", yearTo, weekTo + 10);
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.format("%1$d%2$02d", yearFrom, weekFrom) + " - "
					+ String.format("%1$d%2$02d", yearTo, weekTo) + " "
					+ transportConstraintEnum.toString();
		}

		public ProductArea getProductArea() {
			return productArea;
		}

		public void setProductArea(ProductArea productArea) {
			ProductArea oldArea = getProductArea();
			this.productArea = productArea;
			firePropertyChange(PROPERTY_PRODUCT_AREA, oldArea, productArea);
		}

		public boolean isValid() {
			if (productArea != null) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Rapportutvalgenum
	 * 
	 * @author atle.brekka
	 * 
	 */
	public enum TransportConstraintEnum {
		/**
		 * 
		 */
		TRANSPORTED("TRANSPORTED"),
		/**
		 * 
		 */
		TRANSPORT_PLANNED("TRANSPORT_PLANNED"),
		/**
		 * 
		 */
		ALL("ALL");

		/**
		 * 
		 */
		private String value;

		/**
		 * @param aValue
		 */
		private TransportConstraintEnum(String aValue) {
			value = aValue;
		}

		/**
		 * @return verdi
		 */
		String getValue() {
			return value;
		}

		/**
		 * Finner enum basert på strengverdi
		 * 
		 * @param aString
		 * @return enum
		 */
		public static TransportConstraintEnum getEnum(String aString) {
			if (aString.equalsIgnoreCase("TRANSPORTED")) {
				return TRANSPORTED;
			} else if (aString.equalsIgnoreCase("TRANSPORT_PLANNED")) {
				return TRANSPORT_PLANNED;
			} else if (aString.equalsIgnoreCase("ALL")) {
				return ALL;
			}
			return null;

		}
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean canClose(String actionString, WindowInterface window) {
		return true;
	}

	/**
	 * Eksport til excel
	 * 
	 * @throws ProTransException
	 */
	void exportToExcel() throws ProTransException {
		String fileName = "transport_" + Util.getCurrentDateAsDateTimeString()
				+ ".xls";
		String excelDirectory = ApplicationParamUtil
				.findParamByName("excel_path");

		JXTable excelTable = null;
		if (panelStatistic.isShowing()) {
			excelTable = tableReport;
		} else {
			excelTable = new JXTable(new ReportTableModel(
					reportBasisSelectionList, reportTableModelBasis
							.getColumnHeadings(), true, true));
		}

		ExcelUtil.showDataInExcel(excelDirectory, fileName, null,
				"Transport - " + transportReportSetting.toString(), excelTable,
				null, null, 16, false);
	}

	/**
	 * Eksporter til excel
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class ExcelAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ExcelAction(WindowInterface aWindow) {
			super("Excel");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			try {
				exportToExcel();
				Util
						.showMsgDialog(window.getComponent(), "Excel generert",
								"Dersom excelfil ikke kom opp ligger den i katalog definert for excel");
			} catch (ProTransException e) {
				e.printStackTrace();
				Util.showErrorDialog(window, "Feil", e.getMessage());
			}
			Util.setDefaultCursor(window.getComponent());

		}
	}

	/**
	 * Vis rapport
	 * 
	 * @param forBasis
	 */
	void showReport(boolean forBasis) {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");

		if (forBasis) {
			createReportForBasis(orderManager);
		} else {

			createReportForSum(orderManager);
		}
	}

	private void createReportForSum(OrderManager orderManager) {
		List<ReportDataTransport> reportDataTransportList;
		reportDataTransportList = orderManager.countOrderAndCosts(
				transportReportSetting.getFromString(), transportReportSetting
						.getToString(), transportReportSetting
						.getTransportConstraintEnum(),transportReportSetting.getProductArea());

		if (reportDataTransportList != null) {
			setupReportForSum(reportDataTransportList);
		}
	}

	private void setupReportForSum(
			List<ReportDataTransport> reportDataTransportList) {
		reportList.clear();
		reportList.addAll(reportDataTransportList);
		String[] columnHeadings = ReportTableModel.COLUMN_HEADINGS_SUM;
		if (reportList.size() != 0) {
			List<String> headingList = generateHeadings(
					reportDataTransportList, columnHeadings,3);

			columnHeadings = headingList.toArray(columnHeadings);

		}
		tableReport.setModel(new ReportTableModel(reportSelectionList,
				columnHeadings, false, false));
		setColumnWidth(false, columnHeadings.length);
	}

	private void createReportForBasis(OrderManager orderManager) {
		List<ReportDataTransport> reportDataTransportList;
		reportDataTransportList = orderManager.getOrdersAndCosts(
				transportReportSetting.getFromString(), transportReportSetting
						.getToString(), transportReportSetting
						.getTransportConstraintEnum(),transportReportSetting.getProductArea());

		if (reportDataTransportList != null) {
			setupReportForBasis(reportDataTransportList);
		}
	}

	private void setupReportForBasis(
			List<ReportDataTransport> reportDataTransportList) {
		reportBasisList.addAll(reportDataTransportList);
		String[] columnHeadings = ReportTableModel.COLUMN_HEADINGS_BASIS;
		if (reportBasisList.size() != 0) {
			List<String> headingList = generateHeadings(
					reportDataTransportList, columnHeadings,9);

			columnHeadings = headingList.toArray(columnHeadings);

		}
		reportTableModelBasis = new ReportTableModel(reportBasisSelectionList,
				columnHeadings, true, false);
		tableBasis.setModel(reportTableModelBasis);
		setColumnWidth(true, columnHeadings.length);
	}

	private List<String> generateHeadings(
			List<ReportDataTransport> reportDataTransportList,
			String[] columnHeadings,int startExtraHeadings) {
		List<String> headingList = new ArrayList<String>();
		headingList.addAll(Arrays.asList(columnHeadings));
		headingList.addAll(startExtraHeadings, reportDataTransportList.get(0).getCostHeadings());
		return headingList;
	}

	/**
	 * Setter kolonnebredde
	 * 
	 * @param forBasis
	 * @param numberOfCols
	 */
	private void setColumnWidth(boolean forBasis, int numberOfCols) {
		{
			if (forBasis) {
				// Etterlevering
				tableBasis.getColumnExt(0).setPreferredWidth(80);
				// Ordrenr
				tableBasis.getColumnExt(1).setPreferredWidth(60);
				// Kundenr
				tableBasis.getColumnExt(2).setPreferredWidth(60);
				// Navn
				tableBasis.getColumnExt(3).setPreferredWidth(120);
				// Adresse
				tableBasis.getColumnExt(4).setPreferredWidth(150);
				// Postnr
				tableBasis.getColumnExt(5).setPreferredWidth(60);
				// Transport
				tableBasis.getColumnExt(6).setPreferredWidth(100);
				// År
				tableBasis.getColumnExt(7).setPreferredWidth(40);
				// Uke
				tableBasis.getColumnExt(8).setPreferredWidth(40);

				for (int i = 9; i < numberOfCols; i++) {
					// Kostnad
					tableBasis.getColumnExt(i).setPreferredWidth(110);
				}

			} else {
				// År
				tableReport.getColumnExt(0).setPreferredWidth(40);
				// Uke
				tableReport.getColumnExt(1).setPreferredWidth(40);
				// Antall
				tableReport.getColumnExt(2).setPreferredWidth(40);
				for (int i = 3; i < numberOfCols; i++) {
					// Kostnad
					tableReport.getColumnExt(i).setPreferredWidth(110);
				}

			}
		}

	}

	/**
	 * Vis rapport
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class ShowReportAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ShowReportAction(WindowInterface aWindow) {
			super("Vis statistikk");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if (transportReportSetting.isValid()) {
				generateBasis = true;
				tabbedPane.setSelectedIndex(0);
				Util.runInThreadWheel(window.getRootPane(),
						new TransportReport(false), null);
			} else {
				Util.showErrorDialog(window, "Sett utvalg", "Sett alle utvalg");
			}
		}
	}

	/**
	 * Tabellmodell for rapport
	 * 
	 * @author atle.brekka
	 * 
	 */
	private static final class ReportTableModel extends AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public static String[] COLUMN_HEADINGS_SUM = { "År", "Uke", "Antall" };

		/**
		 * 
		 */
		public static String[] COLUMN_HEADINGS_BASIS = { "Etterlevering",
				"Ordrenr", "Kundenr", "Navn", "Adresse", "Postnr", "Transport",
				"År", "Uke" };

		/**
		 * 
		 */
		private String[] columnHeadings;

		/**
		 * 
		 */
		private boolean isForBasis = false;

		/**
		 * 
		 */
		private boolean isForExcel = false;

		/**
		 * @param listModel
		 * @param columnHeadings
		 * @param forBasis
		 * @param forExcel
		 */
		ReportTableModel(ListModel listModel, String[] columnHeadings,
				boolean forBasis, boolean forExcel) {
			super(listModel, columnHeadings);
			isForBasis = forBasis;
			isForExcel = forExcel;
			this.columnHeadings = columnHeadings!=null?columnHeadings.clone():null;

		}

		/**
		 * @return kolonneoverskrifter
		 */
		public String[] getColumnHeadings() {
			return columnHeadings;
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			ReportDataTransport reportDataTransport = (ReportDataTransport) getRow(rowIndex);
			if (isForBasis) {
				return getValueForBasis(reportDataTransport, columnIndex);
			}
			return getValueForSum(reportDataTransport, columnIndex);
		}

		/**
		 * Henter verdi for statistikkpanel
		 * 
		 * @param reportDataTransport
		 * @param columnIndex
		 * @return verdi
		 */
		private Object getValueForSum(ReportDataTransport reportDataTransport,
				int columnIndex) {
			switch (columnIndex) {
			case 0:
				return reportDataTransport.getYear();
			case 1:
				return reportDataTransport.getWeek();
			case 2:
				return reportDataTransport.getNumberOf();

			default:
				return reportDataTransport.getCosts().get(
						columnHeadings[columnIndex]);
			}

		}

		/**
		 * Henter verdi for grunnlagpanel
		 * 
		 * @param reportDataTransport
		 * @param columnIndex
		 * @return verdi
		 */
		private Object getValueForBasis(
				ReportDataTransport reportDataTransport, int columnIndex) {
			switch (columnIndex) {
			case 0:
				if (isForExcel) {
					if (reportDataTransport.isPostShipment()) {
						return "X";
					}
					return "";
				}
				return reportDataTransport.isPostShipment();
			case 1:
				return reportDataTransport.getOrderNr();
			case 2:
				return reportDataTransport.getCustomerNr();
			case 3:
				return reportDataTransport.getCustomerName();
			case 4:
				return reportDataTransport.getDeliveryAddress();
			case 5:
				return reportDataTransport.getPostalCode();
			case 6:
				return reportDataTransport.getTransportName();
			case 7:
				return reportDataTransport.getYear();
			case 8:
				return reportDataTransport.getWeek();

			default:
				try {
					return reportDataTransport.getCosts().get(
							columnHeadings[columnIndex]);
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					return null;
				}
			}

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (isForBasis) {
				return getColumnClassForBasis(columnIndex);
			}
			return getColumnClassForSum(columnIndex);
		}

		/**
		 * Henter kolonneklasse for statistikk
		 * 
		 * @param columnIndex
		 * @return kolonneklasse
		 */
		private Class<?> getColumnClassForSum(int columnIndex) {
			switch (columnIndex) {
			case 0:
			case 1:
			case 2:
				return Integer.class;
			default:
				return BigDecimal.class;
			}
		}

		/**
		 * Henter kolonneklasse for grunnlag
		 * 
		 * @param columnIndex
		 * @return kolonneklasse
		 */
		private Class<?> getColumnClassForBasis(int columnIndex) {
			switch (columnIndex) {
			case 0:
				if (isForExcel) {
					return String.class;
				}
				return Boolean.class;
			case 1:
			case 3:
			case 4:
			case 5:
			case 6:
				return String.class;
			case 2:
			case 7:
			case 8:
				return Integer.class;

			default:
				return BigDecimal.class;
			}
		}

	}

	/**
	 * @return vindustittel
	 */
	public String getWindowTitle() {
		return "Transportstatistikk";
	}

	/**
	 * @return vindusstørrelse
	 */
	public Dimension getWindowSize() {
		return new Dimension(580, 400);
	}

	/**
	 * @return true dersom dispose skal kjøres
	 */
	public boolean getDisposeOnClose() {
		return disposeOnClose;
	}

	/**
	 * Håndterer grunnlagpanel
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class PanelBasisListener extends ComponentAdapter {
		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public PanelBasisListener(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ComponentAdapter#componentShown(java.awt.event.ComponentEvent)
		 */
		@Override
		public void componentShown(ComponentEvent event) {
			if (generateBasis) {
				generateBasis = false;
				reportBasisSelectionList.clearSelection();
				reportBasisList.clear();
				Util.runInThreadWheel(window.getRootPane(),
						new TransportReport(true), null);
			}
		}

	}

	/**
	 * Håndterer visning av rapport
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class TransportReport implements Threadable {
		/**
		 * 
		 */
		private boolean forBasis = false;

		/**
		 * @param basis
		 */
		public TransportReport(boolean basis) {
			forBasis = basis;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
		 */
		public void doWhenFinished(Object object) {
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
		 *      javax.swing.JLabel)
		 */
		public Object doWork(Object[] params, JLabel labelInfo) {
			labelInfo.setText("Genererer rapport...");
			showReport(forBasis);
			return null;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(boolean enable) {
		}

	}
}
