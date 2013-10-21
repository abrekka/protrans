package no.ugland.utransprod.model;

import java.util.Set;

import no.ugland.utransprod.gui.StartWindowEnum;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som representerer tabell USER_TYPE
 * 
 * @author atle.brekka
 * 
 */
public class UserType extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer userTypeId;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private String startupWindow;

	/**
	 * 
	 */
	private Set<UserTypeAccess> userTypeAccesses;

	/**
	 * 
	 */
	private Integer isAdmin;

	/**
	 * 
	 */
	public UserType() {
		super();
	}

	/**
	 * @param userTypeId
	 * @param description
	 * @param startupWindow
	 * @param userTypeAccesses
	 * @param isAdmin
	 */
	public UserType(Integer userTypeId, String description,
			String startupWindow, Set<UserTypeAccess> userTypeAccesses,
			Integer isAdmin) {
		super();
		this.userTypeId = userTypeId;
		this.description = description;
		this.startupWindow = startupWindow;
		this.userTypeAccesses = userTypeAccesses;
		this.isAdmin = isAdmin;
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return bukertypeid
	 */
	public Integer getUserTypeId() {
		return userTypeId;
	}

	/**
	 * @param userTypeId
	 */
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof UserType))
			return false;
		UserType castOther = (UserType) other;
		return new EqualsBuilder().append(description, castOther.description)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(description).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return description;
	}

	/**
	 * @return oppstartsvindu
	 */
	public String getStartupWindow() {
		return startupWindow;
	}

	/**
	 * @param startupWindow
	 */
	public void setStartupWindow(String startupWindow) {
		this.startupWindow = startupWindow;
	}

	/**
	 * @return aksesser
	 */
	public Set<UserTypeAccess> getUserTypeAccesses() {
		return userTypeAccesses;
	}

	/**
	 * @param userTypeAccesses
	 */
	public void setUserTypeAccesses(Set<UserTypeAccess> userTypeAccesses) {
		this.userTypeAccesses = userTypeAccesses;
	}

	/**
	 * @return 1 dersom administrator
	 */
	public Integer getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin
	 */
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return enum for oppstartsvindu
	 */
	public StartWindowEnum getStartupWindowEnum() {
		return StartWindowEnum.getStartWindowEnum(startupWindow);
	}

	/**
	 * @param startupWindowEnum
	 */
	public void setStartupWindowEnum(StartWindowEnum startupWindowEnum) {
		if (startupWindowEnum != null) {
			this.startupWindow = startupWindowEnum.getClassName();
		} else {
			this.startupWindow = null;
		}
	}

	/**
	 * @return true dersom administrator
	 */
	public Boolean isAdministrator() {
		return Util.convertNumberToBoolean(isAdmin);
	}
}
