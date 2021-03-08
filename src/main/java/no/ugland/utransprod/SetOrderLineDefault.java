/*    */ package no.ugland.utransprod;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
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
/*    */ public class SetOrderLineDefault {
/*    */    public static void setOrderLineDefault() {
/* 24 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*    */ 
/* 26 */       OrderLineManager orderLineManager = (OrderLineManager)ModelUtil.getBean("orderLineManager");
/*    */ 
/* 28 */       List<Order> orders = orderManager.findAll();
/*    */ 
/*    */ 
/*    */ 
/* 32 */       Iterator var4 = orders.iterator();      while(var4.hasNext()) {         Order order = (Order)var4.next();
/* 33 */          orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES});
/*    */ 
/* 35 */          Set<OrderLine> orderLines = order.getOrderLines();
/* 36 */          Iterator var6 = orderLines.iterator();         while(var6.hasNext()) {            OrderLine orderLine = (OrderLine)var6.next();
/* 37 */             orderLineManager.lazyLoad(orderLine, new LazyLoadOrderLineEnum[]{LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE});
/*    */ 
/*    */ 
/*    */ 
/* 41 */             orderLine.isDefault();
/*    */          }
/*    */          try {
/* 44 */             orderManager.saveOrder(order);
/* 45 */          } catch (ProTransException var8) {
/* 46 */             var8.printStackTrace();
/*    */          }
/*    */       }
/*    */ 
/* 50 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static void main(String[] args) {
/* 57 */       setOrderLineDefault();
/* 58 */       System.exit(0);
/*    */ 
/* 60 */    }
/*    */ }
