/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.model.Deviation;
/*    */ import no.ugland.utransprod.model.Order;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DeviationAction<T> extends AbstractAction {
/*    */    private static final long serialVersionUID = 1L;
/*    */    private WindowInterface window;
/*    */    private DeviationViewHandler2 deviationViewHandler;
/*    */ 
/*    */    @Inject
/*    */    public DeviationAction(DeviationViewHandlerFactory deviationViewHandlerFactory) {
/* 56 */       super("Reg. avvik...");
/* 57 */       this.deviationViewHandler = deviationViewHandlerFactory.create((Order)null, true, false, true, (Deviation)null, true, true);
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
/* 73 */    }
/*    */ 
/*    */    public void setWindowInterface(WindowInterface aWindow) {
/* 76 */       this.window = aWindow;
/* 77 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void actionPerformed(ActionEvent arg0) {
/* 87 */       this.deviationViewHandler.openEditView(new Deviation(), false, this.window, false);
/* 88 */    }
/*    */ }
