/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.DeviationStatus;
/*     */ import no.ugland.utransprod.util.Util;
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
/*     */ public class DeviationStatusModel extends AbstractModel<DeviationStatus, DeviationStatusModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_DEVIATION_STATUS_DESCRIPTION = "deviationStatusDescription";
/*     */    public static final String PROPERTY_DEVIATION_STATUS_NAME = "deviationStatusName";
/*     */    public static final String PROPERTY_FOR_MANAGER_BOOL = "forManagerBool";
/*     */    public static final String PROPERTY_DEVIATION_DONE_BOOL = "deviationDoneBool";
/*     */    public static final String PROPERTY_FOR_DEVIATION_BOOL = "forDeviationBool";
/*     */    public static final String PROPERTY_FOR_ACCIDENT_BOOL = "forAccidentBool";
/*     */ 
/*     */    public DeviationStatusModel(DeviationStatus object) {
/*  46 */       super(object);
/*  47 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDeviationStatusDescription() {
/*  53 */       return ((DeviationStatus)this.object).getDeviationStatusDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeviationStatusDescription(String deviationStatusDescription) {
/*  60 */       String oldDesc = this.getDeviationStatusDescription();
/*  61 */       ((DeviationStatus)this.object).setDeviationStatusDescription(deviationStatusDescription);
/*  62 */       this.firePropertyChange("deviationStatusDescription", oldDesc, deviationStatusDescription);
/*     */ 
/*  64 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDeviationStatusName() {
/*  70 */       return ((DeviationStatus)this.object).getDeviationStatusName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeviationStatusName(String deviationStatusName) {
/*  77 */       String oldName = this.getDeviationStatusName();
/*  78 */       ((DeviationStatus)this.object).setDeviationStatusName(deviationStatusName);
/*  79 */       this.firePropertyChange("deviationStatusName", oldName, deviationStatusName);
/*     */ 
/*  81 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getForManagerBool() {
/*  87 */       return Util.convertNumberToBoolean(((DeviationStatus)this.object).getForManager());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setForManagerBool(Boolean forManager) {
/*  94 */       Boolean oldManager = this.getForManagerBool();
/*  95 */       ((DeviationStatus)this.object).setForManager(Util.convertBooleanToNumber(forManager));
/*  96 */       this.firePropertyChange("forManagerBool", oldManager, forManager);
/*     */ 
/*  98 */    }
/*     */ 
/*     */    public Boolean getDeviationDoneBool() {
/* 101 */       return Util.convertNumberToBoolean(((DeviationStatus)this.object).getDeviationDone());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeviationDoneBool(Boolean deviationDone) {
/* 108 */       Boolean oldDone = this.getDeviationDoneBool();
/* 109 */       ((DeviationStatus)this.object).setDeviationDone(Util.convertBooleanToNumber(deviationDone));
/* 110 */       this.firePropertyChange("deviationDoneBool", oldDone, deviationDone);
/*     */ 
/* 112 */    }
/*     */ 
/*     */    public Boolean getForDeviationBool() {
/* 115 */       return Util.convertNumberToBoolean(((DeviationStatus)this.object).getForDeviation());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setForDeviationBool(Boolean forDeviation) {
/* 122 */       Boolean oldforDeviation = this.getForDeviationBool();
/* 123 */       ((DeviationStatus)this.object).setForDeviation(Util.convertBooleanToNumber(forDeviation));
/* 124 */       this.firePropertyChange("forDeviationBool", oldforDeviation, forDeviation);
/*     */ 
/* 126 */    }
/*     */    public Boolean getForAccidentBool() {
/* 128 */       return Util.convertNumberToBoolean(((DeviationStatus)this.object).getForAccident());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setForAccidentBool(Boolean forAccident) {
/* 135 */       Boolean oldForAccidnet = this.getForAccidentBool();
/* 136 */       ((DeviationStatus)this.object).setForAccident(Util.convertBooleanToNumber(forAccident));
/* 137 */       this.firePropertyChange("forDeviationBool", oldForAccidnet, forAccident);
/*     */ 
/* 139 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 148 */       presentationModel.getBufferedModel("deviationStatusDescription").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 151 */       presentationModel.getBufferedModel("deviationStatusName").addValueChangeListener(listener);
/*     */ 
/* 153 */       presentationModel.getBufferedModel("forManagerBool").addValueChangeListener(listener);
/*     */ 
/* 155 */       presentationModel.getBufferedModel("deviationDoneBool").addValueChangeListener(listener);
/*     */ 
/* 157 */       presentationModel.getBufferedModel("forDeviationBool").addValueChangeListener(listener);
/*     */ 
/* 159 */       presentationModel.getBufferedModel("forAccidentBool").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 162 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public DeviationStatusModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 171 */       DeviationStatusModel model = new DeviationStatusModel(new DeviationStatus());
/*     */ 
/* 173 */       model.setDeviationStatusDescription((String)presentationModel.getBufferedValue("deviationStatusDescription"));
/*     */ 
/* 175 */       model.setDeviationStatusName((String)presentationModel.getBufferedValue("deviationStatusName"));
/*     */ 
/* 177 */       model.setForManagerBool((Boolean)presentationModel.getBufferedValue("forManagerBool"));
/*     */ 
/* 179 */       model.setDeviationDoneBool((Boolean)presentationModel.getBufferedValue("deviationDoneBool"));
/*     */ 
/* 181 */       model.setForDeviationBool((Boolean)presentationModel.getBufferedValue("forDeviationBool"));
/*     */ 
/* 183 */       model.setForAccidentBool((Boolean)presentationModel.getBufferedValue("forAccidentBool"));
/*     */ 
/* 185 */       return model;
/*     */    }
/*     */ }
