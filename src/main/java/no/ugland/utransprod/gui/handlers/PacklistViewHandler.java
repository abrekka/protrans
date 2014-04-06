package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

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
import no.ugland.utransprod.gui.model.ExternalOrderModel;
import no.ugland.utransprod.gui.model.HorizontalAlignmentCellRenderer;
import no.ugland.utransprod.gui.model.PacklistApplyList;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.gui.model.TextPaneRendererOrder;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer setting av pakkliste klar
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

    @Inject
    public PacklistViewHandler(Login login, ManagerRepository aManagerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory,
	    OrderViewHandlerFactory orderViewHandlerFactory, PacklistApplyList productionInterface,
	    @Named("kostnadTypeTakstoler") CostType aCostTypeTross, @Named("kostnadEnhetTakstoler") CostUnit aCostUnitTross) {
	super(login, aManagerRepository, deviationViewHandlerFactory, productionInterface, "Pakklister", TableEnum.TABLEPACKLIST);
	costTypeTross = aCostTypeTross;
	costUnitTross = aCostUnitTross;
	orderViewHandler = orderViewHandlerFactory.create(true);
	presentationModelBudget = new PresentationModel(new ProductionBudgetModel(new Budget(null, null, null, BigDecimal.valueOf(0), null, null)));
	presentationModelCount = new PresentationModel(new CountModel(Integer.valueOf(0), Integer.valueOf(0)));

	initBudgetAndCount();
	emptySelectionListener = new EmptySelectionListener(objectSelectionList);
	objectSelectionList.addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION_EMPTY, emptySelectionListener);

    }

    /**
     * Initierer budsjett og antall pakklister
     */
    private void initBudgetAndCount() {

	YearWeek yearWeekPlussOne = Util.addWeek(new YearWeek(Util.getCurrentYear(), Util.getCurrentWeek()), 1);
	ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
	Budget productionBudget = managerRepository.getBudgetManager().findByYearAndWeekPrProductAreaGroup(yearWeekPlussOne.getYear(),
		yearWeekPlussOne.getWeek(), group, BudgetType.PRODUCTION);

	if (productionBudget == null) {
	    productionBudget = new Budget(null, null, null, BigDecimal.valueOf(0), null, null);
	}

	presentationModelBudget.setValue(ProductionBudgetModel.PROPERTY_BUDGET_VALUE, productionBudget.getBudgetValueString());

	Date fromDateWeek = Util.getFirstDateInWeek(Util.getCurrentYear(), Util.getCurrentWeek());
	Date toDateWeek = Util.getLastDateInWeek(Util.getCurrentYear(), Util.getCurrentWeek());
	Integer weekCount = managerRepository.getOrderManager().getPacklistCountForWeekByProductAreaGroupName(fromDateWeek, toDateWeek, group);

	Date fromDateYear = Util.getFirstDateInYear(Util.getCurrentYear());
	Date toDateYear = Util.getLastDateInWeek(Util.getCurrentYear(), Util.getCurrentWeek());
	Integer yearCount = managerRepository.getOrderManager().getPacklistCountForWeekByProductAreaGroupName(fromDateYear, toDateYear, group);

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
     * Lager knapp for å editere ordre
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
	return BasicComponentFactory.createLabel(presentationModelBudget.getModel(ProductionBudgetModel.PROPERTY_BUDGET_VALUE));
    }

    /**
     * Lager label for antall pakklsiter
     * 
     * @return label
     */
    public JLabel getLabelCountWeek() {
	return BasicComponentFactory.createLabel(presentationModelCount.getModel(CountModel.PROPERTY_COUNT_WEEK_STRING));
    }

    /**
     * Lager label for akkumulert antall pakklister
     * 
     * @return label
     */
    public JLabel getLabelCountYear() {
	return BasicComponentFactory.createLabel(presentationModelCount.getModel(CountModel.PROPERTY_COUNT_YEAR_STRING));
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

	    EditPacklistView editPacklistView = new EditPacklistView(login, true, order.getPacklistDuration());

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

	    return PacklistColumn.hentKolonne(getColumnName(columnIndex)).getValue(packlistV, takstolChecker, statusMap, window, managerRepository,
		    applyListInterface);
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
		    (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
	    PacklistV packlistV = getSearchObject(window, list);
	    if (packlistV != null) {
		int selectedIndex = objectList.indexOf(packlistV);
		table.getSelectionModel()
			.setSelectionInterval(table.convertRowIndexToView(selectedIndex), table.convertRowIndexToView(selectedIndex));

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
	return 0;
    }

    /**
     * Åpne ordre
     * 
     * @param window
     */
    void doEditAction(WindowInterface window) {
	OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
	Util.setWaitCursor(window.getComponent());
	PacklistV packlistV = getSelectedObject();
	Order order = orderManager.findByOrderNr(packlistV.getOrderNr());
	orderViewHandler.openOrderView(order, false, window);
	Util.setDefaultCursor(window.getComponent());

    }

    /**
     * Håndterer dobbeltklikk på ordre
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
	    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
		if (objectSelectionList.getSelection() != null) {
		    doEditAction(window);
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
		ExternalOrderViewHandler externalOrderViewHandler = new ExternalOrderViewHandler(login, managerRepository, order);
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
	ORDRE("Ordre", 150, true) {
	    @Override
	    public Class<?> getColumnClass() {
		return PacklistV.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		return packlistV;
	    }

	},
	TAKSTOLER("Takstoler", 120, true) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		return getStatus(takstolChecker, statusMap, packlistV, window, managerRepository, applyListInterface);
	    }

	},
	GULVSPON("Gulvspon", 70, true) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		if (packlistV.getHasGulvspon() != null && packlistV.getHasGulvspon() == 1) {
		    return "V " + Util.nullIntegerToString(packlistV.getNumberOfGulvspon());
		}
		return null;
	    }

	},
	TRANSPORT("Transport", 100, true) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		return packlistV.getTransportDetails();
	    }

	},
	PAKKLISTE_KLAR("Pakkliste klar", 100, true) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		if (packlistV.getPacklistReady() != null) {
		    return Util.SHORT_DATE_FORMAT.format(packlistV.getPacklistReady());
		}
		return "---";
	    }

	},

	TAKSTOL_PROSJEKTERING("Takstol prosjektering", 120, true) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		if (packlistV.getTrossReady() != null) {
		    return Util.SHORT_DATE_FORMAT.format(packlistV.getTrossReady());

		}
		return "---";
	    }

	},
	TEGNER("Tegner", 120, true) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		return packlistV.getTrossDrawer();
	    }

	},
	PRODUKSJONSGRUNNLAG("Produksjonsgrunnlag", 120, true) {
	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		return packlistV.getProductionBasis();
	    }
	},
	TIDSBRUK("Tidsbruk", 70, true) {
	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		return packlistV.getPacklistDuration();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }
	},
	GJORT_AV("Gjort av", 120, true) {
	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		return packlistV.getPacklistDoneBy();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }
	},
	PROD_UKE("Prod.uke", 70, true) {
	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
		return packlistV.getProductionWeek();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }
	},
	PRODUKTOMRÅDE("Produktområde", 70, false) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		    WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
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
	    return PacklistColumn.valueOf(StringUtils.replace(StringUtils.upperCase(String.valueOf(kolonneoverskrift)), " ", "_").replaceAll("\\.",
		    "_"));
	}

	public boolean skalTilExcel() {
	    return skalTilExcel;
	}

	public abstract Object getValue(PacklistV packlistV, StatusCheckerInterface<Transportable> takstolChecker, Map<String, String> statusMap,
		WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface);

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

	String getStatus(StatusCheckerInterface<Transportable> checker, Map<String, String> statusMap, PacklistV packlistV, WindowInterface window,
		ManagerRepository managerRepository, ApplyListInterface<PacklistV> applyListInterface) {
	    String status = statusMap.get(checker.getArticleName());
	    if (status != null) {
		return status;
	    }

	    Order order = managerRepository.getOrderManager().findByOrderNr(packlistV.getOrderNr());
	    if (order != null) {
		managerRepository.getOrderManager().lazyLoadTree(order);
		status = checker.getArticleStatus(order);
		statusMap.put(checker.getArticleName(), status);
		order.setStatus(Util.statusMapToString(statusMap));
		try {
		    managerRepository.getOrderManager().saveOrder(order);
		} catch (ProTransException e) {
		    Util.showErrorDialog(window, "Feil", e.getMessage());
		    e.printStackTrace();
		}
		applyListInterface.refresh(packlistV);

	    }
	    return status;
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
     * Holder rede på antall pakklister gjeldende uke og akkumulert
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

	List<PacklistV> list = managerRepository.getPacklistVManager().findApplyableByOrderNr(transportable.getOrder().getOrderNr());
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
	return PacklistColumn.PRODUKTOMRÅDE.ordinal();
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
		Util.showMsgFrame(window.getComponent(), "Excel generert", "Dersom excelfil ikke kom opp ligger den i katalog definert for excel");
	    } catch (ProTransException e) {
		e.printStackTrace();
		Util.showErrorDialog(window, "Feil", e.getMessage());
	    }
	    Util.setDefaultCursor(window.getComponent());

	}
    }

    private void exportToExcel(WindowInterface window) throws ProTransException {
	String fileName = "Pakkliste_" + Util.getCurrentDateAsDateTimeString() + ".xls";
	String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");

	// JXTable tableReport = new JXTable(new
	// PacklistTableModel(getObjectSelectionList(), window, true));

	// ExcelUtil.showDataInExcel(excelDirectory, fileName, null,
	// "Pakkliste", tableReport, null, null, 16, false);
	ExcelUtil.showTableDataInExcel(excelDirectory, fileName, null, "Produksjonsoversikt", table, null, null, 16, false);
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
		    managerRepository.getOrderManager().lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });
		    TrossReadyViewHandler trossReadyViewHandler = new TrossReadyViewHandler(managerRepository, order, costTypeTross, costUnitTross,
			    login);
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
}
