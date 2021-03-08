/*     */ package no.ugland.utransprod.service;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import no.ugland.utransprod.model.Ord;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderCost;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.OrderLineAttribute;
/*     */ import no.ugland.utransprod.model.Ordln;
/*     */ import no.ugland.utransprod.model.Ordln.CostLine;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GarasjeConverter implements ConstructionTypeAttributesConverter {
/*     */    private static final String ARTICLE_PATH_GARAGE_TYPE = "Garasjetype";
/*     */    private static final String WALL_HEIGHT_ATTRIBUTE = "Vegghøyde";
/*     */    private static final String BRICK_WALL_HEIGHT_ATTRIBUTE = "Murhøyde";
/*     */    private static final int GARAGE_TYPE_LINE = 1;
/*     */    private static final String WIDTH_ATTRIBUTE = "Bredde";
/*     */    private static final String LENGTH_ATTRIBUTE = "Lengde";
/*     */    private OrdlnManager ordlnManager;
/*     */    private CostTypeManager costTypeManager;
/*     */    private CostUnitManager costUnitManager;
/*     */ 
/*     */    public GarasjeConverter(OrdlnManager aOrdlnManager, CostTypeManager costTypeManager, CostUnitManager costUnitManager) {
/*  32 */       this.ordlnManager = aOrdlnManager;
/*  33 */       this.costTypeManager = costTypeManager;
/*  34 */       this.costUnitManager = costUnitManager;
/*  35 */    }
/*     */ 
/*     */    public void setConstructionTypeAttributes(Ord ord, Order order) {
/*  38 */       OrderLine garageOrderLine = order.getOrderLine("Garasjetype");
/*     */ 
/*  40 */       garageOrderLine = garageOrderLine != OrderLine.UNKNOWN ? garageOrderLine : this.createGarageOrderLine(order);
/*  41 */       if (ord != null) {
/*  42 */          this.setGarageAttributes(garageOrderLine, ord);
/*     */       }
/*     */ 
/*  45 */       if ("Rekke".equalsIgnoreCase(order.getProductArea().getProductArea())) {
/*  46 */          OrderCost monteringskostnad = CostLine.MONTERING_VILLA.addCost(this.costTypeManager, this.costUnitManager, order, BigDecimal.ZERO);
/*     */ 
/*  48 */          order.addOrderCost(monteringskostnad);
/*     */       }
/*  50 */       DefaultConverter.setAttributes(garageOrderLine, this.ordlnManager);
/*  51 */    }
/*     */ 
/*     */    private void setGarageAttributes(OrderLine garageOrderLine, Ord ord) {
/*  54 */       this.setWallHeigth(garageOrderLine, ord);
/*  55 */       this.setBrickWallHeigth(garageOrderLine, ord);
/*  56 */       this.setWidthAndLength(garageOrderLine, ord);
/*  57 */    }
/*     */ 
/*     */    private void setWidthAndLength(OrderLine garageOrderLine, Ord ord) {
/*  60 */       Ordln ordln = this.getVismaOrderLineForGarageType(ord);
/*  61 */       this.setWidth(ordln, garageOrderLine);
/*  62 */       this.setLength(ordln, garageOrderLine);
/*  63 */    }
/*     */ 
/*     */    private void setLength(Ordln ordln, OrderLine garageOrderLine) {
/*  66 */       BigDecimal vismaLength = this.getVismaLength(ordln);
/*  67 */       if (this.vismaLengthHasValue(vismaLength)) {
/*  68 */          OrderLineAttribute attribute = garageOrderLine.getAttributeByName("Lengde");
/*  69 */          attribute.setAttributeValue(String.valueOf(vismaLength.setScale(0)));
/*     */       }
/*     */ 
/*  72 */    }
/*     */ 
/*     */    private boolean vismaLengthHasValue(BigDecimal length) {
/*  75 */       return length != null && length != BigDecimal.ZERO;
/*     */    }
/*     */ 
/*     */    private BigDecimal getVismaLength(Ordln ordln) {
/*  79 */       return ordln != null ? ordln.getLgtU() : null;
/*     */    }
/*     */ 
/*     */    private void setWidth(Ordln ordln, OrderLine garageOrderLine) {
/*  83 */       BigDecimal vismaWidth = this.getVismaWidth(ordln);
/*  84 */       if (this.vismaWidthHasValue(vismaWidth)) {
/*  85 */          OrderLineAttribute attribute = garageOrderLine.getAttributeByName("Bredde");
/*  86 */          attribute.setAttributeValue(String.valueOf(vismaWidth.setScale(0)));
/*     */       }
/*     */ 
/*  89 */    }
/*     */ 
/*     */    private BigDecimal getVismaWidth(Ordln ordln) {
/*  92 */       return ordln != null ? ordln.getWdtu() : null;
/*     */    }
/*     */ 
/*     */    private boolean vismaWidthHasValue(BigDecimal width) {
/*  96 */       return width != null && width != BigDecimal.ZERO;
/*     */    }
/*     */ 
/*     */    private Ordln getVismaOrderLineForGarageType(Ord ord) {
/* 100 */       Ordln ordln = this.ordlnManager.findByOrdnoAndPrCatNo2(ord.getOrdno(), 1);
/* 101 */       return ordln;
/*     */    }
/*     */ 
/*     */    private void setBrickWallHeigth(OrderLine garageOrderLine, Ord ord) {
/* 105 */       if (this.hasBrickWallHeigth(ord)) {
/* 106 */          OrderLineAttribute attribute = garageOrderLine.getAttributeByName("Murhøyde");
/* 107 */          attribute.setAttributeValue(this.getBrickWallHeigth(ord));
/*     */       }
/*     */ 
/* 110 */    }
/*     */ 
/*     */    private String getBrickWallHeigth(Ord ord) {
/* 113 */       return StringUtils.substringBefore(StringUtils.substringBefore(ord.getFree2(), ","), ".");
/*     */    }
/*     */ 
/*     */    private boolean hasBrickWallHeigth(Ord ord) {
/* 117 */       return ord.getFree2() != null;
/*     */    }
/*     */ 
/*     */    private void setWallHeigth(OrderLine garageOrderLine, Ord ord) {
/* 121 */       if (this.hasWallHeigth(ord)) {
/* 122 */          OrderLineAttribute attribute = garageOrderLine.getAttributeByName("Vegghøyde");
/* 123 */          attribute.setAttributeValue(this.getWallHeigth(ord));
/*     */       }
/*     */ 
/* 126 */    }
/*     */ 
/*     */    private String getWallHeigth(Ord ord) {
/* 129 */       return StringUtils.substringBefore(StringUtils.substringBefore(ord.getFree1(), ","), ".");
/*     */    }
/*     */ 
/*     */    private boolean hasWallHeigth(Ord ord) {
/* 133 */       return ord.getFree1() != null;
/*     */    }
/*     */ 
/*     */    private OrderLine createGarageOrderLine(Order order) {
/* 137 */       OrderLine orderLine = new OrderLine();
/* 138 */       orderLine.setArticlePath("Garasjetype");
/* 139 */       order.addOrderLine(orderLine);
/* 140 */       return orderLine;
/*     */    }
/*     */ }
