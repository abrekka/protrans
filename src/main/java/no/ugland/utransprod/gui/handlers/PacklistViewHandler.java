package no.ugland.utransprod.gui.handlers;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.list.SelectionInList;

import net.sf.jasperreports.engine.JRException;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.TrossReadyView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.edit.EditPacklistView;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.gui.model.Delelisteinfo;
import no.ugland.utransprod.gui.model.ExternalOrderModel;
import no.ugland.utransprod.gui.model.HorizontalAlignmentCellRenderer;
import no.ugland.utransprod.gui.model.Ordreinfo;
import no.ugland.utransprod.gui.model.PacklistApplyList;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.gui.model.ProductionReportData;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.TextPaneRendererOrder;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.Collicreator;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PdfUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.excel.ExcelUtil;
import no.ugland.utransprod.util.report.ReportViewer;

/**
 * H�ndterer setting av pakkliste klar
 * 
 * @author atle.brekka
 */
public class PacklistViewHandler extends AbstractProductionPackageViewHandlerShort<PacklistV> {
	private OrderViewHandler orderViewHandler;

	private PresentationModel presentationModelBudget;

	private PresentationModel presentationModelCount;

	private EmptySelectionListener emptySelectionListener;

	private CostType costTypeTross;
	private CostUnit costUnitTross;
	private JTextField textFieldWeekFrom;
	private JTextField textFieldWeekTo;
	private StatusCheckerInterface<Transportable> takstolChecker;
	private JMenuItem menuItemUpdateStatus;
	private Collicreator collicreator;

	@Inject
	public PacklistViewHandler(Login login, ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory, OrderViewHandlerFactory orderViewHandlerFactory,
			PacklistApplyList productionInterface, @Named("kostnadTypeTakstoler") CostType aCostTypeTross,
			@Named("kostnadEnhetTakstoler") CostUnit aCostUnitTross,Collicreator aCollicreator) {
		super(login, aManagerRepository, deviationViewHandlerFactory, productionInterface, "Pakklister",
				TableEnum.TABLEPACKLIST);
		costTypeTross = aCostTypeTross;
		costUnitTross = aCostUnitTross;
		orderViewHandler = orderViewHandlerFactory.create(true);
		presentationModelBudget = new PresentationModel(
				new ProductionBudgetModel(new Budget(null, null, null, BigDecimal.valueOf(0), null, null)));
		presentationModelCount = new PresentationModel(new CountModel(Integer.valueOf(0), Integer.valueOf(0)));
		takstolChecker = Util.getTakstolChecker(managerRepository);

		initBudgetAndCount();
		emptySelectionListener = new EmptySelectionListener(objectSelectionList);
		objectSelectionList.addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				emptySelectionListener);
		menuItemUpdateStatus = new JMenuItem("Oppdater status");

