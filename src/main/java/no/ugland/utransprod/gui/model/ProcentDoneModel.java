/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.ProcentDone;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProcentDoneModel extends AbstractModel<ProcentDone, ProcentDoneModel> {
/*    */    public static final String PROPERTY_PROCENT_DONE_YEAR = "procentDoneYear";
/*    */    public static final String PROPERTY_PROCENT_DONE_WEEK = "procentDoneWeek";
/*    */    public static final String PROPERTY_PROCENT_STRING = "procentString";
/*    */    public static final String PROPERTY_PROCENT_DONE_COMMENT = "procentDoneComment";
/*    */ 
/*    */    public ProcentDoneModel(ProcentDone object) {
/* 18 */       super(object);
/* 19 */    }
/*    */    public Integer getProcentDoneId() {
/* 21 */       return ((ProcentDone)this.object).getProcentDoneId();
/*    */    }
/*    */    public Order getOrder() {
/* 24 */       return ((ProcentDone)this.object).getOrder();
/*    */    }
/*    */ 
/*    */    public Integer getProcentDoneYear() {
/* 28 */       return ((ProcentDone)this.object).getProcentDoneYear() != null ? ((ProcentDone)this.object).getProcentDoneYear() : 0;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setProcentDoneYear(Integer aYear) {
/* 34 */       Integer oldYear = this.getProcentDoneYear();
/* 35 */       ((ProcentDone)this.object).setProcentDoneYear(aYear);
/* 36 */       this.firePropertyChange("procentDoneYear", oldYear, aYear);
/* 37 */    }
/*    */    public Integer getProcentDoneWeek() {
/* 39 */       return ((ProcentDone)this.object).getProcentDoneWeek();
/*    */    }
/*    */    public void setProcentDoneWeek(Integer aWeek) {
/* 42 */       Integer oldWeek = this.getProcentDoneWeek();
/* 43 */       ((ProcentDone)this.object).setProcentDoneWeek(aWeek);
/* 44 */       this.firePropertyChange("procentDoneWeek", oldWeek, aWeek);
/* 45 */    }
/*    */    public String getProcentString() {
/* 47 */       return Util.convertIntegerToString(((ProcentDone)this.object).getProcent());
/*    */    }
/*    */    public void setProcentString(String aProcent) {
/* 50 */       String oldProcent = this.getProcentString();
/* 51 */       ((ProcentDone)this.object).setProcent(Util.convertStringToInteger(aProcent));
/* 52 */       this.firePropertyChange("procentString", oldProcent, aProcent);
/* 53 */    }
/*    */    public String getProcentDoneComment() {
/* 55 */       return ((ProcentDone)this.object).getProcentDoneComment();
/*    */    }
/*    */    public void setProcentDoneComment(String aComment) {
/* 58 */       String oldComment = this.getProcentDoneComment();
/* 59 */       ((ProcentDone)this.object).setProcentDoneComment(aComment);
/* 60 */       this.firePropertyChange("procentDoneComment", oldComment, aComment);
/* 61 */    }
/*    */    public Integer getProcent() {
/* 63 */       return ((ProcentDone)this.object).getProcent();
/*    */    }
/*    */ 
/*    */ 
/*    */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 68 */       presentationModel.getBufferedModel("procentDoneYear").addValueChangeListener(listener);
/* 69 */       presentationModel.getBufferedModel("procentDoneWeek").addValueChangeListener(listener);
/* 70 */       presentationModel.getBufferedModel("procentString").addValueChangeListener(listener);
/* 71 */       presentationModel.getBufferedModel("procentDoneComment").addValueChangeListener(listener);
/*    */ 
/* 73 */    }
/*    */ 
/*    */ 
/*    */    public ProcentDoneModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 77 */       ProcentDoneModel procentDoneModel = new ProcentDoneModel(new ProcentDone());
/*    */ 
/* 79 */       procentDoneModel.setProcentDoneYear((Integer)presentationModel.getBufferedValue("procentDoneYear"));
/*    */ 
/* 81 */       procentDoneModel.setProcentDoneWeek((Integer)presentationModel.getBufferedValue("procentDoneWeek"));
/*    */ 
/* 83 */       procentDoneModel.setProcentString((String)presentationModel.getBufferedValue("procentString"));
/*    */ 
/* 85 */       procentDoneModel.setProcentDoneComment((String)presentationModel.getBufferedValue("procentDoneComment"));
/*    */ 
/* 87 */       return procentDoneModel;
/*    */    }
/*    */ }
