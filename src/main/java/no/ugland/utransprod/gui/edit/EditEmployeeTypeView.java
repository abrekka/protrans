package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.EmployeeTypeViewHandler;
import no.ugland.utransprod.gui.model.EmployeeTypeModel;
import no.ugland.utransprod.model.EmployeeType;
import no.ugland.utransprod.model.validators.EmployeeTypeValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse for visning og editering av ansattype
 * 
 * @author atle.brekka
 * 
 */
public class EditEmployeeTypeView extends
		AbstractEditView<EmployeeTypeModel, EmployeeType> {
	/**
	 * 
	 */
	private JTextField textFieldEmployeeTypeName;

	/**
	 * 
	 */
	private JTextField textFieldEmployeeTypeDescription;

	/**
	 * @param searchDialog
	 * @param employeeType
	 * @param aViewHandler
	 */
	public EditEmployeeTypeView(boolean searchDialog,
			EmployeeType employeeType,
			AbstractViewHandler<EmployeeType, EmployeeTypeModel> aViewHandler) {
		super(searchDialog, new EmployeeTypeModel(employeeType), aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {

		FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldEmployeeTypeName, cc.xy(4, 2));
		builder.addLabel("Beskrivelse:", cc.xy(2, 4));
		builder.add(textFieldEmployeeTypeDescription, cc.xy(4, 4));

		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 6, 3));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());

	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(EmployeeTypeModel object, boolean search) {
		return new EmployeeTypeValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldEmployeeTypeName, true);
		ValidationComponentUtils.setMessageKey(textFieldEmployeeTypeName,
				"Ansattype.navn");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldEmployeeTypeName = ((EmployeeTypeViewHandler) viewHandler)
				.getTextFieldTypeName(presentationModel);

		textFieldEmployeeTypeDescription = ((EmployeeTypeViewHandler) viewHandler)
				.getTextFieldTypeDescription(presentationModel);

	}

	public final String getDialogName() {
		return "EditEmployeeTypeView";
	}

	public final String getHeading() {
		return "Ansattype";
	}
}
