/*     */ package no.ugland.utransprod;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
/*     */ import no.ugland.utransprod.gui.model.Transportable;
/*     */ import no.ugland.utransprod.model.FrontProductionV;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.Produceable;
/*     */ import no.ugland.utransprod.model.VeggProductionV;
/*     */ import no.ugland.utransprod.service.FrontProductionVManager;
/*     */ import no.ugland.utransprod.service.OrderManager;
/*     */ import no.ugland.utransprod.service.VeggProductionVManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrderCommentCache {
/*     */    public static void main(String[] args) {
/*  31 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*     */ 
/*  33 */       FrontProductionVManager frontProductionVManager = (FrontProductionVManager)ModelUtil.getBean("frontProductionVManager");
/*     */ 
/*  35 */       VeggProductionVManager veggProductionVManager = (VeggProductionVManager)ModelUtil.getBean("veggProductionVManager");
/*     */ 
/*  37 */       List<Order> orders = orderManager.findAllNotSent();
/*     */ 
/*  39 */       Iterator var5 = orders.iterator();      while(var5.hasNext()) {         Order order = (Order)var5.next();
/*  40 */          orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.COMMENTS, LazyLoadOrderEnum.COLLIES});
/*     */ 
/*  42 */          order.cacheComments();
/*  43 */          order.cacheGarageColliHeight();
/*     */          try {
/*  45 */             orderManager.saveOrder(order);
/*  46 */          } catch (ProTransException var17) {
/*     */ 
/*  48 */             var17.printStackTrace();
/*     */          }
/*     */       }
/*     */ 
/*  52 */       List<Produceable> fronter = frontProductionVManager.findAllApplyable();
/*     */ 
/*  54 */       StatusCheckerInterface<Transportable> veggChecker = Util.getVeggChecker();
/*     */ 
/*  56 */       Iterator var7 = fronter.iterator();
/*     */       while(var7.hasNext()) {         Produceable front = (Produceable)var7.next();
/*  58 */          Map<String, String> statusMap = Util.createStatusMap(((FrontProductionV)front).getOrderStatus());
/*     */ 
/*     */ 
/*     */ 
/*  62 */          String status = (String)statusMap.get(veggChecker.getArticleName());
/*  63 */          if (status == null) {
/*     */ 
/*  65 */             Order order = orderManager.findByOrderNr(((FrontProductionV)front).getOrderNr());
/*     */ 
/*  67 */             if (order != null) {
/*  68 */                orderManager.lazyLoadTree(order);
/*  69 */                status = veggChecker.getArticleStatus(order);
/*  70 */                statusMap.put(veggChecker.getArticleName(), status);
/*  71 */                order.setStatus(Util.statusMapToString(statusMap));
/*     */                try {
/*  73 */                   orderManager.saveOrder(order);
/*  74 */                } catch (ProTransException var16) {
/*     */ 
/*  76 */                   var16.printStackTrace();
/*     */ 
/*     */                }
/*     */             }
/*     */          }
/*     */       }
/*     */ 
/*  83 */       List<Produceable> vegger = veggProductionVManager.findAllApplyable();
/*     */ 
/*  85 */       StatusCheckerInterface<Transportable> frontChecker = Util.getFrontChecker();
/*     */ 
/*  87 */       Iterator var22 = vegger.iterator();
/*     */       while(var22.hasNext()) {         Produceable vegg = (Produceable)var22.next();
/*  89 */          Map<String, String> statusMap = Util.createStatusMap(((VeggProductionV)vegg).getOrderStatus());
/*     */ 
/*     */ 
/*  92 */          String status = (String)statusMap.get(frontChecker.getArticleName());
/*  93 */          if (status == null) {
/*     */ 
/*  95 */             Order order = orderManager.findByOrderNr(((VeggProductionV)vegg).getOrderNr());
/*     */ 
/*  97 */             if (order != null) {
/*  98 */                orderManager.lazyLoadTree(order);
/*  99 */                status = frontChecker.getArticleStatus(order);
/* 100 */                statusMap.put(frontChecker.getArticleName(), status);
/* 101 */                order.setStatus(Util.statusMapToString(statusMap));
/*     */                try {
/* 103 */                   orderManager.saveOrder(order);
/* 104 */                } catch (ProTransException var15) {
/*     */ 
/* 106 */                   var15.printStackTrace();
/*     */ 
/*     */                }
/*     */             }
/*     */          }
/*     */       }
/*     */ 
/* 113 */    }
/*     */ }
