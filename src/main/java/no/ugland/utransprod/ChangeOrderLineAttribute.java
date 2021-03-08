/*    */ package no.ugland.utransprod;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.model.OrderLineAttribute;
/*    */ import no.ugland.utransprod.service.OrderLineManager;
/*    */ import no.ugland.utransprod.service.OrderManager;
/*    */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
/*    */ import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
/*    */ import no.ugland.utransprod.util.ModelUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ChangeOrderLineAttribute {
/*    */    public static void changeOrderLineAttributes() {
/* 28 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*    */ 
/* 30 */       OrderLineManager orderLineManager = (OrderLineManager)ModelUtil.getBean("orderLineManager");
/*    */ 
/* 32 */       List<Order> orders = orderManager.findAll();
/*    */ 
/*    */ 
/*    */ 
/* 36 */       Iterator var5 = orders.iterator();      while(var5.hasNext()) {         Order order = (Order)var5.next();
/* 37 */          orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES});
/*    */ 
/* 39 */          Set<OrderLine> orderLines = order.getOrderLines();
/* 40 */          Iterator var7 = orderLines.iterator();         while(var7.hasNext()) {            OrderLine orderLine = (OrderLine)var7.next();
/* 41 */             orderLineManager.lazyLoad(orderLine, new LazyLoadOrderLineEnum[]{LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE});
/*    */ 
/*    */ 
/*    */ 
/* 45 */             if (orderLine.getArticlePath() == null) {
/* 46 */                String path = orderLine.getGeneratedArticlePath();
/* 47 */                orderLine.setArticlePath(path);
/*    */             }
/*    */ 
/* 50 */             Set<OrderLineAttribute> orderLineAttributes = orderLine.getOrderLineAttributes();
/* 51 */             Iterator var12 = orderLineAttributes.iterator();            while(var12.hasNext()) {               OrderLineAttribute attribute = (OrderLineAttribute)var12.next();
/* 52 */                if (attribute.getOrderLineAttributeName() == null) {
/* 53 */                   attribute.setOrderLineAttributeName(attribute.getAttributeName());
/*    */ 
/*    */                }
/*    */             }
/*    */          }
/*    */ 
/*    */          try {
/* 60 */             orderManager.saveOrder(order);
/* 61 */          } catch (ProTransException var11) {
/* 62 */             var11.printStackTrace();
/*    */          }
/*    */       }
/*    */ 
/* 66 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static void main(String[] args) {
/* 72 */       changeOrderLineAttributes();
/* 73 */       System.exit(0);
/*    */ 
/* 75 */    }
/*    */ }
