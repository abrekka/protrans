package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProductAreaGroupProvider;
import no.ugland.utransprod.gui.UBColumnControlPopup;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.buttons.RefreshButton;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditProcentDoneView;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.gui.model.ProcentDoneModel;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.TakstolPackageApplyList;
import no.ugland.utransprod.gui.model.TakstolProductionApplyList;
import no.ugland.utransprod.gui.model.TextPaneRendererProcentDone;
import no.ugland.utransprod.gui.model.TextPaneRendererTransport;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProcentDone;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.CustTrManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.TransportableComparator;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

/**
 * Hjelpeklasse for produksjonsoversikt
 * 
 * @author atle.brekka
 */
public class ProductionOverviewViewHandler extends DefaultAbstractViewHandler<Order, OrderModel> implements ProductAreaGroupProvider,
	OrderNrProvider, ProduceableProvider {

    private static final long serialVersionUID = 1L;

    Map<String, StatusCheckerInterface<Transportable>> statusCheckers;

    OrderViewHandler orderViewHandler;

    JPopupMenu popupMenuProduction;

    JMenuItem menuItemPacklist;

    JMenuItem menuItemVegg;

    JMenuItem menuItemOpenOrder;
    private JMenuItem menuItemSetProductionWeek;

    JMenuItem menuItemShowMissing;

    JMenuItem menuItemShowContent;

    JMenuItem menuItemFront;

    JMenuItem menuItemGavl;

    private JMenuItem menuItemProductionTakstol;
    private JMenuItem menuItemProductionUnitTakstol;

    JMenuItem menuItemPackageTakstol;

    JMenuItem menuItemTakstein;

    JMenuItem menuItemGulvspon;

    JMenuItem menuItemDeviation;

    JMenuItem menuItemSetProcent;
    private JMenuItem menuItemSetEstimatedTimeWall;
    private JMenuItem menuItemSetEstimatedTimeGavl;
    private JMenuItem menuItemSetEstimatedTimePack;

    private Login login;

    private TableModel productionOverviewTableModel;

    @SuppressWarnings("unchecked")
    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers;

    private List<ProductAreaGroup> productAreaGroupList;

    PresentationModel productAreaGroupModel;

    JCheckBox checkBoxFilter;
    private VismaFileCreator vismaFileCreator;
    private Map<String, JMenuItem> menuItemMap;

    private ManagerRepository managerRepository;
    private DeviationViewHandlerFactory deviationViewHandlerFactory;

    private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;

    private JButton buttonShowTakstolInfo;

    private JMenuItem menuItemShowTakstolInfo;

    private SetProductionUnitActionFactory setProductionUnitActionFactory;
    private ArticleType articleTypeTakstol;

    @Inject
    public ProductionOverviewViewHandler(final VismaFileCreator aVismaFileCreator, final OrderViewHandlerFactory orderViewHandlerFactory,
	    final Login aLogin, ManagerRepository aManagerRepository, DeviationViewHandlerFactory aDeviationViewHandlerFactory,
	    final ShowTakstolInfoActionFactory aShowTakstolInfoActionFactory, final @Named("takstolArticle") ArticleType aArticleTypeTakstol,
	    final TakstolPackageApplyList takstolPackageApplyList, final TakstolProductionApplyList takstolProductionApplyList,
	    SetProductionUnitActionFactory aSetProductionUnitActionFactory, @Named("kostnadTypeTakstoler") CostType aCostTypeTross,
	    @Named("kostnadEnhetTakstoler") CostUnit aCostUnitTross) {
	super("Produksjonsoversikt", aManagerRepository.getOrderManager(), aLogin.getUserType(), true);
	articleTypeTakstol = aArticleTypeTakstol;
	setProductionUnitActionFactory = aSetProductionUnitActionFactory;
	showTakstolInfoActionFactory = aShowTakstolInfoActionFactory;
	deviationViewHandlerFactory = aDeviationViewHandlerFactory;
	managerRepository = aManagerRepository;
	login = aLogin;
	vismaFileCreator = aVismaFileCreator;
	orderViewHandler = orderViewHandlerFactory.create(true);
	statusCheckers = Util.getStatusCheckersTransport(managerRepository);
	statusCheckers.put("Vegg", Util.getVeggChecker());
	statusCheckers.put("Front", Util.getFrontChecker());

	setupMenus();

	productionPackageHandlers = Util.getProductionPackageHandlers(vismaFileCreator, login, orderViewHandlerFactory, managerRepository,
		deviationViewHandlerFactory, showTakstolInfoActionFactory, aArticleTypeTakstol, takstolPackageApplyList, takstolProductionApplyList,
		aSetProductionUnitActionFactory, aCostTypeTross, aCostUnitTross);
	initProductAreaGroup();

    }

    private void setupMenus() {
	menuItemMap = new Hashtable<String, JMenuItem>();
	popupMenuProduction = new JPopupMenu("Produksjon");
	popupMenuProduction.setName("PopupMenuProduction");
	menuItemOpenOrder = new JMenuItem("Se ordre...");
	menuItemOpenOrder.setName("MenuItemOpenOrder");
	popupMenuProduction.add(menuItemOpenOrder);
	menuItemSetProductionWeek = new JMenuItem("Sett produksjonsuke...");
	menuItemSetProductionWeek.setName("MenuItemSetProductionweek");
	popupMenuProduction.add(menuItemSetProductionWeek);

	menuItemPacklist = new JMenuItem("Sett pakkliste klar...");
	menuItemPacklist.setName("MenuItemPacklist");
	menuItemPacklist.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.PAKKLISTE.getColumnName(), menuItemPacklist);

	menuItemVegg = new JMenuItem("Sett vegg produsert...");
	menuItemVegg.setName("MenuItemVegg");
	menuItemVegg.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.VEGG.getColumnName(), menuItemVegg);

	menuItemFront = new JMenuItem("Sett front produsert...");
	menuItemFront.setName("MenuItemFront");
	menuItemFront.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.PAKK.getColumnName(), menuItemFront);

	menuItemGavl = new JMenuItem("Sett gavl produsert...");
	menuItemGavl.setName("MenuItemGavl");
	menuItemGavl.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.GAVL.getColumnName(), menuItemGavl);

	menuItemProductionTakstol = new JMenuItem("Sett takstol produsert...");
	menuItemProductionTakstol.setName("MenuItemProduksjonTakstol");
	menuItemProductionTakstol.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.TAKSTOL.getColumnName() + "Produksjon", menuItemProductionTakstol);

	menuItemPackageTakstol = new JMenuItem("Sett takstol pakket...");
	menuItemPackageTakstol.setName("MenuItemPakkingTakstol");
	menuItemPackageTakstol.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.TAKSTOL.getColumnName() + "Pakking", menuItemPackageTakstol);

	menuItemTakstein = new JMenuItem("Sett takstein pakket...");
	menuItemTakstein.setName("MenuItemTakstein");
	menuItemTakstein.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.TAKSTEIN.getColumnName(), menuItemTakstein);

	menuItemGulvspon = new JMenuItem("Sett gulvspon pakket...");
	menuItemGulvspon.setName("MenuItemGulvspon");
	menuItemGulvspon.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.GULVSPON.getColumnName(), menuItemGulvspon);

	menuItemShowMissing = new JMenuItem("Se mangler...");
	menuItemShowMissing.setName("MenuItemShowMissing");
	popupMenuProduction.add(menuItemShowMissing);

	menuItemShowContent = new JMenuItem("Vis innhold...");
	menuItemShowContent.setName("MenuItemShowContent");

	menuItemDeviation = new JMenuItem("Registrere avvik...");
	menuItemDeviation.setName("MenuItemDeviation");
	popupMenuProduction.add(menuItemDeviation);

	menuItemSetProcent = new JMenuItem("Sett prosent ferdig...");
	menuItemSetProcent.setName("MenuItemSetProcent");
	menuItemSetProcent.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.PROCENT.getColumnName(), menuItemSetProcent);

	menuItemSetEstimatedTimeWall = new JMenuItem("Sett estimert tid vegg...");
	menuItemSetEstimatedTimeWall.setName("MenuItemSetEstimatedTimeWall");
	menuItemSetEstimatedTimeWall.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.ESTIMERT_TID_VEGG.getColumnName(), menuItemSetEstimatedTimeWall);

	menuItemSetEstimatedTimeGavl = new JMenuItem("Sett estimert tid gavl...");
	menuItemSetEstimatedTimeGavl.setName("MenuItemSetEstimatedTimeGavl");
	menuItemSetEstimatedTimeGavl.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.ESTIMERT_TID_GAVL.getColumnName(), menuItemSetEstimatedTimeGavl);

	menuItemSetEstimatedTimePack = new JMenuItem("Sett estimert tid pakk...");
	menuItemSetEstimatedTimePack.setName("MenuItemSetEstimatedTimePack");
	menuItemSetEstimatedTimePack.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.ESTIMERT_TID_PAKK.getColumnName(), menuItemSetEstimatedTimePack);

	menuItemProductionUnitTakstol = new JMenuItem("Sett produksjonsenhet...");
	menuItemProductionUnitTakstol.setName("MenuItemProductionUnitTakstol");
	menuItemProductionUnitTakstol.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.TAKSTOL.getColumnName() + "ProduksjonEnhet", menuItemProductionUnitTakstol);
    }

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

    @Override
    public CheckObject checkDeleteObject(Order object) {
	return null;
    }

    @Override
    public CheckObject checkSaveObject(OrderModel object, PresentationModel presentationModel, WindowInterface window) {
	return null;
    }

    @Override
    public String getAddRemoveString() {
	return null;
    }

    @Override
    public String getClassName() {
	return null;
    }

    @Override
    protected AbstractEditView<OrderModel, Order> getEditView(AbstractViewHandler<Order, OrderModel> handler, Order object, boolean searching) {
	return null;
    }

    @Override
    public Order getNewObject() {
	return null;
    }

    @Override
    public TableModel getTableModel(WindowInterface window) {
	return productionOverviewTableModel;
    }

    @Override
    public String getTableWidth() {
	return null;
    }

    @Override
    public String getTitle() {
	return "Produksjonsoversikt";
    }

    @Override
    public Dimension getWindowSize() {
	return new Dimension(930, 600);
    }

    @Override
    public Boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(userType, "Produksjonsoversikt");
    }

    @Override
    public void setColumnWidth(JXTable table) {
    }

    private enum ProductionColumn {
	ORDRE("Ordre") {
	    @Override
	    public Class<?> getColumnClass() {
		return Transportable.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable;
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return true;
	    }
	},
	TRANSPORT("Transport") {
	    @Override
	    public Class<?> getColumnClass() {
		return Transport.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getTransport();
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return true;
	    }
	},
	PROD_UKE("Prod.uke") {
	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getProductionWeek();
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return true;
	    }
	},
	PAKKLISTE("Pakkliste") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		if (transportable.getPacklistReady() != null) {
		    return Util.SHORT_DATE_FORMAT.format(transportable.getPacklistReady());
		}
		return null;
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers, getColumnName());
		Applyable applyable = getApplyObject(transportable, handler, window);
		setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett pakkliste klar...", "Sett pakkliste ikke klar", handler,
			popupMenuProduction, applyable);
		return true;
	    }
	},
	VEGG("Vegg") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return statusMap.get(statusCheckers.get("Vegg").getArticleName());
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers, getColumnName());
		Applyable applyable = getApplyObject(transportable, handler, window);
		setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett vegg produsert", "Sett vegg ikke produsert", handler,
			popupMenuProduction, applyable);
		return true;
	    }
	},
	PAKK("Pakk") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return statusMap.get(statusCheckers.get("Front").getArticleName());
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers, getColumnName());
		Applyable applyable = getApplyObject(transportable, handler, window);
		setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett front produsert", "Sett front ikke produsert", handler,
			popupMenuProduction, applyable);
		return true;
	    }
	},
	GAVL("Gavl") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return statusMap.get(statusCheckers.get("Gavl").getArticleName());
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers, getColumnName());
		Applyable applyable = getApplyObject(transportable, handler, window);
		setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett gavl produsert", "Sett gavl ikke produsert", handler,
			popupMenuProduction, applyable);
		return true;
	    }
	},
	TAKSTOL("Takstol") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return statusMap.get(statusCheckers.get("Takstol").getArticleName());
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers, getColumnName() + "Produksjon");
		Applyable applyable = getApplyObject(transportable, handler, window);
		// takstol produksjon
		setMenuItem(transportable, menuItemMap.get(getColumnName() + "Produksjon"), "Sett takstol produsert", "Sett takstol ikke produsert",
			handler, popupMenuProduction, applyable);
		// takstol produksjonsenhet
		setMenuItem(transportable, menuItemMap.get(getColumnName() + "ProduksjonEnhet"), "Sett produksjonsenhet...",
			"Sett produksjonsenhet...", handler, popupMenuProduction, applyable);

		// takstolinfo
		setMenuItem(transportable, menuItemMap.get(getColumnName() + "Takstolinfo"), "Takstolinfo...", "Takstolinfo...", handler,
			popupMenuProduction, applyable);

		handler = getHandler(productionPackageHandlers, getColumnName() + "Pakking");
		applyable = getApplyObject(transportable, handler, window);
		// takstol pakking
		setMenuItem(transportable, menuItemMap.get(getColumnName() + "Pakking"), "Sett takstol pakket", "Sett takstol ikke pakket", handler,
			popupMenuProduction, applyable);
		return true;
	    }
	},
	TAKSTEIN("Takstein") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return statusMap.get(statusCheckers.get("Stein").getArticleName());
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers, getColumnName());
		Applyable applyable = getApplyObject(transportable, handler, window);
		setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett takstein pakket", "Sett takstein ikke pakket", handler,
			popupMenuProduction, applyable);
		return true;
	    }
	},
	GULVSPON("Gulvspon") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return statusMap.get(statusCheckers.get("Gulvspon").getArticleName());
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers, getColumnName());
		Applyable applyable = getApplyObject(transportable, handler, window);
		setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett gulvspon pakket", "Sett gulvspon ikke pakket", handler,
			popupMenuProduction, applyable);
		return true;
	    }
	},
	KOMPLETT("Komplett") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		if (transportable.getOrderComplete() != null) {
		    return "Ja";
		}
		return "Nei";
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return true;
	    }
	},
	KLAR("Klar") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		if (transportable.getOrderReady() != null) {
		    return "Ja";
		}
		return "Nei";
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return true;
	    }
	},
	MONTERING("Montering") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		if (transportable.getDoAssembly() != null && transportable.getDoAssembly() == 1) {
		    return "X";
		}
		return "";
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return true;
	    }
	},
	PRODUKTOMRÅDE("Produktområde") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getProductAreaGroup().getProductAreaGroupName();
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return true;
	    }
	},
	// REST("Rest") {
	// @Override
	// public Class<?> getColumnClass() {
	// return Transportable.class;
	// }
	//
	// @Override
	// public Object getValue(Transportable transportable, Map<String,
	// String> statusMap,
	// Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
	// return transportable;
	// }
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// public boolean setMenus(Transportable transportable, Map<String,
	// JMenuItem> menuItemMap, WindowInterface window,
	// Map<String, AbstractProductionPackageViewHandler>
	// productionPackageHandlers, JPopupMenu popupMenuProduction) {
	// return true;
	// }
	// },
	PROCENT("%") {
	    @Override
	    public Class<?> getColumnClass() {
		return ProcentDone.class;
	    }

	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getLastProcentDone();
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		popupMenuProduction.add(menuItemMap.get(getColumnName()));
		return true;
	    }
	},
	TIDSBRUK_PAKKLISTE("Tidsbruk pakkliste") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getOrder().getPacklistDuration();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		// TODO Auto-generated method stub
		return false;
	    }
	},
	LAGET_PAKKLISTE("Laget pakkliste") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getOrder().getPacklistDoneBy();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		// TODO Auto-generated method stub
		return false;
	    }
	},
	ORDNR("Ordnr") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getOrderNr();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return false;
	    }
	},
	AVDELING("Avdeling") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getOrder().getProductArea().getProductArea();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return false;
	    }
	},
	TID_VEGG("Tid vegg") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		Order order = transportable.getOrder();

		OrderLine vegg = order.getOrderLine("Vegg");
		return vegg.getRealProductionHours() == null ? Tidsforbruk.beregnTidsforbruk(vegg.getActionStarted(), vegg.getProduced()) : vegg
			.getRealProductionHours();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		return false;
	    }
	},
	TID_GAVL("Tid gavl") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		Order order = transportable.getOrder();

		OrderLine gavl = order.getOrderLine("Gavl");
		return gavl.getRealProductionHours() == null ? Tidsforbruk.beregnTidsforbruk(gavl.getActionStarted(), gavl.getProduced()) : gavl
			.getRealProductionHours();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		// TODO Auto-generated method stub
		return false;
	    }
	},
	ESTIMERT_TID_VEGG("Estimert tid vegg") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getOrder().getEstimatedTimeWall();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		popupMenuProduction.add(menuItemMap.get(getColumnName()));
		return true;
	    }
	},
	ESTIMERT_TID_GAVL("Estimert tid gavl") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getOrder().getEstimatedTimeGavl();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		popupMenuProduction.add(menuItemMap.get(getColumnName()));
		return true;
	    }
	},
	ESTIMERT_TID_PAKK("Estimert tid pakk") {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap,
		    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		return transportable.getOrder().getEstimatedTimePack();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return BigDecimal.class;
	    }

	    @Override
	    public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		    Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction) {
		popupMenuProduction.add(menuItemMap.get(getColumnName()));
		return true;
	    }
	}
	// ,
	// KALKULERT_TIDSFORBRUK("Kalkulert tidsforbruk") {
	// @Override
	// public Class<?> getColumnClass() {
	// return BigDecimal.class;
	// }
	//
	// @Override
	// public Object getValue(Transportable transportable, Map<String,
	// String> statusMap,
	// Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
	// return
	// Tidsforbruk.beregnTidsforbruk(transportable.getActionStarted(),
	// transportable.getProduced());
	// }
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// public boolean setMenus(Transportable transportable, Map<String,
	// JMenuItem> menuItemMap, WindowInterface window,
	// Map<String, AbstractProductionPackageViewHandler>
	// productionPackageHandlers, JPopupMenu popupMenuProduction) {
	// popupMenuProduction.add(menuItemMap.get(getColumnName()));
	// return true;
	// }
	// }
	;

	private String columnName;

	private ProductionColumn(String aColumnName) {
	    columnName = aColumnName;
	}

	public String getColumnName() {
	    return columnName;
	}

	private static void setMenuItem(final Transportable transportable, final JMenuItem menuItem, final String applyString,
		final String unapplyString, final AbstractProductionPackageViewHandler<Applyable> handler, JPopupMenu popupMenuProduction,
		final Applyable applyable) {
	    if (applyable != null) {
		if ((transportable instanceof PostShipment && applyable.isForPostShipment()) || transportable instanceof Order) {
		    if (handler.getButtonApplyEnabled(applyable)) {
			menuItem.setText(applyString);
		    } else {
			menuItem.setText(unapplyString);
		    }
		    popupMenuProduction.add(menuItem);
		}

	    }
	}

	private static Applyable getApplyObject(final Transportable transportable, final AbstractProductionPackageViewHandler<Applyable> handler,
		final WindowInterface window) {
	    return handler != null ? handler.getApplyObject(transportable, window) : null;

	}

	@SuppressWarnings("unchecked")
	private static AbstractProductionPackageViewHandler<Applyable> getHandler(
		final Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, final String menuItemName) {
	    return productionPackageHandlers.get(menuItemName);
	}

	public static String[] getColumnNames() {
	    ProductionColumn[] columns = ProductionColumn.values();

	    List<String> columnNameList = new ArrayList<String>();
	    for (int i = 0; i < columns.length; i++) {
		columnNameList.add(columns[i].getColumnName());
	    }
	    String[] columnNames = new String[columnNameList.size()];
	    return columnNameList.toArray(columnNames);
	}

	public abstract Object getValue(Transportable transportable, Map<String, String> statusMap,
		Map<String, StatusCheckerInterface<Transportable>> statusCheckers);

	public abstract Class<?> getColumnClass();

	@SuppressWarnings("unchecked")
	public abstract boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap, WindowInterface window,
		Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers, JPopupMenu popupMenuProduction);
    }

    public final class ProductionOverviewTableModel extends AbstractTableAdapter {

	ProductionOverviewTableModel(ListModel listModel) {
	    super(listModel, ProductionColumn.getColumnNames());

	}

	public Transportable getTransportable(int rowIndex) {
	    return (Transportable) getRow(rowIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
	    Transportable transportable = (Transportable) getRow(rowIndex);

	    Map<String, String> statusMap = Util.createStatusMap(transportable.getStatus());
	    String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_").replaceAll("\\.", "_")
		    .replaceAll("\\%", "PROCENT");
	    if (!Hibernate.isInitialized(transportable.getOrderLines())) {
		((OrderManager) overviewManager).lazyLoadOrder((Order) transportable, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES,
			LazyLoadOrderEnum.PROCENT_DONE });
	    }
	    return ProductionColumn.valueOf(columnName).getValue(transportable, statusMap, statusCheckers);

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_").replaceAll("\\.", "_")
		    .replaceAll("\\%", "PROCENT");
	    return ProductionColumn.valueOf(columnName).getColumnClass();
	}

	public Transportable getObjectAtRow(int rowIndex) {
	    return (Transportable) getRow(rowIndex);
	}

    }

    void cacheComment(Transportable transportable, WindowInterface window, boolean load) {
	if (transportable instanceof Order) {
	    if (load) {
		OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		orderManager.lazyLoadOrder((Order) transportable, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS, LazyLoadOrderEnum.COLLIES });
	    }
	    transportable.cacheComments();
	} else {
	    if (load) {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
		postShipmentManager.lazyLoad((PostShipment) transportable, new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_COMMENTS,
			LazyLoadPostShipmentEnum.COLLIES });
	    }
	    transportable.cacheComments();
	}
    }

    void initTransportable(Transportable transportable, WindowInterface window) {
	PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
	CustTrManager custTrManager = (CustTrManager) ModelUtil.getBean("custTrManager");
	Set<String> checkers = statusCheckers.keySet();
	Map<String, String> statusMap;

	String status;
	StatusCheckerInterface<Transportable> checker;
	boolean orderLoaded = false;
	boolean needToSave = false;

	transportable.setCustTrs(custTrManager.findByOrderNr(transportable.getOrder().getOrderNr()));

	statusMap = Util.createStatusMap(transportable.getStatus());
	for (String checkerName : checkers) {
	    checker = statusCheckers.get(checkerName);
	    status = statusMap.get(checker.getArticleName());

	    if (status == null) {
		needToSave = true;
		if (!orderLoaded && transportable instanceof Order) {
		    ((OrderManager) overviewManager).lazyLoadOrder((Order) transportable, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES,
			    LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.COMMENTS,
			    LazyLoadOrderEnum.PROCENT_DONE });
		    orderLoaded = true;
		} else if (!orderLoaded && transportable instanceof PostShipment) {
		    postShipmentManager.lazyLoad((PostShipment) transportable, new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES,
			    LazyLoadPostShipmentEnum.ORDER_LINES, LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
			    LazyLoadPostShipmentEnum.ORDER_COMMENTS });
		    orderLoaded = true;
		}
		status = checker.getArticleStatus(transportable);

	    }
	    statusMap.put(checker.getArticleName(), status);

	}
	transportable.setStatus(Util.statusMapToString(statusMap));

	if (transportable.getComment() == null) {
	    needToSave = true;
	    cacheComment(transportable, window, !orderLoaded);
	    orderLoaded = true;
	}

	if (needToSave) {
	    if (transportable instanceof Order) {
		try {
		    ((OrderManager) overviewManager).saveOrder((Order) transportable);
		} catch (ProTransException e) {
		    Util.showErrorDialog(window, "Feil", e.getMessage());
		    e.printStackTrace();
		}
	    } else {
		postShipmentManager.savePostShipment((PostShipment) transportable);
	    }
	}
	if (transportable instanceof Order && !Hibernate.isInitialized(((Order) transportable).getProcentDones())) {
	    ((OrderManager) overviewManager).lazyLoadOrder(((Order) transportable), new LazyLoadOrderEnum[] { LazyLoadOrderEnum.PROCENT_DONE });
	}

    }

    private void initOrders(List<Transportable> transportables, WindowInterface window) {
	if (transportables != null) {

	    for (Transportable transportable : transportables) {
		initTransportable(transportable, window);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initObjects() {
	if (!loaded) {

	    if (table != null && table.getModel().getRowCount() > 1) {
		table.setRowSelectionInterval(1, 1);
	    }
	    objectList.clear();
	    objectSelectionList.clearSelection();

	    List<Order> allOrders = ((OrderManager) overviewManager).findAllNotSent();
	    if (allOrders != null) {
		objectList.addAll(allOrders);
	    }
	    PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
	    List<PostShipment> allPostShipments = postShipmentManager.findAllNotSent();
	    if (allPostShipments != null) {
		objectList.addAll(allPostShipments);
	    }
	    Collections.sort(objectList, new TransportableComparator());
	    noOfObjects = objectList.getSize();
	    if (table != null) {
		table.scrollRowToVisible(0);
	    }

	}
    }

    public JButton getButtonRefresh(WindowInterface window) {
	JButton button = new RefreshButton(this, window);
	button.setName("ButtonRefresh");
	setupMenuListeners(window);
	return button;
    }

    public JButton getButtonSearch(WindowInterface window) {
	JButton button = new JButton(new SearchAction(window));
	button.setName("SearchOrder");
	return button;
    }

    public JCheckBox getCheckBoxFilter() {
	checkBoxFilter = new JCheckBox("Vis ferdige");
	checkBoxFilter.setSelected(true);
	checkBoxFilter.setName("CheckBoxFilter");
	checkBoxFilter.addActionListener(new FilterActionListener());
	return checkBoxFilter;
    }

    public JComboBox getComboBoxProductAreaGroup() {
	return Util.getComboBoxProductAreaGroup(login.getApplicationUser(), userType, productAreaGroupModel);
    }

    @SuppressWarnings("unchecked")
    @Override
    public JXTable getTable(WindowInterface window) {
	initObjects();
	initOrders(objectList, window);

	ColorHighlighter readyHighlighter = new ColorHighlighter(new PatternPredicate("Ja", 10), ColorEnum.GREEN.getColor(), null);
	ColorHighlighter startedPackingHighlighter = new ColorHighlighter(new PatternPredicate("Ja", 11), ColorEnum.YELLOW.getColor(), null);

	table = new JXTable();
	productionOverviewTableModel = new ProductionOverviewTableModel(objectList);
	table.setModel(productionOverviewTableModel);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.setSelectionModel(new SingleListSelectionAdapter(objectSelectionList.getSelectionIndexHolder()));
	table.setColumnControlVisible(true);
	table.setColumnControl(new UBColumnControlPopup(table, this));

	table.addMouseListener(new TableClickHandler(window));

	table.setRowHeight(40);

	table.getColumnModel().getColumn(0).setCellRenderer(new TextPaneRendererTransport());

	table.addHighlighter(HighlighterFactory.createAlternateStriping());
	table.addHighlighter(startedPackingHighlighter);
	table.addHighlighter(readyHighlighter);

	// ordre
	table.getColumnExt(0).setPreferredWidth(220);
	// transport
	table.getColumnExt(1).setPreferredWidth(150);
	// prod uke
	table.getColumnExt(2).setPreferredWidth(70);
	DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
	tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	table.getColumnExt(2).setCellRenderer(tableCellRenderer);
	// pakkliste
	table.getColumnExt(3).setPreferredWidth(80);

	// vegg
	table.getColumnExt(4).setPreferredWidth(45);
	table.getColumnExt(4).setCellRenderer(tableCellRenderer);
	// pakk
	table.getColumnExt(5).setPreferredWidth(45);
	table.getColumnExt(5).setCellRenderer(tableCellRenderer);
	// gavl
	table.getColumnExt(6).setPreferredWidth(45);
	table.getColumnExt(6).setCellRenderer(tableCellRenderer);
	// takstol
	table.getColumnExt(7).setPreferredWidth(60);
	table.getColumnExt(7).setCellRenderer(tableCellRenderer);
	// //takstein
	table.getColumnExt(8).setPreferredWidth(60);
	table.getColumnExt(8).setCellRenderer(tableCellRenderer);
	// gulvspon
	table.getColumnExt(9).setPreferredWidth(70);
	table.getColumnExt(9).setCellRenderer(tableCellRenderer);
	// montering
	table.getColumnExt(12).setPreferredWidth(70);
	table.getColumnExt(12).setCellRenderer(tableCellRenderer);
	// rest
	// table.getColumnExt(14).setPreferredWidth(50);
	// %
	table.getColumnExt(14).setPreferredWidth(40);

	// table.getColumnModel().getColumn(14).setCellRenderer(new
	// TextPaneRendererCustTr());
	table.getColumnModel().getColumn(14).setCellRenderer(new TextPaneRendererProcentDone());

	table.getColumnExt(10).setVisible(false);
	table.getColumnExt(10).setVisible(false);
	table.getColumnExt(11).setVisible(false);
	// table.getColumnExt(10).setVisible(false);
	// table.getColumnExt(11).setVisible(false);
	//

	// setupMenuListeners(window);

	// table.setName("ProductionTable");
	table.setName(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());
	return table;

    }

    private void setupMenuListeners(WindowInterface window) {
	menuItemPacklist.addActionListener(new ProductionMenuItemListener(window, "Pakkliste", "Sett pakkliste klar..."));
	menuItemVegg.addActionListener(new ProductionMenuItemListener(window, "Vegg", "Sett vegg produsert"));
	menuItemFront.addActionListener(new ProductionMenuItemListener(window, "Front", "Sett front produsert"));
	menuItemGavl.addActionListener(new ProductionMenuItemListener(window, "Gavl", "Sett gavl produsert"));
	menuItemProductionTakstol.addActionListener(new ProductionMenuItemListener(window, "TakstolProduksjon", "Sett takstol produsert"));
	menuItemPackageTakstol.addActionListener(new ProductionMenuItemListener(window, "TakstolPakking", "Sett takstol pakket"));
	menuItemTakstein.addActionListener(new ProductionMenuItemListener(window, "Takstein", "Sett takstein pakket"));
	menuItemGulvspon.addActionListener(new ProductionMenuItemListener(window, "Gulvspon", "Sett gulvspon pakket"));
	menuItemOpenOrder.addActionListener(new MenuItemListenerOpenOrder(window));
	menuItemSetProductionWeek.addActionListener(new MenuItemListenerSetProductionWeek(window));
	menuItemShowMissing.addActionListener(new MenuItemListenerShowMissing(window));
	menuItemShowContent.addActionListener(new MenuItemListenerShowContent(window));
	menuItemDeviation.addActionListener(new MenuItemListenerDeviation(window));
	menuItemSetProcent.addActionListener(new MenuItemListenerSetProcent(window));
	menuItemSetEstimatedTimeWall.addActionListener(new MenuItemListenerSetEstimatedTimeWall(window));
	menuItemSetEstimatedTimeGavl.addActionListener(new MenuItemListenerSetEstimatedTimeGavl(window));
	menuItemSetEstimatedTimePack.addActionListener(new MenuItemListenerSetEstimatedTimePack(window));
	menuItemProductionUnitTakstol.addActionListener(setProductionUnitActionFactory.create(articleTypeTakstol, this, window));

	menuItemShowTakstolInfo = new JMenuItem(showTakstolInfoActionFactory.create(this, window));
	menuItemShowTakstolInfo.setName("MenuItemShowTakstolInfo");
	menuItemShowTakstolInfo.setEnabled(hasWriteAccess());
	menuItemMap.put(ProductionColumn.TAKSTOL.getColumnName() + "Takstolinfo", menuItemShowTakstolInfo);
    }

    /**
     * Henter vindustittel
     * 
     * @return tittel
     */
    public String getWindowTitle() {
	return "Produksjonsoversikt";
    }

    /**
     * Åpner ordrevindu
     * 
     * @param order
     * @param window
     */
    void openOrderView(Transportable transportable, WindowInterface window) {
	@SuppressWarnings("unused")
	boolean success = transportable != null ? orderViewHandler.openOrderView(transportable.getOrder(), false, window) : false;
    }

    /**
     * Håndterer klikk i tabell
     * 
     * @author atle.brekka
     */
    final class TableClickHandler extends MouseAdapter {
	/**
         * 
         */
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public TableClickHandler(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@SuppressWarnings({ "synthetic-access" })
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
	    Util.setWaitCursor(window.getComponent());
	    Transportable transportable = getSelectedTransportable();
	    if (SwingUtilities.isLeftMouseButton(mouseEvent) && mouseEvent.getClickCount() == 2) {
		openOrderView(transportable, window);

	    } else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
		setMenuItems(mouseEvent, transportable);

	    }
	    Util.setDefaultCursor(window.getComponent());
	}

	private void setMenuItems(MouseEvent mouseEvent, Transportable transportable) {
	    int col = table.columnAtPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));

	    removeMenuItems();

	    if (transportable instanceof PostShipment) {
		popupMenuProduction.add(menuItemShowContent);
	    } else {
		popupMenuProduction.add(menuItemDeviation);
	    }
	    String columnHeader = StringUtils.upperCase((String) table.getColumnExt(col).getHeaderValue()).replaceAll(" ", "_")
		    .replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
	    ;
	    ProductionColumn productionColumn = ProductionColumn.valueOf(columnHeader);
	    boolean success = transportable != null ? productionColumn.setMenus(transportable, menuItemMap, window, productionPackageHandlers,
		    popupMenuProduction) : false;
	    if (success) {
		popupMenuProduction.show((JXTable) mouseEvent.getSource(), mouseEvent.getX(), mouseEvent.getY());
	    }
	}

	private void removeMenuItems() {
	    Collection<JMenuItem> menuItems = menuItemMap.values();
	    for (JMenuItem menuItem : menuItems) {
		popupMenuProduction.remove(menuItem);
	    }

	    popupMenuProduction.remove(menuItemShowContent);
	    popupMenuProduction.remove(menuItemDeviation);
	}

	private Transportable getSelectedTransportable() {
	    Transportable transportable = null;
	    if (objectSelectionList.getSelection() != null) {
		int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		transportable = (Transportable) objectSelectionList.getElementAt(index);
	    }
	    return transportable;
	}
    }

    /**
     * Håndterer menyvalg for pakkliste
     * 
     * @author atle.brekka
     */
    private class ProductionMenuItemListener implements ActionListener {
	private WindowInterface window;

	private String applyString;
	private AbstractProductionPackageViewHandler<Applyable> handler;

	public ProductionMenuItemListener(WindowInterface aWindow, String aStatusName, String aApplyString) {
	    window = aWindow;
	    applyString = aApplyString;
	    handler = productionPackageHandlers.get(aStatusName);
	}

	public void actionPerformed(ActionEvent actionEvent) {
	    Util.setWaitCursor(window.getComponent());
	    Transportable transportable = null;
	    if (objectSelectionList.getSelection() != null) {
		int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		transportable = (Transportable) objectSelectionList.getElementAt(index);
	    }
	    if (transportable != null) {
		boolean apply = false;
		if (actionEvent.getActionCommand().equalsIgnoreCase(applyString)) {
		    apply = true;
		}
		if (handler != null) {

		    handler.setApplied(handler.getApplyObject(transportable, window), apply, window);
		    handler.clearApplyObject();
		}

		if (transportable instanceof Order) {
		    ((OrderManager) overviewManager).refreshObject((Order) transportable);
		} else {
		    PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
		    postShipmentManager.refreshObject((PostShipment) transportable);
		}
		initTransportable(transportable, window);
		Util.setDefaultCursor(window.getComponent());
	    }
	}

    }

    /**
     * Håndterer menyvalg for å åpne ordre
     * 
     * @author atle.brekka
     */
    private class MenuItemListenerOpenOrder implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerOpenOrder(WindowInterface aWindow) {
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent actionEvent) {
	    Transportable transportable = getSelectedObject();
	    if (transportable != null) {
		openOrderView(transportable.getOrder(), window);
	    }
	}

    }

    private class MenuItemListenerSetProductionWeek implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerSetProductionWeek(WindowInterface aWindow) {
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent actionEvent) {

	    Transportable transportable = getSelectedObject();
	    if (transportable != null) {
		Order order = transportable.getOrder();
		String newProductionWeek = Util.showInputDialogWithdefaultValue(window, "Produksjonsuke", "Produksjonsuke",
			order.getProductionWeek() == null ? "" : String.valueOf(order.getProductionWeek()));
		if (newProductionWeek != null) {

		    order.setProductionWeek(Integer.valueOf(newProductionWeek));
		    ((OrderManager) overviewManager).saveOrder(order);
		}
	    }
	}

    }

    public Transportable getSelectedObject() {
	Transportable transportable = null;
	if (objectSelectionList.getSelection() != null) {
	    int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
	    transportable = (Transportable) objectSelectionList.getElementAt(index);
	}
	return transportable;
    }

    /**
     * Håndterer menyvalg for å visse mangler
     * 
     * @author atle.brekka
     */
    private class MenuItemListenerShowMissing implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerShowMissing(WindowInterface aWindow) {
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent actionEvent) {
	    Transportable transportable = null;
	    if (objectSelectionList.getSelection() != null) {
		int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		transportable = (Transportable) objectSelectionList.getElementAt(index);
	    }
	    if (transportable != null) {
		TransportViewHandler.showMissingColliesForTransportable(transportable, window);

	    }
	}

    }

    /**
     * Håndterer menyvalg for å vise innhold
     * 
     * @author atle.brekka
     */
    private class MenuItemListenerShowContent implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerShowContent(WindowInterface aWindow) {
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent actionEvent) {
	    Transportable transportable = null;
	    if (objectSelectionList.getSelection() != null) {
		int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		transportable = (Transportable) objectSelectionList.getElementAt(index);
	    }
	    if (transportable != null && transportable instanceof PostShipment) {
		RouteViewHandler.showContentForPostShipment((PostShipment) transportable, window);

	    }
	}

    }

    /**
     * Håndterer menyvalg for å registrere avvik
     * 
     * @author atle.brekka
     */
    class MenuItemListenerDeviation implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerDeviation(WindowInterface aWindow) {
	    window = aWindow;

	}

	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemDeviation.getText())) {
		Transportable transportable = null;
		if (objectSelectionList.getSelection() != null) {
		    int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		    transportable = (Transportable) objectSelectionList.getElementAt(index);
		}

		if (transportable != null && transportable instanceof Order) {
		    DeviationViewHandler deviationViewHandler = deviationViewHandlerFactory.create((Order) transportable, true, false, true, null,
			    true);
		    deviationViewHandler.registerDeviation((Order) transportable, window);
		}

	    }
	}

    }

    class MenuItemListenerSetProcent implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerSetProcent(WindowInterface aWindow) {
	    window = aWindow;

	}

	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemSetProcent.getText())) {
		Transportable transportable = null;
		if (objectSelectionList.getSelection() != null) {
		    int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		    transportable = (Transportable) objectSelectionList.getElementAt(index);
		}

		if (transportable != null && transportable instanceof Order) {
		    setProcentForOrder((Order) transportable, window);
		}

	    }
	}

    }

    class MenuItemListenerSetEstimatedTimeWall implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerSetEstimatedTimeWall(WindowInterface aWindow) {
	    window = aWindow;

	}

	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemSetEstimatedTimeWall.getText())) {
		Transportable transportable = null;
		if (objectSelectionList.getSelection() != null) {
		    int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		    transportable = (Transportable) objectSelectionList.getElementAt(index);
		}

		if (transportable != null && transportable instanceof Order) {
		    setEstimatedTimewallForOrder((Order) transportable, window);
		}

	    }
	}

    }

    class MenuItemListenerSetEstimatedTimeGavl implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerSetEstimatedTimeGavl(WindowInterface aWindow) {
	    window = aWindow;

	}

	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemSetEstimatedTimeGavl.getText())) {
		Transportable transportable = null;
		if (objectSelectionList.getSelection() != null) {
		    int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		    transportable = (Transportable) objectSelectionList.getElementAt(index);
		}

		if (transportable != null && transportable instanceof Order) {
		    setEstimatedTimeGavlForOrder((Order) transportable, window);
		}

	    }
	}

    }

    class MenuItemListenerSetEstimatedTimePack implements ActionListener {
	private WindowInterface window;

	public MenuItemListenerSetEstimatedTimePack(WindowInterface aWindow) {
	    window = aWindow;

	}

	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemSetEstimatedTimePack.getText())) {
		Transportable transportable = null;
		if (objectSelectionList.getSelection() != null) {
		    int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		    transportable = (Transportable) objectSelectionList.getElementAt(index);
		}

		if (transportable != null && transportable instanceof Order) {
		    setEstimatedTimePackForOrder((Order) transportable, window);
		}

	    }
	}

    }

    private void setProcentForOrder(final Order order, final WindowInterface aWindow) {
	ProcentDoneModel procentDoneModel = new ProcentDoneModel(new ProcentDone(null, null, null, null, order, null, null, null));
	ProcentDoneViewHandler procentDoneViewHandler = new ProcentDoneViewHandler(userType, managerRepository.getProcentDoneManager());
	EditProcentDoneView procentDoneView = new EditProcentDoneView(false, procentDoneModel, procentDoneViewHandler);
	Util.showEditViewable(procentDoneView, aWindow);

	if (!procentDoneView.isCanceled()) {
	    handleProcentDone(order, aWindow, procentDoneModel);

	}
    }

    private void setEstimatedTimewallForOrder(final Order order, final WindowInterface aWindow) {
	BigDecimal estimatedProductionHours = order.getEstimatedTimeWall();
	String estimertTidsforbrukString = Util.showInputDialogWithdefaultValue(null, "Sett estimert tidsforbruk", "Tidsforbruk:",
		estimatedProductionHours == null ? "" : String.valueOf(estimatedProductionHours));

	BigDecimal estimertTidsforbruk = StringUtils.isBlank(estimertTidsforbrukString) ? null : new BigDecimal(StringUtils.replace(
		estimertTidsforbrukString, ",", "."));
	order.setEstimatedTimeWall(estimertTidsforbruk);

	try {
	    ((OrderManager) overviewManager).saveOrder(order);
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}
    }

    private void setEstimatedTimeGavlForOrder(final Order order, final WindowInterface aWindow) {
	BigDecimal estimatedProductionHours = order.getEstimatedTimeGavl();
	String estimertTidsforbrukString = Util.showInputDialogWithdefaultValue(null, "Sett estimert tidsforbruk", "Tidsforbruk:",
		estimatedProductionHours == null ? "" : String.valueOf(estimatedProductionHours));

	BigDecimal estimertTidsforbruk = StringUtils.isBlank(estimertTidsforbrukString) ? null : new BigDecimal(StringUtils.replace(
		estimertTidsforbrukString, ",", "."));
	order.setEstimatedTimeGavl(estimertTidsforbruk);

	try {
	    ((OrderManager) overviewManager).saveOrder(order);
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}
    }

    private void setEstimatedTimePackForOrder(final Order order, final WindowInterface aWindow) {
	BigDecimal estimatedProductionHours = order.getEstimatedTimePack();
	String estimertTidsforbrukString = Util.showInputDialogWithdefaultValue(null, "Sett estimert tidsforbruk", "Tidsforbruk:",
		estimatedProductionHours == null ? "" : String.valueOf(estimatedProductionHours));

	BigDecimal estimertTidsforbruk = StringUtils.isBlank(estimertTidsforbrukString) ? null : new BigDecimal(StringUtils.replace(
		estimertTidsforbrukString, ",", "."));
	order.setEstimatedTimePack(estimertTidsforbruk);

	try {
	    ((OrderManager) overviewManager).saveOrder(order);
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}
    }

    private void handleProcentDone(final Order order, final WindowInterface aWindow, final ProcentDoneModel procentDoneModel) {
	ProcentDone newProcentDone = procentDoneModel.getObject();
	CheckObject checkObject = checkProcentDone(newProcentDone, order);

	if (checkObject != null) {
	    if (Util.showConfirmDialog(aWindow.getComponent(), "Lagre?", checkObject.getMsg())) {
		if (checkObject.getRefObject() != null) {
		    newProcentDone = (ProcentDone) checkObject.getRefObject();
		    order.clearProcentDoneCache();
		} else {
		    order.addProcentDone(newProcentDone);
		}

	    } else {
		return;
	    }
	} else {
	    order.addProcentDone(newProcentDone);
	}

	newProcentDone.setProcent(procentDoneModel.getProcent());
	newProcentDone.setProcentDoneComment(procentDoneModel.getProcentDoneComment());

	newProcentDone.setChangeDate(Util.getCurrentDate());
	newProcentDone.setUserName(login.getApplicationUser().getUserName());
	try {
	    ((OrderManager) overviewManager).saveOrder(order);
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}

    }

    private CheckObject checkProcentDone(ProcentDone newProcentDone, Order order) {
	ProcentDone procentDone = order.getProcentDone(newProcentDone);
	CheckObject checkObject = null;
	if (procentDone != null) {
	    checkObject = new CheckObject("Det er allerde registert en prosent " + procentDone.getProcent()
		    + " for denne uken. Vil du overskrive denne?", true, procentDone);
	} else if (order.getLastProcentDone() != null && order.getLastProcentDone().getProcent() > newProcentDone.getProcent()) {
	    checkObject = new CheckObject("Forrige prosent " + order.getLastProcentDone() + " er høyere enn denne. Vil du likevel lagre denne?",
		    true, null);
	}
	return checkObject;
    }

    /**
     * Håndterer setting av filter
     * 
     * @author atle.brekka
     */
    class FilterPropertyChangeListener implements PropertyChangeListener {

	public void propertyChange(PropertyChangeEvent evt) {
	    handleFilter();

	}

    }

    @SuppressWarnings("unchecked")
    @Override
    public void doRefresh(WindowInterface window) {
	initObjects();
	initOrders(objectList, window);
    }

    /**
     * Søking
     * 
     * @author atle.brekka
     */
    private class SearchAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	public SearchAction(WindowInterface aWindow) {
	    super("Søk ordre...");
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent arg0) {
	    doSearch(window);

	}
    }

    /**
     * Søker ordre
     * 
     * @param window
     */
    void doSearch(WindowInterface window) {
	checkBoxFilter.setSelected(true);
	handleFilter();
	Transportable transportable = orderViewHandler.searchOrder(window, true);
	if (transportable != null) {
	    if (objectList.contains(transportable)) {
		int index = objectList.indexOf(transportable);
		objectSelectionList.setSelectionIndex(table.convertRowIndexToView(index));
		table.scrollRowToVisible(objectSelectionList.getSelectionIndex());
	    }
	} else {
	    Util.showMsgDialog(window.getComponent(), "Fant ikke ordre", "Ordre med  ble ikke funnet");
	}

    }

    /**
     * Håndterer endring av filter
     * 
     * @author atle.brekka
     */
    class FilterActionListener implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
	    handleFilter();

	}
    }

    /**
     * Håndterer filtrering
     */
    protected void handleFilter() {
	table.clearSelection();
	objectSelectionList.clearSelection();

	ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
	PrefsUtil.setInvisibleColumns(group.getProductAreaGroupName(), table.getName(), table);
	group = group.getProductAreaGroupName().equalsIgnoreCase("Alle") ? ProductAreaGroup.UNKNOWN : group;

	List<Filter> filterList = new ArrayList<Filter>();

	if (!checkBoxFilter.isSelected()) {
	    PatternFilter filterDone = new PatternFilter("Nei", Pattern.CASE_INSENSITIVE, 10);
	    filterList.add(filterDone);
	}
	if (group != ProductAreaGroup.UNKNOWN) {
	    if (!group.getProductAreaGroupName().equalsIgnoreCase("Takstol")) {
		filterList.add(new PatternFilter(group.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE, 13));
	    } else {
		filterList.add(new PatternFilter(".*e.*", Pattern.CASE_INSENSITIVE, 7));
	    }

	}
	if (filterList.size() != 0) {
	    Filter[] filterArray = new Filter[filterList.size()];
	    table.setFilters(new FilterPipeline(filterList.toArray(filterArray)));
	} else {
	    table.setFilters(null);
	}
	table.repaint();

    }

    public String getProductAreaGroupName() {
	return ((ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP)).getProductAreaGroupName();
    }

    @Override
    public void beforeClose() {
	PrefsUtil
		.putUserInvisibleColumns(table, (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
    }

    public JButton getButtonShowTakstolInfo(WindowInterface window) {
	buttonShowTakstolInfo = new JButton(showTakstolInfoActionFactory.create(this, window));

	return null;
    }

    public String getSelectedOrderNr() {
	Transportable transportable = getSelectedObject();
	return transportable != null ? transportable.getOrderNr() : null;
    }

    public Produceable getSelectedProduceable() {
	Transportable transportable = getSelectedObject();

	if (transportable != null) {
	    ProductionViewHandler handler = (ProductionViewHandler) productionPackageHandlers.get("TakstolProduksjon");
	    if (handler != null) {
		return handler.getApplyObject(transportable, window);
	    }
	}
	return null;
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
		String fileName = "Produksjonsoversikt_" + Util.getCurrentDateAsDateTimeString() + ".xls";
		String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");

		JXTable tableReport = new JXTable(new ProductionOverviewTableModel(objectList));

		ExcelUtil.showDataInExcel(excelDirectory, fileName, null, "Produksjonsoversikt", tableReport, null, null, 16, false);
	    } catch (ProTransException e) {
		e.printStackTrace();
		Util.showErrorDialog(window, "Feil", e.getMessage());
	    }
	    Util.setDefaultCursor(window.getComponent());

	}
    }

}
