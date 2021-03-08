/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.Date;
/*     */ import no.ugland.utransprod.model.Info;
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
/*     */ public class InfoModel extends AbstractModel<Info, InfoModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_INFO_TEXT = "infoText";
/*     */    public static final String PROPERTY_DATE_FROM = "dateFrom";
/*     */    public static final String PROPERTY_DATE_TO = "dateTo";
/*     */ 
/*     */    public InfoModel(Info object) {
/*  41 */       super(object);
/*  42 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getInfoText() {
/*  48 */       return ((Info)this.object).getInfoText();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setInfoText(String infoText) {
/*  55 */       String oldInfo = this.getInfoText();
/*  56 */       ((Info)this.object).setInfoText(infoText);
/*  57 */       this.firePropertyChange("infoText", oldInfo, infoText);
/*  58 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getDateFrom() {
/*  64 */       return ((Info)this.object).getDateFrom();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDateFrom(Date dateFrom) {
/*  71 */       Date oldDate = this.getDateFrom();
/*  72 */       ((Info)this.object).setDateFrom(dateFrom);
/*  73 */       this.firePropertyChange("dateFrom", oldDate, dateFrom);
/*  74 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getDateTo() {
/*  80 */       return ((Info)this.object).getDateTo();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDateTo(Date dateTo) {
/*  87 */       Date oldDate = this.getDateTo();
/*  88 */       ((Info)this.object).setDateTo(dateTo);
/*  89 */       this.firePropertyChange("dateTo", oldDate, dateTo);
/*  90 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/*  99 */       presentationModel.getBufferedModel("dateFrom").addValueChangeListener(listener);
/*     */ 
/* 101 */       presentationModel.getBufferedModel("dateTo").addValueChangeListener(listener);
/*     */ 
/* 103 */       presentationModel.getBufferedModel("infoText").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 106 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public InfoModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 113 */       InfoModel infoModel = new InfoModel(new Info());
/* 114 */       infoModel.setDateFrom((Date)presentationModel.getBufferedValue("dateFrom"));
/*     */ 
/* 116 */       infoModel.setDateTo((Date)presentationModel.getBufferedValue("dateTo"));
/*     */ 
/* 118 */       infoModel.setInfoText((String)presentationModel.getBufferedValue("infoText"));
/*     */ 
/*     */ 
/* 121 */       return infoModel;
/*     */    }
/*     */ }
