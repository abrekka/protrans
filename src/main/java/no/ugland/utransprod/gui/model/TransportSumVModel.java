/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.math.BigDecimal;
/*     */ import no.ugland.utransprod.model.TransportSumV;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransportSumVModel extends AbstractModel<TransportSumV, TransportSumVModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_ORDER_COUNT_STRING = "orderCountString";
/*     */    public static final String PROPERTY_GARAGE_COST_STRING = "garageCostString";
/*     */ 
/*     */    public TransportSumVModel(TransportSumV object) {
/*  38 */       super(object);
/*  39 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getOrderCountString() {
/*  45 */       return ((TransportSumV)this.object).getOrderCount() != null ? String.valueOf(((TransportSumV)this.object).getOrderCount()) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderCountString(String orderCount) {
/*  55 */       String oldCount = this.getOrderCountString();
/*  56 */       if (orderCount != null) {
/*  57 */          ((TransportSumV)this.object).setOrderCount(Integer.valueOf(orderCount));
/*     */       } else {
/*  59 */          ((TransportSumV)this.object).setOrderCount((Integer)null);
/*     */       }
/*  61 */       this.firePropertyChange("orderCountString", oldCount, orderCount);
/*  62 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getGarageCostString() {
/*  68 */       return ((TransportSumV)this.object).getGarageCost() != null ? String.valueOf(((TransportSumV)this.object).getGarageCost()) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setGarageCostString(String garageCost) {
/*  78 */       String oldCost = this.getGarageCostString();
/*  79 */       if (garageCost != null) {
/*  80 */          ((TransportSumV)this.object).setGarageCost(BigDecimal.valueOf(Double.valueOf(garageCost)));
/*     */ 
/*     */ 
/*     */       } else {
/*  84 */          ((TransportSumV)this.object).setGarageCost((BigDecimal)null);
/*     */       }
/*  86 */       this.firePropertyChange("garageCostString", oldCost, garageCost);
/*  87 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/*  96 */       presentationModel.getBufferedModel("orderCountString").addValueChangeListener(listener);
/*     */ 
/*  98 */       presentationModel.getBufferedModel("garageCostString").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 101 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public TransportSumVModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 109 */       TransportSumVModel model = new TransportSumVModel(new TransportSumV());
/* 110 */       model.setOrderCountString((String)presentationModel.getBufferedValue("orderCountString"));
/*     */ 
/* 112 */       model.setGarageCostString((String)presentationModel.getBufferedValue("garageCostString"));
/*     */ 
/* 114 */       return model;
/*     */    }
/*     */ }
