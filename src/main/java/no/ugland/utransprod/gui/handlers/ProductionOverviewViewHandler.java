package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
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
import no.ugland.utransprod.gui.model.TextPaneRendererCustTr;
import no.ugland.utransprod.gui.model.TextPaneRendererProcentDone;
import no.ugland.utransprod.gui.model.TextPaneRendererTransport;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.Order;
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
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.TransportableComparator;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

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
public class ProductionOverviewViewHandler extends
		DefaultAbstractViewHandler<Order, OrderModel> implements
		ProductAreaGroupProvider, OrderNrProvider,ProduceableProvider {

	private static final long serialVersionUID = 1L;

	/**
	 * Statussjekkere
	 */
	Map<String, StatusCheckerInterface<Transportable>> statusCheckers;

	/**
	 * Hjelpeklasse for å viseordre
	 */
	OrderViewHandler orderViewHandler;

	/**
	 * Høyreklikkmeny i tabell for produksjonsoversikt
	 */
	JPopupMenu popupMenuProduction;

	/**
	 * Meny for å sette pakliste klar/ikke klar
	 */
	JMenuItem menuItemPacklist;

	JMenuItem menuItemVegg;

	JMenuItem menuItemOpenOrder;

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

	// ApplicationUser applicationUser;
	private Login login;

	/**
	 * Tabellmodell for produksjonsoversikttabell
	 */
	private TableModel productionOverviewTableModel;

	@SuppressWarnings("unchecked")
	Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers;

	private List<ProductAreaGroup> productAreaGroupList;

	PresentationModel productAreaGroupModel;

	JCheckBox checkBoxFilter;
	private VismaFileCreator vismaFileCreator;
	private Map<String, JMenuItem> menuItemMap;

	// private ProcentDoneManager procentDoneManager;

	// private DeviationManager deviationManager;

	private ManagerRepository managerRepository;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;

	private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;

	private JButton buttonShowTakstolInfo;

	private JMenuItem menuItemShowTakstolInfo;

	private SetProductionUnitActionFactory setProductionUnitActionFactory;
	private ArticleType articleTypeTakstol;

	@Inject
	public ProductionOverviewViewHandler(
			final VismaFileCreator aVismaFileCreator,
			final OrderViewHandlerFactory orderViewHandlerFactory,
			final Login aLogin, ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			final ShowTakstolInfoActionFactory aShowTakstolInfoActionFactory,
			final @Named("takstolArticle") ArticleType aArticleTypeTakstol,
			//final SetProductionUnitActionFactory setProductionUnitActionFactory,
			final TakstolPackageApplyList takstolPackageApplyList,
			final TakstolProductionApplyList takstolProductionApplyList,SetProductionUnitActionFactory aSetProductionUnitActionFactory,
			@Named("kostnadTypeTakstoler")CostType aCostTypeTross, @Named("kostnadEnhetTakstoler")CostUnit aCostUnitTross) {
		super("Produksjonsoversikt", aManagerRepository.getOrderManager(),
				aLogin.getUserType(), true);
		articleTypeTakstol=aArticleTypeTakstol;
		setProductionUnitActionFactory=aSetProductionUnitActionFactory;
		showTakstolInfoActionFactory = aShowTakstolInfoActionFactory;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		managerRepository = aManagerRepository;
		// deviationManager = aDeviationManager;
		// procentDoneManager = aProcentDoneManager;
		login = aLogin;
		vismaFileCreator = aVismaFileCreator;
		// applicationUser = aApplicationUser;
		orderViewHandler = orderViewHandlerFactory.create(true);
		statusCheckers = Util.getStatusCheckersTransport(managerRepository);
		statusCheckers.put("Vegg", Util.getVeggChecker());
		statusCheckers.put("Front", Util.getFrontChecker());

		setupMenus();

		productionPackageHandlers = Util.getProductionPackageHandlers(
				vismaFileCreator, login, orderViewHandlerFactory,
				managerRepository, deviationViewHandlerFactory,
				showTakstolInfoActionFactory, aArticleTypeTakstol,
				takstolPackageApplyList,takstolProductionApplyList,aSetProductionUnitActionFactory, aCostTypeTross, aCostUnitTross);
		initProductAreaGroup();

	}

	private void setupMenus() {
		menuItemMap = new Hashtable<String, JMenuItem>();
		popupMenuProduction = new JPopupMenu("Produksjon");
		popupMenuProduction.setName("PopupMenuProduction");
		menuItemOpenOrder = new JMenuItem("Se ordre...");
		menuItemOpenOrder.setName("MenuItemOpenOrder");
		popupMenuProduction.add(menuItemOpenOrder);

		menuItemPacklist = new JMenuItem("Sett pakkliste klar...");
		menuItemPacklist.setName("MenuItemPacklist");
		menuItemPacklist.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.PAKKLISTE.getColumnName(),
				menuItemPacklist);

		menuItemVegg = new JMenuItem("Sett vegg produsert...");
		menuItemVegg.setName("MenuItemVegg");
		menuItemVegg.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.VEGG.getColumnName(), menuItemVegg);

		menuItemFront = new JMenuItem("Sett front produsert...");
		menuItemFront.setName("MenuItemFront");
		menuItemFront.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.FRONT.getColumnName(), menuItemFront);

		menuItemGavl = new JMenuItem("Sett gavl produsert...");
		menuItemGavl.setName("MenuItemGavl");
		menuItemGavl.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.GAVL.getColumnName(), menuItemGavl);

		menuItemProductionTakstol = new JMenuItem("Sett takstol produsert...");
		menuItemProductionTakstol.setName("MenuItemProduksjonTakstol");
		menuItemProductionTakstol.setEnabled(hasWriteAccess());
		menuItemMap.put(
				ProductionColumn.TAKSTOL.getColumnName() + "Produksjon",
				menuItemProductionTakstol);

		menuItemPackageTakstol = new JMenuItem("Sett takstol pakket...");
		menuItemPackageTakstol.setName("MenuItemPakkingTakstol");
		menuItemPackageTakstol.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.TAKSTOL.getColumnName() + "Pakking",
				menuItemPackageTakstol);

		menuItemTakstein = new JMenuItem("Sett takstein pakket...");
		menuItemTakstein.setName("MenuItemTakstein");
		menuItemTakstein.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.TAKSTEIN.getColumnName(),
				menuItemTakstein);

		menuItemGulvspon = new JMenuItem("Sett gulvspon pakket...");
		menuItemGulvspon.setName("MenuItemGulvspon");
		menuItemGulvspon.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.GULVSPON.getColumnName(),
				menuItemGulvspon);

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
		menuItemMap.put(ProductionColumn.PROCENT.getColumnName(),
				menuItemSetProcent);

		menuItemProductionUnitTakstol = new JMenuItem(
				"Sett produksjonsenhet...");
		menuItemProductionUnitTakstol.setName("MenuItemProductionUnitTakstol");
		menuItemProductionUnitTakstol.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.TAKSTOL.getColumnName()
				+ "ProduksjonEnhet", menuItemProductionUnitTakstol);
	}

	/**
	 * Initierer liste med produktområdegrupper
	 */
	private void initProductAreaGroup() {
		productAreaGroupModel = new PresentationModel(
				new ProductAreaGroupModel(ProductAreaGroup.UNKNOWN));
		productAreaGroupModel
				.addBeanPropertyChangeListener(new FilterPropertyChangeListener());
		ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
				.getBean("productAreaGroupManager");
		productAreaGroupList = new ArrayList<ProductAreaGroup>();
		List<ProductAreaGroup> groups = productAreaGroupManager.findAll();
		if (groups != null) {
			productAreaGroupList.addAll(groups);
		}
		// productAreaGroupList.add(0, null);
	}

	/**
	 * Gjør ingenting
	 * 
	 * @param object
	 * @return null
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(Order object) {
		return null;
	}

	/**
	 * Gjør ingenting
	 * 
	 * @param object
	 * @param presentationModel
	 * @param window
	 * @return null
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
	 *      com.jgoodies.binding.PresentationModel,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public CheckObject checkSaveObject(OrderModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * Gjør ingenting
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return null;
	}

	/**
	 * Gjør ingenting
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return null;
	}

	/**
	 * Gjør ingenting
	 * 
	 * @param handler
	 * @param object
	 * @param searching
	 * @return null
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
	 *      java.lang.Object, boolean)
	 */
	@Override
	protected AbstractEditView<OrderModel, Order> getEditView(
			AbstractViewHandler<Order, OrderModel> handler, Order object,
			boolean searching) {
		return null;
	}

	/**
	 * Gjør ingenting
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public Order getNewObject() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return productionOverviewTableModel;
	}

	/**
	 * Gjør ingenting
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Produksjonsoversikt";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(930, 600);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Produksjonsoversikt");
	}

	/**
	 * Gjør ingenting
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
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
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}
		},
		TRANSPORT("Transport") {
			@Override
			public Class<?> getColumnClass() {
				return Transport.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getTransport();
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}
		},
		PROD_DATO("Prod.dato") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return Util.formatDate(transportable.getProductionDate(),
						Util.SHORT_DATE_FORMAT);
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}
		},
		PAKKLISTE("Pakkliste") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.getPacklistReady() != null) {
					return Util.SHORT_DATE_FORMAT.format(transportable
							.getPacklistReady());
				}
				return null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(
						productionPackageHandlers, getColumnName());
				Applyable applyable = getApplyObject(transportable, handler,
						window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()),
						"Sett pakkliste klar...", "Sett pakkliste ikke klar",
						handler, popupMenuProduction, applyable);
				return true;
			}
		},
		VEGG("Vegg") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return statusMap.get(statusCheckers.get("Vegg")
						.getArticleName());
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(
						productionPackageHandlers, getColumnName());
				Applyable applyable = getApplyObject(transportable, handler,
						window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()),
						"Sett vegg produsert", "Sett vegg ikke produsert",
						handler, popupMenuProduction, applyable);
				return true;
			}
		},
		FRONT("Front") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return statusMap.get(statusCheckers.get("Front")
						.getArticleName());
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(
						productionPackageHandlers, getColumnName());
				Applyable applyable = getApplyObject(transportable, handler,
						window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()),
						"Sett front produsert", "Sett front ikke produsert",
						handler, popupMenuProduction, applyable);
				return true;
			}
		},
		GAVL("Gavl") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return statusMap.get(statusCheckers.get("Gavl")
						.getArticleName());
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(
						productionPackageHandlers, getColumnName());
				Applyable applyable = getApplyObject(transportable, handler,
						window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()),
						"Sett gavl produsert", "Sett gavl ikke produsert",
						handler, popupMenuProduction, applyable);
				return true;
			}
		},
		TAKSTOL("Takstol") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return statusMap.get(statusCheckers.get("Takstol")
						.getArticleName());
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(
						productionPackageHandlers, getColumnName()
								+ "Produksjon");
				Applyable applyable = getApplyObject(transportable, handler,
						window);
				// takstol produksjon
				setMenuItem(transportable, menuItemMap.get(getColumnName()
						+ "Produksjon"), "Sett takstol produsert",
						"Sett takstol ikke produsert", handler,
						popupMenuProduction, applyable);
				// takstol produksjonsenhet
				setMenuItem(transportable, menuItemMap.get(getColumnName()
						+ "ProduksjonEnhet"), "Sett produksjonsenhet...",
						"Sett produksjonsenhet...", handler,
						popupMenuProduction, applyable);

				// takstolinfo
				setMenuItem(transportable, menuItemMap.get(getColumnName()
						+ "Takstolinfo"), "Takstolinfo...", "Takstolinfo...",
						handler, popupMenuProduction, applyable);

				handler = getHandler(productionPackageHandlers, getColumnName()
						+ "Pakking");
				applyable = getApplyObject(transportable, handler, window);
				// takstol pakking
				setMenuItem(transportable, menuItemMap.get(getColumnName()
						+ "Pakking"), "Sett takstol pakket",
						"Sett takstol ikke pakket", handler,
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
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return statusMap.get(statusCheckers.get("Stein")
						.getArticleName());
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(
						productionPackageHandlers, getColumnName());
				Applyable applyable = getApplyObject(transportable, handler,
						window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()),
						"Sett takstein pakket", "Sett takstein ikke pakket",
						handler, popupMenuProduction, applyable);
				return true;
			}
		},
		GULVSPON("Gulvspon") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return statusMap.get(statusCheckers.get("Gulvspon")
						.getArticleName());
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(
						productionPackageHandlers, getColumnName());
				Applyable applyable = getApplyObject(transportable, handler,
						window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()),
						"Sett gulvspon pakket", "Sett gulvspon ikke pakket",
						handler, popupMenuProduction, applyable);
				return true;
			}
		},
		KOMPLETT("Komplett") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.getOrderComplete() != null) {
					return "Ja";
				}
				return "Nei";
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}
		},
		KLAR("Klar") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.getOrderReady() != null) {
					return "Ja";
				}
				return "Nei";
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}
		},
		MONTERING("Montering") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.getDoAssembly() != null
						&& transportable.getDoAssembly() == 1) {
					return "X";
				}
				return "";
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}
		},
		PRODUKTOMRÅDE("Produktområde") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getProductAreaGroup()
						.getProductAreaGroupName();
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}
		},
		REST("Rest") {
			@Override
			public Class<?> getColumnClass() {
				return Transportable.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}
		},
		PROCENT("%") {
			@Override
			public Class<?> getColumnClass() {
				return ProcentDone.class;
			}

			@Override
			public Object getValue(
					Transportable transportable,
					Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getLastProcentDone();
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(
					Transportable transportable,
					Map<String, JMenuItem> menuItemMap,
					WindowInterface window,
					Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				popupMenuProduction.add(menuItemMap.get(getColumnName()));
				return true;
			}
		};

		private String columnName;

		private ProductionColumn(String aColumnName) {
			columnName = aColumnName;
		}

		public String getColumnName() {
			return columnName;
		}

		
		private static void setMenuItem(final Transportable transportable,
				final JMenuItem menuItem, final String applyString,
				final String unapplyString,
				final AbstractProductionPackageViewHandler<Applyable> handler,
				JPopupMenu popupMenuProduction, final Applyable applyable) {
			if (applyable != null) {
				if ((transportable instanceof PostShipment && applyable
						.isForPostShipment())
						|| transportable instanceof Order) {
					if (handler.getButtonApplyEnabled(applyable)) {
						menuItem.setText(applyString);
					} else {
						menuItem.setText(unapplyString);
					}
					popupMenuProduction.add(menuItem);
				}

			}
		}

		private static Applyable getApplyObject(
				final Transportable transportable,
				final AbstractProductionPackageViewHandler<Applyable> handler,
				final WindowInterface window) {
			return handler != null ? handler.getApplyObject(transportable,
					window) : null;

		}

		@SuppressWarnings("unchecked")
		private static AbstractProductionPackageViewHandler<Applyable> getHandler(
				final Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
				final String menuItemName) {
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

		public abstract Object getValue(
				Transportable transportable,
				Map<String, String> statusMap,
				Map<String, StatusCheckerInterface<Transportable>> statusCheckers);

		public abstract Class<?> getColumnClass();

		@SuppressWarnings("unchecked")
		public abstract boolean setMenus(
				Transportable transportable,
				Map<String, JMenuItem> menuItemMap,
				WindowInterface window,
				Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
				JPopupMenu popupMenuProduction);
	}

	/**
	 * Tabellmodell
	 * 
	 * @author atle.brekka
	 */
	public final class ProductionOverviewTableModel extends
			AbstractTableAdapter {

		/**
         * 
         */
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		ProductionOverviewTableModel(ListModel listModel) {
			/*super(listModel, new String[] { "Ordre", "Transport", "Prod.dato",
					"Pakkliste", "Vegg", "Front", "Gavl", "Takstol",
					"Takstein", "Gulvspon", "Komplett", "Klar", "Montering",
					"Produktområde", "Rest", "%" });*/
			super(listModel, ProductionColumn.getColumnNames());

		}

		/**
		 * Henter objekt for gjeldende indeks
		 * 
		 * @param rowIndex
		 * @return objekt
		 */
		public Transportable getTransportable(int rowIndex) {
			return (Transportable) getRow(rowIndex);
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			Transportable transportable = (Transportable) getRow(rowIndex);

			Map<String, String> statusMap = Util.createStatusMap(transportable
					.getStatus());
			String columnName = StringUtils.upperCase(
					getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
			return ProductionColumn.valueOf(columnName).getValue(transportable,
					statusMap, statusCheckers);
			/*
			 * switch (columnIndex) { case 0: return transportable; case 1:
			 * return transportable.getTransport(); case 2: return
			 * Util.formatDate(transportable.getProductionDate(),
			 * Util.SHORT_DATE_FORMAT); case 3: if
			 * (transportable.getPacklistReady() != null) { return
			 * Util.SHORT_DATE_FORMAT.format(transportable.getPacklistReady());
			 * } return null; case 4: return
			 * statusMap.get(statusCheckers.get("Vegg").getArticleName()); case
			 * 5: return
			 * statusMap.get(statusCheckers.get("Front").getArticleName()); case
			 * 6: return
			 * statusMap.get(statusCheckers.get("Gavl").getArticleName()); case
			 * 7: return
			 * statusMap.get(statusCheckers.get("Takstol").getArticleName());
			 * case 8: return
			 * statusMap.get(statusCheckers.get("Stein").getArticleName()); case
			 * 9: return
			 * statusMap.get(statusCheckers.get("Gulvspon").getArticleName());
			 * case 10: if (transportable.getOrderComplete() != null) { return
			 * "Ja"; } return "Nei"; case 11: if (transportable.getOrderReady()
			 * != null) { return "Ja"; } return "Nei"; case 12: if
			 * (transportable.getDoAssembly() != null &&
			 * transportable.getDoAssembly() == 1) { return "X"; } return "";
			 * case 13: return
			 * transportable.getProductAreaGroup().getProductAreaGroupName();
			 * case 14: return transportable; case 15: return
			 * transportable.getLastProcentDone(); default: throw new
			 * IllegalStateException("Unknown column"); }
			 */

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			String columnName = StringUtils.upperCase(
					getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
			return ProductionColumn.valueOf(columnName).getColumnClass();
			/*
			 * switch (columnIndex) { case 0: return Transportable.class; case
			 * 1: return Transport.class; case 2: case 3: case 4: case 5: case
			 * 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13:
			 * return String.class; case 14: return Transportable.class; case
			 * 15: return ProcentDone.class; default: throw new
			 * IllegalStateException("Unknown column"); }
			 */
		}

		/**
		 * Henter objekt for gjeldende rad
		 * 
		 * @param rowIndex
		 * @return objekt
		 */
		public Transportable getObjectAtRow(int rowIndex) {
			return (Transportable) getRow(rowIndex);
		}

	}

	/**
	 * Casher kommentar
	 * 
	 * @param transportable
	 * @param window
	 * @param load
	 */
	void cacheComment(Transportable transportable, WindowInterface window,
			boolean load) {
		if (transportable instanceof Order) {
			if (load) {
				OrderManager orderManager = (OrderManager) ModelUtil
						.getBean("orderManager");
				orderManager.lazyLoadOrder((Order) transportable,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS,
								LazyLoadOrderEnum.COLLIES });
			}
			transportable.cacheComments();
		} else {
			if (load) {
				PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
						.getBean("postShipmentManager");
				postShipmentManager.lazyLoad((PostShipment) transportable,
						new LazyLoadPostShipmentEnum[] {
								LazyLoadPostShipmentEnum.ORDER_COMMENTS,
								LazyLoadPostShipmentEnum.COLLIES });
			}
			transportable.cacheComments();
		}
	}

	/**
	 * Initierer statuser
	 * 
	 * @param transportable
	 * @param window
	 */
	void initTransportable(Transportable transportable, WindowInterface window) {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
				.getBean("postShipmentManager");
		CustTrManager custTrManager = (CustTrManager) ModelUtil
				.getBean("custTrManager");
		Set<String> checkers = statusCheckers.keySet();
		Map<String, String> statusMap;

		String status;
		StatusCheckerInterface<Transportable> checker;
		boolean orderLoaded = false;
		boolean needToSave = false;

		transportable.setCustTrs(custTrManager.findByOrderNr(transportable
				.getOrder().getOrderNr()));

		statusMap = Util.createStatusMap(transportable.getStatus());
		for (String checkerName : checkers) {
			checker = statusCheckers.get(checkerName);
			status = statusMap.get(checker.getArticleName());

			if (status == null) {
				needToSave = true;
				if (!orderLoaded && transportable instanceof Order) {
					((OrderManager) overviewManager).lazyLoadOrder(
							(Order) transportable, new LazyLoadOrderEnum[] {
									LazyLoadOrderEnum.COLLIES,
									LazyLoadOrderEnum.ORDER_LINES,
									LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
									LazyLoadOrderEnum.COMMENTS,
									LazyLoadOrderEnum.PROCENT_DONE });
					orderLoaded = true;
				} else if (!orderLoaded
						&& transportable instanceof PostShipment) {
					postShipmentManager
							.lazyLoad(
									(PostShipment) transportable,
									new LazyLoadPostShipmentEnum[] {
											LazyLoadPostShipmentEnum.COLLIES,
											LazyLoadPostShipmentEnum.ORDER_LINES,
											LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
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
					((OrderManager) overviewManager)
							.saveOrder((Order) transportable);
				} catch (ProTransException e) {
					Util.showErrorDialog(window, "Feil", e.getMessage());
					e.printStackTrace();
				}
			} else {
				postShipmentManager
						.savePostShipment((PostShipment) transportable);
			}
		}
		if (transportable instanceof Order
				&& !Hibernate.isInitialized(((Order) transportable)
						.getProcentDones())) {
			((OrderManager) overviewManager).lazyLoadOrder(
					((Order) transportable),
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.PROCENT_DONE });
		}

	}

	/**
	 * Initierer objekter med status
	 * 
	 * @param transportables
	 * @param window
	 */
	private void initOrders(List<Transportable> transportables,
			WindowInterface window) {
		if (transportables != null) {

			for (Transportable transportable : transportables) {
				initTransportable(transportable, window);
			}
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#initObjects()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void initObjects() {
		if (!loaded) {

			if (table != null && table.getModel().getRowCount() > 1) {
				table.setRowSelectionInterval(1, 1);
			}
			objectList.clear();
			objectSelectionList.clearSelection();

			List<Order> allOrders = ((OrderManager) overviewManager)
					.findAllNotSent();
			if (allOrders != null) {
				objectList.addAll(allOrders);
			}
			PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
					.getBean("postShipmentManager");
			List<PostShipment> allPostShipments = postShipmentManager
					.findAllNotSent();
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

	/**
	 * Lager oppdateringsknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonRefresh(WindowInterface window) {
		JButton button = new RefreshButton(this, window);
		button.setName("ButtonRefresh");
		setupMenuListeners(window);
		return button;
	}

	/**
	 * LAger søkeknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonSearch(WindowInterface window) {
		JButton button = new JButton(new SearchAction(window));
		button.setName("SearchOrder");
		return button;
	}

	/**
	 * Lager sjekkboks for filtrering av ferdige
	 * 
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxFilter() {
		checkBoxFilter = new JCheckBox("Vis ferdige");
		checkBoxFilter.setSelected(true);
		checkBoxFilter.setName("CheckBoxFilter");
		checkBoxFilter.addActionListener(new FilterActionListener());
		return checkBoxFilter;
	}

	/**
	 * Lager komboboks for prosuktområdegrupper
	 * 
	 * @return komboboks
	 */
	public JComboBox getComboBoxProductAreaGroup() {
		return Util.getComboBoxProductAreaGroup(login.getApplicationUser(),
				userType, productAreaGroupModel);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTable(no.ugland.utransprod.gui.WindowInterface)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JXTable getTable(WindowInterface window) {
		initObjects();
		initOrders(objectList, window);

		ColorHighlighter readyHighlighter = new ColorHighlighter(
				new PatternPredicate("Ja", 10), ColorEnum.GREEN.getColor(),
				null);
		ColorHighlighter startedPackingHighlighter = new ColorHighlighter(
				new PatternPredicate("Ja", 11), ColorEnum.YELLOW.getColor(),
				null);

		table = new JXTable();
		productionOverviewTableModel = new ProductionOverviewTableModel(
				objectList);
		table.setModel(productionOverviewTableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionModel(new SingleListSelectionAdapter(
				objectSelectionList.getSelectionIndexHolder()));
		table.setColumnControlVisible(true);
		table.setColumnControl(new UBColumnControlPopup(table, this));

		table.addMouseListener(new TableClickHandler(window));

		table.setRowHeight(40);

		table.getColumnModel().getColumn(0).setCellRenderer(
				new TextPaneRendererTransport());

		table.addHighlighter(HighlighterFactory.createAlternateStriping());
		table.addHighlighter(startedPackingHighlighter);
		table.addHighlighter(readyHighlighter);

		// ordre
		table.getColumnExt(0).setPreferredWidth(220);
		// transport
		table.getColumnExt(1).setPreferredWidth(150);
		// prod.dato
		table.getColumnExt(2).setPreferredWidth(70);
		// pakkliste
		table.getColumnExt(3).setPreferredWidth(80);

		// vegg
		table.getColumnExt(4).setPreferredWidth(45);
		// front
		table.getColumnExt(5).setPreferredWidth(45);
		// gavl
		table.getColumnExt(6).setPreferredWidth(45);
		// takstol
		table.getColumnExt(7).setPreferredWidth(60);
		// //takstein
		table.getColumnExt(8).setPreferredWidth(60);
		// gulvspon
		table.getColumnExt(9).setPreferredWidth(70);
		// montering
		table.getColumnExt(10).setPreferredWidth(50);
		// rest
		table.getColumnExt(14).setPreferredWidth(50);
		// %
		table.getColumnExt(15).setPreferredWidth(40);

		table.getColumnModel().getColumn(14).setCellRenderer(
				new TextPaneRendererCustTr());
		table.getColumnModel().getColumn(15).setCellRenderer(
				new TextPaneRendererProcentDone());

		/*
		 * table.getColumnExt(10).setVisible(false);
		 * table.getColumnExt(10).setVisible(false);
		 * table.getColumnExt(11).setVisible(false);
		 */

		// setupMenuListeners(window);

		// table.setName("ProductionTable");
		table.setName(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());
		return table;

	}

	private void setupMenuListeners(WindowInterface window) {
		menuItemPacklist.addActionListener(new ProductionMenuItemListener(
				window, "Pakkliste", "Sett pakkliste klar..."));
		menuItemVegg.addActionListener(new ProductionMenuItemListener(window,
				"Vegg", "Sett vegg produsert"));
		menuItemFront.addActionListener(new ProductionMenuItemListener(window,
				"Front", "Sett front produsert"));
		menuItemGavl.addActionListener(new ProductionMenuItemListener(window,
				"Gavl", "Sett gavl produsert"));
		menuItemProductionTakstol
				.addActionListener(new ProductionMenuItemListener(window,
						"TakstolProduksjon", "Sett takstol produsert"));
		menuItemPackageTakstol
				.addActionListener(new ProductionMenuItemListener(window,
						"TakstolPakking", "Sett takstol pakket"));
		menuItemTakstein.addActionListener(new ProductionMenuItemListener(
				window, "Takstein", "Sett takstein pakket"));
		menuItemGulvspon.addActionListener(new ProductionMenuItemListener(
				window, "Gulvspon", "Sett gulvspon pakket"));
		menuItemOpenOrder.addActionListener(new MenuItemListenerOpenOrder(
				window));
		menuItemShowMissing.addActionListener(new MenuItemListenerShowMissing(
				window));
		menuItemShowContent.addActionListener(new MenuItemListenerShowContent(
				window));
		menuItemDeviation.addActionListener(new MenuItemListenerDeviation(
				window));
		menuItemSetProcent.addActionListener(new MenuItemListenerSetProcent(
				window));
		//menuItemProductionUnitTakstol.addActionListener(new MenuItemListenerSetProductionUnit(window));
		menuItemProductionUnitTakstol.addActionListener(setProductionUnitActionFactory.create(articleTypeTakstol, this, window));

		menuItemShowTakstolInfo = new JMenuItem(showTakstolInfoActionFactory
				.create(this, window));
		menuItemShowTakstolInfo.setName("MenuItemShowTakstolInfo");
		menuItemShowTakstolInfo.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.TAKSTOL.getColumnName()
				+ "Takstolinfo", menuItemShowTakstolInfo);
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
		boolean success = transportable != null ? orderViewHandler
				.openOrderView(transportable.getOrder(), false, window) : false;
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
		@SuppressWarnings( { "synthetic-access" })
		@Override
		public void mouseClicked(MouseEvent mouseEvent) {
			Util.setWaitCursor(window.getComponent());
			Transportable transportable = getSelectedTransportable();
			if (SwingUtilities.isLeftMouseButton(mouseEvent)
					&& mouseEvent.getClickCount() == 2) {
				openOrderView(transportable, window);

			} else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
				setMenuItems(mouseEvent, transportable);

			}
			Util.setDefaultCursor(window.getComponent());
		}

		private void setMenuItems(MouseEvent mouseEvent,
				Transportable transportable) {
			int col = table.columnAtPoint(new Point(mouseEvent.getX(),
					mouseEvent.getY()));

			removeMenuItems();

			if (transportable instanceof PostShipment) {
				popupMenuProduction.add(menuItemShowContent);
			} else {
				popupMenuProduction.add(menuItemDeviation);
			}
			String columnHeader = StringUtils.upperCase(
					(String) table.getColumnExt(col).getHeaderValue())
					.replaceAll(" ", "_").replaceAll("\\.", "_").replaceAll(
							"\\%", "PROCENT");
			;
			ProductionColumn productionColumn = ProductionColumn
					.valueOf(columnHeader);
			boolean success = transportable != null ? productionColumn
					.setMenus(transportable, menuItemMap, window,
							productionPackageHandlers, popupMenuProduction)
					: false;
			if (success) {
				popupMenuProduction.show((JXTable) mouseEvent.getSource(),
						mouseEvent.getX(), mouseEvent.getY());
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
				int index = table.convertRowIndexToModel(objectSelectionList
						.getSelectionIndex());
				transportable = (Transportable) objectSelectionList
						.getElementAt(index);
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
		/**
         * 
         */
		private WindowInterface window;

		/**
         * 
         */
		// private String statusName;

		/**
         * 
         */
		private String applyString;
		private AbstractProductionPackageViewHandler<Applyable> handler;

		/**
		 * @param aWindow
		 * @param aStatusName
		 * @param aApplyString
		 */
		@SuppressWarnings("unchecked")
		public ProductionMenuItemListener(WindowInterface aWindow,
				String aStatusName, String aApplyString) {
			window = aWindow;
			applyString = aApplyString;
			handler = productionPackageHandlers.get(aStatusName);
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window.getComponent());
			Transportable transportable = null;
			if (objectSelectionList.getSelection() != null) {
				int index = table.convertRowIndexToModel(objectSelectionList
						.getSelectionIndex());
				transportable = (Transportable) objectSelectionList
						.getElementAt(index);
			}
			if (transportable != null) {
				boolean apply = false;
				if (actionEvent.getActionCommand()
						.equalsIgnoreCase(applyString)) {
					apply = true;
				}
				if (handler != null) {

					handler.setApplied(handler.getApplyObject(transportable,
							window), apply, window);
					handler.clearApplyObject();
				}

				if (transportable instanceof Order) {
					((OrderManager) overviewManager)
							.refreshObject((Order) transportable);
				} else {
					PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
							.getBean("postShipmentManager");
					postShipmentManager
							.refreshObject((PostShipment) transportable);
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
		/**
         * 
         */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerOpenOrder(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Transportable transportable = getSelectedObject();
			if (transportable != null) {
				openOrderView(transportable.getOrder(), window);
			}
		}

	}

	public Transportable getSelectedObject() {
		Transportable transportable = null;
		if (objectSelectionList.getSelection() != null) {
			int index = table.convertRowIndexToModel(objectSelectionList
					.getSelectionIndex());
			transportable = (Transportable) objectSelectionList
					.getElementAt(index);
		}
		return transportable;
	}

	/**
	 * Håndterer menyvalg for å visse mangler
	 * 
	 * @author atle.brekka
	 */
	private class MenuItemListenerShowMissing implements ActionListener {
		/**
         * 
         */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerShowMissing(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Transportable transportable = null;
			if (objectSelectionList.getSelection() != null) {
				int index = table.convertRowIndexToModel(objectSelectionList
						.getSelectionIndex());
				transportable = (Transportable) objectSelectionList
						.getElementAt(index);
			}
			if (transportable != null) {
				TransportViewHandler.showMissingColliesForTransportable(
						transportable, window);

			}
		}

	}

	/**
	 * Håndterer menyvalg for å vise innhold
	 * 
	 * @author atle.brekka
	 */
	private class MenuItemListenerShowContent implements ActionListener {
		/**
         * 
         */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerShowContent(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Transportable transportable = null;
			if (objectSelectionList.getSelection() != null) {
				int index = table.convertRowIndexToModel(objectSelectionList
						.getSelectionIndex());
				transportable = (Transportable) objectSelectionList
						.getElementAt(index);
			}
			if (transportable != null && transportable instanceof PostShipment) {
				RouteViewHandler.showContentForPostShipment(
						(PostShipment) transportable, window);

			}
		}

	}

	/**
	 * Håndterer menyvalg for å registrere avvik
	 * 
	 * @author atle.brekka
	 */
	class MenuItemListenerDeviation implements ActionListener {
		/**
         * 
         */
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
		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getActionCommand().equalsIgnoreCase(
					menuItemDeviation.getText())) {
				Transportable transportable = null;
				if (objectSelectionList.getSelection() != null) {
					int index = table
							.convertRowIndexToModel(objectSelectionList
									.getSelectionIndex());
					transportable = (Transportable) objectSelectionList
							.getElementAt(index);
				}

				if (transportable != null && transportable instanceof Order) {
					DeviationViewHandler deviationViewHandler = deviationViewHandlerFactory
							.create((Order) transportable, true, false, true,
									null, true);
					deviationViewHandler.registerDeviation(
							(Order) transportable, window);
				}

			}
		}

	}

	class MenuItemListenerSetProcent implements ActionListener {
		/**
         * 
         */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerSetProcent(WindowInterface aWindow) {
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getActionCommand().equalsIgnoreCase(
					menuItemSetProcent.getText())) {
				Transportable transportable = null;
				if (objectSelectionList.getSelection() != null) {
					int index = table
							.convertRowIndexToModel(objectSelectionList
									.getSelectionIndex());
					transportable = (Transportable) objectSelectionList
							.getElementAt(index);
				}

				if (transportable != null && transportable instanceof Order) {
					setProcentForOrder((Order) transportable, window);
				}

			}
		}

	}


	private void setProcentForOrder(final Order order,
			final WindowInterface aWindow) {
		ProcentDoneModel procentDoneModel = new ProcentDoneModel(
				new ProcentDone(null, null, null, null, order, null, null, null));
		ProcentDoneViewHandler procentDoneViewHandler = new ProcentDoneViewHandler(
				userType, managerRepository.getProcentDoneManager());
		EditProcentDoneView procentDoneView = new EditProcentDoneView(false,
				procentDoneModel, procentDoneViewHandler);
		Util.showEditViewable(procentDoneView, aWindow);

		if (!procentDoneView.isCanceled()) {
			handleProcentDone(order, aWindow, procentDoneModel);

		}
	}

	private void handleProcentDone(final Order order,
			final WindowInterface aWindow,
			final ProcentDoneModel procentDoneModel) {
		ProcentDone newProcentDone = procentDoneModel.getObject();
		CheckObject checkObject = checkProcentDone(newProcentDone, order);

		if (checkObject != null) {
			if (Util.showConfirmDialog(aWindow.getComponent(), "Lagre?",
					checkObject.getMsg())) {
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
		newProcentDone.setProcentDoneComment(procentDoneModel
				.getProcentDoneComment());

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
			checkObject = new CheckObject(
					"Det er allerde registert en prosent "
							+ procentDone.getProcent()
							+ " for denne uken. Vil du overskrive denne?",
					true, procentDone);
		} else if (order.getLastProcentDone() != null
				&& order.getLastProcentDone().getProcent() > newProcentDone
						.getProcent()) {
			checkObject = new CheckObject("Forrige prosent "
					+ order.getLastProcentDone()
					+ " er høyere enn denne. Vil du likevel lagre denne?",
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

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			handleFilter();

		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#doRefresh(no.ugland.utransprod.gui.WindowInterface)
	 */
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
		public SearchAction(WindowInterface aWindow) {
			super("Søk ordre...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
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
		Transportable transportable = orderViewHandler
				.searchOrder(window, true);
		if (transportable != null) {
			if (objectList.contains(transportable)) {
				int index = objectList.indexOf(transportable);
				objectSelectionList.setSelectionIndex(table
						.convertRowIndexToView(index));
				table.scrollRowToVisible(objectSelectionList
						.getSelectionIndex());
			}
		} else {
			Util.showMsgDialog(window.getComponent(), "Fant ikke ordre",
					"Ordre med  ble ikke funnet");
		}

	}

	/**
	 * Håndterer endring av filter
	 * 
	 * @author atle.brekka
	 */
	class FilterActionListener implements ActionListener {

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
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

		ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel
				.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
		PrefsUtil.setInvisibleColumns(group.getProductAreaGroupName(), table
				.getName(), table);
		group = group.getProductAreaGroupName().equalsIgnoreCase("Alle") ? ProductAreaGroup.UNKNOWN
				: group;

		List<Filter> filterList = new ArrayList<Filter>();

		if (!checkBoxFilter.isSelected()) {
			PatternFilter filterDone = new PatternFilter("Nei",
					Pattern.CASE_INSENSITIVE, 10);
			filterList.add(filterDone);
		}
		if (group != ProductAreaGroup.UNKNOWN) {
			if (!group.getProductAreaGroupName().equalsIgnoreCase("Takstol")) {
				filterList.add(new PatternFilter(group
						.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE,
						13));
			} else {
				filterList.add(new PatternFilter(".*e.*",
						Pattern.CASE_INSENSITIVE, 7));
			}

		}
		if (filterList.size() != 0) {
			Filter[] filterArray = new Filter[filterList.size()];
			table
					.setFilters(new FilterPipeline(filterList
							.toArray(filterArray)));
		} else {
			table.setFilters(null);
		}
		table.repaint();

	}

	public String getProductAreaGroupName() {
		return ((ProductAreaGroup) productAreaGroupModel
				.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP))
				.getProductAreaGroupName();
	}

	@Override
	public void beforeClose() {
		PrefsUtil
				.putUserInvisibleColumns(
						table,
						(ProductAreaGroup) productAreaGroupModel
								.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
	}

	public JButton getButtonShowTakstolInfo(WindowInterface window) {
		buttonShowTakstolInfo = new JButton(showTakstolInfoActionFactory
				.create(this, window));

		return null;
	}

	public String getSelectedOrderNr() {
		Transportable transportable = getSelectedObject();
		return transportable != null ? transportable.getOrderNr() : null;
	}

	public Produceable getSelectedProduceable() {
		Transportable transportable = getSelectedObject();
	
		if (transportable != null) {
			ProductionViewHandler handler = (ProductionViewHandler) productionPackageHandlers
					.get("TakstolProduksjon");
			if (handler != null) {
				return handler.getApplyObject(transportable, window);
			}
		}
		return null;
	}

	
	

}
