package no.ugland.utransprod.gui.edit;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.JobFunctionViewHandler;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.JobFunctionModel;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.validators.JobFunctionValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer editering av jobbfunksjon
 * 
 * @author atle.brekka
 * 
 */
public class EditJobFunctionView extends
		AbstractEditView<JobFunctionModel, JobFunction> {
	/**
	 * 
	 */
	private JTextField textFieldName;

	/**
	 * 
	 */
	private JTextField textFieldDescription;

	/**
	 * 
	 */
	private JComboBox comboBoxManager;

	/**
	 * @param searchDialog
	 * @param object
	 * @param aViewHandler
	 */
	public EditJobFunctionView(boolean searchDialog,
			AbstractModel<JobFunction, JobFunctionModel> object,
			AbstractViewHandler<JobFunction, JobFunctionModel> aViewHandler) {
		super(searchDialog, object, aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,5dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldName, cc.xy(4, 2));
		builder.addLabel("Beskrivelse:", cc.xy(2, 4));
		builder.add(textFieldDescription, cc.xy(4, 4));
		builder.addLabel("Leder:", cc.xy(2, 6));
		builder.add(comboBoxManager, cc.xy(4, 6));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 8, 3));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(JobFunctionModel object, boolean search) {
		return new JobFunctionValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName, "Funksjon.navn");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		textFieldName = ((JobFunctionViewHandler) viewHandler)
				.getTextFieldName(presentationModel);

		textFieldDescription = ((JobFunctionViewHandler) viewHandler)
				.getTextFieldDescription(presentationModel);

		comboBoxManager = ((JobFunctionViewHandler) viewHandler)
				.getComboBoxManager(presentationModel);

	}

	public final String getDialogName() {
		return "EditJobFunctionView";
	}

	public final String getHeading() {
		return "Funksjon";
	}
}
