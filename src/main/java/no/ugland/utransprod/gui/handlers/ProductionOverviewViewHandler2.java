package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProductAreaGroupProvider;
import no.ugland.utransprod.gui.UBColumnControlPopup;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.RefreshButton;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.edit.EditProcentDoneView;
import no.ugland.utransprod.gui.edit.FilterProductionoverviewView;
import no.ugland.utransprod.gui.edit.FilterProductionoverviewView.ProductionoverviewFilterListener;
import no.ugland.utransprod.gui.handlers.AssemblyPlannerViewHandler.MenuItemListenerAssembly;
import no.ugland.utransprod.gui.edit.ProductionoverviewFilter;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.ProcentDoneModel;
import no.ugland.utransprod.gui.model.TakstolPackageApplyList;
import no.ugland.utransprod.gui.model.TakstolProductionApplyList;
import no.ugland.utransprod.gui.model.TextPaneRenderer;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.AssemblyV;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.DokumentV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProcentDone;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.ProductionOverviewV;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.AssemblyManager;
import no.ugland.utransprod.service.DokumentVManager;
import no.ugland.utransprod.service.Dokumentlager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.ProductionOverviewVManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.Collicreator;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Tidsforbruk;
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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Hjelpeklasse for produksjonsoversikt
 * 
 * @author atle.brekka
 */
