/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.ExternalOrderLine;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalOrderLineModel extends AbstractModel<ExternalOrderLine, ExternalOrderLineModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_ARTICLE_NAME = "articleName";
/*     */    public static final String PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTES = "externalOrderLineAttributes";
/*     */    public static final String PROPERTY_NUMBER_OF_ITEMS = "numberOfItems";
/*     */ 
/*     */    public ExternalOrderLineModel(ExternalOrderLine object) {
/*  43 */       super(object);
/*  44 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getArticleName() {
/*  50 */       return ((ExternalOrderLine)this.object).getArticleName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setArticleName(String articleName) {
/*  57 */       String oldName = this.getArticleName();
/*  58 */       ((ExternalOrderLine)this.object).setArticleName(articleName);
/*  59 */       this.firePropertyChange("articleName", oldName, articleName);
/*  60 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getNumberOfItems() {
/*  66 */       return ((ExternalOrderLine)this.object).getNumberOfItems();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setNumberOfItems(Integer numberOfItems) {
/*  73 */       Integer oldNumber = this.getNumberOfItems();
/*  74 */       ((ExternalOrderLine)this.object).setNumberOfItems(numberOfItems);
/*  75 */       this.firePropertyChange("numberOfItems", oldNumber, numberOfItems);
/*  76 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Set<ExternalOrderLineAttribute> getExternalOrderLineAttributes() {
/*  82 */       return ((ExternalOrderLine)this.object).getExternalOrderLineAttributes();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setExternalOrderLineAttributes(Set<ExternalOrderLineAttribute> externalOrderLineAttributes) {
/*  90 */       Set<ExternalOrderLineAttribute> oldAtt = this.getExternalOrderLineAttributes();
/*  91 */       ((ExternalOrderLine)this.object).setExternalOrderLineAttributes(externalOrderLineAttributes);
/*  92 */       this.firePropertyChange("externalOrderLineAttributes", oldAtt, externalOrderLineAttributes);
/*     */ 
/*  94 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 103 */       presentationModel.getBufferedModel("articleName").addValueChangeListener(listener);
/*     */ 
/* 105 */       presentationModel.getBufferedModel("externalOrderLineAttributes").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 108 */       presentationModel.getBufferedModel("numberOfItems").addValueChangeListener(listener);
/*     */ 
/* 110 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ExternalOrderLineModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 119 */       ExternalOrderLineModel model = new ExternalOrderLineModel(new ExternalOrderLine());
/*     */ 
/* 121 */       model.setArticleName((String)presentationModel.getBufferedValue("articleName"));
/*     */ 
/* 123 */       model.setNumberOfItems((Integer)presentationModel.getBufferedValue("numberOfItems"));
/*     */ 
/* 125 */       model.setExternalOrderLineAttributes((Set)presentationModel.getBufferedValue("externalOrderLineAttributes"));
/*     */ 
/*     */ 
/* 128 */       return model;
/*     */    }
/*     */ }
