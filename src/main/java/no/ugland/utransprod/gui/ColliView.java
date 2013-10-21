package no.ugland.utransprod.gui;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.ColliViewHandler;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som h�ndterer visning av ordre for en gitt kolli
 * 
 * @author atle.brekka
 * 
 */
public class ColliView {
	/**
	 * 
	 */
	private ColliViewHandler viewHandler;

	private JLabel labelColliName;

	/**
	 * 
	 */
	private JXTable tableOrderLines;

	/**
	 * 
	 */
	private JCheckBox checkBoxSelection;

	/**
	 * @param handler
	 */
	public ColliView(ColliViewHandler handler) {
		viewHandler = handler;
	}

	/**
	 * @param window
	 * 
	 */
	private void initComponents(WindowInterface window) {
		labelColliName = viewHandler.getLabelColliName();
		tableOrderLines = viewHandler.getTableOrderLines(window);
		checkBoxSelection = viewHandler.getCheckBoxSelection();
		viewHandler.initEventHandling();
	}

	/**
	 * @param window
	 * @return panel
	 */
	public JPanel buildPanel(WindowInterface window) {
		initComponents(window);

		FormLayout layout = new FormLayout("12dlu,3dlu,70dlu,10dlu",
				"p,3dlu,50dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		//PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.add(checkBoxSelection, cc.xy(1, 1));
		builder.add(labelColliName, cc.xy(3, 1));
		builder.add(new JScrollPane(tableOrderLines), cc.xyw(1, 3,3));
		return builder.getPanel();
	}

}
