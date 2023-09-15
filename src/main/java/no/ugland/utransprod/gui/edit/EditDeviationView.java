package no.ugland.utransprod.gui.edit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.OrderArticleView;
import no.ugland.utransprod.gui.OrderCostsView;
import no.ugland.utransprod.gui.Viewer;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.OrderCostsViewHandler;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.validators.DeviationValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;
import no.ugland.utransprod.util.InternalFrameBuilder;
import no.ugland.utransprod.util.Util;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.toedter.calendar.JDateChooser;

/**
 * Klasse som håndterer editering av avvik
 * 
 * @author atle.brekka
 */
public class EditDeviationView extends AbstractEditView<DeviationModel, Deviation> implements Viewer {

	private JTextField textFieldUserName;

	private JComboBox comboBoxOwnFunction;

	private JComboBox comboBoxDeviationFunction;

	private JTextField textFieldCustomerNr;

	private JTextField textFieldOrderNr;
	private JTextField textFieldOrderComplete;
	private JTextField textFieldTransportDate;

	private JComboBox comboBoxStatus;

	private JDateChooser dateChooserProcedureCheck;

	private JComboBox comboBoxProductArea;

	private JComboBox comboBoxCategory;

	private JComboBox comboBoxPreventiveAction;

	private JTextField textFieldCustomerName;

	private JButton buttonDeviationOk;

	private boolean okOnly = false;

	private JButton buttonPrint;

	private JTextField textFieldId;

	private JButton buttonAddComment;

	private JButton buttonEditComment;

	private JList listComments;

	private JCheckBox checkBoxPostShipment;

	private boolean addInternalCost = false;

	private JButton buttonAddPreventiveAction;

	private JCheckBox checkBoxDoAssembly;

	JTabbedPane tabbedPane;

	private JComboBox comboBoxResponsible;

	private JDateChooser dateChooserFromDate;

	private JDateChooser dateChooserToDate;

	private JCheckBox checkBoxChecked;

	private JRadioButton radioButtonInternal;
	private JRadioButton radioButtonEntrepenoer;

	private JRadioButton radioButtonCustomer;

	private JRadioButton radioButtonAssembly;

	private JRadioButton radioButtonTransport;

	private JLabel labelRegistrationDate;

	private JTextField textFieldProjectNr;
	private JTextField textFieldDateClosed;
	private JTextField textFieldCsId;
	private boolean brukOrdrelinjelinjer;

	/**
	 * @param searchDialog
	 * @param object
	 * @param aViewHandler
	 * @param onlyOk
	 * @param doAddInternalCost
	 */
	public EditDeviationView(final boolean searchDialog, final AbstractModel<Deviation, DeviationModel> object,
			final AbstractViewHandler<Deviation, DeviationModel> aViewHandler, final boolean onlyOk,
			final boolean doAddInternalCost, boolean brukOrdrelinjelinjer) {
		super(searchDialog, object, aViewHandler);

		addInternalCost = doAddInternalCost;
		okOnly = onlyOk;
		this.brukOrdrelinjelinjer = brukOrdrelinjelinjer;

	}

