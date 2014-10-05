package no.ugland.utransprod.gui.handlers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableCellRenderer;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.ArticleTypeView;
import no.ugland.utransprod.gui.CloseListener;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.ColliListView;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.JFrameAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MainPackageView;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.UpdateAttributeView;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.RefreshButton;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.edit.EditPackInitialsView;
import no.ugland.utransprod.gui.model.AbstractOrderModel;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.gui.model.ColliListener;
import no.ugland.utransprod.gui.model.ColliModel;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.GulvsponPackageApplyList;
import no.ugland.utransprod.gui.model.ICostableModel;
import no.ugland.utransprod.gui.model.ListMultilineRenderer;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.gui.model.PostShipmentModel;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.gui.model.SumOrderReadyVModel;
import no.ugland.utransprod.gui.model.TextPaneRendererOrder;
import no.ugland.utransprod.gui.model.TextPaneStringRenderer;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ArticleTypeArticleType;
import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.MainPackageV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Packagetype;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.SumOrderReadyV;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.GulvsponPackageVManager;
import no.ugland.utransprod.service.MainPackageVManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Hjelpeklasse for visning av vindu for pakking av garasjepakke
 * 
 * @author atle.brekka
 */
public class MainPackageViewHandler implements Closeable, Updateable, ListDataListener, ColliListener {
    private final ArrayListModel mainPackageVList;
    private JComboBox comboBoxPakketype;

    private final ArrayListModel postShipmentList;

    final SelectionInList orderSelectionList;

    final SelectionInList postShipmentSelectionList;

    private SelectionInList orderLineSelectionList;

    private Multimap<String, String> colliSetup;

    private List<PropertyChangeListener> bufferingListeners = new ArrayList<PropertyChangeListener>();

    private List<ListDataListener> colliListListeners = new ArrayList<ListDataListener>();

    private MainPackageView mainPackageView;

    private boolean refreshing = false;

    private JXTable tableOrders;

    private JXTable tablePostShipment;

    private JCheckBox checkBoxShowPackaged;

    private List<CloseListener> closeListeners = new ArrayList<CloseListener>();

    private PackageOrderTableModel packageOrderTableModel;

    private PackagePostShipmentTableModel packagePostShipmentTableModel;

    private JXTable tableOrderLines;

    private Map<String, StatusCheckerInterface<OrderLine>> statusChekers;

    private JButton buttonRemoveArticle;

    private PackageOrderLineTableModel tableModelOrderLines;

    private PresentationModel presentationModelPackable;

    private PresentationModel presentationModelSum;

    private PresentationModel presentationModelWeekSum;

    private PresentationModel presentationModelBudget;

    private int currentWeek = -1;

    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    private JButton buttonAddArticle;

    private OrderViewHandler orderViewHandler;

    private boolean disposeOnClose = true;

    private ArrayListModel ownOrderLineList;

    private JPopupMenu popupMenuPostShipment;

    private JMenuItem menuItemPacklist;

    final ArrayListModel orderComments;

    private JButton buttonAddComment;

    private JCheckBox checkBoxReadyVegg;
    private JCheckBox checkBoxReadyTakstol;
    private JCheckBox checkBoxReadyPakk;

    private JPopupMenu popupMenuOrder;

    private JMenuItem menuItemDeviation;

    private List<ProductAreaGroup> productAreaGroupList;

    private PresentationModel productAreaGroupModel;
    private VismaFileCreator vismaFileCreator;

    private ManagerRepository managerRepository;
    private Login login;
    private DeviationViewHandlerFactory deviationViewHandlerFactory;

    private JPopupMenu popupMenuOrderLine;
    private JMenuItem menuItemPack;
    private ColliListViewHandler colliListViewHandler;

    /**
     * @param aColliSetup
     * @param chekers
     * @param aUserType
     * @param aOrderViewHandler
     * @param aApplicationUser
     */
    @Inject
    public MainPackageViewHandler(VismaFileCreator aVismaFileCreator, OrderViewHandlerFactory orderViewHandlerFactory, Login aLogin,
	    ManagerRepository aManagerRepository, DeviationViewHandlerFactory aDeviationViewHandlerFactory,
	    @Assisted Multimap<String, String> aColliSetup, @Assisted Map<String, StatusCheckerInterface<OrderLine>> chekers) {
	deviationViewHandlerFactory = aDeviationViewHandlerFactory;
	login = aLogin;
	managerRepository = aManagerRepository;
	vismaFileCreator = aVismaFileCreator;
	orderViewHandler = orderViewHandlerFactory.create(false);
	orderComments = new ArrayListModel();

	this.colliSetup = aColliSetup;

	statusChekers = chekers;

	mainPackageVList = new ArrayListModel();
	orderSelectionList = new SelectionInList((ListModel) mainPackageVList);
	ownOrderLineList = new ArrayListModel();
	orderLineSelectionList = new SelectionInList((ListModel) ownOrderLineList);

	postShipmentList = new ArrayListModel();
	postShipmentSelectionList = new SelectionInList();

	presentationModelPackable = new PresentationModel(new OrderModel(new Order(), false, false, false, null, null));
	presentationModelSum = new PresentationModel(new SumOrderReadyVModel(new SumOrderReadyV(null, BigDecimal.valueOf(0), null, null, null)));
	presentationModelWeekSum = new PresentationModel(new SumOrderReadyVModel(new SumOrderReadyV(null, BigDecimal.valueOf(0), null, null, null)));
	presentationModelBudget = new PresentationModel(new ProductionBudgetModel(new Budget(null, null, null, BigDecimal.valueOf(0), null, null)));
	initProductAreaGroup();
	colliListViewHandler = new ColliListViewHandler(login, managerRepository, colliSetup);
	colliListViewHandler.addListDataListener(this);
	colliListViewHandler.addColliListener(this);

	init();

	popupMenuPostShipment = new JPopupMenu("Etterlevering");
	menuItemPacklist = new JMenuItem("Pakkliste...");
	popupMenuPostShipment.add(menuItemPacklist);

	popupMenuOrder = new JPopupMenu();
	menuItemDeviation = new JMenuItem("Registrer avvik...");
	popupMenuOrder.add(menuItemDeviation);

	popupMenuOrderLine = new JPopupMenu();
	menuItemPack = new JMenuItem("Pakk...");
	popupMenuOrderLine.add(menuItemPack);

    }

    /**
     * Initierer liste med produktområdegrupper
     */
    private void initProductAreaGroup() {
	productAreaGroupModel = new PresentationModel(new ProductAreaGroupModel(ProductAreaGroup.UNKNOWN));
	productAreaGroupModel.addBeanPropertyChangeListener(new FilterPropertyChangeListener());
	ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil.getBean("productAreaGroupManager");
	productAreaGroupList = new ArrayList<ProductAreaGroup>();
	List<ProductAreaGroup> groups = productAreaGroupManager.findAll();
	if (groups != null) {
	    productAreaGroupList.addAll(groups);
	}
    }

    /**
     * Initierer klasse
     */
    private void init() {
	mainPackageVList.clear();
	Calendar cal = Calendar.getInstance();
	currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
	List<MainPackageV> orders = new ArrayList<MainPackageV>(managerRepository.getMainPackageVManager().findAll());
	Collections.sort(orders, new MainPackageVComparator());
	OrderModel orderModel = null;
	if (orders.size() != 0) {
	    mainPackageVList.addAll(orders);
	}
	orderModel = new OrderModel(new Order(), false, false, true, null, null);
	presentationModelPackable = new PresentationModel(orderModel);
	// colliListViewHandler.setPresentationModel(presentationModelPackable);
	orderLineSelectionList.addPropertyChangeListener(new OrderLineSelectionListener());

	SumOrderReadyV sum = managerRepository.getSumOrderReadyVManager().findByDate(Calendar.getInstance().getTime());
	if (sum == null) {
	    sum = new SumOrderReadyV(null, BigDecimal.valueOf(0), null, null, null);
	}
	presentationModelSum = new PresentationModel(new SumOrderReadyVModel(sum));

	SumOrderReadyV sumWeek = managerRepository.getSumOrderReadyVManager().findSumByWeek(currentYear, currentWeek);
	if (sumWeek == null) {
	    sumWeek = new SumOrderReadyV(null, BigDecimal.valueOf(0), null, null, null);
	}
	presentationModelWeekSum = new PresentationModel(new SumOrderReadyVModel(sumWeek));

	Budget productionBudget = null;
	ProductAreaGroup productAreaGroup = (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
	productionBudget = managerRepository.getBudgetManager().findByYearAndWeekPrProductAreaGroup(currentYear, currentWeek, productAreaGroup,
		BudgetType.PRODUCTION);

	if (productionBudget == null) {
	    productionBudget = new Budget(null, null, null, BigDecimal.valueOf(0), null, null);
	}

	presentationModelBudget = new PresentationModel(new ProductionBudgetModel(productionBudget));

	postShipmentList.clear();
	List<PostShipment> postShipments = managerRepository.getPostShipmentManager().findAllNotSent();

	if (postShipments != null) {
	    for (PostShipment postShipment : postShipments) {
		managerRepository.getOrderManager().lazyLoadOrder(postShipment.getOrder(), new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });
	    }
	    Collections.sort(postShipments, new PostShipmentComparator());
	    postShipmentList.addAll(postShipments);
	}

	postShipmentSelectionList.setListModel(postShipmentList);
	orderSelectionList.clearSelection();

    }

    /**
     * Henter tabell for ordre
     * 
     * @param window
     * @return tabell
     */
    public JXTable getTableOrders(WindowInterface window) {
	menuItemDeviation.addActionListener(new MenuItemListenerDeviation(window));
	tableOrders = new JXTable();
	packageOrderTableModel = new PackageOrderTableModel(orderSelectionList);
	tableOrders.setModel(packageOrderTableModel);
	tableOrders.setSelectionModel(new SingleListSelectionAdapter(orderSelectionList.getSelectionIndexHolder()));
	tableOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tableOrders.setColumnControlVisible(true);
	tableOrders.getColumnExt(2).setVisible(false);
	tableOrders.getColumnExt(2).setVisible(false);
	tableOrders.getColumnExt(3).setVisible(false);
	tableOrders.getColumnExt(3).setVisible(false);
	ColorHighlighter pattern = new ColorHighlighter(new PatternPredicate("1", 2), Color.WHITE, Color.LIGHT_GRAY);
	ColorHighlighter readyPattern = new ColorHighlighter(new PatternPredicate("1", 3), Color.WHITE, ColorEnum.GREEN.getColor());
	ColorHighlighter probabilityPattern = new ColorHighlighter(new PatternPredicate("90", 6), ColorEnum.GREY.getColor(), Color.LIGHT_GRAY);

	tableOrders.addHighlighter(readyPattern);
	tableOrders.addHighlighter(pattern);
	tableOrders.addHighlighter(probabilityPattern);
	tableOrders.setRowHeight(40);
	tableOrders.getColumnModel().getColumn(0).setCellRenderer(new TextPaneRendererOrder());
	tableOrders.getColumnExt(0).setPreferredWidth(200);
	tableOrders.getColumnExt(1).setPreferredWidth(150);
	DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
	tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	tableOrders.getColumnExt(3).setCellRenderer(tableCellRenderer);
	tableOrders.setSortable(false);
	tableOrders.clearSelection();
	orderSelectionList.clearSelection();
	tableOrders.setName("TableOrders");
	tableOrders.addMouseListener(new RightClickListenerOrder());

	return tableOrders;
    }

