/*    */ package no.ugland.utransprod.service;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import no.ugland.utransprod.model.ArticleType;
/*    */ import no.ugland.utransprod.model.Ord;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.model.Ordln;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TakstolConverter extends DefaultConverter {
/*    */    private static final String ATTRIBUTE_VINKEL = "Vinkel";
/*    */    private static final String ATTRIBUTE_BREDDE = "Bredde";
/*    */    private static final String ARTICLE_GAVL = "Gavl";
/*    */ 
/*    */    @Inject
/*    */    public TakstolConverter(ManagerRepository managerRepository) {
/* 24 */       super(managerRepository);
/* 25 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public OrderLine convert(ArticleType articleType, Ordln ordln, Order order, Ord ord) {
/* 38 */       return articleType != ArticleType.UNKNOWN && (ordln.getNoinvoab() == null || ordln.getNoinvoab().intValue() != 2) ? this.getOrderLine(articleType, ordln, order) : OrderLine.UNKNOWN;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    protected OrderLine getOrderLine(ArticleType articleType, Ordln ordln, Order order) {
/* 46 */       OrderLine orderLine = super.getOrderLine(articleType, ordln, order);
/* 47 */       orderLine.setAttributeValue("Vinkel", Util.convertBigDecimalToString(ordln.getFree4()));
/* 48 */       orderLine.setAttributeValue("Bredde", Util.convertBigDecimalToString(ordln.getLgtU()));
/* 49 */       this.setGavlVinkelOgBredde(orderLine, order);
/* 50 */       return orderLine;
/*    */    }
/*    */ 
/*    */    private void setGavlVinkelOgBredde(OrderLine takstol, Order order) {
/* 54 */       OrderLine gavl = order.getOrderLine("Gavl");
/* 55 */       String takstolVinkel = takstol.getAttributeValue("Vinkel");
/* 56 */       String takstolBredde = takstol.getAttributeValue("Bredde");
/* 57 */       if (gavl != OrderLine.UNKNOWN && !StringUtils.isEmpty(takstolVinkel)) {
/* 58 */          gavl.setAttributeValue("Vinkel", takstolVinkel);
/*    */       }
/* 60 */       if (gavl != OrderLine.UNKNOWN && !StringUtils.isEmpty(takstolBredde)) {
/* 61 */          gavl.setAttributeValue("Bredde", takstolBredde);
/*    */       }
/*    */ 
/* 64 */    }
/*    */ }
