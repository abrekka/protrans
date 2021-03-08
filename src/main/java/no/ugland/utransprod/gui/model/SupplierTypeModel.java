/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.SupplierType;
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
/*     */ public class SupplierTypeModel extends AbstractModel<SupplierType, SupplierTypeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_SUPPLIER_TYPE_ID = "supplierTypeId";
/*     */    public static final String PROPERTY_SUPPLIER_TYPE_NAME = "supplierTypeName";
/*     */    public static final String PROPERTY_DESCRIPTION = "description";
/*     */ 
/*     */    public SupplierTypeModel(SupplierType object) {
/*  41 */       super(object);
/*  42 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getSupplierTypeId() {
/*  48 */       return ((SupplierType)this.object).getSupplierTypeId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplierTypeId(Integer supplierTypeId) {
/*  55 */       Integer oldId = this.getSupplierTypeId();
/*  56 */       ((SupplierType)this.object).setSupplierTypeId(supplierTypeId);
/*  57 */       this.firePropertyChange("supplierTypeId", oldId, supplierTypeId);
/*  58 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getSupplierTypeName() {
/*  64 */       return ((SupplierType)this.object).getSupplierTypeName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplierTypeName(String supplierTypeName) {
/*  71 */       String oldName = this.getSupplierTypeName();
/*  72 */       ((SupplierType)this.object).setSupplierTypeName(supplierTypeName);
/*  73 */       this.firePropertyChange("supplierTypeName", oldName, supplierTypeName);
/*     */ 
/*  75 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDescription() {
/*  81 */       return ((SupplierType)this.object).getDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDescription(String description) {
/*  88 */       String oldDesc = this.getDescription();
/*  89 */       ((SupplierType)this.object).setDescription(description);
/*  90 */       this.firePropertyChange("description", oldDesc, description);
/*  91 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 100 */       presentationModel.getBufferedModel("supplierTypeName").addValueChangeListener(listener);
/*     */ 
/* 102 */       presentationModel.getBufferedModel("description").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 105 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public SupplierTypeModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 113 */       SupplierTypeModel supplierTypeModel = new SupplierTypeModel(new SupplierType());
/*     */ 
/* 115 */       supplierTypeModel.setSupplierTypeId((Integer)presentationModel.getBufferedValue("supplierTypeId"));
/*     */ 
/* 117 */       supplierTypeModel.setSupplierTypeName((String)presentationModel.getBufferedValue("supplierTypeName"));
/*     */ 
/* 119 */       supplierTypeModel.setDescription((String)presentationModel.getBufferedValue("description"));
/*     */ 
/*     */ 
/* 122 */       return supplierTypeModel;
/*     */    }
/*     */ }
