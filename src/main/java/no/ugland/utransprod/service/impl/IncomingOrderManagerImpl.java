package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.ArticleTypeDAO;
import no.ugland.utransprod.dao.ContactDAO;
import no.ugland.utransprod.dao.CustomerDAO;
import no.ugland.utransprod.dao.ImportOrderVDAO;
import no.ugland.utransprod.dao.OrdlnDAO;
import no.ugland.utransprod.dao.UdsalesmallDAO;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.Contact;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.ImportOrderV;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.Phone;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Udsalesmall;
import no.ugland.utransprod.service.ArticleTypeToOrderLineConverter;
import no.ugland.utransprod.service.ArticleTypeToOrderLineSelector;
import no.ugland.utransprod.service.ConstructionTypeAttributesConverter;
import no.ugland.utransprod.service.ConstructionTypeAttributesConverterSelector;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.CostTypeManager;
import no.ugland.utransprod.service.CostUnitManager;
import no.ugland.utransprod.service.IncomingOrderManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ProbabilityEnum;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Implementasjon av serviceklasse for inkomne ordre.
 * 
 * @author atle.brekka
 */
public class IncomingOrderManagerImpl extends ManagerImpl<Order> implements IncomingOrderManager {
	private static final String ARTICLE_TAKTOL = "Takstoler";

	private static final String ARTICLE_GAVL = "Gavl";

	private static final String ATTRIBUTE_VINKEL = "Vinkel";

	private static final String ATTRIBUTE_BREDDE = "Bredde";

	private ContactDAO contactDao;

	private CustomerDAO customerDao;

	private UdsalesmallDAO udsalesmallDao;

	private ImportOrderVDAO importOrderVDAO;
	private OrdlnDAO ordlnDAO;
	private ArticleTypeDAO articleTypeDAO;
	private OrdlnManager ordlnManager;
	private ConstructionTypeManager constructionTypeManager;

	private CostTypeManager costTypeManager;

	private CostUnitManager costUnitManager;
	private static boolean test = false;

	public void setCostTypeManager(CostTypeManager aCostTypeManager) {
		this.costTypeManager = aCostTypeManager;
	}

	public void setCostUnitManager(CostUnitManager aCostUnitManager) {
		this.costUnitManager = aCostUnitManager;
	}

	public final void setConstructionTypeManager(final ConstructionTypeManager aConstructionTypeManager) {
		this.constructionTypeManager = aConstructionTypeManager;
	}

	public final void setOrdlnManager(final OrdlnManager aOrdlnManager) {
		this.ordlnManager = aOrdlnManager;
	}

	/**
	 * @param aContactDao
	 */
	public final void setContactDAO(final ContactDAO aContactDao) {
		this.contactDao = aContactDao;
	}

	/**
	 * @param aCustomerDao
	 */
	public final void setCustomerDAO(final CustomerDAO aCustomerDao) {
		this.customerDao = aCustomerDao;
	}

	public final void setUdsalesmallDAO(final UdsalesmallDAO aUdsalesmallDao) {
		this.udsalesmallDao = aUdsalesmallDao;
	}

	public final void setImportOrderVDAO(final ImportOrderVDAO aImportOrderVDAO) {
		this.importOrderVDAO = aImportOrderVDAO;
	}

	public final void setOrdlnDAO(final OrdlnDAO aOrdlnDAO) {
		this.ordlnDAO = aOrdlnDAO;
	}

	public final void setArticleTypeDAO(final ArticleTypeDAO aArticleTypeDAO) {
		this.articleTypeDAO = aArticleTypeDAO;
	}

	/**
	 * @see no.ugland.utransprod.service.OverviewManager#findAll()
	 */
	public final List<Order> findAll() {
		return findByOrderNr(null);
	}

	/**
	 * Finner fornavn.
	 * 
	 * @param name
	 * @return fornavn
	 */
	private String getFirstName(final String name) {
		int index = name.lastIndexOf(" ");
		if (index > -1) {
			return name.substring(0, index);
		}
		return name;
	}

	/**
	 * Finner etternavn.
	 * 
	 * @param name
	 * @return etternavn
	 */
	private String getLastName(final String name) {
		int index = name.lastIndexOf(" ");
		if (index > -1) {
			return name.substring(index + 1);
		}
		return name;
	}

