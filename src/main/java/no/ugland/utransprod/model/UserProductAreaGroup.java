package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for tabell USER_PRODUCT_AREA_GROUP
 * 
 * @author atle.brekka
 * 
 */
public class UserProductAreaGroup extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer groupId;

	/**
	 * 
	 */
	private ProductAreaGroup productAreaGroup;

	/**
	 * 
	 */
	private ApplicationUser applicationUser;

	/**
	 * 
	 */
	public UserProductAreaGroup() {
		super();
	}

	/**
	 * @param groupId
	 * @param productAreaGroup
	 * @param applicationUser
	 */
	public UserProductAreaGroup(Integer groupId,
			ProductAreaGroup productAreaGroup, ApplicationUser applicationUser) {
		super();
		this.groupId = groupId;
		this.productAreaGroup = productAreaGroup;
		this.applicationUser = applicationUser;
	}

	/**
	 * @return id
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return produktområdegruppe
	 */
	public ProductAreaGroup getProductAreaGroup() {
		return productAreaGroup;
	}

	/**
	 * @param productAreaGroup
	 */
	public void setProductAreaGroup(ProductAreaGroup productAreaGroup) {
		this.productAreaGroup = productAreaGroup;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof UserProductAreaGroup))
			return false;
		UserProductAreaGroup castOther = (UserProductAreaGroup) other;
		return new EqualsBuilder().append(productAreaGroup,
				castOther.productAreaGroup).append(applicationUser,
				castOther.applicationUser).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(productAreaGroup).append(
				applicationUser).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return productAreaGroup.getProductAreaGroupName();
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
}
