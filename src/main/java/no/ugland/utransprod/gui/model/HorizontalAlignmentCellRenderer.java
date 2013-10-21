package no.ugland.utransprod.gui.model;

import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class HorizontalAlignmentCellRenderer extends DefaultTableCellRenderer {

	public HorizontalAlignmentCellRenderer(int horizontalAlignment) {
		super();
		setHorizontalAlignment(horizontalAlignment);
	}
}