    /**
     * Lager tabell for etterleveringer
     * 
     * @param window
     * @return tabell
     */
    public JXTable getTablePostShipment(WindowInterface window) {
	tablePostShipment = new JXTable();
	packagePostShipmentTableModel = new PackagePostShipmentTableModel(postShipmentSelectionList);
	tablePostShipment.setModel(packagePostShipmentTableModel);
	tablePostShipment.setSelectionModel(new SingleListSelectionAdapter(postShipmentSelectionList.getSelectionIndexHolder()));
	tablePostShipment.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tablePostShipment.setColumnControlVisible(true);
	tablePostShipment.getColumnExt(2).setVisible(false);
	tablePostShipment.getColumnExt(2).setVisible(false);
	ColorHighlighter pattern = new ColorHighlighter(new PatternPredicate("1", 2), Color.WHITE, Color.LIGHT_GRAY);

	tablePostShipment.addMouseListener(new TableClickHandler());
	menuItemPacklist.addActionListener(new MenuItemListenerPacklist(window));

	tablePostShipment.addHighlighter(pattern);
	tablePostShipment.setRowHeight(40);
	tablePostShipment.getColumnModel().getColumn(0).setCellRenderer(new TextPaneRendererOrder());
	tablePostShipment.getColumnExt(0).setPreferredWidth(100);
	tablePostShipment.getColumnExt(1).setPreferredWidth(150);
	tablePostShipment.setName("TablePostShipment");

	return tablePostShipment;
    }

    /**
     * Henter tabell for ordrelinjer
     * 
     * @param window
     * @return tabell
     */
    public JXTable getTableOrderLines(WindowInterface window) {
	menuItemPack.addActionListener(new MenuItemListenerPack(window, colliListViewHandler));

	OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");

	tableOrderLines = new JXTable();
	setTableOrderLinesFilters();

	tableModelOrderLines = new PackageOrderLineTableModel(orderLineSelectionList, orderLineManager, window);
	tableOrderLines.setModel(tableModelOrderLines);
	// tableOrderLines.setSelectionModel(new
	// SingleListSelectionAdapter(orderLineSelectionList.getSelectionIndexHolder()));
	tableOrderLines.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tableOrderLines.setColumnControlVisible(true);

	tableOrderLines.setRowHeight(40);

	tableOrderLines.getColumnExt(3).setCellRenderer(new TextPaneStringRenderer());

	tableOrderLines.getColumnExt(4).setVisible(false);
	tableOrderLines.getColumnExt(4).setVisible(false);
	tableOrderLines.getColumnExt(4).setVisible(false);
	tableOrderLines.getColumnExt(5).setVisible(false);

	tableOrderLines.getColumnExt(0).setPreferredWidth(80);
	tableOrderLines.getColumnExt(1).setPreferredWidth(30);
	tableOrderLines.getColumnExt(2).setPreferredWidth(30);
	tableOrderLines.getColumnExt(3).setPreferredWidth(240);

	tableOrderLines.addMouseListener(new OrderLineDoubleClickHandler(window, colliListViewHandler));

	tableOrderLines.setName("TableOrderLines");
	ColorHighlighter patternStart = new ColorHighlighter(new PatternPredicate("[^---]", 7), ColorEnum.YELLOW.getColor(), null);

	tableOrderLines.addHighlighter(patternStart);
	return tableOrderLines;
    }

    private void setTableOrderLinesFilters() {
	Packagetype pakketype = (Packagetype) comboBoxPakketype.getSelectedItem();
	List<PatternFilter> filters = Lists.newArrayList(new PatternFilter("Ja", Pattern.CASE_INSENSITIVE, 5), new PatternFilter("---",
		Pattern.CASE_INSENSITIVE, 4), new PatternFilter("Ja", Pattern.CASE_INSENSITIVE, 6));
	if (!Packagetype.ALLE.equals(pakketype)) {
	    filters.add(new PatternFilter(String.valueOf(pakketype.getVerdi()), Pattern.CASE_INSENSITIVE, 8));
	}
	Filter[] tableFilters = new Filter[filters.size()];
	filters.toArray(tableFilters);
	FilterPipeline filterPipeline1 = new FilterPipeline(tableFilters);
	if (tableOrderLines != null) {
	    tableOrderLines.setFilters(filterPipeline1);
	}
    }

    /**
     * Henter liste med kollier
     * 
     * @return kollier
     */
    @SuppressWarnings("unchecked")
    public List<Colli> getColliList() {
	return (List<Colli>) presentationModelPackable.getValue(AbstractOrderModel.PROPERTY_COLLI_LIST);
    }

    /**
     * Henter knapp for lukking av vindu
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonCancel(WindowInterface window) {
	return new CancelButton(window, this, disposeOnClose);
    }

    /**
     * Henter vindustittel
     * 
     * @return vindustittel
     */
    public String getWindowTitle() {
	return "Pakking av kollier";
    }

    /**
     * Henter størrelse på vindu
     * 
     * @return størrelse
     */
    public Dimension getWindowSize() {
	return new Dimension(1300, 700);
    }

