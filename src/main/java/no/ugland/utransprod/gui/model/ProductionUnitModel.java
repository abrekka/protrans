package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.model.ProductionUnitProductAreaGroup;

import com.jgoodies.binding.PresentationModel;

public class ProductionUnitModel extends AbstractModel<ProductionUnit, ProductionUnitModel> {
    public static final String PROPERTY_PRODUCTION_UNIT_NAME="productionUnitName";
    public static final String PROPERTY_ARTICLE_TYPE="articleType";
    public static final String PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST="productionUnitProductAreaGroupList";
    
    private List<ProductionUnitProductAreaGroup> groups;

    public ProductionUnitModel(ProductionUnit object) {
        super(object);
        groups=new ArrayList<ProductionUnitProductAreaGroup>();
        if(object.getProductionUnitProductAreaGroups()!=null){
            groups.addAll(object.getProductionUnitProductAreaGroups());
        }
    }
    
    public String getProductionUnitName(){
        return object.getProductionUnitName();
    }
    public void setProductionUnitName(String aName){
        String oldName=getProductionUnitName();
        object.setProductionUnitName(aName);
        firePropertyChange(PROPERTY_PRODUCTION_UNIT_NAME, oldName, aName);
    }
    public ArticleType getArticleType(){
        return object.getArticleType();
    }
    public void setArticleType(ArticleType aArticleType){
        ArticleType oldType=getArticleType();
        object.setArticleType(aArticleType);
        firePropertyChange(PROPERTY_ARTICLE_TYPE, oldType, aArticleType);
    }
    public List<ProductionUnitProductAreaGroup> getProductionUnitProductAreaGroupList(){
        return groups;
    }
    public void setProductionUnitProductAreaGroupList(List<ProductionUnitProductAreaGroup> list){
        List<ProductionUnitProductAreaGroup> oldList=new ArrayList<ProductionUnitProductAreaGroup>(getProductionUnitProductAreaGroupList());
        groups.clear();
        groups.addAll(list);
        firePropertyChange(PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST, oldList, list);
    }

    @Override
    public void viewToModel() {
        Set<ProductionUnitProductAreaGroup> set = object.getProductionUnitProductAreaGroups();
        if(set==null){
            set = new HashSet<ProductionUnitProductAreaGroup>();
        }
        set.clear();
        set.addAll(groups);
        object.setProductionUnitProductAreaGroups(set);
    }

    @Override
    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
        presentationModel.getBufferedModel(PROPERTY_PRODUCTION_UNIT_NAME).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ARTICLE_TYPE).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST).addValueChangeListener(listener);
        
    }

    @Override
    public ProductionUnitModel getBufferedObjectModel(PresentationModel presentationModel) {
        ProductionUnitModel productionUnitModel = new ProductionUnitModel(
                new ProductionUnit());
        productionUnitModel.setProductionUnitName((String) presentationModel
                .getBufferedValue(PROPERTY_PRODUCTION_UNIT_NAME));
        productionUnitModel.setArticleType((ArticleType) presentationModel
                .getBufferedValue(PROPERTY_ARTICLE_TYPE));
        productionUnitModel.setProductionUnitProductAreaGroupList((List<ProductionUnitProductAreaGroup>) presentationModel
                .getBufferedValue(PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST));
        return productionUnitModel;
    }

    public void firePropertyChanged() {
        this.fireMultiplePropertiesChanged();
    }
}
