/*    */ package no.ugland.utransprod.service;
/*    */ 
/*    */ import no.ugland.utransprod.model.ArticleType;
/*    */ import no.ugland.utransprod.model.Ord;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.model.Ordln;
/*    */ 
/*    */ 
/*    */ public class TaksteinConverter extends DefaultConverter {
/*    */    private static final String ATTRIBUTE_TAKSTEINTYPE = "Taksteintype";
/*    */    private static final String SKARPNES = "SKARPNES";
/*    */    private static final String JA = "Ja";
/*    */    private static final String NEI = "Nei";
/*    */    private static final String ATTRIBUTE_SENDES_FRA_GG = "Sendes fra GG";
/*    */ 
/*    */    public TaksteinConverter(ManagerRepository managerRepository) {
/* 18 */       super(managerRepository);
/* 19 */    }
/*    */ 
/*    */    public OrderLine convert(ArticleType articleType, Ordln ordln, Order order, Ord ord) {
/* 22 */       return articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN : this.getOrderLineAndAddAttributes(articleType, ordln, order);
/*    */    }
/*    */ 
/*    */ 
/*    */    private OrderLine getOrderLineAndAddAttributes(ArticleType articleType, Ordln ordln, Order order) {
/* 27 */       OrderLine orderLine = super.getOrderLine(articleType, ordln, order);
/* 28 */       this.setTaksteintype(ordln, orderLine);
/* 29 */       this.setSendesFraGG(ordln, orderLine);
/* 30 */       return orderLine;
/*    */    }
/*    */ 
/*    */    private void setSendesFraGG(Ordln ordln, OrderLine orderLine) {
/* 34 */       if (ordln.getProd() != null) {
/* 35 */          String sendesFraGG = this.getSendesFraGG(ordln.getProd().getInf());
/* 36 */          orderLine.setAttributeValue("Sendes fra GG", sendesFraGG);
/*    */       }
/*    */ 
/* 39 */    }
/*    */ 
/*    */    private String getSendesFraGG(String inf) {
/* 42 */       return inf != null && inf.startsWith("SKARPNES") ? "Ja" : "Nei";
/*    */    }
/*    */ 
/*    */    private void setTaksteintype(Ordln ordln, OrderLine orderLine) {
/* 46 */       if (ordln.getDescription() != null && ordln.getDescription().length() != 0) {
/* 47 */          orderLine.setAttributeValue("Taksteintype", ordln.getDescription().replaceAll("Takstein:", ""));
/*    */       }
/* 49 */    }
/*    */ }
