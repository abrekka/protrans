package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * Klasse som brukes i trestruktur for å vise artikkeltyper for garasjetype
 * 
 * @author atle.brekka
 * 
 */
public class ConstructionTreeNode extends DefaultMutableTreeTableNode{
	/**
	 * Objekt som skal vises i noden
	 */
	private Object object;

	/**
	 * Foreldrenode
	 */
	private ConstructionTreeNode parent;

	/**
	 * Barn
	 */
	private List<TreeTableNode> children = new ArrayList<TreeTableNode>();

	/**
	 * Forteller om node er siste ledd i trepathen
	 */
	private boolean leaf = false;

	/**
	 * @param aObject
	 * @param aParent
	 */
	@SuppressWarnings("unchecked")
	public ConstructionTreeNode(Object aObject, ConstructionTreeNode aParent) {
		object = aObject;
		parent = aParent;

		if (object instanceof List) {
			List<ConstructionTypeArticle> articles = (List<ConstructionTypeArticle>) object;
			for (ConstructionTypeArticle article : articles) {
				children.add(new ConstructionTreeNode(article, null));
			}
		} else if (object instanceof ConstructionType) {
			if (((ConstructionType) object).getConstructionTypeArticles() != null) {
				Set<ConstructionTypeArticle> articles = ((ConstructionType) object)
						.getConstructionTypeArticles();
				for (ConstructionTypeArticle article : articles) {
					children.add(new ConstructionTreeNode(article, this));
				}
			}
			// dersom node skal innholde artikkeltype
		} else if (object instanceof ConstructionTypeArticle) {
			if (((ConstructionTypeArticle) object)
					.getConstructionTypeArticles() != null) {
				Set<ConstructionTypeArticle> articles = ((ConstructionTypeArticle) object)
						.getConstructionTypeArticles();
				for (ConstructionTypeArticle article : articles) {
					children.add(new ConstructionTreeNode(article, this));
				}
			}
			if (((ConstructionTypeArticle) object).getAttributes() != null) {
				Set<ConstructionTypeArticleAttribute> attributes = ((ConstructionTypeArticle) object)
						.getAttributes();
				for (ConstructionTypeArticleAttribute attribute : attributes) {
					children.add(new ConstructionTreeNode(attribute, this));
				}
			}
			// dersom objekt som skal vises i node er attributt for artikkeltype
		} else {

			leaf = true;
		}
	}

	/**
	 * Henter objekt i noden
	 * 
	 * @return objekt
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @see javax.swing.tree.TreeNode#children()
	 */
	public Enumeration<MutableTreeTableNode> children() {
		return new IteratorEnumeration<MutableTreeTableNode>(children.iterator());
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
	public int getIndex(TreeNode aNode) {
		return children.indexOf(aNode);
	}

	/**
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	public ConstructionTreeNode getParent() {
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
