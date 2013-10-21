/**
 * 
 */
package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.model.ProductAreaGroup;

import com.jgoodies.binding.beans.Model;

public class ProductAreaGroupModel extends Model{
    /**
     * 
     */
    public final static String PROPERTY_PRODUCT_AREA_GROUP="productAreaGroup";
    private ProductAreaGroup productAreaGroup;
    
    
    public ProductAreaGroup getProductAreaGroup(){
        return productAreaGroup;
    }
    public void setProductAreaGroup(ProductAreaGroup aProductAreaGroup){
        ProductAreaGroup oldProductAreaGroup=getProductAreaGroup();
        productAreaGroup=aProductAreaGroup;
        firePropertyChange(PROPERTY_PRODUCT_AREA_GROUP, oldProductAreaGroup, aProductAreaGroup);
    }
    
}