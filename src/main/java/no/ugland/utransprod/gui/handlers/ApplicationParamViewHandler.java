package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditApplicationParamView;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.ApplicationParamModel;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer editering av applikasjonsparametre
 * 
 * @author atle.brekka
 */
public class ApplicationParamViewHandler extends
		DefaultAbstractViewHandler<ApplicationParam, ApplicationParamModel>
		implements ViewHandlerExt<ApplicationParam, ApplicationParamModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private List<String> userNameList;
	private final ArrayListModel listCollies;
	private final ArrayListModel listTakstolArticles;
	private final SelectionInList listColliesSelectionList;
	private final SelectionInList listTakstolArticleSelectionList;
	private EmptySelectionListener emptySelectionListenerColli;
	private EmptySelectionListener emptySelectionListenerTakstol;
	private ApplicationParam notPackageParam = null;

	/**
	 * @param aHeading
	 * @param managerName
	 * @param aUserType
	 */
	@SuppressWarnings("unchecked")
	public ApplicationParamViewHandler(String aHeading,
			final OverviewManager aOverviewManager, UserType aUserType) {
		super(aHeading, aOverviewManager, aUserType, true);
		viewHandlerExt = this;
		ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean("applicationUserManager");
		userNameList = applicationUserManager.findAllNamesNotGroup();
		listCollies = new ArrayListModel();
		listTakstolArticles = new ArrayListModel();
		listColliesSelectionList = new SelectionInList((ListModel) listCollies);
		listTakstolArticleSelectionList = new SelectionInList(
				(ListModel) listTakstolArticles);
		emptySelectionListenerColli = new EmptySelectionListener(
				listColliesSelectionList);
		listColliesSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				emptySelectionListenerColli);
		emptySelectionListenerTakstol = new EmptySelectionListener(
				listTakstolArticleSelectionList);
		listTakstolArticleSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				emptySelectionListenerTakstol);
	}

	@SuppressWarnings("unchecked")
	public JList getListCollies(PresentationModel presentationModel) {
		listCollies.clear();
		listCollies.addAll((List) presentationModel
				.getBufferedValue(ApplicationParamModel.PROPERTY_COLLI_PARAMS));
		JList list = BasicComponentFactory.createList(listColliesSelectionList);
		list.setName("ListCollies");
		return list;
	}

	@SuppressWarnings("unchecked")
	public JList getListTakstolArticles(PresentationModel presentationModel) {
		listTakstolArticles.clear();
		listTakstolArticles
				.addAll((List) presentationModel
						.getBufferedValue(ApplicationParamModel.PROPERTY_TAKSTOL_ARTICLE_PARAMS));
		JList list = BasicComponentFactory
				.createList(listTakstolArticleSelectionList);
		list.setName("ListTakstolArticles");
		return list;
	}

	public JButton getButtonAddColli(WindowInterface window,
			PresentationModel presentationModel) {
		JButton button = new JButton(new AddColliAction(window,
				presentationModel));
		button.setName("ButtonAddColli");
		return button;
	}

	public JButton getButtonAddTakstolArticle(WindowInterface window,
			PresentationModel presentationModel) {
		JButton button = new JButton(new AddTakstolArticleAction(window,
				presentationModel));
		button.setName("ButtonAddTakstolArticle");
		return button;
	}

	public JButton getButtonRemoveColli(WindowInterface window,
			PresentationModel presentationModel) {
		JButton buttonRemoveColli = new JButton(new RemoveColliAction(window,
				presentationModel));
		buttonRemoveColli.setName("ButtonRemoveColli");
		buttonRemoveColli.setEnabled(false);
		emptySelectionListenerColli.addButton(buttonRemoveColli);

		return buttonRemoveColli;
	}

	public JButton getButtonRemoveTakstolArticle(WindowInterface window,
			PresentationModel presentationModel) {
		JButton button = new JButton(new RemoveTakstolArticleAction(window,
				presentationModel));
		button.setName("ButtonRemoveTakstolArticle");
		button.setEnabled(false);
		emptySelectionListenerTakstol.addButton(button);

		return button;
	}

	/**
	 * Lager tekstfelt for hostname
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldHostName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ApplicationParamModel.PROPERTY_MAIL_HOST_NAME));
		textField.setName("TextFieldHostName");
		return textField;
	}

	/**
	 * Lager passordfelt for mailoppsett
	 * 
	 * @param presentationModel
	 * @return passordfelt
	 */
	public JPasswordField getPasswordFieldHostPassword(
			PresentationModel presentationModel) {
		JPasswordField passwordField = BasicComponentFactory
				.createPasswordField(presentationModel
						.getBufferedModel(ApplicationParamModel.PROPERTY_MAIL_HOST_PASSWORD));
		passwordField.setName("PasswordFieldHostPassword");
		return passwordField;
	}

	/**
	 * Lager tekstfelt for fra mailadresse
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldFromMail(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ApplicationParamModel.PROPERTY_MAIL_FROM_MAIL));
		textField.setName("TextFieldFromMail");
		return textField;
	}

	/**
	 * Lager tekstfelt for fra navn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldFromName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ApplicationParamModel.PROPERTY_MAIL_FROM_NAME));
		textField.setName("TextFieldFromName");
		return textField;
	}

	/**
	 * Lager tekstfelt for avviksoverskrift
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldDeviationSubject(
			PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ApplicationParamModel.PROPERTY_MAIL_DEVIATION_SUBJECT));
		textField.setName("TextFieldDeviationSubject");
		return textField;
	}

	/**
	 * Lager tekstfelt for avviksmelding
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldDeviationMessage(
			PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ApplicationParamModel.PROPERTY_MAIL_DEVIATION_MESSAGE));
		textField.setName("TextFieldDeviationMessage");
		return textField;
	}

	/**
	 * Lager tekstfelt for beskrivelse av avviksvedlegg
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldDeviationAttachmentDescription(
			PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ApplicationParamModel.PROPERTY_MAIL_DEVIATION_ATTACHMENT_DESCRIPTION));
		textField.setName("TextFieldDeviationAttachmentDescription");
		return textField;
	}

	/**
	 * Lager komboboks for avviksansvarlig
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxManager(PresentationModel presentationModel,
			String property) {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(userNameList,
				presentationModel.getBufferedModel(property)));
		comboBox.setName("ComboBoxManager");
		return comboBox;
	}

	/**
	 * @param object
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(ApplicationParam object) {
		return null;
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
	public CheckObject checkSaveObject(ApplicationParamModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "parameter";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "ApplicationParam";
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
	protected AbstractEditView<ApplicationParamModel, ApplicationParam> getEditView(
			AbstractViewHandler<ApplicationParam, ApplicationParamModel> handler,
			ApplicationParam object, boolean searching) {
		return new EditApplicationParamView(searching,
				new ApplicationParamModel(object), this);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public ApplicationParam getNewObject() {
		return new ApplicationParam();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new ApplicationParamTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "400";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Parametre";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Parametre");
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		table.getColumnExt(0).setPreferredWidth(150);
		table.getColumnExt(1).setPreferredWidth(200);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.ViewHandlerExt#saveObjectExt(no.ugland.utransprod.gui.model.AbstractModel,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean saveObjectExt(
			AbstractModel<ApplicationParam, ApplicationParamModel> objectModel,
			WindowInterface window) {
		Collection<ApplicationParam> mailParams = ApplicationParamModel
				.getMailParams();

		if (mailParams != null) {
			for (ApplicationParam param : mailParams) {
				((ApplicationParamManager) overviewManager)
						.saveApplicationParam(param);
			}
		}

		ApplicationParam deviationManagerParam = ApplicationParamModel
				.getDeviationManagerParam();
		if (deviationManagerParam != null) {
			((ApplicationParamManager) overviewManager)
					.saveApplicationParam(deviationManagerParam);
		}
		List<ApplicationParam> colliParams = ((ApplicationParamModel) objectModel)
				.getColliParams();
		if (colliParams != null) {
			for (ApplicationParam param : colliParams) {
				((ApplicationParamManager) overviewManager)
						.saveApplicationParam(param);
			}
		}
		List<ApplicationParam> takstolArticleParams = ((ApplicationParamModel) objectModel)
				.getTakstolArticleParams();
		if (takstolArticleParams != null) {
			for (ApplicationParam param : takstolArticleParams) {
				((ApplicationParamManager) overviewManager)
						.saveApplicationParam(param);
			}
		}
		if (notPackageParam != null) {
			((ApplicationParamManager) overviewManager)
					.saveApplicationParam(notPackageParam);
		}

		ApplicationParam hmsManagerParam = ApplicationParamModel
				.getHmsManagerParam();
		if (hmsManagerParam != null) {
			((ApplicationParamManager) overviewManager)
					.saveApplicationParam(hmsManagerParam);
		}
		return true;
	}

	/**
	 * Henter vindusstørrelse for mailoppsett
	 * 
	 * @return størrelse
	 */
	public Dimension getMailSetupWindowSize() {
		return new Dimension(340, 150);
	}

	/**
	 * Henter vindusstørrelse for avviksansvarlig
	 * 
	 * @return størrelse
	 */
	public Dimension getDeviationManagerWindowSize() {
		return new Dimension(320, 100);
	}

	public boolean openEditViewExt(ApplicationParam object, boolean searching,
			WindowInterface parentWindow) {
		return false;
	}

	private class AddColliAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;
		private PresentationModel presentationModel;

		public AddColliAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Ny kolli...");
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			addColli(window, presentationModel);

		}
	}

	private class AddTakstolArticleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;
		private PresentationModel presentationModel;

		public AddTakstolArticleAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Ny artikkel...");
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			addTakstolArticle(window, presentationModel);

		}
	}

	private class RemoveColliAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;
		private PresentationModel presentationModel;

		public RemoveColliAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Slett kolli");
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			removeColli(window, presentationModel);

		}
	}

	private class RemoveTakstolArticleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;
		private PresentationModel presentationModel;

		public RemoveTakstolArticleAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Slett artikkel");
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			removeTakstolArticle(window, presentationModel);

		}
	}

	private void removeColli(WindowInterface window,
			PresentationModel presentationModel) {
		ApplicationParam param = (ApplicationParam) listColliesSelectionList
				.getSelection();
		if (param != null) {
			if (Util.showConfirmDialog(window, "Slette",
					"Vil du virkelig slette?")) {
				listCollies.remove(param);
				presentationModel.setBufferedValue(
						ApplicationParamModel.PROPERTY_COLLI_PARAMS,
						listCollies);
				presentationModel.triggerCommit();
				((ApplicationParamManager) overviewManager).removeObject(param);
			}
		}
	}

	private void removeTakstolArticle(WindowInterface window,
			PresentationModel presentationModel) {
		ApplicationParam param = (ApplicationParam) listTakstolArticleSelectionList
				.getSelection();
		if (param != null) {
			if (Util.showConfirmDialog(window, "Slette",
					"Vil du virkelig slette?")) {
				listTakstolArticles.remove(param);
				presentationModel.setBufferedValue(
						ApplicationParamModel.PROPERTY_TAKSTOL_ARTICLE_PARAMS,
						listTakstolArticles);
				String articleName = param.getParamValue();
				notPackageParam = (ApplicationParam) presentationModel
						.getBufferedValue(ApplicationParamModel.PROPERTY_NOT_PACKAGE_PARAM);
				String notPackageValue = notPackageParam.getParamValue();
				notPackageValue = notPackageValue.replaceAll(articleName, "");
				notPackageValue = notPackageValue.replaceAll(";;", ";");
				if (notPackageValue.endsWith(";")) {
					notPackageValue = notPackageValue.substring(0,
							notPackageValue.length() - 1);
				}
				notPackageParam.setParamValue(notPackageValue);
				presentationModel.triggerCommit();
				((ApplicationParamManager) overviewManager).removeObject(param);
				((ApplicationParamManager) overviewManager)
						.saveApplicationParam(notPackageParam);
			}
		}
	}

	private void addColli(WindowInterface window,
			PresentationModel presentationModel) {
		String colliSetup = Util.showInputDialog(window, "Kollioppsett",
				"Kollioppsett:");
		if (colliSetup.length() != 0) {
			if (colliSetup.indexOf(";") == -1) {
				Util
						.showErrorDialog(window, "Feil",
								"Kollioppsett må settes slik: <pakkenavn>;<artikkeltype>");
			} else {
				String paramName = "kolli_" + (listCollies.size() + 1);
				ApplicationParam param = new ApplicationParam(null, paramName,
						colliSetup, null);
				listCollies.add(param);
				presentationModel.setBufferedValue(
						ApplicationParamModel.PROPERTY_COLLI_PARAMS,
						listCollies);
			}
		}
	}

	private void addTakstolArticle(WindowInterface window,
			PresentationModel presentationModel) {
		String takstolArticleSetup = Util.showInputDialog(window,
				"Takstoloppsett", "Artikkel:");
		if (takstolArticleSetup.length() != 0) {
			String paramName = "takstol_artikkel_"
					+ (listTakstolArticles.size() + 1);
			ApplicationParam param = new ApplicationParam(null, paramName,
					takstolArticleSetup, null);
			listTakstolArticles.add(param);
			presentationModel.setBufferedValue(
					ApplicationParamModel.PROPERTY_TAKSTOL_ARTICLE_PARAMS,
					listTakstolArticles);
			notPackageParam = (ApplicationParam) presentationModel
					.getBufferedValue(ApplicationParamModel.PROPERTY_NOT_PACKAGE_PARAM);
			notPackageParam.setParamValue(notPackageParam.getParamValue() + ";"
					+ takstolArticleSetup);
			presentationModel.setBufferedValue(
					ApplicationParamModel.PROPERTY_NOT_PACKAGE_PARAM,
					notPackageParam);
		}
	}

	private static final class ApplicationParamTableModel extends
			AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Navn", "Verdi" };

		/**
		 * @param listModel
		 */
		ApplicationParamTableModel(ListModel listModel) {
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
			ApplicationParam applicationParam = (ApplicationParam) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return applicationParam.getParamName();
			case 1:
				return applicationParam.getParamValue();
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
			case 1:
				return String.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	public JTextField getTextFieldParamName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ApplicationParamModel.PROPERTY_PARAM_NAME));
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldParamValue(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel
				.getBufferedModel(ApplicationParamModel.PROPERTY_PARAM_VALUE),
				false);
	}
}
