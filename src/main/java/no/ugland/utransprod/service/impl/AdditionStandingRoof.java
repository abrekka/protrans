/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.RoundingMode;
/*    */ import no.ugland.utransprod.gui.model.Transportable;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.model.OrderLineAttribute;
/*    */ import no.ugland.utransprod.model.TransportCostAddition;
/*    */ import no.ugland.utransprod.util.Periode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdditionStandingRoof extends AbstractAddition {
/*    */    public AdditionStandingRoof(TransportCostAddition addition, String aArticlePath, String someInfo) {
/* 17 */       super(addition, aArticlePath, someInfo);
/* 18 */    }
/*    */ 
/*    */ 
/*    */    public BigDecimal calculateAddition(BigDecimal basis, Transportable transportable, Periode period, boolean ignoreSent) {
/* 22 */       BigDecimal additionValue = BigDecimal.valueOf(0L);
/* 23 */       if (transportable.getPostShipment() == null) {
/* 24 */          additionValue = this.calculateAdditionForOrder(basis, transportable);
/*    */       }
/* 26 */       return additionValue;
/*    */    }
/*    */ 
/*    */ 
/*    */    private BigDecimal calculateAdditionForOrder(BigDecimal basis, Transportable transportable) {
/* 31 */       BigDecimal additionValue = BigDecimal.valueOf(0L);
/*    */ 
/* 33 */       Order order = transportable.getOrder();
/*    */ 
/* 35 */       OrderLine orderLine = order.getOrderLine("Takstoler");
/* 36 */       if (orderLine != null) {
/* 37 */          OrderLineAttribute attribute = orderLine.getAttributeByName("Stående tak");
/*    */ 
/* 39 */          if (attribute != null && "Ja".equalsIgnoreCase(attribute.getAttributeValue())) {
/*    */ 
/* 41 */             additionValue = basis.multiply(this.transportCostAdditon.getAddition()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
/*    */ 
/*    */ 
/*    */ 
/*    */          }
/*    */       }
/*    */ 
/* 48 */       return additionValue;
/*    */    }
/*    */ }
