package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
@Category(FastTests.class)
public class IncomingOrderManagerIntegrationTest {
	private IncomingOrderManager incomingOrderManager;
	@Mock
	private ManagerRepository managerRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		incomingOrderManager = (IncomingOrderManager) ModelUtil
				.getBean("incomingOrderManager");
		final OrdlnManager ordlnManager = (OrdlnManager) ModelUtil
				.getBean(OrdlnManager.MANAGER_NAME);
		when(managerRepository.getOrdlnManager()).thenReturn(ordlnManager);

	}

	@Test
	// (groups = { "maven" })
	public final void importCosts() throws Exception {
		List<Order> orders = incomingOrderManager.findByOrderNr("11");
		assertEquals(1, orders.size());
		Order order = orders.get(0);
		incomingOrderManager.setCosts(order);

		assertEquals(true, order.doAssembly());
		assertNotNull(order);

		Set<OrderCost> costs = order.getOrderCosts();
		assertNotNull(costs);
		
		boolean egenproduksjonKunde = false;
		boolean egenproduksjonIntern = false;
		boolean frakt = false;
		boolean montering = false;
		boolean jaLinjer = false;
		boolean kraning = false;
		boolean avfall = false;
		for (OrderCost cost : costs) {
			if (cost.getCostType().getCostTypeName().equalsIgnoreCase(
					"Egenproduksjon")
					&& cost.getCostUnit().getCostUnitName().equalsIgnoreCase(
							"Kunde")) {
				egenproduksjonKunde = true;
				assertEquals(BigDecimal.valueOf(200000), cost.getCostAmount());
			}
			if (cost.getCostType().getCostTypeName().equalsIgnoreCase(
					"Egenproduksjon")
					&& cost.getCostUnit().getCostUnitName().equalsIgnoreCase(
							"Intern")) {
				egenproduksjonIntern = true;
				assertEquals(BigDecimal.valueOf(3000), cost.getCostAmount());
			}
			if (cost.getCostType().getCostTypeName().equalsIgnoreCase("Frakt")) {
				assertEquals("Kunde", cost.getCostUnit().getCostUnitName());
				assertEquals(BigDecimal.valueOf(2000), cost.getCostAmount());
				frakt = true;
			}
			if (cost.getCostType().getCostTypeName().equalsIgnoreCase(
					"Montering")) {
				assertEquals("Kunde", cost.getCostUnit().getCostUnitName());
				assertEquals(BigDecimal.valueOf(20000), cost.getCostAmount()
						.setScale(0));
				montering = true;
			}
			if (cost.getCostType().getCostTypeName().equalsIgnoreCase(
					"Jalinjer")) {
				assertEquals("Kunde", cost.getCostUnit().getCostUnitName());
				assertEquals(BigDecimal.valueOf(5000), cost.getCostAmount());
				jaLinjer = true;
			}
			if (cost.getCostType().getCostTypeName()
					.equalsIgnoreCase("Kraning")) {
				assertEquals("Kunde", cost.getCostUnit().getCostUnitName());
				assertEquals(BigDecimal.valueOf(2000), cost.getCostAmount()
						.setScale(0));
				kraning = true;
			}
			if (cost.getCostType().getCostTypeName().equalsIgnoreCase("Avfall")) {
				assertEquals("Kunde", cost.getCostUnit().getCostUnitName());
				assertEquals(BigDecimal.valueOf(500), cost.getCostAmount()
						.setScale(0));
				avfall = true;
			}
		}
		
		assertTrue(egenproduksjonKunde);
		assertTrue(egenproduksjonIntern);
		assertTrue(frakt);
		assertTrue(montering);
		assertTrue(jaLinjer);
		assertTrue(kraning);
		assertTrue(avfall);
	}

	@Test
	public void getOrderThatIsInProTrans() {
		List<Order> orders = incomingOrderManager.findByOrderNr("47909");
		assertEquals(0, orders.size());
	}

	@Test
	public void importGulvspon() {
		List<Order> orders = incomingOrderManager.findByOrderNr("11");
		assertEquals(1, orders.size());

		Order order = orders.get(0);

		incomingOrderManager.setOrderLines(order, managerRepository);
		Set<OrderLine> orderLines = order.getOrderLines();
		assertNotNull(orderLines);
		OrderLine gulvspon = order.getOrderLine("Gulvspon");
		assertNotNull(gulvspon);
	}

	@Test
	public void importGarageType() {
		List<Order> orders = incomingOrderManager.findByOrderNr("11");
		assertEquals(1, orders.size());

		Order order = orders.get(0);
		incomingOrderManager.setOrderLines(order, managerRepository);

		assertEquals("A1", order.getConstructionTypeString());
	}
}
