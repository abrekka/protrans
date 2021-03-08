/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import no.ugland.utransprod.model.MainPackageV;
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
/*    */ public class MainPackageVModel extends AbstractModel<MainPackageV, MainPackageVModel> {
/*    */    private static final long serialVersionUID = 1L;
/*    */    public static final String PROPERTY_ORDER_ID = "orderId";
/*    */ 
/*    */    public MainPackageVModel(MainPackageV object) {
/* 31 */       super(object);
/* 32 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public Integer getOrderId() {
/* 38 */       return ((MainPackageV)this.object).getOrderId();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setOrderId(Integer orderId) {
/* 45 */       Integer oldId = this.getOrderId();
/* 46 */       ((MainPackageV)this.object).setOrderId(orderId);
/* 47 */       this.firePropertyChange("orderId", oldId, orderId);
/* 48 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 57 */       presentationModel.getBufferedModel("orderId").addValueChangeListener(listener);
/*    */ 
/*    */ 
/* 60 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public MainPackageVModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 68 */       MainPackageVModel mainPackageVModel = new MainPackageVModel(new MainPackageV());
/*    */ 
/* 70 */       mainPackageVModel.setOrderId((Integer)presentationModel.getBufferedValue("orderId"));
/*    */ 
/* 72 */       return mainPackageVModel;
/*    */    }
/*    */ }
