package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.test.SlowTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.ReadyCount;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(SlowTests.class)
public class OrderManagerTest {
	private static OrderManager orderManager = (OrderManager) ModelUtil
			.getBean("orderManager");
	private static ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
			.getBean("articleTypeManager");

	private static ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
			.getBean("constructionTypeManager");

	private static ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
			.getBean("productAreaManager");

	private static CustomerManager customerManager = (CustomerManager) ModelUtil
			.getBean("customerManager");

	private static TransportManager transportManager = (TransportManager) ModelUtil
			.getBean("transportManager");

	private static List<Order> orders;

	private static List<Customer> customers;

	private static List<ConstructionType> constructionTypes;

	private static List<Transport> transports;
	private static List<ArticleType> articleTypes;

	private static ProductArea productArea;

	@BeforeClass
	public static void setUp() throws Exception {
		orders = new ArrayList<Order>();
		customers = new ArrayList<Customer>();
		constructionTypes = new ArrayList<ConstructionType>();
		transports = new ArrayList<Transport>();
		articleTypes = new ArrayList<ArticleType>();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		if (transports != null) {
			for (Transport transport : transports) {
				if (transport.getTransportId() != null) {
					transportManager.removeTransport(transport);
				}
			}
		}
		if (orders != null) {
			for (Order order : orders) {
				if (order.getOrderId() != null) {
					orderManager.removeOrder(order);
				}
			}
		}
		if (customers != null) {
			for (Customer customer : customers) {
				if (customer.getCustomerId() != null) {
					customerManager.removeCustomer(customer);
				}
			}
		}
		if (constructionTypes != null) {
			for (ConstructionType constructionType : constructionTypes) {
				if (constructionType.getConstructionTypeId() != null) {
					constructionTypeManager
							.removeConstructionType(constructionType);
				}
			}
		}

		if (articleTypes != null) {
			for (ArticleType articleType : articleTypes) {
				if (articleType.getArticleTypeId() != null) {
					articleTypeManager.removeArticleType(articleType);
				}
			}
		}

		if (productArea != null && productArea.getProductAreaId() != null) {
			productAreaManager.removeProductArea(productArea);

		}

	}

	@Test
	public void skalSettProductionBasis() throws ProTransException {
		Order order = orderManager.findByOrderNr("51693");
		order.setProductionBasis(Integer.valueOf(50));
		orderManager.saveOrder(order);
		order = orderManager.findByOrderNr("51693");
		assertNotNull(order.getProductionBasis());
		order.setProductionBasis(null);
		orderManager.saveOrder(order);
	}

	@Test
	public void insertOrder() throws Exception {
		Order order = new Order();
		orders.add(order);
		Customer customer = new Customer();
		customers.add(customer);
		customer.setCustomerNr(100);
		customer.setFirstName("Atle");
		customer.setLastName("Brekka");
		customerManager.saveCustomer(customer);

		productArea = new ProductArea(null, "test", null, null, null, null,
				null);
		productAreaManager.saveProductArea(productArea);

		ConstructionType constructionType = new ConstructionType();
		constructionTypes.add(constructionType);
		constructionType.setName("Q5");
		constructionType.setProductArea(productArea);
		constructionTypeManager.saveConstructionType(constructionType);

		order.setOrderNr("test");
		order.setCustomer(customer);
		order.setConstructionType(constructionType);
		order.setDeliveryAddress("testing");
		order.setPostalCode("0354");
		order.setPostOffice("Oslo");
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setProductArea(productArea);

		ArticleType articleType = new ArticleType(null, "test", null, null,
				null, null, null, null, null, null, null, null);
		articleTypes.add(articleType);
		articleTypeManager.saveArticleType(articleType);
		OrderLine orderLine = new OrderLine(null, order, null, articleType,
				null, null, null, null, null, null, "test", null, null, null,
				null, null, null, null, null, null, null, null);
		Set<OrderLine> orderLines = new HashSet<OrderLine>();
		orderLines.add(orderLine);

		order.setOrderLines(orderLines);

		orderManager.saveOrder(order);
		order = orderManager.findByOrderNr("1");
		assertNotNull(order);
		assertEquals("1", order.getOrderNr());
		assertNotNull(order.getOrderId());
	}

	@Test
	public void findReadyCountByProductArea() {
		ProductArea productAreaGarasjeVilla = productAreaManager
				.findByName("Garasje villa");
		assertNotNull(productAreaGarasjeVilla);
		List<ReadyCount> countList = orderManager
				.findReadyCountByProductArea(productAreaGarasjeVilla);
		assertNotNull(countList);

		for (ReadyCount ready : countList) {
			if (ready.getOrderNr().equalsIgnoreCase("56459")
					&& ready.getColli() != null) {
				assertFalse(ready.getColli().equalsIgnoreCase("Takstol"));
			}
		}
	}

}
