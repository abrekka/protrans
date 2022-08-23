package no.ugland.utransprod.gui.edit;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.Viewer;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AccidentViewHandler;
import no.ugland.utransprod.gui.model.AccidentModel;
import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.model.validators.AccidentValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;
import no.ugland.utransprod.util.InternalFrameBuilder;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.toedter.calendar.JDateChooser;

public class EditAccidentView extends AbstractEditView<AccidentModel, Accident> implements Viewer {
	private JTextField textFieldRegisteredBy;

	private JDateChooser dateChooserRegistrationDate;

	private JComboBox comboBoxJobFunction;

	private JRadioButton radioButtonPersonalInjuryOver24;

	private JRadioButton radioButtonPersonalInjuryUnder24;
	private JRadioButton radioButtonPersonalInjuryNotAbsent;
	private JRadioButton radioButtonNotPersonalInjury;

	private JButton buttonAddParticipant;

	private JButton buttonDeleteParticipant;

	private JList listParticipants;

	private JDateChooser dateChooserAccidentDate;

	private JTextField textFieldTime;

	private JTextArea textAreaDescription;

	private JTextArea textAreaCause;

	private JCheckBox checkBoxLeader;

	private JCheckBox checkBoxPolice;
	private JCheckBox checkBoxArbeidstilsynet;

	private JCheckBox checkBoxSocialSecurity;

	private JLabel labelLink;

	private JButton buttonPrint;

	private JTextArea textAreaPreventiveActionComment;

	private JComboBox comboBoxResponsible;

	private JTextField textFieldAbsentDays;
	private JTextField textFieldNumberOfOwnEmployess;

	private JDateChooser dateChooserDoneDate;

	private JComboBox comboBoxStatus;

	public EditAccidentView(final boolean searchDialog, final AccidentModel accidentModel,
			final AccidentViewHandler accidentViewHandler) {
		super(searchDialog, accidentModel, accidentViewHandler);
	}

	private JPanel buildDetailPanel() {
		FormLayout layout = new FormLayout("p,3dlu,70dlu,3dlu,p,3dlu,60dlu", "p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Registert av:", cc.xy(1, 1));
		builder.add(textFieldRegisteredBy, cc.xy(3, 1));
		builder.addLabel("Dato:", cc.xy(5, 1));
		builder.add(dateChooserRegistrationDate, cc.xy(7, 1));
		builder.addLabel("Avdeling for hendelse/ulykke:", cc.xyw(1, 3, 3));
		builder.add(comboBoxJobFunction, cc.xyw(5, 3, 3));

		return builder.getPanel();
	}

	private JPanel buildDateTimePanel() {
		FormLayout layout = new FormLayout("60dlu,3dlu,p,3dlu,40dlu,10dlu,3dlu,p,3dlu,p", "p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Dato/klokkeslett for hendelse/ulykke:", cc.xyw(1, 1, 6));
		builder.addLabel("Ferdig dato", cc.xy(8, 1));
		builder.addLabel("Status", cc.xy(10, 1));
		builder.add(dateChooserAccidentDate, cc.xy(1, 3));
		builder.addLabel("/", cc.xy(3, 3));

		builder.add(textFieldTime, cc.xy(5, 3));
		builder.add(dateChooserDoneDate, cc.xy(8, 3));
		builder.add(comboBoxStatus, cc.xy(10, 3));

		return builder.getPanel();
	}

