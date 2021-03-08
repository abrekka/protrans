/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.OrderLineAttribute;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
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
/*     */ public class OrderLineTreeTableModel<T, E> extends DefaultTreeTableModel {
/*     */    private OrderWrapper<T, E> orderWrapper;
/*     */ 
/*     */    public OrderLineTreeTableModel(OrderWrapper<T, E> aObject, boolean brukOrdrelinjelinjer) {
/*  28 */       super(new OrderLineTreeNode(aObject, (OrderLineTreeNode)null, brukOrdrelinjelinjer));
/*  29 */       this.orderWrapper = aObject;
/*     */ 
/*  31 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public OrderWrapper<T, E> getObject() {
/*  39 */       return this.orderWrapper;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public int getColumnCount() {
/*  47 */       return 3;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getColumnName(int column) {
/*  55 */       switch(column) {
/*     */       case 0:
/*  57 */          return "Artikkel";
/*     */       case 1:
/*  59 */          return "Verdi";
/*     */       case 2:
/*  61 */          return "Kolli";
/*     */       default:
/*  63 */          return "";
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getValueAt(Object aObject, int aColumn) {
/*  73 */       OrderLineTreeNode treeNode = (OrderLineTreeNode)aObject;
/*  74 */       Object obj = treeNode.getObject();
/*  75 */       if (obj != null) {
/*  76 */          switch(aColumn) {
/*     */ 
/*     */          case 0:
/*  79 */             return obj.toString();
/*     */          case 1:
/*  81 */             if (treeNode.isLeaf()) {
/*  82 */                if (obj instanceof OrderLineAttributeCriteria) {
/*  83 */                   return Util.nullToString(((OrderLineAttributeCriteria)obj).getAttributeValueFrom()) + "-" + Util.nullToString(((OrderLineAttributeCriteria)obj).getAttributeValueTo());
/*     */                }
/*     */ 
/*  86 */                return ((OrderLineAttribute)obj).getOrderLineAttributeValue();            } else {
/*  87 */                if (obj instanceof OrderLine) {
/*  88 */                   DecimalFormat decimalFormat = new DecimalFormat();
/*  89 */                   decimalFormat.setDecimalSeparatorAlwaysShown(false);
/*  90 */                   decimalFormat.setParseIntegerOnly(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */                   StringBuilder returnString = new StringBuilder();
/*  98 */                   if (((OrderLine)obj).getNumberOfItems() != null) {
/*  99 */                      returnString.append(decimalFormat.format(((OrderLine)obj).getNumberOfItems()));
/*     */ 
/* 101 */                      String metric = ((OrderLine)obj).getMetric();
/* 102 */                      if (metric != null) {
/* 103 */                         returnString.append(" ").append(metric);
/*     */                      }
/*     */                   }
/*     */ 
/* 107 */                   return returnString.toString();
/*     */                }
/* 109 */                return "";
/*     */             }         case 2:
/* 111 */             if (obj instanceof OrderLine) {
/* 112 */                return ((OrderLine)obj).getColli();
/*     */             }
/* 114 */             return "";
/*     */          default:
/* 116 */             return "";
/*     */          }
/*     */       } else {
/* 119 */          return "";
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setValueAt(Object value, Object node, int column) {
/* 129 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void fireChanged(boolean brukOrdrelinjelinjer) {
/* 135 */       this.setRoot(new OrderLineTreeNode(this.orderWrapper, (OrderLineTreeNode)null, brukOrdrelinjelinjer));
/* 136 */    }
/*     */ }
