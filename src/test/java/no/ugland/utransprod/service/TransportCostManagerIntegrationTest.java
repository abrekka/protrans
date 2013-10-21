package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.TransportCost;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.model.TransportCostBasis;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.test.SlowTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(SlowTests.class)
public class TransportCostManagerIntegrationTest {
	private TransportCostManager transportCostManager;

	private TransportCostBasisManager transportCostBasisManager;

	private TransportManager transportManager;

	private OrderManager orderManager;

	private CustomerManager customerManager;

	private ConstructionTypeManager constructionTypeManager;

	private ProductAreaManager productAreaManager;

	private PostShipmentManager postShipmentManager;

	private ColliManager colliManager;

	private Transport transport;

	private Transport transportPostShipment;

	private Transport transportPostShipment2;

	private Order order;

	private Order takstolOrder;

	private Customer customer;

	private Customer takstolCustomer;

	private PostShipment postShipment;

	private PostShipment postShipment2;

	private List<TransportCostBasis> allTransportCostBasis = new ArrayList<TransportCostBasis>();

	private static final Integer YEAR = 2012;

	private static final Integer WEEK_50 = 50;

	private static final Integer WEEK_51 = 51;

	private static final Integer WEEK_52 = 52;

	@Before
	public void setUp() throws Exception {
		transportCostManager = (TransportCostManager) ModelUtil
				.getBean("transportCostManager");
		transportCostBasisManager = (TransportCostBasisManager) ModelUtil
				.getBean("transportCostBasisManager");
		transportManager = (TransportManager) ModelUtil
				.getBean("transportManager");
		orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		customerManager = (CustomerManager) ModelUtil
				.getBean("customerManager");
		constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
		productAreaManager = (ProductAreaManager) ModelUtil
				.getBean("productAreaManager");
		postShipmentManager = (PostShipmentManager) ModelUtil
				.getBean("postShipmentManager");
		colliManager = (ColliManager) ModelUtil.getBean("colliManager");

	}

	@After
	public void tearDown() throws Exception {

		if (order != null && order.getOrderId() != null) {
			orderManager.removeOrder(order);
			order=null;
		}
		if (takstolOrder != null && takstolOrder.getOrderId() != null) {
			orderManager.removeOrder(takstolOrder);
			takstolOrder=null;
		}
		if (customer != null && customer.getCustomerId() != null) {
			customerManager.removeCustomer(customer);
			customer=null;
		}
		if (takstolCustomer != null && takstolCustomer.getCustomerId() != null) {
			customerManager.removeCustomer(takstolCustomer);
			takstolCustomer=null;
		}
		if (transport != null && transport.getTransportId() != null) {
			transportManager.removeTransport(transport);
			transport=null;
		}
		if (transportPostShipment != null
				&& transportPostShipment.getTransportId() != null) {
			transportManager.removeTransport(transportPostShipment);
			transportPostShipment=null;
		}
		if (transportPostShipment2 != null
				&& transportPostShipment2.getTransportId() != null) {
			transportManager.removeTransport(transportPostShipment2);
			transportPostShipment2=null;
		}

		for (TransportCostBasis basis : allTransportCostBasis) {
			if (basis.getTransportCostBasisId() != null) {
				transportCostBasisManager.removeTransportCostBasis(basis);
			}
		}
		allTransportCostBasis.clear();

	}

	@Test
	public void testImportAllPostalCodes() throws Exception {
		importAllCounties();
		importAllAreas();
		importAlPostalCodes();

		List<TransportCost> list = transportCostManager.findAll();
		assertEquals(7, list.size());

	}

	@Test
	public void testImportAllPostalCodesTwice() throws Exception {
		URL url = getClass().getClassLoader().getResource("testpostnr.xls");

		transportCostManager.importAllPostalCodes(url.getFile(), false);

		List<TransportCost> list = transportCostManager.findAll();
		assertEquals(7, list.size());

		transportCostManager.importAllPostalCodes(url.getFile(), false);

		list = transportCostManager.findAll();
		assertEquals(7, list.size());
	}

	@Test
	public void testUpdatePricesFromFile() throws Exception {
		updatePricesFromFile();
		TransportCost transportCost = transportCostManager
				.findByPostalCode("0139");
		assertEquals(BigDecimal.valueOf(1000), transportCost.getCost());
		assertEquals(Integer.valueOf(1), transportCost.getValid());
		assertEquals(BigDecimal.valueOf(15), transportCost.getAddition());
		transportCost = transportCostManager.findByPostalCode("0050");
		assertNull(transportCost);

	}

