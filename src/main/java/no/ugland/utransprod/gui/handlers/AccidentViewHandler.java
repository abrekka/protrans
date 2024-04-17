package no.ugland.utransprod.gui.handlers;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditAccidentParticipantView;
import no.ugland.utransprod.gui.edit.EditAccidentView;
import no.ugland.utransprod.gui.model.AccidentModel;
import no.ugland.utransprod.gui.model.AccidentParticipantModel;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.model.AccidentParticipant;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.MailConfig;
import no.ugland.utransprod.util.report.ReportViewer;

import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.toedter.calendar.JDateChooser;

public class AccidentViewHandler extends
		DefaultAbstractViewHandler<Accident, AccidentModel> {

	private static final long serialVersionUID = 1L;

	private final ArrayListModel participantList;

	private final SelectionInList participantSelectionList;

	private JButton buttonDeleteParticipant;

	private MailConfig mailConfig;

	private Login login;
	private List<DeviationStatus> deviationStatusList;
	private ManagerRepository managerRepository;

	@Inject
	public AccidentViewHandler(final Login aLogin,
			final ManagerRepository aManagerRepository) {
		super("Ulykker", aManagerRepository.getAccidentManager(), aLogin.getUserType(), true);
		managerRepository=aManagerRepository;
		login = aLogin;
		participantList = new ArrayListModel();
		participantSelectionList = new SelectionInList(
				(ListModel) participantList);
		participantSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new EmptySelectionhandler());
		initMailAndStatusList();

	}

	private void initMailAndStatusList() {
		mailConfig = new MailConfig("Hendelse_ulykke", "Hendelse/ulykke",
				"Hendelse/ulykke", "");
		
		deviationStatusList = managerRepository.getDeviationStatusManager()
		.findAllForDeviation();
deviationStatusList.add(0, null);
	}

	public JTextField getTextFieldRegisteredBy(
			PresentationModel presentationModel, boolean searching) {
		if (!searching
				&& presentationModel
						.getBufferedValue(AccidentModel.PROPERTY_ACCIDENT_ID) == null
				&& login.getApplicationUser().getGroupUser().equalsIgnoreCase(
						"Nei")) {
			presentationModel.setBufferedValue(
					AccidentModel.PROPERTY_REGISTERED_BY, login
							.getApplicationUser().getFullName());
		}
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_REGISTERED_BY));
		textField.setName("TextFieldRegisteredBy");
		return textField;
	}

	public JTextField getTextFieldTime(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_TIME));
		textField.setName("TextFieldTime");
		return textField;
	}
	
	public JTextField getTextFieldAbsentDays(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_ABSENT_DAYS_STRING));
		textField.setName("TextFieldAbsentDays");
		return textField;
	}
	
	public JTextField getTextFieldNumberOfOwnEmployees(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_NUMBER_OF_OWN_EMPLOYEES_STRING));
		textField.setName("TextFieldNumberOfOwnEmployees");
		return textField;
	}

	public JTextArea getTextAreaDescription(PresentationModel presentationModel) {
		JTextArea textArea = BasicComponentFactory
				.createTextArea(presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_ACCIDENT_DESCRIPTION));
		textArea.setName("TextAreaDescription");
		return textArea;
	}

	public JTextArea getTextAreaCause(PresentationModel presentationModel) {
		JTextArea textArea = BasicComponentFactory
				.createTextArea(presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_ACCIDENT_CAUSE));
		textArea.setName("TextAreaCause");
		return textArea;
	}

	public JCheckBox getCheckBoxLeader(PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_REPORTED_LEADER_BOOL),
						"Meldt til leder prod, HMS-leder og hovedvernombud");
		checkBox.setName("CheckBoxLeader");
		return checkBox;

	}

	public JCheckBox getCheckBoxPolice(PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_REPORTED_POLICE_BOOL),
						"Meldt til politi");
		checkBox.setName("CheckBoxPolice");
		return checkBox;

	}
	
	public JCheckBox getCheckBoxArbeidstilsynet(PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_REPORTED_ARBEIDSTILSYNET_BOOL),
						"Meldt til arbeidstilsynet");
		checkBox.setName("CheckBoxArbeidstilsynet");
		return checkBox;

	}

	public JCheckBox getCheckBoxSocialSecurity(
			PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_REPORTED_SOCIAL_SECURITY_BOOL),
						"Meldt til trygdekontor");
		checkBox.setName("CheckBoxSocialSecurity");
		return checkBox;

	}

	public JLabel getLabelLink(WindowInterface aWindow) {
		JLabel label = new JLabel("Blankett");
		label.addMouseListener(new LinkMouseListener(aWindow));
		label.setForeground(Color.BLUE);
		return label;
	}

	public JButton getButtonPrint(WindowInterface window,
			PresentationModel presentationModel) {
		JButton buttonPrint = new JButton(new PrintAction(window,
				presentationModel));
		buttonPrint.setIcon(IconEnum.ICON_PRINT.getIcon());
		buttonPrint.setName("ButtonPrint");
		return buttonPrint;
	}

	public JButton getButtonAddParticipant(WindowInterface aWindow,
			PresentationModel presentationModel) {
		JButton button = new JButton(new AddParticipantAction(aWindow,
				presentationModel));
		button.setName("ButtonAddParticipant");
		return button;
	}

	public JButton getButtonDeleteParticipant(WindowInterface aWindow,
			PresentationModel presentationModel) {
		buttonDeleteParticipant = new JButton(new DeleteParticipantAction(
				aWindow, presentationModel));
		buttonDeleteParticipant.setName("ButtonDeleteParticipant");
		buttonDeleteParticipant.setEnabled(false);
		return buttonDeleteParticipant;
	}

	public JList getListParticipants(PresentationModel presentationModel) {
		participantList.clear();
		participantList.addAll((ArrayListModel) presentationModel
				.getBufferedValue(AccidentModel.PROPERTY_PARTICIPANT_LIST));

		JList listParticipants = BasicComponentFactory
				.createList(participantSelectionList);
		listParticipants.setEnabled(hasWriteAccess());
		listParticipants.setName("ListParticipant");
		return listParticipants;
	}

	public JDateChooser getDateChooserRegistrationDate(
			PresentationModel presentationModel, boolean searching) {
		JDateChooser dateChooser = new JDateChooser();
		if (!searching
				&& presentationModel
						.getBufferedValue(AccidentModel.PROPERTY_REGISTRATION_DATE) == null) {
			presentationModel.setBufferedValue(
					AccidentModel.PROPERTY_REGISTRATION_DATE, Util
							.getCurrentDate());
			presentationModel.triggerCommit();
		}
		PropertyConnector conn = new PropertyConnector(
				dateChooser,
				"date",
				presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_REGISTRATION_DATE),
				"value");
		conn.updateProperty1();

		dateChooser.setName("DateChooserRegistrationDate");
		return dateChooser;
	}
	
	public JDateChooser getDateChooserDoneDate(
			PresentationModel presentationModel) {
		JDateChooser dateChooser = new JDateChooser();
		PropertyConnector conn = new PropertyConnector(
				dateChooser,
				"date",
				presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_DONE_DATE),
				"value");
		conn.updateProperty1();

		dateChooser.setName("DateChooserDoneDate");
		return dateChooser;
	}

	public JDateChooser getDateChooserAccidentDate(
			PresentationModel presentationModel) {
		JDateChooser dateChooser = new JDateChooser();
		PropertyConnector conn = new PropertyConnector(
				dateChooser,
				"date",
				presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_ACCIDENT_DATE),
				"value");
		conn.updateProperty1();

		dateChooser.setName("DateChooserAccidentDate");
		return dateChooser;
	}

	public JComboBox getComboBoxJobFunction(PresentationModel presentationModel) {
		JComboBox comboBox = Util.getComboBoxJobFunction(false,
				presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_JOB_FUNCTION));
		comboBox.setName("ComboBoxJobFunction");
		return comboBox;
	}

	public JCheckBox getCheckBoxPersonalInjuryOver24(
			PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_PERSONAL_INJURY_OVER_24_BOOLEAN), "TF1: Personskader med fravær over 24t");
		checkBox.setName("RadioButtonPersonalInjuryOver24");
		return checkBox;
	}

	public JCheckBox getCheckBoxPersonalInjuryUnder24(
			PresentationModel presentationModel) {
		JCheckBox checkBox= BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_PERSONAL_INJURY_UNDER_24_BOOLEAN),
						 "TF2:Personskader med fravær under 24t");
		checkBox.setName("RadioButtonPersonalInjuryUnder24");
		return checkBox;
	}
	
	public JCheckBox getCheckBoxPersonalInjuryNotAbsent(
			PresentationModel presentationModel) {
		JCheckBox checkBox= BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_PERSONAL_INJURY_NOT_ABSENT_BOOLEAN),
						 "TF3: Personskader uten fravær");
		checkBox.setName("RadioButtonPersonalInjuryNotAbsent");
		return checkBox;
	}
	
	public JCheckBox getCheckBoxNotPersonalInjury(
			PresentationModel presentationModel) {
		JCheckBox checkBox= BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_NOT_PERSONAL_INJURY_BOOLEAN),
						 "TF4:Materielle skader, nestenulykke, farlig forhold");
		checkBox.setName("RadioButtonNotPersonalInjury");
		return checkBox;
	}

	@Override
	public final CheckObject checkDeleteObject(final Accident object) {
		return null;
	}

	@Override
	public final CheckObject checkSaveObject(final AccidentModel object,
			final PresentationModel presentationModel,
			final WindowInterface window1) {
		return null;
	}

	@Override
	public final String getAddRemoveString() {
		return "ulykke";
	}

	@Override
	public final String getClassName() {
		return "Accident";
	}

	@Override
	protected final AbstractEditView<AccidentModel, Accident> getEditView(
			final AbstractViewHandler<Accident, AccidentModel> handler,
			final Accident accident, final boolean searching) {
		overviewManager.lazyLoad(accident, new LazyLoadEnum[][] { {
				LazyLoadEnum.ACCIDENT_PARTICIPANTS, LazyLoadEnum.NONE } });
		return new EditAccidentView(searching, new AccidentModel(accident),
				this);

	}

	@Override
	public final Accident getNewObject() {
		return new Accident();
	}

	@Override
	public final TableModel getTableModel(final WindowInterface window1) {
		return new AccidentTableModel(objectSelectionList);
	}

	@Override
	public final String getTableWidth() {
		return "200dlu";
	}

	@Override
	public final String getTitle() {
		return "Ulykker";
	}

	@Override
	public final Dimension getWindowSize() {
		return new Dimension(1200, 600);
	}

	@Override
	public final Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Ulykke");
	}

	@Override
	public final void setColumnWidth(final JXTable table) {
		// registert av
		table.getColumnExt(0).setPreferredWidth(100);
		// dato
		table.getColumnExt(1).setPreferredWidth(80);
		// avdeling
		table.getColumnExt(2).setPreferredWidth(80);
		// personskade over 24
		table.getColumnExt(3).setPreferredWidth(50);
		// personskade under 24
				table.getColumnExt(4).setPreferredWidth(50);
				// personskade uten fravær
				table.getColumnExt(5).setPreferredWidth(50);
				// ikke personskde
				table.getColumnExt(6).setPreferredWidth(50);
		// dato hendelse
		table.getColumnExt(7).setPreferredWidth(110);
		// leder
		table.getColumnExt(8).setPreferredWidth(50);
		// arb.tilsynet
		table.getColumnExt(9).setPreferredWidth(80);
		// trygdekontoret
		table.getColumnExt(10).setPreferredWidth(100);
		// arbeidstilsynet
				table.getColumnExt(11).setPreferredWidth(100);
	}

	public Dimension getRegisterWindowSize() {
		return new Dimension(620, 800);
	}

	private class AddParticipantAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window1;

		private PresentationModel presentationModel;

		public AddParticipantAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Legg til involvert");
			window1 = aWindow;
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			AccidentParticipantModel accidentParticipantModel = new AccidentParticipantModel(
					new AccidentParticipant(null,
							((AccidentModel) presentationModel.getBean())
									.getObject(), null, null, null));
			EditAccidentParticipantView editAccidentParticipantView = new EditAccidentParticipantView(
					false, accidentParticipantModel,
					new AccidentParticipantViewHandler(userType));
			Util.showEditViewable(editAccidentParticipantView, window1);

			if (!editAccidentParticipantView.isCanceled()) {
				ArrayListModel listModel = (ArrayListModel) presentationModel
						.getBufferedValue(AccidentModel.PROPERTY_PARTICIPANT_LIST);
				listModel.add(accidentParticipantModel.getObject());
				presentationModel.setBufferedValue(
						AccidentModel.PROPERTY_PARTICIPANT_LIST, listModel);
				participantList.clear();
				participantList.addAll(listModel);
			}
		}
	}

	private class DeleteParticipantAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window1;

		private PresentationModel presentationModel;

		public DeleteParticipantAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Slett involvert");
			window1 = aWindow;
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			if (Util.showConfirmDialog(window1.getComponent(), "Slette?",
					"Vil du virkelig slette involvert?")) {
				AccidentParticipant participant = (AccidentParticipant) participantSelectionList
						.getSelection();
				ArrayListModel list = (ArrayListModel) presentationModel
						.getBufferedValue(AccidentModel.PROPERTY_PARTICIPANT_LIST);
				list.remove(participant);
				presentationModel.setBufferedValue(
						AccidentModel.PROPERTY_PARTICIPANT_LIST, list);
				participantList.remove(participant);
			}

		}
	}

	private class EmptySelectionhandler implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			if (buttonDeleteParticipant != null) {
				buttonDeleteParticipant.setEnabled(participantSelectionList
						.hasSelection());
			}

		}

	}

	private class LinkMouseListener implements MouseListener {
		private WindowInterface window1;

		public LinkMouseListener(WindowInterface aWindow) {
			window1 = aWindow;
		}

		public void mouseClicked(MouseEvent e) {
			String link = ApplicationParamUtil.findParamByName("nav_blankett");
			if (link != null) {
				try {
					Desktop.browse(new URL(link));
				} catch (Exception e1) {
					throw new ProTransRuntimeException(e1.getMessage());
				}
			}

		}

		public void mouseEntered(MouseEvent e) {
			window1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		}

		public void mouseExited(MouseEvent e) {
			window1
					.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

	private class PrintAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		private PresentationModel presentationModel;

		/**
		 * @param aWindow
		 * @param aPresentationModel
		 */
		public PrintAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Skriv ut...");
			presentationModel = aPresentationModel;
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.runInThreadWheel(window.getRootPane(), new Printer(window,
					presentationModel), null);

		}
	}

	private class Printer implements Threadable {
		private WindowInterface owner;

		private PresentationModel presentationModel;

		/**
		 * @param aOwner
		 * @param aPresentationModel
		 */
		public Printer(WindowInterface aOwner,
				PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
			owner = aOwner;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
		 */
		public void doWhenFinished(Object object) {
			if (object != null) {
				Util.showErrorDialog(owner, "Feil", object.toString());
			}
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
		 *      javax.swing.JLabel)
		 */
		public Object doWork(Object[] params, JLabel labelInfo) {
			String errorMsg = null;
			try {
				labelInfo.setText("Genererer utskrift...");
				generateReport(presentationModel);
			} catch (ProTransException e) {
				e.printStackTrace();
				errorMsg = e.getMessage();
			}
			return errorMsg;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(boolean enable) {
		}

	}

	private void generateReport(PresentationModel presentationModel)
			throws ProTransException {
		Accident accident = ((AccidentModel) presentationModel.getBean())
				.getObject();
		if (accident != null) {

			ReportViewer reportViewer = new ReportViewer("Hendelse/ulykke",
					mailConfig);
			InputStream iconStream = getClass().getClassLoader()
					.getResourceAsStream(IconEnum.ICON_CHECKED.getIconPath());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("checked", iconStream);
			List<Accident> accidentList = new ArrayList<Accident>();
			accidentList.add(accident);
			reportViewer.generateProtransReportFromBeanAndShow(accidentList,
					"Hendelse/ulykke", ReportEnum.ACCIDENT, params, null,
					window, true);
		}
	}

	private static final class AccidentTableModel extends AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Registert av", "Dato",
				"Avdeling", "TF1","TF2","TF3","TF4", "Dato hendelse", "Leder",
				"Politiet", "Trygdekontor","Arbeidstilsynet","Antall egne ansatte" };

		/**
		 * @param listModel
		 */
		AccidentTableModel(ListModel listModel) {
			super(listModel, COLUMNS);
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			Accident accident = (Accident) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return accident.getRegisteredBy();
			case 1:

				return Util.formatDate(accident.getRegistrationDate(),
						Util.SHORT_DATE_FORMAT);
			case 2:
				return accident.getJobFunction();
			case 3:
				return accident.getPersonalInjuryOver24Bool();
			case 4:
				return accident.getPersonalInjuryUnder24Bool();
			case 5:
				return accident.getPersonalInjuryNotAbsentBool();
			case 6:
				return accident.getNotPersonalInjuryBool();
			case 7:
				return accident.getAccidentDateAndTime();
			case 8:
				return accident.getReportedLeaderBool();
			case 9:
				return accident.getReportedPoliceBool();
			case 10:
				return accident.getReportedSocialSecurityBool();
			case 11:
				return accident.getReportedArbeidstilsynetBool();
			case 12:
				return accident.getNumberOfOwnEmployees();
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
			case 7:
			case 1:
				return String.class;
			case 2:
				return JobFunction.class;
			case 3:
			case 4:
			case 5:
			case 6:
			case 8:
			case 9:
			case 10:
			case 11:
				return Boolean.class;
			case 12:
				return Integer.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	public JTextArea getTextAreaPreventiveActionComment(
			PresentationModel presentationModel) {
		JTextArea textArea = BasicComponentFactory
				.createTextArea(presentationModel
						.getBufferedModel(AccidentModel.PROPERTY_PREVENTIVE_ACTION_COMMENT));
		textArea.setName("TextAreaPreventiveActionComment");
		return textArea;
	}

	public JComboBox getComboBoxResponsible(PresentationModel presentationModel) {

		JComboBox comboBox = Util.getComboBoxUsers(presentationModel
				.getBufferedModel(AccidentModel.PROPERTY_RESPONSIBLE), true);
		comboBox.setName("ComboBoxResponsible");

		if (presentationModel
				.getBufferedValue(AccidentModel.PROPERTY_ACCIDENT_ID) == null) {

			String hmsLeader = ApplicationParamUtil
					.findParamByName("hms_leder");
			if (hmsLeader != null) {
				presentationModel.setBufferedValue(
						AccidentModel.PROPERTY_RESPONSIBLE, hmsLeader);
			}
		}
		return comboBox;
	}
	
	private void initDeviationStatusList() {
		deviationStatusList.clear();

			deviationStatusList.addAll(managerRepository.getDeviationStatusManager().findAllForAccident());
			deviationStatusList.add(0, null);
	}
	
	public JComboBox getComboBoxStatus(PresentationModel presentationModel) {
		initDeviationStatusList();
		DeviationStatus status = (DeviationStatus) presentationModel
				.getBufferedValue(AccidentModel.PROPERTY_DEVIATION_STATUS);
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(
						deviationStatusList,
						presentationModel
								.getBufferedModel(AccidentModel.PROPERTY_DEVIATION_STATUS)));
			comboBox.setEnabled(hasWriteAccess());
		comboBox.setName("ComboBoxStatus");
		/*presentationModel.getBufferedModel(
				DeviationModel.PROPERTY_DEVIATION_STATUS)
				.addValueChangeListener(
						new StatusListener(presentationModel));*/

		return comboBox;
	}



}
