/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.jgoodies.binding.beans.Model;
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
/*    */ public class ProductAreaGroupModel extends Model {
/*    */    public static final String PROPERTY_PRODUCT_AREA_GROUP = "productAreaGroup";
/*    */    private ProductAreaGroup productAreaGroup;
/*    */ 
/*    */    public ProductAreaGroup getProductAreaGroup() {
/* 19 */       return this.productAreaGroup;
/*    */    }
/*    */    public void setProductAreaGroup(ProductAreaGroup aProductAreaGroup) {
/* 22 */       ProductAreaGroup oldProductAreaGroup = this.getProductAreaGroup();
/* 23 */       this.productAreaGroup = aProductAreaGroup;
/* 24 */       this.firePropertyChange("productAreaGroup", oldProductAreaGroup, aProductAreaGroup);
/* 25 */    }
/*    */ }
