/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.jgoodies.validation.ValidationResultModel;
/*    */ import com.jgoodies.validation.util.DefaultValidationResultModel;
/*    */ import javax.swing.JButton;
/*    */ import no.ugland.utransprod.gui.Closeable;
/*    */ import no.ugland.utransprod.gui.IconEnum;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.gui.buttons.CancelButton;
/*    */ import no.ugland.utransprod.gui.buttons.CancelListener;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstructionArticleAttributeViewHandler implements Closeable {
/*    */    private ValidationResultModel validationResultModel = new DefaultValidationResultModel();
/*    */ 
/*    */    public JButton getButtonOk(WindowInterface aWindow, ValidationResultModel aValidationResultModel) {
/* 31 */       JButton button = new CancelButton(aWindow, this, true, "Ok", IconEnum.ICON_OK, (CancelListener)null, true);
/* 32 */       button.setName("ButtonOk");
/*    */ 
/* 34 */       this.validationResultModel = aValidationResultModel != null ? aValidationResultModel : this.validationResultModel;
/*    */ 
/* 36 */       return button;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public boolean canClose(String actionString, WindowInterface window) {
/* 44 */       if (this.validationResultModel.hasErrors()) {
/* 45 */          Util.showErrorDialog(window, "Rett feil", "Alle feil må rettes først");
/* 46 */          return false;
/*    */       } else {
/* 48 */          return true;
/*    */       }
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public ValidationResultModel getValidationResultModel() {
/* 56 */       return this.validationResultModel;
/*    */    }
/*    */ }