	@Test
	public void testInsertAdditions() throws Exception {
		URL url = getClass().getClassLoader().getResource(
				"transportpriser_test.xls");
		transportCostManager.updatePricesFromFile(url.getFile());
		TransportCostAdditionManager transportCostAdditionManager = (TransportCostAdditionManager) ModelUtil
				.getBean("transportCostAdditionManager");
		TransportCostAddition transportCostAddition = transportCostAdditionManager
				.findByDescription("BreddeX2+høydeX2");
		assertNotNull(transportCostAddition);
	}

	@Test
	public void testGenerateTransportCostList() throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		List<TransportCostBasis> transportCostBasisList = transportCostBasisManager
				.findById(transportCostBasis.getTransportCostBasisId());
		assertEquals(1, transportCostBasisList.size());

		allTransportCostBasis.addAll(list);

		transportCostBasis = transportCostBasisList.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		assertEquals("" + YEAR + " " + WEEK_50 + "-" + WEEK_50,
				transportCostBasis.getPeriode());
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		assertEquals("Grunnpris:1000", orderCost.getComment());
		BigDecimal result = BigDecimal.valueOf(1000).setScale(2);
		assertEquals(result, orderCost.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithoutTakstol() throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setUpTakstolOrder();
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		List<TransportCostBasis> transportCostBasisList = transportCostBasisManager
				.findById(transportCostBasis.getTransportCostBasisId());
		assertEquals(1, transportCostBasisList.size());

		allTransportCostBasis.addAll(list);

		transportCostBasis = transportCostBasisList.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		assertEquals("" + YEAR + " " + WEEK_50 + "-" + WEEK_50,
				transportCostBasis.getPeriode());
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		assertEquals("Grunnpris:1000", orderCost.getComment());
		BigDecimal result = BigDecimal.valueOf(1000).setScale(2);
		assertEquals(result, orderCost.getCostAmount().setScale(2));

	}

	@Test
	public void testSetInvoiceNr() throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		List<TransportCostBasis> transportCostBasisList = transportCostBasisManager
				.findById(transportCostBasis.getTransportCostBasisId());
		assertEquals(1, transportCostBasisList.size());

		transportCostBasisManager.setInvoiceNr(transportCostBasis, "1111");

		transportCostBasisList = transportCostBasisManager
				.findById(transportCostBasis.getTransportCostBasisId());
		assertEquals(1, transportCostBasisList.size());

		allTransportCostBasis.addAll(list);

