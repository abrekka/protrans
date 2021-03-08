/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.ApplicationUser;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JobFunctionModel extends AbstractModel<JobFunction, JobFunctionModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_JOB_FUNCTION_NAME = "jobFunctionName";
/*     */    public static final String PROPERTY_JOB_FUNCTION_DESCRIPTION = "jobFunctionDescription";
/*     */    public static final String PROPERTY_MANAGER = "manager";
/*     */ 
/*     */    public JobFunctionModel(JobFunction jobFunction) {
/*  42 */       super(jobFunction);
/*  43 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getJobFunctionName() {
/*  49 */       return ((JobFunction)this.object).getJobFunctionName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setJobFunctionName(String jobFunctionName) {
/*  56 */       String oldName = this.getJobFunctionName();
/*  57 */       ((JobFunction)this.object).setJobFunctionName(jobFunctionName);
/*  58 */       this.firePropertyChange("jobFunctionName", oldName, jobFunctionName);
/*  59 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getJobFunctionDescription() {
/*  65 */       return ((JobFunction)this.object).getJobFunctionDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setJobFunctionDescription(String jobFunctionDescription) {
/*  72 */       String oldDesc = this.getJobFunctionDescription();
/*  73 */       ((JobFunction)this.object).setJobFunctionDescription(jobFunctionDescription);
/*  74 */       this.firePropertyChange("jobFunctionDescription", oldDesc, jobFunctionDescription);
/*     */ 
/*  76 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ApplicationUser getManager() {
/*  82 */       return ((JobFunction)this.object).getManager();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setManager(ApplicationUser manager) {
/*  89 */       ApplicationUser oldManager = this.getManager();
/*  90 */       ((JobFunction)this.object).setManager(manager);
/*  91 */       this.firePropertyChange("manager", oldManager, manager);
/*  92 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 101 */       presentationModel.getBufferedModel("jobFunctionName").addValueChangeListener(listener);
/*     */ 
/* 103 */       presentationModel.getBufferedModel("jobFunctionDescription").addValueChangeListener(listener);
/*     */ 
/* 105 */       presentationModel.getBufferedModel("manager").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 108 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JobFunctionModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 116 */       JobFunctionModel model = new JobFunctionModel(new JobFunction());
/* 117 */       model.setJobFunctionName((String)presentationModel.getBufferedValue("jobFunctionName"));
/*     */ 
/* 119 */       model.setJobFunctionDescription((String)presentationModel.getBufferedValue("jobFunctionDescription"));
/*     */ 
/* 121 */       model.setManager((ApplicationUser)presentationModel.getBufferedValue("manager"));
/*     */ 
/* 123 */       return model;
/*     */    }
/*     */ }
