package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som representerer tabell USER_ROLE
 * 
 * @author atle.brekka
 * 
 */
public class UserRole extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer roleId;

	/**
	 * 
	 */
	private UserType userType;

	/**
	 * 
	 */
	private ApplicationUser applicationUser;

	/**
	 * 
	 */
	public UserRole() {
		super();
	}

	/**
	 * @param roleId
	 * @param userType
	 * @param applicationUser
	 */
	public UserRole(Integer roleId, UserType userType,
			ApplicationUser applicationUser) {
		super();
		this.roleId = roleId;
		this.userType = userType;
		this.applicationUser = applicationUser;
	}

	/**
	 * @return bruker
	 */
	public ApplicationUser getApplicationUser() {
		return applicationUser;
	}

	/**
	 * @param applicationUser
	 */
	public void setApplicationUser(ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
	}

	/**
	 * @return rolleid
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return bukertype
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof UserRole))
			return false;
		UserRole castOther = (UserRole) other;
		return new EqualsBuilder().append(userType, castOther.userType).append(
				applicationUser, castOther.applicationUser).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(userType).append(applicationUser)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return userType.toString();
	}
}
