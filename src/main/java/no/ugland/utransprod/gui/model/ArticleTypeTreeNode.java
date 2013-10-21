package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ArticleTypeArticleType;
import no.ugland.utransprod.model.ArticleTypeAttribute;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * Klasse som brukes i trestruktur for å vise artikkeltyper for garasjetype
 * @author atle.brekka
 */
public class ArticleTypeTreeNode extends DefaultMutableTreeTableNode {
    /**
     * Objekt som skal vises i noden
     */
    private Object object;

    /**
     * Foreldrenode
     */
    private ArticleTypeTreeNode parent;

    /**
     * Barn
     */
    private List<TreeTableNode> children = new ArrayList<TreeTableNode>();

    /**
     * Forteller om node er siste ledd i trepathen
     */
    private boolean leaf = false;
    private int counter=0;

    /**
     * @param aObject
     * @param aParent
     * @param fromList
     */
    @SuppressWarnings("unchecked")
    public ArticleTypeTreeNode(Object aObject, ArticleTypeTreeNode aParent,
            boolean fromList,int aCounter) {
        object = aObject;
        parent = aParent;
        counter = aCounter;

        // dersom noden skal inneholde artikkel (rot)
        if (object != null) {
            if (object instanceof List) {
                List<ArticleType> articles = (List<ArticleType>) object;
                for (ArticleType article : articles) {
                    children.add(new ArticleTypeTreeNode(article, this, true,counter+1));
                }
            } else if (object instanceof ArticleType) {
                if (((ArticleType) object).getArticleTypeArticleTypes() != null) {
                    Set<ArticleTypeArticleType> refs = ((ArticleType) object)
                            .getArticleTypeArticleTypes();
                    for (ArticleTypeArticleType ref : refs) {
                        children.add(new ArticleTypeTreeNode(ref, this, false,counter+1));
                    }
                }

                if (fromList) {
                    if (((ArticleType) object).getArticleTypeAttributes() != null) {
                        Set<ArticleTypeAttribute> attributes = ((ArticleType) object)
                                .getArticleTypeAttributes();
                        for (ArticleTypeAttribute attribute : attributes) {
                            children.add(new ArticleTypeTreeNode(attribute,
                                    this, false,counter+1));
                        }
                    }
                }
                // dersom node skal innholde artikkeltype
            } else if (object instanceof ArticleTypeArticleType) {
                getAllChildren((ArticleTypeArticleType) object);
            } else {

                leaf = true;
            }
        }
    }

    /**
     * Setter alle barn av en artikkel
     * @param articleType
     */
    private void getAllChildren(ArticleTypeArticleType articleType) {
        if (articleType.getArticleTypeRef().getArticleTypeArticleTypes() != null) {
            Set<ArticleTypeArticleType> articles = articleType
                    .getArticleTypeRef().getArticleTypeArticleTypes();
            for (ArticleTypeArticleType articleTypeRef : articles) {
                children.add(new ArticleTypeTreeNode(articleTypeRef, this,
                        false,counter+1));
            }
        }
        if (((ArticleTypeArticleType) object).getArticleTypeRef()
                .getArticleTypeAttributes() != null) {
            Set<ArticleTypeAttribute> attributes = ((ArticleTypeArticleType) object)
                    .getArticleTypeRef().getArticleTypeAttributes();
            for (ArticleTypeAttribute attribute : attributes) {
                children.add(new ArticleTypeTreeNode(attribute, this, false,counter+1));
            }
        }
    }

    /**
     * Henter objekt i noden
     * @return objekt
     */
    public Object getObject() {
        return object;
    }

    /**
     * @see javax.swing.tree.TreeNode#children()
     */
    public Enumeration<MutableTreeTableNode> children() {
        return new IteratorEnumeration<MutableTreeTableNode>(children
                .iterator());
    }

    /**
     * @see javax.swing.tree.TreeNode#getAllowsChildren()
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * @see javax.swing.tree.TreeNode#getChildAt(int)
     */
    public TreeTableNode getChildAt(int aIndex) {
        return children.get(aIndex);
    }

    /**
     * @see javax.swing.tree.TreeNode#getChildCount()
     */
    public int getChildCount() {
        return children.size();
    }

    /**
     * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
     */
    public int getIndex(TreeTableNode aNode) {
        return children.indexOf(aNode);
    }

    /**
     * @see javax.swing.tree.TreeNode#getParent()
     */
    public ArticleTypeTreeNode getParent() {
        return parent;
    }

    /**
     * @see javax.swing.tree.TreeNode#isLeaf()
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (object != null) {
            return object.toString();
        }
        return "";
    }

}