	private JPanel buildCheckBoxPanel() {
		FormLayout layout = new FormLayout("p,3dlu,fill:70dlu", "p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(checkBoxLeader, cc.xyw(1, 1, 3));
		builder.add(checkBoxPolice, cc.xy(1, 3));
		builder.add(checkBoxSocialSecurity, cc.xy(1, 5));

		if (!search) {
			builder.add(labelLink, cc.xy(3, 5));
		}
		builder.add(checkBoxArbeidstilsynet, cc.xy(1, 7));

		return builder.getPanel();
	}

	private JPanel buildResponsiblePanel() {
		FormLayout layout = new FormLayout("p,3dlu,p", "p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Saksbehandler:", cc.xy(1, 1));
		builder.add(comboBoxResponsible, cc.xy(3, 1));

		return builder.getPanel();
	}

	@Override
	protected final JComponent buildEditPanel(final WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,300dlu:grow,10dlu",
				"10dlu,p,3dlu,p,3dlu,fill:p:grow,3dlu,p,3dlu,p,3dlu,fill:40dlu:grow,3dlu,p,3dlu,fill:40dlu:grow,3dlu,p,3dlu,fill:40dlu:grow,3dlu,p,3dlu,p,5dlu,"
						+ "p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildDetailPanel(), cc.xy(2, 2));

		// builder.add(radioButtonPersonalInjury, cc.xy(2, 4));
		// builder.add(radioButtonNotPersonalInjury, cc.xy(2, 6));
		builder.add(buildAccidentTypePanel(), cc.xy(2, 4));
		if (!search) {
			builder.add(buildParticipantsPanel(), cc.xy(2, 6));
		}

		builder.add(buildDateTimePanel(), cc.xy(2, 8));
		builder.addLabel("Beskrivelse av hendelse/ulykke:", cc.xy(2, 10));
		builder.add(new JScrollPane(textAreaDescription), cc.xy(2, 12));
		builder.addLabel("Årsak til hendelse/ulykke:", cc.xy(2, 14));
		builder.add(new JScrollPane(textAreaCause), cc.xy(2, 16));

		builder.addLabel("Beskrivelse av tiltak:", cc.xy(2, 18));
		builder.add(new JScrollPane(textAreaPreventiveActionComment), cc.xy(2, 20));

		builder.add(buildResponsiblePanel(), cc.xy(2, 22));

		builder.add(buildCheckBoxPanel(), cc.xy(2, 24));

		if (search) {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel), cc.xy(2, 26));
		} else {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonPrint, buttonSave, buttonCancel), cc.xy(2, 26));
		}

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	private JPanel buildAccidentTypePanel() {
		FormLayout layout = new FormLayout("p,10dlu,p,3dlu,40dlu", "p,1dlu,p,1dlu,p,1dlu,p");

		PanelBuilder builder = new PanelBuilder(layout);
//		 PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(radioButtonPersonalInjuryOver24, cc.xy(1, 1));
		builder.addLabel("Antall fraværsdager:", cc.xy(3, 1));
		builder.addLabel("Antall fast ansatte som er skadet:", cc.xy(3, 3));
		builder.add(textFieldAbsentDays, cc.xy(5, 1));
		builder.add(textFieldNumberOfOwnEmployess, cc.xy(5, 3));
		builder.add(radioButtonPersonalInjuryUnder24, cc.xy(1, 3));
		builder.add(radioButtonPersonalInjuryNotAbsent, cc.xy(1, 5));
		builder.add(radioButtonNotPersonalInjury, cc.xy(1, 7));

		return builder.getPanel();
	}

	private JPanel buildParticipantsPanel() {
		FormLayout layout = new FormLayout("100dlu:grow,3dlu,p", "p,3dlu,30dlu:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Involverte:", cc.xy(1, 1));
		builder.add(new JScrollPane(listParticipants), cc.xy(1, 3));
		builder.add(buildParticipantButtonPanel(), cc.xy(3, 3));

		return builder.getPanel();
	}

	private JPanel buildParticipantButtonPanel() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonAddParticipant);
		builder.addGlue();
		builder.addGridded(buttonDeleteParticipant);
		return builder.getPanel();
	}

	@Override
	protected final Validator getValidator(final AccidentModel object, boolean search) {
		return new AccidentValidator(object);
	}

	@Override
	protected final void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldRegisteredBy, true);
		ValidationComponentUtils.setMessageKey(textFieldRegisteredBy, "Ulykke.registert av");

		ValidationComponentUtils.setMandatory(dateChooserRegistrationDate, true);
		ValidationComponentUtils.setMessageKey(dateChooserRegistrationDate, "Ulykke.registreringsdato");

		ValidationComponentUtils.setMandatory(comboBoxJobFunction, true);
		ValidationComponentUtils.setMessageKey(comboBoxJobFunction, "Ulykke.funksjon");

		ValidationComponentUtils.setMandatory(radioButtonPersonalInjuryOver24, true);
		ValidationComponentUtils.setMessageKey(radioButtonPersonalInjuryOver24, "Ulykke.type");

		ValidationComponentUtils.setMandatory(dateChooserAccidentDate, true);
		ValidationComponentUtils.setMessageKey(dateChooserAccidentDate, "Ulykke.ulykkesdato");

		ValidationComponentUtils.setMandatory(textAreaDescription, true);
		ValidationComponentUtils.setMessageKey(textAreaDescription, "Ulykke.beskrivelse");

		ValidationComponentUtils.setMandatory(textAreaCause, true);
		ValidationComponentUtils.setMessageKey(textAreaCause, "Ulykke.årsak");

	}

	@Override
	protected final void initEditComponents(final WindowInterface aWindow) {
		textFieldRegisteredBy = ((AccidentViewHandler) viewHandler).getTextFieldRegisteredBy(presentationModel, search);
		dateChooserRegistrationDate = ((AccidentViewHandler) viewHandler)
				.getDateChooserRegistrationDate(presentationModel, search);
		comboBoxJobFunction = ((AccidentViewHandler) viewHandler).getComboBoxJobFunction(presentationModel);
		radioButtonPersonalInjuryOver24 = ((AccidentViewHandler) viewHandler)
				.getRadioButtonPersonalInjuryOver24(presentationModel);
		radioButtonPersonalInjuryUnder24 = ((AccidentViewHandler) viewHandler)
				.getRadioButtonPersonalInjuryUnder24(presentationModel);
		radioButtonPersonalInjuryNotAbsent = ((AccidentViewHandler) viewHandler)
				.getRadioButtonPersonalInjuryNotAbsent(presentationModel);
		radioButtonNotPersonalInjury = ((AccidentViewHandler) viewHandler)
				.getRadioButtonNotPersonalInjury(presentationModel);
		buttonAddParticipant = ((AccidentViewHandler) viewHandler).getButtonAddParticipant(aWindow, presentationModel);
		buttonDeleteParticipant = ((AccidentViewHandler) viewHandler).getButtonDeleteParticipant(aWindow,
				presentationModel);
		listParticipants = ((AccidentViewHandler) viewHandler).getListParticipants(presentationModel);
		dateChooserAccidentDate = ((AccidentViewHandler) viewHandler).getDateChooserAccidentDate(presentationModel);
		textFieldTime = ((AccidentViewHandler) viewHandler).getTextFieldTime(presentationModel);
		textAreaDescription = ((AccidentViewHandler) viewHandler).getTextAreaDescription(presentationModel);
		textAreaCause = ((AccidentViewHandler) viewHandler).getTextAreaCause(presentationModel);
		checkBoxLeader = ((AccidentViewHandler) viewHandler).getCheckBoxLeader(presentationModel);
		checkBoxPolice = ((AccidentViewHandler) viewHandler).getCheckBoxPolice(presentationModel);
		checkBoxArbeidstilsynet = ((AccidentViewHandler) viewHandler).getCheckBoxArbeidstilsynet(presentationModel);
		checkBoxSocialSecurity = ((AccidentViewHandler) viewHandler).getCheckBoxSocialSecurity(presentationModel);
		labelLink = ((AccidentViewHandler) viewHandler).getLabelLink(aWindow);
		buttonPrint = ((AccidentViewHandler) viewHandler).getButtonPrint(aWindow, presentationModel);
		textAreaPreventiveActionComment = ((AccidentViewHandler) viewHandler)
				.getTextAreaPreventiveActionComment(presentationModel);
		comboBoxResponsible = ((AccidentViewHandler) viewHandler).getComboBoxResponsible(presentationModel);
		textFieldAbsentDays = ((AccidentViewHandler) viewHandler).getTextFieldAbsentDays(presentationModel);
		textFieldNumberOfOwnEmployess = ((AccidentViewHandler) viewHandler).getTextFieldNumberOfOwnEmployees(presentationModel);
		dateChooserDoneDate = ((AccidentViewHandler) viewHandler).getDateChooserDoneDate(presentationModel);
		comboBoxStatus = ((AccidentViewHandler) viewHandler).getComboBoxStatus(presentationModel);
	}

	public final String getDialogName() {
		return "EditAccidentView";
	}

	public final String getHeading() {
		return "Ulykke";
	}

	public WindowInterface buildWindow() {
		WindowInterface windowDialog = InternalFrameBuilder.buildInternalFrame("Registrere ulykke/hendelse",
				((AccidentViewHandler) viewHandler).getRegisterWindowSize(), false);
		windowDialog.add(buildPanel(windowDialog), BorderLayout.CENTER);
		return windowDialog;
	}

	public void cleanUp() {
	}

	public String getTitle() {
		return "Registrere hendelse/ulykke";
	}

	public void initWindow() {
	}

	public boolean useDispose() {
		return false;
	}

}
