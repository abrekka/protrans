package no.ugland.utransprod.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.action.AdminAccidentAction;
import no.ugland.utransprod.gui.action.AdminDeviationAction;
import no.ugland.utransprod.gui.action.ArticleTypeAction;
import no.ugland.utransprod.gui.action.AttributeAction;
import no.ugland.utransprod.gui.action.ConfirmReportAction;
import no.ugland.utransprod.gui.action.ConstructionTypeAction;
import no.ugland.utransprod.gui.action.ConstructionTypeMasterAction;
import no.ugland.utransprod.gui.action.CostTypeAction;
import no.ugland.utransprod.gui.action.CostUnitAction;
import no.ugland.utransprod.gui.action.DeviationStatusAction;
import no.ugland.utransprod.gui.action.FunctionCategoryAction;
import no.ugland.utransprod.gui.action.ImportAreasAction;
import no.ugland.utransprod.gui.action.ImportCountiesAction;
import no.ugland.utransprod.gui.action.ImportPostalCodesAction;
import no.ugland.utransprod.gui.action.ImportProductionBudgetAction;
import no.ugland.utransprod.gui.action.ImportSaleBudgetAction;
import no.ugland.utransprod.gui.action.ImportSalesmanBudgetAction;
import no.ugland.utransprod.gui.action.ImportSnowloadAction;
import no.ugland.utransprod.gui.action.ImportZoneAdditionAssemblyAction;
import no.ugland.utransprod.gui.action.JobFunctionAction;
import no.ugland.utransprod.gui.action.RegisterAccidentAction;
import no.ugland.utransprod.gui.action.RegisterDeviationAction;
import no.ugland.utransprod.gui.action.SalesGoalReportAction;
import no.ugland.utransprod.gui.action.SupplierAction;
import no.ugland.utransprod.gui.action.SupplierTypeAction;
import no.ugland.utransprod.gui.action.TakstolProductionUnitReportAction;
import no.ugland.utransprod.gui.action.UpdateTransportPricesAction;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandler;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandlerDeviation;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandlerOwnProduction;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandlerSaleStatus;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandlerSales;
import no.ugland.utransprod.gui.handlers.KeyReportViewHandler;
import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler;
import no.ugland.utransprod.gui.handlers.TransportOverviewReportHandler;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.gui.model.WindowEnum;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportEnum;

import org.jdesktop.jdic.desktop.Desktop;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.internal.Lists;

/**
 * Klasse som bygger menulinje for applikasjon
 * 
 * @author abr99
 */
@Singleton
public class MenuBarBuilderImpl implements MenuBarBuilderInterface {
	Login login;

	private JMenuBar menuBar;

	ProTransMain proTransMain;

	private HelpSet mainHS = null;

	private static final String HELPSET_NAME = "helpset/ProTransHelp.hs";

	private HelpBroker mainHB;

	private JMenu menuWindow;

	private RegisterAccidentAction registerAccidentAction;

	private AdminDeviationAction adminDeviationAction;
	private RegisterDeviationAction registerDeviationAction;

	private ConstructionTypeAction constructionTypeAction;

	private ConstructionTypeMasterAction constructionTypeMasterAction;

	private ArticleTypeAction articleTypeAction;

	private AttributeAction attributeAction;

	private SupplierAction supplierAction;

	private SupplierTypeAction supplierTypeAction;

	private CostTypeAction costTypeAction;

	private CostUnitAction costUnitAction;

	private JobFunctionAction jobFunctionAction;

	private FunctionCategoryAction functionCategoryAction;

	private DeviationStatusAction deviationStatusAction;

	private AdminAccidentAction adminAccidentAction;

	private ConfirmReportAction confirmReportAction;

	private AdminMenu adminMenu;

	private GarageMenu garageMenu;

	private PackMenu packMenu;

	private ProductionMenu productionMenu;

	private TakstolProductionUnitReportAction takstolProductionUnitReportAction;

	// private OrdrereserveTakstolReportAction ordrereserveTakstolReportAction;

	private ImportPostalCodesAction importPostalCodesAction;
	private ImportZoneAdditionAssemblyAction importZoneadditionAssemblyAction;

	private ImportCountiesAction importCountiesAction;

	private ImportAreasAction importAreasAction;

	private UpdateTransportPricesAction updateTransportPricesAction;

	private ImportSnowloadAction importSnowloadAction;

	private ImportProductionBudgetAction importProductionBudgetAction;

