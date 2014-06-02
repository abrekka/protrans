package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableCellRenderer;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.AssemblyPlannerView;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderPanelTypeEnum;
import no.ugland.utransprod.gui.OrderPanelView;
import no.ugland.utransprod.gui.SupplierOrderView;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.RefreshButton;
import no.ugland.utransprod.gui.edit.FilterAssemblyView;
import no.ugland.utransprod.gui.edit.FilterAssemblyView.AssemblyFilter;
import no.ugland.utransprod.gui.edit.FilterAssemblyView.AssemblyfilterListener;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.AssemblyOverdueV;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.IAssembly;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.service.AssemblyManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.OrderLineWrapper;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.excel.ExcelUtil;
import no.ugland.utransprod.util.report.AssemblyWeekReport;
import no.ugland.utransprod.util.report.ReportViewer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Hjelpeklasse for visning og administrasjon av monteringer
 * @author atle.brekka
 *
 */
/**
 * @author atle.brekka
 */
public class AssemblyPlannerViewHandler implements Closeable, Updateable, ListDataListener, AssemblyfilterListener {
    public static final boolean INVISIBLE = false;
    public static final boolean VISIBLE = true;
    public static final boolean NOT_CENTER = false;
    public static final boolean CENTER = true;

    private JLabel labelSearchResult;

    private OrderViewHandler orderViewHandler;

    // ArrayListModel suppliers;
    Multimap<ProductAreaGroup, Supplier> supplierMap = ArrayListMultimap.create();

    private final SelectionInList weekSelectionList;

    AssemblyPlannerView view;

    YearWeek yearWeek;

    private Map<Supplier, Map<Integer, SupplierOrderViewHandler>> assemblyViewHandlers = new Hashtable<Supplier, Map<Integer, SupplierOrderViewHandler>>();

    JPopupMenu popupMenuSetAssembly;

    JPopupMenu popupMenuSetAssemblyDeviation;
    JPopupMenu popupMenuAssembly;

    JMenuItem menuItemSetAssembly;

    JMenuItem menuItemShowContent;
    JMenuItem menuItemSetSentBase;

    private WindowInterface currentWindow;

    private PropertyChangeListener assemblyChangeListener;

    private JLabel labelWarning;

    private boolean disposeOnClose = true;

    private JXTable tableDeviation;
    private JXTable tableAssembly;

    private final ArrayListModel deviationList;
    private ArrayListModel assemblyArrayListModel;
    private List<Assembly> assemblies = Lists.newArrayList();

    final SelectionInList deviationSelectionList;
    final SelectionInList assemblySelectionList;

    private Login login;
    private SupplierOrderViewHandlerFactory supplierOrderViewHandlerFactory;
    private ManagerRepository managerRepository;
    PresentationModel productAreaGroupModel;

    private JCheckBox checkBoxListView;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * @param orderViewHandler
     * @param aUserType
     * @param aApplicationUser
     */
    public AssemblyPlannerViewHandler(OrderViewHandler orderViewHandler, Login aLogin,
	    SupplierOrderViewHandlerFactory aSupplierOrderViewHandlerFactory, ManagerRepository aManagerRepository) {
	managerRepository = aManagerRepository;
	supplierOrderViewHandlerFactory = aSupplierOrderViewHandlerFactory;
	login = aLogin;
	deviationList = new ArrayListModel();

	deviationSelectionList = new SelectionInList((ListModel) deviationList);

	assemblyArrayListModel = new ArrayListModel();
	assemblySelectionList = new SelectionInList((ListModel) assemblyArrayListModel);

	labelWarning = new JLabel();
	labelWarning.setIcon(IconEnum.ICON_WARNING.getIcon());

	assemblyChangeListener = new AssemblyChangeListener();

	this.orderViewHandler = orderViewHandler;

	orderViewHandler.addListDataListener(this);

	weekSelectionList = new SelectionInList(Util.getWeeks());

	initOverdue();
	initProductAreaGroup();
    }

    private void initProductAreaGroup() {
	productAreaGroupModel = new PresentationModel(new ProductAreaGroupModel(ProductAreaGroup.UNKNOWN));
	productAreaGroupModel.addBeanPropertyChangeListener(new ProductAreaChangeListener());
	// ProductAreaGroupManager productAreaGroupManager =
	// (ProductAreaGroupManager) ModelUtil
	// .getBean("productAreaGroupManager");
	/*
	 * productAreaGroupList = new ArrayList<ProductAreaGroup>();
	 * List<ProductAreaGroup> groups = productAreaGroupManager.findAll(); if
	 * (groups != null) { productAreaGroupList.addAll(groups); }
	 */
    }

    /**
     * Initerere info om order som er på overtid når det gjelder montering
     */
    void initOverdue() {

	AssemblyOverdueV overdue = managerRepository.getAssemblyOverdueVManager().getAssemblyOverdueV();
	if (overdue != null) {
	    labelWarning.setText(overdue.getYearWeek());
	    labelWarning.setVisible(true);
	} else {
	    labelWarning.setVisible(false);
	}
    }

    /**
     * Initierer liste med avvik
     */
    void initDeviationList() {
	DeviationManager deviationManager = (DeviationManager) ModelUtil.getBean("deviationManager");
	deviationList.clear();
	List<Deviation> deviations = deviationManager.findAllAssembly();
	if (deviations != null) {
	    deviationList.addAll(deviations);
	}
    }

    public void initAssemblyList() {
	assemblyArrayListModel.clear();
	AssemblyManager assemblyManager = (AssemblyManager) ModelUtil.getBean(AssemblyManager.MANAGER_NAME);
	assemblies = assemblyManager.findByYear(yearWeek.getYear());
	if (assemblies != null) {
	    assemblyArrayListModel.addAll(assemblies);
	}
    }

    /**
     * Setter opp popupmenyer
     * 
     * @param window
     */
    private void setUpPopupMenu(WindowInterface window) {
	MenuItemListener menuItemListenerOrder = new MenuItemListener(true, window);
	MenuItemListener menuItemListenerDeviation = new MenuItemListener(false, window);
	MenuItemListenerAssembly menuItemListenerAssembly = new MenuItemListenerAssembly(window);
	popupMenuSetAssembly = new JPopupMenu("Sett montering...");
	menuItemSetAssembly = new JMenuItem("Sett montering...");
	menuItemSetAssembly.setEnabled(hasWriteAccess());
	popupMenuSetAssembly.add(menuItemSetAssembly);
	menuItemSetAssembly.addActionListener(menuItemListenerOrder);

	popupMenuSetAssemblyDeviation = new JPopupMenu("Sett montering...");
	popupMenuAssembly = new JPopupMenu("Sett montering...");
	menuItemSetAssembly = new JMenuItem("Sett montering...");
	menuItemSetAssembly.addActionListener(menuItemListenerDeviation);
	menuItemShowContent = new JMenuItem("Vis innhold...");
	menuItemShowContent.addActionListener(menuItemListenerDeviation);
	popupMenuSetAssemblyDeviation.add(menuItemSetAssembly);
	popupMenuSetAssemblyDeviation.add(menuItemShowContent);

	menuItemSetSentBase = new JMenuItem("Sendt underlag...");
	menuItemSetSentBase.addActionListener(menuItemListenerAssembly);
	popupMenuAssembly.add(menuItemSetSentBase);
    }

