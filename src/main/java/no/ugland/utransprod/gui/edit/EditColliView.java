package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ColliViewHandler;
import no.ugland.utransprod.gui.model.ColliModel;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.validators.ColliValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer editering av kolli
 * 
 * @author atle.brekka
 * 
 */
public class EditColliView extends AbstractEditView<ColliModel, Colli> {
	private JTextField textFieldColliName;
	private JTextField textFieldNumberOfCollies;

	/**
	 * @param searchDialog
	 * @param object
	 * @param aViewHandler
	 */
	public EditColliView(boolean searchDialog, ColliModel colliModel,
			ColliViewHandler colliViewHandler) {
		super(searchDialog, colliModel, colliViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldColliName, cc.xy(4, 2));
		builder.addLabel("Antall:", cc.xy(2, 4));
		builder.add(textFieldNumberOfCollies, cc.xy(4, 4));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 6, 4));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(ColliModel object, boolean search) {
		return new ColliValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldNumberOfCollies, true);
		ValidationComponentUtils.setMessageKey(textFieldNumberOfCollies,
				"Kolli.antall");
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		textFieldColliName = ((ColliViewHandler) viewHandler)
				.getTextFieldColliName(presentationModel);
		textFieldNumberOfCollies = ((ColliViewHandler) viewHandler)
				.getTextFieldNumberOfCollies(presentationModel);

	}

	public final String getDialogName() {
		return "EditColliView";
	}

	public final String getHeading() {
		return "Kolli";
	}
}
