package no.ugland.utransprod.gui.single;

import static org.fest.swing.data.TableCell.row;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.RouteView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderNrProvider;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.handlers.RouteViewHandler;
import no.ugland.utransprod.gui.handlers.RouteViewHandler.PostShipmentTableModel;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoAction;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoActionFactory;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.handlers.TransportOrderTableModel;
import no.ugland.utransprod.gui.handlers.TransportViewHandler;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.gui.util.JYearChooserFinder;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrdchgrHeadVManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.TakstolInfoVManager;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.TakstolProductionVManager;
import no.ugland.utransprod.service.TransportManager;
import no.ugland.utransprod.service.TransportSumVManager;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JCheckBoxFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JMenuItemFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.fixture.JPopupMenuFixture;
import org.fest.swing.fixture.JScrollBarFixture;
import org.fest.swing.fixture.JTabbedPaneFixture;
import org.fest.swing.fixture.JTableFixture;
import org.fest.swing.timing.Pause;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 */
@Category(ManuellTest.class)
public class RouteViewTest {
	private static final int PAUSE = 3000;
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DialogFixture dialogFixture;

	private RouteViewHandler routeViewHandler;

	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TransportViewHandler.setTesting(true);

		PreventiveActionManager preventiveActionManager = (PreventiveActionManager) ModelUtil
				.getBean(PreventiveActionManager.MANAGER_NAME);
		when(managerRepository.getPreventiveActionManager()).thenReturn(
				preventiveActionManager);
		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean(JobFunctionManager.MANAGER_NAME);
		when(managerRepository.getJobFunctionManager()).thenReturn(
				jobFunctionManager);
		ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean(ProductAreaManager.MANAGER_NAME);
		when(managerRepository.getProductAreaManager()).thenReturn(
				productAreaManager);
		DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil
				.getBean(DeviationStatusManager.MANAGER_NAME);
		when(managerRepository.getDeviationStatusManager()).thenReturn(
				deviationStatusManager);
		ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean(ApplicationUserManager.MANAGER_NAME);
		when(managerRepository.getApplicationUserManager()).thenReturn(
				applicationUserManager);
		TransportSumVManager transportSumVManager = (TransportSumVManager) ModelUtil
				.getBean(TransportSumVManager.MANAGER_NAME);
		when(managerRepository.getTransportSumVManager()).thenReturn(
				transportSumVManager);
		BudgetManager budgetManager = (BudgetManager) ModelUtil
				.getBean(BudgetManager.MANAGER_NAME);
		when(managerRepository.getBudgetManager()).thenReturn(budgetManager);
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean(OrderManager.MANAGER_NAME);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
				.getBean(ArticleTypeManager.MANAGER_NAME);
		when(managerRepository.getArticleTypeManager()).thenReturn(
				articleTypeManager);
		SupplierManager supplierManager = (SupplierManager) ModelUtil
				.getBean(SupplierManager.MANAGER_NAME);
		when(managerRepository.getSupplierManager())
				.thenReturn(supplierManager);
		TransportManager transportManager = (TransportManager) ModelUtil
				.getBean(TransportManager.MANAGER_NAME);
		when(managerRepository.getTransportManager()).thenReturn(
				transportManager);
		ColliManager colliManager = (ColliManager) ModelUtil
				.getBean(ColliManager.MANAGER_NAME);
		when(managerRepository.getColliManager()).thenReturn(colliManager);
		DeviationManager deviationManager = (DeviationManager) ModelUtil
				.getBean(DeviationManager.MANAGER_NAME);
		when(managerRepository.getDeviationManager()).thenReturn(
				deviationManager);
		OrdlnManager ordlnManager = (OrdlnManager) ModelUtil
				.getBean(OrdlnManager.MANAGER_NAME);
		when(managerRepository.getOrdlnManager()).thenReturn(ordlnManager);
		TakstolPackageVManager takstolPackageVManager = (TakstolPackageVManager) ModelUtil
				.getBean(TakstolPackageVManager.MANAGER_NAME);
		when(managerRepository.getTakstolPackageVManager()).thenReturn(
				takstolPackageVManager);
		TakstolProductionVManager takstolProductionVManager = (TakstolProductionVManager) ModelUtil
				.getBean(TakstolProductionVManager.MANAGER_NAME);
		when(managerRepository.getTakstolProductionVManager()).thenReturn(
				takstolProductionVManager);
		TakstolInfoVManager takstolInfoVManager = (TakstolInfoVManager) ModelUtil
				.getBean(TakstolInfoVManager.MANAGER_NAME);
		when(managerRepository.getTakstolInfoVManager()).thenReturn(
				takstolInfoVManager);

		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserName("admin");
		applicationUser = applicationUserManager.findByObject(applicationUser)
				.get(0);
		ProductArea productArea = new ProductArea();
		ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
				.getBean(ProductAreaGroupManager.MANAGER_NAME);
		ProductAreaGroup productAreaGroup = productAreaGroupManager
				.findByName("Garasje");
		productArea.setProductAreaGroup(productAreaGroup);
		applicationUser.setProductArea(productArea);
		when(login.getApplicationUser()).thenReturn(applicationUser);