    /**
     * Lager tabell for avvik
     * 
     * @param window
     * @return tabll
     */
    public JXTable getTableDeviation(WindowInterface window) {
	setUpPopupMenu(window);
	tableDeviation = new JXTable();

	initDeviationList();
	tableDeviation.setModel(new DeviationTableModel(deviationSelectionList));
	tableDeviation.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tableDeviation.setSelectionModel(new SingleListSelectionAdapter(deviationSelectionList.getSelectionIndexHolder()));
	tableDeviation.setColumnControlVisible(true);

	tableDeviation.addMouseListener(new DeviationRightClickListener());

	tableDeviation.setName("TableDeviation");
	return tableDeviation;
    }

    public JXTable getTableAssembly() {
	tableAssembly = new JXTable();

	ColorHighlighter assembliedHighlighter = new ColorHighlighter(new PatternPredicate("Ja", AssemblyColumn.MONTERT.ordinal()),
		ColorEnum.GREEN.getColor(), null);
	ColorHighlighter missingHighlighter = new ColorHighlighter(new PatternPredicate("1", AssemblyColumn.SENDT_MANGLER.ordinal()),
		ColorEnum.YELLOW.getColor(), null);
	ColorHighlighter overdueHighlighter = new ColorHighlighter(new PatternPredicate("1", AssemblyColumn.OVERTID.ordinal()),
		ColorEnum.RED.getColor(), null);
	initAssemblyList();
	tableAssembly.setModel(new AssemblyTableModel(assemblyArrayListModel));
	tableAssembly.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tableAssembly.setSortable(true);
	tableAssembly.setSelectionModel(new SingleListSelectionAdapter(assemblySelectionList.getSelectionIndexHolder()));
	tableAssembly.setColumnControlVisible(true);

	tableAssembly.addMouseListener(new AssemblyRightClickListener());

	tableAssembly.addHighlighter(assembliedHighlighter);
	tableAssembly.addHighlighter(missingHighlighter);
	tableAssembly.addHighlighter(overdueHighlighter);

	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

	for (AssemblyColumn column : AssemblyColumn.values()) {
	    if (column.hasCenterAlignment()) {
		tableAssembly.getColumnExt(column.ordinal()).setCellRenderer(centerRenderer);
	    }
	    tableAssembly.getColumnExt(column.ordinal()).setPreferredWidth(column.getWidth());
	}

	// tableAssembly.getColumnExt(AssemblyColumn.UKE.ordinal()).setCellRenderer(centerRenderer);
	// tableAssembly.getColumnExt(AssemblyColumn.MONTERINGSUM.ordinal()).setCellRenderer(centerRenderer);
	// tableAssembly.getColumnExt(AssemblyColumn.MONTERT.ordinal()).setCellRenderer(centerRenderer);
	// tableAssembly.getColumnExt(AssemblyColumn.OPPRINNELIG.ordinal()).setCellRenderer(centerRenderer);
	// tableAssembly.getColumnExt(AssemblyColumn.ORDRENR.ordinal()).setCellRenderer(centerRenderer);
	// tableAssembly.getColumnExt(AssemblyColumn.PLANLAGT.ordinal()).setCellRenderer(centerRenderer);
	// tableAssembly.getColumnExt(AssemblyColumn.POSTNR.ordinal()).setCellRenderer(centerRenderer);
	// tableAssembly.getColumnExt(AssemblyColumn.PROD_UKE.ordinal()).setCellRenderer(centerRenderer);
	// tableAssembly.getColumnExt(AssemblyColumn.SENDT_UNDERLAG.ordinal()).setCellRenderer(centerRenderer);

	tableAssembly.getColumnExt(AssemblyColumn.SENDT_MANGLER.ordinal()).setVisible(false);
	tableAssembly.getColumnExt(AssemblyColumn.OVERTID.ordinal()).setVisible(false);

	tableAssembly.setName("TableAssembly");
	return tableAssembly;
    }

    /**
     * Henter monteringslag
     * 
     * @return monteringslag
     */
    @SuppressWarnings("unchecked")
    public List<Supplier> getSuppliers(YearWeek yearWeek, ProductAreaGroup productAreaGroup) {
	List<Supplier> suppliers = (List<Supplier>) supplierMap.get(productAreaGroup);
	if (suppliers == null || suppliers.size() == 0) {
	    List<Supplier> suppliersHavingAssembly = new ArrayListModel(managerRepository.getSupplierManager().findHavingAssembly(yearWeek.getYear(),
		    yearWeek.getWeek() - 1, yearWeek.getWeek() + 1, productAreaGroup));
	    List<Supplier> activeSuppliers = new ArrayListModel(managerRepository.getSupplierManager().findActiveByTypeName("Montering",
		    "postalCode", productAreaGroup));
	    suppliers = new ArrayListModel(CollectionUtils.union(suppliersHavingAssembly, activeSuppliers));
	    Collections.sort(suppliers, new SupplierComparator());
	    supplierMap.putAll(productAreaGroup, suppliers);
	}
	return suppliers;
    }

    /**
     * Lager knapp for søk
     * 
     * @param window
     * @param assemblyPlannerView
     * @return knapp
     */
    public JButton getButtonSearchOrder(WindowInterface window, AssemblyPlannerView assemblyPlannerView) {
	this.currentWindow = window;
	return new JButton(new SearchOrderAction(window, assemblyPlannerView));
    }

    /**
     * Lager label for søkeresultat
     * 
     * @return label
     */
    public JLabel getLabelSearchResult() {
	labelSearchResult = new JLabel();
	return labelSearchResult;
    }

    /**
     * Henter label for advarsel om order som har gått over tiden
     * 
     * @return label
     */
    public JLabel getLabelWarning() {
	return labelWarning;
    }

    /**
     * Lager avbrytknapp
     * 
     * @param aWindow
     * @return knapp
     */
    public JButton getCancelButton(WindowInterface aWindow) {
	return new CancelButton(aWindow, this, disposeOnClose);
    }

    /**
     * Lager oppdateringknapp
     * 
     * @param aWindow
     * @return knapp
     */
    public JButton getRefreshButton(WindowInterface aWindow) {
	return new RefreshButton(this, aWindow);
    }

    /**
     * Lager knapp for utskrift
     * 
     * @param aWindow
     * @return knapp
     */
    public JButton getPrintButton(WindowInterface aWindow) {
	JButton button = new JButton(new PrintAction(aWindow));
	button.setIcon(IconEnum.ICON_PRINT.getIcon());
	return button;
    }

    public JButton getExcelButton(WindowInterface aWindow) {
	JButton button = new JButton(new ExcelAction(aWindow));
	button.setIcon(IconEnum.ICON_EXCEL.getIcon());
	return button;
    }

