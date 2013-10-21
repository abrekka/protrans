package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.CostUnitViewHandler;
import no.ugland.utransprod.gui.model.CostUnitModel;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.validators.CostUnitValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer visning ag editering av kostnadsenhet
 * 
 * @author atle.brekka
 * 
 */
public class EditCostUnitView extends AbstractEditView<CostUnitModel, CostUnit> {
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
	 * @param costUnit
	 * @param searchDialog
	 */
	public EditCostUnitView(CostUnitViewHandler handler, CostUnit costUnit,
			boolean searchDialog) {
		super(searchDialog, new CostUnitModel(costUnit), handler);
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
	protected Validator getValidator(CostUnitModel object, boolean search) {
		return new CostUnitValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName,
				"Kostnadsenhet.navn");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		textFieldName = ((CostUnitViewHandler) viewHandler)
				.getTextFieldName(presentationModel);

		textFieldDescription = ((CostUnitViewHandler) viewHandler)
				.getTextFieldDescription(presentationModel);

	}

	public final String getDialogName() {
		return "EditCostUnitView";
	}

	public final String getHeading() {
		return "Kostnadsenhet";
	}
}