    /**
     * Tabellmodell for order
     * 
     * @author atle.brekka
     */
    private class PackageOrderTableModel extends AbstractTableAdapter {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
	 * @param listModel
	 */
	public PackageOrderTableModel(ListModel listModel) {
	    super(listModel, new String[] { "Ordre", "Transport", "Pakket", "Klar", "Opplasting", "Produktområde", "Sannsynlighet", "Prod.uke" });
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    MainPackageV mainPackageV = (MainPackageV) getRow(rowIndex);
	    switch (columnIndex) {
	    case 0:
		return mainPackageV;
	    case 1:
		return mainPackageV.getTransportDetails();
	    case 2:
		if (mainPackageV.getDonePackage() != null) {
		    return 1;
		}
		return 0;
	    case 3:
		return mainPackageV.getPackageReady();
	    case 4:
		return mainPackageV.getLoadingDate();
	    case 5:
		return mainPackageV.getProductAreaGroupName();
	    case 6:
		return mainPackageV.getProbability();
	    case 7:
		return mainPackageV.getProductionWeek();
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		return MainPackageV.class;
	    case 1:
	    case 5:
		return String.class;
	    case 2:
	    case 3:
	    case 6:
	    case 7:
		return Integer.class;
	    case 4:
		return Date.class;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

    }

    /**
     * Tabellmodell for etterleveringer
     * 
     * @author atle.brekka
     */
    private class PackagePostShipmentTableModel extends AbstractTableAdapter {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
	 * @param listModel
	 */
	public PackagePostShipmentTableModel(ListModel listModel) {
	    super(listModel, new String[] { "Ordre", "Transport", "Pakket", "Produktområde", "Opplastning" });
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
	    PostShipment postShipment = (PostShipment) getRow(row);
	    switch (column) {
	    case 0:
		return postShipment;
	    case 1:
		return postShipment.getTransport();
	    case 2:
		if (postShipment.getPostShipmentComplete() != null) {
		    return 1;
		}
		return 0;
	    case 3:
		return postShipment.getProductAreaGroup().getProductAreaGroupName();
	    case 4:
		if (postShipment.getTransport() != null) {
		    return postShipment.getTransport().getLoadingDate();
		}
		return null;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
	    switch (column) {
	    case 0:
		return PostShipment.class;
	    case 1:
		return Transport.class;
	    case 2:
		return Integer.class;
	    case 3:
		return String.class;
	    case 4:
		return Date.class;

	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

    }

    /**
     * Henter knapp for å legge til artikkel
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonAddArticle(WindowInterface window) {
	buttonAddArticle = new JButton(new AddArticleAction(window));
	buttonAddArticle.setEnabled(false);
	buttonAddArticle.setName("ButtonAddArticle");
	return buttonAddArticle;
    }

    /**
     * Henter knapp for å fjerne artikkel
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonRemoveArticle(WindowInterface window) {
	buttonRemoveArticle = new JButton(new RemoveArticleAction(window));
	buttonRemoveArticle.setEnabled(false);
	return buttonRemoveArticle;
    }

    /**
     * Initierer hendelsehåndtering
     * 
     * @param aMainPackageView
     * @param window
     */
    public void initEventHandling(MainPackageView aMainPackageView, WindowInterface window) {
	mainPackageView = aMainPackageView;
	orderSelectionList.addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION_INDEX, new OrderSelectionListener(window));
	postShipmentSelectionList.addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION_INDEX, new PostShipmentSelectionListener(window));
    }

    /**
     * Sjekker status for ordrelinje
     * 
     * @param orderLine
     * @return status
     */
    String checkStatusOrderLine(OrderLine orderLine) {
	String returnValue = "---";

	StatusCheckerInterface<OrderLine> checker = statusChekers.get(orderLine.getArticleName());
	if (checker != null) {
	    returnValue = checker.getArticleStatus(orderLine);
	}

	return returnValue;
    }

    @SuppressWarnings("unchecked")
    private AbstractOrderModel lazyLoadPackable() {
	AbstractOrderModel abstractOrderModel = (AbstractOrderModel) presentationModelPackable.getBean();
	OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(abstractOrderModel.getManagerName());

	overviewManager.refreshObject(abstractOrderModel.getObject());
	overviewManager.lazyLoad(abstractOrderModel.getObject(), new LazyLoadEnum[][] { { LazyLoadEnum.COLLIES, LazyLoadEnum.NONE },
		{ LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE }, { LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE } });
	return abstractOrderModel;
    }

    /**
     * Oppdaterer tabell over ordre
     * 
     * @param updatedOrderLine
     * @param removeColli
     * @param window
     * @param combo
     */
    @SuppressWarnings("unchecked")
    void refreshTableOrder(OrderLine updatedOrderLine, boolean removeColli, WindowInterface window, boolean combo) {
	AbstractOrderModel abstractOrderModel = lazyLoadPackable();
	OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(abstractOrderModel.getManagerName());

	int rowCount = tableOrderLines.getRowCount();
	if (combo) {
	    rowCount--;
	}

	boolean colliDone = true;
	if (removeColli) {
	    List<OrderLine> orderLineList1 = (abstractOrderModel.getOrderLines());
	    OrderLine orderLine = orderLineList1.get(orderLineList1.indexOf(updatedOrderLine));
	    orderLine.setColli(null);
	    if (rowCount == 0) {
		colliDone = false;
	    }
	}

	tableOrders.revalidate();
	tablePostShipment.revalidate();

	abstractOrderModel.setStatus(null);
	if (abstractOrderModel.getPackageStarted() == null)
	    abstractOrderModel.setPackageStarted(Calendar.getInstance().getTime());

	if (rowCount == 0 && colliDone) {
	    abstractOrderModel.setColliesDone(1);

	} else {
	    abstractOrderModel.setColliesDone(0);

	}

	try {
	    overviewManager.saveObject(abstractOrderModel.getObject());
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", "Ordre er oppdatert av noen andre, oppdater før lagring");
	    e.printStackTrace();
	} catch (HibernateOptimisticLockingFailureException e) {
	    Util.showErrorDialog(window, "Feil", "Ordre er oppdatert av noen andre, oppdater før lagring");
	    e.printStackTrace();
	}
	tableModelOrderLines.fireTableDataChanged();

	refreshOwnOrderLineList(abstractOrderModel);
	if (orderSelectionList.hasSelection()) {
	    MainPackageVManager mainPackageVManager = (MainPackageVManager) ModelUtil.getBean("mainPackageVManager");
	    MainPackageV mainPackageV = (MainPackageV) orderSelectionList.getElementAt(tableOrders.convertRowIndexToModel(orderSelectionList
		    .getSelectionIndex()));

	    mainPackageVManager.refresh(mainPackageV);
	}

    }

    private void refreshOwnOrderLineList(Packable packable) {
	ownOrderLineList.clear();
	if (packable.getOrderLineList() != null) {
	    ownOrderLineList.addAll(getInitiatedOrderLines(packable));
	}

    }

    private List<OrderLine> getInitiatedOrderLines(Packable packable) {

	List<OrderLine> orderLines = packable.getOwnOrderLines();
	if (orderLines != null) {
	    OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
	    for (OrderLine orderLine : orderLines) {
		if (orderLine.getOrdNo() != null) {
		    orderLine.setOrdln(orderLineManager.findOrdlnByOrderLine(orderLine.getOrderLineId()));
		}
	    }
	}
	return orderLines;
    }

    /**
     * Tabellmodell for orderlinjer
     * 
     * @author atle.brekka
     */
    public class PackageOrderLineTableModel extends AbstractTableAdapter {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	private OrderLineManager orderLineManager1;

	/**
	 * @param listModel
	 * @param aOrderLineManager
	 * @param aWindow
	 */
	public PackageOrderLineTableModel(ListModel listModel, OrderLineManager aOrderLineManager, WindowInterface aWindow) {
	    super(listModel,
		    new String[] { "Artikkel", "#", "Klar", "Spesifikasjon", "Kolli", "Top level", "Har artikkel", "Startet", "Produkttype" });
	    orderLineManager1 = aOrderLineManager;
	    window = aWindow;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    if (refreshing) {
		return null;
	    }
	    Util.setWaitCursor(window.getComponent());
	    if (orderLineSelectionList.getSize() != 0) {
		OrderLine orderLine = (OrderLine) getRow(rowIndex);
		if (orderLine.getArticlePath().contains("Vindu")) {
		    System.out.println();

		}

		if (orderLine.getHasArticle() == null || orderLine.getAttributeInfo() == null || orderLine.getIsDefault() == null) {
		    orderLineManager1.lazyLoadTree(orderLine);

		    orderLine.hasArticle();
		    orderLine.setAttributeInfo(orderLine.getAttributesAsString());
		    orderLine.isDefault();

		    orderLineManager1.saveOrderLine(orderLine);
		}

		Util.setDefaultCursor(window.getComponent());
		switch (columnIndex) {
		case 0:
		    return orderLine.getArticleName();
		case 1:
		    String metric = orderLine.getMetric();
		    StringBuilder builder = new StringBuilder();
		    if (orderLine.getNumberOfItems() != null) {
			builder.append(orderLine.getNumberOfItems());
			if (metric != null) {
			    builder.append(" ").append(metric);
			}
		    }
		    return builder.toString();
		case 2:
		    return checkStatusOrderLine(orderLine);

		case 3:
		    if (orderLine.getOrdln() != null) {
			return orderLine.getOrdln().getDescription();
		    }
		    return Util.removeNoAttributes(orderLine.getAttributeInfo());
		case 4:
		    if (orderLine.getColli() != null) {
			return orderLine.getColli().toString();
		    }
		    return "---";
		case 5:
		    return Util.convertBooleanToString(orderLine.hasTopLevelArticle());
		case 6:
		    return Util.convertBooleanToString(orderLine.hasArticle());
		case 7:
		    if (orderLine.getActionStarted() != null) {
			return Util.SHORT_DATE_FORMAT.format(orderLine.getActionStarted());
		    }
		    return "---";
		case 8:
		    if (orderLine.getOrdln() != null) {
			return orderLine.getOrdln().getProdTp();
		    } else {
			return 0;
		    }
		default:
		    throw new IllegalStateException("Unknown column");
		}
	    }
	    return null;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
	    case 1:
	    case 2:
	    case 3:
	    case 4:
	    case 5:
	    case 6:
	    case 7:
		return String.class;
	    case 8:
		return Integer.class;

	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

	/**
	 * Henter orderlinje for gitt indeks
	 * 
	 * @param rowIndex
	 * @return orderlinje
	 */
	public OrderLine getOrderLine(int rowIndex) {
	    return (OrderLine) getRow(rowIndex);
	}

	/**
	 * Oppdaterer tabell over ordre
	 * 
	 * @param updatedOrderLine
	 * @param removeColli
	 */
	public void refreshOrderTable(OrderLine updatedOrderLine, boolean removeColli) {
	    refreshTableOrder(updatedOrderLine, removeColli, window, false);
	}

    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean canClose(String actionString, WindowInterface window) {

	fireClose();
	cleanUp();
	return true;
    }

    /**
     * Rydder opp
     */
    private void cleanUp() {
	mainPackageVList.clear();

	postShipmentList.clear();
	statusChekers.clear();
	bufferingListeners.clear();
	colliListListeners.clear();
	closeListeners.clear();
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean doDelete(WindowInterface window) {
	boolean returnValue = true;
	/*
	 * Set<Colli> collies = selectedColliViewHandlers.keySet();
	 * ColliViewHandler handler;
	 * 
	 * for (Colli colli : collies) { handler =
	 * selectedColliViewHandlers.get(colli); if (handler != null) {
	 * returnValue = handler.doDelete(window); if (returnValue) { handler =
	 * colliViewHandlers.remove(colli); ((Packable)
	 * presentationModelPackable.getBean()) .removeColli(colli); } } }
	 * selectedColliViewHandlers.clear(); fireColliRemoved(); updatePanel();
	 */
	return returnValue;
    }

    /**
     * Gir lyttere beskjed om at kolli er fjernet
     */
    /*
     * private void fireColliRemoved() { for (ListDataListener listener :
     * colliListListeners) { listener.intervalRemoved(new ListDataEvent(this,
     * -1, -1, -1)); } buttonEditColli.setEnabled(false);
     * buttonRemoveColli.setEnabled(false); }
     */

    /**
     * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
     */
    @SuppressWarnings("unchecked")
    public void doNew(WindowInterface window) {
	AbstractOrderModel abstractOrderModel = (AbstractOrderModel) presentationModelPackable.getBean();

	Colli newColli = new Colli(null, abstractOrderModel.getOrderModelOrder(), null, null, null, null,
		abstractOrderModel.getOrderModelPostShipment(), null, null);
	ColliViewHandler colliViewHandler = new ColliViewHandler("Kolli", newColli, abstractOrderModel, login, managerRepository, window);
	colliViewHandler.openEditView(null, false, window);
	abstractOrderModel.addColli(newColli);
	updatePanel();
    }

    /**
     * Oppdaterer vindu
     */
    private void updatePanel() {
	mainPackageView.updateColliesPanel(false);
    }

    /**
     * Setter summer
     */
    void setSums() {
	Budget productionBudget = null;
	ProductAreaGroup productAreaGroup = (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);

	String productAreaGroupName = null;
	if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
	    productAreaGroupName = productAreaGroup.getProductAreaGroupName();
	}

	SumOrderReadyV sum = managerRepository.getSumOrderReadyVManager().findByDateAndProductAreaGroupName(Calendar.getInstance().getTime(),
		productAreaGroupName);
	if (sum == null) {
	    sum = new SumOrderReadyV(null, BigDecimal.valueOf(0), null, null, null);
	}
	presentationModelSum.setBean(new SumOrderReadyVModel(sum));

	SumOrderReadyV sumWeek = managerRepository.getSumOrderReadyVManager().findSumByWeekAndProductAreaGroupName(currentYear, currentWeek,
		productAreaGroupName);
	if (sumWeek == null) {
	    sumWeek = new SumOrderReadyV(null, BigDecimal.valueOf(0), null, null, null);
	}

	presentationModelWeekSum.setBean(new SumOrderReadyVModel(sumWeek));

	productionBudget = managerRepository.getBudgetManager().findByYearAndWeekPrProductAreaGroup(currentYear, currentWeek, productAreaGroup,
		BudgetType.PRODUCTION);

	if (productionBudget == null) {
	    productionBudget = new Budget(null, null, null, BigDecimal.valueOf(0), null, null);
	}

	presentationModelBudget.setBean(new ProductionBudgetModel(productionBudget));
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doRefresh(WindowInterface window) {
	// try {
	PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
	MainPackageVManager mainPackageVManager = (MainPackageVManager) ModelUtil.getBean("mainPackageVManager");
	OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
	refreshing = true;

	setSums();

	List<MainPackageV> orders = new ArrayList<MainPackageV>(mainPackageVManager.findAll());
	Collections.sort(orders, new MainPackageVComparator());
	orderSelectionList.clearSelection();
	mainPackageVList.clear();
	if (orders.size() != 0) {
	    mainPackageVList.addAll(orders);
	}

	orderSelectionList.clearSelection();

	tableOrders.scrollRowToVisible(0);

	postShipmentSelectionList.clearSelection();
	postShipmentList.clear();
	List<PostShipment> postShipments = postShipmentManager.findAllNotSent();

	if (postShipments != null) {
	    for (PostShipment postShipment : postShipments) {
		orderManager.lazyLoadOrder(postShipment.getOrder(), new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });
	    }
	    Collections.sort(postShipments, new PostShipmentComparator());
	    postShipmentList.addAll(postShipments);
	}

	refreshing = false;
	orderLineSelectionList.clearSelection();

	presentationModelPackable.setBean(new OrderModel(new Order(), false, false, false, null, null));

	ownOrderLineList.clear();
	orderComments.clear();

	buttonAddArticle.setEnabled(false);
	buttonAddComment.setEnabled(false);
	buttonRemoveArticle.setEnabled(false);
	checkBoxReadyVegg.setEnabled(false);
	checkBoxReadyTakstol.setEnabled(false);
	checkBoxReadyPakk.setEnabled(false);
	// selectedColliViewHandlers.clear();
	// buttonAddColli.setEnabled(false);
	// buttonRemoveColli.setEnabled(false);
	// buttonEditColli.setEnabled(false);

	tablePostShipment.scrollRowToVisible(0);

	// checkCollies(window);

	mainPackageView.updateColliesPanel(true);
	/*
	 * } catch (ProTransException e) { e.printStackTrace();
	 * Util.showErrorDialog(window, "Feil", e.getMessage()); }
	 */

    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doSave(WindowInterface window) {

    }

    /**
     * Håndterer editering av kolli
     * 
     * @author atle.brekka
     */
    /*
     * private final class EditColliAction extends AbstractAction { private
     * static final long serialVersionUID = 1L;
     * 
     * private WindowInterface window;
     * 
     * public EditColliAction(WindowInterface aWindow) {
     * super("Editer kolli..."); window = aWindow; }
     * 
     * public void actionPerformed(ActionEvent arg0) {
     * 
     * if (selectedColliViewHandlers.size() != 1) { Util.showErrorDialog(window,
     * "Feil", "Det kan bare velges en kolli for editering"); } else { Colli
     * colli = selectedColliViewHandlers.keySet().iterator() .next();
     * ColliViewHandler handler = selectedColliViewHandlers .remove(colli);
     * handler.openEditView(colli, false, window);
     * handler.setColliSelected(false); colliViewHandlers.put(colli, handler);
     * selectedColliViewHandlers.clear(); handler.updateColliModel(); }
     * 
     * } }
     */

    /**
     * Henter view for visning av kolli
     * 
     * @param colli
     * @return view
     */
    /*
     * public ColliView getColliView(Colli colli) { ColliViewHandler
     * colliViewHandler = colliViewHandlers.get(colli);
     * 
     * if (colliViewHandler == null) { colliViewHandler = new
     * ColliViewHandler("Kolli", colli, (Packable)
     * presentationModelPackable.getBean(), login, managerRepository);
     * colliViewHandlers.put(colli, colliViewHandler);
     * colliViewHandler.addColliSelectionListener(this); } else {
     * colliViewHandler.initColli(colli); } ColliView colliView = new
     * ColliView(colliViewHandler); return colliView; }
     */

    /**
     * Håndterer valg av ordre
     * 
     * @author atle.brekka
     */
    private class OrderSelectionListener implements PropertyChangeListener {
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public OrderSelectionListener(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
	    if (event.getNewValue() != null && event.getPropertyName().equalsIgnoreCase("selectionIndex") && (Integer) event.getNewValue() != -1) {
		if (hasWriteAccess()) {
		    buttonAddArticle.setEnabled(hasWriteAccess());
		    buttonAddComment.setEnabled(hasWriteAccess());
		    checkBoxReadyVegg.setEnabled(hasWriteAccess());
		    checkBoxReadyTakstol.setEnabled(hasWriteAccess());
		    checkBoxReadyPakk.setEnabled(hasWriteAccess());
		}
		changeBean(window);
	    }
	}

    }

    /**
     * Håndterer selektering av etterlevering
     * 
     * @author atle.brekka
     */
    private class PostShipmentSelectionListener implements PropertyChangeListener {
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public PostShipmentSelectionListener(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
	    if (event.getNewValue() != null && event.getPropertyName().equalsIgnoreCase("selectionIndex") && (Integer) event.getNewValue() != -1) {
		buttonAddArticle.setEnabled(false);
		boolean writeAccess = hasWriteAccess();
		buttonAddComment.setEnabled(writeAccess);
		checkBoxReadyVegg.setEnabled(writeAccess);
		checkBoxReadyTakstol.setEnabled(writeAccess);
		checkBoxReadyPakk.setEnabled(writeAccess);
		changeBeanPostShipment(window);
	    }
	}

    }

    /**
     * Endrer gjeldende ordre
     * 
     * @param window
     */
    final void changeBean(final WindowInterface window) {
	try {
	    if (refreshing) {
		return;
	    }

	    checkBuffering(window);

	    ownOrderLineList.clear();
	    orderComments.clear();
	    if (orderSelectionList.hasSelection()) {
		changeOrderBean();
	    }

	    // selectedColliViewHandlers.clear();
	    // buttonAddColli.setEnabled(hasWriteAccess());
	    // buttonRemoveColli.setEnabled(false);
	    // buttonEditColli.setEnabled(false);
	    // checkCollies(window);
	    colliListViewHandler.checkCollies(window);
	    updatePanel();
	} catch (ProTransException e) {
	    e.printStackTrace();
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	}

    }

    private void changeOrderBean() {
	postShipmentSelectionList.clearSelection();
	int selectedIndex = orderSelectionList.getSelectionIndex();
	int realIndex = tableOrders.convertRowIndexToModel(selectedIndex);
	if (realIndex != -1 && realIndex < orderSelectionList.getSize()) {
	    MainPackageV mainPackageV = (MainPackageV) orderSelectionList.getElementAt(realIndex);
	    if (mainPackageV != null) {
		setBean(mainPackageV);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    private void setBean(MainPackageV mainPackageV) {

	Order order = managerRepository.getOrderManager().lazyLoadOrderLineAndCollies(mainPackageV.getOrderId());
	if (order != null) {
	    OrderModel orderModel = new OrderModel(order, false, false, true, null, null);
	    colliListViewHandler.setPackable(order, null);

	    presentationModelPackable.setBean(orderModel);
	    ((OrderModel) presentationModelPackable.getBean()).firePropertiesChanged();
	    refreshOwnOrderLineList(orderModel);

	    orderComments.addAll((List<OrderComment>) presentationModelPackable.getBufferedValue(OrderModel.PROPERTY_COMMENTS));
	}

    }

    private void checkBuffering(WindowInterface window) {
	if (presentationModelPackable.isBuffering()) {
	    if (JOptionPane.showConfirmDialog(window.getComponent(), "Det er gjort endringer, ønsker du å lagre?", "Lagre?",
		    JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
		saveComment(window);
	    } else {
		presentationModelPackable.triggerFlush();
	    }
	}
    }

    /**
     * Skifter etterlevering
     * 
     * @param window
     */
    @SuppressWarnings("unchecked")
    void changeBeanPostShipment(WindowInterface window) {
	try {
	    PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
	    orderSelectionList.clearSelection();
	    orderLineSelectionList.clearSelection();
	    if (!refreshing) {
		checkBuffering(window);
		ownOrderLineList.clear();
		if (orderComments.size() != 0) {
		    orderComments.clear();
		}
		if (postShipmentSelectionList.hasSelection()) {

		    int selectedIndex = postShipmentSelectionList.getSelectionIndex();
		    int realIndex = tablePostShipment.convertRowIndexToModel(selectedIndex);
		    if (realIndex != -1 && realIndex < postShipmentList.size()) {
			PostShipment postShipment = (PostShipment) postShipmentSelectionList.getElementAt(realIndex);
			if (postShipment != null) {
			    postShipmentManager.lazyLoad(postShipment, new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES,
				    LazyLoadPostShipmentEnum.ORDER_LINES, LazyLoadPostShipmentEnum.ORDER_COMMENTS });
			    PostShipmentModel postShipmentModel = new PostShipmentModel(postShipment);
			    presentationModelPackable.setBean(postShipmentModel);
			    ((PostShipmentModel) presentationModelPackable.getBean()).firePropertiesChanged();
			    refreshOwnOrderLineList(postShipmentModel);

			    orderComments.addAll(PostShipmentModel.getPostShipmentComments((List<OrderComment>) presentationModelPackable
				    .getBufferedValue(PostShipmentModel.PROPERTY_COMMENTS)));
			    colliListViewHandler.setPackable(postShipment, null);
			}
		    }
		}
		colliListViewHandler.checkCollies(window);
		updatePanel();
	    }
	} catch (ProTransException e) {
	    e.printStackTrace();
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	}
    }

    /**
     * Legger til lytter for buffering
     * 
     * @param listener
     */
    public void addBufferingListener(PropertyChangeListener listener) {
	bufferingListeners.add(listener);
    }

    /**
     * Henter knapp for oppdatering av vindu
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonRefresh(WindowInterface window) {
	return new RefreshButton(this, window);
    }

    /**
     * Henter sjekkboks for visning av pakkede order
     * 
     * @return sjekkboks
     */
    public JCheckBox getCheckBoxShowPackaged() {
	checkBoxShowPackaged = new JCheckBox(new ShowPackagedAction());
	checkBoxShowPackaged.setSelected(true);
	checkBoxShowPackaged.setName("CheckBoxShowPackaged");
	return checkBoxShowPackaged;
    }

    /**
     * Henter sjekkboks for å sette ordre klar
     * 
     * @param window
     * @return sjekkboks
     */
    public JCheckBox getCheckBoxReadyVegg(WindowInterface window) {
	checkBoxReadyVegg = BasicComponentFactory.createCheckBox(presentationModelPackable.getModel(OrderModel.PROPERTY_ORDER_READY_VEGG_BOOL),
		"Ordre klar vegg");
	checkBoxReadyVegg.addActionListener(new CheckBoxReadyVeggActionListener(window));

	checkBoxReadyVegg.setEnabled(false);
	checkBoxReadyVegg.setName("CheckBoxReadyVegg");
	return checkBoxReadyVegg;
    }

    public JCheckBox getCheckBoxReadyTakstol(WindowInterface window) {
	checkBoxReadyTakstol = BasicComponentFactory.createCheckBox(presentationModelPackable.getModel(OrderModel.PROPERTY_ORDER_READY_TAKSTOL_BOOL),
		"Ordre klar takstol");
	checkBoxReadyTakstol.addActionListener(new CheckBoxReadyTakstolActionListener(window));

	checkBoxReadyTakstol.setEnabled(false);
	checkBoxReadyTakstol.setName("CheckBoxReadyTakstol");
	return checkBoxReadyTakstol;
    }

    public JCheckBox getCheckBoxReadyPakk(WindowInterface window) {
	checkBoxReadyPakk = BasicComponentFactory.createCheckBox(presentationModelPackable.getModel(OrderModel.PROPERTY_ORDER_READY_PAKK_BOOL),
		"Ordre klar pakk");
	checkBoxReadyPakk.addActionListener(new CheckBoxReadyPakkActionListener(window));

	checkBoxReadyPakk.setEnabled(false);
	checkBoxReadyPakk.setName("CheckBoxReadyPakk");
	return checkBoxReadyPakk;
    }

    /**
     * Fjerner filter
     */
    void clearFilter() {
	orderLineSelectionList.clearSelection();
	orderSelectionList.clearSelection();
	postShipmentSelectionList.clearSelection();
	checkBoxShowPackaged.setSelected(true);
	tableOrders.setFilters(null);
	tableOrders.repaint();
	tablePostShipment.setFilters(null);
	tablePostShipment.repaint();
    }

    /**
     * Håndterer visning eller ikke visning av pakkede ordre
     * 
     * @author atle.brekka
     */
    private class ShowPackagedAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public ShowPackagedAction() {
	    super("Vis pakkede");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    refreshing = true;
	    tableOrders.clearSelection();
	    tablePostShipment.clearSelection();

	    orderLineSelectionList.clearSelection();
	    postShipmentSelectionList.clearSelection();
	    orderSelectionList.clearSelection();
	    handleFilter();
	    presentationModelPackable.setBean(new OrderModel(new Order(), false, false, false, null, null));
	    ownOrderLineList.clear();
	    orderComments.clear();
	    mainPackageView.updateColliesPanel(true);
	    buttonAddArticle.setEnabled(false);
	    buttonAddComment.setEnabled(false);
	    buttonRemoveArticle.setEnabled(false);

	    checkBoxReadyVegg.setEnabled(false);
	    checkBoxReadyTakstol.setEnabled(false);
	    checkBoxReadyPakk.setEnabled(false);
	    refreshing = false;
	}
    }

    /**
     * Sammenlikner to ordre
     * 
     * @author atle.brekka
     */
    class MainPackageVComparator implements Comparator<MainPackageV> {
	/**
	 * @param order1
	 * @param order2
	 * @return 1 dersom større, -1 dersom mindre og 0 dersom lik
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(MainPackageV order1, MainPackageV order2) {
	    if (order1 != null && order2 != null) {
		if (order1.getTransportDetails() != null && order2.getTransportDetails() != null) {
		    return new CompareToBuilder().append(order1.getTransportYear(), order2.getTransportYear())
			    .append(order1.getTransportWeek(), order2.getTransportWeek()).append(order1.getLoadingDate(), order2.getLoadingDate())
			    .append(order1.getTransportDetails(), order2.getTransportDetails()).toComparison();
		} else if (order1.getTransportDetails() != null) {
		    return -1;
		} else if (order2.getTransportDetails() != null) {
		    return 1;
		} else {
		    return new CompareToBuilder().append(order1.getOrderId(), order2.getOrderId()).toComparison();
		}
	    }
	    return 0;
	}
    }

    /**
     * Komparator for etterleveringer
     * 
     * @author atle.brekka
     */
    class PostShipmentComparator implements Comparator<PostShipment> {
	/**
	 * @param postShipment1
	 * @param postShipment2
	 * @return 1 dersom større, -1 dersom minder eller 0 dersom lik
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(PostShipment postShipment1, PostShipment postShipment2) {
	    if (postShipment1 != null && postShipment2 != null) {
		if (postShipment1.getTransport() != null && postShipment2.getTransport() != null) {
		    return new CompareToBuilder()
			    .append(postShipment1.getTransport().getTransportYear(), postShipment2.getTransport().getTransportYear())
			    .append(postShipment1.getTransport().getTransportWeek(), postShipment2.getTransport().getTransportWeek()).toComparison();
		} else if (postShipment1.getTransport() != null) {
		    return -1;
		} else if (postShipment2.getTransport() != null) {
		    return 1;
		} else {
		    return new CompareToBuilder().append(postShipment1.getPostShipmentId(), postShipment2.getPostShipmentId()).toComparison();
		}
	    }
	    return 0;
	}
    }

    /**
     * Sammenlikner kollier
     * 
     * @author atle.brekka
     */
    public class ColliComparator implements Comparator<Colli> {

	/**
	 * Sammenlikner på navn
	 * 
	 * @param colli1
	 * @param colli2
	 * @return 1 dersom større, -1 dersom mindre og 0 dersom lik
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Colli colli1, Colli colli2) {
	    return new CompareToBuilder().append(colli1.getColliName(), colli2.getColliName()).toComparison();
	}

    }

    /**
     * Henter komparator for kollier
     * 
     * @return komparator
     */
    public ColliComparator getColliComparator() {
	return new ColliComparator();
    }

    /**
     * Legger til lytter for lukking
     * 
     * @param listener
     */
    public void addCloseListener(CloseListener listener) {
	closeListeners.add(listener);
    }

    /**
     * Gir beskjed om at vindu lukker
     */
    private void fireClose() {
	for (CloseListener listener : closeListeners) {
	    listener.windowClosed();
	}
    }

    /**
     * Åpner dialog for setting av attributter for ordrelinje
     * 
     * @param aOrderLine
     * @param orderLineAttributes
     */
    private void openAttributeView(OrderLine aOrderLine, Set<OrderLineAttribute> orderLineAttributes) {
	ConstructionArticleAttributeViewHandler constructionArticleAttributeViewHandler = new ConstructionArticleAttributeViewHandler();

	UpdateAttributeView constructionArticleAttributeView = new UpdateAttributeView(aOrderLine,
		OrderLineAttribute.convertToInterface(orderLineAttributes), constructionArticleAttributeViewHandler);
	WindowInterface dialogAttributes = new JDialogAdapter(new JDialog(ProTransMain.PRO_TRANS_MAIN, "Attributter", true));
	dialogAttributes.setName("OrderLineAttributeView");
	dialogAttributes.add(constructionArticleAttributeView.buildPanel(dialogAttributes));
	dialogAttributes.pack();
	Util.locateOnScreenCenter(dialogAttributes);
	dialogAttributes.setVisible(true);
	dialogAttributes.dispose();
    }

    /**
     * Setter ordrelinjereferanser for ny ordrelinje
     * 
     * @param articleType
     * @param orderLineMain
     */
    private void setOrderLineRefs(ArticleType articleType, OrderLine orderLineMain) {
	ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil.getBean("articleTypeManager");
	articleTypeManager.lazyLoad(articleType, new LazyLoadArticleTypeEnum[] { LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE });
	Set<ArticleTypeArticleType> articleRefs = articleType.getArticleTypeArticleTypes();
	OrderLine orderLine;
	Set<OrderLineAttribute> orderLineAttributes;
	Set<OrderLine> orderLineRefs = new HashSet<OrderLine>();
	if (articleRefs != null) {
	    for (ArticleTypeArticleType articleRef : articleRefs) {
		orderLine = OrderLine.getInstance((Order) presentationModelPackable.getBufferedValue(ICostableModel.PROPERTY_ORDER),
			articleRef.getArticleTypeRef(), orderLineMain,
			(Deviation) presentationModelPackable.getBufferedValue(ICostableModel.PROPERTY_DEVIATION));

		ArticleType articleTypeRef = articleRef.getArticleTypeRef();
		articleTypeManager.lazyLoad(articleTypeRef, new LazyLoadArticleTypeEnum[] { LazyLoadArticleTypeEnum.ATTRIBUTE });
		Set<ArticleTypeAttribute> attributes = articleTypeRef.getArticleTypeAttributes();

		if (attributes != null) {
		    orderLineAttributes = new HashSet<OrderLineAttribute>();

		    for (ArticleTypeAttribute attribute : attributes) {
			orderLineAttributes.add(new OrderLineAttribute(null, orderLine, null, null, attribute, null, null, attribute.getAttribute()
				.getName()));
		    }

		    openAttributeView(orderLine, orderLineAttributes);

		    orderLine.setOrderLineAttributes(orderLineAttributes);

		    setOrderLineRefs(articleRef.getArticleTypeRef(), orderLine);
		}
		orderLineRefs.add(orderLine);
		orderLine.setArticlePath(orderLine.getGeneratedArticlePath());
	    }
	    orderLineMain.setOrderLines(orderLineRefs);
	}
    }

    /**
     * Viser vindu for setting av attributter
     * 
     * @param newArticles
     * @param order
     * @param window
     * @return ny ordrelinje
     */
    @SuppressWarnings("unchecked")
    private OrderLine showArticleAttributeView(List<ArticleType> newArticles, AbstractOrderModel abstractOrderModel, WindowInterface window) {
	if (newArticles != null && newArticles.size() == 1) {
	    ArticleType newArticleType = newArticles.get(0);
	    managerRepository.getArticleTypeManager().lazyLoad(newArticleType,
		    new LazyLoadArticleTypeEnum[] { LazyLoadArticleTypeEnum.ATTRIBUTE, LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE });
	    Set<ArticleTypeAttribute> attributes = newArticleType.getArticleTypeAttributes();

	    OrderLine orderLineMain = OrderLine.getInstance(abstractOrderModel.getOrder(), newArticleType, abstractOrderModel.getDeviation(),
		    abstractOrderModel.getOrderModelPostShipment());

	    if (attributes != null && attributes.size() != 0) {

		Set<OrderLineAttribute> orderLineAttributes = new HashSet<OrderLineAttribute>();
		for (ArticleTypeAttribute articleTypeAttribute : attributes) {
		    createNewOrderLineAttribute(orderLineMain, orderLineAttributes, articleTypeAttribute);
		}

		orderLineMain.setOrderLineAttributes(orderLineAttributes);

		openAttributeView(orderLineMain, orderLineAttributes);
	    } else {

		String metric = orderLineMain.getMetric();
		if (metric != null) {
		    metric = "(" + metric + ")";
		} else {
		    metric = "";
		}

		String numberOfValue = JOptionPane.showInputDialog(window.getComponent(), "Gi antall" + metric, orderLineMain.getNumberOfItems());

		if (numberOfValue != null && numberOfValue.length() != 0) {
		    try {
			orderLineMain.setNumberOfItems(Integer.valueOf(numberOfValue.replace(',', '.')));
		    } catch (NumberFormatException e) {
			orderLineMain.setNumberOfItems(Integer.valueOf(0));
			e.printStackTrace();
		    }
		}

	    }

	    setOrderLineRefs(newArticleType, orderLineMain);

	    orderLineMain.setArticlePath(orderLineMain.getGeneratedArticlePath());
	    return orderLineMain;

	}
	return null;
    }

    private void createNewOrderLineAttribute(OrderLine orderLineMain, Set<OrderLineAttribute> orderLineAttributes,
	    ArticleTypeAttribute articleTypeAttribute) {
	if (!articleTypeAttribute.getIsInactive()) {
	    orderLineAttributes.add(new OrderLineAttribute(null, orderLineMain, null, null, articleTypeAttribute, null, null, articleTypeAttribute
		    .getAttribute().getName()));
	}
    }

    /**
     * Legger til ordrelinje
     * 
     * @param window
     * @param order
     * @return ny ordrelinje
     */
    @SuppressWarnings("unchecked")
    OrderLine addArticle(WindowInterface window, AbstractOrderModel abstractOrderModel) {
	ArticleTypeViewHandler articleTypeViewHandler = new ArticleTypeViewHandler(login, managerRepository, null);
	ArticleTypeView articleTypeView = new ArticleTypeView(articleTypeViewHandler, true, true);
	JDialog dialog = Util.getDialog(window, "Artikkel", true);
	WindowInterface dialogWindow = new JDialogAdapter(dialog);
	dialogWindow.setName("ArticleView");
	dialogWindow.add(articleTypeView.buildPanel(dialogWindow));
	dialogWindow.pack();
	Util.locateOnScreenCenter(dialogWindow);
	dialogWindow.setVisible(true);

	List<ArticleType> newArticles = articleTypeView.getSelectedObjects();
	return showArticleAttributeView(newArticles, abstractOrderModel, window);
    }

    /**
     * Håndterer å legge til artikkel
     * 
     * @author atle.brekka
     */
    private class AddArticleAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public AddArticleAction(WindowInterface aWindow) {
	    super("Legg til artikkel...");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent arg0) {

	    AbstractOrderModel abstractOrderModel = (AbstractOrderModel) presentationModelPackable.getBean();

	    if (abstractOrderModel != null) {
		OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(abstractOrderModel.getManagerName());
		overviewManager.refreshObject(abstractOrderModel.getObject());

		OrderLine newOrderLine = addArticle(window, abstractOrderModel);
		if (newOrderLine != null) {
		    abstractOrderModel.setColliesDone(0);
		    abstractOrderModel.setOrderComplete(null);
		    newOrderLine.setAttributeInfo(newOrderLine.getAttributesAsString());
		    try {
			managerRepository.getOrderLineManager().saveOrderLine(newOrderLine);
			overviewManager.saveObject(abstractOrderModel.getObject());
			abstractOrderModel.addOrderLine(newOrderLine);
			ownOrderLineList.add(newOrderLine);
		    } catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		    }
		    refreshTableOrder(null, false, window, false);
		}
	    }
	}
    }

    /**
     * Håndterer sletting av artikkel
     * 
     * @author atle.brekka
     */
    private class RemoveArticleAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public RemoveArticleAction(WindowInterface aWindow) {
	    super("Fjern artikkel...");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent arg0) {
	    if (JOptionPane.showConfirmDialog(window.getComponent(), "Vil du virkelig slette artikkel?", "Slette", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
		List<OrderLine> selectedOrderLines = getSelectedOrderLines();
		if (selectedOrderLines.size() == 1) {
		    OrderLine orderLine = selectedOrderLines.get(0);
		    AbstractOrderModel abstractOrderModel = (AbstractOrderModel) presentationModelPackable.getBean();
		    OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(abstractOrderModel.getManagerName());
		    ownOrderLineList.remove(orderLine);

		    abstractOrderModel.removeOrderLine(orderLine);
		    abstractOrderModel.viewToModel();
		    try {
			overviewManager.saveObject(abstractOrderModel.getObject());
		    } catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		    }
		    orderLineSelectionList.clearSelection();
		    tableModelOrderLines.fireTableDataChanged();
		    buttonRemoveArticle.setEnabled(false);
		}
	    }

	}
    }

    /**
     * Setter høyde på garasjepakke.
     * 
     * @param colliHeight
     */
    final void setGarageColliHeight(final Integer colliHeight) {
	ColliManager colliManager = (ColliManager) ModelUtil.getBean("colliManager");
	List<Colli> collies = getColliList();
	for (Colli colli : collies) {
	    if (colli.getColliName().equalsIgnoreCase("Garasjepakke")) {
		colli.setHeight(colliHeight);
		colliManager.saveColli(colli);
		break;
	    }
	}
    }

    /**
     * Håndterer avkrysning i sjekkboks.
     * 
     * @author atle.brekka
     */
    class CheckBoxReadyVeggActionListener implements ActionListener {
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public CheckBoxReadyVeggActionListener(final WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
	    AbstractOrderModel<?, ?> abstractOrderModel = (AbstractOrderModel<?, ?>) presentationModelPackable.getBean();
	    Date orderReadyWall = abstractOrderModel.getOrderReadyWall();

	    String packedBy = (String) presentationModelPackable.getValue(OrderModel.PROPERTY_PACKED_BY);
	    boolean canceled = false;
	    if (orderReadyWall != null) {
		PackInitialsViewHandler packInitialsViewHandler = showPackInitialsView(packedBy, window);

		if (!packInitialsViewHandler.isCanceled()) {
		    packedBy = packInitialsViewHandler.getInitials();
		    orderReadyWall = packInitialsViewHandler.getReadyDate();

		} else {
		    canceled = true;
		    checkBoxReadyVegg.setSelected(false);
		}
	    } else {
		packedBy = null;
	    }

	    handlePackedByAndColliHeight(abstractOrderModel, orderReadyWall, packedBy, canceled);
	}

	@SuppressWarnings("unchecked")
	private void handlePackedByAndColliHeight(AbstractOrderModel abstractOrderModel, Date orderReadyWall, String packedBy, boolean canceled) {
	    if (!canceled) {
		OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(abstractOrderModel.getManagerName());

		overviewManager.refreshObject(abstractOrderModel.getObject());
		overviewManager.lazyLoad(abstractOrderModel.getObject(), new LazyLoadEnum[][] { { LazyLoadEnum.COLLIES, LazyLoadEnum.NONE },
			{ LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE }, { LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE } });
		abstractOrderModel.setOrderReadyVegg(orderReadyWall);
		setOrderReady(abstractOrderModel, orderReadyWall);
		presentationModelPackable.setValue(AbstractOrderModel.PROPERTY_PACKED_BY, packedBy);
		// if (colliHeight != null) {
		// presentationModelPackable.setValue(AbstractOrderModel.PROPERTY_GARAGE_COLLI_HEIGHT,
		// colliHeight);
		// }
		if (abstractOrderModel.isDonePackage()) {
		    abstractOrderModel.setOrderComplete(Util.getCurrentDate());
		}
		try {
		    overviewManager.saveObject(abstractOrderModel.getObject());
		} catch (ProTransException e) {
		    Util.showErrorDialog(window, "Feil", e.getMessage());
		    e.printStackTrace();
		}
		setSums();
	    }
	}

    }

    class CheckBoxReadyTakstolActionListener implements ActionListener {
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public CheckBoxReadyTakstolActionListener(final WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
	    AbstractOrderModel<?, ?> abstractOrderModel = (AbstractOrderModel<?, ?>) presentationModelPackable.getBean();
	    Date orderReadyTross = abstractOrderModel.getOrderReadyTross();

	    String packedBy = (String) presentationModelPackable.getValue(OrderModel.PROPERTY_PACKED_BY_TROSS);
	    boolean canceled = false;
	    if (orderReadyTross != null) {
		PackInitialsViewHandler packInitialsViewHandler = showPackInitialsView(packedBy, window);

		if (!packInitialsViewHandler.isCanceled()) {
		    packedBy = packInitialsViewHandler.getInitials();
		    orderReadyTross = packInitialsViewHandler.getReadyDate();
		} else {
		    canceled = true;
		    checkBoxReadyTakstol.setSelected(false);
		}
	    } else {
		packedBy = null;
	    }

	    handlePackedByAndColliHeight(abstractOrderModel, orderReadyTross, packedBy, canceled);
	}

	@SuppressWarnings("unchecked")
	private void handlePackedByAndColliHeight(AbstractOrderModel abstractOrderModel, Date orderReadyTross, String packedByTross, boolean canceled) {
	    if (!canceled) {
		OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(abstractOrderModel.getManagerName());

		overviewManager.refreshObject(abstractOrderModel.getObject());
		overviewManager.lazyLoad(abstractOrderModel.getObject(), new LazyLoadEnum[][] { { LazyLoadEnum.COLLIES, LazyLoadEnum.NONE },
			{ LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE }, { LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE } });
		abstractOrderModel.setOrderReadyTakstol(orderReadyTross);
		setOrderReady(abstractOrderModel, orderReadyTross);
		presentationModelPackable.setValue(AbstractOrderModel.PROPERTY_PACKED_BY_TROSS, packedByTross);
		// if (colliHeight != null) {
		// presentationModelPackable.setValue(AbstractOrderModel.PROPERTY_GARAGE_COLLI_HEIGHT,
		// colliHeight);
		// }
		if (abstractOrderModel.isDonePackage()) {
		    abstractOrderModel.setOrderComplete(Util.getCurrentDate());
		}
		try {
		    overviewManager.saveObject(abstractOrderModel.getObject());
		} catch (ProTransException e) {
		    Util.showErrorDialog(window, "Feil", e.getMessage());
		    e.printStackTrace();
		}
		setSums();
	    }
	}

    }

    class CheckBoxReadyPakkActionListener implements ActionListener {
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public CheckBoxReadyPakkActionListener(final WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
	    AbstractOrderModel<?, ?> abstractOrderModel = (AbstractOrderModel<?, ?>) presentationModelPackable.getBean();
	    Date orderReadyPakk = abstractOrderModel.getOrderReadyPack();

	    String packedByPack = (String) presentationModelPackable.getValue(OrderModel.PROPERTY_PACKED_BY_PACK);
	    boolean canceled = false;
	    if (orderReadyPakk != null) {
		PackInitialsViewHandler packInitialsViewHandler = showPackInitialsView(packedByPack, window);

		if (!packInitialsViewHandler.isCanceled()) {
		    packedByPack = packInitialsViewHandler.getInitials();
		    orderReadyPakk = packInitialsViewHandler.getReadyDate();
		} else {
		    canceled = true;
		    checkBoxReadyPakk.setSelected(false);
		}
	    } else {
		packedByPack = null;
	    }

	    handlePackedByAndColliHeight(abstractOrderModel, orderReadyPakk, packedByPack, canceled);
	}

	@SuppressWarnings("unchecked")
	private void handlePackedByAndColliHeight(AbstractOrderModel abstractOrderModel, Date orderReadyPakk, String packedByPack, boolean canceled) {
	    if (!canceled) {
		OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(abstractOrderModel.getManagerName());

		overviewManager.refreshObject(abstractOrderModel.getObject());
		overviewManager.lazyLoad(abstractOrderModel.getObject(), new LazyLoadEnum[][] { { LazyLoadEnum.COLLIES, LazyLoadEnum.NONE },
			{ LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE }, { LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE } });
		abstractOrderModel.setOrderReadyPakk(orderReadyPakk);
		setOrderReady(abstractOrderModel, orderReadyPakk);

		presentationModelPackable.setValue(AbstractOrderModel.PROPERTY_PACKED_BY_PACK, packedByPack);
		if (abstractOrderModel.isDonePackage()) {
		    abstractOrderModel.setOrderComplete(Util.getCurrentDate());
		}
		try {
		    overviewManager.saveObject(abstractOrderModel.getObject());
		} catch (ProTransException e) {
		    Util.showErrorDialog(window, "Feil", e.getMessage());
		    e.printStackTrace();
		}
		setSums();
	    }
	}

    }

    private void setOrderReady(AbstractOrderModel abstractOrderModel, Date orderReadyEnhet) {
	if (orderReadyEnhet != null && abstractOrderModel.getOrderReadyTakstolBool() && abstractOrderModel.getOrderReadyVeggBool()
		&& abstractOrderModel.getOrderReadyPakkBool()) {
	    abstractOrderModel.setOrderReady(orderReadyEnhet);
	} else {
	    abstractOrderModel.setOrderReady(null);
	}
    }

    private PackInitialsViewHandler showPackInitialsView(String packedBy, WindowInterface window
    // , Integer height
    ) {
	PackInitialsViewHandler packInitialsViewHandler = new PackInitialsViewHandler(packedBy,
	// height,
		(ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP),
		managerRepository.getApplicationUserManager());
	EditPackInitialsView editPackInitialsView = new EditPackInitialsView(packInitialsViewHandler);
	JDialog dialog = Util.getDialog(window, "Pakket av", true);
	WindowInterface window1 = new JDialogAdapter(dialog);
	window1.add(editPackInitialsView.buildPanel(window1));
	window1.pack();
	Util.locateOnScreenCenter(window1);
	window1.setVisible(true);
	return packInitialsViewHandler;
    }

    /**
     * Håndterer valg av ordrelinje
     * 
     * @author atle.brekka
     */
    class OrderLineSelectionListener implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent arg0) {
	    buttonRemoveArticle.setEnabled(false);
	    if (orderLineSelectionList.hasSelection()) {

		OrderLine orderLine = null;
		orderLine = (OrderLine) orderLineSelectionList.getElementAt(tableOrderLines.convertRowIndexToModel(orderLineSelectionList
			.getSelectionIndex()));

		if (orderLine.getArticleType() != null && orderLine.getArticleType().isExtra()) {
		    buttonRemoveArticle.setEnabled(true);
		}
	    }

	}

    }

    /**
     * Lager liste med kommentarer
     * 
     * @return liste
     */
    @SuppressWarnings("unchecked")
    public JList getListComments() {
	orderComments.clear();
	orderComments.addAll((List<OrderComment>) presentationModelPackable.getBufferedValue(OrderModel.PROPERTY_COMMENTS));
	JList list = new JList(orderComments);
	list.setName("ListOrderComments");
	list.setCellRenderer(new ListMultilineRenderer(80));
	return list;
    }

    /**
     * Lager knapp for å legge til kommentar
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonAddComment(WindowInterface window) {
	buttonAddComment = new JButton(new AddComment(window));
	buttonAddComment.setName("AddComment");
	buttonAddComment.setEnabled(false);
	return buttonAddComment;
    }

    /**
     * Lager komboboks for produktområdegruppe
     * 
     * @return komboboks
     */
    public JComboBox getComboBoxProductAreaGroup() {
	return Util.getComboBoxProductAreaGroup(login.getApplicationUser(), login.getUserType(), productAreaGroupModel);
    }

    public JComboBox getComboBoxPakketype() {
	comboBoxPakketype = new JComboBox(Packagetype.values());
	comboBoxPakketype.setName("ComboBoxPakketype");
	comboBoxPakketype.addItemListener(new PakketypeListener());

	Packagetype defaultPakketype = Packagetype.getPackageType(login.getApplicationUser().getPackageType());
	comboBoxPakketype.setSelectedItem(defaultPakketype);

	return comboBoxPakketype;
    }

    /**
     * Henter knapp for lagring av kommentar
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonSaveComment(WindowInterface window) {
	JButton buttonSaveComment = new JButton(new SaveCommentAction(window));
	buttonSaveComment.setEnabled(false);
	PropertyConnector.connect(buttonSaveComment, "enabled", presentationModelPackable, PresentationModel.PROPERTYNAME_BUFFERING);
	return buttonSaveComment;
    }

    /**
     * Lagrer kommentar
     * 
     * @param window
     */
    @SuppressWarnings("unchecked")
    void saveComment(WindowInterface window) {

	presentationModelPackable.triggerCommit();
	AbstractOrderModel abstractOrderModel = (AbstractOrderModel) presentationModelPackable.getBean();

	OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(abstractOrderModel.getManagerName());
	try {
	    overviewManager.saveObject(abstractOrderModel.getObject());
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}
	if (orderSelectionList.hasSelection()) {
	    MainPackageVManager mainPackageVManager = (MainPackageVManager) ModelUtil.getBean("mainPackageVManager");
	    MainPackageV mainPackageV = (MainPackageV) orderSelectionList.getElementAt(tableOrders.convertRowIndexToModel(orderSelectionList
		    .getSelectionIndex()));

	    mainPackageVManager.refresh(mainPackageV);
	}
    }

    /**
     * Håndterer lagring av kommentar
     * 
     * @author atle.brekka
     */
    private class SaveCommentAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public SaveCommentAction(WindowInterface aWindow) {
	    super("Lagre kommentar");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    saveComment(window);

	}
    }

    private void packOrderLines(List<OrderLine> orderLines, Packable packable, WindowInterface window, ArticlePacker articlePacker,
	    boolean useDefaultColli) {

	articlePacker.packOrderLines(orderLines, packable, window, useDefaultColli);
	refreshTableOrder(null, false, window, true);
    }

    /**
     * Håndterer dobbeltklikk på ordrelinje
     * 
     * @author atle.brekka
     */
    final class OrderLineDoubleClickHandler extends MouseAdapter {
	private WindowInterface window;
	private ColliViewHandlerProvider colliViewHandlerProvider;

	/**
	 * @param aWindow
	 */
	public OrderLineDoubleClickHandler(WindowInterface aWindow, ColliViewHandlerProvider aColliViewHandlerProvider) {
	    colliViewHandlerProvider = aColliViewHandlerProvider;
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
	    Packable packable = lazyLoadPackable();
	    // Packable packable = (Packable)
	    // presentationModelPackable.getBean();
	    if (packable.getProbability() != null && packable.getProbability() == 90) {
		Util.showErrorDialog(window, "Kan ikke pakke", "Kan ikke pakke ordre som ikke er 100%");
	    } else {

		handleMouseClick(mouseEvent, packable);
	    }
	}

	private void handleMouseClick(MouseEvent mouseEvent, Packable packable) {
	    ArticlePacker articlePacker = new ArticlePacker(colliViewHandlerProvider, colliSetup, vismaFileCreator);
	    List<OrderLine> orderLines = getSelectedOrderLines();
	    if (SwingUtilities.isLeftMouseButton(mouseEvent) && mouseEvent.getClickCount() == 2 && hasWriteAccess()) {
		if (!orderLines.isEmpty()) {
		    orderLineSelectionList.clearSelection();

		    packOrderLines(orderLines, packable, window, articlePacker, true);

		}
	    } else if (SwingUtilities.isRightMouseButton(mouseEvent) && hasWriteAccess()) {
		List<String> articleNames = Lists.newArrayList(Iterables.transform(orderLines, tilArticleName()));
		if (!orderLines.isEmpty() && articlePacker.canPack(articleNames)) {
		    popupMenuOrderLine.show((JXTable) mouseEvent.getSource(), mouseEvent.getX(), mouseEvent.getY());
		}
	    } else if (SwingUtilities.isLeftMouseButton(mouseEvent) && mouseEvent.getClickCount() == 1) {
		if (orderLines.size() == 1 && orderLines.get(0).getArticleType() != null && orderLines.get(0).getArticleType().isExtra()) {
		    buttonRemoveArticle.setEnabled(true);
		}
	    }
	}

    }

    private Function<OrderLine, String> tilArticleName() {
	return new Function<OrderLine, String>() {

	    public String apply(OrderLine orderline) {
		return orderline.getArticleName();
	    }
	};
    }

    private List<OrderLine> getSelectedOrderLines() {
	int[] selectedRows = tableOrderLines.getSelectedRows();
	List<OrderLine> orderlines = Lists.newArrayList();
	if (selectedRows.length > 0) {
	    for (int i : selectedRows) {
		orderlines.add((OrderLine) orderLineSelectionList.getElementAt(tableOrderLines.convertRowIndexToModel(i)));
	    }
	}
	return orderlines;
	// return orderLineSelectionList.hasSelection() ? (OrderLine)
	// orderLineSelectionList.getElementAt(tableOrderLines
	// .convertRowIndexToModel(orderLineSelectionList.getSelectionIndex()))
	// : null;
    }

    /**
     * Henter knapp for å søke etter ordre
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonSearchOrder(WindowInterface window) {
	return new JButton(new SearchOrderAction(window));
    }

    /**
     * Søker ordre
     * 
     * @param orderNr
     * @param customerNr
     * @param window
     */
    void searchOrder(final Transportable transportable, final WindowInterface window) {
	orderLineSelectionList.clearSelection();
	orderSelectionList.clearSelection();
	postShipmentSelectionList.clearSelection();
	if (transportable.getPostShipment() == null) {
	    MainPackageVManager mainPackageVManager = (MainPackageVManager) ModelUtil.getBean("mainPackageVManager");
	    MainPackageV mainPackageV = mainPackageVManager.findByOrderNr(transportable.getOrder().getOrderNr());

	    if (mainPackageV != null) {
		orderSelectionList.setSelection(mainPackageV);
		orderSelectionList.setSelectionIndex(tableOrders.convertRowIndexToView(orderSelectionList.getSelectionIndex()));
		tableOrders.scrollRowToVisible(orderSelectionList.getSelectionIndex());

	    } else {
		Util.showMsgDialog(window.getComponent(), "Fant ikke ordre", "Ordre med ble ikke funnet");
	    }
	} else {
	    PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");

	    PostShipment postShipment = postShipmentManager.loadById(transportable.getPostShipment().getPostShipmentId());
	    if (postShipment != null) {
		postShipmentSelectionList.setSelection(postShipment);
		if (postShipmentSelectionList.getSelectionIndex() < 0) {
		    Util.showMsgDialog(window.getComponent(), "Fant ikke ordre", "Etterlevering med ble ikke funnet");
		} else {
		    tablePostShipment.scrollRowToVisible(postShipmentSelectionList.getSelectionIndex());
		}
	    }
	}
    }

    /**
     * Håndterer søking etter ordre
     * 
     * @author atle.brekka
     */
    private class SearchOrderAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public SearchOrderAction(WindowInterface aWindow) {
	    super("Søk ordre...");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    Util.setWaitCursor(window.getComponent());
	    orderLineSelectionList.clearSelection();
	    orderSelectionList.clearSelection();
	    clearFilter();

	    Transportable transportable = doSearch(window);

	    if (transportable != null) {
		searchOrder(transportable, window);
	    }

	    Util.setDefaultCursor(window.getComponent());

	}

    }

    /**
     * Søker
     * 
     * @param window
     * @return order dersom funnet
     */
    Transportable doSearch(WindowInterface window) {
	Transportable transportable = orderViewHandler.searchOrder(window, true);
	return transportable;
    }

    /**
     * Hent label for daglig sum
     * 
     * @return label
     */
    public JLabel getLabelDayValue() {
	return BasicComponentFactory.createLabel(presentationModelSum.getModel(SumOrderReadyVModel.PROPERTY_PACKAGE_SUM_STRING));
    }

    /**
     * Henter label for ukentlig sum
     * 
     * @return label
     */
    public JLabel getLabelWeekValue() {
	return BasicComponentFactory.createLabel(presentationModelWeekSum.getModel(SumOrderReadyVModel.PROPERTY_PACKAGE_SUM_STRING));
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
     * Lager label for pakket av
     * 
     * @return label
     */
    public JLabel getLabelPackedByWall() {
	return BasicComponentFactory.createLabel(presentationModelPackable.getModel(OrderModel.PROPERTY_PACKED_BY));
    }

    public JLabel getLabelPackedByTross() {
	return BasicComponentFactory.createLabel(presentationModelPackable.getModel(OrderModel.PROPERTY_PACKED_BY_TROSS));
    }

    public JLabel getLabelPackedByPakk() {
	return BasicComponentFactory.createLabel(presentationModelPackable.getModel(OrderModel.PROPERTY_PACKED_BY_PACK));
    }

    /**
     * Henter meny for å kunne aksessere gulvsponpakkevindu
     * 
     * @param user
     * @param gulvsponPackageVManager
     * @param window
     * @return meny
     */
    public JMenuBar getMenuBar(ApplicationUser user, GulvsponPackageVManager gulvsponPackageVManager, WindowInterface window) {
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Vinduer");
	JMenuItem menuItem = new JMenuItem(new GulvsponMenuAction(user, gulvsponPackageVManager, window));
	menu.add(menuItem);
	menuBar.add(menu);
	return menuBar;
    }

    /**
     * Håndterer menyvalget gulvspon
     * 
     * @author atle.brekka
     */
    private class GulvsponMenuAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface parentWindow;

	/**
	 * @param aApplicationUser
	 * @param aGulvsponPackageVManager
	 * @param aWindow
	 */
	public GulvsponMenuAction(ApplicationUser aApplicationUser, GulvsponPackageVManager aGulvsponPackageVManager, WindowInterface aWindow) {
	    super("Gulvspon...");
	    parentWindow = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    Util.setWaitCursor(parentWindow.getComponent());

	    GulvsponPackageVManager gulvsponPackageVManager = (GulvsponPackageVManager) ModelUtil.getBean("gulvsponPackageVManager");
	    AbstractProductionPackageViewHandler<no.ugland.utransprod.model.PackableListItem> packageViewHandler = new PackageViewHandler(login,
		    managerRepository, deviationViewHandlerFactory, new GulvsponPackageApplyList(login, gulvsponPackageVManager, managerRepository),
		    "Pakking av gulvspon", TableEnum.TABLEPACKAGEGULVSPON, ApplicationParamUtil.findParamByName("gulvspon_attributt"));

	    ApplyListView<no.ugland.utransprod.model.PackableListItem> applyListView = new ApplyListView<no.ugland.utransprod.model.PackableListItem>(
		    packageViewHandler, true);
	    JFrame frame = new JFrame("Gulvsponpakking");
	    WindowInterface window = new JFrameAdapter(frame);
	    frame.add(applyListView.buildPanel(window));
	    frame.pack();
	    frame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
	    frame.setSize(packageViewHandler.getWindowSize());
	    Util.locateOnScreenCenter(window);
	    frame.setVisible(true);
	    Util.setDefaultCursor(parentWindow.getComponent());

	}
    }

    /**
     * @see no.ugland.utransprod.gui.model.ColliListener#orderLineRemoved(no.ugland.utransprod.gui.WindowInterface)
     */
    public void orderLineRemoved(WindowInterface window) {
	refreshTableOrder(null, false, window, true);

    }

    /**
     * Sjekker om bruker har skriverettigheter
     * 
     * @return true dersom skriverettigheter
     */
    public boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(login.getUserType(), "Garasjepakke");
    }

    /**
     * Henter om vindu skal kjøre dispose
     * 
     * @return true dersom dispose
     */
    public boolean getDisposeOnClose() {
	return disposeOnClose;
    }

    /**
     * Håndterer museklikk i tabell for etterleveringer
     * 
     * @author atle.brekka
     */
    final class TableClickHandler extends MouseAdapter {
	public TableClickHandler() {
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	    if (SwingUtilities.isRightMouseButton(e)) {

		popupMenuPostShipment.show((JXTable) e.getSource(), e.getX(), e.getY());

	    }

	}
    }

    /**
     * Pakkliste
     * 
     * @author atle.brekka
     */
    private class MenuItemListenerPacklist implements ActionListener {
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public MenuItemListenerPacklist(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent actionEvent) {
	    PostShipment postShipment = (PostShipment) postShipmentSelectionList.getElementAt(tablePostShipment
		    .convertRowIndexToModel(postShipmentSelectionList.getSelectionIndex()));
	    Util.runInThreadWheel(window.getRootPane(), new PacklistPrinter(window, postShipment), null);
	}

    }

    /**
     * Legg til kommentar
     * 
     * @author atle.brekka
     */
    private class AddComment extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	/**
	 * @param aWindow
	 * @param aPresentationModel
	 */
	public AddComment(WindowInterface aWindow) {
	    super("Kommentar...");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    addCommentToOrder(window);

	}

	private void addCommentToOrder(WindowInterface window) {
	    CommentViewHandler commentViewHandler = new CommentViewHandler(login, managerRepository.getOrderManager());
	    OrderComment newOrderComment = commentViewHandler.showAndEditOrderComment(window, null, "orderManager");

	    if (newOrderComment != null) {
		saveAndAddOrderComment(newOrderComment, window);
	    }

	}

    }

    @SuppressWarnings("unchecked")
    private void saveAndAddOrderComment(OrderComment orderComment, WindowInterface window) {

	AbstractOrderModel abstractOrderModel = (AbstractOrderModel) presentationModelPackable.getBean();

	initializeAndSaveOrderComment(abstractOrderModel.getOrder(), orderComment, abstractOrderModel.getDeviation(), window);

    }

    private void initializeAndSaveOrderComment(Order order, OrderComment orderComment, Deviation deviation, WindowInterface window) {
	OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
	orderManager.refreshObject(order);
	if (!Hibernate.isInitialized(order.getOrderComments())) {
	    orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.COMMENTS,
		    LazyLoadOrderEnum.ORDER_LINES });
	}
	order.addOrderComment(orderComment);
	order.cacheComments();
	orderComment.setDeviation(deviation);
	try {
	    orderManager.saveOrder(order);

	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}
	orderComments.add(0, orderComment);
    }

