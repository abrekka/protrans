/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.ExternalOrderLineAttribute;
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
/*     */ public class ExternalOrderLineAttributeModel extends AbstractModel<ExternalOrderLineAttribute, ExternalOrderLineAttributeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_NAME = "externalOrderLineAttributeName";
/*     */    public static final String PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_VALUE = "externalOrderLineAttributeValue";
/*     */ 
/*     */    public ExternalOrderLineAttributeModel(ExternalOrderLineAttribute object) {
/*  37 */       super(object);
/*  38 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getExternalOrderLineAttributeName() {
/*  44 */       return ((ExternalOrderLineAttribute)this.object).getExternalOrderLineAttributeName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setExternalOrderLineAttributeName(String externalOrderLineAttributeName) {
/*  52 */       String oldName = this.getExternalOrderLineAttributeName();
/*  53 */       ((ExternalOrderLineAttribute)this.object).setExternalOrderLineAttributeName(externalOrderLineAttributeName);
/*     */ 
/*  55 */       this.firePropertyChange("externalOrderLineAttributeName", oldName, externalOrderLineAttributeName);
/*     */ 
/*  57 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getExternalOrderLineAttributeValue() {
/*  63 */       return ((ExternalOrderLineAttribute)this.object).getExternalOrderLineAttributeValue();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setExternalOrderLineAttributeValue(String externalOrderLineAttributeValue) {
/*  71 */       String oldValue = this.getExternalOrderLineAttributeValue();
/*  72 */       ((ExternalOrderLineAttribute)this.object).setExternalOrderLineAttributeValue(externalOrderLineAttributeValue);
/*     */ 
/*  74 */       this.firePropertyChange("externalOrderLineAttributeValue", oldValue, externalOrderLineAttributeValue);
/*     */ 
/*  76 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/*  85 */       presentationModel.getBufferedModel("externalOrderLineAttributeName").addValueChangeListener(listener);
/*     */ 
/*     */ 
/*  88 */       presentationModel.getBufferedModel("externalOrderLineAttributeValue").addValueChangeListener(listener);
/*     */ 
/*     */ 
/*  91 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ExternalOrderLineAttributeModel getBufferedObjectModel(PresentationModel presentationModel) {
/*  99 */       ExternalOrderLineAttributeModel model = new ExternalOrderLineAttributeModel(new ExternalOrderLineAttribute());
/*     */ 
/* 101 */       model.setExternalOrderLineAttributeName((String)presentationModel.getBufferedValue("externalOrderLineAttributeName"));
/*     */ 
/* 103 */       model.setExternalOrderLineAttributeValue((String)presentationModel.getBufferedValue("externalOrderLineAttributeValue"));
/*     */ 
/*     */ 
/* 106 */       return model;
/*     */    }
/*     */ }
