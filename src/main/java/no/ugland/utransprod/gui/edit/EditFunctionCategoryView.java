package no.ugland.utransprod.gui.edit;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.FunctionCategoryViewHandler;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.FunctionCategoryModel;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.validators.FunctionCategoryValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer editering av funksjonskategorier
 * 
 * @author atle.brekka
 * 
 */
public class EditFunctionCategoryView extends
		AbstractEditView<FunctionCategoryModel, FunctionCategory> {
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
	private JComboBox comboBoxFunction;

	/**
	 * @param searchDialog
	 * @param object
	 * @param aViewHandler
	 */
	public EditFunctionCategoryView(
			boolean searchDialog,
			AbstractModel<FunctionCategory, FunctionCategoryModel> object,
			AbstractViewHandler<FunctionCategory, FunctionCategoryModel> aViewHandler) {
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
		builder.addLabel("Funksjon:", cc.xy(2, 6));
		builder.add(comboBoxFunction, cc.xy(4, 6));
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
	protected Validator getValidator(FunctionCategoryModel object,
			boolean search) {
		return new FunctionCategoryValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName, "Kategori.navn");

		ValidationComponentUtils.setMandatory(comboBoxFunction, true);
		ValidationComponentUtils.setMessageKey(comboBoxFunction,
				"Kategori.funksjon");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		textFieldName = ((FunctionCategoryViewHandler) viewHandler)
				.getTextFieldName(presentationModel);

		textFieldDescription = ((FunctionCategoryViewHandler) viewHandler)
				.getTextFieldDescription(presentationModel);

		comboBoxFunction = ((FunctionCategoryViewHandler) viewHandler)
				.getComboBoxFunction(presentationModel);

	}

	public final String getDialogName() {
		return "EditFunctioncategoryView";
	}

	public final String getHeading() {
		return "Kategori";
	}
}
