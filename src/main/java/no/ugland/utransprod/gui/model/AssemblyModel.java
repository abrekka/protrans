/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.Assembly;
/*     */ import no.ugland.utransprod.model.OrderComment;
/*     */ import no.ugland.utransprod.model.Supplier;
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
/*     */ public class AssemblyModel extends AbstractModel<Assembly, AssemblyModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_SUPPLIER = "supplier";
/*     */    public static final String PROPERTY_ASSEMBLY_YEAR = "assemblyYear";
/*     */    public static final String PROPERTY_ASSEMBLY_WEEK = "assemblyWeek";
/*     */    public static final String PROPERTY_COMMENT_LIST = "commentList";
/*     */    public static final String PROPERTY_COMMENT = "comment";
/*     */    public static final String PROPERTY_PLANNED = "planned";
/*     */    public static final String PROPERTY_ASSEMBLIED_BOOL = "assembliedBool";
/*     */    public static final String PROPERTY_ASSEMBLIED_DATE = "assembliedDate";
/*     */    private List<OrderComment> assemblyComments = new ArrayList();
/*     */    private String userName;
/*     */ 
/*     */    public AssemblyModel(Assembly object, String aUserName) {
/*  44 */       super(object);
/*     */ 
/*  46 */       if (object.getOrder() != null && object.getOrder().getAssemblyComments() != null) {
/*  47 */          this.assemblyComments.addAll(object.getOrder().getAssemblyComments());
/*     */       }
/*  49 */       this.userName = aUserName;
/*  50 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Supplier getSupplier() {
/*  58 */       return ((Assembly)this.object).getSupplier();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplier(Supplier supplier) {
/*  67 */       Supplier oldSupplier = this.getSupplier();
/*  68 */       ((Assembly)this.object).setSupplier(supplier);
/*  69 */       this.firePropertyChange("supplier", oldSupplier, supplier);
/*  70 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getAssemblyYear() {
/*  78 */       return ((Assembly)this.object).getAssemblyYear();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAssemblyYear(Integer assemblyYear) {
/*  87 */       Integer oldYear = this.getAssemblyYear();
/*  88 */       ((Assembly)this.object).setAssemblyYear(assemblyYear);
/*  89 */       this.firePropertyChange("assemblyYear", oldYear, assemblyYear);
/*  90 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getAssemblyWeek() {
/*  98 */       return ((Assembly)this.object).getAssemblyWeek();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAssemblyWeek(Integer assemblyWeek) {
/* 107 */       Integer oldWeek = this.getAssemblyWeek();
/* 108 */       ((Assembly)this.object).setAssemblyWeek(assemblyWeek);
/* 109 */       this.firePropertyChange("assemblyWeek", oldWeek, assemblyWeek);
/* 110 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getAssembliedBool() {
/* 116 */       return ((Assembly)this.object).getAssembliedBool();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAssembliedBool(Boolean assemblied) {
/* 123 */       Boolean oldAssemblied = this.getAssembliedBool();
/* 124 */       ((Assembly)this.object).setAssembliedBool(assemblied);
/* 125 */       this.firePropertyChange("assembliedBool", oldAssemblied, assemblied);
/* 126 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getAssembliedDate() {
/* 132 */       return ((Assembly)this.object).getAssembliedDate();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAssembliedDate(Date assembliedDate) {
/* 139 */       Date oldAssemblied = this.getAssembliedDate();
/* 140 */       ((Assembly)this.object).setAssembliedDate(assembliedDate);
/* 141 */       this.firePropertyChange("assembliedDate", oldAssemblied, assembliedDate);
/* 142 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<OrderComment> getCommentList() {
/* 150 */       return this.assemblyComments;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCommentList(List<OrderComment> comments) {
/* 159 */       List<OrderComment> oldComments = new ArrayList(this.getCommentList());
/* 160 */       this.assemblyComments.clear();
/* 161 */       if (comments != null) {
/* 162 */          this.assemblyComments.addAll(comments);
/*     */       }
/*     */ 
/* 165 */       this.firePropertyChange("commentList", oldComments, comments);
/* 166 */    }
/*     */ 
/*     */    public String getComment() {
/* 169 */       return ((Assembly)this.object).getAssemblyComment();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setComment(String comments) {
/* 178 */       String oldComment = this.getComment();
/* 179 */       ((Assembly)this.object).setAssemblyComment(comments);
/* 180 */       this.firePropertyChange("comment", oldComment, comments);
/* 181 */    }
/*     */ 
/*     */ 
/*     */    public void viewToModel() {
/* 185 */       if (((Assembly)this.object).getOrder() != null) {
/* 186 */          Set<OrderComment> comments = ((Assembly)this.object).getOrder().getOrderComments();
/* 187 */          if (comments == null) {
/* 188 */             comments = new HashSet();
/*     */          }
/*     */ 
/* 191 */          ((Set)comments).addAll(this.assemblyComments);
/* 192 */          ((Assembly)this.object).getOrder().setOrderComments((Set)comments);
/*     */       }
/* 194 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPlanned() {
/* 200 */       return ((Assembly)this.object).getPlanned();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPlanned(String planned) {
/* 207 */       String oldPlanned = this.getPlanned();
/* 208 */       ((Assembly)this.object).setPlanned(planned);
/* 209 */       this.firePropertyChange("planned", oldPlanned, planned);
/* 210 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 218 */       presentationModel.getBufferedModel("assembliedDate").addValueChangeListener(listener);
/* 219 */       presentationModel.getBufferedModel("supplier").addValueChangeListener(listener);
/* 220 */       presentationModel.getBufferedModel("assemblyWeek").addValueChangeListener(listener);
/* 221 */       presentationModel.getBufferedModel("assemblyYear").addValueChangeListener(listener);
/* 222 */       presentationModel.getBufferedModel("commentList").addValueChangeListener(listener);
/* 223 */       presentationModel.getBufferedModel("comment").addValueChangeListener(listener);
/* 224 */       presentationModel.getBufferedModel("planned").addValueChangeListener(listener);
/*     */ 
/* 226 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public AssemblyModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 234 */       AssemblyModel assemblyModel = new AssemblyModel(new Assembly(), this.userName);
/* 235 */       assemblyModel.setSupplier((Supplier)presentationModel.getBufferedValue("supplier"));
/* 236 */       assemblyModel.setAssemblyWeek((Integer)presentationModel.getBufferedValue("assemblyWeek"));
/* 237 */       assemblyModel.setAssemblyYear((Integer)presentationModel.getBufferedValue("assemblyYear"));
/* 238 */       assemblyModel.setCommentList((List)presentationModel.getBufferedValue("commentList"));
/* 239 */       assemblyModel.setComment((String)presentationModel.getBufferedValue("comment"));
/* 240 */       assemblyModel.setPlanned((String)presentationModel.getBufferedValue("planned"));
/* 241 */       assemblyModel.setAssembliedDate((Date)presentationModel.getBufferedValue("assembliedDate"));
/* 242 */       return assemblyModel;
/*     */    }
/*     */ }
