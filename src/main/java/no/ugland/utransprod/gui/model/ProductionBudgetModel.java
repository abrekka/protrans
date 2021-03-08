/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.math.BigDecimal;
/*     */ import no.ugland.utransprod.model.Budget;
/*     */ import no.ugland.utransprod.model.ProductArea;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProductionBudgetModel extends AbstractModel<Budget, ProductionBudgetModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_BUDGET_ID = "budgetId";
/*     */    public static final String PROPERTY_BUDGET_YEAR = "budgetYear";
/*     */    public static final String PROPERTY_BUDGET_WEEK = "budgetWeek";
/*     */    public static final String PROPERTY_BUDGET_VALUE = "budgetValue";
/*     */    public static final String PROPERTY_PRODUCT_AREA = "productArea";
/*     */ 
/*     */    public ProductionBudgetModel(Budget object) {
/*  53 */       super(object);
/*  54 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getBudgetYear() {
/*  60 */       if (((Budget)this.object).getBudgetYear() == null) {
/*  61 */          ((Budget)this.object).setBudgetYear(Util.getCurrentYear());
/*     */ 
/*     */       }
/*     */ 
/*  65 */       return ((Budget)this.object).getBudgetYear();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setBudgetYear(Integer budgetYear) {
/*  72 */       Integer oldYear = this.getBudgetYear();
/*  73 */       ((Budget)this.object).setBudgetYear(budgetYear);
/*  74 */       this.firePropertyChange("budgetYear", oldYear, budgetYear);
/*  75 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getBudgetId() {
/*  81 */       return ((Budget)this.object).getBudgetId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setBudgetId(Integer budgetId) {
/*  88 */       Integer oldId = this.getBudgetId();
/*  89 */       ((Budget)this.object).setBudgetId(budgetId);
/*  90 */       this.firePropertyChange("budgetId", oldId, budgetId);
/*  91 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getBudgetWeek() {
/*  97 */       return ((Budget)this.object).getBudgetWeek();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setBudgetWeek(Integer budgetWeek) {
/* 104 */       Integer oldWeek = this.getBudgetWeek();
/* 105 */       ((Budget)this.object).setBudgetWeek(budgetWeek);
/* 106 */       this.firePropertyChange("budgetWeek", oldWeek, budgetWeek);
/* 107 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ProductArea getProductArea() {
/* 113 */       return ((Budget)this.object).getProductArea();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setProductArea(ProductArea productArea) {
/* 120 */       ProductArea oldArea = this.getProductArea();
/* 121 */       ((Budget)this.object).setProductArea(productArea);
/* 122 */       this.firePropertyChange("productArea", oldArea, productArea);
/* 123 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getBudgetValue() {
/* 129 */       return ((Budget)this.object).getBudgetValue() != null ? String.valueOf(((Budget)this.object).getBudgetValue()) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setBudgetValue(String budgetValue) {
/* 139 */       String oldValue = this.getBudgetValue();
/* 140 */       if (budgetValue != null && Util.isNumber(budgetValue)) {
/* 141 */          ((Budget)this.object).setBudgetValue(BigDecimal.valueOf(Double.valueOf(budgetValue)));
/*     */ 
/*     */       } else {
/* 144 */          ((Budget)this.object).setBudgetValue((BigDecimal)null);
/*     */       }
/*     */ 
/* 147 */       this.firePropertyChange("budgetValue", oldValue, budgetValue);
/* 148 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 157 */       presentationModel.getBufferedModel("budgetYear").addValueChangeListener(listener);
/*     */ 
/* 159 */       presentationModel.getBufferedModel("budgetWeek").addValueChangeListener(listener);
/*     */ 
/* 161 */       presentationModel.getBufferedModel("budgetValue").addValueChangeListener(listener);
/*     */ 
/* 163 */       presentationModel.getBufferedModel("productArea").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 166 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ProductionBudgetModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 174 */       ProductionBudgetModel productionBudgetModel = new ProductionBudgetModel(new Budget());
/*     */ 
/* 176 */       productionBudgetModel.setBudgetId((Integer)presentationModel.getBufferedValue("budgetId"));
/*     */ 
/* 178 */       productionBudgetModel.setBudgetYear((Integer)presentationModel.getBufferedValue("budgetYear"));
/*     */ 
/* 180 */       productionBudgetModel.setBudgetWeek((Integer)presentationModel.getBufferedValue("budgetWeek"));
/*     */ 
/* 182 */       productionBudgetModel.setBudgetValue((String)presentationModel.getBufferedValue("budgetValue"));
/*     */ 
/* 184 */       productionBudgetModel.setProductArea((ProductArea)presentationModel.getBufferedValue("productArea"));
/*     */ 
/* 186 */       return productionBudgetModel;
/*     */    }
/*     */ }