	/**
	 * Gjør ingenting.
	 * 
	 * @param object
	 * @return ordre
	 * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
	 */
	public final List<Order> findByObject(final Order object) {
		return null;
	}

	/**
	 * Gjør ingenting.
	 * 
	 * @param object
	 * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
	 */
	public final void refreshObject(final Order object) {
	}

	/**
	 * Gjør ingenting.
	 * 
	 * @param object
	 * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
	 */
	public final void removeObject(final Order object) {
	}

	/**
	 * Gjør ingenting.
	 * 
	 * @param object
	 * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
	 */
	public final void saveObject(final Order object) {
	}

	/**
	 * @see no.ugland.utransprod.service.IncomingOrderManager#findByOrderNr(java.lang.String)
	 */
	public final List<Order> findByOrderNr(final String orderNr) {
		List<ImportOrderV> imports = importOrderVDAO.getObjects();
		List<Order> incomingOrders = new ArrayList<Order>();
		if (imports != null) {
			for (ImportOrderV importOrderV : imports) {
				createIncomingOrders(orderNr, incomingOrders, importOrderV);
			}
		}
		return incomingOrders;
	}

	private void createIncomingOrders(final String orderNr, final List<Order> incomingOrders,
			final ImportOrderV importOrderV) {

		if (orderShouldBeImported(orderNr, importOrderV)) {
			Order incomingOrder = createOrder(importOrderV);
			incomingOrders.add(incomingOrder);
		}
	}

	private Order createOrder(final ImportOrderV importOrderV) {
		Order incomingOrder = new Order();
		incomingOrder.setOrderNr(importOrderV.getNumber1());
		Contact contact = getContact(importOrderV);
		incomingOrder.setCustomer(getCustomer(contact));

		setOrderDate(importOrderV, incomingOrder);
		incomingOrder.setAgreementDate(Util.getCurrentDate());

		incomingOrder.setSalesman(importOrderV.getSalesMan());
		setDeliveryAddress(incomingOrder, importOrderV);
		setTelephoneNr(incomingOrder, contact);
		setProductArea(incomingOrder, importOrderV);
		incomingOrder.setProbability(importOrderV.getProbability());
		incomingOrder.setTelephoneNrSite(importOrderV.getTelephoneNrSite());
		setPacklistReady(incomingOrder);
		incomingOrder.setMaxTrossHeight(importOrderV.getMaksHoyde());

		return incomingOrder;
	}

	private Contact getContact(final ImportOrderV importOrderV) {
		Contact contact = null;
		if (importOrderV.getContactId() != null) {
			contact = contactDao.getObject(importOrderV.getContactId());
			lazyLoadContact(contact);
		}
		return contact;
	}

	private void lazyLoadContact(Contact contact) {
		if (contact != null) {
			contactDao.lazyLoad(contact, contact.getContactId(),
					new LazyLoadEnum[][] { { LazyLoadEnum.ADDRESSES, LazyLoadEnum.NONE } });
		}
	}

	private boolean orderShouldBeImported(String orderNr, ImportOrderV importOrderV) {
		return (orderNr == null || (orderNr.equalsIgnoreCase(importOrderV.getNumber1())))
				&& orderHasOwnProduction(importOrderV);
	}

	private void setPacklistReady(Order order) {
		if (shouldSetPacklistReady(order) && probabilityIs100(order)) {
			order.setPacklistReady(Util.getCurrentDate());
		}
	}

	private boolean probabilityIs100(Order order) {
		return order.getProbability() != null
				&& order.getProbability().equals(ProbabilityEnum.PROBABILITY_CONFRIM_ORDER.getProbability());
	}

	private boolean shouldSetPacklistReady(Order order) {
		if (order != null && order.getProductArea() != null) {
			return Util.convertNumberToBoolean(order.getProductArea().getPacklistReady());
		}
		return false;
	}

	private boolean orderHasOwnProduction(ImportOrderV importOrderV) {
		boolean hasOwnProduction = false;
		if (importOrderV.getUserdefId() != null) {
			Udsalesmall udsalesmall = udsalesmallDao.getObject(importOrderV.getUserdefId());
			if (udsalesmall != null && udsalesmall.getEgenprodVerdiKunde() != null
					&& udsalesmall.getEgenprodVerdiKunde().intValue() != 0) {
				hasOwnProduction = true;
			}
		}
		return hasOwnProduction;
	}

