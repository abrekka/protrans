package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.UserProductAreaGroup;
import no.ugland.utransprod.model.UserRole;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for bruker
 * 
 * @author atle.brekka
 * 
 */
public class ApplicationUserModel extends
		AbstractModel<ApplicationUser, ApplicationUserModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_FIRST_NAME = "firstName";

	/**
	 * 
	 */
	public static final String PROPERTY_GROUP_USER = "groupUser";

	/**
	 * 
	 */
	public static final String PROPERTY_LAST_NAME = "lastName";

	/**
	 * 
	 */
	public static final String PROPERTY_PASSWORD = "password";

	/**
	 * 
	 */
	public static final String PROPERTY_USER_NAME = "userName";

	/**
	 * 
	 */
	public static final String PROPERTY_USER_ROLE_LIST = "userRoleList";

	/**
	 * 
	 */
	public static final String PROPERTY_JOB_FUNCTION = "jobFunction";

	/**
	 * 
	 */
	public static final String PROPERTY_PRODUCT_AREA = "productArea";

	/**
	 * 
	 */
	public static final String PROPERTY_USER_PRODUCT_AREA_GROUP_LIST = "userProductAreaGroupList";

	/**
	 * 
	 */
	private List<UserRole> userRoleList;

	/**
	 * 
	 */
	private List<UserProductAreaGroup> userProductAreaGroupList;

	/**
	 * @param object
	 */
	public ApplicationUserModel(ApplicationUser object) {
		super(object);

		userRoleList = new ArrayList<UserRole>();

		if (object.getUserRoles() != null) {
			userRoleList.addAll(object.getUserRoles());
		}

		userProductAreaGroupList = new ArrayList<UserProductAreaGroup>();

		if (object.getUserProductAreaGroups() != null) {
			userProductAreaGroupList.addAll(object.getUserProductAreaGroups());
		}
	}

	/**
	 * @return fornavn
	 */
	public String getFirstName() {
		return object.getFirstName();
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		String oldName = getFirstName();
		object.setFirstName(firstName);
		firePropertyChange(PROPERTY_FIRST_NAME, oldName, firstName);
	}

	/**
	 * @return gruppebruker
	 */
	public String getGroupUser() {
		return object.getGroupUser();
	}

	/**
	 * @param groupUser
	 */
	public void setGroupUser(String groupUser) {
		String oldGroup = getGroupUser();
		object.setGroupUser(groupUser);
		firePropertyChange(PROPERTY_GROUP_USER, oldGroup, groupUser);
	}

	/**
	 * @return etternavn
	 */
	public String getLastName() {
		return object.getLastName();
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		String oldName = getLastName();
		object.setLastName(lastName);
		firePropertyChange(PROPERTY_LAST_NAME, oldName, lastName);
	}

	/**
	 * @return passord
	 */
	public String getPassword() {
		return object.getPassword();
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		String oldPass = getPassword();
		object.setPassword(password);
		firePropertyChange(PROPERTY_PASSWORD, oldPass, password);
	}

	/**
	 * @return brukernavn
	 */
	public String getUserName() {
		return object.getUserName();
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		String oldName = getUserName();
		object.setUserName(userName);
		firePropertyChange(PROPERTY_USER_NAME, oldName, userName);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_FIRST_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_GROUP_USER)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_LAST_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PASSWORD)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_USER_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_USER_ROLE_LIST)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_JOB_FUNCTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PRODUCT_AREA)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(
				PROPERTY_USER_PRODUCT_AREA_GROUP_LIST).addValueChangeListener(
				listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ApplicationUserModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		ApplicationUserModel model = new ApplicationUserModel(
				new ApplicationUser());
		model.setFirstName((String) presentationModel
				.getBufferedValue(PROPERTY_FIRST_NAME));
		model.setGroupUser((String) presentationModel
				.getBufferedValue(PROPERTY_GROUP_USER));
		model.setLastName((String) presentationModel
				.getBufferedValue(PROPERTY_LAST_NAME));
		model.setPassword((String) presentationModel
				.getBufferedValue(PROPERTY_PASSWORD));
		model.setUserName((String) presentationModel
				.getBufferedValue(PROPERTY_USER_NAME));
		model.setUserRoleList((List<UserRole>) presentationModel
				.getBufferedValue(PROPERTY_USER_ROLE_LIST));
		model.setJobFunction((JobFunction) presentationModel
				.getBufferedValue(PROPERTY_JOB_FUNCTION));
		model.setProductArea((ProductArea) presentationModel
				.getBufferedValue(PROPERTY_PRODUCT_AREA));
		model
				.setUserProductAreaGroupList((List<UserProductAreaGroup>) presentationModel
						.getBufferedValue(PROPERTY_USER_PRODUCT_AREA_GROUP_LIST));
		return model;
	}

	/**
	 * @return brukerrolleliste
	 */
	public List<UserRole> getUserRoleList() {
		return new ArrayList<UserRole>(userRoleList);
	}

	/**
	 * @param userRoleList
	 */
	public void setUserRoleList(List<UserRole> userRoleList) {
		List<UserRole> oldRoles = getUserRoleList();
		if (userRoleList != null) {
			this.userRoleList = new ArrayList<UserRole>(userRoleList);
		} else {
			this.userRoleList.clear();
		}
		firePropertyChange(PROPERTY_USER_ROLE_LIST, oldRoles, userRoleList);
	}

	/**
	 * @return produktområder
	 */
	public List<UserProductAreaGroup> getUserProductAreaGroupList() {
		return new ArrayList<UserProductAreaGroup>(userProductAreaGroupList);
	}

	/**
	 * @param userProductAreaGroupList
	 */
	public void setUserProductAreaGroupList(
			List<UserProductAreaGroup> userProductAreaGroupList) {
		List<UserProductAreaGroup> oldGroups = getUserProductAreaGroupList();
		if (userProductAreaGroupList != null) {
			this.userProductAreaGroupList = new ArrayList<UserProductAreaGroup>(
					userProductAreaGroupList);
		} else {
			this.userProductAreaGroupList.clear();
		}
		firePropertyChange(PROPERTY_USER_PRODUCT_AREA_GROUP_LIST, oldGroups,
				userProductAreaGroupList);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#viewToModel()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void viewToModel() {
		if (userRoleList != null) {
			Set<UserRole> userRoles = object.getUserRoles();
			if (userRoles == null) {
				userRoles = new HashSet<UserRole>();
			}
			userRoles.clear();
			userRoles.addAll(userRoleList);
			object.setUserRoles(userRoles);
		}

		if (userProductAreaGroupList != null) {
			Set<UserProductAreaGroup> groups = object
					.getUserProductAreaGroups();
			if (groups == null) {
				groups = new HashSet<UserProductAreaGroup>();
			}
			groups.clear();
			groups.addAll(userProductAreaGroupList);
			object.setUserProductAreaGroups(groups);
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#modelToView()
	 */
	@Override
	public void modelToView() {
		if (object.getUserRoles() != null) {
			userRoleList.clear();
			userRoleList.addAll(object.getUserRoles());
		}
		if (object.getUserProductAreaGroups() != null) {
			userProductAreaGroupList.clear();
			userProductAreaGroupList.addAll(object.getUserProductAreaGroups());
		}
	}

	/**
	 * @return funksjon
	 */
	public JobFunction getJobFunction() {
		return object.getJobFunction();
	}

	/**
	 * @param jobFunction
	 */
	public void setJobFunction(JobFunction jobFunction) {
		JobFunction oldFunction = getJobFunction();
		object.setJobFunction(jobFunction);
		firePropertyChange(PROPERTY_JOB_FUNCTION, oldFunction, jobFunction);
	}

	/**
	 * @return produktområde
	 */
	public ProductArea getProductArea() {
		return object.getProductArea();
	}

	/**
	 * @param productArea
	 */
	public void setProductArea(ProductArea productArea) {
		ProductArea oldArea = getProductArea();
		object.setProductArea(productArea);
		firePropertyChange(PROPERTY_PRODUCT_AREA, oldArea, productArea);
	}

	/**
	 * Fyrer event om at flere attributter har endret seg
	 */
	public void firePropertyChanged() {
		this.fireMultiplePropertiesChanged();
	}
}
