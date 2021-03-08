/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import no.ugland.utransprod.model.ArticleType;
/*    */ import no.ugland.utransprod.model.ProductionUnit;
/*    */ import no.ugland.utransprod.model.ProductionUnitProductAreaGroup;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProductionUnitModel extends AbstractModel<ProductionUnit, ProductionUnitModel> {
/*    */    public static final String PROPERTY_PRODUCTION_UNIT_NAME = "productionUnitName";
/*    */    public static final String PROPERTY_ARTICLE_TYPE = "articleType";
/*    */    public static final String PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST = "productionUnitProductAreaGroupList";
/*    */    private List<ProductionUnitProductAreaGroup> groups = new ArrayList();
/*    */ 
/*    */    public ProductionUnitModel(ProductionUnit object) {
/* 23 */       super(object);
/*    */ 
/* 25 */       if (object.getProductionUnitProductAreaGroups() != null) {
/* 26 */          this.groups.addAll(object.getProductionUnitProductAreaGroups());
/*    */       }
/* 28 */    }
/*    */ 
/*    */    public String getProductionUnitName() {
/* 31 */       return ((ProductionUnit)this.object).getProductionUnitName();
/*    */    }
/*    */    public void setProductionUnitName(String aName) {
/* 34 */       String oldName = this.getProductionUnitName();
/* 35 */       ((ProductionUnit)this.object).setProductionUnitName(aName);
/* 36 */       this.firePropertyChange("productionUnitName", oldName, aName);
/* 37 */    }
/*    */    public ArticleType getArticleType() {
/* 39 */       return ((ProductionUnit)this.object).getArticleType();
/*    */    }
/*    */    public void setArticleType(ArticleType aArticleType) {
/* 42 */       ArticleType oldType = this.getArticleType();
/* 43 */       ((ProductionUnit)this.object).setArticleType(aArticleType);
/* 44 */       this.firePropertyChange("articleType", oldType, aArticleType);
/* 45 */    }
/*    */    public List<ProductionUnitProductAreaGroup> getProductionUnitProductAreaGroupList() {
/* 47 */       return this.groups;
/*    */    }
/*    */    public void setProductionUnitProductAreaGroupList(List<ProductionUnitProductAreaGroup> list) {
/* 50 */       List<ProductionUnitProductAreaGroup> oldList = new ArrayList(this.getProductionUnitProductAreaGroupList());
/* 51 */       this.groups.clear();
/* 52 */       this.groups.addAll(list);
/* 53 */       this.firePropertyChange("productionUnitProductAreaGroupList", oldList, list);
/* 54 */    }
/*    */ 
/*    */ 
/*    */    public void viewToModel() {
/* 58 */       Set<ProductionUnitProductAreaGroup> set = ((ProductionUnit)this.object).getProductionUnitProductAreaGroups();
/* 59 */       if (set == null) {
/* 60 */          set = new HashSet();
/*    */       }
/* 62 */       ((Set)set).clear();
/* 63 */       ((Set)set).addAll(this.groups);
/* 64 */       ((ProductionUnit)this.object).setProductionUnitProductAreaGroups((Set)set);
/* 65 */    }
/*    */ 
/*    */ 
/*    */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 69 */       presentationModel.getBufferedModel("productionUnitName").addValueChangeListener(listener);
/* 70 */       presentationModel.getBufferedModel("articleType").addValueChangeListener(listener);
/* 71 */       presentationModel.getBufferedModel("productionUnitProductAreaGroupList").addValueChangeListener(listener);
/*    */ 
/* 73 */    }
/*    */ 
/*    */ 
/*    */    public ProductionUnitModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 77 */       ProductionUnitModel productionUnitModel = new ProductionUnitModel(new ProductionUnit());
/*    */ 
/* 79 */       productionUnitModel.setProductionUnitName((String)presentationModel.getBufferedValue("productionUnitName"));
/*    */ 
/* 81 */       productionUnitModel.setArticleType((ArticleType)presentationModel.getBufferedValue("articleType"));
/*    */ 
/* 83 */       productionUnitModel.setProductionUnitProductAreaGroupList((List)presentationModel.getBufferedValue("productionUnitProductAreaGroupList"));
/*    */ 
/* 85 */       return productionUnitModel;
/*    */    }
/*    */ 
/*    */    public void firePropertyChanged() {
/* 89 */       this.fireMultiplePropertiesChanged();
/* 90 */    }
/*    */ }
