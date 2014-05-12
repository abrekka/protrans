package no.ugland.utransprod.gui.single;

import static org.fest.swing.data.TableCell.row;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ArticlePackageViewFactory;
import no.ugland.utransprod.gui.ArticleProductionPackageView;
import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProductionOverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderNrProvider;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler;
import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler.ProductionOverviewTableModel;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoAction;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoActionFactory;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.TakstolPackageApplyList;
import no.ugland.utransprod.gui.model.TakstolProductionApplyList;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProcentDone;
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
import no.ugland.utransprod.service.FakturagrunnlagVManager;
import no.ugland.utransprod.service.FrontProductionVManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrdchgrHeadVManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PacklistVManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.TakstolProductionVManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.fixture.JPopupMenuFixture;
import org.fest.swing.fixture.JTableFixture;
import org.jdesktop.swingx.JXTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 */
@Category(ManuellTest.class)
public class ProductionOverviewViewTest {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    private ProductionOverviewViewHandler productionOverviewViewHandler;

    private DialogFixture dialogFixture;
    @Mock
    private Login login;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private DeviationOverviewViewFactory deviationOverviewViewFactory;
    @Mock
    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    @Mock
    private OrderViewHandlerFactory orderViewHandlerFactory;

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
	ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);
	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
	JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil.getBean(JobFunctionManager.MANAGER_NAME);
	when(managerRepository.getJobFunctionManager()).thenReturn(jobFunctionManager);
	DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil.getBean(DeviationStatusManager.MANAGER_NAME);
	when(managerRepository.getDeviationStatusManager()).thenReturn(deviationStatusManager);
	ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME);
	when(managerRepository.getApplicationUserManager()).thenReturn(applicationUserManager);
	ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil.getBean(ArticleTypeManager.MANAGER_NAME);
	when(managerRepository.getArticleTypeManager()).thenReturn(articleTypeManager);
	ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil.getBean(ProductAreaGroupManager.MANAGER_NAME);
	when(managerRepository.getProductAreaGroupManager()).thenReturn(productAreaGroupManager);
	BudgetManager budgetManager = (BudgetManager) ModelUtil.getBean(BudgetManager.MANAGER_NAME);
	when(managerRepository.getBudgetManager()).thenReturn(budgetManager);
	OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	when(managerRepository.getOrderManager()).thenReturn(orderManager);
	ProductionUnitManager productionUnitManager = (ProductionUnitManager) ModelUtil.getBean(ProductionUnitManager.MANAGER_NAME);
	when(managerRepository.getProductionUnitManager()).thenReturn(productionUnitManager);
	TakstolPackageVManager takstolPackageVManager = (TakstolPackageVManager) ModelUtil.getBean(TakstolPackageVManager.MANAGER_NAME);
	when(managerRepository.getTakstolPackageVManager()).thenReturn(takstolPackageVManager);
	TakstolProductionVManager takstolProductionVManager = (TakstolProductionVManager) ModelUtil.getBean(TakstolProductionVManager.MANAGER_NAME);
	when(managerRepository.getTakstolProductionVManager()).thenReturn(takstolProductionVManager);
	PacklistVManager packlistVManager = (PacklistVManager) ModelUtil.getBean(PacklistVManager.MANAGER_NAME);
	when(managerRepository.getPacklistVManager()).thenReturn(packlistVManager);
	SupplierManager supplierManager = (SupplierManager) ModelUtil.getBean(SupplierManager.MANAGER_NAME);
	when(managerRepository.getSupplierManager()).thenReturn(supplierManager);
	DeviationManager deviationManager = (DeviationManager) ModelUtil.getBean(DeviationManager.MANAGER_NAME);
	when(managerRepository.getDeviationManager()).thenReturn(deviationManager);
	FrontProductionVManager frontProductionVManager = (FrontProductionVManager) ModelUtil.getBean(FrontProductionVManager.MANAGER_NAME);
	when(managerRepository.getFrontProductionVManager()).thenReturn(frontProductionVManager);
	OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);
	when(managerRepository.getOrderLineManager()).thenReturn(orderLineManager);
	ColliManager colliManager = (ColliManager) ModelUtil.getBean(ColliManager.MANAGER_NAME);
	when(managerRepository.getColliManager()).thenReturn(colliManager);

	final ApplicationUser applicationUser = new ApplicationUser();
	ProductArea productArea = new ProductArea();
	ProductAreaGroup productAreaGroup = new ProductAreaGroup();
	productArea.setProductAreaGroup(productAreaGroup);
	applicationUser.setProductArea(productArea);
	applicationUser.setUserName("username");
	when(login.getApplicationUser()).thenReturn(applicationUser);

	final UserType userType = new UserType();
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

	OrderViewHandler orderViewHandler = new OrderViewHandler(login, managerRepository, deviationOverviewViewFactory, deviationViewHandlerFactory,
		true);
	when(orderViewHandlerFactory.create(true)).thenReturn(orderViewHandler);

	final ShowTakstolInfoActionFactory showTakstolInfoActionFactory = new ShowTakstolInfoActionFactory() {

	    public ShowTakstolInfoAction create(OrderNrProvider aProduceableProvider, WindowInterface window) {
		return null;
	    }
	};

	OrdchgrHeadVManager ordchgrHeadVManager = (OrdchgrHeadVManager) ModelUtil.getBean(OrdchgrHeadVManager.MANAGER_NAME);
	FakturagrunnlagVManager fakturagrunnlagVManager = (FakturagrunnlagVManager) ModelUtil.getBean(FakturagrunnlagVManager.MANAGER_NAME);
	VismaFileCreator vismaFileCreator = new VismaFileCreatorImpl(ordchgrHeadVManager, false, fakturagrunnlagVManager);
	final SetProductionUnitActionFactory setProductionUnitActionFactory = new SetProductionUnitActionFactory() {

	    public SetProductionUnitAction create(ArticleType aArticleType, ProduceableProvider aProduceableProvider, WindowInterface aWindow) {
		return new SetProductionUnitAction(managerRepository, aArticleType, aProduceableProvider, aWindow);
	    }
	};
	ArticleType articleTypeTakstol = articleTypeManager.findByName("Takstoler");
	final ArticlePackageViewHandlerFactory articlePackageViewHandlerFactory = new ArticlePackageViewHandlerFactory() {

	    public ArticlePackageViewHandler create(ArticleType articleType, String defaultColliName) {
		return new ArticlePackageViewHandler(setProductionUnitActionFactory, login, managerRepository, null, articleType, defaultColliName);
	    }
	};
	ArticlePackageViewFactory articlePackageViewFactory = new ArticlePackageViewFactory() {

	    public ArticleProductionPackageView create(ArticleType articleType, ApplyListInterface applyListInterface, String defaultColliName) {

		return new ArticleProductionPackageView(articlePackageViewHandlerFactory, articleType, defaultColliName);
	    }
	};

	TakstolProductionApplyList takstolProductionApplyList = new TakstolProductionApplyList("Takstol", login, "Takstoler", managerRepository,
		articlePackageViewFactory);
	TakstolPackageApplyList takstolPackageApplyList = new TakstolPackageApplyList(takstolPackageVManager, vismaFileCreator, login, "Takstoler",
		null, articlePackageViewFactory, managerRepository);

	productionOverviewViewHandler = new ProductionOverviewViewHandler(vismaFileCreator, orderViewHandlerFactory, login, managerRepository,
		deviationViewHandlerFactory, showTakstolInfoActionFactory, articleTypeTakstol, takstolPackageApplyList, takstolProductionApplyList,
		setProductionUnitActionFactory, null, null);

	final ProductionOverviewView viewer = new ProductionOverviewView(productionOverviewViewHandler);

	JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
	    protected JDialog executeInEDT() {
		JDialog dialog = new JDialog();
		WindowInterface window = new JDialogAdapter(dialog);
		dialog.add(viewer.buildPanel(window));
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
    public void testShow() {
	dialogFixture.requireVisible();

	dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetPacklistReadyForOrder() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");
	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean orderWithoutPacklistNotFound = true;
	String content;
	Transportable transportable = null;
	while (orderWithoutPacklistNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);
	    if (transportable instanceof Order) {
		content = tableFixture.cell(row(row).column(3)).value();
		if (content.equalsIgnoreCase("")) {
		    orderWithoutPacklistNotFound = false;
		}
	    }
	}
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(3)).click();
	tableFixture.cell(row(row).column(3)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemPacklist").click();

	DialogFixture dateDialog = WindowFinder.findDialog("DateView").using(dialogFixture.robot);
	dateDialog.button("ButtonOk").click();

	assertEquals(true, tableFixture.cell(row(row).column(3)).value().length() != 0);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetPacklistNotReadyForOrder() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean orderWithPacklistNotFound = true;
	String content;
	Transportable transportable = null;
	while (orderWithPacklistNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);
	    if (transportable instanceof Order) {
		content = tableFixture.cell(row(row).column(3)).value();
		if (!content.equalsIgnoreCase("")) {
		    orderWithPacklistNotFound = false;
		}
	    }
	}
	assertEquals(false, orderWithPacklistNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(3)).click();
	tableFixture.cell(row(row).column(3)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemPacklist").click();

	assertEquals(true, tableFixture.cell(row(row).column(3)).value().length() == 0);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetVeggProducedForOrder() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = 0;
	boolean orderWithoutVeggNotFound = true;
	String content;
	Transportable transportable = null;
	while (orderWithoutVeggNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);
	    if (transportable instanceof Order) {

		content = tableFixture.cell(row(row).column(4)).value();
		if (content.indexOf("e") != -1 && content.indexOf("X") == -1) {
		    orderWithoutVeggNotFound = false;
		}
	    }

	}
	assertEquals(false, orderWithoutVeggNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(4)).click();
	tableFixture.cell(row(row).column(4)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemVegg").click();

	assertEquals(true, tableFixture.cell(row(row).column(4)).value().indexOf("X") != -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetVeggNotProducedForOrder() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean orderWithoutVeggNotFound = true;
	String content;
	while (orderWithoutVeggNotFound) {
	    row++;

	    content = tableFixture.cell(row(row).column(4)).value();
	    if (content.indexOf("X") != -1) {
		orderWithoutVeggNotFound = false;
	    }

	}
	assertEquals(false, orderWithoutVeggNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(4)).click();
	tableFixture.cell(row(row).column(4)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemVegg").click();

	assertEquals(true, tableFixture.cell(row(row).column(4)).value().indexOf("X") == -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testOpenOrder() {
	dialogFixture.requireVisible();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	tableFixture.cell(row(0).column(2)).click();

	Order selectedOrder = productionOverviewViewHandler.getSelectedObject().getOrder();

	final PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(login, managerRepository);
	DeviationViewHandler deviationViewHandler = new DeviationViewHandler(login, managerRepository, preventiveActionViewHandler, selectedOrder,
		true, false, true, null, true);
	when(deviationViewHandlerFactory.create(selectedOrder, true, true, false, null, false)).thenReturn(deviationViewHandler);

	DeviationOverviewView deviationOverviewView = new DeviationOverviewView(preventiveActionViewHandler, deviationViewHandler, false,
		selectedOrder, true, false, true, null, true);
	when(deviationOverviewViewFactory.create(deviationViewHandler, false, selectedOrder, true, true, false, null, false)).thenReturn(
		deviationOverviewView);

	tableFixture.cell(row(0).column(2)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemOpenOrder").click();

	DialogFixture orderDialog = WindowFinder.findDialog("EditOrderView").using(dialogFixture.robot);
	orderDialog.button("EditCancelOrder").click();

	dialogFixture.button("ButtonCancel").click();

    }

    @Test
    public void testShowMissingForOrder() {
	dialogFixture.requireVisible();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean orderNotFound = true;

	Transportable transportable = null;
	while (orderNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);
	    if (transportable instanceof Order) {
		orderNotFound = false;
	    }
	}
	assertEquals(false, orderNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);

	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(2)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemShowMissing").click();

	DialogFixture dialog = WindowFinder.findDialog("Mangler").withTimeout(20000).using(dialogFixture.robot);
	dialog.button("ButtonCancel").click();

	dialogFixture.button("ButtonCancel").click();

    }

    @Test
    public void testShowMissingForPostShipment() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean postShipmentNotFound = true;

	Transportable transportable = null;
	int maxRow = tableFixture.target.getRowCount();
	while (postShipmentNotFound && row < maxRow - 1) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);
	    if (transportable instanceof PostShipment) {
		postShipmentNotFound = false;
	    }
	}
	assertEquals(false, postShipmentNotFound);
	if (!postShipmentNotFound) {
	    ((JXTable) tableFixture.target).scrollRowToVisible(row);

	    tableFixture.cell(row(row).column(2)).click();
	    tableFixture.cell(row(row).column(2)).rightClick();
	    JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		    "PopupMenuProduction"));
	    popupMenuFixture.menuItem("MenuItemShowMissing").click();

	    DialogFixture dialog = WindowFinder.findDialog("Mangler").using(dialogFixture.robot);
	    dialog.button("ButtonCancel").click();

	    dialogFixture.button("ButtonCancel").click();
	}

    }

    @Test
    public void testShowContentForPostShipment() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");
	// dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean postShipmentNotFound = true;

	Transportable transportable = null;
	int maxRow = tableFixture.target.getRowCount();
	while (postShipmentNotFound && row < maxRow - 1) {

	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);
	    if (transportable instanceof PostShipment) {
		postShipmentNotFound = false;
	    }
	}
	assertEquals(false, postShipmentNotFound);
	if (!postShipmentNotFound) {
	    ((JXTable) tableFixture.target).scrollRowToVisible(row);

	    tableFixture.cell(row(row).column(2)).click();
	    tableFixture.cell(row(row).column(2)).rightClick();
	    JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		    "PopupMenuProduction"));
	    popupMenuFixture.menuItem("MenuItemShowContent").click();

	    DialogFixture dialog = WindowFinder.findDialog("Innhold").using(dialogFixture.robot);
	    dialog.button("ButtonCancel").click();

	    dialogFixture.button("ButtonCancel").click();
	}

    }

    @Test
    public void testSetFrontProducedForOrder() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = 0;
	boolean orderWithoutFrontNotFound = true;
	String content;
	Transportable transportable = null;
	while (orderWithoutFrontNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);

	    if (transportable instanceof Order) {

		content = tableFixture.cell(row(row).column(5)).value();
		if (content.indexOf("e") != -1 && content.indexOf("X") == -1) {
		    orderWithoutFrontNotFound = false;
		}
	    }

	}
	assertEquals(false, orderWithoutFrontNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(5)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemFront").click();

	assertEquals(true, tableFixture.cell(row(row).column(5)).value().indexOf("X") != -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetFrontNotProducedForOrder() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean orderWithFrontNotFound = true;
	String content;
	while (orderWithFrontNotFound) {
	    row++;

	    content = tableFixture.cell(row(row).column(5)).value();
	    if (content.indexOf("X") != -1) {
		orderWithFrontNotFound = false;
	    }

	}
	assertEquals(false, orderWithFrontNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(5)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemFront").click();

	assertEquals(true, tableFixture.cell(row(row).column(5)).value().indexOf("X") == -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetGavlProduced() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = 0;
	boolean withoutGavlNotFound = true;
	String content;
	Transportable transportable = null;
	while (withoutGavlNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);

	    if (transportable instanceof Order && transportable.getProductAreaGroup().getProductAreaGroupName().equalsIgnoreCase("Garasje")
		    && transportable.getTransportString().indexOf("Tilleggsordre") == -1) {

		content = tableFixture.cell(row(row).column(6)).value();

		if (content.length() > 1 && content.indexOf("X") == -1) {
		    withoutGavlNotFound = false;
		}
	    }

	}
	assertEquals(false, withoutGavlNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(6)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemGavl").click();

	assertEquals(true, tableFixture.cell(row(row).column(6)).value().indexOf("X") != -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetGavlNotProduced() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean withGavlNotFound = true;
	String content;
	while (withGavlNotFound) {
	    row++;

	    content = tableFixture.cell(row(row).column(6)).value();
	    if (content.indexOf("X") != -1) {
		withGavlNotFound = false;
	    }

	}
	assertEquals(false, withGavlNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(6)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemGavl").click();

	assertEquals(true, tableFixture.cell(row(row).column(6)).value().indexOf("X") == -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetTakstolNotProduced() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean withTakstolNotFound = true;
	String content;
	while (withTakstolNotFound) {
	    row++;

	    content = tableFixture.cell(row(row).column(7)).value();
	    if (content.indexOf("eX") != -1) {
		withTakstolNotFound = false;
	    }

	}
	assertEquals(false, withTakstolNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(7)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemProduksjonTakstol").click();

	assertEquals(true, tableFixture.cell(row(row).column(7)).value().indexOf("X") == -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetTakstolProduced() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean withoutTakstolNotFound = true;
	String content;
	while (withoutTakstolNotFound) {
	    row++;

	    content = tableFixture.cell(row(row).column(7)).value();
	    if (content.indexOf("e") != -1 && content.indexOf("X") == -1) {
		withoutTakstolNotFound = false;
	    }

	}
	assertEquals(false, withoutTakstolNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(7)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemProduksjonTakstol").click();

	DialogFixture productionUnitDialog = WindowFinder.findDialog("Velg produksjonsenhet").using(dialogFixture.robot);
	productionUnitDialog.comboBox().selectItem("Jig 1");
	productionUnitDialog.button("ButtonOk").click();

	DialogFixture articleView = WindowFinder.findDialog("ArticlePackageView").withTimeout(30000).using(dialogFixture.robot);
	articleView.button("ButtonOk").click();

	assertEquals(true, tableFixture.cell(row(row).column(7)).value().indexOf("X") != -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetProductionUnitTakstol() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean withoutTakstolNotFound = true;
	String content;
	while (withoutTakstolNotFound) {
	    row++;

	    content = tableFixture.cell(row(row).column(7)).value();
	    if (content.indexOf("e") != -1 && content.indexOf("X") == -1) {
		withoutTakstolNotFound = false;
	    }

	}
	assertEquals(false, withoutTakstolNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(7)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemProductionUnitTakstol").click();

	DialogFixture productionUnitDialog = WindowFinder.findDialog("Velg produksjonsenhet").using(dialogFixture.robot);
	productionUnitDialog.comboBox().selectItem("Jig 1");
	productionUnitDialog.button("ButtonOk").click();

	assertEquals(true, tableFixture.cell(row(row).column(7)).value().indexOf("Jig 1") != -1);
	dialogFixture.button("ButtonCancel").click();

    }

    @Test
    public void testSetTakstolPacked() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");
	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = 0;
	boolean withoutTakstolPackNotFound = true;
	String content;
	Transportable transportable = null;
	while (withoutTakstolPackNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);
	    if (transportable instanceof Order && transportable.getTransportString().indexOf("Tilleggsordre") == -1) {

		content = tableFixture.cell(row(row).column(7)).value();
		if (content.indexOf("e") == -1 && content.indexOf("X") == -1) {
		    withoutTakstolPackNotFound = false;
		}
	    }

	}
	assertEquals(false, withoutTakstolPackNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(7)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemPakkingTakstol").click();

	DialogFixture articleView = WindowFinder.findDialog("ArticlePackageView").using(dialogFixture.robot);
	JTableFixture tableFixtureArticles = articleView.table("TableArticles");
	int rows = tableFixtureArticles.rowCount();
	for (int i = 0; i < rows; i++) {
	    String test = tableFixtureArticles.cell(row(i).column(1)).value();
	    if ("false".equalsIgnoreCase(test)) {
		tableFixtureArticles.cell(row(i).column(1)).click();
	    }
	}

	articleView.button("ButtonOk").click();

	assertEquals(true, tableFixture.cell(row(row).column(7)).value().indexOf("X") != -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetTakstolNotPacked() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean withTakstolPackNotFound = true;
	String content;
	while (withTakstolPackNotFound) {
	    row++;

	    content = tableFixture.cell(row(row).column(7)).value();
	    if (content.indexOf("e") == -1 && content.indexOf("X") != -1) {
		withTakstolPackNotFound = false;
	    }

	}
	assertEquals(false, withTakstolPackNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(7)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemPakkingTakstol").click();

	DialogFixture articleView = WindowFinder.findDialog("ArticlePackageView").withTimeout(30000).using(dialogFixture.robot);
	JTableFixture tableFixtureArticles = articleView.table("TableArticles");
	int rows = tableFixtureArticles.rowCount();
	for (int i = 0; i < rows; i++) {
	    String test = tableFixtureArticles.cell(row(i).column(1)).value();
	    if ("true".equalsIgnoreCase(test)) {
		tableFixtureArticles.cell(row(i).column(1)).click();
	    }
	}

	articleView.button("ButtonOk").click();
	assertEquals(true, tableFixture.cell(row(row).column(7)).value().indexOf("X") == -1);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSearchOrder() {

	dialogFixture.button("SearchOrder").click();

	DialogFixture editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.textBox("OrderNr").enterText("34558");
	editDialog.button("EditSearchOrder").click();

    }

    @Test
    public void testShowProductionDate() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());
	assertEquals("Prod.dato", tableFixture.target.getColumnName(2));
    }

    @Test
    public void testShowRestAmount() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());
	assertEquals("Rest", tableFixture.target.getColumnName(11));
    }

    @Test
    public void testSetProcentFinished() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = -1;
	boolean orderNotFound = true;

	Transportable transportable = null;

	ProcentDone procentDone;
	while (orderNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);

	    if (transportable instanceof Order) {
		// content = tableFixture.cell(row(row).column(12)).contents();
		procentDone = (ProcentDone) tableFixture.target.getValueAt(row, 12);
		if (procentDone == null || procentDone.getProcent() == 0) {
		    orderNotFound = false;
		}

	    }

	}
	assertEquals(false, orderNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	((JXTable) tableFixture.target).scrollColumnToVisible(12);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(12)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemSetProcent").click();

	DialogFixture procentdialog = WindowFinder.findDialog("EditProcentDoneView").using(dialogFixture.robot);

	procentdialog.textBox("TextFieldProcent").enterText("30");
	procentdialog.textBox("TextAreaProcentDoneComment").enterText("kommentar");
	procentdialog.button("ButtonOk").click();

	tableFixture.cell(row(row).column(12)).click();
	procentDone = (ProcentDone) tableFixture.target.getValueAt(row, 12);
	assertNotNull(procentDone);
	assertEquals(Integer.valueOf(30), procentDone.getProcent());
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetProcentFinished200() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = 0;
	boolean orderNotFound = true;

	Transportable transportable = null;
	ProcentDone procentDone;
	while (orderNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);

	    if (transportable instanceof Order) {
		// content = tableFixture.cell(row(row).column(12)).contents();
		procentDone = (ProcentDone) tableFixture.target.getValueAt(row, 12);
		if (procentDone == null || procentDone.getProcent() == 0) {
		    orderNotFound = false;
		}

	    }

	}
	assertEquals(false, orderNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	((JXTable) tableFixture.target).scrollColumnToVisible(12);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(12)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemSetProcent").click();

	DialogFixture procentdialog = WindowFinder.findDialog("EditProcentDoneView").using(dialogFixture.robot);

	procentdialog.textBox("TextFieldProcent").enterText("200");
	procentdialog.textBox("TextAreaProcentDoneComment").enterText("kommentar");
	procentdialog.button("ButtonOk").click();

	JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().using(dialogFixture.robot);
	optionPane.buttonWithText("OK").click();

	procentdialog.textBox("TextFieldProcent").deleteText();
	procentdialog.textBox("TextFieldProcent").enterText("40");
	procentdialog.button("ButtonOk").click();

	procentDone = (ProcentDone) tableFixture.target.getValueAt(row, 12);
	assertNotNull(procentDone);
	assertEquals(Integer.valueOf(40), procentDone.getProcent());
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetProcentFinishedSameWeek() {
	dialogFixture.show();
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = 0;
	boolean orderNotFound = true;

	Transportable transportable = null;
	ProcentDone procentDone;
	while (orderNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);

	    if (transportable instanceof Order) {
		// content = tableFixture.cell(row(row).column(12)).contents();
		procentDone = (ProcentDone) tableFixture.target.getValueAt(row, 12);
		if (procentDone == null || procentDone.getProcent() == 0) {
		    orderNotFound = false;
		}

	    }

	}
	assertEquals(false, orderNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	((JXTable) tableFixture.target).scrollColumnToVisible(12);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(12)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemSetProcent").click();

	DialogFixture procentdialog = WindowFinder.findDialog("EditProcentDoneView").using(dialogFixture.robot);

	procentdialog.textBox("TextFieldProcent").enterText("30");
	procentdialog.textBox("TextAreaProcentDoneComment").enterText("kommentar");
	procentdialog.button("ButtonOk").click();

	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(12)).rightClick();
	popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName("PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemSetProcent").click();

	procentdialog = WindowFinder.findDialog("EditProcentDoneView").using(dialogFixture.robot);

	procentdialog.textBox("TextFieldProcent").deleteText();
	procentdialog.textBox("TextFieldProcent").enterText("40");
	procentdialog.button("ButtonOk").click();

	JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().using(dialogFixture.robot);
	optionPane.buttonWithText("Ja").click();

	procentDone = (ProcentDone) tableFixture.target.getValueAt(row, 12);
	assertNotNull(procentDone);
	assertEquals(Integer.valueOf(40), procentDone.getProcent());
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testSetProcentFinishedLessProcent() {
	dialogFixture.requireVisible();
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

	int row = 0;
	boolean orderNotFound = true;

	Transportable transportable = null;
	ProcentDone procentDone;
	while (orderNotFound) {
	    row++;
	    transportable = ((ProductionOverviewTableModel) tableFixture.target.getModel()).getObjectAtRow(row);

	    if (transportable instanceof Order) {
		// content = tableFixture.cell(row(row).column(12)).contents();
		procentDone = (ProcentDone) tableFixture.target.getValueAt(row, 12);
		if (procentDone == null || procentDone.getProcent() == 0) {
		    orderNotFound = false;
		}

	    }

	}
	assertEquals(false, orderNotFound);
	((JXTable) tableFixture.target).scrollRowToVisible(row);
	((JXTable) tableFixture.target).scrollColumnToVisible(12);
	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(12)).rightClick();
	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemSetProcent").click();

	DialogFixture procentdialog = WindowFinder.findDialog("EditProcentDoneView").using(dialogFixture.robot);

	procentdialog.textBox("TextFieldProcent").enterText("30");
	procentdialog.textBox("TextAreaProcentDoneComment").enterText("kommentar");
	procentdialog.button("ButtonOk").click();

	tableFixture.cell(row(row).column(2)).click();
	tableFixture.cell(row(row).column(12)).rightClick();
	popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName("PopupMenuProduction"));
	popupMenuFixture.menuItem("MenuItemSetProcent").click();

	procentdialog = WindowFinder.findDialog("EditProcentDoneView").using(dialogFixture.robot);

	procentdialog.comboBox("ComboBoxWeek").selectItem(Util.getCurrentWeek() + 1);
	procentdialog.textBox("TextFieldProcent").deleteText();
	procentdialog.textBox("TextFieldProcent").enterText("20");
	procentdialog.button("ButtonOk").click();

	JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().using(dialogFixture.robot);
	optionPane.buttonWithText("Ja").click();

	procentDone = (ProcentDone) tableFixture.target.getValueAt(row, 12);
	assertNotNull(procentDone);
	assertEquals(Integer.valueOf(20), procentDone.getProcent());
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testNotShowProduced() {
	dialogFixture.show();
	int rowCount = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName()).target.getRowCount();
	dialogFixture.checkBox("CheckBoxFilter").uncheck();
	int filterRowCount = dialogFixture.table(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName()).target.getRowCount();
	assertEquals(false, rowCount == filterRowCount);
    }
}
