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
/*    */ public class AdditionWidhtHeightMax extends AbstractAddition {
/*    */    public AdditionWidhtHeightMax(TransportCostAddition addition, String articlePath, String info) {
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
/*    */    private BigDecimal calculateAdditionForOrder(BigDecimal basis, Transportable transportable) {
/* 27 */       BigDecimal additionValue = BigDecimal.valueOf(0L);
/* 28 */       Order order = transportable.getOrder();
/* 29 */       String orderInfo = order.getInfo();
/* 30 */       if (orderInfo == null) {
/* 31 */          orderInfo = order.orderLinesToString();
/*    */       }
/* 33 */       if (orderInfo != null && orderInfo.length() != 0) {
/* 34 */          String[] infoSplit = orderInfo.split("x");
/* 35 */          double totalLenght = Double.valueOf(infoSplit[0]) * 2.0D + Double.valueOf(infoSplit[1]) * 2.0D;
/* 36 */          if (totalLenght >= Double.valueOf(this.transportCostAdditon.getBasis())) {
/* 37 */             additionValue = basis.multiply(this.transportCostAdditon.getAddition()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
/*    */          }
/*    */       }
/*    */ 
/* 41 */       return additionValue;
/*    */    }
/*    */ }
