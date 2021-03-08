/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.FunctionCategory;
/*     */ import no.ugland.utransprod.model.JobFunction;
/*     */ import no.ugland.utransprod.model.PreventiveAction;
/*     */ import no.ugland.utransprod.model.PreventiveActionComment;
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
/*     */ public class PreventiveActionModel extends AbstractModel<PreventiveAction, PreventiveActionModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_PREVENTIVE_ACTION_ID = "preventiveActionId";
/*     */    public static final String PROPERTY_MANAGER = "manager";
/*     */    public static final String PROPERTY_JOB_FUNCTION = "jobFunction";
/*     */    public static final String PROPERTY_FUNCTION_CATEGORY = "functionCategory";
/*     */    public static final String PROPERTY_DESCRIPTION = "description";
/*     */    public static final String PROPERTY_EXPECTED_OUTCOME = "expectedOutcome";
/*     */    public static final String PROPERTY_COMMENTS = "comments";
/*     */    public static final String PROPERTY_PROJECT_CLOSED_BOOLEAN = "projectClosedBoolean";
/*     */    public static final String PROPERTY_PREVENTIVE_ACTION_NAME = "preventiveActionName";
/*     */    private final List<PreventiveActionComment> commentList = new ArrayList();
/*     */ 
/*     */    public PreventiveActionModel(PreventiveAction object) {
/*  84 */       super(object);
/*     */ 
/*  86 */       if (object.getPreventiveActionComments() != null) {
/*  87 */          this.commentList.addAll(object.getPreventiveActionComments());
/*     */       }
/*  89 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPreventiveActionId() {
/*  95 */       return ((PreventiveAction)this.object).getPreventiveActionId() != null ? String.valueOf(((PreventiveAction)this.object).getPreventiveActionId()) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPreventiveActionId(String preventiveActionId) {
/* 105 */       String oldPreventiveActionId = this.getPreventiveActionId();
/* 106 */       if (preventiveActionId != null) {
/* 107 */          ((PreventiveAction)this.object).setPreventiveActionId(Integer.valueOf(preventiveActionId));
/*     */       } else {
/* 109 */          ((PreventiveAction)this.object).setPreventiveActionId((Integer)null);
/*     */       }
/* 111 */       this.firePropertyChange("preventiveActionId", oldPreventiveActionId, preventiveActionId);
/*     */ 
/* 113 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getManager() {
/* 119 */       return ((PreventiveAction)this.object).getManager();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setManager(String manager) {
/* 126 */       String oldManager = this.getManager();
/* 127 */       ((PreventiveAction)this.object).setManager(manager);
/* 128 */       this.firePropertyChange("manager", oldManager, manager);
/* 129 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JobFunction getJobFunction() {
/* 135 */       return ((PreventiveAction)this.object).getJobFunction();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setJobFunction(JobFunction jobFunction) {
/* 142 */       JobFunction oldFunction = this.getJobFunction();
/* 143 */       ((PreventiveAction)this.object).setJobFunction(jobFunction);
/* 144 */       this.firePropertyChange("jobFunction", oldFunction, jobFunction);
/* 145 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public FunctionCategory getFunctionCategory() {
/* 151 */       return ((PreventiveAction)this.object).getFunctionCategory();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFunctionCategory(FunctionCategory functionCategory) {
/* 158 */       FunctionCategory oldCategory = this.getFunctionCategory();
/* 159 */       ((PreventiveAction)this.object).setFunctionCategory(functionCategory);
/* 160 */       this.firePropertyChange("functionCategory", oldCategory, functionCategory);
/*     */ 
/* 162 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDescription() {
/* 168 */       return ((PreventiveAction)this.object).getDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDescription(String desc) {
/* 175 */       String oldDesc = this.getDescription();
/* 176 */       ((PreventiveAction)this.object).setDescription(desc);
/* 177 */       this.firePropertyChange("description", oldDesc, desc);
/* 178 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getExpectedOutcome() {
/* 184 */       return ((PreventiveAction)this.object).getExpectedOutcome();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setExpectedOutcome(String outcome) {
/* 191 */       String oldOutcome = this.getExpectedOutcome();
/* 192 */       ((PreventiveAction)this.object).setExpectedOutcome(outcome);
/* 193 */       this.firePropertyChange("expectedOutcome", oldOutcome, outcome);
/* 194 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<PreventiveActionComment> getComments() {
/* 200 */       return new ArrayList(this.commentList);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setComments(List<PreventiveActionComment> comments) {
/* 207 */       List<PreventiveActionComment> oldList = new ArrayList(this.commentList);
/*     */ 
/* 209 */       this.commentList.clear();
/* 210 */       this.commentList.addAll(comments);
/* 211 */       this.firePropertyChange("comments", oldList, comments);
/* 212 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addComment(PreventiveActionComment comment) {
/* 218 */       if (comment != null) {
/* 219 */          comment.setPreventiveAction((PreventiveAction)this.object);
/*     */ 
/* 221 */          List<PreventiveActionComment> oldList = new ArrayList(this.commentList);
/*     */ 
/* 223 */          this.commentList.add(comment);
/* 224 */          this.firePropertyChange("comments", oldList, this.commentList);
/*     */       }
/* 226 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getProjectClosedBoolean() {
/* 232 */       return ((PreventiveAction)this.object).getClosedDate() != null ? Boolean.TRUE : Boolean.FALSE;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setProjectClosedBoolean(Boolean closed) {
/* 241 */       Boolean oldClosed = this.getProjectClosedBoolean();
/* 242 */       if (closed) {
/* 243 */          ((PreventiveAction)this.object).setClosedDate(Util.getCurrentDate());
/*     */       } else {
/* 245 */          ((PreventiveAction)this.object).setClosedDate((Date)null);
/*     */       }
/*     */ 
/* 248 */       this.firePropertyChange("projectClosedBoolean", oldClosed, closed);
/* 249 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPreventiveActionName() {
/* 255 */       return ((PreventiveAction)this.object).getPreventiveActionName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPreventiveActionName(String name) {
/* 262 */       String oldName = this.getPreventiveActionName();
/* 263 */       ((PreventiveAction)this.object).setPreventiveActionName(name);
/* 264 */       this.firePropertyChange("preventiveActionName", oldName, name);
/* 265 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 274 */       presentationModel.getBufferedModel("preventiveActionId").addValueChangeListener(listener);
/* 275 */       presentationModel.getBufferedModel("manager").addValueChangeListener(listener);
/*     */ 
/* 277 */       presentationModel.getBufferedModel("jobFunction").addValueChangeListener(listener);
/*     */ 
/* 279 */       presentationModel.getBufferedModel("functionCategory").addValueChangeListener(listener);
/*     */ 
/* 281 */       presentationModel.getBufferedModel("description").addValueChangeListener(listener);
/*     */ 
/* 283 */       presentationModel.getBufferedModel("expectedOutcome").addValueChangeListener(listener);
/*     */ 
/* 285 */       presentationModel.getBufferedModel("projectClosedBoolean").addValueChangeListener(listener);
/*     */ 
/* 287 */       presentationModel.getBufferedModel("preventiveActionName").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 290 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public PreventiveActionModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 298 */       PreventiveActionModel model = new PreventiveActionModel(new PreventiveAction());
/*     */ 
/* 300 */       model.setPreventiveActionId((String)presentationModel.getBufferedValue("preventiveActionId"));
/* 301 */       model.setManager((String)presentationModel.getBufferedValue("manager"));
/*     */ 
/* 303 */       model.setJobFunction((JobFunction)presentationModel.getBufferedValue("jobFunction"));
/*     */ 
/* 305 */       model.setFunctionCategory((FunctionCategory)presentationModel.getBufferedValue("functionCategory"));
/*     */ 
/* 307 */       model.setDescription((String)presentationModel.getBufferedValue("description"));
/*     */ 
/* 309 */       model.setExpectedOutcome((String)presentationModel.getBufferedValue("expectedOutcome"));
/*     */ 
/* 311 */       model.setProjectClosedBoolean((Boolean)presentationModel.getBufferedValue("projectClosedBoolean"));
/*     */ 
/* 313 */       model.setPreventiveActionName((String)presentationModel.getBufferedValue("preventiveActionName"));
/*     */ 
/* 315 */       return model;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void viewToModel() {
/* 323 */       Set<PreventiveActionComment> comments = ((PreventiveAction)this.object).getPreventiveActionComments();
/*     */ 
/* 325 */       if (comments == null) {
/* 326 */          comments = new HashSet();
/*     */       }
/* 328 */       ((Set)comments).clear();
/* 329 */       ((Set)comments).addAll(this.commentList);
/* 330 */       ((PreventiveAction)this.object).setPreventiveActionComments((Set)comments);
/* 331 */    }
/*     */ }
