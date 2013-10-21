package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for tabell USER_TYPE_ACCESS
 * 
 * @author atle.brekka
 * 
 */
public class UserTypeAccess extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer userTypeAccessId;

	/**
	 * 
	 */
	private Integer writeAccess;

	/**
	 * 
	 */
	private UserType userType;

	/**
	 * 
	 */
	private WindowAccess windowAccess;

	/**
	 * 
	 */
	public UserTypeAccess() {
		super();
	}

	/**
	 * @param userTypeAccessId
	 * @param writeAccess
	 * @param userType
	 * @param windowAccess
	 */
	public UserTypeAccess(Integer userTypeAccessId, Integer writeAccess,
			UserType userType, WindowAccess windowAccess) {
		super();
		this.userTypeAccessId = userTypeAccessId;
		this.writeAccess = writeAccess;
		this.userType = userType;
		this.windowAccess = windowAccess;
	}

	/**
	 * @return brukertype
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
	 * @return id
	 */
	public Integer getUserTypeAccessId() {
		return userTypeAccessId;
	}

	/**
	 * @param userTypeAccessId
	 */
	public void setUserTypeAccessId(Integer userTypeAccessId) {
		this.userTypeAccessId = userTypeAccessId;
	}

	/**
	 * @return vindusaksess
	 */
	public WindowAccess getWindowAccess() {
		return windowAccess;
	}

	/**
	 * @param windowAccess
	 */
	public void setWindowAccess(WindowAccess windowAccess) {
		this.windowAccess = windowAccess;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof UserTypeAccess))
			return false;
		UserTypeAccess castOther = (UserTypeAccess) other;
		return new EqualsBuilder().append(userType, castOther.userType).append(
				windowAccess, castOther.windowAccess).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(userType).append(windowAccess)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"userTypeAccessId", userTypeAccessId).append("userType",
				userType).append("windowAccess", windowAccess).toString();
	}

	/**
	 * @return 1 dersom skriveaksess
	 */
	public Integer getWriteAccess() {
		return writeAccess;
	}

	/**
	 * @param writeAccess
	 */
	public void setWriteAccess(Integer writeAccess) {
		this.writeAccess = writeAccess;
	}
}