	private ImportSaleBudgetAction importSaleBudgetAction;
	private SalesGoalReportAction salesGoalReportAction;
	private ImportSalesmanBudgetAction importSalesmanBudgetAction;

	// private TrossDrawReportAction trossDrawReportAction;

	public MenuBarBuilderImpl(Login aLogin) {
		login = aLogin;
	}

	@Inject
	public MenuBarBuilderImpl(final RegisterAccidentAction aRegisterAccidentAction,
			final AdminDeviationAction aAdminDeviationAction, final RegisterDeviationAction aRegisterDeviationAction,

			ConstructionTypeAction aConstructionTypeAction, ConstructionTypeMasterAction aConstructionTypeMasterAction,
			ArticleTypeAction aArticleTypeAction, AttributeAction aAttributeAction, SupplierAction aSupplierAction,
			SupplierTypeAction aSupplierTypeAction, CostTypeAction aCostTypeAction, CostUnitAction aCostUnitAction,
			JobFunctionAction aJobFunctionAction, FunctionCategoryAction aFunctionCategoryAction,
			DeviationStatusAction aDeviationStatusAction, AdminAccidentAction aAdminAccidentAction,

			ConfirmReportAction aConfirmReportAction, AdminMenu aAdminMenu, GarageMenu aGarageMenu, PackMenu aPackMenu,
			Login aLogin, ProductionMenu aProductionMenu,
			TakstolProductionUnitReportAction aTakstolProductionUnitReportAction,
			// OrdrereserveTakstolReportAction aOrdrereserveTakstolReportAction,
			ImportPostalCodesAction aImportPostalCodesAction, ImportCountiesAction aImportCountiesAction,
			ImportAreasAction aImportAreasAction, UpdateTransportPricesAction aUpdateTransportPricesAction,
			ImportSnowloadAction aImportSnowloadAction, ImportProductionBudgetAction aImportBudgetAction,
			ImportSaleBudgetAction aImportSaleBudgetAction, SalesGoalReportAction aSalesGoalReportAction,
			ImportSalesmanBudgetAction aImportSalesmanBudgetAction,
			ImportZoneAdditionAssemblyAction aImportZoneAdditionAssemblyAction)
	// TrossDrawReportAction aTrossDrawReportAction)
	{
		// trossDrawReportAction = aTrossDrawReportAction;
		importSalesmanBudgetAction = aImportSalesmanBudgetAction;
		salesGoalReportAction = aSalesGoalReportAction;
		importSaleBudgetAction = aImportSaleBudgetAction;
		importProductionBudgetAction = aImportBudgetAction;
		importSnowloadAction = aImportSnowloadAction;
		updateTransportPricesAction = aUpdateTransportPricesAction;
		importAreasAction = aImportAreasAction;
		importCountiesAction = aImportCountiesAction;
		importPostalCodesAction = aImportPostalCodesAction;
		importZoneadditionAssemblyAction = aImportZoneAdditionAssemblyAction;
		// ordrereserveTakstolReportAction = aOrdrereserveTakstolReportAction;
		takstolProductionUnitReportAction = aTakstolProductionUnitReportAction;
		productionMenu = aProductionMenu;
		login = aLogin;
		packMenu = aPackMenu;
		garageMenu = aGarageMenu;
		adminMenu = aAdminMenu;
		confirmReportAction = aConfirmReportAction;

		adminAccidentAction = aAdminAccidentAction;
		deviationStatusAction = aDeviationStatusAction;

		functionCategoryAction = aFunctionCategoryAction;
		jobFunctionAction = aJobFunctionAction;
		costUnitAction = aCostUnitAction;
		costTypeAction = aCostTypeAction;
		supplierTypeAction = aSupplierTypeAction;
		supplierAction = aSupplierAction;
		attributeAction = aAttributeAction;
		articleTypeAction = aArticleTypeAction;
		constructionTypeMasterAction = aConstructionTypeMasterAction;
		constructionTypeAction = aConstructionTypeAction;

		registerDeviationAction = aRegisterDeviationAction;
		adminDeviationAction = aAdminDeviationAction;
		registerAccidentAction = aRegisterAccidentAction;
	}

