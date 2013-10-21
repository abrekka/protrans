package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.gui.StartWindowEnum;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for brukertype
 * 
 * @author atle.brekka
 * 
 */
public class UserTypeModel extends AbstractModel<UserType, UserTypeModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_DESCRIPTION = "description";

	/**
	 * 
	 */
	public static final String PROPERTY_STARTUP_WINDOW = "startupWindow";

	/**
	 * 
	 */
	public static final String PROPERTY_IS_ADMIN_BOOLEAN = "isAdminBoolean";

	/**
	 * 
	 */
	public static final String PROPERTY_USER_TYPE_ACCESS_LIST = "userTypeAccessList";

	/**
	 * 
	 */
	public static final String PROPERTY_STARTUP_WINDOW_ENUM = "startupWindowEnum";

	/**
	 * 
	 */
	private List<UserTypeAccess> userTypeAccessList;

	/**
	 * @param object
	 */
	public UserTypeModel(UserType object) {
		super(object);

		userTypeAccessList = new ArrayList<UserTypeAccess>();
		if (object.getUserTypeAccesses() != null) {
			userTypeAccessList.addAll(object.getUserTypeAccesses());
		}
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return object.getDescription();
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		String oldDesc = getDescription();
		object.setDescription(description);
		firePropertyChange(PROPERTY_DESCRIPTION, oldDesc, description);
	}

	/**
	 * @return oppstartsvindu
	 */
	public String getStartupWindow() {
		return object.getStartupWindow();
	}

	/**
	 * @param startupWindow
	 */
	public void setStartupWindow(String startupWindow) {
		String oldWin = getStartupWindow();
		object.setStartupWindow(startupWindow);
		firePropertyChange(PROPERTY_STARTUP_WINDOW, oldWin, startupWindow);
	}

	/**
	 * @return enum for oppstartsvindu
	 */
	public StartWindowEnum getStartupWindowEnum() {
		return object.getStartupWindowEnum();
	}

	/**
	 * @param startupWindowEnum
	 */
	public void setStartupWindowEnum(StartWindowEnum startupWindowEnum) {
		StartWindowEnum oldWin = getStartupWindowEnum();
		object.setStartupWindowEnum(startupWindowEnum);
		firePropertyChange(PROPERTY_STARTUP_WINDOW_ENUM, oldWin,
				startupWindowEnum);
	}

	/**
	 * @return true dersom administrator
	 */
	public Boolean getIsAdminBoolean() {
		return Util.convertNumberToBoolean(object.getIsAdmin());
	}

	/**
	 * @param isAdmin
	 */
	public void setIsAdminBoolean(Boolean isAdmin) {
		Boolean oldAdmin = getIsAdminBoolean();
		object.setIsAdmin(Util.convertBooleanToNumber(isAdmin));
		firePropertyChange(PROPERTY_IS_ADMIN_BOOLEAN, oldAdmin, isAdmin);
	}

	/**
	 * @return akksessliste
	 */
	public List<UserTypeAccess> getUserTypeAccessList() {
		return new ArrayList<UserTypeAccess>(userTypeAccessList);
	}

	/**
	 * @param list
	 */
	public void setUserTypeAccessList(List<UserTypeAccess> list) {
		userTypeAccessList.clear();
		userTypeAccessList.addAll(list);
		firePropertyChange(PROPERTY_USER_TYPE_ACCESS_LIST, list, list);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_DESCRIPTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_STARTUP_WINDOW)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_USER_TYPE_ACCESS_LIST)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_STARTUP_WINDOW_ENUM)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserTypeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		UserTypeModel model = new UserTypeModel(new UserType());
		model.setDescription((String) presentationModel
				.getBufferedValue(PROPERTY_DESCRIPTION));
		model.setStartupWindow((String) presentationModel
				.getBufferedValue(PROPERTY_STARTUP_WINDOW));
		model.setUserTypeAccessList((List<UserTypeAccess>) presentationModel
				.getBufferedValue(PROPERTY_USER_TYPE_ACCESS_LIST));
		model.setStartupWindowEnum((StartWindowEnum) presentationModel
				.getBufferedValue(PROPERTY_STARTUP_WINDOW_ENUM));
		return model;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#viewToModel()
	 */
	@Override
	public void viewToModel() {
		Set<UserTypeAccess> userTypeAccesses = object.getUserTypeAccesses();
		if (userTypeAccesses == null) {
			userTypeAccesses = new HashSet<UserTypeAccess>();
		}
		userTypeAccesses.clear();
		userTypeAccesses.addAll(userTypeAccessList);
		object.setUserTypeAccesses(userTypeAccesses);
	}

}