	private void setProductArea(final Order order, final ImportOrderV importOrderV) {
		if (importOrderV.getProductAreaNr() != null) {
			ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean("productAreaManager");
			ProductArea productArea = productAreaManager.getProductAreaForProductAreaNr(importOrderV.getProductAreaNr(),
					!test);
			if (productArea != null) {
				order.setProductArea(productArea);
			}
		}
	}

	public void setCosts(final Order incomingOrder) throws ProTransException {
		setCostsFromUdsalesmall(incomingOrder);
		setCostsFromVismaOrderLines(incomingOrder);
	}

	private void setCostsFromVismaOrderLines(Order incomingOrder) {
		List<Ordln> costOrderLines = ordlnManager.findCostLines(incomingOrder.getOrderNr());
		Set<OrderCost> orderCosts = new HashSet<OrderCost>();
		for (Ordln ordln : costOrderLines) {
			Ordln.CostLine costLine = Ordln.CostLine
					.getCostLine(ordln.getProd() != null ? ordln.getProd().getProdNo() : "");
			if (costLine != null) {
				orderCosts.add(costLine.addCost(costTypeManager, costUnitManager, incomingOrder, ordln.getAm()));
			}
		}
		incomingOrder.addOrderCosts(orderCosts);
	}

	private void setCostsFromUdsalesmall(final Order incomingOrder) throws ProTransException {
		ImportOrderV importOrderV = importOrderVDAO.findByNumber1(incomingOrder.getOrderNr());
		if (importOrderV == null) {
			throw new ProTransException("Kan ikke finne importordre");
		}
		if (importOrderV.getUserdefId() != null) {
			Udsalesmall udsalesmall = udsalesmallDao.getObject(importOrderV.getUserdefId());
			setCustomerCost(incomingOrder, udsalesmall);
		}
	}

	public void setCustomerCost(final Order incomingOrder, final Udsalesmall udsalesmall) {
		if (udsalesmall != null) {
			Set<OrderCost> orderCosts = new HashSet<OrderCost>();

			if (udsalesmall.getEgenprodVerdiKunde() != null) {
				orderCosts.add(getOrderCost("Egenproduksjon", costTypeManager, udsalesmall.getEgenprodVerdiKunde(),
						incomingOrder, costUnitManager, "Kunde"));
			}
			if (udsalesmall.getKostEgenProd() != null) {
				orderCosts.add(getOrderCost("Egenproduksjon", costTypeManager, udsalesmall.getKostEgenProd(),
						incomingOrder, costUnitManager, "Intern"));
			}
			if (udsalesmall.getFraktKunde() != null) {
				orderCosts.add(getOrderCost("Frakt", costTypeManager, udsalesmall.getFraktKunde(), incomingOrder,
						costUnitManager, "Kunde"));
			}
			if (udsalesmall.getJaLinjer() != null) {
				orderCosts.add(getOrderCost("Jalinjer", costTypeManager, udsalesmall.getJaLinjer(), incomingOrder,
						costUnitManager, "Kunde"));
			}
			incomingOrder.addOrderCosts(orderCosts);
		}
	}

	private OrderCost getOrderCost(final String costTypeName, final CostTypeManager costTypeManager,
			final BigDecimal costAmount, final Order order, final CostUnitManager costUnitManager,
			final String costUnitName) {
		CostType costType = costTypeManager.findByName(costTypeName);
		CostUnit costUnit = costUnitManager.findByName(costUnitName);
		OrderCost orderCost = order.getOrderCost(costTypeName, costUnitName);
		orderCost = orderCost != null ? orderCost : new OrderCost();
		orderCost.setCostAmount(costAmount);
		orderCost.setCostType(costType);
		orderCost.setCostUnit(costUnit);
		orderCost.setOrder(order);
		return orderCost;
	}

	private void setTelephoneNr(final Order incomingOrder, final Contact contact) {
		if (contact != null) {
			Set<Phone> phones = contact.getPhones();
			if (phones != null && phones.size() != 0) {
				StringBuffer buffer = new StringBuffer();
				int counter = 0;
				for (Phone phone : phones) {
					counter++;
					if (counter > 1) {
						buffer.append(";");
					}
					buffer.append(phone.getPhone());
				}
				incomingOrder.setTelephoneNr(buffer.toString());

			}
		}
	}

