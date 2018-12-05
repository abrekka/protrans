package no.ugland.utransprod.gui.model;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;

public abstract class TextPaneRenderer<T> extends JTextPane implements
		TableCellRenderer {
	private static final long serialVersionUID = 1L;
	public static final int MAX_LENGHT = 50;

	@SuppressWarnings("unchecked")
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setCenteredAlignment();
		setText(getPaneText((T) value));
		formatAndSetToolTip(getPaneToolTipText((T) value));
		insertIcons((T) value);
		setBackgrounAndForeground(table, isSelected);

		return this;
	}

	protected abstract void setCenteredAlignment();

	protected abstract String getPaneText(T object);

	protected abstract StringBuffer getPaneToolTipText(T object);

	protected abstract List<Icon> getIcons(T object);

	private void formatAndSetToolTip(StringBuffer toolTip) {
		if (toolTip.length() > 0) {
			toolTip.insert(0, "<html>");
			toolTip.append("</html>");
			setToolTipText(toolTip.toString());
		} else {
			setToolTipText("");
		}
	}

	private void insertIcons(T object) {
		List<Icon> icons = getIcons(object);
		icons = icons != null ? icons : new ArrayList<Icon>();
		for (Icon icon : icons) {
			insertIcon(icon);
		}
	}

	private void setBackgrounAndForeground(final JTable table,
			final boolean isSelected) {
		setBackground(Color.CYAN);
		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());

		} else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}
	}
}
