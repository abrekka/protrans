/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.swing.tree.TreeNode;
/*     */ import no.ugland.utransprod.model.Colli;
/*     */ import no.ugland.utransprod.model.OrderLine;
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
/*     */ public class TransportableTreeNode extends DefaultMutableTreeTableNode {
/*     */    private Object object;
/*     */    private TransportableTreeNode parent;
/*     */    private List<TreeTableNode> children = new ArrayList();
/*     */    private boolean leaf = false;
/*     */ 
/*     */    public TransportableTreeNode(Object aObject, TransportableTreeNode aParent) {
/*  31 */       this.object = aObject;
/*  32 */       this.parent = aParent;
/*     */       Set orderLines;      Iterator var4;      OrderLine orderLine;
/*  34 */       if (this.object instanceof Transportable) {
/*  35 */          if (((Transportable)this.object).getCollies() != null) {
/*  36 */             orderLines = ((Transportable)this.object).getCollies();
/*     */ 
/*  38 */             var4 = orderLines.iterator();            while(var4.hasNext()) {               Colli colli = (Colli)var4.next();
/*  39 */                this.children.add(new TransportableTreeNode(colli, this));
/*     */             }
/*     */          }
/*     */ 
/*  43 */          if (((Transportable)this.object).getOrderLines() != null) {
/*  44 */             orderLines = ((Transportable)this.object).getOrderLines();
/*     */ 
/*  46 */             var4 = orderLines.iterator();            while(var4.hasNext()) {               orderLine = (OrderLine)var4.next();
/*  47 */                if (orderLine.getColli() == null && orderLine.hasTopLevelArticle() && orderLine.belongTo((Transportable)this.object)) {
/*  48 */                   this.children.add(new TransportableTreeNode(orderLine, this));
/*     */ 
/*     */ 
/*     */                }
/*     */             }
/*     */          }
/*  54 */       } else if (this.object instanceof Colli) {
/*  55 */          if (((Colli)this.object).getOrderLines() != null) {
/*  56 */             orderLines = ((Colli)this.object).getOrderLines();
/*     */ 
/*  58 */             var4 = orderLines.iterator();            while(var4.hasNext()) {               orderLine = (OrderLine)var4.next();
/*  59 */                if (orderLine.hasTopLevelArticle()) {
/*  60 */                   this.children.add(new TransportableTreeNode(orderLine, this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                }
/*     */             }
/*     */          }
/*     */       } else {
/*  69 */          this.leaf = true;
/*     */       }
/*  71 */    }
/*     */ 
/*     */    public Enumeration<MutableTreeTableNode> children() {
/*  74 */       return new IteratorEnumeration(this.children.iterator());
/*     */    }
/*     */ 
/*     */    public boolean getAllowsChildren() {
/*  78 */       return true;
/*     */    }
/*     */ 
/*     */    public TreeTableNode getChildAt(int childIndex) {
/*  82 */       return (TreeTableNode)this.children.get(childIndex);
/*     */    }
/*     */ 
/*     */    public int getChildCount() {
/*  86 */       return this.children.size();
/*     */    }
/*     */ 
/*     */    public int getIndex(TreeNode node) {
/*  90 */       return this.children.indexOf(node);
/*     */    }
/*     */ 
/*     */    public TreeTableNode getParent() {
/*  94 */       return this.parent;
/*     */    }
/*     */ 
/*     */    public boolean isLeaf() {
/*  98 */       return this.leaf;
/*     */    }
/*     */ 
/*     */    public String toString() {
/* 102 */       return this.object != null ? this.object.toString() : "";
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getObject() {
/* 109 */       return this.object;
/*     */    }
/*     */ 
/*     */    public Object getParentObject() {
/* 113 */       return this.parent != null ? this.parent.getObject() : null;
/*     */    }
/*     */ }
