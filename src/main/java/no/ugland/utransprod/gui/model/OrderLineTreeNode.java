/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.swing.tree.TreeNode;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.OrderLineAttribute;
/*     */ import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
/*     */ import org.jdesktop.swingx.treetable.MutableTreeTableNode;
/*     */ import org.jdesktop.swingx.treetable.TreeTableNode;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrderLineTreeNode extends DefaultMutableTreeTableNode {
/*     */    private Object object;
/*     */    private OrderLineTreeNode parent;
/*     */    private List<TreeTableNode> children = new ArrayList();
/*     */    private boolean leaf = false;
/*     */ 
/*     */    public OrderLineTreeNode(Object aObject, OrderLineTreeNode aParent, boolean brukOrdrelinjelinjer) {
/*  51 */       this.object = aObject;
/*  52 */       this.parent = aParent;
/*     */       List orderLines;      Iterator var5;      OrderLine orderLine;
/*  54 */       if (this.object instanceof List) {
/*  55 */          orderLines = (List)this.object;
/*  56 */          var5 = orderLines.iterator();         while(var5.hasNext()) {            orderLine = (OrderLine)var5.next();
/*  57 */             this.children.add(new OrderLineTreeNode(orderLine, this, brukOrdrelinjelinjer));
/*     */          }
/*  59 */       } else if (this.object instanceof OrderWrapper) {
/*  60 */          if (((OrderWrapper)this.object).getOrderLines() != null) {
/*  61 */             orderLines = ((OrderWrapper)this.object).getOrderLines();
/*  62 */             var5 = orderLines.iterator();            while(var5.hasNext()) {               orderLine = (OrderLine)var5.next();
/*  63 */                if (orderLine.getOrderLineRef() == null) {
/*  64 */                   this.children.add(new OrderLineTreeNode(orderLine, this, brukOrdrelinjelinjer));
/*     */ 
/*     */                }
/*     */             }
/*     */          }
/*  69 */       } else if (this.object instanceof OrderLine) {
/*  70 */          if (brukOrdrelinjelinjer) {            Set orderLineAttributes;
/*  71 */             if (((OrderLine)this.object).getOrderLines() != null) {
/*  72 */                orderLineAttributes = ((OrderLine)this.object).getOrderLines();               var5 = orderLineAttributes.iterator();
/*  73 */                while(var5.hasNext()) {                  orderLine = (OrderLine)var5.next();
/*  74 */                   this.children.add(new OrderLineTreeNode(orderLine, this, brukOrdrelinjelinjer));
/*     */                }
/*     */             }
/*     */ 
/*  78 */             if (((OrderLine)this.object).getOrderLineAttributes() != null) {
/*  79 */                orderLineAttributes = ((OrderLine)this.object).getOrderLineAttributes();
/*  80 */                var5 = orderLineAttributes.iterator();               while(var5.hasNext()) {                  OrderLineAttribute orderLineAttribute = (OrderLineAttribute)var5.next();
/*  81 */                   this.children.add(new OrderLineTreeNode(orderLineAttribute, this, brukOrdrelinjelinjer));
/*     */ 
/*     */ 
/*     */                }
/*     */             }
/*     */          }
/*     */       } else {
/*  88 */          this.leaf = true;
/*     */       }
/*  90 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getObject() {
/*  98 */       return this.object;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Enumeration<MutableTreeTableNode> children() {
/* 105 */       return new IteratorEnumeration(this.children.iterator());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public boolean getAllowsChildren() {
/* 112 */       return true;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public TreeTableNode getChildAt(int aIndex) {
/* 119 */       return (TreeTableNode)this.children.get(aIndex);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public int getChildCount() {
/* 126 */       return this.children.size();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public int getIndex(TreeNode aNode) {
/* 133 */       return this.children.indexOf(aNode);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public OrderLineTreeNode getParent() {
/* 140 */       return this.parent;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public boolean isLeaf() {
/* 147 */       return this.leaf;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String toString() {
/* 155 */       return this.object != null ? this.object.toString() : "";
/*     */    }
/*     */ }
