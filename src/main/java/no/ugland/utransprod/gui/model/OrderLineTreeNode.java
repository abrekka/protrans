package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * Node i tretabell for ordrelinjer
 * 
 * @author atle.brekka
 * 
 */
public class OrderLineTreeNode extends DefaultMutableTreeTableNode {
	/**
	 * Objekt som skal vises i noden
	 */
	private Object object;

	/**
	 * Foreldrenode
	 */
	private OrderLineTreeNode parent;

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
	public OrderLineTreeNode(Object aObject, OrderLineTreeNode aParent, boolean brukOrdrelinjelinjer) {

		object = aObject;
		parent = aParent;

		if (object instanceof List) {
			List<OrderLine> orderLines = (List<OrderLine>) object;
			for (OrderLine orderLine : orderLines) {
				children.add(new OrderLineTreeNode(orderLine, this, brukOrdrelinjelinjer));
			}
		} else if (object instanceof OrderWrapper) {
			if (((OrderWrapper) object).getOrderLines() != null) {
				List<OrderLine> orderLines = ((OrderWrapper) object).getOrderLines();
				for (OrderLine orderLine : orderLines) {
					if (orderLine.getOrderLineRef() == null) {
						children.add(new OrderLineTreeNode(orderLine, this, brukOrdrelinjelinjer));
					}
				}
			}

		} else if (object instanceof OrderLine) {
			if (brukOrdrelinjelinjer) {
				if (((OrderLine) object).getOrderLines() != null) {
					Set<OrderLine> orderLines = ((OrderLine) object).getOrderLines();
					for (OrderLine orderLine : orderLines) {
						children.add(new OrderLineTreeNode(orderLine, this, brukOrdrelinjelinjer));
					}
				}

				if (((OrderLine) object).getOrderLineAttributes() != null) {
					Set<OrderLineAttribute> orderLineAttributes = ((OrderLine) object).getOrderLineAttributes();
					for (OrderLineAttribute orderLineAttribute : orderLineAttributes) {
						children.add(new OrderLineTreeNode(orderLineAttribute, this, brukOrdrelinjelinjer));
					}
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
	public OrderLineTreeNode getParent() {
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
