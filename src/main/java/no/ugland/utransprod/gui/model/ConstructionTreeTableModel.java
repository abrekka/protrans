package no.ugland.utransprod.gui.model;

import java.text.DecimalFormat;

import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 * Modellklasse for tretabell som skal vise artikkeltyper for grasjetype
 * 
 * @author atle.brekka
 * 
 */
public class ConstructionTreeTableModel extends DefaultTreeTableModel {
	/**
	 * Objekt som skal vises (garasjetype)
	 */
	private Object object;

	/**
	 * 
	 */
	private boolean withCriteria = false;

	/**
	 * @param aObject
	 */
	public ConstructionTreeTableModel(Object aObject) {
		this(aObject, false);
	}

	/**
	 * @param aObject
	 * @param criteria
	 */
	public ConstructionTreeTableModel(Object aObject, boolean criteria) {
		super(new ConstructionTreeNode(aObject, null));
		object = aObject;
		withCriteria = criteria;
	}

	/**
	 * Henter objekt som vises (garasjetype)
	 * 
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
		if (withCriteria) {
			return 2;
		}
		return 3;
	}

	/**
	 * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		if (withCriteria) {
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
		case 1:
			return "Verdi";
		case 2:
			return "Rekkefølge";
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
		ConstructionTreeNode treeNode = (ConstructionTreeNode) aObject;
		Object obj = treeNode.getObject();

		switch (aColumn) {
		case 0:

			return obj.toString();
		case 1:
			if (treeNode.isLeaf()) {
				if (obj instanceof ConstructionTypeArticleAttributeCriteria) {
					return Util
							.nullToString(((ConstructionTypeArticleAttributeCriteria) obj)
									.getAttributeValueFrom())
							+ "-"
							+ Util
									.nullToString(((ConstructionTypeArticleAttributeCriteria) obj)
											.getAttributeValueTo());
				}
				return ((ConstructionTypeArticleAttribute) obj)
						.getConstructionTypeArticleValue();
			} else if (obj instanceof ConstructionTypeArticle) {
				DecimalFormat decimalFormat = new DecimalFormat();
				decimalFormat.setDecimalSeparatorAlwaysShown(false);
				decimalFormat.setParseIntegerOnly(true);
				if (((ConstructionTypeArticle) obj).getNumberOfItems() != null) {
					return decimalFormat.format(((ConstructionTypeArticle) obj)
							.getNumberOfItems());
				}
				return ((ConstructionTypeArticle) obj).getNumberOfItems();
			}
			return "";
		case 2:
			if (treeNode.isLeaf()) {
				return ((ConstructionTypeArticleAttribute) obj)
						.getDialogOrder();
			} else if (obj instanceof ConstructionTypeArticle) {
				return ((ConstructionTypeArticle) obj).getDialogOrder();

			}
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
		setRoot(new ConstructionTreeNode(object, null));
	}

}
