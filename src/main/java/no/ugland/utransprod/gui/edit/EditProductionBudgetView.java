package no.ugland.utransprod.gui.edit;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.ProductionBudgetViewHandler;
import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.validators.ProductionBudgetValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.toedter.calendar.JYearChooser;

/**
 * Til editering av budsjett
 * 
 * @author atle.brekka
 * 
 */
public class EditProductionBudgetView extends
		AbstractEditView<ProductionBudgetModel, Budget> {
	/**
	 * 
	 */
	private JYearChooser yearChooser;

	/**
	 * 
	 */
	private JComboBox comboBoxWeek;

	/**
	 * 
	 */
	private JTextField textFieldBudget;

	/**
	 * 
	 */
	private JComboBox comboBoxProductionArea;

	/**
	 * @param searchDialog
	 * @param productionBudget
	 * @param aViewHandler
	 */
	public EditProductionBudgetView(boolean searchDialog,
			Budget productionBudget,
			AbstractViewHandler<Budget, ProductionBudgetModel> aViewHandler) {
		super(searchDialog, new ProductionBudgetModel(productionBudget),
				aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,center:p,10dlu",
				"10dlu,p,5dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildFields(), cc.xyw(2, 2, 1));

		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 4, 1));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Lager panel med felter for år, uke og budsjett
	 * 
	 * @return panel
	 */
	private JPanel buildFields() {
		FormLayout layout = new FormLayout("p,3dlu,p,30dlu",
				"p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("År:", cc.xy(1, 1));
		builder.add(yearChooser, cc.xy(3, 1));
		builder.addLabel("Uke:", cc.xy(1, 3));
		builder.add(comboBoxWeek, cc.xy(3, 3));
		builder.addLabel("Budsjett:", cc.xy(1, 5));
		builder.add(textFieldBudget, cc.xyw(3, 5, 2));
		builder.addLabel("Produktområde:", cc.xy(1, 7));
		builder.add(comboBoxProductionArea, cc.xyw(3, 7, 2));

		return builder.getPanel();
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(ProductionBudgetModel object,
			boolean search) {
		return new ProductionBudgetValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(yearChooser, true);
		ValidationComponentUtils.setMessageKey(yearChooser,
				"Produksjonsbudsjett.år");

		ValidationComponentUtils.setMandatory(comboBoxWeek, true);
		ValidationComponentUtils.setMessageKey(comboBoxWeek,
				"Produksjonsbudsjett.uke");

		ValidationComponentUtils.setMandatory(textFieldBudget, true);
		ValidationComponentUtils.setMessageKey(textFieldBudget,
				"Produksjonsbudsjett.budsjett");

		ValidationComponentUtils.setMandatory(comboBoxProductionArea, true);
		ValidationComponentUtils.setMessageKey(comboBoxProductionArea,
				"Produksjonsbudsjett.produktområde");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		yearChooser = ((ProductionBudgetViewHandler) viewHandler)
				.getYearChooser(presentationModel);
		comboBoxWeek = ((ProductionBudgetViewHandler) viewHandler)
				.getComboBoxWeek(presentationModel);
		textFieldBudget = ((ProductionBudgetViewHandler) viewHandler)
				.getTextFieldBudget(presentationModel);
		comboBoxProductionArea = ((ProductionBudgetViewHandler) viewHandler)
				.getComboBoxProductArea(presentationModel);

	}

	public final String getDialogName() {
		return "EditProductionBudgetView";
	}

	public final String getHeading() {
		return "Budsjett";
	}
}
