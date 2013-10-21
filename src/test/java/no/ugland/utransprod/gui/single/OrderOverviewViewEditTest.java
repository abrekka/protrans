package no.ugland.utransprod.gui.single;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderCommentCommentType;
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
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.toedter.calendar.JDateChooser;

public class OrderOverviewViewEditTest {
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
		final JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean(JobFunctionManager.MANAGER_NAME);
		when(managerRepository.getJobFunctionManager()).thenReturn(
				jobFunctionManager);
		final ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean(ProductAreaManager.MANAGER_NAME);
		when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
		orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		final SupplierManager supplierManager = (SupplierManager) ModelUtil
				.getBean(SupplierManager.MANAGER_NAME);
		when(managerRepository.getSupplierManager()).thenReturn(supplierManager);
		final DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil
				.getBean(DeviationStatusManager.MANAGER_NAME);
		when(managerRepository.getDeviationStatusManager()).thenReturn(deviationStatusManager);
		final ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean(ApplicationUserManager.MANAGER_NAME);
		when(managerRepository.getApplicationUserManager()).thenReturn(applicationUserManager);
		final DeviationManager deviationManager = (DeviationManager) ModelUtil
				.getBean(DeviationManager.MANAGER_NAME);
		when(managerRepository.getDeviationManager()).thenReturn(deviationManager);
		final ApplicationUser applicationUser = new ApplicationUser();
		
		final ProductArea productArea = productAreaManager
				.findByName("Garasje villa");
		ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
				.getBean(ProductAreaGroupManager.MANAGER_NAME);
		ProductAreaGroup productAreaGroup = productAreaGroupManager
				.findByName("Garasje");
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

