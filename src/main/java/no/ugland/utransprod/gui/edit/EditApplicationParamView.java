package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.gui.model.ApplicationParamModel;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;

public class EditApplicationParamView extends
		AbstractEditView<ApplicationParamModel, ApplicationParam> {

	private JTextField textFieldParamName;
	private JTextField textFieldParamValue;

	public EditApplicationParamView(boolean searchDialog,
			ApplicationParamModel applicationParamModel,
			ApplicationParamViewHandler aViewHandler) {
		super(searchDialog, applicationParamModel, aViewHandler);
	}

	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,120dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldParamName, cc.xy(4, 2));
		builder.addLabel("Verdi:", cc.xy(2, 4));
		builder.add(textFieldParamValue, cc.xy(4, 4));

		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 6, 3));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	@Override
	protected Validator getValidator(ApplicationParamModel object,
			boolean search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initComponentAnnotations() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldParamName = ((ApplicationParamViewHandler) viewHandler)
				.getTextFieldParamName(presentationModel);
		textFieldParamValue = ((ApplicationParamViewHandler) viewHandler)
				.getTextFieldParamValue(presentationModel);

	}

	public String getDialogName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHeading() {
		// TODO Auto-generated method stub
		return null;
	}

}
