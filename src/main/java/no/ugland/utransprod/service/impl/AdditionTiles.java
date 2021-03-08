/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import no.ugland.utransprod.gui.model.Transportable;
/*    */ import no.ugland.utransprod.model.Colli;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.model.TransportCostAddition;
/*    */ import no.ugland.utransprod.util.Periode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdditionTiles extends AbstractAddition {
/*    */    public AdditionTiles(TransportCostAddition addition, String articlePath, String info) {
/* 15 */       super(addition, articlePath, info);
/* 16 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final BigDecimal calculateAddition(BigDecimal basis, Transportable transportable, Periode period, boolean ignoreSent) {
/* 22 */       BigDecimal addValue = BigDecimal.valueOf(0L);
/* 23 */       OrderLine orderLine = transportable.getOrderLine(this.articlePath);
/* 24 */       if (orderLine != null) {
/* 25 */          addValue = this.checkAndAddAddition(transportable, period, ignoreSent, orderLine);
/*    */       }
/*    */ 
/* 28 */       return addValue;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    private BigDecimal checkAndAddAddition(Transportable transportable, Periode period, boolean ignoreSent, OrderLine orderLine) {
/* 34 */       BigDecimal addValue = BigDecimal.valueOf(0L);
/* 35 */       if (this.sendingFromGGAndNotShingel(orderLine)) {
/*    */ 
/* 37 */          Colli colli = orderLine.getColli();
/* 38 */          if (colli != null) {
/* 39 */             addValue = this.getAddValue(period, ignoreSent, addValue, colli, transportable);
/*    */ 
/*    */          }
/*    */       }
/*    */ 
/* 44 */       return addValue;
/*    */    }
/*    */ 
/*    */    private boolean sendingFromGGAndNotShingel(OrderLine orderLine) {
/* 48 */       boolean sendingFromGGAndNotShingel = false;
/* 49 */       String attributeValue = orderLine.getOrderLineAttributeValue("Sendes fra GG");
/*    */ 
/* 51 */       if (attributeValue != null && attributeValue.equalsIgnoreCase("Ja")) {
/* 52 */          attributeValue = orderLine.getOrderLineAttributeValue("Taksteintype");
/*    */ 
/* 54 */          if (attributeValue.indexOf("Shingel") < 0) {
/* 55 */             sendingFromGGAndNotShingel = true;
/*    */          }      }
/*    */ 
/* 58 */       return sendingFromGGAndNotShingel;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    private BigDecimal getAddValue(Periode period, boolean ignoreSent, BigDecimal aAddValue, Colli colli, Transportable transportable) {
/* 64 */       BigDecimal addValue = aAddValue;
/* 65 */       if (!ignoreSent) {
/* 66 */          if (colli.getSent() != null) {
/*    */ 
/* 68 */             addValue = this.addIfColliIsForTransportable(transportable, colli);
/*    */          }
/*    */       } else {
/* 71 */          addValue = this.transportCostAdditon.getAddition();
/*    */       }
/* 73 */       return addValue;
/*    */    }
/*    */ 
/*    */ 
/*    */    private BigDecimal addIfColliIsForTransportable(Transportable transportable, Colli colli) {
/* 78 */       return this.compareColliWithTransportable(transportable, colli) == 0 ? this.transportCostAdditon.getAddition() : BigDecimal.valueOf(0L);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    private int compareColliWithTransportable(Transportable transportable, Colli colli) {
/* 86 */       if (transportable.getPostShipment() != null && colli.getPostShipment() == null) {
/*    */ 
/* 88 */          return -1;
/* 89 */       } else if (transportable.getPostShipment() == null && colli.getPostShipment() != null) {
/*    */ 
/* 91 */          return 1;      } else {
/* 92 */          return transportable.getPostShipment() != null && colli.getPostShipment() != null ? transportable.getPostShipment().getPostShipmentId().compareTo(colli.getPostShipment().getPostShipmentId()) : transportable.getOrder().getOrderId().compareTo(colli.getOrder().getOrderId());
/*    */       }
/*    */    }
/*    */ }