public class ProductionOverviewViewHandler2 implements ProductAreaGroupProvider, OrderNrProvider, ProduceableProvider,
		Updateable, Closeable, ProductionoverviewFilterListener {

	private List<ProductionOverviewV> productionoverviewList = Lists.newArrayList();

	private ProductionOverviewVManager overviewManager;
	private final ArrayListModel objectList;
	private JXTable table;
	private boolean loaded = false;
	private UserType userType;
	private SelectionInList objectSelectionList;
	private JButton buttonCancel;
	private JLabel labelAntallGarasjer;
	private JLabel labelSumTidVegg;
	private JLabel labelSumTidGavl;
	private JLabel labelSumTidPakk;

	JButton buttonRefresh;
	Map<String, StatusCheckerInterface<Transportable>> statusCheckers;

	OrderViewHandler orderViewHandler;

	JPopupMenu popupMenuProduction;

	JMenuItem menuItemPacklist;

	JMenuItem menuItemVegg;

	JMenuItem menuItemOpenOrder;
	private JMenuItem menuItemSetProductionWeek;

	JMenuItem menuItemShowMissing;
	JMenuItem menuItemSetComment;
	JMenuItem menuItemSetAntallStandardvegger;
	JMenuItem menuItemShowDocument;

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
	private JMenuItem menuItemSetReceivedTrossDrawing;
	private JMenuItem menuItemSetEstimatedTimeWall;
	private JMenuItem menuItemSetEstimatedTimeGavl;
	private JMenuItem menuItemSetEstimatedTimePack;

	private Login login;

	private TableModel productionOverviewTableModel;

	Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers;

	JCheckBox checkBoxFilter;
	private VismaFileCreator vismaFileCreator;
	private Map<String, JMenuItem> menuItemMap;

	private ManagerRepository managerRepository;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;

	private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;

	private JMenuItem menuItemShowTakstolInfo;
	private JMenuItem menuItemUpdate;

	private SetProductionUnitActionFactory setProductionUnitActionFactory;
	private ArticleType articleTypeTakstol;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
	ProductionoverviewFilter currentProductionoverviewFilter;

	@Inject
	public ProductionOverviewViewHandler2(final VismaFileCreator aVismaFileCreator,
			final OrderViewHandlerFactory orderViewHandlerFactory, final Login aLogin,
			ManagerRepository aManagerRepository, DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			final ShowTakstolInfoActionFactory aShowTakstolInfoActionFactory,
			final @Named("takstolArticle") ArticleType aArticleTypeTakstol,
			final TakstolPackageApplyList takstolPackageApplyList,
			final TakstolProductionApplyList takstolProductionApplyList,
			SetProductionUnitActionFactory aSetProductionUnitActionFactory,
			@Named("kostnadTypeTakstoler") CostType aCostTypeTross,
			@Named("kostnadEnhetTakstoler") CostUnit aCostUnitTross, Collicreator collicreator) {
		userType = aLogin.getUserType();
		overviewManager = (ProductionOverviewVManager) ModelUtil.getBean(ProductionOverviewVManager.MANAGER_NAME);
		objectList = new ArrayListModel();

		objectSelectionList = new SelectionInList((ListModel) objectList);
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

		productionPackageHandlers = Util.getProductionPackageHandlers(vismaFileCreator, login, orderViewHandlerFactory,
				managerRepository, deviationViewHandlerFactory, showTakstolInfoActionFactory, aArticleTypeTakstol,
				takstolPackageApplyList, takstolProductionApplyList, aSetProductionUnitActionFactory, aCostTypeTross,
				aCostUnitTross, collicreator);
		// initProductAreaGroup();

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
		// menuItemMap.put(ProductionColumn.PROCENT.getColumnName(),
		// menuItemSetProcent);

		menuItemSetReceivedTrossDrawing = new JMenuItem("Sett mottatt tegninger fra Jatak...");
		menuItemSetReceivedTrossDrawing.setName("menuItemSetReceivedTrossDrawing");
		menuItemSetReceivedTrossDrawing.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.TEGNINGER_JATAK.getColumnName(), menuItemSetReceivedTrossDrawing);

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

		menuItemUpdate = new JMenuItem("Oppdater linje");
		popupMenuProduction.add(menuItemUpdate);

		menuItemSetComment = new JMenuItem("Legg til kommentar...");
		popupMenuProduction.add(menuItemSetComment);

		menuItemSetAntallStandardvegger = new JMenuItem("Sett antall standardvegger...");
		popupMenuProduction.add(menuItemSetAntallStandardvegger);

		menuItemShowDocument = new JMenuItem("Se dokument...");
		popupMenuProduction.add(menuItemShowDocument);
	}

	public TableModel getTableModel(WindowInterface window) {
		return productionOverviewTableModel;
	}

	public String getTitle() {
		return "Produksjonsoversikt";
	}

	public Dimension getWindowSize() {
		return new Dimension(930, 600);
	}

	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Produksjonsoversikt");
	}

	public enum ProductionColumn {
		PAKKLISTE("Pakkliste", false, 80, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (productionOverviewV.getPacklistReady() != null) {
					return Util.SHORT_DATE_FORMAT.format(productionOverviewV.getPacklistReady());
				}
				return null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers,
						getColumnName());
				Applyable applyable = getApplyObject(transportable, handler, window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett pakkliste klar...",
						"Sett pakkliste ikke klar", handler, popupMenuProduction, applyable);
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.PACKLIST_READY), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getPacklistReady())) {
					return productionOverviewV.getPacklistReady() != null
							&& simpleDateFormat.format(productionOverviewV.getPacklistReady()).matches(
									productionoverviewFilter.getPacklistReady().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getPacklistReady() == null
						&& productionOverviewV2.getPacklistReady() == null
								? 0
								: productionOverviewV1.getPacklistReady() == null ? -1
										: productionOverviewV2.getPacklistReady() == null ? 1
												: productionOverviewV1.getPacklistReady()
														.compareTo(productionOverviewV2.getPacklistReady());
			}
		},
		LAGET_PAKKLISTE("Laget pakkliste", false, 90, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getPacklistDoneBy();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.PACKLIST_DONE_BY), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getPacklistDoneBy())) {
					return productionOverviewV.getPacklistDoneBy() != null
							&& productionOverviewV.getPacklistDoneBy().toLowerCase().matches(
									productionoverviewFilter.getPacklistDoneBy().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getPacklistDoneBy() == null
						&& productionOverviewV2.getPacklistDoneBy() == null
								? 0
								: productionOverviewV1.getPacklistDoneBy() == null ? -1
										: productionOverviewV2.getPacklistDoneBy() == null ? 1
												: productionOverviewV1.getPacklistDoneBy()
														.compareTo(productionOverviewV2.getPacklistDoneBy());
			}
		},
		TIDSBRUK_PAKKLISTE("Tidsbruk pakkliste", true, 100, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getPacklistDuration();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.PACKLIST_DURATION), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getPacklistDuration())) {
					return productionOverviewV.getPacklistDuration() != null
							&& String.valueOf(productionOverviewV.getPacklistDuration()).toLowerCase().matches(
									productionoverviewFilter.getPacklistDuration().toLowerCase().replaceAll("%", ".*"));
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getPacklistDuration() == null
						&& productionOverviewV2.getPacklistDuration() == null
								? 0
								: productionOverviewV1.getPacklistDuration() == null ? -1
										: productionOverviewV2.getPacklistDuration() == null ? 1
												: productionOverviewV1.getPacklistDuration()
														.compareTo(productionOverviewV2.getPacklistDuration());
			}
		},
		ORDRENR("Ordrenr", false, 90, true) {
			@Override
			public Class<?> getColumnClass() {
				return ProductionOverviewV.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.ORDERNR), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getOrderNr())) {
					return productionOverviewV.getOrderNr() != null && productionOverviewV.getOrderNr().toLowerCase()
							.matches(productionoverviewFilter.getOrderNr().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getOrderNr().compareTo(productionOverviewV2.getOrderNr());
			}
		},
		// ORDNR("Ordnr", true, 70) {
		// @Override
		// public Object getValue(ProductionOverviewV transportable, Map<String,
		// String> statusMap,
		// Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		// return transportable.getOrderNr();
		// }
		//
		// @Override
		// public Class<?> getColumnClass() {
		// return String.class;
		// }
		//
		// @Override
		// public boolean setMenus(Transportable transportable, Map<String,
		// JMenuItem> menuItemMap, WindowInterface window,
		// Map<String, AbstractProductionPackageViewHandler>
		// productionPackageHandlers, JPopupMenu popupMenuProduction) {
		// return false;
		// }
		// },
		FORNAVN("Fornavn", false, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV.getFirstName();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.FIRSTNAME), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getFirstname())) {
					return productionOverviewV.getFirstName() != null
							&& productionOverviewV.getFirstName().toLowerCase().matches(
									productionoverviewFilter.getFirstname().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getFirstName() == null && productionOverviewV2.getFirstName() == null ? 0
						: productionOverviewV1.getFirstName() == null ? -1
								: productionOverviewV2.getFirstName() == null ? 1
										: productionOverviewV1.getFirstName()
												.compareTo(productionOverviewV2.getFirstName());
			}
		},
		ETTERNAVN("Etternavn", false, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV.getLastName();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.LASTNAME), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getLastname())) {
					return productionOverviewV.getLastName() != null && productionOverviewV.getLastName().toLowerCase()
							.matches(productionoverviewFilter.getLastname().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getLastName() == null && productionOverviewV2.getLastName() == null ? 0
						: productionOverviewV1.getLastName() == null ? -1
								: productionOverviewV2.getLastName() == null ? 1
										: productionOverviewV1.getLastName()
												.compareTo(productionOverviewV2.getLastName());
			}
		},
		POSTNR("Postnr", false, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV.getPostalCode();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.POSTAL_CODE), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getPostalCode())) {
					return productionOverviewV.getPostalCode() != null
							&& productionOverviewV.getPostalCode().toLowerCase().matches(
									productionoverviewFilter.getPostalCode().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getPostalCode() == null && productionOverviewV2.getPostalCode() == null ? 0
						: productionOverviewV1.getPostalCode() == null ? -1
								: productionOverviewV2.getPostalCode() == null ? 1
										: productionOverviewV1.getPostalCode()
												.compareTo(productionOverviewV2.getPostalCode());
			}
		},
		STED("Sted", false, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV.getPostOffice();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.POSTOFFICE), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getPostoffice())) {
					return productionOverviewV.getPostOffice() != null
							&& productionOverviewV.getPostOffice().toLowerCase().matches(
									productionoverviewFilter.getPostoffice().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getPostOffice() == null && productionOverviewV2.getPostOffice() == null ? 0
						: productionOverviewV1.getPostOffice() == null ? -1
								: productionOverviewV2.getPostOffice() == null ? 1
										: productionOverviewV1.getPostOffice()
												.compareTo(productionOverviewV2.getPostOffice());
			}
		},
		TYPE("Type", false, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV.getConstructionTypeName();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory.createTextField(
						presentationModel.getModel(ProductionoverviewFilter.CONSTRUCTION_TYPE_NAME), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getConstructionTypeName())) {
					return productionOverviewV.getConstructionTypeName() != null && productionOverviewV
							.getConstructionTypeName().toLowerCase().matches(productionoverviewFilter
									.getConstructionTypeName().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getConstructionTypeName() == null
						&& productionOverviewV2.getConstructionTypeName() == null
								? 0
								: productionOverviewV1.getConstructionTypeName() == null ? -1
										: productionOverviewV2.getConstructionTypeName() == null ? 1
												: productionOverviewV1.getConstructionTypeName()
														.compareTo(productionOverviewV2.getConstructionTypeName());
			}
		},
		STØRRELSE("Størrelse", false, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV.getInfo();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory.createTextField(presentationModel.getModel(ProductionoverviewFilter.INFO),
						false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getInfo())) {
					return productionOverviewV.getInfo() != null && productionOverviewV.getInfo().toLowerCase()
							.matches(productionoverviewFilter.getInfo().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getInfo() == null && productionOverviewV2.getInfo() == null ? 0
						: productionOverviewV1.getInfo() == null ? -1
								: productionOverviewV2.getInfo() == null ? 1
										: productionOverviewV1.getInfo().compareTo(productionOverviewV2.getInfo());
			}
		},
		AVDELING("Avdeling", false, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getProductArea();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.PRODUCT_AREA), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getProductArea())) {
					return productionOverviewV.getProductArea() != null
							&& productionOverviewV.getProductArea().toLowerCase().matches(
									productionoverviewFilter.getProductArea().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getProductArea() == null && productionOverviewV2.getProductArea() == null
						? 0
						: productionOverviewV1.getProductArea() == null ? -1
								: productionOverviewV2.getProductArea() == null ? 1
										: productionOverviewV1.getProductArea()
												.compareTo(productionOverviewV2.getProductArea());
			}
		},
		PROD_UKE("Prod.uke", true, 70, true) {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getProductionWeek();
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.PRODUCTION_WEEK), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getProductionWeek())) {
					return productionOverviewV.getProductionWeek() != null
							&& String.valueOf(productionOverviewV.getProductionWeek()).toLowerCase().matches(
									productionoverviewFilter.getProductionWeek().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getProductionWeek() == null
						&& productionOverviewV2.getProductionWeek() == null
								? 0
								: productionOverviewV1.getProductionWeek() == null ? -1
										: productionOverviewV2.getProductionWeek() == null ? 1
												: productionOverviewV1.getProductionWeek()
														.compareTo(productionOverviewV2.getProductionWeek());
			}
		},
		TRANSPORT("Transport", false, 120, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				// return transportable.getTransport();
				return hentTransport(productionOverviewV);
			}

			private String hentTransport(ProductionOverviewV productionOverviewV) {
				return productionOverviewV.getTransportYear() == null ? ""
						: productionOverviewV.getTransportYear() + " " + productionOverviewV.getTransportWeek() + "-"
								+ productionOverviewV.getTransportName();
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.TRANSPORT), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getTransport())) {
					return hentTransport(productionOverviewV).toLowerCase().matches(
							productionoverviewFilter.getTransport().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentTransport(productionOverviewV1).compareTo(hentTransport(productionOverviewV2));
			}
		},
		ESTIMERT_TID_VEGG("Estimert tid vegg", true, 100, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getEstimatedTimeWall();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				popupMenuProduction.add(menuItemMap.get(getColumnName()));
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory.createTextField(
						presentationModel.getModel(ProductionoverviewFilter.ESTIMATED_TIME_WALL), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getEstimatedTimeWall())) {
					return productionOverviewV.getEstimatedTimeWall() != null
							&& String.valueOf(productionOverviewV.getEstimatedTimeWall()).toLowerCase()
									.matches(productionoverviewFilter.getEstimatedTimeWall().toLowerCase()
											.replaceAll("%", ".*"));
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getEstimatedTimeWall() == null
						&& productionOverviewV2.getEstimatedTimeWall() == null
								? 0
								: productionOverviewV1.getEstimatedTimeWall() == null ? -1
										: productionOverviewV2.getEstimatedTimeWall() == null ? 1
												: productionOverviewV1.getEstimatedTimeWall()
														.compareTo(productionOverviewV2.getEstimatedTimeWall());
			}
		},
		TID_VEGG("Tid vegg", true, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return calculateTimeWall(productionOverviewV);
			}

			private BigDecimal calculateTimeWall(ProductionOverviewV productionOverviewV) {
				return productionOverviewV.getWallRealProductionHours();
//						== null
//						? Tidsforbruk.beregnTidsforbruk(productionOverviewV.getWallActionStarted(),
//								productionOverviewV.getWallProduced())
//						: productionOverviewV.getWallRealProductionHours();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.TIME_WALL), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getTimeWall())) {
					return calculateTimeWall(productionOverviewV) != null
							&& String.valueOf(calculateTimeWall(productionOverviewV)).toLowerCase().matches(
									productionoverviewFilter.getTimeWall().toLowerCase().replaceAll("%", ".*"));
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return calculateTimeWall(productionOverviewV1) == null
						&& calculateTimeWall(productionOverviewV2) == null
								? 0
								: calculateTimeWall(productionOverviewV1) == null ? -1
										: calculateTimeWall(productionOverviewV2) == null ? 1
												: calculateTimeWall(productionOverviewV1)
														.compareTo(calculateTimeWall(productionOverviewV2));
			}
		},
		ESTIMERT_TID_GAVL("Estimert tid gavl", true, 90, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getEstimatedTimeGavl();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				popupMenuProduction.add(menuItemMap.get(getColumnName()));
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory.createTextField(
						presentationModel.getModel(ProductionoverviewFilter.ESTIMATED_TIME_GAVL), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getEstimatedTimeGavl())) {
					return productionOverviewV.getEstimatedTimeGavl() != null
							&& String.valueOf(productionOverviewV.getEstimatedTimeGavl()).toLowerCase()
									.matches(productionoverviewFilter.getEstimatedTimeGavl().toLowerCase()
											.replaceAll("%", ".*"));
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getEstimatedTimeGavl() == null
						&& productionOverviewV2.getEstimatedTimeGavl() == null
								? 0
								: productionOverviewV1.getEstimatedTimeGavl() == null ? -1
										: productionOverviewV2.getEstimatedTimeGavl() == null ? 1
												: productionOverviewV1.getEstimatedTimeGavl()
														.compareTo(productionOverviewV2.getEstimatedTimeGavl());
			}
		},
		TID_GAVL("Tid gavl", true, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV productionOverviewV, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return calculateTimeGavl(productionOverviewV);
			}

			private BigDecimal calculateTimeGavl(ProductionOverviewV productionOverviewV) {
				return productionOverviewV.getGavlRealProductionHours();
//						== null
//						? Tidsforbruk.beregnTidsforbruk(productionOverviewV.getGavlActionStarted(),
//								productionOverviewV.getGavlProduced())
//						: productionOverviewV.getGavlRealProductionHours();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.TIME_GAVL), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getTimeWall())) {
					return calculateTimeGavl(productionOverviewV) != null
							&& String.valueOf(calculateTimeGavl(productionOverviewV)).toLowerCase().matches(
									productionoverviewFilter.getTimeGavl().toLowerCase().replaceAll("%", ".*"));
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return calculateTimeGavl(productionOverviewV1) == null
						&& calculateTimeGavl(productionOverviewV2) == null
								? 0
								: calculateTimeGavl(productionOverviewV1) == null ? -1
										: calculateTimeGavl(productionOverviewV2) == null ? 1
												: calculateTimeGavl(productionOverviewV1)
														.compareTo(calculateTimeGavl(productionOverviewV2));
			}
		},
		ESTIMERT_TID_PAKK("Estimert tid pakk", true, 90, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getEstimatedTimePack();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				popupMenuProduction.add(menuItemMap.get(getColumnName()));
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory.createTextField(
						presentationModel.getModel(ProductionoverviewFilter.ESTIMATED_TIME_PACK), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getEstimatedTimePack())) {
					return productionOverviewV.getEstimatedTimePack() != null
							&& String.valueOf(productionOverviewV.getEstimatedTimePack()).toLowerCase()
									.matches(productionoverviewFilter.getEstimatedTimePack().toLowerCase()
											.replaceAll("%", ".*"));
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getEstimatedTimePack() == null
						&& productionOverviewV2.getEstimatedTimePack() == null
								? 0
								: productionOverviewV1.getEstimatedTimePack() == null ? -1
										: productionOverviewV2.getEstimatedTimePack() == null ? 1
												: productionOverviewV1.getEstimatedTimePack()
														.compareTo(productionOverviewV2.getEstimatedTimePack());
			}
		},
		TEGNINGER_JATAK("Tegninger Jatak", true, 90, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getReceivedTrossDrawing();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				popupMenuProduction.add(menuItemMap.get(getColumnName()));
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.TEGNINGER_JATAK), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getTegningerJatak())) {
					return productionOverviewV.getReceivedTrossDrawing() != null
							&& productionOverviewV.getReceivedTrossDrawing().toLowerCase().matches(
									productionoverviewFilter.getTegningerJatak().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getReceivedTrossDrawing() == null
						&& productionOverviewV2.getReceivedTrossDrawing() == null
								? 0
								: productionOverviewV1.getReceivedTrossDrawing() == null ? -1
										: productionOverviewV2.getReceivedTrossDrawing() == null ? 1
												: productionOverviewV1.getReceivedTrossDrawing()
														.compareTo(productionOverviewV2.getReceivedTrossDrawing());
			}
		},
		ØNSKET_LEVERING("Ønsket levering", true, 90, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {

				return hentOnsketLevering(transportable);
			}

			private String hentOnsketLevering(ProductionOverviewV transportable) {
				Integer forventetLevering = transportable.getTrossDeldt() == null ? 0 : transportable.getTrossDeldt();
				Integer bekreftetLevering = transportable.getTrossCfdeldt() == null ? 0
						: transportable.getTrossCfdeldt();

				return bekreftetLevering == 0 ? "" + forventetLevering : bekreftetLevering + "(B)";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.ONSKET_LEVERING), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getOnsketLevering())) {
					return hentOnsketLevering(productionOverviewV) != null
							&& hentOnsketLevering(productionOverviewV).toLowerCase().matches(
									productionoverviewFilter.getOnsketLevering().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentOnsketLevering(productionOverviewV1) == null
						&& hentOnsketLevering(productionOverviewV2) == null
								? 0
								: hentOnsketLevering(productionOverviewV1) == null ? -1
										: hentOnsketLevering(productionOverviewV2) == null ? 1
												: hentOnsketLevering(productionOverviewV1)
														.compareTo(hentOnsketLevering(productionOverviewV2));
			}
		},
		VEGG("Vegg", true, 50, false) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.isPostShipment()) {
					return "";
				}
				return hentVeggstatus(statusMap, statusCheckers);
			}

			private String hentVeggstatus(Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {

				String veggStatus = statusMap.get(statusCheckers.get("Vegg").getArticleName());
				return veggStatus == null ? "MANGLER" : veggStatus;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers,
						getColumnName());
				Applyable applyable = getApplyObject(transportable, handler, window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett vegg produsert",
						"Sett vegg ikke produsert", handler, popupMenuProduction, applyable);
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.WALL_STATUS), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getWallStatus())) {
					return hentVeggstatus(statusMap, statusCheckers) != null
							&& hentVeggstatus(statusMap, statusCheckers).toLowerCase().matches(
									productionoverviewFilter.getWallStatus().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentVeggstatus(statusMap1, statusCheckers) == null
						&& hentVeggstatus(statusMap2, statusCheckers) == null
								? 0
								: hentVeggstatus(statusMap1, statusCheckers) == null ? -1
										: hentVeggstatus(statusMap2, statusCheckers) == null ? 1
												: hentVeggstatus(statusMap1, statusCheckers)
														.compareTo(hentVeggstatus(statusMap2, statusCheckers));
			}
		},
		GULVSPON("Gulvspon", false, 80, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.isPostShipment()) {
					return "";
				}
				return hentGulvsponstatus(statusMap, statusCheckers);
			}

			private String hentGulvsponstatus(Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				String gulvsponStatus = statusMap.get(statusCheckers.get("Gulvspon").getArticleName());
				return gulvsponStatus == null ? "MANGLER" : gulvsponStatus;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers,
						getColumnName());
				Applyable applyable = getApplyObject(transportable, handler, window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett gulvspon pakket",
						"Sett gulvspon ikke pakket", handler, popupMenuProduction, applyable);
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.GULVSPON_STATUS), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getGulvsponStatus())) {
					return hentGulvsponstatus(statusMap, statusCheckers) != null
							&& hentGulvsponstatus(statusMap, statusCheckers).toLowerCase().matches(
									productionoverviewFilter.getGulvsponStatus().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentGulvsponstatus(statusMap1, statusCheckers) == null
						&& hentGulvsponstatus(statusMap2, statusCheckers) == null
								? 0
								: hentGulvsponstatus(statusMap1, statusCheckers) == null ? -1
										: hentGulvsponstatus(statusMap2, statusCheckers) == null ? 1
												: hentGulvsponstatus(statusMap1, statusCheckers)
														.compareTo(hentGulvsponstatus(statusMap2, statusCheckers));
			}
		},
		TAKSTOL("Takstol", false, 80, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.isPostShipment()) {
					return "";
				}
				return hentTakstolstatus(statusMap, statusCheckers);
			}

			private String hentTakstolstatus(Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				String takstolStatus = statusMap.get(statusCheckers.get("Takstol").getArticleName());
				return takstolStatus == null ? "MANGLER" : takstolStatus;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers,
						getColumnName() + "Produksjon");
				Applyable applyable = getApplyObject(transportable, handler, window);
				// takstol produksjon
				setMenuItem(transportable, menuItemMap.get(getColumnName() + "Produksjon"), "Sett takstol produsert",
						"Sett takstol ikke produsert", handler, popupMenuProduction, applyable);
				// takstol produksjonsenhet
				setMenuItem(transportable, menuItemMap.get(getColumnName() + "ProduksjonEnhet"),
						"Sett produksjonsenhet...", "Sett produksjonsenhet...", handler, popupMenuProduction,
						applyable);

				// takstolinfo
				setMenuItem(transportable, menuItemMap.get(getColumnName() + "Takstolinfo"), "Takstolinfo...",
						"Takstolinfo...", handler, popupMenuProduction, applyable);

				handler = getHandler(productionPackageHandlers, getColumnName() + "Pakking");
				applyable = getApplyObject(transportable, handler, window);
				// takstol pakking
				setMenuItem(transportable, menuItemMap.get(getColumnName() + "Pakking"), "Sett takstol pakket",
						"Sett takstol ikke pakket", handler, popupMenuProduction, applyable);
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.TAKSTOL_STATUS), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getTakstolStatus())) {
					return hentTakstolstatus(statusMap, statusCheckers) != null
							&& hentTakstolstatus(statusMap, statusCheckers).toLowerCase().matches(
									productionoverviewFilter.getTakstolStatus().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentTakstolstatus(statusMap1, statusCheckers) == null
						&& hentTakstolstatus(statusMap2, statusCheckers) == null
								? 0
								: hentTakstolstatus(statusMap1, statusCheckers) == null ? -1
										: hentTakstolstatus(statusMap2, statusCheckers) == null ? 1
												: hentTakstolstatus(statusMap1, statusCheckers)
														.compareTo(hentTakstolstatus(statusMap2, statusCheckers));
			}
		},
		GAVL("Gavl", false, 80, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.isPostShipment()) {
					return "";
				}
				return hentGavlstatus(statusMap, statusCheckers);
			}

			private String hentGavlstatus(Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				String gavlstatus = statusMap.get(statusCheckers.get("Gavl").getArticleName());
				return gavlstatus == null ? "MANGLER" : gavlstatus;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers,
						getColumnName());
				Applyable applyable = getApplyObject(transportable, handler, window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett gavl produsert",
						"Sett gavl ikke produsert", handler, popupMenuProduction, applyable);
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.GAVL_STATUS), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getGavlStatus())) {
					return hentGavlstatus(statusMap, statusCheckers) != null
							&& hentGavlstatus(statusMap, statusCheckers).toLowerCase().matches(
									productionoverviewFilter.getGavlStatus().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentGavlstatus(statusMap1, statusCheckers) == null
						&& hentGavlstatus(statusMap2, statusCheckers) == null
								? 0
								: hentGavlstatus(statusMap1, statusCheckers) == null ? -1
										: hentGavlstatus(statusMap2, statusCheckers) == null ? 1
												: hentGavlstatus(statusMap1, statusCheckers)
														.compareTo(hentGavlstatus(statusMap2, statusCheckers));
			}
		},

		PAKK("Pakk", false, 80, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.isPostShipment()) {
					return "";
				}
				return hentPakkstatus(statusMap, statusCheckers);
			}

			private String hentPakkstatus(Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				String pakkstatus = statusMap.get(statusCheckers.get("Front").getArticleName());
				return pakkstatus == null ? "MANGLER" : pakkstatus;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers,
						getColumnName());
				Applyable applyable = getApplyObject(transportable, handler, window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett front produsert",
						"Sett front ikke produsert", handler, popupMenuProduction, applyable);
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.PAKK_STATUS), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getPakkStatus())) {
					return hentPakkstatus(statusMap, statusCheckers) != null
							&& hentPakkstatus(statusMap, statusCheckers).toLowerCase().matches(
									productionoverviewFilter.getPakkStatus().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentPakkstatus(statusMap1, statusCheckers) == null
						&& hentPakkstatus(statusMap2, statusCheckers) == null
								? 0
								: hentPakkstatus(statusMap1, statusCheckers) == null ? -1
										: hentPakkstatus(statusMap2, statusCheckers) == null ? 1
												: hentPakkstatus(statusMap1, statusCheckers)
														.compareTo(hentPakkstatus(statusMap2, statusCheckers));
			}
		},

		TAKSTEIN("Takstein", false, 80, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.isPostShipment()) {
					return "";
				}
				return hentTaksteinstatus(statusMap, statusCheckers);
			}

			private String hentTaksteinstatus(Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				String taksteinstatus = statusMap.get(statusCheckers.get("Stein").getArticleName());
				return taksteinstatus == null ? "MANGLER" : taksteinstatus;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers,
						getColumnName());
				Applyable applyable = getApplyObject(transportable, handler, window);
				setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett takstein pakket",
						"Sett takstein ikke pakket", handler, popupMenuProduction, applyable);
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.TAKSTEIN_STATUS), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getTaksteinStatus())) {
					return hentTaksteinstatus(statusMap, statusCheckers) != null
							&& hentTaksteinstatus(statusMap, statusCheckers).toLowerCase().matches(
									productionoverviewFilter.getTaksteinStatus().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentTaksteinstatus(statusMap1, statusCheckers) == null
						&& hentTaksteinstatus(statusMap2, statusCheckers) == null
								? 0
								: hentTaksteinstatus(statusMap1, statusCheckers) == null ? -1
										: hentTaksteinstatus(statusMap2, statusCheckers) == null ? 1
												: hentTaksteinstatus(statusMap1, statusCheckers)
														.compareTo(hentTaksteinstatus(statusMap2, statusCheckers));
			}
		},
		MONTERING("Montering", true, 80, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentMontering(transportable);
			}

			private String hentMontering(ProductionOverviewV transportable) {
				String returnString = "";
				if (transportable.getDoAssembly() != null && transportable.getDoAssembly() == 1) {
					returnString = "M"
							+ (transportable.getAssemblyWeek() == null ? "" : transportable.getAssemblyWeek());
				}
				return returnString;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.MONTERING), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getMontering())) {
					return hentMontering(productionOverviewV) != null
							&& hentMontering(productionOverviewV).toLowerCase().matches(
									productionoverviewFilter.getMontering().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return hentMontering(productionOverviewV1) == null && hentMontering(productionOverviewV2) == null ? 0
						: hentMontering(productionOverviewV1) == null ? -1
								: hentMontering(productionOverviewV2) == null ? 1
										: hentMontering(productionOverviewV1)
												.compareTo(hentMontering(productionOverviewV2));
			}
		},
		EGENPRODUKSJON("Egenproduksjon", true, 90, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getOwnProduction();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.EGENPRODUKSJON), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getEgenproduksjon())) {
					return productionOverviewV.getOwnProduction() != null
							&& String.valueOf(productionOverviewV.getOwnProduction()).toLowerCase().matches(
									productionoverviewFilter.getEgenproduksjon().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getOwnProduction() == null
						&& productionOverviewV2.getOwnProduction() == null
								? 0
								: productionOverviewV1.getOwnProduction() == null ? -1
										: productionOverviewV2.getOwnProduction() == null ? 1
												: productionOverviewV1.getOwnProduction()
														.compareTo(productionOverviewV2.getOwnProduction());
			}
		},
		// PROCENT("%") {
		// @Override
		// public Class<?> getColumnClass() {
		// return ProcentDone.class;
		// }
		//
		// @Override
		// public Object getValue(ProductionOverviewV transportable, Map<String,
		// String> statusMap,
		// Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
		// // return transportable.getLastProcentDone();
		// return null;
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
		// },
		KOST("Kost", true, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getOwnProductionInternal();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory.createTextField(presentationModel.getModel(ProductionoverviewFilter.KOST),
						false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getKost())) {
					return productionOverviewV.getOwnProductionInternal() != null
							&& String.valueOf(productionOverviewV.getOwnProductionInternal()).toLowerCase().matches(
									productionoverviewFilter.getKost().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getOwnProductionInternal() == null
						&& productionOverviewV2.getOwnProductionInternal() == null
								? 0
								: productionOverviewV1.getOwnProductionInternal() == null ? -1
										: productionOverviewV2.getOwnProductionInternal() == null ? 1
												: productionOverviewV1.getOwnProductionInternal()
														.compareTo(productionOverviewV2.getOwnProductionInternal());
			}
		},
		FRAKT("Frakt", true, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getDeliveryCost();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory.createTextField(presentationModel.getModel(ProductionoverviewFilter.FRAKT),
						false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getFrakt())) {
					return productionOverviewV.getDeliveryCost() != null
							&& String.valueOf(productionOverviewV.getDeliveryCost()).toLowerCase().matches(
									productionoverviewFilter.getFrakt().toLowerCase().replaceAll("%", ".*") + ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getDeliveryCost() == null && productionOverviewV2.getDeliveryCost() == null
						? 0
						: productionOverviewV1.getDeliveryCost() == null ? -1
								: productionOverviewV2.getDeliveryCost() == null ? 1
										: productionOverviewV1.getDeliveryCost()
												.compareTo(productionOverviewV2.getDeliveryCost());
			}
		},
		MONTERINGSUM("Monteringsum", true, 80, true) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getAssemblyCost();
			}

			@Override
			public Class<?> getColumnClass() {
				return BigDecimal.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return BasicComponentFactory
						.createTextField(presentationModel.getModel(ProductionoverviewFilter.MONTERINGSUM), false);
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (StringUtils.isNotBlank(productionoverviewFilter.getMonteringsum())) {
					return productionOverviewV.getAssemblyCost() != null
							&& String.valueOf(productionOverviewV.getAssemblyCost()).toLowerCase().matches(
									productionoverviewFilter.getMonteringsum().toLowerCase().replaceAll("%", ".*")
											+ ".*");
				}
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return productionOverviewV1.getAssemblyCost() == null && productionOverviewV2.getAssemblyCost() == null
						? 0
						: productionOverviewV1.getAssemblyCost() == null ? -1
								: productionOverviewV2.getAssemblyCost() == null ? 1
										: productionOverviewV1.getAssemblyCost()
												.compareTo(productionOverviewV2.getAssemblyCost());
			}
		},

		KOMPLETT("Komplett", true, 80, false) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.getOrderComplete() != null) {
					return "Ja";
				}
				return "Nei";
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				// TODO Auto-generated method stub
				return 0;
			}
		},
		KLAR("Klar", true, 80, false) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				if (transportable.getOrderReady() != null) {
					return "Ja";
				}
				return "Nei";
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				// TODO Auto-generated method stub
				return 0;
			}
		},

		PRODUKTOMRÅDE("Produktområde", true, 80, false) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getProductAreaGroupName();
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				// TODO Auto-generated method stub
				return 0;
			}
		},
		KOMMENTARER("Kommentarer", false, 200, false) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getComment();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return null;
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return false;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return 0;
			}
		},
		ANTALL_STANDARDVEGGER("Antall standardvegger", false, 200, false) {
			@Override
			public Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return transportable.getAntallStandardvegger();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
					JPopupMenu popupMenuProduction) {
				return true;
			}

			@Override
			public Component getFilterComponent(PresentationModel presentationModel) {
				return null;
			}

			@Override
			public boolean filter(ProductionOverviewV productionOverviewV,
					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return false;
			}

			@Override
			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
					Map<String, String> statusMap1, Map<String, String> statusMap2,
					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
				return 0;
			}
		}
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

		;

		private String columnName;
		private boolean centerAlignment;
		private int width;
		private boolean visible;

		private ProductionColumn(String aColumnName, boolean centerAlignment, int width, boolean visible) {
			columnName = aColumnName;
			this.centerAlignment = centerAlignment;
			this.width = width;
			this.visible = visible;
		}

		public String getColumnName() {
			return columnName;
		}

		@Override
		public String toString() {
			return columnName;
		}

		private static void setMenuItem(final Transportable transportable, final JMenuItem menuItem,
				final String applyString, final String unapplyString,
				final AbstractProductionPackageViewHandler<Applyable> handler, JPopupMenu popupMenuProduction,
				final Applyable applyable) {
			if (applyable != null) {
				if ((transportable instanceof PostShipment && applyable.isForPostShipment())
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

		private static Applyable getApplyObject(final Transportable transportable,
				final AbstractProductionPackageViewHandler<Applyable> handler, final WindowInterface window) {
			return handler != null ? handler.getApplyObject(transportable, window) : null;

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

		public abstract Object getValue(ProductionOverviewV transportable, Map<String, String> statusMap,
				Map<String, StatusCheckerInterface<Transportable>> statusCheckers);

		public abstract Class<?> getColumnClass();

		@SuppressWarnings("unchecked")
		public abstract boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
				WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
				JPopupMenu popupMenuProduction);

		public boolean hasCenterAlignment() {
			return centerAlignment;
		}

		public int getWidth() {
			return width;
		}

		public static List<ProductionColumn> getVisibleColumns() {
			List<ProductionColumn> columns = Lists.newArrayList();
			for (ProductionColumn column : ProductionColumn.values()) {
				if (column.isVisible()) {
					columns.add(column);
				}
			}
			return columns;
		}

		private boolean isVisible() {
			return visible;
		}

		public abstract Component getFilterComponent(PresentationModel presentationModel);

		public abstract boolean filter(ProductionOverviewV productionOverviewV,
				ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
				Map<String, StatusCheckerInterface<Transportable>> statusCheckers);

		public abstract int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
				Map<String, String> statusMap1, Map<String, String> statusMap2,
				Map<String, StatusCheckerInterface<Transportable>> statusCheckers);
	}

	public final class ProductionOverviewTableModel extends AbstractTableAdapter {

		ProductionOverviewTableModel(ListModel listModel) {
			super(listModel, ProductionColumn.getColumnNames());

		}

		public Transportable getTransportable(int rowIndex) {
			return (Transportable) getRow(rowIndex);
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			ProductionOverviewV transportable = (ProductionOverviewV) getRow(rowIndex);

			Map<String, String> statusMap = Util.createStatusMap(transportable.getStatus());
			String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
			// if (!Hibernate.isInitialized(transportable.getOrderLines()) ||
			// !Hibernate.isInitialized(transportable.getOrder().getOrderCosts()))
			// {
			// if (Order.class.isInstance(transportable)) {
			// ((OrderManager) overviewManager).lazyLoadOrder((Order)
			// transportable, new LazyLoadOrderEnum[] {
			// LazyLoadOrderEnum.ORDER_LINES,
			// LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.PROCENT_DONE });
			// } else {
			// PostShipmentManager postShipmentManager = (PostShipmentManager)
			// ModelUtil.getBean("postShipmentManager");
			// postShipmentManager.lazyLoad((PostShipment) transportable,
			// new LazyLoadPostShipmentEnum[] {
			// LazyLoadPostShipmentEnum.ORDER_LINES });
			// }
			// }
			return ProductionColumn.valueOf(columnName).getValue(transportable, statusMap, statusCheckers);

		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
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
				orderManager.lazyLoadOrder((Order) transportable,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS, LazyLoadOrderEnum.COLLIES });
			}
			transportable.cacheComments();
		} else {
			if (load) {
				PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
						.getBean("postShipmentManager");
				postShipmentManager.lazyLoad((PostShipment) transportable, new LazyLoadPostShipmentEnum[] {
						LazyLoadPostShipmentEnum.ORDER_COMMENTS, LazyLoadPostShipmentEnum.COLLIES });
			}
			transportable.cacheComments();
		}
	}

	// void initTransportable(Transportable transportable, WindowInterface
	// window) {
	// PostShipmentManager postShipmentManager = (PostShipmentManager)
	// ModelUtil.getBean(PostShipmentManager.MANAGER_NAME);
	// CustTrManager custTrManager = (CustTrManager)
	// ModelUtil.getBean(CustTrManager.MANAGER_NAME);
	// OrdlnManager ordlnManager = (OrdlnManager)
	// ModelUtil.getBean(OrdlnManager.MANAGER_NAME);
	// Set<String> checkers = statusCheckers.keySet();
	// Map<String, String> statusMap;
	//
	// String status;
	// StatusCheckerInterface<Transportable> checker;
	// boolean orderLoaded = false;
	// boolean needToSave = false;
	//
	// transportable.setCustTrs(custTrManager.findByOrderNr(transportable.getOrder().getOrderNr()));
	//
	// statusMap = Util.createStatusMap(transportable.getStatus());
	// for (String checkerName : checkers) {
	// checker = statusCheckers.get(checkerName);
	// status = statusMap.get(checker.getArticleName());
	//
	// if (status == null) {
	// needToSave = true;
	// if (!orderLoaded && transportable instanceof Order) {
	// ((OrderManager) overviewManager).lazyLoadOrder((Order) transportable, new
	// LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES,
	// LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
	// LazyLoadOrderEnum.COMMENTS,
	// LazyLoadOrderEnum.PROCENT_DONE });
	// orderLoaded = true;
	// } else if (!orderLoaded && transportable instanceof PostShipment) {
	// postShipmentManager.lazyLoad((PostShipment) transportable, new
	// LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES,
	// LazyLoadPostShipmentEnum.ORDER_LINES,
	// LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
	// LazyLoadPostShipmentEnum.ORDER_COMMENTS });
	// orderLoaded = true;
	// }
	// status = checker.getArticleStatus(transportable);
	//
	// }
	// statusMap.put(checker.getArticleName(), status);
	//
	// }
	// if (!Hibernate.isInitialized(transportable.getOrderLines())) {
	// ((OrderManager) overviewManager).lazyLoadOrder((Order) transportable, new
	// LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES,
	// LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
	// LazyLoadOrderEnum.COMMENTS,
	// LazyLoadOrderEnum.PROCENT_DONE });
	// }
	// OrderLine takstol = transportable.getOrderLine("Takstoler");
	// if (takstol != null) {
	// Ordln ordln = ordlnManager.findByOrdNoAndLnNo(takstol.getOrdNo(),
	// takstol.getLnNo());
	// if (ordln != null && ordln.getPurcno() != null) {
	// Ord ord = ordlnManager.findByOrdNo(ordln.getPurcno());
	// if (ord != null) {
	// transportable.setTakstolKjopOrd(ord);
	// }
	// }
	// }
	//
	// transportable.setStatus(Util.statusMapToString(statusMap));
	//
	// if (transportable.getComment() == null) {
	// needToSave = true;
	// cacheComment(transportable, window, !orderLoaded);
	// orderLoaded = true;
	// }
	//
	// if (needToSave) {
	// if (transportable instanceof Order) {
	// try {
	// ((OrderManager) overviewManager).saveOrder((Order) transportable);
	// } catch (ProTransException e) {
	// Util.showErrorDialog(window, "Feil", e.getMessage());
	// e.printStackTrace();
	// }
	// } else {
	// postShipmentManager.savePostShipment((PostShipment) transportable);
	// }
	// }
	// if (transportable instanceof Order && !Hibernate.isInitialized(((Order)
	// transportable).getProcentDones())) {
	// ((OrderManager) overviewManager).lazyLoadOrder(((Order) transportable),
	// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.PROCENT_DONE });
	// }
	//
	// }

	// private void initOrders(List<Transportable> transportables,
	// WindowInterface window) {
	// if (transportables != null) {
	//
	// for (Transportable transportable : transportables) {
	// initTransportable(transportable, window);
	// }
	// }
	// }

	@SuppressWarnings("unchecked")
	// @Override
	protected void initObjects() {
		if (!loaded) {

			// if (table != null && table.getModel().getRowCount() > 1) {
			// table.setRowSelectionInterval(1, 1);
			// }
			objectList.clear();
			objectSelectionList.clearSelection();

			// List<Order> allOrders = ((OrderManager)
			// overviewManager).findAllNotSent();
			productionoverviewList = overviewManager.findAll();

			if (productionoverviewList != null) {
				// if (initOrders(productionoverviewList)) {
				// productionoverviewList = overviewManager.findAll();
				// }
				objectList.addAll(productionoverviewList);
			}

			Summer summer = summerProduksjon(productionoverviewList);

			if (labelAntallGarasjer == null) {
				labelAntallGarasjer = new JLabel(String.valueOf(summer.antallGarasjer));
			} else {
				labelAntallGarasjer.setText(String.valueOf(summer.antallGarasjer));
			}

			if (labelSumTidVegg == null) {
				labelSumTidVegg = new JLabel(String.valueOf(summer.sumTidVegg));
			} else {
				labelSumTidVegg.setText(String.valueOf(summer.sumTidVegg));
			}

			if (labelSumTidGavl == null) {
				labelSumTidGavl = new JLabel(String.valueOf(summer.sumTidGavl));
			} else {
				labelSumTidGavl.setText(String.valueOf(summer.sumTidGavl));
			}

			if (labelSumTidPakk == null) {
				labelSumTidPakk = new JLabel(String.valueOf(summer.sumTidPakk));
			} else {
				labelSumTidPakk.setText(String.valueOf(summer.sumTidPakk));
			}

			// PostShipmentManager postShipmentManager = (PostShipmentManager)
			// ModelUtil.getBean("postShipmentManager");
			// List<PostShipment> allPostShipments =
			// postShipmentManager.findAllNotSent();
			// if (allPostShipments != null) {
			// objectList.addAll(allPostShipments);
			// }
			// Collections.sort(objectList, new
			// ProductionOverviewVComparator());
			if (table != null) {
				table.scrollRowToVisible(0);
			}

		}
	}

	private Summer summerProduksjon(List<ProductionOverviewV> produksjonliste) {
		Summer summer = new Summer();
		for (ProductionOverviewV productionOverviewV : produksjonliste) {
			summer.antallGarasjer++;
			summer.sumTidVegg = summer.sumTidVegg
					.add(productionOverviewV.getEstimatedTimeWall() == null ? BigDecimal.ZERO
							: productionOverviewV.getEstimatedTimeWall());
			summer.sumTidGavl = summer.sumTidGavl
					.add(productionOverviewV.getEstimatedTimeGavl() == null ? BigDecimal.ZERO
							: productionOverviewV.getEstimatedTimeGavl());
			summer.sumTidPakk = summer.sumTidPakk
					.add(productionOverviewV.getEstimatedTimePack() == null ? BigDecimal.ZERO
							: productionOverviewV.getEstimatedTimePack());
		}
		return summer;
	}

	private boolean initOrders(List<ProductionOverviewV> productionoverviewList) {
		boolean ordreOppdatert = false;
		for (ProductionOverviewV productionOverviewV : productionoverviewList) {
			Map<String, String> statusMap = Util.createStatusMap(productionOverviewV.getStatus());
			Set<String> checkers = statusCheckers.keySet();
			OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
			boolean needToSave = false;
			Order order = null;
			for (String checkerName : checkers) {
				StatusCheckerInterface<Transportable> checker = statusCheckers.get(checkerName);
				String status = statusMap.get(checker.getArticleName());

				if (status == null) {
					needToSave = true;
					ordreOppdatert = true;
					if (!productionOverviewV.isPostShipment()) {
						order = orderManager.findByOrderNr(productionOverviewV.getOrderNr());
						orderManager.lazyLoadOrder(order,
								new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES,
										LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.COMMENTS,
										LazyLoadOrderEnum.PROCENT_DONE });
						status = checker.getArticleStatus(order);
						statusMap.put(checker.getArticleName(), status);
						order.setStatus(Util.statusMapToString(statusMap));
					}

				}

			}
			if (needToSave && order != null) {
				orderManager.saveObject(order);
			}

		}
		return ordreOppdatert;
	}

	public JButton getButtonRefresh(WindowInterface window) {
		if (buttonRefresh == null) {
			buttonRefresh = new RefreshButton(this, window);
			buttonRefresh.setName("ButtonRefresh");
			setupMenuListeners(window);
		}
		return buttonRefresh;
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

	// public JComboBox getComboBoxProductAreaGroup() {
	// return Util.getComboBoxProductAreaGroup(login.getApplicationUser(),
	// userType, productAreaGroupModel);
	// }

	@SuppressWarnings("unchecked")
	// @Override
	public JXTable getTable(WindowInterface window) {
		initObjects();
		// initOrders(objectList, window);

		ColorHighlighter readyHighlighter = new ColorHighlighter(
				new PatternPredicate("Ja", ProductionColumn.KOMPLETT.ordinal()), ColorEnum.GREEN.getColor(), null);
		ColorHighlighter startedPackingHighlighter = new ColorHighlighter(
				new PatternPredicate("Ja", ProductionColumn.KLAR.ordinal()), ColorEnum.YELLOW.getColor(), null);

		table = new JXTable();
		productionOverviewTableModel = new ProductionOverviewTableModel(objectList);
		table.setModel(productionOverviewTableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionModel(new SingleListSelectionAdapter(objectSelectionList.getSelectionIndexHolder()));
		table.setColumnControlVisible(true);
		table.setColumnControl(new UBColumnControlPopup(table, this));

		table.addMouseListener(new TableClickHandler(window));

		table.setRowHeight(30);

		table.addHighlighter(HighlighterFactory.createAlternateStriping());
		table.addHighlighter(startedPackingHighlighter);
		table.addHighlighter(readyHighlighter);

		DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
		tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (ProductionColumn column : ProductionColumn.values()) {
			if (column.hasCenterAlignment()) {
				table.getColumnExt(column.ordinal()).setCellRenderer(tableCellRenderer);
			}
			table.getColumnExt(column.ordinal()).setPreferredWidth(column.getWidth());
		}

		// ordre
		// table.getColumnExt(ProductionColumn.ORDRE.ordinal()).setPreferredWidth(220);
		table.getColumnModel().getColumn(ProductionColumn.ORDRENR.ordinal())
				.setCellRenderer(new TextPaneRendererProductionoverviewV());

		// pakkliste
		// table.getColumnExt(ProductionColumn.PAKKLISTE.ordinal()).setPreferredWidth(80);
		// table.getColumnExt(ProductionColumn.PAKKLISTE.ordinal()).setCellRenderer(tableCellRenderer);
		//
		// table.getColumnExt(ProductionColumn.TIDSBRUK_PAKKLISTE.ordinal()).setPreferredWidth(110);
		// table.getColumnExt(ProductionColumn.TIDSBRUK_PAKKLISTE.ordinal()).setCellRenderer(tableCellRenderer);
		//
		// table.getColumnExt(ProductionColumn.LAGET_PAKKLISTE.ordinal()).setPreferredWidth(110);
		// table.getColumnExt(ProductionColumn.LAGET_PAKKLISTE.ordinal()).setCellRenderer(tableCellRenderer);
		//
		// table.getColumnExt(ProductionColumn.ORDNR.ordinal()).setPreferredWidth(70);
		//
		// table.getColumnExt(ProductionColumn.AVDELING.ordinal()).setPreferredWidth(100);
		//
		// table.getColumnExt(ProductionColumn.PROD_UKE.ordinal()).setCellRenderer(tableCellRenderer);

		// transport
		// table.getColumnExt(ProductionColumn.TRANSPORT.ordinal()).setPreferredWidth(150);
		//
		// table.getColumnExt(ProductionColumn.ESTIMERT_TID_VEGG.ordinal()).setPreferredWidth(100);
		// table.getColumnExt(ProductionColumn.ESTIMERT_TID_VEGG.ordinal()).setCellRenderer(tableCellRenderer);
		//
		// table.getColumnExt(ProductionColumn.TID_VEGG.ordinal()).setCellRenderer(tableCellRenderer);
		//
		// table.getColumnExt(ProductionColumn.ESTIMERT_TID_GAVL.ordinal()).setPreferredWidth(100);
		// table.getColumnExt(ProductionColumn.ESTIMERT_TID_GAVL.ordinal()).setCellRenderer(tableCellRenderer);
		//
		// table.getColumnExt(ProductionColumn.TID_GAVL.ordinal()).setCellRenderer(tableCellRenderer);
		//
		// table.getColumnExt(ProductionColumn.ESTIMERT_TID_PAKK.ordinal()).setPreferredWidth(100);
		// table.getColumnExt(ProductionColumn.ESTIMERT_TID_PAKK.ordinal()).setCellRenderer(tableCellRenderer);

		// vegg
		// table.getColumnExt(ProductionColumn.VEGG.ordinal()).setPreferredWidth(45);
		// table.getColumnExt(ProductionColumn.VEGG.ordinal()).setCellRenderer(tableCellRenderer);
		// pakk
		// table.getColumnExt(ProductionColumn.PAKK.ordinal()).setPreferredWidth(45);
		// table.getColumnExt(ProductionColumn.PAKK.ordinal()).setCellRenderer(tableCellRenderer);
		// gavl
		// table.getColumnExt(ProductionColumn.GAVL.ordinal()).setPreferredWidth(45);
		// table.getColumnExt(ProductionColumn.GAVL.ordinal()).setCellRenderer(tableCellRenderer);
		// takstol
		// table.getColumnExt(ProductionColumn.TAKSTOL.ordinal()).setPreferredWidth(60);
		// table.getColumnExt(ProductionColumn.TAKSTOL.ordinal()).setCellRenderer(tableCellRenderer);
		// //takstein
		// table.getColumnExt(ProductionColumn.TAKSTEIN.ordinal()).setPreferredWidth(60);
		// table.getColumnExt(ProductionColumn.TAKSTEIN.ordinal()).setCellRenderer(tableCellRenderer);
		// gulvspon
		// table.getColumnExt(ProductionColumn.GULVSPON.ordinal()).setPreferredWidth(70);
		// table.getColumnExt(ProductionColumn.GULVSPON.ordinal()).setCellRenderer(tableCellRenderer);
		// montering
		// table.getColumnExt(ProductionColumn.MONTERING.ordinal()).setPreferredWidth(70);
		// table.getColumnExt(ProductionColumn.MONTERING.ordinal()).setCellRenderer(tableCellRenderer);
		// rest
		// table.getColumnExt(14).setPreferredWidth(50);
		// %
		// table.getColumnExt(ProductionColumn.PROCENT.ordinal()).setPreferredWidth(40);
		// table.getColumnModel().getColumn(ProductionColumn.PROCENT.ordinal()).setCellRenderer(new
		// TextPaneRendererProcentDone());

		// table.getColumnExt(ProductionColumn.TEGNINGER_JATAK.ordinal()).setPreferredWidth(100);

		// table.getColumnModel().getColumn(14).setCellRenderer(new
		// TextPaneRendererCustTr());

		// table.getColumnExt(ProductionColumn.KOMPLETT.ordinal()).setVisible(false);
		table.getColumnExt(20).setVisible(false);
		table.getColumnExt(20).setVisible(false);
		table.getColumnExt(20).setVisible(false);
		table.getColumnExt(20).setVisible(false);
		table.getColumnExt(20).setVisible(false);
		table.getColumnExt(20).setVisible(false);
		table.getColumnExt(27).setVisible(false);
		table.getColumnExt(26).setVisible(false);
		table.getColumnExt(25).setVisible(false);

		// table.getColumnExt(ProductionColumn.VEGG.ordinal()).setVisible(false);
		// table.getColumnExt(ProductionColumn.GULVSPON.ordinal()).setVisible(false);
		// table.getColumnExt(ProductionColumn.TAKSTOL.ordinal()).setVisible(false);
		// table.getColumnExt(ProductionColumn.GAVL.ordinal()).setVisible(false);
		// table.getColumnExt(ProductionColumn.PAKK.ordinal()).setVisible(false);
		// table.getColumnExt(ProductionColumn.TAKSTEIN.ordinal()).setVisible(false);
		// table.getColumnExt(10).setVisible(false);
		// table.getColumnExt(11).setVisible(false);
		// table.getColumnExt(10).setVisible(false);
		// table.getColumnExt(11).setVisible(false);
		//

		// setupMenuListeners(window);

		// table.setName("ProductionTable");
		table.setName(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());
		return table;

	}

	private void setupMenuListeners(WindowInterface window) {
		menuItemPacklist
				.addActionListener(new ProductionMenuItemListener(window, "Pakkliste", "Sett pakkliste klar..."));
		menuItemVegg.addActionListener(new ProductionMenuItemListener(window, "Vegg", "Sett vegg produsert"));
		menuItemFront.addActionListener(new ProductionMenuItemListener(window, "Front", "Sett front produsert"));
		menuItemGavl.addActionListener(new ProductionMenuItemListener(window, "Gavl", "Sett gavl produsert"));
		menuItemProductionTakstol.addActionListener(
				new ProductionMenuItemListener(window, "TakstolProduksjon", "Sett takstol produsert"));
		menuItemPackageTakstol
				.addActionListener(new ProductionMenuItemListener(window, "TakstolPakking", "Sett takstol pakket"));
		menuItemTakstein.addActionListener(new ProductionMenuItemListener(window, "Takstein", "Sett takstein pakket"));
		menuItemGulvspon.addActionListener(new ProductionMenuItemListener(window, "Gulvspon", "Sett gulvspon pakket"));
		menuItemOpenOrder.addActionListener(new MenuItemListenerOpenOrder(window));
		menuItemSetProductionWeek.addActionListener(new MenuItemListenerSetProductionWeek(window));
		menuItemShowMissing.addActionListener(new MenuItemListenerShowMissing(window));
		menuItemSetComment.addActionListener(new MenuItemListenerSetComment(window));
		menuItemSetAntallStandardvegger.addActionListener(new MenuItemListenerSetAntallStandardvegger(window));
		menuItemShowContent.addActionListener(new MenuItemListenerShowContent(window));
		menuItemDeviation.addActionListener(new MenuItemListenerDeviation(window));
		menuItemSetProcent.addActionListener(new MenuItemListenerSetProcent(window));
		menuItemSetReceivedTrossDrawing.addActionListener(new MenuItemListenerSetReceivedTrossDrawing(window));
		menuItemSetEstimatedTimeWall.addActionListener(new MenuItemListenerSetEstimatedTimeWall(window));
		menuItemSetEstimatedTimeGavl.addActionListener(new MenuItemListenerSetEstimatedTimeGavl(window));
		menuItemSetEstimatedTimePack.addActionListener(new MenuItemListenerSetEstimatedTimePack(window));
		menuItemProductionUnitTakstol
				.addActionListener(setProductionUnitActionFactory.create(articleTypeTakstol, this, window));

		menuItemShowTakstolInfo = new JMenuItem(showTakstolInfoActionFactory.create(this, window));
		menuItemShowTakstolInfo.setName("MenuItemShowTakstolInfo");
		menuItemShowTakstolInfo.setEnabled(hasWriteAccess());
		menuItemMap.put(ProductionColumn.TAKSTOL.getColumnName() + "Takstolinfo", menuItemShowTakstolInfo);

		menuItemUpdate.addActionListener(new MenuItemUpdate(window));
		menuItemShowDocument.addActionListener(new MenuItemListenerShowDocument(window));
	}

	class MenuItemListenerSetComment implements ActionListener {

		private WindowInterface window;

		public MenuItemListenerSetComment(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			Transportable transportable = getSelectedTransportable();

			if (transportable != null && transportable instanceof Order) {
				Order order = (Order) transportable;
				String value = Util.showTextAreaInputDialogWithdefaultValue(window, "Kommentar", "Kommentar",
						order.getComment() == null ? "" : order.getComment());
				order.setCachedComment(value);
				managerRepository.getOrderManager().saveOrder(order);
				doRefresh(window);
			}
		}
	}

	class MenuItemListenerSetAntallStandardvegger implements ActionListener {

		private WindowInterface window;

		public MenuItemListenerSetAntallStandardvegger(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			Transportable transportable = getSelectedTransportable();

			if (transportable != null && transportable instanceof Order) {
				Order order = (Order) transportable;
				String value = Util.showTextAreaInputDialogWithdefaultValue(window, "Antall standardvegger",
						"Antall standardvegger",
						order.getAntallStandardvegger() == null ? "" : order.getAntallStandardvegger());
				order.setAntallStandardvegger(value);
				managerRepository.getOrderManager().saveOrder(order);
				doRefresh(window);
			}
		}
	}

	class MenuItemListenerShowDocument implements ActionListener {

		private WindowInterface window;

		public MenuItemListenerShowDocument(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			Transportable transportable = getSelectedTransportable();
			DokumentVManager dokumentVManager = (DokumentVManager) ModelUtil.getBean(DokumentVManager.MANAGER_NAME);
			List<DokumentV> dokumenter = dokumentVManager.finnDokumenter(transportable.getOrderNr());
//			List<DokumentV> dokumenter = Arrays.asList(new DokumentV(1, "123", "123 - Test Tstesen - MODELL",
//					"BFL - Endelig ordrebekreftelse for produksjon dette fblir en mye lengre teskt", "Ordre: SO-123 (PDF)", new Date()));
			
			Collection<?> valgteDokumenter = Util.showOptionsDialog(window, dokumenter, "Velg dokument", true, false,
					true);

			if (valgteDokumenter != null && !valgteDokumenter.isEmpty()) {
				DokumentV valgtDokument = (DokumentV) valgteDokumenter.iterator().next();
				if (valgtDokument != null) {
					Dokumentlager.aapneDokument(valgtDokument.getProjectNumber(), valgtDokument.getType());
				}
			}
		}
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
		boolean success = transportable != null
				? orderViewHandler.openOrderView(transportable.getOrder(), false, window, false)
				: false;
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
			String columnHeader = StringUtils.upperCase((String) table.getColumnExt(col).getHeaderValue())
					.replaceAll(" ", "_").replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
			;
			ProductionColumn productionColumn = ProductionColumn.valueOf(columnHeader);
			boolean success = transportable != null
					? productionColumn.setMenus(transportable, menuItemMap, window, productionPackageHandlers,
							popupMenuProduction)
					: false;
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

	}

	private Transportable getSelectedTransportable() {
		Transportable transportable = null;
		if (objectSelectionList.getSelection() != null) {
			ProductionOverviewV productionOverviewV = getSelectedProductionOverviewV();

			if (productionOverviewV.isPostShipment()) {
				transportable = managerRepository.getPostShipmentManager()
						.loadById(productionOverviewV.getPostshipmentId());
			} else {
				transportable = managerRepository.getOrderManager().findByOrderNr(productionOverviewV.getOrderNr());
			}
		}
		return transportable;
	}

	private ProductionOverviewV getSelectedProductionOverviewV() {
		int index = table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		ProductionOverviewV productionOverviewV = (ProductionOverviewV) objectSelectionList.getElementAt(index);
		return productionOverviewV;
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
			Transportable transportable = getSelectedTransportable();
			// if (objectSelectionList.getSelection() != null) {
			// int index =
			// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
			// transportable = (Transportable)
			// objectSelectionList.getElementAt(index);
			// }
			if (transportable != null) {
				boolean apply = false;
				if (actionEvent.getActionCommand().equalsIgnoreCase(applyString)) {
					apply = true;
				}
				if (handler != null) {

					handler.setApplied(handler.getApplyObject(transportable, window), apply, window);
					handler.clearApplyObject();
				}

				// if (transportable instanceof Order) {
				// managerRepository.getOrderManager().refreshObject((Order)
				// transportable);
				// } else {
				// managerRepository.getPostShipmentManager().refreshObject((PostShipment)
				// transportable);
				// }
				// initTransportable(transportable, window);
				doRefresh(window);
				Util.setDefaultCursor(window.getComponent());
			}
		}

	}

	private class MenuItemUpdate implements ActionListener {
		private WindowInterface window;

		public MenuItemUpdate(WindowInterface window) {
			this.window = window;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window);
			ProductionOverviewV productionOverviewV = getSelectedProductionOverviewV();
			Map<String, String> statusMap = Util.createStatusMap(productionOverviewV.getStatus());
			Set<String> checkers = statusCheckers.keySet();
			OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
			boolean needToSave = false;
			Order order = null;
			for (String checkerName : checkers) {
				StatusCheckerInterface<Transportable> checker = statusCheckers.get(checkerName);
				String status = statusMap.get(checker.getArticleName());

				if (status == null) {
					needToSave = true;
					if (!productionOverviewV.isPostShipment()) {
						order = orderManager.findByOrderNr(productionOverviewV.getOrderNr());
						orderManager.lazyLoadOrder(order,
								new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES,
										LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.COMMENTS,
										LazyLoadOrderEnum.PROCENT_DONE });
						status = checker.getArticleStatus(order);
						statusMap.put(checker.getArticleName(), status);
						order.setStatus(Util.statusMapToString(statusMap));
					}

				}

			}
			if (needToSave && order != null) {
				orderManager.saveObject(order);
			}
			doRefresh(window);
			Util.setDefaultCursor(window);
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
			Transportable transportable = getSelectedTransportable();
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

			Transportable transportable = getSelectedTransportable();
			if (transportable != null) {
				Order order = transportable.getOrder();
				String newProductionWeek = Util.showInputDialogWithdefaultValue(window, "Produksjonsuke",
						"Produksjonsuke",
						order.getProductionWeek() == null ? "" : String.valueOf(order.getProductionWeek()));
				if (newProductionWeek != null) {

					order.setProductionWeek(
							StringUtils.isNotBlank(newProductionWeek) ? Integer.valueOf(newProductionWeek) : null);
					managerRepository.getOrderManager().saveOrder(order);
					ProductionOverviewV productionOverviewV = getSelectedProductionOverviewV();
					productionOverviewV.setProductionWeek(order.getProductionWeek());
					if (!Hibernate.isInitialized(order.getOrderLines())) {
						managerRepository.getOrderManager().lazyLoadOrder(order,
								new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });

					}
					vismaFileCreator.createVismaFileForProductionWeek(order);
					// doRefresh(window);
				}
			}
		}

	}

	// public ProductionOverviewV getSelectedObject() {
	// ProductionOverviewV transportable = null;
	// if (objectSelectionList.getSelection() != null) {
	// int index =
	// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
	// transportable = (ProductionOverviewV)
	// objectSelectionList.getElementAt(index);
	// }
	// return transportable;
	// }

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
			Transportable transportable = getSelectedTransportable();
			// if (objectSelectionList.getSelection() != null) {
			// int index =
			// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
			// transportable = (Transportable)
			// objectSelectionList.getElementAt(index);
			// }
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
			Transportable transportable = getSelectedTransportable();
			// if (objectSelectionList.getSelection() != null) {
			// int index =
			// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
			// transportable = (Transportable)
			// objectSelectionList.getElementAt(index);
			// }
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
				Transportable transportable = getSelectedTransportable();
				// if (objectSelectionList.getSelection() != null) {
				// int index =
				// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
				// transportable = (Transportable)
				// objectSelectionList.getElementAt(index);
				// }

				if (transportable != null && transportable instanceof Order) {
					DeviationViewHandler2 deviationViewHandler = deviationViewHandlerFactory
							.create((Order) transportable, true, false, true, null, true, true);
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
				Transportable transportable = getSelectedTransportable();

				// if (objectSelectionList.getSelection() != null) {
				// int index =
				// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
				// transportable = (Transportable)
				// objectSelectionList.getElementAt(index);
				// }

				if (transportable != null && transportable instanceof Order) {
					setProcentForOrder((Order) transportable, window);
					// doRefresh(window);
				}

			}
		}

	}

	class MenuItemListenerSetReceivedTrossDrawing implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerSetReceivedTrossDrawing(WindowInterface aWindow) {
			window = aWindow;

		}

		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemSetReceivedTrossDrawing.getText())) {
				Transportable transportable = getSelectedTransportable();

				// if (objectSelectionList.getSelection() != null) {
				// int index =
				// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
				// transportable = (Transportable)
				// objectSelectionList.getElementAt(index);
				// }

				if (transportable != null && transportable instanceof Order) {
					Order order = (Order) transportable;
					String jaNei = (String) Util.showOptionsDialogCombo(window, Lists.newArrayList("Ja", "Nei"),
							"Mottatt", true, "Ja");
					order.setReceivedTrossDrawing(jaNei);
					try {
						managerRepository.getOrderManager().saveOrder(order);
						ProductionOverviewV productionOverviewV = getSelectedProductionOverviewV();
						productionOverviewV.setReceivedTrossDrawing(order.getReceivedTrossDrawing());
						// doRefresh(window);
					} catch (ProTransException e) {
						Util.showErrorDialog(window, "Feil", e.getMessage());
						e.printStackTrace();
					}
					// setProcentForOrder((Order) transportable, window);
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
				Transportable transportable = getSelectedTransportable();
				// if (objectSelectionList.getSelection() != null) {
				// int index =
				// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
				// transportable = (Transportable)
				// objectSelectionList.getElementAt(index);
				// }

				if (transportable != null && transportable instanceof Order) {
					setEstimatedTimewallForOrder((Order) transportable, window);
					// doRefresh(window);
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
				Transportable transportable = getSelectedTransportable();
				// if (objectSelectionList.getSelection() != null) {
				// int index =
				// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
				// transportable = (Transportable)
				// objectSelectionList.getElementAt(index);
				// }

				if (transportable != null && transportable instanceof Order) {
					setEstimatedTimeGavlForOrder((Order) transportable, window);
					// doRefresh(window);
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
				Transportable transportable = getSelectedTransportable();
				// if (objectSelectionList.getSelection() != null) {
				// int index =
				// table.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
				// transportable = (Transportable)
				// objectSelectionList.getElementAt(index);
				// }

				if (transportable != null && transportable instanceof Order) {
					setEstimatedTimePackForOrder((Order) transportable, window);
					// doRefresh(window);

				}

			}
		}

	}

	private void setProcentForOrder(final Order order, final WindowInterface aWindow) {
		ProcentDoneModel procentDoneModel = new ProcentDoneModel(
				new ProcentDone(null, null, null, null, order, null, null, null));
		ProcentDoneViewHandler procentDoneViewHandler = new ProcentDoneViewHandler(userType,
				managerRepository.getProcentDoneManager());
		EditProcentDoneView procentDoneView = new EditProcentDoneView(false, procentDoneModel, procentDoneViewHandler);
		Util.showEditViewable(procentDoneView, aWindow);

		if (!procentDoneView.isCanceled()) {
			handleProcentDone(order, aWindow, procentDoneModel);

		}
	}

	private void setEstimatedTimewallForOrder(final Order order, final WindowInterface aWindow) {
		BigDecimal estimatedProductionHours = order.getEstimatedTimeWall();
		String estimertTidsforbrukString = Util.showInputDialogWithdefaultValue(null, "Sett estimert tidsforbruk",
				"Tidsforbruk:", estimatedProductionHours == null ? "" : String.valueOf(estimatedProductionHours));

		BigDecimal estimertTidsforbruk = StringUtils.isBlank(estimertTidsforbrukString) ? null
				: new BigDecimal(StringUtils.replace(estimertTidsforbrukString, ",", "."));
		order.setEstimatedTimeWall(estimertTidsforbruk);

		try {
			managerRepository.getOrderManager().saveOrder(order);
			ProductionOverviewV productionOverviewV = getSelectedProductionOverviewV();
			productionOverviewV.setEstimatedTimeWall(order.getEstimatedTimeWall());
		} catch (ProTransException e) {
			Util.showErrorDialog(aWindow, "Feil", e.getMessage());
			e.printStackTrace();
		}
	}

	private void setEstimatedTimeGavlForOrder(final Order order, final WindowInterface aWindow) {
		BigDecimal estimatedProductionHours = order.getEstimatedTimeGavl();
		String estimertTidsforbrukString = Util.showInputDialogWithdefaultValue(null, "Sett estimert tidsforbruk",
				"Tidsforbruk:", estimatedProductionHours == null ? "" : String.valueOf(estimatedProductionHours));

		BigDecimal estimertTidsforbruk = StringUtils.isBlank(estimertTidsforbrukString) ? null
				: new BigDecimal(StringUtils.replace(estimertTidsforbrukString, ",", "."));
		order.setEstimatedTimeGavl(estimertTidsforbruk);

		try {
			managerRepository.getOrderManager().saveOrder(order);
			ProductionOverviewV productionOverviewV = getSelectedProductionOverviewV();
			productionOverviewV.setEstimatedTimeGavl(order.getEstimatedTimeGavl());
		} catch (ProTransException e) {
			Util.showErrorDialog(aWindow, "Feil", e.getMessage());
			e.printStackTrace();
		}
	}

	private void setEstimatedTimePackForOrder(final Order order, final WindowInterface aWindow) {
		BigDecimal estimatedProductionHours = order.getEstimatedTimePack();
		String estimertTidsforbrukString = Util.showInputDialogWithdefaultValue(null, "Sett estimert tidsforbruk",
				"Tidsforbruk:", estimatedProductionHours == null ? "" : String.valueOf(estimatedProductionHours));

		BigDecimal estimertTidsforbruk = StringUtils.isBlank(estimertTidsforbrukString) ? null
				: new BigDecimal(StringUtils.replace(estimertTidsforbrukString, ",", "."));
		order.setEstimatedTimePack(estimertTidsforbruk);

		try {
			managerRepository.getOrderManager().saveOrder(order);
			ProductionOverviewV productionOverviewV = getSelectedProductionOverviewV();
			productionOverviewV.setEstimatedTimePack(order.getEstimatedTimePack());
		} catch (ProTransException e) {
			Util.showErrorDialog(aWindow, "Feil", e.getMessage());
			e.printStackTrace();
		}
	}

	private void handleProcentDone(final Order order, final WindowInterface aWindow,
			final ProcentDoneModel procentDoneModel) {
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
			managerRepository.getOrderManager().saveOrder(order);
		} catch (ProTransException e) {
			Util.showErrorDialog(aWindow, "Feil", e.getMessage());
			e.printStackTrace();
		}

	}

	private CheckObject checkProcentDone(ProcentDone newProcentDone, Order order) {
		ProcentDone procentDone = order.getProcentDone(newProcentDone);
		CheckObject checkObject = null;
		if (procentDone != null) {
			checkObject = new CheckObject("Det er allerde registert en prosent " + procentDone.getProcent()
					+ " for denne uken. Vil du overskrive denne?", true, procentDone);
		} else if (order.getLastProcentDone() != null
				&& order.getLastProcentDone().getProcent() > newProcentDone.getProcent()) {
			checkObject = new CheckObject("Forrige prosent " + order.getLastProcentDone()
					+ " er høyere enn denne. Vil du likevel lagre denne?", true, null);
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
	// @Override
	public void doRefresh(WindowInterface window) {
		initObjects();
		// initOrders(objectList, window);
		if (currentProductionoverviewFilter != null) {
			doFilter(currentProductionoverviewFilter);
		}
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
		Transportable transportable = orderViewHandler.searchOrder(window, true, true);
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

		// ProductAreaGroup group = (ProductAreaGroup)
		// productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
		PrefsUtil.setInvisibleColumns(ProductAreaGroup.UNKNOWN.getProductAreaGroupName(), table.getName(), table);
		// group = group.getProductAreaGroupName().equalsIgnoreCase("Alle") ?
		// ProductAreaGroup.UNKNOWN : group;

		List<Filter> filterList = new ArrayList<Filter>();

		if (!checkBoxFilter.isSelected()) {
			PatternFilter filterDone = new PatternFilter("Nei", Pattern.CASE_INSENSITIVE,
					ProductionColumn.KOMPLETT.ordinal());
			filterList.add(filterDone);
		}
		// if (group != ProductAreaGroup.UNKNOWN) {
		// if (!group.getProductAreaGroupName().equalsIgnoreCase("Takstol")) {
		// filterList
		// .add(new PatternFilter(group.getProductAreaGroupName(),
		// Pattern.CASE_INSENSITIVE, ProductionColumn.PRODUKTOMRÅDE.ordinal()));
		// } else {
		// filterList.add(new PatternFilter(".*e.*", Pattern.CASE_INSENSITIVE,
		// ProductionColumn.TAKSTOL.ordinal()));
		// }
		//
		// }
		if (filterList.size() != 0) {
			Filter[] filterArray = new Filter[filterList.size()];
			table.setFilters(new FilterPipeline(filterList.toArray(filterArray)));
		} else {
			table.setFilters(null);
		}
		table.repaint();

	}

	public String getProductAreaGroupName() {
		// return ((ProductAreaGroup)
		// productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP)).getProductAreaGroupName();
		return ProductAreaGroup.UNKNOWN.getProductAreaGroupName();
	}

	// @Override
	public void beforeClose() {
		PrefsUtil.putUserInvisibleColumns(table, ProductAreaGroup.UNKNOWN);
	}

	public String getSelectedOrderNr() {
		Transportable transportable = getSelectedTransportable();
		return transportable != null ? transportable.getOrderNr() : null;
	}

	public Produceable getSelectedProduceable() {
		Transportable transportable = getSelectedTransportable();

		if (transportable != null) {
			ProductionViewHandler handler = (ProductionViewHandler) productionPackageHandlers.get("TakstolProduksjon");
			if (handler != null) {
				return handler.getApplyObject(transportable, null);
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
				String fileName = "Produksjonsoversikt_" + Util.getCurrentDateAsDateTimeString() + ".xlsx";
				String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");

				// JXTable tableReport = new JXTable(new
				// ProductionOverviewTableModel(objectList));

				// ExcelUtil.showDataInExcel(excelDirectory, fileName, null,
				// "Produksjonsoversikt", table, null, null, 16, false);
				ExcelUtil.showTableDataInExcel(excelDirectory, fileName, null, "Produksjonsoversikt", table, null, null,
						16, false);
			} catch (ProTransException e) {
				e.printStackTrace();
				Util.showErrorDialog(window, "Feil", e.getMessage());
			}
			Util.setDefaultCursor(window.getComponent());

		}
	}

	public void doSave(WindowInterface window) {
		// TODO Auto-generated method stub

	}

	public boolean doDelete(WindowInterface window) {
		// TODO Auto-generated method stub
		return false;
	}

	public void doNew(WindowInterface window) {
		// TODO Auto-generated method stub

	}

	public JButton getCancelButton(WindowInterface window) {
		buttonCancel = new CancelButton(window, this, true);
		buttonCancel.setName("ButtonCancel");
		return buttonCancel;
	}

	public boolean canClose(String actionString, WindowInterface window) {
		return true;
	}

	// public class ProductionOverviewVComparator implements
	// Comparator<ProductionOverviewV> {
	//
	// public final int compare(final ProductionOverviewV productionOverviewV1,
	// final ProductionOverviewV productionOverviewV2) {
	// if (productionOverviewV1 != null && productionOverviewV2 != null) {
	// return new
	// CompareToBuilder().append(productionOverviewV1.getTransportYear(),
	// productionOverviewV2.getTransportYear())
	// .append(productionOverviewV1.getTransportWeek(),
	// productionOverviewV2.getTransportWeek())
	// .append(productionOverviewV1.getLoadingDate(),
	// productionOverviewV2.getLoadingDate())
	// .append(productionOverviewV1.getTransportName(),
	// productionOverviewV2.getTransportName()).toComparison();
	//
	// } else if (productionOverviewV1 != null) {
	// return -1;
	// } else if (productionOverviewV2 != null) {
	// return 1;
	// }
	// return 0;
	// }
	//
	// }

	public class TextPaneRendererProductionoverviewV extends TextPaneRenderer<ProductionOverviewV> {

		private static final long serialVersionUID = 1L;

		private String getWarning(final ProductionOverviewV transportable) {

			return StringUtils.isNotBlank(transportable.getSpecialConcern()) ? transportable.getSpecialConcern() : "";
		}

		private String getComment(final ProductionOverviewV transportable) {
			return StringUtils.isNotBlank(transportable.getComment()) ? getSplitComment(transportable.getComment())
					: "";
		}

		private String getSplitComment(final String comment) {
			return Util.splitLongStringIntoLinesWithBr(comment, MAX_LENGHT);

		}

		@Override
		protected List<Icon> getIcons(ProductionOverviewV transportable) {
			List<Icon> icons = new ArrayList<Icon>();
			boolean added = StringUtils.isNotBlank(transportable.getComment())
					? icons.add(IconEnum.ICON_COMMENT.getIcon())
					: false;
			added = StringUtils.isNotBlank(transportable.getSpecialConcern())
					? icons.add(IconEnum.ICON_WARNING.getIcon())
					: false;
			added = transportable.isPostShipment() ? icons.add(IconEnum.ICON_POST_SHIPMENT.getIcon()) : false;
			return icons;
		}

		@Override
		protected String getPaneText(ProductionOverviewV transportable) {
			return transportable.getOrderNr();
		}

		@Override
		protected StringBuffer getPaneToolTipText(ProductionOverviewV transportable) {
			StringBuffer toolTip = new StringBuffer();

			toolTip.append(getComment(transportable));

			toolTip.append(getWarning(transportable));
			return toolTip;
		}

		@Override
		protected void setCenteredAlignment() {
			// SimpleAttributeSet set = new SimpleAttributeSet();
			// StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
			// setParagraphAttributes(set, false);
			// repaint();
		}

	}

	public JButton getButtonFilter(WindowInterface window2) {
		JButton button = new JButton(new FilterAction(window2, this));
		button.setSize(20, 12);
		return button;
	}

	private class FilterAction extends AbstractAction {
		private WindowInterface window;
		private ProductionoverviewFilterListener productionoverviewFilterListener;

		public FilterAction(WindowInterface aWindow, ProductionoverviewFilterListener listener) {
			super("Filtrer");
			window = aWindow;
			this.productionoverviewFilterListener = listener;
		}

		public void actionPerformed(ActionEvent arg0) {
			FilterProductionoverviewView filterProductionoverviewView = new FilterProductionoverviewView();

			JDialog dialog = Util.getDialog(window, "Filter", true);
			WindowInterface window = new JDialogAdapter(dialog);

			window.add(filterProductionoverviewView.buildPanel(window, productionoverviewFilterListener));
			window.pack();
			window.setSize(new Dimension(450, 950));

			Util.locateOnScreenCenter(window);
			window.setVisible(true);

		}
	}

	public void doFilter(ProductionoverviewFilter productionoverviewFilter) {
		objectList.clear();
		currentProductionoverviewFilter = productionoverviewFilter;

		List<ProductionOverviewV> filtered = Lists
				.newArrayList(Iterables.filter(productionoverviewList, filtrer(productionoverviewFilter)));
		Collections.sort(filtered, sorter(productionoverviewFilter));
		objectList.addAll(filtered);

		Summer summer = summerProduksjon(filtered);

		labelAntallGarasjer.setText(String.valueOf(summer.antallGarasjer));
		labelSumTidVegg.setText(String.valueOf(summer.sumTidVegg));
		labelSumTidGavl.setText(String.valueOf(summer.sumTidGavl));
		labelSumTidPakk.setText(String.valueOf(summer.sumTidPakk));

	}

	private Comparator<ProductionOverviewV> sorter(final ProductionoverviewFilter productionoverviewFilter) {
		return new Comparator<ProductionOverviewV>() {

			public int compare(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2) {
				return productionoverviewFilter.sort(productionOverviewV1, productionOverviewV2, statusCheckers);
			}
		};
	}

	private Predicate<ProductionOverviewV> filtrer(final ProductionoverviewFilter productionoverviewFilter) {
		return new Predicate<ProductionOverviewV>() {

			public boolean apply(ProductionOverviewV productionOverviewV) {

				return productionoverviewFilter.filter(productionOverviewV, statusCheckers);
			}
		};
	}

	public JLabel getLabelAntallGarasjer() {
		if (labelAntallGarasjer == null) {
			labelAntallGarasjer = new JLabel("test");
		}
		return labelAntallGarasjer;
	}

	public JLabel getLabelSumTidVegg() {
		if (labelSumTidVegg == null) {
			labelSumTidVegg = new JLabel("test");
		}
		return labelSumTidVegg;
	}

	public static class Summer {
		int antallGarasjer = 0;
		BigDecimal sumTidVegg = BigDecimal.ZERO;
		BigDecimal sumTidGavl = BigDecimal.ZERO;
		BigDecimal sumTidPakk = BigDecimal.ZERO;
	}

	public JLabel getLabelSumTidGavl() {
		if (labelSumTidGavl == null) {
			labelSumTidGavl = new JLabel("test");
		}
		return labelSumTidGavl;
	}

	public JLabel getLabelSumTidPakk() {
		if (labelSumTidPakk == null) {
			labelSumTidPakk = new JLabel("test");
		}
		return labelSumTidPakk;
	}
}
