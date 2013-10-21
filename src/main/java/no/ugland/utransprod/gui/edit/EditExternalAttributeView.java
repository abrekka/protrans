package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.ExternalOrderLineAttributeViewHandler;
import no.ugland.utransprod.gui.model.ExternalOrderLineAttributeModel;
import no.ugland.utransprod.model.ExternalOrderLineAttribute;
import no.ugland.utransprod.model.validators.ExternalOrderLineAttributeValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klase for visning og editering av attributt for ekstern ordre
 * 
 * @author atle.brekka
 * 
 */
public class EditExternalAttributeView
		extends
		AbstractEditView<ExternalOrderLineAttributeModel, ExternalOrderLineAttribute> {
	/**
	 * 
	 */
	private JTextField textFieldName;

	/**
	 * 
	 */
	private JTextField textFieldValue;

	/**
	 * @param externalOrderLineAttribute
	 * @param aViewHandler
	 */
	public EditExternalAttributeView(
			ExternalOrderLineAttribute externalOrderLineAttribute,
			AbstractViewHandler<ExternalOrderLineAttribute, ExternalOrderLineAttributeModel> aViewHandler) {
		super(false, new ExternalOrderLineAttributeModel(
				externalOrderLineAttribute), aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldName, cc.xy(4, 2));
		builder.addLabel("Verdi:", cc.xy(2, 4));
		builder.add(textFieldValue, cc.xy(4, 4));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel),
				cc.xyw(2, 6, 3));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(ExternalOrderLineAttributeModel object,
			boolean search) {
		return new ExternalOrderLineAttributeValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName, "Attributt.navn");

		ValidationComponentUtils.setMandatory(textFieldValue, true);
		ValidationComponentUtils.setMessageKey(textFieldValue,
				"Attributt.verdi");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldName = ((ExternalOrderLineAttributeViewHandler) viewHandler)
				.getTextFieldName(presentationModel);
		textFieldValue = ((ExternalOrderLineAttributeViewHandler) viewHandler)
				.getTextFieldValue(presentationModel);
		buttonCancel = ((ExternalOrderLineAttributeViewHandler) viewHandler)
				.getButtonCancel(aWindow);
	}

	public final String getDialogName() {
		return "EditExternalAttributeView";
	}

	public final String getHeading() {
		return "Attributt";
	}
}