    /**
     * Håndterer popupmeny for registrering av avvik
     * 
     * @author atle.brekka
     */
    class MenuItemListenerDeviation implements ActionListener {
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public MenuItemListenerDeviation(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    MainPackageV mainPackageV = (MainPackageV) orderSelectionList.getElementAt(tableOrders.convertRowIndexToModel(orderSelectionList
		    .getSelectionIndex()));
	    OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
	    Order order = orderManager.findByOrderNr(mainPackageV.getOrderNr());
	    if (order != null) {
		DeviationViewHandler deviationViewHandler = deviationViewHandlerFactory.create(order, true, false, true, null, true);
		deviationViewHandler.registerDeviation(order, window);
	    }
	}

    }

    class MenuItemListenerPack implements ActionListener {
	private WindowInterface window;
	private ColliViewHandlerProvider colliViewHandlerProvider;

	/**
	 * @param aWindow
	 */
	public MenuItemListenerPack(WindowInterface aWindow, ColliViewHandlerProvider aColliViewHandlerProvider) {
	    colliViewHandlerProvider = aColliViewHandlerProvider;
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    ArticlePacker articlePacker = new ArticlePacker(colliViewHandlerProvider, colliSetup, vismaFileCreator);
	    List<OrderLine> orderLines = getSelectedOrderLines();
	    Packable packable = (Packable) presentationModelPackable.getBean();
	    packOrderLines(orderLines, packable, window, articlePacker, true);
	}

    }

