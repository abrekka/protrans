package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.hibernate.QuerySettings;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.report.SumTakstolOwnOrder;

/**
 * Implementasjon av serviceklasse for tabell ORDER_LINE.
 * 
 * @author atle.brekka
 */
public class OrderLineManagerImpl extends AbstractApplyListManager<PackableListItem> implements OrderLineManager {

    private static final String COST_TYPE_TAKSTOLER = "Takstoler";

    private static final String COST_UNIT_INTERN = "Intern";

    private ArticleTypeManager articleTypeManager;

    private OrderManager orderManager;
    private OrdlnManager ordlnManager;
    private ArticleType takstolArticleType = ArticleType.UNKNOWN;
    private Map<Periode, List<Order>> basisMap = new Hashtable<Periode, List<Order>>();

    /**
     * @param manager
     */
    public final void setArticleTypeManager(final ArticleTypeManager manager) {
	this.articleTypeManager = manager;
    }

    /**
     * @param manager
     */
    public final void setOrderManager(final OrderManager manager) {
	this.orderManager = manager;
    }

    public final void setOrdlnManager(final OrdlnManager manager) {
	this.ordlnManager = manager;
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#findUnproducedByArticle(java.lang.String)
     */
    public final List<OrderLine> findUnproducedByArticle(final String articleName) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	List<OrderLine> orders = null;
	if (articleType != null) {
	    orders = orderLineDAO.findUnproducedByArticle(articleType);
	}
	return orders;
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#saveOrderLine(no.ugland.utransprod.model.OrderLine)
     */
    public final void saveOrderLine(final OrderLine orderLine) {
	orderLineDAO.saveObject(orderLine);

    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#lazyLoadOrder(no.ugland.utransprod.model.Order,
     *      no.ugland.utransprod.service.enums.LazyLoadOrderEnum[])
     */
    public final void lazyLoadOrder(final Order order, final LazyLoadOrderEnum[] enums) {
	orderManager.lazyLoadOrder(order, enums);

    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#lazyLoad(no.ugland.utransprod.model.OrderLine,
     *      no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum[])
     */
    public final void lazyLoad(final OrderLine orderLine, final LazyLoadOrderLineEnum[] enums) {
	orderLineDAO.lazyLoad(orderLine, enums);

    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#
     *      countByDate(no.ugland.utransprod.dao.hibernate.QuerySettings)
     */
    public final Integer countByDate(final QuerySettings querySettings) {
	return orderLineDAO.countByDate(querySettings);
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#
     *      findByConstructionTypeArticleAttributes(java.util.List,
     *      no.ugland.utransprod.dao.hibernate.QuerySettings)
     */
    public final List<Order> findByConstructionTypeArticleAttributes(final List<OrderLine> criterias, final QuerySettings querySettings) {
	return orderLineDAO.findByConstructionTypeArticleAttributes(criterias, querySettings);

    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#findUnpackageByArticle(java.lang.String)
     */
    public final List<OrderLine> findUnpackageByArticle(final String articleName) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	List<OrderLine> orders = null;
	if (articleType != null) {
	    orders = orderLineDAO.findUnpackageByArticle(articleType);
	}
	return orders;

    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#saveOrder(no.ugland.utransprod.model.Order)
     */
    public final void saveOrder(final Order order) throws ProTransException {
	orderManager.saveOrder(order);

    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#refreshOrder(no.ugland.utransprod.model.Order)
     */
    public final void refreshOrder(final Order order) {
	orderManager.refreshObject(order);

    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#lazyLoadTree(no.ugland.utransprod.model.OrderLine)
     */
    public final void lazyLoadTree(final OrderLine orderLine) {
	orderLineDAO.lazyLoadTree(orderLine);

    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#
     *      findUnproducedByOrderNrAndArticleName(java.lang.String,
     *      java.lang.String)
     */
    public final List<OrderLine> findUnproducedByOrderNrAndArticleName(final String orderNr, final String articleName) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	return orderLineDAO.findUnproducedByOrderNrAndArticleName(orderNr, articleType);
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#
     *      findUnpackedByOrderNrAndArticleName(java.lang.String,
     *      java.lang.String)
     */
    public final List<OrderLine> findUnpackedByOrderNrAndArticleName(final String orderNr, final String articleName) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	return orderLineDAO.findUnpackedByOrderNrAndArticleName(orderNr, articleType);
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#findByOrderLineId(java.lang.Integer)
     */
    public final OrderLine findByOrderLineId(final Integer orderLineId) {
	return orderLineId != null ? orderLineDAO.getObject(orderLineId) : null;
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#
     *      findUnproducedByCustomerNrAndArticleName(java.lang.Integer,
     *      java.lang.String)
     */
    public final List<OrderLine> findUnproducedByCustomerNrAndArticleName(final Integer customerNr, final String articleName) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	return orderLineDAO.findUnproducedByCustomerNrAndArticleName(customerNr, articleType);
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#
     *      findUnpackedByCustomerNrAndArticleName(java.lang.Integer,
     *      java.lang.String)
     */
    public final List<OrderLine> findUnpackedByCustomerNrAndArticleName(final Integer customerNr, final String articleName) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	return orderLineDAO.findUnpackedByCustomerNrAndArticleName(customerNr, articleType);
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#findByArticleAndAttribute(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public final List<OrderLine> findByArticleAndAttribute(final String articleName, final String attributeName, final String attributeValue) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	List<OrderLine> orders = null;
	if (articleType != null) {
	    orders = orderLineDAO.findByArticleAndAttribute(articleType, attributeName, attributeValue);
	}
	return orders;
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#
     *      findByOrderNrArticleNameAndAttribute(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public final List<OrderLine> findByOrderNrArticleNameAndAttribute(final String orderNr, final String articleName, final String attributeName,
	    final String attributeValue) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	return orderLineDAO.findByOrderNrArticleNameAndAttribute(orderNr, articleType, attributeName, attributeValue);
    }

    /**
     * @see no.ugland.utransprod.service.OrderLineManager#
     *      findByCustomerNrArticleNameAndAttribute(java.lang.Integer,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public final List<OrderLine> findByCustomerNrArticleNameAndAttribute(final Integer customerNr, final String articleName,
	    final String attributeName, final String attributeValue) {
	ArticleType articleType = articleTypeManager.findByName(articleName);
	return orderLineDAO.findByCustomerNrArticleNameAndAttribute(customerNr, articleType, attributeName, attributeValue);
    }

    /**
     * Brukes for pakking av takstein.
     * 
     * @see no.ugland.utransprod.service.IApplyListManager#findAllApplyable()
     */
    public final List<PackableListItem> findAllApplyable() {
	return orderLineDAO.findAllApplyable();
    }

    /**
     * Brukes for pakking av takstein.
     * 
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    public final List<PackableListItem> findApplyableByCustomerNr(final Integer customerNr) {
	return orderLineDAO.findApplyableByCustomerNr(customerNr);
    }

    /**
     * Brukes for pakking av takstein.
     * 
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByOrderNr(java.lang.String)
     */
    public final List<PackableListItem> findApplyableByOrderNr(final String orderNr) {
	return orderLineDAO.findApplyableByOrderNr(orderNr);
    }

    /**
     * Brukes for pakking av takstein.
     * 
     * @param object
     * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
     */
    public final void refresh(final PackableListItem object) {
	orderLineDAO.refresh(object);

    }

    public final List<OrderLine> findAllConstructionTypeNotSent(final ProductArea productArea) {
	return orderLineDAO.findAllConstructionTypeNotSent(productArea);
    }

    public CheckObject checkExcel(ExcelReportSetting params) {
	return null;
    }

    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
	if (params.getExcelReportType() == ExcelReportEnum.TAKSTOL_OWN_ORDER_REPORT) {
	    return getReportData(params);
	} else {
	    return getReportBasisData(params);
	}
    }

    private List<?> getReportBasisData(ExcelReportSetting params) {
	List<Order> orderBasisList = basisMap.get(params.getPeriode());
	if (orderBasisList == null) {
	    orderBasisList = orderLineDAO.findTakstolOwnOrderByPeriode(params.getPeriode());

	}
	setOwnProductionAndInternalCostTakstol(orderBasisList);
	basisMap.put(params.getPeriode(), orderBasisList);
	return orderBasisList;
    }

    private void setOwnProductionAndInternalCostTakstol(List<Order> orderBasisList) {
	for (Order order : orderBasisList) {
	    BigDecimal cstpr = ordlnManager.getSumCcstpr(order.getOrderNr(), takstolArticleType.getProdCatNo());
	    order.setOwnProductionTakstol(cstpr);
	}

    }

    private List<?> getReportData(final ExcelReportSetting params) throws ProTransException {
	initTakstolArticleType();

	List<Order> takstolOrders = orderLineDAO.findTakstolOwnOrderByPeriode(params.getPeriode());
	basisMap.clear();
	basisMap.put(params.getPeriode(), takstolOrders);
	Map<ProductArea, SumTakstolOwnOrder> sumMap = new Hashtable<ProductArea, SumTakstolOwnOrder>();
	for (Order order : takstolOrders) {
	    setCostprAndInternalOrderCost(sumMap, order);
	}
	return new ArrayList<SumTakstolOwnOrder>(sumMap.values());
    }

    private void setCostprAndInternalOrderCost(final Map<ProductArea, SumTakstolOwnOrder> sumMap, final Order order) {
	BigDecimal cstpr = ordlnManager.getSumCcstpr(order.getOrderNr(), takstolArticleType.getProdCatNo());
	SumTakstolOwnOrder sum = getSum(sumMap, order);
	sum.addNumberOfOrders(1);
	sum.addSumOwnProduction(cstpr);
	sum.addSumInternalOrderCost(order.getCost(COST_TYPE_TAKSTOLER, COST_UNIT_INTERN));
	sumMap.put(order.getProductArea(), sum);
    }

    private SumTakstolOwnOrder getSum(final Map<ProductArea, SumTakstolOwnOrder> sumMap, final Order order) {
	SumTakstolOwnOrder sum = sumMap.get(order.getProductArea());
	if (sum == null) {
	    sum = new SumTakstolOwnOrder(order.getProductArea(), 0, BigDecimal.ZERO, BigDecimal.ZERO);
	}
	return sum;
    }

    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
	// TODO Auto-generated method stub
	return null;
    }

    public String getInfoTop(ExcelReportSetting params) {
	// TODO Auto-generated method stub
	return null;
    }

    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
	// TODO Auto-generated method stub
	return null;
    }

    private void initTakstolArticleType() throws ProTransException {
	if (takstolArticleType == ArticleType.UNKNOWN) {
	    ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil.getBean(ArticleTypeManager.MANAGER_NAME);
	    takstolArticleType = articleTypeManager.findByName(Util.getTakstolArticleName());
	    if (takstolArticleType.getProdCatNo() == null) {
		throw new ProTransException("Takstolartikkel har ikke produktkategori satt");
	    }
	}
    }

    public List<PackableListItem> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
	return findApplyableByCustomerNr(customerNr);
    }

    public List<PackableListItem> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
	return findApplyableByOrderNr(orderNr);
    }

	public void fjernColli(Integer orderLineId) {
		orderLineDAO.fjernColli(orderLineId);
		
	}
}
