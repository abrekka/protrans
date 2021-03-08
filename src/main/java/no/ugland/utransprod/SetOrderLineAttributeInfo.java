/*    */ package no.ugland.utransprod;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.service.OrderManager;
/*    */ import no.ugland.utransprod.util.ModelUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetOrderLineAttributeInfo {
/*    */    public static void setOrderLineAttributeInfo() {
/* 20 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*    */ 
/* 22 */       List<Order> orders = orderManager.findAllNotSent();
/*    */ 
/*    */ 
/*    */ 
/* 26 */       Iterator var3 = orders.iterator();      while(var3.hasNext()) {         Order order = (Order)var3.next();
/* 27 */          orderManager.lazyLoadTree(order);
/* 28 */          Set<OrderLine> orderLines = order.getOrderLines();         Iterator var5 = orderLines.iterator();
/* 29 */          while(var5.hasNext()) {
/*    */ 
/*    */ 
/*    */             OrderLine orderLine = (OrderLine)var5.next();
/* 33 */             if (orderLine.getArticlePath().equalsIgnoreCase("Takstoler")) {
/* 34 */                orderLine.setAttributeInfo(orderLine.getAttributesAsString());
/*    */             }
/*    */          }
/*    */ 
/*    */          try {
/* 39 */             orderManager.saveOrder(order);
/* 40 */          } catch (ProTransException var7) {
/*    */ 
/* 42 */             var7.printStackTrace();
/*    */          }
/*    */       }
/*    */ 
/* 46 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static void main(String[] args) {
/* 53 */       setOrderLineAttributeInfo();
/* 54 */       System.exit(0);
/*    */ 
/* 56 */    }
/*    */ }
