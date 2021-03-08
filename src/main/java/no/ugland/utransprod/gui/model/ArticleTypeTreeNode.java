/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.ArticleType;
/*     */ import no.ugland.utransprod.model.ArticleTypeArticleType;
/*     */ import no.ugland.utransprod.model.ArticleTypeAttribute;
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
/*     */ public class ArticleTypeTreeNode extends DefaultMutableTreeTableNode {
/*     */    private Object object;
/*     */    private ArticleTypeTreeNode parent;
/*     */    private List<TreeTableNode> children = new ArrayList();
/*     */    private boolean leaf = false;
/*     */    private int counter = 0;
/*     */ 
/*     */    public ArticleTypeTreeNode(Object aObject, ArticleTypeTreeNode aParent, boolean fromList, int aCounter) {
/*  50 */       this.object = aObject;
/*  51 */       this.parent = aParent;
/*  52 */       this.counter = aCounter;
/*     */ 
/*     */ 
/*  55 */       if (this.object != null) {         Iterator var6;
/*  56 */          if (this.object instanceof List) {
/*  57 */             List<ArticleType> articles = (List)this.object;
/*  58 */             var6 = articles.iterator();            while(var6.hasNext()) {               ArticleType article = (ArticleType)var6.next();
/*  59 */                this.children.add(new ArticleTypeTreeNode(article, this, true, this.counter + 1));
/*     */             }
/*  61 */          } else if (this.object instanceof ArticleType) {            Set attributes;
/*  62 */             if (((ArticleType)this.object).getArticleTypeArticleTypes() != null) {
/*  63 */                attributes = ((ArticleType)this.object).getArticleTypeArticleTypes();
/*     */ 
/*  65 */                var6 = attributes.iterator();               while(var6.hasNext()) {                  ArticleTypeArticleType ref = (ArticleTypeArticleType)var6.next();
/*  66 */                   this.children.add(new ArticleTypeTreeNode(ref, this, false, this.counter + 1));
/*     */                }
/*     */             }
/*     */ 
/*  70 */             if (fromList && ((ArticleType)this.object).getArticleTypeAttributes() != null) {
/*     */ 
/*  72 */                attributes = ((ArticleType)this.object).getArticleTypeAttributes();
/*     */ 
/*  74 */                var6 = attributes.iterator();               while(var6.hasNext()) {                  ArticleTypeAttribute attribute = (ArticleTypeAttribute)var6.next();
/*  75 */                   this.children.add(new ArticleTypeTreeNode(attribute, this, false, this.counter + 1));
/*     */ 
/*     */ 
/*     */ 
/*     */                }
/*     */             }
/*  81 */          } else if (this.object instanceof ArticleTypeArticleType) {
/*  82 */             this.getAllChildren((ArticleTypeArticleType)this.object);
/*     */ 
/*     */          } else {
/*  85 */             this.leaf = true;
/*     */          }      }
/*     */ 
/*  88 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    private void getAllChildren(ArticleTypeArticleType articleType) {
/*     */       Set attributes;
/*     */       Iterator var3;
/*  95 */       if (articleType.getArticleTypeRef().getArticleTypeArticleTypes() != null) {
/*     */ 
/*  97 */          attributes = articleType.getArticleTypeRef().getArticleTypeArticleTypes();
/*  98 */          var3 = attributes.iterator();         while(var3.hasNext()) {            ArticleTypeArticleType articleTypeRef = (ArticleTypeArticleType)var3.next();
/*  99 */             this.children.add(new ArticleTypeTreeNode(articleTypeRef, this, false, this.counter + 1));
/*     */          }
/*     */       }
/*     */ 
/* 103 */       if (((ArticleTypeArticleType)this.object).getArticleTypeRef().getArticleTypeAttributes() != null) {
/*     */ 
/* 105 */          attributes = ((ArticleTypeArticleType)this.object).getArticleTypeRef().getArticleTypeAttributes();
/*     */          var3 = attributes.iterator();         while(var3.hasNext()) {
/* 107 */             ArticleTypeAttribute attribute = (ArticleTypeAttribute)var3.next();
/* 108 */             this.children.add(new ArticleTypeTreeNode(attribute, this, false, this.counter + 1));
/*     */          }      }
/*     */ 
/* 111 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getObject() {
/* 118 */       return this.object;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Enumeration<MutableTreeTableNode> children() {
/* 125 */       return new IteratorEnumeration(this.children.iterator());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public boolean getAllowsChildren() {
/* 133 */       return true;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public TreeTableNode getChildAt(int aIndex) {
/* 140 */       return (TreeTableNode)this.children.get(aIndex);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public int getChildCount() {
/* 147 */       return this.children.size();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public int getIndex(TreeTableNode aNode) {
/* 154 */       return this.children.indexOf(aNode);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArticleTypeTreeNode getParent() {
/* 161 */       return this.parent;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public boolean isLeaf() {
/* 168 */       return this.leaf;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String toString() {
/* 176 */       return this.object != null ? this.object.toString() : "";
/*     */    }
/*     */ }
