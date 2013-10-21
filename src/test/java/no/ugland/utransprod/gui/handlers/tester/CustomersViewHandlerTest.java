package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.CustomersViewHandler;
import no.ugland.utransprod.gui.model.CustomerModel;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.CustomerManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class CustomersViewHandlerTest {
	private CustomersViewHandler viewHandler;
	@Mock
	private Login login;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);
		final CustomerManager customerManager = (CustomerManager) ModelUtil
				.getBean(CustomerManager.MANAGER_NAME);

		viewHandler = new CustomersViewHandler(login, customerManager);
	}

	@After
	public void tearDown() throws Exception {
		CustomerManager customerManager = (CustomerManager) ModelUtil
				.getBean("customerManager");
		Customer customer = new Customer();
		customer.setFirstName("Tull");
		customer.setLastName("Ball");
		List<Customer> customers = customerManager.findByObject(customer);
		if (customers != null) {
			for (Customer cust : customers) {
				customerManager.removeCustomer(cust);
			}
		}
	}

	@Test
	public void testGetCustomerListAtStart() {
		assertEquals(0, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testGetCustomerSelectionList() {
		assertEquals(0, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testGetTableModel() {
		assertNotNull(viewHandler.getTableModel(null));
	}

	@Test
	public void testCanClose() {
		assertEquals(true, viewHandler.canClose(null, null));
	}

	@Test
	public void testGetAddButton() {
		assertNotNull(viewHandler.getAddButton(null));
	}

	@Test
	public void testGetCancelButton() {
		assertNotNull(viewHandler.getCancelButton(null));
	}

	@Test
	public void testGetCustomerTableModel() {
		TableModel tableModel = viewHandler.getTableModel(null);
		assertNotNull(tableModel);
		assertEquals(3, tableModel.getColumnCount());
	}

	@Test
	public void testGetEditButton() {
		assertNotNull(viewHandler.getEditButton(null));
	}

	@Test
	public void testGetRemoveButton() {
		assertNotNull(viewHandler.getRemoveButton(null));
	}

	@Test
	public void testGetSearchButton() {
		assertNotNull(viewHandler.getSearchButton(null));
	}

	@Test
	public void testDoNotSaveCustomerWithSameNr() {
		viewHandler.saveObject(new CustomerModel(new Customer(null, 100100100,
				"Tull", "Ball", null)), null);
		assertNotNull(viewHandler.checkSaveObject(new CustomerModel(
				new Customer(null, 100100100, "Tull", "Ball", null)), null,
				null));
	}

	@Test
	public void testSaveCustomer() throws Exception {
		viewHandler.saveObject(new CustomerModel(new Customer(null, 100100100,
				"Tull", "Ball", null)), null);
		assertEquals(1, viewHandler.getObjectSelectionListSize());
	}

}
