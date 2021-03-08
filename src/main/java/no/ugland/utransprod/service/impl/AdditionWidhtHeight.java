/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.RoundingMode;
/*    */ import no.ugland.utransprod.gui.model.Transportable;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.TransportCostAddition;
/*    */ import no.ugland.utransprod.util.Periode;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdditionWidhtHeight extends AbstractAddition {
/*    */    public AdditionWidhtHeight(TransportCostAddition addition, String articlePath, String info) {
/* 14 */       super(addition, articlePath, info);
/* 15 */    }
/*    */ 
/*    */ 
/*    */    public final BigDecimal calculateAddition(BigDecimal basis, Transportable transportable, Periode period, boolean ignoreSent) {
/* 19 */       BigDecimal additionValue = BigDecimal.valueOf(0L);
/* 20 */       if (transportable.getPostShipment() == null) {
/* 21 */          additionValue = this.calculateAdditionForOrder(basis, transportable);
/*    */       }
/* 23 */       return additionValue;
/*    */    }
/*    */ 
/*    */ 
/*    */    private BigDecimal calculateAdditionForOrder(BigDecimal basis, Transportable transportable) {
/* 28 */       BigDecimal additionValue = BigDecimal.valueOf(0L);
/* 29 */       Order order = transportable.getOrder();
/* 30 */       String orderInfo = order.getInfo();
/* 31 */       if (orderInfo == null) {
/* 32 */          orderInfo = order.orderLinesToString();
/*    */       }
/* 34 */       if (orderInfo != null && orderInfo.length() != 0) {
/* 35 */          String[] infoSplit = orderInfo.split("x");
/* 36 */          double totalLenght = Double.valueOf(infoSplit[0]) * 2.0D + Double.valueOf(infoSplit[1]) * 2.0D;
/* 37 */          if (totalLenght >= Double.valueOf(this.transportCostAdditon.getBasis()) && totalLenght < Double.valueOf(this.transportCostAdditon.getBasis2())) {
/*    */ 
/* 39 */             additionValue = basis.multiply(this.transportCostAdditon.getAddition()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
/*    */          }
/*    */       }
/*    */ 
/* 43 */       return additionValue;
/*    */    }
/*    */ }
