/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import com.jgoodies.binding.adapter.BasicComponentFactory;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTextField;
/*    */ import no.ugland.utransprod.gui.Closeable;
/*    */ import no.ugland.utransprod.gui.IconEnum;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.gui.buttons.CancelButton;
/*    */ import no.ugland.utransprod.gui.buttons.CancelListener;
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
/*    */ 
/*    */ public class AttributeCriteriaViewHandler implements Closeable {
/*    */    public JCheckBox getCheckBoxYesNo(PresentationModel presentationModel) {
/* 32 */       return presentationModel != null ? BasicComponentFactory.createCheckBox(presentationModel.getModel("attributeValueBoolean"), "") : new JCheckBox();
/*    */    }
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
/*    */    public JTextField getTextFieldFrom(PresentationModel presentationModel) {
/* 49 */       return BasicComponentFactory.createTextField(presentationModel.getModel("attributeValueFrom"));
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public JTextField getTextFieldTo(PresentationModel presentationModel) {
/* 61 */       return BasicComponentFactory.createTextField(presentationModel.getModel("attributeValueTo"));
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public JButton getButtonOk(WindowInterface window) {
/* 73 */       return new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK, (CancelListener)null, true);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public JLabel getLabelAttribute(PresentationModel presentstionModel) {
/* 84 */       return BasicComponentFactory.createLabel(presentstionModel.getModel("attributeName"));
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public boolean canClose(String actionString, WindowInterface window) {
/* 93 */       return true;
/*    */    }
/*    */ }
