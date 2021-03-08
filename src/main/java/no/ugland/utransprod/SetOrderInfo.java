/*    */ package no.ugland.utransprod;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.model.Order;
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
/*    */ public class SetOrderInfo {
/*    */    public static void setInfo() {
/* 20 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*    */ 
/*    */ 
/* 23 */       List<Order> orders = orderManager.findAll();
/*    */       Iterator var2 = orders.iterator();      while(var2.hasNext()) {
/* 25 */          Order order = (Order)var2.next();
/* 26 */          orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.ORDER_COSTS});
/* 27 */          order.setInfo(order.orderLinesToString());
/*    */ 
/*    */          try {
/* 30 */             orderManager.saveOrder(order);
/* 31 */          } catch (ProTransException var5) {
/*    */ 
/* 33 */             var5.printStackTrace();
/*    */          }
/*    */       }
/*    */ 
/* 37 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static void main(String[] args) {
/* 43 */       setInfo();
/* 44 */       System.exit(0);
/*    */ 
/* 46 */    }
/*    */ }
