/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import java.math.BigDecimal;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.gui.Login;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.model.FaktureringV;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.service.FaktureringVManager;
/*    */ import no.ugland.utransprod.service.OrderManager;
/*    */ import no.ugland.utransprod.service.VismaFileCreator;
/*    */ import no.ugland.utransprod.util.ModelUtil;
/*    */ import no.ugland.utransprod.util.UserUtil;
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
/*    */ public class InvoiceApplyList extends AbstractApplyList<FaktureringV> {
/*    */    @Inject
/*    */    public InvoiceApplyList(Login login, FaktureringVManager manager) {
/* 34 */       super(login, manager, (VismaFileCreator)null);
/* 35 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setApplied(FaktureringV object, boolean applied, WindowInterface window) {
/* 45 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/* 46 */       Order order = orderManager.findByOrderNr(object.getOrderNr());
/* 47 */       if (applied) {
/* 48 */          Date invoiceDate = Util.getDate(window);
/* 49 */          order.setInvoiceDate(invoiceDate);
/*    */       } else {
/* 51 */          order.setInvoiceDate((Date)null);
/*    */       }
/*    */       try {
/* 54 */          orderManager.saveOrder(order);
/* 55 */       } catch (ProTransException var7) {
/* 56 */          Util.showErrorDialog(window, "Feil", var7.getMessage());
/* 57 */          var7.printStackTrace();
/*    */       }
/* 59 */       this.applyListManager.refresh(object);
/*    */ 
/* 61 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public boolean hasWriteAccess() {
/* 67 */       return UserUtil.hasWriteAccess(this.login.getUserType(), "Fakturering");
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public FaktureringV getApplyObject(Transportable transportable, WindowInterface window) {
/* 74 */       List<FaktureringV> list = this.applyListManager.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
/* 75 */       return list != null && list.size() == 1 ? (FaktureringV)list.get(0) : null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setStarted(FaktureringV object, boolean started) {
/* 82 */    }
public void setPause(FaktureringV object, boolean started) {
/* 82 */    }
/*    */ 
/*    */ 
/*    */    public boolean shouldGenerateVismaFile() {
/* 86 */       return false;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setRealProductionHours(FaktureringV object, BigDecimal overstyrtTidsforbruk) {
/* 92 */    }
/*    */ }
