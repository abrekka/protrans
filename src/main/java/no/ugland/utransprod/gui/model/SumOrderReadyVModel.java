/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.math.BigDecimal;
/*    */ import no.ugland.utransprod.model.SumOrderReadyV;
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
/*    */ public class SumOrderReadyVModel extends AbstractModel<SumOrderReadyV, SumOrderReadyVModel> {
/*    */    private static final long serialVersionUID = 1L;
/*    */    public static final String PROPERTY_PACKAGE_SUM_STRING = "packageSumString";
/*    */ 
/*    */    public SumOrderReadyVModel(SumOrderReadyV object) {
/* 33 */       super(object);
/* 34 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getPackageSumString() {
/* 40 */       return ((SumOrderReadyV)this.object).getPackageSum() != null ? String.valueOf(((SumOrderReadyV)this.object).getPackageSum()) : "";
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setPackageSumString(String packageSumString) {
/* 50 */       String oldSum = this.getPackageSumString();
/* 51 */       if (packageSumString != null) {
/* 52 */          ((SumOrderReadyV)this.object).setPackageSum(BigDecimal.valueOf(Long.valueOf(packageSumString)));
/*    */       }
/*    */ 
/* 55 */       this.firePropertyChange("packageSumString", oldSum, packageSumString);
/*    */ 
/* 57 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 66 */       presentationModel.getBufferedModel("packageSumString").addValueChangeListener(listener);
/*    */ 
/* 68 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public SumOrderReadyVModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 76 */       SumOrderReadyVModel model = new SumOrderReadyVModel(new SumOrderReadyV());
/*    */ 
/* 78 */       model.setPackageSumString((String)presentationModel.getBufferedValue("packageSumString"));
/*    */ 
/* 80 */       return model;
/*    */    }
/*    */ }
