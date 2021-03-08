/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import com.jgoodies.binding.adapter.BasicComponentFactory;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JTextArea;
/*    */ import no.ugland.utransprod.gui.Closeable;
/*    */ import no.ugland.utransprod.gui.IconEnum;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.gui.buttons.CancelButton;
/*    */ import no.ugland.utransprod.gui.buttons.CancelListener;
/*    */ import no.ugland.utransprod.gui.model.FileModel;
/*    */ 
/*    */ public class FileViewHandler implements Closeable {
/*    */    private PresentationModel presentationModel;
/*    */ 
/*    */    public FileViewHandler(String fileContent) {
/* 18 */       this.presentationModel = new PresentationModel(new FileModel(fileContent));
/* 19 */    }
/*    */ 
/*    */    public JTextArea getTextAreaFile() {
/* 22 */       JTextArea textArea = BasicComponentFactory.createTextArea(this.presentationModel.getModel("fileContent"));
/* 23 */       textArea.setName("TextAreaFile");
/* 24 */       textArea.setEnabled(false);
/* 25 */       return textArea;
/*    */    }
/*    */ 
/*    */    public JButton getButtonCancel(WindowInterface window) {
/* 29 */       JButton button = new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK, (CancelListener)null, true);
/* 30 */       button.setName("ButtonOk");
/* 31 */       return button;
/*    */    }
/*    */ 
/*    */    public boolean canClose(String actionString, WindowInterface window) {
/* 35 */       return true;
/*    */    }
/*    */ }
