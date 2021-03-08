/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.FunctionCategory;
/*     */ import no.ugland.utransprod.model.JobFunction;
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
/*     */ public class FunctionCategoryModel extends AbstractModel<FunctionCategory, FunctionCategoryModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_FUNCTION_CATEGORY_DESCRIPTION = "functionCategoryDescription";
/*     */    public static final String PROPERTY_FUNCTION_CATEGORY_NAME = "functionCategoryName";
/*     */    public static final String PROPERTY_JOB_FUNCTION = "jobFunction";
/*     */ 
/*     */    public FunctionCategoryModel(FunctionCategory object) {
/*  37 */       super(object);
/*  38 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getFunctionCategoryDescription() {
/*  44 */       return ((FunctionCategory)this.object).getFunctionCategoryDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFunctionCategoryDescription(String functionCategoryDescription) {
/*  50 */       String oldDesc = this.getFunctionCategoryDescription();
/*  51 */       ((FunctionCategory)this.object).setFunctionCategoryDescription(functionCategoryDescription);
/*  52 */       this.firePropertyChange("functionCategoryDescription", oldDesc, functionCategoryDescription);
/*  53 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getFunctionCategoryName() {
/*  59 */       return ((FunctionCategory)this.object).getFunctionCategoryName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFunctionCategoryName(String functionCategoryName) {
/*  65 */       String oldName = this.getFunctionCategoryName();
/*  66 */       ((FunctionCategory)this.object).setFunctionCategoryName(functionCategoryName);
/*  67 */       this.firePropertyChange("functionCategoryName", oldName, functionCategoryName);
/*  68 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JobFunction getJobFunction() {
/*  74 */       return ((FunctionCategory)this.object).getJobFunction();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setJobFunction(JobFunction jobFunction) {
/*  80 */       JobFunction oldFunction = this.getJobFunction();
/*  81 */       ((FunctionCategory)this.object).setJobFunction(jobFunction);
/*  82 */       this.firePropertyChange("jobFunction", oldFunction, jobFunction);
/*  83 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/*  90 */       presentationModel.getBufferedModel("functionCategoryDescription").addValueChangeListener(listener);
/*  91 */       presentationModel.getBufferedModel("functionCategoryName").addValueChangeListener(listener);
/*  92 */       presentationModel.getBufferedModel("jobFunction").addValueChangeListener(listener);
/*  93 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public FunctionCategoryModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 100 */       FunctionCategoryModel model = new FunctionCategoryModel(new FunctionCategory());
/* 101 */       model.setFunctionCategoryDescription((String)presentationModel.getBufferedValue("functionCategoryDescription"));
/* 102 */       model.setFunctionCategoryName((String)presentationModel.getBufferedValue("functionCategoryName"));
/* 103 */       model.setJobFunction((JobFunction)presentationModel.getBufferedValue("jobFunction"));
/* 104 */       return model;
/*     */    }
/*     */ }