    /**
     * Håndterer høyreklikk i tabell over ordre
     * 
     * @author atle.brekka
     */
    final class RightClickListenerOrder extends MouseAdapter {
	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	    if (SwingUtilities.isRightMouseButton(e)) {
		popupMenuOrder.show((JXTable) e.getSource(), e.getX(), e.getY());
	    }
	}
    }

    /**
     * Håndterer filtrering
     */
    protected void handleFilter() {
	// gi beskjed til alle transportlister at de skal filtrere
	ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);

	List<Filter> filtersOrder = new ArrayList<Filter>();
	List<Filter> filtersPostShipment = new ArrayList<Filter>();

	FilterGroupSelector.valueOf(StringUtils.upperCase(group.getProductAreaGroupName())).addFilter(group, filtersOrder, filtersPostShipment);

	@SuppressWarnings("unused")
	boolean added = !checkBoxShowPackaged.isSelected() ? addPackedFilter(filtersOrder, filtersPostShipment) : false;

	Filter[] filterArray = new Filter[filtersOrder.size()];
	FilterPipeline filterPipeline = filtersOrder.size() != 0 ? new FilterPipeline(filtersOrder.toArray(filterArray)) : null;
	tableOrders.setFilters(filterPipeline);

	filterArray = new Filter[filtersPostShipment.size()];
	filterPipeline = filtersPostShipment.size() != 0 ? new FilterPipeline(filtersPostShipment.toArray(filterArray)) : null;
	tablePostShipment.setFilters(filterPipeline);

	tablePostShipment.repaint();
	tableOrders.repaint();

    }

    private boolean addPackedFilter(List<Filter> filtersOrder, List<Filter> filtersPostShipment) {
	Filter filterPackageOrder = new PatternFilter("0", Pattern.CASE_INSENSITIVE, 2);
	Filter filterPackagePostShipment = new PatternFilter("0", Pattern.CASE_INSENSITIVE, 2);
	filtersOrder.add(filterPackageOrder);
	filtersPostShipment.add(filterPackagePostShipment);
	return true;
    }

    private enum FilterGroupSelector {
	ALLE {
	    @Override
	    public void addFilter(ProductAreaGroup group, List<Filter> filtersOrder, List<Filter> filtersPostShipment) {
	    }
	},
	GARASJE {
	    @Override
	    public void addFilter(ProductAreaGroup group, List<Filter> filtersOrder, List<Filter> filtersPostShipment) {
		addFiltersForGroup(group, filtersOrder, filtersPostShipment);

	    }
	},
	BYGGELEMENT {
	    @Override
	    public void addFilter(ProductAreaGroup group, List<Filter> filtersOrder, List<Filter> filtersPostShipment) {
		addFiltersForGroup(group, filtersOrder, filtersPostShipment);

	    }
	},
	TAKSTOL {
	    @Override
	    public void addFilter(ProductAreaGroup group, List<Filter> filtersOrder, List<Filter> filtersPostShipment) {
		addFiltersForGroup(group, filtersOrder, filtersPostShipment);

	    }
	};
	public abstract void addFilter(ProductAreaGroup group, List<Filter> filtersOrder, List<Filter> filtersPostShipment);

	private static void addFiltersForGroup(final ProductAreaGroup group, final List<Filter> filtersOrder, final List<Filter> filtersPostShipment) {
	    Filter filterGroupOrder = new PatternFilter(group.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE, 5);
	    Filter filterGroupPostShipment = new PatternFilter(group.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE, 3);
	    filtersOrder.add(filterGroupOrder);
	    filtersPostShipment.add(filterGroupPostShipment);
	}
    }

    /**
     * Håndterer endring av filter
     * 
     * @author atle.brekka
     */
    class FilterPropertyChangeListener implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
	    handleFilter();
	    setSums();

	}

    }

    public JPanel getColliListView(WindowInterface window) {

	ColliListView colliListView = new ColliListView(colliListViewHandler);
	return colliListView.buildPanel(window, "180", false);
    }

    public void contentsChanged(ListDataEvent e) {
	mainPackageView.updateColliesPanel(false);

    }

    public void intervalAdded(ListDataEvent e) {
    }

    public void intervalRemoved(ListDataEvent e) {
	mainPackageView.updateColliesPanel(false);

    }

    public void colliSelectionChange(boolean selection, ColliModel colliModel) {
    }

    public void refreshCollies() {
	// TODO Auto-generated method stub

    }

    public class PakketypeListener implements ItemListener {

	public void itemStateChanged(ItemEvent arg0) {
	    setTableOrderLinesFilters();
	}

    }
}
