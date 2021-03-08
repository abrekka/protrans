/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.CostUnit;
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
/*     */ public class CostUnitModel extends AbstractModel<CostUnit, CostUnitModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_COST_UNIT_ID = "costUnitId";
/*     */    public static final String PROPERTY_COST_UNIT_NAME = "costUnitName";
/*     */    public static final String PROPERTY_COST_UNIT_DESCRIPTION = "description";
/*     */ 
/*     */    public CostUnitModel(CostUnit costUnit) {
/*  40 */       super(costUnit);
/*  41 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getCostUnitId() {
/*  47 */       return ((CostUnit)this.object).getCostUnitId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCostUnitId(Integer costUnitId) {
/*  54 */       Integer oldId = this.getCostUnitId();
/*  55 */       ((CostUnit)this.object).setCostUnitId(costUnitId);
/*  56 */       this.firePropertyChange("costUnitId", oldId, costUnitId);
/*  57 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getCostUnitName() {
/*  63 */       return ((CostUnit)this.object).getCostUnitName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCostUnitName(String costUnitName) {
/*  70 */       String oldName = this.getCostUnitName();
/*  71 */       ((CostUnit)this.object).setCostUnitName(costUnitName);
/*  72 */       this.firePropertyChange("costUnitName", oldName, costUnitName);
/*  73 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDescription() {
/*  79 */       return ((CostUnit)this.object).getDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDescription(String description) {
/*  86 */       String oldDesc = this.getDescription();
/*  87 */       ((CostUnit)this.object).setDescription(description);
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
/*  98 */       presentationModel.getBufferedModel("costUnitId").addValueChangeListener(listener);
/*     */ 
/* 100 */       presentationModel.getBufferedModel("costUnitName").addValueChangeListener(listener);
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
/*     */    public CostUnitModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 113 */       CostUnitModel costUnitModel = new CostUnitModel(new CostUnit());
/* 114 */       costUnitModel.setCostUnitId((Integer)presentationModel.getBufferedValue("costUnitId"));
/*     */ 
/* 116 */       costUnitModel.setCostUnitName((String)presentationModel.getBufferedValue("costUnitName"));
/*     */ 
/* 118 */       costUnitModel.setDescription((String)presentationModel.getBufferedValue("description"));
/*     */ 
/* 120 */       return costUnitModel;
/*     */    }
/*     */ }
