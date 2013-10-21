package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class CustomerManagerTest {
	private CustomerManager manager;

	private List<Customer> customers = new ArrayList<Customer>();

	private Customer cust;

	@After
	public void tearDown() throws Exception {
		if (customers != null) {
			for (Customer customer : customers) {
				if (customer.getCustomerId() != null) {
					manager.removeCustomer(customer);
					
				}
			}
			customers.clear();
		}
	}

	@Before
	public void setUp() throws Exception {
		manager = (CustomerManager) ModelUtil.getBean("customerManager");
		cust = new Customer();
		customers.add(cust);
	}

	@Test
	public void testInsertCustomer() throws Exception {
		cust.setCustomerNr(9999);
		cust.setFirstName("test");
		cust.setLastName("testen");
		manager.saveCustomer(cust);
		assertNotNull(cust.getCustomerId());
	}

	@Test
	public void testEditCustomer() throws Exception {
		cust.setCustomerNr(9999);
		cust.setFirstName("test");
		cust.setLastName("testen");
		manager.saveCustomer(cust);
		assertNotNull(cust.getCustomerId());
		cust = manager.findByCustomerNr(9999);
		assertNotNull(cust);
		cust.setCustomerNr(9998);
		manager.saveCustomer(cust);
		cust = manager.findByCustomerNr(9998);
		assertNotNull(cust);
	}

	@Test
	public void testRemoveCustomer() throws Exception {
		cust.setCustomerNr(9999);
		cust.setFirstName("test");
		cust.setLastName("testen");
		manager.saveCustomer(cust);
		assertNotNull(cust.getCustomerId());
		manager.removeCustomer(cust);
		customers.remove(cust);
		cust = manager.findByCustomerNr(9999);
		assertNull(cust);
	}

	@Test
	public void testFindAll() throws Exception {
		cust.setCustomerNr(9999);
		cust.setFirstName("test");
		cust.setLastName("testen");
		manager.saveCustomer(cust);

		cust = new Customer();
		customers.add(cust);
		cust.setCustomerNr(9998);
		cust.setFirstName("test");
		cust.setLastName("testen");
		manager.saveCustomer(cust);

		List<Customer> customers1 = manager.findAll();
		assertNotNull(customers1);
		assertEquals(true, customers1.size() >= 2);
	}
}
