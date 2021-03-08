/*    */ package no.ugland.utransprod.service;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import no.ugland.utransprod.model.ArticleType;
/*    */ import no.ugland.utransprod.model.Ord;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.OrderLine;
/*    */ import no.ugland.utransprod.model.Ordln;
/*    */ 
/*    */ 
/*    */ public class GavlConverter extends DefaultConverter {
/*    */    private static final String TAKSTOLER = "Takstoler";
/*    */    private static final String ATTRIBUTE_VINKEL = "Vinkel";
/*    */    private static final String ATTRIBUTE_BREDDE = "Bredde";
/*    */ 
/*    */    @Inject
/*    */    public GavlConverter(ManagerRepository managerRepository) {
/* 18 */       super(managerRepository);
/* 19 */    }
/*    */ 
/*    */    public OrderLine convert(ArticleType articleType, Ordln ordln, Order order, Ord ord) {
/* 22 */       OrderLine gavl = articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN : this.getOrderLine(articleType, ordln, order);
/*    */ 
/* 24 */       return this.setAngleAndWidth(gavl, order);
/*    */    }
/*    */ 
/*    */    private OrderLine setAngleAndWidth(OrderLine gavl, Order order) {
/* 28 */       if (gavl != OrderLine.UNKNOWN) {
/* 29 */          OrderLine takstol = order.getOrderLine("Takstoler");
/* 30 */          if (takstol != OrderLine.UNKNOWN) {
/* 31 */             gavl.setAttributeValue("Vinkel", takstol.getAttributeValue("Vinkel"));
/* 32 */             gavl.setAttributeValue("Bredde", takstol.getAttributeValue("Bredde"));
/*    */          }      }
/*    */ 
/* 35 */       return gavl;
/*    */    }
/*    */ }