	// Skal hentes fra VISMA legge dett inn i import view
	// private void setDeliveryAddress(final Order incomingOrder, final Contact
	// contact) {
	private void setDeliveryAddress(final Order incomingOrder, final ImportOrderV importOrderV) {
		incomingOrder.setDeliveryAddress(importOrderV.getDeliveryAddress());
		incomingOrder.setPostalCode(importOrderV.getPostalCode());
		incomingOrder.setPostOffice(importOrderV.getPostoffice());
	}

	private void setOrderDate(final ImportOrderV importOrderV, final Order incomingOrder) {
		Udsalesmall udsalesmall = udsalesmallDao.getObject(importOrderV.getUserdefId());
		if (udsalesmall.getOrderDate() != null && udsalesmall.getOrderDate().intValue() != 0) {
			incomingOrder.setOrderDate(Util.convertIntToDate(udsalesmall.getOrderDate()));

		}
		incomingOrder.setRegistrationDate(importOrderV.getRegistered());
		if (udsalesmall.getWantedDeliveryDate() != null && udsalesmall.getWantedDeliveryDate().intValue() != 0) {
			incomingOrder.setDeliveryWeek(Util.getWeekPart(Util.convertIntToDate(udsalesmall.getWantedDeliveryDate())));
		}
	}

	private Customer getCustomer(final Contact contact) {
		Customer customer = null;
		if (contact != null) {
			customer = customerDao.findByCustomerNr(Integer.valueOf(contact.getNumber2()));
			if (customer == null) {
				customer = new Customer();
				customer.setCustomerNr(Integer.valueOf(contact.getNumber2()));
				customer.setFirstName(getFirstName(contact.getName()));
				customer.setLastName(getLastName(contact.getName()));
			}
		}
		return customer;
	}

	public void setOrderLines(final Order incomingOrder, ManagerRepository managerRepository) {
		Ord ord = ordlnManager.findOrdByOrderNr(incomingOrder.getOrderNr());
		List<Ordln> ordlnList = ordlnDAO.findByOrderNr(incomingOrder.getOrderNr());

		Multimap<ArticleTypeToOrderLineSelector, Ordln> selectors = gelSelectorMap(ordlnList);

		setOrderLines(incomingOrder, selectors, ord, managerRepository);
		setConstructionTypeAttributes(incomingOrder, ord);

	}

	private Multimap<ArticleTypeToOrderLineSelector, Ordln> gelSelectorMap(List<Ordln> ordlnList) {
		Multimap<ArticleTypeToOrderLineSelector, Ordln> selectors = ArrayListMultimap.create();

		for (Ordln ordln : ordlnList) {
			ArticleType articleType = getArticleTypeByProdCatNo(ordln);
			ordln.setArticleType(articleType);
			ArticleTypeToOrderLineSelector selector = ArticleTypeToOrderLineSelector
					.getConverter(articleType.getArticleTypeName());
			selectors.put(selector, ordln);
		}
		return selectors;
	}

	private void setOrderLines(final Order incomingOrder,
			final Multimap<ArticleTypeToOrderLineSelector, Ordln> selectors, Ord ord,
			ManagerRepository managerRepository) {
		Set<ArticleTypeToOrderLineSelector> keys = new TreeSet<ArticleTypeToOrderLineSelector>(selectors.keySet());

		Set<OrderLine> orderLines = addConstructionTypeWithDefaultOrderLines(ord, null, incomingOrder);

		incomingOrder.setOrderLines(orderLines);

		for (ArticleTypeToOrderLineSelector selector : keys) {

			for (Ordln ordln : selectors.get(selector)) {
				addOrderLine(incomingOrder, ordln, selector, managerRepository);
			}
		}

		// Set<OrderLine>
		orderLines = addConstructionTypeWithDefaultOrderLines(ord, incomingOrder.getOrderLines(), incomingOrder);

		if (orderLines != null) {
			incomingOrder.setOrderLines(orderLines);
			setGavlVinkelOgBredde(incomingOrder);
		}

		if (incomingOrder.getOrderLines() != null) {
			for (OrderLine orderLine : incomingOrder.getOrderLines()) {
				orderLine.calculateAttributes();

				if (orderLine.getArticleName().equalsIgnoreCase("Takstein")) {
					Colli newColli = new Colli(null, incomingOrder, "Takstein", null, null, null, null, null, null,
							"Import");
					incomingOrder.addColli(newColli);
					newColli.addOrderLine(orderLine);
//				managerRepository.getColliManager().saveColli(newColli);
				}
			}
		}
	}

