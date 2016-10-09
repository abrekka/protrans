package no.ugland.utransprod.gui.packageproduction.manuelt;

import static junit.framework.Assert.assertEquals;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.google.common.collect.Multimap;

import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MainPackageView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.checker.LagerProductionStatusChecker;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.MainPackageViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.MainPackageVManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SumOrderReadyVManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;

/**
 * @author atle.brekka
 */
@Category(ManuellTest.class)
public class MainPackageViewTest {
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
    @Mock
    private Login login;
    @Mock
    private DeviationOverviewViewFactory deviationOverviewViewFactory;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    @Mock
    private VismaFileCreator vismaFileCreator;
    @Mock
    private OrderViewHandlerFactory orderViewHandlerFactory;

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
	ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);
	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
	MainPackageVManager mainPackageVManager = (MainPackageVManager) ModelUtil.getBean(MainPackageVManager.MANAGER_NAME);
	when(managerRepository.getMainPackageVManager()).thenReturn(mainPackageVManager);
	SumOrderReadyVManager sumOrderReadyVManager = (SumOrderReadyVManager) ModelUtil.getBean(SumOrderReadyVManager.MANAGER_NAME);
	when(managerRepository.getSumOrderReadyVManager()).thenReturn(sumOrderReadyVManager);
	BudgetManager budgetManager = (BudgetManager) ModelUtil.getBean(BudgetManager.MANAGER_NAME);
	when(managerRepository.getBudgetManager()).thenReturn(budgetManager);
	PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean(PostShipmentManager.MANAGER_NAME);
	when(managerRepository.getPostShipmentManager()).thenReturn(postShipmentManager);

	final OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);

	when(managerRepository.getOrderManager()).thenReturn(orderManager);

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

	final ColliManager colliManager = (ColliManager) ModelUtil.getBean(ColliManager.MANAGER_NAME);
	when(managerRepository.getColliManager()).thenReturn(colliManager);
	final ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME);
	when(managerRepository.getApplicationUserManager()).thenReturn(applicationUserManager);
	final ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil.getBean(ArticleTypeManager.MANAGER_NAME);
	when(managerRepository.getArticleTypeManager()).thenReturn(articleTypeManager);
	final OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);
	when(managerRepository.getOrderLineManager()).thenReturn(orderLineManager);
	final AttributeManager attributeManager = (AttributeManager) ModelUtil.getBean(AttributeManager.MANAGER_NAME);

	final OrderViewHandler orderViewHandler = new OrderViewHandler(login, managerRepository, deviationOverviewViewFactory,
		deviationViewHandlerFactory, false, null);
	Multimap<String, String> colliSetup = ApplicationParamUtil.getColliSetup();

	Map<String, StatusCheckerInterface<OrderLine>> statusChekers = new Hashtable<String, StatusCheckerInterface<OrderLine>>();

	String veggArticleName = ApplicationParamUtil.findParamByName("vegg_artikkel");
	String attributeName = ApplicationParamUtil.findParamByName("front_attributt_navn");
	String attributeValue = ApplicationParamUtil.findParamByName("front_attributt_verdi");
	String frontArticleName = ApplicationParamUtil.findParamByName("front_artikkel");

	statusChekers.put(veggArticleName, new LagerProductionStatusChecker(veggArticleName, attributeName, attributeValue));
	statusChekers.put(frontArticleName, new LagerProductionStatusChecker(frontArticleName, attributeName, attributeValue));
	final ApplicationUser applicationUser = new ApplicationUser();
	ProductArea productArea = new ProductArea();
	ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil.getBean(ProductAreaGroupManager.MANAGER_NAME);
	ProductAreaGroup productAreaGroup = productAreaGroupManager.findByName("Garasje");
	productArea.setProductAreaGroup(productAreaGroup);
	applicationUser.setProductArea(productArea);
	applicationUser.setUserName("username");
	applicationUser.setGroupUser("Nei");
	when(login.getApplicationUser()).thenReturn(applicationUser);

	MainPackageViewHandler mainPackageViewHandler = new MainPackageViewHandler(vismaFileCreator, orderViewHandlerFactory, login,
		managerRepository, deviationViewHandlerFactory, colliSetup, statusChekers);
	final MainPackageView view = new MainPackageView(mainPackageViewHandler);

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
    public void testShowNumberOf() {
	JTableFixture tableFixture = dialogFixture.table("TableOrderLines");
	assertEquals("#", tableFixture.target.getColumnName(1));
    }

    @Test
    public void testSetColliHeight() {
	dialogFixture.checkBox("CheckBoxShowPackaged").uncheck();
	JTableFixture tableFixture = dialogFixture.table("TableOrders");
	tableFixture.cell(row(0).column(0)).select();
	if (dialogFixture.checkBox("CheckBoxReady").target.isSelected()) {
	    dialogFixture.checkBox("CheckBoxReady").uncheck();
	}
	dialogFixture.checkBox("CheckBoxReady").check();

	DialogFixture editPackInitials = WindowFinder.findDialog("EditPackInitials").using(dialogFixture.robot);
	editPackInitials.comboBox("ComboBoxInitials1").selectItem(0);
	editPackInitials.textBox("TextFieldColliHeight").deleteText();
	editPackInitials.textBox("TextFieldColliHeight").enterText("200");
	editPackInitials.button("ButtonOk").click();

	dialogFixture.checkBox("CheckBoxReady").uncheck();
	dialogFixture.checkBox("CheckBoxReady").check();

	editPackInitials = WindowFinder.findDialog("EditPackInitials").using(dialogFixture.robot);
	editPackInitials.textBox("TextFieldColliHeight").requireText("200");
    }

    @Test
    public void testClickPostShipment() {
	JTableFixture tableFixture = dialogFixture.table("TablePostShipment");
	tableFixture.cell(row(0).column(0)).select();
    }

    @Test
    public void testAddArticleAddComment() {
	JTableFixture tableFixture = dialogFixture.table("TableOrders");
	tableFixture.cell(row(0).column(0)).select();
	dialogFixture.button("ButtonAddArticle").click();

	DialogFixture dialogArticle = WindowFinder.findDialog("ArticleTypeView").using(dialogFixture.robot);
	dialogArticle.list("ListArticles").selectItem(0);
	dialogArticle.button("ButtonOkArticle").click();

	JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().using(dialogFixture.robot);
	optionPane.textBox().enterText("1");
	optionPane.buttonWithText("OK").click();

	dialogFixture.button("AddComment").click();
	DialogFixture dialogComment = WindowFinder.findDialog("EditCommentView").using(dialogFixture.robot);
	dialogComment.textBox("TextAreaComment").enterText("test");
	dialogComment.button("ButtonCommentOk").click();

    }

    @Test
    public void testShowStarted() {
	dialogFixture.show(new Dimension(1010, 600));
	JTableFixture tableFixture = dialogFixture.table("TableOrderLines");
	assertEquals("Startet", tableFixture.target.getColumnName(4));
    }

    @Test
    public void testSetReady() {
	dialogFixture.show(new Dimension(1010, 600));
	dialogFixture.checkBox("CheckBoxShowPackaged").uncheck();
	JTableFixture tableFixture = dialogFixture.table("TableOrders");
	tableFixture.cell(row(0).column(0)).select();
	boolean isChecked = false;
	if (dialogFixture.checkBox("CheckBoxReady").target.isSelected()) {
	    isChecked = true;
	    dialogFixture.checkBox("CheckBoxReady").uncheck();
	} else {
	    dialogFixture.checkBox("CheckBoxReady").check();
	}

	if (!isChecked) {
	    DialogFixture editPackInitials = WindowFinder.findDialog("EditPackInitials").using(dialogFixture.robot);
	    editPackInitials.comboBox("ComboBoxInitials1").selectItem(0);
	    editPackInitials.textBox("TextFieldColliHeight").deleteText();
	    editPackInitials.textBox("TextFieldColliHeight").enterText("200");
	    editPackInitials.button("ButtonOk").click();
	}

	tableFixture.cell(row(1).column(0)).select();
	tableFixture.cell(row(0).column(0)).select();

	if (isChecked) {
	    dialogFixture.checkBox("CheckBoxReady").requireNotSelected();
	} else {
	    dialogFixture.checkBox("CheckBoxReady").requireSelected();
	}
    }

    @Test
    public void testPackOrderLine() {
	dialogFixture.show(new Dimension(1010, 600));
	dialogFixture.checkBox("CheckBoxShowPackaged").uncheck();

	JTableFixture tableFixtureOrder = dialogFixture.table("TableOrders");
	tableFixtureOrder.cell(row(0).column(0)).select();

	JTableFixture tableFixture = dialogFixture.table("TableOrderLines");
	tableFixture.cell(row(0).column(0)).select();
	tableFixture.cell(row(0).column(0)).doubleClick();

	DialogFixture optionDialog = WindowFinder.findDialog("Velg kolli").using(dialogFixture.robot);
	optionDialog.comboBox("ComboBoxOptions").selectItem(0);
	optionDialog.button("ButtonOk").click();
    }
}
