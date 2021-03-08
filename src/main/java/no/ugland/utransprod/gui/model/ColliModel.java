/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.list.ArrayListModel;
/*     */ import com.jgoodies.validation.util.ValidationUtils;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.model.Colli;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import org.apache.commons.lang.StringUtils;
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
/*     */ public class ColliModel extends AbstractModel<Colli, ColliModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_COLLI_NAME = "colliName";
/*     */    public static final String PROPERTY_HEIGHT = "height";
/*     */    public static final String PROPERTY_LENGHT = "lenght";
/*     */    public static final String PROPERTY_WIDHT = "widht";
/*     */    public static final String PROPERTY_ORDER_LINES = "orderLines";
/*     */    public static final String PROPERTY_NUMBER_OF_COLLIES = "numberOfCollies";
/*     */    public static final String PROPERTY_COLLI_NAME_AND_NUMBER = "colliNameAndNumber";
/*     */    public static final String PROPERTY_COLLI_HEIGHT = "colliHeightWidhtLenght";
/*     */    private final ArrayListModel orderLineList = new ArrayListModel();
/*     */ 
/*     */    public ColliModel(Colli colli) {
/*  41 */       super(colli);
/*     */ 
/*     */ 
/*  44 */       if (((Colli)this.object).getOrderLines() != null) {
/*  45 */          this.orderLineList.addAll(((Colli)this.object).getOrderLines());
/*     */       }
/*     */ 
/*  48 */    }
/*     */ 
/*     */    public String getColliName() {
/*  51 */       return ((Colli)this.object).getColliName();
/*     */    }
/*     */ 
/*     */    public void setColliName(String colliName) {
/*  55 */       String oldName = this.getColliName();
/*  56 */       ((Colli)this.object).setColliName(colliName);
/*  57 */       this.firePropertyChange("colliName", oldName, colliName);
/*  58 */    }
/*     */ 
/*     */    public String getHeight() {
/*  61 */       return ((Colli)this.object).getHeight() == null ? null : ((Colli)this.object).getHeight().toString();
/*     */    }
/*     */ 
/*     */    public void setHeight(String height) {
/*  65 */       String oldHeight = this.getHeight();
/*  66 */       ((Colli)this.object).setHeight(StringUtils.isEmpty(height) ? null : Integer.valueOf(StringUtils.trim(height)));
/*  67 */       this.firePropertyChange("height", oldHeight, height);
/*  68 */    }
/*     */ 
/*     */    public String getWidht() {
/*  71 */       return ((Colli)this.object).getWidht() == null ? null : ((Colli)this.object).getWidht().toString();
/*     */    }
/*     */ 
/*     */    public void setWidht(String widht) {
/*  75 */       if (!ValidationUtils.isNumeric(widht)) {
/*  76 */          widht = "0";
/*     */       }
/*  78 */       String oldWidht = this.getWidht();
/*  79 */       ((Colli)this.object).setWidht(StringUtils.isEmpty(widht) ? null : Integer.valueOf(StringUtils.trim(widht)));
/*  80 */       this.firePropertyChange("widht", oldWidht, widht);
/*  81 */    }
/*     */ 
/*     */    public String getLenght() {
/*  84 */       return ((Colli)this.object).getLenght() == null ? null : ((Colli)this.object).getLenght().toString();
/*     */    }
/*     */ 
/*     */    public void setLenght(String lenght) {
/*  88 */       if (!ValidationUtils.isNumeric(lenght)) {
/*  89 */          lenght = "0";
/*     */       }
/*  91 */       String oldLenght = this.getLenght();
/*     */ 
/*  93 */       ((Colli)this.object).setLenght(StringUtils.isEmpty(lenght) ? null : Integer.valueOf(StringUtils.trim(lenght)));
/*  94 */       this.firePropertyChange("lenght", oldLenght, lenght);
/*  95 */    }
/*     */ 
/*     */    public String getColliNameAndNumber() {
/*  98 */       return ((Colli)this.object).getColliName() + (((Colli)this.object).getNumberOfCollies() != null ? " - " + ((Colli)this.object).getNumberOfCollies() : "");
/*     */    }
/*     */ 
/*     */    public String getColliHeightWidhtLenght() {
/* 102 */       return "L:" + (((Colli)this.object).getLenght() == null ? "0" : ((Colli)this.object).getLenght()) + " B:" + (((Colli)this.object).getWidht() == null ? "0" : ((Colli)this.object).getWidht()) + " H:" + (((Colli)this.object).getHeight() == null ? "0" : ((Colli)this.object).getHeight());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getNumberOfCollies() {
/* 108 */       return ((Colli)this.object).getNumberOfCollies() != null ? String.valueOf(((Colli)this.object).getNumberOfCollies()) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setNumberOfCollies(String numberOfCollies) {
/* 115 */       if (!ValidationUtils.isNumeric(numberOfCollies)) {
/* 116 */          numberOfCollies = "0";
/*     */       }
/* 118 */       String oldNumber = this.getNumberOfCollies();
/* 119 */       ((Colli)this.object).setNumberOfCollies(!StringUtils.isEmpty(numberOfCollies) ? Integer.valueOf(numberOfCollies) : null);
/* 120 */       this.firePropertyChange("numberOfCollies", oldNumber, numberOfCollies);
/* 121 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<OrderLine> getOrderLines() {
/* 128 */       return this.orderLineList;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 137 */       presentationModel.getBufferedModel("colliName").addValueChangeListener(listener);
/* 138 */       presentationModel.getBufferedModel("orderLines").addValueChangeListener(listener);
/* 139 */       presentationModel.getBufferedModel("height").addValueChangeListener(listener);
/* 140 */       presentationModel.getBufferedModel("lenght").addValueChangeListener(listener);
/* 141 */       presentationModel.getBufferedModel("widht").addValueChangeListener(listener);
/*     */ 
/* 143 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ColliModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 150 */       ColliModel colliModel = new ColliModel(new Colli("ColliModel"));
/* 151 */       colliModel.setColliName((String)presentationModel.getBufferedValue("colliName"));
/* 152 */       colliModel.setHeight((String)presentationModel.getBufferedValue("height"));
/* 153 */       colliModel.setLenght((String)presentationModel.getBufferedValue("lenght"));
/* 154 */       colliModel.setWidht((String)presentationModel.getBufferedValue("widht"));
/* 155 */       return colliModel;
/*     */    }
/*     */ 
/*     */    public void firePropertyChanged(String propertyName) {
/* 159 */       this.firePropertyChange(propertyName, (Object)null, this.getColliNameAndNumber());
/*     */ 
/* 161 */    }
/*     */ }
