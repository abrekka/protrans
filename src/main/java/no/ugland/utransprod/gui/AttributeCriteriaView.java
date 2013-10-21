package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.handlers.AttributeCriteriaViewHandler;
import no.ugland.utransprod.gui.model.OrderLineAttributeCriteria;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klassesom brukes for å sette verdier på utvalg som skal gjøres på attributter
 * 
 * @author atle.brekka
 * 
 */
public class AttributeCriteriaView {
	/**
	 * 
	 */
	private PresentationModel presentationModel;

	/**
	 * 
	 */
	private JCheckBox checkBoxYesNo;

	/**
	 * 
	 */
	private JTextField textFieldFrom;

	/**
	 * 
	 */
	private JTextField textFieldTo;

	/**
	 * 
	 */
	private JButton buttonOk;

	/**
	 * 
	 */
	private AttributeCriteriaViewHandler viewHandler;

	/**
	 * 
	 */
	private JLabel labelAttribute;

	/**
	 * 
	 */
	private OrderLineAttributeCriteria currentCriteria;


	/**
	 * @param criteria
	 * @param handler
	 */
	public AttributeCriteriaView(OrderLineAttributeCriteria criteria,
			AttributeCriteriaViewHandler handler) {
		currentCriteria = criteria;
		presentationModel = new PresentationModel(criteria);
		viewHandler = handler;
	}

	/**
	 * Initierer komponenter
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		checkBoxYesNo = viewHandler.getCheckBoxYesNo(presentationModel);
		if (currentCriteria.isYesNo()) {
			checkBoxYesNo.setSelected(true);
		}
		textFieldFrom = viewHandler.getTextFieldFrom(presentationModel);
		textFieldTo = viewHandler.getTextFieldTo(presentationModel);
		buttonOk = viewHandler.getButtonOk(window);
		labelAttribute = viewHandler.getLabelAttribute(presentationModel);

	}

	/**
	 * Bygger panel med komponenter
	 * @param window
	 * @return panel
	 */
	public JComponent buildPanel(WindowInterface window) {
		initComponents(window);
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,50dlu,3dlu,50dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(labelAttribute, cc.xy(2, 4));
		if (currentCriteria.isYesNo()) {
			builder.add(checkBoxYesNo, cc.xy(4, 4));
		} else if (currentCriteria.getChoices() != null
				&& currentCriteria.getChoices().size() != 0) {
			builder.addLabel("Valg:", cc.xy(4, 2));
			builder.add(textFieldFrom, cc.xy(4, 4));
		} else {
			builder.addLabel("Fra:", cc.xy(4, 2));
			builder.add(textFieldFrom, cc.xy(4, 4));
			builder.addLabel("Til:", cc.xy(6, 2));
			builder.add(textFieldTo, cc.xy(6, 4));
		}
		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk), cc
				.xyw(2, 6, 5));
		return builder.getPanel();
	}
}
