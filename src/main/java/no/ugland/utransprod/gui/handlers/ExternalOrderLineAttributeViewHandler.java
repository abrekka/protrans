/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.adapter.BasicComponentFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JTextField;
/*     */ import no.ugland.utransprod.gui.IconEnum;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.gui.buttons.CancelButton;
/*     */ import no.ugland.utransprod.gui.buttons.CancelListener;
/*     */ import no.ugland.utransprod.gui.edit.AbstractEditView;
/*     */ import no.ugland.utransprod.gui.model.ExternalOrderLineAttributeModel;
/*     */ import no.ugland.utransprod.model.ExternalOrderLineAttribute;
/*     */ import no.ugland.utransprod.model.UserType;
/*     */ import no.ugland.utransprod.service.OverviewManager;
/*     */ import no.ugland.utransprod.util.UserUtil;
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
/*     */ public class ExternalOrderLineAttributeViewHandler extends AbstractViewHandlerShort<ExternalOrderLineAttribute, ExternalOrderLineAttributeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */ 
/*     */    public ExternalOrderLineAttributeViewHandler(UserType userType) {
/*  38 */       super("Attributter", (OverviewManager)null, false, userType, true);
/*     */ 
/*  40 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JTextField getTextFieldName(PresentationModel presentationModel) {
/*  49 */       return BasicComponentFactory.createTextField(presentationModel.getModel("externalOrderLineAttributeName"));
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
/*     */    public JTextField getTextFieldValue(PresentationModel presentationModel) {
/*  61 */       return BasicComponentFactory.createTextField(presentationModel.getModel("externalOrderLineAttributeValue"));
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
/*     */    public JButton getButtonCancel(WindowInterface window) {
/*  73 */       return new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK, (CancelListener)null, true);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean hasWriteAccess() {
/*  82 */       return UserUtil.hasWriteAccess(this.userType, "Bestillinger");
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
/*     */ 
/*     */ 
/*     */    protected AbstractEditView<ExternalOrderLineAttributeModel, ExternalOrderLineAttribute> getEditView(AbstractViewHandler<ExternalOrderLineAttribute, ExternalOrderLineAttributeModel> handler, ExternalOrderLineAttribute object, boolean searching) {
/*  97 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public CheckObject checkDeleteObject(ExternalOrderLineAttribute object) {
/* 102 */       return null;
/*     */    }
/*     */ }