		transportCostBasis = transportCostBasisList.get(0);
		assertEquals("1111", transportCostBasis.getInvoiceNr());
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });

		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();

		for (OrderCost orderCost : orderCosts) {
			assertEquals("1111", orderCost.getInvoiceNr());
		}

	}

	@Test
	public void testGenerateTransportCostListTwice() throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		List<TransportCostBasis> transportCostBasisList = transportCostBasisManager
				.findById(transportCostBasis.getTransportCostBasisId());
		assertEquals(1, transportCostBasisList.size());

		allTransportCostBasis.addAll(list);

		transportCostBasis = transportCostBasisList.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		assertEquals("Grunnpris:1000", orderCost.getComment());
		BigDecimal result = BigDecimal.valueOf(1000).setScale(2);
		assertEquals(result, orderCost.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_50, WEEK_50));
		assertEquals(0, list.size());

	}

	@Test
	public void skal_sette_dobbel_kostnad_ved_staaende_tak() throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setStaaendeTak();
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		List<TransportCostBasis> transportCostBasisList = transportCostBasisManager
				.findById(transportCostBasis.getTransportCostBasisId());
		assertEquals(1, transportCostBasisList.size());

		allTransportCostBasis.addAll(list);

		transportCostBasis = transportCostBasisList.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		assertEquals("Grunnpris:1000,Stående tak:1000.00", orderCost
				.getComment());
		BigDecimal result = BigDecimal.valueOf(2000).setScale(2);
		assertEquals(result, orderCost.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_50, WEEK_50));
		assertEquals(0, list.size());

	}

	@Test
	public void testGenerateTransportCostListWithAdditionWidth()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setOrderLenghtWidth(860, 620);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000);
		BigDecimal result;
		result = basis.add(basis.multiply(BigDecimal.valueOf(0.5)));
		assertEquals("Grunnpris:1000,Lang garasje:500.00", orderCost
				.getComment());
		assertEquals(result.setScale(2), orderCost.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithAdditionWidthAndTakstein()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setOrderLenghtWidth(860, 620);
		setColliPackedAndSent("Takstein", "Takstein", null);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000)
				.add(BigDecimal.valueOf(660));
		BigDecimal result;
		result = basis.add(basis.multiply(BigDecimal.valueOf(0.5)));
		String costComment=orderCost.getComment();
		assertTrue(costComment.contains("Grunnpris:1000"));
		assertTrue(costComment.contains("Takstein:660.00"));
		assertTrue(costComment.contains("Lang garasje:830.00"));
		assertEquals(result.setScale(2), orderCost.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithTakstein() throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setColliPackedAndSent("Takstein", "Takstein", null);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000);
		BigDecimal result;
		result = basis.add(BigDecimal.valueOf(660));
		assertEquals(result.setScale(2), orderCost.getCostAmount().setScale(2));
		assertEquals("Grunnpris:1000,Takstein:660.00", orderCost.getComment());

	}

	@Test
	public void testGenerateTransportCostListWithTaksteinNotSentFromGG()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		OrderLine orderLine = setColliPackedAndSent("Takstein", "Takstein",
				null);
		orderLine.setOrderLineAttributeValue("Sendes fra GG", "Nei");
		orderManager.saveOrder(order);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000);

		assertEquals(basis.setScale(2), orderCost.getCostAmount().setScale(2));
		assertEquals("Grunnpris:1000", orderCost.getComment());

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setTakstolVinkel(31);
		setColliPackedAndSent("Takstol", "Takstoler", null);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000);
		BigDecimal result;
		result = basis.add(BigDecimal.valueOf(660)).setScale(2);
		assertEquals("Grunnpris:1000,Takstol:660.00", orderCost.getComment());
		assertEquals(result, orderCost.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30AndTakstein()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setTakstolVinkel(31);
		setColliPackedAndSent("Takstein", "Takstein", null);
		setColliPackedAndSent("Takstol", "Takstoler", null);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000);
		BigDecimal result;
		result = basis.add(BigDecimal.valueOf(660)).setScale(2);
		result = result.add(BigDecimal.valueOf(660)).setScale(2);
		assertEquals(result, orderCost.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30AndTaksteinOnlyOneAddition()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("4841");
		setTakstolVinkel(31);
		setColliPackedAndSent("Takstein", "Takstein", null);
		setColliPackedAndSent("Takstol", "Takstoler", null);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1470);
		BigDecimal result;
		result = basis.add(BigDecimal.valueOf(660)).setScale(2);

		assertEquals(result, orderCost.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30AndTaksteinOnlyOneAdditionTakstolAsPostShipment()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("4841");
		setTakstolVinkel(31);
		createPostShipment();
		setColliPackedAndSent("Takstein", "Takstein", null);
		setColliPackedAndSent("Takstol", "Takstoler", postShipment);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1470);
		BigDecimal result;
		result = basis.add(BigDecimal.valueOf(660)).setScale(2);
		assertEquals("Grunnpris:1470,Takstein:660.00", orderCost.getComment());
		assertEquals(result, orderCost.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_51, WEEK_51));

		assertNotNull(list);
		assertEquals(1, list.size());

		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();

		assertEquals("Etterlevering:", orderCost.getComment());
		assertEquals(BigDecimal.valueOf(0), orderCost.getCostAmount());

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30AndTaksteinTwoAdditionTakstolAsPostShipment()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setTakstolVinkel(31);
		createPostShipment();
		setColliPackedAndSent("Takstein", "Takstein", null);
		setColliPackedAndSent("Takstol", "Takstoler", postShipment);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000);
		BigDecimal result;
		result = basis.add(BigDecimal.valueOf(660)).setScale(2);
		assertEquals("Grunnpris:1000,Takstein:660.00", orderCost.getComment());
		assertEquals(result, orderCost.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_51, WEEK_51));

		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();
		assertEquals("Etterlevering:Takstol:660.00", orderCost.getComment());
		assertEquals(BigDecimal.valueOf(660).setScale(2), orderCost
				.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30AndTaksteinOnlyOneAdditionTakstolAndTaksteinAsPostShipment()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("4841");
		setTakstolVinkel(31);
		createPostShipment();
		setColliPackedAndSent("Takstein", "Takstein", postShipment);
		setColliPackedAndSent("Takstol", "Takstoler", postShipment);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1470);
		// BigDecimal result;
		// result = basis.add(BigDecimal.valueOf(660)).setScale(2);
		assertEquals("Grunnpris:1470", orderCost.getComment());
		assertEquals(basis.setScale(2), orderCost.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_51, WEEK_51));

		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();

		assertEquals(BigDecimal.valueOf(660).setScale(2), orderCost
				.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListForPostShipmentWithoutAddition()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		createPostShipment();
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000);
		// BigDecimal result;
		// result = basis.add(BigDecimal.valueOf(660)).setScale(2);
		assertEquals("Grunnpris:1000", orderCost.getComment());
		assertEquals(basis.setScale(2), orderCost.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_51, WEEK_51));

		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();

		assertEquals(BigDecimal.valueOf(0).setScale(2), orderCost
				.getCostAmount().setScale(2));
		assertEquals("Etterlevering:", orderCost.getComment());

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30AndTaksteinTwoAdditionTakstolAndTaksteinAsPostShipment()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setTakstolVinkel(31);
		createPostShipment();
		setColliPackedAndSent("Takstein", "Takstein", postShipment);
		setColliPackedAndSent("Takstol", "Takstoler", postShipment);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();
		BigDecimal basis = BigDecimal.valueOf(1000);
		// BigDecimal result;
		// result = basis.add(BigDecimal.valueOf(660)).setScale(2);
		assertEquals("Grunnpris:1000", orderCost.getComment());
		assertEquals(basis.setScale(2), orderCost.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_51, WEEK_51));

		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();

		assertEquals(BigDecimal.valueOf(1320).setScale(2), orderCost
				.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30AndTaksteinOnlyOneAdditionTakstolAndTaksteinInDifferentPostShipment()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("4841");
		setTakstolVinkel(31);
		createPostShipment();
		createPostShipment2();
		setColliPackedAndSent("Takstein", "Takstein", postShipment);
		setColliPackedAndSent("Takstol", "Takstoler", postShipment2);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_51, WEEK_51));

		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();
		assertEquals("Etterlevering:Takstein:660.00", orderCost.getComment());
		assertEquals(BigDecimal.valueOf(660).setScale(2), orderCost
				.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_52, WEEK_52));

		assertNotNull(list);
		assertEquals(1, list.size());

		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();

		assertEquals(BigDecimal.valueOf(0).setScale(2), orderCost
				.getCostAmount().setScale(2));

	}

	@Test
	public void testGenerateTransportCostListWithTakstolOver30AndTaksteinTwoAdditionTakstolAndTaksteinInDifferentPostShipment()
			throws Exception {
		importAlPostalCodes();
		updatePricesFromFile();
		setUpOrder("0354");
		setTakstolVinkel(31);
		createPostShipment();
		createPostShipment2();
		setColliPackedAndSent("Takstein", "Takstein", postShipment);
		setColliPackedAndSent("Takstol", "Takstoler", postShipment2);
		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(new Periode(YEAR, WEEK_50, WEEK_50));
		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		TransportCostBasis transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		OrderCost orderCost = orderCosts.iterator().next();

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_51, WEEK_51));

		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();
		assertEquals("Etterlevering:Takstein:660.00", orderCost.getComment());
		assertEquals(BigDecimal.valueOf(660).setScale(2), orderCost
				.getCostAmount().setScale(2));

		list = transportCostManager.generateTransportCostList(new Periode(YEAR,
				WEEK_52, WEEK_52));

		assertNotNull(list);
		assertEquals(1, list.size());
		allTransportCostBasis.addAll(list);

		transportCostBasis = list.get(0);

		assertNotNull(transportCostBasis.getTransportCostBasisId());

		list = transportCostBasisManager.findById(transportCostBasis
				.getTransportCostBasisId());
		assertEquals(1, list.size());

		transportCostBasis = list.get(0);
		transportCostBasisManager.lazyLoad(transportCostBasis,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS,
						LazyLoadEnum.NONE } });
		orderCosts = transportCostBasis.getOrderCosts();
		assertNotNull(orderCosts);
		assertEquals(1, orderCosts.size());
		orderCost = orderCosts.iterator().next();
		assertEquals("Etterlevering:Takstol:660.00", orderCost.getComment());
		assertEquals(BigDecimal.valueOf(660).setScale(2), orderCost
				.getCostAmount().setScale(2));
	}

	private void createPostShipment() throws Exception {
		transportPostShipment = new Transport();
		transportPostShipment.setTransportName("testEtterlevering");
		transportPostShipment.setTransportYear(YEAR);
		transportPostShipment.setTransportWeek(WEEK_51);
		transportPostShipment.setSent(Util.SHORT_DATE_FORMAT
				.parse("2008.12.15"));

		SupplierManager supplierManager = (SupplierManager) ModelUtil
				.getBean("supplierManager");
		Supplier supplier = supplierManager.findByName("T.O.Bull AS");
		transportPostShipment.setSupplier(supplier);

		transportManager.saveTransport(transportPostShipment);
		postShipment = new PostShipment();
		postShipment.setSent(Util.SHORT_DATE_FORMAT.parse("2008.12.15"));
		postShipment.setOrder(order);
		postShipment.setTransport(transportPostShipment);
		postShipmentManager.savePostShipment(postShipment);
	}

	private void createPostShipment2() throws Exception {
		transportPostShipment2 = new Transport();
		transportPostShipment2.setTransportName("testEtterlevering2");
		transportPostShipment2.setTransportYear(YEAR);
		transportPostShipment2.setTransportWeek(WEEK_52);
		transportPostShipment2.setSent(Util.SHORT_DATE_FORMAT
				.parse("2008.12.22"));

		SupplierManager supplierManager = (SupplierManager) ModelUtil
				.getBean("supplierManager");
		Supplier supplier = supplierManager.findByName("T.O.Bull AS");
		transportPostShipment2.setSupplier(supplier);

		transportManager.saveTransport(transportPostShipment2);
		postShipment2 = new PostShipment();
		postShipment2.setSent(Util.SHORT_DATE_FORMAT.parse("2008.12.22"));
		postShipment2.setOrder(order);
		postShipment2.setTransport(transportPostShipment2);
		postShipmentManager.savePostShipment(postShipment2);
	}

	private void setTakstolVinkel(Integer vinkel) throws ProTransException {
		OrderLine orderLine = order.getOrderLine("Takstoler");
		OrderLineAttribute attribute = orderLine.getAttributeByName("Vinkel");
		attribute.setAttributeValue(String.valueOf(vinkel));
		orderManager.saveOrder(order);
	}

	private void setStaaendeTak() throws ProTransException {
		OrderLine orderLine = order.getOrderLine("Takstoler");
		OrderLineAttribute attribute = orderLine
				.getAttributeByName("Stående tak");
		attribute.setAttributeValue("Ja");
		orderManager.saveOrder(order);
	}

	private OrderLine setColliPackedAndSent(String colliName,
			String orderLineArticlePath, PostShipment postShipment)
			throws ProTransException {
		Colli colli = new Colli();
		colli.setColliName(colliName);

		OrderLine orderLine = order.getOrderLine(orderLineArticlePath);
		colli.addOrderLine(orderLine);

		if (postShipment != null) {
			postShipment.addOrderLine(orderLine);
			postShipment.addColli(colli);
			colli.setSent(postShipment.getSent());
			colliManager.saveColli(colli);
			postShipmentManager.savePostShipment(postShipment);
		} else {
			order.addColli(colli);
			colli.setSent(order.getSent());
			orderManager.saveOrder(order);
		}
		return orderLine;

	}

	private void setOrderLenghtWidth(int lenght, int width)
			throws ProTransException {
		Set<OrderLine> orderLines = order.getOrderLines();
		String lenghtString = null;
		String widthString = null;
		for (OrderLine orderLine : orderLines) {
			if (lenghtString != null && widthString != null) {
				break;
			}
			if (orderLine.getArticleType() == null
					&& orderLine.getConstructionTypeArticle() == null) {
				Set<OrderLineAttribute> attributes = orderLine
						.getOrderLineAttributes();
				if (attributes != null) {
					for (OrderLineAttribute attribute : attributes) {
						if (attribute.getAttributeName().equalsIgnoreCase(
								"Lengde")) {
							lenghtString = attribute.getAttributeValue();
							attribute.setAttributeValue(String.valueOf(lenght));
							if (widthString != null) {
								break;
							}
						} else if (attribute.getAttributeName()
								.equalsIgnoreCase("Bredde")) {
							widthString = attribute.getAttributeValue();
							attribute.setAttributeValue(String.valueOf(width));
							if (lenghtString != null) {
								break;
							}
						}
					}
				}
			}
		}
		order.setInfo(null);
		orderManager.saveOrder(order);
	}

	private void importAllAreas() throws Exception {
		URL url = getClass().getClassLoader().getResource("testkommuner.xls");
		transportCostManager.importAllAreas(url.getFile(), false);
	}

	private void importAllCounties() throws Exception {
		URL url = getClass().getClassLoader().getResource("testfylker.xls");
		transportCostManager.importAllCounties(url.getFile(), false);
	}

	private void importAlPostalCodes() throws Exception {

		URL url = getClass().getClassLoader().getResource("testpostnr.xls");

		transportCostManager.importAllPostalCodes(url.getFile(), false);

	}

	private void updatePricesFromFile() throws Exception {
		URL url = getClass().getClassLoader().getResource(
				"transportpriser_test.xls");
		transportCostManager.updatePricesFromFile(url.getFile());

	}

	private void setUpOrder(String postalCode) throws Exception {
		transport = new Transport();
		transport.setTransportName("test");
		transport.setTransportYear(YEAR);
		transport.setTransportWeek(WEEK_50);
		transport.setSent(Util.SHORT_DATE_FORMAT.parse("2008.12.08"));

		SupplierManager supplierManager = (SupplierManager) ModelUtil
				.getBean("supplierManager");
		Supplier supplier = supplierManager.findByName("T.O.Bull AS");
		transport.setSupplier(supplier);

		transportManager.saveTransport(transport);

		order = new Order();

		customer = new Customer();

		customer.setCustomerNr(100);
		customer.setFirstName("Atle");
		customer.setLastName("Brekka");
		customerManager.saveCustomer(customer);

		ConstructionType constructionType = constructionTypeManager
				.findByName("H1");

		order.setConstructionType(constructionType);

		order.setOrderNr("test");
		order.setCustomer(customer);
		order.setConstructionType(constructionType);
		order.setDeliveryAddress("testing");
		order.setPostalCode(postalCode);
		order.setPostOffice("Oslo");
		order.setOrderDate(Calendar.getInstance().getTime());

		order.setSent(Util.SHORT_DATE_FORMAT.parse("2008.12.08"));

		order.setTransport(transport);

		ProductArea productArea = productAreaManager
				.findByName("Garasje villa");

		order.setProductArea(productArea);

		setOrderLines(order);

		orderManager.saveOrder(order);

	}

	private void setUpTakstolOrder() throws Exception {
		takstolOrder = new Order();

		takstolCustomer = new Customer();

		takstolCustomer.setCustomerNr(100100);
		takstolCustomer.setFirstName("Ask");
		takstolCustomer.setLastName("Brekka");
		customerManager.saveCustomer(takstolCustomer);

		ConstructionType constructionType = constructionTypeManager
				.findByName("Takstol");

		takstolOrder.setConstructionType(constructionType);

		takstolOrder.setOrderNr("testtakstol");
		takstolOrder.setCustomer(takstolCustomer);

		takstolOrder.setDeliveryAddress("testing");
		takstolOrder.setPostalCode("4841");
		takstolOrder.setPostOffice("Arendal");
		takstolOrder.setOrderDate(Calendar.getInstance().getTime());

		takstolOrder.setSent(Util.SHORT_DATE_FORMAT.parse("2008.12.08"));

		takstolOrder.setTransport(transport);

		ProductArea productArea = productAreaManager.findByName("Takstol");

		takstolOrder.setProductArea(productArea);

		setOrderLines(takstolOrder);

		orderManager.saveOrder(takstolOrder);
	}

	private void setOrderLines(Order order) {
		constructionTypeManager
				.lazyLoad(
						order.getConstructionType(),
						new LazyLoadConstructionTypeEnum[] {
								LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ARTICLE,
								LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ATTRIBUTE });
		Set<ConstructionTypeAttribute> attributes = order.getConstructionType()
				.getConstructionTypeAttributes();

		Set<OrderLine> orderLines = new LinkedHashSet<OrderLine>();
		Set<OrderLineAttribute> orderLineAttributes;
		OrderLine orderLineMain;
		orderLineMain = OrderLine.getInstance(order, 0, null);
		orderLineAttributes = new LinkedHashSet<OrderLineAttribute>();

		for (ConstructionTypeAttribute attribute : attributes) {
			orderLineAttributes.add(new OrderLineAttribute(null, orderLineMain,
					null, attribute, null, attribute.getAttributeValue(),
					attribute.getDialogOrder(), attribute.getAttributeName()));
		}
		orderLineMain.setOrderLineAttributes(orderLineAttributes);
		orderLines.add(orderLineMain);
		orderLineMain.setArticlePath(orderLineMain.getGeneratedArticlePath());

		Set<ConstructionTypeArticle> articles = order.getConstructionType()
				.getConstructionTypeArticles();

		if (articles != null && articles.size() != 0) {
			Set<ConstructionTypeArticleAttribute> articleAttributes;
			if (articles.size() != 0) {
				for (ConstructionTypeArticle article : articles) {
					orderLineMain = OrderLine.getInstance(order, article,
							article.getNumberOfItems(), article
									.getDialogOrder(), null,
							article.getOrdNo(), article.getLnNo());

					constructionTypeManager
							.lazyLoadArticle(
									article,
									new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.ATTRIBUTES });

					articleAttributes = article.getAttributes();

					if (articleAttributes != null
							&& articleAttributes.size() != 0) {
						orderLineAttributes = new LinkedHashSet<OrderLineAttribute>();
						for (ConstructionTypeArticleAttribute articleAttribute : articleAttributes) {
							orderLineAttributes.add(new OrderLineAttribute(
									null, orderLineMain, articleAttribute,
									null, null, articleAttribute
											.getAttributeValue(),
									articleAttribute.getDialogOrder(),
									articleAttribute.getAttributeName()));
						}
						orderLineMain
								.setOrderLineAttributes(orderLineAttributes);
						orderLineMain.setArticlePath(orderLineMain
								.getGeneratedArticlePath());
					}
					setOrderLineConstructionRefs(article, orderLineMain, order);
					orderLines.add(orderLineMain);
					orderLineMain.setArticlePath(orderLineMain
							.getGeneratedArticlePath());
				}
			}

		}

		if (orderLines.size() != 0) {
			calculateAttributes(orderLines);
			order.setOrderLines(orderLines);
		}
	}

	private void setOrderLineConstructionRefs(
			ConstructionTypeArticle constructionArticle,
			OrderLine orderLineMain, Order order) {
		constructionTypeManager
				.lazyLoadArticle(
						constructionArticle,
						new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.CONSTRUCTION_TYPE_ARTICLES });
		Set<ConstructionTypeArticle> articleRefs = constructionArticle
				.getConstructionTypeArticles();
		OrderLine orderLine;
		Set<OrderLineAttribute> orderLineAttributes;
		Set<OrderLine> orderLineRefs = new LinkedHashSet<OrderLine>();
		if (articleRefs != null) {
			for (ConstructionTypeArticle articleRef : articleRefs) {
				orderLine = OrderLine.getInstance(order, articleRef,
						orderLineMain, articleRef.getNumberOfItems(),
						articleRef.getDialogOrder(), null);

				constructionTypeManager
						.lazyLoadArticle(
								articleRef,
								new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.ATTRIBUTES });
				Set<ConstructionTypeArticleAttribute> attributes = articleRef
						.getAttributes();

				if (attributes != null) {
					orderLineAttributes = new LinkedHashSet<OrderLineAttribute>();

					for (ConstructionTypeArticleAttribute attribute : attributes) {
						orderLineAttributes.add(new OrderLineAttribute(null,
								orderLine, attribute, null, null, attribute
										.getAttributeValue(), attribute
										.getDialogOrder(), attribute
										.getAttributeName()));
					}

					orderLine.setOrderLineAttributes(orderLineAttributes);

					setOrderLineConstructionRefs(articleRef, orderLine, order);
				}
				orderLineRefs.add(orderLine);
				orderLine.setArticlePath(orderLine.getGeneratedArticlePath());
			}
			orderLineMain.setOrderLines(orderLineRefs);
		}
	}

	private void calculateAttributes(Collection<OrderLine> orderLines) {
		for (OrderLine orderLine : orderLines) {
			orderLine.calculateAttributes();
		}
	}

}
