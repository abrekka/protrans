/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.IComment;
/*     */ import no.ugland.utransprod.model.PreventiveActionComment;
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
/*     */ public class PreventiveActionCommentModel extends AbstractModel<IComment, ICommentModel> implements ICommentModel {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_COMMENT_TYPE = "commentType";
/*     */ 
/*     */    public PreventiveActionCommentModel(PreventiveActionComment comment) {
/*  34 */       super(comment);
/*  35 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getUserName() {
/*  41 */       return ((IComment)this.object).getUserName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setUserName(String userName) {
/*  48 */       String oldName = this.getUserName();
/*  49 */       ((IComment)this.object).setUserName(userName);
/*  50 */       this.firePropertyChange("userName", oldName, userName);
/*  51 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getComment() {
/*  57 */       return ((IComment)this.object).getComment();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setComment(String comment) {
/*  64 */       String oldComment = this.getComment();
/*  65 */       ((IComment)this.object).setComment(comment);
/*  66 */       this.firePropertyChange("comment", oldComment, comment);
/*  67 */    }
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
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/*  92 */       presentationModel.getBufferedModel("userName").addValueChangeListener(listener);
/*     */ 
/*  94 */       presentationModel.getBufferedModel("comment").addValueChangeListener(listener);
/*     */ 
/*  96 */       presentationModel.getBufferedModel("commentType").addValueChangeListener(listener);
/*     */ 
/*     */ 
/*  99 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public PreventiveActionCommentModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 107 */       PreventiveActionCommentModel model = new PreventiveActionCommentModel(new PreventiveActionComment());
/*     */ 
/* 109 */       model.setUserName((String)presentationModel.getBufferedValue("userName"));
/*     */ 
/* 111 */       model.setComment((String)presentationModel.getBufferedValue("comment"));
/*     */ 
/*     */ 
/* 114 */       return model;
/*     */    }
/*     */ }
