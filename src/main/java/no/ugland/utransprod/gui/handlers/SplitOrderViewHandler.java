/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.swing.JButton;
/*    */ import no.ugland.utransprod.gui.Closeable;
/*    */ import no.ugland.utransprod.gui.IconEnum;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.gui.buttons.CancelButton;
/*    */ import no.ugland.utransprod.gui.buttons.CancelListener;
/*    */ import no.ugland.utransprod.gui.model.Transportable;
/*    */ import no.ugland.utransprod.gui.model.TransportableTreeTableModel;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.PostShipment;
/*    */ import no.ugland.utransprod.service.OrderManager;
/*    */ import no.ugland.utransprod.service.PostShipmentManager;
/*    */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
/*    */ import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
/*    */ import no.ugland.utransprod.util.ModelUtil;
/*    */ import org.jdesktop.swingx.JXTreeTable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SplitOrderViewHandler implements Closeable {
/*    */    private Transportable transportable;
/*    */    private boolean canceled = false;
/*    */    private TransportableTreeTableModel transportableTreeTableModel;
/*    */ 
/*    */    public SplitOrderViewHandler(Transportable aTransportable) {
/* 32 */       this.transportable = aTransportable;
/* 33 */       this.lazyLoadTransportable(this.transportable);
/* 34 */    }
/*    */ 
/*    */    public JXTreeTable getTreeTableTransportable() {
/* 37 */       this.transportableTreeTableModel = new TransportableTreeTableModel(this.transportable);
/* 38 */       JXTreeTable treeTable = new JXTreeTable(this.transportableTreeTableModel);
/* 39 */       treeTable.setName("TreeTableTransportable");
/* 40 */       treeTable.setAutoResizeMode(0);
/* 41 */       treeTable.getColumnExt(0).setPreferredWidth(200);
/* 42 */       treeTable.getColumnExt(1).setPreferredWidth(50);
/* 43 */       return treeTable;
/*    */    }
/*    */    public JButton getButtonOk(WindowInterface window) {
/* 46 */       JButton button = new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK, (CancelListener)null, true);
/* 47 */       button.setName("ButtonOk");
/* 48 */       return button;
/*    */    }
/*    */    public JButton getButtonCancel(WindowInterface window) {
/* 51 */       JButton button = new CancelButton(window, this, true);
/* 52 */       button.setName("ButtonCancel");
/* 53 */       return button;
/*    */    }
/*    */ 
/*    */    private void lazyLoadTransportable(Transportable transportable) {
/* 57 */       if (transportable != null) {
/* 58 */          if (transportable instanceof Order) {
/* 59 */             OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*    */ 
/* 61 */             orderManager.lazyLoadOrder((Order)transportable, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.COLLIES});
/*    */ 
/*    */ 
/*    */ 
/*    */          } else {
/* 66 */             PostShipmentManager postShipmentManager = (PostShipmentManager)ModelUtil.getBean("postShipmentManager");
/*    */ 
/* 68 */             postShipmentManager.lazyLoad((PostShipment)transportable, new LazyLoadPostShipmentEnum[]{LazyLoadPostShipmentEnum.ORDER_LINES, LazyLoadPostShipmentEnum.COLLIES});
/*    */ 
/*    */ 
/*    */          }
/*    */       }
/*    */ 
/* 74 */    }
/*    */ 
/*    */    public boolean canClose(String actionString, WindowInterface window) {
/* 77 */       if (!actionString.equalsIgnoreCase("Ok")) {
/* 78 */          this.canceled = true;
/*    */       }
/* 80 */       return true;
/*    */    }
/*    */ 
/*    */    public List<Object> getSplitted() {
/* 84 */       return new ArrayList(this.transportableTreeTableModel.getSplitFromOrder());
/*    */    }
/*    */ 
/*    */    public boolean isCanceled() {
/* 88 */       return this.canceled;
/*    */    }
/*    */ }
