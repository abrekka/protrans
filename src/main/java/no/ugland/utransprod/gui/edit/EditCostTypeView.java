package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.CostTypeViewHandler;
import no.ugland.utransprod.gui.model.CostTypeModel;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.validators.CostTypeValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer visning og editering av kostnadstype
 * 
 * @author atle.brekka
 * 
 */
public class EditCostTypeView extends AbstractEditView<CostTypeModel, CostType> {
	/**
	 * 
	 */
	private JTextField textFieldName;

	/**
	 * 
	 */
	private JTextField textFieldDescription;

	/**
	 * @param handler
	 * @param costType
	 * @param searchDialog
	 */
	public EditCostTypeView(CostTypeViewHandler handler, CostType costType,
			boolean searchDialog) {
		super(searchDialog, new CostTypeModel(costType), handler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		textFieldName = ((CostTypeViewHandler) viewHandler)
				.getTextFieldName(presentationModel);

		textFieldDescription = ((CostTypeViewHandler) viewHandler)
				.getTextFieldDescription(presentationModel);

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,p,50dlu,20dlu,,20dlu", "");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.appendRow(new RowSpec("bottom:20dlu"));
		builder.setLeadingColumnOffset(1);
		builder.nextColumn();
		builder.append("Navn:", textFieldName);
		builder.nextLine();
		builder.append("Beskrivelse:", textFieldDescription, 2);
		builder.nextLine();
		builder.append(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel), 5);

		builder.appendRow(new RowSpec("5dlu"));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(CostTypeModel object, boolean search) {
		return new CostTypeValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName,
				"Kostnadstype.navn");

	}

	public final String getDialogName() {
		return "EditCostTypeView";
	}

	public final String getHeading() {
		return "Kostnadstype";
	}
}
