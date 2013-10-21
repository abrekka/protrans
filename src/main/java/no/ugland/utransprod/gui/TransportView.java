package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.handlers.TransportViewHandler;
import no.ugland.utransprod.gui.model.TransportModel;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

/**
 * Klasse som håndterer visning av transportuke.
 * 
 * @author atle.brekka
 */
public class TransportView {
	private JLabel labelName;

	private JDateChooser datePicker;

	private JXTable tableOrders;

	private TransportViewHandler viewHandler;

	private JCheckBox checkBoxSelection;

	private JCheckBox checkBoxSent;

	private JComboBox comboBoxSupplier;

	private JComboBox comboBoxEmployee;

	private JTextField textFieldLoadTime;

	private JTextField textFieldTrolley;

	/**
	 * @param handler
	 * @param transport
	 */
	public TransportView(final TransportViewHandler handler,
			final Transport transport) {
		viewHandler = handler;
		viewHandler.setPresentationModel(new PresentationModel(
				new TransportModel(transport)));
	}

	/**
	 * Initierer komponenter.
	 * 
	 * @param window
	 * @param number
	 */
	private void initComponents(final WindowInterface window, final int number,
			final ProductAreaGroup productAreaGroup) {
		viewHandler.setWindowInterface(window);
		labelName = viewHandler.getLabelName();
		labelName.setName("LabelName");

		datePicker = viewHandler.getDatePicker();
		tableOrders = viewHandler.getTableOrders(window, number,
				productAreaGroup);

		checkBoxSelection = viewHandler.getSelectionCheckBox(number);
		checkBoxSent = viewHandler.getCheckBoxSent(window, number);
		comboBoxSupplier = viewHandler.getComboBoxSupplier(number);
		comboBoxEmployee = viewHandler.getComboBoxEmployee();
		textFieldLoadTime = viewHandler.getTextFieldLoadTime();
		textFieldTrolley = viewHandler.getTextFieldTrolley();
	}

	/**
	 * Bygger panel
	 * 
	 * @param window
	 * @param number
	 * @return panel
	 */
	public final Component buildPanel(final WindowInterface window,
			final int number, ProductAreaGroup productAreaGroup) {
		initComponents(window, number, productAreaGroup);
		viewHandler.initEventHandling();

		int numberOfOrders = tableOrders.getRowCount();
		int tableSize = numberOfOrders * 25 + 25;

		FormLayout layout = new FormLayout("fill:100dlu:grow",
				"10dlu,p,3dlu,fill:" + tableSize + "dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
		// layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildDetailPanel(), cc.xy(1, 2));
		builder.add(new JScrollPane(tableOrders), cc.xy(1, 4));
		return builder.getPanel();
	}

	/**
	 * Bygger detaljpanel
	 * 
	 * @return panel
	 */
	private JPanel buildDetailPanel() {
		FormLayout layout = new FormLayout(
				"p,3dlu,5dlu,80dlu,3dlu,35dlu,5dlu,20dlu,3dlu,18dlu,3dlu,60dlu,3dlu,p,3dlu,25dlu",
				"p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(checkBoxSelection, cc.xy(1, 1));
		builder.add(labelName, cc.xyw(3, 1, 2));
		builder.add(checkBoxSent, cc.xy(6, 1));
		builder.addLabel("Opplasting:", cc.xyw(8, 1, 3));
		builder.add(datePicker, cc.xy(12, 1));
		builder.addLabel("Tid:", cc.xy(14, 1));
		builder.add(textFieldLoadTime, cc.xy(16, 1));
		builder.addLabel("Firma:", cc.xyw(1, 3, 3));
		builder.add(comboBoxSupplier, cc.xyw(4, 3, 3));
		builder.addLabel("Sjåfør:", cc.xy(8, 3));
		builder.add(comboBoxEmployee, cc.xyw(10, 3, 3));
		builder.addLabel("Tralle:", cc.xy(14, 3));
		builder.add(textFieldTrolley, cc.xy(16, 3));

		return builder.getPanel();
	}

	public final void cleanUp() {
		datePicker.cleanup();

	}
}
