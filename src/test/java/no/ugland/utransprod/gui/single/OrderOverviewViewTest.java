package no.ugland.utransprod.gui.single;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderOverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
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
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JFileChooserFinder;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JFileChooserFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.fixture.JTabbedPaneFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;

public class OrderOverviewViewTest {
    private DialogFixture dialogFixture;
    @Mock
    private Login login;
    @Mock
    private ManagerRepository managerRepository;
    private OrderManager orderManager;

    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Before
    public void beforeClass() {
	MockitoAnnotations.initMocks(this);
	JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil.getBean(JobFunctionManager.MANAGER_NAME);
	when(managerRepository.getJobFunctionManager()).thenReturn(jobFunctionManager);
	ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);
	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);

	PreventiveActionManager preventiveActionManager = (PreventiveActionManager) ModelUtil.getBean(PreventiveActionManager.MANAGER_NAME);
	when(managerRepository.getPreventiveActionManager()).thenReturn(preventiveActionManager);
	orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
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

	    public DeviationOverviewView create(DeviationViewHandler deviationViewHandler, boolean useSearchButton, Order aOrder, boolean doSeeAll,
		    boolean forOrderInfo, boolean isForRegisterNew, Deviation notDisplayDeviation, boolean isDeviationTableEditable) {
		return new DeviationOverviewView(null, deviationViewHandler, false, null, false, true, true, null, true);
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
    public void showMaxTrossHeight() {
	dialogFixture.button("AddOrder").click();

	DialogFixture editDialog = new DialogFixture(dialogFixture.robot, (JDialog) dialogFixture.robot.finder().findByName("EditOrderView"));
	editDialog.textBox("TextFieldMaxTrossHeight").requireVisible();
    }

    @Test
    public void openWindow() throws Exception {

	dialogFixture.requireVisible();
    }

    @Test
    public void articleStatistics() {
	dialogFixture.show();
	dialogFixture.button("ArticleStatistics").click();
	Component comp = dialogFixture.robot.finder().findByName("SearchAttributeView");
	assertNotNull(comp);
    }

    @Test
    public void importCutting() {
	Util.setFileDirectory("c:\\java\\projects\\ProTrans\\ProTrans\\src\\test\\resources");
	dialogFixture.show();

	dialogFixture.button("SearchOrder").click();

	DialogFixture editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.textBox("OrderNr").enterText("49838");
	editDialog.button("EditSearchOrder").click();

	JTableFixture tableFixture = dialogFixture.table("TableOrder");
	int count = tableFixture.target.getModel().getRowCount();
	assertEquals(true, count != 0);

	tableFixture.cell(row(0).column(2)).doubleClick();

	editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(60000).using(dialogFixture.robot);

	JTabbedPaneFixture tabbedPane = editDialog.tabbedPane("TabbedPaneDeviationArticle");
	tabbedPane.selectTab(2);

	String cuttingFileName = editDialog.textBox("TextFieldCuttingFile").target.getText();
	editDialog.checkBox("CheckBoxLock").uncheck();

	editDialog.button("ButtonImportCuttingFile").click();

	if (cuttingFileName != null && cuttingFileName.length() != 0) {
	    JOptionPaneFinder.findOptionPane().using(dialogFixture.robot).buttonWithText("Ja").click();
	}

	JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser("fileChooser").withTimeout(10000).using(dialogFixture.robot);
	fileChooser.fileNameTextBox().enterText("test_cutting.boq");
	fileChooser.approveButton().click();

	JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().withTimeout(40000).using(dialogFixture.robot);
	optionPane.buttonWithText("OK").click();

	cuttingFileName = editDialog.textBox("TextFieldCuttingFile").target.getText();
	assertEquals("0950435", cuttingFileName);

	Order order = orderManager.findByOrderNr("49838");
	assertNotNull(order);
	assertNotNull(order.getCutting());
    }

    @Test
    public void searchSearch() {
	dialogFixture.show();
	dialogFixture.button("SearchOrder").click();

	DialogFixture editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.textBox("OrderNr").enterText("34558");
	editDialog.button("EditSearchOrder").click();

	JTableFixture tableFixture = dialogFixture.table("TableOrder");
	int count = tableFixture.target.getModel().getRowCount();
	assertEquals(true, count != 0);

	tableFixture.cell(row(0).column(1)).doubleClick();

	editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.button("EditCancelOrder").click();

	dialogFixture.button("SearchOrder").requireEnabled();
    }

    @Test
    public void showAttachments() {
	Util.setFileDirectory("c:\\java\\projects\\ProTrans\\src\\test\\resources");
	dialogFixture.show();

	dialogFixture.button("SearchOrder").click();

	DialogFixture editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.textBox("OrderNr").enterText("49838");
	editDialog.button("EditSearchOrder").click();

	JTableFixture tableFixture = dialogFixture.table("TableOrder");
	int count = tableFixture.target.getModel().getRowCount();
	assertEquals(true, count != 0);

	tableFixture.cell(row(0).column(2)).doubleClick();

	editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	JTabbedPaneFixture tabbedPane = editDialog.tabbedPane("TabbedPaneDeviationArticle");
	tabbedPane.selectTab(3);

	editDialog.list("ListAttachments").requireVisible();
    }

    @Test
    public void showCuttingFile() {
	Util.setFileDirectory("c:\\java\\projects\\ProTrans\\ProTrans\\src\\test\\resources");
	dialogFixture.show();

	dialogFixture.button("SearchOrder").click();

	DialogFixture editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	editDialog.textBox("OrderNr").enterText("49838");
	editDialog.button("EditSearchOrder").click();

	JTableFixture tableFixture = dialogFixture.table("TableOrder");
	int count = tableFixture.target.getModel().getRowCount();
	assertEquals(true, count != 0);

	tableFixture.cell(row(0).column(2)).doubleClick();

	editDialog = WindowFinder.findDialog("EditOrderView").withTimeout(20000).using(dialogFixture.robot);

	JTabbedPaneFixture tabbedPane = editDialog.tabbedPane("TabbedPaneDeviationArticle");
	tabbedPane.selectTab(2);

	String cuttingFileName = editDialog.textBox("TextFieldCuttingFile").target.getText();
	editDialog.checkBox("CheckBoxLock").uncheck();

	editDialog.button("ButtonOpenCuttingFile").click();

	if (cuttingFileName == null || cuttingFileName.length() == 0) {
	    JOptionPaneFinder.findOptionPane().using(dialogFixture.robot).buttonWithText("Ok").click();
	    JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser("fileChooser").withTimeout(10000).using(dialogFixture.robot);
	    fileChooser.fileNameTextBox().enterText("test_cutting.boq");
	    fileChooser.approveButton().click();

	    JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().withTimeout(40000).using(dialogFixture.robot);
	    optionPane.buttonWithText("OK").click();

	    cuttingFileName = editDialog.textBox("TextFieldCuttingFile").target.getText();
	    assertEquals("0950435", cuttingFileName);
	    editDialog.button("ButtonOpenCuttingFile").click();
	}

	DialogFixture showFileDialog = WindowFinder.findDialog("FileView").withTimeout(20000).using(dialogFixture.robot);

	showFileDialog
		.textBox("TextAreaFile")
		.requireText(
			"VERSION :110\nPRO_SIGN:JW\nPRO_ID  :0950435\nKAP     :  1:  0:  0:  1:C30   :  48: 223: 4711: 4668:   103225:  50.000: 223.002:   0.000: 136.399:   0.000: 136.399: 236.248:   0.002:4710.888:   0.000:4710.888: 111.500:4710.888: 111.500:4710.888: 223.000:OVERGURT                      :  36:O1    : 5100:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:T2/T3/T4/T5/T6/T7/T8                    :  21:  15:2:*1:\nPOSTCUT :  2:  8: 4710.89:    0.00: 4710.89:  223.00:   50.00:  223.00:    0.00:  136.40:  236.25:    0.00:  556.34:    0.00:  576.32:   34.62:  636.29:    0.00:\nKAP     :  2:  0:  0:  1:C30   :  48: 223: 4711: 4668:   103225:  50.000: 223.002:   0.000: 136.399:   0.000: 136.399: 236.248:   0.002:4710.888:   0.000:4710.888: 111.500:4710.888: 111.500:4710.888: 223.000:OVERGURT                      :  12:O2    : 5100:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:T1/T9/T10                               :   6:   6:2:*1:\nKAP     :  3:  0:  0:  3:C30   :  48: 223: 3696: 3696:    82421:   0.000: 223.000:   0.000: 111.500:   0.000: 111.500:   0.000:   0.000:3696.000:   0.001:3696.000: 111.501:3696.000: 111.501:3696.000: 223.000:OVERGURT                      :   2:O3    : 3900:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:B3                                      :   0:   0:2:*1:\nPOSTCUT :  4: 11:   50.00:  223.00:    0.00:  136.40:  236.25:    0.00:  556.34:    0.00:  584.63:   49.01:  669.51:    0.00: 2784.91:    0.00: 2825.11:   69.64: 2885.73:   34.64: 2970.54:  181.52: 2898.69:  223.00:\nKAP     :  4:  0:  0:  1:C30   :  48: 223: 2971: 2887:    63315:  50.000: 223.001:   0.000: 136.399:   0.000: 136.399: 236.247:   0.001:2865.737:   0.000:2970.536: 181.517:2970.536: 181.517:2898.686: 223.000:OVERGURT                      :   6:O4    : 3300:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo1                                     :   0:   0:2:*1:\nPOSTCUT :  5: 11:   50.00:  223.00:    0.00:  136.40:  236.25:    0.00:  548.25:    0.00:  578.02:   51.56:  667.33:    0.00: 2784.91:    0.00: 2825.11:   69.64: 2885.73:   34.64: 2970.54:  181.52: 2898.69:  223.00:\nKAP     :  5:  0:  0:  1:C30   :  48: 223: 2971: 2887:    63315:  50.000: 223.001:   0.000: 136.399:   0.000: 136.399: 236.247:   0.001:2865.737:   0.000:2970.536: 181.517:2970.536: 181.517:2898.686: 223.000:OVERGURT                      :   3:O5    : 3300:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo2                                     :   0:   0:2:*1:\nPOSTCUT :  6: 11:   50.00:  223.00:    0.00:  136.40:  236.25:    0.00:  492.83:    0.00:  536.45:   75.56:  667.33:    0.00: 2784.91:    0.00: 2825.11:   69.64: 2885.73:   34.64: 2970.54:  181.52: 2898.69:  223.00:\nKAP     :  6:  0:  0:  1:C30   :  48: 223: 2971: 2887:    63315:  50.000: 223.001:   0.000: 136.399:   0.000: 136.399: 236.247:   0.001:2865.737:   0.000:2970.536: 181.517:2970.536: 181.517:2898.686: 223.000:OVERGURT                      :   3:O6    : 3300:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo6                                     :   0:   0:2:*1:\nPOSTCUT :  7:  9:  386.25:    0.00: 2390.80:    0.00: 2431.01:   69.64: 2491.63:   34.64: 2576.43:  181.52: 2504.58:  223.00:  115.43:  223.00:  254.02:  142.98:  225.16:   93.00:\nKAP     :  7:  0:  0:  1:C30   :  48: 223: 2576: 2343:    52048:   0.000: 223.001: 193.123: 111.501: 193.123: 111.501: 386.247:   0.001:2471.634:   0.000:2576.433: 181.517:2576.433: 181.517:2504.583: 223.000:OVERGURT                      :   1:O7    : 2700:1:1:4:  0:300:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo3/fo5                                 :   0:   2:2:*1:\nKAP     :  7:  0:  0:  1:C30   :  48: 223: 2576: 2343:    52048:   0.000: 223.001: 193.123: 111.501: 193.123: 111.501: 386.247:   0.001:2471.634:   0.000:2576.433: 181.517:2576.433: 181.517:2504.583: 223.000:OVERGURT                      :   1:O7/   : 2700:1:1:4:300:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo3/fo5                                 :   0:   2:2:*1:\nPOSTCUT :  8:  7:  128.75:  223.00:    0.00:    0.00: 1759.83:    0.00: 1800.04:   69.64: 1860.66:   34.64: 1945.46:  181.52: 1873.61:  223.00:\nKAP     :  8:  0:  0:  1:C30   :  48: 223: 1945: 1841:    40848: 128.749: 223.001:  64.375: 111.501:  64.375: 111.501:   0.000:   0.001:1840.658:   0.000:1945.458: 181.517:1945.458: 181.517:1873.607: 223.000:OVERGURT                      :   2:O8    : 2100:1:1:4:450:450:1:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo4                                     :   0:   0:2:*1:\nPOSTCUT :  9:  7:   71.79:  223.00:    0.00:  181.55:   84.81:   34.66:  154.09:   74.66:  197.19:    0.00: 1344.68:    0.00: 1215.93:  223.00:\nKAP     :  9:  0:  0:  2:C30   :  48: 223: 1345: 1240:    27450:  71.792: 223.001:   0.000: 181.553:   0.000: 181.553: 104.818:   0.001:1344.675:   0.000:1280.302: 111.500:1280.302: 111.500:1215.928: 223.000:OVERGURT                      :   5:O9    : 1500:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:T4/T6                                   :   0:   5:2:*1:\nKAP     : 10:  0:  0:  1:C30   :  48: 223: 1127: 1036:    23103:  91.040: 223.000:  45.520: 111.500:  45.520: 111.500:   0.000:   0.000:1036.012:   0.000:1081.532: 111.500:1081.532: 111.500:1127.052: 223.000:OVERGURT                      :   2:O10   : 1500:2:1:4:450:450:1:   0:5:8:450:450:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo7                                     :   0:   0:2:*1:\n");
	showFileDialog.button("ButtonOk");
    }

}