	/**
	 * Bygger detaljpanel
	 * 
	 * @return panel
	 */
	private JPanel buildDetailsPanel() {
		FormLayout layout = new FormLayout("40dlu,p,3dlu,100dlu,3dlu,p,3dlu,100dlu",
				"p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Initiert av:", cc.xy(2, 1));
		builder.add(buildInitiatedPanel(), cc.xyw(4, 1, 5));
		builder.addLabel("Id:", cc.xy(2, 3));
		builder.add(textFieldId, cc.xy(4, 3));
		builder.addLabel("Registert av:", cc.xy(2, 5));
		builder.add(textFieldUserName, cc.xy(4, 5));
		builder.addLabel("Behandlingsansvarlig:", cc.xy(2, 7));
		builder.add(comboBoxResponsible, cc.xy(4, 7));
		builder.addLabel("Ordrenr:", cc.xy(2, 9));
		builder.add(textFieldOrderNr, cc.xy(4, 9));
		builder.addLabel("Ordre komplett:", cc.xy(2, 11));
		builder.add(textFieldOrderComplete, cc.xy(4, 11));
		builder.addLabel("Transportdato:", cc.xy(2, 13));
		builder.add(textFieldTransportDate, cc.xy(4, 13));
		builder.addLabel("Prosjektnr:", cc.xy(2, 15));
		builder.add(textFieldProjectNr, cc.xy(4, 15));
		builder.addLabel("Kundenr:", cc.xy(2, 17));
		builder.add(textFieldCustomerNr, cc.xy(4, 17));
		builder.addLabel("Kundenavn:", cc.xy(2, 19));
		builder.add(textFieldCustomerName, cc.xy(4, 19));

		builder.addLabel("Korrigerende tiltak:", cc.xy(2, 21));
		builder.add(comboBoxPreventiveAction, cc.xy(4, 21));
		if (!search) {
			builder.add(ButtonBarFactory.buildLeftAlignedBar(buttonAddPreventiveAction), cc.xyw(6, 21, 3));
		}
		builder.add(buildCheckBoxPanel(), cc.xyw(2, 23, 7));

		builder.addLabel("Produktområde:", cc.xy(6, 3));
		builder.add(comboBoxProductArea, cc.xy(8, 3));
		builder.addLabel("Egen funksjon:", cc.xy(6, 5));
		builder.add(comboBoxOwnFunction, cc.xy(8, 5));
		builder.addLabel("Avviksfunksjon:", cc.xy(6, 7));
		builder.add(comboBoxDeviationFunction, cc.xy(8, 7));
		builder.addLabel("Kategori:", cc.xy(6, 9));
		builder.add(comboBoxCategory, cc.xy(8, 9));
		builder.addLabel("Status:", cc.xy(6, 11));
		builder.add(comboBoxStatus, cc.xy(8, 11));
		builder.addLabel("Prosedyresjekk:", cc.xy(6, 13));
		builder.add(dateChooserProcedureCheck, cc.xy(8, 13));
		builder.addLabel("Lukket:", cc.xy(6, 15));
		builder.add(textFieldDateClosed, cc.xy(8, 15));
		builder.addLabel("CS-ID:", cc.xy(6, 17));
		builder.add(textFieldCsId, cc.xy(8, 17));

		return builder.getPanel();
	}

