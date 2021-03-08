/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.CostType;
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
/*     */ public class CostTypeModel extends AbstractModel<CostType, CostTypeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_COST_TYPE_ID = "costTypeId";
/*     */    public static final String PROPERTY_COST_TYPE_NAME = "costTypeName";
/*     */    public static final String PROPERTY_COST_TYPE_DESCRIPTION = "description";
/*     */ 
/*     */    public CostTypeModel(CostType costType) {
/*  40 */       super(costType);
/*  41 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getCostTypeId() {
/*  47 */       return ((CostType)this.object).getCostTypeId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCostTypeId(Integer costTypeId) {
/*  54 */       Integer oldId = this.getCostTypeId();
/*  55 */       ((CostType)this.object).setCostTypeId(costTypeId);
/*  56 */       this.firePropertyChange("costTypeId", oldId, costTypeId);
/*  57 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getCostTypeName() {
/*  63 */       return ((CostType)this.object).getCostTypeName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCostTypeName(String costTypeName) {
/*  70 */       String oldName = this.getCostTypeName();
/*  71 */       ((CostType)this.object).setCostTypeName(costTypeName);
/*  72 */       this.firePropertyChange("costTypeName", oldName, costTypeName);
/*  73 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDescription() {
/*  79 */       return ((CostType)this.object).getDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDescription(String description) {
/*  86 */       String oldDesc = this.getDescription();
/*  87 */       ((CostType)this.object).setDescription(description);
/*  88 */       this.firePropertyChange("description", oldDesc, description);
/*  89 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/*  98 */       presentationModel.getBufferedModel("costTypeId").addValueChangeListener(listener);
/*     */ 
/* 100 */       presentationModel.getBufferedModel("costTypeName").addValueChangeListener(listener);
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
/*     */    public CostTypeModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 113 */       CostTypeModel costTypeModel = new CostTypeModel(new CostType());
/* 114 */       costTypeModel.setCostTypeId((Integer)presentationModel.getBufferedValue("costTypeId"));
/*     */ 
/* 116 */       costTypeModel.setCostTypeName((String)presentationModel.getBufferedValue("costTypeName"));
/*     */ 
/* 118 */       costTypeModel.setDescription((String)presentationModel.getBufferedValue("description"));
/*     */ 
/* 120 */       return costTypeModel;
/*     */    }
/*     */ }
