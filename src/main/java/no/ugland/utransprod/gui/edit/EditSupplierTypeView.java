package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.SupplierTypeViewHandler;
import no.ugland.utransprod.gui.model.SupplierTypeModel;
import no.ugland.utransprod.model.SupplierType;
import no.ugland.utransprod.model.validators.SupplierTypeValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse for visning og editering av leverandørtype
 * 
 * @author atle.brekka
 * 
 */
public class EditSupplierTypeView extends
		AbstractEditView<SupplierTypeModel, SupplierType> {
	/**
	 * 
	 */
	private JTextField textFieldSupplierTypeName;

	/**
	 * 
	 */
	private JTextField textFieldDescription;

	/**
	 * @param searchDialog
	 * @param supplierType
	 * @param aViewHandler
	 */
	public EditSupplierTypeView(boolean searchDialog,
			SupplierType supplierType,
			AbstractViewHandler<SupplierType, SupplierTypeModel> aViewHandler) {
		super(searchDialog, new SupplierTypeModel(supplierType), aViewHandler);
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
		builder.add(textFieldSupplierTypeName, cc.xy(4, 2));
		builder.addLabel("Beskrivelse:", cc.xy(2, 4));
		builder.add(textFieldDescription, cc.xy(4, 4));

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
	protected Validator getValidator(SupplierTypeModel object, boolean search) {
		return new SupplierTypeValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldSupplierTypeName, true);
		ValidationComponentUtils.setMessageKey(textFieldSupplierTypeName,
				"SupplierType.navn");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldSupplierTypeName = ((SupplierTypeViewHandler) viewHandler)
				.getTextFieldTypeName(presentationModel);

		textFieldDescription = ((SupplierTypeViewHandler) viewHandler)
				.getTextFieldTypeDescription(presentationModel);

	}

	public final String getDialogName() {
		return "EditSupplierTypeView";
	}

	public final String getHeading() {
		return "Leverandørtype";
	}
}