		UserType userType = new UserType();
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWriteAccess(1);
		WindowAccess windowAccess = new WindowAccess();
		windowAccess.setWindowName("Produksjonsenhet");
		userTypeAccess.setWindowAccess(windowAccess);
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);

		final PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(
				login, managerRepository);
		final DeviationOverviewViewFactory deviationOverviewViewFactory = new DeviationOverviewViewFactory() {

			public DeviationOverviewView create(
					DeviationViewHandler deviationViewHandler,
					boolean useSearchButton, Order aOrder, boolean doSeeAll,
					boolean forOrderInfo, boolean isForRegisterNew,
					Deviation notDisplayDeviation,
					boolean isDeviationTableEditable) {
				return new DeviationOverviewView(preventiveActionViewHandler,
						deviationViewHandler, useSearchButton, aOrder,
						doSeeAll, forOrderInfo, isForRegisterNew,
						notDisplayDeviation, isDeviationTableEditable);
			}
		};
		final DeviationViewHandlerFactory deviationViewHandlerFactory = new DeviationViewHandlerFactoryTestImp(
				deviationOverviewViewFactory, login, managerRepository,
				preventiveActionViewHandler);

		final DeviationViewHandler deviationViewHandler = new DeviationViewHandler(
				login, managerRepository, preventiveActionViewHandler, null,
				false, false, false, null, false);
		final ShowTakstolInfoActionFactory showTakstolInfoActionFactory = new ShowTakstolInfoActionFactory() {

			public ShowTakstolInfoAction create(
					OrderNrProvider aProduceableProvider, WindowInterface window) {
				return new ShowTakstolInfoAction(managerRepository, window,
						aProduceableProvider);
			}
		};

		OrderViewHandlerFactory orderViewHandlerFactory = new OrderViewHandlerFactory() {

			public OrderViewHandler create(boolean notInitData) {
				return new OrderViewHandler(login, managerRepository,
						deviationOverviewViewFactory,
						deviationViewHandlerFactory, notInitData);
			}
		};
		routeViewHandler = new RouteViewHandler(orderViewHandlerFactory, login,
				managerRepository, deviationViewHandlerFactory,
				showTakstolInfoActionFactory,
				new VismaFileCreatorImpl((OrdchgrHeadVManager) ModelUtil
						.getBean(OrdchgrHeadVManager.MANAGER_NAME), false));
		final RouteView view = new RouteView(routeViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(view.buildPanel(window));
				dialog.pack();
				return dialog;
			}
		});
		dialogFixture = new DialogFixture(dialog);
		dialogFixture.show();

	}

	@After
	public void tearDown() throws Exception {
		dialogFixture.cleanUp();
	}

	@Test
	public void testOpenWindow() throws Exception {

		dialogFixture.requireVisible();
	}

	@Test
	public void testDeleteTransport() {
		dialogFixture.show();

		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2012);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		int transports = routeViewHandler.getNumberOfTransports();

		// fjerner alle ordre
		int rowCount = dialogFixture.table(TableEnum.TABLETRANSPORTORDERS
				.getTableName() + "1").target.getRowCount();
		while (rowCount != 0) {
			dialogFixture
					.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
					.cell(row(0).column(1)).select();
			dialogFixture
					.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
					.cell(row(0).column(1)).rightClick();
			JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
					dialogFixture.robot, (JPopupMenu) dialogFixture.robot
							.finder().findByName("PopupMenuTransport"));
			popupMenuFixture.menuItem("MenuItemRemoveTransport").click();
			rowCount = dialogFixture.table(TableEnum.TABLETRANSPORTORDERS
					.getTableName() + "1").target.getRowCount();
		}

		dialogFixture.checkBox("CheckBoxSelection1").click();
		dialogFixture.button("ButtonRemoveTransport").click();
		JOptionPaneFixture jOptionPaneFixture = new JOptionPaneFixture(
				dialogFixture.robot, dialogFixture.robot.finder().findByType(
						JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();
		assertEquals(transports - 1, routeViewHandler.getNumberOfTransports());
	}

	@Test
	public void testSendNotPaid() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2010);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		JCheckBoxFixture checkBox = dialogFixture.checkBox("CheckBoxSent1");
		if (checkBox.target.isSelected()) {
			checkBox.click();
			checkBox.click();
		} else {
			checkBox.click();
		}
		JOptionPaneFixture jOptionPaneFixture = new JOptionPaneFixture(
				dialogFixture.robot, dialogFixture.robot.finder().findByType(
						JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Nei").click();
	}

	@Test
	public void testOpenOrder() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2010);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		JTableFixture tableFixture = dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1");
		tableFixture.cell(row(0).column(0)).select();
		tableFixture.cell(row(0).column(0)).doubleClick();

		WindowFinder.findDialog("EditOrderView").withTimeout(10000)
				.using(dialogFixture.robot);
	}

	@Test
	public void testChangeWeek() {
		dialogFixture.comboBox("ComboBoxWeeks").target.setSelectedIndex(10);

	}

	@Test
	public void testSetTransportForOrder() {
		dialogFixture.show();
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2013);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}

		JTableFixture tableFixture = dialogFixture.table("TableOrders");
		tableFixture.cell(row(0).column(0)).select();
		tableFixture.cell(row(0).column(0)).rightClick();

		JMenuItemFixture menuItem = new JMenuItemFixture(dialogFixture.robot,
				(JMenuItem) dialogFixture.robot.finder().findByName(
						"MenuItemSetTransportOrder"));
		menuItem.click();

		JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane()
				.using(dialogFixture.robot);
		optionPane.buttonWithText("OK").click();
	}

	@Test
	@Ignore("har ikke etterleveringer")
	public void testSetTransportForPostShipment() {
		dialogFixture.show(routeViewHandler.getWindowSize());
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		boolean oldWeek = false;
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2012);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
			oldWeek = true;
		}

		JTableFixture tableFixture = dialogFixture.table("TablePostShipments");
		tableFixture.cell(row(0).column(0)).select();
		tableFixture.cell(row(0).column(0)).rightClick();

		JMenuItemFixture menuItem = new JMenuItemFixture(dialogFixture.robot,
				(JMenuItem) dialogFixture.robot.finder().findByName(
						"MenuItemSetTransportPostShipment"));
		menuItem.click();

		JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane()
				.using(dialogFixture.robot);
		optionPane.buttonWithText("OK").click();

		if (oldWeek) {
			JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
					.buttonWithText("Ja").click();
		}
	}

	@Test
	public void testSend() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2010);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		dialogFixture.comboBox("ComboBoxSupplier1").selectItem(0);
		dialogFixture.button("SaveTransport").click();
		JCheckBoxFixture checkBox = dialogFixture.checkBox("CheckBoxSent1");
		if (checkBox.target.isSelected()) {
			checkBox.click();
			checkBox.click();
		} else {
			checkBox.click();
		}
		JOptionPaneFixture jOptionPaneFixture = new JOptionPaneFixture(
				dialogFixture.robot, dialogFixture.robot.finder().findByType(
						JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();

		jOptionPaneFixture = new JOptionPaneFixture(dialogFixture.robot,
				dialogFixture.robot.finder().findByType(JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();

		DialogFixture dateDialog = WindowFinder.findDialog("DateView").using(
				dialogFixture.robot);
		dateDialog.button("ButtonOk").click();

		DialogFixture sentDialog = WindowFinder.findDialog("SentTransportView")
				.using(dialogFixture.robot);
		int rowCount = sentDialog.table("TableOrders").target.getRowCount();

		sentDialog.button("ButtonOk").click();

		for (int i = 0; i < rowCount; i++) {
			try {
				jOptionPaneFixture = new JOptionPaneFixture(
						dialogFixture.robot, dialogFixture.robot.finder()
								.findByType(JOptionPane.class));
				jOptionPaneFixture.buttonWithText("OK").click();

				DialogFixture deviationDialog = WindowFinder.findDialog(
						"EditDeviationView").using(dialogFixture.robot);
				deviationDialog.radioButton("RadioButtonInternal").click();
				deviationDialog.comboBox("ComboBoxResponsible").selectItem(1);
				deviationDialog.comboBox("ComboBoxDeviationFunction")
						.selectItem(1);
				deviationDialog.comboBox("ComboBoxFunctionCategory")
						.selectItem(1);

				deviationDialog.button("AddDeviationComment").click();
				JDialog commentDialog = (JDialog) dialogFixture.robot.finder()
						.findByName("EditCommentView");
				DialogFixture commentDialogFixture = new DialogFixture(
						dialogFixture.robot, commentDialog);
				commentDialogFixture.textBox("TextAreaDeviationCommentComment")
						.enterText("test");
				commentDialogFixture.button("DeviationCommentOk").click();
				deviationDialog.button("ButtonOk").click();
			} catch (ComponentLookupException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}

		}

	}

	@Test
	public void testUpdateGulvspon() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2012);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}

		JTableFixture tableFixture = dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1");
		String gulvspon = tableFixture.cell(row(0).column(5)).value();
		tableFixture.cell(row(0).column(0)).doubleClick();

		DialogFixture editOrder = WindowFinder.findDialog("EditOrderView")
				.withTimeout(10000).using(dialogFixture.robot);

		editOrder.checkBox("CheckBoxLock").uncheck();
		JTabbedPaneFixture tabbedPane = editOrder
				.tabbedPane("TabbedPaneDeviationArticle");
		tabbedPane.selectTab(1);
		editOrder.button("ButtonEditAll").click();
		DialogFixture editAllDialog = WindowFinder.findDialog(
				"UpdateOrderLineView").using(dialogFixture.robot);
		JScrollBarFixture scrollBar = editAllDialog.scrollPane()
				.verticalScrollBar();
		scrollBar.target.setValue(scrollBar.target.getMaximum());

		JCheckBoxFixture checkBoxGulvspon = editAllDialog
				.checkBox("CheckBoxHar gulvspon");

		if (checkBoxGulvspon.target.isSelected()) {
			checkBoxGulvspon.uncheck();
		} else {
			checkBoxGulvspon.check();
		}

		editAllDialog.button("ButtonOk").click();

		editOrder.button("SaveOrder").click();
		editOrder.button("EditCancelOrder").click();

		dialogFixture.button("ButtonRefresh").click();

		tableFixture = dialogFixture.table(TableEnum.TABLETRANSPORTORDERS
				.getTableName() + "1");
		String gulvspon2 = tableFixture.cell(row(0).column(5)).value();

		assertEquals(true, !gulvspon.equalsIgnoreCase(gulvspon2));

	}

	@Test
	public void testRemoveTransport() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2010);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(0).column(1)).select();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(0).column(1)).rightClick();
		JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
				dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder()
						.findByName("PopupMenuTransport"));
		popupMenuFixture.menuItem("MenuItemRemoveTransport").click();
	}

	@Test
	public void testGenerateTransportLetter() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2010);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		;
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(0).column(1)).select();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(0).column(1)).rightClick();
		JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
				dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder()
						.findByName("PopupMenuTransport"));
		popupMenuFixture.menuItem("MenuItemTransportLetter").click();

		DialogFixture printer = WindowFinder.findDialog("Fraktbrev")
				.withTimeout(20000).using(dialogFixture.robot);
		printer.button("ButtonCancel").click();
	}

	@Test
	@Ignore
	public void testRemoveColli() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2010);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		JComboBoxFixture combo = dialogFixture.comboBox("ComboBoxSupplier1");
		combo.selectItem(combo.target.getSelectedIndex() + 1);
		dialogFixture.button("SaveTransport").click();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(0).column(1)).select();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(0).column(1)).rightClick();
		JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
				dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder()
						.findByName("PopupMenuTransport"));
		popupMenuFixture.menuItem("MenuItemSetSent").click();

		JOptionPaneFixture jOptionPaneFixture = new JOptionPaneFixture(
				dialogFixture.robot, dialogFixture.robot.finder().findByType(
						JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();

		jOptionPaneFixture = new JOptionPaneFixture(dialogFixture.robot,
				dialogFixture.robot.finder().findByType(JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();

		DialogFixture dateDialog = WindowFinder.findDialog("DateView").using(
				dialogFixture.robot);
		dateDialog.button("ButtonOk").click();

		DialogFixture sentDialog = WindowFinder.findDialog("SentTransportView")
				.using(dialogFixture.robot);

		sentDialog.table("TableOrders").cell(row(0).column(1)).select();

		sentDialog.button("ButtonShowCollies").click();

	}

	@Test
	@Ignore
	public void testSendOrderWithMissing() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2013);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedItem(1);
			Pause.pause(PAUSE);
		}
		JComboBoxFixture combo = dialogFixture.comboBox("ComboBoxSupplier1");
		combo.selectItem(combo.target.getSelectedIndex() + 1);
		dialogFixture.button("SaveTransport").click();
		JCheckBoxFixture checkBox = dialogFixture.checkBox("CheckBoxSent1");

		if (checkBox.target.isSelected()) {
			checkBox.uncheck();
		}
		boolean missingNotFound = true;
		int currentRowOrder = 0;
		String[] missing = null;
		while (missingNotFound) {
			dialogFixture
					.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
					.cell(row(currentRowOrder).column(1)).select();
			dialogFixture
					.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
					.cell(row(currentRowOrder).column(1)).rightClick();
			JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
					dialogFixture.robot, (JPopupMenu) dialogFixture.robot
							.finder().findByName("PopupMenuTransport"));
			popupMenuFixture.menuItem("MenuItemMissing").click();

			DialogFixture missingDialog = WindowFinder.findDialog("Mangler")
					.using(dialogFixture.robot);
			missing = missingDialog.list("ListOptions").contents();
			if (missing != null && missing.length != 0) {
				missingNotFound = false;
			} else {
				currentRowOrder++;
			}
			missingDialog.button("ButtonCancel").click();
		}

		Transportable transportable = ((TransportOrderTableModel) dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1").target
				.getModel()).getTransportable(currentRowOrder);
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(currentRowOrder).column(1)).select();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(currentRowOrder).column(1)).rightClick();
		JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
				dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder()
						.findByName("PopupMenuTransport"));
		popupMenuFixture.menuItem("MenuItemSetSent").click();

		JOptionPaneFixture jOptionPaneFixture = new JOptionPaneFixture(
				dialogFixture.robot, dialogFixture.robot.finder().findByType(
						JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();

		jOptionPaneFixture = new JOptionPaneFixture(dialogFixture.robot,
				dialogFixture.robot.finder().findByType(JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();

		DialogFixture dateDialog = WindowFinder.findDialog("DateView").using(
				dialogFixture.robot);
		dateDialog.button("ButtonOk").click();

		DialogFixture sentDialog = WindowFinder.findDialog("SentTransportView")
				.using(dialogFixture.robot);

		sentDialog.button("ButtonOk").click();

		jOptionPaneFixture = new JOptionPaneFixture(dialogFixture.robot,
				dialogFixture.robot.finder().findByType(JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();

		DialogFixture deviationDialog = WindowFinder.findDialog(
				"EditDeviationView").using(dialogFixture.robot);
		deviationDialog.radioButton("RadioButtonInternal").click();
		deviationDialog.comboBox("ComboBoxResponsible").selectItem(1);
		deviationDialog.comboBox("ComboBoxDeviationFunction").selectItem(1);
		deviationDialog.comboBox("ComboBoxFunctionCategory").selectItem(1);
		deviationDialog.comboBox("ComboBoxStatus").selectItem(1);

		deviationDialog.button("AddDeviationComment").click();
		JDialog commentDialog = (JDialog) dialogFixture.robot.finder()
				.findByName("EditCommentView");
		DialogFixture commentDialogFixture = new DialogFixture(
				dialogFixture.robot, commentDialog);
		commentDialogFixture.textBox("TextAreaComment").enterText("test");
		commentDialogFixture.button("ButtonCommentOk").click();
		deviationDialog.button("ButtonOk").click();
		//
		PostShipment postShipment = null;
		boolean postShipmentNotFound = true;
		int currentRowPostShipment = 0;
		while (postShipmentNotFound) {
			postShipment = ((PostShipmentTableModel) dialogFixture
					.table("TablePostShipments").target.getModel())
					.getPostShipment(currentRowPostShipment);
			if (postShipment != null
					&& postShipment
							.getOrder()
							.getOrderNr()
							.equalsIgnoreCase(
									transportable.getOrder().getOrderNr())) {
				postShipmentNotFound = false;
			} else {
				currentRowPostShipment++;
			}
		}

		assertNotNull(postShipment);

		dialogFixture.table("TablePostShipments")
				.cell(row(currentRowPostShipment).column(1)).select();
		dialogFixture.table("TablePostShipments")
				.cell(row(currentRowPostShipment).column(1)).rightClick();
		popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot,
				(JPopupMenu) dialogFixture.robot.finder().findByName(
						"PopupMenuPostShipment"));
		popupMenuFixture.menuItem("MenuItemShowContent").click();

		DialogFixture missingDialog = WindowFinder.findDialog("Innhold").using(
				dialogFixture.robot);
		String[] contents = missingDialog.list("ListOptions").contents();
		Arrays.sort(contents);
		missingDialog.button("ButtonCancel").click();

		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(currentRowOrder).column(1)).select();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(currentRowOrder).column(1)).rightClick();
		popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot,
				(JPopupMenu) dialogFixture.robot.finder().findByName(
						"PopupMenuTransport"));
		popupMenuFixture.menuItem("MenuItemSetSent").click();

		dialogFixture.table("TablePostShipments")
				.cell(row(currentRowPostShipment).column(1)).select();

		dialogFixture.button("ButtonDeletePostShipment").click();

		jOptionPaneFixture = new JOptionPaneFixture(dialogFixture.robot,
				dialogFixture.robot.finder().findByType(JOptionPane.class));
		jOptionPaneFixture.buttonWithText("Ja").click();

		assertNotNull(contents);
		Arrays.sort(missing);
		assertEquals(getArrayAsString(missing), getArrayAsString(contents));
	}

	@Test
	@Ignore
	public void testAddCommentToPostShipment() {

		PostShipment postShipment = ((PostShipmentTableModel) dialogFixture
				.table("TablePostShipments").target.getModel())
				.getPostShipment(0);

		assertNotNull(postShipment);
		String postShipmentComment = postShipment.getComment();
		postShipmentComment = postShipmentComment != null ? postShipmentComment
				: "";

		dialogFixture.table("TablePostShipments").cell(row(0).column(1))
				.select();
		dialogFixture.table("TablePostShipments").cell(row(0).column(1))
				.rightClick();
		JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
				dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder()
						.findByName("PopupMenuPostShipment"));
		popupMenuFixture.menuItem("MenuItemAddComment").click();

		DialogFixture dialogComment = WindowFinder
				.findDialog("EditCommentView").using(dialogFixture.robot);
		dialogComment.textBox("TextAreaComment").enterText("test");
		dialogComment.checkBox("CheckBoxTransport").check();
		dialogComment.checkBox("CheckBoxPackage").check();
		dialogComment.button("ButtonCommentOk").click();

		postShipment = ((PostShipmentTableModel) dialogFixture
				.table("TablePostShipments").target.getModel())
				.getPostShipment(0);

		assertEquals(postShipmentComment + "test;", postShipment.getComment());
	}

	@Test
	public void testSpliOrder() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2012);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(0).column(1)).select();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1")
				.cell(row(0).column(1)).rightClick();
		JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
				dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder()
						.findByName("PopupMenuTransport"));
		popupMenuFixture.menuItem("MenuItemSplitOrder").click();

		WindowFinder.findDialog("SplitOrderView").using(dialogFixture.robot);
	}

	@Test
	@Ignore
	public void testShowDeviationForPostshipment() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2012);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}
		int tableCount = 0;
		int tableRow = 0;
		boolean postShipmentFound = false;
		while (!postShipmentFound && tableCount < 10) {

			tableCount++;

			JTableFixture table = dialogFixture
					.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "1");
			int rowCount = table.target.getRowCount();
			for (int j = 0; j < rowCount; j++) {
				tableRow = j;
				Transportable transportable = ((TransportOrderTableModel) table.target
						.getModel()).getTransportable(j);
				if (transportable instanceof PostShipment) {
					postShipmentFound = true;
					break;
				}
			}
		}
		assertEquals(true, postShipmentFound);

		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName()
						+ tableCount).cell(row(tableRow).column(1)).select();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName()
						+ tableCount).cell(row(tableRow).column(1))
				.rightClick();

		JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
				dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder()
						.findByName("PopupMenuTransport"));
		popupMenuFixture.menuItem("MenuItemShowDeviation").click();

		WindowFinder.findDialog("EditDeviationView").withTimeout(60000)
				.using(dialogFixture.robot);
	}

	@Test
	public void testShowDeviationForPostShipment() {
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2012);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedIndex(Util.getCurrentWeek() - 2);
			Pause.pause(PAUSE);
		}

		JTableFixture tableFixture = dialogFixture.table("TablePostShipments");
		tableFixture.cell(row(0).column(0)).select();
		tableFixture.cell(row(0).column(0)).rightClick();

		JMenuItemFixture menuItem = new JMenuItemFixture(dialogFixture.robot,
				(JMenuItem) dialogFixture.robot.finder().findByName(
						"MenuItemShowDeviation"));
		menuItem.click();

		WindowFinder.findDialog("EditDeviationView").withTimeout(60000)
				.using(dialogFixture.robot);

	}

	@Test
	public void testShowAsList() {
		dialogFixture.checkBox("CheckBoxListView").check();
	}

	private String getArrayAsString(String[] array) {
		StringBuilder arrayString = new StringBuilder();
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				arrayString.append(array[i]);
			}
		}
		return arrayString.toString();
	}

	private class DeviationViewHandlerFactoryTestImp implements
			DeviationViewHandlerFactory {

		private DeviationOverviewViewFactory deviationOverviewViewFactory;
		private Login login;
		private ManagerRepository managerRepository;

		public DeviationViewHandlerFactoryTestImp(
				DeviationOverviewViewFactory deviationOverviewViewFactory,
				Login login, ManagerRepository managerRepository,
				PreventiveActionViewHandler preventiveActionViewHandler) {
			super();
			this.deviationOverviewViewFactory = deviationOverviewViewFactory;
			this.login = login;
			this.managerRepository = managerRepository;
			this.preventiveActionViewHandler = preventiveActionViewHandler;
		}

		private PreventiveActionViewHandler preventiveActionViewHandler;

		public DeviationViewHandler create(Order aOrder, boolean doSeAll,
				boolean forOrderInfo, boolean isForRegisterNew,
				Deviation notDisplayDeviation, boolean isDeviationTableEditable) {
			// TODO Auto-generated method stub
			return new DeviationViewHandler(login, managerRepository,
					preventiveActionViewHandler, aOrder, doSeAll, forOrderInfo,
					isForRegisterNew, notDisplayDeviation,
					isDeviationTableEditable);
		}

	}

	@Test
	@Ignore
	public void testGenerateTakstolInfo() {
		dialogFixture.show();
		int numberOfTransports = routeViewHandler.getNumberOfTransports();
		if (numberOfTransports == 0) {
			dialogFixture.robot.finder().find(
					new JYearChooserFinder("YearChooserTransport")).setYear(2010);
			dialogFixture.comboBox("ComboBoxWeeks").target
					.setSelectedItem(1);
			Pause.pause(PAUSE);
		}
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "8")
				.cell(row(0).column(1)).select();
		dialogFixture
				.table(TableEnum.TABLETRANSPORTORDERS.getTableName() + "8")
				.cell(row(0).column(1)).rightClick();
		JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(
				dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder()
						.findByName("PopupMenuTransport"));
		popupMenuFixture.menuItem("MenuItemShowTakstolInfo").click();

		DialogFixture printer = WindowFinder.findDialog("Takstolinfo")
				.withTimeout(20000).using(dialogFixture.robot);
		printer.button("ButtonCancel").click();
	}

}
