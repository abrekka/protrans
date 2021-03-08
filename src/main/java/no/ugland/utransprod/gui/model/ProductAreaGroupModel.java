/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;
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
/*    */ public class ProductAreaGroupModel extends AbstractModel<ProductAreaGroup, ProductAreaGroupModel> {
/*    */    private static final long serialVersionUID = 1L;
/*    */    public static final String PROPERTY_PRODUCT_AREA_GROUP = "productAreaGroup";
/*    */ 
/*    */    public ProductAreaGroupModel(ProductAreaGroup object) {
/* 21 */       super(object);
/* 22 */    }
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
/*    */    public ProductAreaGroup getProductAreaGroup() {
/* 38 */       return (ProductAreaGroup)this.object;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setProductAreaGroup(ProductAreaGroup group) {
/* 45 */       ProductAreaGroup oldGroup = this.getProductAreaGroup();
/* 46 */       this.object = group;
/* 47 */       this.firePropertyChange("productAreaGroup", oldGroup, group);
/* 48 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 59 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public ProductAreaGroupModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 69 */       return null;
/*    */    }
/*    */ }
