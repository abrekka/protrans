package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.ArticleTypeView;
import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditCommentView;
import no.ugland.utransprod.gui.edit.EditDeviationView;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.gui.model.ICostableModel;
import no.ugland.utransprod.gui.model.ListMultilineRenderer;
import no.ugland.utransprod.gui.model.OrderCommentModel;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.UpdateableListener;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.CommentTypeUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.MultiColPatternFilter;
import no.ugland.utransprod.util.SuperPatternFilter;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.MailConfig;
import no.ugland.utransprod.util.report.ReportViewer;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.validation.ValidationResultModel;
import com.toedter.calendar.JDateChooser;

/**
 * Hjelpeklasse for visning og editering av avvik
 * @author atle.brekka
 */
/**
 * @author atle.brekka
 */
public class DeviationViewHandler extends
		AbstractViewHandler<Deviation, DeviationModel> implements
		ViewHandlerExt<Deviation, DeviationModel> {
	private static final long serialVersionUID = 1L;

	private List<DeviationStatus> deviationStatusList;

	private ArrayListModel functionCategoryList;

	private List<ProductArea> productAreaList;

	private boolean userIsSet = false;

	private Order order;

	private SelectionInList orderLineSelectionList;

	private ArrayListModel orderLineList;

	private JButton buttonRemoveOrderLine;

	private JCheckBox checkBoxFilterDone;

	private JCheckBox checkBoxFilterOwn;

	private FilterPipeline filterPipelineOwnDone;

	private FilterPipeline filterPipelineOwn;

	private FilterPipeline filterPipelineDone;

	private boolean isForOrderInfo = false;

	private boolean registerNew = false;

	private JDateChooser dateChooserProcedureCheck;

	private final ArrayListModel orderComments;

	private ArrayListModel preventiveActionList;

	private static List<String> responsibleList;

	/**
	 * Gjeldende avvik dersom klassen skal vise andre avvik for samme ordre
	 */
	private Deviation currentDeviation;

	/**
	 * Antall andre avvik for samme ordre som gjeldende avvik
	 */
	private DeviationViewHandler deviationViewHandlerOtherDeviations;

	/**
	 * Lyttere på om ordre endrer seg
	 */
	private List<PropertyChangeListener> orderChangeListeners = new ArrayList<PropertyChangeListener>();

	private String userFullName;

	private boolean deviationTableEditable;

	private SelectionInList orderCommentsSelectionList;

	private JButton buttonEditComment;

	private MailConfig mailConfig;
	private Login login;
	private ManagerRepository managerRepository;
	private PreventiveActionViewHandler preventiveActionViewHandler;

	/**
	 * @param aApplicationUser
	 * @param aOrder
	 * @param aUserType
	 * @param doSeAll
	 * @param forOrderInfo
	 * @param isForRegisterNew
	 * @param notDisplayDeviation
	 * @param isDeviationTableEditable
	 */
	@Inject
	public DeviationViewHandler(final Login aLogin,
			final ManagerRepository aManagerRepository,
			PreventiveActionViewHandler aPreventiveActionViewHandler,
			@Assisted final Order aOrder, @Assisted final boolean doSeAll,
			@Assisted final boolean forOrderInfo,
			@Assisted final boolean isForRegisterNew,
			@Assisted final Deviation notDisplayDeviation,
			@Assisted final boolean isDeviationTableEditable) {
		super("Avvik", aManagerRepository.getDeviationManager(), aLogin
				.getUserType(), true);
		preventiveActionViewHandler = aPreventiveActionViewHandler;
		login = aLogin;
		managerRepository = aManagerRepository;
		viewHandlerExt = this;
		deviationTableEditable = isDeviationTableEditable;
		currentDeviation = notDisplayDeviation;
		registerNew = isForRegisterNew;
		isForOrderInfo = forOrderInfo;
		orderComments = new ArrayListModel();
		orderCommentsSelectionList = new SelectionInList(
				(ListModel) orderComments);

		orderCommentsSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_INDEX,
				new CommentSelectionListener());

		order = aOrder;

		initLists();

		initFilter();
		initMail();

	}

	private void initMail() {
		String heading = ApplicationParamUtil
				.findParamByName("mail_deviation_subject");
		String msg = ApplicationParamUtil
				.findParamByName("mail_deviation_message");
		mailConfig = new MailConfig("Avvik", heading, msg, "");
	}

	private void initFilter() {
		int filterColumn = 16;
		if (isForOrderInfo) {
			filterColumn = 5;
		}

		Filter[] filtersDone = new Filter[] { new PatternFilter("[^1]",
				Pattern.CASE_INSENSITIVE, filterColumn) };
		filterPipelineDone = new FilterPipeline(filtersDone);

		if (!isForOrderInfo) {

			userFullName = login.getApplicationUser().getFullName();

			MultiColPatternFilter ownFilters = new MultiColPatternFilter(2, 7);
			ownFilters.setFilterStr(userFullName,
					SuperPatternFilter.MODE.REGEX_FIND);
			Filter[] filtersOwn = new Filter[] { ownFilters };

			filterPipelineOwn = new FilterPipeline(filtersOwn);

			ownFilters = new MultiColPatternFilter(2, 7);
			ownFilters.setFilterStr(userFullName,
					SuperPatternFilter.MODE.REGEX_FIND);

			Filter[] filtersOwnDone = new Filter[] {
					new PatternFilter("[^1]", Pattern.CASE_INSENSITIVE,
							filterColumn), ownFilters };

			filterPipelineOwnDone = new FilterPipeline(filtersOwnDone);
		}
	}

	private void initLists() {

		productAreaList = managerRepository.getProductAreaManager().findAll();
		if (productAreaList != null) {
			productAreaList.add(0, null);
		}

		deviationStatusList = managerRepository.getDeviationStatusManager()
				.findAllForDeviation();
		deviationStatusList.add(0, null);

		functionCategoryList = new ArrayListModel();

		preventiveActionList = new ArrayListModel();

		orderLineList = new ArrayListModel();
		orderLineSelectionList = new SelectionInList((ListModel) orderLineList);

		setResponsibleList();
	}

	private void setResponsibleList() {
		if (responsibleList == null) {
			responsibleList = new ArrayList<String>();
			List<String> users = managerRepository.getApplicationUserManager()
					.findAllNamesNotGroup();

			if (users != null) {
				Collections.sort(users);
				responsibleList.addAll(users);
				responsibleList.add(0, null);
			}
		}
	}

	/**
	 * Legger til lytter for om ordre har endret seg
	 * 
	 * @param listener
	 */
	public void addOrderChangeListener(PropertyChangeListener listener) {
		orderChangeListeners.add(listener);
	}

	/**
	 * Forteller lyttere at ordre har endret seg
	 */
	void fireOrderChange() {
		for (PropertyChangeListener listener : orderChangeListeners) {
			listener.propertyChange(new PropertyChangeEvent(this, "order",
					null, null));
		}
	}

	/**
	 * Lager tekstfelt for brukernavn
	 * 
	 * @param presentationModel
	 * @param search
	 * @return tekstfelt
	 */
	public JTextField getTextFieldUserName(PresentationModel presentationModel,
			boolean search) {
		JTextField textField = BasicComponentFactory.createTextField(
				presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_USER_NAME),
				!search);
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldName");
		return textField;
	}

	public JTextField getTextFieldProjectNr(
			PresentationModel presentationModel, boolean search) {
		JTextField textField = BasicComponentFactory.createTextField(
				presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_PROJECT_NR),
				!search);
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldProjectNr");
		return textField;
	}

	/**
	 * Lager label for registreringsdato
	 * 
	 * @param presentationModel
	 * @return label
	 */
	public JLabel getLabelRegistrationDate(PresentationModel presentationModel) {
		JLabel label = BasicComponentFactory
				.createLabel(presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_REGISTRATION_DATE_STRING));
		label.setName("LabelRegistrationDate");
		return label;
	}

	public JTextField getTextFieldDateClosed(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_DATE_CLOSED_STRING));
		textField.setName("TextFieldDateClosed");
		textField.setEnabled(false);
		return textField;
	}

	/**
	 * Lager tekstfelt for id
	 * 
	 * @param presentationModel
	 * @param search
	 * @return tekstfelt
	 */
	public JTextField getTextFieldId(PresentationModel presentationModel,
			boolean search) {
		JTextField textField = BasicComponentFactory
				.createTextField(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_DEVIATION_ID),
						!search);

		textField.setEnabled(search);
		return textField;
	}

	/**
	 * Lager tekstfelt for kundenr
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldCustomerNr(PresentationModel presentationModel) {
		if (order != null
				&& presentationModel
						.getBufferedValue(DeviationModel.PROPERTY_CUSTOMER_NR) == null) {
			presentationModel.getBufferedModel(
					DeviationModel.PROPERTY_CUSTOMER_NR).setValue(
					order.getCustomer().getCustomerNr());
		}
		JTextField textField = BasicComponentFactory
				.createIntegerField(presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_CUSTOMER_NR));
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldCustomerNr");
		return textField;
	}

	/**
	 * Lager tekstfelt for kundenavn
	 * 
	 * @param presentationModel
	 * @param search
	 * @return tekstfelt
	 */
	public JTextField getTextFieldCustomerName(
			PresentationModel presentationModel, boolean search) {
		if (order != null
				&& presentationModel
						.getBufferedValue(DeviationModel.PROPERTY_CUSTOMER_NAME) == null) {
			presentationModel.getBufferedModel(
					DeviationModel.PROPERTY_CUSTOMER_NAME).setValue(
					order.getCustomer().getFullName());
		}
		JTextField textField = BasicComponentFactory
				.createTextField(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_CUSTOMER_NAME),
						!search);
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldCustomerName");
		return textField;
	}

	/**
	 * Lager tekstfelt for ordernummer
	 * 
	 * @param presentationModel
	 * @param search
	 * @return tekstfelt
	 */
	public JTextField getTextFieldOrderNr(PresentationModel presentationModel,
			boolean search) {
		if (order != null
				&& presentationModel
						.getBufferedValue(DeviationModel.PROPERTY_ORDER_NR) == null) {
			presentationModel
					.getBufferedModel(DeviationModel.PROPERTY_ORDER_NR)
					.setValue(order.getOrderNr());
			presentationModel.getBufferedModel(DeviationModel.PROPERTY_ORDER)
					.setValue(order);
			presentationModel.getModel(DeviationModel.PROPERTY_ORDER).setValue(
					order);
		} else {
			presentationModel
					.getBufferedModel(DeviationModel.PROPERTY_ORDER_NR)
					.addPropertyChangeListener(
							new OrderNrChangeListener(presentationModel));
		}
		JTextField textField = BasicComponentFactory.createTextField(
				presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_ORDER_NR),
				!search);
		textField.setEnabled(false);
		if (hasWriteAccess()) {
			if (presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_ID) == null) {
				textField.setEnabled(true);
			}
		} else {
			textField.setEnabled(false);
		}
		textField.setName("TextFieldOrderNr");
		return textField;
	}

	/**
	 * Lager ok-knapp for registreringsvindu
	 * 
	 * @param window
	 * @param validationResultModel
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonOk(WindowInterface window,
			ValidationResultModel validationResultModel,
			PresentationModel presentationModel) {
		JButton buttonOk = new JButton(new OkAction(window,
				validationResultModel, presentationModel));
		buttonOk.setIcon(IconEnum.ICON_OK.getIcon());
		buttonOk.setName("ButtonOk");
		return buttonOk;
	}

	/**
	 * Lager print knapp
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonPrint(WindowInterface window,
			PresentationModel presentationModel) {
		JButton buttonPrint = new JButton(new PrintAction(window,
				presentationModel));
		buttonPrint.setIcon(IconEnum.ICON_PRINT.getIcon());
		buttonPrint.setName("ButtonPrint");
		return buttonPrint;
	}

	/**
	 * Sjekker om bruker er satt
	 * 
	 * @return true dersom bruker er satt
	 */
	public boolean isUserSet() {
		return userIsSet;
	}

	/**
	 * Lager komboboks for egen funksjon
	 * 
	 * @param search
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxOwnFunction(boolean search,
			PresentationModel presentationModel) {
		if (!search) {
			if (login.getApplicationUser() != null
					&& ((DeviationModel) presentationModel.getBean())
							.getObject().getDeviationId() == null) {
				ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
						.getBean("applicationUserManager");
				applicationUserManager
						.refreshObject(login.getApplicationUser());

				userIsSet = true;
				presentationModel.getBufferedModel(
						DeviationModel.PROPERTY_OWN_FUNCTION).setValue(
						login.getApplicationUser().getJobFunction());
				presentationModel.getBufferedModel(
						DeviationModel.PROPERTY_USER_NAME).setValue(
						login.getApplicationUser().toString());

			}
		}

		JComboBox comboBox = Util
				.getComboBoxJobFunction(true, presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_OWN_FUNCTION));

		comboBox.setEnabled(hasWriteAccess());
		comboBox.setName("ComboBoxOwnFunction");
		return comboBox;
	}

	/**
	 * Lager komboboks for avviksfunksjon
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public final JComboBox getComboBoxDeviationFunction(
			final PresentationModel presentationModel) {
		JComboBox comboBox = Util
				.getComboBoxJobFunction(
						true,
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_DEVIATION_FUNCTION));
		comboBox.addItemListener(new DeviationFunctionItemListener(
				presentationModel));
		comboBox.setEnabled(hasWriteAccess());
		comboBox.setName("ComboBoxDeviationFunction");
		return comboBox;
	}

	/**
	 * Initierer statusliste
	 * 
	 * @param jobFunction
	 * @return true dersom liste inneholder lederstatus
	 */
	private boolean initDeviationStatusList(JobFunction jobFunction) {
		deviationStatusList.clear();
		boolean isManager = false;
		DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil
				.getBean("deviationStatusManager");

		if (jobFunction != null && jobFunction.getManager() != null
				&& jobFunction.getManager().equals(login.getApplicationUser())
				|| userType.isAdministrator()) {
			deviationStatusList.addAll(deviationStatusManager
					.findAllForDeviation());
			deviationStatusList.add(0, null);
			isManager = true;
		} else {
			deviationStatusList.addAll(deviationStatusManager
					.findAllNotForManagerForDeviation());
			deviationStatusList.add(0, null);
		}
		return isManager;
	}

	/**
	 * Lager komboboks for status
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxStatus(PresentationModel presentationModel) {
		boolean isManager = initDeviationStatusList((JobFunction) presentationModel
				.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_FUNCTION));
		DeviationStatus status = (DeviationStatus) presentationModel
				.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_STATUS);
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(
						deviationStatusList,
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_DEVIATION_STATUS)));
		if (status != null
				&& Util.convertNumberToBoolean(status.getForManager())
				&& !isManager) {
			comboBox.setEnabled(false);
		} else {
			comboBox.setEnabled(hasWriteAccess());
		}
		comboBox.setName("ComboBoxStatus");
		presentationModel.getBufferedModel(
				DeviationModel.PROPERTY_DEVIATION_STATUS)
				.addValueChangeListener(
						new DeviationStatusListener(presentationModel));

		return comboBox;
	}

	/**
	 * Lager komboboks for kategori
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxFunctionCategory(
			PresentationModel presentationModel) {
		setCategoryList(presentationModel);
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(
						(ListModel) functionCategoryList,
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_FUNCTION_CATEGORY)));
		comboBox.addItemListener(new DeviationCategoryItemListener(
				presentationModel));
		comboBox.setEnabled(hasWriteAccess());
		comboBox.setName("ComboBoxFunctionCategory");
		return comboBox;
	}

	/**
	 * Lager komboboks for prevantive tiltak
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxPreventiveAction(
			PresentationModel presentationModel) {
		setPreventiveActionList(presentationModel, null);
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(
						(ListModel) preventiveActionList,
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_PREVENTIVE_ACTION)));
		comboBox.setEnabled(hasWriteAccess());
		comboBox.setName("ComboBoxPreventiveAction");
		return comboBox;
	}

	/**
	 * Lager komboboks for bhenadlingsansvarlig
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxResponsible(PresentationModel presentationModel) {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(responsibleList,
				presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_RESPONSIBLE)));
		comboBox.setEnabled(hasWriteAccess());
		comboBox.setName("ComboBoxResponsible");
		return comboBox;
	}

	/**
	 * Lager kombobks for produktområde
	 * 
	 * @param presentationModel
	 * @param search
	 * @return komboboks
	 */
	public JComboBox getComboBoxProductArea(
			PresentationModel presentationModel, boolean search) {
		if (!search) {
			if (login.getApplicationUser() != null
					&& ((DeviationModel) presentationModel.getBean())
							.getObject().getDeviationId() == null) {
				ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
						.getBean("applicationUserManager");
				applicationUserManager
						.refreshObject(login.getApplicationUser());

				if (login.getApplicationUser().getProductArea() != null) {

					presentationModel.setBufferedValue(
							DeviationModel.PROPERTY_PRODUCT_AREA, login
									.getApplicationUser().getProductArea());
				}

			}
		}

		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(productAreaList, presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_PRODUCT_AREA)));
		comboBox.setName("ComboBoxProductArea");

		if (login.getApplicationUser().getProductArea() == null) {
			comboBox.setSelectedIndex(0);
		}
		return comboBox;

	}

	/**
	 * Lager sjekkboks for filtrering av ferdige avvik
	 * 
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxFilterDone() {
		checkBoxFilterDone = new JCheckBox("Vis ferdige");
		checkBoxFilterDone.setSelected(false);
		checkBoxFilterDone.setName("CheckBoxFilterDone");
		checkBoxFilterDone.addActionListener(new FilterActionListener());
		return checkBoxFilterDone;
	}

	/**
	 * Lager sjekkboks for å filtrere på egne avvik
	 * 
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxFilterOwn() {
		checkBoxFilterOwn = new JCheckBox("Vis egne");
		checkBoxFilterOwn.setSelected(true);
		checkBoxFilterOwn.setName("CheckBoxFilterOwn");
		checkBoxFilterOwn.addActionListener(new FilterActionListener());
		return checkBoxFilterOwn;
	}

	/**
	 * Lager sjekkboks for om avvik er en ettersending
	 * 
	 * @param presentationModel
	 * @param isSearch
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxPostShipment(
			PresentationModel presentationModel, boolean isSearch) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_POST_SHIPMENT_BOOL),
						"Etterlevering");
		if (isSearch) {
			checkBox.setEnabled(true);
		} else if (hasWriteAccess()) {
			PropertyConnector conn = new PropertyConnector(
					checkBox,
					"enabled",
					presentationModel
							.getBufferedModel(DeviationModel.PROPERTY_CAN_SET_POST_SHIPMENT),
					"value");
			conn.updateProperty1();
		} else {
			checkBox.setEnabled(false);
		}
		checkBox.setName("CheckBoxPostShipment");
		return checkBox;
	}

	/**
	 * Lager sjekkboks for kontrollert
	 * 
	 * @param presentationModel
	 * @param isSearch
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxChecked(PresentationModel presentationModel,
			boolean isSearch) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_CHECKED_BOOL),
						"Kontrollert");
		if (isSearch) {
			checkBox.setEnabled(true);
		} else if (hasWriteAccess()) {
			if (login.getApplicationUser().isDeviationManager()) {
				checkBox.setEnabled(true);
			} else {
				checkBox.setEnabled(false);
			}
		} else {
			checkBox.setEnabled(false);
		}
		checkBox.setName("CheckBoxChecked");
		return checkBox;
	}

	/**
	 * Lager radioknapp for intern initiering
	 * 
	 * @param presentationModel
	 * @return radioknapp
	 */
	public JRadioButton getRadioButtonInternal(
			PresentationModel presentationModel) {
		JRadioButton radioButton = BasicComponentFactory
				.createRadioButton(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_INITIATED_BY),
						"Internt", "Internt");
		radioButton.setName("RadioButtonInternal");
		return radioButton;
	}

	/**
	 * Lager radioknapp for initiert av kunde
	 * 
	 * @param presentationModel
	 * @return radioknapp
	 */
	public JRadioButton getRadioButtonCustomer(
			PresentationModel presentationModel) {
		JRadioButton radioButton = BasicComponentFactory
				.createRadioButton(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_INITIATED_BY),
						"Kunde", "Kunde");
		radioButton.setName("RadioButtonCustomer");
		return radioButton;
	}

	/**
	 * Lager radioknapp for initiert av montør
	 * 
	 * @param presentationModel
	 * @return radioknapp
	 */
	public JRadioButton getRadioButtonAssembly(
			PresentationModel presentationModel) {
		JRadioButton radioButton = BasicComponentFactory
				.createRadioButton(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_INITIATED_BY),
						"Montør", "Montør");
		radioButton.setName("RadioButtonAssembly");
		return radioButton;
	}

	/**
	 * Lager radioknapp for initiert av transportør
	 * 
	 * @param presentationModel
	 * @return radioknapp
	 */
	public JRadioButton getRadioButtonTransport(
			PresentationModel presentationModel) {
		JRadioButton radioButton = BasicComponentFactory
				.createRadioButton(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_INITIATED_BY),
						"Transportør", "Transportør");
		radioButton.setName("RadioButtonTransport");
		return radioButton;
	}

	/**
	 * Lager sjekkboks for montering
	 * 
	 * @param presentationModel
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxDoAssembly(PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(DeviationModel.PROPERTY_DO_ASSEMBLY_BOOL),
						"Montering");

		checkBox.setName("CheckBoxDoAssembly");
		return checkBox;
	}

	/**
	 * Lager datovelger for avsluttet
	 * 
	 * @param presentationModel
	 * @return datovelger
	 */
	public JDateChooser getDateChooserProcedureCheck(
			PresentationModel presentationModel) {
		dateChooserProcedureCheck = new JDateChooser();
		PropertyConnector conn = new PropertyConnector(
				dateChooserProcedureCheck,
				"date",
				presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_PROCEDURE_CHECK),
				"value");
		conn.updateProperty1();
		dateChooserProcedureCheck.setEnabled(false);
		if (hasWriteAccess()) {
			if (userType.isAdministrator()) {
				dateChooserProcedureCheck.setEnabled(true);
			} else {
				presentationModel.getBufferedModel(
						DeviationModel.PROPERTY_DEVIATION_FUNCTION)
						.addValueChangeListener(
								new DeviationFunctionChangeListener(
										presentationModel));
				JobFunction function = (JobFunction) presentationModel
						.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_FUNCTION);
				if (function != null) {

					ApplicationUser manager = function.getManager();
					if (manager != null
							&& manager.equals(login.getApplicationUser())) {
						dateChooserProcedureCheck.setEnabled(true);
					}
				}
			}

		}
		dateChooserProcedureCheck.setName("DateChooserProcedureCheck");
		return dateChooserProcedureCheck;
	}

	/**
	 * Lager datovelger for fra dato
	 * 
	 * @param presentationModel
	 * @return datovelger
	 */
	public JDateChooser getDateChooserFrom(PresentationModel presentationModel) {
		JDateChooser dateChooser = new JDateChooser();
		PropertyConnector conn = new PropertyConnector(dateChooser, "date",
				presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_DATE_FROM),
				"value");
		conn.updateProperty1();

		dateChooser.setName("DateChooserFrom");
		return dateChooser;
	}

	/**
	 * Lager datovelger for til dato
	 * 
	 * @param presentationModel
	 * @return datovelger
	 */
	public JDateChooser getDateChooserTo(PresentationModel presentationModel) {
		JDateChooser dateChooser = new JDateChooser();
		PropertyConnector conn = new PropertyConnector(dateChooser, "date",
				presentationModel
						.getBufferedModel(DeviationModel.PROPERTY_DATE_TO),
				"value");
		conn.updateProperty1();

		dateChooser.setName("DateChooserTo");
		return dateChooser;
	}

	/**
	 * @param deviation
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(Deviation deviation) {
		if (deviation != null) {
			if (deviation.getPostShipment() != null) {
				return new CheckObject(
						"Kan ikke slette avvik som er knyttet til ettersending",
						false);
			}
		}

		return new CheckObject(null, true);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#doDelete(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public boolean doDelete(WindowInterface window) {
		boolean returnValue = true;
		int selectedIndex = objectSelectionList.getSelectionIndex();
		if (table != null) {
			selectedIndex = table.convertRowIndexToModel(selectedIndex);
		} else {
			selectedIndex = -1;
		}
		if (selectedIndex != -1) {
			Deviation object = (Deviation) objectSelectionList
					.getElementAt(selectedIndex);
			CheckObject checkObject = checkDeleteObject(object);
			if (checkObject != null) {
				String msg = checkObject.getMsg();
				if (msg == null) {
					returnValue = doDeleteObject(window, returnValue,
							selectedIndex, object);
				} else {
					returnValue = handleContinue(window, returnValue,
							selectedIndex, object, checkObject, msg);

				}
			}

		}
		return returnValue;
	}

	private boolean handleContinue(WindowInterface window, boolean returnValue,
			int selectedIndex, Deviation object, CheckObject checkObject,
			String msg) {
		if (checkObject.canContinue()) {
			boolean doContinue = Util.showConfirmDialog(window.getComponent(),
					"Slette?", msg + " Vil du slette?");
			if (doContinue) {
				doDeleteObject(window, returnValue, selectedIndex, object);
			} else {
				returnValue = false;
			}
		} else {
			returnValue = false;
			Util.showErrorDialog((Component) null, "Feil", msg);
		}
		return returnValue;
	}

	private boolean doDeleteObject(WindowInterface window, boolean returnValue,
			int selectedIndex, Deviation object) {
		try {
			Order order1 = object.getOrder();
			if (order1 != null) {
				managerRepository.getOrderManager().refreshObject(order1);
				order1.setCachedComment(null);

				managerRepository.getOrderManager().saveOrder(order1);

			}
			objectList.remove(selectedIndex);
			overviewManager.removeObject(object);
			noOfObjects--;
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
			returnValue = false;
		}
		return returnValue;
	}

	/**
	 * @param object
	 * @param presentationModel
	 * @param window
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
	 *      com.jgoodies.binding.PresentationModel,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public CheckObject checkSaveObject(DeviationModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "avvik";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddString()
	 */
	@Override
	public String getAddString() {
		return "Nytt";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Deviation";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public Deviation getNewObject() {
		return new Deviation();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		initFilter();
		// dersom tabell skal vises i oversiktsvindu
		if (!isForOrderInfo) {
			table.setFilters(filterPipelineOwnDone);
		} else {// dersom tabell skal vises i ordrevindu
			table.setFilters(filterPipelineDone);
		}

		if (!isForOrderInfo) {
			return new DeviationTableModel(objectSelectionList, false,
					DeviationColumnOrderAllEnum.getColumnNames(),
					isForOrderInfo, false);
		}
		return new DeviationTableModel(objectSelectionList, false,
				DeviationColumnOrderInfoEnum.getColumnNames(), isForOrderInfo,
				false);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getExcelTable()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected JXTable getExcelTable() {
		List<Deviation> deviations = new ArrayList<Deviation>(objectList);
		for (Deviation deviation : deviations) {
			((DeviationManager) overviewManager).lazyLoad(deviation,
					new LazyLoadDeviationEnum[] {
							LazyLoadDeviationEnum.COMMENTS,
							LazyLoadDeviationEnum.ORDER_COSTS });
		}
		JXTable excelTable = new JXTable(new DeviationTableModel(objectList,
				false, DeviationColumnExcelEnum.getColumnNames(), false, true));

		// egne satt, ferdige ikke satt
		if (checkBoxFilterOwn.isSelected() && !checkBoxFilterDone.isSelected()) {
			MultiColPatternFilter ownFilters = new MultiColPatternFilter(2, 7);
			ownFilters.setFilterStr(userFullName,
					SuperPatternFilter.MODE.REGEX_FIND);

			Filter[] filtersOwnDone = new Filter[] {
					new PatternFilter("[^1]", Pattern.CASE_INSENSITIVE, 21),
					ownFilters };

			FilterPipeline filterPipelineOwnDoneExcel = new FilterPipeline(
					filtersOwnDone);
			excelTable.setFilters(filterPipelineOwnDoneExcel);
		}
		// egne satt,ferdige satt
		else if (checkBoxFilterOwn.isSelected()
				&& checkBoxFilterDone.isSelected()) {
			MultiColPatternFilter ownFilters = new MultiColPatternFilter(2, 7);
			ownFilters.setFilterStr(userFullName,
					SuperPatternFilter.MODE.REGEX_FIND);
			Filter[] filtersOwn = new Filter[] { ownFilters };

			FilterPipeline filterPipelineOwnExcel = new FilterPipeline(
					filtersOwn);
			excelTable.setFilters(filterPipelineOwnExcel);
		}
		// egne ikke satt,ferdige ikke satt -> ingen filtre
		else if (!checkBoxFilterOwn.isSelected()
				&& !checkBoxFilterDone.isSelected()) {
			Filter[] filtersDone = new Filter[] { new PatternFilter("[^1]",
					Pattern.CASE_INSENSITIVE, 21) };
			FilterPipeline filterPipelineDoneExcel = new FilterPipeline(
					filtersDone);
			excelTable.setFilters(filterPipelineDoneExcel);
		}
		// egne ikke satt,ferdige satt
		else if (!checkBoxFilterOwn.isSelected()
				&& checkBoxFilterDone.isSelected()) {
			excelTable.setFilters(null);
		}

		Util.copySortOrder(table, excelTable);

		return excelTable;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#initObjects()
	 */
	@Override
	protected void initObjects() {
		objectList.clear();
		boolean doSeeAll = true;

		if (order != null) {
			objectList.addAll(((DeviationManager) overviewManager)
					.findByOrder(order));
			if (currentDeviation != null) {
				objectList.remove(currentDeviation);
			}
		} else {
			if (currentDeviation == null) {
				if (doSeeAll) {
					objectList.addAll(overviewManager.findAll());
				} else {
					objectList.addAll(((DeviationManager) overviewManager)
							.findByManager(login.getApplicationUser()));
				}
			}
		}
		noOfObjects = objectList.getSize();
	}

	/**
	 * @param object
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#updateViewList(java.lang.Object)
	 */
	@Override
	protected void updateViewList(final Deviation object,
			WindowInterface aWindow) {
		Util.runInThreadWheel(aWindow.getRootPane(), new Threadable() {

			public void enableComponents(boolean enable) {
			}

			public Object doWork(Object[] params, JLabel labelInfo) {
				labelInfo.setText("Oppdaterer vindu...");
				objectSelectionList.clearSelection();
				objectList.clear();
				List<Deviation> objects = overviewManager.findByObject(object);

				for (Deviation deviation : objects) {
					((DeviationManager) overviewManager)
							.lazyLoad(
									deviation,
									new LazyLoadDeviationEnum[] { LazyLoadDeviationEnum.ORDER_COSTS });
				}

				if (objects.size() != noOfObjects) {
					setFiltered(true);
				} else {
					setFiltered(false);
				}

				objectList.addAll(objects);
				return null;
			}

			public void doWhenFinished(Object object) {
			}

		}, null);

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		if (isForOrderInfo) {
			return "190dlu";
		}
		return "200dlu";
	}

	public String getTableHeight() {
		if (isForOrderInfo) {
			return "80dlu";
		}
		return "200dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Avvik";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {

		return new Dimension(850, 710);

	}

	/**
	 * Henter størrelse for registreringsvindu
	 * 
	 * @return størrelse
	 */

	public Dimension getRegisterWindowSize() {
		return new Dimension(710, 710);
	}

	public boolean saveObjectExt(
			AbstractModel<Deviation, DeviationModel> object,
			WindowInterface currentWindow) {
		object.viewToModel();
		Deviation deviation = object.getObject();
		int index = objectList.indexOf(deviation);
		checkAndSaveDeviation((DeviationModel) object);
		addDeviationToTable(index, deviation);
		return true;
	}

	private void checkAndSaveDeviation(DeviationModel deviationModel) {
		Deviation deviation = deviationModel.getObject();
		DeviationModel.setRegistrationAndChangeDate(deviation, login
				.getApplicationUser());

		handleDeviationCost(deviation);

		Order deviationOrder = checkAndGetDeviationOrder(deviation);

		if (deviationOrder != null) {
			handleOrderLines(deviation, deviationModel, deviationOrder);
		}
		deviation.cacheComments();
		((DeviationManager) overviewManager).saveDeviation(deviation);
	}

	private void addDeviationToTable(int index, Deviation deviation) {
		if (index < 0) {
			objectList.add(deviation);
			noOfObjects++;
		} else {
			objectSelectionList.fireContentsChanged(index, index);
		}
	}

	private Order checkAndGetDeviationOrder(Deviation deviation) {
		Order deviationOrder = deviation.getOrder();
		if (deviationOrder == null) {
			deviationOrder = findDeviationOrderByOrderNr(deviation.getOrderNr());

		}
		lazyLoadOrder(deviationOrder);
		return deviationOrder;
	}

	private void handleDeviationCost(Deviation deviation) {
		Set<OrderCost> orderCosts = deviation.getOrderCosts();

		if (orderCosts != null) {
			List<OrderCost> orderCostList = new ArrayList<OrderCost>(orderCosts);

			for (OrderCost orderCost : orderCostList) {
				if (orderCost.getCostAmount() == null) {
					orderCost.setDeviation(null);
					orderCosts.remove(orderCost);

				}
			}
		}

	}

	private void handleOrderLines(Deviation deviation,
			DeviationModel deviationModel, Order deviationOrder) {
		PostShipment postShipment = deviation.getPostShipment();

		ArrayListModel orderLines = deviationModel.getOrderLineArrayListModel();

		if (postShipment != null && orderLines != null
				&& orderLines.size() != 0) {
			postShipment.setDeviation(deviation);
			moveOrderLinesToPostShipment(postShipment, orderLines,
					deviationOrder);
			((DeviationManager) overviewManager).saveDeviation(deviation);
		}

		if (postShipment != null && postShipment.getOrderLines() != null
				&& postShipment.getOrderLines().size() != 0) {
			savePostShipment(postShipment, deviationOrder);

		} else if (postShipment != null
				&& (postShipment.getOrderLines() == null || postShipment
						.getOrderLines().size() != 0)) {
			Util
					.showErrorDialog(
							window,
							"Ingen artikler",
							"Det er ikke registert noen artikler, så det vil ikke bli laget noen etterlevering");
		}

	}

	private void savePostShipment(PostShipment postShipment,
			Order deviationOrder) {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
				.getBean("postShipmentManager");
		postShipment.setOrder(deviationOrder);
		postShipment.cacheComments();
		postShipmentManager.savePostShipment(postShipment);
	}

	@SuppressWarnings("unchecked")
	private void moveOrderLinesToPostShipment(PostShipment postShipment,
			ArrayListModel orderLines, Order deviationOrder) {
		Set<OrderLine> postOrderLines = postShipment.getOrderLines();

		if (postOrderLines == null) {
			postOrderLines = new HashSet<OrderLine>();
		}

		postOrderLines.clear();

		Iterator<OrderLine> it = orderLines.iterator();
		while (it.hasNext()) {
			OrderLine orderLine = it.next();
			orderLine.setPostShipment(postShipment);
			orderLine.setDeviation(null);
			postOrderLines.add(orderLine);
		}
		postShipment.setOrderLines(postOrderLines);
		postShipment.setOrder(deviationOrder);

	}

	private void lazyLoadOrder(Order order) {
		if (order != null) {
			List<LazyLoadOrderEnum> enumList=Lists.newArrayList();
			if(!Hibernate.isInitialized(order.getOrderLines())){
				enumList.add(LazyLoadOrderEnum.ORDER_LINES);
			}
			if(!Hibernate.isInitialized(order.getComment())){
				enumList.add(LazyLoadOrderEnum.COMMENTS);
			}
			if(!Hibernate.isInitialized(order.getOrderCosts())){
				enumList.add(LazyLoadOrderEnum.ORDER_COSTS);
			}
			if(!Hibernate.isInitialized(order.getCollies())){
				enumList.add(LazyLoadOrderEnum.COLLIES);
			}
			if(!enumList.isEmpty()){
			LazyLoadOrderEnum[] lazyEnums=new LazyLoadOrderEnum[enumList.size()];
			enumList.toArray(lazyEnums);
			managerRepository.getOrderManager().lazyLoadOrder(
					order,
					lazyEnums);
			}
		}
	}

	private Order findDeviationOrderByOrderNr(String orderNr) {
		Order deviationOrder = null;
		if (orderNr != null) {
			deviationOrder = managerRepository.getOrderManager().findByOrderNr(
					String.valueOf(orderNr));

			if (deviationOrder == null) {
				Util.showErrorDialog(window, "Fant ikke ordre",
						"Fant ikke ordre med ordrenr " + orderNr);

			}
		}
		return deviationOrder;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		components.add(table);
		TableColumn col;
		if (isForOrderInfo) {
			// Opprettet
			col = table.getColumnModel().getColumn(0);
			col.setPreferredWidth(80);

			// Navn
			col = table.getColumnModel().getColumn(1);
			col.setPreferredWidth(100);

			// Kategori
			col = table.getColumnModel().getColumn(2);
			col.setPreferredWidth(100);

			// Status
			col = table.getColumnModel().getColumn(3);
			col.setPreferredWidth(100);

			table.getColumnExt(5).setVisible(false);
		} else {
			// Id
			col = table.getColumnModel().getColumn(0);
			col.setPreferredWidth(30);

			// Opprettet
			col = table.getColumnModel().getColumn(1);
			col.setPreferredWidth(80);

			// Navn
			col = table.getColumnModel().getColumn(2);
			col.setPreferredWidth(100);

			// Kundenr
			col = table.getColumnModel().getColumn(3);
			col.setPreferredWidth(50);

			// Kundenavn
			col = table.getColumnModel().getColumn(4);
			col.setPreferredWidth(100);

			// Ordrenr
			col = table.getColumnModel().getColumn(5);
			col.setPreferredWidth(50);

			// Prouktområde
			col = table.getColumnModel().getColumn(6);
			col.setPreferredWidth(100);

			// Behandlingsansvarlig
			col = table.getColumnModel().getColumn(7);
			col.setPreferredWidth(150);

			// Egen funksjon
			col = table.getColumnModel().getColumn(8);
			col.setPreferredWidth(150);

			// Avviksfunksjon
			col = table.getColumnModel().getColumn(9);
			col.setPreferredWidth(100);

			// Kategori
			col = table.getColumnModel().getColumn(10);
			col.setPreferredWidth(100);

			// Korrigerende tiltak
			col = table.getColumnModel().getColumn(11);
			col.setPreferredWidth(100);

			// Status
			col = table.getColumnModel().getColumn(12);
			col.setPreferredWidth(120);
			// Endret
			col = table.getColumnModel().getColumn(13);
			col.setPreferredWidth(80);

			// Viser ikke lukket
			table.getColumnExt(13).setVisible(false);
			// Viser ikke kommentar
			table.getColumnExt(13).setVisible(false);
			// Viser ikke ferdig
			table.getColumnExt(14).setVisible(false);
		}

	}

	/**
	 * Lytter på avviksfunskjon
	 * 
	 * @author atle.brekka
	 */
	private class DeviationFunctionItemListener implements ItemListener {
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public DeviationFunctionItemListener(
				PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent arg0) {
			setCategoryList(presentationModel);

		}

	}

	/**
	 * Lytter på endringer av katgeori
	 * 
	 * @author atle.brekka
	 */
	private class DeviationCategoryItemListener implements ItemListener {
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public DeviationCategoryItemListener(
				PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent arg0) {
			setPreventiveActionList(presentationModel, null);

		}

	}

	/**
	 * Setter kategoriliste ut i fra funksjon
	 * 
	 * @param presentationModel
	 */
	void setCategoryList(PresentationModel presentationModel) {
		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean("jobFunctionManager");
		JobFunction deviationFunction = (JobFunction) presentationModel
				.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_FUNCTION);

		jobFunctionManager.lazyLoad(deviationFunction, new LazyLoadEnum[][] { {
				LazyLoadEnum.FUNCTION_CATEGORIES, LazyLoadEnum.NONE } });
		if (deviationFunction != null) {
			functionCategoryList.clear();
			functionCategoryList.addAll(deviationFunction
					.getFunctionCategories());
			functionCategoryList.add(0, null);

		}
	}

	/**
	 * Setter liste for prevantive tiltak
	 * 
	 * @param presentationModel
	 * @param selectedAction
	 */
	void setPreventiveActionList(PresentationModel presentationModel,
			PreventiveAction selectedAction) {
		JobFunction deviationFunction = (JobFunction) presentationModel
				.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_FUNCTION);
		FunctionCategory functionCategory = (FunctionCategory) presentationModel
				.getBufferedValue(DeviationModel.PROPERTY_FUNCTION_CATEGORY);

		if (deviationFunction != null && functionCategory != null) {
			PreventiveActionManager preventiveActionManager = (PreventiveActionManager) ModelUtil
					.getBean("preventiveActionManager");
			preventiveActionList.clear();
			List<PreventiveAction> actions = preventiveActionManager
					.findAllOpenByFunctionAndCategory(deviationFunction,
							functionCategory);
			if (actions != null) {
				actions.add(0, null);
				preventiveActionList.addAll(actions);
				preventiveActionList.add(0, null);
			}
			if (selectedAction != null) {
				presentationModel.setBufferedValue(
						DeviationModel.PROPERTY_PREVENTIVE_ACTION,
						selectedAction);
			}
		}

	}

	private enum DeviationColumnOrderInfoEnum {
		OPPRETTET("Opprettet") {
			@Override
			public Object getValue(Deviation deviation) {
				return Util.SHORT_DATE_FORMAT.format(deviation
						.getRegistrationDate());
			}

			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}
		},
		NAVN("Navn") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getUserName();
			}
		},
		KATEGORI("Kategori") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getFunctionCategory() != null ? deviation
						.getFunctionCategory().toString() : null;
			}
		},
		STATUS("Status") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getDeviationStatus() != null ? deviation
						.getDeviationStatus().toString() : "Ikke satt";
			}
		},
		ENDRET("Endret") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Date.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getLastChanged();
			}
		},
		FERDIG("Ferdig") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Integer.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getDeviationStatus() != null
						&& deviation.getDeviationStatus().getDeviationDone() != null ? deviation
						.getDeviationStatus().getDeviationDone()
						: 0;
			}
		};

		private String columnName;

		private DeviationColumnOrderInfoEnum(String aColumnName) {
			columnName = aColumnName;
		}

		public String getColumnName() {
			return columnName;
		}

		public static String[] getColumnNames() {
			List<String> columnNameList = new ArrayList<String>();
			DeviationColumnOrderInfoEnum[] enums = DeviationColumnOrderInfoEnum
					.values();
			for (DeviationColumnOrderInfoEnum aEnum : enums) {
				columnNameList.add(aEnum.getColumnName());
			}
			String[] colArray = new String[columnNameList.size()];
			return columnNameList.toArray(colArray);
		}

		public abstract Object getValue(Deviation deviation);

		@SuppressWarnings("unchecked")
		public abstract Class getClassFor();
	}

	private enum DeviationColumnOrderAllEnum {
		ID("Id") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Integer.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getDeviationId();
			}
		},
		OPPRETTET("Opprettet") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return Util.SHORT_DATE_FORMAT.format(deviation
						.getRegistrationDate());
			}
		},
		NAVN("Navn") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getUserName();
			}
		},
		KUNDENR("Kundenr") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Integer.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getCustomerNr();
			}
		},
		KUNDENAVN("Kundenavn") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getCustomerName();
			}
		},
		ORDRENR("Ordrenr") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getOrderNr();
			}
		},
		PRODUKTOMRÅDE("Produktområde") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getProductArea() != null ? deviation
						.getProductArea().toString() : null;
			}
		},
		BEHANDLINGSANSVARLIG("Behandlingsansvarlig") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getResponsible() != null ? deviation
						.getResponsible() : "";
			}
		},
		EGEN_FUNKSJON("Egen funksjon") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getOwnFunction() != null ? deviation
						.getOwnFunction().toString() : null;
			}
		},
		AVVIKSFUNKSJON("Avviksfunksjon") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getDeviationFunction() != null ? deviation
						.getDeviationFunction().toString() : null;
			}
		},
		KATEGORI("Kategori") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getFunctionCategory() != null ? deviation
						.getFunctionCategory().toString() : null;
			}
		},
		KORRIGERENDE_TILTAK("Korrigerende tiltak") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return PreventiveAction.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getPreventiveAction();
			}
		},
		STATUS("Status") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getDeviationStatus() != null ? deviation
						.getDeviationStatus().toString() : "Ikke satt";
			}
		},
		LUKKET("Lukket") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getDateClosed() != null ? Util.SHORT_DATE_FORMAT
						.format(deviation.getDateClosed())
						: null;
			}
		},
		FRITEKST("Fritekst") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return !report ? null : deviation.getDeviationCommentsString();
			}
		},
		ENDRET("Endret") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Date.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getLastChanged();
			}
		},
		FERDIG("Ferdig") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Integer.class;
			}

			@Override
			public Object getValue(Deviation deviation, boolean report) {
				return deviation.getDeviationStatus() != null
						&& deviation.getDeviationStatus().getDeviationDone() != null ? deviation
						.getDeviationStatus().getDeviationDone()
						: 0;
			}
		};
		private String columnName;

		private DeviationColumnOrderAllEnum(String aColumnName) {
			columnName = aColumnName;
		}

		public String getColumnName() {
			return columnName;
		}

		public static String[] getColumnNames() {
			List<String> columnNameList = new ArrayList<String>();
			DeviationColumnOrderAllEnum[] enums = DeviationColumnOrderAllEnum
					.values();
			for (DeviationColumnOrderAllEnum aEnum : enums) {
				columnNameList.add(aEnum.getColumnName());
			}
			String[] colArray = new String[columnNameList.size()];
			return columnNameList.toArray(colArray);
		}

		public abstract Object getValue(Deviation deviation, boolean report);

		@SuppressWarnings("unchecked")
		public abstract Class getClassFor();
	}

	private enum DeviationColumnExcelEnum {
		ID("Id") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Integer.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getDeviationId();
			}
		},
		OPPRETTET("Opprettet") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return Util.SHORT_DATE_FORMAT.format(deviation
						.getRegistrationDate());
			}
		},
		NAVN("Navn") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getUserName();
			}
		},
		KUNDENR("Kundenr") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Integer.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getCustomerNr();
			}
		},
		KUNDENAVN("Kundenavn") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getCustomerName();
			}
		},
		ORDRENR("Ordrenr") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getOrderNr();
			}
		},
		PRODUKTOMRÅDE("Produktområde") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getProductArea() != null ? deviation
						.getProductArea().toString() : null;
			}
		},
		BEHANDLINGSANSVARLIG("Behandlingsansvarlig") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getResponsible();
			}
		},
		EGEN_FUNKSJON("Egen funksjon") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getOwnFunction() != null ? deviation
						.getOwnFunction().toString() : null;
			}
		},
		AVVIKSFUNKSJON("Avviksfunksjon") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getDeviationFunction() != null ? deviation
						.getDeviationFunction().toString() : null;
			}
		},
		KATEGORI("Kategori") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getFunctionCategory() != null ? deviation
						.getFunctionCategory().toString() : null;
			}
		},
		KORRIGERENDE_TILTAK("Korrigerende tiltak") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return PreventiveAction.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getPreventiveAction();
			}
		},
		STATUS("Status") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getDeviationStatus() != null ? deviation
						.getDeviationStatus().toString() : "Ikke satt";
			}
		},
		LUKKET("Lukket") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getDateClosed() != null ? Util.SHORT_DATE_FORMAT
						.format(deviation.getDateClosed())
						: null;
			}
		},
		FRITEKST("Fritekst") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return String.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getDeviationCommentsString();
			}
		},
		KOST_KUNDE("Kost kunde") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return BigDecimal.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getCost("Kunde");
			}
		},
		KOST_TRANSPORTØR("Kost Transportør") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return BigDecimal.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getCost("Transportør");
			}
		},
		KOST_MONTØR("Kost montør") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return BigDecimal.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getCost("Montør");
			}
		},
		KOST_UNDERLEVERANDØR("Kost underleverandør") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return BigDecimal.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getCost("Underleverandør");
			}
		},
		KOST_INTERN("Kost intern") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return BigDecimal.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getCost("Intern");
			}
		},
		KOST_EKSTERN("Kost ekstern") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return BigDecimal.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getCost("Ekstern");
			}
		},
		FERDIG("Ferdig") {
			@SuppressWarnings("unchecked")
			@Override
			public Class getClassFor() {
				return Integer.class;
			}

			@Override
			public Object getValue(Deviation deviation) {
				return deviation.getDeviationStatus() != null
						&& deviation.getDeviationStatus().getDeviationDone() != null ? deviation
						.getDeviationStatus().getDeviationDone()
						: 0;
			}
		};
		private String columnName;

		private DeviationColumnExcelEnum(String aColumnName) {
			columnName = aColumnName;
		}

		public String getColumnName() {
			return columnName;
		}

		public static String[] getColumnNames() {
			List<String> columnNameList = new ArrayList<String>();
			DeviationColumnExcelEnum[] enums = DeviationColumnExcelEnum
					.values();
			for (DeviationColumnExcelEnum aEnum : enums) {
				columnNameList.add(aEnum.getColumnName());
			}
			String[] colArray = new String[columnNameList.size()];
			return columnNameList.toArray(colArray);
		}

		public abstract Object getValue(Deviation deviation);

		@SuppressWarnings("unchecked")
		public abstract Class getClassFor();

	}

	/**
	 * Tabellmodell for avvik
	 * 
	 * @author atle.brekka
	 */
	private static final class DeviationTableModel extends AbstractTableAdapter {
		private static final long serialVersionUID = 1L;

		private boolean report = false;

		private boolean isForOrderInfo = false;

		private boolean isForExcel = false;

		/**
		 * @param listModel
		 * @param report
		 * @param columns
		 * @param forOrderInfo
		 * @param forExcel
		 */
		DeviationTableModel(ListModel listModel, boolean report,
				String[] columns, boolean forOrderInfo, boolean forExcel) {
			super(listModel, columns);
			this.report = report;
			isForOrderInfo = forOrderInfo;
			isForExcel = forExcel;
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex < getRowCount()) {
				Deviation deviation = (Deviation) getRow(rowIndex);
				if (isForOrderInfo) {
					return getValueAtForOrderInfo(deviation, columnIndex);
				} else if (isForExcel) {
					return getValueAtForExcel(deviation, columnIndex);
				}
				return getValueAtForAll(deviation, columnIndex);
			}
			return null;
		}

		/**
		 * Henter kolonneverdi for ordreinfo
		 * 
		 * @param deviation
		 * @param columnIndex
		 * @return kolonneverdi
		 */
		public Object getValueAtForOrderInfo(Deviation deviation,
				int columnIndex) {
			DeviationColumnOrderInfoEnum column = DeviationColumnOrderInfoEnum
					.valueOf(StringUtils.upperCase(getColumnName(columnIndex)));
			return column.getValue(deviation);

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int column) {
			if (isForOrderInfo) {
				return getColumnClassForOrderInfo(column);
			} else if (isForExcel) {
				return getColumnClassForExcel(column);
			}
			return getColumnClassForAll(column);
		}

		/**
		 * Henter kolonneklasse for alle
		 * 
		 * @param column
		 * @return kolonneklasse
		 */
		public Class<?> getColumnClassForAll(int columnIndex) {
			DeviationColumnOrderAllEnum column = DeviationColumnOrderAllEnum
					.valueOf(StringUtils.replace(StringUtils
							.upperCase(getColumnName(columnIndex)), " ", "_"));
			return column.getClassFor();

		}

		/**
		 * Henter kolonneklasse for modell for excel
		 * 
		 * @param column
		 * @return klasse
		 */
		public Class<?> getColumnClassForExcel(int columnIndex) {
			DeviationColumnExcelEnum column = DeviationColumnExcelEnum
					.valueOf(StringUtils.replace(StringUtils
							.upperCase(getColumnName(columnIndex)), " ", "_"));
			return column.getClassFor();

		}

		/**
		 * Henter kolonneklasse for ordreinfo
		 * 
		 * @param column
		 * @return kolonneklasse
		 */
		public Class<?> getColumnClassForOrderInfo(int columnIndex) {
			DeviationColumnOrderInfoEnum column = DeviationColumnOrderInfoEnum
					.valueOf(StringUtils.upperCase(getColumnName(columnIndex)));
			return column.getClassFor();
		}

		/**
		 * Henter kolonneverdi for vanlig tabell
		 * 
		 * @param deviation
		 * @param columnIndex
		 * @return kolonneverdi
		 */
		public Object getValueAtForAll(Deviation deviation, int columnIndex) {
			DeviationColumnOrderAllEnum column = DeviationColumnOrderAllEnum
					.valueOf(StringUtils.replace(StringUtils
							.upperCase(getColumnName(columnIndex)), " ", "_"));
			return column.getValue(deviation, report);

		}

		/**
		 * Henter verdi for kolonne for excelmodell
		 * 
		 * @param deviation
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAtForExcel(Deviation deviation, int columnIndex) {
			DeviationColumnExcelEnum column = DeviationColumnExcelEnum
					.valueOf(StringUtils.replace(StringUtils
							.upperCase(getColumnName(columnIndex)), " ", "_"));
			return column.getValue(deviation);
		}

	}

	/**
	 * Håndterer ok-knapp
	 * 
	 * @author atle.brekka
	 */
	private class OkAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		private PresentationModel presentationModel;

		private ValidationResultModel validationResultModel;

		/**
		 * @param aWindow
		 * @param aValidationResultModel
		 * @param aPresentationModel
		 */
		public OkAction(WindowInterface aWindow,
				ValidationResultModel aValidationResultModel,
				PresentationModel aPresentationModel) {
			super("Ok");
			presentationModel = aPresentationModel;
			window = aWindow;
			validationResultModel = aValidationResultModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (validationResultModel.hasErrors()) {
				Util.showErrorDialog(window, "Rett feil",
						"Rett alle feil før lagring!");
				return;
			}
			presentationModel.triggerCommit();
			saveObject((DeviationModel) presentationModel.getBean(), window);
			window.dispose();
		}
	}

	/**
	 * Lytter til forandring av ordrenummer
	 * 
	 * @author atle.brekka
	 */
	private class OrderNrChangeListener implements PropertyChangeListener {
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public OrderNrChangeListener(PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getPropertyName().equalsIgnoreCase("value")) {
				String orderNr = (String) event.getNewValue();

				if (orderNr != null) {
					Order order1 = managerRepository.getOrderManager()
							.findByOrderNr(orderNr);
					if (order1 != null) {
						presentationModel.setBufferedValue(
								DeviationModel.PROPERTY_ORDER, order1);
						((DeviationModel) presentationModel.getBean())
								.getObject().setOrder(order1);
						presentationModel.setBufferedValue(
								DeviationModel.PROPERTY_CUSTOMER_NAME, order1
										.getCustomer().getFirstName()
										+ " "
										+ order1.getCustomer().getLastName());
						presentationModel.setBufferedValue(
								DeviationModel.PROPERTY_CUSTOMER_NR, order1
										.getCustomer().getCustomerNr());
						if ((presentationModel
								.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_ID) == null || presentationModel
								.getBufferedValue(DeviationModel.PROPERTY_POST_SHIPMENT) == null)
								&& orderLineList.size() != 0) {
							presentationModel
									.setBufferedValue(
											DeviationModel.PROPERTY_CAN_SET_POST_SHIPMENT,
											true);
						}

						presentationModel.setBufferedValue(
								DeviationModel.PROPERTY_CAN_ADD_ORDER_LINE,
								true);

						if (deviationViewHandlerOtherDeviations != null) {
							deviationViewHandlerOtherDeviations
									.changeOrder(order1);
							fireOrderChange();
						}

					}
				}
			}

		}

	}

	/**
	 * Håndterer utskrift
	 * 
	 * @author atle.brekka
	 */
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

	/**
	 * Skriver ut
	 * 
	 * @author atle.brekka
	 */
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

		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
		 *      javax.swing.JLabel)
		 */
		public Object doWork(Object[] params, JLabel labelInfo) {
			Deviation deviation = ((DeviationModel) presentationModel.getBean())
					.getObject();
			if (deviation != null) {
				labelInfo.setText("Genererer utskrift...");

				ReportViewer reportViewer = new ReportViewer("Avvik",
						mailConfig);

				JDialog dialog = Util.getDialog(owner, "Avvik", true);
				WindowInterface window = new JDialogAdapter(dialog);

				window.add(reportViewer.buildPanel(window));

				try {
					List<Deviation> list = new ArrayList<Deviation>();
					list.add(deviation);
					reportViewer.generateProtransReportFromBean(list, "Avvik",
							ReportEnum.DEVIATION, null, "Avvik "
									+ deviation.getDeviationId() + "_"
									+ Util.getCurrentDateAsDateTimeString()
									+ ".pdf");
					window.pack();
					window.setSize(new Dimension(850, 700));
					Util.locateOnScreenCenter(window);
					window.setVisible(true);
				} catch (ProTransException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(boolean enable) {
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		if (registerNew) {
			return true;
		}
		return UserUtil.hasWriteAccess(userType, "Avvik");
	}

	/**
	 * Lager knapp for å legge til kommentar
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonAddComment(WindowInterface window,
			PresentationModel presentationModel) {
		JButton button = new JButton(new AddComment(window, presentationModel));
		button.setName("AddDeviationComment");
		return button;
	}

	public JButton getButtonEditComment(WindowInterface window,
			PresentationModel presentationModel) {
		buttonEditComment = new JButton(new EditComment(presentationModel));
		buttonEditComment.setName("EditDeviationComment");
		buttonEditComment.setEnabled(false);
		return buttonEditComment;
	}

	/**
	 * Lager knapp for å legge til prevantivt tiltak
	 * 
	 * @param presentationModel
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonAddPreventiveAction(
			PresentationModel presentationModel, WindowInterface window) {
		JButton button = new JButton(new AddPreventiveAction(presentationModel,
				window));
		button.setName("ButtonAddPreventiveAction");
		return button;
	}

	/**
	 * Lager liste for kommentarer
	 * 
	 * @param presentationModel
	 * @return liste
	 */
	@SuppressWarnings("unchecked")
	public JList getListComments(PresentationModel presentationModel) {
		orderComments.clear();
		orderComments.addAll((List<OrderComment>) presentationModel
				.getBufferedValue(DeviationModel.PROPERTY_COMMENTS));
		JList list = BasicComponentFactory
				.createList(orderCommentsSelectionList);

		list.setName("ListOrderComments");
		list.setCellRenderer(new ListMultilineRenderer(85));
		return list;
	}

	/**
	 * Lager liste for ordrelinjer som er med i avvik
	 * 
	 * @param presentationModel
	 * @return liste
	 */
	public JList getListOrderLines(PresentationModel presentationModel) {
		orderLineList.clear();
		orderLineList
				.addAll((ArrayListModel) presentationModel
						.getBufferedValue(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL));
		JList list = BasicComponentFactory.createList(orderLineSelectionList);
		list.setName("ListOrderLines");

		return list;
	}

	/**
	 * Lager knapp for å legge til ordrelinjer til avvik
	 * 
	 * @param presentationModel
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonAddOrderLine(PresentationModel presentationModel,
			WindowInterface window) {
		JButton button = new JButton(new AddOrderLineAction(presentationModel,
				window));
		button.setName("ButtonAddOrderLine");

		if (hasWriteAccess()) {
			PropertyConnector conn = new PropertyConnector(
					button,
					"enabled",
					presentationModel
							.getBufferedModel(DeviationModel.PROPERTY_CAN_ADD_ORDER_LINE),
					"value");
			conn.updateProperty1();
		} else {
			button.setEnabled(false);
		}

		return button;
	}

	/**
	 * Lager knapp for å ta vekk ordrelinjer fra avvik
	 * 
	 * @param presentationModel
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonRemoveOrderLine(
			PresentationModel presentationModel, WindowInterface window) {
		buttonRemoveOrderLine = new JButton(new RemoveOrderLineAction(
				presentationModel, window));
		buttonRemoveOrderLine.setName("ButtonRemoveOrderLine");

		orderLineSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new EmptySelectionListener(presentationModel));
		buttonRemoveOrderLine.setEnabled(false);
		return buttonRemoveOrderLine;
	}

	/**
	 * Legg til kommentar
	 * 
	 * @author atle.brekka
	 */
	private class AddComment extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		private PresentationModel presentationModel;

		/**
		 * @param aWindow
		 * @param aPresentationModel
		 */
		public AddComment(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Legg til kommentar...");

			window = aWindow;
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			OrderComment orderComment = new OrderComment();
			orderComment.setUserName(login.getApplicationUser().getUserName());
			orderComment.setCommentDate(Util.getCurrentDate());
			orderComment
					.addCommentType(CommentTypeUtil.getCommentType("Ordre"));

			CommentViewHandler deviationCommentViewHandler = new CommentViewHandler(
					login, managerRepository.getOrderManager());
			EditCommentView editDeviationCommentView = new EditCommentView(
					new OrderCommentModel(orderComment),
					deviationCommentViewHandler);

			JDialog dialog = Util.getDialog(window, "Legg til kommentar", true);
			dialog.setName("EditDeviationCommentView");
			WindowInterface dialogWindow = new JDialogAdapter(dialog);
			dialogWindow.add(editDeviationCommentView.buildPanel(dialogWindow));
			dialog.pack();
			Util.locateOnScreenCenter(dialog);
			dialogWindow.setVisible(true);

			if (!deviationCommentViewHandler.isCanceled()) {
				orderComment.setDeviation(((DeviationModel) presentationModel
						.getBean()).getObject());
				orderComment.setOrder(((DeviationModel) presentationModel
						.getBean()).getObject().getOrder());
				orderComments.add(0, orderComment);
				presentationModel.setBufferedValue(
						DeviationModel.PROPERTY_COMMENTS, orderComments);
			}

		}

	}

	private class EditComment extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private PresentationModel presentationModel;

		/**
		 * @param aWindow
		 * @param aPresentationModel
		 */
		public EditComment(PresentationModel aPresentationModel) {
			super("Editer kommentar...");
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			showAndEditComment(presentationModel);

		}

	}

	private void showAndEditComment(PresentationModel presentationModel) {
		OrderComment orderComment = (OrderComment) orderCommentsSelectionList
				.getSelection();
		CommentViewHandler commentViewHandler = new CommentViewHandler(login,
				managerRepository.getOrderManager());
		orderComment = commentViewHandler.showAndEditOrderComment(window,
				orderComment, "orderManager");
	}

	/**
	 * Legg til prevantivt tiltak
	 * 
	 * @author atle.brekka
	 */
	private class AddPreventiveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private PresentationModel presentationModel;

		private WindowInterface window;

		/**
		 * @param aPresentationModel
		 * @param aWindow
		 */
		public AddPreventiveAction(PresentationModel aPresentationModel,
				WindowInterface aWindow) {
			super("Lag korrigerende tiltak...");

			presentationModel = aPresentationModel;
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			PreventiveAction preventiveAction = new PreventiveAction();
			preventiveAction.setDescription((String) presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_LAST_COMMENT));
			preventiveAction
					.setFunctionCategory((FunctionCategory) presentationModel
							.getBufferedValue(DeviationModel.PROPERTY_FUNCTION_CATEGORY));
			preventiveAction
					.setJobFunction((JobFunction) presentationModel
							.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_FUNCTION));

			preventiveActionViewHandler.openEditView(preventiveAction, false,
					window);
			setPreventiveActionList(presentationModel, preventiveAction);

		}

	}

	/**
	 * Legger til ordrelinje
	 * 
	 * @author atle.brekka
	 */
	private class AddOrderLineAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private PresentationModel presentationModel;

		private WindowInterface window;

		/**
		 * @param aPresentationModel
		 * @param aWindow
		 */
		public AddOrderLineAction(PresentationModel aPresentationModel,
				WindowInterface aWindow) {
			super("Legg til artikkel");
			presentationModel = aPresentationModel;
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			PostShipment postShipment = (PostShipment) presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_POST_SHIPMENT);
			if (postShipment != null && postShipment.getSent() != null) {
				Util
						.showErrorDialog(window, "Sent!",
								"Kan ikke legge til artikkel til etterlevering som er sendt!");
			} else {
				ArticleTypeViewHandler articleTypeViewHandler = new ArticleTypeViewHandler(
						login, managerRepository, null);
				ArticleTypeView articleTypeView = new ArticleTypeView(
						articleTypeViewHandler, true, true);
				WindowInterface dialog = new JDialogAdapter(new JDialog(
						ProTransMain.PRO_TRANS_MAIN, "Artikkel", true));
				dialog.setName("ArticleView");
				dialog.add(articleTypeView.buildPanel(dialog));
				dialog.pack();
				Util.locateOnScreenCenter(dialog);
				dialog.setVisible(true);

				List<ArticleType> newArticles = articleTypeView
						.getSelectedObjects();
				List<OrderLine> newOrderLines = new ArrayList<OrderLine>();
				if (newArticles != null && newArticles.size() > 0) {
					Deviation deviation = ((DeviationModel) presentationModel
							.getBean()).getObject();
					for (ArticleType article : newArticles) {
						newOrderLines
								.add(new OrderLine(
										null,
										(Order) presentationModel
												.getBufferedValue(DeviationModel.PROPERTY_ORDER),
										null, article, null, null, null, null,
										null, null, article
												.getArticleTypeName(), null, 1,
										null, null, null, null, deviation,
										null, null, null, null));
					}

					ArrayListModel bufferedOrderLines = (ArrayListModel) presentationModel
							.getBufferedValue(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);
					bufferedOrderLines.addAll(newOrderLines);
					presentationModel
							.setBufferedValue(
									ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL,
									bufferedOrderLines);

					orderLineList.clear();
					orderLineList.addAll(bufferedOrderLines);

					if (!(Boolean) presentationModel
							.getBufferedValue(DeviationModel.PROPERTY_IS_POST_SHIPMENT)
							&& ((presentationModel
									.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_ID) == null || presentationModel
									.getBufferedValue(DeviationModel.PROPERTY_POST_SHIPMENT) == null) && orderLineList
									.size() != 0)) {
						presentationModel.setBufferedValue(
								DeviationModel.PROPERTY_CAN_SET_POST_SHIPMENT,
								true);
					}
				}
			}

		}
	}

	/**
	 * Fjerner ordrelinje fra avvik
	 * 
	 * @author atle.brekka
	 */
	private class RemoveOrderLineAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private PresentationModel presentationModel;

		private WindowInterface window;

		/**
		 * @param aPresentationModel
		 * @param aWindow
		 */
		public RemoveOrderLineAction(PresentationModel aPresentationModel,
				WindowInterface aWindow) {
			super("Fjern artikkel");
			presentationModel = aPresentationModel;
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			PostShipment postShipment = (PostShipment) presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_POST_SHIPMENT);
			if (postShipment != null && postShipment.getSent() != null) {
				Util
						.showErrorDialog(window, "Sent!",
								"Kan ikke slette artikkel til etterlevering som er sendt!");
			} else {
				OrderLine orderLine = (OrderLine) orderLineSelectionList
						.getSelection();
				if (Util.showConfirmDialog(window.getComponent(), "Slette?",
						"Vil du virkelig slette artikkel?")) {
					if (orderLine != null) {
						ArrayListModel bufferedOrderLines = (ArrayListModel) presentationModel
								.getBufferedValue(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);
						bufferedOrderLines.remove(orderLine);
						presentationModel
								.setBufferedValue(
										ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL,
										bufferedOrderLines);

						orderLineList.clear();
						orderLineList.addAll(bufferedOrderLines);

						if (!(Boolean) presentationModel
								.getBufferedValue(DeviationModel.PROPERTY_IS_POST_SHIPMENT)
								&& ((presentationModel
										.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_ID) == null || presentationModel
										.getBufferedValue(DeviationModel.PROPERTY_POST_SHIPMENT) == null) && orderLineList
										.size() != 0)) {
							presentationModel
									.setBufferedValue(
											DeviationModel.PROPERTY_CAN_SET_POST_SHIPMENT,
											true);
						} else {
							presentationModel
									.setBufferedValue(
											DeviationModel.PROPERTY_CAN_SET_POST_SHIPMENT,
											false);
						}
					}
				}
			}

		}
	}

	/**
	 * Lytter på valg av linjer i ordrelinje liste
	 * 
	 * @author atle.brekka
	 */
	class EmptySelectionListener implements PropertyChangeListener {
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public EmptySelectionListener(PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			if (presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_ID) == null) {
				buttonRemoveOrderLine.setEnabled(orderLineSelectionList
						.hasSelection());
			} else {
				buttonRemoveOrderLine.setEnabled(false);
			}

		}

	}

	/**
	 * Tar bort filtrering på tabell
	 */
	void clearFilter() {
		checkBoxFilterDone.setSelected(true);
		table.setFilters(null);
		table.repaint();
	}

	/**
	 * Lytter på sjekkboks for filtrering
	 * 
	 * @author atle.brekka
	 */
	class FilterActionListener implements ActionListener {

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent event) {
			setTableFilters();

		}

	}

	private void setTableFilters() {
		initFilter();
		table.clearSelection();
		objectSelectionList.clearSelection();
		// egne satt, ferdige ikke satt
		if (checkBoxFilterOwn.isSelected() && !checkBoxFilterDone.isSelected()) {
			table.setFilters(filterPipelineOwnDone);
		}
		// egne satt,ferdige satt
		else if (checkBoxFilterOwn.isSelected()
				&& checkBoxFilterDone.isSelected()) {
			table.setFilters(filterPipelineOwn);
		}
		// egne ikke satt,ferdige ikke satt -> ingen filtre
		else if (!checkBoxFilterOwn.isSelected()
				&& !checkBoxFilterDone.isSelected()) {
			table.setFilters(filterPipelineDone);
		}
		// egne ikke satt,ferdige satt
		else if (!checkBoxFilterOwn.isSelected()
				&& checkBoxFilterDone.isSelected()) {
			table.setFilters(null);
		}

		table.repaint();
	}

	/**
	 * Setter knappetilgjenglighet
	 * 
	 * @param enable
	 */
	public void setComponentEnablement(boolean enable) {
		for (Component component : components) {
			component.setEnabled(enable);
		}
		updateButtonEnablement();
	}

	/**
	 * Henter gjeldende bruker
	 * 
	 * @return bruker
	 */
	public ApplicationUser getApplicationUser() {
		return login.getApplicationUser();
	}

	/**
	 * Lytter på endring av avvikstatus
	 * 
	 * @author atle.brekka
	 */
	private class DeviationFunctionChangeListener implements
			PropertyChangeListener {

		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public DeviationFunctionChangeListener(
				PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			JobFunction function = (JobFunction) presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_FUNCTION);
			if (function != null) {
				ApplicationUser manager = function.getManager();
				if (manager != null
						&& manager.equals(login.getApplicationUser())) {
					dateChooserProcedureCheck.setEnabled(true);
				}
			}

		}

	}

	/**
	 * Henter hjelpeklasse for kostnad
	 * 
	 * @param presentationModel
	 * @param addInternalCost
	 * @param addDefaultCosts
	 * @return hjelpeklasse for kostnad
	 */
	public OrderCostsViewHandler getOrderCostsViewHandler(
			PresentationModel presentationModel, boolean addInternalCost,
			boolean addDefaultCosts) {
		return new OrderCostsViewHandler(presentationModel, login,
				addInternalCost, deviationTableEditable, addDefaultCosts,
				managerRepository);
	}

	/**
	 * Henter hjelpeklasse for artikler
	 * 
	 * @param aPresentationModel
	 * @param searching
	 * @param window
	 * @return hjelpeklasse
	 */
	public OrderArticleViewHandler<Deviation, DeviationModel> getOrderArticleViewHandler(
			PresentationModel aPresentationModel, boolean searching,
			WindowInterface window) {
		OrderArticleViewHandler<Deviation, DeviationModel> orderArticleViewHandler = new OrderArticleViewHandler<Deviation, DeviationModel>(
				aPresentationModel, searching, login, managerRepository);

		JButton buttonAddArticle = orderArticleViewHandler.getAddArticleButton(
				window, new ArticleUpdateListener(aPresentationModel, window));

		PropertyConnector conn = new PropertyConnector(
				buttonAddArticle,
				"enabled",
				aPresentationModel
						.getBufferedModel(DeviationModel.PROPERTY_CAN_ADD_ORDER_LINE),
				"value");
		conn.updateProperty1();

		return orderArticleViewHandler;
	}

	/**
	 * Lytter på om artikler legges til
	 * 
	 * @author atle.brekka
	 */
	private class ArticleUpdateListener implements UpdateableListener {
		private PresentationModel presentationModel;

		private WindowInterface window;

		/**
		 * @param aPresentationModel
		 * @param aWindow
		 */
		public ArticleUpdateListener(PresentationModel aPresentationModel,
				WindowInterface aWindow) {
			presentationModel = aPresentationModel;
			window = aWindow;
		}

		/**
		 * @see no.ugland.utransprod.gui.model.UpdateableListener#afterAdded()
		 */
		public void afterAdded() {
			if (!(Boolean) presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_IS_POST_SHIPMENT)
					&& ((presentationModel
							.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_ID) == null || presentationModel
							.getBufferedValue(DeviationModel.PROPERTY_POST_SHIPMENT) == null) && ((ArrayListModel) presentationModel
							.getBufferedValue(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL))
							.size() != 0)) {
				presentationModel.setBufferedValue(
						DeviationModel.PROPERTY_CAN_SET_POST_SHIPMENT, true);
			}

		}

		/**
		 * @see no.ugland.utransprod.gui.model.UpdateableListener#beforeAdded()
		 */
		public boolean beforeAdded() {
			PostShipment postShipment = (PostShipment) presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_POST_SHIPMENT);
			if (postShipment != null && postShipment.getSent() != null) {
				Util
						.showErrorDialog(window, "Sent!",
								"Kan ikke legge til artikkel til etterlevering som er sendt!");
				return false;
			}
			return true;

		}

	}

	/**
	 * @param handler
	 * @param object
	 * @param searching
	 * @return view
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
	 *      java.lang.Object, boolean)
	 */
	@Override
	protected AbstractEditView<DeviationModel, Deviation> getEditView(
			AbstractViewHandler<Deviation, DeviationModel> handler,
			Deviation object, boolean searching) {
		if (object != null) {
			boolean addCost = false;
			if (object.getDeviationId() != null) {
				((DeviationManager) overviewManager).lazyLoad(object,
						new LazyLoadDeviationEnum[] {
								LazyLoadDeviationEnum.COMMENTS,
								LazyLoadDeviationEnum.ORDER_COSTS,
								LazyLoadDeviationEnum.ORDER_LINES,
								LazyLoadDeviationEnum.ORDER_LINE_ORDER_LINES });
			} else {
				addCost = true;
			}

			return new EditDeviationView(searching, new DeviationModel(object,
					false), this, false, addCost);
		}
		return null;
	}

	/**
	 * Registrerer nytt avvik
	 * 
	 * @param applicationUser
	 * @param order
	 * @param userType
	 * @param window
	 */
	public void registerDeviation(Order order, WindowInterface window) {
		Deviation deviation = new Deviation();
		deviation.setOrder(order);
		openEditView(deviation, false, window);
	}

	public void showDeviation(Deviation deviation, WindowInterface window) {
		if (deviation != null) {
			openEditView(deviation, false, window);
		}
	}

	/**
	 * Lager panel med andre avvik for samme ordre som gjeldende avvik
	 * 
	 * @param window
	 * @param presentationModel
	 * @param deviation
	 * @return panel
	 */
	public JPanel getDeviationPane(WindowInterface window,
			PresentationModel presentationModel, Deviation deviation) {
		Order order1 = (Order) presentationModel
				.getBufferedValue(DeviationModel.PROPERTY_ORDER);
		deviationViewHandlerOtherDeviations = new DeviationViewHandler(login,
				managerRepository, preventiveActionViewHandler, order1, true,
				true, false, deviation, deviationTableEditable);

		DeviationOverviewView deviationOverviewView = new DeviationOverviewView(
				preventiveActionViewHandler,
				deviationViewHandlerOtherDeviations, false, order1, true,
				false, false, currentDeviation, false);
		JPanel panel = deviationOverviewView.buildDeviationPanel(window, true);
		return panel;
	}

	/**
	 * Sjekker om knapper for editering skal være med. Dersom det er et panel
	 * med andre avvik for samme ordre som gjeldende avvik skal det ikke vises
	 * knapper
	 * 
	 * @return true dersom knapper skal vises
	 */
	public boolean useButtons() {
		if (currentDeviation != null) {
			return false;
		}
		return true;
	}

	/**
	 * Henter ut antall andre avvik for samme ordre som gjeldende avvik
	 * 
	 * @return antall andre avvik
	 */
	public int getNumberOfOtherDeviations() {
		if (deviationViewHandlerOtherDeviations != null) {
			return deviationViewHandlerOtherDeviations.getNoOfObjects();
		}
		return 0;
	}

	/**
	 * Endrer order
	 * 
	 * @param aOrder
	 */
	public void changeOrder(Order aOrder) {
		order = aOrder;
		initObjects();
	}

	private class CommentSelectionListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			setButtonEditCommentEnablement();

		}

	}

	private void setButtonEditCommentEnablement() {
		boolean buttonEnabled = false;
		if (orderCommentsSelectionList.hasSelection()) {
			buttonEnabled = isNewComment();
		}
		buttonEditComment.setEnabled(buttonEnabled);

	}

	private boolean isNewComment() {
		OrderComment orderComment = (OrderComment) orderCommentsSelectionList
				.getSelection();
		if (orderComment != null) {
			return orderComment.isNew();
		}
		return false;
	}

	public boolean openEditViewExt(Deviation object, boolean searching,
			WindowInterface parentWindow) {
		return false;
	}

	@Override
	void afterSaveObject(Deviation object, WindowInterface window) {
	}

	private class DeviationStatusListener implements PropertyChangeListener {
		private PresentationModel presentationModel;

		public DeviationStatusListener(PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			String closedDate = (String) presentationModel
					.getBufferedValue(DeviationModel.PROPERTY_DATE_CLOSED_STRING);
			if (closedDate == null) {
				DeviationStatus status = (DeviationStatus) presentationModel
						.getBufferedValue(DeviationModel.PROPERTY_DEVIATION_STATUS);
				if (Util.convertNumberToBoolean(status != null ? status
						.getDeviationDone() : null)) {
					presentationModel.setBufferedValue(
							DeviationModel.PROPERTY_DATE_CLOSED_STRING, Util
									.getCurrentDateAsDateString());
				}
			}

		}

	}
}
