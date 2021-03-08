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
/*    */ public class AdditionInnredning extends AbstractAddition {
/*    */    public AdditionInnredning(TransportCostAddition addition, String aArticlePath, String someInfo) {
/* 14 */       super(addition, aArticlePath, someInfo);
/* 15 */    }
/*    */ 
/*    */ 
/*    */    public BigDecimal calculateAddition(BigDecimal basis, Transportable transportable, Periode period, boolean ignoreSent) {
/* 19 */       BigDecimal addValue = BigDecimal.valueOf(0L);
/* 20 */       OrderLine orderLine = transportable.getOrderLine(this.articlePath);
/* 21 */       if (orderLine != null) {
/*    */ 
/* 23 */          Colli colli = orderLine.getColli();
/* 24 */          if (colli != null) {
/* 25 */             addValue = this.getAddValue(period, ignoreSent, addValue, colli, transportable);
/*    */          }      }
/*    */ 
/* 28 */       return addValue;
/*    */    }
/*    */ 
/*    */ 
/*    */    private BigDecimal getAddValue(Periode period, boolean ignoreSent, BigDecimal aAddValue, Colli colli, Transportable transportable) {
/* 33 */       BigDecimal addValue = aAddValue;
/* 34 */       if (!ignoreSent) {
/* 35 */          if (colli.getSent() != null) {
/* 36 */             addValue = this.addIfColliIsForTransportable(transportable, colli);
/*    */          }
/*    */       } else {
/* 39 */          addValue = this.transportCostAdditon.getAddition();
/*    */       }
/* 41 */       return addValue;
/*    */    }
/*    */ 
/*    */    private BigDecimal addIfColliIsForTransportable(Transportable transportable, Colli colli) {
/* 45 */       return this.compareColliWithTransportable(transportable, colli) == 0 ? this.transportCostAdditon.getAddition() : BigDecimal.valueOf(0L);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    private int compareColliWithTransportable(Transportable transportable, Colli colli) {
/* 52 */       if (transportable.getPostShipment() != null && colli.getPostShipment() == null) {
/* 53 */          return -1;
/* 54 */       } else if (transportable.getPostShipment() == null && colli.getPostShipment() != null) {
/* 55 */          return 1;      } else {
/* 56 */          return transportable.getPostShipment() != null && colli.getPostShipment() != null ? transportable.getPostShipment().getPostShipmentId().compareTo(colli.getPostShipment().getPostShipmentId()) : transportable.getOrder().getOrderId().compareTo(colli.getOrder().getOrderId());
/*    */       }
/*    */    }
/*    */ }
