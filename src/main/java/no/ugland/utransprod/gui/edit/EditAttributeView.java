package no.ugland.utransprod.gui.edit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AttributeViewHandler;
import no.ugland.utransprod.gui.model.AttributeModel;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.validators.AttributeValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer visning og editering av attributt
 * 
 * @author atle.brekka
 */
public class EditAttributeView extends
		AbstractEditView<AttributeModel, Attribute> {
	private JTextField textFieldName;

	private JTextField textFielddescription;

	private JCheckBox checkBoxYesNo;

	// private JList listChoices;

	// private JButton buttonAddChoice;

	// private JButton buttonRemoveChoice;

	private JCheckBox checkBoxSpecialConcern;
	private JTextField textFieldProdCatNo;
	private JTextField textFieldProdCatNo2;

	private JComboBox comboBoxDataType;

	/**
	 * @param handler
	 * @param attribute
	 * @param searchDialog
	 */
	public EditAttributeView(AttributeViewHandler handler, Attribute attribute,
			boolean searchDialog) {
		super(searchDialog, new AttributeModel(attribute), handler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {

		initEventHandling();
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,80dlu,3dlu,p,3dlu,45dlu,3dlu,30dlu,20dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,70dlu,5dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
		// layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldName, cc.xyw(4, 2, 1));
		builder.addLabel("Produktkategorinr:", cc.xyw(6, 2, 3));
		builder.add(textFieldProdCatNo, cc.xy(10, 2));
		builder.addLabel("Beskrivelse:", cc.xy(2, 4));
		builder.add(textFielddescription, cc.xyw(4, 4, 1));
		builder.addLabel("Produktkategorinr2:", cc.xyw(6, 4, 3));
		builder.add(textFieldProdCatNo2, cc.xy(10, 4));
		builder.add(checkBoxYesNo, cc.xy(2, 6));
		builder.add(checkBoxSpecialConcern, cc.xy(4, 6));
		builder.addLabel("Type:", cc.xy(6, 6));
		builder.add(comboBoxDataType, cc.xy(8, 6));
		builder.addLabel("Valg:", cc.xy(2, 8));

		builder.add(((AttributeViewHandler) viewHandler)
				.buildAttributeChoicePanel(presentationModel, window), cc.xyw(
				2, 10, 9));

		((AttributeViewHandler) viewHandler)
				.enableChoices(((AttributeModel) presentationModel.getBean())
						.getObject().getAttributeId() == null ? false : true);
		window.setName("EditAttributeView");
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 12, 10));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Initierer hendelsehåndtering
	 */
	private void initEventHandling() {
		presentationModel.getBufferedModel(AttributeModel.PROPERTY_YES_NO)
				.addPropertyChangeListener(new ChoiceChangeListener());
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(AttributeModel object, boolean search) {
		return new AttributeValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName, "Attributt.navn");

		ValidationComponentUtils.setMandatory(textFieldProdCatNo, true);
		ValidationComponentUtils.setMessageKey(textFieldProdCatNo,
				"Attributt.produktkategori");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		textFieldName = ((AttributeViewHandler) viewHandler)
				.getTextFieldName(presentationModel);

		textFielddescription = ((AttributeViewHandler) viewHandler)
				.getTextFieldDescription(presentationModel);

		checkBoxYesNo = ((AttributeViewHandler) viewHandler)
				.getCheckBoxYesNo(presentationModel);
		checkBoxSpecialConcern = ((AttributeViewHandler) viewHandler)
				.getCheckBoxSpecialConcern(presentationModel);
		textFieldProdCatNo = ((AttributeViewHandler) viewHandler)
				.getTextFieldProdCatNo(presentationModel);
		textFieldProdCatNo2 = ((AttributeViewHandler) viewHandler)
				.getTextFieldProdCatNo2(presentationModel);
		comboBoxDataType = ((AttributeViewHandler) viewHandler)
				.getComboBoxDataType(presentationModel);
	}

	/**
	 * Klasse som håndterer enabler komponenter ved valg i dialog
	 * 
	 * @author atle.brekka
	 */
	final class ChoiceChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			((AttributeViewHandler) viewHandler).enableChoices(!(Boolean) event
					.getNewValue());

		}

	}

	public final String getDialogName() {
		return "EditAttributeView";
	}

	public final String getHeading() {
		return "Attributt";
	}
}
