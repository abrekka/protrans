/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.math.BigDecimal;
/*     */ import no.ugland.utransprod.model.CostType;
/*     */ import no.ugland.utransprod.model.CostUnit;
/*     */ import no.ugland.utransprod.model.OrderCost;
/*     */ import no.ugland.utransprod.model.Supplier;
/*     */ import no.ugland.utransprod.util.YesNoInteger;
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
/*     */ public class OrderCostModel extends AbstractModel<OrderCost, OrderCostModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_ORDER_COST_ID = "orderCostId";
/*     */    public static final String PROPERTY_COST_TYPE = "costType";
/*     */    public static final String PROPERTY_COST_UNIT = "costUnit";
/*     */    public static final String PROPERTY_COST_AMOUNT = "costAmount";
/*     */    public static final String PROPERTY_INCL_VAT = "inclVat";
/*     */    public static final String PROPERTY_IS_INCL_VAT = "isInclVat";
/*     */    public static final String PROPERTY_SUPPLIER = "supplier";
/*     */    public static final String PROPERTY_INVOICE_NR = "invoiceNr";
/*     */ 
/*     */    public OrderCostModel(OrderCost orderCost) {
/*  70 */       super(orderCost);
/*  71 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getOrderCostId() {
/*  77 */       return ((OrderCost)this.object).getOrderCostId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderCostId(Integer orderCostId) {
/*  84 */       Integer oldId = this.getOrderCostId();
/*  85 */       ((OrderCost)this.object).setOrderCostId(orderCostId);
/*  86 */       this.firePropertyChange("orderCostId", oldId, orderCostId);
/*  87 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public CostType getCostType() {
/*  93 */       return ((OrderCost)this.object).getCostType();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCostType(CostType costType) {
/* 100 */       CostType oldType = this.getCostType();
/* 101 */       ((OrderCost)this.object).setCostType(costType);
/* 102 */       this.firePropertyChange("costType", oldType, costType);
/* 103 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public CostUnit getCostUnit() {
/* 109 */       return ((OrderCost)this.object).getCostUnit();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCostUnit(CostUnit costUnit) {
/* 116 */       CostUnit oldUnit = this.getCostUnit();
/* 117 */       ((OrderCost)this.object).setCostUnit(costUnit);
/* 118 */       this.firePropertyChange("costUnit", oldUnit, costUnit);
/* 119 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public BigDecimal getCostAmount() {
/* 125 */       return ((OrderCost)this.object).getCostAmount();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCostAmount(BigDecimal costAmount) {
/* 132 */       BigDecimal oldAmount = this.getCostAmount();
/* 133 */       ((OrderCost)this.object).setCostAmount(costAmount);
/* 134 */       this.firePropertyChange("costAmount", oldAmount, costAmount);
/* 135 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getInclVat() {
/* 141 */       return ((OrderCost)this.object).getInclVat();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setInclVat(Integer inclVat) {
/* 148 */       Integer oldVat = this.getInclVat();
/* 149 */       ((OrderCost)this.object).setInclVat(inclVat);
/* 150 */       this.firePropertyChange("inclVat", oldVat, inclVat);
/* 151 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public YesNoInteger getIsInclVat() {
/* 157 */       return new YesNoInteger(((OrderCost)this.object).getInclVat());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setIsInclVat(YesNoInteger inclVat) {
/* 164 */       YesNoInteger oldIsVat = this.getIsInclVat();
/* 165 */       ((OrderCost)this.object).setInclVat(inclVat.getIntegerValue());
/* 166 */       this.firePropertyChange("isInclVat", oldIsVat, inclVat);
/* 167 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Supplier getSupplier() {
/* 173 */       return ((OrderCost)this.object).getSupplier();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplier(Supplier supplier) {
/* 180 */       Supplier oldSupplier = this.getSupplier();
/* 181 */       ((OrderCost)this.object).setSupplier(supplier);
/* 182 */       this.firePropertyChange("supplier", oldSupplier, supplier);
/* 183 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getInvoiceNr() {
/* 189 */       return ((OrderCost)this.object).getInvoiceNr();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setInvoiceNr(String invoiceNr) {
/* 196 */       String oldNr = this.getInvoiceNr();
/* 197 */       ((OrderCost)this.object).setInvoiceNr(invoiceNr);
/* 198 */       this.firePropertyChange("invoiceNr", oldNr, invoiceNr);
/* 199 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 207 */       presentationModel.getBufferedModel("orderCostId").addValueChangeListener(listener);
/* 208 */       presentationModel.getBufferedModel("costType").addValueChangeListener(listener);
/* 209 */       presentationModel.getBufferedModel("costUnit").addValueChangeListener(listener);
/* 210 */       presentationModel.getBufferedModel("costAmount").addValueChangeListener(listener);
/* 211 */       presentationModel.getBufferedModel("inclVat").addValueChangeListener(listener);
/* 212 */       presentationModel.getBufferedModel("isInclVat").addValueChangeListener(listener);
/* 213 */       presentationModel.getBufferedModel("supplier").addValueChangeListener(listener);
/* 214 */       presentationModel.getBufferedModel("invoiceNr").addValueChangeListener(listener);
/*     */ 
/* 216 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public OrderCostModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 223 */       OrderCostModel orderCostModel = new OrderCostModel(new OrderCost());
/* 224 */       orderCostModel.setOrderCostId((Integer)presentationModel.getBufferedValue("orderCostId"));
/* 225 */       orderCostModel.setCostType((CostType)presentationModel.getBufferedValue("costType"));
/* 226 */       orderCostModel.setCostUnit((CostUnit)presentationModel.getBufferedValue("costUnit"));
/* 227 */       orderCostModel.setCostAmount((BigDecimal)presentationModel.getBufferedValue("costAmount"));
/* 228 */       orderCostModel.setInclVat((Integer)presentationModel.getBufferedValue("inclVat"));
/* 229 */       orderCostModel.setIsInclVat((YesNoInteger)presentationModel.getBufferedValue("isInclVat"));
/* 230 */       orderCostModel.setSupplier((Supplier)presentationModel.getBufferedValue("supplier"));
/* 231 */       orderCostModel.setInvoiceNr((String)presentationModel.getBufferedValue("invoiceNr"));
/* 232 */       return orderCostModel;
/*     */    }
/*     */ }
