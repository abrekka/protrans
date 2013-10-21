package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.model.PreventiveActionModel;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.model.validators.PreventiveActionValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Håndterer editering av prevantivt tiltak
 * 
 * @author atle.brekka
 * 
 */
public class EditPreventiveActionView extends
		AbstractEditView<PreventiveActionModel, PreventiveAction> {

	/**
	 * 
	 */
	private JTextField textFieldProjectNr;

	/**
	 * 
	 */
	private JTextField textFieldManager;

	/**
	 * 
	 */
	private JComboBox comboBoxFunction;

	/**
	 * 
	 */
	private JComboBox comboBoxCategory;

	/**
	 * 
	 */
	private JTextArea textAreaDescription;

	/**
	 * 
	 */
	private JTextArea textAreaExpectedOutcome;

	/**
	 * 
	 */
	private JCheckBox checkBoxClosed;

	/**
	 * 
	 */
	private JList listComments;

	/**
	 * 
	 */
	private JButton buttonAddComment;

	/**
	 * 
	 */
	private JTextField textFieldName;

	/**
	 * @param searchDialog
	 * @param object
	 * @param aViewHandler
	 */
	public EditPreventiveActionView(boolean searchDialog,
			PreventiveAction object, PreventiveActionViewHandler aViewHandler) {
		super(searchDialog, new PreventiveActionModel(object), aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldProjectNr = ((PreventiveActionViewHandler) viewHandler)
				.getTextFieldProjectNr(presentationModel);
		textFieldManager = ((PreventiveActionViewHandler) viewHandler)
				.getTextFieldManager(presentationModel);
		comboBoxFunction = ((PreventiveActionViewHandler) viewHandler)
				.getComboBoxFunction(presentationModel);
		comboBoxCategory = ((PreventiveActionViewHandler) viewHandler)
				.getComboBoxCategory(presentationModel);
		textAreaDescription = ((PreventiveActionViewHandler) viewHandler)
				.getTextAreaDescription(presentationModel);
		textAreaExpectedOutcome = ((PreventiveActionViewHandler) viewHandler)
				.getTextAreaExpectedOutcome(presentationModel);
		listComments = ((PreventiveActionViewHandler) viewHandler)
				.getListComments(presentationModel);
		buttonAddComment = ((PreventiveActionViewHandler) viewHandler)
				.getButtonAddComment(aWindow, presentationModel);
		checkBoxClosed = ((PreventiveActionViewHandler) viewHandler)
				.getCheckBoxClosed(presentationModel, aWindow);
		textFieldName = ((PreventiveActionViewHandler) viewHandler)
				.getTextFieldName(presentationModel);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout(
				"10dlu,p,center:3dlu,center:120dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,fill:50dlu:grow,3dlu,p,3dlu,fill:50dlu:grow,3dlu,p,3dlu,fill:100dlu:grow,3dlu,p,5dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildDetailsPanel(), cc.xyw(2, 2, 3));
		builder.addLabel("Beskrivelse:", cc.xy(2, 4));
		builder.add(new JScrollPane(textAreaDescription), cc.xywh(2, 6, 3, 1));
		builder.addLabel("Forventet resultat:", cc.xy(2, 8));
		builder.add(new JScrollPane(textAreaExpectedOutcome),
				cc.xywh(2, 10, 3, 1));
		builder.addLabel("Historikk:", cc.xy(2, 12));
		builder.add(new JScrollPane(listComments), cc.xywh(2, 14, 3, 1));
		builder.add(ButtonBarFactory.buildLeftAlignedBar(buttonAddComment),
				cc.xyw(2, 16, 3));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 18, 3));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Bygger panel for detaljer
	 * 
	 * @return panel
	 */
	private JPanel buildDetailsPanel() {
		FormLayout layout = new FormLayout("p,3dlu,p",
				"p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Prosjektnr:", cc.xy(1, 1));
		builder.add(textFieldProjectNr, cc.xy(3, 1));
		builder.addLabel("Prosjektnavn:", cc.xy(1, 3));
		builder.add(textFieldName, cc.xy(3, 3));
		builder.addLabel("Prosjektleder:", cc.xy(1, 5));
		builder.add(textFieldManager, cc.xy(3, 5));
		builder.addLabel("Funksjon:", cc.xy(1, 7));
		builder.add(comboBoxFunction, cc.xy(3, 7));
		builder.addLabel("Kategori:", cc.xy(1, 9));
		builder.add(comboBoxCategory, cc.xy(3, 9));
		builder.add(checkBoxClosed, cc.xy(3, 11));

		return builder.getPanel();
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(PreventiveActionModel object,
			boolean search) {
		return new PreventiveActionValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName,
				"Prevantivt tiltak.prosjektnavn");

	}

	public final String getDialogName() {
		return "EditPreventiveActionView";
	}

	public final String getHeading() {
		return "Korrigerende tiltak";
	}
}
