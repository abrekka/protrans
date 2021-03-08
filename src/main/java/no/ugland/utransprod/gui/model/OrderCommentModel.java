/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.ApplicationUser;
/*     */ import no.ugland.utransprod.model.IComment;
/*     */ import no.ugland.utransprod.model.OrderComment;
/*     */ import no.ugland.utransprod.util.CommentTypeUtil;
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
/*     */ public class OrderCommentModel extends AbstractModel<IComment, ICommentModel> implements ICommentModel {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_FOR_TRANSPORT_BOOL = "forTransportBool";
/*     */    public static final String PROPERTY_FOR_PACKAGE_BOOL = "forPackageBool";
/*     */ 
/*     */    public OrderCommentModel(OrderComment orderComment) {
/*  29 */       super(orderComment);
/*  30 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getUserName() {
/*  36 */       return ((IComment)this.object).getUserName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setUserName(String userName) {
/*  43 */       String oldName = this.getUserName();
/*  44 */       ((IComment)this.object).setUserName(userName);
/*  45 */       this.firePropertyChange("userName", oldName, userName);
/*  46 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getComment() {
/*  52 */       return ((IComment)this.object).getComment();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setComment(String comment) {
/*  59 */       String oldComment = this.getComment();
/*  60 */       ((IComment)this.object).setComment(comment);
/*  61 */       this.firePropertyChange("comment", oldComment, comment);
/*  62 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getForTransportBool() {
/*  68 */       return CommentTypeUtil.hasCommentType(((IComment)this.object).getCommentTypes(), "Transport");
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setForTransportBool(Boolean forTransport) {
/*  79 */       Boolean oldTransport = this.getForTransportBool();
/*     */ 
/*  81 */       if (forTransport) {
/*  82 */          ((IComment)this.object).addCommentType(CommentTypeUtil.getCommentType("Transport"));
/*     */       } else {
/*  84 */          ((IComment)this.object).removeCommentType(CommentTypeUtil.getCommentType("Transport"));
/*     */       }
/*     */ 
/*  87 */       this.firePropertyChange("forTransportBool", oldTransport, forTransport);
/*     */ 
/*  89 */    }
/*     */ 
/*     */    public void setForPackageBool(Boolean forPackage) {
/*  92 */       Boolean oldPackage = this.getForPackageBool();
/*     */ 
/*  94 */       if (forPackage) {
/*  95 */          ((IComment)this.object).addCommentType(CommentTypeUtil.getCommentType("Pakking"));
/*     */       } else {
/*  97 */          ((IComment)this.object).removeCommentType(CommentTypeUtil.getCommentType("Pakking"));
/*     */ 
/*     */       }
/*     */ 
/* 101 */       this.firePropertyChange("forPackageBool", oldPackage, forPackage);
/* 102 */    }
/*     */ 
/*     */    public Boolean getForPackageBool() {
/* 105 */       return CommentTypeUtil.hasCommentType(((IComment)this.object).getCommentTypes(), "Pakking");
/*     */    }
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
/* 118 */       presentationModel.getBufferedModel("userName").addValueChangeListener(listener);
/*     */ 
/* 120 */       presentationModel.getBufferedModel("comment").addValueChangeListener(listener);
/*     */ 
/* 122 */       presentationModel.getBufferedModel("forTransportBool").addValueChangeListener(listener);
/*     */ 
/* 124 */       presentationModel.getBufferedModel("forPackageBool").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 127 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public OrderCommentModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 135 */       OrderCommentModel model = new OrderCommentModel(new OrderComment());
/* 136 */       model.setUserName((String)presentationModel.getBufferedValue("userName"));
/*     */ 
/* 138 */       model.setComment((String)presentationModel.getBufferedValue("comment"));
/*     */ 
/* 140 */       model.setForTransportBool((Boolean)presentationModel.getBufferedValue("forTransportBool"));
/*     */ 
/* 142 */       model.setForPackageBool((Boolean)presentationModel.getBufferedValue("forPackageBool"));
/*     */ 
/* 144 */       return model;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public static OrderComment createOrderCommentWithUserAndDate(ApplicationUser applicationUser) {
/* 150 */       OrderComment orderComment = new OrderComment();
/* 151 */       orderComment.setUserName(applicationUser.getUserName());
/* 152 */       orderComment.setCommentDate(Util.getCurrentDate());
/* 153 */       orderComment.addCommentType(CommentTypeUtil.getCommentType("Ordre"));
/* 154 */       return orderComment;
/*     */    }
/*     */ }
