package no.ugland.utransprod.gui.edit;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AccidentParticipantViewHandler;
import no.ugland.utransprod.gui.model.AccidentParticipantModel;
import no.ugland.utransprod.model.AccidentParticipant;
import no.ugland.utransprod.model.validators.AccidentParticipantValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

public class EditAccidentParticipantView extends
		AbstractEditView<AccidentParticipantModel, AccidentParticipant> {

	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JComboBox comboBoxEmployeeType;

	public EditAccidentParticipantView(boolean searchDialog,
			AccidentParticipantModel object,
			AccidentParticipantViewHandler aViewHandler) {
		super(searchDialog, object, aViewHandler);
	}

	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,70dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Fornavn:", cc.xy(2, 2));
		builder.add(textFieldFirstName, cc.xy(4, 2));
		builder.addLabel("Etternavn:", cc.xy(2, 4));
		builder.add(textFieldLastName, cc.xy(4, 4));
		builder.addLabel("Stilling:", cc.xy(2, 6));
		builder.add(comboBoxEmployeeType, cc.xy(4, 6));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel),
				cc.xyw(2, 8, 3));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	@Override
	protected Validator getValidator(AccidentParticipantModel object,
			boolean search) {
		return new AccidentParticipantValidator(object);
	}

	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldFirstName, true);
		ValidationComponentUtils.setMessageKey(textFieldFirstName,
				"Involvert.fornavn");

		ValidationComponentUtils.setMandatory(textFieldLastName, true);
		ValidationComponentUtils.setMessageKey(textFieldLastName,
				"Involvert.etternavn");

	}

	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldFirstName = ((AccidentParticipantViewHandler) viewHandler)
				.getTextFieldFirstName(presentationModel);
		textFieldLastName = ((AccidentParticipantViewHandler) viewHandler)
				.getTextFieldLastName(presentationModel);
		comboBoxEmployeeType = ((AccidentParticipantViewHandler) viewHandler)
				.getComboBoxEmployeeType(presentationModel);

	}

	public String getDialogName() {
		return "EditAccidentParticipantView";
	}

	public String getHeading() {
		return "Involvert";
	}

}
