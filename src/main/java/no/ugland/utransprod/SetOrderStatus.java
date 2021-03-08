/*    */ package no.ugland.utransprod;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
/*    */ import no.ugland.utransprod.gui.model.Transportable;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.service.ApplicationParamManager;
/*    */ import no.ugland.utransprod.service.OrderManager;
/*    */ import no.ugland.utransprod.util.ModelUtil;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetOrderStatus {
/*    */    public static void setStatus() {
/* 23 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/* 24 */       ApplicationParamManager applicationParamManager = (ApplicationParamManager)ModelUtil.getBean("applicationParamManager");
/* 25 */       List<Order> orders = orderManager.getAllNewOrders();
/*    */ 
/*    */ 
/* 28 */       String steinArticleName = applicationParamManager.findByName("stein_artikkel");
/* 29 */       StatusCheckerInterface<Transportable> steinChecker = Util.getSteinChecker();      Iterator var6 = orders.iterator();      while(var6.hasNext()) {
/* 30 */          Order order = (Order)var6.next();
/* 31 */          if (order.getStatus() == null) {
/* 32 */             orderManager.lazyLoadTree(order);
/* 33 */             String status = steinChecker.getArticleStatus(order);
/* 34 */             order.setStatus(steinArticleName + ";" + status);
/*    */             try {
/* 36 */                orderManager.saveOrder(order);
/* 37 */             } catch (ProTransException var9) {
/*    */ 
/* 39 */                var9.printStackTrace();
/*    */             }
/*    */          }
/*    */       }
/*    */ 
/* 44 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static void main(String[] args) {
/* 50 */       setStatus();
/* 51 */       System.exit(0);
/*    */ 
/* 53 */    }
/*    */ }
