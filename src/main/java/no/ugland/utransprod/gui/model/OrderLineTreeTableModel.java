package no.ugland.utransprod.gui.model;

import java.text.DecimalFormat;

import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 * Modell for tretabell for ordrelinjer
 * 
 * @author atle.brekka
 * @param <T>
 * @param <E>
 */
public class OrderLineTreeTableModel<T, E> extends DefaultTreeTableModel {
	/**
	 * Objekt som skal vises (garasjetype)
	 */
	private OrderWrapper<T, E> orderWrapper;

	/**
	 * @param aObject
	 */
	public OrderLineTreeTableModel(OrderWrapper<T, E> aObject, boolean brukOrdrelinjelinjer) {
		super(new OrderLineTreeNode(aObject, null, brukOrdrelinjelinjer));
		orderWrapper = aObject;

	}

	/**
	 * Henter objekt som vises (garasjetype)
	 * 
	 * @return objekt
	 */
	public OrderWrapper<T, E> getObject() {
		return orderWrapper;
	}

	/**
	 * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 3;
	}

	/**
	 * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Artikkel";
		case 1:
			return "Verdi";
		case 2:
			return "Kolli";
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
		OrderLineTreeNode treeNode = (OrderLineTreeNode) aObject;
		Object obj = treeNode.getObject();
		if (obj != null) {
			switch (aColumn) {
			case 0:

				return obj.toString();
			case 1:
				if (treeNode.isLeaf()) {
					if (obj instanceof OrderLineAttributeCriteria) {
						return Util.nullToString(((OrderLineAttributeCriteria) obj).getAttributeValueFrom()) + "-"
								+ Util.nullToString(((OrderLineAttributeCriteria) obj).getAttributeValueTo());
					}
					return ((OrderLineAttribute) obj).getOrderLineAttributeValue();
				} else if (obj instanceof OrderLine) {
					DecimalFormat decimalFormat = new DecimalFormat();
					decimalFormat.setDecimalSeparatorAlwaysShown(false);
					decimalFormat.setParseIntegerOnly(true);

					/*
					 * if (((OrderLine) obj).getNumberOfItems() != null) {
					 * return decimalFormat.format(((OrderLine) obj)
					 * .getNumberOfItems()); }
					 */
					StringBuilder returnString = new StringBuilder();
					if (((OrderLine) obj).getNumberOfItems() != null) {
						returnString.append(decimalFormat.format(((OrderLine) obj).getNumberOfItems()));

						String metric = ((OrderLine) obj).getMetric();
						if (metric != null) {
							returnString.append(" ").append(metric);
						}
					}

					return returnString.toString();
				}
				return "";
			case 2:
				if (obj instanceof OrderLine) {
					return ((OrderLine) obj).getColli();
				}
				return "";
			default:
				return "";
			}
		}
		return "";
	}

	/**
	 * @see org.jdesktop.swingx.treetable.DefaultTreeTableModel#setValueAt(java.lang.Object,
	 *      java.lang.Object, int)
	 */
	@Override
	public void setValueAt(Object value, Object node, int column) {

	}

	/**
	 * Gir beskjed om at tre har endret segF
	 */
	public void fireChanged(boolean brukOrdrelinjelinjer) {
		setRoot(new OrderLineTreeNode(orderWrapper, null, brukOrdrelinjelinjer));
	}

}
