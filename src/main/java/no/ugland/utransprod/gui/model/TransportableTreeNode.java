package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public class TransportableTreeNode extends DefaultMutableTreeTableNode {
    private Object object;

    /**
     * Foreldrenode
     */
    private TransportableTreeNode parent;

    private List<TreeTableNode> children = new ArrayList<TreeTableNode>();

    private boolean leaf = false;

    @SuppressWarnings("unchecked")
    public TransportableTreeNode(Object aObject, TransportableTreeNode aParent) {
        object = aObject;
        parent = aParent;

        if (object instanceof Transportable) {
            if (((Transportable) object).getCollies() != null) {
                Set<Colli> collies = ((Transportable) object)
                        .getCollies();
                for (Colli colli : collies) {
                    children.add(new TransportableTreeNode(colli, this));

                }
            }
            if (((Transportable) object).getOrderLines() != null) {
                Set<OrderLine> orderLines = ((Transportable) object)
                        .getOrderLines();
                for (OrderLine orderLine : orderLines) {
                    if(orderLine.getColli()==null&&orderLine.hasTopLevelArticle()&&orderLine.belongTo((Transportable) object)){
                    children.add(new TransportableTreeNode(orderLine, this));
                    }

                }
            }

        }else if(object instanceof Colli){
            if (((Colli) object).getOrderLines() != null) {
                Set<OrderLine> orderLines = ((Colli) object)
                        .getOrderLines();
                for (OrderLine orderLine : orderLines) {
                    if(orderLine.hasTopLevelArticle()){
                    children.add(new TransportableTreeNode(orderLine, this));
                    }
                    

                }
            }
        }
        else{

            leaf = true;
        }
    }

    public Enumeration<MutableTreeTableNode> children() {
        return new IteratorEnumeration<MutableTreeTableNode>(children.iterator());
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public TreeTableNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    public int getChildCount() {
        return children.size();
    }

    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    public TreeTableNode getParent() {
        return parent;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public String toString() {
        if (object != null) {
            return object.toString();
        }
        return "";
    }

    public Object getObject() {
        return object;
    }

    public Object getParentObject(){
        if(parent!=null){
        return parent.getObject();
        }
        return null;
    }
}
