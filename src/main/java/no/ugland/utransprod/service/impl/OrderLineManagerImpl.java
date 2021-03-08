/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.dao.hibernate.QuerySettings;
/*     */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*     */ import no.ugland.utransprod.model.ArticleType;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.PackableListItem;
/*     */ import no.ugland.utransprod.model.ProductArea;
/*     */ import no.ugland.utransprod.model.ProductAreaGroup;
/*     */ import no.ugland.utransprod.service.ArticleTypeManager;
/*     */ import no.ugland.utransprod.service.OrderLineManager;
/*     */ import no.ugland.utransprod.service.OrderManager;
/*     */ import no.ugland.utransprod.service.OrdlnManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.Periode;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportEnum;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*     */ import no.ugland.utransprod.util.report.SumTakstolOwnOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrderLineManagerImpl extends AbstractApplyListManager<PackableListItem> implements OrderLineManager {
/*     */    private static final String COST_TYPE_TAKSTOLER = "Takstoler";
/*     */    private static final String COST_UNIT_INTERN = "Intern";
/*     */    private ArticleTypeManager articleTypeManager;
/*     */    private OrderManager orderManager;
/*     */    private OrdlnManager ordlnManager;
/*     */    private ArticleType takstolArticleType;
/*     */    private Map<Periode, List<Order>> basisMap;
/*     */ 
/*     */    public OrderLineManagerImpl() {
/*  46 */       this.takstolArticleType = ArticleType.UNKNOWN;
/*  47 */       this.basisMap = new Hashtable();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void setArticleTypeManager(ArticleTypeManager manager) {
/*  53 */       this.articleTypeManager = manager;
/*  54 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void setOrderManager(OrderManager manager) {
/*  60 */       this.orderManager = manager;
/*  61 */    }
/*     */ 
/*     */    public final void setOrdlnManager(OrdlnManager manager) {
/*  64 */       this.ordlnManager = manager;
/*  65 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findUnproducedByArticle(String articleName) {
/*  71 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/*  72 */       List<OrderLine> orders = null;
/*  73 */       if (articleType != null) {
/*  74 */          orders = this.orderLineDAO.findUnproducedByArticle(articleType);
/*     */       }
/*  76 */       return orders;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveOrderLine(OrderLine orderLine) {
/*  83 */       this.orderLineDAO.saveObject(orderLine);
/*     */ 
/*  85 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoadOrder(Order order, LazyLoadOrderEnum[] enums) {
/*  92 */       this.orderManager.lazyLoadOrder(order, enums);
/*     */ 
/*  94 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoad(OrderLine orderLine, LazyLoadOrderLineEnum[] enums) {
/* 101 */       this.orderLineDAO.lazyLoad(orderLine, enums);
/*     */ 
/* 103 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Integer countByDate(QuerySettings querySettings) {
/* 110 */       return this.orderLineDAO.countByDate(querySettings);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Order> findByConstructionTypeArticleAttributes(List<OrderLine> criterias, QuerySettings querySettings) {
/* 119 */       return this.orderLineDAO.findByConstructionTypeArticleAttributes(criterias, querySettings);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findUnpackageByArticle(String articleName) {
/* 127 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/* 128 */       List<OrderLine> orders = null;
/* 129 */       if (articleType != null) {
/* 130 */          orders = this.orderLineDAO.findUnpackageByArticle(articleType);
/*     */       }
/* 132 */       return orders;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveOrder(Order order) throws ProTransException {
/* 140 */       this.orderManager.saveOrder(order);
/*     */ 
/* 142 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshOrder(Order order) {
/* 148 */       this.orderManager.refreshObject(order);
/*     */ 
/* 150 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoadTree(OrderLine orderLine) {
/* 156 */       this.orderLineDAO.lazyLoadTree(orderLine);
/*     */ 
/* 158 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findUnproducedByOrderNrAndArticleName(String orderNr, String articleName) {
/* 166 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/* 167 */       return this.orderLineDAO.findUnproducedByOrderNrAndArticleName(orderNr, articleType);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findUnpackedByOrderNrAndArticleName(String orderNr, String articleName) {
/* 176 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/* 177 */       return this.orderLineDAO.findUnpackedByOrderNrAndArticleName(orderNr, articleType);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final OrderLine findByOrderLineId(Integer orderLineId) {
/* 184 */       return orderLineId != null ? (OrderLine)this.orderLineDAO.getObject(orderLineId) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findUnproducedByCustomerNrAndArticleName(Integer customerNr, String articleName) {
/* 193 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/* 194 */       return this.orderLineDAO.findUnproducedByCustomerNrAndArticleName(customerNr, articleType);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findUnpackedByCustomerNrAndArticleName(Integer customerNr, String articleName) {
/* 203 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/* 204 */       return this.orderLineDAO.findUnpackedByCustomerNrAndArticleName(customerNr, articleType);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findByArticleAndAttribute(String articleName, String attributeName, String attributeValue) {
/* 212 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/* 213 */       List<OrderLine> orders = null;
/* 214 */       if (articleType != null) {
/* 215 */          orders = this.orderLineDAO.findByArticleAndAttribute(articleType, attributeName, attributeValue);
/*     */       }
/* 217 */       return orders;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findByOrderNrArticleNameAndAttribute(String orderNr, String articleName, String attributeName, String attributeValue) {
/* 227 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/* 228 */       return this.orderLineDAO.findByOrderNrArticleNameAndAttribute(orderNr, articleType, attributeName, attributeValue);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<OrderLine> findByCustomerNrArticleNameAndAttribute(Integer customerNr, String articleName, String attributeName, String attributeValue) {
/* 238 */       ArticleType articleType = this.articleTypeManager.findByName(articleName);
/* 239 */       return this.orderLineDAO.findByCustomerNrArticleNameAndAttribute(customerNr, articleType, attributeName, attributeValue);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PackableListItem> findAllApplyable() {
/* 248 */       return this.orderLineDAO.findAllApplyable();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PackableListItem> findApplyableByCustomerNr(Integer customerNr) {
/* 257 */       return this.orderLineDAO.findApplyableByCustomerNr(customerNr);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<PackableListItem> findApplyableByOrderNr(String orderNr) {
/* 266 */       return this.orderLineDAO.findApplyableByOrderNr(orderNr);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refresh(PackableListItem object) {
/* 276 */       this.orderLineDAO.refresh(object);
/*     */ 
/* 278 */    }
/*     */ 
/*     */    public final List<OrderLine> findAllConstructionTypeNotSent(ProductArea productArea) {
/* 281 */       return this.orderLineDAO.findAllConstructionTypeNotSent(productArea);
/*     */    }
/*     */ 
/*     */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 285 */       return null;
/*     */    }
/*     */ 
/*     */    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
/* 289 */       return params.getExcelReportType() == ExcelReportEnum.TAKSTOL_OWN_ORDER_REPORT ? this.getReportData(params) : this.getReportBasisData(params);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private List<?> getReportBasisData(ExcelReportSetting params) {
/* 297 */       List<Order> orderBasisList = (List)this.basisMap.get(params.getPeriode());
/* 298 */       if (orderBasisList == null) {
/* 299 */          orderBasisList = this.orderLineDAO.findTakstolOwnOrderByPeriode(params.getPeriode());
/*     */       }
/*     */ 
/* 302 */       this.setOwnProductionAndInternalCostTakstol(orderBasisList);
/* 303 */       this.basisMap.put(params.getPeriode(), orderBasisList);
/* 304 */       return orderBasisList;
/*     */    }
/*     */ 
/*     */    private void setOwnProductionAndInternalCostTakstol(List<Order> orderBasisList) {
/* 308 */       Iterator var2 = orderBasisList.iterator();      while(var2.hasNext()) {         Order order = (Order)var2.next();
/* 309 */          BigDecimal cstpr = this.ordlnManager.getSumCcstpr(order.getOrderNr(), this.takstolArticleType.getProdCatNo());
/* 310 */          order.setOwnProductionTakstol(cstpr);
/*     */       }
/*     */ 
/* 313 */    }
/*     */ 
/*     */    private List<?> getReportData(ExcelReportSetting params) throws ProTransException {
/* 316 */       this.initTakstolArticleType();
/*     */ 
/* 318 */       List<Order> takstolOrders = this.orderLineDAO.findTakstolOwnOrderByPeriode(params.getPeriode());
/* 319 */       this.basisMap.clear();
/* 320 */       this.basisMap.put(params.getPeriode(), takstolOrders);
/* 321 */       Map<ProductArea, SumTakstolOwnOrder> sumMap = new Hashtable();      Iterator var4 = takstolOrders.iterator();      while(var4.hasNext()) {
/* 322 */          Order order = (Order)var4.next();
/* 323 */          this.setCostprAndInternalOrderCost(sumMap, order);
/*     */       }
/* 325 */       return new ArrayList(sumMap.values());
/*     */    }
/*     */ 
/*     */    private void setCostprAndInternalOrderCost(Map<ProductArea, SumTakstolOwnOrder> sumMap, Order order) {
/* 329 */       BigDecimal cstpr = this.ordlnManager.getSumCcstpr(order.getOrderNr(), this.takstolArticleType.getProdCatNo());
/* 330 */       SumTakstolOwnOrder sum = this.getSum(sumMap, order);
/* 331 */       sum.addNumberOfOrders(1);
/* 332 */       sum.addSumOwnProduction(cstpr);
/* 333 */       sum.addSumInternalOrderCost(order.getCost("Takstoler", "Intern"));
/* 334 */       sumMap.put(order.getProductArea(), sum);
/* 335 */    }
/*     */ 
/*     */    private SumTakstolOwnOrder getSum(Map<ProductArea, SumTakstolOwnOrder> sumMap, Order order) {
/* 338 */       SumTakstolOwnOrder sum = (SumTakstolOwnOrder)sumMap.get(order.getProductArea());
/* 339 */       if (sum == null) {
/* 340 */          sum = new SumTakstolOwnOrder(order.getProductArea(), 0, BigDecimal.ZERO, BigDecimal.ZERO);
/*     */       }
/* 342 */       return sum;
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
/* 347 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getInfoTop(ExcelReportSetting params) {
/* 352 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 357 */       return null;
/*     */    }
/*     */ 
/*     */    private void initTakstolArticleType() throws ProTransException {
/* 361 */       if (this.takstolArticleType == ArticleType.UNKNOWN) {
/* 362 */          ArticleTypeManager articleTypeManager = (ArticleTypeManager)ModelUtil.getBean("articleTypeManager");
/* 363 */          this.takstolArticleType = articleTypeManager.findByName(Util.getTakstolArticleName());
/* 364 */          if (this.takstolArticleType.getProdCatNo() == null) {
/* 365 */             throw new ProTransException("Takstolartikkel har ikke produktkategori satt");
/*     */          }      }
/*     */ 
/* 368 */    }
/*     */ 
/*     */    public List<PackableListItem> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/* 371 */       return this.findApplyableByCustomerNr(customerNr);
/*     */    }
/*     */ 
/*     */    public List<PackableListItem> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 375 */       return this.findApplyableByOrderNr(orderNr);
/*     */    }
/*     */ 
/*     */    public void fjernColli(Integer orderLineId) {
/* 379 */       this.orderLineDAO.fjernColli(orderLineId);
/*     */ 
/* 381 */    }
/*     */ }
