package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.EmployeeViewHandler;
import no.ugland.utransprod.gui.handlers.SupplierViewHandler;
import no.ugland.utransprod.gui.model.EmployeeModel;
import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.validators.EmployeeValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse for visning og editering av ensatt
 * 
 * @author atle.brekka
 * 
 */
public class EditEmployeeView extends AbstractEditView<EmployeeModel, Employee> {
	private JTextField textFieldFirstName;

	private JTextField textFieldLastName;
	private JTextField textFieldPhone;

	private JComboBox comboBoxEmployeeType;

	private JButton buttonEmployeeType;
	private JCheckBox checkBoxInactive;

	public EditEmployeeView(boolean searchDialog, Employee employee,
			AbstractViewHandler<Employee, EmployeeModel> aViewHandler) {
		super(searchDialog, new EmployeeModel(employee), aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {

		FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Fornavn:", cc.xy(2, 2));
		builder.add(textFieldFirstName, cc.xy(4, 2));
		builder.addLabel("Etternavn:", cc.xy(2, 4));
		builder.add(textFieldLastName, cc.xy(4, 4));
		builder.addLabel("Telefon:", cc.xy(2, 6));
		builder.add(textFieldPhone, cc.xy(4, 6));
		builder.add(checkBoxInactive, cc.xy(2, 8));
		builder.addLabel("Type:", cc.xy(2, 10));
		builder.add(comboBoxEmployeeType, cc.xy(4, 10));

		builder.add(ButtonBarFactory.buildCenteredBar(buttonEmployeeType),
				cc.xy(6, 10));

		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 12, 5));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());

	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(EmployeeModel object, boolean search) {
		return new EmployeeValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldFirstName, true);
		ValidationComponentUtils.setMessageKey(textFieldFirstName,
				"Ansatt.fornavn");

		ValidationComponentUtils.setMandatory(textFieldLastName, true);
		ValidationComponentUtils.setMessageKey(textFieldLastName,
				"Ansatt.etternavn");

		ValidationComponentUtils.setMandatory(comboBoxEmployeeType, true);
		ValidationComponentUtils.setMessageKey(comboBoxEmployeeType,
				"Ansatt.type");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldFirstName = ((EmployeeViewHandler) viewHandler)
				.getTextFieldFirstName(presentationModel);
		textFieldLastName = ((EmployeeViewHandler) viewHandler)
				.getTextFieldLastName(presentationModel);
		textFieldPhone = ((EmployeeViewHandler) viewHandler)
				.getTextFieldPhone(presentationModel);
		comboBoxEmployeeType = ((EmployeeViewHandler) viewHandler)
				.getComboBoxEmployeeType(presentationModel);
		buttonEmployeeType = ((EmployeeViewHandler) viewHandler)
				.getButtonEmployeeType();
		checkBoxInactive = ((EmployeeViewHandler) viewHandler)
				.getCheckBoxInactive(presentationModel);
	}

	public final String getDialogName() {
		return "EditEmployeeView";
	}

	public final String getHeading() {
		return "Ansatt";
	}
}
