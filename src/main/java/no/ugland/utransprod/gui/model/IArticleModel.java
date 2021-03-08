/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import no.ugland.utransprod.model.IArticle;
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
/*     */ public class IArticleModel extends AbstractModel<IArticle, IArticleModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_NUMBER_OF_ITEMS_LONG = "numberOfItemsLong";
/*     */    public static final String PROPERTY_DIALOG_ORDER = "dialogOrder";
/*     */ 
/*     */    public IArticleModel(IArticle object) {
/*  36 */       super(object);
/*  37 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Long getNumberOfItemsLong() {
/*  43 */       return ((IArticle)this.object).getNumberOfItemsLong();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setNumberOfItemsLong(Long numberOfItems) {
/*  51 */       Long oldNumber = this.getNumberOfItemsLong();
/*  52 */       ((IArticle)this.object).setNumberOfItemsLong(numberOfItems);
/*  53 */       this.firePropertyChange("numberOfItemsLong", oldNumber, numberOfItems);
/*     */ 
/*     */ 
/*  56 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getDialogOrder() {
/*  62 */       return ((IArticle)this.object).getDialogOrder();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDialogOrder(Integer dialogOrder) {
/*  70 */       Integer oldNumber = this.getDialogOrder();
/*  71 */       ((IArticle)this.object).setDialogOrder(dialogOrder);
/*  72 */       this.firePropertyChange("dialogOrder", oldNumber, dialogOrder);
/*     */ 
/*  74 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/*  83 */       presentationModel.getBufferedModel("numberOfItemsLong").addValueChangeListener(listener);
/*     */ 
/*  85 */       presentationModel.getBufferedModel("dialogOrder").addValueChangeListener(listener);
/*     */ 
/*     */ 
/*  88 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public IArticleModel getBufferedObjectModel(PresentationModel presentationModel) {
/*  96 */       IArticleModel model = new IArticleModel(((IArticle)this.object).getNewInstance());
/*  97 */       model.setNumberOfItemsLong((Long)presentationModel.getBufferedValue("numberOfItemsLong"));
/*     */ 
/*  99 */       model.setDialogOrder((Integer)presentationModel.getBufferedValue("dialogOrder"));
/*     */ 
/* 101 */       return model;
/*     */    }
/*     */ }
