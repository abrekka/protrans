package no.ugland.utransprod.gui;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandler;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Klasse som håndterer visning av order som skal monteres av gitt team
 * 
 * @author atle.brekka
 * 
 */
public class SupplierOrderView {
	/**
	 * 
	 */
	private SupplierOrderViewHandler viewHandler;

	/**
	 * 
	 */
	private JXTable tableOrders;

	/**
	 * @param handler
	 */
	public SupplierOrderView(SupplierOrderViewHandler handler) {
		viewHandler = handler;
	}

	/**
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		tableOrders = viewHandler.getTableOrders(window);
	}

	/**
	 * @param window
	 * @param rowSize
	 * @return panel
	 */
	public JComponent buildPanel(WindowInterface window, int rowSize) {
		initComponents(window);

		FormLayout layout = new FormLayout("150dlu", "");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		// DefaultFormBuilder builder = new DefaultFormBuilder(new
		// FormDebugPanel(),layout);

		int rowCount = tableOrders.getRowCount();
		if (rowCount == 0) {
			rowCount = 1;
		}
		int tableSize = rowSize;
		String rowSizeSpec = "fill:" + tableSize + "dlu:grow";
		builder.appendRow(new RowSpec(rowSizeSpec));
		builder.append(new JScrollPane(tableOrders));
		return builder.getPanel();
	}
}
