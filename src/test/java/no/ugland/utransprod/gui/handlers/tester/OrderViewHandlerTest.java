package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderPanelTypeEnum;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.CustomerManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author atle.brekka
 */
@Category(FastTests.class)
public class OrderViewHandlerTest {

	private OrderViewHandler viewHandler;
	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;
	@Mock
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	@Mock
	private DeviationOverviewViewFactory deviationOverviewViewFactory;
	@Mock
	private ProductAreaManager productAreaManager;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		final OrderManager orderManager = (OrderManager) ModelUtil
				.getBean(OrderManager.MANAGER_NAME);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		when(managerRepository.getProductAreaManager()).thenReturn(
				productAreaManager);
		final UserType userType = new UserType();
		userType.setIsAdmin(1);
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWindowAccess(new WindowAccess(null, "Attributter",
				null));
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);

		viewHandler = new OrderViewHandler(login, managerRepository,
				deviationOverviewViewFactory, deviationViewHandlerFactory,
				false);
	}

	@After
	public void tearDown() throws Exception {
		CustomerManager customerManager = (CustomerManager) ModelUtil
				.getBean(CustomerManager.MANAGER_NAME);
		Customer customer = customerManager.findByCustomerNr(100000);
		if (customer != null) {
			customerManager.removeCustomer(customer);
		}
	}

	@Test
	public void testGetOrderListAtStart() {
		assertEquals(0, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testGetAddRemoveString() {
		assertNotNull(viewHandler.getAddRemoveString());
		assertEquals("ordre", viewHandler.getAddRemoveString());
	}

	@Test
	public void testGetNewObject() {
		assertNotNull(viewHandler.getNewObject());
		assertEquals(Order.class, viewHandler.getNewObject().getClass());
	}

	@Test
	public void testGetTableModel() {
		assertNotNull(viewHandler.getTableModel(null));

	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> customers = viewHandler.getCustomerList();
		assertNotNull(customers);
		assertEquals(true, customers.size() != 0);
	}

	@Test
	public void testGetAllConstructionTypes() {
		List<ConstructionType> types = viewHandler.getConstructionTypeList();
		assertNotNull(types);
		assertEquals(true, types.size() != 0);
	}

	@Test
	public void testGetAllTransports() {
		List<Transport> transports = viewHandler.getTransportList(false);
		assertNotNull(transports);
		assertEquals(true, transports.size() != 0);
	}

	@Test
	public void testFindNewOrders() {
		assertEquals(true, viewHandler.initAndGetOrderPanelSelectionList(
				OrderPanelTypeEnum.NEW_ORDERS).getSize() != 0);
	}

	@Test
	public void testSaveObjectExt() {
		ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean(ProductAreaManager.MANAGER_NAME);
		ProductArea productArea = productAreaManager
				.findByName("Garasje villa");
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean(ConstructionTypeManager.MANAGER_NAME);
		ConstructionType constructionType = constructionTypeManager
				.findByName("A1");
		OrderLine orderLine = new OrderLine();
		orderLine.setOrdNo(7);
		orderLine.setLnNo(13);
		Order order = new Order();
		order.setProductArea(productArea);
		order.setOrderNr("100100100");
		order.setConstructionType(constructionType);
		order.addOrderLine(orderLine);
		orderLine.setArticlePath(orderLine.getGeneratedArticlePath());
		order.setDeliveryAddress("deliveryAddress");
		order.setPostalCode("4841");
		order.setPostOffice("postOffice");
		order.setOrderDate(Util.getCurrentDate());
		OrderModel orderModel = new OrderModel(order, false, true, true, null,
				null);
		orderModel.setCustomerNr("100000");
		orderModel.setCustomerFirstName("test");
		orderModel.setCustomerLastName("testesen");
		viewHandler.saveObjectExt(orderModel, null);
		assertEquals(Integer.valueOf(1), orderLine.getIsDefault());
	}
}
