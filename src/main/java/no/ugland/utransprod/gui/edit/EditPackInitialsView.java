package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.PackInitialsViewHandler;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Brukes til å sette initialer for pakkere
 * 
 * @author atle.brekka
 * 
 */
public class EditPackInitialsView {
	/**
	 * 
	 */
	private JComboBox comboBoxInitials1;

	/**
	 * 
	 */
	private JComboBox comboBoxInitials2;

	/**
	 * 
	 */
	private JComboBox comboBoxInitials3;

	/**
	 * 
	 */
	private JButton buttonOk;
	/**
	 * 
	 */
	private JButton buttonCancel;

	/**
	 * 
	 */
	private PackInitialsViewHandler viewHandler;

	/**
	 * 
	 */
	private JTextField textFieldColliHeight;

	/**
	 * @param handler
	 */
	public EditPackInitialsView(PackInitialsViewHandler handler) {
		viewHandler = handler;

	}

	/**
	 * Initierere vinduskomponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		comboBoxInitials1 = viewHandler.getComboBoxInitials1();
		comboBoxInitials2 = viewHandler.getComboBoxInitials2();
		comboBoxInitials3 = viewHandler.getComboBoxInitials3();
		buttonOk = viewHandler.getButtonOk(window);
		buttonCancel = viewHandler.getButtonCancel(window);
		textFieldColliHeight = viewHandler.getTextFieldColliHeight();
	}

	/**
	 * Bygger panel
	 * 
	 * @param window
	 * @return panel
	 */
	public JPanel buildPanel(WindowInterface window) {
		window.setName("EditPackInitials");
		initComponents(window);
		FormLayout layout = new FormLayout(
				"10dlu,50dlu,3dlu,50dlu,3dlu,50dlu,3dlu,50dlu,3dlu,30dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Pakker 1:", cc.xy(2, 2));
		builder.addLabel("Pakker 2:", cc.xy(4, 2));
		builder.addLabel("Pakker 3:", cc.xy(6, 2));
		builder.addLabel("Høyde garasjepakke(cm):", cc.xyw(8, 2, 3));

		builder.add(comboBoxInitials1, cc.xy(2, 4));
		builder.add(comboBoxInitials2, cc.xy(4, 4));
		builder.add(comboBoxInitials3, cc.xy(6, 4));
		builder.add(textFieldColliHeight, cc.xy(8, 4));

		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk,buttonCancel), cc
				.xyw(2, 6, 9));

		return builder.getPanel();
	}

}
