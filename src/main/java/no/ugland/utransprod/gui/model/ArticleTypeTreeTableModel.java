package no.ugland.utransprod.gui.model;

import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreePath;

import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 * Modellklasse for tretabell som skal vise artikkeltyper for grasjetype
 * @author atle.brekka
 */
public class ArticleTypeTreeTableModel extends DefaultTreeTableModel {
    /**
     * Objekt som skal vises (artikkel)
     */
    private Object object;

    /**
     * 
     */
    private boolean withCriterias = false;

    /**
     * @param aObject
     */
    public ArticleTypeTreeTableModel(Object aObject) {
        this(aObject, false);
    }

    /**
     * @param aObject
     * @param criteria
     */
    public ArticleTypeTreeTableModel(Object aObject, boolean criteria) {
        super(new ArticleTypeTreeNode(aObject, null, false,0));
        object = aObject;
        withCriterias = criteria;
    }

    /**
     * Henter objekt som vises (garasjetype)
     * @return objekt
     */
    public Object getObject() {
        return object;
    }

    /**
     * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        if (withCriterias) {
            return 2;
        }
        return 1;
    }

    /**
     * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        if (withCriterias) {
            return getColumnNameCriteria(column);
        }
        return getColumnNameNoCriteria(column);
    }

    /**
     * @param column
     * @return kolonnenavn
     */
    public String getColumnNameCriteria(int column) {
        switch (column) {
        case 0:
            return "Artikkel";
        case 1:
            return "Verdi";
        default:
            return "";
        }
    }

    /**
     * @param column
     * @return kolonnenavn
     */
    public String getColumnNameNoCriteria(int column) {
        switch (column) {
        case 0:
            return "Artikkel";
        default:
            return "";
        }
    }

    /**
     * @see org.jdesktop.swingx.treetable.DefaultTreeTableModel#getValueAt(java.lang.Object,
     *      int)
     */
    @Override
    public Object getValueAt(Object aObject, int aColumn) {
        if (withCriterias) {
            return getValueAtCriteria(aObject, aColumn);
        }
        //return getValueAtNoCriteria(aObject, aColumn);
        return aObject;
    }

    /**
     * @param aObject
     * @param aColumn
     * @return verdi
     */
    public Object getValueAtNoCriteria(Object aObject, int aColumn) {
        return "";
    }

    /**
     * @param aObject
     * @param aColumn
     * @return verdi
     */
    public Object getValueAtCriteria(Object aObject, int aColumn) {
        ArticleTypeTreeNode treeNode = (ArticleTypeTreeNode) aObject;
        Object obj = treeNode.getObject();

        switch (aColumn) {
        case 0:

            return obj.toString();
        case 1:
            if (treeNode.isLeaf()) {
                if (obj instanceof ArticleTypeAttributeCriteria) {
                    return Util
                            .nullToString(((ArticleTypeAttributeCriteria) obj)
                                    .getAttributeValueFrom())
                            + "-"
                            + Util
                                    .nullToString(((ArticleTypeAttributeCriteria) obj)
                                            .getAttributeValueTo());
                }
                return "";
            }
            return "";

        default:
            return "";
        }

    }

    /**
     * @see org.jdesktop.swingx.treetable.DefaultTreeTableModel#setValueAt(java.lang.Object,
     *      java.lang.Object, int)
     */
    @Override
    public void setValueAt(Object arg0, Object arg1, int arg2) {
    }

    /**
     * Gir beskjed om at tre har endret seg
     */
    public void fireChanged() {
        setRoot(new ArticleTypeTreeNode(object, null, false,0));
    }

}
