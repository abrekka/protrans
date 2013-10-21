package no.ugland.utransprod.gui.edit;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationStatusViewHandler;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.DeviationStatusModel;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.model.validators.DeviationStatusValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer editering av avviksstatus
 * 
 * @author atle.brekka
 */
public class EditDeviationStatusView extends
		AbstractEditView<DeviationStatusModel, DeviationStatus> {
	private JTextField textFieldName;

	private JTextField textFieldDescription;

	private JCheckBox chckBoxForManager;

	private JCheckBox chckBoxDeviationDone;

	private JCheckBox chckBoxForDeviation;

	private JCheckBox chckBoxForAccident;

	/**
	 * @param searchDialog
	 * @param object
	 * @param aViewHandler
	 */
	public EditDeviationStatusView(
			final boolean searchDialog,
			final AbstractModel<DeviationStatus, DeviationStatusModel> object,
			final AbstractViewHandler<DeviationStatus, DeviationStatusModel> aViewHandler) {
		super(searchDialog, object, aViewHandler);
	}

	@Override
	protected final JComponent buildEditPanel(final WindowInterface window) {
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,5dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldName, cc.xyw(4, 2, 3));
		builder.addLabel("Beskrivelse:", cc.xy(2, 4));
		builder.add(textFieldDescription, cc.xyw(4, 4, 3));
		builder.add(chckBoxForManager, cc.xy(2, 6));
		builder.add(chckBoxDeviationDone, cc.xy(4, 6));
		builder.add(chckBoxForDeviation, cc.xy(6, 6));
		builder.add(chckBoxForAccident, cc.xy(8, 6));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 8, 7));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());

	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected final Validator getValidator(final DeviationStatusModel object,
			boolean search) {
		return new DeviationStatusValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected final void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName,
				"Avvikstatus.navn");

	}

	@Override
	protected final void initEditComponents(final WindowInterface window1) {
		textFieldName = ((DeviationStatusViewHandler) viewHandler)
				.getTextFieldName(presentationModel);

		textFieldDescription = ((DeviationStatusViewHandler) viewHandler)
				.getTextFieldDescription(presentationModel);
		chckBoxForManager = ((DeviationStatusViewHandler) viewHandler)
				.getCheckBoxForManager(presentationModel);
		chckBoxDeviationDone = ((DeviationStatusViewHandler) viewHandler)
				.getCheckBoxDeviationDone(presentationModel);
		chckBoxForDeviation = ((DeviationStatusViewHandler) viewHandler)
				.getCheckBoxForDeviation(presentationModel);
		chckBoxForAccident = ((DeviationStatusViewHandler) viewHandler)
				.getCheckBoxForAccident(presentationModel);

	}

	public final String getDialogName() {
		return "EditDeviationStatusView";
	}

	public final String getHeading() {
		return "Avvikstatus";
	}
}
