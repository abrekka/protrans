/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.math.BigDecimal;
/*     */ import no.ugland.utransprod.model.NokkelProduksjonV;
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
/*     */ public class NokkelProduksjonVModel extends AbstractModel<NokkelProduksjonV, NokkelProduksjonVModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_BUDGET_VALUE_STRING = "budgetValueString";
/*     */    public static final String PROPERTY_BUDGET_DEVIATION_STRING = "budgetDeviationString";
/*     */    public static final String PROPERTY_BUDGET_DEVIATION_PROC_STRING = "budgetDeviationProcString";
/*     */    public static final String PROPERTY_PACKAGE_SUM_WEEK_STRING = "packageSumWeekString";
/*     */ 
/*     */    public NokkelProduksjonVModel(NokkelProduksjonV object) {
/*  47 */       super(object);
/*  48 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getBudgetValueString() {
/*  54 */       return String.format("%1$.0f", ((NokkelProduksjonV)this.object).getBudgetValue());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setBudgetValueString(String budgetValue) {
/*  61 */       String oldValue = this.getBudgetValueString();
/*  62 */       if (budgetValue != null) {
/*  63 */          ((NokkelProduksjonV)this.object).setBudgetValue(BigDecimal.valueOf(Double.valueOf(budgetValue)));
/*     */ 
/*     */       } else {
/*  66 */          ((NokkelProduksjonV)this.object).setBudgetValue((BigDecimal)null);
/*     */       }
/*  68 */       this.firePropertyChange("budgetValueString", oldValue, budgetValue);
/*  69 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPackageSumWeekString() {
/*  75 */       return String.format("%1$.0f", ((NokkelProduksjonV)this.object).getPackageSumWeek());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPackageSumWeekString(String packageSumWeekString) {
/*  82 */       String oldValue = this.getPackageSumWeekString();
/*  83 */       if (packageSumWeekString != null) {
/*  84 */          ((NokkelProduksjonV)this.object).setPackageSumWeek(BigDecimal.valueOf(Double.valueOf(packageSumWeekString)));
/*     */ 
/*     */       } else {
/*  87 */          ((NokkelProduksjonV)this.object).setPackageSumWeek((BigDecimal)null);
/*     */       }
/*  89 */       this.firePropertyChange("packageSumWeekString", oldValue, packageSumWeekString);
/*     */ 
/*  91 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getBudgetDeviationString() {
/*  97 */       return String.format("%1$.0f", ((NokkelProduksjonV)this.object).getBudgetDeviation());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setBudgetDeviationString(String budgetDeviation) {
/* 104 */       String oldValue = this.getBudgetDeviationString();
/* 105 */       if (budgetDeviation != null) {
/* 106 */          ((NokkelProduksjonV)this.object).setBudgetDeviation(BigDecimal.valueOf(Double.valueOf(budgetDeviation)));
/*     */ 
/*     */       } else {
/* 109 */          ((NokkelProduksjonV)this.object).setBudgetDeviation((BigDecimal)null);
/*     */       }
/* 111 */       this.firePropertyChange("budgetDeviationString", oldValue, budgetDeviation);
/*     */ 
/* 113 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getBudgetDeviationProcString() {
/* 119 */       return String.format("%1$.1f", ((NokkelProduksjonV)this.object).getBudgetDeviationProc());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setBudgetDeviationProcString(String budgetDeviationProc) {
/* 126 */       String oldValue = this.getBudgetDeviationProcString();
/* 127 */       if (budgetDeviationProc != null) {
/* 128 */          ((NokkelProduksjonV)this.object).setBudgetDeviationProc(BigDecimal.valueOf(Double.valueOf(budgetDeviationProc)));
/*     */ 
/*     */       } else {
/* 131 */          ((NokkelProduksjonV)this.object).setBudgetDeviationProc((BigDecimal)null);
/*     */       }
/* 133 */       this.firePropertyChange("budgetDeviationProcString", oldValue, budgetDeviationProc);
/*     */ 
/* 135 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 144 */       presentationModel.getBufferedModel("budgetDeviationProcString").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 147 */       presentationModel.getBufferedModel("budgetDeviationString").addValueChangeListener(listener);
/*     */ 
/* 149 */       presentationModel.getBufferedModel("budgetValueString").addValueChangeListener(listener);
/*     */ 
/* 151 */       presentationModel.getBufferedModel("packageSumWeekString").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 154 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public NokkelProduksjonVModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 162 */       NokkelProduksjonVModel model = new NokkelProduksjonVModel(new NokkelProduksjonV());
/*     */ 
/* 164 */       model.setBudgetDeviationProcString((String)presentationModel.getBufferedValue("budgetDeviationProcString"));
/*     */ 
/* 166 */       model.setBudgetDeviationString((String)presentationModel.getBufferedValue("budgetDeviationString"));
/*     */ 
/* 168 */       model.setBudgetValueString((String)presentationModel.getBufferedValue("budgetValueString"));
/*     */ 
/* 170 */       model.setPackageSumWeekString((String)presentationModel.getBufferedValue("packageSumWeekString"));
/*     */ 
/* 172 */       return model;
/*     */    }
/*     */ }
