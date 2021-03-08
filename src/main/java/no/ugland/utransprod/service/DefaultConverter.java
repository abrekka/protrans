/*     */ package no.ugland.utransprod.service;
/*     */ 
/*     */ import com.google.inject.Inject;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.gui.model.OrderModel;
/*     */ import no.ugland.utransprod.model.ArticleType;
/*     */ import no.ugland.utransprod.model.Attribute;
/*     */ import no.ugland.utransprod.model.AttributeChoice;
/*     */ import no.ugland.utransprod.model.Ord;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.OrderLineAttribute;
/*     */ import no.ugland.utransprod.model.Ordln;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultConverter implements ArticleTypeToOrderLineConverter {
/*     */    protected ManagerRepository managerRepository;
/*     */ 
/*     */    @Inject
/*     */    public DefaultConverter(ManagerRepository aManagerRepository) {
/*  27 */       this.managerRepository = aManagerRepository;
/*  28 */    }
/*     */ 
/*     */    public OrderLine convert(ArticleType articleType, Ordln ordln, Order order, Ord ord) {
/*  31 */       return articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN : this.getOrderLine(articleType, ordln, order);
/*     */    }
/*     */ 
/*     */ 
/*     */    protected OrderLine getOrderLine(ArticleType articleType, Ordln ordln, Order order) {
/*  36 */       OrderLine orderLine = new OrderLine();
/*  37 */       orderLine.setArticleType(articleType);
/*  38 */       orderLine.setOrdln(ordln);
/*  39 */       orderLine.setHasArticle(1);
/*  40 */       this.setNumberOfItems(ordln, orderLine);
/*  41 */       orderLine.setOrder(order);
/*  42 */       this.setOrderLineAttributes(articleType, orderLine);
/*  43 */       this.setAttributeHasValue(articleType, orderLine);
/*  44 */       this.setOrdnoAndLnno(ordln, orderLine);
/*  45 */       orderLine.setArticlePath(orderLine.getGeneratedArticlePath());
/*  46 */       setAttributes(orderLine, this.managerRepository.getOrdlnManager());
/*  47 */       return orderLine;
/*     */    }
/*     */ 
/*     */    private void setOrdnoAndLnno(Ordln ordln, OrderLine orderLine) {
/*  51 */       if (ordln != null) {
/*  52 */          orderLine.setOrdNo(ordln.getOrdno());
/*  53 */          orderLine.setLnNo(ordln.getLnno());
/*     */       }
/*  55 */    }
/*     */ 
/*     */    private void setAttributeHasValue(ArticleType articleType, OrderLine orderLine) {
/*  58 */       if (articleType != null) {
/*  59 */          orderLine.setOrderLineAttributeValue("Har " + articleType.getArticleTypeName(), "Ja");
/*     */       }
/*  61 */    }
/*     */ 
/*     */    private void setOrderLineAttributes(ArticleType articleType, OrderLine orderLine) {
/*  64 */       if (articleType != null) {
/*  65 */          orderLine.setOrderLineAttributes(OrderModel.getOrderLinesAttributes(articleType.getArticleTypeAttributes(), orderLine));
/*     */       }
/*     */ 
/*  68 */    }
/*     */ 
/*     */    private void setNumberOfItems(Ordln ordln, OrderLine orderLine) {
/*  71 */       orderLine.setNumberOfItems(Util.convertBigDecimalToInteger(ordln != null ? ordln.getNoinvoab() : BigDecimal.ZERO));
/*     */ 
/*  73 */    }
/*     */ 
/*     */    public static void setAttributes(OrderLine orderLine, OrdlnManager ordlnManager) {
/*  76 */       Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes() != null ? orderLine.getOrderLineAttributes() : new HashSet();
/*     */       Iterator var3 = ((Set)attributes).iterator();
/*     */       while(var3.hasNext()) {
/*  79 */          OrderLineAttribute orderLineAttribute = (OrderLineAttribute)var3.next();
/*  80 */          Attribute attribute = orderLineAttribute.getAttribute();
/*  81 */          if (!setChoices(orderLine, attribute, orderLineAttribute)) {
/*  82 */             setAttributeBoolValue(orderLine, ordlnManager, orderLineAttribute, attribute);
/*     */          }      }
/*     */ 
/*  85 */    }
/*     */ 
/*     */ 
/*     */    private static void setAttributeBoolValue(OrderLine orderLine, OrdlnManager ordlnManager, OrderLineAttribute orderLineAttribute, Attribute attribute) {
/*  89 */       Ordln ordln = attribute.hasProdCatNo() ? ordlnManager.findByOrderNrProdCatNo(orderLine.getOrderNr(), attribute.getProdCatNo(), attribute.getProdCatNo2()) : Ordln.UNKNOWN;
/*     */ 
/*     */ 
/*     */ 
/*  93 */       if (ordln != Ordln.UNKNOWN) {
/*  94 */          orderLineAttribute.setAttributeValueBool(true);
/*     */       }
/*  96 */    }
/*     */ 
/*     */ 
/*     */    private static boolean setChoices(OrderLine orderLine, Attribute attribute, OrderLineAttribute orderLineAttribute) {
/* 100 */       OrdlnManager ordlnManager = (OrdlnManager)ModelUtil.getBean("ordlnManager");
/* 101 */       Set<AttributeChoice> choices = attribute.getAttributeChoices() != null ? attribute.getAttributeChoices() : new HashSet();
/*     */ 
/* 103 */       boolean hasChoices = false;
/* 104 */       boolean notFound = true;      String value;
/* 105 */       for(Iterator choiceIt = ((Set)choices).iterator(); choiceIt.hasNext() && notFound; notFound = value.length() == 0) {
/*     */ 
/* 107 */          AttributeChoice choice = (AttributeChoice)choiceIt.next();
/* 108 */          hasChoices = true;
/* 109 */          Ordln ordln = choice.hasProdCatNo() ? ordlnManager.findByOrderNrProdCatNo(orderLine.getOrderNr(), choice.getProdCatNo(), choice.getProdCatNo2()) : Ordln.UNKNOWN;
/*     */ 
/*     */ 
/*     */ 
/* 113 */          value = ordln != Ordln.UNKNOWN ? choice.getChoiceValue() : "";
/* 114 */          orderLineAttribute.setAttributeValue(value);
/*     */       }
/*     */ 
/* 117 */       return hasChoices;
/*     */    }
/*     */ }
