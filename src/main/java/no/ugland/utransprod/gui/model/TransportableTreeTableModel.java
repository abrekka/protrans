/**
 * 
 */
package no.ugland.utransprod.gui.model;

import java.util.HashSet;
import java.util.Set;

import no.ugland.utransprod.model.Colli;

import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class TransportableTreeTableModel extends DefaultTreeTableModel {
    Set<Object> splitFromOrder = new HashSet<Object>();

    
    public TransportableTreeTableModel(Transportable transportable) {
        super(new TransportableTreeNode(transportable, null));
    }

    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
        case 0:
            return "Artikkel";
        case 1:
            return "Ta ut";
        default:
            return "";
        }
    }

    @Override
    public Object getValueAt(Object aObject, int aColumn) {
        TransportableTreeNode treeNode = (TransportableTreeNode) aObject;
        Object obj = treeNode.getObject();
        Object parentObject = treeNode.getParentObject();

        switch (aColumn) {
        case 0:

            return obj.toString();
        case 1:
            if (splitFromOrder.contains(obj)||splitFromOrder.contains(parentObject)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;

        default:
            return "";
        }
    }

    @Override
    public void setValueAt(Object value, Object node, int column) {
        if (column == 1) {
            TransportableTreeNode treeNode = (TransportableTreeNode)node;
            Object object = treeNode.getObject();
            if ((Boolean) value) {
                splitFromOrder.add(object);
            } else {
                splitFromOrder.remove(object);
            }
        }
    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        if (column == 1) {
            TransportableTreeNode transportableTreeNode=(TransportableTreeNode)node;
            Object object =transportableTreeNode.getObject();
            Object parentObject = transportableTreeNode.getParentObject();
            if(parentObject instanceof Transportable){
            return true;
            }
        }
        return false;
    }

    @Override
    public Class getColumnClass(int column) {
        if (column == 1) {
            return Boolean.class;
        }
        return super.getColumnClass(column);
    }
    public Set<Object> getSplitFromOrder(){
        return splitFromOrder;
    }

}