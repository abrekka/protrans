/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.list.ArrayListModel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import no.ugland.utransprod.model.OrderLine;
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
/*    */ public class OrderWrapper<T, E> {
/*    */    private List<OrderLine> orderLines;
/*    */ 
/*    */    public OrderWrapper(ICostableModel<T, E> costableModel) {
/* 27 */       this.init(costableModel);
/* 28 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    private void init(ICostableModel<T, E> costableModel) {
/* 36 */       ArrayListModel lines = costableModel.getOrderLineArrayListModel();
/* 37 */       if (lines != null) {
/* 38 */          this.orderLines = new ArrayList(lines);
/*    */       } else {
/* 40 */          this.orderLines = new ArrayList();
/*    */       }
/* 42 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public List<OrderLine> getOrderLines() {
/* 48 */       return this.orderLines;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void removeOrderLine(OrderLine orderLine) {
/* 56 */       Set<OrderLine> refOrders = orderLine.getOrderLines();
/* 57 */       Iterator var3 = refOrders.iterator();      while(var3.hasNext()) {         OrderLine line = (OrderLine)var3.next();
/* 58 */          this.removeOrderLine(line);
/*    */       }
/* 60 */       this.orderLines.remove(orderLine);
/* 61 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setOrderLines(List<OrderLine> orders) {
/* 68 */       this.orderLines = orders;
/* 69 */    }
/*    */ }