	private void setGavlVinkelOgBredde(Order incomingOrder) {
		OrderLine takstol = incomingOrder.getOrderLine(ARTICLE_TAKTOL);
		if (takstol != OrderLine.UNKNOWN) {
			OrderLine gavl = incomingOrder.getOrderLine(ARTICLE_GAVL);
			if (gavl != OrderLine.UNKNOWN) {
				String takstolVinkel = takstol.getAttributeValue(ATTRIBUTE_VINKEL);
				String takstolBredde = takstol.getAttributeValue(ATTRIBUTE_BREDDE);
				if (!StringUtils.isEmpty(takstolVinkel)) {
					gavl.setAttributeValue(ATTRIBUTE_VINKEL, takstolVinkel);
				}
				if (!StringUtils.isEmpty(takstolBredde)) {
					gavl.setAttributeValue(ATTRIBUTE_BREDDE, takstolBredde);
				}
			}
		}

	}

	private void setConstructionTypeAttributes(Order incomingOrder, Ord ord) {
		ConstructionTypeAttributesConverter converter = ConstructionTypeAttributesConverterSelector
				.valueOf(StringUtils.upperCase(incomingOrder.getProductAreaGroup().getProductAreaGroupName()))
				.getConverter(ordlnManager, costTypeManager, costUnitManager);
		if (converter != null) {
			converter.setConstructionTypeAttributes(ord, incomingOrder);
		}

	}

	private Set<OrderLine> addConstructionTypeWithDefaultOrderLines(final Ord ord,
			Collection<OrderLine> originalOrderLines, Order order) {
		ConstructionType constructionType = getConstructiontype(ord);
		if (constructionType != null) {
			order.setConstructionType(constructionType);
		}
		return constructionTypeManager.getOrderLinesForNewConstructionType(originalOrderLines, constructionType, order,
				null);

	}

	private ConstructionType getConstructiontype(Ord ord) {
		return hasConstructionTypeDeclared(ord) ? constructionTypeManager.findByName(ord.getInf()) : null;
	}

	private boolean hasConstructionTypeDeclared(Ord ord) {
		return ord != null && ord.getInf() != null ? true : false;
	}

	private void addOrderLine(final Order incomingOrder, Ordln ordln, ArticleTypeToOrderLineSelector selector,
			ManagerRepository managerRepository) {
		OrderLine orderLine = getOrderLine(ordln, incomingOrder, selector, managerRepository);
		if (orderLine != OrderLine.UNKNOWN) {
			incomingOrder.addOrderLine(orderLine);
		}
	}

	private OrderLine getOrderLine(Ordln ordln, Order order, ArticleTypeToOrderLineSelector selector,
			ManagerRepository managerRepository) {
		ArticleTypeToOrderLineConverter converter = selector.getConverter(managerRepository);
		return converter.convert(ordln.getArticleType(), ordln, order, null);
	}

	private ArticleType getArticleTypeByProdCatNo(Ordln ordln) {
		ArticleType articleType = ArticleType.UNKNOWN;
		if (ordln.getProd() != null) {
			if (ordln.getProd().getPrCatNo2() != null && ordln.getProd().getPrCatNo2().equals(3)) {// dersom
				// kledning
				articleType = articleTypeDAO.findByProdCatNoAndProdCatNo2(null, ordln.getProd().getPrCatNo2());
			} else {
				articleType = articleTypeDAO.findByProdCatNoAndProdCatNo2(ordln.getProd().getPrCatNo(),
						ordln.getProd().getPrCatNo2());
			}
		}
		return articleType;
	}

	@SuppressWarnings("unchecked")
	public void lazyLoad(Order object, Enum[] enums) {
	}

	@Override
	protected Serializable getObjectId(Order object) {
		return object.getOrderId();
	}

	public static void setTest(boolean test) {
		IncomingOrderManagerImpl.test = test;
	}

}
