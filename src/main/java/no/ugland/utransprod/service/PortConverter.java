/*    */ package no.ugland.utransprod.service;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import java.math.BigDecimal;
/*    */ import no.ugland.utransprod.model.ArticleType;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.model.Ordln;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PortConverter extends DefaultConverter {
/*    */    private static final String ATTRIBUTE_PORTMAAL = "Portmål";
/*    */ 
/*    */    @Inject
/*    */    public PortConverter(ManagerRepository managerRepository) {
/* 19 */       super(managerRepository);
/* 20 */    }
/*    */ 
/*    */    public OrderLine convert(ArticleType articleType, Ordln ordln, Order order) {
/* 23 */       return articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN : this.getOrderLine(articleType, ordln, order);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    protected OrderLine getOrderLine(ArticleType articleType, Ordln ordln, Order order) {
/* 30 */       OrderLine orderLine = super.getOrderLine(articleType, ordln, order);
/* 31 */       this.setSizeAttribute(orderLine, ordln);
/* 32 */       return orderLine;
/*    */    }
/*    */ 
/*    */    private void setSizeAttribute(OrderLine orderLine, Ordln ordln) {
/* 36 */       if (this.hasCompleteSizeInfo(ordln)) {
/* 37 */          orderLine.setAttributeValue("Portmål", this.getSize(ordln));
/*    */       }
/* 39 */    }
/*    */ 
/*    */    private boolean hasCompleteSizeInfo(Ordln ordln) {
/* 42 */       return ordln.getLgtU() != null && ordln.getLgtU() != BigDecimal.ZERO && ordln.getHgtU() != null && ordln.getHgtU() != BigDecimal.ZERO;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    private String getSize(Ordln ordln) {
/* 48 */       return Util.convertBigDecimalToString(ordln.getLgtU()) + "x" + Util.convertBigDecimalToString(ordln.getHgtU());
/*    */    }
/*    */ }