	/**
	 * Bygger panel med radioknapper for initiert av
	 * 
	 * @return panel
	 */
	private JPanel buildInitiatedPanel() {
		FormLayout layout = new FormLayout("p,3dlu,p,3dlu,p,3dlu,p,3dlu,p", "p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.add(radioButtonInternal, cc.xy(1, 1));
		builder.add(radioButtonCustomer, cc.xy(3, 1));
		builder.add(radioButtonAssembly, cc.xy(5, 1));
		builder.add(radioButtonTransport, cc.xy(7, 1));
		builder.add(radioButtonEntrepenoer, cc.xy(9, 1));
		return builder.getPanel();
	}

	/**
	 * Lager panel med sjekkbokser
	 * 
	 * @return panel
	 */
	private JPanel buildCheckBoxPanel() {
		FormLayout layout = new FormLayout("p,3dlu,p,3dlu,p", "p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.add(checkBoxPostShipment, cc.xy(1, 1));
		builder.add(checkBoxDoAssembly, cc.xy(3, 1));
		builder.add(checkBoxChecked, cc.xy(5, 1));

		return builder.getPanel();
	}

	/**
	 * Bygger artikkelpanel
	 * 
	 * @param window
	 * @return panel
	 */
	private JPanel buildArticlePanel(final WindowInterface window) {
		FormLayout layout = new FormLayout("p,3dlu,p", "p,fill:p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		OrderArticleView<Deviation, DeviationModel> orderArticleView = new OrderArticleView<Deviation, DeviationModel>(
				((DeviationViewHandler) viewHandler).getOrderArticleViewHandler(presentationModel, search, window,
						brukOrdrelinjelinjer),
				true, false);

		builder.addLabel("Artikler:", cc.xy(1, 1));
		builder.add(orderArticleView.buildPanel(window), cc.xy(1, 2));
		return builder.getPanel();
	}

	@Override
	protected final JComponent buildEditPanel(final WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,fill:p,10dlu", "10dlu,p,3dlu,top:p,3dlu,fill:50dlu:grow,5dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
//		 PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildDetailsPanel(), cc.xy(2, 2));
		if (!search) {
			builder.add(buildArticleCostPanel(window), cc.xy(2, 4));
			builder.add(buildCommentsPanel(), cc.xy(2, 6));
		} else {
			builder.add(buildSearchDatePanel(), cc.xy(2, 6));
		}

		builder.add(buildButtonPanel(), cc.xy(2, 8));

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		if (screenSize.getHeight() < 860) {
			return new JScrollPane(new IconFeedbackPanel(validationResultModel, builder.getPanel()),
					ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		}

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Lager knappepanel
	 * 
	 * @return panel
	 */
	private JPanel buildButtonPanel() {
		FormLayout layout;
		if (search) {
			layout = new FormLayout("p,3dlu,100dlu,p,fill:p", "p");
		} else if (!okOnly) {
			layout = new FormLayout("p,3dlu,75dlu,p,fill:p", "p");
		} else {
			layout = new FormLayout("p,3dlu,140dlu,p,fill:p", "p");
		}
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Opprettet:", cc.xy(1, 1));
		builder.add(labelRegistrationDate, cc.xy(3, 1));

		if (search) {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel), cc.xy(5, 1));
		} else if (!okOnly) {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonPrint, buttonSave, buttonCancel), cc.xy(5, 1));
		} else {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonDeviationOk), cc.xy(5, 1));
		}

		return builder.getPanel();
	}

	/**
	 * Bygger artikkel- og kostnadspanel
	 * 
	 * @param window
	 * @return panel
	 */
	private JPanel buildArticleCostPanel(final WindowInterface window) {
		FormLayout layout = new FormLayout("p,3dlu,p", "150dlu:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildArticlePanel(window), cc.xy(1, 1));
		builder.add(buildCostDeviationTabbedPanel(window), cc.xy(3, 1));

		return builder.getPanel();
	}

	/**
	 * Bygger kommentarpanel
	 * 
	 * @return panel
	 */
	private JPanel buildCommentsPanel() {
		FormLayout layout = new FormLayout("300dlu,3dlu,p", "p,3dlu,fill:50dlu:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Beskrivelse av avvik og strakstiltak:", cc.xy(1, 1));
		builder.add(new JScrollPane(listComments), cc.xy(1, 3));

		builder.add(buildCommentButtonPanel(), cc.xy(3, 3));

		return builder.getPanel();
	}

	private JPanel buildCommentButtonPanel() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonAddComment);
		builder.addRelatedGap();
		builder.addGridded(buttonEditComment);
		return builder.getPanel();
	}

	/**
	 * Bygger panel for å søke fra og til dato
	 * 
	 * @return panel
	 */
	private JPanel buildSearchDatePanel() {
		FormLayout layout = new FormLayout("p,3dlu,70dlu,3dlu,70dlu", "p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Registreringsdato:", cc.xy(1, 1));
		builder.add(dateChooserFromDate, cc.xy(3, 1));
		builder.add(dateChooserToDate, cc.xy(5, 1));

		return builder.getPanel();
	}

	/**
	 * Bygger kostnadspanel
	 * 
	 * @param window
	 * @return panel
	 */
	private Component buildCostPanel(final WindowInterface window) {
		FormLayout layout = new FormLayout("p", "fill:120dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		OrderCostsViewHandler orderCostsViewHandler = ((DeviationViewHandler) viewHandler)
				.getOrderCostsViewHandler(presentationModel, addInternalCost, true);

		OrderCostsView orderCostsView = new OrderCostsView(orderCostsViewHandler, false);

		try {
			builder.add(orderCostsView.buildPanel(window), cc.xy(1, 1));
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
		return builder.getPanel();
	}

	/**
	 * Lager tabbed panel med kostnader og andre avvik
	 * 
	 * @param window
	 * @return panel
	 */
	private Component buildCostDeviationTabbedPanel(final WindowInterface window) {
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Kostnader", buildCostPanel(window));
		tabbedPane.add("Andre avvik", buildDeviationPanel(window));
		tabbedPane.setTitleAt(1,
				"Andre avvik(" + ((DeviationViewHandler) viewHandler).getNumberOfOtherDeviations() + ")");
		((DeviationViewHandler) viewHandler).addOrderChangeListener(new OrderChangeListener());
		return tabbedPane;
	}

	/**
	 * Lager panel med andre avvik for samme ordre
	 * 
	 * @param window
	 * @return panel
	 */
	private JPanel buildDeviationPanel(final WindowInterface window) {
		return ((DeviationViewHandler) viewHandler).getDeviationPane(window, presentationModel,
				((DeviationModel) presentationModel.getBean()).getObject());
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected final Validator getValidator(final DeviationModel object, boolean search) {
		return new DeviationValidator(object, search);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected final void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldUserName, true);
		ValidationComponentUtils.setMessageKey(textFieldUserName, "Avvik.navn");

		ValidationComponentUtils.setMandatory(textFieldOrderNr, true);
		ValidationComponentUtils.setMessageKey(textFieldOrderNr, "Avvik.ordrenr");

		ValidationComponentUtils.setMandatory(radioButtonInternal, true);
		ValidationComponentUtils.setMessageKey(radioButtonInternal, "Avvik.initiert av");

		ValidationComponentUtils.setMandatory(comboBoxResponsible, true);
		ValidationComponentUtils.setMessageKey(comboBoxResponsible, "Avvik.behandlingsansvarlig");

		ValidationComponentUtils.setMandatory(comboBoxDeviationFunction, true);
		ValidationComponentUtils.setMessageKey(comboBoxDeviationFunction, "Avvik.avviksfunksjon");

		ValidationComponentUtils.setMandatory(comboBoxCategory, true);
		ValidationComponentUtils.setMessageKey(comboBoxCategory, "Avvik.kategori");

		ValidationComponentUtils.setMandatory(comboBoxStatus, true);
		ValidationComponentUtils.setMessageKey(comboBoxStatus, "Avvik.status");

		ValidationComponentUtils.setMandatory(listComments, true);
		ValidationComponentUtils.setMessageKey(listComments, "Avvik.kommentar");

	}

	@Override
	protected final void initEditComponents(final WindowInterface window1) {

		textFieldId = ((DeviationViewHandler) viewHandler).getTextFieldId(presentationModel, search);

		textFieldUserName = ((DeviationViewHandler) viewHandler).getTextFieldUserName(presentationModel, search);

		comboBoxOwnFunction = ((DeviationViewHandler) viewHandler).getComboBoxOwnFunction(search, presentationModel);
		comboBoxDeviationFunction = ((DeviationViewHandler) viewHandler)
				.getComboBoxDeviationFunction(presentationModel);
		textFieldCustomerNr = ((DeviationViewHandler) viewHandler).getTextFieldCustomerNr(presentationModel);
		textFieldCustomerName = ((DeviationViewHandler) viewHandler).getTextFieldCustomerName(presentationModel,
				search);
		textFieldOrderNr = ((DeviationViewHandler) viewHandler).getTextFieldOrderNr(presentationModel, search);
		textFieldOrderComplete = ((DeviationViewHandler) viewHandler).getTextFieldOrderComplete(presentationModel,
				search);
		textFieldTransportDate = ((DeviationViewHandler) viewHandler).getTextFieldTransportDate(presentationModel,
				search);
		comboBoxStatus = ((DeviationViewHandler) viewHandler).getComboBoxStatus(presentationModel);
		dateChooserProcedureCheck = ((DeviationViewHandler) viewHandler)
				.getDateChooserProcedureCheck(presentationModel);

		comboBoxProductArea = ((DeviationViewHandler) viewHandler).getComboBoxProductArea(presentationModel, search);
		comboBoxCategory = ((DeviationViewHandler) viewHandler).getComboBoxFunctionCategory(presentationModel);

		comboBoxPreventiveAction = ((DeviationViewHandler) viewHandler).getComboBoxPreventiveAction(presentationModel);

		if (((DeviationViewHandler) viewHandler).isUserSet()) {
			buttonSave.setEnabled(true);
		}

		buttonDeviationOk = ((DeviationViewHandler) viewHandler).getButtonOk(window1, validationResultModel,
				presentationModel);
		buttonPrint = ((DeviationViewHandler) viewHandler).getButtonPrint(window1, presentationModel);

		buttonAddComment = ((DeviationViewHandler) viewHandler).getButtonAddComment(window1, presentationModel);
		listComments = ((DeviationViewHandler) viewHandler).getListComments(presentationModel);

		checkBoxPostShipment = ((DeviationViewHandler) viewHandler).getCheckBoxPostShipment(presentationModel, search);
		buttonAddPreventiveAction = ((DeviationViewHandler) viewHandler).getButtonAddPreventiveAction(presentationModel,
				window1);
		checkBoxDoAssembly = ((DeviationViewHandler) viewHandler).getCheckBoxDoAssembly(presentationModel);

		comboBoxResponsible = ((DeviationViewHandler) viewHandler).getComboBoxResponsible(presentationModel);
		dateChooserFromDate = ((DeviationViewHandler) viewHandler).getDateChooserFrom(presentationModel);
		dateChooserToDate = ((DeviationViewHandler) viewHandler).getDateChooserTo(presentationModel);
		checkBoxChecked = ((DeviationViewHandler) viewHandler).getCheckBoxChecked(presentationModel, search);
		radioButtonInternal = ((DeviationViewHandler) viewHandler).getRadioButtonInternal(presentationModel);
		radioButtonEntrepenoer = ((DeviationViewHandler) viewHandler).getRadioButtonEntrepenoer(presentationModel);
		radioButtonCustomer = ((DeviationViewHandler) viewHandler).getRadioButtonCustomer(presentationModel);
		radioButtonAssembly = ((DeviationViewHandler) viewHandler).getRadioButtonAssembly(presentationModel);
		radioButtonTransport = ((DeviationViewHandler) viewHandler).getRadioButtonTransport(presentationModel);

		labelRegistrationDate = ((DeviationViewHandler) viewHandler).getLabelRegistrationDate(presentationModel);

		textFieldProjectNr = ((DeviationViewHandler) viewHandler).getTextFieldProjectNr(presentationModel, search);

		buttonEditComment = ((DeviationViewHandler) viewHandler).getButtonEditComment(window1, presentationModel);

		textFieldDateClosed = ((DeviationViewHandler) viewHandler).getTextFieldDateClosed(presentationModel);

		textFieldCsId = ((DeviationViewHandler) viewHandler).getTextFieldCsId(presentationModel);
	}

	/**
	 * Håndterer endring av ordre
	 * 
	 * @author atle.brekka
	 */
	class OrderChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(final PropertyChangeEvent evt) {
			tabbedPane.setTitleAt(1,
					"Andre avvik(" + ((DeviationViewHandler) viewHandler).getNumberOfOtherDeviations() + ")");

		}

	}

	public final String getDialogName() {
		return "EditDeviationView";
	}

	public final String getHeading() {
		return "Avvik";
	}

	public WindowInterface buildWindow() {
		WindowInterface window = InternalFrameBuilder.buildInternalFrame("Registrere avvik",
				((DeviationViewHandler) viewHandler).getRegisterWindowSize(), false);
		window.add(buildPanel(window), BorderLayout.CENTER);
		return window;
	}

	public void cleanUp() {
		// TODO Auto-generated method stub

	}

	public String getTitle() {
		return "Avvik";
	}

	public void initWindow() {
		// TODO Auto-generated method stub

	}

	public boolean useDispose() {
		return true;
	}
}
