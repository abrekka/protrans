/*    */ package no.ugland.utransprod;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.service.OrderLineManager;
/*    */ import no.ugland.utransprod.service.OrderManager;
/*    */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
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
/*    */ public class SetOrderLineHasArticle {
/*    */    public static void setOrderLinehasArticle() {
/* 22 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/* 23 */       OrderLineManager orderLineManager = (OrderLineManager)ModelUtil.getBean("orderLineManager");
/* 24 */       Set<Order> orders = orderManager.findNotSent();
/*    */ 
/* 26 */       Iterator var4 = orders.iterator();      while(var4.hasNext()) {         Order order = (Order)var4.next();
/* 27 */          orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES});
/* 28 */          Set<OrderLine> orderLines = order.getOrderLines();
/* 29 */          Iterator var6 = orderLines.iterator();
/*    */          while(var6.hasNext()) {            OrderLine orderLine = (OrderLine)var6.next();
/* 31 */             orderLine.hasArticle();
/* 32 */             orderLineManager.saveOrderLine(orderLine);
/*    */          }      }
/*    */ 
/* 35 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static void main(String[] args) {
/* 41 */       setOrderLinehasArticle();
/* 42 */       System.exit(0);
/*    */ 
/* 44 */    }
/*    */ }
