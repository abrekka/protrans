/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import no.ugland.utransprod.gui.model.Transportable;
/*    */ import no.ugland.utransprod.model.Colli;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.model.OrderLineAttribute;
/*    */ import no.ugland.utransprod.model.TransportCostAddition;
/*    */ import no.ugland.utransprod.util.Periode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdditionTruss extends AbstractAddition {
/*    */    public AdditionTruss(TransportCostAddition addition, String articlePath, String info) {
/* 16 */       super(addition, articlePath, info);
/* 17 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public final BigDecimal calculateAddition(BigDecimal basis, Transportable transportable, Periode period, boolean ignoreSent) {
/* 22 */       OrderLine orderLine = transportable.getOrderLine(this.articlePath);
/* 23 */       BigDecimal addValue = BigDecimal.valueOf(0L);
/* 24 */       if (orderLine != null) {
/* 25 */          Colli colli = orderLine.getColli();
/* 26 */          if (colli != null) {
/* 27 */             addValue = this.getAddValue(period, ignoreSent, orderLine, addValue, colli, transportable);
/*    */          }
/*    */       }
/*    */ 
/* 31 */       return addValue;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    private BigDecimal getAddValue(Periode period, boolean ignoreSent, OrderLine orderLine, BigDecimal aAddValue, Colli colli, Transportable transportable) {
/* 37 */       BigDecimal addValue = aAddValue;
/* 38 */       if (!ignoreSent) {
/* 39 */          if (colli.getSent() != null) {
/* 40 */             addValue = this.addIfColliIsForTransportable(transportable, colli, orderLine);
/*    */ 
/*    */          }
/*    */       } else {
/* 44 */          addValue = this.checkAndGetAddValue(orderLine);
/*    */       }
/* 46 */       return addValue;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    private BigDecimal addIfColliIsForTransportable(Transportable transportable, Colli colli, OrderLine orderLine) {
/* 52 */       return this.compareColliWithTransportable(transportable, colli) == 0 ? this.checkAndGetAddValue(orderLine) : BigDecimal.valueOf(0L);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    private int compareColliWithTransportable(Transportable transportable, Colli colli) {
/* 60 */       if (transportable.getPostShipment() != null && colli.getPostShipment() == null) {
/*    */ 
/* 62 */          return -1;
/* 63 */       } else if (transportable.getPostShipment() == null && colli.getPostShipment() != null) {
/*    */ 
/* 65 */          return 1;      } else {
/* 66 */          return transportable.getPostShipment() != null && colli.getPostShipment() != null ? transportable.getPostShipment().getPostShipmentId().compareTo(colli.getPostShipment().getPostShipmentId()) : transportable.getOrder().getOrderId().compareTo(colli.getOrder().getOrderId());
/*    */       }
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    private BigDecimal checkAndGetAddValue(OrderLine orderLine) {
/* 77 */       OrderLineAttribute attribute = orderLine.getAttributeByName("Vinkel");
/* 78 */       BigDecimal addValue = BigDecimal.valueOf(0L);
/* 79 */       String value = attribute.getAttributeNumberValue();
/* 80 */       if (Double.valueOf(value) >= Double.valueOf(this.transportCostAdditon.getBasis())) {
/*    */ 
/* 82 */          addValue = this.transportCostAdditon.getAddition();
/*    */       }
/* 84 */       return addValue;
/*    */    }
/*    */ }
