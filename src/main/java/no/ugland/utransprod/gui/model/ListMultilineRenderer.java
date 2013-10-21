package no.ugland.utransprod.gui.model;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;

/**
 * Dette er en renderer som lager linjeskift dersom tekst er lengre enn satt
 * linjebredde
 * 
 * @author atle.brekka
 * 
 */
public class ListMultilineRenderer extends JTextPane implements
		ListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private int lineSize = 120;

	/**
	 * @param aLineSize
	 */
	public ListMultilineRenderer(int aLineSize) {
		lineSize = aLineSize;
	}

	/**
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList,
	 *      java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		String allText = value.toString();
		String[] lines = allText.split("\\n");
		StringBuilder builder = new StringBuilder();

		if (lines == null || lines.length == 0) {
			lines = new String[] { allText };
		}
		for (int j = 0; j < lines.length; j++) {
			if (j > 0) {
				builder.append("\n");
			}
			String text = lines[j];
			if (text != null && text.length() > lineSize) {
				int times = text.length() / lineSize;
				for (int i = 0; i <= times; i++) {
					int stopIndex;
					if (text.length() > i * lineSize + lineSize) {
						stopIndex = i * lineSize + lineSize;
					} else {
						stopIndex = text.length();
					}

					builder.append(text.substring(i * lineSize, stopIndex))
							.append("\n");
				}
				builder.deleteCharAt(builder.length() - 1);
			} else {
				builder.append(text);
			}
		}

		setText(builder.toString());

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());

		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		return this;
	}

}