		popupMenu.add(menuItemUpdateStatus);
		collicreator=aCollicreator;
	}

	/**
	 * Initierer budsjett og antall pakklister
	 */
	private void initBudgetAndCount() {

		YearWeek yearWeekPlussOne = Util.addWeek(new YearWeek(Util.getCurrentYear(), Util.getCurrentWeek()), 1);
		// ProductAreaGroup group = (ProductAreaGroup)
		// productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
		Budget productionBudget = managerRepository.getBudgetManager().findByYearAndWeekPrProductAreaGroup(
				yearWeekPlussOne.getYear(), yearWeekPlussOne.getWeek(), BudgetType.PRODUCTION);

		if (productionBudget == null) {
			productionBudget = new Budget(null, null, null, BigDecimal.valueOf(0), null, null);
		}

		presentationModelBudget.setValue(ProductionBudgetModel.PROPERTY_BUDGET_VALUE,
				productionBudget.getBudgetValueString());

		Date fromDateWeek = Util.getFirstDateInWeek(Util.getCurrentYear(), Util.getCurrentWeek());
		Date toDateWeek = Util.getLastDateInWeek(Util.getCurrentYear(), Util.getCurrentWeek());
		Integer weekCount = managerRepository.getOrderManager()
				.getPacklistCountForWeekByProductAreaGroupName(fromDateWeek, toDateWeek);

		Date fromDateYear = Util.getFirstDateInYear(Util.getCurrentYear());
		Date toDateYear = Util.getLastDateInWeek(Util.getCurrentYear(), Util.getCurrentWeek());
		Integer yearCount = managerRepository.getOrderManager()
				.getPacklistCountForWeekByProductAreaGroupName(fromDateYear, toDateYear);

		if (weekCount == null) {
			weekCount = Integer.valueOf(0);
		}
		if (yearCount == null) {
			yearCount = Integer.valueOf(0);
		}
		presentationModelCount.setValue(CountModel.PROPERTY_COUNT_WEEK_STRING, String.valueOf(weekCount));
		presentationModelCount.setValue(CountModel.PROPERTY_COUNT_YEAR_STRING, String.valueOf(yearCount));
	}

	/**
	 * Lager knapp for � editere ordre
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonEditOrder(WindowInterface window) {
		JButton buttonEditOrder = new JButton(new EditOrderAction(window));
		buttonEditOrder.setEnabled(false);
		emptySelectionListener.addButton(buttonEditOrder);
		return buttonEditOrder;
	}

	/**
	 * Lager knapp for bestilling av artikler
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonExternalOrder(WindowInterface window) {
		JButton buttonExternalOrder = new JButton(new ExternalOrderAction(window));
		buttonExternalOrder.setEnabled(false);
		emptySelectionListener.addButton(buttonExternalOrder);
		return buttonExternalOrder;
	}

	/**
	 * Lager label for budsjett
	 * 
	 * @return label
	 */
	public JLabel getLabelBudget() {
		return BasicComponentFactory
				.createLabel(presentationModelBudget.getModel(ProductionBudgetModel.PROPERTY_BUDGET_VALUE));
	}

	/**
	 * Lager label for antall pakklsiter
	 * 
	 * @return label
	 */
	public JLabel getLabelCountWeek() {
		return BasicComponentFactory
				.createLabel(presentationModelCount.getModel(CountModel.PROPERTY_COUNT_WEEK_STRING));
	}

	/**
	 * Lager label for akkumulert antall pakklister
	 * 
	 * @return label
	 */
	public JLabel getLabelCountYear() {
		return BasicComponentFactory
				.createLabel(presentationModelCount.getModel(CountModel.PROPERTY_COUNT_YEAR_STRING));
	}

	/**
	 * @param object
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandlerShort#checkLazyLoad(no.ugland.utransprod.gui.model.Applyable)
	 */
	@Override
	protected void checkLazyLoad(PacklistV object) {
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyText()
	 */
	@Override
	protected String getApplyText() {
		return "Sett pakkliste klar";
	}

	/**
	 * @param packlistV
	 * @return true dersom enabled
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getButtonApplyEnabled(no.ugland.utransprod.gui.model.Applyable)
	 */
	@Override
	protected boolean getButtonApplyEnabled(PacklistV packlistV) {
		if (packlistV.getPacklistReady() == null) {
			return true;
		}
		return false;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getCheckBoxText()
	 */
	@Override
	protected String getCheckBoxText() {
		return "Vis pakkliste klar";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getUnapplyText()
	 */
	@Override
	protected String getUnapplyText() {
		return "Sett pakkliste ikke klar";
	}

	/**
	 * @param packlistV
	 * @param applied
	 * @param window
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#setApplied(no.ugland.utransprod.gui.model.Applyable,
	 *      boolean, no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void setApplied(PacklistV packlistV, boolean applied, WindowInterface window) {
		Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
		if (applied) {

			EditPacklistView editPacklistView = new EditPacklistView(login, true, order.getPacklistDuration(),
					order.getPacklistDoneBy());

			JDialog dialog = Util.getDialog(window, "Pakkliste klar", true);
			WindowInterface window1 = new JDialogAdapter(dialog);
			window1.add(editPacklistView.buildPanel(window1));
			window1.pack();
			Util.locateOnScreenCenter(window1);
			window1.setVisible(true);

			if (!editPacklistView.isCanceled()) {
				order.setPacklistReady(editPacklistView.getPacklistDate());
				order.setPacklistDuration(editPacklistView.getPacklistDuration());
				order.setPacklistDoneBy(editPacklistView.getDoneBy());
				order.setProductionBasis(Integer.valueOf(100));
				managerRepository.getOrderManager().saveOrder(order);
				collicreator.opprettKollier(order,"Pakkliste");
			}

			// Date packlistDate = Util.getDate(window);

		} else {
			order.setPacklistDoneBy(null);
			order.setPacklistReady(null);
			order.setPacklistDuration(null);
			order.setProductionBasis(null);
		}
		try {
			managerRepository.getOrderManager().saveOrder(order);
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
		managerRepository.getPacklistVManager().refresh(packlistV);
		initBudgetAndCount();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected TableModel getTableModel(WindowInterface window) {
		table.addMouseListener(new DoubleClickHandler(window));

		return new PacklistTableModel(getObjectSelectionList(), window, false);
	}

	@Override
	void initColumnWidthExt() {
		PacklistColumn[] columns = PacklistColumn.values();
		for (PacklistColumn column : columns) {
			column.setPrefferedWidth(table);
		}

	}

	final void initColumnWidth() {
		List<TableColumn> columns = table.getColumns();
		for (TableColumn col : columns) {

			PacklistColumn packColumn = PacklistColumn.hentKolonne((String) col.getHeaderValue());
			table.getColumnExt(packColumn.getColumnName()).setPreferredWidth(packColumn.getColumnWidth());
		}
		table.getColumnModel().getColumn(PacklistColumn.TAKSTOLER.ordinal())
				.setCellRenderer(new HorizontalAlignmentCellRenderer(SwingConstants.LEFT));
		table.getColumnModel().getColumn(PacklistColumn.GULVSPON.ordinal())
				.setCellRenderer(new HorizontalAlignmentCellRenderer(SwingConstants.CENTER));
		table.getColumnModel().getColumn(PacklistColumn.PROD_UKE.ordinal())
				.setCellRenderer(new HorizontalAlignmentCellRenderer(SwingConstants.CENTER));
		table.getColumnModel().getColumn(PacklistColumn.MONTERING.ordinal())
				.setCellRenderer(new HorizontalAlignmentCellRenderer(SwingConstants.CENTER));
	}

	/**
	 * Tabellmodell for tabell med order til fakturering
	 * 
	 * @author atle.brekka
	 */
	public final class PacklistTableModel extends AbstractTableAdapter {

		private static final long serialVersionUID = 1L;
		private StatusCheckerInterface<Transportable> takstolChecker;
		private WindowInterface window;

		public PacklistTableModel(ListModel listModel, WindowInterface aWindow, boolean excel) {
			super(listModel, PacklistColumn.getColumnNames(excel));
			window = aWindow;
			takstolChecker = Util.getTakstolChecker(managerRepository);
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			PacklistV packlistV = (PacklistV) getRow(rowIndex);

			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalSeparatorAlwaysShown(false);
			decimalFormat.setParseIntegerOnly(true);
			Map<String, String> statusMap = Util.createStatusMap(packlistV.getOrderStatus());

			return PacklistColumn.hentKolonne(getColumnName(columnIndex)).getValue(packlistV, takstolChecker, statusMap,
					window, managerRepository, applyListInterface);
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return PacklistColumn.hentKolonne(getColumnName(columnIndex)).getColumnClass();
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyColumn()
	 */
	@Override
	protected Integer getApplyColumn() {
		return PacklistColumn.PAKKLISTE_KLAR.ordinal();
		// return 4;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(1200, 800);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "210dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#searchOrder(java.lang.String,
	 *      java.lang.String, no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void searchOrder(String orderNr, String customerNr, WindowInterface window) {
		try {
			List<PacklistV> list = applyListInterface.doSearch(orderNr, customerNr,
					(ProductAreaGroup) productAreaGroupModel
							.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
			PacklistV packlistV = getSearchObject(window, list);
			if (packlistV != null) {
				int selectedIndex = objectList.indexOf(packlistV);
				table.getSelectionModel().setSelectionInterval(table.convertRowIndexToView(selectedIndex),
						table.convertRowIndexToView(selectedIndex));

				table.scrollRowToVisible(table.getSelectedRow());

			}
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getOrderCellRenderer()
	 */
	@Override
	protected TableCellRenderer getOrderCellRenderer() {
		return new TextPaneRendererOrder();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getOrderInfoCell()
	 */
	@Override
	protected int getOrderInfoCell() {
		return 2;
	}

	/**
	 * �pne ordre
	 * 
	 * @param window
	 */
	void doEditAction(WindowInterface window) {
		OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		Util.setWaitCursor(window.getComponent());
		PacklistV packlistV = getSelectedObject();
		Order order = orderManager.findByOrderNr(packlistV.getOrderNr());
		orderViewHandler.openOrderView(order, false, window, false);
		Util.setDefaultCursor(window.getComponent());

	}

	/**
	 * H�ndterer dobbeltklikk p� ordre
	 * 
	 * @author atle.brekka
	 */
	final class DoubleClickHandler extends MouseAdapter {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public DoubleClickHandler(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
				if (objectSelectionList.getSelection() != null) {
					doEditAction(window);
				}
			} else if (SwingUtilities.isRightMouseButton(e)) {
				popupMenu.show((JXTable) e.getSource(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * editer ordre
	 * 
	 * @author atle.brekka
	 */
	private class EditOrderAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public EditOrderAction(WindowInterface aWindow) {
			super("Editer...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			doEditAction(window);

		}
	}

	/**
	 * Bestille srtikler
	 * 
	 * @author atle.brekka
	 */
	private class ExternalOrderAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ExternalOrderAction(WindowInterface aWindow) {
			super("Bestillinger...");
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			PacklistV packlistV = getSelectedObject();
			if (packlistV != null) {

				Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
				managerRepository.getOrderManager().lazyLoadTree(order);
				ExternalOrderViewHandler externalOrderViewHandler = new ExternalOrderViewHandler(login,
						managerRepository, order);
				OverviewView<ExternalOrder, ExternalOrderModel> externalOverviewView = new OverviewView<ExternalOrder, ExternalOrderModel>(
						externalOrderViewHandler, false);

				JDialog dialog = Util.getDialog(window, "Bestillinger", true);

				WindowInterface windowDialog = new JDialogAdapter(dialog);
				windowDialog.add(externalOverviewView.buildPanel(windowDialog));
				windowDialog.setSize(externalOrderViewHandler.getWindowSize());
				Util.locateOnScreenCenter(windowDialog);
				dialog.setVisible(true);
				Util.setDefaultCursor(window.getComponent());
			}

		}
	}

	public enum PacklistColumn {
		PROD_UKE("Prod.uke", 70, true) {
			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				return packlistV.getProductionWeek();
			}

			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}
		},
		ORDRE_NR("Ordre nr", 50, true) {
			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				return packlistV.getOrderNr();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		ORDRE("Ordre", 200, true) {
			@Override
			public Class<?> getColumnClass() {
				return PacklistV.class;
			}

			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				return packlistV;
			}

		},
		TRANSPORT("Transport", 100, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				return packlistV.getTransportDetails();
			}

		},
		PAKKLISTE_KLAR("Pakkliste klar", 100, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				if (packlistV.getPacklistReady() != null) {
					return Util.SHORT_DATE_FORMAT.format(packlistV.getPacklistReady());
				}
				return "---";
			}

		},
		GULVSPON("Gulvspon", 70, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				if (packlistV.getHasGulvspon() != null && packlistV.getHasGulvspon() == 1) {
					return "V " + Util.nullIntegerToString(packlistV.getNumberOfGulvspon());
				}
				return null;
			}

		},
		TAKSTOLER("Takstoler", 120, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				return getStatus(takstolChecker, statusMap, packlistV, window, managerRepository, applyListInterface);
			}

		},
		TIDSBRUK("Tidsbruk", 70, true) {
			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				return packlistV.getPacklistDuration();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}
		},
		GJORT_AV("Gjort av", 120, true) {
			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				return packlistV.getPacklistDoneBy();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		MONTERING("Montering", 100, true) {
			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				String returnString = "";
				if (packlistV.getDoAssembly() != null && packlistV.getDoAssembly() == 1) {
					returnString = "M" + (packlistV.getAssemblyWeek() == null ? "" : packlistV.getAssemblyWeek());
				}
				return returnString;
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},

		// TAKSTOL_PROSJEKTERING("Takstol prosjektering", 120, false) {
		// @Override
		// public Class<?> getColumnClass() {
		// return String.class;
		// }
		//
		// @Override
		// public Object getValue(PacklistV packlistV,
		// StatusCheckerInterface<Transportable> takstolChecker, Map<String,
		// String> statusMap,
		// WindowInterface window, ManagerRepository managerRepository,
		// ApplyListInterface<PacklistV> applyListInterface) {
		// if (packlistV.getTrossReady() != null) {
		// return Util.SHORT_DATE_FORMAT.format(packlistV.getTrossReady());
		//
		// }
		// return "---";
		// }
		//
		// },
		// TEGNER("Tegner", 120, false) {
		// @Override
		// public Class<?> getColumnClass() {
		// return String.class;
		// }
		//
		// @Override
		// public Object getValue(PacklistV packlistV,
		// StatusCheckerInterface<Transportable> takstolChecker, Map<String,
		// String> statusMap,
		// WindowInterface window, ManagerRepository managerRepository,
		// ApplyListInterface<PacklistV> applyListInterface) {
		// return packlistV.getTrossDrawer();
		// }
		//
		// },
		// PRODUKSJONSGRUNNLAG("Produksjonsgrunnlag", 120, false) {
		// @Override
		// public Class<?> getColumnClass() {
		// return Integer.class;
		// }
		//
		// @Override
		// public Object getValue(PacklistV packlistV,
		// StatusCheckerInterface<Transportable> takstolChecker, Map<String,
		// String> statusMap,
		// WindowInterface window, ManagerRepository managerRepository,
		// ApplyListInterface<PacklistV> applyListInterface) {
		// return packlistV.getProductionBasis();
		// }
		// },

		PRODUKTOMR�DE("Produktomr�de", 70, false) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
					Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
					ApplyListInterface<PacklistV> applyListInterface) {
				if (packlistV.getProductAreaGroupName() != null) {
					return packlistV.getProductAreaGroupName();
				}
				return "";
			}

		};
		private String columnName;
		private int columnWidth;
		private boolean skalTilExcel;

		private PacklistColumn(String aColumnName, int aColumnWidth, boolean skalTilExcel) {
			columnName = aColumnName;
			columnWidth = aColumnWidth;
			this.skalTilExcel = skalTilExcel;
		}

		public static PacklistColumn hentKolonne(String kolonneoverskrift) {
			return PacklistColumn
					.valueOf(StringUtils.replace(StringUtils.upperCase(String.valueOf(kolonneoverskrift)), " ", "_")
							.replaceAll("\\.", "_"));
		}

		public boolean skalTilExcel() {
			return skalTilExcel;
		}

		public abstract Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker,
				Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
				ApplyListInterface<PacklistV> applyListInterface);

		public abstract Class<?> getColumnClass();

		public void setPrefferedWidth(JXTable table) {
			table.getColumnExt(getColumnName()).setPreferredWidth(getColumnWidth());
		}

		public String getColumnName() {
			return columnName;
		}

		public int getColumnWidth() {
			return columnWidth;
		}

		public static String[] getColumnNames(boolean excel) {
			List<String> columnNameList = new ArrayList<String>();
			for (PacklistColumn column : PacklistColumn.values()) {
				if (excel) {
					if (column.skalTilExcel) {
						columnNameList.add(column.getColumnName());
					}
				} else {
					columnNameList.add(column.getColumnName());
				}
			}
			// PacklistColumn[] columns = PacklistColumn.values();
			//
			// List<String> columnNameList = new ArrayList<String>();
			// for (int i = 0; i < columns.length; i++) {
			// columnNameList.add(columns[i].getColumnName());
			// }
			String[] columnNames = new String[columnNameList.size()];
			return columnNameList.toArray(columnNames);
		}

		String getStatus(StatusCheckerInterface<Transportable> checker, Map<String, String> statusMap,
				PacklistV packlistV, WindowInterface window, ManagerRepository managerRepository,
				ApplyListInterface<PacklistV> applyListInterface) {
			String status = statusMap.get(checker.getArticleName());

			return status == null ? "MANGLER" : status;
			// if (status != null) {
			// return status;
			// }

			// Order order =
			// managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
			// if (order != null) {
			// managerRepository.getOrderManager().lazyLoadTree(order);
			// status = checker.getArticleStatus(order);
			// statusMap.put(checker.getArticleName(), status);
			// order.setStatus(Util.statusMapToString(statusMap));
			// try {
			// managerRepository.getOrderManager().saveOrder(order);
			// } catch (ProTransException e) {
			// Util.showErrorDialog(window, "Feil", e.getMessage());
			// e.printStackTrace();
			// }
			// applyListInterface.refresh(packlistV);
			//
			// }
			// return status;
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#hasWriteAccess()
	 */
	@Override
	protected boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(login.getUserType(), "Pakkliste");
	}

	/**
	 * Holder rede p� antall pakklister gjeldende uke og akkumulert
	 * 
	 * @author atle.brekka
	 */
	public class CountModel extends Model {
		private static final long serialVersionUID = 1L;

		public static final String PROPERTY_COUNT_WEEK_STRING = "countWeekString";

		public static final String PROPERTY_COUNT_YEAR_STRING = "countYearString";

		private Integer countWeek;

		private Integer countYear;

		/**
		 * @param weekCount
		 * @param yearCount
		 */
		public CountModel(Integer weekCount, Integer yearCount) {
			countWeek = weekCount;
			countYear = yearCount;
		}

		/**
		 * @return antall gjeldende uke
		 */
		public String getCountWeekString() {
			if (countWeek != null) {
				return countWeek.toString();
			}
			return null;
		}

		/**
		 * @param countString
		 */
		public void setCountWeekString(String countString) {
			String oldCount = getCountWeekString();
			if (countString != null) {
				countWeek = Integer.valueOf(countString);
			} else {
				countWeek = null;
			}
			firePropertyChange(PROPERTY_COUNT_WEEK_STRING, oldCount, countString);

		}

		/**
		 * @return akkumulert antall pakklister
		 */
		public String getCountYearString() {
			if (countYear != null) {
				return countYear.toString();
			}
			return null;
		}

		/**
		 * @param countString
		 */
		public void setCountYearString(String countString) {
			String oldCount = getCountYearString();
			if (countString != null) {
				countYear = Integer.valueOf(countString);
			} else {
				countYear = null;
			}
			firePropertyChange(PROPERTY_COUNT_YEAR_STRING, oldCount, countString);

		}

		/**
		 * @return antall pakklister gjeldende uke
		 */
		public Integer getCountWeek() {
			return countWeek;
		}

		/**
		 * @param count
		 */
		public void setCountWeek(Integer count) {
			String oldCount = getCountWeekString();
			String newCount = null;
			if (count != null) {
				newCount = String.valueOf(count);
			}
			countWeek = count;
			firePropertyChange(PROPERTY_COUNT_WEEK_STRING, oldCount, newCount);
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
	 */
	@Override
	public PacklistV getApplyObject(Transportable transportable, WindowInterface window) {

		List<PacklistV> list = managerRepository.getPacklistVManager()
				.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	void handleFilterExt() {
		initBudgetAndCount();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getProductAreaColumn()
	 */
	@Override
	protected int getProductAreaColumn() {
		return PacklistColumn.PRODUKTOMR�DE.ordinal();
		// return 5;
	}

	@Override
	public void clearApplyObject() {
	}

	public JButton getButtonTrossReady(WindowInterface window) {
		JButton buttonTrossReady = new JButton(new TrossReadyAction(window));
		buttonTrossReady.setEnabled(false);
		emptySelectionListener.addButton(buttonTrossReady);
		return buttonTrossReady;
	}

	public JButton getButtonProductionBasis(WindowInterface window) {
		JButton buttonProductionBasis = new JButton(new ProductionBasisAction(window));
		buttonProductionBasis.setEnabled(false);
		emptySelectionListener.addButton(buttonProductionBasis);
		return buttonProductionBasis;
	}

	public JButton getButtonProductionReport(WindowInterface window) {
		JButton buttonProductionReport = new JButton(new ProductionReportAction(window));
		buttonProductionReport.setEnabled(false);
		emptySelectionListener.addButton(buttonProductionReport);
		return buttonProductionReport;
	}

	public JButton getButtonMonteringsanvisning(WindowInterface window) {
		JButton buttonMonteringsanvisning = new JButton(new MonteringsanvisningAction(window));
		buttonMonteringsanvisning.setEnabled(false);
		emptySelectionListener.addButton(buttonMonteringsanvisning);
		return buttonMonteringsanvisning;
	}

	public JButton getButtonDelelisteReport(WindowInterface window) {
		JButton buttonDelelisteReport = new JButton(new DelelisteReportAction(window));
		buttonDelelisteReport.setEnabled(false);
		emptySelectionListener.addButton(buttonDelelisteReport);
		return buttonDelelisteReport;
	}

	public JButton getButtonExcel(WindowInterface window) {
		JButton buttonExcel = new JButton(new ExcelAction(window));
		buttonExcel.setIcon(IconEnum.ICON_EXCEL.getIcon());
		return buttonExcel;
	}

	private class ExcelAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

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
				exportToExcel(window);
				Util.showMsgFrame(window.getComponent(), "Excel generert",
						"Dersom excelfil ikke kom opp ligger den i katalog definert for excel");
			} catch (ProTransException e) {
				e.printStackTrace();
				Util.showErrorDialog(window, "Feil", e.getMessage());
			}
			Util.setDefaultCursor(window.getComponent());

		}
	}

	private void exportToExcel(WindowInterface window) throws ProTransException {
		String fileName = "Pakkliste_" + Util.getCurrentDateAsDateTimeString() + ".xlsx";
		String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");

		// JXTable tableReport = new JXTable(new
		// PacklistTableModel(getObjectSelectionList(), window, true));

		// ExcelUtil.showDataInExcel(excelDirectory, fileName, null,
		// "Pakkliste", tableReport, null, null, 16, false);
		ExcelUtil.showTableDataInExcel(excelDirectory, fileName, null, "Produksjonsoversikt", table, null, null, 16,
				false);
		// ExcelUtil.showDataInExcelInThread(window, fileName, getTitle(),
		// getExcelTable(), null, null, 16, false);
	}

	private class TrossReadyAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public TrossReadyAction(WindowInterface aWindow) {
			super("Takstolprosjektering...");
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			setTrossReady(window, costTypeTross, costUnitTross);

		}

	}

	private class ProductionBasisAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public ProductionBasisAction(WindowInterface aWindow) {
			super("Produksjonsgrunnlag...");
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			setProductionBasis(window);

		}

	}

	private class ProductionReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public ProductionReportAction(WindowInterface aWindow) {
			super("Produksjonsrapport...");
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			Util.runInThreadWheel(window.getRootPane(), new Threadable() {

				public void enableComponents(boolean enable) {
				}

				public Object doWork(Object[] params, JLabel labelInfo) {
					labelInfo.setText("Genererer produksjonsrapport...");
					String errorMsg = null;
					try {
						// generateAssemblyReport();
						generateProductionReport(window);
					} catch (ProTransException e) {
						errorMsg = e.getMessage();
						e.printStackTrace();
					}
					return errorMsg;
				}

				public void doWhenFinished(Object object) {
					if (object != null) {
						Util.showErrorDialog(window, "Feil", object.toString());
					}
				}

			}, null);

		}

	}

	private class MonteringsanvisningAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public MonteringsanvisningAction(WindowInterface aWindow) {
			super("Monteringsanvisning...");
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			Util.runInThreadWheel(window.getRootPane(), new Threadable() {

				public void enableComponents(boolean enable) {
				}

				public Object doWork(Object[] params, JLabel labelInfo) {
					labelInfo.setText("Genererer monteringsanvisning...");
					String errorMsg = null;
					try {
						opprettOgVisMonteringsanvisning(window);
					} catch (ProTransException e) {
						errorMsg = e.getMessage();
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return errorMsg;
				}

				public void doWhenFinished(Object object) {
					if (object != null) {
						Util.showErrorDialog(window, "Feil", object.toString());
					}
				}

			}, null);

		}

	}

	private void opprettOgVisMonteringsanvisning(WindowInterface window)
			throws IOException, URISyntaxException, JRException {
		PacklistV packlistV = getSelectedObject();
		List<String> monteringsanvisninger = managerRepository.getOrderManager()
				.finnMonteringsanvisninger(packlistV.getOrderNr());

		if (monteringsanvisninger != null && !monteringsanvisninger.isEmpty()) {
			String monteringsanvisning = PdfUtil.slaaSammenFiler(packlistV.getCustomerDetails(), packlistV.getOrderNr(),
					monteringsanvisninger);
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(new File(monteringsanvisning));
			}
		}
	}

	private class DelelisteReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public DelelisteReportAction(WindowInterface aWindow) {
			super("Deleliste kunde...");
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			Util.runInThreadWheel(window.getRootPane(), new Threadable() {

				public void enableComponents(boolean enable) {
				}

				public Object doWork(Object[] params, JLabel labelInfo) {
					labelInfo.setText("Genererer deleliste kunde...");
					String errorMsg = null;
					try {
						// generateAssemblyReport();
						generateDelelisteReport(window);
					} catch (ProTransException e) {
						errorMsg = e.getMessage();
						e.printStackTrace();
					}
					return errorMsg;
				}

				public void doWhenFinished(Object object) {
					if (object != null) {
						Util.showErrorDialog(window, "Feil", object.toString());
					}
				}

			}, null);

		}

	}

	private void generateProductionReport(WindowInterface window) throws ProTransException {
		PacklistV packlistV = getSelectedObject();
		if (packlistV != null) {
			Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
			managerRepository.getOrderManager().lazyLoadOrder(order,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
							LazyLoadOrderEnum.COMMENTS });
			List<Ordreinfo> ordreinfo = managerRepository.getOrderManager().finnOrdreinfo(order.getOrderNr());

			List<String> garasjetyper = Lists
					.newArrayList(Iterables.transform(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

						public boolean apply(Ordreinfo ordreinfo) {
							return ordreinfo.getBeskrivelse().contains("GARASJETYPE");
						}
					}), new Function<Ordreinfo, String>() {

						public String apply(Ordreinfo from) {
							return from.getBeskrivelse();
						}
					}));

			String garasjetype = !garasjetyper.isEmpty() ? garasjetyper.get(0) : "Ukjent";

			List<Delelisteinfo> deleliste = managerRepository.getOrderManager().finnDeleliste(order.getOrderNr(),
					order.getCustomer().getFullName(), order.getPostOffice(), garasjetype);

			OrderLine takstein = order.getOrderLine("Takstein");
			Ordln ordlnTakstein = managerRepository.getOrdlnManager().findByOrdNoAndLnNo(takstein.getOrdNo(),
					takstein.getLnNo());
			if (ordlnTakstein != null) {
				takstein.setOrdln(ordlnTakstein);
			}

			ProductionReportData productionReportData = new ProductionReportData(packlistV.getOrderNr())
					.medNavn(order.getCustomer().getFullName()).medLeveringsadresse(order.getDeliveryAddress())
					.medPostnr(order.getPostalCode()).medTelefonliste(order.getTelephoneNr())
					.medPoststed(order.getPostOffice()).medMontering(order.getDoAssembly())
					.medTransportuke(order.getTransport() == null ? null : order.getTransport().getTransportWeek())
					.medProduksjonsuke(order.getProductionWeek()).medKommentarer(order.getOrderComments())
					.medOrdreinfo(ordreinfo)
					// .medTaktekke(takstein.getDetailsWithoutNoAttributes())
					.medPakketAv(order.getPacklistDoneBy()).medBruker(login.getApplicationUser().getFullName())
					.medProductArea(order.getProductArea()).medDeleliste(deleliste);
					// Order order = packlistV.getOrder() == null ?
					// assembly.getDeviation().getOrder() : assembly.getOrder();
					// managerRepository.getOrderManager().lazyLoadOrder(order,
					// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS,
					// LazyLoadOrderEnum.COMMENTS });

			// List<Ordln> vismaOrderLines =
			// managerRepository.getOrdlnManager().findForFakturagrunnlag(assembly.getOrder().getOrderNr());

			// Integer orderId = order == null ?
			// assembly.getDeviation().getOrder().getOrderId() :
			// order.getOrderId();
			// List<FakturagrunnlagV> fakturagrunnlag =
			// managerRepository.getFakturagrunnlagVManager().findFakturagrunnlag(orderId);
			// List<FakturagrunnlagV> filtrertFakturagrunnlag =
			// Lists.newArrayList(Iterables.filter(fakturagrunnlag,
			// ikkeFraktMed001()));
			// AssemblyReport assemblyReport =
			// assemblyReportFactory.create(assembly.getOrder(),
			// vismaOrderLines);
			// final CraningCostManager craningCostManager =
			// (CraningCostManager)
			// ModelUtil.getBean(CraningCostManager.MANAGER_NAME);
			// AssemblyReportNy assemblyReport = new
			// AssemblyReportNy(craningCostManager, order,
			// filtrertFakturagrunnlag);
			//
			// String orderNr = order == null ?
			// assembly.getDeviation().getOrderNr() : order.getOrderNr();
			// MailConfig mailConfig = new MailConfig("Fakturagrunnlag",
			// "Fakturagrunnlag", "", "");
			// mailConfig.addToHeading(" for ordrenummer " + orderNr);
			//
			ReportViewer reportViewer = new ReportViewer("Produksjon");
			List<ProductionReportData> reportList = Lists.newArrayList(productionReportData);
			// assemblyReportList.add(assemblyReport);
			reportViewer.generateProtransReportFromBeanAndShow(reportList, "Produksjon", ReportEnum.PRODUCTION_REPORT,
					null, null, window, true);

		}
	}

	private void generateDelelisteReport(WindowInterface window) throws ProTransException {
		PacklistV packlistV = getSelectedObject();
		if (packlistV != null) {
			Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
			managerRepository.getOrderManager().lazyLoadOrder(order,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
							LazyLoadOrderEnum.COMMENTS });
			List<Ordreinfo> ordreinfo = managerRepository.getOrderManager().finnOrdreinfo(order.getOrderNr());

			List<String> garasjetyper = Lists
					.newArrayList(Iterables.transform(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

						public boolean apply(Ordreinfo ordreinfo) {
							return ordreinfo.getBeskrivelse().contains("GARASJETYPE");
						}
					}), new Function<Ordreinfo, String>() {

						public String apply(Ordreinfo from) {
							return from.getBeskrivelse();
						}
					}));

			List<Delelisteinfo> deleliste = managerRepository.getOrderManager().finnDeleliste(order.getOrderNr(),
					order.getCustomer().getFullName(), order.getPostOffice(),
					garasjetyper.isEmpty() ? "" : garasjetyper.get(0));

			OrderLine takstein = order.getOrderLine("Takstein");
			Ordln ordlnTakstein = managerRepository.getOrdlnManager().findByOrdNoAndLnNo(takstein.getOrdNo(),
					takstein.getLnNo());
			if (ordlnTakstein != null) {
				takstein.setOrdln(ordlnTakstein);
			}

			ProductionReportData productionReportData = new ProductionReportData(packlistV.getOrderNr())
					.medNavn(order.getCustomer().getFullName()).medLeveringsadresse(order.getDeliveryAddress())
					.medPostnr(order.getPostalCode()).medTelefonliste(order.getTelephoneNr())
					.medPoststed(order.getPostOffice()).medMontering(order.getDoAssembly())
					.medTransportuke(order.getTransport() == null ? null : order.getTransport().getTransportWeek())
					.medProduksjonsuke(order.getProductionWeek()).medKommentarer(order.getOrderComments())
					.medOrdreinfo(ordreinfo)
					// .medTaktekke(takstein.getDetailsWithoutNoAttributes())
					.medPakketAv(order.getPacklistDoneBy()).medBruker(login.getApplicationUser().getFullName())
					.medProductArea(order.getProductArea()).medDeleliste(deleliste);
			//
			ReportViewer reportViewer = new ReportViewer("Deleliste kunde");
			List<ProductionReportData> reportList = Lists.newArrayList(productionReportData);
			reportViewer.generateProtransReportFromBeanAndShow(reportList, "Deleliste kunde",
					ReportEnum.DELELISTE_KUNDE_REPORT, null, null, window, true);

		}
	}

	private void setProductionBasis(WindowInterface window) {
		try {
			PacklistV packlistV = getSelectedObject();
			if (packlistV != null) {
				Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
				if (order != null) {
					String prosent = Util.showInputDialog(window, "Grunnlag", "Angi prosent(heltall)");
					if (!StringUtils.isBlank(prosent)) {
						order.setProductionBasis(Integer.valueOf(prosent));
						managerRepository.getOrderManager().saveOrder(order);
						managerRepository.getPacklistVManager().refresh(packlistV);
					}
				}
			}
		} catch (NumberFormatException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
	}

	private void setTrossReady(WindowInterface window, CostType costTypeTross, CostUnit costUnitTross) {
		try {
			PacklistV packlistV = getSelectedObject();
			if (packlistV != null) {
				Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
				if (order != null) {
					managerRepository.getOrderManager().lazyLoadOrder(order,
							new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });
					TrossReadyViewHandler trossReadyViewHandler = new TrossReadyViewHandler(managerRepository, order,
							costTypeTross, costUnitTross, login);
					TrossReadyView trossReadyView = new TrossReadyView(trossReadyViewHandler);
					Util.showEditViewable(trossReadyView, window);

					if (!trossReadyViewHandler.getCanceled()) {
						order = trossReadyViewHandler.getOrder();
						managerRepository.getOrderManager().saveOrder(order);
						managerRepository.getPacklistVManager().refresh(packlistV);
					}
				}
			}

		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	protected void setRealProductionHours(PacklistV object, BigDecimal overstyrtTidsforbruk) {
		// TODO Auto-generated method stub

	}

	public JTextField getTextFieldWeekFrom() {
		textFieldWeekFrom = new JTextField();
		// textFieldWeekFrom.addFocusListener(new TextFieldFocusListener());
		return textFieldWeekFrom;
	}

	public JTextField getTextFieldWeekTo() {
		textFieldWeekTo = new JTextField();
		// textFieldWeekTo.addFocusListener(new TextFieldFocusListener());
		return textFieldWeekTo;
	}

	private class TextFieldFocusListener implements FocusListener {

		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void focusLost(FocusEvent arg0) {
			objectList.clear();
			Collection<PacklistV> objectLines = applyListInterface.getObjectLines();
			// Set<T> distinkteLinjer = Sets.newHashSet();
			// distinkteLinjer.addAll(objectLines);
			if (objectLines != null) {
				objectList.addAll(objectLines);
				// objectList.addAll(distinkteLinjer);
			}

		}

	}

	public JButton getButtonFilter() {
		JButton button = new JButton(new FilterAction());
		return button;
	}

	public JButton getButtonUpdateStatus(WindowInterface window) {
		JButton button = new JButton(new UpdateStatusAction(window));
		menuItemUpdateStatus.addActionListener(new UpdateOrderstatusActionListener(window));
		return button;
	}

	public class FilterAction extends AbstractAction {

		public FilterAction() {
			super("Filtrer");
		}

		public void actionPerformed(ActionEvent arg0) {
			objectList.clear();
			Collection<PacklistV> objectLines = applyListInterface.getObjectLines();
			Integer fraUke = StringUtils.isBlank(textFieldWeekFrom.getText()) ? 1
					: Integer.valueOf(textFieldWeekFrom.getText());
			Integer tilUke = StringUtils.isBlank(textFieldWeekTo.getText()) ? 53
					: Integer.valueOf(textFieldWeekTo.getText());
			List<PacklistV> filter = Lists.newArrayList(Iterables.filter(objectLines, ukeFraTil(fraUke, tilUke)));
			// Set<T> distinkteLinjer = Sets.newHashSet();
			// distinkteLinjer.addAll(objectLines);
			if (filter != null) {
				objectList.addAll(filter);
				// objectList.addAll(distinkteLinjer);
			}

		}

		private Predicate<PacklistV> ukeFraTil(final Integer fraUke, final Integer tilUke) {
			return new Predicate<PacklistV>() {

				public boolean apply(PacklistV packlistV) {
					return fraUke != null && tilUke != null && packlistV.getProductionWeek() >= fraUke
							&& packlistV.getProductionWeek() <= tilUke;
				}
			};
		}

	}

	public class UpdateStatusAction extends AbstractAction {
		private WindowInterface window;

		public UpdateStatusAction(WindowInterface window) {
			super("Oppdater status");
			this.window = window;
		}

		public void actionPerformed(ActionEvent arg0) {
			Util.setWaitCursor(window);

			for (PacklistV packlistV : applyListInterface.getObjectLines()) {
				oppdaterInfo(packlistV);

				Map<String, String> statusMap = Util.createStatusMap(packlistV.getOrderStatus());
				String status = statusMap.get(takstolChecker.getArticleName());
				if (status == null) {
					updateOrderStatus(packlistV, statusMap, window);
				}
			}
			Util.setDefaultCursor(window);

		}

	}

	private void oppdaterInfo(PacklistV packlistV) {
		Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());

		if (order.getInfo() == null) {
			if (!Hibernate.isInitialized(order.getOrderLines())) {
				managerRepository.getOrderManager().lazyLoadOrder(order,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES,
								LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES, LazyLoadOrderEnum.COMMENTS,
								LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.ORDER_COSTS,
								LazyLoadOrderEnum.COLLIES });
			}
			order.setInfo(order.orderLinesToString());
			managerRepository.getOrderManager().saveOrder(order);
		}
	}

	private void updateOrderStatus(PacklistV packlistV, Map<String, String> statusMap, WindowInterface window) {
		String status;
		Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
		if (order != null) {
			managerRepository.getOrderManager().lazyLoadTree(order);
			status = takstolChecker.getArticleStatus(order);
			statusMap.put(takstolChecker.getArticleName(), status);
			order.setStatus(Util.statusMapToString(statusMap));
			try {
				managerRepository.getOrderManager().saveOrder(order);
			} catch (ProTransException e) {
				Util.showErrorDialog(window, "Feil", e.getMessage());
				e.printStackTrace();
			}
			applyListInterface.refresh(packlistV);
		}
	}

	class UpdateOrderstatusActionListener implements ActionListener {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public UpdateOrderstatusActionListener(final WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(final ActionEvent arg0) {
			Util.setWaitCursor(window);
			PacklistV packlistV = (PacklistV) objectSelectionList
					.getElementAt(table.convertRowIndexToModel(objectSelectionList.getSelectionIndex()));
			oppdaterInfo(packlistV);
			Map<String, String> statusMap = Util.createStatusMap(packlistV.getOrderStatus());
			String status = statusMap.get(takstolChecker.getArticleName());
			if (status == null) {
				updateOrderStatus(packlistV, statusMap, window);
			}
			Util.setDefaultCursor(window);
		}

	}

	@Override
	public boolean isProductionWindow() {
		return false;
	}

}
