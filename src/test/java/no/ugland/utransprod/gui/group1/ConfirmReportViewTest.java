package no.ugland.utransprod.gui.group1;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ConfirmReportView;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ConfirmReportViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.util.JYearChooserFinder;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.core.ComponentFinder;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTabbedPaneFixture;
import org.fest.swing.timing.Pause;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.toedter.calendar.JYearChooser;

@Category(GUITests.class)
public class ConfirmReportViewTest {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    // LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private DialogFixture dialogFixture;
    private ConfirmReportViewHandler viewHandler;

    @Mock
    private Login login;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private DeviationOverviewViewFactory deviationOverviewViewFactory;
    @Mock
    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    @Mock
    private OrderManager orderManager;
    @Mock
    private ProductAreaManager productAreaManager;
    private ProductAreaGroupManager productAreaGroupManager;
    @Mock
    private ApplicationParamManager applicationParamManager;

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
	productAreaGroupManager = (ProductAreaGroupManager) ModelUtil.getBean(ProductAreaGroupManager.MANAGER_NAME);

	Util.setProductAreaGroupManager(productAreaGroupManager);
	ApplicationParamUtil.setApplicationParamManger(applicationParamManager);
	when(managerRepository.getOrderManager()).thenReturn(orderManager);
	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
	List<Order> orderList = new ArrayList<Order>();
	Order order = new Order();
	orderList.add(order);
	when(orderManager.findByConfirmWeekProductAreaGroup(anyInt(), anyInt(), anyInt(), (ProductAreaGroup) anyObject())).thenReturn(orderList);

	OrderViewHandler orderViewHandler = new OrderViewHandler(login, managerRepository, deviationOverviewViewFactory, deviationViewHandlerFactory,
		true);

	viewHandler = new ConfirmReportViewHandler(orderViewHandler, managerRepository);
	final ConfirmReportView view = new ConfirmReportView(viewHandler);

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
    public void testShow() {
	dialogFixture.requireVisible();

	ComponentFinder finder = dialogFixture.robot.finder();
	finder.find(new JYearChooserFinder("YearChooser"));

	dialogFixture.comboBox("ComboBoxWeekFrom").requireVisible();
	dialogFixture.comboBox("ComboBoxWeekTo").requireVisible();
	dialogFixture.button("ButtonGenerateReport").requireVisible();

	JTabbedPaneFixture tabbedPane = dialogFixture.tabbedPane("TabbedPaneResult").requireVisible();
	dialogFixture.table("TableResult").requireVisible();
	tabbedPane.selectTab(1);
	dialogFixture.table("TableOrders");
	dialogFixture.button("ButtonCancel");
    }

    @Test
    public void testGenerateReport() throws Exception {
	dialogFixture.requireVisible();

	ComponentFinder finder = dialogFixture.robot.finder();
	JYearChooser yearChooser = finder.find(new JYearChooserFinder("YearChooser"));
	yearChooser.setYear(2008);

	dialogFixture.comboBox("ComboBoxWeekFrom").requireVisible();
	dialogFixture.comboBox("ComboBoxWeekFrom").target.setSelectedItem(40);
	dialogFixture.comboBox("ComboBoxWeekTo").requireVisible();
	dialogFixture.comboBox("ComboBoxWeekTo").target.setSelectedItem(41);
	dialogFixture.comboBox("ComboBoxProductAreaGroup").target.setSelectedItem(1);

	dialogFixture.button("ButtonGenerateReport").click();
	Pause.pause(1000);
	assertEquals(true, dialogFixture.table("TableResult").target.getRowCount() != 0);
    }

}
