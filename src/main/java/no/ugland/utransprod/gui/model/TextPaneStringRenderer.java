package no.ugland.utransprod.gui.model;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Render for å vise tekst i et textpane
 * 
 * @author atle.brekka
 * 
 */
public class TextPaneStringRenderer extends DefaultTableCellRenderer {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
	 *      java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object object,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setText(((String) object));
		setOpaque(true);
		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());

		} else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}

		return this;
	}

}
