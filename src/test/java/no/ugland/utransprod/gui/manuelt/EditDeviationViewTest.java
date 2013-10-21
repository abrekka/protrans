package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.LoginImpl;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditDeviationView;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.UserRole;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.CustomerManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.fixture.JTabbedPaneFixture;
import org.hibernate.ObjectNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.toedter.calendar.JDateChooser;

/**
 * @author atle.brekka
 */
@Category(ManuellTest.class)
public class EditDeviationViewTest {
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static DeviationManager deviationManager;

	private DialogFixture dialogFixture;

	private static Order order;
	private static OrderManager orderManager = null;

	private static ProductArea productArea;

	@Mock
	private ManagerRepository managerRepository;

	@BeforeClass
	public static void setUpOrder() throws Exception {
		FailOnThreadViolationRepaintManager.install();
		orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		CustomerManager customerManager = (CustomerManager) ModelUtil
				.getBean("customerManager");
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean(ConstructionTypeManager.MANAGER_NAME);
		final ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean(ProductAreaManager.MANAGER_NAME);

		order = new Order();
		Set<OrderLine> orderLines = new HashSet<OrderLine>();
		OrderLine orderLine = new OrderLine();
		orderLine.setOrder(order);
		orderLine.setArticlePath("articlePath");
		orderLines.add(orderLine);
		order.setOrderLines(orderLines);
		order.setOrderNr("123456789");
		order.setDeliveryAddress("deliveryAddress");
		order.setPostalCode("1234");
		order.setPostOffice("postOffice");
		order.setOrderDate(Calendar.getInstance().getTime());
		Customer customer = customerManager.findByCustomerNr(1);
		order.setCustomer(customer);
		ConstructionType constructionType = constructionTypeManager
				.findByName("A1");
		order.setConstructionType(constructionType);
		productArea = productAreaManager.findByName("Garasje villa");
		order.setProductArea(productArea);
		orderManager.saveOrder(order);

	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		final ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean(ProductAreaManager.MANAGER_NAME);

		deviationManager = (DeviationManager) ModelUtil
				.getBean("deviationManager");
		final ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean("applicationUserManager");
		ApplicationUser user;
		user = applicationUserManager.login("avviktransport", "avviktransport");
		applicationUserManager.lazyLoad(user, new LazyLoadEnum[][] { {
				LazyLoadEnum.USER_ROLES, LazyLoadEnum.NONE } });
		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean("jobFunctionManager");
		if (user == null) {

			UserTypeManager userTypeManager = (UserTypeManager) ModelUtil
					.getBean("userTypeManager");
			JobFunction jobFunction = new JobFunction();
			jobFunction.setJobFunctionName("Transport");
			List<JobFunction> jobFunctions = jobFunctionManager
					.findByObject(jobFunction);
			jobFunction = jobFunctions.get(0);

			user = new ApplicationUser(null, "avviktransport",
					"avviktransport", "avviktransport", "avviktransport", null,
					"Nei", jobFunction, productArea, null);
			applicationUserManager.saveObject(user);

			UserType userType = new UserType();
			userType.setDescription("Avvik");
			List<UserType> userTypes = userTypeManager.findByObject(userType);
			userType = userTypes.get(0);

			Set<UserRole> userRoles = new HashSet<UserRole>();
			UserRole userRole = new UserRole(null, userType, user);
			userRoles.add(userRole);
			user.setUserRoles(userRoles);

			applicationUserManager.saveObject(user);
		}

		JobFunction jobFunction = user.getJobFunction();

		if (!jobFunction.getManager().equals(user)) {
			jobFunction.setManager(user);
			jobFunctionManager.saveObject(jobFunction);
		}

		Login login = new LoginImpl(user, user.getUserRoles().iterator().next()
				.getUserType());

		final PreventiveActionManager preventiveActionManager = (PreventiveActionManager) ModelUtil
				.getBean(PreventiveActionManager.MANAGER_NAME);
		final DeviationManager deviationManager = (DeviationManager) ModelUtil
				.getBean(DeviationManager.MANAGER_NAME);
		final OrderManager orderManager = (OrderManager) ModelUtil
				.getBean(OrderManager.MANAGER_NAME);
		final ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
				.getBean(ArticleTypeManager.MANAGER_NAME);
		final DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil
				.getBean(DeviationStatusManager.MANAGER_NAME);
		final SupplierManager supplierManager = (SupplierManager) ModelUtil
				.getBean(SupplierManager.MANAGER_NAME);

		when(managerRepository.getJobFunctionManager()).thenReturn(
				jobFunctionManager);
		when(managerRepository.getProductAreaManager()).thenReturn(
				productAreaManager);
		when(managerRepository.getDeviationStatusManager()).thenReturn(
				deviationStatusManager);
		when(managerRepository.getApplicationUserManager()).thenReturn(
				applicationUserManager);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		when(managerRepository.getDeviationManager()).thenReturn(
				deviationManager);
		when(managerRepository.getArticleTypeManager()).thenReturn(
				articleTypeManager);
		when(managerRepository.getSupplierManager())
				.thenReturn(supplierManager);
		when(managerRepository.getPreventiveActionManager()).thenReturn(
				preventiveActionManager);

		final PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(
				login, managerRepository);

		final DeviationViewHandler deviationViewHandler = new DeviationViewHandler(
				login, managerRepository, preventiveActionViewHandler, null,
				true, false, true, null, true);

		DeviationModel deviationModel = new DeviationModel(new Deviation(),
				false);
		final EditDeviationView editDeviationView = new EditDeviationView(
				false, deviationModel, deviationViewHandler, false, true);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(editDeviationView.buildPanel(window));
				dialog.pack();
				return dialog;
			}
		});
		dialogFixture = new DialogFixture(dialog);
		dialogFixture.show();

	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		dialogFixture.cleanUp();
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
				.getBean("postShipmentManager");

		Deviation deviation = new Deviation();
		deviation.setCustomerName("test");
		List<Deviation> list = deviationManager.findByObject(deviation);
		if (list != null) {
			for (Deviation dev : list) {
				deviationManager.removeObject(dev);
			}
		}
		deviation = new Deviation();
		deviation.setUserName("admin admin");
		list = deviationManager.findByObject(deviation);
		orderManager.lazyLoadOrder(order,
				new LazyLoadOrderEnum[] { LazyLoadOrderEnum.POST_SHIPMENTS });
		Set<PostShipment> postShipments = order.getPostShipments();
		if (postShipments != null) {
			for (PostShipment postShipment : postShipments) {
				order.removePostShipment(postShipment);
				orderManager.saveOrder(order);
				deviation = postShipment.getDeviation();
				try {
					postShipmentManager.refreshObject(postShipment);
					postShipmentManager.removePostShipment(postShipment);
				} catch (HibernateObjectRetrievalFailureException e) {
				} catch (ObjectNotFoundException onfex) {

				}
				deviationManager.removeObject(deviation);
			}
		}
	}

	@AfterClass
	public static void tearDownOrder() {
		orderManager.removeOrder(order);
	}

	@Test
	public void testOpenWindow() throws Exception {
		dialogFixture.show();
		dialogFixture.requireVisible();
	}

	@Test
	public void testNew() throws Exception {
		Util.locateOnScreenCenter(dialogFixture.target);
		dialogFixture.textBox("TextFieldCustomerName").focus();
		dialogFixture.textBox("TextFieldCustomerName").enterText("test");
		dialogFixture.textBox("TextFieldOrderNr").enterText(order.getOrderNr());
		dialogFixture.radioButton("RadioButtonInternal").click();
		dialogFixture.comboBox("ComboBoxResponsible").selectItem(1);
		dialogFixture.comboBox("ComboBoxDeviationFunction").selectItem(1);
		dialogFixture.comboBox("ComboBoxFunctionCategory").selectItem(1);
		dialogFixture.comboBox("ComboBoxStatus").selectItem(1);

		dialogFixture.button("AddDeviationComment").click();
		JDialog commentDialog = (JDialog) dialogFixture.robot.finder()
				.findByName("EditCommentView");
		DialogFixture commentDialogFixture = new DialogFixture(
				dialogFixture.robot, commentDialog);
		commentDialogFixture.textBox("TextAreaComment").enterText("test");
		commentDialogFixture.button("ButtonCommentOk").click();

		dialogFixture.button("SaveDeviation").click();

		Deviation deviation = new Deviation();
		deviation.setCustomerName(order.getCustomer().getFullName());
		List<Deviation> list = deviationManager.findByObject(deviation);
		assertEquals(true, list.size() != 0);
	}

	@Test
	public void testAddComment() throws Exception {
		dialogFixture.button("AddDeviationComment").click();
		JDialog commentDialog = (JDialog) dialogFixture.robot.finder()
				.findByName("EditCommentView");
		DialogFixture commentDialogFixture = new DialogFixture(
				dialogFixture.robot, commentDialog);
		commentDialogFixture.textBox("TextAreaComment").enterText("test");
		commentDialogFixture.button("ButtonCommentOk").click();

	}


	@Test
	public void testCheckBoxPostShipmentDisabled() {
		dialogFixture.checkBox("CheckBoxPostShipment").requireDisabled();
		dialogFixture.button("ButtonRemveArticle").requireDisabled();
		dialogFixture.button("ButtonAddArticle").requireDisabled();
	}

	@Test
	public void testSetOrderNr() {
		dialogFixture.textBox("TextFieldOrderNr").enterText(order.getOrderNr());
		dialogFixture.textBox("TextFieldName").focus();
		dialogFixture.textBox("TextFieldCustomerName").requireText(
				order.getCustomer().getFullName());
		dialogFixture.checkBox("CheckBoxPostShipment").requireDisabled();
	}

	@Test
	public void testAddArticle() {
		dialogFixture.textBox("TextFieldOrderNr").enterText(order.getOrderNr());
		dialogFixture.textBox("TextFieldName").focus();
		dialogFixture.button("ButtonAddArticle").click();

		DialogFixture editArticleType = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"ArticleTypeView"));
		editArticleType.list("ListArticles").selectItem(0);
		editArticleType.button("ButtonOkArticle").click();

		JOptionPaneFixture optionPaneFixture = new JOptionPaneFixture(
				dialogFixture.robot, dialogFixture.robot.finder().findByType(
						JOptionPane.class));
		optionPaneFixture.textBox().enterText("1");
		optionPaneFixture.okButton().click();

		dialogFixture.checkBox("CheckBoxPostShipment").requireEnabled();
	}

	@Test
	public void testSetPostShipment() {
		dialogFixture.textBox("TextFieldOrderNr").enterText(order.getOrderNr());
		dialogFixture.textBox("TextFieldName").focus();
		dialogFixture.comboBox("ComboBoxDeviationFunction").selectItem(1);
		dialogFixture.comboBox("ComboBoxFunctionCategory").selectItem(1);
		dialogFixture.comboBox("ComboBoxStatus").selectItem(1);
		dialogFixture.comboBox("ComboBoxResponsible").selectItem(1);

		dialogFixture.button("AddDeviationComment").click();
		JDialog commentDialog = (JDialog) dialogFixture.robot.finder()
				.findByName("EditCommentView");
		DialogFixture commentDialogFixture = new DialogFixture(
				dialogFixture.robot, commentDialog);
		commentDialogFixture.textBox("TextAreaComment").enterText("test");
		commentDialogFixture.button("ButtonCommentOk").click();

		dialogFixture.button("ButtonAddArticle").click();

		DialogFixture editArticleType = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"ArticleTypeView"));
		editArticleType.list("ListArticles").selectItem(0);
		editArticleType.button("ButtonOkArticle").click();

		JOptionPaneFixture optionPaneFixture = new JOptionPaneFixture(
				dialogFixture.robot, dialogFixture.robot.finder().findByType(
						JOptionPane.class));
		optionPaneFixture.textBox().enterText("1");
		optionPaneFixture.okButton().click();

		dialogFixture.checkBox("CheckBoxPostShipment").click();
		dialogFixture.radioButton("RadioButtonInternal").click();
		dialogFixture.button("SaveDeviation").click();

		Deviation deviation = new Deviation();
		deviation.setCustomerName(order.getCustomer().getFullName());
		deviation.setCustomerNr(order.getCustomer().getCustomerNr());
		deviation.setUserName("avviktransport avviktransport");

		List<Deviation> list = deviationManager.findByObject(deviation);
		assertEquals(true, list.size() != 0);
	}

	@Test
	public void testManagerCanSetEndDate() {

		JDateChooser dateChooser = (JDateChooser) dialogFixture.robot.finder()
				.findByName("DateChooserProcedureCheck");
		assertEquals(false, dateChooser.isEnabled());

		dialogFixture.comboBox("ComboBoxDeviationFunction").selectItem(
				"Transport");

		assertEquals(true, dateChooser.isEnabled());

	}

	@Test
	public void testAddCost() {
		dialogFixture.button("ButtonAddCost").click();

		DialogFixture costDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"OrderCostView"));
		costDialog.comboBox("ComboBoxCostType").selectItem(0);
		costDialog.textBox("TextFieldAmount").enterText("100");
		costDialog.button("OkOrderCost").click();
	}

	@Test
	public void testSetPreventiveAction() {

		dialogFixture.comboBox("ComboBoxDeviationFunction").selectItem(1);
		dialogFixture.comboBox("ComboBoxFunctionCategory").selectItem(1);
		dialogFixture.button("ButtonAddPreventiveAction").click();
		DialogFixture addPreventiveAction = WindowFinder.findDialog(
				"EditPreventiveActionView").using(dialogFixture.robot);
//		addPreventiveAction.textBox("TextFieldProjectNr").enterText("9999");
		addPreventiveAction.textBox("TextFieldName").enterText("TEST");
		addPreventiveAction.textBox("TextFieldManager").click();
		addPreventiveAction.button("SavePreventiveAction").click();
		addPreventiveAction.button("EditCancelPreventiveAction").click();
		dialogFixture.comboBox("ComboBoxPreventiveAction").selectItem(1);
	}

	@Test
	public void testSetAssembly() {
		dialogFixture.checkBox("CheckBoxDoAssembly").check();
	}

	@Test
	public void testNoOtherDeviation() {
		dialogFixture.show();

		JTabbedPaneFixture tabbedPane = new JTabbedPaneFixture(
				dialogFixture.robot, dialogFixture.robot.finder().findByType(
						JTabbedPane.class));
		tabbedPane = tabbedPane.selectTab(1);

		assertEquals(0, dialogFixture.table("TableDeviation").target
				.getRowCount());
	}

	@Test
	public void testShowRegistrationDate() {
		dialogFixture.label("LabelRegistrationDate").requireVisible();
	}

	@Test
	public void testShowClosedDate() {
		dialogFixture.textBox("TextFieldDateClosed").requireVisible();
		dialogFixture.textBox("TextFieldDateClosed").requireDisabled();
	}
}