	/**
	 * Bygger systemmeny
	 * 
	 * @return meny
	 */
	private JMenu buildSystemMenu() {
		JMenu menuSystem = addMenu("System", KeyEvent.VK_S);
		addMenuItem(menuSystem, new SystemCloseAction(), KeyEvent.VK_A,
				KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK), IconEnum.ICON_EXIT, null, null, false);
		return menuSystem;

	}

	/**
	 * Lager garasjemeny
	 * 
	 * @return meny
	 */
	private JMenu buildGarageMenu() {
		return garageMenu.buildMenu();
	}

	/**
	 * Lager grunndatameny
	 * 
	 * @return meny
	 */
	private JMenu buildBasisMenu() {
		JMenu menuBasis = addMenu("Grunndata", KeyEvent.VK_D);
		addMenuItem(menuBasis, constructionTypeMasterAction, KeyEvent.VK_M, null, null, null, "Master garasjetype",
				false);
		addMenuItem(menuBasis, constructionTypeAction, KeyEvent.VK_K, null, null, null, "Konstruksjonstyper", false);
		addMenuItem(menuBasis, articleTypeAction, KeyEvent.VK_A, null, null, null, "Artikkel", false);
		addMenuItem(menuBasis, costTypeAction, KeyEvent.VK_K, null, null, null, "Kostnadstyper", false);
		addMenuItem(menuBasis, costUnitAction, KeyEvent.VK_E, null, null, null, "Kostnadsenheter", false);
		addMenuItem(menuBasis, jobFunctionAction, KeyEvent.VK_F, null, null, null, "Funksjon", false);
		addMenuItem(menuBasis, functionCategoryAction, KeyEvent.VK_K, null, null, null, "Kategori", false);
		addMenuItem(menuBasis, deviationStatusAction, KeyEvent.VK_G, null, null, null, "Avikstatus", false);
		addMenuItem(menuBasis, attributeAction, KeyEvent.VK_K, null, null, null, "Attributter", false);
		addMenuItem(menuBasis, supplierAction, KeyEvent.VK_L, null, null, null, "Leverandører", false);
		addMenuItem(menuBasis, supplierTypeAction, KeyEvent.VK_T, null, null, null, "Leverandørtyper", false);
		addMenuItem(menuBasis, importCountiesAction, KeyEvent.VK_F, null, null, null, "Importer fylker", false);
		addMenuItem(menuBasis, importAreasAction, KeyEvent.VK_K, null, null, null, "Importer kommuner", false);
		addMenuItem(menuBasis, importPostalCodesAction, KeyEvent.VK_I, null, null, null, "Importer gyldige postnummer",
				false);
		addMenuItem(menuBasis, updateTransportPricesAction, KeyEvent.VK_O, null, null, null, "Oppdater transportpriser",
				false);
		addMenuItem(menuBasis, importProductionBudgetAction, KeyEvent.VK_B, null, null, null, "Importer budsjettall",
				false);
		addMenuItem(menuBasis, importSnowloadAction, KeyEvent.VK_S, null, null, null, "Importer snølast", false);
		addMenuItem(menuBasis, importSaleBudgetAction, KeyEvent.VK_B, null, null, null, "Importer budsjettall", false);
		addMenuItem(menuBasis, importSalesmanBudgetAction, KeyEvent.VK_B, null, null, null,
				"Importer budsjettall selger", false);
		addMenuItem(menuBasis, importZoneadditionAssemblyAction, KeyEvent.VK_S, null, null, null,
				"Importer sonetillegg montering", false);
		return menuBasis;
	}

	/**
	 * Lager prosuksjonsmeny
	 * 
	 * @return meny
	 */
	private JMenu buildProductionMenu() {
		return productionMenu != null ? productionMenu.buildMenu() : new JMenu();
	}

	/**
	 * Lager pakkemeny
	 * 
	 * @return meny
	 */
	private JMenu buildPackMenu() {
		return packMenu != null ? packMenu.buildMenu() : new JMenu();
	}

	/**
	 * Lager rapportmeny.
	 * 
	 * @return meny
	 */
	private JMenu buildReportMenu() {
		JMenu menuReport = addMenu("Rapport", KeyEvent.VK_R);

		JMenu menuSale = addMenu("Salg", KeyEvent.VK_S);
		addMenuItem(menuSale, confirmReportAction, KeyEvent.VK_A, null, null, null, "Avropsrapport", false);
		addMenuItem(menuSale, new SalesReportAction(), KeyEvent.VK_G, null, null, null, "Salgsrapport", false);
		addMenuItem(menuSale, new SalesStatusReportAction(), KeyEvent.VK_T, null, null, null, "Salgsstatus", false);
		addMenuItem(menuSale, salesGoalReportAction, KeyEvent.VK_T, null, null, null, "Salgsmål", false);

		menuReport.add(menuSale);

		JMenu menuProject = addMenu("Drift og prosjektering", KeyEvent.VK_D);
		addMenuItem(menuProject, new PacklistNotReadyReportAction(), KeyEvent.VK_K, null, null, null,
				"Pakkliste ikke klar", false);
		addMenuItem(menuProject, new DrawReportAction(), KeyEvent.VK_T, null, null, null, "Tegningsrapport", false);
		// addMenuItem(menuProject, trossDrawReportAction, KeyEvent.VK_T, null,
		// null, null, "Takstoltegning", false);
		menuReport.add(menuProject);

		JMenu menuProduction = addMenu("Produksjon", KeyEvent.VK_P);
		addMenuItem(menuProduction, new OwnProductionReportAction(), KeyEvent.VK_E, null, null, null, "Egenproduksjon",
				false);
		addMenuItem(menuProduction, new ProductivityReportAction(), KeyEvent.VK_P, null, null, null, "Produktivitet",
				false);
		// addMenuItem(menuProduction, new TakstolOwnProductionReportAction(),
		// KeyEvent.VK_I, null, null, null, "Internordre takstol", false);
		addMenuItem(menuProduction, takstolProductionUnitReportAction, KeyEvent.VK_J, null, null, null, "Jiggrapport",
				false);
		// addMenuItem(menuProduction, ordrereserveTakstolReportAction,
		// KeyEvent.VK_O, null, null, null, "Ordrereserve takstol", false);
		menuReport.add(menuProduction);

		JMenu menuTransport = addMenu("Transport", KeyEvent.VK_T);
		addMenuItem(menuTransport, new PostShipmentSentReportAction(), KeyEvent.VK_R, null, null, null,
				"Ettersendinger sendt", false);
		addMenuItem(menuTransport, new TransportListReportAction(), KeyEvent.VK_A, null, null, null,
				"Transportert i periode", false);
		addMenuItem(menuTransport, new TransportOverviewReportAction(), KeyEvent.VK_O, null, null, null,
				"Transportoversikt", false);
		addMenuItem(menuTransport, new TransportReportAction(), KeyEvent.VK_T, null, null, null, "Transportstatistikk",
				false);
		menuReport.add(menuTransport);

		JMenu menuAdm = addMenu("Adm. og økonomi", KeyEvent.VK_A);
		addMenuItem(menuAdm, new InvoiceReportAction(), KeyEvent.VK_I, null, null, null, "Ikke fakturert", false);
		addMenuItem(menuAdm, new KeyReportAction(), KeyEvent.VK_N, null, null, null, "Nøkkeltall", false);
		addMenuItem(menuAdm, new OrderStatusReportAction(), KeyEvent.VK_S, null, null, null,
				"Status alle ordre ikke sendt", false);
		addMenuItem(menuAdm, new ReadyCountReportAction(), KeyEvent.VK_L, null, null, null, "Telleliste", false);
		menuReport.add(menuAdm);

		JMenu menuDeviation = addMenu("Avvik", KeyEvent.VK_V);

		addMenuItem(menuDeviation, new DeviationReportAction(), KeyEvent.VK_V, null, null, null, "Avvik", false);
		addMenuItem(menuDeviation, new SummaryReportDeviationAction(), KeyEvent.VK_J, null, null, null,
				"Avvik - Skjema gjennomgåelse", false);
		addMenuItem(menuDeviation, new DeviationFunctionReportAction(), KeyEvent.VK_V, null, null, null,
				"Avvik pr aviksfunksjon", false);
		menuReport.add(menuDeviation);

		return menuReport;
	}

	/**
	 * Lager avviksmeny
	 * 
	 * @return meny
	 */
	private JMenu buildDeviationMenu() {
		DeviationManager deviationManager = (DeviationManager) ModelUtil.getBean(DeviationManager.MANAGER_NAME);
		List<Deviation> deviations = deviationManager.findByResponsible(login.getApplicationUser());
		String avvikTekst = "Avvik";
		boolean harAvvik = false;
		if (deviations != null && !deviations.isEmpty()) {
			ArrayList<Deviation> deviationsNotClosed = Lists.newArrayList(Iterables.filter(deviations, ikkeLukket()));
			if (!deviationsNotClosed.isEmpty()) {
				avvikTekst = avvikTekst + "(" + deviationsNotClosed.size() + ")";
				harAvvik = true;
			}

		}
		JMenu menuDeviation = addMenu(avvikTekst, KeyEvent.VK_V);
		if (harAvvik) {
			menuDeviation.setOpaque(true);
			menuDeviation.setForeground(Color.RED);
		}
		addMenuItem(menuDeviation, registerDeviationAction, KeyEvent.VK_R, null, null, null, null, true);
		addMenuItem(menuDeviation, adminDeviationAction, KeyEvent.VK_A, null, null, null, "Avvik", false);
		addMenuItem(menuDeviation, registerAccidentAction, KeyEvent.VK_U, null, null, null, null, true);
		addMenuItem(menuDeviation, adminAccidentAction, KeyEvent.VK_A, null, null, null, "Ulykke", false);

		return menuDeviation;
	}

	private Predicate<Deviation> ikkeLukket() {
		return new Predicate<Deviation>() {

			public boolean apply(Deviation deviation) {
				return deviation.getDateClosed() == null;
			}
		};
	}

	/**
	 * Lager administrasjonsmeny
	 * 
	 * @return meny
	 */
	private JMenu buildAdminMenu() {
		return adminMenu != null ? adminMenu.buildMenu() : new JMenu();

	}

	/**
	 * Lager hjelpemeny
	 * 
	 * @return meny
	 */
	private JMenu buildHelpMenu() {
		JMenu menuHelp = addMenu("Hjelp", KeyEvent.VK_H);
		addMenuItem(menuHelp, new HelpAction(), KeyEvent.VK_J, null, null, null, null, false)
				.addActionListener(new CSH.DisplayHelpFromSource(mainHB));
		addMenuItem(menuHelp, new OpenSupportAction(), KeyEvent.VK_S, null, null, null, null, false);
		addMenuItem(menuHelp, new ShowInfoAction(), KeyEvent.VK_I, null, null, null, null, false);
		return menuHelp;
	}

	/**
	 * Lager vindusmeny
	 * 
	 * @return meny
	 */
	private JMenu buildWindowMenu() {
		menuWindow = addMenu("Vindu", KeyEvent.VK_V);
		addMenuItem(menuWindow, new CascadeWindows(), KeyEvent.VK_K, null, null, null, null, false);
		menuWindow.add(new JSeparator());
		return menuWindow;
	}

	/**
	 * Bygger menylinje
	 * 
	 * @param parentFrame
	 * @param aApplicationUser
	 * @param currentUserType
	 * @return menylinje
	 */
	public final JMenuBar buildMenuBar(final ProTransMain parentFrame, final ApplicationUser aApplicationUser,
			final UserType currentUserType) {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			proTransMain = parentFrame;

			menuBar.add(buildSystemMenu());
			menuBar.add(buildGarageMenu());
			menuBar.add(buildBasisMenu());
			menuBar.add(buildProductionMenu());
			menuBar.add(buildPackMenu());
			menuBar.add(buildReportMenu());
			menuBar.add(buildDeviationMenu());
			menuBar.add(buildAdminMenu());
			menuBar.add(buildHelpMenu());
			menuBar.add(buildWindowMenu());
		}
		return menuBar;
	}

	/**
	 * Legger til meny
	 * 
	 * @param menuText
	 * @param keyEvent
	 * @return meny
	 */
	private JMenu addMenu(final String menuText, final int keyEvent) {
		JMenu menu = new JMenu(menuText);
		menu.setMnemonic(keyEvent);
		return menu;
	}

	/**
	 * Legger til menyvalg på meny
	 * 
	 * @param menu
	 * @param menuItemAction
	 * @param keyEvent
	 * @param accelerator
	 * @param iconEnum
	 * @param actionCommand
	 * @param windowName
	 * @param writeAccess
	 * @return menyvalg
	 */
	private JMenuItem addMenuItem(final JMenu menu, final Action menuItemAction, final int keyEvent,
			final KeyStroke accelerator, final IconEnum iconEnum, final String actionCommand, final String windowName,
			final boolean writeAccess) {
		return addMenuItem(menu, menuItemAction, keyEvent, accelerator, iconEnum, actionCommand, windowName,
				writeAccess, false);
	}

	/**
	 * Legger til menyvalg på meny
	 * 
	 * @param menu
	 * @param menuItemAction
	 * @param keyEvent
	 * @param accelerator
	 * @param iconEnum
	 * @param actionCommand
	 * @param windowName
	 * @param writeAccess
	 * @param adminOnly
	 * @return menuvalg
	 */
	private JMenuItem addMenuItem(final JMenu menu, final Action menuItemAction, final int keyEvent,
			final KeyStroke accelerator, final IconEnum iconEnum, final String actionCommand, final String windowName,
			final boolean writeAccess, final boolean adminOnly) {
		JMenuItem menuItem = null;
		if (menuItemAction != null) {
			menuItem = new JMenuItem(menuItemAction);

			menuItem.setName("MenuItem" + menuItemAction.getValue(Action.NAME));
			if (keyEvent != -1) {
				menuItem.setMnemonic(keyEvent);
			}

			if (accelerator != null) {
				menuItem.setAccelerator(accelerator);
			}

			if (iconEnum != null) {
				menuItem.setIcon(iconEnum.getIcon());
			}

			if (actionCommand != null) {
				menuItem.setActionCommand(actionCommand);
			}

			menu.add(menuItem);
			menuItem.setEnabled(false);

			if (adminOnly) {
				if (Util.convertNumberToBoolean(login.getUserType().getIsAdmin())) {
					menuItem.setEnabled(true);
				}
			} else {

				if (windowName != null) {
					if (writeAccess) {
						if (UserUtil.hasWriteAccess(login.getUserType(), windowName)) {
							menuItem.setEnabled(true);
						}
					} else {
						if (UserUtil.hasAccess(login.getUserType(), windowName)) {
							menuItem.setEnabled(true);
						}
					}
				} else {
					menuItem.setEnabled(true);

				}
			}
		}

		return menuItem;
	}

	public final void openFrame(final Viewer viewer) {
		proTransMain.openFrame(viewer);
	}

	/**
	 * Åpner vindu
	 * 
	 * @param window
	 * @param windowEnum
	 */
	final void openFrame(final WindowInterface window, final WindowEnum windowEnum) {
		proTransMain.openFrame(window, windowEnum);
	}

	/**
	 * Forandrer look and feel
	 * 
	 * @param lookAndFeel
	 */
	final void changeLookAndFeel(final String lookAndFeel) {
		LFEnum lfEnum = null;
		try {
			lfEnum = LFEnum.getLFEnum(lookAndFeel);
		} catch (ProTransException e1) {
			Util.showErrorMsgFrame(proTransMain, "Feil", e1.getMessage());
		}

		if (lfEnum != null) {
			try {
				UIManager.setLookAndFeel(lfEnum.getClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(proTransMain);
		}
	}

	/**
	 * Handling som avslutter system
	 * 
	 * @author abr99
	 */
	private static class SystemCloseAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SystemCloseAction() {
			super("Avslutt");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			System.exit(0);

		}
	}

	/*
	 * private class ImportBudgetAction extends AbstractAction { private static
	 * final long serialVersionUID = 1L;
	 * 
	 * public ImportBudgetAction() { super("Importer budsjettall produksjon..."
	 * ); }
	 * 
	 * public void actionPerformed(final ActionEvent arg0) {
	 * Util.createDialogAndRunInThread(new ProductionBudgetImportHandler(
	 * ProTransMain.PRO_TRANS_MAIN, null));
	 * 
	 * } }
	 */

	/**
	 * Håndterer menyvalg Transport statistikk...
	 * 
	 * @author atle.brekka
	 */
	private class TransportReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public TransportReportAction() {
			super("Transportstatistikk...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ReportConstraintViewHandler reportConstraintViewHandler = new ReportConstraintViewHandler();
			openFrame(new ReportConstraintView(reportConstraintViewHandler));

		}
	}

	/**
	 * Håndterer menyvalg Transportoversikt
	 * 
	 * @author atle.brekka
	 */
	private class TransportOverviewReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public TransportOverviewReportAction() {
			super("Transportoversikt...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			StatusCheckerInterface<Transportable> steinChecker = Util.getSteinChecker();
			TransportOverviewReportHandler transportOverviewReportHandler = new TransportOverviewReportHandler(
					steinChecker);
			openFrame(new TransportOverviewReport(transportOverviewReportHandler));

		}
	}

	/**
	 * Håndterer menyvalg Nøkkeltall...
	 * 
	 * @author atle.brekka
	 */
	private class KeyReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public KeyReportAction() {
			super("Nøkkeltall...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			KeyReportViewHandler keyReportViewHandler = new KeyReportViewHandler();
			openFrame(new KeyReportView(keyReportViewHandler));

		}
	}

	/**
	 * Håndterer menyvalg Produktivitet
	 * 
	 * @author atle.brekka
	 */
	private class ProductivityReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ProductivityReportAction() {
			super("Produktivitet...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
					ExcelReportEnum.PRODUCTIVITY_PACK, new Dimension(320, 150));
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	private class TakstolOwnProductionReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public TakstolOwnProductionReportAction() {
			super("Internordre takstol...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
					ExcelReportEnum.TAKSTOL_OWN_ORDER_REPORT, new Dimension(320, 110));
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	private class SalesReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SalesReportAction() {
			super("Salgsrapport...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandlerSales excelReportViewHandler = new ExcelReportViewHandlerSales(
					ExcelReportEnum.SALES_REPORT);
			openFrame(new ExcelReportView(excelReportViewHandler, true));

		}
	}

	private class SalesStatusReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SalesStatusReportAction() {
			super("Salgsstatus...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandlerSaleStatus excelReportViewHandler = new ExcelReportViewHandlerSaleStatus();
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	private class DrawReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public DrawReportAction() {
			super("Tegningsrapport...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(ExcelReportEnum.DRAWER_REPORT,
					new Dimension(320, 130));
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	/**
	 * Menyvalg Ettersendinger sendt...
	 * 
	 * @author atle.brekka
	 */
	private class PostShipmentSentReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public PostShipmentSentReportAction() {
			super("Ettersendinger sendt...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
					ExcelReportEnum.POST_SHIPMENT_SENT_COUNT, new Dimension(320, 130));
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	/**
	 * Avviskrapport
	 * 
	 * @author atle.brekka
	 */
	private class DeviationReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public DeviationReportAction() {
			super("Avvik...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandlerDeviation excelReportViewHandler = new ExcelReportViewHandlerDeviation("Avvik", true,
					ExcelReportEnum.DEVIATION_SUM);
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	private class DeviationFunctionReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public DeviationFunctionReportAction() {
			super("Avvik pr aviksfunksjon...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandlerDeviation excelReportViewHandler = new ExcelReportViewHandlerDeviation(
					"Avvik pr aviksfunksjon", true, ExcelReportEnum.DEVIATION_JOB_FUNCTION_SUM);
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	/**
	 * Håndterer menyvalg Avvik - Skjema gjennomgåelse
	 * 
	 * @author atle.brekka
	 */
	private class SummaryReportDeviationAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SummaryReportDeviationAction() {
			super("Avvik - Skjema gjennomgåelse...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandlerDeviation excelReportViewHandler = new ExcelReportViewHandlerDeviation(
					"Avvik - Skjema gjennomgåelse", false, ExcelReportEnum.DEVIATION_SUMMARY);
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	/**
	 * Håndterer menyvalg Transportert i periode
	 * 
	 * @author atle.brekka
	 */
	private class TransportListReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public TransportListReportAction() {
			super("Transportert i periode...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(ExcelReportEnum.TRANSPORT_LIST,
					new Dimension(320, 130));
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	/**
	 * Håndterer menyvalg Egenproduksjon...
	 * 
	 * @author atle.brekka
	 */
	private class OwnProductionReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public OwnProductionReportAction() {
			super("Egenproduksjon...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandlerOwnProduction excelReportViewHandler = new ExcelReportViewHandlerOwnProduction(
					ExcelReportEnum.OWN_PRODUCTION);
			openFrame(new ExcelReportView(excelReportViewHandler, false));

		}
	}

	/**
	 * Håndterer menyvalg Ikke fakturert...
	 * 
	 * @author atle.brekka
	 */
	private class InvoiceReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public InvoiceReportAction() {
			super("Ikke fakturert...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
					ExcelReportEnum.PRODUCTIVITY_LIST_NOT_INVOICED, new Dimension(250, 110));
			openFrame(new ExcelReportView(excelReportViewHandler, false));
		}
	}

	/**
	 * Håndterer menyvalg Pakkliste ikke klar
	 * 
	 * @author atle.brekka
	 */
	private class PacklistNotReadyReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public PacklistNotReadyReportAction() {
			super("Pakkliste ikke klar...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
					ExcelReportEnum.PACKLIST_NOT_READY, new Dimension(250, 110));
			openFrame(new ExcelReportView(excelReportViewHandler, false));
		}
	}

	/**
	 * Håndterer menyvalg Status alle ordre ikke sendt
	 * 
	 * @author atle.brekka
	 */
	private class OrderStatusReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public OrderStatusReportAction() {
			super("Status alle ordre ikke sendt...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
					ExcelReportEnum.PRODUCTIVITY_ORDER_STATUS, new Dimension(250, 110));
			openFrame(new ExcelReportView(excelReportViewHandler, false));
		}
	}

	/**
	 * Håndterer menyvalg Telleliste
	 * 
	 * @author atle.brekka
	 */
	private class ReadyCountReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ReadyCountReportAction() {
			super("Telleliste...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(ExcelReportEnum.READY_COUNT,
					new Dimension(250, 110));
			openFrame(new ExcelReportView(excelReportViewHandler, false));
		}
	}

	/**
	 * Klasse som håndterer menyvalget Support...
	 * 
	 * @author atle.brekka
	 */
	private class OpenSupportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public OpenSupportAction() {
			super("Support...");
		}

		/**
		 * Åpner supportside med default browser
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			showSupportPage();

		}
	}

	/**
	 * Klasse som håndterer menyvalget Info...
	 * 
	 * @author atle.brekka
	 */
	private class ShowInfoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ShowInfoAction() {
			super("Info...");
		}

		/**
		 * Viser dialog med versjon
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			if (MainClass.version == null) {
				ResourceBundle configuration = ResourceBundle.getBundle("Configuration");

				MainClass.version = configuration.getString("version");
			}
			Util.showMsgFrame(ProTransMain.PRO_TRANS_MAIN.getContentPane(), "ProTrans - Grimstad Industrier",
					"Versjon " + MainClass.version);

		}
	}

	/**
	 * Håndterer menyvalg Kaskade vinduer...
	 * 
	 * @author atle.brekka
	 */
	private class CascadeWindows extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public CascadeWindows() {
			super("Kaskade vinduer...");
		}

		/**
		 * Viser dialog med versjon
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			JInternalFrame[] frames = proTransMain.getAllWindows();
			int x = 0;
			int y = 0;
			int width = proTransMain.getContentPane().getWidth();
			int height = (int) (proTransMain.getContentPane().getHeight() * 0.7);
			int frameDistance = proTransMain.getHeight() - proTransMain.getContentPane().getHeight();

			for (int i = 0; i < frames.length; i++) {
				if (!frames[i].isIcon()) {
					try {
						frames[i].setMaximum(false);
						frames[i].reshape(x, y, width, height);

						x += frameDistance;
						y += frameDistance;

						if (x + width > proTransMain.getContentPane().getWidth()) {
							x = 0;
						}
						if (y + height > proTransMain.getContentPane().getHeight()) {
							y = 0;
						}
						frames[i].toFront();
						frames[i].setSelected(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Viser supportside i default browser
	 */
	final void showSupportPage() {
		try {
			Desktop.browse(new URL("http://support.bouvet.no/ugland"));
		} catch (Exception e) {
			e.printStackTrace();
			Util.showErrorMsgFrame(ProTransMain.PRO_TRANS_MAIN, "Feil", e.getMessage());

		}
	}

	/**
	 * Initierer hjelpesystemet
	 */
	final void initHelpMenu() {
		try {
			ClassLoader cl = MenuBarBuilderImpl.class.getClassLoader();
			URL url = HelpSet.findHelpSet(cl, HELPSET_NAME);
			mainHS = new HelpSet(cl, url);
		} catch (Exception ee) {
			System.out.println("Help Set " + HELPSET_NAME + " not found");
			return;
		} catch (ExceptionInInitializerError ex) {
			System.err.println("initialization error:");
			ex.getException().printStackTrace();
		}
		mainHB = mainHS.createHelpBroker();

	}

	/**
	 * Klasse som håndterer menyvalget Hjelp...
	 * 
	 * @author atle.brekka
	 */
	private class HelpAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public HelpAction() {
			super("Hjelp...");
			initHelpMenu();
		}

		/**
		 * Denne gjør ingenting for det er en klasse i JavaHelp som håndterer
		 * dette
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {

		}
	}

	/**
	 * Henter menyvindu
	 * 
	 * @return menyvindu
	 */
	public final JMenu getMenuWindow() {
		return menuWindow;
	}

	public ApplicationUser getApplicationUser() {
		return login.getApplicationUser();
	}

	public UserType getUserType() {
		return login.getUserType();
	}

	public void setGarageMenu(GarageMenu aGarageMenu) {
		this.garageMenu = aGarageMenu;

	}

	public void setProductionMenu(ProductionMenu aProductionMenu) {
		productionMenu = aProductionMenu;

	}

}
