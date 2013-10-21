package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditApplicationUserView;
import no.ugland.utransprod.gui.model.ApplicationUserModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.UserProductAreaGroup;
import no.ugland.utransprod.model.UserRole;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer brukere
 * 
 * @author atle.brekka
 * 
 */
public class ApplicationUserViewHandler extends
		DefaultAbstractViewHandler<ApplicationUser, ApplicationUserModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	JButton buttonRemoveUserType;

	/**
	 * 
	 */
	JButton buttonRemoveProductAreaGroup;

	/**
	 * 
	 */
	private List<UserType> userTypes;

	/**
	 * 
	 */
	private List<ProductAreaGroup> productAreaGroups;;

	/**
	 * 
	 */
	final SelectionInList userRoleSelectionList;

	/**
	 * 
	 */
	final SelectionInList userProductAreaGroupSelectionList;

	/**
	 * 
	 */
	private final ArrayListModel userRoleList;

	/**
	 * 
	 */
	private final ArrayListModel userProductAreaGroupList;

	/**
	 * 
	 */
	private List<JobFunction> jobFunctionList;

	/**
	 * 
	 */
	private List<ProductArea> productAreaList;

	/**
	 * @param userType
	 */
	public ApplicationUserViewHandler(Login aLogin,ApplicationUserManager applicationUserManager) {
		super("Brukere", applicationUserManager, aLogin.getUserType(), true);

		UserTypeManager userTypeManager = (UserTypeManager) ModelUtil
				.getBean("userTypeManager");
		userTypes = userTypeManager.findAll();
		userRoleList = new ArrayListModel();
		userRoleSelectionList = new SelectionInList((ListModel) userRoleList);
		userRoleSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new EmptyListenerUserRole());

		ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
				.getBean("productAreaGroupManager");
		productAreaGroups = productAreaGroupManager.findAll();
		userProductAreaGroupList = new ArrayListModel();
		userProductAreaGroupSelectionList = new SelectionInList(
				(ListModel) userProductAreaGroupList);
		userProductAreaGroupSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new EmptyListenerProductAreaGroup());

		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean("jobFunctionManager");
		jobFunctionList = jobFunctionManager.findAll();

		ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean("productAreaManager");
		productAreaList = productAreaManager.findAll();
	}

	/**
	 * Lager knapp for å legge til bruker
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonAddUserType(WindowInterface window,
			PresentationModel presentationModel) {
		return new NewButton("profil", new ProfileUpdater(presentationModel),
				window);
	}

	/**
	 * Lager knapp fpr å legge til produktområde
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonAddProductAreaGroup(WindowInterface window,
			PresentationModel presentationModel) {
		return new NewButton("produktområde", new ProductAreaGroupUpdater(
				presentationModel), window, "Nytt");
	}

	/**
	 * Lager knapp for å slette bruker
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonRemoveUserType(WindowInterface window,
			PresentationModel presentationModel) {
		buttonRemoveUserType = new DeleteButton("profil", new ProfileUpdater(
				presentationModel), window);
		buttonRemoveUserType.setEnabled(false);
		return buttonRemoveUserType;
	}

	/**
	 * Lager knapp for å fjerne produktområde
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonRemoveProductAreaGroup(WindowInterface window,
			PresentationModel presentationModel) {
		buttonRemoveProductAreaGroup = new DeleteButton("produktområde",
				new ProductAreaGroupUpdater(presentationModel), window);
		buttonRemoveProductAreaGroup.setEnabled(false);
		return buttonRemoveProductAreaGroup;
	}

	/**
	 * @param user
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(ApplicationUser user) {
		if (((ApplicationUserManager) overviewManager)
				.isUserFunctionManager(user)) {
			return new CheckObject("Kan ikke slette bruker som er leder for en funksjon",false);
		}
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
	public CheckObject checkSaveObject(ApplicationUserModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "bruker";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "ApplicationUser";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public ApplicationUser getNewObject() {
		return new ApplicationUser();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new ApplicationUserTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "220dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Brukere";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(700, 400);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		// Brukernavn
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(100);

		// Fornavn
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(100);

		// Etternavn
		col = table.getColumnModel().getColumn(2);
		col.setPreferredWidth(100);

		// Gruppebruker
		col = table.getColumnModel().getColumn(3);
		col.setPreferredWidth(80);
		// Produktområde
		table.getColumnExt(4).setPreferredWidth(100);

	}

	/**
	 * Henter brukerroller
	 * 
	 * @param presentationModel
	 * @return brukerroller
	 */
	public SelectionInList getUserRoleSelectionList(
			PresentationModel presentationModel) {
		userRoleList.clear();
		userRoleList
				.addAll((List) presentationModel
						.getBufferedValue(ApplicationUserModel.PROPERTY_USER_ROLE_LIST));
		return userRoleSelectionList;
	}

	/**
	 * Henter liste med produktområde
	 * 
	 * @param presentationModel
	 * @return liste
	 */
	public SelectionInList getUserProductAreaGroupSelectionList(
			PresentationModel presentationModel) {
		userProductAreaGroupList.clear();
		userProductAreaGroupList
				.addAll((List) presentationModel
						.getBufferedValue(ApplicationUserModel.PROPERTY_USER_PRODUCT_AREA_GROUP_LIST));
		return userProductAreaGroupSelectionList;
	}

	/**
	 * Funksjonsliste
	 * 
	 * @return funksjoner
	 */
	public List<JobFunction> getJobFunctionList() {
		return jobFunctionList;
	}

	/**
	 * Liste med alle produktområder
	 * 
	 * @return liste
	 */
	public List<ProductArea> getProductAreaList() {
		return productAreaList;
	}

	/**
	 * Tabellmodell for brukere
	 * 
	 * @author atle.brekka
	 * 
	 */
	private static final class ApplicationUserTableModel extends
			AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Brukernavn", "Fornavn",
				"Etternavn", "Gruppebruker", "Produktområde" };

		/**
		 * @param listModel
		 */
		ApplicationUserTableModel(ListModel listModel) {
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
			ApplicationUser applicationUser = (ApplicationUser) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return applicationUser.getUserName();
			case 1:
				return applicationUser.getFirstName();
			case 2:
				return applicationUser.getLastName();
			case 3:
				return applicationUser.getGroupUser();
			case 4:
				return applicationUser.getProductArea();
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
			case 2:
			case 3:
				return String.class;
			case 4:
				return ProductArea.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * Legg til brukertype
	 * 
	 * @param window
	 * @param presentationModel
	 */
	void addUserType(WindowInterface window, PresentationModel presentationModel) {
		UserType selectedUserType = (UserType) JOptionPane.showInputDialog(
				window.getComponent(), "Velg profil", "Legg til profil",
				JOptionPane.INFORMATION_MESSAGE, null, userTypes.toArray(),
				null);
		if (selectedUserType != null && presentationModel != null) {
			UserRole userRole = new UserRole(null, selectedUserType,
					((ApplicationUserModel) presentationModel.getBean())
							.getObject());
			userRoleList.add(userRole);
			presentationModel.setBufferedValue(
					ApplicationUserModel.PROPERTY_USER_ROLE_LIST, userRoleList);
			((ApplicationUserModel) presentationModel.getBean())
					.firePropertyChanged();
			// BufferedValueModel bufferedValueModel =
			// presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_USER_ROLE_LIST);
			// bufferedValueModel.setValue(userRoleList);
		}
	}

	/**
	 * Legger til produktområde
	 * 
	 * @param window
	 * @param presentationModel
	 */
	void addProductAreaGroup(WindowInterface window,
			PresentationModel presentationModel) {
		ApplicationUser user = ((ApplicationUserModel) presentationModel
				.getBean()).getObject();
		List<ProductAreaGroup> groups = new ArrayList<ProductAreaGroup>(
				productAreaGroups);
        
		if (user.getProductArea() != null) {
			groups.remove(user.getProductArea().getProductAreaGroup());
		}
		ProductAreaGroup selectedGroup = (ProductAreaGroup) JOptionPane
				.showInputDialog(window.getComponent(), "Velg produktområde",
						"Legg til produktområde",
						JOptionPane.INFORMATION_MESSAGE, null,
						groups.toArray(), null);
		if (selectedGroup != null) {
			UserProductAreaGroup userGroup = new UserProductAreaGroup(null,
					selectedGroup, user);
			userProductAreaGroupList.add(userGroup);
			presentationModel.setBufferedValue(
					ApplicationUserModel.PROPERTY_USER_PRODUCT_AREA_GROUP_LIST,
					userProductAreaGroupList);
			((ApplicationUserModel) presentationModel.getBean())
					.firePropertyChanged();
		}
	}

	/**
	 * fjern brukertype
	 * 
	 * @param presentationModel
	 */
	void removeUserType(PresentationModel presentationModel) {
		UserRole userRole = (UserRole) userRoleSelectionList.getSelection();
		if (userRole != null) {
			userRoleList.remove(userRole);

			presentationModel.setBufferedValue(
					ApplicationUserModel.PROPERTY_USER_ROLE_LIST, userRoleList);

			// BufferedValueModel bufferedValueModel =
			// presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_USER_ROLE_LIST);
			// bufferedValueModel.setValue(userRoleList);
		}
	}

	/**
	 * Fjerner produktområde
	 * 
	 * @param presentationModel
	 */
	void removeProductAreaGroup(PresentationModel presentationModel) {
		UserProductAreaGroup group = (UserProductAreaGroup) userProductAreaGroupSelectionList
				.getSelection();
		if (group != null) {
			userProductAreaGroupList.remove(group);

			presentationModel.setBufferedValue(
					ApplicationUserModel.PROPERTY_USER_PRODUCT_AREA_GROUP_LIST,
					userProductAreaGroupList);
		}
	}

	/**
	 * Håndterer valg av brukertype
	 * 
	 * @author atle.brekka
	 * 
	 */
	class EmptyListenerUserRole implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			buttonRemoveUserType.setEnabled(userRoleSelectionList
					.hasSelection());

		}

	}

	/**
	 * Håndterer valg i liste over produktområder
	 * 
	 * @author atle.brekka
	 * 
	 */
	class EmptyListenerProductAreaGroup implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			buttonRemoveProductAreaGroup
					.setEnabled(userProductAreaGroupSelectionList
							.hasSelection());

		}

	}

	/**
	 * Håndterer editeing av brukerprofiler
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class ProfileUpdater implements Updateable {
		/**
		 * 
		 */
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public ProfileUpdater(PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
		 */
		public boolean doDelete(WindowInterface window) {
			removeUserType(presentationModel);
			return true;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doNew(WindowInterface window) {
			addUserType(window, presentationModel);

		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doRefresh(WindowInterface window) {
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doSave(WindowInterface window1) {
		}

	}

	/**
	 * Håndterer legge til og fjerne produktområde
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class ProductAreaGroupUpdater implements Updateable {
		/**
		 * 
		 */
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public ProductAreaGroupUpdater(PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
		 */
		public boolean doDelete(WindowInterface window) {
			removeProductAreaGroup(presentationModel);
			return true;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doNew(WindowInterface window) {
			addProductAreaGroup(window, presentationModel);

		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doRefresh(WindowInterface window) {
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doSave(WindowInterface window1) {
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return Util.convertNumberToBoolean(userType.getIsAdmin());
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
	protected AbstractEditView<ApplicationUserModel, ApplicationUser> getEditView(
			AbstractViewHandler<ApplicationUser, ApplicationUserModel> handler,
			ApplicationUser object, boolean searching) {
		overviewManager.lazyLoad(object,
				new LazyLoadEnum[][] {
						{LazyLoadEnum.USER_ROLES,LazyLoadEnum.NONE},
						{LazyLoadEnum.USER_PRODUCT_AREA_GROUPS,LazyLoadEnum.NONE} });
		currentObject = object;
		return new EditApplicationUserView(searching, new ApplicationUserModel(
				object), this);
	}

    
}
