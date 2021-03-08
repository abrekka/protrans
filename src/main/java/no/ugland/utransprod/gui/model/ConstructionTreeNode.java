/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.swing.tree.TreeNode;
/*     */ import no.ugland.utransprod.model.ConstructionType;
/*     */ import no.ugland.utransprod.model.ConstructionTypeArticle;
/*     */ import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
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
/*     */ public class ConstructionTreeNode extends DefaultMutableTreeTableNode {
/*     */    private Object object;
/*     */    private ConstructionTreeNode parent;
/*     */    private List<TreeTableNode> children = new ArrayList();
/*     */    private boolean leaf = false;
/*     */ 
/*     */    public ConstructionTreeNode(Object aObject, ConstructionTreeNode aParent) {
/*  51 */       this.object = aObject;
/*  52 */       this.parent = aParent;
/*     */       Iterator var4;      ConstructionTypeArticle article;
/*  54 */       if (this.object instanceof List) {
/*  55 */          List<ConstructionTypeArticle> articles = (List)this.object;
/*  56 */          var4 = articles.iterator();         while(var4.hasNext()) {            article = (ConstructionTypeArticle)var4.next();
/*  57 */             this.children.add(new ConstructionTreeNode(article, (ConstructionTreeNode)null));
/*     */          }      } else {         Set attributes;
/*  59 */          if (this.object instanceof ConstructionType) {
/*  60 */             if (((ConstructionType)this.object).getConstructionTypeArticles() != null) {
/*  61 */                attributes = ((ConstructionType)this.object).getConstructionTypeArticles();
/*     */ 
/*  63 */                var4 = attributes.iterator();               while(var4.hasNext()) {                  article = (ConstructionTypeArticle)var4.next();
/*  64 */                   this.children.add(new ConstructionTreeNode(article, this));
/*     */ 
/*     */                }
/*     */             }
/*  68 */          } else if (this.object instanceof ConstructionTypeArticle) {
/*  69 */             if (((ConstructionTypeArticle)this.object).getConstructionTypeArticles() != null) {
/*     */ 
/*  71 */                attributes = ((ConstructionTypeArticle)this.object).getConstructionTypeArticles();
/*     */                var4 = attributes.iterator();               while(var4.hasNext()) {
/*  73 */                   article = (ConstructionTypeArticle)var4.next();
/*  74 */                   this.children.add(new ConstructionTreeNode(article, this));
/*     */                }            }
/*     */ 
/*  77 */             if (((ConstructionTypeArticle)this.object).getAttributes() != null) {
/*  78 */                attributes = ((ConstructionTypeArticle)this.object).getAttributes();
/*     */ 
/*  80 */                var4 = attributes.iterator();               while(var4.hasNext()) {                  ConstructionTypeArticleAttribute attribute = (ConstructionTypeArticleAttribute)var4.next();
/*  81 */                   this.children.add(new ConstructionTreeNode(attribute, this));
/*     */ 
/*     */ 
/*     */                }
/*     */             }
/*     */          } else {
/*  87 */             this.leaf = true;
/*     */          }      }
/*  89 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getObject() {
/*  97 */       return this.object;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Enumeration<MutableTreeTableNode> children() {
/* 104 */       return new IteratorEnumeration(this.children.iterator());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public boolean getAllowsChildren() {
/* 111 */       return true;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public TreeTableNode getChildAt(int aIndex) {
/* 118 */       return (TreeTableNode)this.children.get(aIndex);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public int getChildCount() {
/* 125 */       return this.children.size();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public int getIndex(TreeNode aNode) {
/* 132 */       return this.children.indexOf(aNode);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ConstructionTreeNode getParent() {
/* 139 */       return this.parent;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public boolean isLeaf() {
/* 146 */       return this.leaf;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String toString() {
/* 154 */       return this.object != null ? this.object.toString() : "";
/*     */    }
/*     */ }