			public DeviationOverviewView create(
					DeviationViewHandler deviationViewHandler,
					boolean useSearchButton, Order aOrder, boolean doSeeAll,
					boolean forOrderInfo, boolean isForRegisterNew,
					Deviation notDisplayDeviation,
					boolean isDeviationTableEditable) {
				return new DeviationOverviewView(null, deviationViewHandler,
						false, null, false, true, true, null, true);
			}
		};

		

		

		PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(
				login, managerRepository);
		final DeviationViewHandlerFactory deviationViewHandlerFactory = new DeviationViewHandlerFactoryImpl(
				login, managerRepository, preventiveActionViewHandler);

		OrderViewHandler orderViewHandler = new OrderViewHandler(login,
				managerRepository, deviationOverviewViewFactory,
				deviationViewHandlerFactory, false);
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

		CustomerManager customerManager = (CustomerManager) ModelUtil
				.getBean("customerManager");
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
	public void editByDoubleClick() throws Exception {

		JTableFixture tableFixture = dialogFixture.table("TableOrder");
		int count = tableFixture.target.getModel().getRowCount();
		assertEquals(true, count != 0);

		tableFixture.cell(row(0).column(1)).doubleClick();

		dialogFixture.robot.finder().findByName("EditOrderView");
	}

	@Test
	public void newOrder() throws Exception {
		dialogFixture.show();
		JTableFixture tableFixture = dialogFixture.table("TableOrder");
		int count = tableFixture.target.getModel().getRowCount();
		assertEquals(true, count != 0);

		dialogFixture.button("AddOrder").click();

		DialogFixture editDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"EditOrderView"));

		editDialog.textBox("CustomerNr").enterText("100");
		editDialog.textBox("CustomerFirstName").enterText("Atle");
		editDialog.textBox("CustomerLastName").enterText("Brekka");
		editDialog.textBox("CustomerFirstName").click();
		editDialog.textBox("OrderNr").enterText("100100100");
		editDialog.textBox("Address").enterText("Arendal");
		editDialog.textBox("PostalCode").enterText("4800");
		editDialog.textBox("Postoffice").enterText("Arendal");

		editDialog.checkBox("Assembly").check();
		editDialog.comboBox("AssemblyWeek").selectItem(Util.getCurrentWeek());
		editDialog.comboBox("ComboBoxAssemblyTeam").selectItem(0);

		assertEquals(true,
				dialogFixture.robot.finder().findByName("AssemblyPanel")
						.isVisible());

		editDialog.comboBox("ComboBoxProductArea").selectItem("Garasje villa");
		editDialog.comboBox("ComboBoxConstructionType").selectItem(0);

		DialogFixture garasjeDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"UpdateOrderLineView"));

		garasjeDialog.button("ButtonOk").click();

		JDateChooser orderDate = (JDateChooser) dialogFixture.robot.finder()
				.findByName("OrderDate");

		orderDate.setDate(Calendar.getInstance().getTime());

		JDateChooser productionDate = (JDateChooser) dialogFixture.robot
				.finder().findByName("DateChooserProduction");

		productionDate.setDate(Calendar.getInstance().getTime());

		JButtonFixture addCostButton = editDialog.button("ButtonAddCost");
		addCostButton.click();
		DialogFixture kostnadDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"OrderCostView"));

		kostnadDialog.comboBox("ComboBoxCostType").selectItem("Egenproduksjon");
		kostnadDialog.comboBox("ComboBoxCostUnit").selectItem("Kunde");
		kostnadDialog.textBox("TextFieldAmount").enterText("100");
		kostnadDialog.button("OkOrderCost").click();

		addCostButton.click();
		kostnadDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"OrderCostView"));

		kostnadDialog.comboBox("ComboBoxCostType").selectItem("Frakt");
		kostnadDialog.comboBox("ComboBoxCostUnit").selectItem("Kunde");
		kostnadDialog.textBox("TextFieldAmount").enterText("200");
		kostnadDialog.button("OkOrderCost").click();

		addCostButton.click();
		kostnadDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"OrderCostView"));

		kostnadDialog.comboBox("ComboBoxCostType").selectItem("Montering");
		kostnadDialog.comboBox("ComboBoxCostUnit").selectItem("Kunde");
		kostnadDialog.textBox("TextFieldAmount").enterText("300");
		kostnadDialog.button("OkOrderCost").click();

		editDialog.button("ButtonAddComment").click();

		DialogFixture commentDialog = WindowFinder
				.findDialog("EditCommentView").withTimeout(60000)
				.using(dialogFixture.robot);
		commentDialog.textBox("TextAreaComment").enterText("orderkommentar");
		commentDialog.button("ButtonCommentOk").click();

		editDialog.button("SaveOrder").click();
		editDialog.button("SaveOrder").requireDisabled();

		assertEquals(count + 1, tableFixture.target.getModel().getRowCount());

		Order order = new Order();
		order.setOrderNr("100100100");
		List<Order> orders = orderManager.findByObject(order);
		assertNotNull(orders);
		assertEquals(1, orders.size());
		order = orders.get(0);

		orderManager.lazyLoadOrder(order,
				new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });

		Set<OrderComment> comments = order.getOrderComments();
		assertNotNull(comments);
		assertEquals(1, comments.size());
		OrderComment orderComment = comments.iterator().next();
		Set<OrderCommentCommentType> commentTypes = orderComment
				.getOrderCommentCommentTypes();
		assertNotNull(commentTypes);
		assertEquals(1, commentTypes.size());
		OrderCommentCommentType type = commentTypes.iterator().next();
		assertNotNull(type);
		assertEquals("Ordre", type.getCommentType().getCommentTypeName());

	}

	@Test
	public void newOrderWithTransportAndPackingComment() throws Exception {
		dialogFixture.show();
		JTableFixture tableFixture = dialogFixture.table("TableOrder");
		int count = tableFixture.target.getModel().getRowCount();
		assertEquals(true, count != 0);

		dialogFixture.button("AddOrder").click();

		DialogFixture editDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"EditOrderView"));

		editDialog.textBox("CustomerNr").enterText("100");
		editDialog.textBox("CustomerFirstName").enterText("Atle");
		editDialog.textBox("CustomerLastName").enterText("Brekka");
		editDialog.textBox("CustomerFirstName").click();
		editDialog.textBox("OrderNr").enterText("100100100");
		editDialog.textBox("Address").enterText("Arendal");
		editDialog.textBox("PostalCode").enterText("4800");
		editDialog.textBox("Postoffice").enterText("Arendal");

		editDialog.comboBox("ComboBoxProductArea").selectItem("Garasje villa");
		editDialog.comboBox("ComboBoxConstructionType").selectItem(0);

		DialogFixture garasjeDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"UpdateOrderLineView"));

		garasjeDialog.button("ButtonOk").click();

		JDateChooser orderDate = (JDateChooser) dialogFixture.robot.finder()
				.findByName("OrderDate");

		orderDate.setDate(Calendar.getInstance().getTime());

		JDateChooser productionDate = (JDateChooser) dialogFixture.robot
				.finder().findByName("DateChooserProduction");

		productionDate.setDate(Calendar.getInstance().getTime());

		JButtonFixture addCostButton = editDialog.button("ButtonAddCost");
		addCostButton.click();
		DialogFixture kostnadDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"OrderCostView"));

		kostnadDialog.comboBox("ComboBoxCostType").selectItem("Egenproduksjon");
		kostnadDialog.comboBox("ComboBoxCostUnit").selectItem("Kunde");
		kostnadDialog.textBox("TextFieldAmount").enterText("100");
		kostnadDialog.button("OkOrderCost").click();

		addCostButton.click();
		kostnadDialog = new DialogFixture(dialogFixture.robot,
				(JDialog) dialogFixture.robot.finder().findByName(
						"OrderCostView"));

		kostnadDialog.comboBox("ComboBoxCostType").selectItem("Frakt");
		kostnadDialog.comboBox("ComboBoxCostUnit").selectItem("Kunde");
		kostnadDialog.textBox("TextFieldAmount").enterText("200");
		kostnadDialog.button("OkOrderCost").click();

		editDialog.button("ButtonAddComment").click();

		DialogFixture commentDialog = WindowFinder
				.findDialog("EditCommentView").withTimeout(60000)
				.using(dialogFixture.robot);
		commentDialog.textBox("TextAreaComment").enterText("orderkommentar");

		commentDialog.checkBox("CheckBoxTransport").click();
		commentDialog.checkBox("CheckBoxPackage").click();
		commentDialog.button("ButtonCommentOk").click();

		editDialog.button("SaveOrder").click();
		editDialog.button("SaveOrder").requireDisabled();

		assertEquals(count + 1, tableFixture.target.getModel().getRowCount());

		Order order = new Order();
		order.setOrderNr("100100100");
		List<Order> orders = orderManager.findByObject(order);
		assertNotNull(orders);
		assertEquals(1, orders.size());
		order = orders.get(0);

		orderManager.lazyLoadOrder(order,
				new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });

		Set<OrderComment> comments = order.getOrderComments();
		assertNotNull(comments);
		assertEquals(1, comments.size());
		OrderComment orderComment = comments.iterator().next();
		Set<OrderCommentCommentType> commentTypes = orderComment
				.getOrderCommentCommentTypes();
		assertNotNull(commentTypes);
		assertEquals(3, commentTypes.size());
		count = 0;
		List<OrderCommentCommentType> commentTypeList = new ArrayList<OrderCommentCommentType>();
		Collections.sort(commentTypeList);
		for (OrderCommentCommentType type : commentTypeList) {
			count++;
			if (count == 1) {
				assertEquals("Transport", type.getCommentType()
						.getCommentTypeName());
			} else if (count == 2) {
				assertEquals("Pakking", type.getCommentType()
						.getCommentTypeName());
			} else if (count == 3) {
				assertEquals("Ordre", type.getCommentType()
						.getCommentTypeName());
			}
		}

	}

}