    public JButton getFilterButton(WindowInterface aWindow) {
	JButton button = new JButton(new FilterAction(aWindow, this));
	button.setSize(20, 12);
	return button;
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
	    if (!checkBoxListView.isSelected()) {
		Util.showErrorDialog(window, "Excel", "Excelknappen kan kun brukes i listevisning");
		Util.setDefaultCursor(window.getComponent());
		return;
	    }

	    try {
		String fileName = "Produksjonsoversikt_" + Util.getCurrentDateAsDateTimeString() + ".xls";
		String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");

		// JXTable tableReport = new JXTable(new
		// ProductionOverviewTableModel(objectList));

		// ExcelUtil.showDataInExcel(excelDirectory, fileName, null,
		// "Produksjonsoversikt", table, null, null, 16, false);
		ExcelUtil.showTableDataInExcel(excelDirectory, fileName, null, "Montering", tableAssembly, null, null, 16, false);
	    } catch (ProTransException e) {
		e.printStackTrace();
		Util.showErrorDialog(window, "Feil", e.getMessage());
	    }
	    Util.setDefaultCursor(window.getComponent());

	}
    }

    private class FilterAction extends AbstractAction {
	private WindowInterface window;
	private AssemblyfilterListener assemblyfilterListener;

	public FilterAction(WindowInterface aWindow, AssemblyfilterListener listener) {
	    super("Filtrer");
	    window = aWindow;
	    this.assemblyfilterListener = listener;
	}

	public void actionPerformed(ActionEvent arg0) {
	    FilterAssemblyView filterAssemblyView = new FilterAssemblyView();

	    JDialog dialog = Util.getDialog(window, "Filter", true);
	    WindowInterface window = new JDialogAdapter(dialog);

	    window.add(filterAssemblyView.buildPanel(window, assemblyfilterListener));
	    window.pack();
	    window.setSize(new Dimension(450, 700));

	    Util.locateOnScreenCenter(window);
	    window.setVisible(true);

	}
    }

    /**
     * Henter panel med order som ikke har montering
     * 
     * @param assemblyPlannerView
     * @return view
     */
    public OrderPanelView getOrderPanelView() {

	return new OrderPanelView(orderViewHandler, OrderPanelTypeEnum.ASSEMBLY_ORDERS, "Ordre:");
    }

    public void setAssemblyPlannerView(AssemblyPlannerView assemblyPlannerView) {
	view = assemblyPlannerView;
    }

    /**
     * Søking
     * 
     * @param window
     * @param assemblyPlannerView
     */
    void doSearch(WindowInterface window, AssemblyPlannerView assemblyPlannerView) {
	Transportable transportable = orderViewHandler.searchOrder(window, true);
	if (transportable != null) {

	    if (transportable.getAssembly() != null) {
		YearWeek selectedYearWeek = new YearWeek(transportable.getAssembly().getAssemblyYear(), transportable.getAssembly().getAssemblyWeek());
		assemblyPlannerView.changeWeek(selectedYearWeek);
		setSelectedOrder(transportable.getAssembly());
	    } else {
		labelSearchResult.setText("Orderen skal ikke monteres");
		orderViewHandler.setSelectedNewTransportable(transportable);
	    }

	}
    }

    /**
     * Søking
     * 
     * @author atle.brekka
     */
    private class SearchOrderAction extends AbstractAction {
	/**
		 *
		 */
	private static final long serialVersionUID = 1L;

	/**
		 *
		 */
	private WindowInterface window;

	/**
		 *
		 */
	private AssemblyPlannerView assemblyPlannerView;

	/**
	 * @param aWindow
	 * @param aAssemblyPlannerView
	 */
	public SearchOrderAction(WindowInterface aWindow, AssemblyPlannerView aAssemblyPlannerView) {
	    super("Søk ordre...");
	    window = aWindow;
	    assemblyPlannerView = aAssemblyPlannerView;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    doSearch(window, assemblyPlannerView);
	}
    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean canClose(String actionString, WindowInterface window) {
	cleanUp();
	return true;
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean doDelete(WindowInterface window) {
	return true;

    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doNew(WindowInterface window) {
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doRefresh(WindowInterface window) {
	orderViewHandler.initAndGetOrderPanelSelectionList(OrderPanelTypeEnum.ASSEMBLY_ORDERS);
	view.changeWeek(null);
	initDeviationList();

    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doSave(WindowInterface window) {
    }

    /**
     * Henter vindusstørrelse
     * 
     * @return vindusstørrelse
     */
    public Dimension getWindowSize() {
	return new Dimension(1275, 700);
    }

    /**
     * Henter horisontal lytter for scrollbar
     * 
     * @param scrollBarWeek
     * @return lytter
     */
    public AdjustmentListener getHorizontalAdjustmentListener(JScrollBar scrollBarWeek) {
	return new HorizontalScrollAdjustmentListener(scrollBarWeek);
    }

    /**
     * Henter vertikal lytter for scrollbar
     * 
     * @param scrollBar
     * @return lytter
     */
    public AdjustmentListener getVerticalAdjustmentListener(JScrollBar scrollBar) {
	return new VerticalScrollAdjustmentListener(scrollBar);
    }

    /**
     * Håndterer scrollbar for synkronisering med andre scrollbarer
     * 
     * @author atle.brekka
     */
    private class HorizontalScrollAdjustmentListener implements AdjustmentListener {
	/**
		 *
		 */
	private JScrollBar horizontalScrollBarWeeks;

	/**
	 * @param horScrollBar
	 */
	public HorizontalScrollAdjustmentListener(JScrollBar horScrollBar) {
	    horizontalScrollBarWeeks = horScrollBar;
	}

	/**
	 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.AdjustmentEvent)
	 */
	public void adjustmentValueChanged(AdjustmentEvent e) {
	    horizontalScrollBarWeeks.setValue(e.getValue());

	}

    }

    /**
     * Håndterer endringer for vertikal scrollbar
     * 
     * @author atle.brekka
     */
    private class VerticalScrollAdjustmentListener implements AdjustmentListener {
	/**
		 *
		 */
	private JScrollBar verticalScrollBar;

	/**
	 * @param verScrollBar
	 */
	public VerticalScrollAdjustmentListener(JScrollBar verScrollBar) {
	    verticalScrollBar = verScrollBar;
	}

	/**
	 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.AdjustmentEvent)
	 */
	public void adjustmentValueChanged(AdjustmentEvent e) {
	    verticalScrollBar.setValue(e.getValue());

	}

    }

    /**
     * Henter ukeliste
     * 
     * @param routeDateAdapter
     * @return ukeliste
     */
    public SelectionInList getWeekSelectionList(BeanAdapter routeDateAdapter) {
	weekSelectionList.setSelectionHolder(routeDateAdapter.getValueModel(YearWeek.PROPERTY_WEEK));
	return weekSelectionList;
    }

    /**
     * Henter lytter for endring av uke
     * 
     * @param aRouteDate
     * @param window
     * @return lytter
     */
    public PropertyChangeListener getWeekChangeListener(YearWeek aRouteDate, WindowInterface window) {
	yearWeek = aRouteDate;
	return new WeekChangeListener(window);
    }

    /**
     * Håndterer endring av valgt uke
     * 
     * @author atle.brekka
     */
    private final class WeekChangeListener implements PropertyChangeListener {
	/**
		 *
		 */
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public WeekChangeListener(WindowInterface aWindow) {
	    window = aWindow;

	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
	    Util.setWaitCursor(window.getComponent());
	    view.changeWeek(null);
	    Util.setDefaultCursor(window.getComponent());
	}

    }

    /**
     * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
     */
    public void contentsChanged(ListDataEvent arg0) {
    }

    /**
     * @see javax.swing.event.ListDataListener#intervalAdded(javax.swing.event.ListDataEvent)
     */
    public void intervalAdded(ListDataEvent arg0) {
    }

    /**
     * @see javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event.ListDataEvent)
     */
    public void intervalRemoved(ListDataEvent arg0) {
    }

    /**
     * Henter handler for monteringsteam
     * 
     * @param assemblyTeam
     * @param currentYearWeek
     * @param weekCounter
     * @param starting
     * @param productAreaGroup
     * @return handler
     */
    public SupplierOrderViewHandler getSupplierOrderViewHandler(Supplier supplier, YearWeek currentYearWeek, int weekCounter, boolean starting,
	    ProductAreaGroup productAreaGroup) {
	Map<Integer, SupplierOrderViewHandler> tmpViewHandlers = assemblyViewHandlers.get(supplier);

	if (tmpViewHandlers == null) {
	    tmpViewHandlers = new Hashtable<Integer, SupplierOrderViewHandler>();
	}
	SupplierOrderViewHandler supplierOrderViewHandler = tmpViewHandlers.get(weekCounter);

	if (supplierOrderViewHandler == null) {

	    supplierOrderViewHandler = supplierOrderViewHandlerFactory.create(supplier,
		    new YearWeek(currentYearWeek.getYear(), currentYearWeek.getWeek()));
	    supplierOrderViewHandler.addAssemblyChangeListener(assemblyChangeListener);

	    tmpViewHandlers.put(weekCounter, supplierOrderViewHandler);
	    assemblyViewHandlers.put(supplier, tmpViewHandlers);
	} else if (!starting) {
	    supplierOrderViewHandler.refresh(currentYearWeek);
	}
	supplierOrderViewHandler.setProductAreaGroup(productAreaGroup);
	return supplierOrderViewHandler;
    }

    /**
     * Henter view for monteringsteam
     * 
     * @param assemblyTeam
     * @param currentYearWeek
     * @param weekCounter
     * @param starting
     * @param productAreaGroup
     * @return view
     */
    public SupplierOrderView getAssemblyTeamOrderView(Supplier supplier, YearWeek currentYearWeek, int weekCounter, boolean starting,
	    ProductAreaGroup productAreaGroup) {
	SupplierOrderViewHandler supplierOrderViewHandler = getSupplierOrderViewHandler(supplier, currentYearWeek, weekCounter, starting,
		productAreaGroup);
	return new SupplierOrderView(supplierOrderViewHandler);
    }

    /**
     * Setter selektert ordre
     * 
     * @param assembly
     */
    private void setSelectedOrder(Assembly assembly) {
	Supplier supplier = assembly.getSupplier();

	Map<Integer, SupplierOrderViewHandler> tmpViewHandlers = assemblyViewHandlers.get(supplier);

	if (tmpViewHandlers != null) {
	    SupplierOrderViewHandler handler = tmpViewHandlers.get(2);
	    if (handler != null) {
		handler.setSelectedAssembly(assembly);
	    }
	}
    }

    /**
     * Henter vindustittel
     * 
     * @return vindustittel
     */
    public String getWindowTitle() {
	return "Montering";
    }

    /**
     * Henter lytter for høyreklikk
     * 
     * @return lytter
     */
    public MouseListener getRightClickListener() {
	return new RightClickListener();
    }

    /**
     * Håndterer høyreklikk
     * 
     * @author atle.brekka
     */
    final class RightClickListener extends MouseAdapter {
	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	    if (SwingUtilities.isRightMouseButton(e)) {
		popupMenuSetAssembly.show((JXTable) e.getSource(), e.getX(), e.getY());
	    }
	}
    }

    /**
     * Viser innhold i avvik
     * 
     * @param iAssembly
     * @param window
     */
    void showContent(IAssembly iAssembly, WindowInterface window) {
	Collection<OrderLine> orderLines = iAssembly.getAssemblyOrderLines();
	if (orderLines != null) {
	    List<OrderLineWrapper> missingList = Util.getOrderLineWrapperList(orderLines);
	    Util.showOptionsDialog(window, missingList, "Innhold", false, false);
	}
    }

    class MenuItemListener implements ActionListener {
	private boolean forOrder = true;

	private WindowInterface window;

	public MenuItemListener(boolean isForOrder, WindowInterface aWindow) {
	    window = aWindow;
	    forOrder = isForOrder;
	}

	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemSetAssembly.getText())) {

		setAssembly(forOrder, window);

	    } else if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemShowContent.getText())) {
		DeviationManager deviationManager = (DeviationManager) ModelUtil.getBean("deviationManager");
		Deviation deviation = (Deviation) deviationSelectionList.getElementAt(tableDeviation.convertRowIndexToModel(deviationSelectionList
			.getSelectionIndex()));
		deviationManager.lazyLoad(deviation, new LazyLoadDeviationEnum[] { LazyLoadDeviationEnum.ORDER_LINES });
		showContent(deviation, window);

	    }
	}

    }

    class MenuItemListenerAssembly implements ActionListener {

	private WindowInterface window;

	public MenuItemListenerAssembly(WindowInterface aWindow) {
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemSetSentBase.getText())) {
		Assembly assembly = getSelectedAssembly();
		String value = Util.showInputDialogWithdefaultValue(window, "Sendt underlag", "Sendt uderlag", assembly.getSentBase() == null ? ""
			: assembly.getSentBase());
		assembly.setSentBase(value);
		((AssemblyManager) ModelUtil.getBean(AssemblyManager.MANAGER_NAME)).saveAssembly(assembly);
		;

	    }
	}
    }

    /**
     * Setter montering
     * 
     * @param team
     * @param iassembly
     * @param window
     */
    private boolean setAssembly(Supplier supplier, IAssembly iassembly, WindowInterface window) {

	if (iassembly != null && supplier != null) {
	    if (yearWeek.getFormattetYearWeek().compareToIgnoreCase(Util.getCurrentYearWeekAsString()) < 0) {
		if (!Util.showConfirmDialog(currentWindow.getComponent(), "Gammel uke",
			"Du prøver å sette montering til en gammel uke, ønsker du å gjøre dette?")) {
		    return false;
		}
	    }
	    AssemblyManager assemblyManager = (AssemblyManager) ModelUtil.getBean("assemblyManager");
	    Assembly assembly = iassembly.getAssembly();

	    if (assembly == null) {
		assembly = new Assembly();

	    }
	    assembly.setSupplier(supplier);
	    assembly.setOrder(iassembly.getAssemblyOrder());
	    assembly.setAssemblyYear(yearWeek.getYear());
	    assembly.setAssemblyWeek(yearWeek.getWeek());
	    assembly.setDeviation(iassembly.getAssemblyDeviation());
	    assembly.setInactive(0);

	    if (assembly.getFirstPlanned() == null) {
		assembly.setFirstPlanned(assembly.toString());
	    }

	    assemblyManager.saveAssembly(assembly);
	    iassembly.setAssembly(assembly);

	    Map<Integer, SupplierOrderViewHandler> tmpViewHandlers = assemblyViewHandlers.get(supplier);

	    int currentWeekIndex = 2;
	    if (yearWeek.getWeek() == 1) {
		currentWeekIndex = 1;
	    }

	    if (tmpViewHandlers != null) {
		SupplierOrderViewHandler assemblyTeamOrderViewHandler = tmpViewHandlers.get(currentWeekIndex);

		if (assemblyTeamOrderViewHandler != null) {
		    ((AssemblyTeamTableModel) assemblyTeamOrderViewHandler.getTableModel(window)).insertRow(0, assembly);
		}

	    }
	}
	return true;
    }

    /**
     * Setter montering
     * 
     * @param forOrder
     * @param window
     */
    void setAssembly(boolean forOrder, WindowInterface window) {
	Supplier team = getSupplierTeam();

	if (forOrder) {
	    setAssemblyForOrder(window, team);
	} else {
	    setAssemblyForDeviation(window, team);
	}

    }

    private void setAssemblyForDeviation(WindowInterface window, Supplier team) {
	Deviation deviation = getSelectedDeviation();

	if (deviation != null && team != null) {
	    managerRepository.getDeviationManager().lazyLoad(deviation,
		    new LazyLoadDeviationEnum[] { LazyLoadDeviationEnum.COMMENTS, LazyLoadDeviationEnum.ORDER_LINES });
	    if (setAssembly(team, deviation, window)) {
		deviationList.remove(deviation);
	    }
	}
    }

    private Deviation getSelectedDeviation() {
	Deviation deviation = (Deviation) deviationSelectionList.getElementAt(tableDeviation.convertRowIndexToModel(deviationSelectionList
		.getSelectionIndex()));
	return deviation;
    }

    private Assembly getSelectedAssembly() {
	return (Assembly) assemblySelectionList.getElementAt(tableAssembly.convertRowIndexToModel(assemblySelectionList.getSelectionIndex()));
    }

    private void setAssemblyForOrder(WindowInterface window, Supplier team) {
	int index = orderViewHandler.getOrderPanelSelectedOrderIndex();
	Order order = getSelectedOrder(index);

	if (order != null && team != null && validTransport(order, window)) {
	    if (setAssembly(team, order, window)) {
		try {
		    orderViewHandler.getOrderManager().saveOrder(order);

		    orderViewHandler.getOrderManager().lazyLoadOrder(order,
			    new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS, LazyLoadOrderEnum.ORDER_LINES });
		    orderViewHandler.getOrderPanelTableModel().removeRow(index);
		} catch (ProTransException e) {
		    Util.showErrorDialog(window, "Feil", e.getMessage());
		    e.printStackTrace();
		}
	    }
	}
    }

    private Order getSelectedOrder(int index) {
	SelectionInList orderPanelSelectionList = orderViewHandler.getOrderPanelSelectionList();
	Order order = (Order) orderPanelSelectionList.getElementAt(index);
	orderViewHandler.getOrderManager().refreshObject(order);
	return order;
    }

    private Supplier getSupplierTeam() {
	Supplier team = (Supplier) JOptionPane.showInputDialog(currentWindow.getComponent(), "Velg team", "Monteringsteam",
		JOptionPane.OK_CANCEL_OPTION, null,
		getSuppliers(yearWeek, ((ProductAreaGroupModel) productAreaGroupModel.getBean()).getProductAreaGroup()).toArray(), null);
	return team;
    }

    private boolean validTransport(final Order order, final WindowInterface window) {
	boolean hasTransportAfter = order.hasTransportAfter(yearWeek);

	if (hasTransportAfter) {
	    Util.showErrorDialog(window, "Feil", "Kan ikke sette montering før transport");
	}
	return !hasTransportAfter;
    }

    /**
     * Utskrift
     * 
     * @author atle.brekka
     */
    private class PrintAction extends AbstractAction {
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
	public PrintAction(WindowInterface aWindow) {
	    super("Skriv ut...");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent arg0) {
	    Collection<Supplier> selectedTeams = (Collection<Supplier>) Util.showOptionsDialog(window,
		    getSuppliers(yearWeek, ((ProductAreaGroupModel) productAreaGroupModel.getBean()).getObject()), "Monteringslag", true, true);
	    if (selectedTeams != null && selectedTeams.size() != 0) {
		Util.runInThreadWheel(window.getRootPane(), new Printer(window, new ArrayListModel(selectedTeams)), null);
	    }

	}
    }

    /**
     * Utskrift
     * 
     * @author atle.brekka
     */
    private class Printer implements Threadable {
	private WindowInterface owner;

	private List<Supplier> teams;

	/**
	 * @param aOwner
	 * @param selectedTeams
	 */
	public Printer(WindowInterface aOwner, List<Supplier> selectedTeams) {
	    teams = selectedTeams;
	    owner = aOwner;
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
	    labelInfo.setText("Genererer utskrift...");
	    ReportViewer reportViewer = new ReportViewer(getWindowTitle());

	    try {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ASSEMBLY_WEEK", yearWeek.getWeek());
		reportViewer.generateProtransReportFromBeanAndShow(getAssemblyWeekReportList(yearWeek, teams), "Montering", ReportEnum.MONTERING,
			parameters, "Montering", owner, true);
	    } catch (ProTransException e) {
		e.printStackTrace();
	    }

	    return null;
	}

	/**
	 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
	 */
	public void enableComponents(boolean enable) {
	}

    }

    private List<AssemblyWeekReport> getAssemblyWeekReportList(final YearWeek yearWeek, final List<Supplier> teams) {
	SupplierManager supplierManager = (SupplierManager) ModelUtil.getBean(SupplierManager.MANAGER_NAME);
	List<AssemblyWeekReport> reportList = new ArrayList<AssemblyWeekReport>();
	for (Supplier supplier : teams) {
	    supplierManager.lazyLoad(supplier, new LazyLoadEnum[][] { { LazyLoadEnum.ASSEMBLIES, LazyLoadEnum.NONE } });
	    reportList.add(new AssemblyWeekReport(yearWeek, supplier));
	}
	return reportList;
    }

    /**
     * Initierer
     */
    public void init() {
	doRefresh(null);
    }

    /**
     * Håndterer endring av montering
     * 
     * @author atle.brekka
     */
    class AssemblyChangeListener implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent arg0) {
	    initOverdue();
	    initDeviationList();
	}

    }

    /**
     * Henter ut maks antall order for et team i en gitt periode
     * 
     * @param assemblyTeam
     * @param yearWeek1
     * @param starting
     * @param productAreaGroup
     * @return maks antall ordre
     */
    public int getMaxNumbersOfOrders(Supplier supplier, YearWeek yearWeek1, boolean starting, ProductAreaGroup productAreaGroup) {
	int weekStart = yearWeek1.getWeek() - 1;
	int weekStop = yearWeek1.getWeek() + 1;
	YearWeek currentYearWeek = new YearWeek(yearWeek1.getYear(), weekStart);
	int maxSize = 0;
	int numberOfOrders;
	SupplierOrderViewHandler supplierOrderViewHandler;
	int weekCounter = 0;
	for (int i = weekStart; i <= weekStop; i++) {
	    weekCounter++;
	    currentYearWeek.setWeek(i);
	    supplierOrderViewHandler = getSupplierOrderViewHandler(supplier, currentYearWeek, weekCounter, starting, productAreaGroup);
	    numberOfOrders = supplierOrderViewHandler.getNumberOfOrders();
	    if (numberOfOrders > maxSize) {
		maxSize = numberOfOrders;
	    }
	}
	return maxSize;
    }

    /**
     * Rydder opp
     */
    private void cleanUp() {

    }

    /**
     * Sjekker rettigheter
     * 
     * @return true dersom har rettighet
     */
    public boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(login.getUserType(), "Montering");
    }

    /**
     * Henter om dialog skl bruke dispose ved lukking
     * 
     * @return dispose ved lukking
     */
    public boolean getDisposeOnClose() {
	return disposeOnClose;
    }

    /**
     * Tabellmodell for avvik
     * 
     * @author atle.brekka
     */
    private static class DeviationTableModel extends AbstractTableAdapter {

	/**
		 *
		 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param listModel
	 */
	public DeviationTableModel(ListModel listModel) {
	    super(listModel, DeviationColumn.getColumnNames());
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
	    Deviation deviation = (Deviation) getRow(row);
	    String columnName = StringUtils.upperCase(getColumnName(column)).replaceAll(" ", "_");
	    return DeviationColumn.valueOf(columnName).getValue(deviation);
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
	    String columnName = StringUtils.upperCase(getColumnName(column)).replaceAll(" ", "_");
	    return DeviationColumn.valueOf(columnName).getColumnClass();
	}

	private enum DeviationColumn {
	    KUNDE("Kunde") {
		@Override
		public Class<?> getColumnClass() {
		    return Customer.class;
		}

		@Override
		public Object getValue(Deviation deviation) {
		    return deviation != null && deviation.getOrder() != null ? deviation.getOrder().getCustomer() : null;
		}
	    },
	    ORDRENR("Ordrenr") {
		@Override
		public Class<?> getColumnClass() {
		    return String.class;
		}

		@Override
		public Object getValue(Deviation deviation) {
		    return deviation.getOrderNr();
		}
	    },
	    ADRESSE("Adresse") {
		@Override
		public Class<?> getColumnClass() {
		    return String.class;
		}

		@Override
		public Object getValue(Deviation deviation) {
		    return deviation != null && deviation.getOrder() != null ? deviation.getOrder().getDeliveryAddress() : null;
		}
	    },
	    POSTNUMMER("Postnummer") {
		@Override
		public Class<?> getColumnClass() {
		    return String.class;
		}

		@Override
		public Object getValue(Deviation deviation) {
		    return deviation != null && deviation.getOrder() != null ? deviation.getOrder().getPostalCode() : null;
		}
	    },
	    POSTSTED("Poststed") {
		@Override
		public Class<?> getColumnClass() {
		    return String.class;
		}

		@Override
		public Object getValue(Deviation deviation) {
		    return deviation != null && deviation.getOrder() != null ? deviation.getOrder().getPostOffice() : null;
		}
	    },
	    PRODUKTOMRÅDE("Produktområde") {
		@Override
		public Class<?> getColumnClass() {
		    return ProductAreaGroup.class;
		}

		@Override
		public Object getValue(Deviation deviation) {
		    return deviation != null ? deviation.getProductAreaGroup() : null;
		}
	    };
	    private String columnName;
	    private static final List<String> COLUMN_NAMES = Lists.newArrayList();

	    static {
		for (DeviationColumn col : DeviationColumn.values()) {
		    COLUMN_NAMES.add(col.getColumnName());
		}
	    }

	    private DeviationColumn(String aColumnName) {
		columnName = aColumnName;
	    }

	    public String getColumnName() {
		return columnName;
	    }

	    public static String[] getColumnNames() {
		String[] colArray = new String[COLUMN_NAMES.size()];
		return COLUMN_NAMES.toArray(colArray);
	    }

	    public abstract Object getValue(Deviation deviation);

	    public abstract Class<?> getColumnClass();
	}

    }

    private static class AssemblyTableModel extends AbstractTableAdapter {

	/**
		 *
		 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param listModel
	 */
	public AssemblyTableModel(ListModel listModel) {
	    super(listModel, AssemblyColumn.getColumnNames());
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
	    Assembly assembly = (Assembly) getRow(row);
	    String columnName = StringUtils.upperCase(getColumnName(column)).replaceAll(" ", "_");
	    return AssemblyColumn.valueOf(columnName).getValue(assembly);
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
	    String columnName = StringUtils.upperCase(getColumnName(column)).replaceAll(" ", "_");
	    return AssemblyColumn.valueOf(columnName).getColumnClass();
	}

    }

    /**
     * Håndterer høyreklikk i tabell med avvik
     * 
     * @author atle.brekka
     */
    final class DeviationRightClickListener extends MouseAdapter {
	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	    if (SwingUtilities.isRightMouseButton(e)) {
		popupMenuSetAssemblyDeviation.show((JXTable) e.getSource(), e.getX(), e.getY());
	    }
	}
    }

    final class AssemblyRightClickListener extends MouseAdapter {
	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	    if (SwingUtilities.isRightMouseButton(e)) {
		popupMenuAssembly.show((JXTable) e.getSource(), e.getX(), e.getY());
	    }
	}
    }

    public enum AssemblyColumn {
	SENDT("Sendt", VISIBLE, NOT_CENTER, 50) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getSentBool();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Boolean.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getSentBool().compareTo(assembly2.getOrder().getSentBool());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createCheckBox(presentationModel.getModel("sent"), "");
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (assemblyFilter.getSent() != null) {
		    return assembly.getOrder().getSentBool().equals(assemblyFilter.getSent());
		}
		return true;
	    }
	},
	MONTERT("Montert", VISIBLE, NOT_CENTER, 50) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getAssembliedBool() ? "Ja" : "Nei";
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getAssembliedBool().compareTo(assembly2.getAssembliedBool());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createCheckBox(presentationModel.getModel("assemblied"), "");
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (assemblyFilter.getAssemblied() != null) {
		    return assembly.getAssembliedBool().equals(assemblyFilter.getAssemblied());
		}
		return true;
	    }
	},
	UKE("Uke", VISIBLE, CENTER, 50) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getAssemblyWeek();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getAssemblyWeek().compareTo(assembly2.getAssemblyWeek());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("assemblyWeek"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getAssemblyWeek())) {
		    return assembly.getAssemblyWeek() != null
			    && String.valueOf(assembly.getAssemblyWeek()).matches(
				    assemblyFilter.getAssemblyWeek().toLowerCase().replaceAll("%", ".*"));
		}
		return true;
	    }
	},
	MONTØR("Montør", VISIBLE, NOT_CENTER, 100) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getSupplier() == null ? null : assembly.getSupplier().getSupplierName();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getAssemblyteamName().compareTo(assembly2.getAssemblyteamName());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("assemblyteam"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getAssemblyteam())) {
		    return assembly.getAssemblyteamName().toLowerCase()
			    .matches(assemblyFilter.getAssemblyteam().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	PLANLAGT("Planlagt", VISIBLE, NOT_CENTER, 60) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return hentPlanlagt(assembly);
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return hentPlanlagt(assembly1).compareTo(hentPlanlagt(assembly2));
	    }

	    private String hentPlanlagt(Assembly assembly) {
		return assembly.getPlanned() == null ? "" : assembly.getPlanned();
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("planned"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getPlanned())) {
		    return assembly.getPlanned() != null
			    && assembly.getPlanned().toLowerCase().matches(assemblyFilter.getPlanned().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	PAKKLISTE_KLAR("Pakkliste klar", VISIBLE, NOT_CENTER, 80) {
	    @Override
	    public Object getValue(Assembly assembly) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return assembly.getOrder().getPacklistReady() == null ? null : simpleDateFormat.format(assembly.getOrder().getPacklistReady());
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getPacklistReady() == null && assembly2.getOrder().getPacklistReady() == null ? 0 : assembly1.getOrder()
			.getPacklistReady() == null ? -1 : assembly2.getOrder().getPacklistReady() == null ? 1 : assembly1.getOrder()
			.getPacklistReady().compareTo(assembly2.getOrder().getPacklistReady());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("packlist"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getPacklist())) {
		    return assembly.getOrder().getPacklistReady() != null
			    && simpleDateFormat.format(assembly.getOrder().getPacklistReady()).matches(
				    assemblyFilter.getPacklist().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	SENDT_UNDERLAG("Sendt underlag", VISIBLE, CENTER, 80) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getSentBase();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getSentBase() == null && assembly2.getSentBase() == null ? 0 : assembly1.getSentBase() == null ? -1 : assembly2
			.getSentBase() == null ? 1 : assembly1.getSentBase().compareTo(assembly2.getSentBase());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("sentBase"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getSentBase())) {
		    return assembly.getSentBase() != null
			    && assembly.getSentBase().toLowerCase().matches(assemblyFilter.getSentBase().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	ORDRENR("Ordrenr", VISIBLE, CENTER, 60) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getOrderNr();
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getOrderNr().compareTo(assembly2.getOrder().getOrderNr());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("orderNr"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getOrderNr())) {
		    return assembly.getOrder().getOrderNr().toLowerCase()
			    .matches(assemblyFilter.getOrderNr().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},

	FORNAVN("Fornavn", VISIBLE, NOT_CENTER, 100) {
	    @Override
	    public Class<?> getColumnClass() {
		return Customer.class;
	    }

	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly != null && assembly.getOrder() != null ? assembly.getOrder().getCustomer().getFirstName() : null;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getCustomer().getFirstName().compareTo(assembly2.getOrder().getCustomer().getFirstName());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("firstname"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getFirstname())) {
		    return assembly.getOrder().getCustomer().getFirstName().toLowerCase()
			    .matches(assemblyFilter.getFirstname().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	ETTERNAVN("Etternavn", VISIBLE, NOT_CENTER, 100) {
	    @Override
	    public Class<?> getColumnClass() {
		return Customer.class;
	    }

	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly != null && assembly.getOrder() != null ? assembly.getOrder().getCustomer().getLastName() : null;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getCustomer().getLastName().compareTo(assembly2.getOrder().getCustomer().getLastName());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("lastname"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getLastname())) {
		    return assembly.getOrder().getCustomer().getLastName().toLowerCase()
			    .matches(assemblyFilter.getLastname().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	POSTNR("Postnr", VISIBLE, CENTER, 50) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getPostalCode();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getPostalCode().compareTo(assembly2.getOrder().getPostalCode());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("postalCode"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getPostalCode())) {
		    return assembly.getOrder().getPostalCode().toLowerCase()
			    .matches(assemblyFilter.getPostalCode().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	STED("Sted", VISIBLE, NOT_CENTER, 90) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getPostOffice();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getPostOffice().compareTo(assembly2.getOrder().getPostOffice());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("postOffice"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getPostOffice())) {
		    return assembly.getOrder().getPostOffice().toLowerCase()
			    .matches(assemblyFilter.getPostOffice().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	TELEFON("Telefon", VISIBLE, NOT_CENTER, 70) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getTelephoneNrFormatted();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getTelephoneNrFormatted().compareTo(assembly2.getOrder().getTelephoneNrFormatted());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("telephone"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getTelephone())) {
		    return assembly.getOrder().getTelephoneNrFormatted() != null
			    && assembly.getOrder().getTelephoneNrFormatted().toLowerCase()
				    .matches(assemblyFilter.getTelephone().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	KOMMENTAR("Kommentar", VISIBLE, NOT_CENTER, 100) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return null;
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return null;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		throw new NotImplementedException();
		// return 0;
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("comment"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		return true;
	    }
	},
	AVDELING("Avdeling", VISIBLE, CENTER, 80) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getProductArea();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return ProductArea.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getProductArea().getProductArea().compareTo(assembly2.getOrder().getProductArea().getProductArea());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("productArea"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getProductArea())) {
		    return assembly.getOrder().getProductArea().getProductArea() != null
			    && assembly.getOrder().getProductArea().getProductArea().toLowerCase()
				    .matches(assemblyFilter.getProductArea().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	PROD_UKE("Prod uke", VISIBLE, CENTER, 60) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getProductionWeek();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getProductionWeek() == null && assembly2.getOrder().getProductionWeek() == null ? 0 : assembly1
			.getOrder().getProductionWeek() == null ? -1 : assembly2.getOrder().getProductionWeek() == null ? 1 : assembly1.getOrder()
			.getProductionWeek().compareTo(assembly2.getOrder().getProductionWeek());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("productionWeek"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getProductionWeek())) {
		    return assembly.getOrder().getProductionWeek() != null
			    && String.valueOf(assembly.getOrder().getProductionWeek()).toLowerCase()
				    .matches(assemblyFilter.getProductionWeek().toLowerCase().replaceAll("%", ".*"));
		}
		return true;
	    }
	},
	TRANSPORT("Transport", VISIBLE, NOT_CENTER, 110) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getTransportDetails();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getTransportDetails().compareTo(assembly2.getOrder().getTransportDetails());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("transport"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getTransport())) {
		    return assembly.getOrder().getTransportDetails() != null
			    && assembly.getOrder().getTransportDetails().toLowerCase()
				    .matches(assemblyFilter.getTransport().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	TAKSTEIN("Takstein", VISIBLE, NOT_CENTER, 100) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return hentTaksteininfo(assembly);
	    }

	    private String hentTaksteininfo(Assembly assembly) {
		OrderLine takstein = assembly.getOrder().getOrderLine("Takstein");
		return takstein == null ? "" : takstein.getAttributeInfoWithoutNo();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return hentTaksteininfo(assembly1).compareTo(hentTaksteininfo(assembly2));
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("takstein"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getTakstein())) {
		    return hentTaksteininfo(assembly).toLowerCase().matches(assemblyFilter.getTakstein().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	MONTERINGSUM("Monteringsum", VISIBLE, CENTER, 70) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getCost("Montering", "Kunde");
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getCost("Montering", "Kunde").compareTo(assembly2.getOrder().getCost("Montering", "Kunde"));
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("assemblyCost"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getAssemblyCost())) {
		    return assembly.getOrder().getCost("Montering", "Kunde") != null
			    && String.valueOf(assembly.getOrder().getCost("Montering", "Kunde")).toLowerCase()
				    .matches(assemblyFilter.getAssemblyCost().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},

	OPPRINNELIG("Opprinnelig", VISIBLE, CENTER, 60) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getFirstPlanned();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getFirstPlanned().compareTo(assembly2.getFirstPlanned());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("firstPlanned"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getFirstPlanned())) {
		    return assembly.getFirstPlanned() != null
			    && assembly.getFirstPlanned().toLowerCase()
				    .matches(assemblyFilter.getFirstPlanned().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	KRANING("Kraning", VISIBLE, CENTER, 60) {
	    @Override
	    public Object getValue(Assembly assembly) {
		return assembly.getOrder().getCraningCost();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return assembly1.getOrder().getCraningCost().compareTo(assembly2.getOrder().getCraningCost());
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel.getModel("craningCost"), false);
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		if (StringUtils.isNotBlank(assemblyFilter.getCraningCost())) {
		    return assembly.getOrder().getCraningCost() != null
			    && String.valueOf(assembly.getOrder().getCraningCost()).toLowerCase()
				    .matches(assemblyFilter.getCraningCost().toLowerCase().replaceAll("%", ".*") + ".*");
		}
		return true;
	    }
	},
	OVERTID("Overtid", INVISIBLE, CENTER, 50) {
	    @Override
	    public Object getValue(Assembly assembly) {
		if (assembly.getAssembliedDate() == null) {
		    Integer yearWeek = Integer.valueOf(String.valueOf(Util.getCurrentYear()) + String.format("%02d", Util.getCurrentWeek()));
		    Integer assemblyYearWeek = Integer.valueOf(String.valueOf(assembly.getAssemblyYear())
			    + String.format("%02d", assembly.getAssemblyWeek()));

		    if (assemblyYearWeek < yearWeek) {
			return 1;
		    }
		}
		return 0;
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return 0;
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return null;
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		return true;
	    }
	},
	SENDT_MANGLER("Sendt mangler", INVISIBLE, CENTER, 50) {
	    @Override
	    public Object getValue(Assembly assembly) {
		Order order = assembly.getOrder();
		if (order != null && order.getSent() != null) {
		    return order.getHasMissingCollies();
		}
		return 0;
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }

	    @Override
	    public int sort(Assembly assembly1, Assembly assembly2) {
		return 0;
	    }

	    @Override
	    public Component getFilterComponent(PresentationModel presentationModel) {
		return null;
	    }

	    @Override
	    public boolean filter(Assembly assembly, AssemblyFilter assemblyFilter) {
		return true;
	    }
	};
	private String columnName;
	private boolean visible;
	private boolean centerAlignment;
	private int width;
	private static final List<String> COLUMN_NAMES = Lists.newArrayList();

	static {
	    for (AssemblyColumn col : AssemblyColumn.values()) {
		COLUMN_NAMES.add(col.getColumnName());
	    }
	}

	private AssemblyColumn(String aColumnName, boolean visible, boolean centerAlignment, int width) {
	    columnName = aColumnName;
	    this.visible = visible;
	    this.centerAlignment = centerAlignment;
	    this.width = width;
	}

	public int getWidth() {
	    return width;
	}

	public boolean hasCenterAlignment() {
	    return centerAlignment;
	}

	public String getColumnName() {
	    return columnName;
	}

	public static String[] getColumnNames() {
	    String[] colArray = new String[COLUMN_NAMES.size()];
	    return COLUMN_NAMES.toArray(colArray);
	}

	public static List<AssemblyColumn> getVisibleColumns() {
	    List<AssemblyColumn> columnNames = Lists.newArrayList();
	    for (AssemblyColumn assemblyColumn : AssemblyColumn.values()) {
		if (assemblyColumn.isVisible()) {
		    columnNames.add(assemblyColumn);
		}
	    }
	    return columnNames;
	}

	private boolean isVisible() {
	    return visible;
	}

	public abstract Object getValue(Assembly assembly);

	public abstract Class<?> getColumnClass();

	@Override
	public String toString() {
	    return columnName;
	}

	public abstract int sort(Assembly assembly1, Assembly assembly2);

	public abstract Component getFilterComponent(PresentationModel presentationModel);

	public abstract boolean filter(Assembly assembly, AssemblyFilter assemblyFilter);
    }

    private class SupplierComparator implements Comparator<Supplier> {

	public int compare(Supplier supplier1, Supplier supplier2) {
	    return new CompareToBuilder().append(supplier1.getPostalCode(), supplier2.getPostalCode()).toComparison();
	}

    }

    public JComboBox getComboBoxProductAreaGroup() {
	return Util.getComboBoxProductAreaGroup(login.getApplicationUser(), login.getUserType(), productAreaGroupModel);
    }

    private class ProductAreaChangeListener implements PropertyChangeListener {

	public void propertyChange(PropertyChangeEvent evt) {
	    changeProductAreaGroup();
	    handleFilter();

	}

    }

    private final void changeProductAreaGroup() {
	if (view != null) {
	    view.setProductAreaGroup((ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
	    view.changeWeek(null);
	}
    }

    public void handleFilter() {
	// gi beskjed til alle transportlister at de skal filtrere
	// if (viewHandler != null) {
	ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
	// PrefsUtil.setInvisibleColumns(group.getProductAreaGroupName(),tableDeviation.getName(),
	// tableDeviation);
	// transportWeekViewHandler.setFilterSent(!checkBoxFilter.isSelected(),
	// group);
	orderViewHandler.handleFilter(group, OrderPanelTypeEnum.ASSEMBLY_ORDERS);

	if (group != null && !group.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
	    Filter[] filters = new Filter[] { new PatternFilter(group.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE, 5) };
	    FilterPipeline filterPipeline = new FilterPipeline(filters);
	    tableDeviation.setFilters(filterPipeline);
	} else {
	    tableDeviation.setFilters(null);
	}
	tableDeviation.repaint();
	// setTransportSum();
	// }

    }

    public JCheckBox getCheckBoxListView(WindowInterface window) {
	checkBoxListView = new JCheckBox(new ListAction(window));
	checkBoxListView.setName("CheckBoxListView");
	return checkBoxListView;
    }

    private class ListAction extends AbstractAction {
	private WindowInterface windowInterface;

	public ListAction(WindowInterface window) {
	    super("Liste");
	    this.windowInterface = window;
	}

	public void actionPerformed(ActionEvent e) {
	    doRefresh(windowInterface);
	}
    }

    public void doFilter(AssemblyFilter assemblyFilter) {
	assemblyArrayListModel.clear();
	List<Assembly> filteredAssemblies = Lists.newArrayList(Iterables.filter(assemblies, filtrer(assemblyFilter)));
	Collections.sort(filteredAssemblies, sorter(assemblyFilter));
	assemblyArrayListModel.addAll(filteredAssemblies);

    }

    private Comparator<Assembly> sorter(final AssemblyFilter assemblyFilter) {
	return new Comparator<Assembly>() {

	    public int compare(Assembly assembly1, Assembly assembly2) {
		return assemblyFilter.sort(assembly1, assembly2);
	    }
	};
    }

    private Predicate<Assembly> filtrer(final AssemblyFilter assemblyFilter) {
	return new Predicate<Assembly>() {

	    public boolean apply(Assembly assembly) {

		return assemblyFilter.filter(assembly);
	    }
	};
    }
}
