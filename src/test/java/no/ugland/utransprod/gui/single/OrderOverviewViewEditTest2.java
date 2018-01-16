package no.ugland.utransprod.gui.single;

import static junit.framework.Assert.assertEquals;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.DeviationOverviewView2;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderOverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler2;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactoryImpl;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.CustomerManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTabbedPaneFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;

public class OrderOverviewViewEditTest2 {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private OrderManager orderManager;
    private DialogFixture dialogFixture;
    @Mock
    private Login login;
    @Mock
    private ManagerRepository managerRepository;

    @Before
    public void beforeClass() {
	MockitoAnnotations.initMocks(this);

	orderManager = (OrderManager) ModelUtil.getBean("orderManager");
	final JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil.getBean(JobFunctionManager.MANAGER_NAME);
	when(managerRepository.getJobFunctionManager()).thenReturn(jobFunctionManager);
	final ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);
	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
	when(managerRepository.getOrderManager()).thenReturn(orderManager);
	final SupplierManager supplierManager = (SupplierManager) ModelUtil.getBean(SupplierManager.MANAGER_NAME);
	when(managerRepository.getSupplierManager()).thenReturn(supplierManager);
	final DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil.getBean(DeviationStatusManager.MANAGER_NAME);
	when(managerRepository.getDeviationStatusManager()).thenReturn(deviationStatusManager);
	final ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME);
	when(managerRepository.getApplicationUserManager()).thenReturn(applicationUserManager);
	final DeviationManager deviationManager = (DeviationManager) ModelUtil.getBean(DeviationManager.MANAGER_NAME);
	when(managerRepository.getDeviationManager()).thenReturn(deviationManager);

	final ApplicationUser applicationUser = new ApplicationUser();

	final ProductArea productArea = productAreaManager.findByName("Garasje villa");
	ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil.getBean(ProductAreaGroupManager.MANAGER_NAME);
	ProductAreaGroup productAreaGroup = productAreaGroupManager.findByName("Garasje");
	productArea.setProductAreaGroup(productAreaGroup);
	applicationUser.setProductArea(productArea);
	applicationUser.setUserName("username");
	applicationUser.setGroupUser("Nei");
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
	final DeviationOverviewViewFactory deviationOverviewViewFactory = new DeviationOverviewViewFactory() {

	    public DeviationOverviewView2 create(DeviationViewHandler2 deviationViewHandler, boolean useSearchButton, Order aOrder, boolean doSeeAll,
		    boolean forOrderInfo, boolean isForRegisterNew, Deviation notDisplayDeviation, boolean isDeviationTableEditable) {
		return new DeviationOverviewView2(null, deviationViewHandler, false, null, false, true, true, null, true,managerRepository);
	    }
	};

	PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(login, managerRepository);
	final DeviationViewHandlerFactory deviationViewHandlerFactory = new DeviationViewHandlerFactoryImpl(login, managerRepository,
		preventiveActionViewHandler);

	OrderViewHandler orderViewHandler = new OrderViewHandler(login, managerRepository, deviationOverviewViewFactory, deviationViewHandlerFactory,
		false, null);
	final OrderOverviewView view = new OrderOverviewView(orderViewHandler);

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
    public void afterMethod() {
	dialogFixture.cleanUp();

	CustomerManager customerManager = (CustomerManager) ModelUtil.getBean("customerManager");
	Order order = orderManager.findByOrderNr("100100100");
	if (order != null) {
	    orderManager.removeOrder(order);
	}

	Customer customer = customerManager.findByCustomerNr(100);
	if (customer != null) {
	    customerManager.removeCustomer(customer);
	}

    }

    @Test
    public void showOrderlinesFromVisma() {

	dialogFixture.button("SearchOrder").click();

	DialogFixture editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.textBox("OrderNr").enterText("43860");
	editDialog.button("EditSearchOrder").click();

	JTableFixture tableFixture = dialogFixture.table("TableOrder");
	int count = tableFixture.target.getModel().getRowCount();
	assertEquals(true, count != 0);

	tableFixture.cell(row(0).column(1)).doubleClick();

	editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.checkBox("CheckBoxLock").uncheck();

	JTabbedPaneFixture tabbedPane = editDialog.tabbedPane("TabbedPaneDeviationArticle");
	tabbedPane.selectTab(1);
	editDialog.button("ButtonShowOrdln").click();
	DialogFixture showVismaDialog = WindowFinder.findDialog("OrdlnView").using(dialogFixture.robot);

	assertEquals(3, showVismaDialog.table("TableOrdln").target.getRowCount());
	showVismaDialog.button("ButtonCancelOrdlnView").click();

    }

    @Test
    public void showOwnDeviation() throws Exception {
	dialogFixture.show();

	dialogFixture.button("SearchOrder").click();

	DialogFixture editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.textBox("OrderNr").enterText("24431");
	editDialog.button("EditSearchOrder").click();

	JTableFixture tableFixture = dialogFixture.table("TableOrder");

	tableFixture.cell(row(0).column(1)).doubleClick();

	editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	// JTableFixture tableFixture = dialogFixture.table("TableOrder");

	tableFixture.cell(row(0).column(1)).doubleClick();

	editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);
	editDialog.checkBox("CheckBoxLock").uncheck();
	String orderNr = editDialog.textBox("OrderNr").target.getText();

	JTableFixture tableDevition = editDialog.table("TableDeviation");
	if (tableDevition.target.getRowCount() != 0) {
	    tableDevition.cell(row(0).column(0)).doubleClick();

	    DialogFixture deviationDialog = WindowFinder.findDialog("EditDeviationView").withTimeout(20000).using(dialogFixture.robot);
	    assertEquals(orderNr, deviationDialog.textBox("TextFieldOrderNr").target.getText());
	}
    }

    @Test
    public void showPaidDate() {
	dialogFixture.show();

	JTableFixture tableFixture = dialogFixture.table("TableOrder");
	int count = tableFixture.target.getModel().getRowCount();
	assertEquals(true, count != 0);

	dialogFixture.button("AddOrder").click();

	new DialogFixture(dialogFixture.robot, (JDialog) dialogFixture.robot.finder().findByName("EditOrderView"));

	dialogFixture.robot.finder().findByName("DateChooserPaid");

    }

    @Test
    public void showProductArea() {
	dialogFixture.show();
	dialogFixture.button("AddOrder").click();

	DialogFixture editDialog = new DialogFixture(dialogFixture.robot, (JDialog) dialogFixture.robot.finder().findByName("EditOrderView"));
	editDialog.comboBox("ComboBoxProductArea").requireVisible();
    }
}
